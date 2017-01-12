package de.metas.handlingunits.impl;

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

import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.model.IContextAware;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.junit.Assert;
import org.junit.Test;

import de.metas.handlingunits.AbstractHUTest;
import de.metas.handlingunits.HUTestHelper;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHUTrxBL;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.IMutableHUContext;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.handlingunits.storage.IHUStorage;

public class HandlingUnitsBL_Destroy_Test extends AbstractHUTest
{
	/** Service under test */
	private HandlingUnitsBL handlingUnitsBL;
	
	/** Service under test */
	private HUTrxBL huTrxBL;
	
	/** other service */
	private IHandlingUnitsDAO handlingUnitsDAO;

	// CU config
	private I_M_Product cuProduct;
	private I_C_UOM cuUOM;

	// TU config
	private I_M_HU_PI piTU;
	private I_M_HU_PI_Item piTU_Item;
	private I_M_HU_PI_Item_Product piTU_Item_Product;
	private BigDecimal qtyCUsPerTU;

	// LU config
	private I_M_HU_PI piLU;
	private I_M_HU_PI_Item piLU_Item;
	private BigDecimal qtyTUsPerLU;

	@Override
	protected HUTestHelper createHUTestHelper()
	{
		return new HUTestHelper()
		{
			@Override
			protected String createAndStartTransaction()
			{
				final ITrxManager trxManager = Services.get(ITrxManager.class);
				final String trxName = trxManager.createTrxName("HandlingUnitsBL_Destroy_Test");
				return trxName;
			}

			@Override
			protected IMutableHUContext createInitialHUContext(final IContextAware contextProvider)
			{
				return createMutableHUContextForProcessing(contextProvider.getTrxName());
			}
		};
	}

	@Override
	protected void initialize()
	{
		handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
		
		handlingUnitsBL = (HandlingUnitsBL)Services.get(IHandlingUnitsBL.class);
		huTrxBL = (HUTrxBL)Services.get(IHUTrxBL.class);

		createMaterData();
	}

	private void createMaterData()
	{
		cuProduct = pTomato;
		cuUOM = uomKg;

		//
		// TU
		qtyCUsPerTU = BigDecimal.valueOf(10);
		piTU = helper.createHUDefinition(HUTestHelper.NAME_IFCO_Product, X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);
		{
			piTU_Item = helper.createHU_PI_Item_Material(piTU);
			piTU_Item_Product = helper.assignProduct(piTU_Item, cuProduct, qtyCUsPerTU, cuUOM); // 10 x Tomato Per IFCO
		}

		//
		// LU
		qtyTUsPerLU = BigDecimal.valueOf(48);
		piLU = helper.createHUDefinition(HUTestHelper.NAME_Palet_Product, X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit);
		{
			piLU_Item = helper.createHU_PI_Item_IncludedHU(piLU, piTU, qtyTUsPerLU);
		}
	}

	@Test
	public void test_destroyIfEmptyStorage_TakeOutLastTUFromLU_AssumeLUIsDestroyed()
	{
		final IHUContext huContext = helper.getHUContext();

		//
		// Create LU with ONE TU
		final I_M_HU luHU = helper.createLU(huContext, piLU_Item, piTU_Item_Product, new BigDecimal("1"));

		//
		// Get created TU
		final List<I_M_HU> tuHUs = handlingUnitsDAO.retrieveIncludedHUs(luHU);
		Assert.assertEquals("Invalid TUs count", 1, tuHUs.size());
		final I_M_HU tuHU = tuHUs.get(0);

		//
		// Empty the TU (i.e. just remove it's storage Qty)
		{
			final IHUStorage tuHUStorage = huContext.getHUStorageFactory().getStorage(tuHU);
			final BigDecimal cuQty = tuHUStorage.getQty(cuProduct, cuUOM);
			tuHUStorage.addQty(cuProduct, cuQty.negate(), cuUOM);
			Assert.assertTrue("TU's storage shall be empty", tuHUStorage.isEmpty());
		}

		//
		// Destroy the TU
		// i.e. calling method under test
		handlingUnitsBL.destroyIfEmptyStorage(huContext, tuHU);
		Assert.assertEquals("TU shall be destroyed", X_M_HU.HUSTATUS_Destroyed, tuHU.getHUStatus());
		Assert.assertEquals("TU shall have no parent", null, tuHU.getM_HU_Item_Parent());

		//
		// Make sure the LU was also destroyed (because this was the only TU on it)
		// NOTE: because LU is changed in another instace, we need to refresh our instance
		// FIXME: we need to find a way of doing this somehow auto-magically (without any performance penalty)
		InterfaceWrapperHelper.refresh(luHU);
		Assert.assertEquals("LU shall be destroyed", X_M_HU.HUSTATUS_Destroyed, luHU.getHUStatus());
	}

	@Test
	public void test_HUTrxBL_setParentHU_TakeOutLastTUFromLU_AssumeLUIsDestroyed()
	{
		final IHUContext huContext = helper.getHUContext();

		//
		// Create LU with ONE TU
		final I_M_HU luHU = helper.createLU(huContext, piLU_Item, piTU_Item_Product, new BigDecimal("1"));

		//
		// Get created TU
		final List<I_M_HU> tuHUs = handlingUnitsDAO.retrieveIncludedHUs(luHU);
		Assert.assertEquals("Invalid TUs count", 1, tuHUs.size());
		final I_M_HU tuHU = tuHUs.get(0);

		//
		// Take out the TU from LU
		// i.e. calling method under test
		handlingUnitsBL.destroyIfEmptyStorage(huContext, tuHU);
		huTrxBL.setParentHU(huContext,
				(I_M_HU_Item)null, // parentHUItem=null
				tuHU
				);

		//
		// Make sure the LU was also destroyed (because this was the only TU on it)
		// NOTE: because LU is changed in another instace, we need to refresh our instance
		// FIXME: we need to find a way of doing this somehow auto-magically (without any performance penalty)
		InterfaceWrapperHelper.refresh(luHU);
		Assert.assertEquals("LU shall be destroyed", X_M_HU.HUSTATUS_Destroyed, luHU.getHUStatus());
	}

}
