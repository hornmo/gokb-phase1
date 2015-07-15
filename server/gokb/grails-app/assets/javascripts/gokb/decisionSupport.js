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

function addNote(id) {

  var v = $('#'+id+'_newnote').val();

  $.ajax({
    // libs and culture: 0894-8631
    url: gokb.config.baseUrl+'/ajaxSupport/criterionComment?comp='+id+'&comment='+v,
    dataType:"json"
  }).done(function(data) {
    // alert(data);
  });

  $('#'+id+'_notestable').append("<tr><td>"+v+"</td></tr>");
  $('#'+id+'_newnote').val('');

  return false;
}