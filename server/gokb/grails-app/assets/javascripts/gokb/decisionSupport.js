$(document).ready(function(){
  $('#tab-decision-suppport .vote a').click(function(e){

    // The ID.
    var id = $(this).attr('id');
    var id_comp = id.split("_");

    setAppliedCriterion($(this), id_comp[0], id_comp[1], id_comp[2], id_comp[3]);

    // Prevent the default action.
    e.stopImmediatePropagation();
    return false;
  });
});

function setAppliedCriterion(target, component_id, criterion_id, v ,c) {
  $.ajax({
    url: gokb.config.baseUrl+'/ajaxSupport/appliedCriterion?comp='+component_id+'&crit='+criterion_id+'&val='+v,
    dataType:"json",
  }).done(function(data) {
    // alert(data);

    if (data && "status" in data && data.status == 'OK') {

      // Remove the
      target.siblings().removeClass("text-neutral text-negative text-contentious text-positive");
      target.addClass("text-" + c);
    }
  });
}

function vote(component_id,criteria_id,assessment) {
  $.ajax({
    // libs and culture: 0894-8631
    url: gokb.config.baseUrl+'/ajaxSupport/criterionVote?comp='+component_id+'&crit='+criteria_id+'&vote='+assessment,
    dataType:"json"
  }).done(function(data) {
    // alert(data);
    // $('#'+component_id+'_'+criteria_id+'_notestable').append("<tr><td>"+data.user+"</td><td>"+data.timestamp+"</td><td>"+data.note+"</td></tr>");
    // $('#'+component_id+'_'+criteria_id+'_newnote').val('');
    $('#'+component_id+'_'+criteria_id+'_r').text(data.red_count);
    $('#'+component_id+'_'+criteria_id+'_a').text(data.amber_count);
    $('#'+component_id+'_'+criteria_id+'_g').text(data.green_count);
    $('#'+component_id+'_'+criteria_id+'_u_g').css('opacity','0.4');
    $('#'+component_id+'_'+criteria_id+'_u_a').css('opacity','0.4');
    $('#'+component_id+'_'+criteria_id+'_u_r').css('opacity','0.4');
    if ( assessment==='Red') {
      $('#'+component_id+'_'+criteria_id+'_u_r').css('opacity','1.0');
    }
    else if ( assessment==='Amber') {
      $('#'+component_id+'_'+criteria_id+'_u_a').css('opacity','1.0');
    }
    else{
      $('#'+component_id+'_'+criteria_id+'_u_g').css('opacity','1.0');
    }

  });
}


function addNote(component_id,criteria_id) {

  var v = $('#'+component_id+'_'+criteria_id+'_newnote').val();

  $.ajax({
    // libs and culture: 0894-8631
    url: gokb.config.baseUrl+'/ajaxSupport/criterionComment?comp='+component_id+'&crit='+criteria_id+'&comment='+v,
    dataType:"json"
  }).done(function(data) {
    // alert(data);
    $('#'+component_id+'_'+criteria_id+'_notestable').append("<tr><td>"+data.user+"</td><td>"+data.timestamp+"</td><td>"+data.note+"</td></tr>");
    $('#'+component_id+'_'+criteria_id+'_newnote').val('');

  });


  return false;
}
