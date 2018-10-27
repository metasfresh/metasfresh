package de.metas.handlingunits.shipmentschedule.api.impl;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.ZERO;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.List;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.lang.IPair;
import org.adempiere.util.lang.ImmutablePair;
import org.compiere.model.I_M_Product;
import org.compiere.model.X_M_InOut;
import org.compiere.util.Env;
import org.junit.Before;
import org.junit.Test;

import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.allocation.transfer.impl.LUTUProducerDestinationTestSupport;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI_Version;
import de.metas.handlingunits.model.I_M_InOut;
import de.metas.handlingunits.model.I_M_InOutLine;
import de.metas.handlingunits.model.I_M_ShipmentSchedule;
import de.metas.handlingunits.model.I_M_ShipmentSchedule_QtyPicked;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleWithHU;
import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.inoutcandidate.api.impl.ShipmentScheduleBL;
import de.metas.product.ProductId;
import de.metas.util.Services;
import de.metas.util.collections.CollectionUtils;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class ShipmentScheduleWithHUTests
{

	private PlainContextAware contextProvider;

	private static final String typeLU = X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit;
	private static final String typeTU = X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit;

	private ProductId productId;

	private LUTUProducerDestinationTestSupport testSupport;

	@Before
	public void init()
	{
		// AdempiereTestHelper.get().init();
		this.testSupport = new LUTUProducerDestinationTestSupport();
		contextProvider = PlainContextAware.newOutOfTrx(Env.getCtx());

		final I_M_Product product = newInstance(I_M_Product.class);
		saveRecord(product);
		productId = testSupport.helper.pTomatoProductId;

		Services.registerService(IShipmentScheduleBL.class, ShipmentScheduleBL.newInstanceForUnitTesting());
	}

	@Test
	public void testupdateHUDeliveryQuantities_AllocationsHaveNoLUTU_Complete()
	{
		final I_M_ShipmentSchedule schedule = createScheduleWithQtyOrderedLUandQtyOrderedTU(1, 2);

		final I_M_InOut io = createInOut(X_M_InOut.DOCSTATUS_Completed);
		final I_M_InOutLine iol = createInOutLine(io, new BigDecimal(2));

		final I_M_ShipmentSchedule_QtyPicked shipmentScheduleQtyPicked = ShipmentScheduleWithHU
				.ofShipmentScheduleWithoutHu(schedule, new BigDecimal("99"))
				.setM_InOutLine(iol)
				.setM_InOut(io)
				.getShipmentScheduleQtyPicked();

		assertThat(shipmentScheduleQtyPicked.getQtyLU()).as("Wrong QtyLU").isEqualByComparingTo(ONE);
		assertThat(shipmentScheduleQtyPicked.getQtyTU()).as("Wrong QtyTU").isEqualByComparingTo("2");
	}

	@Test
	public void testupdateHUDeliveryQuantities_AllocationsHaveNoLUTU_Reverse()
	{
		final I_M_ShipmentSchedule schedule = createScheduleWithQtyOrderedLUandQtyOrderedTU(1, 2);

		final I_M_InOut io = createInOut(X_M_InOut.DOCSTATUS_Reversed);
		final I_M_InOutLine iol = createInOutLine(io, new BigDecimal(2));

		final I_M_ShipmentSchedule_QtyPicked shipmentScheduleQtyPicked = ShipmentScheduleWithHU
				.ofShipmentScheduleWithoutHu(schedule, new BigDecimal("99"))
				.setM_InOutLine(iol)
				.setM_InOut(io)
				.getShipmentScheduleQtyPicked();

		assertThat(shipmentScheduleQtyPicked.getQtyLU()).as("Wrong QtyLU").isEqualByComparingTo(ZERO);
		assertThat(shipmentScheduleQtyPicked.getQtyTU()).as("Wrong QtyTU").isEqualByComparingTo(ZERO);
	}

	@Test
	public void testupdateHUDeliveryQuantities_AllocationsHaveNoLUTU_Draft()
	{
		final I_M_ShipmentSchedule schedule = createScheduleWithQtyOrderedLUandQtyOrderedTU(1, 2);

		final I_M_InOut io = createInOut(X_M_InOut.DOCSTATUS_Drafted);
		final I_M_InOutLine iol = createInOutLine(io, new BigDecimal(2));

		final I_M_ShipmentSchedule_QtyPicked shipmentScheduleQtyPicked = ShipmentScheduleWithHU
				.ofShipmentScheduleWithoutHu(schedule, new BigDecimal("99"))
				.setM_InOutLine(iol)
				.setM_InOut(io)
				.getShipmentScheduleQtyPicked();

		assertThat(shipmentScheduleQtyPicked.getQtyLU()).as("Wrong QtyLU").isEqualByComparingTo(ZERO);
		assertThat(shipmentScheduleQtyPicked.getQtyTU()).as("Wrong QtyTU").isEqualByComparingTo(ZERO);
	}

	@Test
	public void testupdateHUDeliveryQuantities_AllocationsHaveNoLUTU_Void()
	{
		final I_M_ShipmentSchedule schedule = createScheduleWithQtyOrderedLUandQtyOrderedTU(1, 2);

		final I_M_InOut io = createInOut(X_M_InOut.DOCSTATUS_Voided);
		final I_M_InOutLine iol = createInOutLine(io, new BigDecimal(2));

		final I_M_ShipmentSchedule_QtyPicked shipmentScheduleQtyPicked = ShipmentScheduleWithHU
				.ofShipmentScheduleWithoutHu(schedule, new BigDecimal("99"))
				.setM_InOutLine(iol)
				.setM_InOut(io)
				.getShipmentScheduleQtyPicked();

		assertThat(shipmentScheduleQtyPicked.getQtyLU()).as("Wrong QtyLU").isEqualByComparingTo(ZERO);
		assertThat(shipmentScheduleQtyPicked.getQtyTU()).as("Wrong QtyTU").isEqualByComparingTo(ZERO);
	}

	@Test
	public void testupdateHUDeliveryQuantities_AllocationsHaveNoLUTU_Closed()
	{
		final I_M_ShipmentSchedule schedule = createScheduleWithQtyOrderedLUandQtyOrderedTU(1, 2);

		final I_M_InOut io = createInOut(X_M_InOut.DOCSTATUS_Closed);
		final I_M_InOutLine iol = createInOutLine(io, new BigDecimal(2));

		final I_M_ShipmentSchedule_QtyPicked shipmentScheduleQtyPicked = ShipmentScheduleWithHU
				.ofShipmentScheduleWithoutHu(schedule, new BigDecimal("99"))
				.setM_InOutLine(iol)
				.setM_InOut(io)
				.getShipmentScheduleQtyPicked();

		assertThat(shipmentScheduleQtyPicked.getQtyLU()).as("Wrong QtyLU").isEqualByComparingTo(ONE);
		assertThat(shipmentScheduleQtyPicked.getQtyTU()).as("Wrong QtyTU").isEqualByComparingTo("2");
	}

	@Test
	public void testupdateHUDeliveryQuantities_AllocationsHaveLUTU_1TU()
	{
		final I_M_ShipmentSchedule schedule = createScheduleWithQtyOrderedLUandQtyOrderedTU(1, 1);

		final I_M_InOut io = createInOut(X_M_InOut.DOCSTATUS_Completed);
		final I_M_InOutLine iol = createInOutLine(io, new BigDecimal(-99)); // this doesn't matter. the HUs are counted

		final I_M_HU lu = createHU(typeLU);
		final I_M_HU tu = createHU(typeTU);

		final I_M_ShipmentSchedule_QtyPicked shipmentScheduleQtyPicked = ShipmentScheduleWithHU
				.ofShipmentScheduleQtyPicked(createShipmentScheduleQtyPicked(schedule, iol, lu, tu))
				.setM_InOutLine(iol)
				.setM_InOut(io)
				.getShipmentScheduleQtyPicked();

		assertThat(shipmentScheduleQtyPicked.getQtyLU()).as("Wrong QtyLU").isEqualByComparingTo(ONE);
		assertThat(shipmentScheduleQtyPicked.getQtyTU()).as("Wrong QtyTU").isEqualByComparingTo(ONE);

		// assertThat(iol.getQtyEnteredTU(), comparesEqualTo(BigDecimal.ONE)); iol.getQtyEnteredTU() is not updated by the allocaction
	}

	@Test
	public void testupdateHUDeliveryQuantities_AllocationsHaveLUTU_2TU()
	{
		final I_M_ShipmentSchedule schedule = createScheduleWithQtyOrderedLUandQtyOrderedTU(1, 2);

		final I_M_InOut io = createInOut(X_M_InOut.DOCSTATUS_Completed);
		final I_M_InOutLine iol = createInOutLine(io, new BigDecimal(2)); // this doesn't matter. the HUs are counted

		final I_M_HU lu = createHU(typeLU);
		final I_M_HU tu = createHU(typeTU);

		final I_M_ShipmentSchedule_QtyPicked shipmentScheduleQtyPicked = ShipmentScheduleWithHU
				.ofShipmentScheduleQtyPicked(createShipmentScheduleQtyPicked(schedule, iol, lu, tu))
				.setM_InOutLine(iol)
				.setM_InOut(io)
				.getShipmentScheduleQtyPicked();

		assertThat(shipmentScheduleQtyPicked.getQtyLU()).as("Wrong QtyLU").isEqualByComparingTo(ONE);
		assertThat(shipmentScheduleQtyPicked.getQtyTU()).as("Wrong QtyTU").isEqualByComparingTo(ONE);
	}

	@Test
	public void testupdateHUDeliveryQuantities_LU_With_AggregatedTU()
	{
		final I_M_ShipmentSchedule schedule = createScheduleWithQtyOrderedLUandQtyOrderedTU(1, 48);

		final I_M_InOut io = createInOut(X_M_InOut.DOCSTATUS_Completed);
		final I_M_InOutLine iol = createInOutLine(io, new BigDecimal(2)); // this doesn't matter. the HUs are counted

		final IPair<I_M_HU, I_M_HU> luAndTU = createLUAndTU(48);
		final I_M_HU lu = luAndTU.getLeft();
		final I_M_HU tu = luAndTU.getRight();

		final I_M_ShipmentSchedule_QtyPicked shipmentScheduleQtyPicked = ShipmentScheduleWithHU
				.ofShipmentScheduleQtyPicked(createShipmentScheduleQtyPicked(schedule, iol, lu, tu))
				.setM_InOutLine(iol)
				.setM_InOut(io)
				.getShipmentScheduleQtyPicked();

		assertThat(shipmentScheduleQtyPicked.getQtyLU()).as("Wrong QtyLU").isEqualByComparingTo(ONE);
		assertThat(shipmentScheduleQtyPicked.getQtyTU()).as("Wrong QtyTU").isEqualByComparingTo("48");
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

	private IPair<I_M_HU, I_M_HU> createLUAndTU(final int qtyTU)
	{
		final I_M_HU lu = testSupport.createLU(qtyTU, 5);
		final List<I_M_HU> tus = Services.get(IHandlingUnitsDAO.class).retrieveIncludedHUs(lu);
		final I_M_HU aggregatedTU = CollectionUtils.singleElement(tus);

		return ImmutablePair.of(lu, aggregatedTU);
	}

	private I_M_ShipmentSchedule_QtyPicked createShipmentScheduleQtyPicked(I_M_ShipmentSchedule schedule, I_M_InOutLine iol, final I_M_HU lu, final I_M_HU tu)

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

	private I_M_ShipmentSchedule createScheduleWithQtyOrderedLUandQtyOrderedTU(
			final int qtyOrderedLU,
			final int qtyOrderedTU)
	{
		final I_M_ShipmentSchedule sched = InterfaceWrapperHelper.newInstance(I_M_ShipmentSchedule.class, contextProvider);
		sched.setM_Product_ID(productId.getRepoId());
		sched.setQtyOrdered_LU(new BigDecimal(qtyOrderedLU));
		sched.setQtyOrdered_TU(new BigDecimal(qtyOrderedTU));

		InterfaceWrapperHelper.save(sched);
		return sched;
	}
}
