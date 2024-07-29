package com.project.demo.logic.entity.task;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.demo.logic.entity.food.FoodEnum;
import com.project.demo.logic.entity.prize.Prize;
import com.project.demo.logic.entity.rol.Role;
import com.project.demo.logic.entity.unlock.Unlock;
import com.project.demo.logic.entity.user.User;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Table(name = "task")
@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    private String recurrent;

    private Long repeatTimes;

    private Long parentId;
    @Transient
    private Long userId;

    private String priority;

    private String description;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX")
    private Date startDate;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX")
    private Date endDate;

    private boolean visible;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "prize_id", referencedColumnName = "id")
    private Prize prize;

    private Boolean isCompleted;

    private Boolean verified;

    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;


    public Long getId() {
        return id;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Prize getPrize() {
        return prize;
    }

    public void setPrize(Prize prize) {
        this.prize = prize;
    }

    public Boolean getCompleted() {
        return isCompleted;
    }

    public void setCompleted(Boolean completed) {
        isCompleted = completed;
    }
    
    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Boolean getVerified() {
        return verified;
    }

    public void setVerified(Boolean verified) {
            this.verified = verified;
        }

    public String getRecurrent() {
        return recurrent;
    }

    public void setRecurrent(String recurrent) {
        this.recurrent = recurrent;
    }

    public Long getRepeatTimes() {
        return repeatTimes;
    }

    public void setRepeatTimes(Long repeatTimes) {
        this.repeatTimes = repeatTimes;
    }

    public Task() {
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Task taskWithNewDates(Date newStartDate, Date newEndDate) {
        Task newTask = new Task();
        newTask.setName(this.name);
        newTask.setUser(this.user);
        newTask.setPriority(this.priority);
        newTask.setDescription(this.description);
        newTask.setStartDate(newStartDate);
        newTask.setEndDate(newEndDate);
        newTask.setPrize(this.prize);
        newTask.setCompleted(this.isCompleted);
        newTask.setVerified(this.verified);
        newTask.setRecurrent(this.recurrent);
        newTask.setRepeatTimes(this.repeatTimes);
        newTask.setVisible(true);
        newTask.setParentId(this.id);  // Set the parentId to the id of the original task
        return newTask;
    }

    public List<Task> generateRecurringTasks() {
        List<Task> recurringTasks = new ArrayList<>();
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(this.getStartDate());
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(this.getEndDate());

        String recurrence = this.getRecurrent();
        int repeatCount = 0;

        while (repeatCount < this.getRepeatTimes()) {
            switch (recurrence.toLowerCase()) {
                case "daily":
                    startCalendar.add(Calendar.DATE, 1);
                    endCalendar.add(Calendar.DATE, 1);
                    break;
                case "weekly":
                    startCalendar.add(Calendar.WEEK_OF_YEAR, 1);
                    endCalendar.add(Calendar.WEEK_OF_YEAR, 1);
                    break;
                case "monthly":
                    startCalendar.add(Calendar.MONTH, 1);
                    endCalendar.add(Calendar.MONTH, 1);
                    break;
                case "yearly":
                    startCalendar.add(Calendar.YEAR, 1);
                    endCalendar.add(Calendar.YEAR, 1);
                    break;
                default:
                    break;
            }

            Date newStartDate = startCalendar.getTime();
            Date newEndDate = endCalendar.getTime();

            Task newTask = this.taskWithNewDates(newStartDate, newEndDate);
            recurringTasks.add(newTask);

            repeatCount++;
        }

        return recurringTasks;
    }
}


