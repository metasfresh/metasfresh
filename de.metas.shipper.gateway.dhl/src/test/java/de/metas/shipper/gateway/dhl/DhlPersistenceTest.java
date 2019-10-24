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

import de.metas.shipper.gateway.dhl.model.DhlClientConfigRepository;
import de.metas.shipper.gateway.dhl.model.DhlServiceType;
import de.metas.shipper.gateway.spi.model.Address;
import de.metas.shipper.gateway.spi.model.DeliveryOrder;
import de.metas.shipper.gateway.spi.model.DeliveryPosition;
import de.metas.shipper.gateway.spi.model.PackageDimensions;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Country;
import org.compiere.model.I_C_Location;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Set;

class DhlPersistenceTest
{
	private DhlDraftDeliveryOrderCreator draftDeliveryOrderCreator;

	@BeforeEach
	void setUp()
	{
		AdempiereTestHelper.get().init();

		final DhlClientConfigRepository clientConfigRepository = new DhlClientConfigRepository();
		draftDeliveryOrderCreator = new DhlDraftDeliveryOrderCreator(clientConfigRepository);
	}

	@Test
	void DEtoDEDraftDeliveryOrderCreator()
	{
		final DeliveryOrder deliveryOrder = DhlTestHelper.createDummyDeliveryOrderDEtoDE();
		final DeliveryPosition deliveryPosition = deliveryOrder.getDeliveryPositions().get(0);

		//
		final Set<Integer> mpackageIds = deliveryPosition.getPackageIds();

		//
		final I_C_BPartner pickupFromBPartner = createBPartner(deliveryOrder.getPickupAddress());
		final I_C_Location pickupFromLocation = createLocation(deliveryOrder.getPickupAddress());
		final LocalDate pickupDate = deliveryOrder.getPickupDate().getDate();

		//
		final I_C_BPartner deliverToBPartner = createBPartner(deliveryOrder.getDeliveryAddress());
		//noinspection ConstantConditions
		deliverToBPartner.setEMail(deliveryOrder.getDeliveryContact().getEmailAddress());
		final I_C_Location deliverToLocation = createLocation(deliveryOrder.getDeliveryAddress());
		final int deliverToBPartnerLocationId = 0;
		final String deliverToPhoneNumber = deliveryOrder.getDeliveryContact().getSimplePhoneNumber();

		//
		final DhlServiceType detectedServiceType = (DhlServiceType)deliveryOrder.getServiceType();
		final int grossWeightInKg = deliveryPosition.getGrossWeightKg();
		final int shipperId = deliveryOrder.getShipperId();
		final int shipperTransportationId = deliveryOrder.getShipperTransportationId();
		final PackageDimensions packageDimensions = deliveryPosition.getPackageDimensions();

		//noinspection ConstantConditions
		final DeliveryOrder newDeliveryOrder = draftDeliveryOrderCreator.createDeliveryOrderFromParams(
				mpackageIds,
				pickupFromBPartner,
				pickupFromLocation,
				pickupDate,
				deliverToBPartner,
				deliverToBPartnerLocationId,
				deliverToLocation,
				deliverToPhoneNumber,
				detectedServiceType,
				grossWeightInKg,
				shipperId,
				shipperTransportationId,
				packageDimensions);

		Assertions.assertEquals(deliveryOrder, newDeliveryOrder);
	}

	@Test
	void DEtoCHDraftDeliveryOrderCreator()
	{
		final DeliveryOrder deliveryOrder = DhlTestHelper.createDummyDeliveryOrderDEtoCH();
		final DeliveryPosition deliveryPosition = deliveryOrder.getDeliveryPositions().get(0);

		//
		final Set<Integer> mpackageIds = deliveryPosition.getPackageIds();

		//
		final I_C_BPartner pickupFromBPartner = createBPartner(deliveryOrder.getPickupAddress());
		final I_C_Location pickupFromLocation = createLocation(deliveryOrder.getPickupAddress());
		final LocalDate pickupDate = deliveryOrder.getPickupDate().getDate();

		//
		final I_C_BPartner deliverToBPartner = createBPartner(deliveryOrder.getDeliveryAddress());
		//noinspection ConstantConditions
		deliverToBPartner.setEMail(deliveryOrder.getDeliveryContact().getEmailAddress());
		final I_C_Location deliverToLocation = createLocation(deliveryOrder.getDeliveryAddress());
		final int deliverToBPartnerLocationId = 0;
		final String deliverToPhoneNumber = deliveryOrder.getDeliveryContact().getSimplePhoneNumber();

		//
		final DhlServiceType detectedServiceType = (DhlServiceType)deliveryOrder.getServiceType();
		final int grossWeightInKg = deliveryPosition.getGrossWeightKg();
		final int shipperId = deliveryOrder.getShipperId();
		final int shipperTransportationId = deliveryOrder.getShipperTransportationId();
		final PackageDimensions packageDimensions = deliveryPosition.getPackageDimensions();

		//noinspection ConstantConditions
		final DeliveryOrder newDeliveryOrder = draftDeliveryOrderCreator.createDeliveryOrderFromParams(
				mpackageIds,
				pickupFromBPartner,
				pickupFromLocation,
				pickupDate,
				deliverToBPartner,
				deliverToBPartnerLocationId,
				deliverToLocation,
				deliverToPhoneNumber,
				detectedServiceType,
				grossWeightInKg,
				shipperId,
				shipperTransportationId,
				packageDimensions);

		Assertions.assertEquals(deliveryOrder, newDeliveryOrder);
	}


	@NonNull
	private I_C_Location createLocation(final Address pickupAddress)
	{
		final I_C_Location pickupFromLocation = InterfaceWrapperHelper.newInstance(I_C_Location.class);
		pickupFromLocation.setAddress1(pickupAddress.getStreet1() + " " + pickupAddress.getHouseNo());
		pickupFromLocation.setAddress2(pickupAddress.getStreet2());
		pickupFromLocation.setPostal(pickupAddress.getZipCode());
		pickupFromLocation.setCity(pickupAddress.getCity());
		final I_C_Country i_c_country = InterfaceWrapperHelper.newInstance(I_C_Country.class);
		i_c_country.setCountryCode(pickupAddress.getCountry().getAlpha2());
		InterfaceWrapperHelper.save(i_c_country);
		pickupFromLocation.setC_Country(i_c_country);

		return pickupFromLocation;
	}

	@NonNull
	private I_C_BPartner createBPartner(final Address pickupAddress)
	{
		final I_C_BPartner pickupFromBPartner = InterfaceWrapperHelper.newInstance(I_C_BPartner.class);
		pickupFromBPartner.setName(pickupAddress.getCompanyName1());
		pickupFromBPartner.setName2(pickupAddress.getCompanyName2());
		return pickupFromBPartner;
	}

}
