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
import de.metas.shipper.gateway.dpd.model.DpdOrderCustomDeliveryData;
import de.metas.shipper.gateway.spi.ShipperTestHelper;
import de.metas.shipper.gateway.spi.model.CustomDeliveryData;
import de.metas.shipper.gateway.spi.model.DeliveryOrder;
import de.metas.shipper.gateway.spi.model.DeliveryOrderLine;
import de.metas.shipper.gateway.spi.model.PickupDate;
import de.metas.shipper.gateway.spi.model.ServiceType;
import de.metas.shipping.ShipperId;
import de.metas.shipping.api.ShipperTransportationId;
import lombok.NonNull;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Location;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.Assert.assertEquals;

@Disabled("Makes ACTUAL calls to dpd api and needs auth")
public class IntegrationDEtoDETest
{
	DpdDraftDeliveryOrderCreator draftDeliveryOrderCreator = new DpdDraftDeliveryOrderCreator();

	@BeforeEach
	void setUp()
	{
		AdempiereTestHelper.get().init();

	}

	@Test
	@DisplayName("Delivery Order DE -> DE + test persistence after all steps")
	void DEtoDEDraftDeliveryOrderCreatorAndPersistence()
	{
		final DeliveryOrder initialDummyDeliveryOrder = DpdTestHelper.createDummyDeliveryOrderDEtoDE();
		final DeliveryOrder draftDeliveryOrder = createDraftDeliveryOrderFromDummy(initialDummyDeliveryOrder);
		assertEquals("nothing should be changed", initialDummyDeliveryOrder, draftDeliveryOrder);

	}

	@NonNull
	private DeliveryOrder createDraftDeliveryOrderFromDummy(@NonNull final DeliveryOrder deliveryOrder)
	{
		//
		final I_C_BPartner pickupFromBPartner = ShipperTestHelper.createBPartner(deliveryOrder.getPickupAddress());
		final I_C_Location pickupFromLocation = ShipperTestHelper.createLocation(deliveryOrder.getPickupAddress());
		final PickupDate pickupDate1 = deliveryOrder.getPickupDate();
		final LocalDate pickupDate = pickupDate1.getDate();
		final LocalTime timeFrom = pickupDate1.getTimeFrom();
		final LocalTime timeTo = pickupDate1.getTimeTo();

		//
		final I_C_BPartner deliverToBPartner = ShipperTestHelper.createBPartner(deliveryOrder.getDeliveryAddress());
		//noinspection deprecation,ConstantConditions
		deliverToBPartner.setEMail(deliveryOrder.getDeliveryContact().getEmailAddress());
		final I_C_Location deliverToLocation = ShipperTestHelper.createLocation(deliveryOrder.getDeliveryAddress());
		final int deliverToBPartnerLocationId = 0;
		final String deliverToPhoneNumber = deliveryOrder.getDeliveryContact().getSimplePhoneNumber();

		//
		final ServiceType detectedServiceType = deliveryOrder.getServiceType();
		final ShipperId shipperId = deliveryOrder.getShipperId();
		final ShipperTransportationId shipperTransportationId = deliveryOrder.getShipperTransportationId();

		//
		final ImmutableList<DeliveryOrderLine> deliveryOrderLines = deliveryOrder.getDeliveryOrderLines();

		final Integer allPackagesGrossWeightKg = deliveryOrderLines.stream().map(DeliveryOrderLine::getGrossWeightKg).reduce(Integer::sum).get();

		//
		final String customerReference = deliveryOrder.getCustomerReference();

		final CustomDeliveryData customDeliveryData = deliveryOrder.getCustomDeliveryData();

		return draftDeliveryOrderCreator.createDeliveryOrderFromParams(
				pickupFromBPartner,
				pickupFromLocation,
				pickupDate,
				timeFrom,
				timeTo,
				deliverToBPartner,
				deliverToBPartnerLocationId,
				deliverToLocation,
				deliverToPhoneNumber,
				detectedServiceType,
				allPackagesGrossWeightKg,
				shipperId,
				shipperTransportationId,
				customerReference,
				DpdOrderCustomDeliveryData.cast(customDeliveryData),
				deliveryOrderLines
		);
	}

}
