/*
 * #%L
 * de.metas.shipper.gateway.nshift
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

package de.metas.shipper.gateway.nshift;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.bpartner.service.IBPartnerOrgBL;
import de.metas.common.util.CoalesceUtil;
import de.metas.customstariff.CustomsTariffId;
import de.metas.customstariff.CustomsTariffRepository;
import de.metas.handlingunits.shipping.HUPackageService;
import de.metas.inout.InOutAndLineId;
import de.metas.inout.InOutId;
import de.metas.interfaces.I_C_OrderLine;
import de.metas.location.ILocationDAO;
import de.metas.location.LocationId;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.order.IOrderDAO;
import de.metas.organization.OrgId;
import de.metas.product.IProductBL;
import de.metas.product.Product;
import de.metas.product.ProductId;
import de.metas.product.ProductRepository;
import de.metas.quantity.Quantity;
import de.metas.shipper.gateway.commons.DeliveryOrderUtil;
import de.metas.shipper.gateway.commons.model.CarrierGoodsTypeRepository;
import de.metas.shipper.gateway.commons.model.CarrierProductRepository;
import de.metas.shipper.gateway.commons.model.CarrierShipmentOrderServiceRepository;
import de.metas.shipper.gateway.spi.DraftDeliveryOrderCreator;
import de.metas.shipper.gateway.spi.model.Address;
import de.metas.shipper.gateway.spi.model.ContactPerson;
import de.metas.shipper.gateway.spi.model.DeliveryOrder;
import de.metas.shipper.gateway.spi.model.DeliveryOrderItem;
import de.metas.shipper.gateway.spi.model.DeliveryOrderParcel;
import de.metas.shipper.gateway.spi.model.DeliveryOrderItemGroupKey;
import de.metas.shipper.gateway.spi.model.PickupDate;
import de.metas.shipping.PurchaseOrderToShipperTransportationRepository;
import de.metas.shipping.ShipperGatewayId;
import de.metas.shipping.ShipperId;
import de.metas.shipping.mpackage.PackageId;
import de.metas.shipping.mpackage.PackageItem;
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.UomId;
import de.metas.user.User;
import de.metas.user.UserRepository;
import de.metas.util.Check;
import de.metas.util.Services;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Location;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static de.metas.shipper.gateway.commons.DeliveryOrderUtil.getPOReferences;

@Component
@RequiredArgsConstructor
public class NShiftDraftDeliveryOrderCreator implements DraftDeliveryOrderCreator
{
	@NonNull private final CarrierProductRepository carrierProductRepository;
	@NonNull private final CarrierGoodsTypeRepository carrierGoodsTypeRepository;
	@NonNull private final CarrierShipmentOrderServiceRepository carrierServiceRepository;
	@NonNull private final PurchaseOrderToShipperTransportationRepository purchaseOrderToShipperTransportationRepository;
	@NonNull private final UserRepository userRepository;
	@NonNull private final ProductRepository productRepository;
	@NonNull private final CustomsTariffRepository customsTariffRepository;
	@NonNull private final HUPackageService huPackageService;

	@NonNull private final IBPartnerOrgBL bpartnerOrgBL = Services.get(IBPartnerOrgBL.class);
	@NonNull private final IBPartnerBL bpartnerBL = Services.get(IBPartnerBL.class);
	@NonNull private final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);
	@NonNull private final ILocationDAO locationDAO = Services.get(ILocationDAO.class);
	@NonNull private final IProductBL productBL = Services.get(IProductBL.class);
	@NonNull private final IOrderDAO orderDAO = Services.get(IOrderDAO.class);
	@NonNull private final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);


	private static final Logger logger = LoggerFactory.getLogger(NShiftDraftDeliveryOrderCreator.class);

	private static final BigDecimal DEFAULT_PackageWeightInKg = BigDecimal.ONE;

	@NonNull
	@Override
	public ShipperGatewayId getShipperGatewayId() {return NShiftConstants.SHIPPER_GATEWAY_ID;}

	@NonNull
	@Override
	public DeliveryOrder createDraftDeliveryOrder(@NonNull final CreateDraftDeliveryOrderRequest request)
	{
		final DeliveryOrderKey deliveryOrderKey = request.getDeliveryOrderKey();

		final I_C_BPartner pickupFromBPartner = bpartnerOrgBL.retrieveLinkedBPartner(deliveryOrderKey.getFromOrgId());
		final I_C_BPartner_Location pickupFromBPLocation = bpartnerOrgBL.retrieveOrgBPLocation(OrgId.ofRepoId(deliveryOrderKey.getFromOrgId()));
		final I_C_Location pickupFromLocation = locationDAO.getById(LocationId.ofRepoId(pickupFromBPLocation.getC_Location_ID()));
		final User pickupFromContact = bpartnerBL.retrieveContactOrNull(IBPartnerBL.RetrieveContactRequest.builder()
						.contactType(IBPartnerBL.RetrieveContactRequest.ContactType.SHIP_TO_DEFAULT)
						.onlyActive(true)
						.bpartnerId(BPartnerId.ofRepoId(pickupFromBPartner.getC_BPartner_ID()))
						.ifNotFound(IBPartnerBL.RetrieveContactRequest.IfNotFound.RETURN_DEFAULT_CONTACT)
				.build());
		final LocalDate pickupDate = deliveryOrderKey.getPickupDate();

		final BPartnerLocationId deliverToBPartnerLocationId = BPartnerLocationId.ofRepoId(deliveryOrderKey.getDeliverToBPartnerId(), deliveryOrderKey.getDeliverToBPartnerLocationId());
		final I_C_BPartner deliverToBPartner = bpartnerBL.getById(deliverToBPartnerLocationId.getBpartnerId());
		final I_C_BPartner_Location deliverToBPLocation = Check.assumeNotNull(bpartnerDAO.getBPartnerLocationByIdInTrx(deliverToBPartnerLocationId), "bp location not null");
		final I_C_Location deliverToLocation = locationDAO.getById(LocationId.ofRepoId(deliverToBPLocation.getC_Location_ID()));
		final User deliverToContact = deliveryOrderKey.getDeliverContactId() != null ? userRepository.getByIdInTrx(deliveryOrderKey.getDeliverContactId()) : null;

		final ShipperId shipperId = deliveryOrderKey.getShipperId();

		return DeliveryOrder.builder()
				.shipperId(shipperId)
				.shipperTransportationId(deliveryOrderKey.getShipperTransportationId())
				//

				.customerReference(getPOReferences(request.getPackageInfos()))
				.shipperEORI(pickupFromBPartner.getEORI())
				.receiverEORI(deliverToBPartner.getEORI())
				//
				// Pickup aka Shipper
				.pickupAddress(toPickFromAddress(pickupFromBPartner, pickupFromLocation, pickupFromBPLocation))
				.pickupContact(toContact(pickupFromBPartner, pickupFromBPLocation, pickupFromContact))
				.pickupDate(PickupDate.builder()
						.date(pickupDate)
						.timeFrom(deliveryOrderKey.getTimeFrom())
						.timeTo(deliveryOrderKey.getTimeTo())
						.build())
				//
				// Delivery aka Receiver
				.deliveryAddress(toDeliverToAddress(deliverToBPartner, deliverToLocation, deliverToBPLocation))
				.deliveryContact(toContact(deliverToBPartner, deliverToBPLocation, deliverToContact))
				//
				// Delivery content
				.deliveryOrderParcels(toDeliveryOrderLines(request.getPackageInfos()))
				.goodsType(carrierGoodsTypeRepository.getCachedGoodsTypeById(deliveryOrderKey.getCarrierGoodsTypeId()))
				.shipperProduct(carrierProductRepository.getCachedShipperProductById(deliveryOrderKey.getCarrierProductId()))
				.services(deliveryOrderKey.getCarrierServices() != null ? deliveryOrderKey.getCarrierServices().stream().map(carrierServiceRepository::getCachedCarrierServiceById).collect(ImmutableSet.toImmutableSet()) : ImmutableSet.of())
				//
				.build();

	}

	@NonNull
	private static Address toPickFromAddress(@NonNull final I_C_BPartner pickupFromBPartner, @NonNull final I_C_Location pickupFromLocation, @NonNull final I_C_BPartner_Location pickupFromBPLocation)
	{
		return DeliveryOrderUtil.prepareAddressFromLocationBP(pickupFromLocation, pickupFromBPartner, pickupFromBPLocation)
				.build();
	}

	@NonNull
	private static Address toDeliverToAddress(@NonNull final I_C_BPartner deliverToBPartner, @NonNull final I_C_Location deliverToLocation, @NonNull final I_C_BPartner_Location deliverToBPLocation)
	{
		return DeliveryOrderUtil.prepareAddressFromLocationBP(deliverToLocation, deliverToBPartner, deliverToBPLocation)
				.bpartnerId(deliverToBPartner.getC_BPartner_ID()) // used for label archive
				.build();
	}

	@NonNull
	private static ContactPerson toContact(@NonNull final I_C_BPartner bPartner,
										   @NonNull final I_C_BPartner_Location bPLocation,
										   @Nullable final User contact)
	{
		return DeliveryOrderUtil.getContactPerson(bPartner, bPLocation, contact);
	}

	@NonNull
	private ImmutableList<DeliveryOrderParcel> toDeliveryOrderLines(@NonNull final Set<CreateDraftDeliveryOrderRequest.PackageInfo> packageInfos)
	{
		final Map<PackageId, Map<InOutAndLineId, String>> countryByPackage = fetchCountryOfOriginByInOutLine(packageInfos);

		return packageInfos.stream()
				.flatMap(packageInfo -> toParcelsGroupedByCountryOfOrigin(packageInfo, countryByPackage.getOrDefault(packageInfo.getPackageId(), ImmutableMap.of())))
				.collect(ImmutableList.toImmutableList());
	}

	@NonNull
	private Map<PackageId, Map<InOutAndLineId, String>> fetchCountryOfOriginByInOutLine(
			@NonNull final Set<CreateDraftDeliveryOrderRequest.PackageInfo> packageInfos)
	{
		final ImmutableSet<PackageId> packageIds = packageInfos.stream()
				.map(CreateDraftDeliveryOrderRequest.PackageInfo::getPackageId)
				.collect(ImmutableSet.toImmutableSet());
		final Map<PackageId, InOutId> inOutIdByPackageId = new HashMap<>();
		for (final PackageId pkgId : packageIds)
		{
			final InOutId inOutId = purchaseOrderToShipperTransportationRepository.getPackageById(pkgId).getInOutId();
			if (inOutId != null)
			{
				inOutIdByPackageId.put(pkgId, inOutId);
			}
		}
		return huPackageService.fetchCountryOfOriginByInOutLine(inOutIdByPackageId);
	}

	/**
	 * Converts one {@code PackageInfo} into one or more {@link DeliveryOrderParcel}s.
	 * Items with different countries of origin are split into separate parcels so each parcel
	 * carries a uniform country — required for customs declarations in nShift.
	 * Items without a country are grouped together in one parcel.
	 */
	@NonNull
	private Stream<DeliveryOrderParcel> toParcelsGroupedByCountryOfOrigin(
			@NonNull final CreateDraftDeliveryOrderRequest.PackageInfo packageInfo,
			@NonNull final Map<InOutAndLineId, String> countryByInOutLine)
	{
		// Filter to InOutLines assigned to this package only.
		// getPackageContents() returns ALL InOutLines of the M_InOut — without this filter
		// each of N packages sharing one M_InOut sees all N lines, causing N² parcels.
		final ImmutableList<DeliveryOrderItem> allItems = purchaseOrderToShipperTransportationRepository
				.getPackageById(packageInfo.getPackageId())
				.getPackageContents()
				.stream()
				.filter(item -> countryByInOutLine.isEmpty()
						|| (item.getInOutAndLineId() != null && countryByInOutLine.containsKey(item.getInOutAndLineId())))
				.map(packageItem -> createDeliveryOrderItem(packageItem, countryByInOutLine))
				.collect(ImmutableList.toImmutableList());

		if (allItems.isEmpty() && !countryByInOutLine.isEmpty())
		{
			logger.warn("Package {} has no items after InOutLine assignment filter — no parcel created. Check M_HU_Assignment.",
					packageInfo.getPackageId());
			return Stream.empty();
		}

		// Group items by parcel key — items with the same key go into the same parcel
		final LinkedHashMap<DeliveryOrderItemGroupKey, List<DeliveryOrderItem>> byCountry = allItems.stream()
				.collect(Collectors.groupingBy(
						DeliveryOrderItemGroupKey::of,
						LinkedHashMap::new,
						Collectors.toList()));

		if (byCountry.size() <= 1)
		{
			// All items share the same country (or none) — single parcel, existing weight applies
			return Stream.of(DeliveryOrderParcel.builder()
					.packageDimensions(packageInfo.getPackageDimension())
					.packageId(packageInfo.getPackageId())
					.grossWeightKg(packageInfo.getWeightInKgOr(DEFAULT_PackageWeightInKg))
					.content(packageInfo.getDescription())
					.items(allItems)
					.build());
		}

		// Multiple countries — one parcel per country group; gross weight derived from item weights
		return byCountry.values().stream()
				.map(items -> {
					final BigDecimal groupWeightKg = items.stream()
							.map(DeliveryOrderItem::getTotalWeightInKg)
							.reduce(BigDecimal.ZERO, BigDecimal::add);
					return DeliveryOrderParcel.builder()
							.packageDimensions(packageInfo.getPackageDimension())
							.packageId(packageInfo.getPackageId())
							.grossWeightKg(groupWeightKg.compareTo(BigDecimal.ZERO) > 0 ? groupWeightKg : packageInfo.getWeightInKgOr(DEFAULT_PackageWeightInKg))
							.content(packageInfo.getDescription())
							.items(ImmutableList.copyOf(items))
							.build();
				});
	}

	@NonNull
	private DeliveryOrderItem createDeliveryOrderItem(
			@NonNull final PackageItem packageItem,
			@NonNull final Map<InOutAndLineId, String> countryByInOutLine)
	{
		Check.assumeNotNull(packageItem.getQuantity(), "quantity must not be null, for packageItem " + packageItem);
		final ProductId productId = packageItem.getProductId();
		final Product product = productRepository.getById(productId);
		final BigDecimal weightInKg = computeNominalGrossWeightInKg(packageItem).orElse(BigDecimal.ZERO);
		final I_C_OrderLine orderLine = orderDAO.getOrderLineById(packageItem.getOrderLineId());

		final UomId targetUOMID = CoalesceUtil.coalesceNotNull(UomId.ofRepoIdOrNull(orderLine.getPrice_UOM_ID()), packageItem.getQuantity().getUomId());

		final Quantity quantity = uomConversionBL.convertQuantityTo(packageItem.getQuantity(), productId, targetUOMID);
		final Money unitPrice = Money.of(orderLine.getPriceEntered(), CurrencyId.ofRepoId(orderLine.getC_Currency_ID()));
		final Money totalPackageValue = unitPrice.multiply(quantity.toBigDecimal());

		final CustomsTariffId customsTariffId = product.getCustomsTariffId();
		final String customsTariff = customsTariffId != null ? customsTariffRepository.getById(customsTariffId).getValue() : null;

		final String countryOfOrigin = packageItem.getInOutAndLineId() != null
				? countryByInOutLine.get(packageItem.getInOutAndLineId())
				: null;

		return DeliveryOrderItem.builder()
				.productName(product.getName().getDefaultValue())
				.productValue(product.getValue())
				.customsTariff(customsTariff)
				.countryOfOrigin(countryOfOrigin)
				.totalWeightInKg(weightInKg)
				.shippedQuantity(packageItem.getQuantity())
				.unitPrice(unitPrice)
				.totalValue(totalPackageValue)
				.build();
	}

	@NonNull
	private Optional<BigDecimal> computeNominalGrossWeightInKg(@NonNull final PackageItem packageItem)
	{
		final ProductId productId = packageItem.getProductId();
		final Quantity quantity = packageItem.getQuantity();
		return productBL.computeGrossWeight(productId, quantity)
				.map(weight -> uomConversionBL.convertToKilogram(weight, productId))
				.map(Quantity::getAsBigDecimal);
	}

}
