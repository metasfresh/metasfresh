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


import java.math.BigDecimal;
import java.util.List;

import org.adempiere.mm.attributes.api.IAttributeSet;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Transaction;
import org.compiere.model.X_M_Transaction;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import de.metas.handlingunits.AbstractHUTest;
import de.metas.handlingunits.HUTestHelper;
import de.metas.handlingunits.IHUAware;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.attribute.storage.IAttributeStorageFactory;
import de.metas.handlingunits.conversion.ConversionHelper;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.X_M_HU_PI_Version;

public class WeightAttributeValueCalloutTest extends AbstractHUTest
{
	// NOTE: If you change some of these, please always use even numbers
	private static final BigDecimal COUNT_IFCOS_PER_PALET = BigDecimal.valueOf(2);
	private static final BigDecimal COUNT_TOMATOS_PER_IFCO = BigDecimal.valueOf(10);

	private static final BigDecimal PM_IFCO_WeightTare = BigDecimal.valueOf(16);
	private static final BigDecimal PM_Palet_WeightTare = BigDecimal.valueOf(24);
	private static final BigDecimal PM_Palet_WeightTare_Total = new BigDecimal(
			PM_Palet_WeightTare.intValueExact()
					+ COUNT_IFCOS_PER_PALET.intValueExact() * PM_IFCO_WeightTare.intValueExact()
			);

	private I_M_HU_PI huDefPalet;
	private I_M_HU_PI huDefIFCO;

	private IAttributeStorage huPalet_Attrs;
	private IAttributeStorage huIFCO1_Attrs;
	private IAttributeStorage huIFCO2_Attrs;

	private IAttributeStorageFactory attributeStorageFactory;

	/**
	 * Create WeightGross, WeightTare and WeightNet attributes.
	 *
	 * Assign them to NoPI.
	 *
	 * @param helper
	 */
	public static void setupWeightsToNoPI(final HUTestHelper helper)
	{
		//
		// IFCO Weight Tare
		{
			final I_M_Product pIFCO = helper.pmIFCO.getM_Product();
			pIFCO.setWeight(PM_IFCO_WeightTare);
			InterfaceWrapperHelper.save(pIFCO);
		}

		//
		// Pallete Weight Tare
		{
			final I_M_Product pPalet = helper.pmPalet.getM_Product();
			pPalet.setWeight(PM_Palet_WeightTare);
			InterfaceWrapperHelper.save(pPalet);
		}
	}

	@Override
	protected void initialize()
	{
		setupWeightsToNoPI(helper);

		//
		// HU PI: IFCO
		huDefIFCO = helper.createHUDefinition(HUTestHelper.NAME_IFCO_Product, X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);
		{
			final I_M_HU_PI_Item itemMA = helper.createHU_PI_Item_Material(huDefIFCO);
			helper.assignProduct(itemMA, pTomato, COUNT_TOMATOS_PER_IFCO, uomEach);

			helper.createHU_PI_Item_PackingMaterial(huDefIFCO, pmIFCO);
		}

		//
		// HU PI: Palet
		huDefPalet = helper.createHUDefinition(HUTestHelper.NAME_Palet_Product, X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit);
		{
			helper.createHU_PI_Item_IncludedHU(huDefPalet, huDefIFCO, COUNT_IFCOS_PER_PALET);

			helper.createHU_PI_Item_PackingMaterial(huDefPalet, pmPallets);
		}
	}

	/**
	 * Creates:
	 * <ul>
	 * <li>create IFCO HU
	 * <li>bind variables
	 * </ul>
	 */
	private void createMasterData()
	{
		attributeStorageFactory = helper.getHUContext().getHUAttributeStorageFactory();

		//
		// Create inital Palets
		final I_M_Transaction incomingTrxDoc = helper.createMTransaction(X_M_Transaction.MOVEMENTTYPE_VendorReceipts,
				pTomato,
				COUNT_IFCOS_PER_PALET.multiply(COUNT_TOMATOS_PER_IFCO).multiply(BigDecimal.valueOf(2)));
		final List<I_M_HU> huPalets = createPlainHU(incomingTrxDoc, huDefPalet);

		//
		// Bind data to be able to access them in our tests
		final I_M_HU huPalet = huPalets.get(0);
		huPalet.setValue("Palet1");
		InterfaceWrapperHelper.save(huPalet);
		huPalet_Attrs = attributeStorageFactory.getAttributeStorage(huPalet);

		final List<I_M_HU> huIncluded = helper.retrieveIncludedHUs(huPalet);

		final I_M_HU huIFCO1 = huIncluded.get(0);
		huIFCO1.setValue("IFCO1");
		InterfaceWrapperHelper.save(huIFCO1);
		huIFCO1_Attrs = attributeStorageFactory.getAttributeStorage(huIFCO1);

		final I_M_HU huIFCO2 = huIncluded.get(1);
		huIFCO2.setValue("IFCO1");
		InterfaceWrapperHelper.save(huIFCO2);
		huIFCO2_Attrs = attributeStorageFactory.getAttributeStorage(huIFCO2);
	}

