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
import de.metas.location.ICountryDAO;
import de.metas.order.DeliveryRule;
import de.metas.product.IProductDAO;
import de.metas.product.ProductId;
import de.metas.rest_api.shipping.info.GenerateShipmentsRequest;
import de.metas.rest_api.shipping.info.LocationBasicInfo;
import de.metas.rest_api.shipping.info.UpdateShipmentScheduleRequest;
import de.metas.shipping.IShipperDAO;
import de.metas.shipping.ShipperId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.trx.processor.api.FailTrxItemExceptionHandler;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeCode;
import org.adempiere.mm.attributes.AttributeValueType;
import org.adempiere.mm.attributes.api.CreateAttributeInstanceReq;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.compiere.model.I_M_Attribute;
import org.compiere.util.Env;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class ShipmentService
{

	private final IShipmentScheduleBL scheduleBL = Services.get(IShipmentScheduleBL.class);
	private final IShipmentScheduleEffectiveBL scheduleEffectiveBL = Services.get(IShipmentScheduleEffectiveBL.class);
	private final IHUShipmentScheduleBL shipmentScheduleBL = Services.get(IHUShipmentScheduleBL.class);
	private final IBPartnerDAO bPartnerDAO = Services.get(IBPartnerDAO.class);
	private final ICountryDAO countryDAO = Services.get(ICountryDAO.class);
	private final IAttributeDAO attributeDAO = Services.get(IAttributeDAO.class);
	private final IProductDAO productDAO = Services.get(IProductDAO.class);
	private final IShipperDAO shipperDAO = Services.get(IShipperDAO.class);

	private final ShipmentScheduleWithHUService shipmentScheduleWithHUService;

	public ShipmentService(final ShipmentScheduleWithHUService shipmentScheduleWithHUService)
	{
		this.shipmentScheduleWithHUService = shipmentScheduleWithHUService;
	}

	public void validateRequest(@NonNull final JsonCreateShipmentRequest request)
	{
		if (Check.isEmpty(request.getCreateShipmentInfoList()))
		{
			throw new AdempiereException("Empty request!");
		}

		request.getCreateShipmentInfoList().forEach(createShipmentInfo ->
				{
					final ShipmentScheduleId shipmentScheduleId = ShipmentScheduleId.ofRepoId(createShipmentInfo.getShipmentScheduleId().getValue());

					//will throw error if no schedule is found
					final I_M_ShipmentSchedule shipmentSchedule = scheduleBL.getById(shipmentScheduleId);

					if (shipmentSchedule.isProcessed())
					{
						throw new AdempiereException("M_ShipmentSchedule was already processed!")
								.appendParametersToMessage()
								.setParameter("M_ShipmentSchedule",shipmentSchedule);
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
		);
	}

	public void updateShipmentSchedule(@NonNull final UpdateShipmentScheduleRequest request)
	{
		if (request.emptyRequest())
		{
			return;//nothing to do
		}

		final ApplyShipmentScheduleChangesRequest.ApplyShipmentScheduleChangesRequestBuilder applyChangesRequestBuilder =
				ApplyShipmentScheduleChangesRequest.builder()
						.shipmentScheduleId(request.getShipmentScheduleId())
						.qtyToDeliverStockingUOM(request.getQtyToDeliver())
						.deliveryDate(request.getDeliveryDate());

		if (Check.isNotBlank(request.getDeliveryRuleCode()))
		{
			final DeliveryRule deliveryRule = DeliveryRule.ofCode(request.getDeliveryRuleCode());
			applyChangesRequestBuilder.deliveryRule(deliveryRule);
		}


		if (request.getBPartnerLocation() != null)
		{
			final I_M_ShipmentSchedule shipmentSchedule = scheduleBL.getById(request.getShipmentScheduleId());

			final Optional<BPartnerId> bPartnerIdOverride = getBPartnerIdByValue(request.getBPartnerCode());

			final BPartnerId bPartnerId = bPartnerIdOverride
					.orElseGet(() -> scheduleEffectiveBL.getBPartnerId(shipmentSchedule));

			final Optional<BPartnerLocationId> bPartnerLocationId = getBPartnerLocationBy(bPartnerId, request.getBPartnerLocation());

			bPartnerLocationId.map(applyChangesRequestBuilder::bPartnerLocationIdOverride);
		}

		if (request.getAttributes() != null)
		{
			final List<CreateAttributeInstanceReq> createAttributeRequests = request.getAttributes()
					.stream()
					.map(this::buildCreateAttributeInstanceReq)
					.collect(ImmutableList.toImmutableList());

			applyChangesRequestBuilder.attributes(createAttributeRequests);
		}

		scheduleBL.applyShipmentScheduleChanges(applyChangesRequestBuilder.build());
	}

	public UpdateShipmentScheduleRequest buildUpdateShipmentScheduleRequest(@NonNull final JsonCreateShipmentInfo createShipmentInfo)
	{
		final ShipmentScheduleId scheduleId = ShipmentScheduleId.ofRepoId(createShipmentInfo.getShipmentScheduleId().getValue());

		final LocationBasicInfo locationBasicInfo = createShipmentInfo.getShipToLocation() != null
				? LocationBasicInfo.of(createShipmentInfo.getShipToLocation())
				: null;

		return UpdateShipmentScheduleRequest.builder()
				.shipmentScheduleId(scheduleId)
				.bPartnerCode(createShipmentInfo.getBusinessPartnerSearchKey())
				.bPartnerLocation(locationBasicInfo)
				.attributes(createShipmentInfo.getAttributes())
				.deliveryDate(createShipmentInfo.getMovementDate())
				.qtyToDeliver(createShipmentInfo.getMovementQuantity())
				.deliveryRuleCode(createShipmentInfo.getDeliveryRule())
				.build();
	}

	public GenerateShipmentsRequest buildGenerateShipmentsRequest(@NonNull final JsonCreateShipmentRequest request)
	{
		final ImmutableMap.Builder<ShipmentScheduleId, ShipmentScheduleExternalInfo> scheduleId2ExternalInfoBuilder = new ImmutableMap.Builder<>();

		final ImmutableSet.Builder<ShipmentScheduleId> shipmentScheduleIdsBuilder = new ImmutableSet.Builder<>();

		request.getCreateShipmentInfoList().forEach(createShipmentInfo ->
				{
					final ShipmentScheduleId shipmentScheduleId = ShipmentScheduleId.ofRepoId(createShipmentInfo.getShipmentScheduleId().getValue());

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
				}
		);

		final GenerateShipmentsRequest.GenerateShipmentsRequestBuilder generateShipmentsRequestBuilder =  GenerateShipmentsRequest.builder()
				.scheduleIds(shipmentScheduleIdsBuilder.build())
				.scheduleToExternalInfo(scheduleId2ExternalInfoBuilder.build())
				.quantityTypeToUse(M_ShipmentSchedule_QuantityTypeToUse.TYPE_QTY_TO_DELIVER);

		final Optional<ShipperId> shipperId = Check.isNotBlank(request.getShipperCode())
				? shipperDAO.getShipperIdByValue(request.getShipperCode(), Env.getOrgId())
				: Optional.empty();

		shipperId.map(generateShipmentsRequestBuilder::shipperId);

		return generateShipmentsRequestBuilder.build();
	}

	public InOutGenerateResult generateShipments(@NonNull final GenerateShipmentsRequest request)
	{
		final Collection<I_M_ShipmentSchedule> shipmentSchedules = scheduleBL
				.getByIds(request.getScheduleIds())
				.values();

		final ImmutableList<I_M_ShipmentSchedule> schedules = ImmutableList.copyOf(shipmentSchedules);

		final ImmutableList<ShipmentScheduleWithHU> scheduleWithHUS = shipmentScheduleWithHUService.createShipmentSchedulesWithHU(schedules, request.getQuantityTypeToUse());

		return shipmentScheduleBL
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

		final BPartnerQuery query = BPartnerQuery.builder()
				.bpartnerValue(bPartnerValue)
				.build();

		return bPartnerDAO.retrieveBPartnerIdBy(query);
	}

	private Optional<BPartnerLocationId> getBPartnerLocationBy(@NonNull final BPartnerId bPartnerId,
			                                                   @NonNull final LocationBasicInfo locationBasicInfo)
	{
		final CountryId countryId = countryDAO.getCountryIdByCountryCode(locationBasicInfo.getCountryCode());

		if (countryId == null)
		{
			return Optional.empty();
		}

		final IBPartnerDAO.BPartnerLocationQuery bPartnerLocationQuery = IBPartnerDAO.BPartnerLocationQuery.builder()
				.bpartnerId(bPartnerId)
				.type(IBPartnerDAO.BPartnerLocationQuery.Type.SHIP_TO)
				.applyTypeStrictly(true)
				.city(locationBasicInfo.getCity())
				.postalCode(locationBasicInfo.getPostalCode())
				.countryId(countryId)
				.build();

		return Optional.ofNullable(bPartnerDAO.retrieveBPartnerLocationId(bPartnerLocationQuery));
	}

	private CreateAttributeInstanceReq buildCreateAttributeInstanceReq(@NonNull final JsonAttributeInstance jsonAttributeInstance)
	{
		return CreateAttributeInstanceReq.builder()
				.attributeCode(AttributeCode.ofString(jsonAttributeInstance.getAttributeCode()))
				.value(getAttributeValue(jsonAttributeInstance))
				.build();
	}

	private Object getAttributeValue(@NonNull final JsonAttributeInstance attributeInstance)
	{
		final I_M_Attribute attribute = attributeDAO.retrieveAttributeByValue(attributeInstance.getAttributeCode());

		final AttributeValueType targetAttributeType = AttributeValueType.ofCode(attribute.getAttributeValueType());

		final Object value;

		switch (targetAttributeType)
		{
			case DATE:
				value = attributeInstance.getValueDate();
				break;
			case NUMBER:
				value = attributeInstance.getValueNumber();
				break;
			case STRING:
			case LIST:
				value = attributeInstance.getValueStr();
				break;
			default:
				throw new IllegalArgumentException("@NotSupported@ @AttributeValueType@=" + targetAttributeType + ", @M_Attribute_ID@=" + attribute.getM_Attribute_ID());
		}

		return value;
	}
}
