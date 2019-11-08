package de.metas.invoicecandidate.externallyreferenced;

import javax.annotation.Nullable;

import de.metas.bpartner.service.BPartnerInfo;
import de.metas.document.DocTypeId;
import de.metas.lang.SOTrx;
import de.metas.order.InvoiceRule;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.product.ProductPrice;
import de.metas.quantity.StockQtyAndUOMQty;
import de.metas.util.lang.Percent;
import lombok.Data;
import lombok.NonNull;

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
@Data
public class ExternallyReferencedCandidate
{
	private OrgId orgId;

	@NonNull
	private InvoiceCandidateLookupKey lookupKey;

	@NonNull
	private BPartnerInfo billPartnerInfo;

	@NonNull
	private ProductId productId;

	@NonNull
	private InvoiceRule invoiceRuleOverride;

	@NonNull
	private SOTrx soTrx;

	@NonNull
	private StockQtyAndUOMQty qtyOrdered;

	@NonNull
	private StockQtyAndUOMQty qtyDelivered;

	/** If given, then productId has to match */
	@Nullable
	private ProductPrice priceEnteredOverride;

	@Nullable
	private Percent discountOverride;

	@Nullable
	private DocTypeId invoiceDocTypeId;

	// TODO: figure something out for tax

	public ExternallyReferencedCandidate()
	{
	}

	public boolean isNew()
	{
		return lookupKey == null || lookupKey.getInvoiceCandidateId() == null;
	}
}
