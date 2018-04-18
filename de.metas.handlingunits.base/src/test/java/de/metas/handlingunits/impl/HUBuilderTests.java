package de.metas.handlingunits.impl;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.util.Collections;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.junit.Before;
import org.junit.Test;

import de.metas.handlingunits.HUTestHelper;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.IMutableHUContext;
import de.metas.handlingunits.expectations.HUsExpectation;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Version;
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

public class HUBuilderTests
{
	private HUTestHelper helper;

	private IHandlingUnitsDAO handlingUnitsDAO;

	private IMutableHUContext huContext;
	private I_M_HU_PI_Version piLU_Version;
	private I_M_HU_PI piLU;

	private I_M_HU_PI_Item piLU_Item_TU;

	private I_M_HU_PI_Version piTU_Version;
	private I_M_HU_PI piTU;

	@Before
	public void init()
	{
		helper = new HUTestHelper();
		helper.init();

		handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);

		huContext = helper.createMutableHUContext();
		// POJOLookupMap.get().dumpStatus();

		// create a TU related packing instruction
		piTU = helper.createHUDefinition("TU", X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);

		I_M_HU_PI_Item piTU_Item = helper.createHU_PI_Item_Material(piTU);
		helper.createHU_PI_Item_PackingMaterial(piTU, helper.pmIFCO);

		piTU_Version = piTU_Item.getM_HU_PI_Version();
		assertThat(piTU_Version.getM_HU_PI(), is(piTU)); // guard

		// create a LU related packing instruction and link to it the TU related PI we created above
		piLU = helper.createHUDefinition("LU", X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit);
		helper.createHU_PI_Item_PackingMaterial(piLU, helper.pmPalet);

		piLU_Item_TU = helper.createHU_PI_Item_IncludedHU(piLU, piTU, new BigDecimal("5"));

