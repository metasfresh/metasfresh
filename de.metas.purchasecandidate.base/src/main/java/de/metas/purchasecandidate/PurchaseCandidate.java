package de.metas.purchasecandidate;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

import org.adempiere.util.Check;

import de.metas.vendor.gateway.api.ProductAndQuantity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Delegate;

/*
 * #%L
 * de.metas.purchasecandidate.base
 * %%
 * Copyright (C) 2017 metas GmbH
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

@Data
public class PurchaseCandidate
{
	@Setter(AccessLevel.NONE)
	private int purchaseCandidateId;

	@Setter(AccessLevel.NONE)
	private int purchaseOrderLineId;
	@Setter(AccessLevel.NONE)
	private int purchaseOrderLineIdInitial;

	private BigDecimal qtyRequired;
	@Setter(AccessLevel.NONE)
	private BigDecimal qtyRequiredInitial;

	private Date datePromised;

	@Setter(AccessLevel.NONE)
	private Date datePromisedInitial;

	@Delegate
	private final PurchaseCandidateImmutableFields identifier;

	private final PurchaseCandidateState state;

	@Builder
	private PurchaseCandidate(
			final int purchaseCandidateId,
			final int salesOrderId,
			final int salesOrderLineId,
			final int purchaseOrderLineId,
			final int orgId,
			final int warehouseId,
			final int productId,
			final int uomId,
			final int vendorBPartnerId,
			@NonNull final VendorProductInfo vendorProductInfo,
			@NonNull final BigDecimal qtyRequired,
			@NonNull final Date datePromised,
			final boolean processed,
			final boolean locked)
	{
		Check.assume(salesOrderId > 0, "salesOrderId > 0"); // for now this shall be always set; might be that in future this won't be mandatory
		Check.assume(salesOrderLineId > 0, "salesOrderLineId > 0"); // for now this shall be always set; might be that in future this won't be mandatory
		Check.assume(orgId > 0, "orgId > 0");
		Check.assume(warehouseId > 0, "warehouseId > 0");
		Check.assume(productId > 0, "productId > 0");
		Check.assume(uomId > 0, "uomId > 0");
		Check.assume(vendorBPartnerId > 0, "vendorBPartnerId > 0");

		this.purchaseCandidateId = purchaseCandidateId;

		this.identifier = PurchaseCandidateImmutableFields.builder()
				.orgId(orgId)
				.productId(productId)
				.salesOrderId(salesOrderId)
				.salesOrderLineId(salesOrderLineId)
				.uomId(uomId)
				.vendorBPartnerId(vendorBPartnerId)
				.vendorProductInfo(vendorProductInfo)
				.warehouseId(warehouseId).build();

		this.state = PurchaseCandidateState.builder()
				.locked(locked)
				.processed(processed).build();

		this.purchaseOrderLineId = purchaseOrderLineId > 0 ? purchaseOrderLineId : 0;
		this.purchaseOrderLineIdInitial = this.purchaseOrderLineId;

		this.qtyRequired = qtyRequired;
		this.qtyRequiredInitial = qtyRequired;
		this.datePromised = datePromised;
		this.datePromisedInitial = datePromised;
	}

	private PurchaseCandidate(@NonNull final PurchaseCandidate from)
	{
		purchaseCandidateId = from.purchaseCandidateId;

		purchaseOrderLineId = from.purchaseOrderLineId;
		purchaseOrderLineIdInitial = from.purchaseOrderLineIdInitial;

		qtyRequired = from.qtyRequired;
		qtyRequiredInitial = from.qtyRequiredInitial;
		datePromised = from.datePromised;
		datePromisedInitial = from.datePromisedInitial;

		identifier = from.identifier;
		state = from.state.createCopy();
	}

	public PurchaseCandidate copy()
	{
		return new PurchaseCandidate(this);
	}

	public void setPurchaseOrderLineIdAndMarkProcessed(final int purchaseOrderLineId)
	{
		Check.assume(!state.isProcessed(), "already processed: {}", this);
		Check.assume(purchaseOrderLineId > 0, "purchaseOrderLineId > 0");

		state.setProcessed();
		this.purchaseOrderLineId = purchaseOrderLineId;
	}

	public boolean isProcessedOrLocked()
	{
		return state.isProcessed() || state.isLocked();
	}

	public boolean isProcessed()
	{
		return state.isProcessed();
	}

	public boolean hasChanges()
	{
		return purchaseCandidateId <= 0 // never saved
				|| state.hasChanges()
				|| purchaseOrderLineId != purchaseOrderLineIdInitial
				|| qtyRequired.compareTo(qtyRequiredInitial) != 0
				|| !Objects.equals(datePromised, datePromisedInitial);
	}

	public void markSaved(final int C_PurchaseCandidate_ID)
	{
		this.purchaseCandidateId = C_PurchaseCandidate_ID;

		state.markSaved();

		purchaseOrderLineIdInitial = purchaseOrderLineId;
		qtyRequiredInitial = qtyRequired;
		datePromisedInitial = datePromised;
	}

	public ProductAndQuantity createRequestItem()
	{
		final String productValue = identifier.getVendorProductInfo().getProductNo();
		final BigDecimal qtyRequired = getQtyRequired();

		return new ProductAndQuantity(productValue, qtyRequired);
	}
}
