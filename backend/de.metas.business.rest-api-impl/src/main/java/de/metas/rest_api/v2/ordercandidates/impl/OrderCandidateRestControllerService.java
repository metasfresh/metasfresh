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

package de.metas.rest_api.v2.ordercandidates.impl;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.async.AsyncBatchId;
import de.metas.async.api.IAsyncBatchBL;
import de.metas.bpartner.BPartnerId;
import de.metas.common.ordercandidates.v2.request.JsonOLCandCreateBulkRequest;
import de.metas.common.ordercandidates.v2.request.JsonOLCandCreateRequest;
import de.metas.common.ordercandidates.v2.request.JsonOLCandProcessRequest;
import de.metas.common.ordercandidates.v2.request.alberta.JsonAlbertaOrderInfo;
import de.metas.common.ordercandidates.v2.response.JsonGenerateOrdersResponse;
import de.metas.common.ordercandidates.v2.response.JsonOLCandCreateBulkResponse;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.impex.InputDataSourceId;
import de.metas.inout.IInOutDAO;
import de.metas.inout.InOutId;
import de.metas.invoice.InvoiceId;
import de.metas.monitoring.adapter.PerformanceMonitoringService;
import de.metas.order.IOrderBL;
import de.metas.order.OrderId;
import de.metas.ordercandidate.api.OLCand;
import de.metas.ordercandidate.api.OLCandCreateRequest;
import de.metas.ordercandidate.api.OLCandId;
import de.metas.ordercandidate.api.OLCandQuery;
import de.metas.ordercandidate.api.OLCandRepository;
import de.metas.organization.OrgId;
import de.metas.rest_api.utils.IdentifierString;
import de.metas.rest_api.v2.invoice.impl.InvoiceService;
import de.metas.rest_api.v2.invoice.impl.JSONInvoiceInfoResponse;
import de.metas.rest_api.v2.shipping.ShipmentService;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.web.exception.MissingResourceException;
import de.metas.vertical.healthcare.alberta.order.AlbertaOrderCompositeInfo;
import de.metas.vertical.healthcare.alberta.order.service.AlbertaOrderService;
import lombok.NonNull;
import org.compiere.model.I_M_InOutLine;
import org.compiere.util.Env;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static de.metas.common.util.CoalesceUtil.coalesce;

@Service
public class OrderCandidateRestControllerService
{
	private static final String DATA_SOURCE_INTERNAL_NAME = "SOURCE." + OrderCandidatesRestController.class.getName();

	private final IAsyncBatchBL asyncBatchBL = Services.get(IAsyncBatchBL.class);
	private final IOrderBL orderBL = Services.get(IOrderBL.class);
	private final IInOutDAO shipmentDAO = Services.get(IInOutDAO.class);

	private final JsonConverters jsonConverters;
	private final OLCandRepository olCandRepo;
	private final PerformanceMonitoringService perfMonService;
	private final AlbertaOrderService albertaOrderService;
	private final ShipmentService shipmentService;
	private final InvoiceService invoiceService;
	private final OrderService orderService;

	public OrderCandidateRestControllerService(
			@NonNull final JsonConverters jsonConverters,
			@NonNull final OLCandRepository olCandRepo,
			@NonNull final PerformanceMonitoringService perfMonService,
			@NonNull final AlbertaOrderService albertaOrderService,
			@NonNull final ShipmentService shipmentService,
			@NonNull final InvoiceService invoiceService,
			@NonNull final OrderService orderService)
	{
		this.jsonConverters = jsonConverters;
		this.olCandRepo = olCandRepo;
		this.perfMonService = perfMonService;
		this.albertaOrderService = albertaOrderService;
		this.shipmentService = shipmentService;
		this.invoiceService = invoiceService;
		this.orderService = orderService;
	}

