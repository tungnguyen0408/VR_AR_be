package bt1.web_ban_giay.mapper;

import bt1.web_ban_giay.dto.PaymentTransactionDTO;
import bt1.web_ban_giay.entity.PaymentTransaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PaymentTransactionMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "order", ignore = true)
    PaymentTransaction toEntity(PaymentTransactionDTO paymentTransactionDTO);

    @Mapping(target = "orderId", source = "order.id")
    PaymentTransactionDTO toDTO(PaymentTransaction paymentTransaction);
}