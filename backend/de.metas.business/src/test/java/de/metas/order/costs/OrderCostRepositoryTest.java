package de.metas.order.costs;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerId;
import de.metas.business.BusinessTestHelper;
import de.metas.costing.CostElementId;
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
import lombok.NonNull;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_UOM;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class OrderCostRepositoryTest
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

	@SuppressWarnings("SameParameterValue")
	private Quantity kg(final String kg) {return Quantity.of(kg, uomKg);}

	@Test
	void save_load()
	{
		final OrderCost orderCost = OrderCost.builder()
				.orderId(OrderId.ofRepoId(1))
				.soTrx(SOTrx.PURCHASE)
				.orgId(OrgId.MAIN)
				.bpartnerId(BPartnerId.ofRepoId(2))
				.costElementId(CostElementId.ofRepoId(3))
				.costTypeId(OrderCostTypeId.ofRepoId(4))
				.calculationMethod(CostCalculationMethod.FixedAmount)
				.calculationMethodParams(FixedAmountCostCalculationMethodParams.builder().fixedAmount(euro("100")).build())
				.distributionMethod(CostDistributionMethod.Amount)
				.costAmount(euro("100"))
				.details(ImmutableList.of(
								OrderCostDetail.builder()
										.orderLineInfo(OrderCostDetailOrderLinePart.builder()
												.orderLineId(OrderLineId.ofRepoId(11))
												.productId(ProductId.ofRepoId(12))
												.qtyOrdered(kg("11"))
												.orderLineNetAmt(euro("1000"))
												.build())
										.costAmount(euro("123")) // NOTE: the figure makes no sense, but we want to see it's saved/loaded as-is
										.inoutCostAmount(euro("13"))
										.build()
						)
				)
				.build();

		final OrderCostRepository orderCostRepository = new OrderCostRepository();
		orderCostRepository.save(orderCost);

		assert orderCost.getId() != null;
		final OrderCost orderCostLoaded = orderCostRepository.getById(orderCost.getId());
		assertThat(orderCostLoaded).usingRecursiveComparison().isEqualTo(orderCost);
		assertThat(orderCostLoaded).isEqualTo(orderCost);
	}
}