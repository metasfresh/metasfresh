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

package de.metas.rest_api.shipping;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.BPartnerQuery;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.common.rest_api.JsonAttributeInstance;
import de.metas.common.shipment.JsonCreateShipmentInfo;
import de.metas.common.shipment.JsonCreateShipmentRequest;
import de.metas.handlingunits.shipmentschedule.api.IHUShipmentScheduleBL;
import de.metas.handlingunits.shipmentschedule.api.M_ShipmentSchedule_QuantityTypeToUse;
import de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleWithHU;
import de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleWithHUService;
import de.metas.handlingunits.shipmentschedule.spi.impl.CalculateShippingDateRule;
import de.metas.handlingunits.shipmentschedule.spi.impl.ShipmentScheduleExternalInfo;
import de.metas.inoutcandidate.ShipmentScheduleId;
import de.metas.inoutcandidate.api.ApplyShipmentScheduleChangesRequest;
import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.inoutcandidate.api.IShipmentScheduleEffectiveBL;
import de.metas.inoutcandidate.api.InOutGenerateResult;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.location.CountryId;
import de.metas.location.ICountryCodeFactory;
import de.metas.location.ICountryDAO;
import de.metas.logging.LogManager;
import de.metas.order.DeliveryRule;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.product.IProductDAO;
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
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.compiere.util.Env;
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
	private final IAttributeDAO attributeDAO = Services.get(IAttributeDAO.class);
	private final IProductDAO productDAO = Services.get(IProductDAO.class);
	private final IShipperDAO shipperDAO = Services.get(IShipperDAO.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);

	private final ShipmentScheduleWithHUService shipmentScheduleWithHUService;
	private final AttributeSetHelper attributeSetHelper;

	public ShipmentService(final ShipmentScheduleWithHUService shipmentScheduleWithHUService, final AttributeSetHelper attributeSetHelper)
	{
		this.shipmentScheduleWithHUService = shipmentScheduleWithHUService;
		this.attributeSetHelper = attributeSetHelper;
	}

	public InOutGenerateResult updateShipmentSchedulesAndGenerateShipments(@NonNull final JsonCreateShipmentRequest request)
	{
		final ShipmentSchedulesCache cache = ShipmentSchedulesCache.builder()
				.shipmentScheduleBL(shipmentScheduleBL)
				.scheduleEffectiveBL(scheduleEffectiveBL)
				.build();
		cache.warmUpForShipmentScheduleIds(extractShipmentScheduleIds(request));

		validateRequest(request, cache);
		updateShipmentSchedules(request.getCreateShipmentInfoList(), cache);

		return generateShipments(toGenerateShipmentsRequest(request));
	}

	private ImmutableSet<ShipmentScheduleId> extractShipmentScheduleIds(@NonNull final JsonCreateShipmentRequest request)
	{
		return request.getCreateShipmentInfoList()
				.stream()
				.map(createShipmentInfo -> extractShipmentScheduleId(createShipmentInfo))
				.collect(ImmutableSet.toImmutableSet());
	}

	private static ShipmentScheduleId extractShipmentScheduleId(final JsonCreateShipmentInfo createShipmentInfo)
	{
		return ShipmentScheduleId.ofRepoId(createShipmentInfo.getShipmentScheduleId().getValue());
	}

	private void validateRequest(
			@NonNull final JsonCreateShipmentRequest request,
			@NonNull final ShipmentSchedulesCache cache)
	{
		if (Check.isEmpty(request.getCreateShipmentInfoList()))
		{
			throw new AdempiereException("Empty request!");
		}

		request.getCreateShipmentInfoList().forEach(createShipmentInfo -> validateJsonCreateShipmentInfo(createShipmentInfo, cache));
	}

	private void validateJsonCreateShipmentInfo(
			@NonNull final JsonCreateShipmentInfo createShipmentInfo,
			@NonNull final ShipmentSchedulesCache cache)
	{
		final ShipmentScheduleId shipmentScheduleId = extractShipmentScheduleId(createShipmentInfo);

		// will throw error if no schedule is found
		final I_M_ShipmentSchedule shipmentSchedule = cache.getShipmentScheduleById(shipmentScheduleId);

		if (shipmentSchedule.isProcessed())
		{
			throw new AdempiereException("M_ShipmentSchedule was already processed!")
					.appendParametersToMessage()
					.setParameter("M_ShipmentSchedule", shipmentSchedule);
		}

		if (Check.isNotBlank(createShipmentInfo.getProductSearchKey()))
		{
			final ProductId incomingProductId = productDAO.retrieveProductIdByValue(createShipmentInfo.getProductSearchKey());

			if (incomingProductId == null || incomingProductId.getRepoId() != shipmentSchedule.getM_Product_ID())
			{
				throw new AdempiereException("Invalid productSearchKey!")
						.appendParametersToMessage()
						.setParameter("productSearchKey", createShipmentInfo.getProductSearchKey())
						.setParameter("corresponding productId", incomingProductId)
						.setParameter("shipmentSchedule.M_Product_ID", shipmentSchedule.getM_Product_ID());
			}
		}
	}

	private void updateShipmentSchedules(
			@NonNull final List<JsonCreateShipmentInfo> createShipmentInfos,
			@NonNull final ShipmentSchedulesCache cache)
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
			@NonNull final ShipmentSchedulesCache cache)
	{
		shipmentScheduleBL.applyShipmentScheduleChanges(toApplyShipmentScheduleChangesRequest(request, cache));
	}

	private ApplyShipmentScheduleChangesRequest toApplyShipmentScheduleChangesRequest(
			@NonNull final UpdateShipmentScheduleRequest request,
			@NonNull final ShipmentSchedulesCache cache)
	{
		return ApplyShipmentScheduleChangesRequest.builder()
				.shipmentScheduleId(request.getShipmentScheduleId())
				.qtyToDeliverStockingUOM(request.getQtyToDeliverInStockingUOM())
				.deliveryDate(request.getDeliveryDate())
				.deliveryRule(request.getDeliveryRule())
				.attributes(request.getAttributes())
				.bPartnerLocationIdOverride(extractBPartnerLocationId(request, cache).orElse(null))
				.build();
	}

	private Optional<BPartnerLocationId> extractBPartnerLocationId(
			@NonNull final UpdateShipmentScheduleRequest request,
			@NonNull final ShipmentSchedulesCache cache)
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

	private UpdateShipmentScheduleRequest toUpdateShipmentScheduleRequestOrNull(
			@NonNull final JsonCreateShipmentInfo createShipmentInfo,
			@NonNull final ShipmentSchedulesCache cache)
	{
		final LocalDateTime deliveryDate = createShipmentInfo.getMovementDate();
		final BigDecimal qtyToDeliverInStockingUOM = createShipmentInfo.getMovementQuantity();
		final LocationBasicInfo bPartnerLocation = LocationBasicInfo.ofNullable(createShipmentInfo.getShipToLocation(), countryCodeFactory)
				.orElse(null);
		final String bpartnerCode = createShipmentInfo.getBusinessPartnerSearchKey();
		final List<JsonAttributeInstance> attributes = createShipmentInfo.getAttributes();
		final DeliveryRule deliveryRule = DeliveryRule.ofNullableCode(createShipmentInfo.getDeliveryRule());

		if (deliveryDate == null
				&& qtyToDeliverInStockingUOM == null
				&& bPartnerLocation == null
				&& Check.isBlank(bpartnerCode)
				&& Check.isEmpty(attributes)
				&& deliveryRule == null)
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
				.build();
	}

	private GenerateShipmentsRequest toGenerateShipmentsRequest(@NonNull final JsonCreateShipmentRequest request)
	{
		final ImmutableMap.Builder<ShipmentScheduleId, ShipmentScheduleExternalInfo> scheduleId2ExternalInfoBuilder = new ImmutableMap.Builder<>();

		final ImmutableSet.Builder<ShipmentScheduleId> shipmentScheduleIdsBuilder = new ImmutableSet.Builder<>();

		request.getCreateShipmentInfoList().forEach(createShipmentInfo -> {
			final ShipmentScheduleId shipmentScheduleId = extractShipmentScheduleId(createShipmentInfo);

			shipmentScheduleIdsBuilder.add(shipmentScheduleId);

			if (Check.isNotBlank(createShipmentInfo.getDocumentNo())
					|| !Check.isEmpty(createShipmentInfo.getTrackingNumbers()))
			{
				final ShipmentScheduleExternalInfo externalInfo = ShipmentScheduleExternalInfo
						.builder()
						.documentNo(createShipmentInfo.getDocumentNo())
						.trackingNumbers(createShipmentInfo.getTrackingNumbers())
						.build();

				scheduleId2ExternalInfoBuilder.put(shipmentScheduleId, externalInfo);
			}
		});

		final GenerateShipmentsRequest.GenerateShipmentsRequestBuilder generateShipmentsRequestBuilder = GenerateShipmentsRequest.builder()
				.scheduleIds(shipmentScheduleIdsBuilder.build())
				.scheduleToExternalInfo(scheduleId2ExternalInfoBuilder.build())
				.quantityTypeToUse(M_ShipmentSchedule_QuantityTypeToUse.TYPE_QTY_TO_DELIVER);

		final Optional<ShipperId> shipperId = Check.isNotBlank(request.getShipperCode())
				? shipperDAO.getShipperIdByValue(request.getShipperCode(), Env.getOrgId())
				: Optional.empty();

		shipperId.map(generateShipmentsRequestBuilder::shipperId);

		return generateShipmentsRequestBuilder.build();
	}

	private InOutGenerateResult generateShipments(@NonNull final GenerateShipmentsRequest request)
	{
		final ImmutableList<I_M_ShipmentSchedule> shipmentSchedules = ImmutableList.copyOf(shipmentScheduleBL.getByIds(request.getScheduleIds()).values());

		final ImmutableList<ShipmentScheduleWithHU> scheduleWithHUS = shipmentScheduleWithHUService.createShipmentSchedulesWithHU(
				shipmentSchedules,
				request.getQuantityTypeToUse());

		return huShipmentScheduleBL
				.createInOutProducerFromShipmentSchedule()
				.setProcessShipments(true)
				.setScheduleIdToExternalInfo(request.getScheduleToExternalInfo())
				.setShipperId(request.getShipperId())
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

	//
	//
	//
	private static class ShipmentSchedulesCache
	{
		private final IShipmentScheduleBL shipmentScheduleBL;
		private final IShipmentScheduleEffectiveBL scheduleEffectiveBL;

		private final HashMap<ShipmentScheduleId, I_M_ShipmentSchedule> shipmentSchedulesById = new HashMap<>();

		@Builder
		private ShipmentSchedulesCache(
				@NonNull final IShipmentScheduleBL shipmentScheduleBL,
				@NonNull final IShipmentScheduleEffectiveBL scheduleEffectiveBL)
		{
			this.shipmentScheduleBL = shipmentScheduleBL;
			this.scheduleEffectiveBL = scheduleEffectiveBL;
		}

		public void warmUpForShipmentScheduleIds(@NonNull final Collection<ShipmentScheduleId> shipmentScheduleIds)
		{
			CollectionUtils.getAllOrLoad(
					shipmentSchedulesById,
					shipmentScheduleIds,
					shipmentScheduleBL::getByIds);
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
	}
}
