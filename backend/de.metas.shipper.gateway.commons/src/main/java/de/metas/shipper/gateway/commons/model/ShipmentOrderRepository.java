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

package de.metas.shipper.gateway.commons.model;

import com.google.common.base.Functions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import de.metas.location.CountryCode;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.quantity.Quantitys;
import de.metas.shipper.gateway.spi.DeliveryOrderId;
import de.metas.shipper.gateway.spi.model.Address;
import de.metas.shipper.gateway.spi.model.ContactPerson;
import de.metas.shipper.gateway.spi.model.DeliveryOrder;
import de.metas.shipper.gateway.spi.model.DeliveryOrderLine;
import de.metas.shipper.gateway.spi.model.DeliveryOrderLineId;
import de.metas.shipper.gateway.spi.model.PackageDimensions;
import de.metas.shipper.gateway.spi.model.PickupDate;
import de.metas.shipping.ShipperId;
import de.metas.shipping.model.ShipperTransportationId;
import de.metas.shipping.mpackage.PackageId;
import de.metas.uom.UomId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_Carrier_ShipmentOrder;
import org.compiere.model.I_Carrier_ShipmentOrder_Item;
import org.compiere.model.I_Carrier_ShipmentOrder_Parcel;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Repository;

import java.util.Objects;
import java.util.stream.Stream;

import static org.adempiere.model.InterfaceWrapperHelper.saveAll;