	@Test
	@Ignore
	// TODO fix & reactivate when http://dewiki908/mediawiki/index.php/07267_Automatic_weighing_for_top-level_HUs_%28108576827619%29 is solved
	public void testWeightNet()
	{
		createMasterData();

		final BigDecimal WeightNA = null;

		// Initial values check: Gross=0, Net = 0 - Tare
		assertWeightTare(huPalet_Attrs, PM_Palet_WeightTare_Total);
		testGrossTareNetWeight(huPalet_Attrs,
				WeightNA, // Gross
				WeightNA, // Tare
				BigDecimal.valueOf(0 - PM_Palet_WeightTare_Total.intValueExact()) // Net
		);

		// Gross=100, Net = 100 - Tare
		testGrossTareNetWeight(huPalet_Attrs,
				BigDecimal.valueOf(100), // Gross
				WeightNA, // Tare
				BigDecimal.valueOf(100 - PM_Palet_WeightTare_Total.intValueExact()) // Net
		);

		// Gross=0, => Net = 0 - Tare
		testGrossTareNetWeight(huPalet_Attrs,
				BigDecimal.valueOf(0), // Gross
				WeightNA, // Tare
				BigDecimal.valueOf(0 - PM_Palet_WeightTare_Total.intValueExact()) // Net
		);
	}

	private void testGrossTareNetWeight(
			final IAttributeSet attrs,
			final BigDecimal weightGrossToSet,
			final BigDecimal weightTareToSet,
			final BigDecimal weightNetExpected)
	{
		if (weightGrossToSet != null)
		{
			attrs.setValue(attr_WeightGross, weightGrossToSet);
		}
		if (weightTareToSet != null)
		{
			attrs.setValue(attr_WeightTare, weightTareToSet);
		}

		final Object weightNetActualObj = attrs.getValue(attr_WeightNet);
		final BigDecimal weightNetActual = ConversionHelper.toBigDecimal(weightNetActualObj);
		Assert.assertThat(
				"Invalid WeightNet (GrossToSet=" + weightGrossToSet + ", TareToSet=" + weightTareToSet + ")",
				weightNetActual,
				Matchers.comparesEqualTo(weightNetExpected));
	}

	private void assertWeightTare(final IAttributeSet attrs, final Object weightTareExpectedObj)
	{
		final BigDecimal weightTareExpected = ConversionHelper.toBigDecimal(weightTareExpectedObj);
		final Object weightTareActualObj = attrs.getValue(attr_WeightTare);
		final BigDecimal weightTareActual = ConversionHelper.toBigDecimal(weightTareActualObj);
		Assert.assertThat(
				"Invalid WeightTare on " + getDisplayName(attrs),
				weightTareActual,
				Matchers.comparesEqualTo(weightTareExpected));
	}

	private void assertWeightGross(final IAttributeSet attrs, final Object weightGrossExpectedObj)
	{
		final BigDecimal weightGrossExpected = ConversionHelper.toBigDecimal(weightGrossExpectedObj);
		final Object weightGrossActualObj = attrs.getValue(attr_WeightGross);
		final BigDecimal weightGrossActual = ConversionHelper.toBigDecimal(weightGrossActualObj);
		Assert.assertThat(
				"Invalid WeightGross on " + getDisplayName(attrs),
				weightGrossActual,
				Matchers.comparesEqualTo(weightGrossExpected));
	}

	private void assertWeightNet(final IAttributeSet attrs, final Object weightNetExpectedObj)
	{
		final BigDecimal weightNetExpected = ConversionHelper.toBigDecimal(weightNetExpectedObj);
		final Object WeightNetActualObj = attrs.getValue(attr_WeightNet);
		final BigDecimal WeightNetActual = ConversionHelper.toBigDecimal(WeightNetActualObj);
		Assert.assertThat(
				"Invalid WeightNet on " + getDisplayName(attrs),
				WeightNetActual,
				Matchers.comparesEqualTo(weightNetExpected));
	}

