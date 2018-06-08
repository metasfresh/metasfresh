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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.adempiere.ad.wrapper.POJOWrapper;
import org.adempiere.util.Services;
import org.adempiere.util.lang.IMutable;
import org.junit.Assert;
import org.junit.Test;

import de.metas.handlingunits.AbstractHUTest;
import de.metas.handlingunits.HUIteratorListenerAdapter;
import de.metas.handlingunits.HUTestHelper;
import de.metas.handlingunits.IHUBuilder;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHUIterator;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.X_M_HU_PI_Item;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.handlingunits.storage.IHUItemStorage;

public class HUIteratorTest extends AbstractHUTest
{
	private IHandlingUnitsDAO handlingUnitsDAO;

	private I_M_HU_PI huDefIFCO;
	private I_M_HU_PI huDefPalet;

	@Override
	protected void initialize()
	{
		handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);

		//
		// Handling Units Definition
		huDefIFCO = helper.createHUDefinition(HUTestHelper.NAME_IFCO_Product, X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);
		{
			final I_M_HU_PI_Item huDefIFCO_item_MI = helper.createHU_PI_Item_Material(huDefIFCO);
			helper.assignProduct(huDefIFCO_item_MI, pTomato, new BigDecimal("20"), uomEach);

			helper.createHU_PI_Item_PackingMaterial(huDefIFCO, pmIFCO);
		}

