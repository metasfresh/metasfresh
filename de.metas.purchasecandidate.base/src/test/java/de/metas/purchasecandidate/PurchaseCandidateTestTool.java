package de.metas.purchasecandidate;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;

import org.adempiere.bpartner.BPartnerId;
import org.adempiere.util.time.SystemTime;

import de.metas.product.ProductId;

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
	public static final int SALES_ORDER_LINE_ID = 2;

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
				.productId(5)
				.uomId(6)
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

}