@Repository
public class ShipmentOrderRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public DeliveryOrder getById(@NonNull final DeliveryOrderId deliveryOrderId)
	{
		final I_Carrier_ShipmentOrder shipmentOrder = InterfaceWrapperHelper.load(deliveryOrderId, I_Carrier_ShipmentOrder.class);

		final ImmutableListMultimap<DeliveryOrderLineId, ShipmentOrderItem> parcelIdToItems = queryBL.createQueryBuilder(I_Carrier_ShipmentOrder_Parcel.class)
				.addEqualsFilter(I_Carrier_ShipmentOrder_Parcel.COLUMNNAME_Carrier_ShipmentOrder_ID, deliveryOrderId)
				.andCollectChildren(I_Carrier_ShipmentOrder_Item.COLUMNNAME_Carrier_ShipmentOrder_Parcel_ID, I_Carrier_ShipmentOrder_Item.class)
				.create()
				.stream()
				// FIXME: not sure we have a link from parcel to item
				.collect(ImmutableListMultimap.toImmutableListMultimap(item -> DeliveryOrderLineId.ofRepoId(item.getCarrier_ShipmentOrder_Parcel_ID()), this::itemFromPO));

		final ImmutableList<DeliveryOrderLine> parcels = queryBL.createQueryBuilder(I_Carrier_ShipmentOrder_Parcel.class)
				.addEqualsFilter(I_Carrier_ShipmentOrder_Parcel.COLUMNNAME_Carrier_ShipmentOrder_ID, deliveryOrderId)
				.create()
				.stream()
				.map(parcel -> parcelFromPO(parcel, parcelIdToItems.get(DeliveryOrderLineId.ofRepoId(parcel.getCarrier_ShipmentOrder_Parcel_ID()))))
				.collect(ImmutableList.toImmutableList());

		return fromPO(shipmentOrder, parcels);
	}

	private ShipmentOrderItem itemFromPO(@NonNull final I_Carrier_ShipmentOrder_Item item)
	{
		final CurrencyId currencyId = CurrencyId.ofRepoId(item.getC_Currency_ID());
		return ShipmentOrderItem.builder()
				.id(ShipmentOrderItemId.ofRepoId(item.getCarrier_ShipmentOrder_Item_ID()))
				.productName(item.getProductName())
				.productValue(item.getArticleValue())
				.totalWeightInKg(item.getTotalWeightInKg())
				.unitPrice(Money.of(item.getPrice(), currencyId))
				.totalValue(Money.of(item.getTotalPrice(), currencyId))
				.shippedQuantity(Quantitys.of(item.getQtyShipped(), UomId.ofRepoId(item.getC_UOM_ID())))
				.build();
	}

	private DeliveryOrder fromPO(@NonNull final I_Carrier_ShipmentOrder po, @NonNull final ImmutableList<DeliveryOrderLine> parcels)
	{
		return DeliveryOrder.builder()
				.shipperProduct(CarrierShipperProduct.ofCode(po.getCarrier_Product()))
				.customerReference(po.getCustomerReference())
				.shipperId(ShipperId.ofRepoId(po.getM_Shipper_ID()))
				.shipperTransportationId(ShipperTransportationId.ofRepoIdOrNull(po.getM_ShipperTransportation_ID()))
				.pickupDate(PickupDate.builder()
						.date(TimeUtil.asLocalDate(po.getShipmentDate()))
						.build())
				.deliveryContact(ContactPerson.builder()
						.emailAddress(po.getReceiver_Email())
						.build())
				.pickupAddress(Address.builder()
						.companyName1(po.getShipper_Name1())
						.companyName2(po.getShipper_Name2())
						.city(po.getShipper_City())
						.zipCode(po.getShipper_ZipCode())
						.street1(po.getShipper_StreetName1())
						.street2(po.getShipper_StreetName2())
						.houseNo(po.getShipper_StreetNumber())
						.country(CountryCode.builder()
								.alpha2(po.getShipper_CountryISO2Code())
								.alpha3(po.getShipper_CountryISO3Code()) // FIXME don't need both versions
								.build())
						.bpartnerId(po.getC_BPartner_ID())
						.build())
				.deliveryAddress(Address.builder()
						.companyName1(po.getReceiver_Name1())
						.companyName2(po.getReceiver_Name2())
						.city(po.getReceiver_City())
						.zipCode(po.getReceiver_ZipCode())
						.street1(po.getReceiver_StreetName1())
						.street2(po.getReceiver_StreetName2())
						.houseNo(po.getReceiver_StreetNumber())
						.country(CountryCode.builder()
								.alpha2(po.getReceiver_CountryISO2Code())
								.alpha3(po.getReceiver_CountryISO3Code())// FIXME don't need both versions
								.build())
						.build())
				.customDeliveryData(ShipmentOrderData.builder()
						.shipperEORI(po.getShipper_EORI())
						.receiverEORI(po.getReceiver_EORI())
						.build())
				.deliveryOrderLines(parcels)
				.build();
	}

	private DeliveryOrderLine parcelFromPO(@NonNull final I_Carrier_ShipmentOrder_Parcel po, final ImmutableList<ShipmentOrderItem> shipmentOrderItems)
	{
		return DeliveryOrderLine.builder()
				.id(DeliveryOrderLineId.ofRepoId(po.getCarrier_ShipmentOrder_Parcel_ID()))
				.customDeliveryLineData(ShipmentOrderParcel.builder()
						.awb(po.getawb())
						.trackingUrl(po.getTrackingURL())
						.labelPdfBase64(po.getPdfLabelData())
						.items(shipmentOrderItems)
						.build())
				.packageDimensions(PackageDimensions.builder()
						.heightInCM(po.getHeightInCm())
						.lengthInCM(po.getLengthInCm())
						.widthInCM(po.getWidthInCm())
						.build())
				.grossWeightKg(po.getWeightInKg())
				.packageId(PackageId.ofRepoId(po.getM_Package_ID()))
				.build();
	}

	public DeliveryOrder save(@NonNull final DeliveryOrder order)
	{
		if (order.getId() != null)
		{
			// FIXME: we need a full save, not only parcels
			updateShipmentOrderParcels(order);
			return order;
		}
		else
		{
			final DeliveryOrderId orderId = createShipmentOrder(order);
			return order
					.toBuilder()
					.id(orderId)
					.build();
		}
	}

	private void updateShipmentOrderParcels(final @NonNull DeliveryOrder order)
	{
		final ImmutableMap<DeliveryOrderLineId, I_Carrier_ShipmentOrder_Parcel> parcelsById = queryBL.createQueryBuilder(I_Carrier_ShipmentOrder_Parcel.class)
				.addEqualsFilter(I_Carrier_ShipmentOrder_Parcel.COLUMNNAME_Carrier_ShipmentOrder_ID, order.getId())
				.create()
				.map(I_Carrier_ShipmentOrder_Parcel.class, parcel -> DeliveryOrderLineId.ofRepoId(parcel.getCarrier_ShipmentOrder_Parcel_ID()));
		final ImmutableList<I_Carrier_ShipmentOrder_Parcel> updatedParcels = order.getDeliveryOrderLines()
				.stream()
				.map(deliveryOrderLine -> getAndUpdateParcel(deliveryOrderLine, parcelsById))
				.collect(ImmutableList.toImmutableList());

		// FIXME parcels which no longer exists in the order shall be deleted from database
		saveAll(updatedParcels);
	}

	private static I_Carrier_ShipmentOrder_Parcel getAndUpdateParcel(final DeliveryOrderLine deliveryOrderLine, final ImmutableMap<DeliveryOrderLineId, I_Carrier_ShipmentOrder_Parcel> parcelsById)
	{
		// FIXME parcels that are not present in database shall be created (i.e. when deliveryOrderLine.getId() == null)
		final I_Carrier_ShipmentOrder_Parcel parcel = parcelsById.get(deliveryOrderLine.getId());
		if (parcel == null)
		{
			throw new AdempiereException("Could not find shipment order line with id " + deliveryOrderLine.getId());
		}
		final ShipmentOrderParcel shipmentOrderParcel = ShipmentOrderParcel.ofDeliveryOrderLineOrNull(deliveryOrderLine);
		if (shipmentOrderParcel == null)
		{
			throw new AdempiereException("Shipment order line " + deliveryOrderLine.getId() + " has no custom delivery data");
		}
		parcel.setawb(shipmentOrderParcel.getAwb());
		parcel.setTrackingURL(shipmentOrderParcel.getTrackingUrl());
		parcel.setPdfLabelData(shipmentOrderParcel.getLabelPdfBase64());
		return parcel;
	}

	private DeliveryOrderId createShipmentOrder(final @NonNull DeliveryOrder request)
	{
		final I_Carrier_ShipmentOrder po = InterfaceWrapperHelper.newInstance(I_Carrier_ShipmentOrder.class);
		final ContactPerson deliveryContact = request.getDeliveryContact();
		po.setCarrier_Product(request.getShipperProduct().getCode());
		po.setC_BPartner_ID(request.getDeliveryAddress().getBpartnerId());
		po.setCustomerReference(request.getCustomerReference());
		po.setInternationalDelivery(!Objects.equals(request.getDeliveryAddress().getCountry(), request.getPickupAddress().getCountry()));
		po.setM_Shipper_ID(ShipperId.toRepoId(request.getShipperId()));
		po.setM_ShipperTransportation_ID(ShipperTransportationId.toRepoId(request.getShipperTransportationId()));
		po.setShipmentDate(TimeUtil.asTimestamp(request.getPickupDate().getDate()));
		// FIXME what about pickup time from/to

		if (deliveryContact != null)
		{
			po.setReceiver_Email(deliveryContact.getEmailAddress());
		}

		final Address shipperAddress = request.getPickupAddress();
		po.setShipper_Name1(shipperAddress.getCompanyName1());
		po.setShipper_Name2(shipperAddress.getCompanyName2());
		po.setShipper_StreetName1(shipperAddress.getStreet1());
		po.setShipper_StreetName2(shipperAddress.getStreet2());
		po.setShipper_StreetNumber(shipperAddress.getHouseNo());
		po.setShipper_ZipCode(shipperAddress.getZipCode());
		po.setShipper_City(shipperAddress.getCity());
		final CountryCode shipperCountry = shipperAddress.getCountry();
		po.setShipper_CountryISO2Code(shipperCountry.getAlpha2());
		po.setShipper_CountryISO3Code(shipperCountry.getAlpha3());

		final Address receiverAddress = request.getDeliveryAddress();
		po.setReceiver_Name1(receiverAddress.getCompanyName1());
		po.setReceiver_Name2(receiverAddress.getCompanyName2());
		po.setReceiver_StreetName1(receiverAddress.getStreet1());
		po.setReceiver_StreetName2(receiverAddress.getStreet2());
		po.setReceiver_StreetNumber(receiverAddress.getHouseNo());
		po.setReceiver_ZipCode(receiverAddress.getZipCode());
		po.setReceiver_City(receiverAddress.getCity());
		final CountryCode receiverCountry = receiverAddress.getCountry();
		po.setReceiver_CountryISO2Code(receiverCountry.getAlpha2());
		po.setReceiver_CountryISO3Code(receiverCountry.getAlpha3());

		final ShipmentOrderData shipmentOrderData = ShipmentOrderData.ofDeliveryOrderOrNull(request);
		if (shipmentOrderData != null)
		{
			po.setShipper_EORI(shipmentOrderData.getShipperEORI());
			po.setReceiver_EORI(shipmentOrderData.getReceiverEORI());
		}

		InterfaceWrapperHelper.saveRecord(po);

		final DeliveryOrderId deliveryOrderId = DeliveryOrderId.ofRepoId(po.getCarrier_ShipmentOrder_ID());

		final ImmutableMap<DeliveryOrderLine, I_Carrier_ShipmentOrder_Parcel> orderLineToParcel = request.getDeliveryOrderLines()
				.stream()
				.collect(ImmutableMap.toImmutableMap(Functions.identity(), item -> createParcel(item, deliveryOrderId)));

		saveAll(orderLineToParcel.values());

		saveAll(orderLineToParcel.keySet()
				.stream()
				.flatMap(orderLine -> createItems(orderLine, DeliveryOrderLineId.ofRepoId(orderLineToParcel.get(orderLine).getCarrier_ShipmentOrder_Parcel_ID())))
				.collect(ImmutableList.toImmutableList()));

		return deliveryOrderId;
	}

	private Stream<I_Carrier_ShipmentOrder_Item> createItems(@NonNull final DeliveryOrderLine orderLine, @NonNull final DeliveryOrderLineId deliveryOrderLineId)
	{
		final ShipmentOrderParcel shipmentOrderParcel = ShipmentOrderParcel.ofDeliveryOrderLineOrNull(orderLine);
		if (shipmentOrderParcel == null)
		{
			return Stream.empty();
		}
		return shipmentOrderParcel.getItems()
				.stream()
				.map(item -> createItem(item, deliveryOrderLineId));
	}

	private I_Carrier_ShipmentOrder_Item createItem(final ShipmentOrderItem item, final @NonNull DeliveryOrderLineId deliveryOrderLineId)
	{
		final I_Carrier_ShipmentOrder_Item po;
		if (item.getId() != null)
		{
			po = InterfaceWrapperHelper.load(item.getId(), I_Carrier_ShipmentOrder_Item.class);
			Check.assumeEquals(po.getCarrier_ShipmentOrder_Parcel_ID(), item.getId().getRepoId());
		}
		else
		{
			po = InterfaceWrapperHelper.newInstance(I_Carrier_ShipmentOrder_Item.class);
			po.setCarrier_ShipmentOrder_Parcel_ID(deliveryOrderLineId.getRepoId());
		}
		po.setProductName(item.getProductName());
		po.setArticleValue(item.getProductValue());
		Check.assumeEquals(item.getTotalValue().getCurrencyId(), item.getUnitPrice().getCurrencyId());
		po.setPrice(item.getUnitPrice().toBigDecimal());
		po.setC_Currency_ID(item.getUnitPrice().getCurrencyId().getRepoId());
		po.setTotalPrice(item.getTotalValue().toBigDecimal());
		po.setTotalWeightInKg(item.getTotalWeightInKg());
		po.setC_UOM_ID(item.getShippedQuantity().getUomId().getRepoId());
		po.setQtyShipped(item.getShippedQuantity().toBigDecimal());

		return po;
	}

	private I_Carrier_ShipmentOrder_Parcel createParcel(final @NonNull DeliveryOrderLine parcelRequest, @NonNull final DeliveryOrderId deliveryOrderId)
	{
		final I_Carrier_ShipmentOrder_Parcel po;
		if (parcelRequest.getId() != null)
		{
			po = InterfaceWrapperHelper.load(parcelRequest.getId(), I_Carrier_ShipmentOrder_Parcel.class);
			Check.assumeEquals(po.getCarrier_ShipmentOrder_ID(), parcelRequest.getId().getRepoId());
		}
		else
		{
			po = InterfaceWrapperHelper.newInstance(I_Carrier_ShipmentOrder_Parcel.class);
			po.setCarrier_ShipmentOrder_ID(deliveryOrderId.getRepoId());
		}
		final PackageDimensions packageDimensions = parcelRequest.getPackageDimensions();

		po.setHeightInCm(packageDimensions.getHeightInCM());
		po.setLengthInCm(packageDimensions.getLengthInCM());
		po.setWidthInCm(packageDimensions.getWidthInCM());
		po.setWeightInKg(parcelRequest.getGrossWeightKg());
		po.setM_Package_ID(PackageId.toRepoId(parcelRequest.getPackageId()));

		return po;
	}

}
