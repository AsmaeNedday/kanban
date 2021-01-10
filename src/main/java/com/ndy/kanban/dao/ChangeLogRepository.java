package com.ndy.kanban.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ndy.kanban.domain.ChangeLog;


public interface ChangeLogRepository extends JpaRepository<ChangeLog,Long> {

}
