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
import de.metas.shipper.gateway.spi.DeliveryOrderRepository;
import de.metas.shipper.gateway.spi.model.Address;
import de.metas.shipper.gateway.spi.model.ContactPerson;
import de.metas.shipper.gateway.spi.model.DeliveryOrder;
import de.metas.shipper.gateway.spi.model.DeliveryPosition;
import de.metas.shipper.gateway.spi.model.PackageDimensions;
import de.metas.shipper.gateway.spi.model.PickupDate;
import de.metas.shipping.ShipperId;
import de.metas.shipping.model.ShipperTransportationId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.ITableRecordReference;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

@Repository
public class DhlDeliveryOrderRepository implements DeliveryOrderRepository
{
	@Override
	public String getShipperGatewayId()
	{
		return DhlConstants.SHIPPER_GATEWAY_ID;
	}

	@NonNull
	@Override
	public ITableRecordReference toTableRecordReference(@NonNull final DeliveryOrder deliveryOrder)
	{
		final DeliveryOrderId deliveryOrderRepoId = deliveryOrder.getId();
		Check.assumeNotNull(deliveryOrderRepoId, "DeliveryOrder ID must not be null for deliveryOrder " + deliveryOrder);
		return TableRecordReference.of(I_DHL_ShipmentOrderRequest.Table_Name, deliveryOrderRepoId);
	}

	@NonNull
	@Override
	public DeliveryOrder getByRepoId(@NonNull final DeliveryOrderId deliveryOrderRepoId)
	{
		final I_DHL_ShipmentOrderRequest dhlShipmentOrderRequest = InterfaceWrapperHelper.load(deliveryOrderRepoId, I_DHL_ShipmentOrderRequest.class);
		Check.assumeNotNull(dhlShipmentOrderRequest, "DHL delivery order must exist for ID={}", deliveryOrderRepoId);

		return toDeliveryOrderFromPO(dhlShipmentOrderRequest);
	}

	/**
	 * Explanation of the different data structures:
	 * <p>
	 * - DeliveryOrder is the DTO
	 * - I_DHL_ShipmentOrderRequest is the persisted object for that DTO with data relevant for DHL.
	 * Each different shipper has its own "shipper-PO" with its own relevant data.
	 */
	@NonNull
	@Override
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
	private DeliveryOrder toDeliveryOrderFromPO(@NonNull final I_DHL_ShipmentOrderRequest requestPo)
	{
		final List<I_DHL_ShipmentOrder> ordersPo = getAllShipmentOrdersForRequest(requestPo.getDHL_ShipmentOrderRequest_ID());

		final I_DHL_ShipmentOrder firstOrder = ordersPo.get(0);

		final ImmutableList<DhlCustomDeliveryDataDetail> dhlCustomDeliveryDataDetail = ordersPo.stream()
				.map(po -> {
					//					DhlCustomsDocument customsDocument = null;
					//					if (po.isInternationalDelivery())
					//					{
					//						customsDocument = DhlCustomsDocument.builder()
					//								.exportType(po.getExportType())
					//								.exportTypeDescription(po.getExportTypeDescription())
					//								.additionalFee(po.getAdditionalFee())
					//								.electronicExportNotification(po.getElectronicExportNotification())
					//								.packageDescription(po.getPackageDescription())
					//								.customsTariffNumber(po.getCustomsTariffNumber())
					//								.customsAmount(BigInteger.valueOf(po.getCustomsAmount()))
					//								.netWeightInKg(po.getNetWeightKg())
					//								.customsValue(po.getCustomsValue())
					//								.invoiceId(CustomsInvoiceId.ofRepoId(po.getC_Customs_Invoice_ID()))
					//								.invoiceLineId(CustomsInvoiceLineId.ofRepoIdOrNull(CustomsInvoiceId.ofRepoId(po.getC_Customs_Invoice_ID()), po.getC_Customs_Invoice_Line_ID()))
					//								.build();
					//					}

					return DhlCustomDeliveryDataDetail.builder()
							.packageId(po.getPackageId())
							.awb(po.getawb())
							.sequenceNumber(DhlSequenceNumber.of(po.getDHL_ShipmentOrder_ID()))
							.pdfLabelData(po.getPdfLabelData())
							.trackingUrl(po.getTrackingURL())
							.internationalDelivery(po.isInternationalDelivery())
							//							.customsDocument(customsDocument)
							.build();
				})
				.collect(ImmutableList.toImmutableList());

		final ImmutableSet<PackageId> packageIds = dhlCustomDeliveryDataDetail.stream()
				.map(dhlCustomDeliveryDataDetail1 -> PackageId.ofRepoId(dhlCustomDeliveryDataDetail1.getPackageId()))
				.collect(ImmutableSet.toImmutableSet());

		return DeliveryOrder.builder()
				.id(DeliveryOrderId.ofRepoId(requestPo.getDHL_ShipmentOrderRequest_ID()))
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
				.deliveryPositions(constructDeliveryPositions(firstOrder, packageIds))
				.shipperId(ShipperId.ofRepoId(firstOrder.getM_Shipper_ID()))
				.shipperTransportationId(ShipperTransportationId.ofRepoId(firstOrder.getM_ShipperTransportation_ID()))
				.customDeliveryData(DhlCustomDeliveryData.builder()
						.details(dhlCustomDeliveryDataDetail)
						.build())
				.build();
	}

