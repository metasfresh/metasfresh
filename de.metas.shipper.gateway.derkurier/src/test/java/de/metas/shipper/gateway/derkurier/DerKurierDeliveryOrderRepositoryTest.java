package de.metas.shipper.gateway.derkurier;

import static de.metas.shipper.gateway.derkurier.DerKurierTestTools.M_SHIPPER_ID;
import static de.metas.shipper.gateway.derkurier.DerKurierTestTools.M_SHIPPER_TRANSPORTATION_ID;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_Country;
import org.junit.Before;
import org.junit.Test;

import de.metas.shipper.gateway.derkurier.misc.Converters;
import de.metas.shipper.gateway.derkurier.misc.DerKurierServiceType;
import de.metas.shipper.gateway.derkurier.model.I_DerKurier_DeliveryOrder;
import de.metas.shipper.gateway.derkurier.model.I_DerKurier_DeliveryOrderLine;
import de.metas.shipper.gateway.spi.model.Address;
import de.metas.shipper.gateway.spi.model.DeliveryOrder;
import de.metas.shipper.gateway.spi.model.DeliveryOrder.DeliveryOrderBuilder;
import de.metas.shipper.gateway.spi.model.PickupDate;

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

public class DerKurierDeliveryOrderRepositoryTest
{
	private DerKurierDeliveryOrderRepository derKurierDeliveryOrderRepository;
	private I_C_Country countryDe;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

		countryDe = newInstance(I_C_Country.class);
		countryDe.setCountryCode("DE");
		save(countryDe);

		derKurierDeliveryOrderRepository = new DerKurierDeliveryOrderRepository(new Converters());
	}

	@Test
	public void test_save()
	{
		final DeliveryOrder deliveryOrder = DerKurierTestTools.createTestDeliveryOrderwithOneLine();

		//
		// invoke the method under test
		final DeliveryOrder savedDeliveryOrder = derKurierDeliveryOrderRepository.save(deliveryOrder);

		// test some particular fields; feel free to extend
		assertThat(savedDeliveryOrder.getRepoId()).isGreaterThan(0);
		assertThat(savedDeliveryOrder.getShipperId()).isEqualTo(M_SHIPPER_ID);
		assertThat(savedDeliveryOrder.getShipperTransportationId()).isEqualByComparingTo(M_SHIPPER_TRANSPORTATION_ID);

		final List<I_DerKurier_DeliveryOrder> headerRecords = POJOLookupMap.get().getRecords(I_DerKurier_DeliveryOrder.class);
		assertThat(headerRecords).hasSize(1);

		final I_DerKurier_DeliveryOrder headerRecord = headerRecords.get(0);
		assertThat(headerRecord.getDK_Sender_Street()).isEqualTo(deliveryOrder.getPickupAddress().getStreet1());
		assertThat(headerRecord.getDerKurier_DeliveryOrder_ID()).isEqualTo(savedDeliveryOrder.getOrderId().getOrderIdAsInt());
		assertThat(headerRecord.getM_Shipper_ID()).isEqualTo(M_SHIPPER_ID);
		assertThat(headerRecord.getM_ShipperTransportation_ID()).isEqualTo(M_SHIPPER_TRANSPORTATION_ID);

		assertThat(deliveryOrder.getPickupDate().getTimeFrom()).isNull(); // guard
		assertThat(headerRecord.getDK_DesiredPickupTime_From()).isNull();

		// reload the saved order and verify that it's still the same
		final DeliveryOrder loadedDeliveryOrder = derKurierDeliveryOrderRepository.getByRepoId(savedDeliveryOrder.getRepoId());
		assertThat(loadedDeliveryOrder).isEqualTo(savedDeliveryOrder);
	}

	/**
	 * The async processor loads a drafted delivery order that has no delivery date; this needs to succeed
	 */
	@Test
	public void addLineRecordDataToDeliveryOrderBuilder_null_deliveryDate()
	{

		final I_DerKurier_DeliveryOrderLine deliveryOrderLine = newInstance(I_DerKurier_DeliveryOrderLine.class);
		deliveryOrderLine.setDK_Consignee_Name("DK_Consignee_Name");
		deliveryOrderLine.setDK_Consignee_Street("street1-only");
		deliveryOrderLine.setDK_Consignee_ZipCode("DK_Consignee_ZipCode");
		deliveryOrderLine.setDK_Consignee_City("DK_Consignee_City");
		deliveryOrderLine.setC_Country(countryDe);
		save(deliveryOrderLine);

		final Address pickupAddress = DerKurierTestTools.createPickupAddress();
		final PickupDate pickupDate = DerKurierTestTools.createPickupDate();

		// create a builder, add properties that are mandatory, but not related to the deliveryOrderLine's data
		final DeliveryOrderBuilder builder = DeliveryOrder.builder()
				.pickupAddress(pickupAddress) // pickupAddress is mandatory, but not coming from I_DerKurier_DeliveryOrderLine
				.pickupDate(pickupDate) // same as pickupAddress
				.serviceType(DerKurierServiceType.OVERNIGHT);

		// invoke the method under test
		derKurierDeliveryOrderRepository.addLineRecordDataToDeliveryOrderBuilder(
				builder,
				deliveryOrderLine,
				null);
		final DeliveryOrder result = builder.build();

		assertThat(result.getPickupAddress())
				.as("pickupAddress shall be left unchanged by the DerKurier_DeliveryOrderLine record")
				.isSameAs(pickupAddress);
		assertThat(result.getPickupDate())
				.as("pickupAddress shall be left unchanged by the DerKurier_DeliveryOrderLine record")
				.isSameAs(pickupDate);

		final Address deliveryAddress = result.getDeliveryAddress();
		assertThat(deliveryAddress.getCompanyName1()).isEqualTo("DK_Consignee_Name");
		assertThat(deliveryAddress.getStreet1()).isEqualTo("street1-only");
		assertThat(deliveryAddress.getStreet2()).isNull();
		assertThat(deliveryAddress.getZipCode()).isEqualTo("DK_Consignee_ZipCode");
		assertThat(deliveryAddress.getCity()).isEqualTo("DK_Consignee_City");

		assertThat(result.getDeliveryDate()).isNull();
	}

}
