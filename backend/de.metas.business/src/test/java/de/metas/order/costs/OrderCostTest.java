package de.metas.order.costs;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerId;
import de.metas.business.BusinessTestHelper;
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
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.quantity.QuantityUOMConverters;
import de.metas.util.lang.Percent;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_UOM;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import javax.annotation.Nullable;

import static org.assertj.core.api.Assertions.assertThat;

class OrderCostTest
{
	private CurrencyId eur;
	private I_C_UOM uomKg;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		this.eur = BusinessTestHelper.getEURCurrencyId();
		this.uomKg = BusinessTestHelper.createUomKg();
	}

	private Money euro(@NonNull final String amount) {return Money.of(amount, eur);}

	private Money euroOrZero(@Nullable final String amount) {return amount != null ? euro(amount) : euro("0");}

	private Quantity kg(final String kg) {return Quantity.of(kg, uomKg);}

	@Builder(builderMethodName = "detail", builderClassName = "$DetailBuilder")
	private OrderCostDetail createOrderCostDetail(
			@Nullable OrderLineId orderLineId,
			@Nullable String qty,
			@Nullable String orderLineNetAmt,
			@Nullable String costAmount,
			@Nullable String inoutCostAmount)
	{
		return OrderCostDetail.builder()
				.orderLineId(orderLineId != null ? orderLineId : OrderLineId.ofRepoId(11))
				.productId(ProductId.ofRepoId(12))
				.qtyOrdered(qty != null ? kg(qty) : kg("0"))
				.orderLineNetAmt(euroOrZero(orderLineNetAmt))
				.costAmount(euroOrZero(costAmount))
				.inoutCostAmount(euroOrZero(inoutCostAmount))
				.build();

	}

	@Builder(builderMethodName = "orderCost", builderClassName = "$OrderCostBuilder")
	private OrderCost createOrderCost(
			@Nullable String fixedCostAmount,
			@Nullable String percentageOfAmount,
			@NonNull CostDistributionMethod distributionMethod,
			@Singular ImmutableList<OrderCostDetail> details)
	{
		CostCalculationMethod calculationMethod;
		CostCalculationMethodParams calculationMethodParams;
		if (fixedCostAmount != null)
		{
			calculationMethod = CostCalculationMethod.FixedAmount;
			calculationMethodParams = FixedAmountCostCalculationMethodParams.builder().fixedAmount(euro(fixedCostAmount)).build();
		}
		else if (percentageOfAmount != null)
		{
			calculationMethod = CostCalculationMethod.PercentageOfAmount;
			calculationMethodParams = PercentageCostCalculationMethodParams.builder().percentage(Percent.of(percentageOfAmount)).build();
		}
		else
		{
			throw new AdempiereException("Cannot determine the calculation method");
		}

		return OrderCost.builder()
				.orderId(OrderId.ofRepoId(1))
				.soTrx(SOTrx.PURCHASE)
				.orgId(OrgId.MAIN)
				.bpartnerId(BPartnerId.ofRepoId(2))
				.costElementId(CostElementId.ofRepoId(3))
				.costTypeId(OrderCostTypeId.ofRepoId(4))
				.calculationMethod(calculationMethod)
				.calculationMethodParams(calculationMethodParams)
				.distributionMethod(distributionMethod)
				.costAmount(euro("0")) // to be updated if needed
				.details(details)
				.build();
	}

	@Nested
	class updateCostAmount
	{
		@Test
		void fixedAmount_distributeByAmount()
		{
			final OrderCost orderCost = orderCost()
					.fixedCostAmount("10")
					.distributionMethod(CostDistributionMethod.Amount)
					.detail(detail().qty("1").orderLineNetAmt("10").build())
					.detail(detail().qty("1").orderLineNetAmt("60").build())
					.detail(detail().qty("1").orderLineNetAmt("20").build())
					.detail(detail().qty("1").orderLineNetAmt("10").build())
					.build();

			orderCost.updateCostAmount(currencyId -> CurrencyPrecision.TWO, QuantityUOMConverters.noConversion());

			assertThat(orderCost.getDetails().get(0).getCostAmount()).isEqualTo(euro("1"));
			assertThat(orderCost.getDetails().get(1).getCostAmount()).isEqualTo(euro("6"));
			assertThat(orderCost.getDetails().get(2).getCostAmount()).isEqualTo(euro("2"));
			assertThat(orderCost.getDetails().get(3).getCostAmount()).isEqualTo(euro("1"));
		}

		@Test
		void percentageOfAmount_distributeByAmount()
		{
			final OrderCost orderCost = orderCost()
					.percentageOfAmount("10")
					.distributionMethod(CostDistributionMethod.Amount)
					.detail(detail().qty("1").orderLineNetAmt("100").build())
					.detail(detail().qty("1").orderLineNetAmt("95").build())
					.build();

			orderCost.updateCostAmount(currencyId -> CurrencyPrecision.ofInt(4), QuantityUOMConverters.noConversion());
			assertThat(orderCost.getCostAmount()).isEqualTo(euro("19.5"));
			assertThat(orderCost.getDetails().get(0).getCostAmount()).isEqualTo(euro("10"));
			assertThat(orderCost.getDetails().get(1).getCostAmount()).isEqualTo(euro("9.5"));
		}

		@Test
		void percentageOfAmount_distributeByQty()
		{
			final OrderCost orderCost = orderCost()
					.percentageOfAmount("10")
					.distributionMethod(CostDistributionMethod.Quantity)
					.detail(detail().qty("95").orderLineNetAmt("500").build())
					.detail(detail().qty("5").orderLineNetAmt("500").build())
					.build();

			orderCost.updateCostAmount(currencyId -> CurrencyPrecision.ofInt(4), QuantityUOMConverters.noConversion());
			assertThat(orderCost.getCostAmount()).isEqualTo(euro("100"));
			assertThat(orderCost.getDetails().get(0).getCostAmount()).isEqualTo(euro("95"));
			assertThat(orderCost.getDetails().get(1).getCostAmount()).isEqualTo(euro("5"));
		}

		@Test
		void fixedAmount_distributeByQuantity()
		{
			final OrderCost orderCost = orderCost()
					.fixedCostAmount("10")
					.distributionMethod(CostDistributionMethod.Quantity)
					.detail(detail().qty("10").orderLineNetAmt("0").build())
					.detail(detail().qty("60").orderLineNetAmt("0").build())
					.detail(detail().qty("20").orderLineNetAmt("0").build())
					.detail(detail().qty("10").orderLineNetAmt("0").build())
					.build();

			orderCost.updateCostAmount(currencyId -> CurrencyPrecision.TWO, QuantityUOMConverters.noConversion());

			assertThat(orderCost.getDetails().get(0).getCostAmount()).isEqualTo(euro("1"));
			assertThat(orderCost.getDetails().get(1).getCostAmount()).isEqualTo(euro("6"));
			assertThat(orderCost.getDetails().get(2).getCostAmount()).isEqualTo(euro("2"));
			assertThat(orderCost.getDetails().get(3).getCostAmount()).isEqualTo(euro("1"));
		}
	}

	@Nested
	class computeInOutCostAmountForQty
	{
		@Test
		void underReceipt()
		{
			final OrderLineId orderLineId = OrderLineId.ofRepoId(111);
			final OrderCost orderCost = orderCost()
					.fixedCostAmount("500")
					.distributionMethod(CostDistributionMethod.Amount)
					.detail(detail().orderLineId(orderLineId).qty("500").costAmount("500").inoutCostAmount("400").build())
					.build();

			final Money computedInOutCost = orderCost.computeInOutCostAmountForQty(orderLineId, kg("50"), CurrencyPrecision.TWO);
			assertThat(computedInOutCost).isEqualTo(euro("50"));
		}

		@Test
		void overReceipt()
		{
			final OrderLineId orderLineId = OrderLineId.ofRepoId(111);
			final OrderCost orderCost = orderCost()
					.fixedCostAmount("500")
					.distributionMethod(CostDistributionMethod.Amount)
					.detail(detail().orderLineId(orderLineId).qty("500").costAmount("500").inoutCostAmount("400").build())
					.build();

			final Money computedInOutCost = orderCost.computeInOutCostAmountForQty(orderLineId, kg("300"), CurrencyPrecision.TWO);
			assertThat(computedInOutCost).isEqualTo(euro("100"));
		}
	}
}