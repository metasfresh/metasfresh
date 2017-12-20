package de.metas.material.interceptor;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.adempiere.ad.modelvalidator.ModelChangeType;
import org.adempiere.mm.attributes.api.impl.ModelProductDescriptorExtractorUsingAttributeSetInstanceFactory;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import de.metas.ShutdownListener;
import de.metas.StartupListener;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.material.event.shipmentschedule.AbstractShipmentScheduleEvent;
import de.metas.material.event.shipmentschedule.ShipmentScheduleCreatedEvent;
import de.metas.material.event.shipmentschedule.ShipmentScheduleDeletedEvent;
import de.metas.material.event.shipmentschedule.ShipmentScheduleUpdatedEvent;
import de.metas.material.interceptor.M_ShipmentSchedule;
import mockit.Expectations;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { StartupListener.class,
		ShutdownListener.class,
		ModelProductDescriptorExtractorUsingAttributeSetInstanceFactory.class })
public class M_ShipmentScheduleTest
{

	private static final BigDecimal ONE = BigDecimal.ONE;
	private static final BigDecimal FOUR = new BigDecimal("4");
	private static final BigDecimal FIVE = new BigDecimal("5");
	private static final BigDecimal TEN = BigDecimal.TEN;
	private static final BigDecimal TWENTY = new BigDecimal("20");


	private I_M_ShipmentSchedule shipmentSchedule;

	private I_M_ShipmentSchedule oldShipmentSchedule;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

		oldShipmentSchedule = newInstance(I_M_ShipmentSchedule.class);
		oldShipmentSchedule.setQtyOrdered_Calculated(TWENTY); // note that setQtyOrdered is just for display!, QtyOrdered_Calculated one or QtyOrdered_Override is where the qty is!
		oldShipmentSchedule.setQtyReserved(FOUR);
		oldShipmentSchedule.setM_Product_ID(20);
		oldShipmentSchedule.setM_Warehouse_ID(30);
		oldShipmentSchedule.setC_BPartner_ID(40);
		oldShipmentSchedule.setC_BPartner_Override_ID(45);
		save(oldShipmentSchedule);

		shipmentSchedule = newInstance(I_M_ShipmentSchedule.class);
		shipmentSchedule.setQtyOrdered_Calculated(TEN); // decrease by ten
		shipmentSchedule.setQtyReserved(FIVE); // increase by one
		shipmentSchedule.setM_Product_ID(20);
		shipmentSchedule.setM_Warehouse_ID(30);
		shipmentSchedule.setC_BPartner_ID(40);
		shipmentSchedule.setC_BPartner_Override_ID(45);
		save(shipmentSchedule);

	}

	@Test
	public void createShipmentscheduleEvent_after_new()
	{
		final AbstractShipmentScheduleEvent result = M_ShipmentSchedule.INSTANCE
				.createShipmentScheduleEvent(shipmentSchedule, ModelChangeType.AFTER_NEW);

		assertThat(result).isNotNull();
		assertThat(result).isInstanceOf(ShipmentScheduleCreatedEvent.class);

		final ShipmentScheduleCreatedEvent createdEvent = (ShipmentScheduleCreatedEvent)result;
		assertThat(createdEvent.getShipmentScheduleId()).isEqualTo(shipmentSchedule.getM_ShipmentSchedule_ID());
		assertThat(createdEvent.getOrderLineId()).isLessThanOrEqualTo(0);
		assertThat(createdEvent.getOrderedMaterial().getBPartnerId()).isEqualTo(45);
		assertThat(createdEvent.getOrderedMaterial().getQuantity()).isEqualByComparingTo(TEN);
		assertThat(createdEvent.getOrderedMaterial().getProductId()).isEqualTo(20);
		assertThat(createdEvent.getOrderedMaterial().getWarehouseId()).isEqualTo(30);
		assertThat(createdEvent.getReservedQuantity()).isEqualByComparingTo(FIVE);
	}

	@Test
	public void createShipmentscheduleEvent_after_change()
	{
		// @formatter:off
		new Expectations(InterfaceWrapperHelper.class) {{

			InterfaceWrapperHelper.createOld(shipmentSchedule, I_M_ShipmentSchedule.class);
			times = 1; result = oldShipmentSchedule;
		}}; // @formatter:on

		final AbstractShipmentScheduleEvent result = M_ShipmentSchedule.INSTANCE
				.createShipmentScheduleEvent(shipmentSchedule, ModelChangeType.AFTER_CHANGE);

		assertThat(result).isNotNull();
		assertThat(result).isInstanceOf(ShipmentScheduleUpdatedEvent.class);

		final ShipmentScheduleUpdatedEvent updatedEvent = (ShipmentScheduleUpdatedEvent)result;
		assertThat(updatedEvent.getShipmentScheduleId()).isEqualTo(shipmentSchedule.getM_ShipmentSchedule_ID());
		assertThat(updatedEvent.getOrderedMaterial().getBPartnerId()).isEqualTo(45);
		assertThat(updatedEvent.getOrderedMaterial().getQuantity()).isEqualByComparingTo(TEN);
		assertThat(updatedEvent.getOrderedMaterial().getProductId()).isEqualTo(20);
		assertThat(updatedEvent.getOrderedMaterial().getWarehouseId()).isEqualTo(30);
		assertThat(updatedEvent.getReservedQuantity()).isEqualByComparingTo(FIVE);
		assertThat(updatedEvent.getReservedQuantityDelta()).isEqualByComparingTo(ONE);
		assertThat(updatedEvent.getOrderedQuantityDelta()).isEqualByComparingTo(TEN.negate());
	}

	@Test
	public void createShipmentscheduleEvent_before_delete()
	{
		final AbstractShipmentScheduleEvent result = M_ShipmentSchedule.INSTANCE
				.createShipmentScheduleEvent(shipmentSchedule, ModelChangeType.BEFORE_DELETE);

		assertThat(result).isNotNull();
		assertThat(result).isInstanceOf(ShipmentScheduleDeletedEvent.class);

		final ShipmentScheduleDeletedEvent deletedEvent = (ShipmentScheduleDeletedEvent)result;
		assertThat(deletedEvent.getShipmentScheduleId()).isEqualTo(shipmentSchedule.getM_ShipmentSchedule_ID());
		assertThat(deletedEvent.getOrderedMaterial().getBPartnerId()).isEqualTo(45);
		assertThat(deletedEvent.getOrderedMaterial().getQuantity()).isEqualByComparingTo(TEN);
		assertThat(deletedEvent.getOrderedMaterial().getProductId()).isEqualTo(20);
		assertThat(deletedEvent.getOrderedMaterial().getWarehouseId()).isEqualTo(30);
		assertThat(deletedEvent.getReservedQuantity()).isEqualByComparingTo(FIVE);
	}
}