	public JsonOLCandCreateBulkResponse creatOrderLineCandidatesBulk(
			@NonNull final JsonOLCandCreateBulkRequest bulkRequest,
			@NonNull final MasterdataProvider masterdataProvider)
	{
		final PerformanceMonitoringService.SpanMetadata spanMetadata = PerformanceMonitoringService.SpanMetadata.builder()
				.name("CreatOrderLineCandidatesBulk")
				.type(PerformanceMonitoringService.Type.REST_API_PROCESSING.getCode())
				.build();

		return perfMonService.monitorSpan(
				() -> creatOrderLineCandidates0(bulkRequest, masterdataProvider),
				spanMetadata);
	}

	private JsonOLCandCreateBulkResponse creatOrderLineCandidates0(
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

	public void createAlbertaOrderRecords(
			@Nullable final String orgCode,
			@NonNull final OLCand olCand,
			@NonNull final JsonAlbertaOrderInfo jsonAlbertaOrderInfo,
			@NonNull final MasterdataProvider masterdataProvider)
	{
		final AlbertaOrderCompositeInfo albertaOrderCompositeInfo = AlbertaOrderCompositeInfo.builder()
				.orgId(OrgId.ofRepoId(olCand.getAD_Org_ID()))
				.olCandId(OLCandId.ofRepoId(olCand.getId()))
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
				.updated(jsonAlbertaOrderInfo.getUpdated())
				.salesLineId(jsonAlbertaOrderInfo.getSalesLineId())
				.unit(jsonAlbertaOrderInfo.getUnit())
				.isPrivateSale(jsonAlbertaOrderInfo.getIsPrivateSale())
				.isRentalEquipment(jsonAlbertaOrderInfo.getIsRentalEquipment())
				.durationAmount(jsonAlbertaOrderInfo.getDurationAmount())
				.timePeriod(jsonAlbertaOrderInfo.getTimePeriod())
				.therapy(jsonAlbertaOrderInfo.getTherapy())
				.therapyTypes(jsonAlbertaOrderInfo.getTherapyTypes())
				.build();

		albertaOrderService.saveAlbertaOrderCompositeInfo(albertaOrderCompositeInfo);
	}

	@NonNull
	public JsonProcessCompositeResponse processOLCands(@NonNull final JsonOLCandProcessRequest request)
	{
		final Set<OLCandId> olCandIds = getOLCands(IdentifierString.of(request.getInputDataSourceName()), request.getExternalHeaderId());

		final Map<AsyncBatchId, List<OLCandId>> asyncBatchId2OLCandIds = orderService.getAsyncBathId2OLCandIds(olCandIds);

		final Set<OrderId> orderIds = orderService.generateOrderSync(asyncBatchId2OLCandIds);

		final JsonProcessCompositeResponse.JsonProcessCompositeResponseBuilder responseBuilder = JsonProcessCompositeResponse.builder()
				.orderResponse(buildGenerateOrdersResponse(orderIds));

		if (request.getShip())
		{
			final Set<InOutId> createdShipmentIds = shipmentService.generateShipmentsForOLCands(asyncBatchId2OLCandIds);

			responseBuilder.shipmentResponse(shipmentService.buildCreateShipmentResponse(createdShipmentIds));
		}

		if (request.getInvoice())
		{
			final List<I_M_InOutLine> shipmentLines = shipmentDAO.retrieveShipmentLinesForOrderId(orderIds);

			final Set<InvoiceId> invoiceIds = invoiceService.generateInvoicesFromShipmentLines(shipmentLines);

			final List<JSONInvoiceInfoResponse> invoiceInfoResponses = invoiceIds.stream()
					.map(invoiceId -> invoiceService.getInvoiceInfo(invoiceId, Env.getAD_Language()))
					.collect(ImmutableList.toImmutableList());

			responseBuilder.invoiceInfoResponse(invoiceInfoResponses);
		}

		asyncBatchId2OLCandIds.keySet().forEach(asyncBatchBL::updateProcessedFromMilestones);

		if (Boolean.TRUE.equals(request.getCloseOrder()))
		{
			orderIds.forEach(orderBL::closeOrder);
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
}
