<table class="table table-bordered">
  <thead>
    <tr>
      <th>Category</th>
      <th>Criterion</th>
      <th>Value</th>
    </tr>
  </thead>
  <tbody>
    <g:each in="${d.decisionSupportLines}" var="dsl">
      <tr>
        <td>${dsl[0]}</td>
        <td>${dsl[0]}</td>
        <td>${dsl[1]}</td>
      </tr>
    </g:each>
  </tbody>
</table>