		piLU_Version = piLU_Item_TU.getM_HU_PI_Version();
		assertThat(piLU_Version.getM_HU_PI(), is(piLU)); // guard
	}

	@Test
	public void simpleTest()
	{
		final I_M_HU_PI_Version piVersion = InterfaceWrapperHelper.newInstance(I_M_HU_PI_Version.class);
		InterfaceWrapperHelper.save(piVersion);

		final HUBuilder testee = new HUBuilder(huContext);
		final I_M_HU result = testee.create(piVersion);

		assertThat(result, notNullValue());
		assertThat(Services.get(IHandlingUnitsBL.class).getPIVersion(result), is(piVersion));
	}

	/**
	 * Invokes the {@link HUBuilder} once to create an LU handling unit according to {@link #piLU_Version}.
	 */
	@Test
	public void testOnlyLULevel()
	{
		final HUBuilder testee = new HUBuilder(huContext);

		final I_M_HU result = testee.create(piLU_Version);

		//@formatter:off
		final HUsExpectation compressedHUExpectation = new HUsExpectation()
				.newHUExpectation()
					.huPI(piLU)
					.newHUItemExpectation() // the virtual item that shall hold the "bag" VHU
						.itemType(X_M_HU_Item.ITEMTYPE_HUAggregate)
						.huPIItem(null)
						.noIncludedHUs() // note that the HU builder does not really recurse, but only creates the HU for the given piVersion, plus the HU's items. not no included HUs
					.endExpectation() // huItemExpectation
					.newHUItemExpectation() // the packing material item for this LU
						.noIncludedHUs()
						.itemType(X_M_HU_Item.ITEMTYPE_PackingMaterial)
						.packingMaterial(helper.pmPalet)
					.endExpectation() // HUItemExpectation()
				.endExpectation() // huExpectation
		;
		//@formatter:on
		compressedHUExpectation.assertExpected(Collections.singletonList(result));
	}

	/**
	 * Invokes the {@link HUBuilder} two times to first create a LU handling unit.
	 * It then explicitly  creates a "HU" type hu-item and invokes the builder with that item as parent item to create a TU handling unit.  
	 */
	@Test
	public void testLUandTULevelExplicit()
	{
		// create the LU hu with its items etc.
		final HUBuilder testeeLU = new HUBuilder(huContext);

		final I_M_HU luHU = testeeLU.create(piLU_Version);
		assertThat(luHU, notNullValue());
		assertThat(luHU.getM_HU_Item_Parent(), nullValue());

		// to create the TU and link it to the LU, we need to give it a parent-item
		final I_M_HU_Item parentItemAggregate = handlingUnitsDAO.retrieveItem(luHU, piLU_Item_TU);

		// these asserts are just guards. the real verification happens further down.
		// it's ok that the DAI returned the aggregate item, but..
		assertThat(parentItemAggregate, notNullValue());
		assertThat(parentItemAggregate.getItemType(), is(X_M_HU_Item.ITEMTYPE_HUAggregate));

		// .. now we explicitly create the HU item..
		handlingUnitsDAO.createHUItem(luHU, piLU_Item_TU);
		final I_M_HU_Item parentItem = handlingUnitsDAO.retrieveItem(luHU, piLU_Item_TU);

		// ..and now we don't want the aggregate item to be returned anymore
		assertThat(parentItem, notNullValue());
		assertThat(parentItem.getItemType(), is(X_M_HU_Item.ITEMTYPE_HandlingUnit));

		final HUBuilder testeeTU = new HUBuilder(huContext);
		testeeTU.setM_HU_Item_Parent(parentItem);
		final I_M_HU luTU = testeeTU.create(piTU_Version);

		assertThat(luTU, notNullValue());
		assertThat(luTU.getM_HU_Item_Parent(), notNullValue());
		assertThat(luTU.getM_HU_Item_Parent().getM_HU(), is(luHU));

		//@formatter:off
		final HUsExpectation compressedHUExpectation = new HUsExpectation()
				.newHUExpectation()
					.huPI(piLU)

					.newHUItemExpectation()
						.itemType(X_M_HU_Item.ITEMTYPE_HandlingUnit)
						// the HU builder does not really recurse, but only creates the HU for the given piVersion, plus the HU's items
						.newIncludedHUExpectation()
						.huPI(piTU)
							.newHUItemExpectation()
								.noIncludedHUs()
								.itemType(X_M_HU_Item.ITEMTYPE_Material)
							.endExpectation() // HUItemExpectation()
							
							.newHUItemExpectation()
								.noIncludedHUs()
								.itemType(X_M_HU_Item.ITEMTYPE_PackingMaterial)
								.packingMaterial(helper.pmIFCO)
							.endExpectation() // HUItemExpectation()
						.endExpectation() // includedHUExpectation
					.endExpectation() // HU - huItemExpectation

					.newHUItemExpectation()
						.itemType(X_M_HU_Item.ITEMTYPE_HUAggregate)	
						.huPIItem(null)
						.noIncludedHUs()
					.endExpectation() // HA - HUItemExpectation()

					.newHUItemExpectation()
						.noIncludedHUs()
						.itemType(X_M_HU_Item.ITEMTYPE_PackingMaterial)
						.packingMaterial(helper.pmPalet)
					.endExpectation() // PM - HUItemExpectation()
				.endExpectation(); // huExpectation
		//@formatter:on
		compressedHUExpectation.assertExpected(Collections.singletonList(luHU));
	}

	/**
	 * Invokes the {@link HUBuilder} two times to first create a LU handling unit.
	 * It then invokes the builder a second time to create a an "aggregate" handling unit below the LU. There shall be no "explicit" TU, but the TU's packaging shall be added to the LU's aggregate item.  
	 */
	@Test
	public void testLUandTULevel()
	{
		final HUBuilder testeeLU = new HUBuilder(huContext);
		final I_M_HU luHU = testeeLU.create(piLU_Version);

		final I_M_HU_Item parentItem = handlingUnitsDAO.retrieveItem(luHU, piLU_Item_TU);
		assertThat(parentItem, notNullValue());
		assertThat(parentItem.getItemType(), is(X_M_HU_Item.ITEMTYPE_HUAggregate));

		final HUBuilder testeeCompressedVHU = new HUBuilder(huContext);
		testeeCompressedVHU.setM_HU_Item_Parent(parentItem);
		/* final I_M_HU aggregateVHU = */ testeeCompressedVHU.create(piTU_Version);

		//@formatter:off
		final HUsExpectation compressedHUExpectation = new HUsExpectation()
				.newHUExpectation()
					.huPI(piLU)
					.newHUItemExpectation() // the virtual item that shall hold the "bag" VHU
						.itemType(X_M_HU_Item.ITEMTYPE_HUAggregate)
						.huPIItem(null)
						.newIncludedHUExpectation() // the "bag" VHU itself
							.huPI(helper.huDefVirtual)
							.newVirtualHUItemExpectation() // the product qty that is still "bagged"
								.noIncludedHUs()
								.itemType(X_M_HU_Item.ITEMTYPE_Material)
							.endExpectation()
							.newHUItemExpectation() // the remaining packaging (IFCO) that is still "bagged"
								.noIncludedHUs()
								.itemType(X_M_HU_Item.ITEMTYPE_PackingMaterial)
								.packingMaterial(helper.pmIFCO)
							.endExpectation()
						.endExpectation() // includedHUExpectation
					.endExpectation() // huItemExpectation with itemType=HA
					.newHUItemExpectation() // the packing material item for this LU
						.noIncludedHUs()
						.itemType(X_M_HU_Item.ITEMTYPE_PackingMaterial)
						.packingMaterial(helper.pmPalet)
					.endExpectation()
				.endExpectation() // huExpectation
		;
		//@formatter:on
		compressedHUExpectation.assertExpected(Collections.singletonList(luHU));
	}
}
