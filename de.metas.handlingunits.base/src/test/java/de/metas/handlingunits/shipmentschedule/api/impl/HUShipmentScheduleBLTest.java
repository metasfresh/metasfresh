package de.metas.handlingunits.shipmentschedule.api.impl;

import static org.hamcrest.Matchers.comparesEqualTo;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.Services;
import org.compiere.model.X_M_InOut;
import org.compiere.util.Env;
import org.junit.Before;
import org.junit.Test;

import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI_Version;
import de.metas.handlingunits.model.I_M_InOut;
import de.metas.handlingunits.model.I_M_InOutLine;
import de.metas.handlingunits.model.I_M_ShipmentSchedule;
import de.metas.handlingunits.model.I_M_ShipmentSchedule_QtyPicked;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.handlingunits.shipmentschedule.api.IHUShipmentScheduleBL;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2015 metas GmbH
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

public class HUShipmentScheduleBLTest
{

	private PlainContextAware contextProvider;
	private IHUShipmentScheduleBL shipmentScheduleBL = Services.get(IHUShipmentScheduleBL.class);

	private String typeLU = X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit;
	private String typeTU = X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
		contextProvider = PlainContextAware.newOutOfTrx(Env.getCtx());
	}

	@Test
	public void testupdateHUDeliveryQuantities_AllocationsHaveNoLUTU_Complete()
	{
		final I_M_ShipmentSchedule schedule = createSchedule(new BigDecimal(1), new BigDecimal(2));

		final I_M_InOut io = createInOut(X_M_InOut.DOCSTATUS_Completed);
		final I_M_InOutLine iol = createInOutLine(io, new BigDecimal(2));

		createAlloc(schedule, iol, null, null);

		shipmentScheduleBL.updateHUDeliveryQuantities(schedule);
		InterfaceWrapperHelper.save(schedule);

		assertThat(" Wrong QtyToDeliverLU: " + schedule.getQtyToDeliver_LU(), schedule.getQtyToDeliver_LU(), comparesEqualTo(BigDecimal.ZERO));
		assertThat(" Wrong QtyToDeliverTU: " + schedule.getQtyToDeliver_TU(), schedule.getQtyToDeliver_TU(), comparesEqualTo(BigDecimal.ZERO));

		assertThat(" Wrong QtyDeliveredLU: " + schedule.getQtyDelivered_LU(), schedule.getQtyDelivered_LU(), comparesEqualTo(BigDecimal.ONE));
		assertThat(" Wrong QtyDeliveredTU: " + schedule.getQtyDelivered_TU(), schedule.getQtyDelivered_TU(), comparesEqualTo(new BigDecimal(2)));

	}

	@Test
	public void testupdateHUDeliveryQuantities_AllocationsHaveNoLUTU_Reverse()
	{
		final I_M_ShipmentSchedule schedule = createSchedule(new BigDecimal(1), new BigDecimal(2));

		final I_M_InOut io = createInOut(X_M_InOut.DOCSTATUS_Reversed);
		final I_M_InOutLine iol = createInOutLine(io, new BigDecimal(2));

		createAlloc(schedule, iol, null, null);

		shipmentScheduleBL.updateHUDeliveryQuantities(schedule);
		InterfaceWrapperHelper.save(schedule);

		assertThat(" Wrong QtyToDeliverLU: " + schedule.getQtyToDeliver_LU(), schedule.getQtyToDeliver_LU(), comparesEqualTo(BigDecimal.ONE));
		assertThat(" Wrong QtyToDeliverTU: " + schedule.getQtyToDeliver_TU(), schedule.getQtyToDeliver_TU(), comparesEqualTo(new BigDecimal(2)));

		assertThat(" Wrong QtyDeliveredLU: " + schedule.getQtyDelivered_LU(), schedule.getQtyDelivered_LU(), comparesEqualTo(BigDecimal.ZERO));
		assertThat(" Wrong QtyDeliveredTU: " + schedule.getQtyDelivered_TU(), schedule.getQtyDelivered_TU(), comparesEqualTo(BigDecimal.ZERO));

	}

	@Test
	public void testupdateHUDeliveryQuantities_AllocationsHaveNoLUTU_Draft()
	{
		final I_M_ShipmentSchedule schedule = createSchedule(new BigDecimal(1), new BigDecimal(2));

		final I_M_InOut io = createInOut(X_M_InOut.DOCSTATUS_Drafted);
		final I_M_InOutLine iol = createInOutLine(io, new BigDecimal(2));

		createAlloc(schedule, iol, null, null);

		shipmentScheduleBL.updateHUDeliveryQuantities(schedule);
		InterfaceWrapperHelper.save(schedule);

		assertThat(" Wrong QtyToDeliverLU: " + schedule.getQtyToDeliver_LU(), schedule.getQtyToDeliver_LU(), comparesEqualTo(BigDecimal.ONE));
		assertThat(" Wrong QtyToDeliverTU: " + schedule.getQtyToDeliver_TU(), schedule.getQtyToDeliver_TU(), comparesEqualTo(new BigDecimal(2)));

		assertThat(" Wrong QtyDeliveredLU: " + schedule.getQtyDelivered_LU(), schedule.getQtyDelivered_LU(), comparesEqualTo(BigDecimal.ZERO));
		assertThat(" Wrong QtyDeliveredTU: " + schedule.getQtyDelivered_TU(), schedule.getQtyDelivered_TU(), comparesEqualTo(BigDecimal.ZERO));

	}

	@Test
	public void testupdateHUDeliveryQuantities_AllocationsHaveNoLUTU_Void()
	{
		final I_M_ShipmentSchedule schedule = createSchedule(new BigDecimal(1), new BigDecimal(2));

		final I_M_InOut io = createInOut(X_M_InOut.DOCSTATUS_Voided);
		final I_M_InOutLine iol = createInOutLine(io, new BigDecimal(2));

		createAlloc(schedule, iol, null, null);

		shipmentScheduleBL.updateHUDeliveryQuantities(schedule);
		InterfaceWrapperHelper.save(schedule);

		assertThat(" Wrong QtyToDeliverLU: " + schedule.getQtyToDeliver_LU(), schedule.getQtyToDeliver_LU(), comparesEqualTo(BigDecimal.ONE));
		assertThat(" Wrong QtyToDeliverTU: " + schedule.getQtyToDeliver_TU(), schedule.getQtyToDeliver_TU(), comparesEqualTo(new BigDecimal(2)));

		assertThat(" Wrong QtyDeliveredLU: " + schedule.getQtyDelivered_LU(), schedule.getQtyDelivered_LU(), comparesEqualTo(BigDecimal.ZERO));
		assertThat(" Wrong QtyDeliveredTU: " + schedule.getQtyDelivered_TU(), schedule.getQtyDelivered_TU(), comparesEqualTo(BigDecimal.ZERO));

	}

	@Test
	public void testupdateHUDeliveryQuantities_AllocationsHaveNoLUTU_Closed()
	{
		final I_M_ShipmentSchedule schedule = createSchedule(new BigDecimal(1), new BigDecimal(2));

		final I_M_InOut io = createInOut(X_M_InOut.DOCSTATUS_Closed);
		final I_M_InOutLine iol = createInOutLine(io, new BigDecimal(2));

		createAlloc(schedule, iol, null, null);

		shipmentScheduleBL.updateHUDeliveryQuantities(schedule);
		InterfaceWrapperHelper.save(schedule);

		assertThat(" Wrong QtyToDeliverLU: " + schedule.getQtyToDeliver_LU(), schedule.getQtyToDeliver_LU(), comparesEqualTo(BigDecimal.ONE));
		assertThat(" Wrong QtyToDeliverTU: " + schedule.getQtyToDeliver_TU(), schedule.getQtyToDeliver_TU(), comparesEqualTo(new BigDecimal(2)));

		assertThat(" Wrong QtyDeliveredLU: " + schedule.getQtyDelivered_LU(), schedule.getQtyDelivered_LU(), comparesEqualTo(BigDecimal.ZERO));
		assertThat(" Wrong QtyDeliveredTU: " + schedule.getQtyDelivered_TU(), schedule.getQtyDelivered_TU(), comparesEqualTo(BigDecimal.ZERO));

	}

	@Test
	public void testupdateHUDeliveryQuantities_AllocationsHaveLUTU_1TU()
	{
		final I_M_ShipmentSchedule schedule = createSchedule(new BigDecimal(1), new BigDecimal(1));

		final I_M_InOut io = createInOut(X_M_InOut.DOCSTATUS_Completed);
		final I_M_InOutLine iol = createInOutLine(io, new BigDecimal(-99)); // this doesn't matter. the HUs are counted 

		final I_M_HU lu = createHU(typeLU);

		final I_M_HU tu = createHU(typeTU);

		createAlloc(schedule, iol, lu, tu);

		shipmentScheduleBL.updateHUDeliveryQuantities(schedule);
		InterfaceWrapperHelper.save(schedule);

		assertThat(" Wrong QtyToDeliverLU: " + schedule.getQtyToDeliver_LU(), schedule.getQtyToDeliver_LU(), comparesEqualTo(BigDecimal.ZERO));
		assertThat(" Wrong QtyToDeliverTU: " + schedule.getQtyToDeliver_TU(), schedule.getQtyToDeliver_TU(), comparesEqualTo(BigDecimal.ZERO));

		assertThat(" Wrong QtyDeliveredLU: " + schedule.getQtyDelivered_LU(), schedule.getQtyDelivered_LU(), comparesEqualTo(BigDecimal.ONE));
		assertThat(" Wrong QtyDeliveredTU: " + schedule.getQtyDelivered_TU(), schedule.getQtyDelivered_TU(), comparesEqualTo(BigDecimal.ONE));
		
		// assertThat(iol.getQtyEnteredTU(), comparesEqualTo(BigDecimal.ONE)); iol.getQtyEnteredTU() is not updated by the allocaction
	}

	

	@Test
	public void testupdateHUDeliveryQuantities_AllocationsHaveLUTU_2TU()
	{
		final I_M_ShipmentSchedule schedule = createSchedule(new BigDecimal(1), new BigDecimal(2));

		final I_M_InOut io = createInOut(X_M_InOut.DOCSTATUS_Completed);
		final I_M_InOutLine iol = createInOutLine(io, new BigDecimal(2)); // this doesn't matter. the HUs are counted 

		final I_M_HU lu = createHU(typeLU);

		final I_M_HU tu = createHU(typeTU);

		createAlloc(schedule, iol, lu, tu);

		shipmentScheduleBL.updateHUDeliveryQuantities(schedule);
		InterfaceWrapperHelper.save(schedule);

		assertThat(" Wrong QtyToDeliverLU: " + schedule.getQtyToDeliver_LU(), schedule.getQtyToDeliver_LU(), comparesEqualTo(BigDecimal.ZERO));
		assertThat(" Wrong QtyToDeliverTU: " + schedule.getQtyToDeliver_TU(), schedule.getQtyToDeliver_TU(), comparesEqualTo(BigDecimal.ONE));

		assertThat(" Wrong QtyDeliveredLU: " + schedule.getQtyDelivered_LU(), schedule.getQtyDelivered_LU(), comparesEqualTo(BigDecimal.ONE));
		assertThat(" Wrong QtyDeliveredTU: " + schedule.getQtyDelivered_TU(), schedule.getQtyDelivered_TU(), comparesEqualTo(BigDecimal.ONE));

	}
	
	@Test
	public void testupdateHUDeliveryQuantities_AllocationsHaveLUTU_2TU_2LU()
	{
		final I_M_ShipmentSchedule schedule = createSchedule(new BigDecimal(1), new BigDecimal(2));

		final I_M_InOut io = createInOut(X_M_InOut.DOCSTATUS_Completed);
		final I_M_InOutLine iol = createInOutLine(io, new BigDecimal(2)); // this doesn't matter. the HUs are counted 

		final I_M_HU lu1 = createHU(typeLU);

		final I_M_HU tu1 = createHU(typeTU);
		final I_M_HU lu2 = createHU(typeLU);

		final I_M_HU tu2 = createHU(typeTU);

		createAlloc(schedule, iol, lu1, tu1);
		createAlloc(schedule, iol, lu2, tu2);


		shipmentScheduleBL.updateHUDeliveryQuantities(schedule);
		InterfaceWrapperHelper.save(schedule);

		assertThat(" Wrong QtyToDeliverLU: " + schedule.getQtyToDeliver_LU(), schedule.getQtyToDeliver_LU(), comparesEqualTo(BigDecimal.ZERO));
		assertThat(" Wrong QtyToDeliverTU: " + schedule.getQtyToDeliver_TU(), schedule.getQtyToDeliver_TU(), comparesEqualTo(BigDecimal.ZERO));

		assertThat(" Wrong QtyDeliveredLU: " + schedule.getQtyDelivered_LU(), schedule.getQtyDelivered_LU(), comparesEqualTo(new BigDecimal(2)));
		assertThat(" Wrong QtyDeliveredTU: " + schedule.getQtyDelivered_TU(), schedule.getQtyDelivered_TU(), comparesEqualTo(new BigDecimal(2)));

	}
	private I_M_HU createHU(final String unitType)
	{
		final I_M_HU hu = InterfaceWrapperHelper.newInstance(I_M_HU.class, contextProvider);

		final I_M_HU_PI_Version version = InterfaceWrapperHelper.newInstance(I_M_HU_PI_Version.class, contextProvider);
		version.setHU_UnitType(unitType);
		InterfaceWrapperHelper.save(version);

		hu.setM_HU_PI_Version(version);

		InterfaceWrapperHelper.save(hu);

		return hu;
	}

	private I_M_ShipmentSchedule_QtyPicked createAlloc(I_M_ShipmentSchedule schedule, I_M_InOutLine iol, final I_M_HU lu, final I_M_HU tu)

	{
		final I_M_ShipmentSchedule_QtyPicked alloc = InterfaceWrapperHelper.newInstance(I_M_ShipmentSchedule_QtyPicked.class, contextProvider);

		alloc.setM_LU_HU(lu);
		alloc.setM_TU_HU(tu);
		alloc.setM_InOutLine(iol);
		alloc.setM_ShipmentSchedule(schedule);

		InterfaceWrapperHelper.save(alloc);

		return alloc;
	}

	private I_M_InOutLine createInOutLine(I_M_InOut io, final BigDecimal qtyEnteredTU)
	{
		final I_M_InOutLine iol = InterfaceWrapperHelper.newInstance(I_M_InOutLine.class, contextProvider);

		iol.setM_InOut(io);
		iol.setQtyEnteredTU(qtyEnteredTU);

		InterfaceWrapperHelper.save(iol);

		return iol;

	}

	private I_M_InOut createInOut(final String docStatus)
	{
		final I_M_InOut io = InterfaceWrapperHelper.newInstance(I_M_InOut.class, contextProvider);

		io.setDocStatus(docStatus);

		InterfaceWrapperHelper.save(io);

		return io;
	}

	private I_M_ShipmentSchedule createSchedule(final BigDecimal qtyOrderedLU, final BigDecimal qtyOrderedTU)
	{
		final I_M_ShipmentSchedule sched = InterfaceWrapperHelper.newInstance(I_M_ShipmentSchedule.class, contextProvider);
		sched.setQtyOrdered_LU(qtyOrderedLU);
		sched.setQtyOrdered_TU(qtyOrderedTU);

		InterfaceWrapperHelper.save(sched);
		return sched;
	}
}
