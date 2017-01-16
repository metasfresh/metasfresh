package de.metas.handlingunits.spi.impl;

import static org.hamcrest.Matchers.comparesEqualTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Assume;
import org.junit.Test;

import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_LUTU_Configuration;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_ReceiptSchedule;
import de.metas.handlingunits.model.I_M_ReceiptSchedule_Alloc;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.receiptschedule.impl.ReceiptScheduleHUGenerator;

/**
 * Tests if HUs are correctly generated when {@link I_M_ReceiptSchedule} is generated. Note that the actual HU creation which we test there is done in {@link ReceiptScheduleHUGenerator}.
 *
 * @author tsa
 * @task http://dewiki908/mediawiki/index.php/07451_Palette_capacity_calculation_for_LU-TU_configuration_screwed_%28103309808369%29
 * @see ReceiptScheduleHUGenerator
 *
 */
public class HUReceiptScheduleProducer_CreatePlanningHUs_Test extends AbstractHUReceiptScheduleProducerTest
{
	// Setup data
	private I_M_HU_PI piTU_ToUse = null;
	private BigDecimal qtyTUsPerLU;
	private BigDecimal qtyCUsPerTU;

	/** How many CUs (i.e. products) to order ? */
	private BigDecimal qtyCUsToOrder;
	/** How many TUs to order ? */
	private BigDecimal qtyTUsToOrder;

	/** Expectation: How many LUs are expected to be produced? */
	private int expect_QtyLUs;
	/** Expectation: How many TUs (top level) are expected to be produced? */
	private BigDecimal expect_QtyTUs = BigDecimal.ZERO;
	/** Expectation: LU/TU Configuration: QtyTU */
	private BigDecimal expect_LUTUConfig_QtyTUs;
	/** Expectation: LU/TU Configuration: QtyCU */
	private BigDecimal expect_LUTUConfig_QtyCUs;

	@Test
	public void testCreate_NoLUs_OnePartialTU()
	{
		qtyTUsPerLU = null;
		qtyCUsPerTU = BigDecimal.valueOf(10);

		qtyTUsToOrder = BigDecimal.valueOf(1);
		qtyCUsToOrder = BigDecimal.valueOf(1); // under one TU

		expect_QtyLUs = 0;
		expect_QtyTUs = BigDecimal.valueOf(1);
		expect_LUTUConfig_QtyTUs = qtyTUsToOrder;
		expect_LUTUConfig_QtyCUs = qtyCUsToOrder; // ...instead of QtyCUsPerTU

		executeTest();
	}

	@Test
	public void testCreate_NoLUs_OneFullTU()
	{
		qtyTUsPerLU = null;
		qtyCUsPerTU = BigDecimal.valueOf(10);

		qtyTUsToOrder = BigDecimal.valueOf(1);
		qtyCUsToOrder = qtyCUsPerTU; // 1 TU

		expect_QtyLUs = 0;
		expect_QtyTUs = BigDecimal.valueOf(1);
		expect_LUTUConfig_QtyTUs = qtyTUsToOrder;
		expect_LUTUConfig_QtyCUs = qtyCUsPerTU;

		executeTest();
	}

	@Test
	public void testCreate_NoLUs_OneFullTU_And_OnePartialTU()
	{
		qtyTUsPerLU = null;
		qtyCUsPerTU = BigDecimal.valueOf(10);

		qtyTUsToOrder = BigDecimal.valueOf(2);
		qtyCUsToOrder = BigDecimal.valueOf(10 + 1); // 1TU and 1CU

		expect_QtyLUs = 0;
		expect_QtyTUs = BigDecimal.valueOf(2);
		expect_LUTUConfig_QtyTUs = qtyTUsToOrder;
		expect_LUTUConfig_QtyCUs = qtyCUsPerTU;

		executeTest();
	}

	@Test
	public void testCreate_NoLUs_TwoFullTU()
	{
		qtyTUsPerLU = null;
		qtyCUsPerTU = BigDecimal.valueOf(10);

		qtyTUsToOrder = BigDecimal.valueOf(2);
		qtyCUsToOrder = BigDecimal.valueOf(10 + 10); // 2xTU

		expect_QtyLUs = 0;
		expect_QtyTUs = BigDecimal.valueOf(2);
		expect_LUTUConfig_QtyTUs = qtyTUsToOrder;
		expect_LUTUConfig_QtyCUs = qtyCUsPerTU;

		executeTest();
	}

