<table class="table table-bordered">
  <thead>
    <tr>
      <th id="44_33" style="color:pink;">Criterion</th>
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
          <td>&nbsp;&nbsp;<h5>${c[0]}</h5></td>
          <td>
            ${c[1]} 

            <i id="${c[2]}_${c[3]}_q" class="fa fa-question-circle fa-2x" style="color:${c[1]==null?'blue':'grey'};"></i>&nbsp;

            <a href='javascript:setAppliedCriterion("${c[2]}","${c[3]}","r","red");'><i id="${c[2]}_${c[3]}_r" 
                                                                          class="fa fa-times-circle fa-2x" 
                                                                          style="color:${c[1]=='red'?'red':'grey'};"></i></a> &nbsp;

            <a href='javascript:setAppliedCriterion("${c[2]}","${c[3]}","a","#FFBF00");'><i id="${c[2]}_${c[3]}_a" 
                                                                          class="fa fa-info-circle fa-2x" 
                                                                          style="color:${c[1]=='amber'?'#FFBF00':'grey'};"></i></a>&nbsp;

            <a href='javascript:setAppliedCriterion("${c[2]}","${c[3]}","g","green");'><i id="${c[2]}_${c[3]}_g" 
                                                                          class="fa fa-check-circle fa-2x" 
                                                                          style="color:${c[1]=='green'?'green':'grey'};"></i></a>
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

<asset:script type="text/javascript">
  function setAppliedCriterion(component_id, criterion_id, v ,c) {
    // grey out then select the appropriate
    $('#'+component_id+'_'+criterion_id+'_q').css('color','grey');
    $('#'+component_id+'_'+criterion_id+'_r').css('color','grey');
    $('#'+component_id+'_'+criterion_id+'_a').css('color','grey');
    $('#'+component_id+'_'+criterion_id+'_g').css('color','grey');

    $('#'+component_id+'_'+criterion_id+'_'+v).css('color',c);
  }
</asset:script>
