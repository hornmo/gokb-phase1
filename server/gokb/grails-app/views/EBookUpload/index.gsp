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
      <p> This is a new page to try to let you upload an eBook file. Please supply all fields. Datafiles are associated with
        Ingestion Profiles; you can select an existing profile to use, or create a new one.  </p>
      <p>
        Please note - The system only maintains a unique copy of each file. Repeat uploads will not be processed as new files.
      </p>
      <p>
        Please note - The default platform url will only be used if a url cannot be deduced from the uploaded file.
      </p>
      <g:form action="processSubmission" method="post" enctype="multipart/form-data" params="${params}">
        <div class="form-group">
          <label class="control-label">Ingestion Profiles</label>
          <g:simpleReferenceTypedown class="form-control" name="ingestionProfileId" baseClass="org.gokb.cred.IngestionProfile"/>
          <button id="NewIngestProfileButton" class="hidden-license-details btn btn-default btn-sm btn-primary" type="button"
            data-toggle="collapse" data-target="#collapseableAddIP">Add new <i class="glyphicon glyphicon-plus"></i>
          </button>
        </div>

        <div id="collapseableAddIP" class="dl-horizontal collapse">
          <div class="form-group">
            <label class="control-label" for='profileName'>Profile Name:</label>
            <input type='text' class='form-control' name='profileName' id='profileName' />
          </div>
          <div class="form-group">
            <label class="control-label" for='packageName'>Package Name:</label>
            <input type='text' class='form-control' name='packageName' id='packageName' />
          </div>
          <div class="form-group">
            <label class="control-label" for='packageType'>Data Format:</label>
            <g:simpleReferenceTypedown class="form-control" name="packageType" baseClass="org.gokb.cred.RefdataValue" filter1="ebook.filetype"/>
          </div>
      
          <div class="form-group">
            <label class="control-label" for='platformUrl'>Url:</label>
            <input type='text' class='form-control' name='platformUrl' id='platformUrl' value='http://' />
          </div>

          <div class="form-group">
            <label class="control-label">Source</label>
            <g:simpleReferenceTypedown class="form-control" name="sourceId" baseClass="org.gokb.cred.Source"/>
            <button class="hidden-license-details btn btn-default btn-sm btn-primary" type="button"
              data-toggle="collapse" data-target="#collapseableAddSource">Add new <i class="glyphicon glyphicon-plus"></i>
            </button>
          </div>

          <div id="collapseableAddSource" class="dl-horizontal collapse">
            <div class="form-group">
              <label class="control-label" for='sourceName'>Source Name:</label>
              <input type='text' class='form-control' name='sourceName' id='sourceName' />
            </div>

            <div class="form-group">
              <label class="control-label" for='defaultSupplyMethod'>Default Supply Method</label>
              <g:simpleReferenceTypedown class="form-control" name="defaultSupplyMethod" baseClass="org.gokb.cred.RefdataValue" filter1="Source.DataSupplyMethod" />
            </div>

            <div class="form-group">
              <label class="control-label" for="responsibleParty">Responsible Party</label>
              <g:simpleReferenceTypedown class="form-control" name="orgId" baseClass="org.gokb.cred.Org"/>
            </div>
          </div>
        </div>

        <div class="form-group">
          <label class="control-label" for="submissionFile">Local file to upload</label>
          <div class="input-group" >
            <span class="input-group-btn">
              <span class="btn btn-default btn-file">
               Browse <input type="file" id="submissionFile" name="submissionFile" onchange='$("#upload-file-info").html($(this).val());' />
              </span>
            </span>
            <span class='form-control' id="upload-file-info"><label for="submissionFile" >Select a file...</label></span>
            <span class="input-group-btn">
              <button type="submit" class="btn btn-primary">Upload via local File</button>
            </span>
          </div>
        </div>

        <div class="form-group">
          <label class="control-label" for="submissionURL">Remote URL</label>

          <div class="input-group" >
            <input type='text' class='form-control' name='submissionURL' id='submissionURL' placeholder='Enter the URL Of resource to ingest' />
            <span class="input-group-btn">
              <button type="submit" class="btn btn-primary">Upload via Remote URL</button>
            </span>
          </div>
        </div>

       <div class="form-group">
          <label class="control-label" for="ingestMode">Ingest Mode</label>

          <div class="input-group" >
            <select name="ingestMode" class="form-control">
              <option value="background" selected="true">Background - after submission go directly to the upload status page</option>
              <option value="foreground">Foreground - This page will not return until the ingest has completed</option>
            </select>
          </div>
        </div>


      </g:form>
    </div>
  </div>
  </body>
</html>
