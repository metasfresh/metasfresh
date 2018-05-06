package de.metas.shipper.gateway.derkurier;

import static de.metas.shipper.gateway.derkurier.DerKurierConstants.SHIPPER_GATEWAY_ID;
import static de.metas.shipper.gateway.derkurier.model.I_DerKurier_DeliveryOrderLine.COLUMNNAME_C_Country_ID;
import static de.metas.shipper.gateway.derkurier.model.I_DerKurier_DeliveryOrderLine.COLUMNNAME_DK_Consignee_City;
import static de.metas.shipper.gateway.derkurier.model.I_DerKurier_DeliveryOrderLine.COLUMNNAME_DK_Consignee_DesiredStation;
import static de.metas.shipper.gateway.derkurier.model.I_DerKurier_DeliveryOrderLine.COLUMNNAME_DK_Consignee_EMail;
import static de.metas.shipper.gateway.derkurier.model.I_DerKurier_DeliveryOrderLine.COLUMNNAME_DK_Consignee_HouseNumber;
import static de.metas.shipper.gateway.derkurier.model.I_DerKurier_DeliveryOrderLine.COLUMNNAME_DK_Consignee_Name;
import static de.metas.shipper.gateway.derkurier.model.I_DerKurier_DeliveryOrderLine.COLUMNNAME_DK_Consignee_Name2;
import static de.metas.shipper.gateway.derkurier.model.I_DerKurier_DeliveryOrderLine.COLUMNNAME_DK_Consignee_Name3;
import static de.metas.shipper.gateway.derkurier.model.I_DerKurier_DeliveryOrderLine.COLUMNNAME_DK_Consignee_Phone;
import static de.metas.shipper.gateway.derkurier.model.I_DerKurier_DeliveryOrderLine.COLUMNNAME_DK_Consignee_Street;
import static de.metas.shipper.gateway.derkurier.model.I_DerKurier_DeliveryOrderLine.COLUMNNAME_DK_Consignee_ZipCode;
import static de.metas.shipper.gateway.derkurier.model.I_DerKurier_DeliveryOrderLine.COLUMNNAME_DK_CustomerNumber;
import static de.metas.shipper.gateway.derkurier.model.I_DerKurier_DeliveryOrderLine.COLUMNNAME_DK_DesiredDeliveryDate;
import static de.metas.shipper.gateway.derkurier.model.I_DerKurier_DeliveryOrderLine.COLUMNNAME_DK_DesiredDeliveryTime_From;
import static de.metas.shipper.gateway.derkurier.model.I_DerKurier_DeliveryOrderLine.COLUMNNAME_DK_DesiredDeliveryTime_To;
import static de.metas.shipper.gateway.derkurier.model.I_DerKurier_DeliveryOrderLine.COLUMNNAME_DK_Reference;
import static org.adempiere.model.InterfaceWrapperHelper.getValueOrNull;
import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.annotation.Nullable;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.IPair;
import org.adempiere.util.lang.ITableRecordReference;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_Country;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Repository;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Objects;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Multimaps;

import de.metas.adempiere.service.ICountryDAO;
import de.metas.attachments.AttachmentEntry;
import de.metas.attachments.IAttachmentBL;
import de.metas.shipper.gateway.commons.DeliveryOrderUtil;
import de.metas.shipper.gateway.derkurier.misc.Converters;
import de.metas.shipper.gateway.derkurier.misc.DerKurierServiceType;
import de.metas.shipper.gateway.derkurier.model.I_DerKurier_DeliveryOrder;
import de.metas.shipper.gateway.derkurier.model.I_DerKurier_DeliveryOrderLine;
import de.metas.shipper.gateway.derkurier.model.I_DerKurier_DeliveryOrderLine_Package;
import de.metas.shipper.gateway.spi.DeliveryOrderRepository;
import de.metas.shipper.gateway.spi.model.Address;
import de.metas.shipper.gateway.spi.model.Address.AddressBuilder;
import de.metas.shipper.gateway.spi.model.ContactPerson;
import de.metas.shipper.gateway.spi.model.CountryCode;
import de.metas.shipper.gateway.spi.model.DeliveryDate;
import de.metas.shipper.gateway.spi.model.DeliveryOrder;
import de.metas.shipper.gateway.spi.model.DeliveryOrder.DeliveryOrderBuilder;
import de.metas.shipper.gateway.spi.model.DeliveryPosition;
import de.metas.shipper.gateway.spi.model.DeliveryPosition.DeliveryPositionBuilder;
import de.metas.shipper.gateway.spi.model.OrderId;
import de.metas.shipper.gateway.spi.model.PackageDimensions;
import de.metas.shipper.gateway.spi.model.PickupDate;
import lombok.NonNull;

