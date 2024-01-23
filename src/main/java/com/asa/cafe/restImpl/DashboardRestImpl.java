package com.asa.cafe.restImpl;

import com.asa.cafe.rest.DashboardRest;
import com.asa.cafe.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class DashboardRestImpl implements DashboardRest {
    @Autowired
    DashboardService dashboardService;

    @Override
    public ResponseEntity<Map<String, Object>> getCount() {
        return dashboardService.getCount();
    }



}
