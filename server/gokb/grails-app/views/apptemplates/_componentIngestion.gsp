<g:set var="ctxoid" value="${org.gokb.cred.KBComponent.deproxy(d).class.name}:${d.id}"/>

<table class="table table-striped table-bordered">
  <thead>
    <tr>
      <g:each in="${cols}" var="ch">
        <th>${ch.colhead}</th>
      </g:each>
    </tr>
  </thead>
  <tbody>
    <g:each in="${d[property].sort{a,b-> a.component.dateCreated.compareTo(b.component.dateCreated)}}" var="row">
     <g:set var="rowoid" value="${org.gokb.cred.KBComponent.deproxy(row).class.name}:${row.id}"/>
      <tr>
        <g:each in="${cols}" var="c">           
          <td>
            <g:if test="${c.action=='link-component'}">
              <g:link controller="resource" action="show" id="org.gokb.cred.KBComponent:${row.component.id}">${groovy.util.Eval.x(row, 'x.' + c.expr)}</g:link>
            </g:if>
            <g:else>${groovy.util.Eval.x(row, 'x.' + c.expr)}</g:else>
          </td>
        </g:each>
      </tr>
    </g:each>
  </tbody>
</table>