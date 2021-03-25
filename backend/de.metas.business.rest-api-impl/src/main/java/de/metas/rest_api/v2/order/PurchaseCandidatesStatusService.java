/*
 * #%L
 * de.metas.business.rest-api-impl
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.rest_api.v2.order;

import com.google.common.collect.ImmutableList;
import de.metas.async.api.IQueueDAO;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.common.rest_api.v1.JsonExternalId;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.i18n.TranslatableStrings;
import de.metas.order.IOrderDAO;
import de.metas.order.OrderId;
import de.metas.purchasecandidate.PurchaseCandidate;
import de.metas.purchasecandidate.PurchaseCandidateId;
import de.metas.purchasecandidate.PurchaseCandidateRepository;
import de.metas.purchasecandidate.async.C_PurchaseCandidates_GeneratePurchaseOrders;
import de.metas.purchasecandidate.model.I_C_PurchaseCandidate;
import de.metas.rest_api.invoicecandidates.response.JsonWorkPackageStatus;
import de.metas.rest_api.order.JsonPurchaseCandidate;
import de.metas.rest_api.order.JsonPurchaseCandidateResponse;
import de.metas.rest_api.order.JsonPurchaseCandidatesRequest;
import de.metas.rest_api.order.JsonPurchaseOrder;
import de.metas.util.Services;
import de.metas.util.lang.ExternalHeaderIdWithExternalLineIds;
import de.metas.util.web.exception.InvalidEntityException;
import lombok.NonNull;
import org.adempiere.archive.api.IArchiveBL;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_Order;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

@Service
public class PurchaseCandidatesStatusService
{
	private final IQueueDAO queueDAO = Services.get(IQueueDAO.class);
	private final IArchiveBL archiveBL = Services.get(IArchiveBL.class);

	private final PurchaseCandidateRepository purchaseCandidateRepo;

	public PurchaseCandidatesStatusService(@NonNull final PurchaseCandidateRepository purchaseCandidateRepo)
	{
		this.purchaseCandidateRepo = purchaseCandidateRepo;
	}

	public JsonPurchaseCandidateResponse getStatusForPurchaseCandidates(
			@NonNull final JsonPurchaseCandidatesRequest request)
	{
		if (request.getPurchaseCandidates().isEmpty())
		{
			throw new InvalidEntityException(TranslatableStrings.constant("The request's purchaseCandidates array may not be empty"));
		}
		final List<ExternalHeaderIdWithExternalLineIds> headerAndLineIds = POJsonConverters.fromJson(request.getPurchaseCandidates());

		final List<PurchaseCandidate> invoiceCandidateRecords = purchaseCandidateRepo.getByExternal(headerAndLineIds);

		final List<JsonPurchaseCandidate> invoiceCandidates = retrieveStatus(invoiceCandidateRecords);

		return JsonPurchaseCandidateResponse.builder()
				.purchaseCandidates(invoiceCandidates)
				.build();
	}

	private List<JsonPurchaseCandidate> retrieveStatus(final List<PurchaseCandidate> candidates)
	{
		final List<JsonPurchaseCandidate> jsonPurchaseCandidates = new ArrayList<>();

		for (final PurchaseCandidate candidate : candidates)
		{
			final PurchaseCandidateId id = candidate.getId();

			final List<JsonPurchaseOrder> purchaseOrders = retrievePurchaseOrderInfo(id);
			final List<JsonWorkPackageStatus> workPackagesInfo = retrieveWorkPackageInfo(id);

			final JsonPurchaseCandidate invoiceCandidateStatus = prepareInvoiceCandidateStatus(candidate)
					.purchaseOrders(purchaseOrders)
					.workPackages(workPackagesInfo)
					.build();
			jsonPurchaseCandidates.add(invoiceCandidateStatus);
		}

		return jsonPurchaseCandidates.stream()
				.sorted(Comparator.comparing(candidate -> candidate.getMetasfreshId().getValue()))
				.collect(ImmutableList.toImmutableList());
	}

	private List<JsonWorkPackageStatus> retrieveWorkPackageInfo(final PurchaseCandidateId invoiceCandidateId)
	{
		final List<I_C_Queue_WorkPackage> workPackageRecords =
				queueDAO.retrieveUnprocessedWorkPackagesByEnqueuedRecord(
						C_PurchaseCandidates_GeneratePurchaseOrders.class,
						TableRecordReference.of(I_C_PurchaseCandidate.Table_Name, invoiceCandidateId));
		if (workPackageRecords.isEmpty())
		{
			return ImmutableList.of();
		}
		return workPackageRecords.stream()
				.map(PurchaseCandidatesStatusService::toJsonForWorkPackage)
				.collect(ImmutableList.toImmutableList());
	}

	private static JsonWorkPackageStatus toJsonForWorkPackage(final I_C_Queue_WorkPackage workPackageRecord)
	{
		return JsonWorkPackageStatus.builder()
				.error(workPackageRecord.getErrorMsg())
				.enqueued(TimeUtil.asInstant(workPackageRecord.getCreated()))
				.readyForProcessing(workPackageRecord.isReadyForProcessing())
				.metasfreshId(JsonMetasfreshId.of(workPackageRecord.getC_Queue_WorkPackage_ID()))
				.build();
	}

	private List<JsonPurchaseOrder> retrievePurchaseOrderInfo(final PurchaseCandidateId candidate)
	{
		final Collection<OrderId> orderIds = purchaseCandidateRepo.getOrderIdsForPurchaseCandidates(candidate);
		return Services.get(IOrderDAO.class).getByIds(orderIds)
				.stream()
				.map(this::toJsonOrder)
				.collect(ImmutableList.toImmutableList());
	}

	private static JsonPurchaseCandidate.JsonPurchaseCandidateBuilder prepareInvoiceCandidateStatus(final PurchaseCandidate candidate)
	{
		return JsonPurchaseCandidate.builder()
				.externalHeaderId(JsonExternalId.of(candidate.getExternalHeaderId().getValue()))
				.externalLineId(JsonExternalId.of(candidate.getExternalLineId().getValue()))
				.metasfreshId(JsonMetasfreshId.of(candidate.getId().getRepoId()))
				.processed(candidate.isProcessed());
	}

	private JsonPurchaseOrder toJsonOrder(final I_C_Order order)
	{
		final boolean hasArchive = hasArchive(order);

		return JsonPurchaseOrder.builder()
				.dateOrdered(TimeUtil.asZonedDateTime(order.getDateOrdered()))
				.datePromised(TimeUtil.asZonedDateTime(order.getDatePromised()))
				.docStatus(order.getDocStatus())
				.documentNo(order.getDocumentNo())
				.metasfreshId(JsonMetasfreshId.of(order.getC_Order_ID()))
				.pdfAvailable(hasArchive)
				.build();
	}

	private boolean hasArchive(final I_C_Order order)
	{
		return archiveBL.getLastArchive(TableRecordReference.of(I_C_Order.Table_Name, order.getC_Order_ID())).isPresent();
	}

}
