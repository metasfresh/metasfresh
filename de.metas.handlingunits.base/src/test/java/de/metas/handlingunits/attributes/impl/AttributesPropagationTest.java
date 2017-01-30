package de.metas.handlingunits.attributes.impl;

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


import static org.hamcrest.Matchers.comparesEqualTo;

import java.math.BigDecimal;
import java.util.List;

import org.adempiere.mm.attributes.model.I_M_Attribute;
import org.adempiere.util.Services;
import org.compiere.model.I_M_Transaction;
import org.compiere.model.X_M_Attribute;
import org.compiere.model.X_M_Transaction;
import org.junit.Assert;
import org.junit.Test;

import de.metas.handlingunits.AbstractHUTest;
import de.metas.handlingunits.HUAssert;
import de.metas.handlingunits.HUTestHelper;
import de.metas.handlingunits.HUXmlConverter;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.attribute.IAttributeValue;
import de.metas.handlingunits.attribute.propagation.IHUAttributePropagationContext;
import de.metas.handlingunits.attribute.propagation.IHUAttributePropagator;
import de.metas.handlingunits.attribute.propagation.IHUAttributePropagatorFactory;
import de.metas.handlingunits.attribute.propagation.impl.HUAttributePropagationContext;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.attribute.storage.IAttributeStorageFactory;
import de.metas.handlingunits.attribute.strategy.impl.CopyAttributeSplitterStrategy;
import de.metas.handlingunits.attribute.strategy.impl.SumAggregationStrategy;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.X_M_HU_PI_Attribute;
import de.metas.handlingunits.model.X_M_HU_PI_Item;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.handlingunits.test.misc.builders.HUPIAttributeBuilder;

public class AttributesPropagationTest extends AbstractHUTest
{
	private I_M_HU_PI huDefPalet;
	private I_M_HU_PI huDefIFCO;
	private I_M_HU_PI huDefBag;

	@Override
	protected void initialize()
	{
		//
		// Handling Units Definition
		huDefBag = helper.createHUDefinition(HUTestHelper.NAME_Bag_Product, X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);
		{
			final I_M_HU_PI_Item itemMA = helper.createHU_PI_Item_Material(huDefBag);
			helper.assignProduct(itemMA, pTomato, BigDecimal.TEN, uomEach);

			helper.createHU_PI_Item_PackingMaterial(huDefBag, pmBag);

			// value will not be propagated
			helper.createM_HU_PI_Attribute(new HUPIAttributeBuilder(attr_FragileSticker)
					.setM_HU_PI(huDefBag)
					.setPropagationType(X_M_HU_PI_Attribute.PROPAGATIONTYPE_NoPropagation));
			// value will be inherited
			helper.createM_HU_PI_Attribute(new HUPIAttributeBuilder(attr_CountryMadeIn)
					.setM_HU_PI(huDefBag)
					.setPropagationType(X_M_HU_PI_Attribute.PROPAGATIONTYPE_TopDown)
					.setSplitterStrategyClass(CopyAttributeSplitterStrategy.class));
			// value will be pulled up to parents using *sum* aggregation strategy
			helper.createM_HU_PI_Attribute(new HUPIAttributeBuilder(attr_Volume)
					.setM_HU_PI(huDefBag)
					.setPropagationType(X_M_HU_PI_Attribute.PROPAGATIONTYPE_BottomUp)
					.setAggregationStrategyClass(SumAggregationStrategy.class));
		}

		huDefIFCO = helper.createHUDefinition(HUTestHelper.NAME_IFCO_Product, X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);
		{
			//helper.createHU_PI_Item_IncludedHU(huDefIFCO, huDefIFCO, BigDecimal.ONE);
			final I_M_HU_PI_Item   piTU_Item_IFCO = helper.createHU_PI_Item_Material(huDefIFCO);
			helper.assignProduct(piTU_Item_IFCO, pTomato, BigDecimal.TEN, uomEach);
			
			// value will not be propagated
			helper.createM_HU_PI_Attribute(new HUPIAttributeBuilder(attr_FragileSticker)
					.setM_HU_PI(huDefIFCO)
					.setPropagationType(X_M_HU_PI_Attribute.PROPAGATIONTYPE_NoPropagation));
			// value will be inherited
			helper.createM_HU_PI_Attribute(new HUPIAttributeBuilder(attr_CountryMadeIn)
					.setM_HU_PI(huDefIFCO)
					.setPropagationType(X_M_HU_PI_Attribute.PROPAGATIONTYPE_TopDown)
					.setSplitterStrategyClass(CopyAttributeSplitterStrategy.class));
			// value will be pulled up to parents using *sum* aggregation strategy
			helper.createM_HU_PI_Attribute(new HUPIAttributeBuilder(attr_Volume)
					.setM_HU_PI(huDefIFCO)
					.setPropagationType(X_M_HU_PI_Attribute.PROPAGATIONTYPE_BottomUp)
					.setAggregationStrategyClass(SumAggregationStrategy.class));
		}

		huDefPalet = helper.createHUDefinition(HUTestHelper.NAME_Palet_Product, X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit);
		{
			helper.createHU_PI_Item_IncludedHU(huDefPalet, huDefIFCO, new BigDecimal("2"));
			helper.createHU_PI_Item_PackingMaterial(huDefPalet, pmPallets);

			// value will not be propagated
			helper.createM_HU_PI_Attribute(new HUPIAttributeBuilder(attr_FragileSticker)
					.setM_HU_PI(huDefPalet)
					.setPropagationType(X_M_HU_PI_Attribute.PROPAGATIONTYPE_NoPropagation));
			// value will be inherited
			helper.createM_HU_PI_Attribute(new HUPIAttributeBuilder(attr_CountryMadeIn)
					.setM_HU_PI(huDefPalet)
					.setPropagationType(X_M_HU_PI_Attribute.PROPAGATIONTYPE_TopDown)
					.setSplitterStrategyClass(CopyAttributeSplitterStrategy.class));
			// value will be pulled up to parents using *sum* aggregation strategy
			helper.createM_HU_PI_Attribute(new HUPIAttributeBuilder(attr_Volume)
					.setM_HU_PI(huDefPalet)
					.setPropagationType(X_M_HU_PI_Attribute.PROPAGATIONTYPE_BottomUp)
					.setAggregationStrategyClass(SumAggregationStrategy.class));
		}
	}

