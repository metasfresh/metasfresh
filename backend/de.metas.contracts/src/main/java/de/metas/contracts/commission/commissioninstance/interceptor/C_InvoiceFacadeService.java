package de.metas.contracts.commission.commissioninstance.interceptor;

import com.google.common.collect.ImmutableList;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.commissiontrigger.CommissionTriggerDocument;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.commissiontrigger.CommissionTriggerDocumentService;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.commissiontrigger.salesinvoiceline.SalesInvoice;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.commissiontrigger.salesinvoiceline.SalesInvoiceFactory;
import de.metas.logging.LogManager;
import lombok.NonNull;
import org.compiere.model.I_C_Invoice;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

@Service
public class C_InvoiceFacadeService
{
	private static final Logger logger = LogManager.getLogger(C_InvoiceFacadeService.class);

	private final SalesInvoiceFactory salesInvoiceFactory;
	private final CommissionTriggerDocumentService commissionTriggerDocumentService;

	public C_InvoiceFacadeService(
			@NonNull final SalesInvoiceFactory salesInvoiceFactory,
			@NonNull final CommissionTriggerDocumentService commissionTriggerDocumentService)
	{
		this.salesInvoiceFactory = salesInvoiceFactory;
		this.commissionTriggerDocumentService = commissionTriggerDocumentService;
	}

	public void syncInvoiceToCommissionInstance(@NonNull final I_C_Invoice invoiceRecord)
	{
		final Optional<SalesInvoice> salesInvoice = salesInvoiceFactory.forRecord(invoiceRecord);
		if (!salesInvoice.isPresent())
		{
			logger.debug("The C_Invoice is not commission-relevant; -> nothing to do");
			return;
		}

		final ImmutableList<CommissionTriggerDocument> commissionTriggerDocuments = salesInvoice.get().asCommissionTriggerDocuments();
		for (final CommissionTriggerDocument commissionTriggerDocument : commissionTriggerDocuments)
		{
			commissionTriggerDocumentService.syncTriggerDocumentToCommissionInstance(commissionTriggerDocument, false);
		}
	}
}
