package kitchenpos.application;

import kitchenpos.domain.OrderStatus;
import kitchenpos.domain.OrderTable;
import kitchenpos.domain.OrderTables;
import kitchenpos.domain.TableGroup;
import kitchenpos.repository.OrderRepository;
import kitchenpos.repository.OrderTableRepository;
import kitchenpos.repository.TableGroupRepository;
import kitchenpos.ui.request.TableGroupCreateRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class TableGroupService {

    private final OrderRepository orderRepository;
    private final OrderTableRepository orderTableRepository;
    private final TableGroupRepository tableGroupRepository;

    public TableGroupService(OrderRepository orderRepository, OrderTableRepository orderTableRepository, TableGroupRepository tableGroupRepository) {
        this.orderRepository = orderRepository;
        this.orderTableRepository = orderTableRepository;
        this.tableGroupRepository = tableGroupRepository;
    }

    public TableGroup create(final TableGroupCreateRequest tableGroupCreateRequest) {
        tableGroupCreateRequest.validate();

        List<Long> orderTableIds = tableGroupCreateRequest.getOrderTableIds()
                .stream().map(TableGroupCreateRequest.OrderTableId::getId)
                .collect(Collectors.toList());

        final OrderTables savedOrderTables = new OrderTables(orderTableRepository.findAllByIdIn(orderTableIds));

        savedOrderTables.verify();

        TableGroup tableGroup = new TableGroup(LocalDateTime.now(), new ArrayList<>());

        savedOrderTables.setTableGroup(tableGroup);

        return tableGroupRepository.save(tableGroup);
    }

    public void ungroup(final Long tableGroupId) {
        final List<OrderTable> orderTables = orderTableRepository.findAllByTableGroupId(tableGroupId);

        final List<Long> orderTableIds = orderTables.stream()
                .map(OrderTable::getId)
                .collect(Collectors.toList());

        checkIfAnyOrderIsNotCompleted(orderTableIds);

        orderTables.forEach(OrderTable::unGroup);
    }

    private void checkIfAnyOrderIsNotCompleted(List<Long> orderTableIds) {
        if (orderRepository.existsByOrderTableIdInAndOrderStatusIn(
                orderTableIds, Arrays.asList(OrderStatus.COOKING, OrderStatus.MEAL))) {
            throw new IllegalArgumentException();
        }
    }
}
