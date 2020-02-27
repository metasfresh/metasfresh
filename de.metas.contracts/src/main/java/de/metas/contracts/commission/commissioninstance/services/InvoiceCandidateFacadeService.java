package de.metas.contracts.commission.commissioninstance.services;

import org.adempiere.ad.table.api.IADTableDAO;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import de.metas.contracts.commission.model.I_C_Commission_Share;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.logging.LogManager;
import de.metas.util.Services;
import lombok.NonNull;

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
public class InvoiceCandidateFacadeService
{

	private static final Logger logger = LogManager.getLogger(InvoiceCandidateFacadeService.class);

	private final SalesInvoiceCandidateService invoiceCandidateService;
	private final SettlementInvoiceCandidateService settlementInvoiceCandidateService;

	private final IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);

	public InvoiceCandidateFacadeService(
			@NonNull final SalesInvoiceCandidateService invoiceCandidateService,
			@NonNull final SettlementInvoiceCandidateService settlementInvoiceCandidateService)
	{
		this.invoiceCandidateService = invoiceCandidateService;
		this.settlementInvoiceCandidateService = settlementInvoiceCandidateService;
	}

	public void syncICToCommissionInstance(
			@NonNull final InvoiceCandidateId invoiceCandidateId,
			final boolean candidateDeleted)
	{
		final I_C_Invoice_Candidate icRecord = invoiceCandDAO.getById(invoiceCandidateId);

		final String tableName = Services.get(IADTableDAO.class).retrieveTableName(icRecord.getAD_Table_ID());

		if (I_C_Commission_Share.Table_Name.equals(tableName))
		{
			logger.debug("ic references a record from AD_Table.TableName={}; -> invoke SettlementInvoiceCandidateService", tableName);
			settlementInvoiceCandidateService.syncSettlementICToCommissionInstance(invoiceCandidateId, candidateDeleted);
		}
		else
		{
			logger.debug("ic references a record from AD_Table.TableName={}; -> invoke SalesInvoiceCandidateService", tableName);
			invoiceCandidateService.syncSalesICToCommissionInstance(invoiceCandidateId, candidateDeleted);
		}
	}
}
