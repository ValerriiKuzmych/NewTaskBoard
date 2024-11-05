// Function to show TaskBoard details and hide the TaskBoard container
function openTaskBoard(taskBoardId) {
	document.getElementById('taskboards').style.display = 'none';
	document.getElementById('taskboard-detail').style.display = 'flex';
}

// Function to close TaskBoard details and show the TaskBoard container
function closeTaskBoard() {
	document.getElementById('taskboards').style.display = 'flex';
	document.getElementById('taskboard-detail').style.display = 'none';
}

// Function to show Task details and hide TaskBoard details
function openTaskDetail(taskId) {
	document.getElementById('taskboard-detail').style.display = 'none';
	document.getElementById('task-detail').style.display = 'flex';
}

// Function to close Task details and show TaskBoard details
function closeTaskDetail() {
	document.getElementById('taskboard-detail').style.display = 'flex';
	document.getElementById('task-detail').style.display = 'none';
}

// Placeholder function to open a modal for creating a new TaskBoard
function openCreateTaskBoardModal() {
	alert('Open modal to create new TaskBoard'); // Logic for modal opening can go here
}

// Placeholder function to open a modal for creating a new Task
function openCreateTaskModal() {
	alert('Open modal to create new Task'); // Logic for modal opening can go here
}
