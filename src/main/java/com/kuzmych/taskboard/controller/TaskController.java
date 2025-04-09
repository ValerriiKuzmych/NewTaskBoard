package com.kuzmych.taskboard.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.kuzmych.taskboard.entity.Task;
import com.kuzmych.taskboard.entity.TaskBoard;
import com.kuzmych.taskboard.entity.TaskLog;
import com.kuzmych.taskboard.entity.User;
import com.kuzmych.taskboard.service.ITaskBoardLogService;
import com.kuzmych.taskboard.service.ITaskLogService;
import com.kuzmych.taskboard.service.ITaskService;
import com.kuzmych.taskboard.service.IUserTaskBoardAccessService;

import java.io.InputStream;
import java.io.FileInputStream;
import java.io.OutputStream;

@Controller
@RequestMapping("/tasks")
public class TaskController {

	@Autowired
	private ITaskService taskService;

	@Autowired
	private ITaskLogService taskLogService;

	@Autowired
	private ITaskBoardLogService taskBoardLogService;

	@Autowired
	IUserTaskBoardAccessService userTaskBoardAccessService;

	@GetMapping("/show/{id}")
	public String showTask(@PathVariable Long id, Model model, HttpSession session) {

		User loggedInUser = (User) session.getAttribute("loggedInUser");
		if (loggedInUser == null) {
			return "redirect:/users/login";
		}

		Task task = taskService.findById(id);

		if (task == null
				|| !task.getTaskBoard().getGeneralPage().getUser().getLogin().equals(loggedInUser.getLogin())) {
			return "error/403";
		}
		String timeLeft = taskService.getTimeLeft(task);
		model.addAttribute("timeLeft", timeLeft);
		model.addAttribute("task", task);
		return "task/show";
	}

	@GetMapping("/show-access/{id}")
	public String showTaskForUsersWithAccess(@PathVariable Long id, Model model, HttpSession session) {

		User loggedInUser = (User) session.getAttribute("loggedInUser");
		if (loggedInUser == null) {
			return "redirect:/users/login";
		}

		Task task = taskService.findById(id);

		if (task == null || !userTaskBoardAccessService.chekAccessToReadingTask(loggedInUser.getId(),
				task.getTaskBoard().getId())) {
			return "error/403";
		}
		String timeLeft = taskService.getTimeLeft(task);
		model.addAttribute("timeLeft", timeLeft);
		model.addAttribute("task", task);
		return "task/show_for_users_with_access";
	}

	@GetMapping("/{id}/delete")
	public String delete(@PathVariable Long id, HttpSession session) {

		User loggedInUser = (User) session.getAttribute("loggedInUser");
		if (loggedInUser == null) {
			return "redirect:/users/login";
		}

		Task task = taskService.findById(id);

		if (task == null
				|| !task.getTaskBoard().getGeneralPage().getUser().getLogin().equals(loggedInUser.getLogin())) {
			return "error/403";
		}

		taskBoardLogService.saveTaskLogInTaskBoard(id, loggedInUser.getName());

		taskService.delete(id);

		return "redirect:/taskboards/show/" + task.getTaskBoard().getId();
	}

	@GetMapping("/{id}/delete-access")
	public String deleteForUsersWithAccess(@PathVariable Long id, HttpSession session) {

		User loggedInUser = (User) session.getAttribute("loggedInUser");
		if (loggedInUser == null) {
			return "redirect:/users/login";
		}

		Task task = taskService.findById(id);

		if (task == null || !userTaskBoardAccessService.chekAccessToDeletingTask(loggedInUser.getId(),
				task.getTaskBoard().getId())) {
			return "UserHasNotAccess";
		}
		taskService.delete(id);

		return "redirect:/taskboards/show-access/" + task.getTaskBoard().getId();
	}

	@GetMapping("/edit/{id}")
	public String editTaskForm(@PathVariable Long id, Model model, HttpSession session) {
		User loggedInUser = (User) session.getAttribute("loggedInUser");
		if (loggedInUser == null) {
			return "redirect:/users/login";
		}

		Task task = taskService.findById(id);

		if (task == null
				|| !task.getTaskBoard().getGeneralPage().getUser().getLogin().equals(loggedInUser.getLogin())) {
			return "error/403";
		}

		TaskBoard taskBoard = task.getTaskBoard();

		if (taskBoard == null) {
			return "error/404";
		}
		String timeLeft = taskService.getTimeLeft(task);
		model.addAttribute("task", task);
		model.addAttribute("timeLeft", timeLeft);
		model.addAttribute("taskBoard", taskBoard);

		return "task/edit";
	}

	@GetMapping("/edit-access/{id}")
	public String editTaskFormForUsersWithAccess(@PathVariable Long id, Model model, HttpSession session) {

		User loggedInUser = (User) session.getAttribute("loggedInUser");
		if (loggedInUser == null) {
			return "redirect:/users/login";
		}

		Task task = taskService.findById(id);

		if (task == null || !userTaskBoardAccessService.chekAccessToEditingTask(loggedInUser.getId(),
				task.getTaskBoard().getId())) {
			return "UserHasNotAccessToEditing";
		}

		TaskBoard taskBoard = task.getTaskBoard();

		if (taskBoard == null) {
			return "error/404";
		}

		model.addAttribute("task", task);
		model.addAttribute("taskBoard", taskBoard);

		return "task/edit-for-users-with-access";
	}