		huDefPalet = helper.createHUDefinition(HUTestHelper.NAME_Palet_Product, X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit);
		{
			helper.createHU_PI_Item_IncludedHU(huDefPalet, huDefIFCO, new BigDecimal("2"));
			helper.createHU_PI_Item_PackingMaterial(huDefPalet, pmPallets);
		}
	}

	@Test
	public void test_AllIncludedHandlingUnitsAreVisited()
	{
		final I_M_HU palet1 = createHU("palet1", huDefPalet, null); // parent=null
		final I_M_HU palet1_ifco1 = createHU("palet1_ifco1", huDefIFCO, palet1);
		final I_M_HU palet1_ifco2 = createHU("palet1_ifco2", huDefIFCO, palet1);

		final List<Integer> seenHuIdsExpected = new ArrayList<>();
		seenHuIdsExpected.add(palet1.getM_HU_ID());
		seenHuIdsExpected.add(palet1_ifco1.getM_HU_ID());
		seenHuIdsExpected.add(palet1_ifco2.getM_HU_ID());

		final List<Integer> seenHuIds = new ArrayList<>();
		final HUIterator iterator = new HUIterator();
		iterator.setListener(new HUIteratorListenerAdapter()
		{
			@Override
			public Result beforeHU(final IMutable<I_M_HU> hu)
			{
				final int huId = hu.getValue().getM_HU_ID();
				Assert.assertFalse("HU " + hu + " was already visited", seenHuIds.contains(huId));
				seenHuIds.add(huId);
				return Result.CONTINUE;
			}
		});
		iterator.iterate(palet1);

		Assert.assertTrue("HU shall be visited: " + palet1, seenHuIds.contains(palet1.getM_HU_ID()));
		Assert.assertTrue("HU shall be visited: " + palet1_ifco1, seenHuIds.contains(palet1_ifco1.getM_HU_ID()));
		Assert.assertTrue("HU shall be visited: " + palet1_ifco2, seenHuIds.contains(palet1_ifco2.getM_HU_ID()));
		Assert.assertEquals("Invalid seen HU ID list", seenHuIdsExpected, seenHuIds);
	}

	@Test
	public void test_avoidDuplicates()
	{
		final I_M_HU palet1 = createHU("palet1", huDefPalet, null);
		final I_M_HU palet1_ifco1 = createHU("palet1_ifco1", huDefIFCO, palet1);
		final I_M_HU palet1_ifco2 = createHU("palet1_ifco2", huDefIFCO, palet1);

		final List<Integer> seenHuIds = new ArrayList<>();
		final HUIterator iterator = new HUIterator();
		iterator.setListener(new HUIteratorListenerAdapter()
		{
			@Override
			public Result beforeHU(final IMutable<I_M_HU> hu)
			{
				Assert.assertFalse("HU " + hu + " was already visited",
						seenHuIds.contains(hu.getValue().getM_HU_ID()));
				return Result.CONTINUE;
			}
		});
		iterator.iterate(Arrays.asList(palet1_ifco2, palet1, palet1_ifco1));
	}

	/**
	 * Test on which we don't iterate below HUs
	 */
	@Test
	public void test_SkipDownsteam_HU()
	{
		final I_M_HU palet1 = createHU("palet1", huDefPalet, null);
		createHU("palet1_ifco1", huDefIFCO, palet1);
		createHU("palet1_ifco2", huDefIFCO, palet1);

		final HUIterator iterator = new HUIterator();
		iterator.setListener(new HUIteratorListenerAdapter()
		{
			@Override
			public Result beforeHU(final IMutable<I_M_HU> hu)
			{
				return Result.SKIP_DOWNSTREAM;
			}

			@Override
			public Result afterHUItem(final I_M_HU_Item item)
			{
				throw new RuntimeException("Shall never been called");
			}

			@Override
			public Result afterHUItemStorage(final IHUItemStorage itemStorage)
			{
				throw new RuntimeException("Shall never been called");
			}
		});
		iterator.iterate(Arrays.asList(palet1));
	}

	@Test
	public void test_beforeHU_afterHU()
	{
		final I_M_HU palet1 = createHU("palet1", huDefPalet, null);
		createHU("palet1_ifco1", huDefIFCO, palet1);
		createHU("palet1_ifco2", huDefIFCO, palet1);

		final HUIterator iterator = new HUIterator();
		iterator.setListener(new HUIteratorListenerAdapter()
		{
			final Map<Integer, Boolean> huId2called = new HashMap<>();

			@Override
			public Result beforeHU(final IMutable<I_M_HU> hu)
			{
				final int huId = hu.getValue().getM_HU_ID();

				final Boolean calledOld = huId2called.get(huId);
				Assert.assertNull("beforeHU shall never been called for " + hu, calledOld);

				huId2called.put(huId, true);
				return Result.CONTINUE;
			}

			@Override
			public Result afterHU(final I_M_HU hu)
			{
				final int huId = hu.getM_HU_ID();

				final Boolean calledOld = huId2called.get(huId);
				Assert.assertEquals("beforeHU shall BE called for " + hu,
						Boolean.TRUE, calledOld);

				huId2called.remove(huId);

				return Result.CONTINUE;
			}
		});
		iterator.iterate(Arrays.asList(palet1));
	}

	@Test
	public void test_setHUIterator()
	{
		final IHUIterator iterator = new HUIterator();

		final List<I_M_HU> beforeHUs = new ArrayList<>();
		final HUIteratorListenerAdapter listener = new HUIteratorListenerAdapter()
		{
			@Override
			public Result beforeHU(final IMutable<I_M_HU> hu)
			{
				Assert.assertSame("Invalid HUIterator", iterator, getHUIterator());
				beforeHUs.add(hu.getValue());
				return Result.CONTINUE;
			}

			@Override
			public Result afterHU(final I_M_HU hu)
			{
				Assert.assertSame("Invalid HUIterator", iterator, getHUIterator());
				return Result.CONTINUE;
			}
		};

		Assert.assertNull("HUIterator shall not be set before iteration", listener.getHUIterator());
		iterator.setListener(listener);

		Assert.assertNull("HUIterator shall not be set before iteration", listener.getHUIterator());

		final I_M_HU palet1 = createHU("palet1", huDefPalet, null);
		iterator.iterate(Arrays.asList(palet1));

		Assert.assertSame("HUIterator shall be set after iteration", iterator, listener.getHUIterator());

		Assert.assertEquals("Invalid visited HUs on beforeHU",
				Arrays.asList(palet1), // expected
				beforeHUs // actual
		);
	}

	private int calculateMaxDepthReached(final IHUIterator iterator, final I_M_HU rootHU)
	{
		// Max Depth that was reached after iteration
		final int[] maxDepthReached = new int[] { -1000 };

		//
		// Create iterator and navigate starting from "palet1"
		iterator.setListener(new HUIteratorListenerAdapter()
		{
			@Override
			protected Result getDefaultResult()
			{
				// Update maxDepthReached
				final int depth = getHUIterator().getDepth();
				maxDepthReached[0] = depth > maxDepthReached[0] ? depth : maxDepthReached[0];
				// System.out.println("depth=" + depth);

				// Always continue
				return Result.CONTINUE;
			}
		});
		iterator.iterate(rootHU);

		return maxDepthReached[0];
	}

	/**
	 * Test: enforce the max depth to {@link IHUIterator#DEPTH_FIRSTHUCHILD_HU} and make sure is respected
	 */
	@Test
	public void test_MaxDepth()
	{
		final I_M_HU palet1 = createHU("palet1", huDefPalet, null);
		createHU("palet1_ifco1", huDefIFCO, palet1);
		createHU("palet1_ifco2", huDefIFCO, palet1);

		//
		// Calculate Max Depth we can reach without any restriction
		// => i.e. depth of HU Items of direct children (i.e. IFCOs)
		{
			final HUIterator iterator = new HUIterator();
			final int maxDepthNoRestriction = calculateMaxDepthReached(iterator, palet1);
			Assert.assertEquals("MaxDepth without restriction shall be the depth of HU Items of first child",
					IHUIterator.DEPTH_FIRSTHUCHILD_HU_Item, // expected
					maxDepthNoRestriction // actual
			);
		}

		//
		// Enforce max depth to direct child HUs (i.e. IFCOs)
		// Calculate it and check it
		{
			final HUIterator iterator = new HUIterator();
			iterator.setDepthMax(IHUIterator.DEPTH_FIRSTHUCHILD_HU);
			final int maxDepth = calculateMaxDepthReached(iterator, palet1);
			Assert.assertEquals("MaxDepth shall be the depth of direct child HUs (i.e. IFCOs)",
					IHUIterator.DEPTH_FIRSTHUCHILD_HU, // expected
					maxDepth // actual
			);
		}
	}

	/**
	 * Create an HU structure, iterate it and assume preciselly what objects shall we get on each depth/level.
	 */
	@Test
	public void test_HUs_and_Depths()
	{
		final I_M_HU palet1 = createHU("palet1", huDefPalet, null);
		final I_M_HU palet1_ifco1 = createHU("palet1_ifco1", huDefIFCO, palet1);
		final I_M_HU palet1_ifco2 = createHU("palet1_ifco2", huDefIFCO, palet1);

		//
		// Build a map about what HUs we expected on each depth/level.
		final Map<Integer, List<? extends Object>> depth2expectedItems = new HashMap<>();
		{
			depth2expectedItems.put(IHUIterator.DEPTH_STARTING_HU, Arrays.<Object> asList(palet1));
			depth2expectedItems.put(IHUIterator.DEPTH_STARTING_HU_Item, handlingUnitsDAO.retrieveItems(palet1));
			depth2expectedItems.put(IHUIterator.DEPTH_FIRSTHUCHILD_HU, Arrays.<Object> asList(palet1_ifco1, palet1_ifco2));

			final List<I_M_HU_Item> huItems_FirstChild = new ArrayList<>();
			huItems_FirstChild.addAll(handlingUnitsDAO.retrieveItems(palet1_ifco1));
			huItems_FirstChild.addAll(handlingUnitsDAO.retrieveItems(palet1_ifco2));
			depth2expectedItems.put(IHUIterator.DEPTH_FIRSTHUCHILD_HU_Item, huItems_FirstChild);
		}

		//
		// Create iterator and navigate starting from "palet1"
		final HUIterator iterator = new HUIterator();
		iterator.setListener(new HUIteratorListenerAdapter()
		{
			private final void assertExpectedItem(final Object item)
			{
				final int depth = getHUIterator().getDepth();

				// Get expected HU IDs for current depth
				final List<? extends Object> expectedItems = depth2expectedItems.get(depth);
				final boolean found = expectedItems != null && expectedItems.contains(item);
				Assert.assertTrue("Item shall be present in the list of expected items of this depth/level"
						+ "\nDepth=" + depth
						+ "\nExpected Items=" + expectedItems
						+ "\nCurrent Item=" + item,
						found);
			}

			@Override
			public Result beforeHU(final IMutable<I_M_HU> huRef)
			{
				assertExpectedItem(huRef.getValue());
				return Result.CONTINUE;
			}

			@Override
			public Result beforeHUItem(final IMutable<I_M_HU_Item> item)
			{
				assertExpectedItem(item.getValue());
				return Result.CONTINUE;
			}

			@Override
			public Result beforeHUItemStorage(final IMutable<IHUItemStorage> itemStorage)
			{
				Assert.fail("We shall not reach this item because our HUs shall not have this item: " + itemStorage.getValue());
				assertExpectedItem(itemStorage.getValue());
				return Result.CONTINUE;
			}
		});
		iterator.iterate(Arrays.asList(palet1));
	}

	private I_M_HU createHU(final String name, final I_M_HU_PI huPI, final I_M_HU parent)
	{
		final IHUContext huContext = helper.getHUContext();

		//
		// Search or create the parent item to link to
		I_M_HU_Item parentItemToUse = null;
		if (parent != null)
		{
			for (final I_M_HU_Item parentItem : Services.get(IHandlingUnitsDAO.class).retrieveItems(parent))
			{
				final String parentItemType = Services.get(IHandlingUnitsBL.class).getItemType(parentItem);
				if (X_M_HU_PI_Item.ITEMTYPE_HandlingUnit.equals(parentItemType))
				{
					parentItemToUse = parentItem;
					break;
				}
			}

			if (parentItemToUse == null)
			{
				final I_M_HU_PI_Item piItemForTU = handlingUnitsDAO.retrieveParentPIItemForChildHUOrNull(parent, huPI, huContext);
				parentItemToUse = handlingUnitsDAO.createHUItem(parent, piItemForTU);
			}
		}

		final IHUBuilder huBuilder = Services.get(IHandlingUnitsDAO.class).createHUBuilder(huContext);
		huBuilder.setM_HU_Item_Parent(parentItemToUse);
		huBuilder.setC_BPartner(null); // no BP available in our test
		final I_M_HU hu = huBuilder.create(huPI);

		POJOWrapper.setInstanceName(hu, name);

		return hu;
	}
}
