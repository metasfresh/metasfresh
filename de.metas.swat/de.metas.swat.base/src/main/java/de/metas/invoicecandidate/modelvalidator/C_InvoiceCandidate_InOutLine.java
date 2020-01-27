package de.metas.invoicecandidate.modelvalidator;

import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.ModelValidator;
import org.slf4j.MDC.MDCCloseable;
import org.springframework.stereotype.Component;

import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.invoicecandidate.internalbusinesslogic.InvoiceCandidate;
import de.metas.invoicecandidate.internalbusinesslogic.InvoiceCandidateRepository;
import de.metas.invoicecandidate.model.I_C_InvoiceCandidate_InOutLine;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.logging.TableRecordMDC;
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

@Interceptor(I_C_InvoiceCandidate_InOutLine.class)
@Component
public class C_InvoiceCandidate_InOutLine
{
	private final InvoiceCandidateRepository invoiceCandidateRepository;

	private C_InvoiceCandidate_InOutLine(@NonNull final InvoiceCandidateRepository invoiceCandidateRepository)
	{
		this.invoiceCandidateRepository = invoiceCandidateRepository;
	}

	@ModelChange(
			timings = ModelValidator.TYPE_AFTER_CHANGE, //
			ifColumnsChanged = I_C_InvoiceCandidate_InOutLine.COLUMNNAME_QtyDeliveredInUOM_Override)
	public void updateInvoiceCandidate(@NonNull final I_C_InvoiceCandidate_InOutLine icIlaRecord)
	{
		final InvoiceCandidateId invoiceCandidateId = InvoiceCandidateId.ofRepoId(icIlaRecord.getC_Invoice_Candidate_ID());
		try (final MDCCloseable icMDC = TableRecordMDC.putTableRecordReference(I_C_Invoice_Candidate.Table_Name, invoiceCandidateId);)
		{
			// load the invoice candidate with all relevant data
			final InvoiceCandidate invoiceCandidate = invoiceCandidateRepository.getById(invoiceCandidateId);

			// store the invoice candidate with its computed results.
			invoiceCandidateRepository.save(invoiceCandidate);
		}
	}
}
