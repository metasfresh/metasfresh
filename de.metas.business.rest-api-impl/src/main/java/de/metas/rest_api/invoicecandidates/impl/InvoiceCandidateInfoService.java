package de.metas.rest_api.invoicecandidates.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.adempiere.invoice.service.IInvoiceDAO;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_InvoiceLine;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.invoice.InvoiceId;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.invoicecandidate.api.IInvoiceCandBL;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.rest_api.MetasfreshId;
import de.metas.rest_api.invoicecandidates.response.JsonGetInvoiceCandidatesStatusResponse;
import de.metas.rest_api.invoicecandidates.response.JsonInvoiceCandidateInfo;
import de.metas.rest_api.invoicecandidates.response.JsonInvoiceCandidateInfo.JsonInvoiceCandidateInfoBuilder;
import de.metas.rest_api.invoicecandidates.response.JsonInvoiceCandidateResult;
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
@Service
public class InvoiceCandidateInfoService
{
	private final IInvoiceCandBL invoiceCandBL = Services.get(IInvoiceCandBL.class);
	private final IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);
	private final IInvoiceDAO invoiceDAO = Services.get(IInvoiceDAO.class);

	public JsonGetInvoiceCandidatesStatusResponse getStatusForInvoiceCandidates(final List<ExternalHeaderAndLineId> headerAndLineIds)
	{
		final List<I_C_Invoice_Candidate> invoiceCandidateRecords = invoiceCandDAO.retrieveByHeaderAndLineId(headerAndLineIds);

		final List<JsonInvoiceCandidateInfo> invoiceCandidates = retrieveStatus(invoiceCandidateRecords);

		final JsonInvoiceCandidateResult result = JsonInvoiceCandidateResult.builder()
				.invoiceCandidates(invoiceCandidates)
				.build();

		return JsonGetInvoiceCandidatesStatusResponse.ok(result);
	}

	private List<JsonInvoiceCandidateInfo> retrieveStatus(final List<I_C_Invoice_Candidate> invoiceCandidateRecords)
	{
		final List<JsonInvoiceCandidateInfo> invoiceCandidatesStatus = new ArrayList<>();

		for (final I_C_Invoice_Candidate invoiceCandidateRecord : invoiceCandidateRecords)
		{
			final InvoiceCandidateId invoiceCandidateId = InvoiceCandidateId.ofRepoId(invoiceCandidateRecord.getC_Invoice_Candidate_ID());
			final List<JsonInvoiceInfo> invoicesInfo = retrieveInvoiceInfos(invoiceCandidateId);
			final List<JsonWorkPackageInfo> workPackagesInfo = retrieveWorkPackageInfo(invoiceCandidateId);

			final JsonInvoiceCandidateInfo invoiceCandidateStatus = prepareInvoiceCandidateStatus(invoiceCandidateRecord)
					.invoices(invoicesInfo)
					.workPackages(workPackagesInfo)
					.build();

			invoiceCandidatesStatus.add(invoiceCandidateStatus);
		}

		return invoiceCandidatesStatus.stream()
				.sorted(Comparator.comparing(candidate -> candidate.getMetasfreshId().getValue()))
				.collect(ImmutableList.toImmutableList());
	}

	private List<JsonWorkPackageInfo> retrieveWorkPackageInfo(final InvoiceCandidateId invoiceCandidateId)
	{
		final List<I_C_Queue_WorkPackage> workPackageRecords = invoiceCandBL.getUnprocessedWorkPackagesForInvoiceCandidate(invoiceCandidateId);
		if (workPackageRecords.isEmpty())
		{
			return ImmutableList.of();
		}

		return workPackageRecords.stream()
				.map(workPackageRecord -> toJsonForWorkPackage(workPackageRecord))
				.collect(ImmutableList.toImmutableList());

	}

	private static JsonWorkPackageInfo toJsonForWorkPackage(final I_C_Queue_WorkPackage workPackageRecord)
	{
		return JsonWorkPackageInfo.builder()
				.error(workPackageRecord.getErrorMsg())
				.enqueued(TimeUtil.asInstant(workPackageRecord.getCreated()))
				.readyForProcessing(workPackageRecord.isReadyForProcessing())
				.metasfreshId(MetasfreshId.of(workPackageRecord.getC_Queue_WorkPackage_ID()))
				.build();
	}

	private List<JsonInvoiceInfo> retrieveInvoiceInfos(final InvoiceCandidateId invoiceCandidateId)
	{
		final List<I_C_InvoiceLine> invoiceLinesForInvoiceCandidate = invoiceCandDAO.retrieveIlForIc(invoiceCandidateId);
		if (invoiceLinesForInvoiceCandidate.isEmpty())
		{

			return ImmutableList.of();
		}

		final ImmutableSet<InvoiceId> invoiceIds = invoiceLinesForInvoiceCandidate
				.stream()
				.map(I_C_InvoiceLine::getC_Invoice_ID)
				.map(InvoiceId::ofRepoId)
				.collect(ImmutableSet.toImmutableSet());

		return invoiceDAO.getByIdsInTrx(invoiceIds)
				.stream()
				.map(invoiceRecord -> toJsonInvoiceInfo(invoiceRecord))
				.collect(ImmutableList.toImmutableList());

	}

	private static JsonInvoiceCandidateInfoBuilder prepareInvoiceCandidateStatus(final I_C_Invoice_Candidate invoiceCandidateRecord)
	{
		final BigDecimal qtyToInvoice = invoiceCandidateRecord.getQtyToInvoice();
		final BigDecimal qtyInvoiced = invoiceCandidateRecord.getQtyInvoiced();

		return JsonInvoiceCandidateInfo.builder()
				.qtyEntered(invoiceCandidateRecord.getQtyEntered())
				.dateToInvoice(TimeUtil.asLocalDate(qtyToInvoice.signum() > 0 ? invoiceCandidateRecord.getDateToInvoice() : null))
				.dateInvoiced(TimeUtil.asLocalDate(invoiceCandidateRecord.getDateInvoiced()))
				.externalHeaderId(ExternalId.of(invoiceCandidateRecord.getExternalHeaderId()))
				.externalLineId(ExternalId.of(invoiceCandidateRecord.getExternalLineId()))
				.metasfreshId(MetasfreshId.of(invoiceCandidateRecord.getC_Invoice_Candidate_ID()))
				.qtyInvoiced(qtyInvoiced.signum() > 0 ? qtyInvoiced : null)
				.qtyToInvoice(qtyToInvoice.signum() > 0 ? qtyToInvoice : null);
	}

	private static JsonInvoiceInfo toJsonInvoiceInfo(final I_C_Invoice invoice)
	{
		return JsonInvoiceInfo.builder()
				.dateInvoiced(TimeUtil.asLocalDate(invoice.getDateInvoiced()))
				.docStatus(invoice.getDocStatus())
				.documentNo(invoice.getDocumentNo())
				.metasfreshId(MetasfreshId.of(invoice.getC_Invoice_ID()))
				.build();
	}

}
