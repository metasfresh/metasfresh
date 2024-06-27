package de.metas.contracts.interceptor;

import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.IFlatrateBL;
import de.metas.contracts.impl.CustomerRetentionRepository;
import de.metas.contracts.modular.ComputingMethodType;
import de.metas.contracts.modular.log.ModularContractLogService;
import de.metas.document.DocTypeId;
import de.metas.document.IDocTypeBL;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.service.IInvoiceBL;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_InvoiceLine;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
	private final IDocTypeBL docTypeBL = Services.get(IDocTypeBL.class);
	private final IInvoiceBL invoiceBL = Services.get(IInvoiceBL.class);
	private final IFlatrateBL flatrateBL = Services.get(IFlatrateBL.class);
	private final IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);
	@NonNull private final ModularContractLogService modularContractLogService;

	@DocValidate(timings = { ModelValidator.TIMING_AFTER_COMPLETE })
	public void updateCustomerRetention(final I_C_Invoice invoice)
	{
		final CustomerRetentionRepository customerRetentionRepo = SpringContextHolder.instance.getBean(CustomerRetentionRepository.class);
		final InvoiceId invoiceId = InvoiceId.ofRepoId(invoice.getC_Invoice_ID());

		customerRetentionRepo.updateCustomerRetentionOnInvoiceComplete(invoiceId);

	}

	@DocValidate(timings = { ModelValidator.TIMING_AFTER_REVERSECORRECT, ModelValidator.TIMING_AFTER_VOID })
	public void unprocessModularContractLogs(final I_C_Invoice invoice)
	{
		final DocTypeId docTypeId = DocTypeId.ofRepoId(invoice.getC_DocType_ID());
		if ((!docTypeBL.isFinalInvoiceOrFinalCreditMemo(docTypeId)
				&& !docTypeBL.isDefinitiveInvoiceOrDefinitiveCreditMemo(docTypeId))
				&& !docTypeBL.isInterimInvoice(docTypeId))
		{
			return;
		}

		final InvoiceId invoiceId = InvoiceId.ofRepoId(invoice.getC_Invoice_ID());

		modularContractLogService.unprocessLogsForInvoice(invoiceId, getComputingMethodTypes(docTypeId));

		final List<I_C_Invoice_Candidate> invoiceCandidates = invoiceCandDAO.retrieveInvoiceCandidates(invoiceId);
		for (final I_C_Invoice_Candidate invoiceCandidate : invoiceCandidates)
		{
			invoiceCandidate.setProcessed(false);//maybe should live in de.metas.invoicecandidate.api.impl.InvoiceCandBL.handleReversalForInvoice, but I'm afraid of consequences
			invoiceCandidate.setIsActive(false);
		}
		InterfaceWrapperHelper.saveAll(invoiceCandidates);
		if (docTypeBL.isDefinitiveInvoiceOrDefinitiveCreditMemo(docTypeId))
		{
			final Set<FlatrateTermId> contractIds = invoiceBL.getLines(invoiceId)
					.stream()
					.map(I_C_InvoiceLine::getC_Flatrate_Term_ID)
					.map(FlatrateTermId::ofRepoId)
					.collect(Collectors.toSet());
			flatrateBL.reverseDefinitiveInvoice(contractIds);
		}
	}

	@NonNull
	private Collection<ComputingMethodType> getComputingMethodTypes(@NonNull final DocTypeId docTypeId)
	{
		if (docTypeBL.isFinalInvoiceOrFinalCreditMemo(docTypeId))
		{
			return ComputingMethodType.FINAL_INVOICE_SPECIFIC_METHODS;
		}
		if (docTypeBL.isDefinitiveInvoiceOrDefinitiveCreditMemo(docTypeId))
		{
			return ComputingMethodType.DEFINITIVE_INVOICE_SPECIFIC_METHODS;
		}
		if (docTypeBL.isInterimInvoice(docTypeId))
		{
			return ComputingMethodType.INTERIM_INVOICE_SPECIFIC_METHODS;
		}
		throw new AdempiereException("Unexpected document type: " + docTypeId);
	}
}
