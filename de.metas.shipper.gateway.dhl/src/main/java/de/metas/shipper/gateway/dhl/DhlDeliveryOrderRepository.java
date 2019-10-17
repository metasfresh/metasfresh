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

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.attachments.AttachmentEntryService;
import de.metas.shipper.gateway.dhl.model.DhlCustomDeliveryData;
import de.metas.shipper.gateway.dhl.model.DhlCustomDeliveryDataDetail;
import de.metas.shipper.gateway.dhl.model.DhlSequenceNumber;
import de.metas.shipper.gateway.dhl.model.DhlServiceType;
import de.metas.shipper.gateway.dhl.model.I_DHL_ShipmentOrder;
import de.metas.shipper.gateway.dhl.model.I_DHL_ShipmentOrderRequest;
import de.metas.shipper.gateway.spi.DeliveryOrderId;
import de.metas.shipper.gateway.spi.DeliveryOrderRepository;
import de.metas.shipper.gateway.spi.model.Address;
import de.metas.shipper.gateway.spi.model.ContactPerson;
import de.metas.shipper.gateway.spi.model.CountryCode;
import de.metas.shipper.gateway.spi.model.DeliveryOrder;
import de.metas.shipper.gateway.spi.model.DeliveryPosition;
import de.metas.shipper.gateway.spi.model.PackageDimensions;
import de.metas.shipper.gateway.spi.model.PickupDate;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.ITableRecordReference;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.SpringContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

@Repository
public class DhlDeliveryOrderRepository implements DeliveryOrderRepository
{
	private static final Logger logger = LoggerFactory.getLogger(DhlDeliveryOrderRepository.class);

	@Override
	public String getShipperGatewayId()
	{
		return DhlConstants.SHIPPER_GATEWAY_ID;
	}

	@Override
	public ITableRecordReference toTableRecordReference(final DeliveryOrder deliveryOrder)
	{
		final int deliveryOrderRepoId = deliveryOrder.getRepoId();
		Check.assume(deliveryOrderRepoId > 0, "deliveryOrderRepoId > 0 for {}", deliveryOrder);
		return TableRecordReference.of(I_DHL_ShipmentOrderRequest.Table_Name, deliveryOrderRepoId);
	}

	@Override
	public DeliveryOrder getByRepoId(final DeliveryOrderId deliveryOrderRepoId)
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
	@Override
	public DeliveryOrder save(@NonNull final DeliveryOrder deliveryOrder)
	{
		if (deliveryOrder.getRepoId() >= 1)
		{
			toUpdateShipmentOrderRequestPO(deliveryOrder);
			return deliveryOrder;
		}
		else
		{
			final I_DHL_ShipmentOrderRequest orderRequestPO = toCreateShipmentOrderRequestPO(deliveryOrder);
			return deliveryOrder
					.toBuilder()
					.repoId(orderRequestPO.getDHL_ShipmentOrderRequest_ID())
					.build();
		}
	}

	/**
	 * Read the DHL specific PO and return a DTO.
	 * <p>
	 * keep in sync with {@link #toCreateShipmentOrderRequestPO(DeliveryOrder)}
	 */
	private DeliveryOrder toDeliveryOrderFromPO(final I_DHL_ShipmentOrderRequest requestPo)
	{
		final List<I_DHL_ShipmentOrder> ordersPo = getAllShipmentOrdersForRequest(requestPo.getDHL_ShipmentOrderRequest_ID());

		final I_DHL_ShipmentOrder firstOrder = ordersPo.get(0);

		final ImmutableList<DhlCustomDeliveryDataDetail> dhlCustomDeliveryDataDetail = ordersPo.stream()
				.map(po -> DhlCustomDeliveryDataDetail.builder()
						.packageId(po.getPackageId())
						.awb(po.getawb())
						.sequenceNumber(DhlSequenceNumber.of(po.getDHL_ShipmentOrder_ID()))
						.pdfLabelData(po.getPdfLabelData())
						.build())
				.collect(ImmutableList.toImmutableList());

		final ImmutableSet<Integer> packageIds = dhlCustomDeliveryDataDetail.stream()
				.map(DhlCustomDeliveryDataDetail::getPackageId)
				.collect(ImmutableSet.toImmutableSet());

		//noinspection UnnecessaryLocalVariable
		final DeliveryOrder doFromPO = DeliveryOrder.builder()
				.repoId(requestPo.getDHL_ShipmentOrderRequest_ID())
				.deliveryAddress(Address.builder()
						.bpartnerId(firstOrder.getC_BPartner_ID())
						.bpartnerLocationId(firstOrder.getC_BPartner_Location_ID())
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
				.serviceType(DhlServiceType.valueOf(firstOrder.getDHL_Product()))
				.deliveryPositions(constructDeliveryPositions(firstOrder, packageIds))
				.shipperId(firstOrder.getM_Shipper_ID())
				.shipperTransportationId(firstOrder.getM_ShipperTransportation_ID())
				.customDeliveryData(DhlCustomDeliveryData.builder()
						.details(dhlCustomDeliveryDataDetail)
						.build())
				.build();

		return doFromPO;
	}

	private static List<I_DHL_ShipmentOrder> getAllShipmentOrdersForRequest(final int requestId)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilderOutOfTrx(I_DHL_ShipmentOrder.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_DHL_ShipmentOrder.COLUMNNAME_DHL_ShipmentOrderRequest_ID, requestId)
				.create()
				.list();
	}

