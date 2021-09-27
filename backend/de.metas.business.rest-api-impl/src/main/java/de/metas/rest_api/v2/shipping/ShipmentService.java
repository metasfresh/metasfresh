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
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableSet;
import de.metas.async.AsyncBatchId;
import de.metas.async.QueueWorkPackageId;
import de.metas.async.api.IAsyncBatchBL;
import de.metas.async.asyncbatchmilestone.AsyncBatchMilestone;
import de.metas.async.asyncbatchmilestone.AsyncBatchMilestoneId;
import de.metas.async.asyncbatchmilestone.AsyncBatchMilestoneObserver;
import de.metas.async.asyncbatchmilestone.AsyncBathMilestoneService;
import de.metas.async.asyncbatchmilestone.MilestoneName;
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
import de.metas.handlingunits.shipmentschedule.api.M_ShipmentSchedule_QuantityTypeToUse;
import de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleEnqueuer;
import de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleEnqueuer.ShipmentScheduleWorkPackageParameters;
import de.metas.handlingunits.shipmentschedule.spi.impl.ShipmentScheduleExternalInfo;
import de.metas.inout.IInOutDAO;
import de.metas.inout.InOutId;
import de.metas.inout.InOutLineId;
import de.metas.inoutcandidate.ShipmentScheduleId;
import de.metas.inoutcandidate.api.ApplyShipmentScheduleChangesRequest;
import de.metas.inoutcandidate.api.IShipmentScheduleAllocDAO;
import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.inoutcandidate.api.IShipmentScheduleEffectiveBL;
import de.metas.inoutcandidate.api.IShipmentSchedulePA;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule_QtyPicked;
import de.metas.invoice.InvoiceId;
import de.metas.location.CountryId;
import de.metas.location.ICountryCodeFactory;
import de.metas.location.ICountryDAO;
import de.metas.logging.LogManager;
import de.metas.order.DeliveryRule;
import de.metas.order.OrderLineId;
import de.metas.ordercandidate.api.IOLCandDAO;
import de.metas.ordercandidate.api.IOLCandEffectiveValuesBL;
import de.metas.ordercandidate.api.OLCand;
import de.metas.ordercandidate.api.OLCandId;
import de.metas.ordercandidate.api.OLCandQuery;
import de.metas.ordercandidate.api.OLCandRepository;
import de.metas.ordercandidate.model.I_C_OLCand;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.process.IADPInstanceDAO;
import de.metas.product.IProductDAO;
import de.metas.product.IProductDAO.ProductQuery;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.rest_api.v2.invoice.impl.InvoiceService;
import de.metas.rest_api.v2.invoice.impl.JSONInvoiceInfoResponse;
import de.metas.rest_api.v2.ordercandidates.impl.JsonProcessCompositeResponse;
import de.metas.shipping.IShipperDAO;
import de.metas.shipping.ShipperId;
import de.metas.uom.IUOMConversionBL;
import de.metas.util.Check;
import de.metas.util.ILoggable;
import de.metas.util.Loggables;
import de.metas.util.NumberUtils;
import de.metas.util.Services;
import de.metas.util.collections.CollectionUtils;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.api.CreateAttributeInstanceReq;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.I_M_Shipper;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;

import static de.metas.async.Async_Constants.C_Async_Batch_InternalName_ShipmentSchedule;

@Service
public class ShipmentService
{
	private static final Logger logger = LogManager.getLogger(ShipmentService.class);

