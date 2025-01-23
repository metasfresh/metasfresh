/*
 * #%L
 * de.metas.shipper.gateway.dhl
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

package de.metas.shipper.gateway.dhl;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.location.CountryCode;
import de.metas.mpackage.PackageId;
import de.metas.shipper.gateway.dhl.model.DhlCustomDeliveryData;
import de.metas.shipper.gateway.dhl.model.DhlCustomDeliveryDataDetail;
import de.metas.shipper.gateway.dhl.model.DhlSequenceNumber;
import de.metas.shipper.gateway.dhl.model.DhlShipperProduct;
import de.metas.shipper.gateway.dhl.model.I_DHL_ShipmentOrder;
import de.metas.shipper.gateway.dhl.model.I_DHL_ShipmentOrderRequest;
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
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

@Repository
public class DhlDeliveryOrderRepository
{
	private static final IQueryBL queryBL = Services.get(IQueryBL.class);

	@NonNull
	public DeliveryOrder getByRepoId(@NonNull final DeliveryOrderId deliveryOrderRepoId)
	{
		final I_DHL_ShipmentOrderRequest dhlShipmentOrderRequest = InterfaceWrapperHelper.load(deliveryOrderRepoId, I_DHL_ShipmentOrderRequest.class);
		Check.assumeNotNull(dhlShipmentOrderRequest, "DHL delivery order must exist for ID={}", deliveryOrderRepoId);

		return toDeliveryOrderFromPO(deliveryOrderRepoId);
	}

	/**
	 * Explanation of the different data structures:
	 * <p>
	 * - DeliveryOrder is the DTO
	 * - I_DHL_ShipmentOrderRequest is the persisted object for that DTO with data relevant for DHL.
	 * Each different shipper has its own "shipper-PO" with its own relevant data.
	 */
	@NonNull
	public DeliveryOrder save(@NonNull final DeliveryOrder deliveryOrder)
	{
		if (deliveryOrder.getId() != null)
		{
			updateShipmentOrderRequestPO(deliveryOrder);
			return deliveryOrder;
		}
		else
		{
			final I_DHL_ShipmentOrderRequest orderRequestPO = createShipmentOrderRequestPO(deliveryOrder);
			return deliveryOrder
					.toBuilder()
					.id(DeliveryOrderId.ofRepoId(orderRequestPO.getDHL_ShipmentOrderRequest_ID()))
					.build();
		}
	}

	/**
	 * Read the DHL specific PO and return a DTO.
	 * <p>
	 * keep in sync with {@link #createShipmentOrderRequestPO(DeliveryOrder)} and {@link DhlDraftDeliveryOrderCreator#createDraftDeliveryOrder(de.metas.shipper.gateway.spi.DraftDeliveryOrderCreator.CreateDraftDeliveryOrderRequest)}
	 */
	@NonNull
	private DeliveryOrder toDeliveryOrderFromPO(final @NonNull DeliveryOrderId deliveryOrderId)
	{
		final List<I_DHL_ShipmentOrder> ordersPo = getAllShipmentOrdersForRequest(deliveryOrderId);

		final I_DHL_ShipmentOrder firstOrder = ordersPo.stream()
				.findFirst()
				.orElseThrow(() -> new NoSuchElementException("No value present"));

		final ImmutableSet<PackageId> packageIds = ordersPo.stream()
				.map(shipmentOrder -> PackageId.ofRepoId(shipmentOrder.getPackageId()))
				.collect(ImmutableSet.toImmutableSet());

		return DeliveryOrder.builder()
				.id(deliveryOrderId)
				.deliveryAddress(Address.builder()
						.bpartnerId(firstOrder.getC_BPartner_ID())
						.companyName1(firstOrder.getDHL_Receiver_Name1())
						.companyName2(firstOrder.getDHL_Receiver_Name2())
						.street1(firstOrder.getDHL_Receiver_StreetName1())
						.street2(firstOrder.getDHL_Receiver_StreetName2())
						.houseNo(firstOrder.getDHL_Receiver_StreetNumber())
						.zipCode(firstOrder.getDHL_Receiver_ZipCode())
						.city(firstOrder.getDHL_Receiver_City())
						.country(CountryCode.builder()
								.alpha2(firstOrder.getDHL_Receiver_CountryISO2Code())
								.alpha3(firstOrder.getDHL_Receiver_CountryISO3Code())
								.build())
						.build())
				.deliveryContact(ContactPerson.builder()
						.simplePhoneNumber(firstOrder.getDHL_Receiver_Phone())
						.emailAddress(firstOrder.getDHL_Receiver_Email())
						.build())
				.pickupAddress(Address.builder()
						.companyName1(firstOrder.getDHL_Shipper_Name1())
						.companyName2(firstOrder.getDHL_Shipper_Name2())
						.street1(firstOrder.getDHL_Shipper_StreetName1())
						.street2(firstOrder.getDHL_Shipper_StreetName2())
						.houseNo(firstOrder.getDHL_Shipper_StreetNumber())
						.zipCode(firstOrder.getDHL_Shipper_ZipCode())
						.city(firstOrder.getDHL_Shipper_City())
						.country(CountryCode.builder()
								.alpha2(firstOrder.getDHL_Shipper_CountryISO2Code())
								.alpha3(firstOrder.getDHL_Shipper_CountryISO3Code())
								.build())
						.build())
				.pickupDate(PickupDate.builder()
						.date(LocalDate.parse(firstOrder.getDHL_ShipmentDate(), DateTimeFormatter.ISO_LOCAL_DATE))
						.build())
				// other
				.customerReference(firstOrder.getCustomerReference())
				.shipperProduct(DhlShipperProduct.forCode(firstOrder.getDHL_Product()))
				.deliveryOrderLines(constructDeliveryOrderLines(firstOrder, packageIds))
				.shipperId(ShipperId.ofRepoId(firstOrder.getM_Shipper_ID()))
				.shipperTransportationId(ShipperTransportationId.ofRepoId(firstOrder.getM_ShipperTransportation_ID()))
				.build();
	}

	public static List<I_DHL_ShipmentOrder> getAllShipmentOrdersForRequest(final @NonNull DeliveryOrderId requestId)
	{
		return queryBL
				.createQueryBuilder(I_DHL_ShipmentOrder.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_DHL_ShipmentOrder.COLUMNNAME_DHL_ShipmentOrderRequest_ID, requestId)
				.create()
				.list();
	}

	private Iterable<DeliveryOrderLine> constructDeliveryOrderLines(final I_DHL_ShipmentOrder firstOrder, final ImmutableSet<PackageId> packageIds)
	{
		final DeliveryOrderLine.DeliveryOrderLineBuilder orderLineBuilder = DeliveryOrderLine.builder()
				.packageDimensions(PackageDimensions.builder()
						.widthInCM(firstOrder.getDHL_WidthInCm())
						.lengthInCM(firstOrder.getDHL_LengthInCm())
						.heightInCM(firstOrder.getDHL_HeightInCm())
						.build())
				.grossWeightKg(firstOrder.getDHL_WeightInKg());
		return packageIds.stream()
				.map(orderLineBuilder::packageId)
				.map(DeliveryOrderLine.DeliveryOrderLineBuilder::build)
				.collect(ImmutableList.toImmutableList());
	}

	/**
	 * Persists the shipper-dependant DeliveryOrder details
	 * <p>
	 * keep in sync with {@link #toDeliveryOrderFromPO(DeliveryOrderId)}
	 * and {@link DhlDraftDeliveryOrderCreator#createDraftDeliveryOrder(de.metas.shipper.gateway.spi.DraftDeliveryOrderCreator.CreateDraftDeliveryOrderRequest)}
	 */
	@NonNull
	private I_DHL_ShipmentOrderRequest createShipmentOrderRequestPO(@NonNull final DeliveryOrder deliveryOrder)
	{
		final I_DHL_ShipmentOrderRequest shipmentOrderRequest = InterfaceWrapperHelper.newInstance(I_DHL_ShipmentOrderRequest.class);
		InterfaceWrapperHelper.save(shipmentOrderRequest);

		for (final DeliveryOrderLine deliveryOrderLine : deliveryOrder.getDeliveryOrderLines())
		{
				final ContactPerson deliveryContact = deliveryOrder.getDeliveryContact();

				final I_DHL_ShipmentOrder shipmentOrder = InterfaceWrapperHelper.newInstance(I_DHL_ShipmentOrder.class);
				shipmentOrder.setDHL_ShipmentOrderRequest_ID(shipmentOrderRequest.getDHL_ShipmentOrderRequest_ID()); // save to parent
				{
					//
					// Misc which doesn't fit dhl structure
					final Address deliveryAddress = deliveryOrder.getDeliveryAddress();

					shipmentOrder.setPackageId(deliveryOrderLine.getPackageId().getRepoId());
					shipmentOrder.setC_BPartner_ID(deliveryAddress.getBpartnerId());
					shipmentOrder.setM_Shipper_ID(deliveryOrder.getShipperId().getRepoId());
					shipmentOrder.setM_ShipperTransportation_ID(deliveryOrder.getShipperTransportationId().getRepoId());
					shipmentOrder.setInternationalDelivery(!Objects.equals(deliveryOrder.getDeliveryAddress().getCountry(),deliveryOrder.getPickupAddress().getCountry()));
				}

				{
					// (2.2.1) Shipment Details aka PackageDetails
					shipmentOrder.setDHL_Product(deliveryOrder.getShipperProduct().getCode());
					//noinspection ConstantConditions
					shipmentOrder.setCustomerReference(deliveryOrder.getCustomerReference());
					shipmentOrder.setDHL_ShipmentDate(deliveryOrder.getPickupDate().getDate().format(DateTimeFormatter.ISO_LOCAL_DATE));
					// (2.2.1.8)
					final PackageDimensions packageDimensions = deliveryOrderLine.getPackageDimensions();
					if (packageDimensions != null)
					{
						shipmentOrder.setDHL_HeightInCm(packageDimensions.getHeightInCM());
						shipmentOrder.setDHL_LengthInCm(packageDimensions.getLengthInCM());
						shipmentOrder.setDHL_WidthInCm(packageDimensions.getWidthInCM());
					}
					shipmentOrder.setDHL_WeightInKg(deliveryOrderLine.getGrossWeightKg());
					// (2.2.1.10)
					//noinspection ConstantConditions
					shipmentOrder.setDHL_RecipientEmailAddress(deliveryContact != null ? deliveryContact.getEmailAddress() : null);
				}

				{
					// (2.2.4) Receiver aka Delivery
					final Address deliveryAddress = deliveryOrder.getDeliveryAddress();

					shipmentOrder.setDHL_Receiver_Name1(deliveryAddress.getCompanyName1());
					// (2.2.4.2)
					shipmentOrder.setDHL_Receiver_Name2(deliveryAddress.getCompanyName2());
					shipmentOrder.setDHL_Receiver_StreetName1(deliveryAddress.getStreet1());
					shipmentOrder.setDHL_Receiver_StreetName2(deliveryAddress.getStreet2());
					shipmentOrder.setDHL_Receiver_StreetNumber(deliveryAddress.getHouseNo());
					shipmentOrder.setDHL_Receiver_ZipCode(deliveryAddress.getZipCode());
					shipmentOrder.setDHL_Receiver_City(deliveryAddress.getCity());
					// (2.2.4.2.10)
					shipmentOrder.setDHL_Receiver_CountryISO2Code(deliveryAddress.getCountry().getAlpha2());
					shipmentOrder.setDHL_Receiver_CountryISO3Code(deliveryAddress.getCountry().getAlpha3());
					// (2.2.4.2)
					//noinspection ConstantConditions
					shipmentOrder.setDHL_Receiver_Email(deliveryContact != null ? deliveryContact.getEmailAddress() : null);
					//noinspection ConstantConditions
					shipmentOrder.setDHL_Receiver_Phone(deliveryContact != null ? deliveryContact.getPhoneAsStringOrNull() : null);
				}

				{
					// (2.2.2) Shipper aka Pickup
					final Address pickupAddress = deliveryOrder.getPickupAddress();
					// (2.2.2.1)
					shipmentOrder.setDHL_Shipper_Name1(pickupAddress.getCompanyName1());
					shipmentOrder.setDHL_Shipper_Name2(pickupAddress.getCompanyName2());
					// (2.2.2.2)
					shipmentOrder.setDHL_Shipper_StreetName1(pickupAddress.getStreet1());
					shipmentOrder.setDHL_Shipper_StreetName2(pickupAddress.getStreet2());
					shipmentOrder.setDHL_Shipper_StreetNumber(pickupAddress.getHouseNo());
					shipmentOrder.setDHL_Shipper_ZipCode(pickupAddress.getZipCode());
					shipmentOrder.setDHL_Shipper_City(pickupAddress.getCity());
					// (2.2.2.2.8)
					shipmentOrder.setDHL_Shipper_CountryISO2Code(pickupAddress.getCountry().getAlpha2());
					shipmentOrder.setDHL_Shipper_CountryISO3Code(pickupAddress.getCountry().getAlpha3());
				}

				//				{
				//					// (2.2.6) Export Document - only for international shipments
				//
				//					//noinspection ConstantConditions
				//					final DhlCustomDeliveryData dhlCustomDeliveryData = DhlCustomDeliveryData.cast(deliveryOrder.getCustomDeliveryData());
				//					final DhlCustomDeliveryDataDetail deliveryDataDetail = dhlCustomDeliveryData.getDetailByPackageId(packageIdsAsList.get(i));
				//					if (deliveryDataDetail.isInternationalDelivery())
				//					{
				//						final DhlCustomsDocument customsDocument = deliveryDataDetail.getCustomsDocument();
				//
				//						//noinspection ConstantConditions
				//						shipmentOrder.setExportType(customsDocument.getExportType());
				//						shipmentOrder.setExportTypeDescription(customsDocument.getExportTypeDescription());
				//						shipmentOrder.setAdditionalFee(customsDocument.getAdditionalFee());
				//						// (2.2.6.9)
				//						shipmentOrder.setElectronicExportNotification(customsDocument.getElectronicExportNotification());
				//						// (2.2.6.10)
				//						shipmentOrder.setPackageDescription(customsDocument.getPackageDescription());
				//						shipmentOrder.setCustomsTariffNumber(customsDocument.getCustomsTariffNumber());
				//						shipmentOrder.setCustomsAmount(customsDocument.getCustomsAmount().intValue());
				//						shipmentOrder.setNetWeightKg(customsDocument.getNetWeightInKg());
				//						shipmentOrder.setCustomsValue(customsDocument.getCustomsValue());
				//						shipmentOrder.setC_Customs_Invoice_ID(customsDocument.getInvoiceId().getRepoId());
				//						shipmentOrder.setC_Customs_Invoice_Line_ID(customsDocument.getInvoiceLineId().getRepoId());
				//					}
				//				}

				InterfaceWrapperHelper.save(shipmentOrder);
				{
					// (2.1) The id column (I_DHL_ShipmentOrder_ID) is used as ShipmentOrder.sequenceNumber since it's unique
					// nothing to persist here, but must be filled when retrieving the PO
				}
		}
		return shipmentOrderRequest;
	}

	private void updateShipmentOrderRequestPO(@NonNull final DeliveryOrder deliveryOrder)
	{
		for (final DeliveryOrderLine deliveryOrderLine : deliveryOrder.getDeliveryOrderLines())
		{
			final PackageId packageId = deliveryOrderLine.getPackageId();
			final DhlCustomDeliveryData customDeliveryData = DhlCustomDeliveryData.cast(deliveryOrder.getCustomDeliveryData());

			final I_DHL_ShipmentOrder shipmentOrder = getShipmentOrderByRequestIdAndPackageId(deliveryOrder.getId().getRepoId(), packageId.getRepoId());
			final DhlCustomDeliveryDataDetail deliveryDetail = customDeliveryData.getDetailBySequenceNumber(DhlSequenceNumber.of(shipmentOrder.getDHL_ShipmentOrder_ID()));

			final String awb = deliveryDetail.getAwb();
			if (awb != null)
			{
				shipmentOrder.setawb(awb);
			}

			final byte[] pdfData = deliveryDetail.getPdfLabelData();
			if (pdfData != null)
			{
				shipmentOrder.setPdfLabelData(pdfData);
			}

			final String trackingUrl = deliveryDetail.getTrackingUrl();
			if (trackingUrl != null)
			{
				shipmentOrder.setTrackingURL(trackingUrl);
			}

			saveRecord(shipmentOrder);
		}
	}

	@VisibleForTesting
	I_DHL_ShipmentOrder getShipmentOrderByRequestIdAndPackageId(final int requestId, final int packageId)
	{
		return queryBL
				.createQueryBuilder(I_DHL_ShipmentOrder.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_DHL_ShipmentOrder.COLUMNNAME_DHL_ShipmentOrderRequest_ID, requestId)
				.addEqualsFilter(I_DHL_ShipmentOrder.COLUMNNAME_PackageId, packageId)
				.create()
				.first();
	}
}