	private static List<I_DHL_ShipmentOrder> getAllShipmentOrdersForRequest(final int requestId)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_DHL_ShipmentOrder.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_DHL_ShipmentOrder.COLUMNNAME_DHL_ShipmentOrderRequest_ID, requestId)
				.create()
				.list();
	}

	@NonNull
	private Iterable<? extends DeliveryPosition> constructDeliveryPositions(
			@NonNull final I_DHL_ShipmentOrder firstOrder,
			@NonNull final ImmutableSet<PackageId> packageIds)
	{
		final DeliveryPosition singleDeliveryPosition = DeliveryPosition.builder()
				.packageDimensions(PackageDimensions.builder()
						.widthInCM(firstOrder.getDHL_WidthInCm())
						.lengthInCM(firstOrder.getDHL_LengthInCm())
						.heightInCM(firstOrder.getDHL_HeightInCm())
						.build())
				.grossWeightKg(firstOrder.getDHL_WeightInKg().intValue())
				.numberOfPackages(packageIds.size())
				.packageIds(packageIds)
				.build();

		return Collections.singleton(singleDeliveryPosition);
	}

	/**
	 * Persists the shipper-dependant DeliveryOrder details
	 * <p>
	 * keep in sync with {@link #toDeliveryOrderFromPO(I_DHL_ShipmentOrderRequest)}
	 * and {@link DhlDraftDeliveryOrderCreator#createDraftDeliveryOrder(de.metas.shipper.gateway.spi.DraftDeliveryOrderCreator.CreateDraftDeliveryOrderRequest)}
	 */
	@NonNull
	private I_DHL_ShipmentOrderRequest createShipmentOrderRequestPO(@NonNull final DeliveryOrder deliveryOrder)
	{
		final I_DHL_ShipmentOrderRequest shipmentOrderRequest = InterfaceWrapperHelper.newInstance(I_DHL_ShipmentOrderRequest.class);
		InterfaceWrapperHelper.save(shipmentOrderRequest);

		// maybe this will be removed in the future, but for now it simplifies the PO deserialization implementation and other implementation details dramatically, ref: constructDeliveryPositions()
		// therefore please ignore the for loops over `deliveryOrder.getDeliveryPositions()` as they don't help at all
		Check.errorIf(deliveryOrder.getDeliveryPositions().size() != 1,
				"The DHL implementation needs to always create DeliveryOrders with exactly 1 DeliveryPosition; deliveryOrder={}",
				deliveryOrder);

		for (final DeliveryPosition deliveryPosition : deliveryOrder.getDeliveryPositions()) // only a single delivery position should exist
		{
			final ImmutableList<PackageId> packageIdsAsList = deliveryPosition.getPackageIds().asList();
			for (int i = 0; i < deliveryPosition.getNumberOfPackages(); i++)
			{
				final ContactPerson deliveryContact = deliveryOrder.getDeliveryContact();

				final I_DHL_ShipmentOrder shipmentOrder = InterfaceWrapperHelper.newInstance(I_DHL_ShipmentOrder.class);
				shipmentOrder.setDHL_ShipmentOrderRequest_ID(shipmentOrderRequest.getDHL_ShipmentOrderRequest_ID()); // save to parent
				{
					//
					// Misc which doesn't fit dhl structure
					final Address deliveryAddress = deliveryOrder.getDeliveryAddress();

					shipmentOrder.setPackageId(packageIdsAsList.get(i).getRepoId());
					shipmentOrder.setC_BPartner_ID(deliveryAddress.getBpartnerId());
					shipmentOrder.setM_Shipper_ID(deliveryOrder.getShipperId().getRepoId());
					shipmentOrder.setM_ShipperTransportation_ID(deliveryOrder.getShipperTransportationId().getRepoId());
				}

				{
					// (2.2.1) Shipment Details aka PackageDetails
					shipmentOrder.setDHL_Product(deliveryOrder.getShipperProduct().getCode());
					//noinspection ConstantConditions
					shipmentOrder.setCustomerReference(deliveryOrder.getCustomerReference());
					shipmentOrder.setDHL_ShipmentDate(deliveryOrder.getPickupDate().getDate().format(DateTimeFormatter.ISO_LOCAL_DATE));
					// (2.2.1.8)
					final PackageDimensions packageDimensions = deliveryPosition.getPackageDimensions();
					if (packageDimensions != null)
					{
						shipmentOrder.setDHL_HeightInCm(packageDimensions.getHeightInCM());
						shipmentOrder.setDHL_LengthInCm(packageDimensions.getLengthInCM());
						shipmentOrder.setDHL_WidthInCm(packageDimensions.getWidthInCM());
					}
					shipmentOrder.setDHL_WeightInKg(BigDecimal.valueOf(deliveryPosition.getGrossWeightKg()));
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
			} // fori loop
		}
		return shipmentOrderRequest;
	}

	private void updateShipmentOrderRequestPO(@NonNull final DeliveryOrder deliveryOrder)
	{
		for (final DeliveryPosition deliveryPosition : deliveryOrder.getDeliveryPositions()) // only a single delivery position should exist
		{
			final ImmutableList<PackageId> packageIdsAsList = deliveryPosition.getPackageIds().asList();
			for (int i = 0; i < deliveryPosition.getNumberOfPackages(); i++)
			{
				//noinspection ConstantConditions
				final DhlCustomDeliveryData customDeliveryData = DhlCustomDeliveryData.cast(deliveryOrder.getCustomDeliveryData());

				final I_DHL_ShipmentOrder shipmentOrder = getShipmentOrderByRequestIdAndPackageId(deliveryOrder.getId().getRepoId(), packageIdsAsList.get(i).getRepoId());
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

				InterfaceWrapperHelper.save(shipmentOrder);
			}
		}
	}

	@VisibleForTesting
	I_DHL_ShipmentOrder getShipmentOrderByRequestIdAndPackageId(final int requestId, final int packageId)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_DHL_ShipmentOrder.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_DHL_ShipmentOrder.COLUMNNAME_DHL_ShipmentOrderRequest_ID, requestId)
				.addEqualsFilter(I_DHL_ShipmentOrder.COLUMNNAME_PackageId, packageId)
				.create()
				.first();
	}
}
