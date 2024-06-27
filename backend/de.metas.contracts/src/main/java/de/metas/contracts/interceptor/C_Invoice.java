package de.metas.contracts.interceptor;

import de.metas.contracts.impl.CustomerRetentionRepository;
import de.metas.contracts.modular.log.ModularContractLogService;
import de.metas.document.DocTypeId;
import de.metas.invoice.InvoiceId;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

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
@Component
@RequiredArgsConstructor
public class C_Invoice
{
	@NonNull private final ModularContractLogService modularContractLogService;
	@NonNull private final CustomerRetentionRepository customerRetentionRepo;

	@DocValidate(timings = { ModelValidator.TIMING_AFTER_COMPLETE })
	public void updateCustomerRetention(final I_C_Invoice invoice)
	{
		final InvoiceId invoiceId = InvoiceId.ofRepoId(invoice.getC_Invoice_ID());

		customerRetentionRepo.updateCustomerRetentionOnInvoiceComplete(invoiceId);
	}

	@DocValidate(timings = { ModelValidator.TIMING_AFTER_REVERSECORRECT, ModelValidator.TIMING_AFTER_VOID })
	public void unprocessModularContractLogs(final I_C_Invoice invoice)
	{
		modularContractLogService.unprocessModularContractLogs(InvoiceId.ofRepoId(invoice.getC_Invoice_ID()), DocTypeId.ofRepoId(invoice.getC_DocType_ID()));
	}
}
