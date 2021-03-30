package de.metas.rest_api.invoicecandidates.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_InvoiceLine;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.i18n.TranslatableStrings;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.service.IInvoiceDAO;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.invoicecandidate.api.IInvoiceCandBL;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.api.InvoiceCandidateMultiQuery;
import de.metas.invoicecandidate.api.InvoiceCandidateMultiQuery.InvoiceCandidateMultiQueryBuilder;
import de.metas.invoicecandidate.api.InvoiceCandidateQuery;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.common.rest_api.JsonExternalId;
import de.metas.rest_api.utils.MetasfreshId;
import de.metas.util.web.exception.InvalidEntityException;
import de.metas.rest_api.invoice.impl.InvoiceService;
import de.metas.rest_api.invoicecandidates.request.JsonCheckInvoiceCandidatesStatusRequest;
import de.metas.rest_api.invoicecandidates.response.JsonCheckInvoiceCandidatesStatusResponse;
import de.metas.rest_api.invoicecandidates.response.JsonCheckInvoiceCandidatesStatusResponseItem;
import de.metas.rest_api.invoicecandidates.response.JsonCheckInvoiceCandidatesStatusResponseItem.JsonCheckInvoiceCandidatesStatusResponseItemBuilder;
import de.metas.rest_api.invoicecandidates.response.JsonInvoiceStatus;
import de.metas.rest_api.invoicecandidates.response.JsonWorkPackageStatus;
import de.metas.util.Services;
import de.metas.util.lang.ExternalHeaderIdWithExternalLineIds;
import lombok.NonNull;

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
public class CheckInvoiceCandidatesStatusService
{
	private final IInvoiceCandBL invoiceCandBL = Services.get(IInvoiceCandBL.class);
	private final IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);
	private final IInvoiceDAO invoiceDAO = Services.get(IInvoiceDAO.class);

	private final InvoiceService invoicePDFService;

	public CheckInvoiceCandidatesStatusService(@NonNull final InvoiceService invoicePDFService)
	{

		this.invoicePDFService = invoicePDFService;
	}

	public JsonCheckInvoiceCandidatesStatusResponse getStatusForInvoiceCandidates(
			@NonNull final JsonCheckInvoiceCandidatesStatusRequest request)
	{
		if (request.getInvoiceCandidates().isEmpty())
		{
			throw new InvalidEntityException(TranslatableStrings.constant("The request's invoiceCandidates array may not be empty"));
		}
		final List<ExternalHeaderIdWithExternalLineIds> headerAndLineIds = InvoiceJsonConverters.fromJson(request.getInvoiceCandidates());

		final InvoiceCandidateMultiQueryBuilder multiQuery = InvoiceCandidateMultiQuery.builder();
		for (final ExternalHeaderIdWithExternalLineIds externalId : headerAndLineIds)
		{
			final InvoiceCandidateQuery query = InvoiceCandidateQuery.builder()
					.externalIds(externalId)
					.build();
			multiQuery.query(query);
		}
		final List<I_C_Invoice_Candidate> invoiceCandidateRecords = invoiceCandDAO
				.getByQuery(multiQuery.build());

		final List<JsonCheckInvoiceCandidatesStatusResponseItem> invoiceCandidates = retrieveStatus(invoiceCandidateRecords);

		final JsonCheckInvoiceCandidatesStatusResponse result = JsonCheckInvoiceCandidatesStatusResponse.builder()
				.invoiceCandidates(invoiceCandidates)
				.build();

		return result;
	}

	private List<JsonCheckInvoiceCandidatesStatusResponseItem> retrieveStatus(final List<I_C_Invoice_Candidate> invoiceCandidateRecords)
	{
		final List<JsonCheckInvoiceCandidatesStatusResponseItem> invoiceCandidatesStatus = new ArrayList<>();

		for (final I_C_Invoice_Candidate invoiceCandidateRecord : invoiceCandidateRecords)
		{
			final InvoiceCandidateId invoiceCandidateId = InvoiceCandidateId.ofRepoId(invoiceCandidateRecord.getC_Invoice_Candidate_ID());

			final List<JsonInvoiceStatus> invoicesInfo = retrieveInvoiceInfos(invoiceCandidateId);
			final List<JsonWorkPackageStatus> workPackagesInfo = retrieveWorkPackageInfo(invoiceCandidateId);

			final JsonCheckInvoiceCandidatesStatusResponseItem invoiceCandidateStatus = prepareInvoiceCandidateStatus(invoiceCandidateRecord)
					.invoices(invoicesInfo)
					.workPackages(workPackagesInfo)
					.build();

			invoiceCandidatesStatus.add(invoiceCandidateStatus);
		}

		return invoiceCandidatesStatus.stream()
				.sorted(Comparator.comparing(candidate -> candidate.getMetasfreshId().getValue()))
				.collect(ImmutableList.toImmutableList());
	}

	private List<JsonWorkPackageStatus> retrieveWorkPackageInfo(final InvoiceCandidateId invoiceCandidateId)
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

	private static JsonWorkPackageStatus toJsonForWorkPackage(final I_C_Queue_WorkPackage workPackageRecord)
	{
		return JsonWorkPackageStatus.builder()
				.error(workPackageRecord.getErrorMsg())
				.enqueued(TimeUtil.asInstant(workPackageRecord.getCreated()))
				.readyForProcessing(workPackageRecord.isReadyForProcessing())
				.metasfreshId(MetasfreshId.of(workPackageRecord.getC_Queue_WorkPackage_ID()))
				.build();
	}

	private List<JsonInvoiceStatus> retrieveInvoiceInfos(final InvoiceCandidateId invoiceCandidateId)
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

	private static JsonCheckInvoiceCandidatesStatusResponseItemBuilder prepareInvoiceCandidateStatus(final I_C_Invoice_Candidate invoiceCandidateRecord)
	{
		final BigDecimal qtyToInvoice = invoiceCandidateRecord.getQtyToInvoice();
		final BigDecimal qtyInvoiced = invoiceCandidateRecord.getQtyInvoiced();

		return JsonCheckInvoiceCandidatesStatusResponseItem.builder()
				.qtyEntered(invoiceCandidateRecord.getQtyEntered())
				.dateToInvoice(TimeUtil.asLocalDate(invoiceCandidateRecord.getDateToInvoice()))
				.dateInvoiced(TimeUtil.asLocalDate(invoiceCandidateRecord.getDateInvoiced()))
				.externalHeaderId(JsonExternalId.of(invoiceCandidateRecord.getExternalHeaderId()))
				.externalLineId(JsonExternalId.of(invoiceCandidateRecord.getExternalLineId()))
				.metasfreshId(MetasfreshId.of(invoiceCandidateRecord.getC_Invoice_Candidate_ID()))
				.qtyInvoiced(qtyInvoiced)
				.qtyToInvoice(qtyToInvoice)
				.processed(invoiceCandidateRecord.isProcessed());
	}

	private JsonInvoiceStatus toJsonInvoiceInfo(final I_C_Invoice invoice)
	{
		return JsonInvoiceStatus.builder()
				.dateInvoiced(TimeUtil.asLocalDate(invoice.getDateInvoiced()))
				.docStatus(invoice.getDocStatus())
				.documentNo(invoice.getDocumentNo())
				.metasfreshId(MetasfreshId.of(invoice.getC_Invoice_ID()))
				.pdfAvailable(invoicePDFService.hasArchive(InvoiceId.ofRepoId(invoice.getC_Invoice_ID())))
				.build();
	}

}
