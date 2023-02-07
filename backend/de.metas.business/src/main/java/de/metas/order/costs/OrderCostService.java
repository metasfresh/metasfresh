package de.metas.order.costs;

import de.metas.currency.ICurrencyBL;
import de.metas.order.IOrderBL;
import de.metas.util.Services;
import lombok.NonNull;
import org.springframework.stereotype.Service;

@Service
public class OrderCostService
{
	@NonNull private final IOrderBL orderBL = Services.get(IOrderBL.class);
	@NonNull private final ICurrencyBL currencyBL = Services.get(ICurrencyBL.class);
	@NonNull private final OrderCostRepository orderCostRepository;
	@NonNull private final OrderCostTypeRepository costTypeRepository;

	public OrderCostService(
			final @NonNull OrderCostRepository orderCostRepository,
			final @NonNull OrderCostTypeRepository costTypeRepository)
	{
		this.orderCostRepository = orderCostRepository;
		this.costTypeRepository = costTypeRepository;
	}

	public OrderCostType getCostTypeById(@NonNull final OrderCostTypeId id)
	{
		return costTypeRepository.getById(id);
	}

	public void createOrderCost(@NonNull OrderCostCreateRequest request)
	{
		OrderCostCreateCommand.builder()
				.orderBL(orderBL)
				.currencyBL(currencyBL)
				.orderCostRepository(orderCostRepository)
				.costTypeRepository(costTypeRepository)
				//
				.request(request)
				.build()
				.execute();
	}
}
