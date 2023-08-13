/*
 * #%L
 * de.metas.business.rest-api-impl
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.rest_api.v2.ordercandidates.impl;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.async.AsyncBatchId;
import de.metas.async.api.IAsyncBatchBL;
import de.metas.async.api.IEnqueueResult;
import de.metas.async.service.AsyncBatchService;
import de.metas.bpartner.BPartnerId;
import de.metas.common.ordercandidates.v2.request.JsonOLCandCreateBulkRequest;
import de.metas.common.ordercandidates.v2.request.JsonOLCandCreateRequest;
import de.metas.common.ordercandidates.v2.request.JsonOLCandProcessRequest;
import de.metas.common.ordercandidates.v2.request.alberta.JsonAlbertaOrderInfo;
import de.metas.common.ordercandidates.v2.request.alberta.JsonAlbertaOrderLineInfo;
import de.metas.common.ordercandidates.v2.response.JsonGenerateOrdersResponse;
import de.metas.common.ordercandidates.v2.response.JsonOLCandCreateBulkResponse;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.util.CoalesceUtil;
import de.metas.impex.InputDataSourceId;
import de.metas.order.OrderId;
import de.metas.ordercandidate.api.IOLCandDAO;
import de.metas.ordercandidate.api.OLCand;
import de.metas.ordercandidate.api.OLCandCreateRequest;
import de.metas.ordercandidate.api.OLCandId;
import de.metas.ordercandidate.api.OLCandQuery;
import de.metas.ordercandidate.api.OLCandRepository;
import de.metas.ordercandidate.api.OLCandValidatorService;
import de.metas.organization.OrgId;
import de.metas.process.PInstanceId;
import de.metas.rest_api.utils.IdentifierString;
import de.metas.rest_api.v2.invoice.impl.JsonInvoiceService;
import de.metas.rest_api.v2.shipping.JsonShipmentService;
import de.metas.salesorder.candidate.ProcessOLCandsRequest;
import de.metas.salesorder.candidate.ProcessOLCandsWorkpackageEnqueuer;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.web.exception.MissingResourceException;
import de.metas.vertical.healthcare.alberta.order.AlbertaOrderInfo;
import de.metas.vertical.healthcare.alberta.order.AlbertaOrderLineInfo;
import de.metas.vertical.healthcare.alberta.order.service.AlbertaOrderService;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.compiere.util.DB;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

import static de.metas.async.Async_Constants.C_Async_Batch_InternalName_ProcessOLCands;
import static de.metas.common.util.CoalesceUtil.coalesce;

@Service
public class OrderCandidateRestControllerService
{
	private static final String DATA_SOURCE_INTERNAL_NAME = "SOURCE." + OrderCandidatesRestController.class.getName();

	private final IAsyncBatchBL asyncBatchBL = Services.get(IAsyncBatchBL.class);
	private final IOLCandDAO olCandDAO = Services.get(IOLCandDAO.class);

	private final JsonConverters jsonConverters;
	private final OLCandRepository olCandRepo;
	private final AlbertaOrderService albertaOrderService;
	private final JsonInvoiceService jsonInvoiceService;
	private final JsonShipmentService jsonShipmentService;
	private final ProcessOLCandsWorkpackageEnqueuer processOLCandsWorkpackageEnqueuer;
	private final AsyncBatchService asyncBatchService;

	public OrderCandidateRestControllerService(
			@NonNull final JsonConverters jsonConverters,
			@NonNull final OLCandRepository olCandRepo,
			@NonNull final AlbertaOrderService albertaOrderService,
			@NonNull final JsonShipmentService jsonShipmentService,
			@NonNull final JsonInvoiceService jsonInvoiceService,
			@NonNull final ProcessOLCandsWorkpackageEnqueuer processOLCandsWorkpackageEnqueuer,
			@NonNull final AsyncBatchService asyncBatchService)
	{
		this.jsonConverters = jsonConverters;
		this.olCandRepo = olCandRepo;
		this.albertaOrderService = albertaOrderService;
		this.jsonShipmentService = jsonShipmentService;
		this.jsonInvoiceService = jsonInvoiceService;
		this.processOLCandsWorkpackageEnqueuer = processOLCandsWorkpackageEnqueuer;
		this.asyncBatchService = asyncBatchService;
	}

	public JsonOLCandCreateBulkResponse creatOrderLineCandidatesBulk(
			@NonNull final JsonOLCandCreateBulkRequest bulkRequest,
			@NonNull final MasterdataProvider masterdataProvider)
	{
		final HashMap<OLCandQuery, List<OLCand>> query2OLCandList = new HashMap<>();

		final List<OLCand> olCandidates = bulkRequest
				.getRequests()
				.stream()
				.filter(request -> !wasOLCandAlreadyCreated(request, query2OLCandList))
				.map(request -> createOrderLineCandidate(request, masterdataProvider))
				.collect(ImmutableList.toImmutableList());

		return jsonConverters.toJson(olCandidates, masterdataProvider);
	}

	private boolean wasOLCandAlreadyCreated(
			@NonNull final JsonOLCandCreateRequest olCandRequest,
			@NonNull final HashMap<OLCandQuery, List<OLCand>> queryToOLCandList)
	{
		if (Check.isBlank(olCandRequest.getExternalHeaderId())
				|| Check.isBlank(olCandRequest.getExternalLineId()))
		{
			return false;
		}

		final IdentifierString inputDataSourceIdentifier = IdentifierString.of(olCandRequest.getDataSource());

		final OLCandQuery olCandQuery = OLCandQuery.builder()
				.externalHeaderId(olCandRequest.getExternalHeaderId())
				.inputDataSourceName(inputDataSourceIdentifier.asInternalName())
				.build();

		return queryToOLCandList.computeIfAbsent(olCandQuery, olCandRepo::getByQuery)
				.stream()
				.anyMatch(existingOLCand -> olCandRequest.getExternalLineId().equals(existingOLCand.getExternalLineId()));
	}

	private OLCandCreateRequest fromJson(
			@NonNull final JsonOLCandCreateRequest request,
			@NonNull final MasterdataProvider masterdataProvider)
	{
		final String dataSourceInternalNameToUse = coalesce(
				request.getDataSource(),
				"int-" + DATA_SOURCE_INTERNAL_NAME);

		final InputDataSourceId dataSourceId = masterdataProvider.getDataSourceId(
				dataSourceInternalNameToUse,
				masterdataProvider.getOrgId(request.getOrgCode()));
		if (dataSourceId == null)
		{
			throw MissingResourceException.builder()
					.resourceName("dataSource")
					.resourceIdentifier(dataSourceInternalNameToUse)
					.parentResource(request).build();
		}

		return jsonConverters
				.fromJson(request, masterdataProvider)
				.dataSourceId(dataSourceId)
				.build();
	}

	private OLCand createOrderLineCandidate(
			@NonNull final JsonOLCandCreateRequest request,
			@NonNull final MasterdataProvider masterdataProvider)
	{

		assertCanCreate(request, masterdataProvider);

		final OLCandCreateRequest candCreateRequest = fromJson(request, masterdataProvider);
		final OLCand olCand = olCandRepo.create(candCreateRequest);

		if (request.getAlbertaOrderInfo() == null)
		{
			return olCand;
		}

		createAlbertaOrderRecords(request.getOrgCode(), olCand, request.getAlbertaOrderInfo(), masterdataProvider);

		return olCand;
	}

	@NonNull
	public JsonProcessCompositeResponse processOLCands(@NonNull final JsonOLCandProcessRequest request)
	{
		final Set<OLCandId> olCandIds = getOLCands(IdentifierString.of(request.getInputDataSourceName()), request.getExternalHeaderId());

		processValidOlCands(request, olCandIds);

		final Set<OrderId> orderIds = olCandDAO.getOrderIdsByOLCandIds(olCandIds);

		final JsonProcessCompositeResponse.JsonProcessCompositeResponseBuilder responseBuilder = JsonProcessCompositeResponse.builder()
				.orderResponse(buildGenerateOrdersResponse(orderIds));

		if (CoalesceUtil.coalesceNotNull(request.getShip(), false))
		{
			responseBuilder.shipmentResponse(jsonShipmentService.buildCreateShipmentResponseFromOLCands(olCandIds));
		}

		if (CoalesceUtil.coalesceNotNull(request.getInvoice(), false))
		{
			responseBuilder.invoiceInfoResponse(jsonInvoiceService.buildInvoiceInfoResponseFromOrderIds(orderIds));
		}

		if (Boolean.TRUE.equals(request.getCloseOrder()))
		{
			responseBuilder.invoiceInfoResponse(jsonInvoiceService.buildInvoiceInfoResponseFromOrderIds(orderIds));
		}

		return responseBuilder.build();
	}

	@Nullable
	private BPartnerId resolveExternalBPartnerIdentifier(
			@Nullable final String orgCode,
			@Nullable final String externalBPartnerIdentifier,
			@NonNull final MasterdataProvider masterdataProvider)
	{
		if (Check.isBlank(externalBPartnerIdentifier))
		{
			return null;
		}

		return BPartnerId.ofRepoId(masterdataProvider
										   .getBPartnerInfoByExternalIdentifier(externalBPartnerIdentifier, orgCode)
										   .getMetasfreshId()
										   .getValue());
	}

	private void assertCanCreate(
			@NonNull final JsonOLCandCreateRequest request,
			@NonNull final MasterdataProvider masterdataProvider)
	{
		final OrgId orgId = masterdataProvider.getOrgId(request.getOrgCode());
		masterdataProvider.assertCanCreateNewOLCand(orgId);
	}

	@NonNull
	private JsonGenerateOrdersResponse buildGenerateOrdersResponse(@NonNull final Set<OrderId> orderIds)
	{
		final List<JsonMetasfreshId> orderMetasfreshIds = orderIds.stream()
				.map(OrderId::getRepoId)
				.map(JsonMetasfreshId::of)
				.collect(ImmutableList.toImmutableList());

		return JsonGenerateOrdersResponse.builder()
				.orderIds(orderMetasfreshIds)
				.build();
	}

	@NonNull
	private Set<OLCandId> getOLCands(
			@NonNull final IdentifierString identifierString,
			@NonNull final String externalHeaderId)
	{
		final OLCandQuery olCandQuery = OLCandQuery.builder()
				.externalHeaderId(externalHeaderId)
				.inputDataSourceName(identifierString.asInternalName())
				.build();

		return olCandRepo.getByQuery(olCandQuery)
				.stream()
				.map(OLCand::getId)
				.map(OLCandId::ofRepoId)
				.collect(ImmutableSet.toImmutableSet());
	}

	/**
	 * Enqueue and wait for a {@link  de.metas.salesorder.candidate.ProcessOLCandsWorkpackageProcessor}-Workpackage to take care of creating orders, shipments and invoices as required.
	 */
	private void processValidOlCands(
			@NonNull final JsonOLCandProcessRequest request,
			@NonNull final Set<OLCandId> validOlCandIds)
	{
		if (validOlCandIds.isEmpty())
		{
			return;
		}

		// start another async-batch - just to wait for
		// ProcessOLCandsWorkpackageProcessor to finish doing the work and enqueing sub-processors.
		final AsyncBatchId processOLCandsAsyncBatchId = asyncBatchBL.newAsyncBatch(C_Async_Batch_InternalName_ProcessOLCands);

		final Supplier<IEnqueueResult> action = () -> {
			final PInstanceId validOLCandIdsSelectionId = DB.createT_Selection(validOlCandIds, ITrx.TRXNAME_None);

			final ProcessOLCandsRequest enqueueRequest = ProcessOLCandsRequest.builder()
					.pInstanceId(validOLCandIdsSelectionId)
					.ship(CoalesceUtil.coalesceNotNull(request.getShip(), false))
					.invoice(CoalesceUtil.coalesceNotNull(request.getInvoice(), false))
					.closeOrder(CoalesceUtil.coalesceNotNull(request.getCloseOrder(), false))
					.build();

			processOLCandsWorkpackageEnqueuer.enqueue(enqueueRequest, processOLCandsAsyncBatchId);

			return () -> 1; // we always enqueue one workpackage
		};

		asyncBatchService.executeBatch(action, processOLCandsAsyncBatchId);
	}

	private void createAlbertaOrderRecords(
			@Nullable final String orgCode,
			@NonNull final OLCand olCand,
			@NonNull final JsonAlbertaOrderInfo jsonAlbertaOrderInfo,
			@NonNull final MasterdataProvider masterdataProvider)
	{
		final OLCandId olCandId = OLCandId.ofRepoId(olCand.getId());

		final AlbertaOrderLineInfo albertaOrderLineInfo = jsonAlbertaOrderInfo.getJsonAlbertaOrderLineInfo() != null
				? buildAlbertaOrderLineInfo(jsonAlbertaOrderInfo.getJsonAlbertaOrderLineInfo(), olCandId)
				: null;

		final AlbertaOrderInfo albertaOrderInfo = AlbertaOrderInfo.builder()
				.orgId(OrgId.ofRepoId(olCand.getAD_Org_ID()))
				.olCandId(olCandId)
				.externalId(jsonAlbertaOrderInfo.getExternalId())
				.rootId(jsonAlbertaOrderInfo.getRootId())
				.creationDate(jsonAlbertaOrderInfo.getCreationDate())
				.startDate(jsonAlbertaOrderInfo.getStartDate())
				.endDate(jsonAlbertaOrderInfo.getEndDate())
				.dayOfDelivery(jsonAlbertaOrderInfo.getDayOfDelivery())
				.nextDelivery(jsonAlbertaOrderInfo.getNextDelivery())
				.doctorBPartnerId(resolveExternalBPartnerIdentifier(orgCode, jsonAlbertaOrderInfo.getDoctorBPartnerIdentifier(), masterdataProvider))
				.pharmacyBPartnerId(resolveExternalBPartnerIdentifier(orgCode, jsonAlbertaOrderInfo.getPharmacyBPartnerIdentifier(), masterdataProvider))
				.isInitialCare(jsonAlbertaOrderInfo.getIsInitialCare())
				.isSeriesOrder(jsonAlbertaOrderInfo.getIsSeriesOrder())
				.isArchived(jsonAlbertaOrderInfo.getIsArchived())
				.annotation(jsonAlbertaOrderInfo.getAnnotation())
				.deliveryInformation(jsonAlbertaOrderInfo.getDeliveryInformation())
				.deliveryNote(jsonAlbertaOrderInfo.getDeliveryNote())
				.updated(jsonAlbertaOrderInfo.getUpdated())
				.orderLine(albertaOrderLineInfo)
				.therapy(jsonAlbertaOrderInfo.getTherapy())
				.therapyTypes(jsonAlbertaOrderInfo.getTherapyTypes())
				.build();

		albertaOrderService.saveAlbertaOrderInfo(albertaOrderInfo);
	}

	@NonNull
	private AlbertaOrderLineInfo buildAlbertaOrderLineInfo(@NonNull final JsonAlbertaOrderLineInfo jsonAlbertaOrderLineInfo, @NonNull final OLCandId olCandId)
	{
		return AlbertaOrderLineInfo.builder()
				.externalId(jsonAlbertaOrderLineInfo.getExternalId())
				.olCandId(olCandId)
				.salesLineId(jsonAlbertaOrderLineInfo.getSalesLineId())
				.unit(jsonAlbertaOrderLineInfo.getUnit())
				.isPrivateSale(jsonAlbertaOrderLineInfo.getIsPrivateSale())
				.isRentalEquipment(jsonAlbertaOrderLineInfo.getIsRentalEquipment())
				.durationAmount(jsonAlbertaOrderLineInfo.getDurationAmount())
				.timePeriod(jsonAlbertaOrderLineInfo.getTimePeriod())
				.updated(jsonAlbertaOrderLineInfo.getUpdated())
				.build();
	}
}
