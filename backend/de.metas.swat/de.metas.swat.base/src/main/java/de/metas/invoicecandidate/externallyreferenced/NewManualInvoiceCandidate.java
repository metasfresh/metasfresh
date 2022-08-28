package de.metas.invoicecandidate.externallyreferenced;

import de.metas.bpartner.service.BPartnerInfo;
import de.metas.document.DocTypeId;
import de.metas.invoice.detail.InvoiceDetailItem;
import de.metas.lang.SOTrx;
import de.metas.order.InvoiceRule;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.product.ProductPrice;
import de.metas.project.ProjectId;
import de.metas.quantity.StockQtyAndUOMQty;
import de.metas.uom.UomId;
import de.metas.util.lang.ExternalId;
import de.metas.util.lang.Percent;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.List;

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

/**
 * A "manual" IC is not programmatically created but imported into the system.
 */
@Value
@Builder
public class NewManualInvoiceCandidate
{
	OrgId orgId;

	ExternalId externalHeaderId;
	ExternalId externalLineId;

	String poReference;

	BPartnerInfo billPartnerInfo;

	ProductId productId;

	InvoiceRule invoiceRuleOverride;

	InvoiceRule invoiceRule;

	SOTrx soTrx;

	LocalDate dateOrdered;

	LocalDate presetDateInvoiced;

	StockQtyAndUOMQty qtyOrdered;

	StockQtyAndUOMQty qtyDelivered;

	UomId invoicingUomId;

	/**
	 * If given, then productId and currencyId have to match!
	 */
	ProductPrice priceEnteredOverride;

	Percent discountOverride;

	DocTypeId invoiceDocTypeId;

	String lineDescription;

	ProjectId projectId;

	TableRecordReference recordReference;

	List<InvoiceDetailItem> invoiceDetailItems;

	private NewManualInvoiceCandidate(
			@NonNull final OrgId orgId,

			@Nullable final ExternalId externalHeaderId,
			@Nullable final ExternalId externalLineId,

			@Nullable final String poReference,
			@NonNull final BPartnerInfo billPartnerInfo,
			@NonNull final ProductId productId,
			@Nullable final InvoiceRule invoiceRuleOverride,
			@Nullable final InvoiceRule invoiceRule,
			@NonNull final SOTrx soTrx,
			@NonNull final LocalDate dateOrdered,
			@Nullable final LocalDate presetDateInvoiced,
			@NonNull final StockQtyAndUOMQty qtyOrdered,
			@NonNull final StockQtyAndUOMQty qtyDelivered,
			@NonNull final UomId invoicingUomId,
			@Nullable final ProductPrice priceEnteredOverride,
			@Nullable final Percent discountOverride,
			@Nullable final DocTypeId invoiceDocTypeId,
			@Nullable final String lineDescription,
			@Nullable final ProjectId projectId,
			@Nullable final TableRecordReference recordReference,
			@Nullable final List<InvoiceDetailItem> invoiceDetailItems)
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
		this.projectId = projectId;
		this.recordReference = recordReference;
		this.invoiceRule = invoiceRule;
		this.invoiceDetailItems = invoiceDetailItems;

		if (priceEnteredOverride != null)
		{
			if (!priceEnteredOverride.getProductId().equals(productId))
			{
				throw new AdempiereException("priceEnteredOverride.productId={} is inconsistant with this instance's productId={}")
						.appendParametersToMessage()
						.setParameter("newManualInvoiceCandidate", this);
			}
			if (!priceEnteredOverride.getUomId().equals(invoicingUomId))
			{
				throw new AdempiereException("priceEnteredOverride.uomId={} is inconsistant with this instance's invoicingUomId={}")
						.appendParametersToMessage()
						.setParameter("newManualInvoiceCandidate", this);
			}
		}
	}

}
