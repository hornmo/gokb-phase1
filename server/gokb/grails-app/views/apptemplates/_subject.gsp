<div id="content"
  <dl class="dl-horizontal">
    <dt> <g:annotatedLabel owner="${d}" property="name">Heading</g:annotatedLabel> </dt>
    <dd> <g:xEditable class="ipe" owner="${d}" field="name" /> </dd>
 
    <dt> <g:annotatedLabel owner="${d}" property="scheme">Classification Scheme</g:annotatedLabel> </dt>
    <dd> <g:xEditableRefData owner="${d}" field="scheme" config='Subject.Classification' /></dd>

    <dt> <g:annotatedLabel owner="${d}" property="clsmrk">Classification</g:annotatedLabel> </dt>
    <dd> <g:xEditableRefData owner="${d}" field="clsmrk" config='Dewey' /></dd>  
    <g:if test="${d.parent != null}">
      <dt>
        <g:annotatedLabel owner="${d}" property="parent">Broader Subject</g:annotatedLabel>
      </dt>
      <dd>
        <g:link controller="resource" action="show"
	          id="${d.parent.getClassName()+':'+d.parent.id}">
	    ${d.parent.name}
	</g:link>
      </dd>
    </g:if>
    <g:if test="${d.children?.size() > 0}">
      <dt>
        <g:annotatedLabel owner="${d}" property="children">Narrower Subjects</g:annotatedLabel>
      </dt>
      <dd>
        <ul>
          <g:each in="${d.children}" var="c">
	      <li><g:link controller="resource" action="show"
		              id="${c.getClassName()+':'+c.id}">
		  ${c.name}
		  </g:link></li>
	  </g:each>
	</ul>
      </dd>
    </g:if>
    <dt>
      <g:annotatedLabel owner="${d}" property="children">Narrower Subjects</g:annotatedLabel>
    </dt>
    <dd>
      <g:render template="comboList" contextPath="../apptemplates"
			  model="${[d:d, property:'children', cols:[[expr:'name',colhead:'Subject Heading', action:'link']],targetClass:'org.gokb.cred.Subject',direction:'in']}" />
    </dd>
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
