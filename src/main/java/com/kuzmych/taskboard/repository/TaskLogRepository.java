package com.kuzmych.taskboard.repository;

import org.springframework.stereotype.Repository;

import com.kuzmych.taskboard.entity.TaskLog;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface TaskLogRepository extends JpaRepository<TaskLog, Long> {

	List<TaskLog> findByTaskId(Long taskId);

}
