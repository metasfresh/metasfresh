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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.util.Arrays;
import java.util.List;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.adempiere.util.collections.ListUtils;
import org.adempiere.util.lang.IMutable;
import org.adempiere.util.lang.Mutable;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.junit.Assert;

import de.metas.handlingunits.expectations.HUsExpectation;
import de.metas.handlingunits.expectations.ShipmentScheduleQtyPickedExpectations;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_ShipmentSchedule;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.model.X_M_HU_PI_Item;
import de.metas.inout.IInOutDAO;
import de.metas.shipping.interfaces.I_M_Package;

/**
 * Test case:
 * <ul>
 * <li>create a TU with 2 VHUs, each VHU with 10items
 * <li>assign each VHU to a different shipment schedule
 * <li>ship the TU directly as it is. That's the main point of this test, i.e. be able to ship top level TUs.
 * <li>assume: first VHU is assigned to first shipment schedule, second VHU to second shipment schedule
 * </ul>
 *
 * @author tsa
 * @task http://dewiki908/mediawiki/index.php/08715_Kennzeichen_Gebinde_ausblenden_auf_Lsch._%28Gastro%29%2C_TU_auf_LKW_Verdichtung_%28109809505901%29
 */
public class HUShipmentProcess_1TUwith2VHU_ShipDirectly_IntegrationTest extends AbstractHUShipmentProcessIntegrationTest
{
	private ShipmentScheduleQtyPickedExpectations afterPick_ShipmentScheduleQtyPickedExpectations = null;

	@Override
	protected void step10_createShipmentSchedules()
	{
		shipmentSchedules = Arrays.asList(
				createShipmentSchedule(), // shipment schedule 0
				createShipmentSchedule() // shipment schedule 1
				);
	}

	@Override
	protected void step20_pickTUs()
	{
		//
		// Get shipment schedules
		final I_M_ShipmentSchedule shipmentSchedule1 = shipmentSchedules.get(0);
		final I_M_ShipmentSchedule shipmentSchedule2 = shipmentSchedules.get(1);

		//
		// Create initial TU
		final IMutable<I_M_HU> tu = new Mutable<>();
		final IMutable<I_M_HU> vhu1 = new Mutable<>();
		final IMutable<I_M_HU> vhu2 = new Mutable<>();
		//@formatter:off
		afterPick_HUExpectations = new HUsExpectation()
			//
			// TU
			.newHUExpectation()
				.capture(tu)
				.huStatus(X_M_HU.HUSTATUS_Picked)
				.huPI(piTU)
				.bPartner(bpartner)
				.bPartnerLocation(bpartnerLocation)
				.newHUItemExpectation()
					.itemType(X_M_HU_PI_Item.ITEMTYPE_Material)
					.huPIItem(piTU_Item)
					//
					// VHU 1
					.newIncludedVirtualHU()
						.capture(vhu1)
						.huStatus(X_M_HU.HUSTATUS_Picked)
						.newVirtualHUItemExpectation()
							.newItemStorageExpectation()
								.product(product).uom(productUOM).qty("10")
								.endExpectation()
							.endExpectation()
						.endExpectation()
					//
					// VHU 2
					.newIncludedVirtualHU()
						.capture(vhu2)
						.huStatus(X_M_HU.HUSTATUS_Picked)
						.newVirtualHUItemExpectation()
							.newItemStorageExpectation()
								.product(product).uom(productUOM).qty("10")
								.endExpectation()
							.endExpectation()
						.endExpectation()
					.endExpectation()
				.endExpectation();
		//@formatter:on
		afterPick_HUExpectations.createHUs();

		//
		// Assign VHU1 to shipmentSchedule1
		// Assign VHU2 to shipmentSchedule2
		//@formatter:off
		afterPick_ShipmentScheduleQtyPickedExpectations = new ShipmentScheduleQtyPickedExpectations()
			.newShipmentScheduleQtyPickedExpectation()
				.shipmentSchedule(shipmentSchedule1)
				.noLU().tu(tu).vhu(vhu1).qtyPicked("10")
				.endExpectation()
			.newShipmentScheduleQtyPickedExpectation()
				.shipmentSchedule(shipmentSchedule2)
				.noLU().tu(tu).vhu(vhu2).qtyPicked("10")
				.endExpectation();
		//@formatter:on
		afterPick_ShipmentScheduleQtyPickedExpectations.createM_ShipmentSchedule_QtyPickeds(helper.getContextProvider());
	}

	@Override
	protected void step30_aggregateHUs()
	{
		// Do nothing...

		this.afterAggregation_HUExpectations = this.afterPick_HUExpectations;
		this.afterAggregation_ShipmentScheduleQtyPickedExpectations = this.afterPick_ShipmentScheduleQtyPickedExpectations;
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
		// We expect to have 2 shipment lines, not because we have 2 shipment schedules, but because we have 2 order lines
		final List<I_M_InOutLine> shipmentLines = Services.get(IInOutDAO.class).retrieveLines(shipment);
		Assert.assertEquals("Invalid generated shipment lines count", 2, shipmentLines.size());
		final I_M_InOutLine shipmentLine1 = shipmentLines.get(0);
		final I_M_InOutLine shipmentLine2 = shipmentLines.get(1);

		//
		// Revalidate the ShipmentSchedule_QtyPicked expectations,
		// but this time, also make sure the M_InOutLine_ID is set
		//@formatter:off
		afterAggregation_ShipmentScheduleQtyPickedExpectations
			.shipmentScheduleQtyPickedExpectation(0)
				.inoutLine(shipmentLine1)
				.endExpectation()
			.shipmentScheduleQtyPickedExpectation(1)
				.inoutLine(shipmentLine2)
				.endExpectation()
			.assertExpected("after shipment generated");
		//@formatter:on
	}

	@Override
	protected void step50_GenerateShipment_validateShipperTransportationAfterShipment()
	{
		//
		// Get LUs Package
		Assert.assertEquals("Invalid generated LU packages count", 1, mpackagesForAggregatedHUs.size());
		final I_M_Package mpackage_TU = mpackagesForAggregatedHUs.get(0);

		//
		// Get generated shipment
		final I_M_InOut shipment = ListUtils.singleElement(generatedShipments);

		//
		// Shipper Transportation: Make sure TU's M_Package is updated
		{
			InterfaceWrapperHelper.refresh(mpackage_TU);
			Assert.assertEquals("Aggregated HU's M_Package does not have the right M_InOut_ID",
					shipment.getM_InOut_ID(), mpackage_TU.getM_InOut_ID());
		}
	}

}
