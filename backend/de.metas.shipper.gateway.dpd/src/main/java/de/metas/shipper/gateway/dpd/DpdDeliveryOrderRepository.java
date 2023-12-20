/*
 * #%L
 * de.metas.shipper.gateway.dpd
 * %%
 * Copyright (C) 2019 metas GmbH
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

package de.metas.shipper.gateway.dpd;

import com.google.common.collect.ImmutableList;
import de.metas.location.ICountryCodeFactory;
import de.metas.mpackage.PackageId;
import de.metas.shipper.gateway.dpd.model.DpdNotificationChannel;
import de.metas.shipper.gateway.dpd.model.DpdOrderCustomDeliveryData;
import de.metas.shipper.gateway.dpd.model.DpdPaperFormat;
import de.metas.shipper.gateway.dpd.model.DpdShipperProduct;
import de.metas.shipper.gateway.dpd.model.I_DPD_StoreOrder;
import de.metas.shipper.gateway.dpd.model.I_DPD_StoreOrderLine;
import de.metas.shipper.gateway.dpd.util.DpdConversionUtil;
import de.metas.shipper.gateway.spi.DeliveryOrderId;
import de.metas.shipper.gateway.spi.model.Address;
import de.metas.shipper.gateway.spi.model.ContactPerson;
import de.metas.shipper.gateway.spi.model.DeliveryOrder;
import de.metas.shipper.gateway.spi.model.DeliveryOrderLine;
import de.metas.shipper.gateway.spi.model.PackageDimensions;
import de.metas.shipper.gateway.spi.model.PickupDate;
import de.metas.shipping.ShipperId;
import de.metas.shipping.model.ShipperTransportationId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public class DpdDeliveryOrderRepository
{
	private final ICountryCodeFactory countryCodeFactory = Services.get(ICountryCodeFactory.class);

	public DeliveryOrder getByRepoId(final DeliveryOrderId deliveryOrderRepoId)
	{

		final I_DPD_StoreOrder dpdStoreOrder = InterfaceWrapperHelper.load(deliveryOrderRepoId, I_DPD_StoreOrder.class);
		Check.assumeNotNull(dpdStoreOrder, "DPD delivery order must exist for ID={}", deliveryOrderRepoId);

		return toDeliveryOrderFromPO(dpdStoreOrder);
	}

	/**
	 * Explanation of the different data structures:
	 * <p>
	 * - DeliveryOrder is the DTO
	 * - I_Dpd_StoreOrder is the persisted object for that DTO (which has lines), with data relevant for DPD.
	 * Each different shipper has its own "shipper-po" with its own relevant data.
	 */
	@NonNull
	public DeliveryOrder save(@NonNull final DeliveryOrder deliveryOrder)
	{
		final I_DPD_StoreOrder storeOrderPO = createStoreOrderPO(deliveryOrder);
		return updateDeliveryOrderRepoId(deliveryOrder, storeOrderPO);
	}

	@NonNull
	private DeliveryOrder updateDeliveryOrderRepoId(@NonNull final DeliveryOrder deliveryOrder, @NonNull final I_DPD_StoreOrder storeOrderPO)
	{
		return deliveryOrder.toBuilder()
				.id(DeliveryOrderId.ofRepoId(storeOrderPO.getDPD_StoreOrder_ID()))
				.build();
	}

	/**
	 * Persists the shipper-dependant DeliveryOrder details.
	 * <p>
	 * Keep in sync with {@link #toDeliveryOrderFromPO(I_DPD_StoreOrder)}
	 */
	@NonNull
	private I_DPD_StoreOrder createStoreOrderPO(@NonNull final DeliveryOrder deliveryOrder)
	{
		final I_DPD_StoreOrder orderPO = InterfaceWrapperHelper.loadOrNew(deliveryOrder.getId(), I_DPD_StoreOrder.class);
		InterfaceWrapperHelper.save(orderPO);

		{
			// Misc
			orderPO.setM_Shipper_ID(deliveryOrder.getShipperId().getRepoId());
			orderPO.setM_ShipperTransportation_ID(deliveryOrder.getShipperTransportationId().getRepoId());
		}
		{
			// Pickup aka Sender
			final Address pickupAddress = deliveryOrder.getPickupAddress();
			orderPO.setSenderName1(pickupAddress.getCompanyName1());
			orderPO.setSenderName2(pickupAddress.getCompanyName2());
			orderPO.setSenderStreet(pickupAddress.getStreet1());
			orderPO.setSenderHouseNo(pickupAddress.getHouseNo());
			orderPO.setSenderZipCode(pickupAddress.getZipCode());
			orderPO.setSenderCity(pickupAddress.getCity());
			orderPO.setSenderCountry(pickupAddress.getCountry().getAlpha2());
		}
		{
			// Pickup Date and Time
			final PickupDate pickupDate = deliveryOrder.getPickupDate();
			orderPO.setPickupDate(TimeUtil.asTimestamp(pickupDate.getDate()));
			orderPO.setPickupDay(DpdConversionUtil.getPickupDayOfTheWeek(pickupDate));
			orderPO.setPickupTimeFrom(TimeUtil.asTimestamp(pickupDate.getTimeFrom()));
			orderPO.setPickupTimeTo(TimeUtil.asTimestamp(pickupDate.getTimeTo()));
		}
		{
			// Delivery aka Recipient
			final Address deliveryAddress = deliveryOrder.getDeliveryAddress();
			orderPO.setRecipientName1(deliveryAddress.getCompanyName1());
			orderPO.setRecipientName2(deliveryAddress.getCompanyName2());
			orderPO.setRecipientStreet(deliveryAddress.getStreet1());
			orderPO.setRecipientHouseNo(deliveryAddress.getHouseNo());
			orderPO.setRecipientZipCode(deliveryAddress.getZipCode());
			orderPO.setRecipientCity(deliveryAddress.getCity());
			orderPO.setRecipientCountry(deliveryAddress.getCountry().getAlpha2());
			final ContactPerson deliveryContact = deliveryOrder.getDeliveryContact();
			//noinspection ConstantConditions
			orderPO.setRecipientEmailAddress(deliveryContact.getEmailAddress());
			//noinspection ConstantConditions
			orderPO.setRecipientPhone(deliveryContact.getPhoneAsStringOrNull());
		}
		{
			// Predict aka Notification

			final DpdOrderCustomDeliveryData customDeliveryData = DpdOrderCustomDeliveryData.cast(deliveryOrder.getCustomDeliveryData());
			orderPO.setNotificationChannel(customDeliveryData.getNotificationChannel().getCode());
		}
		{
			// General Shipment Data
			// dpdStoreOrder.identification number // there's no identification number saved. it needs to be created in the client during the request creation!
			final DpdOrderCustomDeliveryData customDeliveryData = DpdOrderCustomDeliveryData.cast(deliveryOrder.getCustomDeliveryData());
			orderPO.setDpdProduct(deliveryOrder.getShipperProduct().getCode());
			orderPO.setDpdOrderType(customDeliveryData.getOrderType());
			orderPO.setSendingDepot(customDeliveryData.getSendingDepot());
			orderPO.setPaperFormat(customDeliveryData.getPaperFormat().getCode());
			orderPO.setPrinterLanguage(customDeliveryData.getPrinterLanguage());
		}
		{
			// Parcels aka Packages aka DeliveryOrderLines
			final List<I_DPD_StoreOrderLine> lines = retrieveAllOrderLines(orderPO.getDPD_StoreOrder_ID());

			for (final DeliveryOrderLine deliveryOrderLine : deliveryOrder.getDeliveryOrderLines())
			{
				final I_DPD_StoreOrderLine orderLinePO = retrieveStoreOrderLinePoByPackageIdOrCreateNew(lines, deliveryOrderLine);

				//noinspection ConstantConditions
				orderLinePO.setPackageContent(deliveryOrderLine.getContent());
				orderLinePO.setWeightInKg(deliveryOrderLine.getGrossWeightKg().intValue());
				orderLinePO.setM_Package_ID(deliveryOrderLine.getPackageId().getRepoId());
				orderLinePO.setDPD_StoreOrder_ID(orderPO.getDPD_StoreOrder_ID());

				final PackageDimensions packageDimensions = deliveryOrderLine.getPackageDimensions();
				orderLinePO.setLengthInCm(packageDimensions.getLengthInCM());
				orderLinePO.setWidthInCm(packageDimensions.getWidthInCM());
				orderLinePO.setHeightInCm(packageDimensions.getHeightInCM());

				InterfaceWrapperHelper.save(orderLinePO);
			}
		}
		{
			// Data related to DeliveryOrder completion

			final DpdOrderCustomDeliveryData customDeliveryData = DpdOrderCustomDeliveryData.cast(deliveryOrder.getCustomDeliveryData());

			final String awb = deliveryOrder.getTrackingNumber();
			final byte[] pdfData = customDeliveryData.getPdfData();

			//noinspection ConstantConditions
			orderPO.setPdfLabelData(pdfData);
			//noinspection ConstantConditions
			orderPO.setawb(awb);
			//noinspection ConstantConditions
			orderPO.setTrackingURL(deliveryOrder.getTrackingUrl());
		}
		InterfaceWrapperHelper.save(orderPO);

		return orderPO;
	}

	private I_DPD_StoreOrderLine retrieveStoreOrderLinePoByPackageIdOrCreateNew(@NonNull final List<I_DPD_StoreOrderLine> lines, final DeliveryOrderLine deliveryOrderLine)
	{
		return lines.stream()
				.filter(it -> it.getM_Package_ID() == deliveryOrderLine.getPackageId().getRepoId())
				.findFirst()
				.orElse(InterfaceWrapperHelper.newInstance(I_DPD_StoreOrderLine.class));
	}

	/**
	 * Retrieves the DPD specific PO and returns the DeliveryOrder DTO.
	 */
	@NonNull
	private DeliveryOrder toDeliveryOrderFromPO(@NonNull final I_DPD_StoreOrder orderPO)
	{
		final List<I_DPD_StoreOrderLine> linesPO = retrieveAllOrderLines(orderPO.getDPD_StoreOrder_ID());

		final ImmutableList.Builder<DeliveryOrderLine> deliveryOrderLIneBuilder = ImmutableList.builder();
		for (final I_DPD_StoreOrderLine linePO : linesPO)
		{
			final DeliveryOrderLine line = DeliveryOrderLine.builder()
					.packageId(PackageId.ofRepoId(linePO.getM_Package_ID()))
					.packageDimensions(PackageDimensions.builder()
							.lengthInCM(linePO.getLengthInCm())
							.widthInCM(linePO.getWidthInCm())
							.heightInCM(linePO.getHeightInCm())
							.build())
					.grossWeightKg(BigDecimal.valueOf(linePO.getWeightInKg()))
					.content(linePO.getPackageContent())
					.build();
			deliveryOrderLIneBuilder.add(line);
		}

		return DeliveryOrder.builder()
				//
				// Misc
				.id(DeliveryOrderId.ofRepoId(orderPO.getDPD_StoreOrder_ID()))
				.shipperId(ShipperId.ofRepoId(orderPO.getM_Shipper_ID()))
				.shipperTransportationId(ShipperTransportationId.ofRepoId(orderPO.getM_ShipperTransportation_ID()))
				//
				// Pickup aka Sender
				.pickupAddress(Address.builder()
						.companyName1(orderPO.getSenderName1())
						.companyName2(orderPO.getSenderName2())
						.street1(orderPO.getSenderStreet())
						.houseNo(orderPO.getSenderHouseNo())
						.zipCode(orderPO.getSenderZipCode())
						.city(orderPO.getSenderCity())
						.country(countryCodeFactory.getCountryCodeByAlpha2(orderPO.getSenderCountry()))
						.build())
				//
				// Pickup Date and Time
				.pickupDate(PickupDate.builder()
						.date(TimeUtil.asLocalDate(orderPO.getPickupDate()))
						.timeFrom(TimeUtil.asLocalTime(orderPO.getPickupTimeFrom()))
						.timeTo(TimeUtil.asLocalTime(orderPO.getPickupTimeTo()))
						.build())
				//
				// Delivery aka Recipient
				.deliveryAddress(Address.builder()
						.companyName1(orderPO.getRecipientName1())
						.companyName2(orderPO.getRecipientName2())
						.street1(orderPO.getRecipientStreet())
						.houseNo(orderPO.getRecipientHouseNo())
						.zipCode(orderPO.getRecipientZipCode())
						.city(orderPO.getRecipientCity())
						.country(countryCodeFactory.getCountryCodeByAlpha2(orderPO.getRecipientCountry()))
						.build())
				.deliveryContact(ContactPerson.builder()
						.emailAddress(orderPO.getRecipientEmailAddress())
						.simplePhoneNumber(orderPO.getRecipientPhone())
						.build())
				//
				// Predict aka Notification
				// General ShipmentData
				// Data related to DeliveryOrder completion
				.customDeliveryData(DpdOrderCustomDeliveryData.builder()
						.notificationChannel(DpdNotificationChannel.ofCode(orderPO.getNotificationChannel()))
						.orderType(orderPO.getDpdOrderType())
						.sendingDepot(orderPO.getSendingDepot())
						.paperFormat(DpdPaperFormat.ofCode(orderPO.getPaperFormat()))
						.printerLanguage(orderPO.getPrinterLanguage())
						.pdfData(orderPO.getPdfLabelData())
						.build())
				.shipperProduct(DpdShipperProduct.ofCode(orderPO.getDpdProduct()))
				//
				// Parcels aka Packages aka DeliveryOrderLines
				.deliveryOrderLines(deliveryOrderLIneBuilder.build())
				//
				// Data related to DeliveryOrder completion
				.trackingNumber(orderPO.getawb())
				.trackingUrl(orderPO.getTrackingURL())
				.build();
	}

	private List<I_DPD_StoreOrderLine> retrieveAllOrderLines(final int dpdStoreOrderId)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_DPD_StoreOrderLine.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_DPD_StoreOrderLine.COLUMNNAME_DPD_StoreOrder_ID, dpdStoreOrderId)
				.create()
				.list();
	}

}
