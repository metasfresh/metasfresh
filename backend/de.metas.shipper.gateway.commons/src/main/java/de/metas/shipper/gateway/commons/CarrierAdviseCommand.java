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

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.bpartner.service.IBPartnerOrgBL;
import de.metas.common.delivery.v1.json.JsonAddress;
import de.metas.common.delivery.v1.json.JsonContact;
import de.metas.common.delivery.v1.json.JsonPackageDimensions;
import de.metas.common.delivery.v1.json.request.JsonCarrierService;
import de.metas.common.delivery.v1.json.request.JsonDeliveryAdvisorRequest;
import de.metas.common.delivery.v1.json.request.JsonDeliveryAdvisorRequestItem;
import de.metas.common.delivery.v1.json.request.JsonGoodsType;
import de.metas.common.delivery.v1.json.request.JsonShipperProduct;
import de.metas.common.delivery.v1.json.response.JsonDeliveryAdvisorResponse;
import de.metas.externalsystem.ExternalSystemId;
import de.metas.externalsystem.ExternalSystemRepository;
import de.metas.incoterms.Incoterms;
import de.metas.incoterms.IncotermsId;
import de.metas.incoterms.IncotermsRepository;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.CarrierAdviseStatus;
import de.metas.inoutcandidate.CarrierGoodsType;
import de.metas.inoutcandidate.CarrierGoodsTypeId;
import de.metas.shipping.CarrierProductId;
import de.metas.inoutcandidate.CarrierService;
import de.metas.inoutcandidate.CarrierServiceId;
import de.metas.inoutcandidate.ShipmentSchedule;
import de.metas.inoutcandidate.ShipmentScheduleService;
import de.metas.location.ILocationDAO;
import de.metas.location.LocationId;
import de.metas.logging.LogManager;
import de.metas.order.IOrderDAO;
import de.metas.order.OrderAndLineId;
import de.metas.organization.OrgId;
import de.metas.product.IProductBL;
import de.metas.product.PackageDimensions;
import de.metas.product.Product;
import de.metas.product.ProductRepository;
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
import de.metas.user.User;
import de.metas.user.UserRepository;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Location;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_M_Shipper;
import org.compiere.util.TimeUtil;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class CarrierAdviseCommand
{
	@NonNull private final static Logger logger = LogManager.getLogger(CarrierAdviseCommand.class);
	// Services
	@NonNull private final ShipperGatewayServicesRegistry shipperRegistry = SpringContextHolder.instance.getBean(ShipperGatewayServicesRegistry.class);
	@NonNull private final ShipmentScheduleService shipmentScheduleService = SpringContextHolder.instance.getBean(ShipmentScheduleService.class);
	@NonNull private final CarrierProductRepository carrierProductRepository = SpringContextHolder.instance.getBean(CarrierProductRepository.class);
	@NonNull private final CarrierGoodsTypeRepository goodsTypeRepository = SpringContextHolder.instance.getBean(CarrierGoodsTypeRepository.class);
	@NonNull private final CarrierShipmentOrderServiceRepository carrierServiceRepository = SpringContextHolder.instance.getBean(CarrierShipmentOrderServiceRepository.class);
	@NonNull private final ProductRepository productRepository = SpringContextHolder.instance.getBean(ProductRepository.class);
	@NonNull private final IncotermsRepository incotermsRepository = SpringContextHolder.instance.getBean(IncotermsRepository.class);
	@NonNull private final ExternalSystemRepository externalSystemRepository = SpringContextHolder.instance.getBean(ExternalSystemRepository.class);
	@NonNull private final UserRepository userRepository = SpringContextHolder.instance.getBean(UserRepository.class);
	@NonNull private final IShipperDAO shipperDAO = Services.get(IShipperDAO.class);
	@NonNull private final IBPartnerOrgBL bpartnerOrgBL = Services.get(IBPartnerOrgBL.class);
	@NonNull private final IBPartnerBL bpartnerBL = Services.get(IBPartnerBL.class);
	@NonNull private final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);
	@NonNull private final ILocationDAO locationDAO = Services.get(ILocationDAO.class);
	@NonNull private final IProductBL productBL = Services.get(IProductBL.class);
	@NonNull private final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);
	@NonNull private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
	@NonNull private final IOrderDAO orderDAO = Services.get(IOrderDAO.class);

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
			final ShipperGatewayId shipperGatewayId = getShipperGatewayIdOrNull(shipperId);

			final JsonDeliveryAdvisorResponse response;
			if(shipperGatewayId != null)
			{
				final ShipperGatewayClient client = shipperRegistry
						.getClientFactory(shipperGatewayId)
						.newClientForShipperId(shipperId);

				final JsonDeliveryAdvisorRequest request = createAdvisorRequest(shipperId, shipmentSchedule, client);
				logger.debug("AdviseShipment request: {}", request);
				response = client.adviseShipment(request);
				logger.debug("AdviseShipment response: {}", response);
			}
			else
			{
				final I_M_Shipper shipper =  shipperDAO.getById(shipperId);
				response = JsonDeliveryAdvisorResponse.builder()
						.requestId(UUID.randomUUID().toString())
						.shipperProduct(JsonShipperProduct.builder()
								.name(shipper.getName())
								.code(shipper.getName())
								.build())
						.build();
			}




			updateShipmentFromResponse(shipmentSchedule, response);
		}
		catch (final Exception e)
		{
			shipmentSchedule.setCarrierAdviseErrorMessage(e.getMessage());
			updateAdviseStatusAndSave(shipmentSchedule, CarrierAdviseStatus.Failed);
		}
	}

	@Nullable
	private ShipperGatewayId getShipperGatewayIdOrNull(@NonNull final ShipperId shipperId)
	{
		return shipperDAO.getShipperGatewayId(shipperId).orElse(null);
	}

	private ShipmentSchedule retrieveShipmentSchedule()
	{
		return shipmentScheduleService.getById(shipmentScheduleId);
	}

	private JsonDeliveryAdvisorRequest createAdvisorRequest(@NonNull final ShipperId shipperId, @NonNull final ShipmentSchedule shipmentSchedule, final ShipperGatewayClient client)
	{
		if (shipmentSchedule.getDateOrdered() == null)
		{
			throw new AdempiereException("shipmentSchedule.dateOrdered is null");
		}
		final I_M_Shipper shipper = shipperDAO.getById(shipperId);

		final I_C_BPartner deliverToBPartner = bpartnerBL.getById(shipmentSchedule.getShipBPartnerId());
		final I_C_BPartner_Location deliverToBPLocation = Check.assumeNotNull(bpartnerDAO.getBPartnerLocationByIdInTrx(shipmentSchedule.getShipLocationId()), "bp location not null");
		final User deliverToContact = shipmentSchedule.getShipContactUserId() != null ? userRepository.getByIdInTrx(shipmentSchedule.getShipContactUserId()) : null;

		final OrgId orgId = shipmentSchedule.getOrgId();
		final I_C_BPartner pickupFromBPartner = bpartnerOrgBL.retrieveLinkedBPartner(orgId);
		final I_C_BPartner_Location pickupFromBPLocation = Check.assumeNotNull(bpartnerOrgBL.retrieveOrgBPLocation(orgId), "Org location should be present");
		final User pickupFromContact = bpartnerBL.retrieveContactOrNull(IBPartnerBL.RetrieveContactRequest.builder()
				.contactType(IBPartnerBL.RetrieveContactRequest.ContactType.SHIP_TO_DEFAULT)
				.onlyActive(true)
				.bpartnerId(BPartnerId.ofRepoId(pickupFromBPartner.getC_BPartner_ID()))
				.ifNotFound(IBPartnerBL.RetrieveContactRequest.IfNotFound.RETURN_DEFAULT_CONTACT)
				.build());

		final JsonDeliveryAdvisorRequest.JsonDeliveryAdvisorRequestBuilder requestBuilder = JsonDeliveryAdvisorRequest.builder()
				.pickupDate(shipmentSchedule.getDateOrdered().toLocalDate().toString())
				.pickupTimeFrom(TimeUtil.asLocalTime(shipper.getPickupTimeFrom()).toString())
				.pickupTimeTo(TimeUtil.asLocalTime(shipper.getPickupTimeTo()).toString())
				.pickupAddress(getJsonAddress(pickupFromBPartner, pickupFromBPLocation))
				.pickupContact(getJsonContact(pickupFromBPartner, pickupFromBPLocation, pickupFromContact))
				.deliveryAddress(getJsonAddress(deliverToBPartner, deliverToBPLocation))
				.deliveryContact(getJsonContact(deliverToBPartner, deliverToBPLocation, deliverToContact))
				.shipperConfig(Objects.requireNonNull(client.getJsonShipperConfig()))
				.item(getJsonDeliveryAdvisorRequestItem(shipmentSchedule));

		final OrderAndLineId orderAndLineId = shipmentSchedule.getOrderAndLineId();
		if (orderAndLineId != null)
		{
			final I_C_Order order = orderDAO.getById(orderAndLineId.getOrderId());
			requestBuilder.customerReference(order.getPOReference());

			final IncotermsId incotermsId = IncotermsId.ofRepoIdOrNull(order.getC_Incoterms_ID());
			if (incotermsId != null)
			{
				final Incoterms incoterms = incotermsRepository.getById(incotermsId);
				requestBuilder.incotermsValue(incoterms.getValue());
			}

			final ExternalSystemId externalSystemId = ExternalSystemId.ofRepoIdOrNull(order.getExternalSystem_ID());
			if(externalSystemId != null)
			{
				requestBuilder.externalSystemValue(externalSystemRepository.getById(externalSystemId).getType().getValue());
			}

		}


		return requestBuilder.build();
	}

	@NonNull
	private JsonDeliveryAdvisorRequestItem getJsonDeliveryAdvisorRequestItem(@NonNull final ShipmentSchedule shipmentSchedule)
	{
		final Product product = productRepository.getById(shipmentSchedule.getProductId());
		final PackageDimensions dimensions = PackageDimensions.ofProductDimensionsAndQty(product.getPackageDimensions(), shipmentSchedule.getQuantityToDeliver());
		return JsonDeliveryAdvisorRequestItem.builder()
				.numberOfItems(shipmentSchedule.getQuantityToDeliver().toBigDecimal().intValue())
				.grossWeightKg(computeProductGrossWeight(shipmentSchedule))
				.packageDimensions(JsonPackageDimensions.builder()
						.heightInCM(dimensions.getHeightInCM())
						.widthInCM(dimensions.getWidthInCM())
						.lengthInCM(dimensions.getLengthInCM())
						.build())
				.build();
	}

	@NonNull
	private BigDecimal computeProductGrossWeight(@NonNull final ShipmentSchedule shipmentSchedule)
	{
		final Quantity productGrossWeight = productBL.getGrossWeight(shipmentSchedule.getProductId()).orElseThrow(() -> new AdempiereException("Product weight not found"));
		//ensure qty is in kg
		return uomConversionBL.convertQuantityTo(productGrossWeight, shipmentSchedule.getProductId(), uomDAO.getByX12DE355(X12DE355.KILOGRAM))
				.toBigDecimal().setScale(0, RoundingMode.UP);
	}

	@NonNull
	private JsonAddress getJsonAddress(@NonNull final I_C_BPartner bPartner, @NonNull final I_C_BPartner_Location bpLocation)
	{
		final I_C_Location deliverToLocation = locationDAO.getById(LocationId.ofRepoId(bpLocation.getC_Location_ID()));

		return JsonShipperConverter.toJsonAddress(DeliveryOrderUtil.prepareAddressFromLocationBP(deliverToLocation, bPartner)
				.bpartnerId(bPartner.getC_BPartner_ID())
				.build());
	}

	@NonNull
	private JsonContact getJsonContact(@NonNull final I_C_BPartner bPartner,
									   @NonNull final I_C_BPartner_Location bpLocation,
									   @Nullable final User contact)
	{
		return JsonShipperConverter.toJsonContactOrNull(DeliveryOrderUtil.getContactPerson(bPartner, bpLocation, contact));
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
			final ShipperId shipperId = Check.assumeNotNull(shipmentSchedule.getShipperId(), "Shipment Schedule ShipperId should be set at this point");
			final JsonGoodsType goodsType = response.getGoodsType();
			if(goodsType != null)
			{
				shipmentSchedule.setCarrierGoodsTypeId(extractCarrierGoodsTypeId(shipperId, goodsType));
			}
			shipmentSchedule.setCarrierServices(extractCarrierServiceIds(shipmentSchedule.getShipperId(), response.getShipperProductServices()));
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

	@NonNull
	private CarrierGoodsTypeId extractCarrierGoodsTypeId(@NonNull final ShipperId shipperId, final @NonNull JsonGoodsType jsonGoodsType)
	{
		final CarrierGoodsType goodsType = goodsTypeRepository.getOrCreateGoodsType(shipperId, jsonGoodsType.getId(), jsonGoodsType.getName());
		return goodsType.getId();
	}

	@NonNull
	private CarrierProductId extractCarrierProductId(@NonNull final ShipperId shipperId, @NonNull final JsonShipperProduct shipperProduct)
	{
		final String name = shipperProduct.getName() != null ? shipperProduct.getName() : shipperProduct.getCode();
		final CarrierProduct carrierProduct = carrierProductRepository.getOrCreateCarrierProduct(shipperId, shipperProduct.getCode(), name);
		return carrierProduct.getId();
	}

	private void updateAdviseStatusAndSave(@NonNull final ShipmentSchedule shipmentSchedule, @NonNull final CarrierAdviseStatus status)
	{
		shipmentSchedule.setCarrierAdvisingStatus(status);
		shipmentScheduleService.save(shipmentSchedule);
	}
}