	@PostMapping("/update")
	public String updateTask(@RequestParam("id") Long id, @ModelAttribute Task task,
			@RequestParam(value = "file", required = false) MultipartFile file, Model model, HttpSession session) {

		User loggedInUser = (User) session.getAttribute("loggedInUser");

		task.setId(id);
		task.setExecutorName(loggedInUser.getName());

		Task existingTask = taskService.findById(id);
		if (file != null && !file.isEmpty()) {
			try {

				String originalFileName = file.getOriginalFilename();
				String sanitizedFileName = originalFileName != null
						? originalFileName.replaceAll("[^a-zA-Z0-9\\.\\-]", "_")
						: "default_file";
				String fileName = System.currentTimeMillis() + "_" + sanitizedFileName;

				Path uploadDir = Paths.get("C:\\TaskBoard\\uploads_files_for_tasks\\");
				if (!Files.exists(uploadDir)) {
					Files.createDirectories(uploadDir);
				}
				Path filePath = uploadDir.resolve(fileName);

				Files.write(filePath, file.getBytes());

				task.setFilePath(fileName);

			} catch (IOException e) {
				model.addAttribute("error", "File upload failed: " + e.getMessage());
				return "task/edit";
			}
		}

		taskService.update(task);
		return "redirect:/taskboards/show/" + existingTask.getTaskBoard().getId();
	}

	@PostMapping("/update-access")
	public String updateTaskForUsersWithAccess(@RequestParam("id") Long id, @ModelAttribute Task task,
			@RequestParam(value = "file", required = false) MultipartFile file, Model model) {

		task.setId(id);
		Task existingTask = taskService.findById(id);

		if (file != null && !file.isEmpty()) {
			try {
				String originalFileName = file.getOriginalFilename();
				String sanitizedFileName = (originalFileName != null)
						? originalFileName.replaceAll("[^a-zA-Z0-9\\.\\-]", "_")
						: "default_file";
				String fileName = System.currentTimeMillis() + "_" + sanitizedFileName;

				Path uploadDir = Paths.get("C:\\TaskBoard\\uploads_files_for_tasks\\");
				if (!Files.exists(uploadDir)) {
					Files.createDirectories(uploadDir);
				}
				Path filePath = uploadDir.resolve(fileName);
				Files.write(filePath, file.getBytes());

				task.setFilePath(fileName);

			} catch (IOException e) {
				model.addAttribute("error", "File upload failed: " + e.getMessage());
				return "task/edit-for-users-with-access";
			}
		}

		taskService.update(task);
		return "redirect:/taskboards/show-access/" + existingTask.getTaskBoard().getId();
	}

	@GetMapping("/download/{id}")
	public void downloadFile(@PathVariable("id") Long taskId, HttpServletResponse response) {
		String uploadDir = "C:\\TaskBoard\\uploads_files_for_tasks\\";
		Task task = taskService.findById(taskId);
		if (task.getFilePath() == null) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return;
		}

		File file = new File(uploadDir + task.getFilePath());
		if (!file.exists()) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return;
		}

		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"");

		try (InputStream inputStream = new FileInputStream(file);
				OutputStream outputStream = response.getOutputStream()) {
			byte[] buffer = new byte[1024];
			int bytesRead;

			while ((bytesRead = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, bytesRead);
			}
		} catch (IOException e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			e.printStackTrace();
		}
	}

	@GetMapping("/files/{fileName:.+}")
	public void serveFile(@PathVariable String fileName, HttpServletResponse response) {
		String uploadDir = "C:\\TaskBoard\\uploads_files_for_tasks\\";
		File file = new File(uploadDir + fileName);
		System.out.println("Requested file: " + file.getAbsolutePath());

		if (file.exists()) {
			try {
				String mimeType = Files.probeContentType(file.toPath());
				if (mimeType == null) {
					mimeType = "application/octet-stream";
				}

				response.setContentType(mimeType);
				response.setHeader("Content-Disposition", "inline; filename=\"" + file.getName() + "\"");

				try (InputStream inputStream = new FileInputStream(file);
						OutputStream outputStream = response.getOutputStream()) {
					byte[] buffer = new byte[1024];
					int bytesRead;
					while ((bytesRead = inputStream.read(buffer)) != -1) {
						outputStream.write(buffer, 0, bytesRead);
					}
				}
			} catch (IOException e) {
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				e.printStackTrace();
			}
		} else {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			System.out.println("File not found: " + file.getAbsolutePath());
		}
	}

	@GetMapping("/{id}/logs")
	public String getTaskLogs(@PathVariable Long id, Model model, HttpSession session) {

		User loggedInUser = (User) session.getAttribute("loggedInUser");
		if (loggedInUser == null) {
			return "redirect:/users/login";
		}

		Task task = taskService.findById(id);

		if (task == null
				|| !task.getTaskBoard().getGeneralPage().getUser().getLogin().equals(loggedInUser.getLogin())) {
			return "error/403";
		}

		List<TaskLog> logs = taskLogService.getAllLogs(id);

		model.addAttribute("task", task);
		model.addAttribute("logs", logs);
		return "task/logs";
	}
}