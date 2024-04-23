package sample.cafekiosk.spring.api.controller.order;

import jakarta.validation.Valid;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import sample.cafekiosk.spring.api.ApiResponse;
import sample.cafekiosk.spring.api.controller.order.request.OrderCreateRequest;
import sample.cafekiosk.spring.api.service.order.OrderService;
import sample.cafekiosk.spring.api.service.order.response.OrderResponse;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    /*
     *request -> servicerequest로
     * dto 전환을 통해 controller변환에 대해 service단의 영향이 없어
     *  느슨한 의존관계를 갖고 책임을 분리할 수 있다.
     *
     *
     * */
    @PostMapping("/api/v1/orders/new")
    public ApiResponse<OrderResponse> createOrder(@Valid @RequestBody OrderCreateRequest request) {
        LocalDateTime registeredDateTime = LocalDateTime.now();
        return ApiResponse.ok(
            orderService.createOrder(request.toServiceRequest(), registeredDateTime));
    }
}
