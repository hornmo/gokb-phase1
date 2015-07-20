<g:set var="current" value="${session.getAt('SPRING_SECURITY_CONTEXT').getAt('authentication').getAt('credentials')}" scope="page" />
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
        <td colspan="1" style="vertical-align:top;"><h3>${dsl.description}</h3></td>
      </tr>
      <g:each in="${dsl.criterion}" var="c">
        <tr>
          <td style="vertical-align:top; white-space: nowrap;">&nbsp;&nbsp;${c[0]}</td>
            <% println(c+"\n\n")  %>
          <td class="vote" style="white-space: nowrap;">
            <g:if test="${(c[1]=='Unknown'||c[1]==null)}">
                <i id="${c[2]}_${c[3]}_q_neutral" class="text-neutral">
                    <span class="fa fa-question-circle fa-2x"></span><span>cast your vote</span>
                </i></br>
            </g:if>
            <g:else>
                <span>Voted</span>
                <sec:username/>
            </g:else>

            <a id="${c[2]}_${c[3]}_r_negative" href='#' ${c[1]=='Red'?'class="text-negative"':''} ><i class="fa fa-times-circle fa-2x"></i></a> &nbsp;
            <a id="${c[2]}_${c[3]}_a_contentious" href='#' ${c[1]=='Amber'?'class="text-contentious"':''} ><i class="fa fa-info-circle fa-2x"></i></a>&nbsp;
            <a id="${c[2]}_${c[3]}_g_positive" href='#' ${c[1]=='Green'?'class="text-positive"':''} ><i class="fa fa-check-circle fa-2x"></i></a>



            %{--Others have voted already--}%
            <g:if test="${true}" >
                </br></br>
                <dl id="otherVoters" style="margin: 0; padding: 0">
                    <dt>Others Voted</dt>
                    %{--<g:each in="">--}%
                        <dd>
                            <p class="DSAuthor DSInlineBlock">Test</p>
                            <p class="DSVote DSInlineBlock">
                                <span id="${c[2]}_${c[3]}_r_negative" ${c[1]=='Red'?'class="text-negative"':''} ><i class="fa fa-times-circle fa-2x"></i></span> &nbsp;
                                <span id="${c[2]}_${c[3]}_a_contentious"  ${c[1]=='Amber'?'class="text-contentious"':''} ><i class="fa fa-info-circle fa-2x"></i></span>&nbsp;
                                <span id="${c[2]}_${c[3]}_g_positive" ${c[1]=='Green'?'class="text-positive"':''} ><i class="fa fa-check-circle fa-2x"></i></span>
                            </p>
                        </dd>
                    %{--</g:each>--}%
                </dl>
            </g:if>

          </td>
          <td style="vertical-align:top;">

            <dl id="${c[2]}_${c[3]}_notestable">
                <g:each in="${c[4]?.notes}" var="x">

                    <dt> <span>Author</span> </dt>
                    <dd>
                        <p class="DSInlineBlock">testing long instution</p>
                        <g:if test="${c[4]?.value.value == null || c[4]?.value.value == 'Amber'}" >
                            <p class="triangle-border DSInlineBlock border-negative">
                        </g:if>
                        <g:elseif test="${c[4]?.value.value == 'Red'}">
                                <p class="triangle-border DSInlineBlock border-contentious">
                        </g:elseif>
                        <g:elseif test="${c[4]?.value.value == 'Green'}">
                            <p class="triangle-border DSInlineBlock border-positive">
                        </g:elseif>

                            <g:if test="${x.criterion.user.username == current}" >
                                <g:xEditable owner="${x}" field="note"/>
                                <a data-comp="${c[2]}_${c[3]}" data-note="${x.id}" class="noteDelete text-negative fa fa-times-circle fa-2x"></a>
                                ${c[4]?.value.value}
                            </g:if>
                            <g:else>
                                ${x.note}
                            </g:else>
                        </p>
                    </dd>
                </g:each>
            </dl>

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