package de.metas.handlingunits.allocation.split.impl;

import java.math.BigDecimal;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.adempiere.util.time.SystemTime;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

import de.metas.handlingunits.HUTestHelper;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.IMutableHUContext;
import de.metas.handlingunits.allocation.IAllocationRequest;
import de.metas.handlingunits.allocation.IAllocationResult;
import de.metas.handlingunits.allocation.impl.AllocationUtils;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.X_M_HU_Item;
import de.metas.handlingunits.model.X_M_HU_PI_Version;

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

public class CompressedHUProducerDestinationTests
{
	private HUTestHelper helper;
	private I_M_HU_PI piLU;
	private final BigDecimal sixThousand = new BigDecimal("6000");
	
	/**
	 * Set up a HU PI that basically sais:
	 * <ul>
	 * <li>one palet can hold 5 IFCOs</li>
	 * <li>one IFCO can hold 10kg of tomatoes
	 * </ul>
	 */
	@Before
	public void init()
	{
		helper = new HUTestHelper();
		helper.init();

		final I_M_HU_PI piTU = helper.createHUDefinition("TU", X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);
		final I_M_HU_PI_Item piTU_Item = helper.createHU_PI_Item_Material(piTU);

		helper.assignProduct(piTU_Item, helper.pTomato, new BigDecimal("10"), helper.uomKg);
		helper.createHU_PI_Item_PackingMaterial(piTU, helper.pmIFCO);

		piLU = helper.createHUDefinition("LU", X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit);
		helper.createHU_PI_Item_IncludedHU(piLU, piTU, new BigDecimal("5"));

		helper.createHU_PI_Item_PackingMaterial(piLU, helper.pmPalet);
	}

// Makes no sense; loadHU is called not with the LU, but with the "bag"-VHU
//	/**
//	 * Verifies that the loader demands <code>IsCompressedVHU='Y'</code> on the given LU hu
//	 */
//	@Test(expected = AdempiereException.class)
//	public void testLoadHUOnlyWorksWithCompressedHU()
//	{
//		final I_M_HU luHU = InterfaceWrapperHelper.newInstance(I_M_HU.class);
//		luHU.setIsCompressedVHU(false);
//		InterfaceWrapperHelper.save(luHU);
//		
//		final IAllocationRequest request = mkRequest(sixThousand);
//
//		CompressedHUProducerDestination testee = new CompressedHUProducerDestination(piLU);
//		testee.loadHU(luHU, request);
//	}

}
