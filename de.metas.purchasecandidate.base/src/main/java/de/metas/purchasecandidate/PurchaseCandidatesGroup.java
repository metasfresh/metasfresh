package de.metas.purchasecandidate;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;

import javax.annotation.Nullable;

import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.warehouse.WarehouseId;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.bpartner.BPartnerId;
import de.metas.order.OrderAndLineId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.purchasecandidate.grossprofit.PurchaseProfitInfo;
import de.metas.quantity.Quantity;
import de.metas.util.Check;
import de.metas.util.collections.CollectionUtils;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
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
	/** Creates a group that consists of a single purchaseCandidate. */
	public static PurchaseCandidatesGroup of(
			@NonNull final PurchaseDemandId purchaseDemandId,
			@NonNull final PurchaseCandidate purchaseCandidate,
			@NonNull final VendorProductInfo vendorProductInfo)
	{
		Check.errorUnless(
				Objects.equals(purchaseCandidate.getProductId(), vendorProductInfo.getProductId()),
				"The given purchaseCandidate and vendorProductInfo have different productIds; purchaseCandidate={}; vendorProductInfo={}",
				purchaseCandidate, vendorProductInfo);

		Check.errorUnless(
				Objects.equals(vendorProductInfo.getAttributeSetInstanceId(), AttributeSetInstanceId.NONE) // if vendorProductInfo has no ASI, then it's also fine.
						|| Objects.equals(purchaseCandidate.getAttributeSetInstanceId(), vendorProductInfo.getAttributeSetInstanceId()),
				"The given purchaseCandidate and vendorProductInfo have different attributeSetInstanceIds; purchaseCandidate={}; vendorProductInfo={}",
				purchaseCandidate, vendorProductInfo);

		final PurchaseCandidatesGroupBuilder builder = builder()
				.purchaseDemandId(purchaseDemandId)
				.demandGroupReferences(ImmutableList.of(purchaseCandidate.getGroupReference()))
				//
				.orgId(purchaseCandidate.getOrgId())
				.warehouseId(purchaseCandidate.getWarehouseId())
				//
				.vendorProductInfo(vendorProductInfo)
				.attributeSetInstanceId(purchaseCandidate.getAttributeSetInstanceId())
				//
				.qtyToPurchase(purchaseCandidate.getQtyToPurchase())
				.purchasedQty(purchaseCandidate.getPurchasedQty())
				//
				.purchaseDatePromised(purchaseCandidate.getPurchaseDatePromised())
				.reminderTime(purchaseCandidate.getReminderTime())
				//
				.profitInfoOrNull(purchaseCandidate.getProfitInfoOrNull())
				//
				.readonly(purchaseCandidate.isProcessedOrLocked());

		if (purchaseCandidate.getId() != null)
		{
			builder.purchaseCandidateId(purchaseCandidate.getId());
		}
		if (purchaseCandidate.getSalesOrderAndLineIdOrNull() != null)
		{
			builder.salesOrderAndLineId(purchaseCandidate.getSalesOrderAndLineIdOrNull());
		}

		return builder.build();
	}

	/** they are needed because when a new purchase candidate is "split" from this group, it needs to inherit a reference. */
	@NonNull
	List<DemandGroupReference> demandGroupReferences;

	@NonNull
	PurchaseDemandId purchaseDemandId;

	@NonNull
	OrgId orgId;

	@NonNull
	WarehouseId warehouseId;

	@NonNull
	VendorProductInfo vendorProductInfo;

	/** note that the ASI-ID of {@link #vendorProductInfo} might be "none" */
	@NonNull
	AttributeSetInstanceId attributeSetInstanceId;

	/** quantity the shall be ordered; initially often zero, can be set to >0 be the user */
	@NonNull
	Quantity qtyToPurchase;

	/** quantity where we know that is was already ordered in purchase order lines */
	@NonNull
	Quantity purchasedQty;

	@NonNull
	ZonedDateTime purchaseDatePromised;
	@Nullable
	Duration reminderTime;

	@Nullable
	PurchaseProfitInfo profitInfoOrNull;

	@NonNull
	@Singular
	ImmutableSet<PurchaseCandidateId> purchaseCandidateIds;

	@NonNull
	@Singular
	ImmutableSet<OrderAndLineId> salesOrderAndLineIds;

	boolean readonly;

	@Getter(AccessLevel.PACKAGE)
	@Default
	@VisibleForTesting
	boolean allowPOAggregation = true;

	public PurchaseCandidateId getSinglePurchaseCandidateIdOrNull()
	{
		return CollectionUtils.singleElementOrNull(getPurchaseCandidateIds());
	}

	public OrderAndLineId getSingleSalesOrderAndLineIdOrNull()
	{
		return CollectionUtils.singleElementOrNull(getSalesOrderAndLineIds());
	}

	public PurchaseCandidatesGroup withProfitInfo(@Nullable final PurchaseProfitInfo newProfitInfo)
	{
		if (Objects.equals(getProfitInfoOrNull(), newProfitInfo))
		{
			return this;
		}
		return toBuilder().profitInfoOrNull(newProfitInfo).build();
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

	public AttributeSetInstanceId getAttributeSetInstanceId()
	{
		return attributeSetInstanceId;
	}

	public boolean isAggregatePOs()
	{
		if (!isAllowPOAggregation())
		{
			return false;
		}

		return getVendorProductInfo().isAggregatePOs();
	}

	public PurchaseCandidatesGroup allowingPOAggregation(final boolean allowPOAggregation)
	{
		if (this.isAllowPOAggregation() == allowPOAggregation)
		{
			return this;
		}

		return toBuilder().allowPOAggregation(allowPOAggregation).build();
	}
}
