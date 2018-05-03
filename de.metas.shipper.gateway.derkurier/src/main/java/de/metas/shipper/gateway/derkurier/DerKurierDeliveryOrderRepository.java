package de.metas.shipper.gateway.derkurier;

import static de.metas.shipper.gateway.derkurier.model.I_DerKurier_DeliveryOrderLine.COLUMNNAME_DK_Consignee_City;
import static de.metas.shipper.gateway.derkurier.model.I_DerKurier_DeliveryOrderLine.COLUMNNAME_DK_Consignee_Country;
import static de.metas.shipper.gateway.derkurier.model.I_DerKurier_DeliveryOrderLine.COLUMNNAME_DK_Consignee_DesiredStation;
import static de.metas.shipper.gateway.derkurier.model.I_DerKurier_DeliveryOrderLine.COLUMNNAME_DK_Consignee_EMail;
import static de.metas.shipper.gateway.derkurier.model.I_DerKurier_DeliveryOrderLine.COLUMNNAME_DK_Consignee_Name;
import static de.metas.shipper.gateway.derkurier.model.I_DerKurier_DeliveryOrderLine.COLUMNNAME_DK_Consignee_Name2;
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
import java.util.List;

import javax.annotation.Nullable;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.ITableRecordReference;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.util.TimeUtil;
import org.compiere.util.Util;
import org.springframework.stereotype.Repository;

import com.google.common.base.Objects;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Multimaps;

