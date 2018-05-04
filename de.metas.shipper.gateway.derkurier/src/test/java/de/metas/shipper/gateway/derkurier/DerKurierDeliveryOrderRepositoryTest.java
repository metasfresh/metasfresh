package de.metas.shipper.gateway.derkurier;

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
import de.metas.shipper.gateway.derkurier.model.I_DerKurier_DeliveryOrder;
import de.metas.shipper.gateway.spi.model.DeliveryOrder;

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

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

		final I_C_Country countryDe = newInstance(I_C_Country.class);
		countryDe.setCountryCode("DE");
		save(countryDe);

		derKurierDeliveryOrderRepository = new DerKurierDeliveryOrderRepository(new Converters());
	}

	@Test
	public void test_save()
	{

		final DeliveryOrder deliveryOrder = DerKurierTestTools.createTestDeliveryOrderwithOneLine();
		final DeliveryOrder savedDeliveryOrder = derKurierDeliveryOrderRepository.save(deliveryOrder);

		assertThat(savedDeliveryOrder.getRepoId()).isGreaterThan(0);
		final List<I_DerKurier_DeliveryOrder> headerRecords = POJOLookupMap.get().getRecords(I_DerKurier_DeliveryOrder.class);
		assertThat(headerRecords).hasSize(1);
		final I_DerKurier_DeliveryOrder headerRecord = headerRecords.get(0);
		assertThat(headerRecord.getDK_Sender_Street()).isEqualTo(deliveryOrder.getPickupAddress().getStreet1());
		assertThat(headerRecord.getDerKurier_DeliveryOrder_ID()).isEqualTo(savedDeliveryOrder.getOrderId().getOrderIdAsInt());

//		final List<I_DerKurier_DeliveryOrderLine> lineRecords = POJOLookupMap.get().getRecords(I_DerKurier_DeliveryOrderLine.class);
//		assertThat(lineRecords).hasSize(1);
//		final I_DerKurier_DeliveryOrderLine lineRecord = lineRecords.get(0);
		assertThat(deliveryOrder.getPickupDate().getTimeFrom()).isNull(); // guard
		assertThat(headerRecord.getDK_DesiredPickupTime_From()).isNull();

		final DeliveryOrder loadedDeliveryOrder = derKurierDeliveryOrderRepository.getByRepoId(savedDeliveryOrder.getRepoId());
		assertThat(loadedDeliveryOrder).isEqualTo(savedDeliveryOrder);
	}

}
