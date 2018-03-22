package de.metas.handlingunits.allocation.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
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
import java.util.List;

import org.adempiere.util.Services;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.junit.Assert;
import org.junit.Test;

import de.metas.handlingunits.AbstractHUTest;
import de.metas.handlingunits.HUAssert;
import de.metas.handlingunits.HUTestHelper;
import de.metas.handlingunits.HUXmlConverter;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.IMutableHUContext;
import de.metas.handlingunits.allocation.IAllocationRequest;
import de.metas.handlingunits.allocation.IAllocationResult;
import de.metas.handlingunits.allocation.IHUProducerAllocationDestination;
import de.metas.handlingunits.allocation.ILUTUConfigurationFactory;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_LUTU_Configuration;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.quantity.Quantity;

/**
 * Test {@link AllocationUtils#createLUTUProducerDestinationForTUConfig(I_M_HU_PI_Item_Product, I_M_Product, I_C_UOM)}.
 *
 * @author tsa
 * @task http://dewiki908/mediawiki/index.php/07245_Wrong_IFCO
 */
public class LUTUConfigurationFactory_createLUTUProducerAllocationDestination_Test extends AbstractHUTest
{
	private IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);

	private LUTUConfigurationFactory lutuFactory;

	private IMutableHUContext huContext;

	private I_M_Product cuProduct;
	private I_C_UOM cuUOM;
	private I_C_BPartner bpartner;

	private I_M_HU_PI piPalet;
	private I_M_HU_PI piIFCO;
	private I_M_HU_PI_Item piIFCO_Item_Material;
	private I_M_HU_PI piVirtual;
	private I_M_HU_PI_Item_Product piVirtual_Item_Product;

	@Override
	protected void initialize()
	{
		handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);

		lutuFactory = (LUTUConfigurationFactory)Services.get(ILUTUConfigurationFactory.class);

		huContext = helper.getHUContext();
		cuProduct = pTomato;
		cuUOM = uomEach;
		bpartner = null;

		// Inherited transaction (needed for LUTUConfigFactory)
		// NOTE: not needed
		// Services.get(ITrxManager.class).setThreadInheritedTrxName("Dummy_" + UUID.randomUUID());

		//
		// Virtual HU PI
		piVirtual = helper.huDefVirtual;
		piVirtual_Item_Product = helper.huDefItemProductVirtual;

		//
		// TU: IFCO
		piIFCO = helper.createHUDefinition(HUTestHelper.NAME_IFCO_Product, X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);
		{
			piIFCO_Item_Material = helper.createHU_PI_Item_Material(piIFCO);
			// piIFCO_Product = helper.assignProduct(piIFCO_Item_Material, cuProduct, BigDecimal.TEN, cuUOM);
		}

		//
		// LU: Palet
		piPalet = helper.createHUDefinition(HUTestHelper.NAME_Palet_Product, X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit);
		{
			// piPalet_IFCO = helper.createHU_PI_Item_IncludedHU(piPalet, piIFCO, new BigDecimal("2"));
		}

	}

	private IHUProducerAllocationDestination createLUTUProducerDestination(final I_M_HU_PI_Item_Product tuPIItemProduct)
	{
		final I_M_HU_LUTU_Configuration lutuConfiguration = lutuFactory.createLUTUConfiguration(
				tuPIItemProduct,
				cuProduct,
				cuUOM,
				bpartner,
				false); // noLUForVirtualTU == false => allow placing the CU (e.g. a packing material product) directly on the LU);

		// Make sure configuration is saved, else HUBuilder will fail
		Services.get(ILUTUConfigurationFactory.class).save(lutuConfiguration);

		return lutuFactory.createLUTUProducerAllocationDestination(lutuConfiguration);
	}

	private List<I_M_HU> createHUs(final I_M_HU_PI_Item_Product tuPIItemProduct, final int cuQtyInt)
	{
		final BigDecimal cuQty = BigDecimal.valueOf(cuQtyInt);

		final IHUProducerAllocationDestination allocationDestination = createLUTUProducerDestination(tuPIItemProduct);

		final AbstractAllocationSourceDestination allocationSource = helper.createDummySourceDestination(cuProduct, Quantity.QTY_INFINITE, true);
		final Object referencedModel = allocationSource.getReferenceModel();

		final IAllocationRequest request = AllocationUtils.createQtyRequest(huContext,
				cuProduct,
				cuQty,
				cuUOM,
				helper.getTodayDate(),
				referencedModel);

		//
		// Execute transfer => HUs will be generated
		final IAllocationResult result = HULoader.of(allocationSource, allocationDestination)
				.load(request);
		Assert.assertTrue("Result shall be completed: " + result, result.isCompleted());

		return allocationDestination.getCreatedHUs();
	}

	public final void assertHUStorageLevel(final I_M_HU hu, final I_M_HU_PI expectedPI, final int expectedQtyInt)
	{
		HUAssert.assertHU_PI(hu, expectedPI);
		final BigDecimal expectedQty = BigDecimal.valueOf(expectedQtyInt);
		HUAssert.assertStorageLevel(huContext, hu, cuProduct, expectedQty);
	}

	@Test(expected = NullPointerException.class)
	public void test_NULL_PIItemProduct()
	{
		lutuFactory.createLUTUConfiguration(
				null, // tuPIItemProduct
				cuProduct,
				cuUOM,
				bpartner,
				false); // noLUForVirtualTU == false => allow placing the CU (e.g. a packing material product) directly on the LU);
	}

	/**
	 * Test it with LU and TU configured
	 */
	@Test
	public void test_LU_TU()
	{
		final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);

		//
		// Configure:
		// LU: Palet with 2 IFCOs
		// TU: IFCO with 10items
		helper.createHU_PI_Item_IncludedHU(piPalet, piIFCO, new BigDecimal("2"));
		final I_M_HU_PI_Item_Product piIFCO_Product = helper.assignProduct(piIFCO_Item_Material, cuProduct, new BigDecimal("10"), cuUOM);

		//
		// Create HUs for cuQty=25
		// We expect:
		// 1 Palet with 2 IFCOs x 10items
		// 1 Palet with 1 IFCO x 5items
		final List<I_M_HU> hus = createHUs(piIFCO_Product, 25);

		assertEquals("Invalid created HUs count", 2, hus.size());

		// verify storage of palet1
		final I_M_HU huPalet1 = hus.get(0);
		assertHUStorageLevel(huPalet1, piPalet, 20);

		// verify storage of palet1's aggregate HU
		{
			final List<I_M_HU> huPalet1_aggregateHUs = handlingUnitsDAO.retrieveIncludedHUs(huPalet1);
			assertThat(huPalet1_aggregateHUs.size(), is(1));

			final I_M_HU aggregateVhu = huPalet1_aggregateHUs.get(0);
			assertTrue(handlingUnitsBL.isAggregateHU(aggregateVhu));

			assertHUStorageLevel(aggregateVhu, piVirtual, 20);
		}

		// verify storage of palet2
		final I_M_HU huPalet2 = hus.get(1);
		assertHUStorageLevel(huPalet2, piPalet, 5);

		System.out.println(HUXmlConverter.toString(HUXmlConverter.toXml(huPalet2)));

		// verify storage of palet2's aggregate HU
		{
			final List<I_M_HU> huPalet2_includedHUs = handlingUnitsDAO.retrieveIncludedHUs(huPalet2);
			assertThat(huPalet2_includedHUs.size(), is(2)); // expect two VHUs; the 2nd one is the aggregate HU "item stub"

			assertFalse(handlingUnitsBL.isAggregateHU(huPalet2_includedHUs.get(0)));
			assertTrue(handlingUnitsBL.isAggregateHU(huPalet2_includedHUs.get(1)));

			final I_M_HU hu = huPalet2_includedHUs.get(0);

			assertHUStorageLevel(hu, piIFCO, 5);
		}
	}

	/**
	 * Test with TUs without LU
	 */
	@Test
	public void test_noLU_TU()
	{
		//
		// Configure:
		// LU: none
		// TU: IFCO with 10items
		final I_M_HU_PI_Item_Product piIFCO_Product = helper.assignProduct(piIFCO_Item_Material, cuProduct, new BigDecimal("10"), cuUOM);

		//
		// Create HUs for cuQty=25
		// We expect:
		// 2 IFCOs x 10items
		// 1 IFCO x 5items
		final List<I_M_HU> hus = createHUs(piIFCO_Product, 25);

		Assert.assertEquals("Invalid IFCOs count", 3, hus.size());
		assertHUStorageLevel(hus.get(0), piIFCO, 10);
		assertHUStorageLevel(hus.get(1), piIFCO, 10);
		assertHUStorageLevel(hus.get(2), piIFCO, 5);
	}

	/**
	 * Test with virtual PIs (as TU) directly on LU
	 */
	@Test
	public void test_LU_virtualTU()
	{
		//
		// Configure:
		// LU: Palet
		// TU: Virtual PI, infinite capacity
		helper.createHU_PI_Item_IncludedHU(piPalet, piVirtual, new BigDecimal("9999"));

		//
		// Create HUs for cuQty=25
		// We expect:
		// 1 Palet with 1 virtual HU
		// 1 virtual HU x 25items
		final List<I_M_HU> hus = createHUs(piVirtual_Item_Product, 25);

		assertThat(hus).as("Invalid Palets count").hasSize(1);
		final I_M_HU huPalet = hus.get(0);
		assertHUStorageLevel(huPalet, piPalet, 25);

		final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);

		final List<I_M_HU> huPalet_VHUs = handlingUnitsDAO.retrieveIncludedHUs(huPalet);

		assertThat(huPalet_VHUs).as("Invalid VHUs count for Palet").hasSize(2); // expect two VHUs; the 2nd one is the aggregate HU "item stub"

		assertTrue(handlingUnitsBL.isAggregateHU(huPalet_VHUs.get(1)));
		assertFalse(handlingUnitsBL.isAggregateHU(huPalet_VHUs.get(0)));
		assertHUStorageLevel(huPalet_VHUs.get(0), piVirtual, 25);
	}

	/**
	 * Test with virtual PIs (as TU) without any LU
	 */
	@Test
	public void test_noLU_virtualTU()
	{
		//
		// Configure:
		// LU: none
		// TU: Virtual PI, infinite capacity

		//
		// Create HUs for cuQty=25
		// We expect:
		// 1 virtual HU x 25items
		final List<I_M_HU> hus = createHUs(piVirtual_Item_Product, 25);

		Assert.assertEquals("Invalid HUs count", 1, hus.size());
		final I_M_HU huVirtual = hus.get(0);
		assertHUStorageLevel(huVirtual, piVirtual, 25);
	}

}
