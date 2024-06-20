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

package de.metas.rest_api.v1.shipping;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.BPartnerQuery;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.common.rest_api.v1.JsonAttributeInstance;
import de.metas.common.shipping.v1.shipment.JsonCreateShipmentInfo;
import de.metas.common.shipping.v1.shipment.JsonCreateShipmentRequest;
import de.metas.handlingunits.shipmentschedule.api.IHUShipmentScheduleBL;
import de.metas.handlingunits.shipmentschedule.api.M_ShipmentSchedule_QuantityTypeToUse;
import de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleWithHU;
import de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleWithHUService;
import de.metas.handlingunits.shipmentschedule.spi.impl.CalculateShippingDateRule;
import de.metas.handlingunits.shipmentschedule.spi.impl.PackageInfo;
import de.metas.handlingunits.shipmentschedule.spi.impl.ShipmentScheduleExternalInfo;
import de.metas.inout.IInOutDAO;
import de.metas.inout.InOutId;
import de.metas.inout.InOutLineId;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.api.ApplyShipmentScheduleChangesRequest;
import de.metas.inoutcandidate.api.IShipmentScheduleAllocDAO;
import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.inoutcandidate.api.IShipmentScheduleEffectiveBL;
import de.metas.inoutcandidate.api.InOutGenerateResult;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule_QtyPicked;
import de.metas.location.CountryId;
import de.metas.location.ICountryCodeFactory;
import de.metas.location.ICountryDAO;
import de.metas.logging.LogManager;
import de.metas.order.DeliveryRule;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.product.IProductDAO;
import de.metas.product.IProductDAO.ProductQuery;
import de.metas.product.ProductId;
import de.metas.shipping.IShipperDAO;
import de.metas.shipping.ShipperId;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.collections.CollectionUtils;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.trx.processor.api.FailTrxItemExceptionHandler;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.api.CreateAttributeInstanceReq;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_Shipper;
import org.compiere.util.TimeUtil;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;

@Service
public class ShipmentService
{
	private static final Logger logger = LogManager.getLogger(ShipmentService.class);

