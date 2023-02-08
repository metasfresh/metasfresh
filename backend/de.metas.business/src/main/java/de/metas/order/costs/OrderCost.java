package de.metas.order.costs;

import com.google.common.collect.ImmutableList;
import de.metas.currency.CurrencyPrecision;
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
import java.util.Optional;
import java.util.function.Function;

@EqualsAndHashCode
@ToString
public class OrderCost
{
	@Setter(AccessLevel.PACKAGE)
	@Getter @Nullable private OrderCostId id;
	@Getter @NonNull private final OrderId orderId;
	@Getter @NonNull private final OrgId orgId;
	@Getter @NonNull private final OrderCostTypeId costTypeId;
	@Getter @NonNull private final CostCalculationMethod calculationMethod;
	@Getter @Nullable private final CostCalculationMethodParams calculationMethodParams;

	@Getter @NonNull private final CostDistributionMethod distributionMethod;

	@Getter @NonNull private Money costAmount;

	@Getter @NonNull private final ImmutableList<OrderCostDetail> details;

	@Builder
	private OrderCost(
			final @Nullable OrderCostId id,
			final @NonNull OrderId orderId,
			final @NonNull OrgId orgId,
			final @NonNull OrderCostTypeId costTypeId,
			final @NonNull CostCalculationMethod calculationMethod,
			final @Nullable CostCalculationMethodParams calculationMethodParams,
			final @NonNull CostDistributionMethod distributionMethod,
			final @Nullable Money costAmount,
			final @NonNull ImmutableList<OrderCostDetail> details)
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
		this.orgId = orgId;
		this.costTypeId = costTypeId;
		this.calculationMethod = calculationMethod;
		this.calculationMethodParams = calculationMethodParams;
		this.distributionMethod = distributionMethod;
		this.costAmount = costAmount != null ? costAmount : Money.zero(detailsCurrencyId);
		this.details = details;
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
			@NonNull final Function<CurrencyId, CurrencyPrecision> currencyPrecisionProvider)
	{
		this.costAmount = computeCostAmount(currencyPrecisionProvider);
		distributeCostAmountToDetails(currencyPrecisionProvider);
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
			@NonNull final Function<CurrencyId, CurrencyPrecision> currencyPrecisionProvider)
	{
		distributionMethod.apply(new CostDistributionMethod.CaseConsumer()
		{
			@Override
			public void amount()
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
						final Percent percent = Percent.of(detail.getOrderLineNetAmt().toBigDecimal(), totalOrderNetAmount.toBigDecimal());
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

			@Override
			public void quantity()
			{
				throw new AdempiereException("Quantity based distribution is not supported"); // TODO
			}
		});
	}

	public OrderCostAddReceiptResult addMaterialReceipt(@NonNull OrderCostAddReceiptRequest request)
	{
		final OrderCostDetail detail = getDetailByOrderLineId(request.getOrderLineId());

		final Quantity qty = request.getQty();
		final Money receivedCostAmt = request.getCostAmount();
		detail.addReceivedCost(receivedCostAmt, qty);

		return OrderCostAddReceiptResult.builder()
				.orderCostDetailId(Check.assumeNotNull(detail.getId(), "detail is saved"))
				.costAmount(receivedCostAmt)
				.qty(qty)
				.build();
	}

	public Money computeCostAmountForQty(
			@NonNull final OrderLineId orderLineId,
			@NonNull final Quantity qty,
			@NonNull final CurrencyPrecision precision)
	{
		final OrderCostDetail detail = getDetailByOrderLineId(orderLineId);

		final Percent percentage = qty.percentageOf(detail.getQtyOrdered());
		return detail.getCostAmount().multiply(percentage, precision);
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

}
