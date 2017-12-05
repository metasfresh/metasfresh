package de.metas.material.model.interceptor;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.adempiere.ad.modelvalidator.ModelChangeType;
import org.adempiere.mm.attributes.api.impl.ModelProductDescriptorExtractorUsingAttributeSetInstanceFactory;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import de.metas.ShutdownListener;
import de.metas.StartupListener;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.material.event.shipmentschedule.ShipmentScheduleCreatedEvent;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2017 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


@RunWith(SpringRunner.class)
@SpringBootTest(classes = { StartupListener.class,
		ShutdownListener.class,
		ModelProductDescriptorExtractorUsingAttributeSetInstanceFactory.class })
public class M_ShipmentScheduleTest
{

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	public void createShipmentscheduleEvent()
	{
		final I_M_ShipmentSchedule shipmentSchedule = newInstance(I_M_ShipmentSchedule.class);
		shipmentSchedule.setQtyOrdered_Calculated(BigDecimal.TEN); // note that setQtyOrdered is just for display!, QtyOrdered_Calculated one or QtyOrdered_Override is where the qty is!
		shipmentSchedule.setM_Product_ID(20);
		shipmentSchedule.setM_Warehouse_ID(30);
		shipmentSchedule.setC_BPartner_ID(40);
		shipmentSchedule.setC_BPartner_Override_ID(45);
		save(shipmentSchedule);

		final ShipmentScheduleCreatedEvent result = M_ShipmentSchedule.INSTANCE
				.createShipmentscheduleEvent(shipmentSchedule, ModelChangeType.AFTER_CHANGE);

		assertThat(result).isNotNull();
		assertThat(result.getShipmentScheduleId()).isEqualTo(shipmentSchedule.getM_ShipmentSchedule_ID());
		assertThat(result.getOrderLineId()).isLessThanOrEqualTo(0);
		assertThat(result.getMaterialDescriptor().getBPartnerId()).isEqualTo(45);
		assertThat(result.getMaterialDescriptor().getQuantity()).isEqualByComparingTo("10");
		assertThat(result.getMaterialDescriptor().getProductId()).isEqualTo(20);
		assertThat(result.getMaterialDescriptor().getWarehouseId()).isEqualTo(30);
	}

}
