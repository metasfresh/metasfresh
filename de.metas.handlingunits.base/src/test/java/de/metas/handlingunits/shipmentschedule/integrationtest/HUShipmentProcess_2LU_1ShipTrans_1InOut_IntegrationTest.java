package de.metas.handlingunits.shipmentschedule.integrationtest;

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
import java.util.List;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.adempiere.util.lang.IMutable;
import org.adempiere.util.lang.Mutable;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.junit.Assert;
import org.slf4j.Logger;

import de.metas.handlingunits.expectations.HUsExpectation;
import de.metas.handlingunits.expectations.ShipmentScheduleQtyPickedExpectations;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_ShipmentSchedule;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.inout.IInOutDAO;
import de.metas.logging.LogManager;
import de.metas.shipping.interfaces.I_M_Package;

/**
 * Test case:
 * <ul>
 * <li>split each TU fully to an LU in Verdichtung
 * <li>assume: both TUs end up on a single shipment, on different lines (one per line per shipment schedule)
 * </ul>
 */
public class HUShipmentProcess_2LU_1ShipTrans_1InOut_IntegrationTest
		extends AbstractAggregateShipment2SSchedHUShipmentProcessIntegrationTest
{
	private static final Logger logger = LogManager.getLogger(HUShipmentProcess_2LU_1ShipTrans_1InOut_IntegrationTest.class);

	@Override
	protected void initialize()
	{
		super.initialize();

		// only set to trace if there are problems to debug
		// LogManager.setLoggerLevel(LogManager.getLogger("de.metas.handlingunits.shipmentschedule"), Level.TRACE);
	}

	@Override
	protected void step30_aggregateHUs()
	{
		//
		// Get Picked TUs
		final I_M_HU tuHU1 = afterPick_HUExpectations.huExpectation(0)
				.getCapturedHU();
		final I_M_HU tuHU2 = afterPick_HUExpectations.huExpectation(1)
				.getCapturedHU();
		final I_M_HU tuHU3 = afterPick_HUExpectations.huExpectation(2)
				.getCapturedHU();
		final I_M_HU tuHU4 = afterPick_HUExpectations.huExpectation(3)
				.getCapturedHU();
		final I_M_HU tuHU5 = afterPick_HUExpectations.huExpectation(4)
				.getCapturedHU();
		final I_M_HU tuHU6 = afterPick_HUExpectations.huExpectation(5)
				.getCapturedHU();

		//
		// Get shipment schedules
		final I_M_ShipmentSchedule shipmentSchedule1 = shipmentSchedules.get(0);
		final I_M_ShipmentSchedule shipmentSchedule2 = shipmentSchedules.get(1);

		//
		// Assign TUs to palettes
		final IMutable<I_M_HU> afterAggregation_LU1 = new Mutable<>();
		final IMutable<I_M_HU> tu1 = new Mutable<>();
		final IMutable<I_M_HU> vhu1 = new Mutable<>();

		final IMutable<I_M_HU> afterAggregation_LU2 = new Mutable<>();
		final IMutable<I_M_HU> tu2 = new Mutable<>();
		final IMutable<I_M_HU> vhu2 = new Mutable<>();
		final IMutable<I_M_HU> tu3 = new Mutable<>();
		final IMutable<I_M_HU> vhu3 = new Mutable<>();
		final IMutable<I_M_HU> tu4 = new Mutable<>();
		final IMutable<I_M_HU> vhu4 = new Mutable<>();
		final IMutable<I_M_HU> tu5 = new Mutable<>();
		final IMutable<I_M_HU> vhu5 = new Mutable<>();
		final IMutable<I_M_HU> tu6 = new Mutable<>();
		final IMutable<I_M_HU> vhu6 = new Mutable<>();

		final List<I_M_HU> splitSS1LUs = splitOnLU(tuHU1, pTomato, new BigDecimal("30"));
		final List<I_M_HU> splitSS2LUs = splitOnLU(tuHU2, pSalad, new BigDecimal("10"));
		final I_M_HU splitSS2LU = splitSS2LUs.get(0); // we split full qty on a palette
		helper.joinHUs(huContext, splitSS2LU, tuHU3, tuHU4, tuHU5, tuHU6);

		final List<I_M_HU> allSplitHUs = new ArrayList<>();
		allSplitHUs.addAll(splitSS1LUs);
		allSplitHUs.addAll(splitSS2LUs);

		//
		// Validate split LU/TU/VHUs
		//@formatter:off
		afterAggregation_HUExpectations = new HUsExpectation()
			//
			// LU
			.newHUExpectation()
				.capture(afterAggregation_LU1)
				.huPI(piLU)
				.huStatus(X_M_HU.HUSTATUS_Picked)
				.newHUItemExpectation(piLU_Item)
					//
					// TUs
					.newIncludedHUExpectation()
						.capture(tu1)
						.huPI(piTU)
						.huStatus(X_M_HU.HUSTATUS_Picked)
						.newHUItemExpectation(piTU_Item)
							//
							// VHUs
							.newIncludedVirtualHU()
								.capture(vhu1)
								.newVirtualHUItemExpectation()
									.newItemStorageExpectation()
										.product(pTomato).qty("30").uom(productUOM)
										.endExpectation()
									.endExpectation()
								.endExpectation()
							.endExpectation()
						.endExpectation()
					.endExpectation()
				.endExpectation()
			.newHUExpectation()
				.capture(afterAggregation_LU2)
				.huPI(piLU)
				.huStatus(X_M_HU.HUSTATUS_Picked)
				.newHUItemExpectation(piLU_Item)
					//
					// TUs
					.newIncludedHUExpectation()
						.capture(tu2)
						.huPI(piTU)
						.huStatus(X_M_HU.HUSTATUS_Picked)
						.newHUItemExpectation(piTU_Item)
							//
							// VHUs
							.newIncludedVirtualHU()
								.capture(vhu2)
								.newVirtualHUItemExpectation()
									.newItemStorageExpectation()
										.product(pSalad).qty("10").uom(productUOM)
										.endExpectation()
									.endExpectation()
								.endExpectation()
							.endExpectation()
					.endExpectation()
					.newIncludedHUExpectation()
						.capture(tu3)
						.huPI(piTU)
						.huStatus(X_M_HU.HUSTATUS_Picked)
						.newHUItemExpectation(piTU_Item)
							//
							// VHUs
							.newIncludedVirtualHU()
								.capture(vhu3)
								.newVirtualHUItemExpectation()
									.newItemStorageExpectation()
										.product(pSalad).qty("10").uom(productUOM)
										.endExpectation()
									.endExpectation()
								.endExpectation()
							.endExpectation()
					.endExpectation()
					.newIncludedHUExpectation()
						.capture(tu4)
						.huPI(piTU)
						.huStatus(X_M_HU.HUSTATUS_Picked)
						.newHUItemExpectation(piTU_Item)
							//
							// VHUs
							.newIncludedVirtualHU()
								.capture(vhu4)
								.newVirtualHUItemExpectation()
									.newItemStorageExpectation()
										.product(pSalad).qty("10").uom(productUOM)
										.endExpectation()
									.endExpectation()
								.endExpectation()
							.endExpectation()
					.endExpectation()
					.newIncludedHUExpectation()
						.capture(tu5)
						.huPI(piTU)
						.huStatus(X_M_HU.HUSTATUS_Picked)
						.newHUItemExpectation(piTU_Item)
							//
							// VHUs
							.newIncludedVirtualHU()
								.capture(vhu5)
								.newVirtualHUItemExpectation()
									.newItemStorageExpectation()
										.product(pSalad).qty("10").uom(productUOM)
										.endExpectation()
									.endExpectation()
								.endExpectation()
							.endExpectation()
					.endExpectation()
					.newIncludedHUExpectation()
						.capture(tu6)
						.huPI(piTU)
						.huStatus(X_M_HU.HUSTATUS_Picked)
						.newHUItemExpectation(piTU_Item)
							//
							// VHUs
							.newIncludedVirtualHU()
								.capture(vhu6)
								.newVirtualHUItemExpectation()
									.newItemStorageExpectation()
										.product(pSalad).qty("10").uom(productUOM)
										.endExpectation()
									.endExpectation()
								.endExpectation()
							.endExpectation()
						.endExpectation()
					.endExpectation()
				.endExpectation()
			//
			.assertExpected("split HUs", allSplitHUs);
		//@formatter:on

		//
		// Validate Shipment Schedule assignments for splitHU's VHUs
		//@formatter:off
		afterAggregation_ShipmentScheduleQtyPickedExpectations = new ShipmentScheduleQtyPickedExpectations()
			.newShipmentScheduleQtyPickedExpectation() // 0 - Tomato
				.shipmentSchedule(shipmentSchedule1)
				.lu(afterAggregation_LU1).tu(tu1).vhu(vhu1).qtyPicked("30")
				.endExpectation()
			.newShipmentScheduleQtyPickedExpectation() // 1 - Salad
				.shipmentSchedule(shipmentSchedule2)
				.lu(afterAggregation_LU2).tu(tu2).vhu(vhu2).qtyPicked("10")
				.endExpectation()
			.newShipmentScheduleQtyPickedExpectation() // 2 - Salad
				.shipmentSchedule(shipmentSchedule2)
				.lu(afterAggregation_LU2).tu(tu3).vhu(vhu3).qtyPicked("10")
				.endExpectation()
			.newShipmentScheduleQtyPickedExpectation() // 3 - Salad
				.shipmentSchedule(shipmentSchedule2)
				.lu(afterAggregation_LU2).tu(tu4).vhu(vhu4).qtyPicked("10")
				.endExpectation()
			.newShipmentScheduleQtyPickedExpectation() // 4 - Salad
				.shipmentSchedule(shipmentSchedule2)
				.lu(afterAggregation_LU2).tu(tu5).vhu(vhu5).qtyPicked("10")
				.endExpectation()
			.newShipmentScheduleQtyPickedExpectation() // 5 - Salad
				.shipmentSchedule(shipmentSchedule2)
				.lu(afterAggregation_LU2).tu(tu6).vhu(vhu6).qtyPicked("10")
				.endExpectation()
			.assertExpected("after loading on palette(s)");
		//@formatter:on
	}

	@Override
	protected void step40_addAggregatedHUsToShipperTransportation()
	{
		super.step40_addAggregatedHUsToShipperTransportation();
	}

	@Override
	protected void step50_GenerateShipment()
	{
		super.step50_GenerateShipment();
	}

	@Override
	protected void step50_GenerateShipment_validateGeneratedShipments()
	{
		//
		// Get generated shipment
		Assert.assertEquals("Invalid generated shipments count", 1, generatedShipments.size());
		final I_M_InOut shipment = generatedShipments.get(0);

		//
		// Retrieve generated shipment lines
		final List<I_M_InOutLine> shipmentLines = Services.get(IInOutDAO.class).retrieveLines(shipment);
		Assert.assertEquals("Invalid generated shipment lines count", 3, shipmentLines.size());
		final I_M_InOutLine shipmentLine1 = shipmentLines.get(0);
		final I_M_InOutLine shipmentLine2 = shipmentLines.get(1);
		final I_M_InOutLine shipmentLine3 = shipmentLines.get(2);
		logger.info("shipmentLine1: {}", shipmentLine1);
		logger.info("shipmentLine2: {}", shipmentLine2);
		logger.info("shipmentLine3: {}", shipmentLine3);

		//
		// Revalidate the ShipmentSchedule_QtyPicked expectations,
		// but this time, also make sure the M_InOutLine_ID is set
		//@formatter:off
		afterAggregation_ShipmentScheduleQtyPickedExpectations
			.shipmentScheduleQtyPickedExpectation(0)
				.inoutLine(shipmentLine2)
				.endExpectation()
			.shipmentScheduleQtyPickedExpectation(1)
				.inoutLine(shipmentLine1)
				.endExpectation()
			.shipmentScheduleQtyPickedExpectation(2)
				.inoutLine(shipmentLine1)
				.endExpectation()
			.shipmentScheduleQtyPickedExpectation(3)
				.inoutLine(shipmentLine1)
				.endExpectation()
			.shipmentScheduleQtyPickedExpectation(4)
				.inoutLine(shipmentLine1)
				.endExpectation()
			.shipmentScheduleQtyPickedExpectation(5)
				.inoutLine(shipmentLine3)
				.endExpectation()
			.assertExpected("after shipment generated");
		//@formatter:on
	}

	@Override
	protected void step50_GenerateShipment_validateShipperTransportationAfterShipment()
	{
		//
		// Get LUs Package
		Assert.assertEquals("Invalid generated Aggregated HU packages count", 2, mpackagesForAggregatedHUs.size());

		for (int i = 0; i < generatedShipments.size(); i++)
		{
			final I_M_Package mpackage_AggregatedHU = mpackagesForAggregatedHUs.get(i);

			//
			// Get generated shipment
			final I_M_InOut shipment = generatedShipments.get(i);

			//
			// Shipper Transportation: Make sure LU's M_Package is updated
			{
				InterfaceWrapperHelper.refresh(mpackage_AggregatedHU);
				Assert.assertEquals("Aggregated HU's M_Package does not have the right M_InOut_ID",
						shipment.getM_InOut_ID(), mpackage_AggregatedHU.getM_InOut_ID());
			}
		}
	}
}
