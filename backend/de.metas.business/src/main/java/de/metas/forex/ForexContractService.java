package de.metas.forex;

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
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_Order;
import org.springframework.stereotype.Service;

@Service
public class ForexContractService
{
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

	public void allocateOrder(
			@NonNull final ForexContractId forexContractId,
			@NonNull final OrderId orderId)
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

		final Money amountToAllocate = Money.of(order.getGrandTotal(), CurrencyId.ofRepoId(order.getC_Currency_ID()));

		forexContractRepository.updateById(
				forexContractId,
				contract -> {
					forexContractAllocationRepository.create(ForexContractAllocateRequest.builder()
							.forexContractId(contract.getId())
							.orgId(contract.getOrgId())
							.orderId(orderId)
							.amount(amountToAllocate)
							.build());

					final Money allocatedAmount = forexContractAllocationRepository.computeAllocatedAmount(contract.getId(), contract.getCurrencyId());
					contract.setAllocatedAmountAndUpdate(allocatedAmount);
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
}
