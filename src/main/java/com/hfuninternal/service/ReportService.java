package com.hfuninternal.service;

import java.security.Principal;

import org.springframework.stereotype.Service;

import com.hfuninternal.model.Report;
import com.hfuninternal.model.User;
import com.hfuninternal.repository.ReportRepository;
import com.hfuninternal.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final ReportRepository reportRepository;
    private final UserRepository userRepository;

    public void reportUser(Long userId, String reason, Principal principal) {

        if (principal == null || principal.getName() == null) {
            throw new RuntimeException("User not authenticated");
        }

        User reportedBy = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new RuntimeException("Reporting user not found"));

        User reportedUser = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Reported user not found"));

        // ✅ Prevent duplicate reports (IMPORTANT)
        if (reportRepository.existsByReportedByAndReportedUser(reportedBy, reportedUser)) {
            return; // already reported → do nothing
        }

        Report report = new Report();
        report.setReportedBy(reportedBy);
        report.setReportedUser(reportedUser);
        report.setReason(reason);

        reportRepository.save(report);
    }
}
