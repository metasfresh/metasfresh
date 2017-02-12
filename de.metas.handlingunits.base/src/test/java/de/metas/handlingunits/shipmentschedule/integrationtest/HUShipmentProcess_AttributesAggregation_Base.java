package de.metas.handlingunits.shipmentschedule.integrationtest;

import java.util.Arrays;

import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.mm.attributes.model.I_M_Attribute;
import org.adempiere.util.Services;
import org.adempiere.util.lang.IMutable;
import org.adempiere.util.lang.Mutable;
import org.compiere.model.I_M_AttributeInstance;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.X_M_Attribute;
import org.junit.Assert;

import de.metas.handlingunits.expectations.HUsExpectation;
import de.metas.handlingunits.expectations.ShipmentScheduleQtyPickedExpectations;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI_Attribute;
import de.metas.handlingunits.model.I_M_ShipmentSchedule;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.model.X_M_HU_PI_Item;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2016 metas GmbH
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

/**
 * Base class for all HU Shipment process tests which are focused how the shipment lines are aggregated based on HU's attributes.
 *
 * @author metas-dev <dev@metasfresh.com>
 * @task FRESH-578 #275
 */
public abstract class HUShipmentProcess_AttributesAggregation_Base extends AbstractHUShipmentProcessIntegrationTest
{
	private ShipmentScheduleQtyPickedExpectations afterPick_ShipmentScheduleQtyPickedExpectations = null;

	protected I_M_Attribute attribute;

	protected I_M_HU_PI_Attribute template_huPIAttribute;
	protected I_M_HU_PI_Attribute tu_huPIAttribute;
	protected I_M_HU_PI_Attribute lu_huPIAttribute;

	protected String lu1_attributeValue;
	protected String lu2_attributeValue;

	@Override
	protected void initialize()
	{
		super.initialize();

		// Logging
		// only set to trace if there are problems to debug
		// LogManager.setLoggerLevel(LogManager.getLogger("de.metas.handlingunits.shipmentschedule"), Level.TRACE);
		// LogManager.setLoggerLevel(LogManager.getLogger(de.metas.handlingunits.attribute.impl.HUTransactionAttributeBuilder.class), Level.TRACE);

		attribute = helper.createM_Attribute("Discriminator", X_M_Attribute.ATTRIBUTEVALUETYPE_StringMax40, true);
	}

	@Override
	protected void step10_createShipmentSchedules()
	{
		shipmentSchedules = Arrays.asList(
				createShipmentSchedule() // shipment schedule 0
		);
	}

	@Override
	protected void step20_pickTUs()
	{
		//
		// Get shipment schedules
		final I_M_ShipmentSchedule shipmentSchedule1 = shipmentSchedules.get(0);

		//
		// Create initial LUs
		final IMutable<I_M_HU> lu1 = new Mutable<>();
		final IMutable<I_M_HU> tu1 = new Mutable<>();
		final IMutable<I_M_HU> vhu1 = new Mutable<>();
		//
		final IMutable<I_M_HU> lu2 = new Mutable<>();
		final IMutable<I_M_HU> tu2 = new Mutable<>();
		final IMutable<I_M_HU> vhu2 = new Mutable<>();
		//
		afterPick_HUExpectations = new HUsExpectation();
		//@formatter:off
		//
		// LU 1
		afterPick_HUExpectations
			.newHUExpectation()
				.capture(lu1)
				.huStatus(X_M_HU.HUSTATUS_Picked)
				.huPI(piLU)
				.bPartner(bpartner)
				.bPartnerLocation(bpartnerLocation)
				// LU 1 attributes
				.attributesExpectations()
					.newAttributeExpectation()
						.attribute(attribute).piAttribute(lu_huPIAttribute).valueString(lu1_attributeValue)
						.endExpectation()
					.endExpectation()
				//
				// LU-TU line
				.newHUItemExpectation()
					.itemType(X_M_HU_PI_Item.ITEMTYPE_HandlingUnit)
					.huPIItem(piLU_Item)
					//
					// TU 1
					.newIncludedHUExpectation()
						.capture(tu1)
						.huStatus(X_M_HU.HUSTATUS_Picked)
						.huPI(piTU)
						//
						// TU-VHU line
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
							.endExpectation()
						.endExpectation()
					.endExpectation()
		;
		//
		// LU 2
		afterPick_HUExpectations
			.newHUExpectation()
				.capture(lu2)
				.huStatus(X_M_HU.HUSTATUS_Picked)
				.huPI(piLU)
				.bPartner(bpartner)
				.bPartnerLocation(bpartnerLocation)
				// LU 2 attributes
				.attributesExpectations()
					.newAttributeExpectation()
						.attribute(attribute).piAttribute(lu_huPIAttribute).valueString(lu2_attributeValue)
						.endExpectation()
					.endExpectation()
				//
				// LU-TU line
				.newHUItemExpectation()
					.itemType(X_M_HU_PI_Item.ITEMTYPE_HandlingUnit)
					.huPIItem(piLU_Item)
					//
					// TU 2
					.newIncludedHUExpectation()
						.capture(tu2)
						.huStatus(X_M_HU.HUSTATUS_Picked)
						.huPI(piTU)
						//
						// TU-VHU line
						.newHUItemExpectation()
							.itemType(X_M_HU_PI_Item.ITEMTYPE_Material)
							.huPIItem(piTU_Item)
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
						.endExpectation()
					.endExpectation()
		;
		//@formatter:on
		afterPick_HUExpectations.createHUs();

		//
		// Assign VHU1 to shipmentSchedule1
		// Assign VHU2 to shipmentSchedule2
		//@formatter:off
		afterPick_ShipmentScheduleQtyPickedExpectations = new ShipmentScheduleQtyPickedExpectations()
			.newShipmentScheduleQtyPickedExpectation()
				.shipmentSchedule(shipmentSchedule1)
				.lu(lu1).tu(tu1).vhu(vhu1).qtyPicked("10")
				.endExpectation()
			.newShipmentScheduleQtyPickedExpectation()
				.shipmentSchedule(shipmentSchedule1)
				.lu(lu2).tu(tu2).vhu(vhu2).qtyPicked("10")
				.endExpectation();
		//@formatter:on
		afterPick_ShipmentScheduleQtyPickedExpectations.createM_ShipmentSchedule_QtyPickeds(helper.getContextProvider());
	}

