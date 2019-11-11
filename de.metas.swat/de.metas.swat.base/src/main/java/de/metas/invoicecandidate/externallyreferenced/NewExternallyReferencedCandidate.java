package de.metas.invoicecandidate.externallyreferenced;

import java.time.LocalDate;

import javax.annotation.Nullable;

import de.metas.bpartner.service.BPartnerInfo;
import de.metas.document.DocTypeId;
import de.metas.lang.SOTrx;
import de.metas.order.InvoiceRule;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.product.ProductPrice;
import de.metas.quantity.StockQtyAndUOMQty;
import de.metas.uom.UomId;
import de.metas.util.lang.ExternalId;
import de.metas.util.lang.Percent;
import lombok.Builder;
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
public class NewExternallyReferencedCandidate
{
	private OrgId orgId;

	private ExternalId externalHeaderId;
	private ExternalId externalLineId;

	@NonNull
	private String poReference;

	@NonNull
	private BPartnerInfo billPartnerInfo;

	@NonNull
	private ProductId productId;

	@NonNull
	private InvoiceRule invoiceRuleOverride;

	@NonNull
	private SOTrx soTrx;

	private LocalDate dateOrdered;

	private LocalDate presetDateInvoiced;

	@NonNull
	private StockQtyAndUOMQty qtyOrdered;

	@NonNull
	private StockQtyAndUOMQty qtyDelivered;

	private UomId invoicingUomId;

	/** If given, then productId and currencyId have to match! */
	@Nullable
	private ProductPrice priceEnteredOverride;

	@Nullable
	private Percent discountOverride;

	@Nullable
	private DocTypeId invoiceDocTypeId;

	private String lineDescription;

	private NewExternallyReferencedCandidate(
			@NonNull final OrgId orgId,

			@NonNull final ExternalId externalHeaderId,
			@NonNull final ExternalId externalLineId,

			String poReference,
			@NonNull final BPartnerInfo billPartnerInfo,
			@NonNull final ProductId productId,
			InvoiceRule invoiceRuleOverride,
			@NonNull final SOTrx soTrx,
			@NonNull final LocalDate dateOrdered,
			LocalDate presetDateInvoiced,
			@NonNull final StockQtyAndUOMQty qtyOrdered,
			@NonNull final StockQtyAndUOMQty qtyDelivered,
			@NonNull final UomId invoicingUomId,
			ProductPrice priceEnteredOverride,
			Percent discountOverride,
			DocTypeId invoiceDocTypeId,
			String lineDescription)
	{
		this.orgId = orgId;

		this.externalLineId = externalLineId;
		this.externalHeaderId = externalHeaderId;

		this.poReference = poReference;
		this.billPartnerInfo = billPartnerInfo;
		this.productId = productId;
		this.invoiceRuleOverride = invoiceRuleOverride;
		this.soTrx = soTrx;
		this.dateOrdered = dateOrdered;
		this.presetDateInvoiced = presetDateInvoiced;
		this.qtyOrdered = qtyOrdered;
		this.qtyDelivered = qtyDelivered;
		this.invoicingUomId = invoicingUomId;
		this.priceEnteredOverride = priceEnteredOverride;
		this.discountOverride = discountOverride;
		this.invoiceDocTypeId = invoiceDocTypeId;
		this.lineDescription = lineDescription;

		if (priceEnteredOverride != null)
		{
			if (!priceEnteredOverride.getProductId().equals(productId))
			{
				// TODO fail
			}
			if (!priceEnteredOverride.getUomId().equals(invoicingUomId))
			{
				// TODO fail
			}
		}
	}

}
