package de.metas.shipper.gateway.go;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_C_Country;
import org.compiere.model.I_C_Location;
import org.compiere.util.TimeUtil;

import de.metas.adempiere.service.ICountryDAO;
import de.metas.shipper.gateway.commons.DeliveryOrderUtil;
import de.metas.shipper.gateway.go.model.I_GO_DeliveryOrder;
import de.metas.shipper.gateway.spi.model.Address;
import de.metas.shipper.gateway.spi.model.ContactPerson;
import de.metas.shipper.gateway.spi.model.DeliveryDate;
import de.metas.shipper.gateway.spi.model.DeliveryOrder;
import de.metas.shipper.gateway.spi.model.DeliveryPosition;
import de.metas.shipper.gateway.spi.model.PhoneNumber;
import de.metas.shipper.gateway.spi.model.PickupDate;
import lombok.experimental.UtilityClass;

/*
 * #%L
 * de.metas.shipper.gateway.go
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

/**
 * Helper methods used to convert {@link I_GO_DeliveryOrder} to/from {@link DeliveryOrder}.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@UtilityClass
/* package */ class GODeliveryOrderConverters
{
	public static Address pickupAddressFromPO(final I_GO_DeliveryOrder orderPO)
	{
		return DeliveryOrderUtil.prepareAddressFromLocation(orderPO.getGO_PickupLocation())
				.companyName1(orderPO.getGO_PickupCompanyName())
				.companyName2(null)
				.build();
	}

	public static void pickupAddressToPO(final I_GO_DeliveryOrder orderPO, final Address pickupAddress)
	{
		orderPO.setGO_PickupCompanyName(pickupAddress.getCompanyName1());
		orderPO.setGO_PickupLocation_ID(createC_Location_ID(pickupAddress));
	}

	public static Address deliveryAddressFromPO(final I_GO_DeliveryOrder orderPO)
	{
		return DeliveryOrderUtil.prepareAddressFromLocation(orderPO.getGO_DeliverToLocation())
				.companyName1(orderPO.getGO_DeliverToCompanyName())
				.companyName2(orderPO.getGO_DeliverToCompanyName2())
				.companyDepartment(orderPO.getGO_DeliverToDepartment())
				.bpartnerId(orderPO.getGO_DeliverToBPartner_ID())
				.bpartnerLocationId(orderPO.getGO_DeliverToBPLocation_ID())
				.build();
	}

	public static void deliveryAddressToPO(final I_GO_DeliveryOrder orderPO, final Address deliveryAddress)
	{
		orderPO.setGO_DeliverToCompanyName(deliveryAddress.getCompanyName1());
		orderPO.setGO_DeliverToCompanyName2(deliveryAddress.getCompanyName2());
		orderPO.setGO_DeliverToDepartment(deliveryAddress.getCompanyDepartment());
		orderPO.setGO_DeliverToLocation_ID(createC_Location_ID(deliveryAddress));

		orderPO.setGO_DeliverToBPartner_ID(deliveryAddress.getBpartnerId());
		orderPO.setGO_DeliverToBPLocation_ID(deliveryAddress.getBpartnerLocationId());
	}

	public static PickupDate pickupDateFromPO(final I_GO_DeliveryOrder orderPO)
	{
		return PickupDate.builder()
				.date(TimeUtil.asLocalDate(orderPO.getGO_PickupDate()))
				.timeFrom(TimeUtil.asLocalTime(orderPO.getGO_PickupTimeFrom()))
				.timeTo(TimeUtil.asLocalTime(orderPO.getGO_PickupTimeTo()))
				.build();
	}

	public static void pickupDateToPO(final I_GO_DeliveryOrder orderPO, final PickupDate pickupDate)
	{
		orderPO.setGO_PickupDate(TimeUtil.asTimestamp(pickupDate.getDate()));
		orderPO.setGO_PickupTimeFrom(TimeUtil.asTimestamp(pickupDate.getDateTimeFrom()));
		orderPO.setGO_PickupTimeTo(TimeUtil.asTimestamp(pickupDate.getDateTimeTo()));
	}

	public static DeliveryDate deliveryDateFromPO(final I_GO_DeliveryOrder orderPO)
	{
		final Timestamp date = orderPO.getGO_DeliverToDate();
		if (date == null)
		{
			return null;
		}
		return DeliveryDate.builder()
				.date(TimeUtil.asLocalDate(date))
				.timeFrom(TimeUtil.asLocalTime(orderPO.getGO_DeliverToTimeFrom()))
				.timeTo(TimeUtil.asLocalTime(orderPO.getGO_DeliverToTimeTo()))
				.build();
	}

	public static void deliveryDateToPO(final I_GO_DeliveryOrder orderPO, final DeliveryDate deliveryDate)
	{
		final LocalDate date = deliveryDate != null ? deliveryDate.getDate() : null;
		final LocalDateTime timeFrom = deliveryDate != null ? deliveryDate.getDateTimeFrom() : null;
		final LocalDateTime timeTo = deliveryDate != null ? deliveryDate.getDateTimeTo() : null;
		orderPO.setGO_DeliverToDate(TimeUtil.asTimestamp(date));
		orderPO.setGO_DeliverToTimeFrom(TimeUtil.asTimestamp(timeFrom));
		orderPO.setGO_DeliverToTimeTo(TimeUtil.asTimestamp(timeTo));
	}

	public static ContactPerson deliveryContactFromPO(final I_GO_DeliveryOrder orderPO)
	{
		final String phoneNo = orderPO.getGO_DeliverToPhoneNo();
		if (Check.isEmpty(phoneNo, true))
		{
			return null;
		}

		return ContactPerson.builder()
				.phone(PhoneNumber.fromString(phoneNo))
				.build();
	}

	public static void deliveryContactToPO(final I_GO_DeliveryOrder orderPO, final ContactPerson deliveryContact)
	{
		orderPO.setGO_DeliverToPhoneNo(deliveryContact != null ? deliveryContact.getPhoneAsStringOrNull() : null);
	}

	public static DeliveryPosition deliveryPositionFromPO(final I_GO_DeliveryOrder orderPO, final Set<Integer> mpackageIds)
	{
		return DeliveryPosition.builder()
				.numberOfPackages(orderPO.getGO_NumberOfPackages())
				.packageIds(mpackageIds)
				.grossWeightKg(orderPO.getGO_GrossWeightKg())
				.content(orderPO.getGO_PackageContentDescription())
				.build();
	}

	public static void deliveryPositionToPO(final I_GO_DeliveryOrder orderPO, final DeliveryPosition deliveryPosition)
	{
		orderPO.setGO_NumberOfPackages(deliveryPosition.getNumberOfPackages());
		orderPO.setGO_GrossWeightKg(deliveryPosition.getGrossWeightKg());
		orderPO.setGO_PackageContentDescription(deliveryPosition.getContent());
	}

	// ------------
	private static int createC_Location_ID(final Address address)
	{
		final I_C_Location locationPO = InterfaceWrapperHelper.newInstanceOutOfTrx(I_C_Location.class);
		locationPO.setAddress1(address.getStreet1());
		locationPO.setAddress2(address.getStreet2());
		locationPO.setAddress3(address.getHouseNo());
		locationPO.setPostal(address.getZipCode());
		locationPO.setCity(address.getCity());

		final String countryCode2 = address.getCountry().getAlpha2();
		final I_C_Country country = Services.get(ICountryDAO.class).retrieveCountryByCountryCode(countryCode2);
		locationPO.setC_Country(country);

		InterfaceWrapperHelper.save(locationPO);

		return locationPO.getC_Location_ID();
	}
}
