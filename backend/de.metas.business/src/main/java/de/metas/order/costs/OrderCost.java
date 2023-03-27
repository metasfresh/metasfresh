package de.metas.order.costs;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerId;
import de.metas.costing.CostElementId;
import de.metas.currency.CurrencyPrecision;
import de.metas.lang.SOTrx;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.order.OrderId;
import de.metas.order.OrderLineId;
import de.metas.order.costs.calculation_methods.CostCalculationMethod;
import de.metas.order.costs.calculation_methods.CostCalculationMethodParams;
import de.metas.order.costs.calculation_methods.FixedAmountCostCalculationMethodParams;
import de.metas.order.costs.calculation_methods.PercentageCostCalculationMethodParams;
import de.metas.organization.OrgId;
import de.metas.quantity.Quantity;
import de.metas.quantity.QuantityUOMConverter;
import de.metas.util.Check;
import de.metas.util.collections.CollectionUtils;
import de.metas.util.lang.Percent;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Optional;
import java.util.function.Function;

@EqualsAndHashCode
@ToString
public class OrderCost
{
	@Setter(AccessLevel.PACKAGE)
	@Getter @Nullable private OrderCostId id;
	@Getter @NonNull private final OrderId orderId;
	@Getter @NonNull private final SOTrx soTrx;
	@Getter @NonNull private final OrgId orgId;

	@Getter @Nullable private final BPartnerId bpartnerId;
	@Getter @NonNull private final CostElementId costElementId;
	@Getter @NonNull private final OrderCostTypeId costTypeId;
	@Getter @NonNull private final CostCalculationMethod calculationMethod;
	@Getter @Nullable private final CostCalculationMethodParams calculationMethodParams;

	@Getter @NonNull private final CostDistributionMethod distributionMethod;

	@Getter @NonNull private Money costAmount;

	@Getter @NonNull private final ImmutableList<OrderCostDetail> details;

	@Getter @Nullable private OrderLineId createdOrderLineId;

	@Builder(toBuilder = true)
	private OrderCost(
			final @Nullable OrderCostId id,
			final @NonNull OrderId orderId,
			final @NonNull SOTrx soTrx,
			final @NonNull OrgId orgId,
			final @Nullable BPartnerId bpartnerId,
			final @NonNull CostElementId costElementId,
			final @NonNull OrderCostTypeId costTypeId,
			final @NonNull CostCalculationMethod calculationMethod,
			final @Nullable CostCalculationMethodParams calculationMethodParams,
			final @NonNull CostDistributionMethod distributionMethod,
			final @Nullable Money costAmount,
			final @NonNull ImmutableList<OrderCostDetail> details,
			final @Nullable OrderLineId createdOrderLineId)
	{
		if (details.isEmpty())
		{
			throw new AdempiereException("No details");
		}

		final CurrencyId detailsCurrencyId = CollectionUtils.extractSingleElement(details, OrderCostDetail::getCurrencyId);
		if (costAmount != null && !CurrencyId.equals(costAmount.getCurrencyId(), detailsCurrencyId))
		{
			throw new AdempiereException("Header and details shall have the same currency");
		}

		this.id = id;
		this.orderId = orderId;
		this.soTrx = soTrx;
		this.orgId = orgId;
		this.bpartnerId = bpartnerId;
		this.costElementId = costElementId;
		this.costTypeId = costTypeId;
		this.calculationMethod = calculationMethod;
		this.calculationMethodParams = calculationMethodParams;
		this.distributionMethod = distributionMethod;
		this.costAmount = costAmount != null ? costAmount : Money.zero(detailsCurrencyId);
		this.details = details;
		this.createdOrderLineId = createdOrderLineId;
	}

	public CurrencyId getCurrencyId() {return costAmount.getCurrencyId();}

	public Money getOrderLinesNetAmt()
	{
		return details.stream()
				.map(OrderCostDetail::getOrderLineNetAmt)
				.reduce(Money::add)
				.orElseThrow(() -> new AdempiereException("No lines"));// shall not happen
	}