	private final IQueryBL queryBL = Services.get(IQueryBL.class);
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
	private final IADPInstanceDAO adPInstanceDAO = Services.get(IADPInstanceDAO.class);
	private final ITrxManager trxManager = Services.get(ITrxManager.class);
	private final IShipmentSchedulePA shipmentSchedulePA = Services.get(IShipmentSchedulePA.class);
	private final IOLCandDAO olCandDAO = Services.get(IOLCandDAO.class);
	private final IAsyncBatchBL asyncBatchBL = Services.get(IAsyncBatchBL.class);
	private final IOLCandEffectiveValuesBL olCandEffectiveValuesBL = Services.get(IOLCandEffectiveValuesBL.class);
	private final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);

	private final AttributeSetHelper attributeSetHelper;
	private final AsyncBatchMilestoneObserver asyncBatchMilestoneObserver;
	private final AsyncBathMilestoneService asyncBathMilestoneService;
	private final InvoiceService invoiceService;
	private final OLCandRepository olCandRepo;

	public ShipmentService(
			@NonNull final AttributeSetHelper attributeSetHelper,
			@NonNull final AsyncBatchMilestoneObserver asyncBatchMilestoneObserver,
			@NonNull final AsyncBathMilestoneService asyncBathMilestoneService,
			@NonNull final InvoiceService invoiceService,
			@NonNull final OLCandRepository olCandRepo)
	{
		this.attributeSetHelper = attributeSetHelper;
		this.asyncBatchMilestoneObserver = asyncBatchMilestoneObserver;
		this.asyncBathMilestoneService = asyncBathMilestoneService;
		this.invoiceService = invoiceService;
		this.olCandRepo = olCandRepo;
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
				.map(this::generateShipments)
				.map(ShipmentScheduleEnqueuer.Result::getEnqueuedPackageIds)
				.flatMap(List::stream)
				.collect(ImmutableSet.toImmutableSet());
	}

	@NonNull
	public Set<InOutId> generateShipmentsForOLCands(@NonNull final Map<AsyncBatchId, List<OLCandId>> asyncBatchId2OLCandIds)
	{
		if (asyncBatchId2OLCandIds.isEmpty())
		{
			return ImmutableSet.of();
		}

		return asyncBatchId2OLCandIds.keySet()
				.stream()
				.map(asyncBatchId -> {
					final ImmutableSet<OLCandId> olCandIdImmutableSet = ImmutableSet.copyOf(asyncBatchId2OLCandIds.get(asyncBatchId));

					return generateShipmentForBatch(olCandIdImmutableSet, asyncBatchId);
				})
				.flatMap(Set::stream)
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

			loggable.addLog("processShipmentSchedules - start creating shipments with currentBatchId={}", AsyncBatchId.toRepoId(generateShipmentRequest.getAsyncBatchId()));
			generateShipments(generateShipmentRequest);
			final Set<InOutId> createdInoutIds = retrieveInOutIdsByScheduleIds(generateShipmentRequest.getScheduleIds());
			
			loggable.addLog("processShipmentSchedules - finished creating shipments with currentBatchId={}; M_InOut_IDs={}",
							AsyncBatchId.toRepoId(generateShipmentRequest.getAsyncBatchId()), createdInoutIds);
			createdShipmentIdsCollector.addAll(createdInoutIds);

			if (request.getInvoice())
			{
				loggable.addLog("processShipmentSchedules - start creating invoices with currentBatchId={}", AsyncBatchId.toRepoId(generateShipmentRequest.getAsyncBatchId()));
				final List<JSONInvoiceInfoResponse> createInvoiceInfos = generateInvoicesForShipmentScheduleIds(generateShipmentRequest.getScheduleIds());

				loggable.addLog("processShipmentSchedules - finished creating invoices with currentBatchId={}; invoiceIds={}",
								AsyncBatchId.toRepoId(generateShipmentRequest.getAsyncBatchId()), createdInoutIds);
				invoiceInfoResponseCollector.addAll(createInvoiceInfos);
			}

			asyncBatchBL.updateProcessedFromMilestones(currentBatchId);

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
	private ImmutableMap<ShipmentScheduleId, BigDecimal> getShipmentScheduleId2QtyToDeliver(
			@NonNull final Set<OLCandId> olCandIds,
			@NonNull final AsyncBatchId asyncBatchId)
	{
		final Map<OLCandId, OrderLineId> olCandId2OrderLineId = olCandDAO.retrieveOLCandIdToOrderLineId(olCandIds);

		if (olCandId2OrderLineId == null || olCandId2OrderLineId.isEmpty())
		{
			return ImmutableMap.of();
		}

		final Map<OLCandId, I_C_OLCand> olCandsById = olCandDAO.retrieveByIds(olCandIds);

		final ImmutableMap.Builder<ShipmentScheduleId, BigDecimal> scheduleId2QtyShipped = ImmutableMap.builder();

		for (final Map.Entry<OLCandId, OrderLineId> olCand2OrderLineEntry : olCandId2OrderLineId.entrySet())
		{
			final I_M_ShipmentSchedule shipmentSchedule = shipmentSchedulePA.getByOrderLineId(olCand2OrderLineEntry.getValue());

			if (shipmentSchedule == null || shipmentSchedule.isProcessed())
			{
				continue;
			}

			final ShipmentScheduleId scheduleId = ShipmentScheduleId.ofRepoId(shipmentSchedule.getM_ShipmentSchedule_ID());

			final I_C_OLCand olCand = olCandsById.get(olCand2OrderLineEntry.getKey());

			if (olCand.getC_Async_Batch_ID() != shipmentSchedule.getC_Async_Batch_ID() || olCand.getC_Async_Batch_ID() != asyncBatchId.getRepoId())
			{
				throw new AdempiereException("olCand.getC_Async_Batch_ID() != shipmentSchedule.getC_Async_Batch_ID() || olCand.getC_Async_Batch_ID() != asyncBatchId")
						.appendParametersToMessage()
						.setParameter("C_OLCand_ID", olCand.getC_OLCand_ID())
						.setParameter("M_ShipmentSchedule_ID", shipmentSchedule.getM_ShipmentSchedule_ID())
						.setParameter("olCand.getC_Async_Batch_ID()", olCand.getC_Async_Batch_ID())
						.setParameter("shipmentSchedule.getC_Async_Batch_ID()", shipmentSchedule.getC_Async_Batch_ID())
						.setParameter("AsyncBatchId", asyncBatchId);
			}

			if (NumberUtils.asBigDecimal(olCand.getQtyShipped(), BigDecimal.ZERO).signum() <= 0)
			{
				scheduleId2QtyShipped.put(scheduleId,
										  shipmentScheduleBL.getQtyToDeliver(shipmentSchedule).toBigDecimal());
				continue;
			}

			final I_C_UOM olCandUOM = olCandEffectiveValuesBL.getC_UOM_Effective(olCand);
			final Quantity olCandQtyShipped = Quantity.of(olCand.getQtyShipped(), olCandUOM);

			final Quantity olCandQtyShippedProductUOM = uomConversionBL.convertToProductUOM(olCandQtyShipped, ProductId.ofRepoId(shipmentSchedule.getM_Product_ID()));

			scheduleId2QtyShipped.put(scheduleId, olCandQtyShippedProductUOM.toBigDecimal());
		}

		return scheduleId2QtyShipped.build();
	}

	@NonNull
	private List<JSONInvoiceInfoResponse> generateInvoicesForShipmentScheduleIds(@NonNull final Set<ShipmentScheduleId> shipmentScheduleIds)
	{
		final List<I_M_InOutLine> shipmentLines = retrieveInOuLineIdByShipScheduleId(shipmentScheduleIds)
				.stream()
				.map(inOutDAO::getLineById)
				.collect(ImmutableList.toImmutableList());

		final Set<InvoiceId> invoiceIds = invoiceService.generateInvoicesFromShipmentLines(shipmentLines);

		return invoiceIds.stream()
				.map(invoiceId -> invoiceService.getInvoiceInfo(invoiceId, Env.getAD_Language()))
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
			@NonNull final ShipmentService.ShippingInfoCache cache)
	{
		if (Check.isEmpty(request.getCreateShipmentInfoList()))
		{
			throw new AdempiereException("Empty request!");
		}

		request.getCreateShipmentInfoList().forEach(createShipmentInfo -> validateJsonCreateShipmentInfo(createShipmentInfo, cache));
	}

	private void validateJsonCreateShipmentInfo(
			@NonNull final JsonCreateShipmentInfo createShipmentInfo,
			@NonNull final ShipmentService.ShippingInfoCache cache)
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
			@NonNull final ShipmentService.ShippingInfoCache cache)
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
			@NonNull final ShipmentService.ShippingInfoCache cache)
	{
		shipmentScheduleBL.applyShipmentScheduleChanges(toApplyShipmentScheduleChangesRequest(request, cache));
	}

	private ApplyShipmentScheduleChangesRequest toApplyShipmentScheduleChangesRequest(
			@NonNull final UpdateShipmentScheduleRequest request,
			@NonNull final ShipmentService.ShippingInfoCache cache)
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

	private Optional<BPartnerLocationId> extractBPartnerLocationId(
			@NonNull final UpdateShipmentScheduleRequest request,
			@NonNull final ShipmentService.ShippingInfoCache cache)
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
			@NonNull final ShipmentService.ShippingInfoCache cache)
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

		final GenerateShipmentsRequest.GenerateShipmentsRequestBuilder generateShipmentsRequestBuilder = GenerateShipmentsRequest.builder()
				.asyncBatchId(asyncBatchId)
				.scheduleIds(shipmentScheduleIdsBuilder.build())
				.scheduleToExternalInfo(scheduleId2ExternalInfo.build())
				.scheduleToQuantityToDeliverOverride(scheduleToQuantityToDeliver.build())
				.quantityTypeToUse(M_ShipmentSchedule_QuantityTypeToUse.TYPE_QTY_TO_DELIVER);

		return generateShipmentsRequestBuilder.build();
	}

	@NonNull
	private ShipmentScheduleEnqueuer.Result generateShipments(@NonNull final GenerateShipmentsRequest request)
	{
		if (Check.isEmpty(request.getScheduleIds()))
		{
			throw new AdempiereException("No shipmentScheduleIds found on request!")
					.appendParametersToMessage()
					.setParameter("GenerateShipmentsRequest", request);
		}

		validateAsyncBatchAssignment(request.getScheduleIds(), request.getAsyncBatchId());

		final AsyncBatchMilestoneId milestoneId = newScheduleShipmentMilestone(request.getAsyncBatchId());

		asyncBatchMilestoneObserver.observeOn(milestoneId);

		final ShipmentScheduleEnqueuer.Result result = trxManager.callInNewTrx(() -> {
			final ICompositeQueryFilter<de.metas.handlingunits.model.I_M_ShipmentSchedule> queryFilters = queryBL
					.createCompositeQueryFilter(de.metas.handlingunits.model.I_M_ShipmentSchedule.class)
					.addInArrayFilter(de.metas.handlingunits.model.I_M_ShipmentSchedule.COLUMNNAME_M_ShipmentSchedule_ID, request.getScheduleIds());

			final ShipmentScheduleWorkPackageParameters workPackageParameters = ShipmentScheduleWorkPackageParameters.builder()
					.adPInstanceId(adPInstanceDAO.createSelectionId())
					.queryFilters(queryFilters)
					.quantityType(request.getQuantityTypeToUse())
					.completeShipments(true)
					.advisedShipmentDocumentNos(request.extractShipmentDocumentNos())
					.qtysToDeliverOverride(request.getScheduleToQuantityToDeliverOverride())
					.build();

			return new ShipmentScheduleEnqueuer()
					.setContext(Env.getCtx(), ITrx.TRXNAME_ThreadInherited)
					.createWorkpackages(workPackageParameters);
		});

		// Wait until processed because the next call might contain the same shipment schedules as the current one.
		// In this case enqueing the same shipmentschedule will fail, because it requires a an exclusive lock and the sched is still enqueued from the current lock
		// See ShipmentScheduleEnqueuer.acquireLock(...)
		asyncBatchMilestoneObserver.waitToBeProcessed(milestoneId);

		return result;
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

		final ImmutableMap<ShipmentScheduleId, List<I_M_ShipmentSchedule_QtyPicked>> scheduleId2qtyPickedRecords = shipmentScheduleAllocDAO.retrieveOnShipmentLineRecordsByScheduleIds(scheduleIds);

		final Set<InOutLineId> inOutLineIds = scheduleId2qtyPickedRecords.values().stream()
				.flatMap(List::stream)
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
	private Set<InOutLineId> retrieveInOuLineIdByShipScheduleId(@NonNull final Set<ShipmentScheduleId> ids)
	{
		return shipmentScheduleAllocDAO.retrieveOnShipmentLineRecordsByScheduleIds(ids)
				.values()
				.stream()
				.flatMap(List::stream)
				.map(I_M_ShipmentSchedule_QtyPicked::getM_InOutLine_ID)
				.map(InOutLineId::ofRepoIdOrNull)
				.filter(Objects::nonNull)
				.collect(ImmutableSet.toImmutableSet());
	}

	@NonNull
	private Set<InOutId> retrieveInOutIdsByScheduleIds(@NonNull final Set<ShipmentScheduleId> ids)
	{
		return retrieveInOuLineIdByShipScheduleId(ids)
				.stream()
				.map(inOutDAO::getLineById)
				.map(I_M_InOutLine::getM_InOut_ID)
				.map(InOutId::ofRepoId)
				.collect(ImmutableSet.toImmutableSet());
	}

	@NonNull
	private AsyncBatchMilestoneId newScheduleShipmentMilestone(@NonNull final AsyncBatchId asyncBatchId)
	{
		final AsyncBatchMilestone asyncBatchMilestone = AsyncBatchMilestone.builder()
				.asyncBatchId(asyncBatchId)
				.orgId(Env.getOrgId())
				.milestoneName(MilestoneName.SHIPMENT_CREATION)
				.build();

		final AsyncBatchMilestone milestone = asyncBathMilestoneService.save(asyncBatchMilestone);

		return milestone.getIdNotNull();
	}

	private void assignAsyncBatchIdToShipmentSchedules(@NonNull final Set<ShipmentScheduleId> ids, @NonNull final AsyncBatchId asyncBatchId)
	{
		ids.forEach(id -> shipmentScheduleBL.setAsyncBatch(id, asyncBatchId));
	}

	private void validateAsyncBatchAssignment(@NonNull final Set<ShipmentScheduleId> ids, @NonNull final AsyncBatchId asyncBatchId)
	{
		final int unassignedScheduleCount = queryBL.createQueryBuilder(I_M_ShipmentSchedule.class)
				.addInArrayFilter(I_M_ShipmentSchedule.COLUMNNAME_M_ShipmentSchedule_ID, ids)
				.addNotEqualsFilter(I_M_ShipmentSchedule.COLUMNNAME_C_Async_Batch_ID, asyncBatchId)
				.create()
				.count();

		if (unassignedScheduleCount > 0)
		{
			throw new AdempiereException("Found a number of unassigned scheduleIds! Count: " + unassignedScheduleCount)
					.appendParametersToMessage()
					.setParameter("scheduleIds", ids);
		}
	}

	@NonNull
	public JsonCreateShipmentResponse buildCreateShipmentResponse(@NonNull final Set<InOutId> shipmentIds)
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

		final List<ShipmentScheduleId> scheduleIds = createShipmentCandidates.stream()
				.map(CreateShipmentInfoCandidate::getShipmentScheduleId)
				.collect(ImmutableList.toImmutableList());

		final ShippingInfoCache shippingInfoCache = initShippingInfoCache();
		shippingInfoCache.warmUpForShipmentScheduleIds(scheduleIds);

		final Map<AsyncBatchId, ArrayList<CreateShipmentInfoCandidate>> asyncBatchId2CreateShipmentInfos = new HashMap<>();

		for (final CreateShipmentInfoCandidate createShipmentInfoCandidate : createShipmentCandidates)
		{
			final ArrayList<CreateShipmentInfoCandidate> currentCandidateList = new ArrayList<>();
			currentCandidateList.add(createShipmentInfoCandidate);

			final AsyncBatchId currentAsyncBatchId = shippingInfoCache.getAsyncBatchId(createShipmentInfoCandidate.getShipmentScheduleId())
					.orElse(AsyncBatchId.NONE_ASYNC_BATCH_ID);

			asyncBatchId2CreateShipmentInfos.merge(currentAsyncBatchId, currentCandidateList, CollectionUtils::mergeLists);
		}

		final ArrayList<CreateShipmentInfoCandidate> shipmentSchedulesWithNoAsyncBatchId = asyncBatchId2CreateShipmentInfos.get(AsyncBatchId.NONE_ASYNC_BATCH_ID);

		Optional.ofNullable(shipmentSchedulesWithNoAsyncBatchId)
				.flatMap(this::assignAsyncBatch)
				.ifPresent(batchId -> {
					asyncBatchId2CreateShipmentInfos.put(batchId, shipmentSchedulesWithNoAsyncBatchId);
					asyncBatchId2CreateShipmentInfos.remove(AsyncBatchId.NONE_ASYNC_BATCH_ID);
				});

		return ImmutableMap.copyOf(asyncBatchId2CreateShipmentInfos);
	}

	@NonNull
	private Optional<AsyncBatchId> assignAsyncBatch(@NonNull final List<CreateShipmentInfoCandidate> shipmentInfoCandidates)
	{
		if (shipmentInfoCandidates.isEmpty())
		{
			return Optional.empty();
		}

		final ImmutableSet<ShipmentScheduleId> shipmentScheduleIds = shipmentInfoCandidates.stream()
				.map(CreateShipmentInfoCandidate::getShipmentScheduleId)
				.collect(ImmutableSet.toImmutableSet());

		final AsyncBatchId asyncBatchId = asyncBatchBL.newAsyncBatch(C_Async_Batch_InternalName_ShipmentSchedule);

		assignAsyncBatchIdToShipmentSchedules(shipmentScheduleIds, asyncBatchId);

		return Optional.of(asyncBatchId);
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

	private Set<InOutId> generateShipmentForBatch(@NonNull final Set<OLCandId> olCandIds, @NonNull final AsyncBatchId olCandsAsyncBatchId)
	{
		final ImmutableMap<ShipmentScheduleId, BigDecimal> shipmentScheduleIdToQtyShipped = getShipmentScheduleId2QtyToDeliver(olCandIds, olCandsAsyncBatchId);

		if (shipmentScheduleIdToQtyShipped.isEmpty())
		{
			return ImmutableSet.of();
		}

		//dev-note: if we came this far, we know all shipment schedules are assigned to the async batch identified by the input param:"asyncBatchId"
		final GenerateShipmentsRequest generateShipmentsRequest = GenerateShipmentsRequest.builder()
				.asyncBatchId(olCandsAsyncBatchId)
				.scheduleIds(shipmentScheduleIdToQtyShipped.keySet())
				.scheduleToExternalInfo(ImmutableMap.of())
				.scheduleToQuantityToDeliverOverride(shipmentScheduleIdToQtyShipped)
				.quantityTypeToUse(M_ShipmentSchedule_QuantityTypeToUse.TYPE_QTY_TO_DELIVER)
				.build();

		generateShipments(generateShipmentsRequest);

		return retrieveInOutIdsByScheduleIds(shipmentScheduleIdToQtyShipped.keySet());
	}

	//
	//
	//
	private static class ShippingInfoCache
	{
		private final IShipmentScheduleBL shipmentScheduleBL;
		private final IShipmentScheduleEffectiveBL scheduleEffectiveBL;
		private final IShipperDAO shipperDAO;

		private final HashMap<ShipmentScheduleId, I_M_ShipmentSchedule> shipmentSchedulesById = new HashMap<>();
		private final HashMap<String, I_M_Shipper> shipperByInternalName = new HashMap<>();

		@Builder
		private ShippingInfoCache(
				@NonNull final IShipmentScheduleBL shipmentScheduleBL,
				@NonNull final IShipmentScheduleEffectiveBL scheduleEffectiveBL,
				@NonNull final IShipperDAO shipperDAO)
		{
			this.shipmentScheduleBL = shipmentScheduleBL;
			this.scheduleEffectiveBL = scheduleEffectiveBL;
			this.shipperDAO = shipperDAO;
		}

		public void warmUpForShipmentScheduleIds(@NonNull final Collection<ShipmentScheduleId> shipmentScheduleIds)
		{
			CollectionUtils.getAllOrLoad(
					shipmentSchedulesById,
					shipmentScheduleIds,
					shipmentScheduleBL::getByIds);
		}

		public void warmUpForShipperInternalNames(@NonNull final Collection<String> shipperInternalNameCollection)
		{
			CollectionUtils.getAllOrLoad(
					shipperByInternalName,
					shipperInternalNameCollection,
					shipperDAO::getByInternalName);
		}

		private I_M_ShipmentSchedule getShipmentScheduleById(@NonNull final ShipmentScheduleId shipmentScheduleId)
		{
			return shipmentSchedulesById.computeIfAbsent(shipmentScheduleId, shipmentScheduleBL::getById);
		}

		public OrgId getOrgId(@NonNull final ShipmentScheduleId shipmentScheduleId)
		{
			final I_M_ShipmentSchedule shipmentSchedule = getShipmentScheduleById(shipmentScheduleId);
			return OrgId.ofRepoId(shipmentSchedule.getAD_Org_ID());
		}

		public BPartnerId getBPartnerId(@NonNull final ShipmentScheduleId shipmentScheduleId)
		{
			final I_M_ShipmentSchedule shipmentSchedule = getShipmentScheduleById(shipmentScheduleId);
			return scheduleEffectiveBL.getBPartnerId(shipmentSchedule);
		}

		public Optional<AsyncBatchId> getAsyncBatchId(@NonNull final ShipmentScheduleId shipmentScheduleId)
		{
			final I_M_ShipmentSchedule shipmentSchedule = getShipmentScheduleById(shipmentScheduleId);
			return Optional.ofNullable(AsyncBatchId.ofRepoIdOrNull(shipmentSchedule.getC_Async_Batch_ID()));
		}

		@Nullable
		public ShipperId getShipperId(@Nullable final String shipperInternalName)
		{
			if (Check.isBlank(shipperInternalName))
			{
				return null;
			}

			final I_M_Shipper shipper = shipperByInternalName.computeIfAbsent(shipperInternalName, this::loadShipper);

			return shipper != null
					? ShipperId.ofRepoId(shipper.getM_Shipper_ID())
					: null;
		}

		@Nullable
		public String getTrackingURL(@NonNull final String shipperInternalName)
		{
			final I_M_Shipper shipper = shipperByInternalName.computeIfAbsent(shipperInternalName, this::loadShipper);

			return shipper != null
					? shipper.getTrackingURL()
					: null;
		}

		@Nullable
		private I_M_Shipper loadShipper(@NonNull final String shipperInternalName)
		{
			return shipperDAO.getByInternalName(ImmutableSet.of(shipperInternalName)).get(shipperInternalName);
		}
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
