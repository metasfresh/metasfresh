/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.invoice.interceptor;

import de.metas.adempiere.model.I_C_Invoice;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.review.InvoiceReviewCreateUpdateRequest;
import de.metas.invoice.service.impl.InvoiceReviewRepository;
import de.metas.organization.ClientAndOrgId;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.service.ClientId;
import org.adempiere.service.ISysConfigBL;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Interceptor(I_C_Invoice.class)
@Component
public class C_Invoice_InvoiceReview
{
	public static final String CFG_INVOICE_REVIEW_AUTO_CREATE_FOR_SALES_INVOICE = "de.metas.invoice.review.AutoCreateForSalesInvoice";
	private final InvoiceReviewRepository invoiceReviewRepository;
	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

	public C_Invoice_InvoiceReview(@NonNull final InvoiceReviewRepository invoiceReviewRepository)
	{
		this.invoiceReviewRepository = invoiceReviewRepository;
	}

	@DocValidate(timings = { ModelValidator.TIMING_AFTER_COMPLETE })
	public void createReviewRecord(final I_C_Invoice invoice)
	{
		final OrgId orgId = OrgId.ofRepoId(invoice.getAD_Org_ID());
		final ClientAndOrgId clientAndOrgId = ClientAndOrgId.ofClientAndOrg(
				ClientId.ofRepoId(invoice.getAD_Client_ID()),
				orgId);
		if(invoice.isSOTrx())
		{
			final boolean autoCreateForSalesInvoice = sysConfigBL.getBooleanValue(CFG_INVOICE_REVIEW_AUTO_CREATE_FOR_SALES_INVOICE, false, clientAndOrgId);
			if (autoCreateForSalesInvoice)
			{
				final InvoiceReviewCreateUpdateRequest request = InvoiceReviewCreateUpdateRequest.builder()
						.invoiceId(InvoiceId.ofRepoId(invoice.getC_Invoice_ID()))
						.orgId(orgId)
						.build();
				invoiceReviewRepository.createOrUpdateByInvoiceId(request);
			}
		}
	}

	@DocValidate(timings = { ModelValidator.TIMING_AFTER_REVERSECORRECT, ModelValidator.TIMING_AFTER_REVERSEACCRUAL, ModelValidator.TIMING_AFTER_VOID, ModelValidator.TIMING_AFTER_REACTIVATE})
	public void deleteReviewRecord(final I_C_Invoice invoice)
	{
		invoiceReviewRepository.delete(InvoiceId.ofRepoId(invoice.getC_Invoice_ID()));
	}
}
