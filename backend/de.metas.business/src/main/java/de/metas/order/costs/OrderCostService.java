package de.metas.order.costs;

import com.google.common.collect.ImmutableList;
import de.metas.currency.CurrencyRepository;
import de.metas.currency.ICurrencyBL;
import de.metas.inout.IInOutBL;
import de.metas.inout.InOutId;
import de.metas.invoice.matchinv.listeners.MatchInvListenersRegistry;
import de.metas.invoice.matchinv.service.MatchInvoiceRepository;
import de.metas.invoice.matchinv.service.MatchInvoiceService;
import de.metas.invoice.service.IInvoiceBL;
import de.metas.order.IOrderBL;
import de.metas.order.costs.inout.InOutCost;
import de.metas.order.costs.inout.InOutCostCreateCommand;
import de.metas.order.costs.inout.InOutCostDeleteCommand;
import de.metas.order.costs.inout.InOutCostId;
import de.metas.order.costs.inout.InOutCostQuery;
import de.metas.order.costs.inout.InOutCostRepository;
import de.metas.order.costs.inout.InOutCostReverseCommand;
import de.metas.order.costs.invoice.CreateMatchInvoiceCommand;
import de.metas.order.costs.invoice.CreateMatchInvoicePlan;
import de.metas.order.costs.invoice.CreateMatchInvoiceRequest;
import de.metas.uom.IUOMConversionBL;
import de.metas.util.Services;
import lombok.NonNull;
import org.compiere.Adempiere;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Stream;

@Service
public class OrderCostService
{
	public static OrderCostService newInstanceForUnitTesting()
	{
		Adempiere.assertUnitTestMode();
		return new OrderCostService(
				new OrderCostRepository(),
				new OrderCostTypeRepository(),
				new InOutCostRepository(),
				new MatchInvoiceService(
						new MatchInvoiceRepository(),
						new MatchInvListenersRegistry(Optional.empty())
				),
				new CurrencyRepository());
	}

	@NonNull private final IOrderBL orderBL = Services.get(IOrderBL.class);
	@NonNull private final IInvoiceBL invoiceBL = Services.get(IInvoiceBL.class);
	@NonNull private final IInOutBL inoutBL = Services.get(IInOutBL.class);
	@NonNull private final ICurrencyBL currencyBL = Services.get(ICurrencyBL.class);
	@NonNull private final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);
	@NonNull private final OrderCostRepository orderCostRepository;
	@NonNull private final OrderCostTypeRepository costTypeRepository;

	@NonNull private final InOutCostRepository inOutCostRepository;
	@NonNull private final MatchInvoiceService matchInvoiceService;
	@NonNull private final CurrencyRepository currencyRepository;

	public OrderCostService(
			final @NonNull OrderCostRepository orderCostRepository,
			final @NonNull OrderCostTypeRepository costTypeRepository,
			final @NonNull InOutCostRepository inOutCostRepository,
			final @NonNull MatchInvoiceService matchInvoiceService,
			final @NonNull CurrencyRepository currencyRepository)
	{
		this.orderCostRepository = orderCostRepository;
		this.costTypeRepository = costTypeRepository;
		this.inOutCostRepository = inOutCostRepository;
		this.matchInvoiceService = matchInvoiceService;
		this.currencyRepository = currencyRepository;
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

	public InOutCost getInOutCostsById(@NonNull final InOutCostId inoutCostId)
	{
		return inOutCostRepository.getById(inoutCostId);
	}

	public ImmutableList<InOutCost> getInOutCostsByIds(@NonNull final Set<InOutCostId> inoutCostIds)
	{
		return inOutCostRepository.getByIds(inoutCostIds);
	}

	public ImmutableList<InOutCost> getByReceiptId(@NonNull final InOutId receiptId)
	{
		return inOutCostRepository.getByReceiptId(receiptId);
	}

	public void updateInOutCostById(@NonNull final InOutCostId inoutCostId, @NonNull final Consumer<InOutCost> consumer)
	{
		inOutCostRepository.updateInOutCostById(inoutCostId, consumer);
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

	public CreateMatchInvoicePlan createMatchInvoice(@NonNull final CreateMatchInvoiceRequest request)
	{
		return createMatchInvoiceCommand(request).execute();
	}

	public CreateMatchInvoicePlan createMatchInvoiceSimulation(@NonNull final CreateMatchInvoiceRequest request)
	{
		return createMatchInvoiceCommand(request).createPlan();
	}

	private CreateMatchInvoiceCommand createMatchInvoiceCommand(final @NonNull CreateMatchInvoiceRequest request)
	{
		return CreateMatchInvoiceCommand.builder()
				.orderCostService(this)
				.matchInvoiceService(matchInvoiceService)
				.invoiceBL(invoiceBL)
				.inoutBL(inoutBL)
				.currencyRepository(currencyRepository)
				.request(request)
				.build();
	}
}
