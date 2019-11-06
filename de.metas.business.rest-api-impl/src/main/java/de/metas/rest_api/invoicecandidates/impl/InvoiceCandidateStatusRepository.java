package de.metas.rest_api.invoicecandidates.impl;

import static org.adempiere.model.InterfaceWrapperHelper.getTableId;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.invoice.service.IInvoiceDAO;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_InvoiceLine;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Repository;

import com.google.common.collect.ImmutableList;

import de.metas.async.api.IQueueDAO;
import de.metas.async.model.I_C_Queue_Block;
import de.metas.async.model.I_C_Queue_Element;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.invoice.InvoiceId;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.async.spi.impl.InvoiceCandWorkpackageProcessor;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.rest_api.MetasfreshId;
import de.metas.rest_api.invoicecandidates.response.JsonGetInvoiceCandidatesStatusResponse;
import de.metas.rest_api.invoicecandidates.response.JsonGetInvoiceCandidatesStatusResult;
import de.metas.rest_api.invoicecandidates.response.JsonInvoiceCandidateResult_StatusEnqueued;
import de.metas.rest_api.invoicecandidates.response.JsonInvoiceCandidateResult_StatusEnqueuedWithError;
import de.metas.rest_api.invoicecandidates.response.JsonInvoiceCandidateResult_StatusInvoiced;
import de.metas.rest_api.invoicecandidates.response.JsonInvoiceCandidateResults_StatusNotEnqueued;
import de.metas.rest_api.invoicecandidates.response.JsonInvoiceCandidateStatus;
import de.metas.rest_api.invoicecandidates.response.JsonInvoiceCandidateStatusEnqueued;
import de.metas.rest_api.invoicecandidates.response.JsonInvoiceCandidateStatusInvoiced;
import de.metas.rest_api.invoicecandidates.response.JsonInvoiceInfo;
import de.metas.rest_api.invoicecandidates.response.JsonWorkPackageInfo;
import de.metas.util.Services;
import de.metas.util.rest.ExternalHeaderAndLineId;
import de.metas.util.rest.ExternalId;

