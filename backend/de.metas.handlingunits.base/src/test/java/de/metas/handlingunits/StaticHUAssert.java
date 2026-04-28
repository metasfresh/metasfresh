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

import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.IContextAware;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Transaction;
import static org.assertj.core.api.Assertions.*;

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
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.util.Check;
import de.metas.util.Services;

public final class StaticHUAssert
{
	private StaticHUAssert()
	{
	}

	public static void assertHUStoragesAreEmpty(final List<I_M_HU> hus)
	{
		final IHUStorageFactory storageFactory = Services.get(IHandlingUnitsBL.class).getStorageFactory();

		for (final I_M_HU hu : hus)
		{
			final IHUStorage huStorage = storageFactory.getStorage(hu);
			assertThat(huStorage.isEmpty())
				.as("Storage shall be empty for " + hu)
				.isTrue();
		}
	}

	public static void assertHUItemStoragesAreEmpty()
	{
		final IHUStorageFactory storageFactory = Services.get(IHandlingUnitsBL.class).getStorageFactory();

		for (final I_M_HU_Item item : POJOLookupMap.get().getRecords(I_M_HU_Item.class))
		{
			final IHUItemStorage itemStorage = storageFactory.getStorage(item);
			assertThat(itemStorage.isEmpty())
				.as("Item storage shall be empty: " + itemStorage)
				.isTrue();

			// Check also parent storages
			IGenericHUStorage huStorage = itemStorage.getParentStorage();
			while (huStorage != null)
			{
				assertThat(huStorage.isEmpty())
					.as("HU storage shall be empty: " + huStorage)
					.isTrue();
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
				assertThat(full)
					.as("Storage shall be full: " + storage)
					.isTrue();
			}
			else
			{
				final boolean empty = storage.isEmpty();
				assertThat(empty)
					.as("Storage shall be empty: " + storage)
					.isTrue();
			}
		}
	}

	/**
	 * @deprecated please use {@link HUAssert#hasStorage(de.metas.product.ProductId, de.metas.quantity.Quantity)}.
	 */
	@Deprecated
	public static void assertHUStorage(final I_M_HU hu, final ProductId productId, final BigDecimal qtyExpected)
	{
		final I_C_UOM uom = Services.get(IProductBL.class).getStockUOM(productId);

		final IHUStorageFactory storageFactory = Services.get(IHandlingUnitsBL.class).getStorageFactory();
		final IHUStorage huStorage = storageFactory.getStorage(hu);
		final BigDecimal qtyActual = huStorage.getQty(productId, uom);

		final String message = "Invalid HU Storage Qty: "
				+ "\nproduct=" + productId
				+ "\nHU=" + hu;
		assertThat(qtyActual)
			.as(message)
			.isEqualByComparingTo(qtyExpected);
	}

	/**
	 * @deprecated please use {@link HUAssert#isDestroyed()}.
	 */
	@Deprecated
	public static void assertHUDestroyed(final I_M_HU hu)
	{
		final boolean destroyed = Services.get(IHandlingUnitsBL.class).isDestroyed(hu);
		assertThat(destroyed)
			.as("HU shall be destroyed"
				+ "\nPI:" + Services.get(IHandlingUnitsBL.class).getPI(hu).getName()
				+ "\nHU:" + hu)
			.isTrue();
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
		assertThat(storage)
			.as("storage shall not be null")
			.isNotNull();

		assertThat(storage.getQtyCapacity())
			.as("Invalid storage total qty: " + storage)
			.isEqualByComparingTo(new BigDecimal(qtyTotalStr));
		
		assertThat(storage.getQtyFree())
			.as("Invalid storage free qty: " + storage)
			.isEqualByComparingTo(new BigDecimal(qtyFreeStr));
		
		assertThat(storage.getQty().toBigDecimal())
			.as("Invalid storage qty: " + storage)
			.isEqualByComparingTo(new BigDecimal(qtyStr));
	}

	@Deprecated
	public static void assertStorageLevel(final I_M_HU hu, final I_M_Product product, final BigDecimal qtyExpected)
	{
		final ProductId productId = ProductId.ofRepoId(product.getM_Product_ID());
		assertStorageLevel(hu, productId, qtyExpected);
	}

	public static void assertStorageLevel(final I_M_HU hu, final ProductId productId, final BigDecimal qtyExpected)
	{
		final IContextAware contextProvider = InterfaceWrapperHelper.getContextAware(hu);
		final IMutableHUContext huContext = Services.get(IHUContextFactory.class).createMutableHUContext(contextProvider);
		assertStorageLevel(huContext, hu, productId, qtyExpected);
	}

	@Deprecated
	public static void assertStorageLevel(final IHUContext huContext, final I_M_HU hu, final I_M_Product product, final BigDecimal qtyExpected)
	{
		final ProductId productId = ProductId.ofRepoId(product.getM_Product_ID());
		assertStorageLevel(huContext, hu, productId, qtyExpected);
	}

	public static void assertStorageLevel(final IHUContext huContext, final I_M_HU hu, final ProductId productId, final BigDecimal qtyExpected)
	{
		final BigDecimal qtyActual = huContext.getHUStorageFactory()
				.getStorage(hu)
				.getProductStorage(productId)
				.getQty()
				.toBigDecimal();

		assertThat(qtyActual)
			.as("Invalid storage qty for HU=" + hu + ", product=" + productId)
			.isEqualByComparingTo(qtyExpected);
	}

	/**
	 * Asserts that given HU has given {@link I_M_HU_PI}
	 */
	public static void assertHU_PI(final I_M_HU hu, final I_M_HU_PI expectedPI)
	{
		assertThat(hu)
			.as("hu shall not be null")
			.isNotNull();

		final I_M_HU_PI_Version huPiVersion = Services.get(IHandlingUnitsBL.class).getPIVersion(hu);
		assertThat(huPiVersion)
			.as("HU_PI_Version shall not be null for " + hu)
			.isNotNull();

		final I_M_HU_PI huPI = huPiVersion.getM_HU_PI();
		assertThat(huPI)
			.as("Invalid HU_PI for " + hu)
			.isEqualTo(expectedPI);
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
		assertThat(hu)
			.as("HU shall not be null")
			.isNotNull();

		final IHUStorageFactory storageFactory = Services.get(IHandlingUnitsBL.class).getStorageFactory();

		for (final I_M_HU_Item huItem : Services.get(IHandlingUnitsDAO.class).retrieveItems(hu))
		{
			final IHUItemStorage itemStorage = storageFactory.getStorage(huItem);
			assertStorageValid(itemStorage);
		}
	}

	public static void assertStorageValid(final IHUItemStorage itemStorage)
	{
		assertThat(itemStorage)
			.as("itemStorage shall not be null")
			.isNotNull();
		
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
		assertThat(itemStorage)
			.as("itemStorage shall not be null")
			.isNotNull();

		// TODO: retrieve included storages and make sure their SUM is same with this storage
	}

	private static void assertStorageValid_Virtual(final IHUItemStorage itemStorage)
	{
		assertThat(itemStorage)
			.as("itemStorage shall not be null")
			.isNotNull();
		// NOTE: nothing to validate here. In the past we had M_HU_Item_Storage_Detail but we removed because it was useless and overhead.
	}

	/***
	 * Do nothing. Used only if you have no assert methods in your test and want to avoid PMD warning.
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
		assertThat(hu)
			.as("hu shall not be null")
			.isNotNull();
		
		assertThat(huParentExpected)
			.as("huParentExpected shall not be null")
			.isNotNull();

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
		fail(errorMsg.toString());
	}
}