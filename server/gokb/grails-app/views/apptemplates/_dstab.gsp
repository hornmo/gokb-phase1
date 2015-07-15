<table id="tab-decision-suppport" class="table table-bordered">
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
          <td class="vote" style="white-space: nowrap;">
            <i id="${c[2]}_${c[3]}_q_neutral" class="fa fa-question-circle fa-2x${(c[1]=='Unknown'||c[1]==null)?' text-neutral':''}"></i>&nbsp;
            <a id="${c[2]}_${c[3]}_r_negative" href='#' ${c[1]=='Red'?'class="text-negative"':''} ><i class="fa fa-times-circle fa-2x"></i></a> &nbsp;
            <a id="${c[2]}_${c[3]}_a_contentious" href='#' ${c[1]=='Amber'?'class="text-contentious"':''} ><i class="fa fa-info-circle fa-2x"></i></a>&nbsp;
            <a id="${c[2]}_${c[3]}_g_positive" href='#' ${c[1]=='Green'?'class="text-positive"':''} ><i class="fa fa-check-circle fa-2x"></i></a>
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
  
  
</asset:script>
