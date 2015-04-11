<table class="table table-bordered">
  <thead>
    <tr>
      <th>Criterion</th>
      <th>Value</th>
    </tr>
  </thead>
  <tbody>
    <g:each in="${d.decisionSupportLines.values()}" var="dsl">
      <tr>
        <td colspan="3">${dsl.description}</td>
      </tr>
      <g:each in="${dsl.criterion}" var="c">
        <tr>
          <td>${c[0]}</td>
          <td>${c[1]}</td>
        </tr>
      </g:each>
    </g:each>
  </tbody>
</table>