	@Test
	public void testCreate_OnePartialLU_OnePartialTU()
	{
		qtyTUsPerLU = BigDecimal.valueOf(5);
		qtyCUsPerTU = BigDecimal.valueOf(10);

		qtyTUsToOrder = BigDecimal.valueOf(1);
		qtyCUsToOrder = BigDecimal.valueOf(1); // under one TU

		expect_QtyLUs = 1;
		expect_QtyTUs = BigDecimal.valueOf(0); // no top level TUs
		expect_LUTUConfig_QtyTUs = qtyTUsToOrder; // ...instead of QtyTUsPerLU
		expect_LUTUConfig_QtyCUs = qtyCUsToOrder; // ...instead of QtyCUsPerTU

		executeTest();
	}

	@Test
	public void testCreate_OnePartialLU()
	{
		qtyTUsPerLU = BigDecimal.valueOf(5);
		qtyCUsPerTU = BigDecimal.valueOf(10);

		qtyTUsToOrder = BigDecimal.valueOf(1);
		qtyCUsToOrder = qtyTUsToOrder.multiply(qtyCUsPerTU); // full TUs

		expect_QtyLUs = 1;
		expect_QtyTUs = BigDecimal.valueOf(0); // no top level TUs
		expect_LUTUConfig_QtyTUs = qtyTUsToOrder; // ...instead of QtyTUsPerLU
		expect_LUTUConfig_QtyCUs = qtyCUsPerTU;

		executeTest();
	}

	@Test
	public void testCreate_OneFullLU_OnePartialLU()
	{
		qtyTUsPerLU = BigDecimal.valueOf(5);
		qtyCUsPerTU = BigDecimal.valueOf(10);

		qtyTUsToOrder = BigDecimal.valueOf(5 + 1);
		qtyCUsToOrder = qtyTUsToOrder.multiply(qtyCUsPerTU); // full TUs

		expect_QtyLUs = 2;
		expect_QtyTUs = BigDecimal.valueOf(0); // no top level TUs
		expect_LUTUConfig_QtyTUs = qtyTUsPerLU;
		expect_LUTUConfig_QtyCUs = qtyCUsPerTU;

		executeTest();
	}

	@Test
	public void testCreate_OneFullLU_OnePartialLU_With_OnePartialTU()
	{
		qtyTUsPerLU = BigDecimal.valueOf(5);
		qtyCUsPerTU = BigDecimal.valueOf(10);

		qtyTUsToOrder = BigDecimal.valueOf(5 + 1);
		qtyCUsToOrder = BigDecimal.valueOf(5 * 10 + 1);

		expect_QtyLUs = 2;
		expect_QtyTUs = BigDecimal.valueOf(0); // no top level TUs
		expect_LUTUConfig_QtyTUs = qtyTUsPerLU;
		expect_LUTUConfig_QtyCUs = qtyCUsPerTU;

		executeTest();
	}

	@Test
	public void testCreate_TwoFullLUs()
	{
		qtyTUsPerLU = BigDecimal.valueOf(5);
		qtyCUsPerTU = BigDecimal.valueOf(10);

		qtyTUsToOrder = BigDecimal.valueOf(5 + 5);
		qtyCUsToOrder = qtyTUsToOrder.multiply(qtyCUsPerTU); // full TUs

		expect_QtyLUs = 2;
		expect_QtyTUs = BigDecimal.valueOf(0); // no top level TUs
		expect_LUTUConfig_QtyTUs = qtyTUsPerLU;
		expect_LUTUConfig_QtyCUs = qtyCUsPerTU;

		executeTest();
	}

	@Test
	public void testCreate_VirtualTUs()
	{
		qtyTUsPerLU = null;
		piTU_ToUse = huTestHelper.huDefVirtual;
		qtyCUsPerTU = null; // virtual PI

		qtyTUsToOrder = BigDecimal.valueOf(1);
		qtyCUsToOrder = BigDecimal.valueOf(100);

		expect_QtyLUs = 0;
		expect_QtyTUs = BigDecimal.valueOf(1);
		expect_LUTUConfig_QtyTUs = qtyTUsToOrder;
		expect_LUTUConfig_QtyCUs = qtyCUsToOrder; // ...instead of QtyCUsPerTU

		executeTest();
	}