	@Override
	protected void step30_aggregateHUs()
	{
		// Do nothing...

		afterAggregation_HUExpectations = afterPick_HUExpectations;
		afterAggregation_ShipmentScheduleQtyPickedExpectations = afterPick_ShipmentScheduleQtyPickedExpectations;
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
	protected void step50_GenerateShipment_validateShipperTransportationAfterShipment()
	{
		// TODO: implement the logic
		// //
		// // Get LUs Package
		// Assert.assertEquals("Invalid generated LU packages count", 1, mpackagesForAggregatedHUs.size());
		// final I_M_Package mpackage_TU = mpackagesForAggregatedHUs.get(0);
		//
		// //
		// // Get generated shipment
		// final I_M_InOut shipment = ListUtils.singleElement(generatedShipments);
		//
		// //
		// // Shipper Transportation: Make sure TU's M_Package is updated
		// {
		// InterfaceWrapperHelper.refresh(mpackage_TU);
		// Assert.assertEquals("Aggregated HU's M_Package does not have the right M_InOut_ID",
		// shipment.getM_InOut_ID(), mpackage_TU.getM_InOut_ID());
		// }
	}

	protected void assertShipmentLineASIAttributeValueString(final String valueStringExpected, final I_M_InOutLine inoutLine, final I_M_Attribute attribute)
	{
		final I_M_AttributeSetInstance asi = inoutLine.getM_AttributeSetInstance();
		Assert.assertNotNull("ASI exists for " + inoutLine, asi);

		final I_M_AttributeInstance ai = Services.get(IAttributeDAO.class).retrieveAttributeInstance(asi, attribute.getM_Attribute_ID());
		Assert.assertNotNull("AI exists for " + inoutLine + "'s ASI=" + asi + ", Attribute=" + attribute, ai);

		final String valueStringActual = ai.getValue();

		Assert.assertEquals("Invalid value for " + attribute.getName() + " in " + inoutLine, valueStringExpected, valueStringActual);
	}

	protected void assertShipmentLineDoesNotHaveAttribute(final I_M_InOutLine inoutLine, final I_M_Attribute attribute)
	{
		final I_M_AttributeSetInstance asi = inoutLine.getM_AttributeSetInstance();
		if (asi == null)
		{
			return;
		}
		final I_M_AttributeInstance ai = Services.get(IAttributeDAO.class).retrieveAttributeInstance(asi, attribute.getM_Attribute_ID());
		Assert.assertNull("AI does not exists for " + inoutLine + "'s ASI=" + asi + ", Attribute=" + attribute, ai);
	}
}
