package com.k_int.gokb.refine.commands;
import com.google.refine.commands.Command;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import java.io.IOException;
import com.k_int.gokb.refine.notifications.NotificationStack;
import com.k_int.gokb.refine.notifications.Notification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.k_int.gokb.module.GOKbModuleImpl;
import com.google.refine.ProjectManager;
import org.json.JSONObject;
import com.google.refine.model.Project;

public class ClearWarning extends Command{
    final static Logger logger = LoggerFactory.getLogger("GOKb-clear-warning_command");

	  @Override
  	public void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
	    String text = request.getParameter("text");
	    String title = request.getParameter("title");
      final Project project = getProject(request);
      Long projectId = project.id;
    	String json_mock = String.format("{'text':'%s','title':'%s'}",text,title);
    	hide_notification(json_mock,projectId);
  	}
    /**
    * Hide the notification that has been closed. Use projectid + userid to retrieve the correct stack.
    * This should be enough for scenarios where we have many users / projects on same instance.
    **/
  	private void hide_notification(String json_mock,Long projectId){
      String currentUser = "0";
      try{
        currentUser =(String) GOKbModuleImpl.singleton.getCurrentUser().get("id");
      }catch(Exception e){
        logger.error("Failed to retrieve current user from GOKbModuleImpl.",e);        
      }
      String stackKey = projectId + currentUser + "-hidden";
      NotificationStack stack = NotificationStack.get(stackKey);
      Notification n= Notification.fromJSON(json_mock, Notification.class);
      stack.add(n);

  	}
}