package de.metas.handlingunits.storage.impl;

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


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.util.Util;

import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.I_M_HU_Item_Storage;
import de.metas.handlingunits.model.I_M_HU_Storage;
import de.metas.handlingunits.storage.IHUStorageDAO;

/**
 * {@link IHUStorageDAO} implementation which acts like a save buffer:
 * <ul>
 * <li>automatically loads attributes from underlying {@link IHUStorageDAO}, if they do not already exist in our local cache
 * <li>on save, instead of directly saving them we are just adding them to the cache/buffer. Later, on {@link #flush()} everything will be saved.
 * </ul>
 * 
 * @author tsa
 *
 */
public class SaveDecoupledHUStorageDAO extends AbstractHUStorageDAO
{
	private final IHUStorageDAO db;

	/** Cache: "M_HU key" to "M_HU_Storage key" to {@link I_M_HU_Storage} */
	private final Map<Object, Map<Object, I_M_HU_Storage>> _hu2storage = new HashMap<>();
	/** Cache: "M_HU_Item key" to "M_HU_Item_Storage key" to {@link I_M_HU_Item_Storage} */
	private final Map<Object, Map<Object, I_M_HU_Item_Storage>> _item2itemStorage = new HashMap<>();

	public SaveDecoupledHUStorageDAO()
	{
		this(new HUStorageDAO());
	}

	public SaveDecoupledHUStorageDAO(final IHUStorageDAO huStorageDAO)
	{
		super();
		Check.assumeNotNull(huStorageDAO, "huStorageDAO not null");
		this.db = huStorageDAO;
	}

	private final Object mkHUKey(final I_M_HU hu)
	{
		return Util.mkKey(I_M_HU.Table_Name, hu.getM_HU_ID());
	}

	private final Object mkHUItemKey(final I_M_HU_Item huItem)
	{
		return Util.mkKey(I_M_HU_Item.Table_Name, huItem.getM_HU_Item_ID());
	}

	private final Object mkHUStorageKey(final I_M_HU_Storage huStorage)
	{
		final int huId = huStorage.getM_HU_ID();
		final int productId = huStorage.getM_Product_ID();
		return mkHUStorageKey(huId, productId);
	}

	private final Object mkHUStorageKey(final int huId, final int productId)
	{
		return Util.mkKey(I_M_HU_Storage.Table_Name, huId, productId);
	}

	private final Object mkHUItemStorageKey(final I_M_HU_Item_Storage huItemStorage)
	{
		final int huItemId = huItemStorage.getM_HU_Item_ID();
		final int productId = huItemStorage.getM_Product_ID();
		return mkHUItemStorageKey(huItemId, productId);
	}

	private final Object mkHUItemStorageKey(final int huItemId, final int productId)
	{
		return Util.mkKey(I_M_HU_Item_Storage.Table_Name, huItemId, productId);
	}

	private final void setReadonly(final Object model)
	{
		if (model == null)
		{
			return;
		}
		InterfaceWrapperHelper.setSaveDeleteDisabled(model, true);
	}

	@Override
	public <T> T newInstance(final Class<T> modelClass, final Object contextProvider)
	{
		final T result = db.newInstance(modelClass, contextProvider);
		setReadonly(result);
		return result;
	}

	@Override
	public I_M_HU_Storage retrieveStorage(final I_M_HU hu, final int productId)
	{
		I_M_HU_Storage result = null;
		for (final I_M_HU_Storage storage : retrieveStorages(hu))
		{
			if (storage.getM_Product_ID() == productId)
			{
				Check.assumeNull(result, "There shall be only one storage for productId={}", productId);
				result = storage;
			}
		}

		return result;
	}

	@Override
	public List<I_M_HU_Storage> retrieveStorages(final I_M_HU hu)
	{
		final boolean retrieveIfNotFound = true;
		final Map<Object, I_M_HU_Storage> huStorages = getHUStorages(hu, retrieveIfNotFound);
		return new ArrayList<I_M_HU_Storage>(huStorages.values());
	}

	private final Map<Object, I_M_HU_Storage> getHUStorages(final I_M_HU hu, final boolean retrieveIfNotFound)
	{
		final Object huKey = mkHUKey(hu);
		Map<Object, I_M_HU_Storage> huStorages = _hu2storage.get(huKey);
		if (huStorages != null)
		{
			return huStorages;
		}

		if (retrieveIfNotFound)
		{
			final List<I_M_HU_Storage> huStoragesList = db.retrieveStorages(hu);
			huStorages = new HashMap<>(huStoragesList.size());
			for (final I_M_HU_Storage huStorage : huStoragesList)
			{
				final Object huStorageKey = mkHUStorageKey(huStorage);
				huStorages.put(huStorageKey, huStorage);
				setReadonly(huStorage);
			}
		}
		else
		{
			huStorages = new HashMap<>();
		}

		_hu2storage.put(huKey, huStorages);

		return huStorages;
	}