	private List<I_M_HU> createIncomingPalets()
	{
		// assume that incomingTrxDoc is a material receipt of 23 tomatoes
		final I_M_Transaction incomingTrxDoc = helper.createMTransaction(X_M_Transaction.MOVEMENTTYPE_VendorReceipts, pTomato, new BigDecimal(23));

		// create and destroy instances only with a I_M_Transaction
		final List<I_M_HU> huPalets = helper.createHUsFromSimplePI(incomingTrxDoc, huDefPalet);
		
		return huPalets;
	}

	@Test
	public void testNormalPropagation()
	{
		final List<I_M_HU> huPalets = createIncomingPalets();
		System.out.println(HUXmlConverter.toString(HUXmlConverter.toXml("huPalets", huPalets)));
		
		Assert.assertEquals("There should be 2 palets", 2, huPalets.size());

		final I_M_HU palet1 = huPalets.get(0);
		final I_M_HU palet2 = huPalets.get(1);

		final IAttributeStorageFactory storageFactory = helper.getHUContext().getHUAttributeStorageFactory();
		final IAttributeStorage palet1_Attrs = storageFactory.getAttributeStorage(palet1);
		final IAttributeStorage palet2_Attrs = storageFactory.getAttributeStorage(palet2);

		// set top-down propagation attribute
		palet1_Attrs.setValue(attr_CountryMadeIn, HUTestHelper.COUNTRYMADEIN_RO); // propagate=true
		palet2_Attrs.setValue(attr_CountryMadeIn, HUTestHelper.COUNTRYMADEIN_DE); // propagate=true

		final List<I_M_HU_Item> huPalet1_items = Services.get(IHandlingUnitsDAO.class).retrieveItems(palet1);
		final List<I_M_HU_Item> huPalet2_Items = Services.get(IHandlingUnitsDAO.class).retrieveItems(palet2);

		// get the item with type's handling unit for pallet one to retrieve the attached IFCOs
		for (final I_M_HU_Item huPaletItem : huPalet1_items)
		{
			// we only have one item with handling unit item type
			final String huPaletItemType = Services.get(IHandlingUnitsBL.class).getItemType(huPaletItem);
			if (huPaletItemType.equals(X_M_HU_PI_Item.ITEMTYPE_HandlingUnit))
			{
				final List<I_M_HU> huIFCOs = Services.get(IHandlingUnitsDAO.class).retrieveIncludedHUs(huPaletItem);
				Assert.assertEquals("Invalid number of IFCOs in pallet, palet item: \n" + huPaletItem, 2, huIFCOs.size());

				final I_M_HU huIFCO1 = huIFCOs.get(0);
				final IAttributeStorage huIFCO1_Attrs = storageFactory.getAttributeStorage(huIFCO1);

				final BigDecimal huIFCO1_Volume = BigDecimal.TEN;
				huIFCO1_Attrs.setValue(attr_Volume, huIFCO1_Volume);

				// Now try to retrieve the volume ONE value and check if it was actually set
				final IAttributeValue volumeAttributeValueOne = huIFCO1_Attrs.getAttributeValue(attr_Volume);
				Assert.assertThat("Volumes are supposed to match: ", volumeAttributeValueOne.getValueAsBigDecimal(), comparesEqualTo(huIFCO1_Volume));

				// Test if "country" was propagated down
				Assert.assertEquals("Invalid countryMadeIn",
						HUTestHelper.COUNTRYMADEIN_RO,
						huIFCO1_Attrs.getAttributeValue(attr_CountryMadeIn).getValueAsString());

				final I_M_HU huIFCO2 = huIFCOs.get(1);
				final IAttributeStorage huIFCO2_Attrs = storageFactory.getAttributeStorage(huIFCO2);
				final BigDecimal huIFCO2_Volume = new BigDecimal(20);
				huIFCO2_Attrs.setValue(attr_Volume, huIFCO2_Volume);

				// Now try to retrieve the volume TWO value and check if it was actually set
				final IAttributeValue volumeAttributeValueTwo = huIFCO2_Attrs.getAttributeValue(attr_Volume);
				Assert.assertThat("Volumes are supposed to match: ", volumeAttributeValueTwo.getValueAsBigDecimal(), comparesEqualTo(huIFCO2_Volume));

				// check that the master volume was aggregated successfully : expected=30
				final IAttributeValue volumePalletOne = palet1_Attrs.getAttributeValue(attr_Volume);
				Assert.assertThat("Invalid expected propagated volume", volumePalletOne.getValueAsBigDecimal(), comparesEqualTo(new BigDecimal("30")));

				// Set the volume value again for IFCO ONE to be able to check that the aggregation strategy re-adds the proper value
				final BigDecimal huIFCO1_Volume_Reset = BigDecimal.TEN;
				huIFCO1_Attrs.setValue(attr_Volume, huIFCO1_Volume_Reset);
				// Set the volume value again for IFCO TWO to be able to check that the aggregation strategy re-adds the proper value
				final BigDecimal huIFCO2_Volume_Reset = new BigDecimal(15);
				huIFCO2_Attrs.setValue(attr_Volume, huIFCO2_Volume_Reset);

				// check that the master volume was re-aggregated successfully : expected=20
				final IAttributeValue volumePalletOneReset = palet1_Attrs.getAttributeValue(attr_Volume);
				Assert.assertThat("Invalid expected propagated volume", volumePalletOneReset.getValueAsBigDecimal(), comparesEqualTo(new BigDecimal("25")));

				// Test if "country" was propagated down
				Assert.assertEquals("Invalid countryMadeIn",
						HUTestHelper.COUNTRYMADEIN_RO,
						huIFCO2_Attrs.getAttributeValue(attr_CountryMadeIn).getValueAsString());
			}
		}

		// get the item with type's handling unit for palet two to retrieve the attached IFCOs
		for (final I_M_HU_Item huPaletItem : huPalet2_Items)
		{
			// we only have one item with handling unit item type
			if (huPaletItem.getItemType().equals(X_M_HU_PI_Item.ITEMTYPE_HandlingUnit))
			{
				final List<I_M_HU> huIFCOs = Services.get(IHandlingUnitsDAO.class).retrieveIncludedHUs(huPaletItem);
				Assert.assertEquals("Invalid number of IFCOs in pallet, palet item: \n" + huPaletItem, 1, huIFCOs.size());

				final I_M_HU huIFCO = huIFCOs.get(0);
				final IAttributeStorage huIFCO_Attrs = storageFactory.getAttributeStorage(huIFCO);

				final BigDecimal huIFCO_Volume = BigDecimal.TEN;
				huIFCO_Attrs.setValue(attr_Volume, huIFCO_Volume);

				// Now try to retrieve the volume value and check if it was actually set
				final IAttributeValue huIFCO_Volume_AV = huIFCO_Attrs.getAttributeValue(attr_Volume);
				Assert.assertThat("Volumes are supposed to match: ", huIFCO_Volume_AV.getValueAsBigDecimal(), comparesEqualTo(huIFCO_Volume));

				final IAttributeValue palet2_Volume_AV = palet2_Attrs.getAttributeValue(attr_Volume);
				Assert.assertThat("Invalid expected propagated volume", palet2_Volume_AV.getValueAsBigDecimal(), comparesEqualTo(BigDecimal.TEN));

				// Test if "country" was propagated down
				Assert.assertEquals("Invalid countryMadeIn",
						HUTestHelper.COUNTRYMADEIN_DE,
						huIFCO_Attrs.getAttributeValue(attr_CountryMadeIn).getValueAsString());
			}
		}
	}

