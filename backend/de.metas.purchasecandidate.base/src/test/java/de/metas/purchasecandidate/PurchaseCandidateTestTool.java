package de.metas.purchasecandidate;

import java.time.temporal.ChronoUnit;

import de.metas.common.util.time.SystemTime;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.warehouse.WarehouseId;

import de.metas.bpartner.BPartnerId;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.order.OrderAndLineId;
import de.metas.order.OrderId;
import de.metas.order.OrderLineId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.purchasecandidate.grossprofit.PurchaseProfitInfo;
import de.metas.quantity.Quantity;

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

	public static final CurrencyId CURRENCY_ID = CurrencyId.ofRepoId(40);

	private PurchaseCandidateTestTool()
	{
	}

	public static PurchaseCandidate createPurchaseCandidate(final int purchaseCandidateId, final Quantity qtyToPurchase)
	{
		final ProductId productId = ProductId.ofRepoId(5);
		final AttributeSetInstanceId attributeSetInstanceId = AttributeSetInstanceId.ofRepoId(6);

		return PurchaseCandidate.builder()
				.id(PurchaseCandidateId.ofRepoIdOrNull(purchaseCandidateId))
				.groupReference(DemandGroupReference.EMPTY)
				.salesOrderAndLineIdOrNull(OrderAndLineId.of(OrderId.ofRepoId(1), SALES_ORDER_LINE_ID))
				.orgId(OrgId.ofRepoId(3))
				.warehouseId(WarehouseId.ofRepoId(4))
				.productId(productId)
				.attributeSetInstanceId(attributeSetInstanceId)
				.vendorProductNo(String.valueOf(productId.getRepoId()))
				.profitInfoOrNull(createPurchaseProfitInfo())
				.vendorId(BPartnerId.ofRepoId(7))
				.qtyToPurchase(qtyToPurchase)
				.purchaseDatePromised(SystemTime.asZonedDateTime().truncatedTo(ChronoUnit.DAYS))
				.processed(false)
				.locked(false)
				.build();
	}

	public static PurchaseProfitInfo createPurchaseProfitInfo()
	{
		return PurchaseProfitInfo.builder()
				.profitSalesPriceActual(Money.of(10, CURRENCY_ID))
				.profitPurchasePriceActual(Money.of(10, CURRENCY_ID))
				.purchasePriceActual(Money.of(10, CURRENCY_ID))
				.build();
	}
}
