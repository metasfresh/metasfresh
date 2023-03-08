package de.metas.order.costs.invoice;

import com.google.common.collect.ImmutableList;
import de.metas.business.BusinessTestHelper;
import de.metas.costing.CostElementId;
import de.metas.currency.CurrencyPrecision;
import de.metas.inout.model.I_M_InOutLine;
import de.metas.invoice.matchinv.MatchInvCostPart;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.order.costs.OrderCostTypeId;
import de.metas.order.costs.inout.InOutCostId;
import de.metas.product.ProductId;
import de.metas.quantity.StockQtyAndUOMQtys;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_UOM;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class CreateMatchInvoicePlanTest
{
	private CurrencyId EUR;
	private ProductId productId;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		this.EUR = BusinessTestHelper.getEURCurrencyId();

		final I_C_UOM uomKg = BusinessTestHelper.createUomKg();
		this.productId = BusinessTestHelper.createProductId("product", uomKg);
	}

	private Money euro(final String amount) {return Money.of(amount, EUR);}

	@Builder(builderMethodName = "planLine", builderClassName = "$PlanLine")
	private CreateMatchInvoicePlanLine createPlanLine(
			@NonNull String costAmount)
	{
		return CreateMatchInvoicePlanLine.builder()
				.inoutLine(InterfaceWrapperHelper.newInstance(I_M_InOutLine.class)) // dummy
				.inoutCost(MatchInvCostPart.builder()
						.inoutCostId(InOutCostId.ofRepoId(1))
						.costTypeId(OrderCostTypeId.ofRepoId(2))
						.costElementId(CostElementId.ofRepoId(3))
						.costAmountInOut(euro(costAmount))
						.costAmountInvoiced(euro(costAmount))
						.build())
				.qty(StockQtyAndUOMQtys.ofQtyInStockUOM(BigDecimal.ONE, productId))
				.build();
	}

	@Nested
	class setCostAmountInvoiced
	{
		@Test
		void received_60_40_invoiced_110()
		{
			final CreateMatchInvoicePlan plan = CreateMatchInvoicePlan.ofList(ImmutableList.of(
					planLine().costAmount("60").build(),
					planLine().costAmount("40").build()
			));

			plan.setCostAmountInvoiced(euro("110"), CurrencyPrecision.TWO);

			assertThat(plan.getLines().get(0).getCostAmountInvoiced()).isEqualTo(euro("66"));
			assertThat(plan.getLines().get(1).getCostAmountInvoiced()).isEqualTo(euro("44"));
		}

		@Test
		void received_10_10_10_invoiced_31()
		{
			final CreateMatchInvoicePlan plan = CreateMatchInvoicePlan.ofList(ImmutableList.of(
					planLine().costAmount("10").build(),
					planLine().costAmount("10").build(),
					planLine().costAmount("10").build()
			));

			plan.setCostAmountInvoiced(euro("31"), CurrencyPrecision.TWO);

			assertThat(plan.getLines().get(0).getCostAmountInvoiced()).isEqualTo(euro("10.33"));
			assertThat(plan.getLines().get(1).getCostAmountInvoiced()).isEqualTo(euro("10.33"));
			assertThat(plan.getLines().get(2).getCostAmountInvoiced()).isEqualTo(euro("10.34"));
		}
	}
}