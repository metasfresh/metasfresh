package de.metas.purchasecandidate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import javax.annotation.Nullable;

import org.adempiere.bpartner.BPartnerId;
import org.adempiere.service.OrgId;
import org.adempiere.util.collections.ListUtils;
import org.adempiere.warehouse.WarehouseId;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.order.OrderAndLineId;
import de.metas.product.ProductId;
import de.metas.purchasecandidate.grossprofit.PurchaseProfitInfo;
import de.metas.quantity.Quantity;
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
	public static PurchaseCandidatesGroup of(
			@NonNull final PurchaseDemandId purchaseDemandId,
			@NonNull final PurchaseCandidate purchaseCandidate,
			@NonNull final VendorProductInfo vendorProductInfo)
	{
		final PurchaseCandidatesGroupBuilder builder = builder()
				.purchaseDemandId(purchaseDemandId)
				.candidateGroupReferences(ImmutableList.of(purchaseCandidate.getGroupReference()))
				//
				.orgId(purchaseCandidate.getOrgId())
				.warehouseId(purchaseCandidate.getWarehouseId())
				//
				.vendorProductInfo(vendorProductInfo)
				//
				.qtyToPurchase(purchaseCandidate.getQtyToPurchase())
				.purchasedQty(purchaseCandidate.getPurchasedQty())
				//
				.purchaseDatePromised(purchaseCandidate.getPurchaseDatePromised())
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
	List<DemandGroupReference> candidateGroupReferences;

	@NonNull
	PurchaseDemandId purchaseDemandId;

	@NonNull
	OrgId orgId;

	@NonNull
	WarehouseId warehouseId;

	@NonNull
	VendorProductInfo vendorProductInfo;

	@NonNull
	Quantity qtyToPurchase;
	@NonNull
	Quantity purchasedQty;

	@NonNull
	LocalDateTime purchaseDatePromised;

	@Nullable
	PurchaseProfitInfo profitInfo;

	@NonNull
	@Singular
	ImmutableSet<PurchaseCandidateId> purchaseCandidateIds;

	@NonNull
	@Singular
	ImmutableSet<OrderAndLineId> salesOrderAndLineIds;

	boolean readonly;

	public PurchaseCandidateId getSinglePurchaseCandidateIdOrNull()
	{
		return ListUtils.singleElementOrNull(getPurchaseCandidateIds());
	}

	public OrderAndLineId getSingleSalesOrderAndLineIdOrNull()
	{
		return ListUtils.singleElementOrNull(getSalesOrderAndLineIds());
	}

	public PurchaseCandidatesGroup withProfitInfo(@Nullable final PurchaseProfitInfo newProfitInfo)
	{
		if (Objects.equals(getProfitInfo(), newProfitInfo))
		{
			return this;
		}

		return toBuilder().profitInfo(newProfitInfo).build();
	}

	public BPartnerId getVendorId()
	{
		return getVendorProductInfo().getVendorId();
	}

	public String getVendorProductNo()
	{
		return getVendorProductInfo().getVendorProductNo();
	}

	public ProductId getProductId()
	{
		return getVendorProductInfo().getProductId();
	}

	public boolean isAggregatePOs()
	{
		return getVendorProductInfo().isAggregatePOs();
	}
}