import de.metas.attachments.IAttachmentBL;
import de.metas.shipper.gateway.derkurier.DerKurierDeliveryData.DerKurierDeliveryDataBuilder;
import de.metas.shipper.gateway.derkurier.misc.DerKurierShipperConfigRepository;
import de.metas.shipper.gateway.derkurier.misc.ParcelNumberGenerator;
import de.metas.shipper.gateway.derkurier.model.I_DerKurier_DeliveryOrder;
import de.metas.shipper.gateway.derkurier.model.I_DerKurier_DeliveryOrderLine;
import de.metas.shipper.gateway.derkurier.model.I_DerKurier_DeliveryOrderLine_Package;
import de.metas.shipper.gateway.spi.DeliveryOrderRepository;
import de.metas.shipper.gateway.spi.model.Address;
import de.metas.shipper.gateway.spi.model.Address.AddressBuilder;
import de.metas.shipper.gateway.spi.model.ContactPerson;
import de.metas.shipper.gateway.spi.model.ContactPerson.ContactPersonBuilder;
import de.metas.shipper.gateway.spi.model.CountryCode;
import de.metas.shipper.gateway.spi.model.CountryCode.CountryCodeBuilder;
import de.metas.shipper.gateway.spi.model.DeliveryDate;
import de.metas.shipper.gateway.spi.model.DeliveryDate.DeliveryDateBuilder;
import de.metas.shipper.gateway.spi.model.DeliveryOrder;
import de.metas.shipper.gateway.spi.model.DeliveryOrder.DeliveryOrderBuilder;
import de.metas.shipper.gateway.spi.model.DeliveryPosition;
import de.metas.shipper.gateway.spi.model.DeliveryPosition.DeliveryPositionBuilder;
import de.metas.shipper.gateway.spi.model.PackageDimensions;
import de.metas.shipper.gateway.spi.model.PackageDimensions.PackageDimensionsBuilder;
import de.metas.shipper.gateway.spi.model.PhoneNumber;
import de.metas.shipper.gateway.spi.model.PhoneNumber.PhoneNumberBuilder;
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
	private static final String STREET_DELIMITER = " - ";

	private final DerKurierShipperConfigRepository derKurierShipperConfigRepository;

	public DerKurierDeliveryOrderRepository(final DerKurierShipperConfigRepository derKurierShipperConfigRepository)
	{
		this.derKurierShipperConfigRepository = derKurierShipperConfigRepository;

	}

	@Override
	public String getShipperGatewayId()
	{
		return DerKurierConstants.SHIPPER_GATEWAY_ID;
	}

	@Override
	public ITableRecordReference toTableRecordReference(@NonNull final DeliveryOrder deliveryOrder)
	{
		return TableRecordReference.of(I_DerKurier_DeliveryOrder.Table_Name, deliveryOrder.getRepoId());
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

		final DeliveryOrderBuilder deliverOrderBuilder = DeliveryOrder.builder();

		I_DerKurier_DeliveryOrderLine previousLineRecord = null; // used to make sure that all lineRecords have the same value when it comes to certain columns
		for (final I_DerKurier_DeliveryOrderLine lineRecord : lineRecords)
		{
			final AddressBuilder deliveryAddressBuilder = Address.builder();
			deliveryAddressBuilder.city(assertSameAsPreviousValue(COLUMNNAME_DK_Consignee_City, lineRecord, previousLineRecord));
			final CountryCodeBuilder countryCodeBuilder = CountryCode.builder();
			countryCodeBuilder.alpha2(assertSameAsPreviousValue(COLUMNNAME_DK_Consignee_Country, lineRecord, previousLineRecord));
			deliveryAddressBuilder.country(countryCodeBuilder.build());

			final DerKurierDeliveryDataBuilder derKurierDeliveryDataBuilder = DerKurierDeliveryData.builder()
					.station(assertSameAsPreviousValue(COLUMNNAME_DK_Consignee_DesiredStation, lineRecord, previousLineRecord));

			final ContactPersonBuilder contactPersonBuilder = ContactPerson.builder();
			contactPersonBuilder.emailAddress(assertSameAsPreviousValue(COLUMNNAME_DK_Consignee_EMail, lineRecord, previousLineRecord));

			deliveryAddressBuilder.companyName1(assertSameAsPreviousValue(COLUMNNAME_DK_Consignee_Name, lineRecord, previousLineRecord));
			deliveryAddressBuilder.companyName2(assertSameAsPreviousValue(COLUMNNAME_DK_Consignee_Name2, lineRecord, previousLineRecord));
			// lineRecord.setDK_Consignee_Name3();

			final PhoneNumberBuilder phoneNumberBuilder = PhoneNumber.builder();
			phoneNumberBuilder.phoneNumber(assertSameAsPreviousValue(COLUMNNAME_DK_Consignee_Phone, lineRecord, previousLineRecord));
			contactPersonBuilder.phone(phoneNumberBuilder.build());
			deliverOrderBuilder.deliveryContact(contactPersonBuilder.build());

			final String street = assertSameAsPreviousValue(COLUMNNAME_DK_Consignee_Street, lineRecord, previousLineRecord);
			final String[] streets = street.split(STREET_DELIMITER);
			if (streets.length > 0)
			{
				deliveryAddressBuilder.street1(streets[0]);
			} ;
			if (streets.length > 1)
			{
				deliveryAddressBuilder.street2(streets[1]);
			}
			deliveryAddressBuilder.zipCode(assertSameAsPreviousValue(COLUMNNAME_DK_Consignee_ZipCode, lineRecord, previousLineRecord));

			deliverOrderBuilder.deliveryAddress(deliveryAddressBuilder.build());

			derKurierDeliveryDataBuilder.customerNumber(assertSameAsPreviousValue(COLUMNNAME_DK_CustomerNumber, lineRecord, previousLineRecord));

			final DeliveryDateBuilder deliveryDateBuilder = DeliveryDate.builder();
			deliveryDateBuilder.date(TimeUtil.asLocalDate(assertSameAsPreviousValue(COLUMNNAME_DK_DesiredDeliveryDate, lineRecord, previousLineRecord)));
			deliveryDateBuilder.timeFrom(TimeUtil.asLocalTime(assertSameAsPreviousValue(COLUMNNAME_DK_DesiredDeliveryTime_From, lineRecord, previousLineRecord)));
			deliveryDateBuilder.timeTo(TimeUtil.asLocalTime(assertSameAsPreviousValue(COLUMNNAME_DK_DesiredDeliveryTime_To, lineRecord, previousLineRecord)));
			deliverOrderBuilder.deliveryDate(deliveryDateBuilder.build());

			final DeliveryPositionBuilder deliveryPositionBuilder = DeliveryPosition.builder();
			deliveryPositionBuilder.numberOfPackages(lineRecord.getDK_PackageAmount());

			final PackageDimensionsBuilder packageDimensionsBuilder = PackageDimensions.builder()
					.heightInCM(lineRecord.getDK_ParcelHeight().intValue())
					.lengthInCM(lineRecord.getDK_ParcelLength().intValue());

			derKurierDeliveryDataBuilder.parcelNumber(lineRecord.getDK_ParcelNumber());
			deliveryPositionBuilder.customDeliveryData(derKurierDeliveryDataBuilder.build());

			deliveryPositionBuilder.grossWeightKg(lineRecord.getDK_ParcelWeight().intValue());

			packageDimensionsBuilder.widthInCM(lineRecord.getDK_ParcelWidth().intValue());
			deliveryPositionBuilder.packageDimensions(packageDimensionsBuilder.build());

			// add the M_Package_IDs to the current deliveryPositionBuilder
			lineId2PackageRecords
					.get(lineRecord.getDerKurier_DeliveryOrderLine_ID())
					.stream()
					.map(I_DerKurier_DeliveryOrderLine_Package::getM_Package_ID)
					.forEach(deliveryPositionBuilder::packageId);

			deliverOrderBuilder.deliveryPosition(deliveryPositionBuilder.build());

			deliverOrderBuilder.customerReference(assertSameAsPreviousValue(COLUMNNAME_DK_Reference, lineRecord, previousLineRecord));

			previousLineRecord = lineRecord;
		}

		return deliverOrderBuilder.build();
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
			InterfaceWrapperHelper.save(headerRecord);
		}
		else
		{
			headerRecord = loadAssumeRecordExists(deliveryOrder.getRepoId(), I_DerKurier_DeliveryOrder.class);
		}

		final DeliveryOrderBuilder resultBuilder = deliveryOrder
				.toBuilder()
				.repoId(headerRecord.getDerKurier_DeliveryOrder_ID())
				.clearDeliveryPositions();

		final ParcelNumberGenerator parcelNumberGenerator = derKurierShipperConfigRepository
				.retrieveConfigForShipperId(deliveryOrder.getShipperId())
				.getParcelNumberGenerator();

		int lineCounter = 0;
		final int lineInterval = 10;

		final List<DeliveryPosition> deliveryPositions = deliveryOrder.getDeliveryPositions();
		for (final DeliveryPosition deliveryPosition : deliveryPositions)
		{
			final DerKurierDeliveryData derKurierDeliveryData = DerKurierDeliveryData.ofDeliveryOrder(deliveryPosition);

			final I_DerKurier_DeliveryOrderLine lineRecord = loadOrNewInstance(deliveryPosition.getRepoId());
			lineRecord.setDerKurier_DeliveryOrder(headerRecord);

			final Address deliveryAddress = deliveryOrder.getDeliveryAddress();
			lineRecord.setDK_Consignee_City(deliveryAddress.getCity());
			lineRecord.setDK_Consignee_Country(deliveryAddress.getCountry().getAlpha2());
			lineRecord.setDK_Consignee_DesiredStation(derKurierDeliveryData.getStation());
			lineRecord.setDK_Consignee_EMail(deliveryOrder.getDeliveryContact().getEmailAddress());
			lineRecord.setDK_Consignee_HouseNumber(deliveryAddress.getHouseNo());
			lineRecord.setDK_Consignee_Name(deliveryAddress.getCompanyName1());
			lineRecord.setDK_Consignee_Name2(deliveryAddress.getCompanyName2());
			// lineRecord.setDK_Consignee_Name3();
			lineRecord.setDK_Consignee_Phone(deliveryOrder.getDeliveryContact().getPhoneAsString());
			lineRecord.setDK_Consignee_Street(String.join(STREET_DELIMITER,
					deliveryAddress.getStreet1(),
					deliveryAddress.getStreet2()));
			lineRecord.setDK_Consignee_ZipCode(deliveryAddress.getZipCode());

			lineRecord.setDK_CustomerNumber(derKurierDeliveryData.getCustomerNumber());

			final DeliveryDate deliveryDate = deliveryOrder.getDeliveryDate();
			lineRecord.setDK_DesiredDeliveryDate(TimeUtil.asTimestamp(deliveryDate.getDate()));
			lineRecord.setDK_DesiredDeliveryTime_From(TimeUtil.asTimestamp(deliveryDate.getDate(), deliveryDate.getTimeFrom()));
			lineRecord.setDK_DesiredDeliveryTime_To(TimeUtil.asTimestamp(deliveryDate.getDate(), deliveryDate.getTimeTo()));
			lineRecord.setDK_PackageAmount(deliveryPosition.getNumberOfPackages());
			lineRecord.setDK_ParcelHeight(BigDecimal.valueOf(deliveryPosition.getPackageDimensions().getHeightInCM()));
			lineRecord.setDK_ParcelLength(BigDecimal.valueOf(deliveryPosition.getPackageDimensions().getLengthInCM()));

			lineRecord.setDK_ParcelNumber(
					Util.coalesceSuppliers(
							() -> derKurierDeliveryData,
							() -> parcelNumberGenerator.getNextParcelNumber())
							.toString());

			lineRecord.setDK_ParcelWeight(BigDecimal.valueOf(deliveryPosition.getGrossWeightKg()));
			lineRecord.setDK_ParcelWidth(BigDecimal.valueOf(deliveryPosition.getPackageDimensions().getWidthInCM()));
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

	public void attachCsvToDeliveryOrder(
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
		Services.get(IAttachmentBL.class).addEntry(record, "CSV-Daten", baos.toByteArray());
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
