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
import de.metas.order.costs.calculation_methods.FixedAmountCostCalculationMethodParams;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.quantity.QuantityUOMConverters;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
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
			@NonNull String fixedCostAmount,
			@NonNull CostDistributionMethod distributionMethod,
			@Singular ImmutableList<OrderCostDetail> details)
	{
		return OrderCost.builder()
				.orderId(OrderId.ofRepoId(1))
				.soTrx(SOTrx.PURCHASE)
				.orgId(OrgId.MAIN)
				.bpartnerId(BPartnerId.ofRepoId(2))
				.costElementId(CostElementId.ofRepoId(3))
				.costTypeId(OrderCostTypeId.ofRepoId(4))
				.calculationMethod(CostCalculationMethod.FixedAmount)
				.calculationMethodParams(FixedAmountCostCalculationMethodParams.builder().fixedAmount(euro(fixedCostAmount)).build())
				.distributionMethod(distributionMethod)
				.costAmount(euro(fixedCostAmount))
				.details(details)
				.build();
	}

	@Nested
	class updateCostAmount
	{
		@Test
		void amountBased()
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
		void qtyBased()
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