package de.metas.handlingunits;

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

import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.model.IContextAware;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Transaction;
import org.hamcrest.Matchers;
import org.junit.Assert;

import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Version;
import de.metas.handlingunits.storage.IGenericHUStorage;
import de.metas.handlingunits.storage.IHUItemStorage;
import de.metas.handlingunits.storage.IHUStorage;
import de.metas.handlingunits.storage.IHUStorageFactory;
import de.metas.handlingunits.storage.IProductStorage;
import de.metas.handlingunits.storage.impl.MTransactionProductStorage;

public final class HUAssert
{
	private HUAssert()
	{
		super();
	}

	public static void assertHUStoragesAreEmpty(final List<I_M_HU> hus)
	{
		final IHUStorageFactory storageFactory = Services.get(IHandlingUnitsBL.class).getStorageFactory();

		for (final I_M_HU hu : hus)
		{
			final IHUStorage huStorage = storageFactory.getStorage(hu);
			Assert.assertTrue("Storage shall be empty for " + hu, huStorage.isEmpty());
		}
	}

	public static void assertHUItemStoragesAreEmpty()
	{
		final IHUStorageFactory storageFactory = Services.get(IHandlingUnitsBL.class).getStorageFactory();

		for (final I_M_HU_Item item : POJOLookupMap.get().getRecords(I_M_HU_Item.class))
		{
			final IHUItemStorage itemStorage = storageFactory.getStorage(item);
			Assert.assertTrue("Item storage shall be empty: " + itemStorage, itemStorage.isEmpty());

			// Check also parent storages
			IGenericHUStorage huStorage = itemStorage.getParentStorage();
			while (huStorage != null)
			{
				Assert.assertTrue("HU storage shall be empty: " + huStorage, huStorage.isEmpty());
				huStorage = huStorage.getParentStorage();
			}
		}
	}

	public static void assertMTransactionStoragesAreEmpty()
	{
		for (final I_M_Transaction mtrx : POJOLookupMap.get().getRecords(I_M_Transaction.class))
		{
			final MTransactionProductStorage storage = new MTransactionProductStorage(mtrx);

			if (storage.isReversal())
			{
				final boolean full = storage.isFull();
				Assert.assertTrue("Storage shall be full: " + storage, full);
			}
			else
			{
				final boolean empty = storage.isEmpty();
				Assert.assertTrue("Storage shall be empty: " + storage, empty);
			}
		}
	}

	public static void assertHUStorage(final I_M_HU hu, final I_M_Product product, final BigDecimal qtyExpected)
	{
		final I_C_UOM uom = product.getC_UOM();

		final IHUStorageFactory storageFactory = Services.get(IHandlingUnitsBL.class).getStorageFactory();
		final IHUStorage huStorage = storageFactory.getStorage(hu);
		final BigDecimal qtyActual = huStorage.getQty(product, uom);

		final String message = "Invalid HU Storage Qty: "
				+ "\nproduct=" + product.getValue()
				+ "\nHU=" + hu;
		Assert.assertThat(message, qtyActual, Matchers.comparesEqualTo(qtyExpected));
	}

	public static void assertHUDestroyed(final I_M_HU hu)
	{
		final boolean destroyed = Services.get(IHandlingUnitsBL.class).isDestroyed(hu);
		Assert.assertTrue("HU shall be destroyed"
				+ "\nPI:" + Services.get(IHandlingUnitsBL.class).getPI(hu).getName()
				+ "\nHU:" + hu,
				destroyed);
	}

	public static void assertHUDestroyed(final List<I_M_HU> hus)
	{
		for (final I_M_HU hu : hus)
		{
			assertHUDestroyed(hu);
		}
	}

	public static void assertStorageLevels(final IProductStorage storage,
			final String qtyTotalStr,
			final String qtyStr,
			final String qtyFreeStr)
	{
		Assert.assertNotNull("storage shall not be null", storage);

		Assert.assertThat("Invalid storage total qty: " + storage,
				storage.getQtyCapacity(),
				Matchers.comparesEqualTo(new BigDecimal(qtyTotalStr)));
		Assert.assertThat("Invalid storage free qty: " + storage,
				storage.getQtyFree(),
				Matchers.comparesEqualTo(new BigDecimal(qtyFreeStr)));
		Assert.assertThat("Invalid storage qty: " + storage,
				storage.getQty(),
				Matchers.comparesEqualTo(new BigDecimal(qtyStr)));
	}

	public static void assertStorageLevel(final I_M_HU hu, final I_M_Product product, final BigDecimal qtyExpected)
	{
		final IContextAware contextProvider = InterfaceWrapperHelper.getContextAware(hu);
		final IMutableHUContext huContext = Services.get(IHUContextFactory.class).createMutableHUContext(contextProvider);
		assertStorageLevel(huContext, hu, product, qtyExpected);
	}

