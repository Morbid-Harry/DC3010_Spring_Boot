/**
 * Handles the filtering of project roles on the dashbaord screen
 */

//Get the filters
const gradeFilterSelect = document.getElementById('grade-filter');
const wayOfWorkingFilterSelect = document.getElementById('way-of-working-filter');
const toolFilterSelect = document.getElementById('tool-filter');

//Get all of the cards
const projectCards = document.querySelectorAll('.project-card');


//Add the applyFilters function as an on change event to both filters
gradeFilterSelect.addEventListener('change', applyFilters);
wayOfWorkingFilterSelect.addEventListener('change', applyFilters);
toolFilterSelect.addEventListener('change', applyFilters);

function applyFilters() {
	//Get the values of the filters 	
	const selectedGrade = gradeFilterSelect.value;
	const selectedWayOfWorking = wayOfWorkingFilterSelect.value;
	const selectedTool = toolFilterSelect.value;

	console.log(selectedTool);

	//For each card check if they match the grade & way of working selected	
	projectCards.forEach(function(card) {


		const gradeRequiredElement = card.querySelector('p[data-label="Grade Required"]').textContent;
		const modifiedGradeString = gradeRequiredElement.split(': ')[1].trim();

		const wayOfWorkingElement = card.querySelector('p[data-label="Way of Working"]').textContent;
		const modifiedWorkingString = wayOfWorkingElement.split(': ')[1].trim();

		//Get each tools label text value within this card
		const groupOfTools = Array.from(card.querySelectorAll('.my-button-group .btn-primary'))
			.map(function(label) {
				return label.textContent.trim();
			});

		const gradeMatch = selectedGrade === 'All' || selectedGrade === modifiedGradeString;
		const wayOfWorkingMatch = selectedWayOfWorking === 'All' || selectedWayOfWorking === modifiedWorkingString;
		const toolMatch = selectedTool === 'All' || groupOfTools.includes(selectedTool);

		//If all are true then display that card else hide it
		if (gradeMatch && wayOfWorkingMatch && toolMatch) {
			card.style.display = 'block';
		} else {
			card.style.display = 'none';
		}

	});
}
