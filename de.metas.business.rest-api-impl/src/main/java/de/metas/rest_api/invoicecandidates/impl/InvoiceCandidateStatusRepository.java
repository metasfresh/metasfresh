package de.metas.rest_api.invoicecandidates.impl;

import static org.adempiere.model.InterfaceWrapperHelper.getTableId;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

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
import de.metas.rest_api.invoicecandidates.response.JsonInvoiceCandidateResult;
import de.metas.rest_api.invoicecandidates.response.JsonInvoiceCandidateStatus;
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

		final List<JsonInvoiceCandidateStatus> invoiceCandidates = createStatus(invoiceCandidateIds);

		final JsonInvoiceCandidateResult result = JsonInvoiceCandidateResult
				.builder()
				.invoiceCandidates(invoiceCandidates)
				.build();

		return JsonGetInvoiceCandidatesStatusResponse.ok(result);
	}

	private List<JsonInvoiceCandidateStatus> createStatus(final List<InvoiceCandidateId> invoiceCandidateIds)
	{
		final List<JsonInvoiceCandidateStatus> invoiceCandidatesStatus = new ArrayList<>();

		for (final InvoiceCandidateId invoiceCandidateId : invoiceCandidateIds)
		{
			final I_C_Invoice_Candidate invoiceCandidateRecord = invoiceCandDAO.getById(invoiceCandidateId);

			final List<JsonInvoiceInfo> invoicesInfoForInvoiceCandidate = createJsonInvoicesInfoForInvoiceCandidate(invoiceCandidateRecord);
			final List<JsonWorkPackageInfo> workPackagesForInvoiceCandidate = createWorkPackagesForInvoiceCandidate(invoiceCandidateRecord);

			final JsonInvoiceCandidateStatus invoiceCandidateStatus = createInvoiceCandidateStatus(invoiceCandidateRecord, invoicesInfoForInvoiceCandidate, workPackagesForInvoiceCandidate);

			invoiceCandidatesStatus.add(invoiceCandidateStatus);
		}

		return invoiceCandidatesStatus.stream()
				.sorted(Comparator.comparing(candidate -> candidate.getMetasfreshId().getValue()))
				.collect(ImmutableList.toImmutableList());
	}

	private List<JsonWorkPackageInfo> createWorkPackagesForInvoiceCandidate(
			final I_C_Invoice_Candidate invoiceCandidateRecord)
	{

		final List<I_C_Queue_WorkPackage> workPackageRecords = getWorkProcessesForInvoiceCandidate(invoiceCandidateRecord);

		if (workPackageRecords.isEmpty())
		{

			return ImmutableList.of();
		}

		return workPackageRecords
				.stream()
				.map(workPackageRecord -> createJsonForWorkPackage(workPackageRecord))
				.collect(ImmutableList.toImmutableList());

	}

	private List<I_C_Queue_WorkPackage> getWorkProcessesForInvoiceCandidate(final I_C_Invoice_Candidate invoiceCandidateRecord)
	{
		final int invoiceCandidatesWorkpackageProcessorId = queueDAO.retrievePackageProcessorDefByClass(Env.getCtx(), InvoiceCandWorkpackageProcessor.class).getC_Queue_PackageProcessor_ID();
		final int invoiceCandidateTableId = getTableId(I_C_Invoice_Candidate.class);

		return queryBL.createQueryBuilder(I_C_Queue_Element.class)
				.addEqualsFilter(I_C_Queue_Element.COLUMNNAME_AD_Table_ID, invoiceCandidateTableId)
				.addEqualsFilter(I_C_Queue_Element.COLUMNNAME_Record_ID, invoiceCandidateRecord.getC_Invoice_Candidate_ID())
				.andCollect(I_C_Queue_Element.COLUMN_C_Queue_WorkPackage_ID)
				.addNotEqualsFilter(I_C_Queue_WorkPackage.COLUMNNAME_Processed, true)
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
				.metasfreshId(MetasfreshId.of(workPackageRecord.getC_Queue_WorkPackage_ID()))
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

	private JsonInvoiceCandidateStatus createInvoiceCandidateStatus(
			final I_C_Invoice_Candidate invoiceCandidateRecord,
			final List<JsonInvoiceInfo> invoices,
			final List<JsonWorkPackageInfo> workPackages)
	{
		final BigDecimal qtyToInvoice = invoiceCandidateRecord.getQtyToInvoice();
		final BigDecimal qtyInvoiced = invoiceCandidateRecord.getQtyInvoiced();

		return JsonInvoiceCandidateStatus.builder()
				.qtyEntered(invoiceCandidateRecord.getQtyEntered())
				.dateToInvoice(TimeUtil.asLocalDate(qtyToInvoice.signum() > 0 ? invoiceCandidateRecord.getDateToInvoice() : null))
				.dateInvoiced(TimeUtil.asLocalDate(invoiceCandidateRecord.getDateInvoiced()))
				.externalHeaderId(ExternalId.of(invoiceCandidateRecord.getExternalHeaderId()))
				.externalLineId(ExternalId.of(invoiceCandidateRecord.getExternalLineId()))
				.metasfreshId(MetasfreshId.of(invoiceCandidateRecord.getC_Invoice_Candidate_ID()))
				.qtyInvoiced(qtyInvoiced.signum() > 0 ? qtyInvoiced : null)
				.qtyToInvoice(qtyToInvoice.signum() > 0 ? qtyToInvoice : null)
				.invoices(invoices)
				.workPackages(workPackages)
				.build();
	}

	private JsonInvoiceInfo createForInvoice(final I_C_Invoice invoice)
	{
		return JsonInvoiceInfo.builder()
				.dateInvoiced(TimeUtil.asLocalDate(invoice.getDateInvoiced()))
				.docStatus(invoice.getDocStatus())
				.documentNo(invoice.getDocumentNo())
				.metasfreshId(MetasfreshId.of(invoice.getC_Invoice_ID()))
				.build();
	}

}