/*
 * #%L
 * de.metas.business.rest-api-impl
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
@Repository
public class InvoiceCandidateStatusRepository
{
	private final IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);
	private final IInvoiceDAO invoiceDAO = Services.get(IInvoiceDAO.class);
	private final IQueueDAO queueDAO = Services.get(IQueueDAO.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public JsonGetInvoiceCandidatesStatusResponse getStatusForInvoiceCandidates(final List<ExternalHeaderAndLineId> headerAndLineIds)
	{
		final List<InvoiceCandidateId> invoiceCandidateIds = invoiceCandDAO.retrieveByHeaderAndLineId(headerAndLineIds);

		final JsonInvoiceCandidateResult_StatusInvoiced invoiced = createStatusInvoiced(invoiceCandidateIds);
		final JsonInvoiceCandidateResult_StatusEnqueued enqueued = createStatusEnqueued(invoiceCandidateIds);
		final JsonInvoiceCandidateResult_StatusEnqueuedWithError enqueuedWithError = createStatusEnqueuedWithError(invoiceCandidateIds);
		final JsonInvoiceCandidateResults_StatusNotEnqueued notEnqueued = createStatusNotEnqueued(invoiceCandidateIds);

		final JsonGetInvoiceCandidatesStatusResult result = JsonGetInvoiceCandidatesStatusResult
				.builder()
				.invoiced(invoiced)
				.enqueued(enqueued)
				.enqueuedWithError(enqueuedWithError)
				.notEnqueued(notEnqueued)
				.build();

		return JsonGetInvoiceCandidatesStatusResponse.ok(result);
	}

	private JsonInvoiceCandidateResult_StatusInvoiced createStatusInvoiced(final List<InvoiceCandidateId> invoiceCandidateIds)
	{
		final List<JsonInvoiceCandidateStatusInvoiced> jsonInvoiceCandidatesInvoiced = new ArrayList<>();

		for (final InvoiceCandidateId invoiceCandidateId : invoiceCandidateIds)
		{
			final I_C_Invoice_Candidate invoiceCandidateRecord = invoiceCandDAO.getById(invoiceCandidateId);

			final List<JsonInvoiceInfo> invoicesInfoForInvoiceCandidate = createJsonInvoicesInfoForInvoiceCandidate(invoiceCandidateRecord);

			if (invoicesInfoForInvoiceCandidate.isEmpty())
			{
				continue;
			}

			final JsonInvoiceCandidateStatusInvoiced invoiceCandidateStatus = createStatusInvoiced(invoiceCandidateRecord, invoicesInfoForInvoiceCandidate);

			jsonInvoiceCandidatesInvoiced.add(invoiceCandidateStatus);

		}

		return JsonInvoiceCandidateResult_StatusInvoiced.builder()
				.jsonInvoiceCandidates(jsonInvoiceCandidatesInvoiced)
				.build();
	}

	private JsonInvoiceCandidateResult_StatusEnqueued createStatusEnqueued(final List<InvoiceCandidateId> invoiceCandidateIds)
	{
		final List<JsonInvoiceCandidateStatusEnqueued> invoiceCandidatesEnqueued = new ArrayList<>();

		for (final InvoiceCandidateId invoiceCandidateId : invoiceCandidateIds)
		{
			final I_C_Invoice_Candidate invoiceCandidateRecord = invoiceCandDAO.getById(invoiceCandidateId);

			final List<JsonWorkPackageInfo> packagesForInvoiceCandidate = createWorkPackagesForInvoiceCandidate(
					invoiceCandidateRecord,
					false);

			if (packagesForInvoiceCandidate.isEmpty())
			{
				continue;
			}

			final JsonInvoiceCandidateStatusEnqueued invoiceCandidateStatus = createStatusEnqueued(invoiceCandidateRecord, packagesForInvoiceCandidate);

			invoiceCandidatesEnqueued.add(invoiceCandidateStatus);

		}

		return JsonInvoiceCandidateResult_StatusEnqueued.builder()
				.invoiceCandidates(invoiceCandidatesEnqueued)
				.build();
	}

	private JsonInvoiceCandidateResult_StatusEnqueuedWithError createStatusEnqueuedWithError(final List<InvoiceCandidateId> invoiceCandidateIds)
	{

		final List<JsonInvoiceCandidateStatusEnqueued> jsonInvoiceCandidatesEnqueuedWithError = new ArrayList<>();

		for (final InvoiceCandidateId invoiceCandidateId : invoiceCandidateIds)
		{
			final I_C_Invoice_Candidate invoiceCandidateRecord = invoiceCandDAO.getById(invoiceCandidateId);

			final List<JsonWorkPackageInfo> packagesForInvoiceCandidate = createWorkPackagesForInvoiceCandidate(
					invoiceCandidateRecord,
					true);

			if (packagesForInvoiceCandidate.isEmpty())
			{
				continue;
			}

			final JsonInvoiceCandidateStatusEnqueued invoiceCandidateStatus = createStatusEnqueued(invoiceCandidateRecord, packagesForInvoiceCandidate);

			jsonInvoiceCandidatesEnqueuedWithError.add(invoiceCandidateStatus);

		}

		return JsonInvoiceCandidateResult_StatusEnqueuedWithError.builder()
				.invoiceCandidates(jsonInvoiceCandidatesEnqueuedWithError)
				.build();
	}

	private JsonInvoiceCandidateResults_StatusNotEnqueued createStatusNotEnqueued(final List<InvoiceCandidateId> invoiceCandidateIds)
	{
		final List<JsonInvoiceCandidateStatus> invoiceCandidatesNotEnqueued = new ArrayList<>();

		for (final InvoiceCandidateId invoiceCandidateId : invoiceCandidateIds)
		{
			final I_C_Invoice_Candidate invoiceCandidateRecord = invoiceCandDAO.getById(invoiceCandidateId);

			final List<JsonWorkPackageInfo> workPackages = createWorkPackagesForInvoiceCandidate(
					invoiceCandidateRecord, null);

			if (!workPackages.isEmpty())
			{
				continue;
			}

			final JsonInvoiceCandidateStatus invoiceCandidateStatus = createInvoiceCandidateStatus(invoiceCandidateRecord);

			invoiceCandidatesNotEnqueued.add(invoiceCandidateStatus);

		}

		return JsonInvoiceCandidateResults_StatusNotEnqueued.builder()
				.invoiceCandidates(invoiceCandidatesNotEnqueued)
				.build();
	}

	private List<JsonWorkPackageInfo> createWorkPackagesForInvoiceCandidate(
			final I_C_Invoice_Candidate invoiceCandidateRecord,
			final Boolean isError)
	{

		final List<I_C_Queue_WorkPackage> workPackageNoErrorRecords = getWorkProcessesForInvoiceCandidate(invoiceCandidateRecord, isError);

		if (workPackageNoErrorRecords.isEmpty())
		{

			return ImmutableList.of();
		}

		return workPackageNoErrorRecords
				.stream()
				.map(workPackageRecord -> createJsonForWorkPackage(workPackageRecord))
				.collect(ImmutableList.toImmutableList());

	}

	private List<I_C_Queue_WorkPackage> getWorkProcessesForInvoiceCandidate(final I_C_Invoice_Candidate invoiceCandidateRecord,
			final Boolean isError)
	{
		final int invoiceCandidatesWorkpackageProcessorId = queueDAO.retrievePackageProcessorDefByClass(Env.getCtx(), InvoiceCandWorkpackageProcessor.class).getC_Queue_PackageProcessor_ID();
		final int invoiceCandidateTableId = getTableId(I_C_Invoice_Candidate.class);
		final ICompositeQueryFilter<I_C_Queue_WorkPackage> workPackageFilter = queryBL.createCompositeQueryFilter(I_C_Queue_WorkPackage.class)
				.addNotEqualsFilter(I_C_Queue_WorkPackage.COLUMNNAME_Processed, true);

		if (isError != null && isError)
		{
			workPackageFilter.addNotNull(I_C_Queue_WorkPackage.COLUMNNAME_ErrorMsg);
		}
		else if (isError != null)
		{
			workPackageFilter.addEqualsFilter(I_C_Queue_WorkPackage.COLUMNNAME_ErrorMsg, null);
		}

		return queryBL.createQueryBuilder(I_C_Queue_Element.class)
				.addEqualsFilter(I_C_Queue_Element.COLUMNNAME_AD_Table_ID, invoiceCandidateTableId)
				.addEqualsFilter(I_C_Queue_Element.COLUMNNAME_Record_ID, invoiceCandidateRecord.getC_Invoice_Candidate_ID())
				.andCollect(I_C_Queue_Element.COLUMN_C_Queue_WorkPackage_ID)
				.filter(workPackageFilter)
				.andCollect(I_C_Queue_WorkPackage.COLUMN_C_Queue_Block_ID)
				.addEqualsFilter(I_C_Queue_Block.COLUMNNAME_C_Queue_PackageProcessor_ID, invoiceCandidatesWorkpackageProcessorId)
				.andCollectChildren(I_C_Queue_WorkPackage.COLUMN_C_Queue_Block_ID)
				.create()
				.list();

	}

	private JsonWorkPackageInfo createJsonForWorkPackage(final I_C_Queue_WorkPackage workPackageRecord)
	{
		return JsonWorkPackageInfo.builder()
				.error(workPackageRecord.getErrorMsg())
				.enqueued(TimeUtil.asInstant(workPackageRecord.getCreated()))
				.readyForProcessing(workPackageRecord.isReadyForProcessing())
				.workpackageId(workPackageRecord.getC_Queue_WorkPackage_ID())
				.build();
	}

	private List<JsonInvoiceInfo> createJsonInvoicesInfoForInvoiceCandidate(final I_C_Invoice_Candidate invoiceCandidateRecord)
	{

		final List<I_C_InvoiceLine> invoiceLinesForInvoiceCandidate = invoiceCandDAO.retrieveIlForIc(invoiceCandidateRecord);

		if (invoiceLinesForInvoiceCandidate.isEmpty())
		{

			return ImmutableList.of();
		}

		return invoiceLinesForInvoiceCandidate
				.stream()
				.map(I_C_InvoiceLine::getC_Invoice_ID)
				.map(invoiceId -> invoiceDAO.getByIdInTrx(InvoiceId.ofRepoId(invoiceId)))
				.map(invoice -> createForInvoice(invoice))
				.collect(ImmutableList.toImmutableList());

	}

	private JsonInvoiceCandidateStatusInvoiced createStatusInvoiced(
			final I_C_Invoice_Candidate invoiceCandidateRecord,
			final List<JsonInvoiceInfo> invoices)
	{

		final JsonInvoiceCandidateStatus invoiceCandidate = createInvoiceCandidateStatus(invoiceCandidateRecord);

		return JsonInvoiceCandidateStatusInvoiced.builder()
				.invoiceCandidate(invoiceCandidate)
				.invoices(invoices)
				.build();
	}

	private JsonInvoiceCandidateStatusEnqueued createStatusEnqueued(
			final I_C_Invoice_Candidate invoiceCandidateRecord,
			final List<JsonWorkPackageInfo> workPackages)
	{
		final JsonInvoiceCandidateStatus invoiceCandidate = createInvoiceCandidateStatus(invoiceCandidateRecord);

		return JsonInvoiceCandidateStatusEnqueued.builder()
				.invoiceCandidate(invoiceCandidate)
				.workPackages(workPackages)
				.build();

	}

	private JsonInvoiceCandidateStatus createInvoiceCandidateStatus(final I_C_Invoice_Candidate invoiceCandidateRecord)
	{
		final BigDecimal qtyToInvoice = invoiceCandidateRecord.getQtyToInvoice();
		final BigDecimal qtyInvoiced = invoiceCandidateRecord.getQtyInvoiced();

		return JsonInvoiceCandidateStatus.builder()
				.qtyEntered(invoiceCandidateRecord.getQtyEntered()))
				.dateToInvoice(TimeUtil.asLocalDate(qtyToInvoice.signum() > 0? invoiceCandidateRecord.getDateToInvoice() : null))
				.dateInvoiced(TimeUtil.asLocalDate(invoiceCandidateRecord.getDateInvoiced()))
				.externalHeaderId(ExternalId.of(invoiceCandidateRecord.getExternalHeaderId()))
				.externalLineId(ExternalId.of(invoiceCandidateRecord.getExternalLineId()))
				.metasfreshId(MetasfreshId.of(invoiceCandidateRecord.getC_Invoice_Candidate_ID()))
				.qtyInvoiced(qtyInvoiced.signum() > 0? qtyInvoiced : null)
				.qtyToInvoice(qtyToInvoice.signum() > 0? qtyToInvoice : null)
				.build();
	}

	private JsonInvoiceInfo createForInvoice(final I_C_Invoice invoice)
	{
		return JsonInvoiceInfo.builder()
				.dateInvoiced(TimeUtil.asLocalDate(invoice.getDateInvoiced()))
				.docStatus(invoice.getDocStatus())
				.documentNo(invoice.getDocumentNo())
				.invoiceId(invoice.getC_Invoice_ID())
				.build();
	}

}
