package bt1.web_ban_giay.controller;

import bt1.web_ban_giay.dto.response.DashboardStatsDTO;
import bt1.web_ban_giay.dto.response.RevenueDataDTO;
import bt1.web_ban_giay.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/stats")
    public ResponseEntity<DashboardStatsDTO> getDashboardStats() {
        return ResponseEntity.ok(dashboardService.getDashboardStats());
    }

    @GetMapping("/revenue")
    public ResponseEntity<List<RevenueDataDTO>> getRevenueData() {
        return ResponseEntity.ok(dashboardService.getRevenueDataByDate());
    }
} 