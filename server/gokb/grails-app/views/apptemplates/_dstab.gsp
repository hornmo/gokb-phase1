<table id="tab-decision-suppport" class="table">
  <tbody>
    <g:each in="${d.decisionSupportLines.values()}" var="dsl">
      <tr>
        <td colspan="3" style="vertical-align:top;"><h3>${dsl.description}</h3></td>
      </tr>
      <g:each in="${dsl.criterion}" var="c">
        <tr>
          <td style="vertical-align:top; white-space: nowrap;" colspan="2">&nbsp;&nbsp;<h4>${c.criteria_name}</h4>
        </tr>
        <tr>
          <td>
            <table class="table">
              <tr>
                <td style="border-top:none;">Summary:</td>
                <td style="border-top:none;"><i class="fa fa-times-circle fa-2x text-negative"></i> <br/> <span class="badge badge-warning"> ${c.red_votes} </span> </td>
                <td style="border-top:none;"><i class="fa fa-info-circle fa-2x text-contentious"></i> <br/> <span class="badge badge-warning"> ${c.amber_votes} </span> </a> </td>
                <td style="border-top:none;"><i class="fa fa-check-circle fa-2x text-positive"></i> <br/> <span class="badge badge-warning"> ${c.green_votes} </span> </a> </td>
              </tr>
              <tr>
                <td><strong>Your Assessment:</strong></td>
                <td><a id="_r_negative" href='#' class="" style="opacity: 0.4;" ><i class="fa fa-times-circle fa-2x"></i></a> </td>
                <td><a id="_a_contentious" href='#' class=""  style="opacity: 0.4;"><i class="fa fa-info-circle fa-2x"></i> </a> </td>
                <td><a id="_g_positive" href='#' class="" style="opacity: 0.4;"><i class="fa fa-check-circle fa-2x"></i></a> </td>
              </tr>
              <tr>
                <td>Official Assessment:</td>
                <td><a id="_r_negative" href='#' class="" style="opacity: 0.4;" ><i class="fa fa-times-circle fa-2x"></i></a> </td>
                <td><a id="_a_contentious" href='#' class=""  style="opacity: 0.4;"><i class="fa fa-info-circle fa-2x"></i> </a> </td>
                <td><a id="_g_positive" href='#' class="" style="opacity: 0.4;"><i class="fa fa-check-circle fa-2x"></i></a> </td>
              </tr>
            </table>
          </td>
          <td style="vertical-align:top;">
            <table class="table" id="${c.component_id}_${c.criteria_id}_notestable">
              <g:each in="${c.notes}" var="n">
                <tr>
                  <td>${n.user.displayName}</td>
                  <td>${n.commentTimestamp}</td>
                  <td>${n.note}</td>
                </tr>
              </g:each>
            </table>

            <form role="form" class="form" onsubmit='return addNote("${c.component_id}","${c.criteria_id}");'>
              <div class="form-group">
                <div class="input-group">
                  <span class="input-group-addon">
                    Add note
                  </span>
                  <textarea class="form-control" id="${c.component_id}_${c.criteria_id}_newnote"></textarea>
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
