package de.metas.handlingunits.allocation.transfer.impl;

import static org.hamcrest.Matchers.hasXPath;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Node;

import de.metas.handlingunits.HUAssert;
import de.metas.handlingunits.HUXmlConverter;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.shipmentschedule.integrationtest.HUShipmentProcess_2LU_1ShipTrans_1InOut_IntegrationTest;

/*
 * #%L
 * de.metas.handlingunits.base
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

/**
 * Tests the splitting behavior using {@link HUSplitBuilder}.
 * 
 * These tests are inspired by {@link HUShipmentProcess_2LU_1ShipTrans_1InOut_IntegrationTest}.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class LUTUProducerDestinationSplitTests
{
	private LUTUProducerDestinationTestSupport data;

	@Before
	public void init()
	{
		data = new LUTUProducerDestinationTestSupport();
	}

	@Test
	public void testSplitTUontoLUwithOneTU()
	{
		final LUTUProducerDestination lutuProducer = new LUTUProducerDestination();

		lutuProducer.setTUPI(data.piTU_IFCO);
		lutuProducer.setLUPI(null);
		lutuProducer.setLUItemPI(null);
		lutuProducer.setMaxLUs(0);
		lutuProducer.setCreateTUsForRemainingQty(true);

		// one ifco can hold 40kg tomatoes
		data.helper.load(lutuProducer, data.helper.pTomato, new BigDecimal("35"), data.helper.uomKg);

		final List<I_M_HU> createdIFCOs = lutuProducer.getCreatedHUs();
		// guards
		assertThat(createdIFCOs.size(), is(1));
		final Node createdIFCOsXML = HUXmlConverter.toXml("IFCOs", createdIFCOs);
		// System.out.println(HUXmlConverter.toString(createdIFCOsXML));
		assertThat(createdIFCOsXML, hasXPath("/IFCOs/HU-TU_IFCO[1]/Storage[@M_Product_Value='Tomato' and @Qty='35.000' and @C_UOM_Name='Kg']"));

		//
		// now do some splitting
		final List<I_M_HU> splitLUs = new HUSplitBuilder(data.helper.ctx)
				.setHUToSplit(createdIFCOs.get(0))
				.setCUQty(new BigDecimal("35"))
				// LU
				.setLU_M_HU_PI_Item(data.piLU_Item_IFCO)
				.setMaxLUToAllocate(new BigDecimal("1"))
				// TU
				.setTU_M_HU_PI_Item(data.piTU_Item_IFCO)
				.setTUPerLU(new BigDecimal("1"))
				// CU
				.setCUProduct(data.helper.pTomato)
				.setCUPerTU(new BigDecimal("40")) // for starts we are OK with one IFCO to be represented inside out aggregated VHU
				.setCUUOM(data.helper.uomKg)
				//
				.split();

		// Validate splitLUsXML
		final Node splitLUsXML = HUXmlConverter.toXml("SplitLUs", splitLUs);
		// System.out.println(HUXmlConverter.toString(splitLUsXML));

		assertThat(splitLUsXML, hasXPath("count(/SplitLUs/HU-LU_Palet)", is("1")));
		assertThat(splitLUsXML, hasXPath("/SplitLUs/HU-LU_Palet[1]/Storage[@M_Product_Value='Tomato' and @Qty='35.000' and @C_UOM_Name='Kg']"));

		// the LU contains one aggregated HU
		assertThat(splitLUsXML, hasXPath("/SplitLUs/HU-LU_Palet[1]/Item[@ItemType='HA']"));
		assertThat(splitLUsXML, hasXPath("/SplitLUs/HU-LU_Palet[1]/Item[@ItemType='HA']/Storage[@M_Product_Value='Tomato' and @Qty='35.000' and @C_UOM_Name='Kg']"));
		assertThat(splitLUsXML, hasXPath("count(/SplitLUs/HU-LU_Palet[1]/Item[@ItemType='HA']/HU-VirtualPI)", is("1")));
		assertThat(splitLUsXML, hasXPath("count(/SplitLUs/HU-LU_Palet[1]/Item[@ItemType='HA']/HU-VirtualPI/Item[@ItemType='MI'])", is("1")));
		assertThat(splitLUsXML, hasXPath("count(/SplitLUs/HU-LU_Palet[1]/Item[@ItemType='HA']/HU-VirtualPI/Item[@ItemType='PM' and @M_HU_PackingMaterial_Product_Value='IFCO' and @Qty='1'])", is("1")));

		HUAssert.assertAllStoragesAreValid();
	}

	/**
	 * 
	 */
	@Test
	public void testSplitTUontoLUwithTwoTUs()
	{
		final LUTUProducerDestination lutuProducer = new LUTUProducerDestination();

		lutuProducer.setTUPI(data.piTU_IFCO);
		lutuProducer.setLUPI(null);
		lutuProducer.setLUItemPI(null);
		lutuProducer.setMaxLUs(0);
		lutuProducer.setCreateTUsForRemainingQty(true);

		// one ifco can hold 40kg tomatoes
		data.helper.load(lutuProducer, data.helper.pTomato, new BigDecimal("35"), data.helper.uomKg);

		final List<I_M_HU> createdIFCOs = lutuProducer.getCreatedHUs();
		// guards
		assertThat(createdIFCOs.size(), is(1));
		final Node createdIFCOsXML = HUXmlConverter.toXml("IFCOs", createdIFCOs);
		// System.out.println(HUXmlConverter.toString(createdIFCOsXML));
		assertThat(createdIFCOsXML, hasXPath("/IFCOs/HU-TU_IFCO[1]/Storage[@M_Product_Value='Tomato' and @Qty='35.000' and @C_UOM_Name='Kg']"));

		//
		// now do some splitting
		final List<I_M_HU> splitLUs = new HUSplitBuilder(data.helper.ctx)
				.setHUToSplit(createdIFCOs.get(0))
				.setCUQty(new BigDecimal("35"))
				// LU
				.setLU_M_HU_PI_Item(data.piLU_Item_IFCO)
				.setMaxLUToAllocate(new BigDecimal("1"))
				// TU
				.setTU_M_HU_PI_Item(data.piTU_Item_IFCO)
				.setTUPerLU(new BigDecimal("2"))
				// CU
				.setCUProduct(data.helper.pTomato)
				.setCUPerTU(new BigDecimal("20")) // only allow 20kg, so that the 35kg don't fit into one IFCO
				.setCUUOM(data.helper.uomKg)
				//
				.split();

		// Validate splitLUsXML
		final Node splitLUsXML = HUXmlConverter.toXml("SplitLUs", splitLUs);
		// System.out.println(HUXmlConverter.toString(splitLUsXML));

		assertThat(splitLUsXML, hasXPath("count(/SplitLUs/HU-LU_Palet)", Matchers.equalTo("1")));
		assertThat(splitLUsXML, hasXPath("/SplitLUs/HU-LU_Palet[1]/Storage[@M_Product_Value='Tomato' and @Qty='35.000' and @C_UOM_Name='Kg']"));

		// the LU contains one aggregated HU
		assertThat(splitLUsXML, hasXPath("/SplitLUs/HU-LU_Palet[1]/Item[@ItemType='HA']"));
		assertThat(splitLUsXML, hasXPath("/SplitLUs/HU-LU_Palet[1]/Item[@ItemType='HA']/Storage[@M_Product_Value='Tomato' and @Qty='35.000' and @C_UOM_Name='Kg']"));
		assertThat(splitLUsXML, hasXPath("count(/SplitLUs/HU-LU_Palet[1]/Item[@ItemType='HA']/HU-VirtualPI)", Matchers.equalTo("1")));
		assertThat(splitLUsXML, hasXPath("count(/SplitLUs/HU-LU_Palet[1]/Item[@ItemType='HA']/HU-VirtualPI/Item[@ItemType='MI'])", Matchers.equalTo("1")));
		assertThat(splitLUsXML, hasXPath("count(/SplitLUs/HU-LU_Palet[1]/Item[@ItemType='HA']/HU-VirtualPI/Item[@ItemType='PM' and @M_HU_PackingMaterial_Product_Value='IFCO' and @Qty='2'])", is("1")));

		HUAssert.assertAllStoragesAreValid();
	}

}