	@Test
	public void testCreate_OneLU_VirtualTU()
	{
		qtyTUsPerLU = BigDecimal.valueOf(1);
		piTU_ToUse = huTestHelper.huDefVirtual;
		qtyCUsPerTU = null; // virtual PI

		qtyTUsToOrder = BigDecimal.valueOf(1);
		qtyCUsToOrder = BigDecimal.valueOf(100);

		expect_QtyLUs = 1;
		expect_QtyTUs = BigDecimal.valueOf(0);
		expect_LUTUConfig_QtyTUs = qtyTUsToOrder;
		expect_LUTUConfig_QtyCUs = qtyCUsToOrder; // ...instead of QtyCUsPerTU

		executeTest();
	}

	/**
	 * Perform the actual test based on setup we made.
	 */
	private void executeTest()
	{
		//
		// Setup: create LU and TU PIs
		setupLUandTUPackingInstructions(piTU_ToUse, qtyTUsPerLU, qtyCUsPerTU);

		//
		// Create Order
		final I_C_Order order = createOrder(warehouse1);
		final I_C_OrderLine orderLine = createOrderLine(order, qtyCUsToOrder, qtyTUsToOrder);

		//
		// Generate receipt schedule
		final I_M_ReceiptSchedule receiptSchedule;
		{
			final List<de.metas.inoutcandidate.model.I_M_ReceiptSchedule> previousReceiptSchedules = Collections.emptyList();
			final List<de.metas.inoutcandidate.model.I_M_ReceiptSchedule> receiptSchedules = orderReceiptScheduleProducer.createOrUpdateReceiptSchedules(order, previousReceiptSchedules);

			assertThat(receiptSchedules.size(), is(1));
			receiptSchedule = InterfaceWrapperHelper.create(receiptSchedules.get(0), I_M_ReceiptSchedule.class);

			assertOrderMatches(receiptSchedule, order);
			assertOrderLineMatches(receiptSchedule, orderLine);
		}

		//
		// Validate generated LU/TU configuration
		assertValidLUTUConfiguration(receiptSchedule);

		//
		// Get assigned LUs and TUs
		final List<I_M_HU> assignedLUs;
		final List<I_M_HU> assignedTUs;
		{
			final List<I_M_HU> assignedHUs = huAssignmentDAO.retrieveTopLevelHUsForModel(receiptSchedule);
			assignedLUs = removeLUs(assignedHUs);
			assignedTUs = assignedHUs; // after removing LUs, the remaining ones are top level TUs or VHUs
		}

		// Retrieve all allocations order by ID (creation sequence)
		final List<I_M_ReceiptSchedule_Alloc> rsAllocs = huReceiptScheduleDAO.retrieveHandlingUnitAllocations(receiptSchedule, ITrx.TRXNAME_None);
		BigDecimal huQtyNotAllocatedTotal = receiptSchedule.getQtyOrdered();

		System.out.println(de.metas.handlingunits.HUXmlConverter.toString(de.metas.handlingunits.HUXmlConverter.toXml("assignedLUs", assignedLUs)));

		//
		// Validate LUs against allocations
		assertThat("Invalid LUs assigned count", assignedLUs.size(), is(expect_QtyLUs));
		{
			// Iterate LUs and validate them
			for (final I_M_HU luHU : assignedLUs)
			{
				// Validate LU
				Assert.assertTrue("Invalid LU's UnitType", handlingUnitsBL.isLoadingUnit(luHU));
				Assert.assertEquals("Invalid LU's LU/TU Configuration", receiptSchedule.getM_HU_LUTU_Configuration(), luHU.getM_HU_LUTU_Configuration());

				final List<I_M_HU> tuHUs = handlingUnitsDAO.retrieveIncludedHUs(luHU);
				huQtyNotAllocatedTotal = assertValidTUs(rsAllocs, huQtyNotAllocatedTotal, luHU, tuHUs);
			}
		}

		//
		// Validate top level TUs/VHUs against allocations
		{
			Assert.assertEquals("Invalid TUs assigned count", expect_QtyTUs.intValueExact(), assignedTUs.size());
			huQtyNotAllocatedTotal = assertValidTUs(rsAllocs, huQtyNotAllocatedTotal, null, assignedTUs);
		}

		//
		// Make sure we evaluated each allocation
		Assert.assertEquals("No other allocations shall exist", Collections.<I_M_ReceiptSchedule_Alloc> emptyList(), rsAllocs);
		Assert.assertThat("Qty not allocated shall be zero at this point", huQtyNotAllocatedTotal, Matchers.comparesEqualTo(BigDecimal.ZERO));
	}

