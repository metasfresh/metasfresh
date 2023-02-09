package de.metas.order.costs;

import de.metas.currency.ICurrencyBL;
import de.metas.inout.IInOutBL;
import de.metas.inout.InOutId;
import de.metas.order.IOrderBL;
import de.metas.order.costs.inout.InOutCost;
import de.metas.order.costs.inout.InOutCostCreateCommand;
import de.metas.order.costs.inout.InOutCostDeleteCommand;
import de.metas.order.costs.inout.InOutCostQuery;
import de.metas.order.costs.inout.InOutCostRepository;
import de.metas.order.costs.inout.InOutCostReverseCommand;
import de.metas.order.costs.invoice.CreateMatchInvoiceCommand;
import de.metas.order.costs.invoice.CreateMatchInvoiceRequest;
import de.metas.uom.IUOMConversionBL;
import de.metas.util.Services;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;

@Service
public class OrderCostService
{
	@NonNull private final IOrderBL orderBL = Services.get(IOrderBL.class);
	@NonNull private final ICurrencyBL currencyBL = Services.get(ICurrencyBL.class);
	@NonNull private final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);
	@NonNull private final OrderCostRepository orderCostRepository;
	@NonNull private final OrderCostTypeRepository costTypeRepository;

	@NonNull private final IInOutBL inoutBL = Services.get(IInOutBL.class);
	@NonNull private final InOutCostRepository inOutCostRepository;

	public OrderCostService(
			final @NonNull OrderCostRepository orderCostRepository,
			final @NonNull OrderCostTypeRepository costTypeRepository,
			final @NonNull InOutCostRepository inOutCostRepository)
	{
		this.orderCostRepository = orderCostRepository;
		this.costTypeRepository = costTypeRepository;
		this.inOutCostRepository = inOutCostRepository;
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

	public void receiveCosts(@NonNull final InOutId receiptId)
	{
		InOutCostCreateCommand.builder()
				.currencyBL(currencyBL)
				.uomConversionBL(uomConversionBL)
				.inoutBL(inoutBL)
				.orderCostRepository(orderCostRepository)
				.inOutCostRepository(inOutCostRepository)
				//
				.receiptId(receiptId)
				//
				.build()
				.execute();
	}

	public void deleteInOutCosts(@NonNull final InOutId receiptId)
	{
		InOutCostDeleteCommand.builder()
				.orderCostRepository(orderCostRepository)
				.inOutCostRepository(inOutCostRepository)
				//
				.receiptId(receiptId)
				//
				.build()
				.execute();
	}

	public void reverseInOutCosts(@NonNull final InOutId receiptId, @NonNull final InOutId initialReversalId)
	{
		InOutCostReverseCommand.builder()
				.inoutBL(inoutBL)
				.orderCostRepository(orderCostRepository)
				.inOutCostRepository(inOutCostRepository)
				//
				.receiptId(receiptId)
				.initialReversalId(initialReversalId)
				//
				.build()
				.execute();
	}

	public Stream<InOutCost> stream(@NonNull final InOutCostQuery query)
	{
		return inOutCostRepository.stream(query);
	}

	public void createMatchInvoice(CreateMatchInvoiceRequest request)
	{
		CreateMatchInvoiceCommand.builder()
				.request(request)
				.build()
				.execute();
	}
}
