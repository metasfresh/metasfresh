package de.metas.handlingunits.allocation.impl;

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

import org.compiere.model.I_C_BPartner;
import org.junit.Assert;
import org.junit.Test;

import de.metas.handlingunits.AbstractHUTest;
import de.metas.handlingunits.HUAssert;
import de.metas.handlingunits.HUTestHelper;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.allocation.IAllocationRequest;
import de.metas.handlingunits.allocation.MockedAllocationSourceDestination;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.X_M_HU_PI_Version;

/**
 * Test how HUs are created and which {@link I_M_HU_PI_Item_Product}s are used when we have a BPartner set in our referenced model.
 *
 * @author tsa
 *
 * @task http://dewiki908/mediawiki/index.php/06797_Use_partner_from_manufacturing_order_when_allocate_qty_in_Produktion_Fertigstellungs_Mask_POS_%28108783959818%29
 */
public class HULoader_WithPartner_Tests extends AbstractHUTest
{
	// private I_M_HU_PI huDefIFCO;
	private I_M_HU_PI_Item huDefIFCO_itemMA;

	private I_C_BPartner bpartner01;
	private I_C_BPartner bpartner02;

	// private MockedAllocationSourceDestination allocationSource;
	private HUProducerDestination allocationDestination;
	private HULoader loader;

	@Override
	protected void initialize()
	{
		bpartner01 = helper.createBPartner("BPartner01");
		bpartner02 = helper.createBPartner("BPartner02");

		//
		// Handling Units Definition
		final I_M_HU_PI huDefIFCO = helper.createHUDefinition(HUTestHelper.NAME_IFCO_Product, X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);
		{
			huDefIFCO_itemMA = helper.createHU_PI_Item_Material(huDefIFCO);
		}

		//
		//
		final MockedAllocationSourceDestination allocationSource = new MockedAllocationSourceDestination();
		allocationSource.setQtyToUnload(MockedAllocationSourceDestination.ANY);

		allocationDestination = HUProducerDestination.of(huDefIFCO);

		loader = HULoader.of(allocationSource, allocationDestination);

	}

	private final IAllocationRequest createAllocationRequest(final int qty, final Object referencedModel)
	{
		return AllocationUtils.createQtyRequest(
				helper.getHUContext(),
				pTomato,
				new BigDecimal(qty), // qtyRequest,
				pTomato.getC_UOM(),
				helper.getTodayDate(),
				referencedModel);
	}

	private final void assertStorageLevel(final I_M_HU hu, final int qtyExpectedInt)
	{
		final IHUContext huContext = helper.getHUContext();
		final BigDecimal qtyExpected = BigDecimal.valueOf(qtyExpectedInt);
		HUAssert.assertStorageLevel(huContext, hu, pTomato, qtyExpected);
	}

	@Test
	public void test_BPartner_InScope()
	{
		//
		// Setup HU PI Item Products
		helper.assignProduct(huDefIFCO_itemMA, pTomato, new BigDecimal("5"), uomEach);
		helper.assignProduct(huDefIFCO_itemMA, pTomato, new BigDecimal("20"), uomEach, bpartner01);

		//
		// Create Referenced model (having BP set)
		final Object referencedModel = helper.createDummyReferenceModel(bpartner01);

		//
		// Execute load to HUs and get those HUs
		final IAllocationRequest unloadRequest = createAllocationRequest(19, referencedModel);
		loader.load(unloadRequest);
		final List<I_M_HU> hus = allocationDestination.getCreatedHUs();

		//
		// Assert: only one HU shall be created because for BPartner01 we accept 20items and we loaded 19
		Assert.assertEquals("Invalid number of created HUs", 1, hus.size());

		//
		// Assert: loaded Qty to our HU is 19
		assertStorageLevel(hus.get(0), 19);

		//
		// Assert: M_HU.C_BPartner_ID is set
		Assert.assertEquals("Invalid BPartner in " + hus.get(0), bpartner01, hus.get(0).getC_BPartner());
	}

	/**
	 * NOTE: shall work exactly like {@link #test_No_BPartner()}
	 */
	@Test
	public void test_BPartner_OutOfScope()
	{
		//
		// Setup HU PI Item Products
		helper.assignProduct(huDefIFCO_itemMA, pTomato, new BigDecimal("5"), uomEach);
		helper.assignProduct(huDefIFCO_itemMA, pTomato, new BigDecimal("20"), uomEach, bpartner01);

		//
		// Create Referenced model (using BPartner02, for which we don't have a particular PI Item Product)
		final Object referencedModel = helper.createDummyReferenceModel(bpartner02);

		//
		// Execute load to HUs and get those HUs
		final IAllocationRequest unloadRequest = createAllocationRequest(19, referencedModel);
		loader.load(unloadRequest);
		final List<I_M_HU> hus = allocationDestination.getCreatedHUs();

		//
		// Assert: 4 HUs shall be created because we accept 5items/HU and we loaded 19
		Assert.assertEquals("Invalid number of created HUs", 4, hus.size());

		//
		// Assert: loaded Qty to our HU is 19
		assertStorageLevel(hus.get(0), 5);
		assertStorageLevel(hus.get(1), 5);
		assertStorageLevel(hus.get(2), 5);
		assertStorageLevel(hus.get(3), 4);

		//
		// Assert: M_HU.C_BPartner_ID is set
		Assert.assertEquals("Invalid BPartner in " + hus.get(0), bpartner02, hus.get(0).getC_BPartner());
		Assert.assertEquals("Invalid BPartner in " + hus.get(1), bpartner02, hus.get(1).getC_BPartner());
		Assert.assertEquals("Invalid BPartner in " + hus.get(2), bpartner02, hus.get(2).getC_BPartner());
		Assert.assertEquals("Invalid BPartner in " + hus.get(3), bpartner02, hus.get(3).getC_BPartner());
	}

	@Test
	public void test_No_BPartner()
	{
		//
		// Setup HU PI Item Products
		helper.assignProduct(huDefIFCO_itemMA, pTomato, new BigDecimal("5"), uomEach);
		helper.assignProduct(huDefIFCO_itemMA, pTomato, new BigDecimal("20"), uomEach, bpartner01);

		//
		// Create Referenced model (NO BPartner!)
		final Object referencedModel = helper.createDummyReferenceModel(null);

		//
		// Execute load to HUs and get those HUs
		final IAllocationRequest unloadRequest = createAllocationRequest(19, referencedModel);
		loader.load(unloadRequest);
		final List<I_M_HU> hus = allocationDestination.getCreatedHUs();

		//
		// Assert: 4 HUs shall be created because we accept 5items/HU and we loaded 19
		Assert.assertEquals("Invalid number of created HUs", 4, hus.size());

		//
		// Assert: loaded Qty to our HU is 19
		assertStorageLevel(hus.get(0), 5);
		assertStorageLevel(hus.get(1), 5);
		assertStorageLevel(hus.get(2), 5);
		assertStorageLevel(hus.get(3), 4);

		//
		// Assert: M_HU.C_BPartner_ID is set
		Assert.assertEquals("Invalid BPartner in " + hus.get(0), null, hus.get(0).getC_BPartner());
		Assert.assertEquals("Invalid BPartner in " + hus.get(1), null, hus.get(1).getC_BPartner());
		Assert.assertEquals("Invalid BPartner in " + hus.get(2), null, hus.get(2).getC_BPartner());
		Assert.assertEquals("Invalid BPartner in " + hus.get(3), null, hus.get(3).getC_BPartner());
	}

}
