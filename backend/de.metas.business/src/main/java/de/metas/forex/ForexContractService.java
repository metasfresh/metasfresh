package de.metas.forex;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.currency.ConversionTypeMethod;
import de.metas.document.engine.DocStatus;
import de.metas.i18n.BooleanWithReason;
import de.metas.invoice.service.IInvoiceBL;
import de.metas.money.CurrencyConversionTypeId;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.money.MoneyService;
import de.metas.order.IOrderBL;
import de.metas.order.OrderId;
import de.metas.organization.ClientAndOrgId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_ForeignExchangeContract;
import org.compiere.model.I_C_Order;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.Set;
import java.util.function.Consumer;

@Service
public class ForexContractService
{
	private final ITrxManager trxManager = Services.get(ITrxManager.class);
	private final IOrderBL orderBL = Services.get(IOrderBL.class);
	private final IInvoiceBL invoiceBL = Services.get(IInvoiceBL.class);
	@NonNull private final ForexContractRepository forexContractRepository;
	@NonNull private final ForexContractAllocationRepository forexContractAllocationRepository;
	@NonNull private final MoneyService moneyService;

	public ForexContractService(
			final @NonNull ForexContractRepository forexContractRepository,
			final @NonNull ForexContractAllocationRepository forexContractAllocationRepository,
			final @NonNull MoneyService moneyService)
	{
		this.forexContractRepository = forexContractRepository;
		this.forexContractAllocationRepository = forexContractAllocationRepository;
		this.moneyService = moneyService;
	}

	public ForexContract getById(@NonNull final ForexContractId id)
	{
		return forexContractRepository.getById(id);
	}

	public ImmutableList<ForexContract> getContractsByOrderId(@NonNull final OrderId orderId)
	{
		final ImmutableSet<ForexContractId> forexContractIds = forexContractAllocationRepository.getContractIdsByOrderIds(ImmutableSet.of(orderId));
		return forexContractRepository.getByIds(forexContractIds);
	}

	public ImmutableSet<ForexContractId> getContractIdsByOrderId(@NonNull final OrderId orderId)
	{
		return forexContractAllocationRepository.getContractIdsByOrderIds(ImmutableSet.of(orderId));
	}

	public ImmutableSet<ForexContractId> getContractIdsByOrderIds(@NonNull final Set<OrderId> orderIds)
	{
		return forexContractAllocationRepository.getContractIdsByOrderIds(orderIds);
	}

	public Money computeOrderAmountToAllocate(final OrderId orderId)
	{
		final I_C_Order order = orderBL.getById(orderId);
		final Money grandTotal = Money.of(order.getGrandTotal(), CurrencyId.ofRepoId(order.getC_Currency_ID()));
		final Money allocatedAmount = forexContractAllocationRepository.computeAllocatedAmount(orderId, grandTotal.getCurrencyId());
		return grandTotal.subtract(allocatedAmount).toZeroIfNegative();
	}

	public void allocateOrder(
			@NonNull final ForexContractId forexContractId,
			@NonNull final OrderId orderId,
			@NonNull final BigDecimal amountToAllocateBD)
	{
		final I_C_Order order = orderBL.getById(orderId);
		checkOrderEligibleToAllocate(order).assertTrue();

		final CurrencyConversionTypeId fecConversionTypeId = moneyService.getCurrencyConversionTypeId(ConversionTypeMethod.ForeignExchangeContract);
		order.setC_ConversionType_ID(fecConversionTypeId.getRepoId());
		orderBL.save(order);

		final Money orderGrandTotal = Money.of(order.getGrandTotal(), CurrencyId.ofRepoId(order.getC_Currency_ID()));
		final Money amountToAllocate = Money.of(amountToAllocateBD, orderGrandTotal.getCurrencyId());

		forexContractRepository.updateById(
				forexContractId,
				contract -> {
					contract.assertCanAllocate(amountToAllocate);

					forexContractAllocationRepository.create(ForexContractAllocateRequest.builder()
							.forexContractId(contract.getId())
							.orgId(contract.getOrgId())
							.orderId(orderId)
							.orderGrandTotal(orderGrandTotal)
							.amountToAllocate(amountToAllocate)
							.build());

					updateAmountsNoSave(contract);
				});
	}

	@NonNull
	public BooleanWithReason checkOrderEligibleToAllocate(@NonNull final OrderId orderId)
	{
		return checkOrderCompleted(orderId);
	}

	@NonNull
	public BooleanWithReason checkOrderEligibleToAllocate(@NonNull final I_C_Order order)
	{
		return !orderBL.isCompleted(order)
				? BooleanWithReason.falseBecause("Order shall be completed")
				: BooleanWithReason.TRUE;
	}

