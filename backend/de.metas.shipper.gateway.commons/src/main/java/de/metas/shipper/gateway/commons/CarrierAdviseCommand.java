/*
 * #%L
 * de.metas.shipper.gateway.commons
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.shipper.gateway.commons;

import de.metas.bpartner.service.IBPartnerBL;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.bpartner.service.IBPartnerOrgBL;
import de.metas.common.delivery.v1.json.JsonAddress;
import de.metas.common.delivery.v1.json.JsonPackageDimensions;
import de.metas.common.delivery.v1.json.request.JsonCarrierService;
import de.metas.common.delivery.v1.json.request.JsonDeliveryAdvisorRequest;
import de.metas.common.delivery.v1.json.request.JsonDeliveryAdvisorRequestItem;
import de.metas.common.delivery.v1.json.request.JsonGoodsType;
import de.metas.common.delivery.v1.json.request.JsonShipperProduct;
import de.metas.common.delivery.v1.json.response.JsonDeliveryAdvisorResponse;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.CarrierAdviseStatus;
import de.metas.inoutcandidate.CarrierGoodsType;
import de.metas.inoutcandidate.CarrierGoodsTypeId;
import de.metas.inoutcandidate.CarrierProductId;
import de.metas.inoutcandidate.CarrierService;
import de.metas.inoutcandidate.CarrierServiceId;
import de.metas.inoutcandidate.ShipmentSchedule;
import de.metas.inoutcandidate.ShipmentScheduleService;
import de.metas.location.ILocationDAO;
import de.metas.location.LocationId;
import de.metas.logging.LogManager;
import de.metas.organization.OrgId;
import de.metas.product.IProductBL;
import de.metas.quantity.Quantity;
import de.metas.shipper.gateway.commons.converters.v1.JsonShipperConverter;
import de.metas.shipper.gateway.commons.model.CarrierGoodsTypeRepository;
import de.metas.shipper.gateway.commons.model.CarrierProduct;
import de.metas.shipper.gateway.commons.model.CarrierProductRepository;
import de.metas.shipper.gateway.commons.model.CarrierShipmentOrderServiceRepository;
import de.metas.shipper.gateway.spi.ShipperGatewayClient;
import de.metas.shipping.IShipperDAO;
import de.metas.shipping.ShipperGatewayId;
import de.metas.shipping.ShipperId;
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.IUOMDAO;
import de.metas.uom.X12DE355;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Location;
import org.compiere.model.I_M_Shipper;
import org.compiere.util.TimeUtil;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class CarrierAdviseCommand
{
	private final static Logger logger = LogManager.getLogger(CarrierAdviseCommand.class);
	// Services
	private final ShipperGatewayServicesRegistry shipperRegistry = SpringContextHolder.instance.getBean(ShipperGatewayServicesRegistry.class);
	private final ShipmentScheduleService shipmentScheduleService = SpringContextHolder.instance.getBean(ShipmentScheduleService.class);
	private final CarrierProductRepository productRepository = SpringContextHolder.instance.getBean(CarrierProductRepository.class);
	private final CarrierGoodsTypeRepository goodsTypeRepository = SpringContextHolder.instance.getBean(CarrierGoodsTypeRepository.class);
	private final CarrierShipmentOrderServiceRepository carrierServiceRepository = SpringContextHolder.instance.getBean(CarrierShipmentOrderServiceRepository.class);
	private final IShipperDAO shipperDAO = Services.get(IShipperDAO.class);
	private final IBPartnerOrgBL bpartnerOrgBL = Services.get(IBPartnerOrgBL.class);
	private final IBPartnerBL bpartnerBL = Services.get(IBPartnerBL.class);
	private final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);
	private final ILocationDAO locationDAO = Services.get(ILocationDAO.class);
	private final IProductBL productBL = Services.get(IProductBL.class);
	private final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);
	private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);

	private final ShipmentScheduleId shipmentScheduleId;

	public static CarrierAdviseCommand of(final @NonNull ShipmentScheduleId id)
	{
		return new CarrierAdviseCommand(id);
	}

	public void execute()
	{
		final ShipmentSchedule shipmentSchedule = retrieveShipmentSchedule();
		if (!shipmentSchedule.getCarrierAdvisingStatus().isRequested())
		{
			logger.info("Skip adviseShipment for {} because it is not requested", shipmentSchedule.getId());
			return;
		}
		updateAdviseStatusAndSave(shipmentSchedule, CarrierAdviseStatus.InProgress);

		try
		{
			final ShipperId shipperId = Check.assumeNotNull(shipmentSchedule.getShipperId(), "shipmentSchedule.shipperId should be set at this point");
			final ShipperGatewayId shipperGatewayId = getShipperGatewayId(shipperId);

			final ShipperGatewayClient client = shipperRegistry
					.getClientFactory(shipperGatewayId)
					.newClientForShipperId(shipperId);

			final JsonDeliveryAdvisorRequest request = createAdvisorRequest(shipperId, shipmentSchedule, client);
			logger.debug("AdviseShipment request: {}", request);
			final JsonDeliveryAdvisorResponse response = client.adviseShipment(request);
			logger.debug("AdviseShipment response: {}", response);
			updateShipmentFromResponse(shipmentSchedule, response);
		}
		catch (final Exception e)
		{
			shipmentSchedule.setCarrierAdviseErrorMessage(e.getMessage());
			updateAdviseStatusAndSave(shipmentSchedule, CarrierAdviseStatus.Failed);
		}
	}

	private @NonNull ShipperGatewayId getShipperGatewayId(final ShipperId shipperId)
	{
		return shipperDAO.getShipperGatewayId(shipperId).orElseThrow();
	}

	private ShipmentSchedule retrieveShipmentSchedule()
	{
		return shipmentScheduleService.getById(shipmentScheduleId);
	}

	private JsonDeliveryAdvisorRequest createAdvisorRequest(final ShipperId shipperId, @NonNull final ShipmentSchedule shipmentSchedule, final ShipperGatewayClient client)
	{
		if (shipmentSchedule.getDateOrdered() == null)
		{
			throw new AdempiereException("shipmentSchedule.dateOrdered is null");
		}
		final I_M_Shipper shipper = shipperDAO.getById(shipperId);

		return JsonDeliveryAdvisorRequest.builder()
				.pickupDate(shipmentSchedule.getDateOrdered().toLocalDate().toString())
				.pickupTimeFrom(TimeUtil.asLocalTime(shipper.getPickupTimeFrom()).toString())
				.pickupTimeTo(TimeUtil.asLocalTime(shipper.getPickupTimeTo()).toString())
				.pickupAddress(getJsonPickupAddress(shipmentSchedule))
				.deliveryAddress(getJsonDeliveryAddress(shipmentSchedule))
				.shipperConfig(Objects.requireNonNull(client.getJsonShipperConfig()))
				.item(getJsonDeliveryAdvisorRequestItem(shipmentSchedule))
				.build();
	}

	private @NonNull JsonDeliveryAdvisorRequestItem getJsonDeliveryAdvisorRequestItem(final @NonNull ShipmentSchedule shipmentSchedule)
	{
		return JsonDeliveryAdvisorRequestItem.builder()
				.numberOfItems(shipmentSchedule.getQuantityToDeliver().toBigDecimal().intValue())
				.grossWeightKg(computeProductGrossWeight(shipmentSchedule))
				.packageDimensions(JsonPackageDimensions.builder()
						//TODO Adrian figure out from where we should retrieve this info
						.heightInCM(30)
						.widthInCM(40)
						.lengthInCM(50)
						.build())
				.build();
	}

	private @NonNull BigDecimal computeProductGrossWeight(final @NonNull ShipmentSchedule shipmentSchedule)
	{
		final Quantity productGrossWeight = productBL.getGrossWeight(shipmentSchedule.getProductId()).orElseThrow(() -> new AdempiereException("Product weight not found"));
		//ensure qty is in kg
		return uomConversionBL.convertQuantityTo(productGrossWeight, shipmentSchedule.getProductId(), uomDAO.getByX12DE355(X12DE355.KILOGRAM))
				.toBigDecimal().setScale(0, RoundingMode.UP);
	}

	private @NonNull JsonAddress getJsonDeliveryAddress(final @NonNull ShipmentSchedule shipmentSchedule)
	{
		final I_C_BPartner deliverToBPartner = bpartnerBL.getById(shipmentSchedule.getShipBPartnerId());
		final I_C_BPartner_Location deliverToBPLocation = Check.assumeNotNull(bpartnerDAO.getBPartnerLocationByIdInTrx(shipmentSchedule.getShipLocationId()), "bp location not null");
		final I_C_Location deliverToLocation = locationDAO.getById(LocationId.ofRepoId(deliverToBPLocation.getC_Location_ID()));
		return JsonShipperConverter.toJsonAddress(DeliveryOrderUtil.prepareAddressFromLocationBP(deliverToLocation, deliverToBPartner)
				.bpartnerId(deliverToBPartner.getC_BPartner_ID())
				.build());
	}

	private JsonAddress getJsonPickupAddress(final @NonNull ShipmentSchedule shipmentSchedule)
	{
		final OrgId orgId = shipmentSchedule.getOrgId();
		final I_C_BPartner pickupFromBPartner = bpartnerOrgBL.retrieveLinkedBPartner(orgId);
		final I_C_Location pickupFromLocation = bpartnerOrgBL.retrieveOrgLocation(orgId);
		return JsonShipperConverter.toJsonAddress(DeliveryOrderUtil.prepareAddressFromLocationBP(pickupFromLocation, pickupFromBPartner).build());
	}

	private void updateShipmentFromResponse(@NonNull final ShipmentSchedule shipmentSchedule, @NonNull final JsonDeliveryAdvisorResponse response)
	{
		if (response.isError())
		{
			shipmentSchedule.setCarrierAdviseErrorMessage(response.getErrorMessage());
			updateAdviseStatusAndSave(shipmentSchedule, CarrierAdviseStatus.Failed);
		}
		else
		{
			final JsonShipperProduct shipperProduct = response.getShipperProduct();
			if (shipperProduct != null)
			{
				shipmentSchedule.setCarrierProductId(extractCarrierProductId(Objects.requireNonNull(shipmentSchedule.getShipperId()), shipperProduct));
			}
			shipmentSchedule.setCarrierGoodsTypeId(extractCarrierGoodsTypeId(Objects.requireNonNull(shipmentSchedule.getShipperId()), response.getGoodsType()));
			shipmentSchedule.setCarrierServices(extractCarrierServiceIds(Objects.requireNonNull(shipmentSchedule.getShipperId()), response.getShipperProductServices()));
			updateAdviseStatusAndSave(shipmentSchedule, CarrierAdviseStatus.Completed);
		}
	}

	private @NonNull Set<CarrierServiceId> extractCarrierServiceIds(@NonNull final ShipperId shipperId, final @NonNull Set<JsonCarrierService> shipperProductServices)
	{
		return shipperProductServices.stream()
				.map(service -> carrierServiceRepository.getOrCreateService(shipperId, service.getId(), service.getName()))
				.map(CarrierService::getId)
				.collect(Collectors.toSet());
	}

	@Nullable
	private CarrierGoodsTypeId extractCarrierGoodsTypeId(@NonNull final ShipperId shipperId, final @NonNull JsonGoodsType goodsType)
	{
		final CarrierGoodsType orCreateGoodsType = goodsTypeRepository.getOrCreateGoodsType(shipperId, goodsType.getId(), goodsType.getName());
		return orCreateGoodsType == null ? null : orCreateGoodsType.getId();
	}

	@Nullable
	private CarrierProductId extractCarrierProductId(@NonNull final ShipperId shipperId, @NonNull final JsonShipperProduct shipperProduct)
	{
		final CarrierProduct carrierProduct = productRepository.getOrCreateCarrierProduct(shipperId, shipperProduct.getCode(), shipperProduct.getName());
		return carrierProduct == null ? null : carrierProduct.getId();
	}

	private void updateAdviseStatusAndSave(@NonNull final ShipmentSchedule shipmentSchedule, @NonNull final CarrierAdviseStatus status)
	{
		shipmentSchedule.setCarrierAdvisingStatus(status);
		shipmentScheduleService.save(shipmentSchedule);
	}
}