	public void updateCostAmount(
			@NonNull final Function<CurrencyId, CurrencyPrecision> currencyPrecisionProvider,
			@NonNull final QuantityUOMConverter uomConverter)
	{
		if (CostCalculationMethod.PercentageOfAmount.equals(calculationMethod)
				&& CostDistributionMethod.Amount.equals(distributionMethod))
		{
			updateCostAmount_AmountBasedDistribution_using_PercetangeOfAmountCalculationMethod(currencyPrecisionProvider);
		}
		else
		{
			this.costAmount = computeCostAmount(currencyPrecisionProvider);
			distributeCostAmountToDetails(currencyPrecisionProvider, uomConverter);
		}
	}

	private void updateCostAmount_AmountBasedDistribution_using_PercetangeOfAmountCalculationMethod(
			@NonNull final Function<CurrencyId, CurrencyPrecision> currencyPrecisionProvider)
	{
		final PercentageCostCalculationMethodParams calculationMethodParams = (PercentageCostCalculationMethodParams)getCalculationMethodParams();
		if (calculationMethodParams == null)
		{
			throw new AdempiereException("No PercentageCostCalculationMethodParams found");
		}
		final Percent percent = calculationMethodParams.getPercentage();

		final CurrencyId currencyId = getCurrencyId();
		Money lineCostAmountSum = Money.zero(currencyId);
		final CurrencyPrecision precision = currencyPrecisionProvider.apply(currencyId);

		for (int i = 0, lastIndex = details.size() - 1; i <= lastIndex; i++)
		{
			final OrderCostDetail detail = details.get(i);
			final Money lineCostAmount = detail.getOrderLineNetAmt().multiply(percent, precision);
			lineCostAmountSum = lineCostAmountSum.add(lineCostAmount);

			detail.setCostAmount(lineCostAmount);
		}

		this.costAmount = lineCostAmountSum;
	}

	private Money computeCostAmount(
			@NonNull final Function<CurrencyId, CurrencyPrecision> currencyPrecisionProvider)
	{
		return this.calculationMethod.mapOnParams(
				this.calculationMethodParams,
				new CostCalculationMethod.ParamsMapper<Money>()
				{
					@Override
					public Money fixedAmount(final FixedAmountCostCalculationMethodParams params)
					{
						return params.getFixedAmount();
					}

					@Override
					public Money percentageOfAmount(final PercentageCostCalculationMethodParams params)
					{
						final Percent percentageOfAmount = params.getPercentage();

						final Money linesNetAmt = getOrderLinesNetAmt();
						final CurrencyPrecision precision = currencyPrecisionProvider.apply(linesNetAmt.getCurrencyId());
						return linesNetAmt.multiply(percentageOfAmount, precision);
					}
				});
	}

	private void distributeCostAmountToDetails(
			@NonNull final Function<CurrencyId, CurrencyPrecision> currencyPrecisionProvider,
			@NonNull final QuantityUOMConverter uomConverter)
	{
		distributionMethod.apply(new CostDistributionMethod.CaseConsumer()
		{
			@Override
			public void amount() {distributeCostAmountToDetails_AmountBased(currencyPrecisionProvider);}

			@Override
			public void quantity() {distributeCostAmountToDetails_QuantityBased(currencyPrecisionProvider, uomConverter);}
		});
	}

	private void distributeCostAmountToDetails_AmountBased(
			@NonNull final Function<CurrencyId, CurrencyPrecision> currencyPrecisionProvider)
	{
		final Money amtToDistribute = getCostAmount();

		final CurrencyId currencyId = amtToDistribute.getCurrencyId();
		final CurrencyPrecision precision = currencyPrecisionProvider.apply(currencyId);

		final Money totalOrderNetAmount = getOrderLinesNetAmt();
		Money distributedAmt = Money.zero(currencyId);
		for (int i = 0, lastIndex = details.size() - 1; i <= lastIndex; i++)
		{
			final OrderCostDetail detail = details.get(i);
			final Money lineCostAmount;
			if (i < lastIndex)
			{
				final Percent percent = detail.getOrderLineNetAmt().percentageOf(totalOrderNetAmount);
				lineCostAmount = amtToDistribute.multiply(percent, precision);
			}
			else
			{
				lineCostAmount = amtToDistribute.subtract(distributedAmt);
			}

			detail.setCostAmount(lineCostAmount);

			distributedAmt = distributedAmt.add(lineCostAmount);
		}
	}

