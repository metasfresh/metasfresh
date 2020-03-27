package de.metas.contracts.commission.commissioninstance.businesslogic.sales.commissiontrigger.salesinvoiceline;

import java.time.Instant;
import java.time.LocalDate;

import org.compiere.util.TimeUtil;

import com.google.common.collect.ImmutableList;

import de.metas.bpartner.BPartnerId;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.commissiontrigger.CommissionTriggerDocument;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.commissiontrigger.CommissionTriggerDocument.CommissionTriggerDocumentBuilder;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.commissiontrigger.CommissionTriggerType;
import de.metas.invoice.InvoiceId;
import de.metas.organization.OrgId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

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
@Builder
public class SalesInvoice
{
	@NonNull
	OrgId orgId;

	@NonNull
	InvoiceId id;

	@NonNull
	BPartnerId salesRepBPartnerId;

	@NonNull
	BPartnerId customerBPartnerId;

	@NonNull
	LocalDate commissionDate;

	@NonNull
	Instant updated;

	@NonNull
	CommissionTriggerType triggerType;

	@Singular
	ImmutableList<SalesInvoiceLine> invoiceLines;

	public ImmutableList<CommissionTriggerDocument> asCommissionTriggerDocuments()
	{
		final CommissionTriggerDocumentBuilder document = CommissionTriggerDocument.builder()
				.triggerType(triggerType)
				.orgId(orgId)
				.salesRepBPartnerId(salesRepBPartnerId)
				.customerBPartnerId(customerBPartnerId)
				.commissionDate(commissionDate);

		final ImmutableList.Builder<CommissionTriggerDocument> result = ImmutableList.builder();
		for (final SalesInvoiceLine invoiceLine : invoiceLines)
		{
			document
					.id(new SalesInvoiceLineDocumentId(invoiceLine.getId()))
					.forecastCommissionPoints(invoiceLine.getForecastCommissionPoints())
					.commissionPointsToInvoice(invoiceLine.getCommissionPointsToInvoice())
					.invoicedCommissionPoints(invoiceLine.getInvoicedCommissionPoints())
					.tradedCommissionPercent(invoiceLine.getTradedCommissionPercent())
					.productId(invoiceLine.getProductId())
					.updated(TimeUtil.max(updated, invoiceLine.getUpdated()));
			result.add(document.build());
		}

		return result.build();
	}
}
