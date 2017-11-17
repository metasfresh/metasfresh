package de.metas.purchasecandidate.impl;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

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
	private final int productId;
	private final int uomId;
	private final int vendorBPartnerId;
	
	@NonNull
	private BigDecimal qtyRequired;
	@NonNull
	private Date datePromised;

	private final boolean processed;

	@Builder
	public PurchaseCandidate(
			final int repoId,
			final int salesOrderLineId,
			final int productId,
			final int uomId,
			final int vendorBPartnerId,
			@NonNull final BigDecimal qtyRequired,
			@NonNull final Date datePromised,
			final boolean processed)
	{
		this.repoId = repoId;
		this.salesOrderLineId = salesOrderLineId;
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
}