	@NonNull
	private Iterable<? extends DeliveryPosition> constructDeliveryPositions(
			@NonNull final I_DHL_ShipmentOrder firstOrder,
			@NonNull final ImmutableSet<Integer> packageIds)
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
	 * todo: https://chat.metasfresh.org/metasfresh/pl/z7pahtuci3b35naios3ha16tnh
	 * <p>
	 * keep in sync with {@link #toDeliveryOrderFromPO(I_DHL_ShipmentOrderRequest)}
	 */
	@NonNull
	private I_DHL_ShipmentOrderRequest toCreateShipmentOrderRequestPO(@NonNull final DeliveryOrder deliveryOrder)
	{
		final I_DHL_ShipmentOrderRequest shipmentOrderRequest = InterfaceWrapperHelper.newInstance(I_DHL_ShipmentOrderRequest.class);
		InterfaceWrapperHelper.save(shipmentOrderRequest);

		// maybe this will be removed in the future, but for now it simplifies the PO deserialisation implementation and other implementation details dramatically, ref: constructDeliveryPositions()
		// therefore please ignore the for loops over `deliveryOrder.getDeliveryPositions()` as they don't help at all
		Check.errorIf(deliveryOrder.getDeliveryPositions().size() != 1,
				"The DHL implementation needs to always create DeliveryOrders with exactly 1 DeliveryPosition; deliveryOrder={}",
				deliveryOrder);

		//		int totalPackages = 0;
		//		final int MAX_NUMBER_OF_PACKAGES = 30; // todo this must not be hardcoded; though i'm not sure where i read about this limitation. maybe i'm wrong and there's no limitations in the number of packages sent in 1 order
		for (final DeliveryPosition deliveryPosition : deliveryOrder.getDeliveryPositions()) // only a single delivery position should exist
		{
			final ImmutableList<Integer> packageIdsAsList = deliveryPosition.getPackageIds().asList();
			for (int i = 0; i < deliveryPosition.getNumberOfPackages(); i++)
			{
				//				if (totalPackages > MAX_NUMBER_OF_PACKAGES)
				//				{
				//					throw new AdempiereException("Maximum number of packages for a delivery order for DHL is 30.");
				//				}
				//				totalPackages++;

				final ContactPerson deliveryContact = deliveryOrder.getDeliveryContact();

				final I_DHL_ShipmentOrder shipmentOrder = InterfaceWrapperHelper.newInstance(I_DHL_ShipmentOrder.class);
				shipmentOrder.setDHL_ShipmentOrderRequest_ID(shipmentOrderRequest.getDHL_ShipmentOrderRequest_ID()); // save to parent
				{
					//
					// Misc which doesn't fit dhl structure
					final Address deliveryAddress = deliveryOrder.getDeliveryAddress();

					shipmentOrder.setPackageId(packageIdsAsList.get(i));
					shipmentOrder.setC_BPartner_ID(deliveryAddress.getBpartnerId());
					shipmentOrder.setC_BPartner_Location_ID(deliveryAddress.getBpartnerLocationId());
					shipmentOrder.setM_Shipper_ID(deliveryOrder.getShipperId());
					shipmentOrder.setM_ShipperTransportation_ID(deliveryOrder.getShipperTransportationId());

				}

				{
					// (2.2.1) Shipment Details aka PackageDetails
					shipmentOrder.setDHL_Product(deliveryOrder.getServiceType().getCode());
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

				InterfaceWrapperHelper.save(shipmentOrder);
				{
					// (2.1) The id column (I_DHL_ShipmentOrder_ID) is used as ShipmentOrder.sequenceNumber since it's unique
					// nothing to persist here, but must be filled when retrieving the PO
				}
			} // fori loop
		}
		return shipmentOrderRequest;
	}

	private void toUpdateShipmentOrderRequestPO(@NonNull final DeliveryOrder deliveryOrder)
	{
		for (final DeliveryPosition deliveryPosition : deliveryOrder.getDeliveryPositions()) // only a single delivery position should exist
		{
			final ImmutableList<Integer> packageIdsAsList = deliveryPosition.getPackageIds().asList();
			for (int i = 0; i < deliveryPosition.getNumberOfPackages(); i++)
			{
				//noinspection ConstantConditions
				final DhlCustomDeliveryData customDeliveryData = DhlCustomDeliveryData.cast(deliveryOrder.getCustomDeliveryData());

				final I_DHL_ShipmentOrder shipmentOrder = getShipmentOrderByRequestIdAndPackageId(deliveryOrder.getRepoId(), packageIdsAsList.get(i));
				final String awb = customDeliveryData.getAwbBySequenceNumber(DhlSequenceNumber.of(shipmentOrder.getDHL_ShipmentOrder_ID()));
				if (awb != null)
				{
					shipmentOrder.setawb(awb);
				}

				final byte[] pdfData = customDeliveryData.getPdfLabelDataBySequenceNumber(DhlSequenceNumber.of(shipmentOrder.getDHL_ShipmentOrder_ID()));
				if (pdfData != null)
				{
					shipmentOrder.setPdfLabelData(pdfData);

					// save pdf as attachment as well
					final TableRecordReference salesOrderRef = TableRecordReference.of(I_DHL_ShipmentOrder.Table_Name, shipmentOrder.getDHL_ShipmentOrder_ID());

					final AttachmentEntryService attachmentEntryService = SpringContextHolder.instance.getBean(AttachmentEntryService.class);
					attachmentEntryService.createNewAttachment(salesOrderRef, awb + ".pdf", pdfData);
				}

				InterfaceWrapperHelper.save(shipmentOrder);
			}
		}
	}

	private static I_DHL_ShipmentOrder getShipmentOrderByRequestIdAndPackageId(final int requestId, final int packageId)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilderOutOfTrx(I_DHL_ShipmentOrder.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_DHL_ShipmentOrder.COLUMNNAME_DHL_ShipmentOrderRequest_ID, requestId)
				.addEqualsFilter(I_DHL_ShipmentOrder.COLUMNNAME_PackageId, packageId)
				.create()
				.first();
	}
}
