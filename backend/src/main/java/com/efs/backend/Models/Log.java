package com.efs.backend.Models;

import com.efs.backend.Enums.LogType;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "log")
public class Log {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long logId;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "userId")
    private User user;

    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "orderId")
    private Order order;

    @Column(name = "log_date")
    private LocalDateTime logDate = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    @Column(name = "log_type")
    private LogType logType;

    @Column(name = "transaction_result")
    private String transactionResult;

    public Log() {
    }

    public Log(User user, Order order, LocalDateTime logDate, LogType logType, String transactionResult) {
        this.user = user;
        this.order = order;
        this.logDate = logDate;
        this.logType = logType;
        this.transactionResult = transactionResult;
    }

    public Log(Long logId, User user, Order order, LocalDateTime logDate, LogType logType, String transactionResult) {
        this.logId = logId;
        this.user = user;
        this.order = order;
        this.logDate = logDate;
        this.logType = logType;
        this.transactionResult = transactionResult;
    }

    public Long getLogId() {
        return logId;
    }

    public void setLogId(Long logId) {
        this.logId = logId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public LocalDateTime getLogDate() {
        return logDate;
    }

    public void setLogDate(LocalDateTime logDate) {
        this.logDate = logDate;
    }

    public LogType getLogType() {
        return logType;
    }

    public void setLogType(LogType logType) {
        this.logType = logType;
    }

    public String getTransactionResult() {
        return transactionResult;
    }

    public void setTransactionResult(String transactionResult) {
        this.transactionResult = transactionResult;
    }
}
