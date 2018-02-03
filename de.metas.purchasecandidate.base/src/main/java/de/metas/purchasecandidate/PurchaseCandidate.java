package de.metas.purchasecandidate;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

import org.adempiere.util.Check;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.Setter;

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

	private final int salesOrderId;
	private final int salesOrderLineId;

	@Setter(AccessLevel.NONE)
	private int purchaseOrderLineId;
	@Setter(AccessLevel.NONE)
	private int purchaseOrderLineIdInitial;

	private final int orgId;
	private final int warehouseId;
	private final int productId;
	private final int uomId;
	private final int vendorBPartnerId;
	private final VendorProductInfo vendorProductInfo;

	private BigDecimal qtyRequired;
	@Setter(AccessLevel.NONE)
	private BigDecimal qtyRequiredInitial;

	private Date datePromised;

	@Setter(AccessLevel.NONE)
	private Date datePromisedInitial;

	@Setter(AccessLevel.NONE)
	private boolean processed;

	@Setter(AccessLevel.NONE)
	private boolean processedInitial;


	private final boolean locked;

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

		this.salesOrderId = salesOrderId;
		this.salesOrderLineId = salesOrderLineId;
		this.purchaseOrderLineId = purchaseOrderLineId > 0 ? purchaseOrderLineId : 0;
		this.purchaseOrderLineIdInitial = this.purchaseOrderLineId;

		this.orgId = orgId;
		this.warehouseId = warehouseId;
		this.productId = productId;
		this.uomId = uomId;
		this.vendorBPartnerId = vendorBPartnerId;
		this.vendorProductInfo = vendorProductInfo;
		this.qtyRequired = qtyRequired;
		this.qtyRequiredInitial = qtyRequired;
		this.datePromised = datePromised;
		this.datePromisedInitial = datePromised;
		this.processed = processed;
		this.processedInitial = processed;
		this.locked = locked;
	}

	private PurchaseCandidate(@NonNull final PurchaseCandidate from)
	{
		purchaseCandidateId = from.purchaseCandidateId;
		salesOrderId = from.salesOrderId;
		salesOrderLineId = from.salesOrderLineId;
		purchaseOrderLineId = from.purchaseOrderLineId;
		purchaseOrderLineIdInitial = from.purchaseOrderLineIdInitial;

		orgId = from.orgId;
		warehouseId = from.warehouseId;
		productId = from.productId;
		uomId = from.uomId;
		vendorBPartnerId = from.vendorBPartnerId;
		vendorProductInfo = from.vendorProductInfo;

		qtyRequired = from.qtyRequired;
		qtyRequiredInitial = from.qtyRequiredInitial;
		datePromised = from.datePromised;
		datePromisedInitial = from.datePromisedInitial;

		processed = from.processed;
		processedInitial = from.processedInitial;

		locked = from.locked;
	}

	public PurchaseCandidate copy()
	{
		return new PurchaseCandidate(this);
	}

	public void setPurchaseOrderLineIdAndMarkProcessed(final int purchaseOrderLineId)
	{
		Check.assume(!processed, "already processed: {}", this);
		Check.assume(purchaseOrderLineId > 0, "purchaseOrderLineId > 0");

		this.processed = true;
		this.purchaseOrderLineId = purchaseOrderLineId;
	}

	public boolean isProcessedOrLocked()
	{
		return isProcessed() || isLocked();
	}

	public boolean hasChanges()
	{
		return purchaseCandidateId <= 0 // never saved
				|| processed != processedInitial
				|| purchaseOrderLineId != purchaseOrderLineIdInitial
				|| qtyRequired.compareTo(qtyRequiredInitial) != 0
				|| !Objects.equals(datePromised, datePromisedInitial);
	}

	public void markSaved(final int C_PurchaseCandidate_ID)
	{
		this.purchaseCandidateId = C_PurchaseCandidate_ID;

		processedInitial = processed;
		purchaseOrderLineIdInitial = purchaseOrderLineId;
		qtyRequiredInitial = qtyRequired;
		datePromisedInitial = datePromised;
	}
}
