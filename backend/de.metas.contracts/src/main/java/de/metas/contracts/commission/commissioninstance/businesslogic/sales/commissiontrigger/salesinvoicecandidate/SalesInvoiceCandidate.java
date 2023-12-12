package de.metas.contracts.commission.commissioninstance.businesslogic.sales.commissiontrigger.salesinvoicecandidate;

import de.metas.bpartner.BPartnerId;
import de.metas.contracts.commission.commissioninstance.businesslogic.CommissionPoints;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.commissiontrigger.CommissionTriggerDocument;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.commissiontrigger.CommissionTriggerType;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.money.CurrencyId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.time.Instant;
import java.time.LocalDate;

/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2020 metas GmbH
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
public class SalesInvoiceCandidate
{
	OrgId orgId;

	InvoiceCandidateId id;

	BPartnerId salesRepBPartnerId;

	BPartnerId customerBPartnerId;

	ProductId productId;

	LocalDate commissionDate;

	Instant updated;

	CommissionPoints forecastCommissionPoints;

	CommissionPoints commissionPointsToInvoice;

	CommissionPoints invoicedCommissionPoints;

	Quantity totalQtyInvolved;

	CurrencyId currencyId;

	@Builder
	private SalesInvoiceCandidate(
			@NonNull final OrgId orgId,
			@NonNull final InvoiceCandidateId id,
			@NonNull final BPartnerId salesRepBPartnerId,
			@NonNull final BPartnerId customerBPartnerId,
			@NonNull final ProductId productId,
			@NonNull final LocalDate commissionDate,
			@NonNull final Instant updated,
			@NonNull final CommissionPoints forecastCommissionPoints,
			@NonNull final CommissionPoints commissionPointsToInvoice,
			@NonNull final CommissionPoints invoicedCommissionPoints,
			@NonNull final Quantity totalQtyInvolved,
			@NonNull final CurrencyId currencyId)
	{
		this.orgId = orgId;
		this.id = id;
		this.salesRepBPartnerId = salesRepBPartnerId;
		this.customerBPartnerId = customerBPartnerId;
		this.productId = productId;
		this.commissionDate = commissionDate;
		this.updated = updated;

		this.invoicedCommissionPoints = invoicedCommissionPoints;
		this.commissionPointsToInvoice = commissionPointsToInvoice;
		this.forecastCommissionPoints = forecastCommissionPoints;
		this.totalQtyInvolved = totalQtyInvolved;
		this.currencyId = currencyId;
	}

	public CommissionTriggerDocument asCommissionTriggerDocument()
	{
		return CommissionTriggerDocument.builder()
				.triggerType(CommissionTriggerType.InvoiceCandidate)
				.id(new SalesInvoiceCandidateDocumentId(id))
				.orgId(orgId)
				.salesRepBPartnerId(salesRepBPartnerId)
				.customerBPartnerId(customerBPartnerId)
				.commissionDate(commissionDate)
				.updated(updated)
				.forecastCommissionPoints(forecastCommissionPoints)
				.commissionPointsToInvoice(commissionPointsToInvoice)
				.invoicedCommissionPoints(invoicedCommissionPoints)
				.productId(productId)
				.totalQtyInvolved(totalQtyInvolved)
				.documentCurrencyId(currencyId)
				.build();
	}
}