	/**
	 * Removes LUs from given HU list.
	 *
	 * @param hus
	 * @return LUs list (which were removed)
	 */
	private List<I_M_HU> removeLUs(final List<I_M_HU> hus)
	{
		final List<I_M_HU> luHUs = new ArrayList<I_M_HU>();
		for (final Iterator<I_M_HU> it = hus.iterator(); it.hasNext();)
		{
			final I_M_HU hu = it.next();
			if (handlingUnitsBL.isLoadingUnit(hu))
			{
				it.remove();
				luHUs.add(hu);
			}
		}

		return luHUs;
	}

	private BigDecimal assertValidTUs(
			final List<I_M_ReceiptSchedule_Alloc> rsAllocs,
			BigDecimal huQtyNotAllocatedTotal,
			final I_M_HU luHU,
			final List<I_M_HU> tuHUs)
	{
		// Iterate TUs and validate them
		for (final I_M_HU tuHU : tuHUs)
		{
			// Validate TU
			assertTrue("Invalid TU's UnitType", handlingUnitsBL.isTransportUnitOrVirtual(tuHU)); // assert valid TU or VHU

			//
			// Retrieve VHUs
			final List<I_M_HU> vhus;
			// Case: our TU is a top level VHU or a VHU on LU
			if (handlingUnitsBL.isVirtual(tuHU))
			{
				vhus = Collections.singletonList(tuHU);
			}
			// Case: our TU is really a TU
			else
			{
				vhus = handlingUnitsDAO.retrieveIncludedHUs(tuHU);
			}

			//
			// Validate Receipt schedule allocation (on LU/TU/VHU level)
			for (final I_M_HU vhu : vhus)
			{
				// Validate VHU
				assertTrue("Invalid VHU's UnitType", handlingUnitsBL.isVirtual(vhu));

				// Validate VHU allocation
				final I_M_ReceiptSchedule_Alloc alloc = removeReceiptScheduleAllocFromList(rsAllocs, luHU, tuHU, vhu);
				final BigDecimal huQtyAllocatedExpected = qtyCUsPerTU == null ? huQtyNotAllocatedTotal : qtyCUsPerTU.min(huQtyNotAllocatedTotal);
				final BigDecimal huQtyAllocatedActual = alloc.getHU_QtyAllocated();
				assertThat("Invalid HU_QtyAllocated", huQtyAllocatedActual, comparesEqualTo(huQtyAllocatedExpected));

				// Update remaining unallocated qty
				huQtyNotAllocatedTotal = huQtyNotAllocatedTotal.subtract(huQtyAllocatedActual);

				//
				// Validate TU's LU/TU Configuration
				final I_M_HU_LUTU_Configuration lutuConfigurationExpected;
				if (handlingUnitsBL.isTopLevel(tuHU))
				{
					// shall get it from receipt schedule
					final I_M_ReceiptSchedule receiptSchedule = InterfaceWrapperHelper.create(alloc.getM_ReceiptSchedule(), I_M_ReceiptSchedule.class);
					lutuConfigurationExpected = receiptSchedule.getM_HU_LUTU_Configuration();
				}
				else
				{
					// NOTE: only top level HUs have the configuration is not set (not good, not bad, but this is how it is)
					lutuConfigurationExpected = null;
				}
				assertThat("Invalid LU's LU/TU Configuration", tuHU.getM_HU_LUTU_Configuration(), is(lutuConfigurationExpected));
			}
		}
		return huQtyNotAllocatedTotal;
	}

