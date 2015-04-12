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
        <td colspan="3"><h3>${dsl.description}</h3></td>
      </tr>
      <g:each in="${dsl.criterion}" var="c">
        <tr>
          <td>&nbsp;&nbsp;${c[0]}</td>
          <td>
            ${c[1]} 

            <i class="fa fa-question-circle fa-2x" style="color:${c[1]==null?'blue':'grey'};"></i>&nbsp;

            <i class="fa fa-times-circle fa-2x" style="color:${c[1]=='red'?'red':'grey'};"></i>&nbsp;
            <i class="fa fa-info-circle fa-2x" style="color:${c[1]=='amber'?'#FFBF00':'grey'};"></i>&nbsp;
            <i class="fa fa-check-circle fa-2x" style="color:${c[1]=='green'?'green':'grey'};"></i>
          </td>
          <td>
            <form class="form-inline">
              <div class="form-group">
                <label for="note">Add note:</label> <textarea></textarea>
              </div>
            </form>
          </td>
        </tr>
      </g:each>
    </g:each>
  </tbody>
</table>