	public boolean hasAllocations(@NonNull final ForexContractId contractId)
	{
		return forexContractAllocationRepository.hasAllocations(contractId);
	}

	public boolean hasAllocations(@NonNull final OrderId orderId)
	{
		return forexContractAllocationRepository.hasAllocations(orderId);
	}

	public void updateAmountsAfterCommit(@NonNull final ForexContractId contractId)
	{
		trxManager.accumulateAndProcessAfterCommit(
				"ForexContractService.contractIdsToUpdate",
				ImmutableList.of(contractId),
				this::updateAmounts);
	}

	public void unallocateLines(@NonNull final ForexContractId contractId, @NonNull final Set<ForexContractAllocation> allocations)
	{
		for (final ForexContractAllocation allocation : allocations)
		{
			validateEligibleToUnallocate(allocation.getOrderId(), contractId);

			unallocateLine(allocation);
		}
	}

	private void updateAmounts(final ImmutableList<ForexContractId> contractIds)
	{
		// NOTE: usually will be only one contract, so it's acceptable to have SQL N+1 issue when fetching allocations for each contact
		forexContractRepository.updateByIds(contractIds, this::updateAmountsNoSave);
	}

	private void updateAmountsNoSave(@NonNull final ForexContract contract)
	{
		final Money allocatedAmount = forexContractAllocationRepository.computeAllocatedAmount(contract.getId(), contract.getCurrencyId());
		contract.setAllocatedAmountAndUpdate(allocatedAmount);
	}

	public void updateWhileSaving(@NonNull final I_C_ForeignExchangeContract record, @NonNull final Consumer<ForexContract> updater)
	{
		ForexContractRepository.updateRecordNoSave(record, updater);
	}

	@NonNull
	public ImmutableSet<ForexContractId> getContractIdsEligibleToAllocateOrder(
			@NonNull final OrderId orderId,
			@Nullable final String displayNameSearchTerm)
	{
		final I_C_Order order = orderBL.getById(orderId);
		checkOrderEligibleToAllocate(order).assertTrue();

		final CurrencyId baseCurrencyId = moneyService.getBaseCurrencyId(ClientAndOrgId.ofClientAndOrg(order.getAD_Client_ID(), order.getAD_Org_ID()));

		return forexContractRepository.queryIds(
				ForexContractQuery.builder()
						.docStatus(DocStatus.Completed)
						.currencyId(CurrencyId.ofRepoId(order.getC_Currency_ID()))
						.currencyToId(baseCurrencyId)
						.onlyWithOpenAmount(true)
						.displayNameSearchTerm(displayNameSearchTerm)
						.build());
	}

	@NonNull
	private BooleanWithReason orderEligibleToUnallocate(
			@NonNull final OrderId orderId,
			@NonNull final ForexContractId contractId)
	{
		final BooleanWithReason isOrderEligible = checkOrderCompleted(orderId);
		if (isOrderEligible.isFalse())
		{
			return isOrderEligible;
		}

		final boolean hasAllocations = hasAllocations(orderId);
		if (!hasAllocations)
		{
			return BooleanWithReason.falseBecause("Order has no FEC allocations!");
		}

		return invoiceBL.hasInvoiceWithForexContract(orderId, contractId)
				? BooleanWithReason.trueBecause("Order has invoices created with Forex contract ID!")
				: BooleanWithReason.FALSE;
	}

	private void validateEligibleToUnallocate(
			@NonNull final OrderId orderId,
			@NonNull final ForexContractId contractId)
	{
		final BooleanWithReason orderEligibleToUnallocate = orderEligibleToUnallocate(orderId, contractId);
		if (orderEligibleToUnallocate.isFalse())
		{
			return;
		}

		throw new AdempiereException(orderEligibleToUnallocate.getReason())
				.appendParametersToMessage()
				.setParameter("ForexContractId", contractId.getRepoId())
				.setParameter("OrderId", orderId.getRepoId())
				.markAsUserValidationError();
	}

	private void unallocateLine(@NonNull final ForexContractAllocation contractAllocation)
	{
		forexContractRepository.updateById(
				contractAllocation.getContractId(),
				contract -> {
					forexContractAllocationRepository.delete(contractAllocation.getId());

					updateAmountsNoSave(contract);
				});
	}

	@NonNull
	private BooleanWithReason checkOrderCompleted(@NonNull final OrderId orderId)
	{
		return !orderBL.isCompleted(orderId)
				? BooleanWithReason.falseBecause("Order shall be completed")
				: BooleanWithReason.TRUE;
	}
}
