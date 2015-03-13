package gokb

import org.gokb.cred.*

class DataValidationJob {
  static final String CAUSE = "TIPP Coverage extends beyond title publication date"
  static final String ACTION = "Review TIPP coverage date"  
  // Every ten minutes
  static triggers = {
    cron name: 'DataValidationJobTrigger', cronExpression: "* 0/10 * * * ?", startDelay:60000
  }

  def execute() {
    tippCoverageConflicts();
  }

  def tippCoverageConflicts() {
    def status_current = RefdataCategory.lookupOrCreate(KBComponent.RD_STATUS, KBComponent.STATUS_CURRENT)

    def hql_query = "select tipp from TitleInstancePackagePlatform tipp, Combo as c where c.toComponent=tipp and c.type.value='TitleInstance.Tipps' and (c.fromComponent.publishedFrom < tipp.accessStartDate OR c.fromComponent.publishedTo > tipp.accessEndDate))"

    def violating_tipps = TitleInstancePackagePlatform.executeQuery(hql_query)
    def review_deleted =  RefdataCategory.lookupOrCreate('ReviewRequest.Status', 'Deleted')
    violating_tipps.each{ tipp ->

      if(tipp.reviewRequests?.find{it.descriptionOfCause == CAUSE && it.status != review_deleted }){
        //review already raised
      }else{
        ReviewRequest.raise(tipp,ACTION,CAUSE)
        // log.debug("Generated review request for ${tipp}")
      }
    }
  }
}
    
