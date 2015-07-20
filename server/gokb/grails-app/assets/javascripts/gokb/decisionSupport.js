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

    $('.noteDelete').click(function (e) {
        var agreed = confirm("Are you sure you wish to delete?");
        if (agreed == true) {
            var elememt = $(this);
            var note    = elememt.data('note');
            deleteNote(elememt,note);
        }

        //e.stopImmediatePropagation();
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
    console.log(data);
    //  $('#'+id+'_notestable').append("<tr><td>"+v+"</td></tr>");
      $('#'+id+'_notestable').append("<dt> <span>Author</span> </dt><dd> <p class='DSInlineBlock'>testing long instution</p> <p class='triangle-border DSInlineBlock'> <span id='org.gokb.cred.DSNote:"+ data.newNote +":note' class='newNote xEditableValue  editable editable-pre-wrapped editable-click' data-pk='org.gokb.cred.DSNote:"+data.newNote+"' data-name='note' data-tpl='<textarea wrap=\"off\"></textarea>' data-type='textarea' data-url='/gokbLabs/ajaxSupport/editableSetValue'>"+ v +"</span> <a data-comp= "+ id +" data-note= "+ data.newNote + " class='noteDelete text-negative fa fa-times-circle fa-2x'></a></p></dd>");
      $('.newNote').editable();
      $('#'+id+'_newnote').val('');
  });



  return false;
}


function deleteNote(target,note) {
  $.ajax({
    url: gokb.config.baseUrl+'/ajaxSupport/criterionCommentDelete?note='+note,
    dataType:"json"
  }).done(function(data) {
    console.log(data);
      if(data.status == 'OK')
      {
          var dl = target.parent().parent()
          dl.prev().remove();
          dl.remove()
      }
  });

  return false;
}