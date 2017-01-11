package de.metas.handlingunits.allocation.split.impl;

import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.util.List;

import org.compiere.model.I_M_Transaction;
import org.compiere.model.X_M_Transaction;
import org.hamcrest.Matchers;
import static org.hamcrest.Matchers.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Node;

import de.metas.handlingunits.HUAssert;
import de.metas.handlingunits.HUTestHelper;
import de.metas.handlingunits.HUXmlConverter;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.attribute.storage.IAttributeStorageFactory;
import de.metas.handlingunits.model.I_M_HU;

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

public class LUTUProducerDestinationTransferTests
{
	private LUTUProducerDestinationTestSupport data;

	@Before
	public void init()
	{
		data = new LUTUProducerDestinationTestSupport();
	}

	@Test
	public void useCase2()
	{
		final List<I_M_HU> huPalets;
		
		//
		// load 23 kg of tomatoes into huPalets
		{
			final LUTUProducerDestination lutuProducer = mkLUTUProducerDestination();

			data.helper.load(lutuProducer, data.helper.pTomato, new BigDecimal("23"), data.helper.uomKg);
			huPalets = lutuProducer.getCreatedHUs();

			final IAttributeStorageFactory attrStorageFactory = data.helper.getHUContext().getHUAttributeStorageFactory();
			final IAttributeStorage huPalet1_Attrs = attrStorageFactory.getAttributeStorage(huPalets.get(0));
			huPalet1_Attrs.setValue(data.helper.attr_CountryMadeIn, HUTestHelper.COUNTRYMADEIN_DE);

			//
			// Validate HUs
			{
				final Node huPaletsXML = HUXmlConverter.toXml("Palets", huPalets);
				// System.out.println("" + HUXmlConverter.toString(huPaletsXML));

				// loaded 23kg tomatoes into a LU with TUs that can hold 40kg each => expecting one LU
				assertThat(huPaletsXML, hasXPath("count(/Palets/HU-LU_Palet)", Matchers.equalTo("1")));
				assertThat(huPaletsXML, hasXPath("/Palets/HU-LU_Palet[1]/Storage[@M_Product_Value='Tomato' and @Qty='23.000' and @C_UOM_Name='Kg']"));

				// the LU contains one aggregated HU
				assertThat(huPaletsXML, hasXPath("/Palets/HU-LU_Palet[1]/Item[@ItemType='HA']"));
				assertThat(huPaletsXML, hasXPath("/Palets/HU-LU_Palet[1]/Item[@ItemType='HA']/Storage[@M_Product_Value='Tomato' and @Qty='23.000' and @C_UOM_Name='Kg']"));
				assertThat(huPaletsXML, hasXPath("count(/Palets/HU-LU_Palet[1]/Item[@ItemType='HA']/HU-VirtualPI)", Matchers.equalTo("1")));

				HUAssert.assertAllStoragesAreValid();
			}
		}
		
		//
		// Transfer 17 items from huPalets to new Bags
		final LUTUProducerDestination lutuProducer = mkLUTUProducerDestination();
		
		// no palets allowed; the bags shall be on the top level
		lutuProducer.setTUPI(data.piTU_Bag);
		lutuProducer.setLUPI(null);
		lutuProducer.setLUItemPI(null);
		lutuProducer.setMaxLUs(0);
		lutuProducer.setMaxTUsForRemainingQty(4); // allow four max; we will expect and verify 3
		lutuProducer.setCreateTUsForRemainingQty(true); 
		
		data.helper.transferMaterialToNewHUs(huPalets,
				lutuProducer,
				new BigDecimal("17"),
				data.helper.pTomato,
				data.helper.uomKg,
				data.piTU_Bag);

		final List<I_M_HU> huBags = lutuProducer.getCreatedHUs();

		// Validate Palets after moving to 17 to Bags
		{
			final Node huPaletsXML = HUXmlConverter.toXml("Palets", huPalets);
			// System.out.println("" + HUXmlConverter.toString(huPaletsXML));

			// we had 23, so 23-17=6 should be left
			assertThat(huPaletsXML, Matchers.hasXPath("count(/Palets/HU-LU_Palet)", Matchers.equalTo("1")));
			assertThat(huPaletsXML, Matchers.hasXPath("/Palets/HU-LU_Palet[1]/Storage[@M_Product_Value='Tomato' and @Qty='6.000' and @C_UOM_Name='Kg']"));

			assertThat(huPaletsXML, Matchers.hasXPath("/Palets/HU-LU_Palet[1]/Item[@ItemType='HA']"));
			assertThat(huPaletsXML, Matchers.hasXPath("/Palets/HU-LU_Palet[1]/Item[@ItemType='HA']/Storage[@M_Product_Value='Tomato' and @Qty='6.000' and @C_UOM_Name='Kg']"));
			assertThat(huPaletsXML, Matchers.hasXPath("count(/Palets/HU-LU_Palet[1]/Item[@ItemType='HA']/HU-VirtualPI)", Matchers.equalTo("1")));

			HUAssert.assertAllStoragesAreValid();
		}
		//
		// Validate Bags after transferring 17 items to it
		{
			final Node huBagsXML = HUXmlConverter.toXml("Bags", huBags);
			System.out.println("" + HUXmlConverter.toString(huBagsXML));
			
			assertThat(huBagsXML, Matchers.hasXPath("count(/Bags/HU-TU_Bag)", Matchers.equalTo("3"))); // one bag can hold 8 kg tomatoes, to there should be 3 bags for our 17 kg tomatoes
			
			// important: 
			// actually, the aggregation feature is not used because there are no lower-level HUs such as IFCOs that would need to be represented
			assertThat(huBagsXML, not(hasXPath("/Bags/HU-TU_Bag[1]/Item[@ItemType='HA']")));
			assertThat(huBagsXML, not(hasXPath("/Bags/HU-TU_Bag[2]/Item[@ItemType='HA']")));
			assertThat(huBagsXML, not(hasXPath("/Bags/HU-TU_Bag[3]/Item[@ItemType='HA']")));
			
			assertThat(huBagsXML, hasXPath("/Bags/HU-TU_Bag[1]/Item[@ItemType='MI']"));
			assertThat(huBagsXML, hasXPath("/Bags/HU-TU_Bag[1]/Item[@ItemType='MI']/Storage[@M_Product_Value='Tomato' and @Qty='6.000' and @C_UOM_Name='Kg']"));
			
			assertThat(huBagsXML, hasXPath("/Bags/HU-TU_Bag[2]/Item[@ItemType='MI']"));
			assertThat(huBagsXML, hasXPath("/Bags/HU-TU_Bag[2]/Item[@ItemType='MI']/Storage[@M_Product_Value='Tomato' and @Qty='6.000' and @C_UOM_Name='Kg']"));
			
			assertThat(huBagsXML, hasXPath("/Bags/HU-TU_Bag[3]/Item[@ItemType='MI']"));
			assertThat(huBagsXML, hasXPath("/Bags/HU-TU_Bag[3]/Item[@ItemType='MI']/Storage[@M_Product_Value='Tomato' and @Qty='6.000' and @C_UOM_Name='Kg']"));
			
			assertThat(huBagsXML, Matchers.hasXPath("/Bags/HU-TU_Bag[1]/Storage[@M_Product_Value='Tomato' and @Qty='8.000' and @C_UOM_Name='Kg']"));
			assertThat(huBagsXML, Matchers.hasXPath("/Bags/HU-TU_Bag[2]/Storage[@M_Product_Value='Tomato' and @Qty='8.000' and @C_UOM_Name='Kg']"));
			assertThat(huBagsXML, Matchers.hasXPath("/Bags/HU-TU_Bag[3]/Storage[@M_Product_Value='Tomato' and @Qty='1.000' and @C_UOM_Name='Kg']"));
		}

		//
		// Transfer another 1 item from Palets to existing Bags
		data.helper.transferMaterialToExistingHUs(huPalets, huBags, data.helper.pTomato, BigDecimal.ONE, data.helper.uomKg);

		//
		// Validate Blisters after transferring another 1 item to it (so we got 18 in total)
		{
			final Node huBagsXML = HUXmlConverter.toXml("Bags", huBags);
			System.out.println(HUXmlConverter.toString(huBagsXML));

			Assert.assertThat(huBagsXML, Matchers.hasXPath("count(/Bags/HU-TU_Bag)", Matchers.equalTo("3")));
			Assert.assertThat(huBagsXML, Matchers.hasXPath("/Bags/HU-TU_Bag[1]/Storage[@M_Product_Value='Tomato' and @Qty='8.000' and @C_UOM_Name='Kg']"));
			Assert.assertThat(huBagsXML, Matchers.hasXPath("/Bags/HU-TU_Bag[2]/Storage[@M_Product_Value='Tomato' and @Qty='8.000' and @C_UOM_Name='Kg']"));
			Assert.assertThat(huBagsXML, Matchers.hasXPath("/Bags/HU-TU_Bag[3]/Storage[@M_Product_Value='Tomato' and @Qty='2.000' and @C_UOM_Name='Kg']"));

			HUAssert.assertAllStoragesAreValid();
		}

		//
		// Shipment: ship 17 items from blisters
		final I_M_Transaction outgoingTrx = data.helper.createMTransaction(X_M_Transaction.MOVEMENTTYPE_CustomerShipment,
				data.helper.pTomato,
				new BigDecimal("-17"));
		data.helper.transferHUsToOutgoing(outgoingTrx, huBags);

		//
		// Validate Blisters after taking out 17 items
		{
			final Node huBlistersXML = HUXmlConverter.toXml("Blisters", huBags);
			// System.out.println(XmlConverter.toString(huBlistersXML));

			Assert.assertThat(huBlistersXML, Matchers.hasXPath("count(/Bags/HU-TU_Bag)", Matchers.equalTo("3")));
			Assert.assertThat(huBlistersXML, Matchers.hasXPath("/Bags/HU-TU_Bag[1]/Storage[@M_Product_Value='Tomato' and @Qty='0']"));
			Assert.assertThat(huBlistersXML, Matchers.hasXPath("/Bags/HU-TU_Bag[2]/Storage[@M_Product_Value='Tomato' and @Qty='0']"));
			Assert.assertThat(huBlistersXML, Matchers.hasXPath("/Bags/HU-TU_Bag[3]/Storage[@M_Product_Value='Tomato' and @Qty='1']"));

			HUAssert.assertAllStoragesAreValid();
		}

		// TraceUtils.dump(huPalets);
		// TraceUtils.dump(huBlisters);
		// TraceUtils.dumpTransactions();
	}

	private LUTUProducerDestination mkLUTUProducerDestination()
	{
		final LUTUProducerDestination lutuProducer = new LUTUProducerDestination();
		lutuProducer.setLUPI(data.piLU);
		lutuProducer.setLUItemPI(data.piLU_Item_IFCO);
		lutuProducer.setTUPI(data.piTU_IFCO);

		return lutuProducer;
	}
}
