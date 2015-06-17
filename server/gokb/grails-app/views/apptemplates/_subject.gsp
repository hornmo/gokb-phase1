<div id="content">
  <dl class="dl-horizontal">
    <dt> <g:annotatedLabel owner="${d}" property="name">Classsifier</g:annotatedLabel> </dt>
    <dd> <g:xEditable class="ipe" owner="${d}" field="name" /> </dd>
 
    <dt> <g:annotatedLabel owner="${d}" property="scheme">Classification Scheme</g:annotatedLabel> </dt>
    <dd> <g:xEditableRefData owner="${d}" field="scheme" config='Subject.Classification' /></dd>

    <dt> <g:annotatedLabel owner="${d}" property="clsmrk">Heading</g:annotatedLabel> </dt>
    <dd> <g:xEditable class="ipe" owner="${d}" field="heading" /> </dd>

    </dl>
    <g:if test="${d.id != null}">
      <ul id="tabs" class="nav nav-tabs">
        <li><a href="#titles" data-toggle="tab">Published Titles</a></li>
      </ul>
      <div id="my-tab-content" class="tab-content">
      <div class="tab-pane active" id="titles">
        <g:link class="display-inline" controller="search" action="index"
		            params="[qbe:'g:1eBooks', hide:['qp_subject'], qp_subject:'org.gokb.cred.Subject:'+d.id]"
	                id="">Book List 2</g:link> 
      </div>
    </div>
  </g:if>
</div>