	private void assertWeights(final IAttributeSet attrs,
			final Object weightGrossExpected,
			final Object weightTareExpected,
			final Object weightNetExpected)
	{
		assertWeightGross(attrs, weightGrossExpected);
		assertWeightTare(attrs, weightTareExpected);
		assertWeightNet(attrs, weightNetExpected);
	}

	@Test
	@Ignore
	// TODO fix & reactivate when http://dewiki908/mediawiki/index.php/07267_Automatic_weighing_for_top-level_HUs_%28108576827619%29 is solved
	public void test_WeightTare_WeightGrossPropagation_WeightNet()
	{
		createMasterData();

		final BigDecimal initialNetPallete = new BigDecimal(0 - PM_Palet_WeightTare_Total.intValueExact());
		final BigDecimal initialNetIfco = initialNetPallete.add(PM_Palet_WeightTare)
				.divide(COUNT_IFCOS_PER_PALET, BigDecimal.ROUND_HALF_UP);
		final BigDecimal initialGrossIfco = initialNetIfco.add(PM_IFCO_WeightTare);

		//
		// Initial status asserts (Gross/Tare/Net)
		assertWeights(huPalet_Attrs, 0, PM_Palet_WeightTare_Total, initialNetPallete);
		assertWeights(huIFCO1_Attrs, initialGrossIfco, PM_IFCO_WeightTare, initialNetIfco); // PM_IFCO_WeightTare.add(initialNetIfco)
		assertWeights(huIFCO2_Attrs, initialGrossIfco, PM_IFCO_WeightTare, initialNetIfco);

		//
		// Set WeightGross=100 on Palet
		// Check how is distributed and how WeightNet is calculated
		{
			huPalet_Attrs.setValue(attr_WeightGross, new BigDecimal("100"));

			final BigDecimal netPalet100 = new BigDecimal(100 - PM_Palet_WeightTare_Total.intValueExact());
			final BigDecimal netIfco100 = netPalet100.divide(COUNT_IFCOS_PER_PALET, BigDecimal.ROUND_HALF_UP);
			final BigDecimal grossIfco100 = netIfco100.add(PM_IFCO_WeightTare);

			assertWeights(huPalet_Attrs, 100, PM_Palet_WeightTare_Total, netPalet100);
			assertWeights(huIFCO1_Attrs, grossIfco100, PM_IFCO_WeightTare, netIfco100);
			assertWeights(huIFCO2_Attrs, grossIfco100, PM_IFCO_WeightTare, netIfco100);
		}

		//
		// Set WeightGross=50 on Palet
		// Check how is distributed and how WeightNet is calculated
		{
			huPalet_Attrs.setValue(attr_WeightGross, new BigDecimal("50"));

			final BigDecimal netPalet50 = new BigDecimal(50 - PM_Palet_WeightTare_Total.intValueExact());
			final BigDecimal netIfco50 = netPalet50.add(PM_Palet_WeightTare)
					.divide(COUNT_IFCOS_PER_PALET, BigDecimal.ROUND_HALF_UP);
			final BigDecimal grossIfco50 = netIfco50.add(PM_IFCO_WeightTare);

			assertWeights(huPalet_Attrs, 50, PM_Palet_WeightTare_Total, 50 - PM_Palet_WeightTare_Total.intValueExact());
			assertWeights(huIFCO1_Attrs, grossIfco50, PM_IFCO_WeightTare, netIfco50);
			assertWeights(huIFCO2_Attrs, grossIfco50, PM_IFCO_WeightTare, netIfco50);

		}
	}

	private void dumpWeights(final String name, final IAttributeStorage attributes)
	{
		System.out.println("");
		if (attributes == null)
		{
			System.out.println(name + " - NO ATTRIBUTES");
			return;
		}

		System.out.println(name + " Weight Gross: "
				+ attributes.getValue(attr_WeightGross)
				+ ", Initial: " + attributes.getValueInitial(attr_WeightGross));

		System.out.println(name + " Weight Tare: "
				+ attributes.getValue(attr_WeightTare)
				+ ", Initial: " + attributes.getValueInitial(attr_WeightTare));

		System.out.println(name + " Weight Net: "
				+ attributes.getValue(attr_WeightNet)
				+ ", Initial: " + attributes.getValueInitial(attr_WeightNet));
	}

	private String getDisplayName(final IAttributeSet attributes)
	{
		final IHUAware huAware = (IHUAware)attributes;
		final I_M_HU hu = huAware.getM_HU();
		return hu.getValue();
	}

	@Override
	protected void afterTestFailed()
	{
		dumpWeights("Palet", huPalet_Attrs);
		dumpWeights("IFCO1", huIFCO1_Attrs);
		dumpWeights("IFCO2", huIFCO2_Attrs);
	}
}