	private I_M_ReceiptSchedule_Alloc removeReceiptScheduleAllocFromList(final List<I_M_ReceiptSchedule_Alloc> rsAllocs,
			final I_M_HU luHU, final I_M_HU tuHU, final I_M_HU vhu)
	{
		final int luHUId = luHU == null ? -1 : luHU.getM_HU_ID();
		final int tuHUId = tuHU == null ? -1 : tuHU.getM_HU_ID();
		final int vhuId = vhu == null ? -1 : vhu.getM_HU_ID();

		I_M_ReceiptSchedule_Alloc allocFound = null;

		for (final Iterator<I_M_ReceiptSchedule_Alloc> it = rsAllocs.iterator(); it.hasNext();)
		{
			final I_M_ReceiptSchedule_Alloc alloc = it.next();

			final int alloc_luHUId = alloc.getM_LU_HU_ID() <= 0 ? -1 : alloc.getM_LU_HU_ID();
			final int alloc_tuHUId = alloc.getM_TU_HU_ID() <= 0 ? -1 : alloc.getM_TU_HU_ID();
			final int alloc_vhuId = alloc.getVHU_ID() <= 0 ? -1 : alloc.getVHU_ID();

			if (luHUId != alloc_luHUId)
			{
				continue;
			}
			if (tuHUId != alloc_tuHUId)
			{
				continue;
			}
			if (vhuId != alloc_vhuId)
			{
				continue;
			}

			//
			Assert.assertNull("Only one allocation shall exist for LU=" + luHU + ", TU=" + tuHU, allocFound);
			it.remove();
			allocFound = alloc;
		}

		Assert.assertNotNull("No allocation found for LU=" + luHU + ", TU=" + tuHU, allocFound);
		return allocFound;
	}

	/**
	 * Verifies the M_HU_LUTU_Configuration of the given {@code receiptSchedule}.
	 * 
	 * @param receiptSchedule
	 */
	private void assertValidLUTUConfiguration(final I_M_ReceiptSchedule receiptSchedule)
	{
		final I_M_HU_LUTU_Configuration lutuConfiguration = receiptSchedule.getM_HU_LUTU_Configuration();
		Assert.assertNotNull("Receipt schedule shall have a LU/TU configuration set", lutuConfiguration);

		Assert.assertEquals("Invalid LU/TU Configuration - LU PI", piLU, lutuConfiguration.getM_LU_HU_PI());
		Assert.assertEquals("Invalid LU/TU Configuration - TU PI", piTU, lutuConfiguration.getM_TU_HU_PI());
		Assert.assertEquals("Invalid LU/TU Configuration - Product", product, lutuConfiguration.getM_Product());
		Assert.assertEquals("Invalid LU/TU Configuration - UOM", productUOM, lutuConfiguration.getC_UOM());

		Assert.assertEquals("Invalid LU/TU Configuration - IsInfiniteQtyCU", false, lutuConfiguration.isInfiniteQtyCU());
		Assert.assertEquals("Invalid LU/TU Configuration - QtyCU", expect_LUTUConfig_QtyCUs, lutuConfiguration.getQtyCU().setScale(0));
		Assert.assertEquals("Invalid LU/TU Configuration - IsInfiniteQtyTU", false, lutuConfiguration.isInfiniteQtyTU());
		Assert.assertEquals("Invalid LU/TU Configuration - QtyTU", expect_LUTUConfig_QtyTUs, lutuConfiguration.getQtyTU().setScale(0));
		Assert.assertEquals("Invalid LU/TU Configuration - IsInfiniteQtyLU", false, lutuConfiguration.isInfiniteQtyLU());
		Assert.assertEquals("Invalid LU/TU Configuration - QtyLU", expect_QtyLUs, lutuConfiguration.getQtyLU().intValue());

		Assert.assertEquals("Invalid LU/TU Configuration - C_BPartner_ID", receiptSchedule.getC_BPartner_ID(), lutuConfiguration.getC_BPartner_ID());
		Assert.assertEquals("Invalid LU/TU Configuration - C_BPartner_Location_ID", receiptSchedule.getC_BPartner_Location_ID(), lutuConfiguration.getC_BPartner_Location_ID());

		Assert.assertEquals("Invalid LU/TU Configuration - M_Locator",
				receiptScheduleBL.getM_Locator_Effective(receiptSchedule),
				lutuConfiguration.getM_Locator());

		Assert.assertEquals("Invalid LU/TU Configuration - HUStatus", X_M_HU.HUSTATUS_Planning, lutuConfiguration.getHUStatus());
	}

	@Override
	public void createReceiptSchedulesTestMorePrev()
	{
		Assume.assumeTrue("Not applicable", false);
	}

	@Override
	public void createReceiptSchedulesTestOnePrev()
	{
		Assume.assumeTrue("Not applicable", false);
	}

	@Override
	public void createReceiptSchedulesTestNullPrev()
	{
		Assume.assumeTrue("Not applicable", false);
	}

	@Override
	public void dataInOrderValidateTest()
	{
		Assume.assumeTrue("Not applicable", false);
	}
}
