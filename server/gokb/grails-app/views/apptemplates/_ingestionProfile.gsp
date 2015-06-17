<dl class="dl-horizontal">
  <dt> <g:annotatedLabel owner="${d}" property="name">Name</g:annotatedLabel> </dt>
  <dd> <g:xEditable class="ipe" owner="${d}" field="name" /> </dd>
  <sec:ifAnyGranted roles="ROLE_ADMIN">
    <dt> Admin Command: </dt>
    <dd> <g:link controller='EBookUpload' 
        action='forceIngest' 
        id='org.gokb.cred.IngestionProfile:${d.id}'>Force Ingest</g:link>
    </dd>
  </sec:ifAnyGranted>
</dl>
<div id="content">
  <ul id="tabs" class="nav nav-tabs">
    <li class="active"><a href="#details" data-toggle="tab">Ingestion Profile Details</a></li>
    <li><a href="#addprops" data-toggle="tab">Custom Fields <span class="badge badge-warning">${d.additionalProperties?.size()}</span></a></li>
    <li><a href="#datafiles" data-toggle="tab">Data Files <span class="badge badge-warning">${d.datafiles?.size()}</span></a></li>
    <li><a href="#newtipps" data-toggle="tab">New TIPPS <span class="badge badge-warning">${d.newTipps?.size()}</span></a></li>
    <li><a href="#gonetipps" data-toggle="tab">Removed TIPPS <span class="badge badge-warning">${d.missingTipps?.size()}</span></a></li>
      
  </ul>
  <div id="my-tab-content" class="tab-content">
    <div class="tab-pane active" id="details">
      <dl class="dl-horizontal">
        <dt> <g:annotatedLabel owner="${d}" property="status">Status</g:annotatedLabel> </dt>
        <dd> <g:xEditableRefData owner="${d}" field="status" config='KBComponent.Status' /> </dd>
        <dt> <g:annotatedLabel owner="${d}" property="packageName">Package Name</g:annotatedLabel> </dt>
        <dd> ${d.packageName} </dd>
        <dt> <g:annotatedLabel owner="${d}" property="packageType">Package Type</g:annotatedLabel> </dt>
        <dd> ${d.packageType} </dd>
        <dt> <g:annotatedLabel owner="${d}" property="source">Source</g:annotatedLabel> </dt>
        <dd> <g:manyToOneReferenceTypedown owner="${d}" field="source" baseClass="org.gokb.cred.Source">${d.source?.name}</g:manyToOneReferenceTypedown> </dd>
      </dl>
    </div>

    <div class="tab-pane" id="addprops">
       <g:render template="addprops" contextPath="../apptemplates" model="${[d:d]}" />
    </div>
    <div class="tab-pane" id="datafiles">
      <g:render template="simpleCombos" contextPath="../apptemplates" 
      model="${[d:d, property:'datafiles', cols:[
		  [expr:'uploadName', colhead:'File Name', action:'link'],
		  [expr:'dateCreated', colhead:'Creation Date'],
		  [expr:'tipps.size()', colhead:'# Tipps']
		  ]  ]}" />
    </div>
    
    <div class="tab-pane" id="newtipps">
      <g:render template="tippdisplay" contextPath="../apptemplates" model="${[d:d.newTipps]}" />
    </div>

    <div class="tab-pane" id="gonetipps">
      <g:render template="tippdisplay" contextPath="../apptemplates" model="${[d:d.missingTipps]}" />
    </div>
  </div>
  <g:render template="componentStatus" contextPath="../apptemplates" model="${[d:displayobj, rd:refdata_properties, dtype:'KBComponent']}" />
</div>


