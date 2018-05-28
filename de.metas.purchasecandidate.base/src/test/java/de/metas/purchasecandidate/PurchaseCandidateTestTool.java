package de.metas.purchasecandidate;

import static java.math.BigDecimal.TEN;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;

import org.adempiere.bpartner.BPartnerId;
import org.adempiere.util.time.SystemTime;

import de.metas.money.Currency;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.order.OrderLineId;
import de.metas.pricing.PriceListVersionId;
import de.metas.product.ProductId;
import de.metas.purchasecandidate.grossprofit.PurchaseProfitInfo;

/*
 * #%L
 * de.metas.purchasecandidate.base
 * %%
 * Copyright (C) 2018 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public final class PurchaseCandidateTestTool
{
	public static final OrderLineId SALES_ORDER_LINE_ID = OrderLineId.ofRepoId(2);

	public static final Currency CURRENCY = Currency.builder()
			.id(CurrencyId.ofRepoId(40))
			.precision(20)
			.build();

	private PurchaseCandidateTestTool()
	{
	}

	public static PurchaseCandidate createPurchaseCandidate(final int purchaseCandidateId)
	{
		return PurchaseCandidate.builder()
				.purchaseCandidateId(purchaseCandidateId)
				.salesOrderId(1)
				.salesOrderLineId(SALES_ORDER_LINE_ID)
				.orgId(3)
				.warehouseId(4)
				.productId(ProductId.ofRepoId(5))
				.uomId(6)
				.profitInfo(createPurchaseProfitInfo())
				.vendorProductInfo(VendorProductInfo.builder()
						.bpartnerProductId(10)
						.vendorBPartnerId(BPartnerId.ofRepoId(7))
						.productId(ProductId.ofRepoId(20))
						.productNo("productNo")
						.productName("productName")
						.build())
				.qtyToPurchase(BigDecimal.ONE)
				.dateRequired(SystemTime.asLocalDateTime().truncatedTo(ChronoUnit.DAYS))
				.processed(false)
				.locked(false)
				.build();
	}

	public static PurchaseProfitInfo createPurchaseProfitInfo()
	{
		return PurchaseProfitInfo.builder()
				.purchasePlvId(PriceListVersionId.ofRepoId(10))
				.customerPriceGrossProfit(Money.of(TEN, CURRENCY))
				.purchasePriceActual(Money.of(TEN, CURRENCY))
				.priceGrossProfit(Money.of(TEN, CURRENCY))
				.build();
	}
}
