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

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.bpartner.service.IBPartnerOrgBL;
import de.metas.common.delivery.v1.json.JsonAddress;
import de.metas.common.delivery.v1.json.JsonContact;
import de.metas.common.delivery.v1.json.JsonMoney;
import de.metas.common.delivery.v1.json.JsonTopLevelType;
import de.metas.common.delivery.v1.json.JsonPackageDimensions;
import de.metas.common.delivery.v1.json.JsonQuantity;
import de.metas.currency.Amount;
import de.metas.customstariff.CustomsTariffId;
import de.metas.customstariff.CustomsTariffRepository;
import de.metas.interfaces.I_C_OrderLine;
import de.metas.money.Money;
import de.metas.money.MoneyService;
import de.metas.uom.UomId;
import de.metas.common.delivery.v1.json.request.JsonCarrierService;
import de.metas.common.delivery.v1.json.request.JsonDeliveryAdvisorRequest;
import de.metas.common.delivery.v1.json.request.JsonShipperConfig;
import de.metas.common.delivery.v1.json.request.JsonDeliveryAdvisorRequestItem;
import de.metas.common.delivery.v1.json.request.JsonDeliveryAdvisorRequestParcel;
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
import de.metas.quantity.Quantitys;
import de.metas.shipper.gateway.commons.converters.v1.JsonShipperConverter;
import de.metas.shipper.gateway.commons.mapping.ShipperMappingConfigList;
import de.metas.shipper.gateway.commons.mapping.ShipperMappingConfigRepository;
import de.metas.shipper.gateway.commons.model.CarrierGoodsTypeRepository;
import de.metas.shipper.gateway.commons.model.CarrierProduct;
import de.metas.shipper.gateway.commons.model.CarrierProductRepository;
import de.metas.shipper.gateway.commons.model.CarrierShipmentOrderServiceRepository;
import de.metas.shipper.gateway.spi.ShipperConfigRequest;
import de.metas.shipper.gateway.spi.ShipperGatewayClient;
import de.metas.shipping.CarrierProductId;
import de.metas.shipping.Shipper;
import de.metas.shipping.ShipperGatewayId;
import de.metas.shipping.ShipperRepository;
import de.metas.shipping.ShipperId;
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.IUOMDAO;
import de.metas.uom.X12DE355;
import de.metas.user.User;
import de.metas.user.UserRepository;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Location;
import org.compiere.model.I_C_Order;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.math.RoundingMode;
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
	@NonNull private final CarrierProductAllocationService carrierProductAllocationService = SpringContextHolder.instance.getBean(CarrierProductAllocationService.class);
	@NonNull private final ShipperMappingConfigRepository shipperMappingConfigRepository = SpringContextHolder.instance.getBean(ShipperMappingConfigRepository.class);
	@NonNull private final JsonShipperConverter jsonShipperConverter = SpringContextHolder.instance.getBean(JsonShipperConverter.class);
	@NonNull private final CustomsTariffRepository customsTariffRepository = SpringContextHolder.instance.getBean(CustomsTariffRepository.class);
	@NonNull private final ShipperRepository shipperRepository = SpringContextHolder.instance.getBean(ShipperRepository.class);
	@NonNull private final MoneyService moneyService = SpringContextHolder.instance.getBean(MoneyService.class);
	@NonNull private final IBPartnerOrgBL bpartnerOrgBL = Services.get(IBPartnerOrgBL.class);
	@NonNull private final IBPartnerBL bpartnerBL = Services.get(IBPartnerBL.class);
	@NonNull private final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);
	@NonNull private final ILocationDAO locationDAO = Services.get(ILocationDAO.class);
	@NonNull private final IProductBL productBL = Services.get(IProductBL.class);
	@NonNull private final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);
	@NonNull private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
	@NonNull private final IOrderDAO orderDAO = Services.get(IOrderDAO.class);

	private final ShipmentScheduleId shipmentScheduleId;
	// HU-advise: the packed-HU parcel (parcel-level fields + per-product items). Null for the schedule-advise path.
	@Nullable private final JsonDeliveryAdvisorRequestParcel packedHUParcel;

	public static CarrierAdviseCommand of(final @NonNull ShipmentScheduleId id)
	{
		return new CarrierAdviseCommand(id, null);
	}

	public static CarrierAdviseCommand ofPackedHU(
			@NonNull final ShipmentScheduleId shipmentScheduleId,
			@NonNull final JsonDeliveryAdvisorRequestParcel packedHUParcel)
	{
		return new CarrierAdviseCommand(shipmentScheduleId, packedHUParcel);
	}

	/**
	 * Advises the schedule only when its advising status is still {@code Requested} — the auto/async path
	 * (at order completion), which must not touch schedules that were already advised or set Manual.
	 */
	public void execute()
	{
		final ShipmentSchedule shipmentSchedule = retrieveShipmentSchedule();
		if (!shipmentSchedule.getCarrierAdvisingStatus().isRequested())
		{
			logger.info("Skip adviseShipment for {} because it is not requested", shipmentSchedule.getId());
			return;
		}
		// The advise is enqueued async, so the schedule can become processed/closed (shipped) between enqueue and
		// now. Don't advise a closed schedule; resolve the dangling Requested status instead — Completed when a
		// carrier product was already determined, otherwise NotRequested.
		if (shipmentSchedule.isProcessed() || shipmentSchedule.isClosed())
		{
			final CarrierAdviseStatus resolvedStatus = shipmentSchedule.getCarrierProductId() != null
					? CarrierAdviseStatus.Completed
					: CarrierAdviseStatus.NotRequested;
			logger.info("Skip adviseShipment for {} because it is processed/closed; resolving status to {}", shipmentSchedule.getId(), resolvedStatus);
			updateAdviseStatusAndSave(shipmentSchedule, resolvedStatus);
			return;
		}
		advise(shipmentSchedule);
	}

	/**
	 * Re-advises the schedule regardless of its current advising status — the mobile packing re-advise, where
	 * the schedule is typically already {@code Completed} from the auto-advise at order completion but must be
	 * re-advised against the actually-packed HU (this {@link CarrierAdviseCommand}'s {@code packedHUItem}).
	 * <p>
	 * The caller is responsible for excluding {@code Manual} schedules (a manually-set carrier product must not
	 * be overwritten) — see {@link de.metas.picking.workflow.PackedHUCarrierAdviseService#advise}.
	 */
	public void executeSync()
	{
		advise(retrieveShipmentSchedule());
	}

	private void advise(@NonNull final ShipmentSchedule shipmentSchedule)
	{
		updateAdviseStatusAndSave(shipmentSchedule, CarrierAdviseStatus.InProgress);

		try
		{
			final JsonDeliveryAdvisorResponse response = callAdvisor(shipmentSchedule);
			updateShipmentFromResponse(shipmentSchedule, response);
		}
		catch (final Exception e)
		{
			shipmentSchedule.setCarrierProductId(null);
			shipmentSchedule.setCarrierAdviseErrorMessage(e.getMessage());
			updateAdviseStatusAndSave(shipmentSchedule, CarrierAdviseStatus.Failed);
		}
	}

	/**
	 * Runs the shipper-gateway advisor for the schedule (against this command's packed-HU parcel when set) and
	 * resolves the response into a carrier product + goods type + services WITHOUT persisting anything onto the
	 * shipment schedule.
	 * <p>
	 * This is the mobile-packing display path: the picker's re-advise must NOT overwrite the schedule (which is the
	 * WebUI advise + shipment-carrier source and whose write triggers expensive recomputes) — the advised carrier is
	 * persisted only onto the picking job (header/line) by the caller. The auto/WebUI advise paths keep using
	 * {@link #execute()} / {@link #executeSync()}, which DO persist onto the schedule.
	 */
	@NonNull
	public AdvisedCarrierResult adviseWithoutPersisting()
	{
		final ShipmentSchedule shipmentSchedule = retrieveShipmentSchedule();
		final JsonDeliveryAdvisorResponse response = callAdvisor(shipmentSchedule);
		if (response.isError())
		{
			throw new AdempiereException("Carrier advise failed: " + response.getErrorMessage());
		}
		return resolveAdvisedCarrier(shipmentSchedule, response);
	}

	private JsonDeliveryAdvisorResponse callAdvisor(@NonNull final ShipmentSchedule shipmentSchedule)
	{
		final ShipperId shipperId = Check.assumeNotNull(shipmentSchedule.getShipperId(), "shipmentSchedule.shipperId should be set at this point");
		final ShipperGatewayId shipperGatewayId = getShipperGatewayIdOrNull(shipperId);

		if (shipperGatewayId != null)
		{
			final ShipperGatewayClient client = shipperRegistry
					.getClientFactory(shipperGatewayId)
					.newClientForShipperId(shipperId);

			final JsonDeliveryAdvisorRequest request = createAdvisorRequest(shipperId, shipmentSchedule, client);
			logger.debug("AdviseShipment request: {}", request);
			final JsonDeliveryAdvisorResponse response = client.adviseShipment(request);
			logger.debug("AdviseShipment response: {}", response);
			return response;
		}
		else
		{
			final Shipper shipper = shipperRepository.getById(shipperId);
			return JsonDeliveryAdvisorResponse.builder()
					.requestId(UUID.randomUUID().toString())
					.shipperProduct(JsonShipperProduct.builder()
							.name(shipper.getName())
							.code(shipper.getName())
							.build())
					.build();
		}
	}

	/**
	 * Resolves a successful advisor response into the carrier product + goods type + services (creating the
	 * carrier-product / goods-type / service master records as the persisting path does), WITHOUT touching the
	 * shipment schedule. Shared shape with {@link #updateShipmentFromResponse} but persistence-free.
	 */
	@NonNull
	private AdvisedCarrierResult resolveAdvisedCarrier(@NonNull final ShipmentSchedule shipmentSchedule, @NonNull final JsonDeliveryAdvisorResponse response)
	{
		final ShipperId shipperId = Check.assumeNotNull(shipmentSchedule.getShipperId(), "Shipment Schedule ShipperId should be set at this point");

		final JsonShipperProduct shipperProduct = response.getShipperProduct();
		final CarrierProductId carrierProductId = shipperProduct != null
				? extractCarrierProductId(shipperId, shipperProduct)
				: null;

		final JsonGoodsType goodsType = response.getGoodsType();
		final CarrierGoodsTypeId goodsTypeId = goodsType != null
				? extractCarrierGoodsTypeId(shipperId, goodsType)
				: null;

		final Set<CarrierServiceId> serviceIds = extractCarrierServiceIds(shipperId, response.getShipperProductServices());

		return AdvisedCarrierResult.builder()
				.carrierProductId(carrierProductId)
				.carrierGoodsTypeId(goodsTypeId)
				.carrierServices(ImmutableList.copyOf(serviceIds))
				.build();
	}

	@Nullable
	private ShipperGatewayId getShipperGatewayIdOrNull(@NonNull final ShipperId shipperId)
	{
		return shipperRepository.getShipperGatewayId(shipperId).orElse(null);
	}

	private ShipmentSchedule retrieveShipmentSchedule()
	{
		return shipmentScheduleService.getById(shipmentScheduleId);
	}

	private JsonDeliveryAdvisorRequest createAdvisorRequest(@NonNull final ShipperId shipperId, @NonNull final ShipmentSchedule shipmentSchedule, final ShipperGatewayClient client)
	{
		final JsonDeliveryAdvisorRequestParcel parcel = packedHUParcel != null
				? packedHUParcel
				: getJsonDeliveryAdvisorRequestParcel(shipmentSchedule);
		final JsonDeliveryAdvisorRequest.JsonDeliveryAdvisorRequestBuilder requestBuilder = JsonDeliveryAdvisorRequest.builder()
				.grossWeightKg(parcel.getGrossWeightKg())
				.packageDimensions(parcel.getPackageDimensions())
				.topLevelType(parcel.getTopLevelType())
				.items(parcel.getItems());
		return applyAdvisorContext(requestBuilder, shipperId, shipmentSchedule, client).build();
	}

	private JsonDeliveryAdvisorRequest.JsonDeliveryAdvisorRequestBuilder applyAdvisorContext(
			@NonNull final JsonDeliveryAdvisorRequest.JsonDeliveryAdvisorRequestBuilder builder,
			@NonNull final ShipperId shipperId,
			@NonNull final ShipmentSchedule shipmentSchedule,
			@NonNull final ShipperGatewayClient client)
	{
		if (shipmentSchedule.getDateOrdered() == null)
		{
			throw new AdempiereException("shipmentSchedule.dateOrdered is null");
		}
		final Shipper shipper = shipperRepository.getById(shipperId);

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

		builder.pickupDate(shipmentSchedule.getDateOrdered().toLocalDate().toString())
				.pickupTimeFrom(shipper.getPickupTimeFrom() != null ? shipper.getPickupTimeFrom().toString() : null)
				.pickupTimeTo(shipper.getPickupTimeTo() != null ? shipper.getPickupTimeTo().toString() : null)
				.pickupAddress(getJsonAddress(pickupFromBPartner, pickupFromBPLocation))
				.pickupContact(getJsonContact(pickupFromBPartner, pickupFromBPLocation, pickupFromContact))
				.deliveryAddress(getJsonAddress(deliverToBPartner, deliverToBPLocation))
				.deliveryContact(getJsonContact(deliverToBPartner, deliverToBPLocation, deliverToContact));

		@Nullable ExternalSystemId externalSystemId = null;
		final OrderAndLineId orderAndLineId = shipmentSchedule.getOrderAndLineId();
		if (orderAndLineId != null)
		{
			final I_C_Order order = orderDAO.getById(orderAndLineId.getOrderId());
			builder.customerReference(order.getPOReference());

			final IncotermsId incotermsId = IncotermsId.ofRepoIdOrNull(order.getC_Incoterms_ID());
			if (incotermsId != null)
			{
				final Incoterms incoterms = incotermsRepository.getById(incotermsId);
				builder.incotermsValue(incoterms.getValue());
			}

			externalSystemId = ExternalSystemId.ofRepoIdOrNull(order.getExternalSystem_ID());
			if (externalSystemId != null)
			{
				builder.externalSystemValue(externalSystemRepository.getById(externalSystemId).getType().getValue());
			}

			builder.preAdviceRequired(StringUtils.ofBoolean(order.isPreAdviceRequired()));
		}

		final ShipperConfigRequest shipperConfigRequest = ShipperConfigRequest.builder()
				.externalSystemId(externalSystemId)
				.build();
		final JsonShipperConfig effectiveShipperConfig = client.getJsonShipperConfigEffective(shipperConfigRequest);
		if (effectiveShipperConfig != null)
		{
			builder.shipperConfig(effectiveShipperConfig);
		}

		final ShipperMappingConfigList mappingConfigs = shipperMappingConfigRepository.getByShipperId(shipperId);
		builder.mappingConfigs(jsonShipperConverter.toJsonMappingConfigList(mappingConfigs));

		return builder;
	}

	// Carrier "final info" build path — schedule-advise (2 of 3).
	// Unit price / total value / shipped quantity derivation is shared across the three nShift build paths via
	// CarrierAdviseItemValue (so they cannot drift):
	//   - HU-advise:        PackedHUCarrierAdviseService#buildRequestItem
	//   - schedule-advise:  CarrierAdviseCommand#getJsonDeliveryAdvisorRequestParcel
	//   - delivery-order:   NShiftDraftDeliveryOrderCreator#createDeliveryOrderItem
	@NonNull
	private JsonDeliveryAdvisorRequestParcel getJsonDeliveryAdvisorRequestParcel(@NonNull final ShipmentSchedule shipmentSchedule)
	{
		final Product product = productRepository.getById(shipmentSchedule.getProductId());
		final PackageDimensions dimensions = product.getPackageDimensions();
		final BigDecimal grossWeightKg = computeProductGrossWeight(shipmentSchedule);

		// Customs tariff — same source as NShiftDraftDeliveryOrderCreator#createDeliveryOrderItem
		final CustomsTariffId customsTariffId = product.getCustomsTariffId();
		final String customsTariff = customsTariffId != null ? customsTariffRepository.getById(customsTariffId).getValue() : null;

		// Unit price / total value from the order line — same derivation as the other two nShift build paths, via
		// the shared CarrierAdviseItemValue. Schedule-advise has no packed HU, so it advises for 1 ordered unit
		// (numberOfItems=1); with qty 1 the unit price and total value are the same value.
		JsonMoney unitPrice = null;
		JsonMoney totalValue = null;
		JsonQuantity shippedQuantity = null;
		final OrderAndLineId orderAndLineId = shipmentSchedule.getOrderAndLineId();
		if (orderAndLineId != null)
		{
			final I_C_OrderLine orderLine = orderDAO.getOrderLineById(orderAndLineId);
			final Quantity oneOrderedUnit = Quantitys.of(BigDecimal.ONE, UomId.ofRepoId(orderLine.getC_UOM_ID()));
			final CarrierAdviseItemValue itemValue = CarrierAdviseItemValue.compute(moneyService, orderLine, shipmentSchedule.getProductId(), oneOrderedUnit);
			unitPrice = toJsonMoney(itemValue.getUnitPrice());
			totalValue = toJsonMoney(itemValue.getTotalValue());
			final Quantity sq = itemValue.getShippedQuantity();
			shippedQuantity = JsonQuantity.builder()
					.value(sq.toBigDecimal())
					.uomCode(sq.getX12DE355().getCode())
					.build();
		}

		final JsonDeliveryAdvisorRequestItem item = JsonDeliveryAdvisorRequestItem.builder()
				.numberOfItems(1)
				.productName(product.getName().getDefaultValue())
				.productValue(product.getValue())
				.customsTariff(customsTariff)
				.unitPrice(unitPrice)
				.totalValue(totalValue)
				.shippedQuantity(shippedQuantity)
				.totalWeightInKg(grossWeightKg)
				.build();

		return JsonDeliveryAdvisorRequestParcel.builder()
				// schedule-advise has no packed HU; a single product unit is a customer unit (CU)
				.topLevelType(JsonTopLevelType.CU.getCode())
				.grossWeightKg(grossWeightKg)
				.packageDimensions(JsonPackageDimensions.builder()
						.heightInCM(dimensions.getHeightInCM())
						.widthInCM(dimensions.getWidthInCM())
						.lengthInCM(dimensions.getLengthInCM())
						.build())
				.items(ImmutableList.of(item))
				.build();
	}

	@NonNull
	private JsonMoney toJsonMoney(@NonNull final Money money)
	{
		// Amount carries both the value and its ISO currency code, so the JsonMoney comes from a single coherent source.
		final Amount amount = moneyService.toAmount(money);
		return JsonMoney.builder()
				.amount(amount.getAsBigDecimal())
				.currencyCode(amount.getCurrencyCode().toThreeLetterCode())
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

		return JsonShipperConverter.toJsonAddress(DeliveryOrderUtil.prepareAddressFromLocationBP(deliverToLocation, bPartner, bpLocation)
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
			final ShipperId shipperId = Check.assumeNotNull(shipmentSchedule.getShipperId(), "Shipment Schedule ShipperId should be set at this point");

			final JsonShipperProduct shipperProduct = response.getShipperProduct();
			final CarrierProductId carrierProductId;
			if (shipperProduct != null)
			{
				carrierProductId = extractCarrierProductId(shipperId, shipperProduct);
				shipmentSchedule.setCarrierProductId(carrierProductId);
			}
			else
			{
				carrierProductId = null;
			}

			final JsonGoodsType goodsType = response.getGoodsType();
			if (goodsType != null)
			{
				final CarrierGoodsTypeId goodsTypeId = extractCarrierGoodsTypeId(shipperId, goodsType);
				shipmentSchedule.setCarrierGoodsTypeId(goodsTypeId);
				if (carrierProductId != null)
				{
					carrierProductAllocationService.addGoodsTypeIfMissing(carrierProductId, goodsTypeId);
				}
			}

			final Set<CarrierServiceId> serviceIds = extractCarrierServiceIds(shipperId, response.getShipperProductServices());
			shipmentSchedule.setCarrierServices(serviceIds);
			if (carrierProductId != null)
			{
				serviceIds.forEach(serviceId -> carrierProductAllocationService.addServiceIfMissing(carrierProductId, serviceId));
			}

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

	/**
	 * The carrier advised for a packed HU, resolved from a successful advisor response WITHOUT persisting to the
	 * shipment schedule — the mobile-packing display result, persisted only onto the picking job by the caller.
	 */
	@lombok.Value
	@lombok.Builder
	public static class AdvisedCarrierResult
	{
		@Nullable CarrierProductId carrierProductId;
		@Nullable CarrierGoodsTypeId carrierGoodsTypeId;
		@NonNull ImmutableList<CarrierServiceId> carrierServices;
	}
}
