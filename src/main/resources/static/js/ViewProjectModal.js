/**Handles to loading of the modal and the project specific data via Asyncronous AJAX call */
$(document)
	.ready(
		function() {
			$('.eBtn')
				.click(
					function(e) {
						/*Prevent the default action of following the link*/
						e.preventDefault();

						/*Get the URL from the href attribute of the <a> tag*/
						var url = $(this).attr('href');

						/*Asynchronous call to dashboard controller*/
						$.ajax({
								type: 'GET',
								url: url,
								success: function(response) {

									/*Get the project data that is stored in the response*/
									var project = response.project;

									/*Get the tool data that is stored in the response*/
									var tools = response.tools;

									/*To check data contents in webconsole*/
									console.log(project);
									console.log(tools);
							

									// Select the existing show interest button in the modal footer
									var showinterestbutton = document.getElementById("show-interest");
									
									// Select the add-favourite button in the modal footer
									var favouritebutton = document.getElementById("add-favourites");
									
									// Select the remove-favourite button in the modal footer
									var removefavouritebutton = document.getElementById("remove-favourites");
									
									//Get the resource managers id that raised the project
									var userId = project.createdBy.userID;
									
									//Get the projects id 
									var projectId = project.projectID
									
									
									function favouriteProjectHandler()
									{
										fetch("/favourite/" + projectId, {method: 'POST'})
											.then(function(response) {
												// Check if the response was successful
												if (response.ok) {
														var modalBody = document.querySelector('.modal-body');
												
														// Create the alert message
														var alert = document.createElement('div');
														alert.className = 'alert alert-success alert-dismissible fade show';
														alert.role = 'alert';
														alert.innerHTML = "Add to your Favourites!" + "<button type='button' class='btn-close' data-bs-dismiss='alert' aria-label='Close'></button>";
														
														// Insert the new alert at the top of the modal body
														modalBody.insertBefore(alert, modalBody.firstChild);
														
												} else {
														var modalBody = document.querySelector('.modal-body');
												
														// Create the alert message
														var alert = document.createElement('div');
														alert.className = 'alert alert-danger alert-dismissible fade show';
														alert.role = 'alert';
														alert.innerHTML = "This project is already in your favorites!" + "<button type='button' class='btn-close' data-bs-dismiss='alert' aria-label='Close'></button>";
														
														// Insert the new alert at the top of the modal body
														modalBody.insertBefore(alert, modalBody.firstChild);
												}
											});
									}
									
									function removeFavouriteProjectHandler()
									{
										fetch("/favourite/remove/" + projectId, {method: 'POST'})
											.then(function(response) {
												// Check if the response was successful
												if (response.ok) {
														var modalBody = document.querySelector('.modal-body');
												
														// Create the alert message
														var alert = document.createElement('div');
														alert.className = 'alert alert-success alert-dismissible fade show';
														alert.role = 'alert';
														alert.innerHTML = "Removed from your Favourites" + "<button type='button' class='btn-close' data-bs-dismiss='alert' aria-label='Close'></button>";
														
														// Insert the new alert at the top of the modal body
														modalBody.insertBefore(alert, modalBody.firstChild);
														
												} else {
														var modalBody = document.querySelector('.modal-body');
												
														// Create the alert message
														var alert = document.createElement('div');
														alert.className = 'alert alert-danger alert-dismissible fade show';
														alert.role = 'alert';
														alert.innerHTML = "Failed to remove from your Favourites" + "<button type='button' class='btn-close' data-bs-dismiss='alert' aria-label='Close'></button>";
														
														// Insert the new alert at the top of the modal body
														modalBody.insertBefore(alert, modalBody.firstChild);
												}
											});
									}
									
									
									
									// Add an event listener to handle the click event
									function showInterestHandler() {
										// Make the GET request
										fetch("/show-interest/" + userId + "/" + projectId)
											.then(function(response) {
												// Check if the response was successful
												if (response.ok) {
														var modalBody = document.querySelector('.modal-body');
												
														// Create the alert message
														var alert = document.createElement('div');
														alert.className = 'alert alert-success alert-dismissible fade show';
														alert.role = 'alert';
														alert.innerHTML = "Email sent to resource manager!" + "<button type='button' class='btn-close' data-bs-dismiss='alert' aria-label='Close'></button>";
														
														// Insert the new alert at the top of the modal body
														modalBody.insertBefore(alert, modalBody.firstChild);
														
												} else {
														var modalBody = document.querySelector('.modal-body');
												
														// Create the alert message
														var alert = document.createElement('div');
														alert.className = 'alert alert-danger alert-dismissible fade show';
														alert.role = 'alert';
														alert.innerHTML = "Email failed to send email! Contact the resource manager directly via: " + project.createdBy.email  + "<button type='button' class='btn-close' data-bs-dismiss='alert' aria-label='Close'></button>";
														
														// Insert the new alert at the top of the modal body
														modalBody.insertBefore(alert, modalBody.firstChild);
												}
											});
									}

									showinterestbutton.addEventListener("click", showInterestHandler);
									
									if(favouritebutton != null)
									{
										favouritebutton.addEventListener("click", favouriteProjectHandler);
									}
									
									if(removefavouritebutton != null)
									{
										removefavouritebutton.addEventListener("click", removeFavouriteProjectHandler);
									}		
									
									$('.modal-title').text(project.companyName);

									var modalBody = document.querySelector('.modal-body');
									
									/**Create all the labels and data information for the specific project on the modal */
									modalBody.innerHTML = `
											  <div class="row">
											    <div class="col-6">
											      <p><strong>Project Name: </strong>${project.projectName}</p>
											    </div>
											    <div class="col-6">
											      <p><strong>Grade Required: </strong> ${project.grade}</p>
											    </div>
											  </div>
											  <div class="row">
												<hr>
											  </div>	
											  <div class="row">
											    <div class="col-6">
											      <p><strong>Start Date: </strong>${project.startDate}</p>
											    </div>
											    <div class="col-6">
											      <p><strong>End Date: </strong>${project.endDate}</p>
											    </div>
											  </div>
											  <div class="row">
												<hr>
											  </div>
											  <div class="row">
											    <div class="col-6">
											      <p><strong>Way of Working: </strong>${project.workLocation}</p>
											    </div>
											    <div class="col-6">
											      <p><strong>Address: </strong>${project.address ? project.address : 'From Home'}</p>
											    </div>
											  </div>
											  <div class="row">
												<hr>
											  </div>
											  <div class="row mb-2">
											    <div class="form-group">
											      <label for="description" class="col-form-label"><strong>Description</strong></label>
											      <textarea class="form-control" id="description" rows="10" style="resize: none; background-color: #fff;" disabled>${project.generalDescription}</textarea>
											    </div>
											  </div>
											  <div class="row text-center">
													<p>
														<strong>Tools Used</strong>
													</p>
											  </div>	
											
											
											  <div class="row text-center"> 
												<div class="modal-button-group flex-wrap"> 
												</div>
											 </div>
											
											  <input type="hidden" id="projectId" value="${project.projectID}">
											`;

									
									var toolsContainer = document.querySelector('.modal-button-group');

									/** Append the tools to the end of the html  */
									tools.forEach(function(tool) {
										var checkbox = document.createElement('input');
										checkbox.type = 'checkbox';
										checkbox.className = 'btn-check tags';
										checkbox.id = tool;
										checkbox.autocomplete = 'off';
										checkbox.name = 'tools-used';
										checkbox.checked = true;
										checkbox.disabled = true;

										var label = document.createElement('label');
										label.className = 'btn btn-primary';
										label.htmlFor = tool;
										label.textContent = tool;

										var span = document.createElement('span');
										span.style.marginRight = "5px";
										span.appendChild(checkbox);
										span.appendChild(label);

										toolsContainer.appendChild(span);
									});
									
									/**Finally show the modal after all data has been added */
									$('#DetailsModal').modal('show');

									//Listen for the modal hide so can remove the show interest event listerner else it will duplicate everytime I open view details
									$('.modal').on("hide.bs.modal", function() {
										// Remove the event listener when the modal is hidden
										showinterestbutton.removeEventListener("click", showInterestHandler);
										
										if(favouritebutton != null)
										{
											favouritebutton.removeEventListener("click", favouriteProjectHandler);
										}
										
										if(removefavouritebutton != null)
										{
											removefavouritebutton.removeEventListener("click", removeFavouriteProjectHandler);
										}	
									});

								},
								error: function(error) {
									//Just print errors to the console
									console.error(error);
								}
							});
					});
		});