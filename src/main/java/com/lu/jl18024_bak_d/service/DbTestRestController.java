package com.lu.jl18024_bak_d.service;

import com.lu.jl18024_bak_d.model.TestResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DbTestRestController {

    private final DbTestService testService;

    public DbTestRestController(DbTestService testService) {
        this.testService = testService;
    }

    @GetMapping(value = "/test1/{count}")
    public ResponseEntity<TestResponse> test1(@PathVariable(name = "count") Integer count) {
        return ResponseEntity.ok(testService.test1(count));
    }

    @GetMapping(value = "/test1/clear")
    public ResponseEntity<TestResponse> test1clear() {
        testService.test1clear();
        return ResponseEntity.ok(null);
    }

    @GetMapping(value = "/test2/{count}")
    public ResponseEntity<TestResponse> test2(@PathVariable(name = "count") Integer count) {
        return ResponseEntity.ok(testService.test2(count));
    }

    @GetMapping(value = "/test2/clear")
    public ResponseEntity<TestResponse> test2clear() {
        testService.test2clear();
        return ResponseEntity.ok(null);
    }

    @GetMapping(value = "/test3/{count}")
    public ResponseEntity<TestResponse> test3(@PathVariable(name = "count") Integer count) {
        return ResponseEntity.ok(testService.test3(count));
    }

    @GetMapping(value = "/test3/clear")
    public ResponseEntity<TestResponse> test3clear() {
        testService.test3clear();
        return ResponseEntity.ok(null);
    }
}