	private void distributeCostAmountToDetails_QuantityBased(
			@NonNull final Function<CurrencyId, CurrencyPrecision> currencyPrecisionProvider,
			@NonNull final QuantityUOMConverter uomConverter)
	{
		//
		Quantity totalQty = null;
		final ArrayList<Quantity> detailQtys = new ArrayList<>(details.size());
		for (final OrderCostDetail detail : details)
		{
			final Quantity qty = detail.getQtyOrdered();
			if (totalQty == null)
			{
				totalQty = qty;
				detailQtys.add(qty);
			}
			else
			{
				final Quantity qtyConv = uomConverter.convertQuantityTo(qty, detail.getProductId(), totalQty.getUomId());
				totalQty = totalQty.add(qtyConv);
				detailQtys.add(qtyConv);
			}
		}

		//
		final Money amtToDistribute = getCostAmount();
		final CurrencyId currencyId = amtToDistribute.getCurrencyId();
		final CurrencyPrecision precision = currencyPrecisionProvider.apply(currencyId);
		Money distributedAmt = Money.zero(currencyId);
		for (int i = 0, lastIndex = details.size() - 1; i <= lastIndex; i++)
		{
			final OrderCostDetail detail = details.get(i);
			final Money lineCostAmount;
			if (i < lastIndex)
			{
				final Percent percent = detailQtys.get(i).percentageOf(totalQty);
				lineCostAmount = amtToDistribute.multiply(percent, precision);
			}
			else
			{
				lineCostAmount = amtToDistribute.subtract(distributedAmt);
			}

			detail.setCostAmount(lineCostAmount);

			distributedAmt = distributedAmt.add(lineCostAmount);
		}
	}

	public OrderCostAddInOutResult addInOutCost(@NonNull OrderCostAddInOutRequest request)
	{
		final OrderCostDetail detail = getDetailByOrderLineId(request.getOrderLineId());

		final Quantity qty = request.getQty();
		final Money amt = request.getCostAmount();
		detail.addInOutCost(amt, qty);

		return OrderCostAddInOutResult.builder()
				.orderCostDetailId(Check.assumeNotNull(detail.getId(), "detail is saved"))
				.costAmount(amt)
				.qty(qty)
				.build();
	}

	public Money computeInOutCostAmountForQty(
			@NonNull final OrderLineId orderLineId,
			@NonNull final Quantity qty,
			@NonNull final CurrencyPrecision precision)
	{
		final OrderCostDetail detail = getDetailByOrderLineId(orderLineId);

		final Percent percentage = qty.percentageOf(detail.getQtyOrdered());

		final Money maxCostAmount = detail.getCostAmount().subtract(detail.getInoutCostAmount()).toZeroIfNegative();
		final Money costAmount = detail.getCostAmount().multiply(percentage, precision);
		return costAmount.min(maxCostAmount);
	}

	public Optional<OrderCostDetail> getDetailByOrderLineIdIfExists(final OrderLineId orderLineId)
	{
		return details.stream()
				.filter(detail -> OrderLineId.equals(detail.getOrderLineId(), orderLineId))
				.findFirst();
	}

	public OrderCostDetail getDetailByOrderLineId(final OrderLineId orderLineId)
	{
		return getDetailByOrderLineIdIfExists(orderLineId)
				.orElseThrow(() -> new AdempiereException("No cost detail found for " + orderLineId));
	}

	public void setCreatedOrderLineId(@Nullable final OrderLineId createdOrderLineId)
	{
		this.createdOrderLineId = createdOrderLineId;
	}

	public OrderCost copy(@NonNull final OrderCostCloneMapper mapper)
	{
		return toBuilder()
				.id(null)
				.orderId(mapper.getTargetOrderId(orderId))
				.createdOrderLineId(createdOrderLineId != null ? mapper.getTargetOrderLineId(createdOrderLineId) : null)
				.details(details.stream()
						.map(detail -> detail.copy(mapper))
						.collect(ImmutableList.toImmutableList()))
				.build();

	}
}
