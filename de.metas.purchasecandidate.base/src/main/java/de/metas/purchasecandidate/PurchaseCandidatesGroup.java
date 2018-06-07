package de.metas.purchasecandidate;

import java.math.BigDecimal;
import java.util.Objects;

import javax.annotation.Nullable;

import org.adempiere.bpartner.BPartnerId;
import org.adempiere.service.OrgId;
import org.adempiere.util.collections.ListUtils;
import org.adempiere.warehouse.WarehouseId;

import com.google.common.collect.ImmutableSet;

import de.metas.order.OrderAndLineId;
import de.metas.product.ProductId;
import de.metas.purchasecandidate.grossprofit.PurchaseProfitInfo;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

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

@Value
@Builder(toBuilder = true)
public class PurchaseCandidatesGroup
{
	public static PurchaseCandidatesGroup of(final PurchaseCandidate purchaseCandidate)
	{
		final PurchaseCandidatesGroupBuilder builder = builder()
				.orgId(purchaseCandidate.getOrgId())
				.warehouseId(purchaseCandidate.getWarehouseId())
				//
				.vendorId(purchaseCandidate.getVendorId())
				//
				.productId(purchaseCandidate.getProductId())
				.vendorProductNo(purchaseCandidate.getVendorProductNo())
				.vendorProductName("") // TODO
				//
				.qtyToPurchase(purchaseCandidate.getQtyToPurchase())
				.purchasedQty(purchaseCandidate.getPurchasedQty())
				//
				.profitInfo(purchaseCandidate.getProfitInfo())
				//
				.readonly(purchaseCandidate.isProcessedOrLocked());

		if (purchaseCandidate.getId() != null)
		{
			builder.purchaseCandidateId(purchaseCandidate.getId());
		}
		if (purchaseCandidate.getSalesOrderAndLineId() != null)
		{
			builder.salesOrderAndLineId(purchaseCandidate.getSalesOrderAndLineId());
		}

		return builder.build();
	}

	@NonNull
	OrgId orgId;
	@NonNull
	WarehouseId warehouseId;

	@NonNull
	BPartnerId vendorId;

	@NonNull
	ProductId productId;
	String vendorProductNo;
	String vendorProductName;

	@NonNull
	BigDecimal qtyToPurchase;
	@NonNull
	BigDecimal purchasedQty;

	@Nullable
	PurchaseProfitInfo profitInfo;

	// purchaseCandidate.isProcessedOrLocked()
	boolean readonly;

	@NonNull
	@Singular
	ImmutableSet<PurchaseCandidateId> purchaseCandidateIds;

	@NonNull
	@Singular
	ImmutableSet<OrderAndLineId> salesOrderAndLineIds;

	public PurchaseCandidateId getSinglePurchaseCandidateIdOrNull()
	{
		return ListUtils.singleElementOrNull(getPurchaseCandidateIds());
	}

	public OrderAndLineId getSingleSalesOrderAndLineId()
	{
		return ListUtils.singleElementOrNull(getSalesOrderAndLineIds());
	}

	public PurchaseCandidatesGroup changeProfitInfo(@Nullable final PurchaseProfitInfo newProfitInfo)
	{
		if (Objects.equals(getProfitInfo(), newProfitInfo))
		{
			return this;
		}

		return toBuilder().profitInfo(newProfitInfo).build();
	}
}
