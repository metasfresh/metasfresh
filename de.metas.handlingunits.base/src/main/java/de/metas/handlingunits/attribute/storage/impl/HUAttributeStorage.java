package de.metas.handlingunits.attribute.storage.impl;

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

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;

import com.google.common.base.MoreObjects.ToStringHelper;

import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.attribute.storage.IAttributeStorageFactory;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;

/* package */class HUAttributeStorage extends AbstractHUAttributeStorage
{
	private final String id;
	private final I_M_HU _hu;

	/**
	 * Map of {@link IAttributeStorage#getId()} to {@link IAttributeStorage}
	 */
	private Map<String, IAttributeStorage> childrenAttributeStoragesMap = null;

	/* package */ HUAttributeStorage(final IAttributeStorageFactory storageFactory, final I_M_HU hu)
	{
		super(storageFactory);

		Check.assumeNotNull(hu, "hu not null");
		_hu = hu;

		id = "M_HU_ID=" + hu.getM_HU_ID();
	}

	@Override
	public String getId()
	{
		return id;
	}

	@Override
	public I_M_HU getM_HU()
	{
		return _hu;
	}

	@Override
	protected void toString(final ToStringHelper stringHelper)
	{
		stringHelper
				.add("hu", _hu)
				// .add("huDisplayName", Services.get(IHandlingUnitsBL.class).getDisplayName(getM_HU())) // used only for debugging)
				;
	}

	@Override
	public IAttributeStorage getParentAttributeStorage()
	{
		final I_M_HU hu = getM_HU();
		if (hu == null)
		{
			return NullAttributeStorage.instance;
		}

		//
		// Refresh is needed in case the M_HU_Item_Parent was changed
		// FIXME: optimization: why we need this and which is the actual case???
		if (!InterfaceWrapperHelper.hasChanges(hu))
		{
			InterfaceWrapperHelper.refresh(hu);
		}

		final IHandlingUnitsDAO handlingUnitsDAO = getHandlingUnitsDAO();
		final I_M_HU_Item parentItem = handlingUnitsDAO.retrieveParentItem(hu);
		if (parentItem == null)
		{
			return NullAttributeStorage.instance;
		}

		//
		// Make sure that we're using a saved parent HU item
		Check.assume(parentItem.getM_HU_Item_ID() > 0, "parentHUItem already saved ({}) for HU={}", parentItem, hu);
		final I_M_HU parentHU = parentItem.getM_HU();

		//
		// Ensure that the parentItem is actually attached to an HU
		Check.assumeNotNull(parentHU, "An M_HU_Item (parentHUItem) {} is linked to a handling unit for child HU={}", parentItem, hu);

		final IAttributeStorageFactory storageFactory = getHUAttributeStorageFactory();
		final IAttributeStorage parentAttributeSetStorage = storageFactory.getAttributeStorage(parentHU);

		return parentAttributeSetStorage;
	}

	private final Map<String, IAttributeStorage> getInnerChildrenAttributeStoragesMap(final boolean loadIfNeeded)
	{
		if (childrenAttributeStoragesMap == null)
		{
			if (!loadIfNeeded)
			{
				return Collections.emptyMap();
			}

			childrenAttributeStoragesMap = retrieveChildrenAttributeStorages();
		}
		return childrenAttributeStoragesMap;
	}

	@Override
	public final Collection<IAttributeStorage> getChildAttributeStorages(final boolean loadIfNeeded)
	{
		final Map<String, IAttributeStorage> childrenAttributeStoragesMap = getInnerChildrenAttributeStoragesMap(loadIfNeeded);
		return Collections.unmodifiableCollection(childrenAttributeStoragesMap.values());
	}

	private final Map<String, IAttributeStorage> retrieveChildrenAttributeStorages()
	{
		final IAttributeStorageFactory storageFactory = getHUAttributeStorageFactory();
		final IHandlingUnitsDAO handlingUnitsDAO = getHandlingUnitsDAO();

		final Map<String, IAttributeStorage> childrenAttributeSetStorages = new LinkedHashMap<String, IAttributeStorage>();

		final I_M_HU hu = getM_HU();
		if (hu == null)
		{
			return childrenAttributeSetStorages;
		}

		// Retrieve HU items and get children HUs
		final List<I_M_HU_Item> huItems = handlingUnitsDAO.retrieveItems(hu);
		final boolean saveOnChange = isSaveOnChange();
		for (final I_M_HU_Item item : huItems)
		{
			final List<I_M_HU> childrenHU = handlingUnitsDAO.retrieveIncludedHUs(item);
			for (final I_M_HU childHU : childrenHU)
			{
				final IAttributeStorage childAttributeSetStorage = storageFactory.getAttributeStorage(childHU);
				childAttributeSetStorage.setSaveOnChange(saveOnChange); // propagate saveOnChange to child
				childrenAttributeSetStorages.put(childAttributeSetStorage.getId(), childAttributeSetStorage);
			}
		}

		return childrenAttributeSetStorages;
	}

	/**
	 * Add the given <code>childAttributeStorage</code> to this storage's children. If children were not yet loaded, then this method also loads them.
	 *
	 * @param childAttributeStorage
	 */
	@Override
	protected void addChildAttributeStorage(final IAttributeStorage childAttributeStorage)
	{
		final Map<String, IAttributeStorage> childrenAttributeStoragesMap = getInnerChildrenAttributeStoragesMap(true);
		childrenAttributeStoragesMap.put(childAttributeStorage.getId(), childAttributeStorage);
	}

	/**
	 * Removes the given <code>childAttributeStorage</code> from this storage's children. If children were not yet loaded, then this method also loads them.
	 *
	 * @param childAttributeStorage
	 */
	@Override
	protected IAttributeStorage removeChildAttributeStorage(final IAttributeStorage childAttributeStorage)
	{
		final Map<String, IAttributeStorage> childrenAttributeStoragesMap = getInnerChildrenAttributeStoragesMap(true);
		return childrenAttributeStoragesMap.remove(childAttributeStorage.getId());
	}
}