	@Test(expected = IllegalStateException.class)
	public void testInvalidPropagation()
	{
		//
		// Create and assign an attribute with "invalid propagation"
		final I_M_Attribute attr_InvalidPropagation = helper.createM_Attribute("InvalidPropagation_Attribute",
				X_M_Attribute.ATTRIBUTEVALUETYPE_StringMax40,
				true // isInstanceAttribute
				);
		helper.createM_HU_PI_Attribute(new HUPIAttributeBuilder(attr_InvalidPropagation)
				.setM_HU_PI(huDefPalet)
				.setPropagationType("Invalid propagation type"));

		final List<I_M_HU> huPalets = createIncomingPalets();
		final I_M_HU palletOne = huPalets.get(0);

		final IAttributeStorageFactory attributeStorageFactory = helper.getHUContext().getHUAttributeStorageFactory();

		final IAttributeStorage attributeSet = attributeStorageFactory.getAttributeStorage(palletOne);

		Services.get(IHUAttributePropagatorFactory.class).getPropagator(attributeSet, attr_InvalidPropagation);

		HUAssert.assertMock("mock");
	}

	@Test
	public void testNoParentCopyValueSplitterStrategy()
	{
		//
		// Create and assign attribute
		final I_M_Attribute attr_CopyValueNoParent = helper.createM_Attribute("InheritValueNoParent_Attribute",
				X_M_Attribute.ATTRIBUTEVALUETYPE_StringMax40,
				true // instance attribute
				);
		helper.createM_HU_PI_Attribute(new HUPIAttributeBuilder(attr_CopyValueNoParent)
				.setM_HU_PI(huDefPalet)
				.setPropagationType(X_M_HU_PI_Attribute.PROPAGATIONTYPE_TopDown)
				.setSplitterStrategyClass(CopyAttributeSplitterStrategy.class));

		final List<I_M_HU> huPalets = createIncomingPalets();
		final I_M_HU palletOne = huPalets.get(0);

		final IAttributeStorageFactory attributeStorageFactory = helper.getHUContext().getHUAttributeStorageFactory();

		final IAttributeStorage attributeSet = attributeStorageFactory.getAttributeStorage(palletOne);

		final String expected = "expectedInheritedValue";
		final IHUAttributePropagator propagator = Services.get(IHUAttributePropagatorFactory.class).getPropagator(attributeSet, attr_CopyValueNoParent);
		final IHUAttributePropagationContext propagationContext = new HUAttributePropagationContext(attributeSet, propagator, attr_CopyValueNoParent);
		propagator.propagateValue(propagationContext, attributeSet, expected); // updateStorageValue=true

		final IAttributeValue actualAttributeValue = attributeSet.getAttributeValue(attr_CopyValueNoParent);
		Assert.assertSame("Invalid attribute propagated on self", expected, actualAttributeValue.getValueAsString());
	}
}
