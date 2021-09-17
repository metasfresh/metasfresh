package de.metas.contracts.commission.commissioninstance.interceptor;

import de.metas.contracts.commission.commissioninstance.businesslogic.sales.commissiontrigger.CommissionTriggerDocumentService;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.commissiontrigger.salesinvoicecandidate.SalesInvoiceCandidate;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.commissiontrigger.salesinvoicecandidate.SalesInvoiceCandidateFactory;
import de.metas.contracts.commission.commissioninstance.services.SettlementInvoiceCandidateService;
import de.metas.contracts.commission.model.I_C_Commission_Share;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.logging.LogManager;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.table.api.IADTableDAO;
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
public class C_Invoice_CandidateFacadeService
{

	private static final Logger logger = LogManager.getLogger(C_Invoice_CandidateFacadeService.class);

	private final CommissionTriggerDocumentService salesInvoiceCandidateService;
	private final SettlementInvoiceCandidateService settlementInvoiceCandidateService;

	private final SalesInvoiceCandidateFactory salesInvoiceCandidateFactory;

	public C_Invoice_CandidateFacadeService(
			@NonNull final CommissionTriggerDocumentService salesInvoiceCandidateService,
			@NonNull final SettlementInvoiceCandidateService settlementInvoiceCandidateService,
			@NonNull final SalesInvoiceCandidateFactory salesInvoiceCandidateFactory)
	{
		this.salesInvoiceCandidateService = salesInvoiceCandidateService;
		this.settlementInvoiceCandidateService = settlementInvoiceCandidateService;
		this.salesInvoiceCandidateFactory = salesInvoiceCandidateFactory;
	}

	public void syncICToCommissionInstance(
			@NonNull final I_C_Invoice_Candidate icRecord,
			final boolean candidateDeleted)
	{
		final String tableName = Services.get(IADTableDAO.class).retrieveTableName(icRecord.getAD_Table_ID());

		if (I_C_Commission_Share.Table_Name.equals(tableName))
		{
			final InvoiceCandidateId invoiceCandidateId = InvoiceCandidateId.ofRepoId(icRecord.getC_Invoice_Candidate_ID());

			logger.debug("ic references a record from AD_Table.TableName={}; -> invoke SettlementInvoiceCandidateService", tableName);
			settlementInvoiceCandidateService.syncSettlementICToCommissionInstance(invoiceCandidateId, candidateDeleted);
		}
		else
		{
			final Optional<SalesInvoiceCandidate> salesInvoiceCandidate = salesInvoiceCandidateFactory.forRecord(icRecord);
			if (!salesInvoiceCandidate.isPresent())
			{
				logger.debug("The C_Invoice_Candidate is not commission-relevant; -> nothing to do");
				return;
			}

			logger.debug("ic references a record from AD_Table.TableName={}; -> invoke SalesInvoiceCandidateService", tableName);
			salesInvoiceCandidateService.syncTriggerDocumentToCommissionInstance(salesInvoiceCandidate.get().asCommissionTriggerDocument(), candidateDeleted);
		}
	}
}