	@Override
	public void save(final I_M_HU_Storage huStorage)
	{
		final I_M_HU hu = huStorage.getM_HU();
		final boolean retrieveIfNotFound = true;
		final Map<Object, I_M_HU_Storage> huStorages = getHUStorages(hu, retrieveIfNotFound);

		final Object huStorageKey = mkHUStorageKey(huStorage);
		huStorages.put(huStorageKey, huStorage);

		setReadonly(huStorage);
	}

	@Override
	public List<I_M_HU_Item_Storage> retrieveItemStorages(final I_M_HU_Item huItem)
	{
		final boolean retrieveIfNotFound = true;
		final Map<Object, I_M_HU_Item_Storage> huItemStorages = getHUItemStorages(huItem, retrieveIfNotFound);
		return new ArrayList<>(huItemStorages.values());
	}

	private final Map<Object, I_M_HU_Item_Storage> getHUItemStorages(final I_M_HU_Item item, final boolean retrieveIfNotFound)
	{
		final Object itemKey = mkHUItemKey(item);
		Map<Object, I_M_HU_Item_Storage> result = _item2itemStorage.get(itemKey);
		if (result != null)
		{
			return result;
		}

		if (retrieveIfNotFound)
		{
			final List<I_M_HU_Item_Storage> huItemStoragesList = db.retrieveItemStorages(item);
			result = new HashMap<>(huItemStoragesList.size());
			for (final I_M_HU_Item_Storage huItemStorage : huItemStoragesList)
			{
				final Object huItemStorageKey = mkHUItemStorageKey(huItemStorage);
				result.put(huItemStorageKey, huItemStorage);
				setReadonly(huItemStorage);
			}
		}
		else
		{
			result = new HashMap<>();
		}

		_item2itemStorage.put(itemKey, result);

		return result;
	}

	@Override
	public void save(final I_M_HU_Item_Storage huItemStorage)
	{
		final I_M_HU_Item item = huItemStorage.getM_HU_Item();

		final boolean retrieveIfNotFound = true;
		final Map<Object, I_M_HU_Item_Storage> itemStorages = getHUItemStorages(item, retrieveIfNotFound);

		final Object huItemStorageKey = mkHUItemStorageKey(huItemStorage);
		itemStorages.put(huItemStorageKey, huItemStorage);

		setReadonly(huItemStorage);
	}

	@Override
	public I_M_HU_Item_Storage retrieveItemStorage(final I_M_HU_Item item, final I_M_Product product)
	{
		final int productId = product.getM_Product_ID();
		final Object huItemStorageKey = mkHUItemStorageKey(item.getM_HU_Item_ID(), productId);
		final Map<Object, I_M_HU_Item_Storage> huItemStorages = getHUItemStorages(item, true); // retrieveIfNotFound=true

		final I_M_HU_Item_Storage result = huItemStorages.get(huItemStorageKey);
		return result;
	}

	@Override
	public void save(final I_M_HU_Item item)
	{
		// nothing
	}

	@Override
	public I_C_UOM getC_UOM(final I_M_HU_Storage storage)
	{
		return db.getC_UOM(storage);
	}

	@Override
	public void initHUStorages(final I_M_HU hu)
	{
		final boolean retrieveIfNotFound = false; // just create an empty list
		getHUStorages(hu, retrieveIfNotFound);
	}

	@Override
	public void initHUItemStorages(final I_M_HU_Item item)
	{
		final boolean retrieveIfNotFound = false; // just create an empty list
		getHUItemStorages(item, retrieveIfNotFound);
	}

	/**
	 * Save all storages to database
	 */
	public final void flush()
	{
		final String trxName = Services.get(ITrxManager.class).getThreadInheritedTrxName();

		//
		// Save HU Storages
		for (final Map<Object, I_M_HU_Storage> huStorages : _hu2storage.values())
		{
			for (final I_M_HU_Storage huStorage : huStorages.values())
			{
				saveToDatabase(huStorage, trxName);
			}
		}

		//
		// Save HU Item Storages
		for (final Map<Object, I_M_HU_Item_Storage> huItemStorages : _item2itemStorage.values())
		{
			for (final I_M_HU_Item_Storage huItemStorage : huItemStorages.values())
			{
				saveToDatabase(huItemStorage, trxName);
			}
		}
	}

	private final void saveToDatabase(final Object model, final String trxName)
	{
		InterfaceWrapperHelper.setSaveDeleteDisabled(model, false);
		InterfaceWrapperHelper.save(model, trxName);
		// InterfaceWrapperHelper.setSaveDeleteDisabled(model, true); // not sure if is necessary
	}
}
