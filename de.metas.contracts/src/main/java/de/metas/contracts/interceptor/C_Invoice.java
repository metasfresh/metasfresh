package de.metas.contracts.interceptor;

import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.compiere.Adempiere;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import de.metas.bpartner.BPartnerId;
import de.metas.contracts.impl.BPartnerTimeSpanRepository;
import de.metas.contracts.invoice.ContractInvoiceService;
import de.metas.invoice.InvoiceId;

/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2018 metas GmbH
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

@Interceptor(I_C_Invoice.class)
@Component("de.metas.contracts.interceptor.C_Invoice")
public class C_Invoice
{

	private final ContractInvoiceService contractInvoiceService = Adempiere.getBean(ContractInvoiceService.class);

	private final BPartnerTimeSpanRepository bpartnerTimeSpanRepo = Adempiere.getBean(BPartnerTimeSpanRepository.class);

	@DocValidate(timings = { ModelValidator.TIMING_AFTER_COMPLETE })
	public void updateBPartnerTimeSpan(final I_C_Invoice invoice)
	{
		final InvoiceId invoiceId = de.metas.invoice.InvoiceId.ofRepoId(invoice.getC_Invoice_ID());

		if (!contractInvoiceService.isContractSalesInvoice(invoiceId))
		{
			// nothing to do
			return;
		}

		final BPartnerId bpartnerId = BPartnerId.ofRepoId(invoice.getC_BPartner_ID());

		if (!bpartnerTimeSpanRepo.isNewCustomer(bpartnerId))
		{
			// only change bpartners that are already marked as "New Customer"
			return;
		}

		bpartnerTimeSpanRepo.updateTimeSpan(bpartnerId);
	}
}
