package com.project.demo.logic.entity.task;

import com.project.demo.logic.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query("SELECT new com.project.demo.logic.entity.task.TaskDTO(t.id, t.name, t.priority, t.description, t.startDate, t.endDate, t.visible, t.recurrent, t.repeatTimes) FROM Task t WHERE t.user.id = :userId and t.visible = true")
    List<TaskDTO> findByUser(@Param("userId") Long userId);

    void deleteByParentId(Long parentId);

}
