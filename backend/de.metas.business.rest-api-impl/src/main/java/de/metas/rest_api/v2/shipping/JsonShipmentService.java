/*
 * #%L
 * de.metas.business.rest-api-impl
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

package de.metas.rest_api.v2.shipping;

import ch.qos.logback.classic.Level;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import de.metas.async.AsyncBatchId;
import de.metas.async.QueueWorkPackageId;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.BPartnerQuery;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.rest_api.v2.JsonAttributeInstance;
import de.metas.common.shipping.v2.shipment.JsonCreateShipmentInfo;
import de.metas.common.shipping.v2.shipment.JsonCreateShipmentRequest;
import de.metas.common.shipping.v2.shipment.JsonCreateShipmentResponse;
import de.metas.common.shipping.v2.shipment.JsonProcessShipmentRequest;
import de.metas.common.shipping.v2.shipment.ShipmentScheduleIdentifier;
import de.metas.handlingunits.shipmentschedule.api.GenerateShipmentsRequest;
import de.metas.handlingunits.shipmentschedule.api.M_ShipmentSchedule_QuantityTypeToUse;
import de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleEnqueuer;
import de.metas.handlingunits.shipmentschedule.api.ShipmentService;
import de.metas.handlingunits.shipmentschedule.api.ShippingInfoCache;
import de.metas.handlingunits.shipmentschedule.spi.impl.ShipmentScheduleExternalInfo;
import de.metas.inout.IInOutDAO;
import de.metas.inout.InOutId;
import de.metas.inout.InOutLineId;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.api.ApplyShipmentScheduleChangesRequest;
import de.metas.inoutcandidate.api.IShipmentScheduleAllocDAO;
import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.inoutcandidate.api.IShipmentScheduleEffectiveBL;
import de.metas.inoutcandidate.api.IShipmentSchedulePA;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule_QtyPicked;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.InvoiceService;
import de.metas.location.CountryId;
import de.metas.location.ICountryCodeFactory;
import de.metas.location.ICountryDAO;
import de.metas.logging.LogManager;
import de.metas.order.DeliveryRule;
import de.metas.order.OrderLineId;
import de.metas.ordercandidate.api.IOLCandDAO;
import de.metas.ordercandidate.api.OLCand;
import de.metas.ordercandidate.api.OLCandId;
import de.metas.ordercandidate.api.OLCandQuery;
import de.metas.ordercandidate.api.OLCandRepository;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.product.IProductDAO;
import de.metas.product.IProductDAO.ProductQuery;
import de.metas.product.ProductId;
import de.metas.rest_api.v2.invoice.impl.JSONInvoiceInfoResponse;
import de.metas.rest_api.v2.invoice.impl.JsonInvoiceService;
import de.metas.rest_api.v2.ordercandidates.impl.JsonProcessCompositeResponse;
import de.metas.shipping.IShipperDAO;
import de.metas.shipping.ShipperId;
import de.metas.util.Check;
import de.metas.util.ILoggable;
import de.metas.util.Loggables;
import de.metas.util.Services;
import de.metas.util.collections.CollectionUtils;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.api.CreateAttributeInstanceReq;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Service
public class JsonShipmentService
{
	private static final Logger logger = LogManager.getLogger(JsonShipmentService.class);

	private final IShipmentScheduleBL shipmentScheduleBL = Services.get(IShipmentScheduleBL.class);
	private final IShipmentScheduleEffectiveBL scheduleEffectiveBL = Services.get(IShipmentScheduleEffectiveBL.class);
	private final IBPartnerDAO bPartnerDAO = Services.get(IBPartnerDAO.class);
	private final ICountryDAO countryDAO = Services.get(ICountryDAO.class);
	private final ICountryCodeFactory countryCodeFactory = Services.get(ICountryCodeFactory.class);
	private final IProductDAO productDAO = Services.get(IProductDAO.class);
	private final IShipperDAO shipperDAO = Services.get(IShipperDAO.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	private final IShipmentScheduleAllocDAO shipmentScheduleAllocDAO = Services.get(IShipmentScheduleAllocDAO.class);
	private final IInOutDAO inOutDAO = Services.get(IInOutDAO.class);
	private final IShipmentSchedulePA shipmentSchedulePA = Services.get(IShipmentSchedulePA.class);
	private final IOLCandDAO olCandDAO = Services.get(IOLCandDAO.class);

	private final AttributeSetHelper attributeSetHelper;
	private final JsonInvoiceService jsonInvoiceService;
	private final OLCandRepository olCandRepo;
	private final ShipmentService shipmentService;
	private final InvoiceService invoiceService;

	public JsonShipmentService(
			@NonNull final AttributeSetHelper attributeSetHelper,
			@NonNull final JsonInvoiceService jsonInvoiceService,
			@NonNull final OLCandRepository olCandRepo,
			@NonNull final ShipmentService shipmentService,
			@NonNull final InvoiceService invoiceService)
	{
		this.attributeSetHelper = attributeSetHelper;
		this.jsonInvoiceService = jsonInvoiceService;
		this.olCandRepo = olCandRepo;
		this.shipmentService = shipmentService;
		this.invoiceService = invoiceService;
	}

	@NonNull
	public ImmutableSet<QueueWorkPackageId> updateShipmentSchedulesAndGenerateShipments(@NonNull final JsonCreateShipmentRequest request)
	{
		updateShipmentSchedules(request);

		final Map<AsyncBatchId, List<CreateShipmentInfoCandidate>> asyncBatchId2ShipmentCandidates =
				getShipmentInfoCandidateByAsyncBatchId(request.getCreateShipmentInfoList());

		return asyncBatchId2ShipmentCandidates.keySet()
				.stream()
				.map(asyncBatchId -> toGenerateShipmentsRequest(asyncBatchId2ShipmentCandidates.get(asyncBatchId), asyncBatchId))
				.map(shipmentService::generateShipments)
				.map(ShipmentScheduleEnqueuer.Result::getEnqueuedPackageIds)
				.flatMap(List::stream)
				.collect(ImmutableSet.toImmutableSet());
	}

	@NonNull
	public JsonProcessCompositeResponse processShipmentSchedules(@NonNull final JsonProcessShipmentRequest request)
	{
		final ILoggable loggable = Loggables.withLogger(logger, Level.DEBUG);

		final JsonCreateShipmentRequest shipmentRequest = request.getCreateShipmentRequest();
		loggable.addLog("processShipmentSchedules - start updating shipment schedules");
		updateShipmentSchedules(shipmentRequest);
		loggable.addLog("processShipmentSchedules - finished updating shipment schedules");

		final Map<AsyncBatchId, List<CreateShipmentInfoCandidate>> asyncBatchId2ShipmentCandidates =
				getShipmentInfoCandidateByAsyncBatchId(request.getCreateShipmentRequest().getCreateShipmentInfoList());

		final List<GenerateShipmentsRequest> generateShipmentsRequestList = asyncBatchId2ShipmentCandidates.keySet()
				.stream()
				.map(asyncBatchId -> toGenerateShipmentsRequest(asyncBatchId2ShipmentCandidates.get(asyncBatchId), asyncBatchId))
				.collect(ImmutableList.toImmutableList());

		final ImmutableSet.Builder<InOutId> createdShipmentIdsCollector = ImmutableSet.builder();
		final ImmutableList.Builder<JSONInvoiceInfoResponse> invoiceInfoResponseCollector = ImmutableList.builder();

		for (final GenerateShipmentsRequest generateShipmentRequest : generateShipmentsRequestList)
		{
			if (generateShipmentRequest.getScheduleIds().isEmpty())
			{
				loggable.addLog("processShipmentSchedules - skip generateShipmentRequest without ShipmentScheduleIds; generateShipmentRequest={}", generateShipmentRequest);
				continue;
			}

			final AsyncBatchId currentBatchId = generateShipmentRequest.getAsyncBatchId();
			
			loggable.addLog("processShipmentSchedules - start creating shipments with currentBatchId={}", AsyncBatchId.toRepoId(currentBatchId));
			shipmentService.generateShipments(generateShipmentRequest);
			final Set<InOutId> createdInoutIds = shipmentService.retrieveInOutIdsByScheduleIds(generateShipmentRequest.getScheduleIds());

			loggable.addLog("processShipmentSchedules - finished creating shipments with currentBatchId={}; M_InOut_IDs={}",
							currentBatchId, createdInoutIds);
			createdShipmentIdsCollector.addAll(createdInoutIds);

			if (request.getInvoice())
			{
				loggable.addLog("processShipmentSchedules - start creating invoices with currentBatchId={}", AsyncBatchId.toRepoId(currentBatchId));
				final List<JSONInvoiceInfoResponse> createInvoiceInfos = generateInvoicesForShipmentScheduleIds(generateShipmentRequest.getScheduleIds());

				loggable.addLog("processShipmentSchedules - finished creating invoices with currentBatchId={}; invoiceIds={}",
								currentBatchId, createdInoutIds);
				invoiceInfoResponseCollector.addAll(createInvoiceInfos);
			}

			if (request.getCloseShipmentSchedule())
			{
				loggable.addLog("processShipmentSchedules - start closing shipmentSchedules");
				generateShipmentRequest.getScheduleIds().forEach(shipmentScheduleBL::closeShipmentSchedule);
				loggable.addLog("processShipmentSchedules - finished closing shipmentSchedules");
			}
		}

		final JsonProcessCompositeResponse.JsonProcessCompositeResponseBuilder responseBuilder = JsonProcessCompositeResponse.builder();
		responseBuilder.shipmentResponse(buildCreateShipmentResponse(createdShipmentIdsCollector.build()));

		if (request.getInvoice())
		{
			responseBuilder.invoiceInfoResponse(invoiceInfoResponseCollector.build());
		}

		return responseBuilder.build();
	}

	private void updateShipmentSchedules(@NonNull final JsonCreateShipmentRequest request)
	{
		final ShippingInfoCache cache = initShippingInfoCache();

		cache.warmUpForShipmentScheduleIds(extractShipmentScheduleIds(request));
		cache.warmUpForShipperInternalNames(extractShippersInternalName(request));

		validateRequest(request, cache);
		updateShipmentSchedules(request.getCreateShipmentInfoList(), cache);
	}

	@NonNull
	private List<JSONInvoiceInfoResponse> generateInvoicesForShipmentScheduleIds(@NonNull final Set<ShipmentScheduleId> shipmentScheduleIds)
	{
		final List<I_M_InOutLine> shipmentLines = shipmentService.retrieveInOutLineByShipScheduleId(shipmentScheduleIds);

		final Set<InvoiceId> invoiceIds = invoiceService.generateInvoicesFromShipmentLines(shipmentLines);

		return invoiceIds.stream()
				.map(invoiceId -> jsonInvoiceService.getInvoiceInfo(invoiceId, Env.getAD_Language()))
				.collect(ImmutableList.toImmutableList());
	}

	private ImmutableSet<ShipmentScheduleId> extractShipmentScheduleIds(@NonNull final JsonCreateShipmentRequest request)
	{
		return request.getCreateShipmentInfoList()
				.stream()
				.map(this::extractShipmentScheduleId)
				.collect(ImmutableSet.toImmutableSet());
	}

	private ImmutableSet<String> extractShippersInternalName(@NonNull final JsonCreateShipmentRequest request)
	{
		return request.getCreateShipmentInfoList()
				.stream()
				.map(JsonCreateShipmentInfo::getShipperInternalName)
				.filter(Check::isNotBlank)
				.collect(ImmutableSet.toImmutableSet());
	}

	@NonNull
	private ShipmentScheduleId extractShipmentScheduleId(final JsonCreateShipmentInfo createShipmentInfo)
	{
		final ShipmentScheduleIdentifier jsonScheduleIdentifier = createShipmentInfo.getShipmentScheduleIdentifier();

		if (jsonScheduleIdentifier == null)
		{
			return ShipmentScheduleId.ofRepoId(createShipmentInfo.getShipmentScheduleId().getValue());
		}
		else if (jsonScheduleIdentifier.identifiedByHeaderAndLineId())
		{
			return getShipmentScheduleIdByExternalHeaderAndLineId(jsonScheduleIdentifier);
		}
		else
		{
			return ShipmentScheduleId.ofRepoId(jsonScheduleIdentifier.getShipmentScheduleId().getValue());
		}
	}

	private void validateRequest(
			@NonNull final JsonCreateShipmentRequest request,
			@NonNull final ShippingInfoCache cache)
	{
		if (Check.isEmpty(request.getCreateShipmentInfoList()))
		{
			throw new AdempiereException("Empty request!");
		}

		request.getCreateShipmentInfoList().forEach(createShipmentInfo -> validateJsonCreateShipmentInfo(createShipmentInfo, cache));
	}

	private void validateJsonCreateShipmentInfo(
			@NonNull final JsonCreateShipmentInfo createShipmentInfo,
			@NonNull final ShippingInfoCache cache)
	{
		final ShipmentScheduleId shipmentScheduleId = extractShipmentScheduleId(createShipmentInfo);

		// will throw error if no schedule is found
		final I_M_ShipmentSchedule shipmentSchedule = cache.getShipmentScheduleById(shipmentScheduleId);

		if (shipmentSchedule.isProcessed())
		{
			throw new AdempiereException("M_ShipmentSchedule was already processed!")
					.appendParametersToMessage()
					.setParameter("M_ShipmentSchedule_ID", shipmentSchedule.getM_ShipmentSchedule_ID());
		}

		if (Check.isNotBlank(createShipmentInfo.getProductSearchKey()))
		{
			final ProductQuery query = ProductQuery.builder().value(createShipmentInfo.getProductSearchKey())
					.orgId(OrgId.ofRepoId(shipmentSchedule.getAD_Org_ID()))
					.includeAnyOrg(true) // include articles with org=*
					.build();
			final ProductId incomingProductId = productDAO.retrieveProductIdBy(query);

			if (incomingProductId == null || incomingProductId.getRepoId() != shipmentSchedule.getM_Product_ID())
			{
				throw new AdempiereException("Inconsistent productSearchKey!")
						.appendParametersToMessage()
						.setParameter("M_ShipmentSchedule_ID", shipmentSchedule.getM_ShipmentSchedule_ID())
						.setParameter("AD_Org_ID", shipmentSchedule.getAD_Org_ID())
						.setParameter("jsonCreateShipmentInfo.productSearchKey", createShipmentInfo.getProductSearchKey())
						.setParameter("productSearchKey belongs to M_Product_ID", ProductId.toRepoId(incomingProductId))
						.setParameter("M_ShipmentSchedule.M_Product_ID", shipmentSchedule.getM_Product_ID());
			}
		}
	}

	private void updateShipmentSchedules(
			@NonNull final List<JsonCreateShipmentInfo> createShipmentInfos,
			@NonNull final ShippingInfoCache cache)
	{
		for (final JsonCreateShipmentInfo createShipmentInfo : createShipmentInfos)
		{
			final UpdateShipmentScheduleRequest request = toUpdateShipmentScheduleRequestOrNull(createShipmentInfo, cache);
			if (request == null)
			{
				logger.debug("Skip empty request: {}", createShipmentInfo);
				continue;
			}

			updateShipmentSchedule(request, cache);
		}
	}

	private void updateShipmentSchedule(
			@NonNull final UpdateShipmentScheduleRequest request,
			@NonNull final ShippingInfoCache cache)
	{
		shipmentScheduleBL.applyShipmentScheduleChanges(toApplyShipmentScheduleChangesRequest(request, cache));
	}

	private ApplyShipmentScheduleChangesRequest toApplyShipmentScheduleChangesRequest(
			@NonNull final UpdateShipmentScheduleRequest request,
			@NonNull final ShippingInfoCache cache)
	{
		return ApplyShipmentScheduleChangesRequest.builder()
				.shipmentScheduleId(request.getShipmentScheduleId())
				.deliveryDate(request.getDeliveryDate())
				.deliveryRule(request.getDeliveryRule())
				.attributes(request.getAttributes())
				.bPartnerLocationIdOverride(extractBPartnerLocationId(request, cache).orElse(null))
				.shipperId(request.getShipperId())
				.doNotInvalidateOnChange(true) // don't invalidate it; we don't need the update processor to deal with it; instead we'll just create the shipment
				.build();
	}

	@NonNull
	private Optional<BPartnerLocationId> extractBPartnerLocationId(
			@NonNull final UpdateShipmentScheduleRequest request,
			@NonNull final ShippingInfoCache cache)
	{
		if (request.getBPartnerLocation() != null)
		{
			final ShipmentScheduleId shipmentScheduleId = request.getShipmentScheduleId();

			final Optional<BPartnerId> bpartnerIdOverride = getBPartnerIdByValue(request.getBPartnerCode());
			final BPartnerId bpartnerId = bpartnerIdOverride.orElseGet(() -> cache.getBPartnerId(shipmentScheduleId));

			return getBPartnerLocationBy(bpartnerId, request.getBPartnerLocation());
		}
		else
		{
			return Optional.empty();
		}
	}

	@Nullable
	private UpdateShipmentScheduleRequest toUpdateShipmentScheduleRequestOrNull(
			@NonNull final JsonCreateShipmentInfo createShipmentInfo,
			@NonNull final ShippingInfoCache cache)
	{
		final LocalDateTime deliveryDate = createShipmentInfo.getMovementDate();
		final LocationBasicInfo bPartnerLocation = LocationBasicInfo.ofNullable(createShipmentInfo.getShipToLocation(), countryCodeFactory)
				.orElse(null);
		final String bpartnerCode = createShipmentInfo.getBusinessPartnerSearchKey();
		final List<JsonAttributeInstance> attributes = createShipmentInfo.getAttributes();
		final DeliveryRule deliveryRule = DeliveryRule.ofNullableCode(createShipmentInfo.getDeliveryRule());
		final ShipperId shipperId = cache.getShipperId(createShipmentInfo.getShipperInternalName());

		if (deliveryDate == null
				&& bPartnerLocation == null
				&& Check.isBlank(bpartnerCode)
				&& Check.isEmpty(attributes)
				&& deliveryRule == null
				&& shipperId == null)
		{
			return null;
		}

		final ShipmentScheduleId shipmentScheduleId = extractShipmentScheduleId(createShipmentInfo);
		final OrgId orgId = cache.getOrgId(shipmentScheduleId);
		final ZoneId timeZoneId = orgDAO.getTimeZone(orgId);

		final List<CreateAttributeInstanceReq> attributeInstanceRequestList = Check.isEmpty(attributes)
				? null
				: attributeSetHelper.toCreateAttributeInstanceReqList(attributes);

		return UpdateShipmentScheduleRequest.builder()
				.shipmentScheduleId(shipmentScheduleId)
				.bPartnerCode(bpartnerCode)
				.bPartnerLocation(bPartnerLocation)
				.attributes(attributeInstanceRequestList)
				.deliveryDate(TimeUtil.asZonedDateTime(deliveryDate, timeZoneId))
				.deliveryRule(deliveryRule)
				.shipperId(shipperId)
				.build();
	}

	@NonNull
	private GenerateShipmentsRequest toGenerateShipmentsRequest(
			@NonNull final List<CreateShipmentInfoCandidate> createShipmentInfoList,
			@NonNull final AsyncBatchId asyncBatchId)
	{
		final ImmutableMap.Builder<ShipmentScheduleId, ShipmentScheduleExternalInfo> scheduleId2ExternalInfo = new ImmutableMap.Builder<>();
		final ImmutableMap.Builder<ShipmentScheduleId, BigDecimal> scheduleToQuantityToDeliver = new ImmutableMap.Builder<>();

		final ImmutableSet.Builder<ShipmentScheduleId> shipmentScheduleIdsBuilder = new ImmutableSet.Builder<>();

		for (final CreateShipmentInfoCandidate createShipmentCandidate : createShipmentInfoList)
		{
			final ShipmentScheduleId shipmentScheduleId = createShipmentCandidate.getShipmentScheduleId();
			final JsonCreateShipmentInfo createShipmentInfo = createShipmentCandidate.getCreateShipmentInfo();

			shipmentScheduleIdsBuilder.add(shipmentScheduleId);

			if (Check.isNotBlank(createShipmentInfo.getDocumentNo()))
			{
				final ShipmentScheduleExternalInfo externalInfo = ShipmentScheduleExternalInfo
						.builder()
						.documentNo(createShipmentInfo.getDocumentNo())
						.build();
				scheduleId2ExternalInfo.put(shipmentScheduleId, externalInfo);
			}

			final BigDecimal qtyToDeliverInStockingUOM = createShipmentInfo.getMovementQuantity();
			if (qtyToDeliverInStockingUOM != null)
			{
				scheduleToQuantityToDeliver.put(shipmentScheduleId, qtyToDeliverInStockingUOM);
			}
		}

		return GenerateShipmentsRequest.builder()
				.asyncBatchId(asyncBatchId)
				.scheduleIds(shipmentScheduleIdsBuilder.build())
				.scheduleToExternalInfo(scheduleId2ExternalInfo.build())
				.scheduleToQuantityToDeliverOverride(scheduleToQuantityToDeliver.build())
				.quantityTypeToUse(M_ShipmentSchedule_QuantityTypeToUse.TYPE_QTY_TO_DELIVER)
				.isCompleteShipment(true)
				.build();
	}

	private Optional<BPartnerId> getBPartnerIdByValue(@Nullable final String bPartnerValue)
	{
		if (Check.isEmpty(bPartnerValue))
		{
			return Optional.empty();
		}
		else
		{
			return bPartnerDAO.retrieveBPartnerIdBy(BPartnerQuery.builder()
															.bpartnerValue(bPartnerValue)
															.build());
		}
	}

	private Optional<BPartnerLocationId> getBPartnerLocationBy(
			@NonNull final BPartnerId bpartnerId,
			@NonNull final LocationBasicInfo locationBasicInfo)
	{
		final CountryId countryId = countryDAO.getCountryIdByCountryCode(locationBasicInfo.getCountryCode().getAlpha2());

		if (countryId == null)
		{
			return Optional.empty();
		}

		final IBPartnerDAO.BPartnerLocationQuery bPartnerLocationQuery = IBPartnerDAO.BPartnerLocationQuery.builder()
				.bpartnerId(bpartnerId)
				.type(IBPartnerDAO.BPartnerLocationQuery.Type.SHIP_TO)
				.applyTypeStrictly(true)
				.city(locationBasicInfo.getCity())
				.postalCode(locationBasicInfo.getPostalCode())
				.countryId(countryId)
				.build();

		return Optional.ofNullable(bPartnerDAO.retrieveBPartnerLocationId(bPartnerLocationQuery));
	}

	@NonNull
	public ImmutableMultimap<ShippedCandidateKey, InOutId> retrieveShipmentIdsByCandidateKey(@NonNull final Set<ShippedCandidateKey> shippedCandidateKeys)
	{
		final Set<ShipmentScheduleId> scheduleIds = shippedCandidateKeys.stream().map(ShippedCandidateKey::getShipmentScheduleId).collect(ImmutableSet.toImmutableSet());

		final ImmutableListMultimap<ShipmentScheduleId, I_M_ShipmentSchedule_QtyPicked> scheduleId2qtyPickedRecords = shipmentScheduleAllocDAO.retrieveOnShipmentLineRecordsByScheduleIds(scheduleIds);

		final Set<InOutLineId> inOutLineIds = scheduleId2qtyPickedRecords.values().stream()
				.map(I_M_ShipmentSchedule_QtyPicked::getM_InOutLine_ID)
				.map(InOutLineId::ofRepoId)
				.collect(ImmutableSet.toImmutableSet());

		final ImmutableMap<InOutLineId, I_M_InOut> lineId2InOut = inOutDAO.retrieveInOutByLineIds(inOutLineIds);

		final ImmutableMultimap.Builder<ShippedCandidateKey, InOutId> candidateKey2ShipmentId = ImmutableMultimap.builder();
		for (final ShippedCandidateKey candidateKey : shippedCandidateKeys)
		{
			final Supplier<AdempiereException> exceptionSupplier = () -> new AdempiereException("No Shipment was found for the M_ShipmentSchedule_ID!")
					.appendParametersToMessage()
					.setParameter("M_ShipmentSchedule_ID", ShipmentScheduleId.toRepoId(candidateKey.getShipmentScheduleId()))
					.setParameter("ShipmentDocumentNumber", candidateKey.getShipmentDocumentNo());

			final List<I_M_ShipmentSchedule_QtyPicked> qtyPickedRecords = scheduleId2qtyPickedRecords.get(candidateKey.getShipmentScheduleId());
			if (qtyPickedRecords == null)
			{
				throw exceptionSupplier.get();
			}

			final ImmutableList<InOutId> targetShipmentIds = qtyPickedRecords
					.stream()
					.map(I_M_ShipmentSchedule_QtyPicked::getM_InOutLine_ID)
					.map(InOutLineId::ofRepoId)
					.map(lineId2InOut::get)
					// don't assume that the shipment's documentNo is equal to the request's assumed documentNo
					//.filter(shipment -> candidateKey.getShipmentDocumentNo().equals(shipment.getDocumentNo()))
					.map(I_M_InOut::getM_InOut_ID)
					.map(InOutId::ofRepoId)
					.collect(ImmutableList.toImmutableList());
			if (targetShipmentIds.isEmpty())
			{
				throw exceptionSupplier.get();
			}

			candidateKey2ShipmentId.putAll(candidateKey, targetShipmentIds);
		}

		return candidateKey2ShipmentId.build();
	}

	@NonNull
	public JsonCreateShipmentResponse buildCreateShipmentResponseFromOLCands(@NonNull final Set<OLCandId> olCandIds)
	{
		final Set<ShipmentScheduleId> shipmentScheduleIds = olCandDAO.retrieveOLCandIdToOrderLineId(olCandIds)
				.values()
				.stream()
				.map(shipmentSchedulePA::getByOrderLineId)
				.filter(Objects::nonNull)
				.map(I_M_ShipmentSchedule::getM_ShipmentSchedule_ID)
				.map(ShipmentScheduleId::ofRepoId)
				.collect(ImmutableSet.toImmutableSet());

		final Set<InOutId> inOutIds = shipmentService.retrieveInOutIdsByScheduleIds(shipmentScheduleIds);

		return buildCreateShipmentResponse(inOutIds);
	}

	@NonNull
	private ShipmentScheduleId getShipmentScheduleIdByExternalHeaderAndLineId(@NonNull final ShipmentScheduleIdentifier identifier)
	{
		Check.assume(identifier.identifiedByHeaderAndLineId(),
					 "getShipmentScheduleIdByExternalHeaderAndLineId should be called for ShipmentScheduleIdentifier with externalHeaderId and externalLineId");

		final OLCandQuery query = OLCandQuery.builder()
				.externalHeaderId(identifier.getExternalHeaderId())
				.externalLineId(identifier.getExternalLineId())
				.orgId(Env.getOrgId())
				.build();

		final Set<OLCandId> olCandIds = olCandRepo.getByQuery(query)
				.stream()
				.map(OLCand::getId)
				.map(OLCandId::ofRepoId)
				.collect(ImmutableSet.toImmutableSet());

		if (olCandIds.size() != 1)
		{
			throw new AdempiereException("Number of olCands found for external header id and line id: "
												 + identifier + " != 1. Found: " + olCandIds.size());
		}

		final OLCandId olCandId = CollectionUtils.singleElement(olCandIds);

		final OrderLineId orderLineId = olCandDAO.retrieveOLCandIdToOrderLineId(ImmutableSet.of(olCandId))
				.get(olCandId);

		if (orderLineId == null)
		{
			throw new AdempiereException("No orderLineId found for olCandId: " + olCandId);
		}

		final Optional<ShipmentScheduleId> shipmentScheduleId = Optional.ofNullable(shipmentSchedulePA.getShipmentScheduleIdByOrderLineId(orderLineId));

		if (!shipmentScheduleId.isPresent())
		{
			throw new AdempiereException("No shipment schedule found for orderLineId" + orderLineId);
		}

		return shipmentScheduleId.get();
	}

	@NonNull
	private JsonCreateShipmentResponse buildCreateShipmentResponse(@NonNull final Set<InOutId> shipmentIds)
	{
		return JsonCreateShipmentResponse.builder()
				.createdShipmentIds(shipmentIds.stream()
											.map(InOutId::getRepoId)
											.map(JsonMetasfreshId::of)
											.collect(ImmutableList.toImmutableList()))
				.build();
	}

	@NonNull
	private Map<AsyncBatchId, List<CreateShipmentInfoCandidate>> getShipmentInfoCandidateByAsyncBatchId(@NonNull final List<JsonCreateShipmentInfo> createShipmentInfos)
	{
		final List<CreateShipmentInfoCandidate> createShipmentCandidates = createShipmentInfos.stream()
				.map(this::buildCreateShipmentCandidate)
				.collect(ImmutableList.toImmutableList());

		final Map<ShipmentScheduleId, CreateShipmentInfoCandidate> candidateInfoById = Maps.uniqueIndex(createShipmentCandidates, CreateShipmentInfoCandidate::getShipmentScheduleId);

		final Map<AsyncBatchId, ArrayList<ShipmentScheduleId>> asyncBatchId2ScheduleIds = shipmentService.getShipmentScheduleIdByAsyncBatchId(candidateInfoById.keySet());

		return asyncBatchId2ScheduleIds.entrySet()
				.stream()
				.collect(Collectors.toMap(Map.Entry::getKey,
										  entry -> entry.getValue()
												  .stream()
												  .map(candidateInfoById::get)
												  .collect(ImmutableList.toImmutableList())));
	}

	@NonNull
	private CreateShipmentInfoCandidate buildCreateShipmentCandidate(@NonNull final JsonCreateShipmentInfo jsonCreateShipmentInfo)
	{
		final ShipmentScheduleId shipmentScheduleId = extractShipmentScheduleId(jsonCreateShipmentInfo);

		return CreateShipmentInfoCandidate.builder()
				.createShipmentInfo(jsonCreateShipmentInfo)
				.shipmentScheduleId(shipmentScheduleId)
				.build();
	}

	private ShippingInfoCache initShippingInfoCache()
	{
		return ShippingInfoCache.builder()
				.shipmentScheduleBL(shipmentScheduleBL)
				.scheduleEffectiveBL(scheduleEffectiveBL)
				.shipperDAO(shipperDAO)
				.build();
	}

	@Value
	@Builder
	private static class CreateShipmentInfoCandidate
	{
		@NonNull
		ShipmentScheduleId shipmentScheduleId;
		@NonNull
		JsonCreateShipmentInfo createShipmentInfo;
	}
}
