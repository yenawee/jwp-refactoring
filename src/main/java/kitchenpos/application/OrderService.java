package kitchenpos.application;

import kitchenpos.application.request.OrderCreateRequest;
import kitchenpos.application.request.OrderLineItemCreateRequest;
import kitchenpos.application.response.OrderResponse;
import kitchenpos.domain.Menu;
import kitchenpos.domain.Order;
import kitchenpos.domain.OrderLineItem;
import kitchenpos.domain.OrderTable;
import kitchenpos.repository.MenuRepository;
import kitchenpos.repository.OrderRepository;
import kitchenpos.repository.OrderTableRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class OrderService {

    private MenuRepository menuRepository;
    private OrderRepository orderRepository;
    private OrderTableRepository orderTableRepository;

    public OrderService(MenuRepository menuRepository, OrderRepository orderRepository, OrderTableRepository orderTableRepository) {
        this.menuRepository = menuRepository;
        this.orderRepository = orderRepository;
        this.orderTableRepository = orderTableRepository;
    }

    public OrderResponse create(final OrderCreateRequest orderCreateRequest) {
        orderCreateRequest.validate();

        OrderTable orderTable = getOrderTable(orderCreateRequest);

        List<OrderLineItemCreateRequest> orderLineItemCreateRequests = orderCreateRequest.getOrderLineItemCreateRequests();

        if (orderLineItemCreateRequests.size() != menuRepository.countByIdIn(orderCreateRequest.getMenuIds())) {
            throw new IllegalArgumentException();
        }

        Order order = new Order(orderTable, new ArrayList<>());

        for (final OrderLineItemCreateRequest orderLineItem : orderLineItemCreateRequests) {
            Menu menu = menuRepository.findById(orderLineItem.getMenuId())
                    .orElseThrow(IllegalArgumentException::new);

            order.addOrderLineItem(new OrderLineItem(order, menu, orderLineItem.getQuantity()));
        }

        Order savedOrder = orderRepository.save(order);

        return OrderResponse.from(savedOrder);
    }

    private OrderTable getOrderTable(OrderCreateRequest orderCreateRequest) {
        OrderTable orderTable = orderTableRepository.findById(orderCreateRequest.getOrderTableId())
                .orElseThrow(IllegalArgumentException::new);

        if (orderTable.isEmpty()) {
            throw new IllegalArgumentException();
        }
        return orderTable;
    }

    @Transactional(readOnly = true)
    public List<OrderResponse> list() {
        List<Order> orders = orderRepository.findAll();

        return orders.stream()
                .map(OrderResponse::from)
                .collect(Collectors.toList());
    }

    public OrderResponse changeOrderStatus(final Long orderId, Order order) {
        Order savedOrder = orderRepository.findById(orderId)
                .orElseThrow(IllegalArgumentException::new);

        savedOrder.changeStatus(order.getOrderStatus());

        return OrderResponse.from(savedOrder);
    }
}