	public static void assertStorageLevel(final IHUContext huContext, final I_M_HU hu, final I_M_Product product, final BigDecimal qtyExpected)
	{
		final BigDecimal qtyActual = huContext.getHUStorageFactory()
				.getStorage(hu)
				.getProductStorage(product)
				.getQty();

		Assert.assertThat("Invalid storage qty for HU=" + hu + ", product=" + product,
				qtyActual,
				Matchers.comparesEqualTo(qtyExpected));
	}

	/**
	 * Asserts that given HU has given {@link I_M_HU_PI}
	 *
	 * @param i_M_HU
	 * @param huDefPalet
	 */
	public static void assertHU_PI(final I_M_HU hu, final I_M_HU_PI expectedPI)
	{
		Assert.assertNotNull("hu shall not be null", hu);

		final I_M_HU_PI_Version huPiVersion = Services.get(IHandlingUnitsBL.class).getPIVersion(hu);
		Assert.assertNotNull("HU_PI_Version shall not be null for " + hu, huPiVersion);

		final I_M_HU_PI huPI = huPiVersion.getM_HU_PI();
		Assert.assertEquals("Invalid HU_PI for " + hu, expectedPI, huPI);
	}

	public static void assertAllStoragesAreValid()
	{
		for (final I_M_HU hu : POJOLookupMap.get().getRecords(I_M_HU.class))
		{
			assertStorageValid(hu);
		}
	}

	public static void assertStorageValid(final I_M_HU hu)
	{
		Assert.assertNotNull("HU shall not be null", hu);

		final IHUStorageFactory storageFactory = Services.get(IHandlingUnitsBL.class).getStorageFactory();

		for (final I_M_HU_Item huItem : Services.get(IHandlingUnitsDAO.class).retrieveItems(hu))
		{
			final IHUItemStorage itemStorage = storageFactory.getStorage(huItem);
			assertStorageValid(itemStorage);
		}
	}

	public static void assertStorageValid(final IHUItemStorage itemStorage)
	{
		Assert.assertNotNull("itemStorage shall not be null", itemStorage);
		if (itemStorage.isVirtual())
		{
			assertStorageValid_Virtual(itemStorage);
		}
		else
		{
			assertStorageValid_NonVirtual(itemStorage);
		}
	}

	private static void assertStorageValid_NonVirtual(final IHUItemStorage itemStorage)
	{
		Assert.assertNotNull("itemStorage shall not be null", itemStorage);

		// TODO: retrieve included storages and make sure their SUM is same with this storage
	}

	private static void assertStorageValid_Virtual(final IHUItemStorage itemStorage)
	{
		Assert.assertNotNull("itemStorage shall not be null", itemStorage);
		// NOTE: nothing to validate here. In the past we had M_HU_Item_Storage_Detail but we removed because it was useless and overhead.
	}

	/***
	 * Do nothing. Used only if you have no assert methods in your test and want to avoid PMD warning.
	 *
	 * @param msg
	 * @return
	 */
	public static boolean assertMock(final String msg)
	{
		return true;
	}

	public static void assertEqualsOrParentOf(final I_M_HU hu, final I_M_HU huParentExpected)
	{
		final String message = null;
		assertEqualsOrParentOf(message, hu, huParentExpected);
	}

	public static void assertEqualsOrParentOf(final String message, final I_M_HU hu, final I_M_HU huParentExpected)
	{
		Assert.assertNotNull("hu shall not be null", hu);
		Assert.assertNotNull("huParentExpected shall not be null", huParentExpected);

		final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
		final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);

		// Same HU => return because it's fine
		if (hu.getM_HU_ID() == huParentExpected.getM_HU_ID())
		{
			return;
		}

		final StringBuilder huTraceInfo = new StringBuilder();
		huTraceInfo.append(handlingUnitsBL.getDisplayName(hu));

		I_M_HU huParent = handlingUnitsDAO.retrieveParent(hu);
		while (huParent != null)
		{
			// Same parent => return because it's fine
			if (huParent.getM_HU_ID() == huParentExpected.getM_HU_ID())
			{
				return;
			}

			huTraceInfo.append("<-").append(handlingUnitsBL.getDisplayName(huParent));

			huParent = handlingUnitsDAO.retrieveParent(huParent);
		}

		final String huParentExpectedStr = handlingUnitsBL.getDisplayName(huParent);

		final StringBuilder errorMsg = new StringBuilder();
		errorMsg.append("HU ").append(huTraceInfo).append(" does not have ").append(huParentExpectedStr).append(" as parent");
		if (!Check.isEmpty(message, true))
		{
			errorMsg.insert(0, " - ");
			errorMsg.insert(0, message);
		}
		Assert.fail(errorMsg.toString());
	}
}
