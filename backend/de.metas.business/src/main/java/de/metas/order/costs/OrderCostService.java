package de.metas.order.costs;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.adempiere.model.I_C_InvoiceLine;
import de.metas.currency.CurrencyRepository;
import de.metas.inout.IInOutBL;
import de.metas.inout.InOutId;
import de.metas.invoice.InvoiceAndLineId;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.matchinv.listeners.MatchInvListenersRegistry;
import de.metas.invoice.matchinv.service.MatchInvoiceRepository;
import de.metas.invoice.matchinv.service.MatchInvoiceService;
import de.metas.invoice.service.IInvoiceBL;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.money.MoneyService;
import de.metas.order.IOrderBL;
import de.metas.order.OrderAndLineId;
import de.metas.order.OrderId;
import de.metas.order.OrderLineId;
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
import org.compiere.model.I_C_Invoice;
import org.springframework.stereotype.Service;

import java.util.List;
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
				new MoneyService(new CurrencyRepository()));
	}

	@NonNull private final IOrderBL orderBL = Services.get(IOrderBL.class);
	@NonNull private final IInvoiceBL invoiceBL = Services.get(IInvoiceBL.class);
	@NonNull private final IInOutBL inoutBL = Services.get(IInOutBL.class);
	@NonNull private final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);
	@NonNull private final OrderCostRepository orderCostRepository;
	@NonNull private final OrderCostTypeRepository costTypeRepository;

	@NonNull private final InOutCostRepository inOutCostRepository;
	@NonNull private final MatchInvoiceService matchInvoiceService;
	@NonNull private final MoneyService moneyService;

	public OrderCostService(
			final @NonNull OrderCostRepository orderCostRepository,
			final @NonNull OrderCostTypeRepository costTypeRepository,
			final @NonNull InOutCostRepository inOutCostRepository,
			final @NonNull MatchInvoiceService matchInvoiceService,
			final @NonNull MoneyService moneyService)
	{
		this.orderCostRepository = orderCostRepository;
		this.costTypeRepository = costTypeRepository;
		this.inOutCostRepository = inOutCostRepository;
		this.matchInvoiceService = matchInvoiceService;
		this.moneyService = moneyService;
	}

	public OrderCostType getCostTypeById(@NonNull final OrderCostTypeId id)
	{
		return costTypeRepository.getById(id);
	}

	public void createOrderCost(@NonNull OrderCostCreateRequest request)
	{
		OrderCostCreateCommand.builder()
				.orderBL(orderBL)
				.moneyService(moneyService)
				.uomConverter(uomConversionBL)
				.orderCostRepository(orderCostRepository)
				.costTypeRepository(costTypeRepository)
				//
				.request(request)
				.build()
				.execute();
	}

	public List<OrderCost> getByOrderId(@NonNull final OrderId orderId)
	{
		return orderCostRepository.getByOrderId(orderId);
	}

	public List<OrderCost> getByOrderIds(@NonNull final Set<OrderId> orderIds)
	{
		return orderCostRepository.getByOrderIds(orderIds);
	}

	public InOutCost getInOutCostsById(@NonNull final InOutCostId inoutCostId)
	{
		return inOutCostRepository.getById(inoutCostId);
	}

	public ImmutableList<InOutCost> getInOutCostsByIds(@NonNull final Set<InOutCostId> inoutCostIds)
	{
		return inOutCostRepository.getByIds(inoutCostIds);
	}

	public ImmutableList<InOutCost> getByInOutId(@NonNull final InOutId inoutId)
	{
		return inOutCostRepository.getByInOutId(inoutId);
	}

	public void updateInOutCostById(@NonNull final InOutCostId inoutCostId, @NonNull final Consumer<InOutCost> consumer)
	{
		inOutCostRepository.updateInOutCostById(inoutCostId, consumer);
	}

	public void createInOutCosts(@NonNull final InOutId inoutId)
	{
		InOutCostCreateCommand.builder()
				.moneyService(moneyService)
				.uomConversionBL(uomConversionBL)
				.inoutBL(inoutBL)
				.orderCostRepository(orderCostRepository)
				.inOutCostRepository(inOutCostRepository)
				//
				.inoutId(inoutId)
				//
				.build()
				.execute();
	}

	public void deleteInOutCosts(@NonNull final InOutId inoutId)
	{
		InOutCostDeleteCommand.builder()
				.orderCostRepository(orderCostRepository)
				.inOutCostRepository(inOutCostRepository)
				//
				.inoutId(inoutId)
				//
				.build()
				.execute();
	}

	public void reverseInOutCosts(@NonNull final InOutId inoutId, @NonNull final InOutId initialReversalId)
	{
		InOutCostReverseCommand.builder()
				.inoutBL(inoutBL)
				.orderCostRepository(orderCostRepository)
				.inOutCostRepository(inOutCostRepository)
				//
				.inoutId(inoutId)
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
				.moneyService(moneyService)
				.request(request)
				.build();
	}

	public Money getInvoiceLineOpenAmt(InvoiceAndLineId invoiceAndLineId)
	{
		final I_C_InvoiceLine invoiceLine = invoiceBL.getLineById(invoiceAndLineId);
		return getInvoiceLineOpenAmt(invoiceLine);
	}

	public Money getInvoiceLineOpenAmt(I_C_InvoiceLine invoiceLine)
	{
		final InvoiceId invoiceId = InvoiceId.ofRepoId(invoiceLine.getC_Invoice_ID());
		final I_C_Invoice invoice = invoiceBL.getById(invoiceId);
		Money openAmt = Money.of(invoiceLine.getLineNetAmt(), CurrencyId.ofRepoId(invoice.getC_Currency_ID()));

		final Money matchedAmt = matchInvoiceService.getCostAmountMatched(InvoiceAndLineId.ofRepoId(invoiceId, invoiceLine.getC_InvoiceLine_ID())).orElse(null);
		if (matchedAmt != null)
		{
			openAmt = openAmt.subtract(matchedAmt);
		}

		return openAmt;
	}

	public void cloneAllByOrderId(
			@NonNull final OrderId orderId,
			@NonNull final OrderCostCloneMapper mapper)
	{
		final List<OrderCost> originalOrderCosts = orderCostRepository.getByOrderId(orderId);

		final ImmutableList<OrderCost> clonedOrderCosts = originalOrderCosts.stream()
				.map(originalOrderCost -> originalOrderCost.copy(mapper))
				.collect(ImmutableList.toImmutableList());

		orderCostRepository.saveAll(clonedOrderCosts);
	}

	public void updateOrderCostsOnOrderLineChanged(final OrderCostDetailOrderLinePart orderLineInfo)
	{
		orderCostRepository.changeByOrderLineId(
				orderLineInfo.getOrderLineId(),
				orderCost -> {
					orderCost.updateOrderLineInfoIfApplies(orderLineInfo, moneyService::getStdPrecision, uomConversionBL);
					updateCreatedOrderLineIfAny(orderCost);
				});

	}

	private void updateCreatedOrderLineIfAny(@NonNull final OrderCost orderCost)
	{
		if (orderCost.getCreatedOrderLineId() == null)
		{
			return;
		}

		CreateOrUpdateOrderLineFromOrderCostCommand.builder()
				.orderBL(orderBL)
				.moneyService(moneyService)
				.orderCost(orderCost)
				.build()
				.execute();
	}

	public void deleteByCreatedOrderLineId(@NonNull final OrderAndLineId createdOrderLineId)
	{
		orderCostRepository.deleteByCreatedOrderLineId(createdOrderLineId);
	}

	public boolean isCostGeneratedOrderLine(@NonNull final OrderLineId orderLineId)
	{
		return orderCostRepository.hasCostsByCreatedOrderLineIds(ImmutableSet.of(orderLineId));
	}

}
