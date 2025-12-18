package com.hfuninternal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hfuninternal.model.Report;
import com.hfuninternal.model.User;

public interface ReportRepository extends JpaRepository<Report, Long> {

    // (Optional but recommended)
    // prevent duplicate reports by same user on same user
    boolean existsByReportedByAndReportedUser(User reportedBy, User reportedUser);
}
