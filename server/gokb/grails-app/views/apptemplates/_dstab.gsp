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
      <g:each in="${dsl.criterion}" var="id, c">
        <tr>
          <td style="vertical-align:top; white-space: nowrap; width: 15%;">&nbsp;&nbsp;${c['title']} </td>
            <% println("\n\n\nID :" )  %>
            <% println(id)  %>
            <% println("</br>")  %>
            <% println(c)  %>
            <% println("</br></br>")  %>
          <td class="vote" style="white-space: nowrap; width: 25%;">
            <g:if test="${(c[1]=='Unknown'||c[1]==null)}">
                <i id="${c['appliedTo']}_${id}_q_neutral" class="text-neutral">
                    <span class="fa fa-question-circle fa-2x"></span><span>cast your vote</span>
                </i></br>
            </g:if>
            <g:else>
                <span>You have voted</span>
            </g:else>
             <div class="DSVote" id="currentVote${c['appliedTo']}_${id}">
                 <a id="${c['appliedTo']}_${id}_r_negative" href='#' ${c[1]=='Red'?'class="text-negative selected"':''} ><i class="fa fa-times-circle fa-2x"></i></a> &nbsp;
                 <a id="${c['appliedTo']}_${id}_a_contentious" href='#' ${c[1]=='Amber'?'class="text-contentious selected"':''} ><i class="fa fa-info-circle fa-2x"></i></a>&nbsp;
                 <a id="${c['appliedTo']}_${id}_g_positive" href='#' ${c[1]=='Green'?'class="text-positive selected"':''} ><i class="fa fa-check-circle fa-2x"></i></a>
             </div>

            %{--Others have voted already--}%
            <g:if test="${true}" >
                </br></br>
                <dl id="otherVoters" style="margin: 0; padding: 0">
                    <dt>Others Voted</dt>
                    %{--<g:each in="">--}%
                        <dd>
                            %{--title="${x.criterion?.user.displayName}"--}%
                            <p class="DSAuthor DSInlineBlock">Test</p>
                            <p class="DSVote DSInlineBlock">
                                <span id="${c['appliedTo']}_${id}_r_negative" ${c[1]=='Red'?'class="text-negative"':''} ><i class="fa fa-times-circle fa-2x"></i></span> &nbsp;
                                <span id="${c['appliedTo']}_${id}_a_contentious"  ${c[1]=='Amber'?'class="text-contentious"':''} ><i class="fa fa-info-circle fa-2x"></i></span>&nbsp;
                                <span id="${c['appliedTo']}_${id}_g_positive" ${c[1]=='Green'?'class="text-positive"':''} ><i class="fa fa-check-circle fa-2x"></i></span>
                            </p>
                        </dd>
                    %{--</g:each>--}%
                </dl>
            </g:if>

          </td>
          <td style="vertical-align:top; width:60%;">

            <dl id="${c['appliedTo']}_${id}_notestable">
                <g:each in="${c['notes']}" var="note">
                    <% println("</br> " + note + "</br>") %>
                    <dt>
                        <span class="DSAuthor">${note?.criterion?.user.username}</span>-
                        <g:if test="${note?.criterion?.value?.value == 'Unknown'}">-(NOT VOTED)--</g:if>
                        <i class="DSTimestamp">
                            <g:if test="${note.lastUpdated == note.dateCreated}"><g:formatDate date="${note.dateCreated}" /></g:if>
                            <g:else>Edited: <g:formatDate date="${note.lastUpdated}" /></g:else>
                        </i>
                    </dt>
                    <dd>
                        <p class="DSInlineBlock DSOrg">
                            <g:if test="${note.criterion?.user?.org?.name == null}">
                                N/A
                            </g:if>
                            <g:else>
                                ${note.criterion?.user?.org?.name}
                            </g:else>
                        </p>
                        <g:if test="${note?.criterion?.value?.value == null || note?.criterion?.value?.value == 'Amber'}" >
                            <p class="triangle-border DSInlineBlock text-contentious border-contentious">
                        </g:if>
                        <g:elseif test="${note?.criterion?.value?.value == 'Red'}">
                                <p class="triangle-border DSInlineBlock text-negative border-negative">
                        </g:elseif>
                        <g:elseif test="${note?.criterion?.value?.value == 'Green'}">
                            <p class="triangle-border DSInlineBlock text-positive border-positive">
                        </g:elseif>
                        <g:elseif test="${note?.criterion?.value?.value == 'Unknown'}">
                            <p class="triangle-border DSInlineBlock text-contentious border-contentious">
                        </g:elseif>
                            <g:if test="${note.criterion.user.username == user.username}" >
                                <g:xEditable owner="${note}" field="note"/>
                                <a data-comp="${c['appliedTo']}_${id}" data-note="${note.id}" class="noteDelete text-negative fa fa-times-circle fa-2x"></a>
                            </g:if>
                            <g:else>
                                ${note.note}
                            </g:else>
                        </p>
                    </dd>
                </g:each>
            </dl>

              <form role="form" class="form" onsubmit='return addNote("${c['appliedTo']}_${id}", "${user.username}", "${user?.org?.name}")'>
              <div class="form-group">
                <div class="input-group">
                  <span class="input-group-addon">
                    Add note
                  </span>
                  <textarea class="form-control" id="${c['appliedTo']}_${id}_newnote"></textarea>
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