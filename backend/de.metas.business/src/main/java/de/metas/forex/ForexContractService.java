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
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_Order;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

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

	public ImmutableSet<ForexContractId> getContractIdsByOrderId(@NonNull final OrderId orderId)
	{
		return forexContractAllocationRepository.getContractIdsByOrderId(orderId);
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
		final BooleanWithReason orderEligibleToAllocate = checkOrderEligibleToAllocate(order);
		if (orderEligibleToAllocate.isFalse())
		{
			throw new AdempiereException(orderEligibleToAllocate.getReason());
		}

		final CurrencyConversionTypeId fecConversionTypeId = currencyBL.getCurrencyConversionTypeId(ConversionTypeMethod.ForeignExchangeContract);
		order.setC_ConversionType_ID(fecConversionTypeId.getRepoId());
		orderBL.save(order);

		final Money amountToAllocate = Money.of(amountToAllocateBD, CurrencyId.ofRepoId(order.getC_Currency_ID()));

		forexContractRepository.updateById(
				forexContractId,
				contract -> {
					contract.assertCanAllocate(amountToAllocate);

					forexContractAllocationRepository.create(ForexContractAllocateRequest.builder()
							.forexContractId(contract.getId())
							.orgId(contract.getOrgId())
							.orderId(orderId)
							.amount(amountToAllocate)
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
}
