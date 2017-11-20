package de.metas.purchasecandidate;

import java.math.BigDecimal;
import java.util.Date;

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

@Builder
@Data
public class PurchaseCandidate
{
	private int repoId;

	private final int salesOrderLineId;
	@Setter(AccessLevel.NONE)
	private int purchaseOrderLineId;

	private final int orgId;
	private final int warehouseId;
	private final int productId;
	private final int uomId;
	private final int vendorBPartnerId;

	@NonNull
	private BigDecimal qtyRequired;
	@NonNull
	private Date datePromised;

	@Setter(AccessLevel.NONE)
	private boolean processed;

	@Builder
	public PurchaseCandidate(
			final int repoId,
			final int salesOrderLineId,
			final int purchaseOrderLineId,
			final int orgId,
			final int warehouseId,
			final int productId,
			final int uomId,
			final int vendorBPartnerId,
			@NonNull final BigDecimal qtyRequired,
			@NonNull final Date datePromised,
			final boolean processed)
	{
		Check.assume(salesOrderLineId > 0, "salesOrderLineId > 0"); // for now this shall be always set; might be that in future this won't be mandatory
		Check.assume(orgId > 0, "orgId > 0");
		Check.assume(warehouseId > 0, "warehouseId > 0");
		Check.assume(productId > 0, "productId > 0");
		Check.assume(uomId > 0, "uomId > 0");
		Check.assume(vendorBPartnerId > 0, "vendorBPartnerId > 0");

		this.repoId = repoId;

		this.salesOrderLineId = salesOrderLineId;
		this.purchaseOrderLineId = purchaseOrderLineId > 0 ? purchaseOrderLineId : 0;

		this.orgId = orgId;
		this.warehouseId = warehouseId;
		this.productId = productId;
		this.uomId = uomId;
		this.vendorBPartnerId = vendorBPartnerId;
		this.qtyRequired = qtyRequired;
		this.datePromised = datePromised;
		this.processed = processed;
	}

	private PurchaseCandidate(final PurchaseCandidate from)
	{
		repoId = from.repoId;
		salesOrderLineId = from.salesOrderLineId;
		orgId = from.orgId;
		warehouseId = from.warehouseId;
		productId = from.productId;
		uomId = from.uomId;
		vendorBPartnerId = from.vendorBPartnerId;
		qtyRequired = from.qtyRequired;
		datePromised = from.datePromised;
		processed = from.processed;
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
}
