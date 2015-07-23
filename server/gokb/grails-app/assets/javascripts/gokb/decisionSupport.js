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

        e.stopImmediatePropagation();
    });

});

function setAppliedCriterion(target, component_id, criterion_id, v ,c) {
  $.ajax({
    url: gokb.config.baseUrl+'/ajaxSupport/appliedCriterion?comp='+component_id+'&crit='+criterion_id+'&val='+v,
    dataType:"json"
  }).done(function(data) {
    if (data && "status" in data && data.status == 'OK') {
      // Remove the existing border colours
      var removeClasses = "text-neutral text-negative text-contentious text-positive";
      target.siblings().removeClass(removeClasses);
      target.addClass("text-" + c + " selected");
      target.parents("td").next().find("span.DSAuthor").each(function (index, element) {
          if($(element).text() === data.username)
          {
              var authorComment = $(element).parent().next().children("p.triangle-border");
              console.log(authorComment);
              authorComment.removeClass(removeClasses + " border-neutral border-negative border-contentious border-positive");
              authorComment.addClass("border-" + c +" text-" + c);
          }
      });
    }
  });
}

function voteColour(currentVote) {
    var retval;
    if(currentVote != 'undefined' || currentVote != null)
    {
        var css = currentVote.removeClass('selected').attr('class');
        switch (css)
        {
            case "text-negative":
                retval = " text-negative border-negative";
                break;
            case "text-positive":
                retval = " text-positive border-positive";
                break
            default:
                retval = " text-contentious border-contentious";
                break;
        }
    }
    currentVote.addClass('selected'); //add back incase further comments added
    return retval;
}

function addNote(id, username, institution, displayname) {
  console.log('Adding note: ',id,username,institution, displayname);
  var v   = $('#'+id+'_newnote').val();
  var i   = (institution == null || institution.length == 0) ? 'N/A' : institution;
  var currentVote = $('#currentVote'+id+' a.selected'); //current users vote, if it exists that is!
  var cssf = voteColour(currentVote);

  $.ajax({
    // libs and culture: 0894-8631
    url: gokb.config.baseUrl+'/ajaxSupport/criterionComment?comp='+id+'&comment='+v,
    dataType:"json"
  }).done(function(data) {
        console.log('Note: '+data);
      $('#'+id+'_notestable').append("<dt> <span class='DSAuthor' data-name="+displayname+"> "+username+"</span> </dt><dd> <p class='DSInlineBlock'>"+i+"</p> <p class='triangle-border DSInlineBlock"+cssf+"'> <span id='org.gokb.cred.DSNote:"+ data.newNote +":note' class='newNote xEditableValue  editable editable-pre-wrapped editable-click' data-pk='org.gokb.cred.DSNote:"+data.newNote+"' data-name='note' data-tpl='<textarea wrap=\"off\"></textarea>' data-type='textarea' data-url='/gokbLabs/ajaxSupport/editableSetValue'>"+ v +"</span> <a data-comp= "+ id +" data-note= "+ data.newNote + " class='noteDelete text-negative fa fa-times-circle fa-2x'></a></p></dd>");
      $('.newNote').editable(); //Xeditable on newly created notes
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
      var dl = target.parent().parent();
      dl.prev().remove();
      dl.remove();
    }
  });

  return false;
}