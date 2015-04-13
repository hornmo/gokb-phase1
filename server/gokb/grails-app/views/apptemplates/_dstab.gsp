<table class="table table-bordered">
  <thead>
    <tr>
      <th>Criterion</th>
      <th>Value</th>
      <th>Notes</th>
    </tr>
  </thead>
  <tbody>
    <g:each in="${d.decisionSupportLines.values()}" var="dsl">
      <tr>
        <td colspan="3" style="vertical-align:top;"><h3>${dsl.description}</h3></td>
      </tr>
      <g:each in="${dsl.criterion}" var="c">
        <tr>
          <td style="vertical-align:top; white-space: nowrap;">&nbsp;&nbsp;${c[0]}</td>
          <td style="white-space: nowrap;">
            <i id="${c[2]}_${c[3]}_r" class="fa fa-times-circle fa-2x" style="color:${(c[1]=='Unknown'||c[1]==null)?'blue':'grey'};"></i>&nbsp;

            <a href='javascript:setAppliedCriterion("${c[2]}","${c[3]}","r","red");'><i id="${c[2]}_${c[3]}_r" 
                                                                          class="fa fa-times-circle fa-2x" 
                                                                          style="color:${c[1]=='Red'?'red':'grey'};"></i></a> &nbsp;

            <a href='javascript:setAppliedCriterion("${c[2]}","${c[3]}","a","#FFBF00");'><i id="${c[2]}_${c[3]}_a" 
                                                                          class="fa fa-info-circle fa-2x" 
                                                                          style="color:${c[1]=='Amber'?'#FFBF00':'grey'};"></i></a>&nbsp;

            <a href='javascript:setAppliedCriterion("${c[2]}","${c[3]}","g","green");'><i id="${c[2]}_${c[3]}_g" 
                                                                          class="fa fa-check-circle fa-2x" 
                                                                          style="color:${c[1]=='Green'?'green':'grey'};"></i></a>
          </td>
          <td style="vertical-align:top;">

            <table class="table table-bordered">
              <tbody id="${c[2]}_${c[3]}_notestable">
                <g:each in="${c[4]?.notes}" var="x">
                  <tr><td>${x.note}</td></tr>
                </g:each>
              </tbody>
            </table>

            <form role="form" class="form" onsubmit='return addNote("${c[2]}_${c[3]}")'>
              <div class="form-group">
                <div class="input-group">
                  <span class="input-group-addon">
                    Add note
                  </span>
                  <textarea class="form-control" id="${c[2]}_${c[3]}_newnote"></textarea>
                  <span class="input-group-addon">
                    <button type="submit">Add</button>
                  </span>
                </div>
              </div>
            </form>
          </td>
        </tr>
      </g:each>
    </g:each>
  </tbody>
</table>

<asset:script type="text/javascript">
  function setAppliedCriterion(component_id, criterion_id, v ,c) {
    // grey out then select the appropriate
    $('#'+component_id+'_'+criterion_id+'_q').css('color','grey');
    $('#'+component_id+'_'+criterion_id+'_r').css('color','grey');
    $('#'+component_id+'_'+criterion_id+'_a').css('color','grey');
    $('#'+component_id+'_'+criterion_id+'_g').css('color','grey');

    $('#'+component_id+'_'+criterion_id+'_'+v).css('color',c);

    $.ajax({
      url: gokb.config.baseUrl+'/ajaxSupport/appliedCriterion?comp='+component_id+'&crit='+criterion_id+'&val='+v,
      dataType:"jsonp",
      crossDomain: true
    }).done(function(data) {
      // alert(data);
    });
  }

  function addNote(id) {

    var v = $('#'+id+'_newnote').val();

    $.ajax({
      // libs and culture: 0894-8631
      url: gokb.config.baseUrl+'/ajaxSupport/criterionComment?comp='+id+'&comment='+v,
      dataType:"jsonp",
      crossDomain: true
    }).done(function(data) {
      // alert(data);
    });

    $('#'+id+'_notestable').append("<tr><td>"+v+"</td></tr>");
    $('#'+id+'_newnote').val('');
    
    return false;
  }
</asset:script>
