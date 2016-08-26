package de.metas.handlingunits.shipmentschedule.integrationtest;

import java.util.List;

import org.adempiere.util.Services;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.junit.Assert;

import de.metas.handlingunits.attribute.strategy.impl.CopyHUAttributeTransferStrategy;
import de.metas.handlingunits.test.misc.builders.HUPIAttributeBuilder;
import de.metas.inout.IInOutDAO;

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
 * Test case:
 * <ul>
 * <li>consider "attribute" it's NOT a template attribute (i.e. it's assigned only to LU and TU)
 * <li>have 1 shipment schedule
 * <li>pick LU1 with attribute's value "same value"
 * <li>pick LU2 with attribute's value "same value"
 * <li>generate shipment
 * <li>expect: one shipment with 1 line for LU1 and LU2
 * <li>expect: attribute is not present in shipment line's ASI
 * </ul>
 * 
 * Same as for {@link HUShipmentProcess_AttributesAggregation_WithSameTemplateAttribute_Test}.
 * 
 * @author metas-dev <dev@metasfresh.com>
 * @task FRESH-578 #275
 */
public class HUShipmentProcess_AttributesAggregation_NoTemplateAttribute_Test extends HUShipmentProcess_AttributesAggregation_Base
{
	@Override
	protected void initialize()
	{
		super.initialize();

		template_huPIAttribute = null;
		tu_huPIAttribute = helper.createM_HU_PI_Attribute(new HUPIAttributeBuilder(attribute)
				.setM_HU_PI(piTU)
				.setUseInASI(true)
				.setTransferStrategyClass(CopyHUAttributeTransferStrategy.class) // does not matter
		);
		lu_huPIAttribute = helper.createM_HU_PI_Attribute(new HUPIAttributeBuilder(attribute)
				.setM_HU_PI(piLU)
				.setUseInASI(true)
				.setTransferStrategyClass(CopyHUAttributeTransferStrategy.class) // does not matter
		);

		lu1_attributeValue = "same value";
		lu2_attributeValue = lu1_attributeValue;
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
		Assert.assertEquals("Invalid generated shipment lines count", 1, shipmentLines.size());
		final I_M_InOutLine shipmentLine1 = shipmentLines.get(0);

		assertShipmentLineDoesNotHaveAttribute(shipmentLine1, attribute);

		//
		// Revalidate the ShipmentSchedule_QtyPicked expectations,
		// but this time, also make sure the M_InOutLine_ID is set
		//@formatter:off
		afterAggregation_ShipmentScheduleQtyPickedExpectations
			.shipmentScheduleQtyPickedExpectation(0)
				.inoutLine(shipmentLine1)
				.endExpectation()
			.shipmentScheduleQtyPickedExpectation(1)
				.inoutLine(shipmentLine1)
				.endExpectation()
			.assertExpected("after shipment generated");
		//@formatter:on
	}
}
