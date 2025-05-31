package bt1.web_ban_giay.service;

import bt1.web_ban_giay.dto.response.DashboardStatsDTO;
import bt1.web_ban_giay.dto.response.RevenueDataDTO;

import java.util.List;

public interface DashboardService {
    DashboardStatsDTO getDashboardStats();
    List<RevenueDataDTO> getRevenueDataByDate();
} 