package de.metas.invoicecandidate.externallyreferenced;

import javax.annotation.Nullable;

import de.metas.bpartner.service.BPartnerInfo;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.lang.SOTrx;
import de.metas.order.InvoiceRule;
import de.metas.product.ProductId;
import de.metas.product.ProductPrice;
import de.metas.quantity.StockQtyAndUOMQty;
import de.metas.util.lang.Percent;
import de.metas.util.rest.ExternalId;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2019 metas GmbH
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

/** A "manual" IC does not reference another record (e.g. order line or contract). */
@Value
@Builder
public class ExternallyReferencedCandidate
{
	@Nullable
	InvoiceCandidateId id;

	@NonNull
	BPartnerInfo billPartnerInfo;

	@NonNull
	ExternalId externalHeaderId;

	@NonNull
	ExternalId externalLineId;

	@NonNull
	ProductId productId;

	@NonNull
	@Default
	InvoiceRule invoiceRule = InvoiceRule.Immediate;

	@NonNull
	SOTrx soTrx;

	@NonNull
	StockQtyAndUOMQty qtyOrdered;

	@NonNull
	StockQtyAndUOMQty qtyDelivered;

	/** If given, then productId has to match */
	@Nullable
	ProductPrice priceEnteredOverride;

	@Nullable
	Percent discountOverride;

	// TODO: figure something out for tax
}
