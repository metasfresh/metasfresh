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
import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.bpartner.service.IBPartnerOrgBL;
import de.metas.common.util.CoalesceUtil;
import de.metas.customstariff.CustomsTariffId;
import de.metas.customstariff.CustomsTariffRepository;
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
import de.metas.shipper.gateway.spi.model.PickupDate;
import de.metas.shipping.PurchaseOrderToShipperTransportationRepository;
import de.metas.shipping.ShipperGatewayId;
import de.metas.shipping.ShipperId;
import de.metas.shipping.mpackage.PackageItem;
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.UomId;
import de.metas.user.User;
import de.metas.user.UserRepository;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Location;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

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

	@NonNull private final IBPartnerOrgBL bpartnerOrgBL = Services.get(IBPartnerOrgBL.class);
	@NonNull private final IBPartnerBL bpartnerBL = Services.get(IBPartnerBL.class);
	@NonNull private final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);
	@NonNull private final ILocationDAO locationDAO = Services.get(ILocationDAO.class);
	@NonNull private final IProductBL productBL = Services.get(IProductBL.class);
	@NonNull private final IOrderDAO orderDAO = Services.get(IOrderDAO.class);
	@NonNull private final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);


	private static final BigDecimal DEFAULT_PackageWeightInKg = BigDecimal.ONE;

	@Override
	public ShipperGatewayId getShipperGatewayId() {return NShiftConstants.SHIPPER_GATEWAY_ID;}

	@NonNull
	@Override
	public @NotNull DeliveryOrder createDraftDeliveryOrder(@NonNull final CreateDraftDeliveryOrderRequest request)
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
				.pickupAddress(toPickFromAddress(pickupFromBPartner, pickupFromLocation))
				.pickupContact(toContact(pickupFromBPartner, pickupFromBPLocation, pickupFromContact))
				.pickupDate(PickupDate.builder()
						.date(pickupDate)
						.timeFrom(deliveryOrderKey.getTimeFrom())
						.timeTo(deliveryOrderKey.getTimeTo())
						.build())
				//
				// Delivery aka Receiver
				.deliveryAddress(toDeliverToAddress(deliverToBPartner, deliverToLocation))
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

	private static Address toPickFromAddress(final I_C_BPartner pickupFromBPartner, final I_C_Location pickupFromLocation)
	{
		return DeliveryOrderUtil.prepareAddressFromLocationBP(pickupFromLocation , pickupFromBPartner)
				.build();
	}

	private static Address toDeliverToAddress(final I_C_BPartner deliverToBPartner, final I_C_Location deliverToLocation)
	{
		return DeliveryOrderUtil.prepareAddressFromLocationBP(deliverToLocation , deliverToBPartner)
				.bpartnerId(deliverToBPartner.getC_BPartner_ID()) // used for label archive
				.build();
	}

	private static ContactPerson toContact(@NonNull final I_C_BPartner bPartner,
										   @NonNull final I_C_BPartner_Location bPLocation,
										   @Nullable final User contact)
	{
		return DeliveryOrderUtil.getContactPerson(bPartner, bPLocation, contact);
	}

	private ImmutableList<DeliveryOrderParcel> toDeliveryOrderLines(@NotNull final Set<CreateDraftDeliveryOrderRequest.PackageInfo> packageInfos)
	{
		return packageInfos.stream()
				.map(packageInfo -> {
					final ImmutableList<DeliveryOrderItem> deliveryOrderItems = purchaseOrderToShipperTransportationRepository.getPackageById(packageInfo.getPackageId()).getPackageContents()
							.stream()
							.map(this::createDeliveryOrderItems)
							.collect(ImmutableList.toImmutableList());
					return DeliveryOrderParcel.builder()
							.packageDimensions(packageInfo.getPackageDimension())
							.packageId(packageInfo.getPackageId())
							.grossWeightKg(packageInfo.getWeightInKgOr(DEFAULT_PackageWeightInKg))
							.content(packageInfo.getDescription())
							.items(deliveryOrderItems)
							.build();
				})
				.collect(ImmutableList.toImmutableList());
	}

	private DeliveryOrderItem createDeliveryOrderItems(@NonNull final PackageItem packageItem)
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

		return DeliveryOrderItem.builder()
				.productName(product.getName().getDefaultValue())
				.productValue(product.getValue())
				.customsTariff(customsTariff)
				.totalWeightInKg(weightInKg)
				.shippedQuantity(packageItem.getQuantity())
				.unitPrice(unitPrice)
				.totalValue(totalPackageValue)
				.build();
	}

	private Optional<BigDecimal> computeNominalGrossWeightInKg(final PackageItem packageItem)
	{
		final ProductId productId = packageItem.getProductId();
		final Quantity quantity = packageItem.getQuantity();
		return productBL.computeGrossWeight(productId, quantity)
				.map(weight -> uomConversionBL.convertToKilogram(weight, productId))
				.map(Quantity::getAsBigDecimal);
	}

}
