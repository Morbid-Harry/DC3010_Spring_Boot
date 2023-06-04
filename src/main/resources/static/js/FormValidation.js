//Disables form submission if there are invalid fields. Does form validation on both field change and when the form is submited
(function () {
  'use strict';

  // Fetch all the forms that require validation
  var forms = document.querySelectorAll('.needs-validation');

  // Loop over the forms and attach the event listener to fields
  Array.prototype.slice.call(forms)
    .forEach(function (form) {
      var fields = form.querySelectorAll('.form-control');

      // Loop over the fields and attach the event listener
      Array.prototype.slice.call(fields)
        .forEach(function (field) {
          field.addEventListener('change', function () {
            if (!field.checkValidity()) {
              field.classList.add('is-invalid');
            } else {
              field.classList.remove('is-invalid');
            }

			field.classList.add('is-valid')
          });
        });

	  // Attach the event listener to form submit event
      form.addEventListener('submit', function (event) {
        if (!form.checkValidity()) {
          event.preventDefault();
          event.stopPropagation();
        }

        form.classList.add('was-validated');
      });
	
	
    });
})();