	private final IShipmentScheduleBL shipmentScheduleBL = Services.get(IShipmentScheduleBL.class);
	private final IShipmentScheduleEffectiveBL scheduleEffectiveBL = Services.get(IShipmentScheduleEffectiveBL.class);
	private final IHUShipmentScheduleBL huShipmentScheduleBL = Services.get(IHUShipmentScheduleBL.class);
	private final IBPartnerDAO bPartnerDAO = Services.get(IBPartnerDAO.class);
	private final ICountryDAO countryDAO = Services.get(ICountryDAO.class);
	private final ICountryCodeFactory countryCodeFactory = Services.get(ICountryCodeFactory.class);
	private final IProductDAO productDAO = Services.get(IProductDAO.class);
	private final IShipperDAO shipperDAO = Services.get(IShipperDAO.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	private final IShipmentScheduleAllocDAO shipmentScheduleAllocDAO = Services.get(IShipmentScheduleAllocDAO.class);
	private final IInOutDAO inOutDAO = Services.get(IInOutDAO.class);

	private final ShipmentScheduleWithHUService shipmentScheduleWithHUService;
	private final AttributeSetHelper attributeSetHelper;

	public ShipmentService(final ShipmentScheduleWithHUService shipmentScheduleWithHUService,
			final AttributeSetHelper attributeSetHelper)
	{
		this.shipmentScheduleWithHUService = shipmentScheduleWithHUService;
		this.attributeSetHelper = attributeSetHelper;
	}

	public InOutGenerateResult updateShipmentSchedulesAndGenerateShipments(@NonNull final JsonCreateShipmentRequest request)
	{
		final ShippingInfoCache cache = ShippingInfoCache.builder()
				.shipmentScheduleBL(shipmentScheduleBL)
				.scheduleEffectiveBL(scheduleEffectiveBL)
				.shipperDAO(shipperDAO)
				.build();

		cache.warmUpForShipmentScheduleIds(extractShipmentScheduleIds(request));
		cache.warmUpForShipperInternalNames(extractShippersInternalName(request));

		validateRequest(request, cache);
		updateShipmentSchedules(request.getCreateShipmentInfoList(), cache);

		return generateShipments(toGenerateShipmentsRequest(request, cache));
	}

	private ImmutableSet<ShipmentScheduleId> extractShipmentScheduleIds(@NonNull final JsonCreateShipmentRequest request)
	{
		return request.getCreateShipmentInfoList()
				.stream()
				.map(ShipmentService::extractShipmentScheduleId)
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

	private static ShipmentScheduleId extractShipmentScheduleId(final JsonCreateShipmentInfo createShipmentInfo)
	{
		return ShipmentScheduleId.ofRepoId(createShipmentInfo.getShipmentScheduleId().getValue());
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
				.qtyToDeliverStockingUOM(request.getQtyToDeliverInStockingUOM())
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
		final BigDecimal qtyToDeliverInStockingUOM = createShipmentInfo.getMovementQuantity();
		final LocationBasicInfo bPartnerLocation = LocationBasicInfo.ofNullable(createShipmentInfo.getShipToLocation(), countryCodeFactory)
				.orElse(null);
		final String bpartnerCode = createShipmentInfo.getBusinessPartnerSearchKey();
		final List<JsonAttributeInstance> attributes = createShipmentInfo.getAttributes();
		final DeliveryRule deliveryRule = DeliveryRule.ofNullableCode(createShipmentInfo.getDeliveryRule());
		final ShipperId shipperId = cache.getShipperId(createShipmentInfo.getShipperInternalName());

		if (deliveryDate == null
				&& qtyToDeliverInStockingUOM == null
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
				.qtyToDeliverInStockingUOM(qtyToDeliverInStockingUOM)
				.deliveryRule(deliveryRule)
				.shipperId(shipperId)
				.build();
	}

	private GenerateShipmentsRequest toGenerateShipmentsRequest(@NonNull final JsonCreateShipmentRequest request, @NonNull final ShipmentService.ShippingInfoCache cache)
	{
		final ImmutableMap.Builder<ShipmentScheduleId, ShipmentScheduleExternalInfo> scheduleId2ExternalInfoBuilder = new ImmutableMap.Builder<>();

		final ImmutableSet.Builder<ShipmentScheduleId> shipmentScheduleIdsBuilder = new ImmutableSet.Builder<>();

		request.getCreateShipmentInfoList().forEach(createShipmentInfo -> {
			final ShipmentScheduleId shipmentScheduleId = extractShipmentScheduleId(createShipmentInfo);

			shipmentScheduleIdsBuilder.add(shipmentScheduleId);

			if (Check.isNotBlank(createShipmentInfo.getDocumentNo())
					|| !Check.isEmpty(createShipmentInfo.getPackages()))
			{
				final ShipmentScheduleExternalInfo externalInfo = ShipmentScheduleExternalInfo
						.builder()
						.documentNo(createShipmentInfo.getDocumentNo())
						.packageInfoList(extractPackageInfos(createShipmentInfo, cache))
						.build();

				scheduleId2ExternalInfoBuilder.put(shipmentScheduleId, externalInfo);
			}
		});

		final GenerateShipmentsRequest.GenerateShipmentsRequestBuilder generateShipmentsRequestBuilder = GenerateShipmentsRequest.builder()
				.scheduleIds(shipmentScheduleIdsBuilder.build())
				.scheduleToExternalInfo(scheduleId2ExternalInfoBuilder.build())
				.quantityTypeToUse(M_ShipmentSchedule_QuantityTypeToUse.TYPE_QTY_TO_DELIVER);

		return generateShipmentsRequestBuilder.build();
	}

	private InOutGenerateResult generateShipments(@NonNull final GenerateShipmentsRequest request)
	{
		final ImmutableList<I_M_ShipmentSchedule> shipmentSchedules = ImmutableList.copyOf(shipmentScheduleBL.getByIds(request.getScheduleIds()).values());

		final ImmutableList<ShipmentScheduleWithHU> scheduleWithHUS = shipmentScheduleWithHUService.createShipmentSchedulesWithHU(
				shipmentSchedules,
				request.getQuantityTypeToUse(),
				false /* backwards compatibility: on-the-fly-pick to (anonymous) CUs */,
				ImmutableMap.of(),
				true  /* backwards compatibility: true - fail if no picked HUs found*/
		);

		return huShipmentScheduleBL
				.createInOutProducerFromShipmentSchedule()
				.setProcessShipments(true)
				.setScheduleIdToExternalInfo(request.getScheduleToExternalInfo())
				.computeShipmentDate(CalculateShippingDateRule.FORCE_SHIPMENT_DATE_DELIVERY_DATE)
				.setTrxItemExceptionHandler(FailTrxItemExceptionHandler.instance)
				.createShipments(scheduleWithHUS);
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

	@Nullable
	private List<PackageInfo> extractPackageInfos(@NonNull final JsonCreateShipmentInfo shipmentInfo, @NonNull final ShipmentService.ShippingInfoCache cache)
	{
		if (Check.isEmpty(shipmentInfo.getPackages()))
		{
			return null;
		}

		final String trackingURL = Check.isNotBlank(shipmentInfo.getShipperInternalName())
				? cache.getTrackingURL(shipmentInfo.getShipperInternalName())
				: null;

		return shipmentInfo.getPackages().stream()
				.map(jsonPackage ->
						PackageInfo.builder()
								.trackingNumber(jsonPackage.getTrackingCode())
								.weight(jsonPackage.getWeight())
								.trackingUrl(trackingURL)
								.build()
				)
				.collect(ImmutableList.toImmutableList());
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
}
