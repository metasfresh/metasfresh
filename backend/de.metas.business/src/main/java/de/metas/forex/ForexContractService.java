package de.metas.forex;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.currency.ConversionTypeMethod;
import de.metas.currency.ICurrencyBL;
import de.metas.document.engine.DocStatus;
import de.metas.i18n.BooleanWithReason;
import de.metas.money.CurrencyConversionTypeId;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.order.IOrderBL;
import de.metas.order.OrderId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.compiere.model.I_C_ForeignExchangeContract;
import org.compiere.model.I_C_Order;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Set;
import java.util.function.Consumer;

@Service
public class ForexContractService
{
	private final ITrxManager trxManager = Services.get(ITrxManager.class);
	private final IOrderBL orderBL = Services.get(IOrderBL.class);
	private final ICurrencyBL currencyBL = Services.get(ICurrencyBL.class);
	@NonNull private final ForexContractRepository forexContractRepository;
	@NonNull private final ForexContractAllocationRepository forexContractAllocationRepository;

	public ForexContractService(
			final @NonNull ForexContractRepository forexContractRepository,
			final @NonNull ForexContractAllocationRepository forexContractAllocationRepository)
	{
		this.forexContractRepository = forexContractRepository;
		this.forexContractAllocationRepository = forexContractAllocationRepository;
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

		final CurrencyConversionTypeId fecConversionTypeId = currencyBL.getCurrencyConversionTypeId(ConversionTypeMethod.ForeignExchangeContract);
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

	public BooleanWithReason checkOrderEligibleToAllocate(final OrderId orderId)
	{
		final I_C_Order order = orderBL.getById(orderId);
		return checkOrderEligibleToAllocate(order);
	}

	private static BooleanWithReason checkOrderEligibleToAllocate(final I_C_Order order)
	{
		if (!DocStatus.ofCode(order.getDocStatus()).isCompleted())
		{
			return BooleanWithReason.falseBecause("Order shall be completed");
		}

		return BooleanWithReason.TRUE;
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

	public ImmutableSet<ForexContractId> getContractIdsEligibleToAllocateOrder(@NonNull final OrderId orderId)
	{
		final I_C_Order order = orderBL.getById(orderId);
		checkOrderEligibleToAllocate(order).assertTrue();

		return forexContractRepository.queryIds(
				ForexContractQuery.builder()
						.docStatus(DocStatus.Completed)
						.currencyId(CurrencyId.ofRepoId(order.getC_Currency_ID()))
						.onlyWithOpenAmount(true)
						.build());
	}
}
