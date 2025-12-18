package com.hfuninternal.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/api/messages")
public class MessageController {

    @GetMapping("/unread-count")
    public Map<String, Object> unread() {
        return Map.of("success", true, "count", 0);
    }
}
