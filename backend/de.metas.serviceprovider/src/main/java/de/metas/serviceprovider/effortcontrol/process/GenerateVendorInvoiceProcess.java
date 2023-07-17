/*
 * #%L
 * de.metas.serviceprovider.base
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.serviceprovider.effortcontrol.process;

import de.metas.common.util.time.SystemTime;
import de.metas.document.DocTypeId;
import de.metas.document.DocTypeQuery;
import de.metas.document.IDocTypeDAO;
import de.metas.invoice.InvoiceDocBaseType;
import de.metas.invoicecandidate.api.IInvoiceCandBL;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.process.params.InvoicingParams;
import de.metas.process.JavaProcess;
import de.metas.process.PInstanceId;
import de.metas.serviceprovider.issue.Status;
import de.metas.serviceprovider.model.I_S_EffortControl;
import de.metas.serviceprovider.model.I_S_Issue;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryFilter;
import org.compiere.model.IQuery;
import org.compiere.model.X_C_DocType;

public class GenerateVendorInvoiceProcess extends JavaProcess
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);
	private final IInvoiceCandBL invoiceCandBL = Services.get(IInvoiceCandBL.class);

	@Override
	protected String doIt() throws Exception
	{
		final DocTypeId vendorInvoiceDocTypeId = retrieveInternalVendorInvoiceDocTypeId();
		final PInstanceId selectionId = getPinstanceId();

		createSelection(vendorInvoiceDocTypeId, selectionId);

		enqueueSelection(selectionId);

		return MSG_OK;
	}

	@NonNull
	private DocTypeId retrieveInternalVendorInvoiceDocTypeId()
	{
		final DocTypeQuery docTypeQuery = DocTypeQuery.builder()
				.adClientId(getClientID().getRepoId())
				.docBaseType(InvoiceDocBaseType.VendorInvoice.getDocBaseType())
				.docSubType(X_C_DocType.DOCSUBTYPE_InternalVendorInvoice)
				.isSOTrx(false)
				.build();

		return docTypeDAO.getDocTypeId(docTypeQuery);
	}

	@NonNull
	private void createSelection(
			@NonNull final DocTypeId vendorInvoiceDocTypeId,
			@NonNull final PInstanceId selectionId)
	{
		final IQuery<I_S_Issue> processedBudgetIssueQuery = queryBL.createQueryBuilder(I_S_Issue.class)
				.addOnlyActiveRecordsFilter()
				.addNotNull(I_S_Issue.COLUMNNAME_EffortAggregationKey)
				.addEqualsFilter(I_S_Issue.COLUMNNAME_Processed, true)
				.addEqualsFilter(I_S_Issue.COLUMNNAME_Status, Status.INVOICED.getCode())
				.addEqualsFilter(I_S_Issue.COLUMNNAME_IsEffortIssue, false)
				.create();

		final IQueryFilter<I_S_EffortControl> processedEffortControlFilter = queryBL.createCompositeQueryFilter(I_S_EffortControl.class)
				.addOnlyActiveRecordsFilter()
				.addInSubQueryFilter(I_S_EffortControl.COLUMNNAME_EffortAggregationKey, I_S_Issue.COLUMNNAME_EffortAggregationKey, processedBudgetIssueQuery);

		final IQueryFilter<I_S_EffortControl> selectedFilter = getProcessInfo()
				.getQueryFilterOrElseTrue();

		final IQuery<I_S_EffortControl> processedEffortControlQuery = queryBL.createQueryBuilder(I_S_EffortControl.class)
				.addFilter(selectedFilter)
				.addFilter(processedEffortControlFilter)
				.create();

		final IQuery<I_S_Issue> effortIssueQuery = queryBL.createQueryBuilder(I_S_Issue.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_S_Issue.COLUMNNAME_IsEffortIssue, true)
				.addEqualsFilter(I_S_Issue.COLUMNNAME_Processed, true)
				.addEqualsFilter(I_S_Issue.COLUMNNAME_Status, Status.INVOICED.getCode())
				.addInSubQueryFilter(I_S_Issue.COLUMNNAME_EffortAggregationKey, I_S_EffortControl.COLUMNNAME_EffortAggregationKey, processedEffortControlQuery)
				.create();

		queryBL.createQueryBuilder(I_C_Invoice_Candidate.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Invoice_Candidate.COLUMNNAME_Processed, false)
				.addEqualsFilter(I_C_Invoice_Candidate.COLUMNNAME_C_DocTypeInvoice_ID, vendorInvoiceDocTypeId)
				.addInSubQueryFilter(I_C_Invoice_Candidate.COLUMNNAME_Record_ID, I_S_Issue.COLUMNNAME_S_Issue_ID, effortIssueQuery)
				.create()
				.createSelection(selectionId);
	}

	private void enqueueSelection(@NonNull final PInstanceId selectionId)
	{
		invoiceCandBL.enqueueForInvoicing()
				.setContext(getCtx())
				.setInvoicingParams(createDefaultInvoicingParams())
				.setFailIfNothingEnqueued(false)
				.enqueueSelection(selectionId);
	}

	@NonNull
	private static InvoicingParams createDefaultInvoicingParams()
	{
		return InvoicingParams.builder()
				.ignoreInvoiceSchedule(false)
				.dateInvoiced(SystemTime.asLocalDate())
				.build();
	}
}
