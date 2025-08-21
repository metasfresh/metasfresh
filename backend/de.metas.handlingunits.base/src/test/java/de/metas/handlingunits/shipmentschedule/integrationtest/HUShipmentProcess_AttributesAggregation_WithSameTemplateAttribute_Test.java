package de.metas.handlingunits.shipmentschedule.integrationtest;

import de.metas.handlingunits.attribute.strategy.impl.CopyHUAttributeTransferStrategy;
import de.metas.handlingunits.test.misc.builders.HUPIAttributeBuilder;
import de.metas.inout.IInOutDAO;
import de.metas.inoutcandidate.model.I_M_IolCandHandler;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule_AttributeConfig;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.mm.attributes.api.impl.AttributesTestHelper;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.X_M_Attribute;
import org.junit.jupiter.api.Assertions;

import java.util.List;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;

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
 * <li>consider "attribute" it's a template attribute, with UseInASI=Yes
 * <li>have 1 shipment schedule
 * <li>pick LU1 with attribute's value "same value"
 * <li>pick LU2 with attribute's value "same value"
 * <li>generate shipment
 * <li>expect: one shipment with 1 line for LU1 and LU2
 * <li>expect: LU1/LU2's attribute is copied to shipment line's ASI
 * </ul>
 *
 * @author metas-dev <dev@metasfresh.com>
 * @task FRESH-578 #275
 */
public class HUShipmentProcess_AttributesAggregation_WithSameTemplateAttribute_Test extends HUShipmentProcess_AttributesAggregation_Base
{
	@Override
	protected void initialize()
	{
		super.initialize();

		template_huPIAttribute = helper.createM_HU_PI_Attribute(HUPIAttributeBuilder.newInstance(attribute)
				.setM_HU_PI(helper.huDefNone)
				.setUseInASI(true)
		// .setTransferStrategyClass() // does not matter
		); // assign it to template
		tu_huPIAttribute = helper.createM_HU_PI_Attribute(HUPIAttributeBuilder.newInstance(attribute)
				.setM_HU_PI(piTU)
				.setUseInASI(true)
		// .setTransferStrategyClass() // does not matter
		);
		lu_huPIAttribute = helper.createM_HU_PI_Attribute(HUPIAttributeBuilder.newInstance(attribute)
				.setM_HU_PI(piLU)
				.setUseInASI(true)
				.setTransferStrategyClass(CopyHUAttributeTransferStrategy.class) // make sure it will copied to ASI
		);

		lu1_attributeValue = "same value";
		lu2_attributeValue = lu1_attributeValue;
	}

	@Override
	protected void initializeAttributeConfig(@NonNull final I_M_IolCandHandler handlerRecord)
	{
		this.attribute = new AttributesTestHelper().createM_Attribute("Discriminator", X_M_Attribute.ATTRIBUTEVALUETYPE_StringMax40, true);


		final I_M_ShipmentSchedule_AttributeConfig attributeConfigRecord = newInstance(I_M_ShipmentSchedule_AttributeConfig.class);
		attributeConfigRecord.setM_Attribute_ID(attribute.getM_Attribute_ID());
		attributeConfigRecord.setOnlyIfInReferencedASI(false);
		attributeConfigRecord.setM_IolCandHandler(handlerRecord);
		save(attributeConfigRecord);
	}

	@Override
	protected void step50_GenerateShipment_validateGeneratedShipments()
	{
		//
		// Get generated shipment
		Assertions.assertEquals( 1,  generatedShipments.size(), "Invalid generated shipments count");
		final I_M_InOut shipment = generatedShipments.getFirst();

		//
		// Retrieve generated shipment lines
		final List<I_M_InOutLine> shipmentLines = Services.get(IInOutDAO.class).retrieveLines(shipment);
		Assertions.assertEquals( 1,  shipmentLines.size(), "Invalid generated shipment lines count");
		final I_M_InOutLine shipmentLine1 = shipmentLines.getFirst();

		// this shall work because we implemeted initializeAttributeConfig
		assertShipmentLineASIAttributeValueString(lu1_attributeValue, shipmentLine1, attribute);
		assertShipmentLineASIAttributeValueString(lu2_attributeValue, shipmentLine1, attribute);

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