/*
 * #%L
 * de.metas.shipper.gateway.derkurier
 * %%
 * Copyright (C) 2018 metas GmbH
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

@Repository
public class DerKurierDeliveryOrderRepository implements DeliveryOrderRepository
{
	private final Converters converters;

	public DerKurierDeliveryOrderRepository(@NonNull final Converters converters)
	{
		this.converters = converters;
	}

	@Override
	public String getShipperGatewayId()
	{
		return SHIPPER_GATEWAY_ID;
	}

	@Override
	public ITableRecordReference toTableRecordReference(@NonNull final DeliveryOrder deliveryOrder)
	{
		return TableRecordReference.of(I_DerKurier_DeliveryOrder.Table_Name, deliveryOrder.getRepoId());
	}

	public ITableRecordReference toTableRecordReference(@NonNull final DeliveryPosition deliveryPosition)
	{
		return TableRecordReference.of(I_DerKurier_DeliveryOrderLine.Table_Name, deliveryPosition.getRepoId());
	}

	@Override
	public DeliveryOrder getByRepoId(final int deliveryOrderRepoId)
	{
		final I_DerKurier_DeliveryOrder orderPO = loadAssumeRecordExists(deliveryOrderRepoId, I_DerKurier_DeliveryOrder.class);

		final DeliveryOrder deliveryOrder = toDeliveryOrder(orderPO);
		return deliveryOrder;
	}

	private DeliveryOrder toDeliveryOrder(@NonNull final I_DerKurier_DeliveryOrder headerRecord)
	{
		final IQueryBuilder<I_DerKurier_DeliveryOrderLine> commonQueryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_DerKurier_DeliveryOrderLine.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_DerKurier_DeliveryOrderLine.COLUMN_DerKurier_DeliveryOrder_ID, headerRecord.getDerKurier_DeliveryOrder_ID());

		final List<I_DerKurier_DeliveryOrderLine> lineRecords = commonQueryBuilder.create().list();

		final List<I_DerKurier_DeliveryOrderLine_Package> orderLinePackageRecords = commonQueryBuilder
				.andCollectChildren(I_DerKurier_DeliveryOrderLine_Package.COLUMN_DerKurier_DeliveryOrderLine_ID)
				.addOnlyActiveRecordsFilter()
				.create().list();
		final ImmutableListMultimap<Integer, I_DerKurier_DeliveryOrderLine_Package> lineId2PackageRecords = //
				Multimaps.index(orderLinePackageRecords, I_DerKurier_DeliveryOrderLine_Package::getDerKurier_DeliveryOrderLine_ID);

		final DeliveryOrderBuilder deliverOrderBuilder = DeliveryOrder
				.builder()
				.repoId(headerRecord.getDerKurier_DeliveryOrder_ID())
				.shipperId(headerRecord.getM_Shipper_ID())
				.shipperTransportationId(headerRecord.getM_ShipperTransportation_ID())
				.serviceType(DerKurierServiceType.OVERNIGHT)
				.pickupAddress(createPickupAddress(headerRecord))
				.pickupDate(createPickupDate(headerRecord))
				.orderId(OrderId.of(SHIPPER_GATEWAY_ID, String.valueOf(headerRecord.getDerKurier_DeliveryOrder_ID())));

		I_DerKurier_DeliveryOrderLine previousLineRecord = null; // used to make sure that all lineRecords have the same value when it comes to certain columns
		for (final I_DerKurier_DeliveryOrderLine lineRecord : lineRecords)
		{
			addLineRecordDataToDeliveryOrderBuilder(deliverOrderBuilder, lineRecord, previousLineRecord);

			final DeliveryPositionBuilder deliveryPositionBuilder = addLineRecordDataToDeliveryPositionBuilder(lineRecord, previousLineRecord);

			// add the M_Package_IDs to the current deliveryPositionBuilder
			lineId2PackageRecords
					.get(lineRecord.getDerKurier_DeliveryOrderLine_ID())
					.stream()
					.map(I_DerKurier_DeliveryOrderLine_Package::getM_Package_ID)
					.forEach(deliveryPositionBuilder::packageId);

			deliverOrderBuilder.deliveryPosition(deliveryPositionBuilder.build());

			previousLineRecord = lineRecord;
		}

		return deliverOrderBuilder.build();
	}

	@VisibleForTesting
	void addLineRecordDataToDeliveryOrderBuilder(
			@NonNull final DeliveryOrderBuilder deliverOrderBuilder,
			@NonNull final I_DerKurier_DeliveryOrderLine lineRecord,
			@Nullable final I_DerKurier_DeliveryOrderLine previousLineRecord)
	{
		deliverOrderBuilder.customerReference(assertSameAsPreviousValue(COLUMNNAME_DK_Reference, lineRecord, previousLineRecord));

		deliverOrderBuilder.deliveryAddress(createDeliveryAddress(previousLineRecord, lineRecord));

		final String email = assertSameAsPreviousValue(COLUMNNAME_DK_Consignee_EMail, lineRecord, previousLineRecord);
		final String phone = assertSameAsPreviousValue(COLUMNNAME_DK_Consignee_Phone, lineRecord, previousLineRecord);
		if (!Check.isEmpty(email, true) || !Check.isEmpty(phone, true))
		{
			final ContactPerson contactPerson = ContactPerson.builder()
					.emailAddress(email)
					.simplePhoneNumber(phone)
					.build();
			deliverOrderBuilder.deliveryContact(contactPerson);
		}

		final LocalDate date = TimeUtil.asLocalDate(assertSameAsPreviousValue(COLUMNNAME_DK_DesiredDeliveryDate, lineRecord, previousLineRecord));
		if (date != null)
		{
			final DeliveryDate deliveryDate = DeliveryDate.builder()
					.date(date)
					.timeFrom(TimeUtil.asLocalTime(assertSameAsPreviousValue(COLUMNNAME_DK_DesiredDeliveryTime_From, lineRecord, previousLineRecord)))
					.timeTo(TimeUtil.asLocalTime(assertSameAsPreviousValue(COLUMNNAME_DK_DesiredDeliveryTime_To, lineRecord, previousLineRecord)))
					.build();
			deliverOrderBuilder.deliveryDate(deliveryDate);
		}
	}

	public DeliveryPositionBuilder addLineRecordDataToDeliveryPositionBuilder(
			@NonNull final I_DerKurier_DeliveryOrderLine lineRecord,
			@Nullable final I_DerKurier_DeliveryOrderLine previousLineRecord)
	{
		final DeliveryPositionBuilder deliveryPositionBuilder = DeliveryPosition
				.builder()
				.repoId(lineRecord.getDerKurier_DeliveryOrderLine_ID())
				.numberOfPackages(lineRecord.getDK_PackageAmount());

		if (lineRecord.getDK_ParcelHeight().signum() > 0
				|| lineRecord.getDK_ParcelLength().signum() > 0
				|| lineRecord.getDK_ParcelWidth().signum() > 0)
		{
			final PackageDimensions packageDimensions = PackageDimensions.builder()
					.heightInCM(lineRecord.getDK_ParcelHeight().intValue())
					.lengthInCM(lineRecord.getDK_ParcelLength().intValue())
					.widthInCM(lineRecord.getDK_ParcelWidth().intValue())
					.build();
			deliveryPositionBuilder.packageDimensions(packageDimensions);
		}
		deliveryPositionBuilder.grossWeightKg(lineRecord.getDK_ParcelWeight().intValue());

		final DerKurierDeliveryData derKurierDeliveryData = DerKurierDeliveryData.builder()
				.station(assertSameAsPreviousValue(COLUMNNAME_DK_Consignee_DesiredStation, lineRecord, previousLineRecord))
				.customerNumber(assertSameAsPreviousValue(COLUMNNAME_DK_CustomerNumber, lineRecord, previousLineRecord))
				.parcelNumber(lineRecord.getDK_ParcelNumber())
				.build();
		deliveryPositionBuilder.customDeliveryData(derKurierDeliveryData);
		return deliveryPositionBuilder;
	}

	private Address createPickupAddress(@NonNull final I_DerKurier_DeliveryOrder headerRecord)
	{
		final AddressBuilder pickupAddressBuilder = Address.builder()
				.companyName1(headerRecord.getDK_Sender_Name())
				.companyName2(headerRecord.getDK_Sender_Name2())
				.companyDepartment(headerRecord.getDK_Sender_Name3());

		final IPair<String, String> street1AndStreet2 = converters
				.splitIntoStreet1AndStreet2(headerRecord.getDK_Sender_Street());

		pickupAddressBuilder
				.street1(street1AndStreet2.getLeft())
				.street2(street1AndStreet2.getRight())
				.houseNo(headerRecord.getDK_Sender_HouseNumber())
				.zipCode(headerRecord.getDK_Sender_ZipCode())
				.city(headerRecord.getDK_Sender_City());

		final CountryCode countryCode = DeliveryOrderUtil.createShipperCountryCode(headerRecord.getC_Country_ID());
		pickupAddressBuilder.country(countryCode);

		final Address pickupAddress = pickupAddressBuilder.build();
		return pickupAddress;
	}

	private PickupDate createPickupDate(@NonNull final I_DerKurier_DeliveryOrder headerRecord)
	{
		final PickupDate pickupDate = PickupDate.builder()
				.date(TimeUtil.asLocalDate(headerRecord.getDK_DesiredPickupDate()))
				.timeFrom(TimeUtil.asLocalTime(headerRecord.getDK_DesiredPickupTime_From()))
				.timeTo(TimeUtil.asLocalTime(headerRecord.getDK_DesiredPickupTime_To()))
				.build();

		return pickupDate;
	}

	private Address createDeliveryAddress(
			@Nullable final I_DerKurier_DeliveryOrderLine previousLineRecord,
			@NonNull final I_DerKurier_DeliveryOrderLine lineRecord)
	{
		final AddressBuilder deliveryAddressBuilder = Address.builder()
				.companyName1(assertSameAsPreviousValue(COLUMNNAME_DK_Consignee_Name, lineRecord, previousLineRecord))
				.companyName2(assertSameAsPreviousValue(COLUMNNAME_DK_Consignee_Name2, lineRecord, previousLineRecord))
				.companyDepartment(assertSameAsPreviousValue(COLUMNNAME_DK_Consignee_Name3, lineRecord, previousLineRecord));

		final String street = assertSameAsPreviousValue(COLUMNNAME_DK_Consignee_Street, lineRecord, previousLineRecord);
		final IPair<String, String> street1AndStreet2 = converters.splitIntoStreet1AndStreet2(street);

		deliveryAddressBuilder
				.street1(street1AndStreet2.getLeft())
				.street2(street1AndStreet2.getRight())
				.houseNo(assertSameAsPreviousValue(COLUMNNAME_DK_Consignee_HouseNumber, lineRecord, previousLineRecord))
				.zipCode(assertSameAsPreviousValue(COLUMNNAME_DK_Consignee_ZipCode, lineRecord, previousLineRecord))
				.city(assertSameAsPreviousValue(COLUMNNAME_DK_Consignee_City, lineRecord, previousLineRecord));

		final Integer countryId = assertSameAsPreviousValue(COLUMNNAME_C_Country_ID, lineRecord, previousLineRecord);
		final CountryCode countryCode = DeliveryOrderUtil.createShipperCountryCode(countryId);
		deliveryAddressBuilder.country(countryCode);

		final Address deliveryAddress = deliveryAddressBuilder.build();
		return deliveryAddress;
	}

	private <T> T assertSameAsPreviousValue(
			@NonNull final String columnName,
			@NonNull final I_DerKurier_DeliveryOrderLine currentLineRecord,
			@Nullable final I_DerKurier_DeliveryOrderLine previousLineRecord)
	{
		final T currentValue = getValueOrNull(currentLineRecord, columnName);

		if (previousLineRecord != null)
		{
			final T previousValue = getValueOrNull(previousLineRecord, columnName);
			Check.errorUnless(Objects.equal(currentValue, previousValue),
					"The {}-value of the given currentLineRecord differes from the value of the previous record; currentValue={}; previousValue={}; currentLineRecord={}; previousLineRecord={}",
					columnName, currentValue, previousValue, currentLineRecord, previousLineRecord);
		}
		return currentValue;
	}

	@Override
	public DeliveryOrder save(@NonNull final DeliveryOrder deliveryOrder)
	{
		final I_DerKurier_DeliveryOrder headerRecord;
		if (deliveryOrder.getRepoId() <= 0)
		{
			headerRecord = newInstance(I_DerKurier_DeliveryOrder.class);
		}
		else
		{
			headerRecord = loadAssumeRecordExists(deliveryOrder.getRepoId(), I_DerKurier_DeliveryOrder.class);
		}

		headerRecord.setM_Shipper_ID(deliveryOrder.getShipperId());
		headerRecord.setM_ShipperTransportation_ID(deliveryOrder.getShipperTransportationId());

		storePickupAddressInHeaderRecord(headerRecord, deliveryOrder.getPickupAddress());
		storePickupDateInHeaderRecord(headerRecord, deliveryOrder.getPickupDate());
		InterfaceWrapperHelper.save(headerRecord);

		final DeliveryOrderBuilder resultBuilder = deliveryOrder
				.toBuilder()
				.repoId(headerRecord.getDerKurier_DeliveryOrder_ID())
				.orderId(OrderId.of(SHIPPER_GATEWAY_ID, String.valueOf(headerRecord.getDerKurier_DeliveryOrder_ID())))
				.clearDeliveryPositions();

		int lineCounter = 0;
		final int lineInterval = 10;

		final List<DeliveryPosition> deliveryPositions = deliveryOrder.getDeliveryPositions();
		for (final DeliveryPosition deliveryPosition : deliveryPositions)
		{
			final DerKurierDeliveryData derKurierDeliveryData = DerKurierDeliveryData.ofDeliveryOrder(deliveryPosition);

			final PackageDimensions packageDimensions = deliveryPosition.getPackageDimensions();

			final I_DerKurier_DeliveryOrderLine lineRecord = loadOrNewInstance(deliveryPosition.getRepoId());
			lineRecord.setDerKurier_DeliveryOrder(headerRecord);

			storeDeliveryAddressInLineRecord(lineRecord, deliveryOrder.getDeliveryAddress());

			lineRecord.setDK_Consignee_DesiredStation(derKurierDeliveryData.getStation());

			final ContactPerson deliveryContact = deliveryOrder.getDeliveryContact();
			if (deliveryContact != null)
			{
				lineRecord.setDK_Consignee_EMail(deliveryContact.getEmailAddress());
				lineRecord.setDK_Consignee_Phone(deliveryContact.getPhoneAsStringOrNull());
			}

			lineRecord.setDK_CustomerNumber(derKurierDeliveryData.getCustomerNumber());

			final DeliveryDate deliveryDate = deliveryOrder.getDeliveryDate();
			if (deliveryDate != null)
			{
				lineRecord.setDK_DesiredDeliveryDate(TimeUtil.asTimestamp(deliveryDate.getDate()));
				lineRecord.setDK_DesiredDeliveryTime_From(TimeUtil.asTimestamp(deliveryDate.getDate(), deliveryDate.getTimeFrom()));
				lineRecord.setDK_DesiredDeliveryTime_To(TimeUtil.asTimestamp(deliveryDate.getDate(), deliveryDate.getTimeTo()));
			}
			lineRecord.setDK_PackageAmount(deliveryPosition.getNumberOfPackages());

			if (packageDimensions != null)
			{
				lineRecord.setDK_ParcelHeight(BigDecimal.valueOf(packageDimensions.getHeightInCM()));
				lineRecord.setDK_ParcelLength(BigDecimal.valueOf(packageDimensions.getLengthInCM()));
				lineRecord.setDK_ParcelWidth(BigDecimal.valueOf(packageDimensions.getWidthInCM()));
			}
			lineRecord.setDK_ParcelWeight(BigDecimal.valueOf(deliveryPosition.getGrossWeightKg()));

			lineRecord.setDK_ParcelNumber(derKurierDeliveryData.getParcelNumber());

			lineRecord.setDK_Reference(deliveryOrder.getCustomerReference());

			lineCounter += lineInterval;
			lineRecord.setLine(lineCounter);

			InterfaceWrapperHelper.save(lineRecord);

			syncPackageAllocationRecords(lineRecord, deliveryPosition.getPackageIds());

			resultBuilder.deliveryPosition(deliveryPosition
					.toBuilder()
					.repoId(lineRecord.getDerKurier_DeliveryOrderLine_ID())
					.build());
		}
		return resultBuilder.build();
	}

	private void storePickupAddressInHeaderRecord(
			@NonNull final I_DerKurier_DeliveryOrder headerRecord,
			@NonNull final Address pickupAddress)
	{
		headerRecord.setDK_Sender_Street(converters.joinStreet1AndStreet2(pickupAddress));
		headerRecord.setDK_Sender_HouseNumber(pickupAddress.getHouseNo());
		headerRecord.setDK_Sender_ZipCode(pickupAddress.getZipCode());
		headerRecord.setDK_Sender_City(pickupAddress.getCity());

		final String pickupCountryCode2 = pickupAddress.getCountry().getAlpha2();
		final I_C_Country pickupCountry = Services.get(ICountryDAO.class).retrieveCountryByCountryCode(pickupCountryCode2);
		headerRecord.setC_Country(pickupCountry);
		headerRecord.setDK_Sender_Country(pickupCountryCode2);

		headerRecord.setDK_Sender_Name(pickupAddress.getCompanyName1());
		headerRecord.setDK_Sender_Name2(pickupAddress.getCompanyName2());
		headerRecord.setDK_Sender_Name3(pickupAddress.getCompanyDepartment());
	}

	private void storeDeliveryAddressInLineRecord(
			@NonNull final I_DerKurier_DeliveryOrderLine lineRecord,
			@NonNull final Address deliveryAddress)
	{
		lineRecord.setDK_Consignee_Street(converters.joinStreet1AndStreet2(deliveryAddress));
		lineRecord.setDK_Consignee_HouseNumber(deliveryAddress.getHouseNo());
		lineRecord.setDK_Consignee_ZipCode(deliveryAddress.getZipCode());
		lineRecord.setDK_Consignee_City(deliveryAddress.getCity());

		final String pickupCountryCode2 = deliveryAddress.getCountry().getAlpha2();
		final I_C_Country pickupCountry = Services.get(ICountryDAO.class).retrieveCountryByCountryCode(pickupCountryCode2);
		lineRecord.setC_Country(pickupCountry);
		lineRecord.setDK_Consignee_Country(pickupCountryCode2);

		lineRecord.setDK_Consignee_Name(deliveryAddress.getCompanyName1());
		lineRecord.setDK_Consignee_Name2(deliveryAddress.getCompanyName2());
		lineRecord.setDK_Consignee_Name3(deliveryAddress.getCompanyDepartment());
	}

	private void storePickupDateInHeaderRecord(
			@NonNull final I_DerKurier_DeliveryOrder headerRecord,
			@NonNull final PickupDate pickupDate)
	{
		headerRecord.setDK_DesiredPickupDate(TimeUtil.asTimestamp(pickupDate.getDate()));

		if (pickupDate.getTimeFrom() == null)
		{
			headerRecord.setDK_DesiredPickupTime_From(null);
		}
		else
		{
			headerRecord.setDK_DesiredPickupTime_From(TimeUtil.asTimestamp(pickupDate.getDate(), pickupDate.getTimeFrom()));
		}

		if (pickupDate.getTimeTo() == null)
		{
			headerRecord.setDK_DesiredPickupTime_To(null);
		}
		else
		{
			headerRecord.setDK_DesiredPickupTime_To(TimeUtil.asTimestamp(pickupDate.getDate(), pickupDate.getTimeTo()));
		}
	}

	private void syncPackageAllocationRecords(
			@NonNull final I_DerKurier_DeliveryOrderLine lineRecord,
			@NonNull final ImmutableSet<Integer> packageIds)
	{
		final List<I_DerKurier_DeliveryOrderLine_Package> preExistingRecords = Services.get(IQueryBL.class)
				.createQueryBuilder(I_DerKurier_DeliveryOrderLine_Package.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_DerKurier_DeliveryOrderLine_Package.COLUMN_DerKurier_DeliveryOrderLine_ID, lineRecord.getDerKurier_DeliveryOrderLine_ID())
				.create()
				.list();
		final ImmutableListMultimap<Integer, I_DerKurier_DeliveryOrderLine_Package> packageId2preExistingRecord = //
				Multimaps.index(preExistingRecords, I_DerKurier_DeliveryOrderLine_Package::getM_Package_ID);

		// create missing records
		for (final int packageId : packageIds)
		{
			final boolean recordForPackageIdExists = packageId2preExistingRecord.containsKey(packageId);
			if (recordForPackageIdExists)
			{
				continue;
			}
			final I_DerKurier_DeliveryOrderLine_Package newRecord = newInstance(I_DerKurier_DeliveryOrderLine_Package.class);
			newRecord.setDerKurier_DeliveryOrderLine(lineRecord);
			newRecord.setM_Package_ID(packageId);
			InterfaceWrapperHelper.save(newRecord);
		}

		// delete surplus records
		for (final int preExistingRecordPackageId : packageId2preExistingRecord.keySet())
		{
			final boolean preExistingRecordCanBeKept = packageIds.contains(preExistingRecordPackageId);
			if (preExistingRecordCanBeKept)
			{
				continue;
			}
			packageId2preExistingRecord
					.get(preExistingRecordPackageId)
					.forEach(InterfaceWrapperHelper::delete);
		}
	}

	private I_DerKurier_DeliveryOrderLine loadOrNewInstance(final int deliveryOrderLineRepoId)
	{
		if (deliveryOrderLineRepoId <= 0)
		{
			return newInstance(I_DerKurier_DeliveryOrderLine.class);
		}
		return loadAssumeRecordExists(deliveryOrderLineRepoId, I_DerKurier_DeliveryOrderLine.class);
	}

	private <T> T loadAssumeRecordExists(
			final int repoId,
			@NonNull final Class<T> modelClass)
	{
		Check.assume(repoId > 0, "deliveryOrderRepoId > 0");
		final T orderPO = InterfaceWrapperHelper.load(repoId, modelClass);
		Check.assumeNotNull(orderPO, "A data recordfor modelClass={} and ID={}", modelClass.getSimpleName(), repoId);

		return orderPO;
	}

	public AttachmentEntry attachCsvToDeliveryOrder(
			final int repoId,
			@NonNull final List<String> csv)
	{
		final I_DerKurier_DeliveryOrder record = load(repoId, I_DerKurier_DeliveryOrder.class);

		// thx to https://stackoverflow.com/a/5619144/1012103
		final ByteArrayOutputStream baos = new ByteArrayOutputStream();
		final DataOutputStream out = new DataOutputStream(baos);
		for (final String csvLine : csv)
		{
			writeLineToStream(csvLine, out);
		}
		return Services.get(IAttachmentBL.class).addEntry(record, "CSV-Daten", baos.toByteArray());
	}

	public void writeLineToStream(
			@NonNull final String csvLine,
			@NonNull final DataOutputStream dataOutputStream)
	{
		try
		{
			dataOutputStream.writeUTF(csvLine);
			dataOutputStream.writeUTF("\n");
		}
		catch (final IOException e)
		{
			throw new AdempiereException("IOException writing clsLine to dataOutputStream", e).appendParametersToMessage()
					.setParameter("csvLine", csvLine)
					.setParameter("dataOutputStream", dataOutputStream);
		}
	}

}
