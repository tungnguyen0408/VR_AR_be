package bt1.web_ban_giay.service.impl;

import bt1.web_ban_giay.dto.response.DashboardStatsDTO;
import bt1.web_ban_giay.dto.response.RevenueDataDTO;
import bt1.web_ban_giay.entity.Order;
import bt1.web_ban_giay.repository.OrderRepository;
import bt1.web_ban_giay.repository.ProductRepository;
import bt1.web_ban_giay.repository.UserRepository;
import bt1.web_ban_giay.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DashboardServiceImpl implements DashboardService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public DashboardStatsDTO getDashboardStats() {
        DashboardStatsDTO stats = new DashboardStatsDTO();

        // Get total revenue from completed orders
        BigDecimal totalRevenue = orderRepository.findAll().stream()
                .filter(order -> order.getStatus() == Order.OrderStatus.SHIPPED)
                .map(Order::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        stats.setTotalRevenue(totalRevenue);

        // Get total orders
        stats.setTotalOrders(orderRepository.count());

        // Get total customers (users)
        stats.setTotalCustomers(userRepository.count());

        // Get total products
        stats.setTotalProducts(productRepository.count());

        return stats;
    }

    @Override
    public List<RevenueDataDTO> getRevenueDataByDate() {
        // Get current date
        LocalDate today = LocalDate.now();
        // Get start date (15 days ago)
        LocalDate startDate = today.minusDays(14);

        // Get all orders within the date range
        List<Order> orders = orderRepository.findAll().stream()
                .filter(order -> {
                    LocalDateTime orderDate = order.getCreatedAt();
                    return orderDate != null && 
                           orderDate.toLocalDate().isAfter(startDate.minusDays(1)) && 
                           orderDate.toLocalDate().isBefore(today.plusDays(1)) &&
                           order.getStatus() == Order.OrderStatus.SHIPPED;
                })
                .collect(Collectors.toList());

        // Group orders by date and calculate revenue and order count
        Map<LocalDate, List<Order>> ordersByDate = orders.stream()
                .collect(Collectors.groupingBy(order -> order.getCreatedAt().toLocalDate()));

        // Create revenue data for each day
        List<RevenueDataDTO> revenueData = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM");

        for (LocalDate date = startDate; !date.isAfter(today); date = date.plusDays(1)) {
            List<Order> dayOrders = ordersByDate.getOrDefault(date, new ArrayList<>());
            
            BigDecimal dayRevenue = dayOrders.stream()
                    .map(Order::getTotalAmount)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            RevenueDataDTO data = new RevenueDataDTO(
                    date.format(formatter),
                    dayRevenue,
                    (long) dayOrders.size()
            );
            revenueData.add(data);
        }

        return revenueData;
    }
} 