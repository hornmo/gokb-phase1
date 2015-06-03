<!DOCTYPE html>
<html>
<head>
<meta name="layout" content="sb-admin" />
<title>GOKb: eBook File Upload</title>
</head>
<body>

	<h1 class="page-header">File upload</h1>
	<div id="mainarea" class="panel panel-default">
		<div class="panel-body">
			<p>
			This is a new page to try to let you upload an eBook file. Please supply all fields. 
      		</p>
      <p>
        Please note - The system only maintains a unique copy of each file. Repeat uploads will not be processed as new files.
      </p>
      <p>
        Please note - The default platform url will only be used if a url cannot be deduced from the uploaded file.
      </p>
      <g:form action="processSubmission" method="post" enctype="multipart/form-data" params="${params}">
         <div class="form-group">
		 	<label class="control-label" for='packageName'>Package Name:</label>
		  	<input type='text'
				   class='form-control' name='packageName' id='packageName' />
		 </div>
		 <div class="form-group">
		 	<label class="control-label" for='packageType'>Package Type:</label>
			<g:simpleReferenceTypedown class="form-control" name="packageType" baseClass="org.gokb.cred.RefdataValue" filter1="ebook.filetype"/> 
		 </div>
		 <div class="form-group">
		 	<label class="control-label" for='platformUrl'>Default Platform Url:</label>
		  	<input type='text'
				   class='form-control' name='platformUrl' id='platformUrl' />
		 </div>
         <div class="input-group" >
         	 <span class="input-group-btn">
		         <span class="btn btn-default btn-file">
						    Browse <input type="file" id="submissionFile" name="submissionFile" onchange='$("#upload-file-info").html($(this).val());' />
				 </span>
			 </span>
        	 <span class='form-control' id="upload-file-info"><label for="submissionFile" >Select a file...</label></span>
        	 <span class="input-group-btn">
				<button type="submit" class="btn btn-primary">Upload</button>
			 </span>
         </div>
      </g:form>
		</div>
	</div>
</body>
</html>
