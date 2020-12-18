/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.handlingunits.impl;

import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.HuItemId;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.attribute.IHUAttributesDAO;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Attribute;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.I_M_HU_Item_Storage;
import de.metas.handlingunits.model.I_M_HU_Storage;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

public class CopyHUsCommand
{
	private final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
	private final IHUAttributesDAO huAttributesDAO = Services.get(IHUAttributesDAO.class);

	private final ImmutableSet<HuId> huIdsToCopy;
	private final String huStatus;

	private final HashMap<HuId, HuId> old2new_M_HU_ID = new HashMap<>();
	private final HashMap<HuItemId, HuItemId> old2new_M_HU_Item_ID = new HashMap<>();
	private final LinkedHashMap<HuId, I_M_HU> old2new_HU = new LinkedHashMap<>();

	@Builder
	public CopyHUsCommand(@NonNull final Collection<HuId> huIdsToCopy)
	{
		this.huIdsToCopy = ImmutableSet.copyOf(huIdsToCopy);
		this.huStatus = X_M_HU.HUSTATUS_Planning;
	}

	public CopyHUsResponse execute()
	{
		copyHUs(huIdsToCopy);
		return buildResponse();
	}

	private CopyHUsResponse buildResponse()
	{
		final CopyHUsResponse.CopyHUsResponseBuilder response = CopyHUsResponse.builder();
		for (final HuId oldHUId : old2new_HU.keySet())
		{
			response.item(CopyHUsResponse.CopyHUsResponseItem.builder()
					.oldHUId(oldHUId)
					.newHU(old2new_HU.get(oldHUId))
					.build());
		}
		return response.build();
	}

	private void copyHUs(final Set<HuId> rootOldHUIds)
	{
		final HashSet<HuId> allOldHUIds = new HashSet<>();
		final HashSet<HuItemId> allOldHUItemIds = new HashSet<>();

		List<I_M_HU> oldHUs = handlingUnitsDAO.getByIds(rootOldHUIds);
		while (!oldHUs.isEmpty())
		{
			for (final I_M_HU oldHU : oldHUs)
			{
				copyHU(oldHU);
			}

			final ImmutableSet<HuId> oldHUIds = extractHUIds(oldHUs);
			final List<I_M_HU_Item> oldHUItems = handlingUnitsDAO.retrieveItemsNoCache(oldHUIds);
			for (final I_M_HU_Item oldHUItem : oldHUItems)
			{
				copyHUItem(oldHUItem);
			}

			final ImmutableSet<HuItemId> oldHUItemIds = extractHUItemIds(oldHUItems);
			oldHUs = handlingUnitsDAO.retrieveIncludedHUsNoCache(oldHUItemIds);

			allOldHUIds.addAll(oldHUIds);
			allOldHUItemIds.addAll(oldHUItemIds);
		}

		for (final I_M_HU_Item_Storage oldHUItemStorage : handlingUnitsDAO.retrieveItemStoragesNoCache(allOldHUItemIds))
		{
			copyHUItemStorage(oldHUItemStorage);
		}

		for (final I_M_HU_Storage oldHUStorage : handlingUnitsDAO.retrieveStoragesNoCache(allOldHUIds))
		{
			copyHUStorage(oldHUStorage);
		}

		for (final I_M_HU_Attribute oldHUAttribute : huAttributesDAO.retrieveAttributesNoCache(allOldHUIds))
		{
			copyHUAttribute(oldHUAttribute);
		}
	}

	private ImmutableSet<HuItemId> extractHUItemIds(final List<I_M_HU_Item> oldHUItems)
	{
		return oldHUItems.stream().map(huItem -> HuItemId.ofRepoId(huItem.getM_HU_Item_ID())).collect(ImmutableSet.toImmutableSet());
	}

	private ImmutableSet<HuId> extractHUIds(final List<I_M_HU> oldHUs)
	{
		return oldHUs.stream().map(hu -> HuId.ofRepoId(hu.getM_HU_ID())).collect(ImmutableSet.toImmutableSet());
	}

	private void copyHU(@NonNull final I_M_HU oldHU)
	{
		final HuItemId oldParentItemId = HuItemId.ofRepoIdOrNull(oldHU.getM_HU_Item_Parent_ID());
		final HuItemId newParentItemId;
		if (oldParentItemId != null)
		{
			newParentItemId = old2new_M_HU_Item_ID.get(oldParentItemId);
			if (newParentItemId == null)
			{
				throw new AdempiereException("Parent item was not already cloned for " + oldParentItemId);
			}
		}
		else
		{
			newParentItemId = null;
		}

		final I_M_HU newHU = InterfaceWrapperHelper.newInstance(I_M_HU.class);
		InterfaceWrapperHelper.copyValues(oldHU, newHU);
		newHU.setM_HU_Item_Parent_ID(HuItemId.toRepoId(newParentItemId));
		newHU.setHUStatus(huStatus);
		newHU.setIsActive(true);
		InterfaceWrapperHelper.saveRecord(newHU);

		final HuId oldHUId = HuId.ofRepoId(oldHU.getM_HU_ID());
		final HuId newHUId = HuId.ofRepoId(newHU.getM_HU_ID());
		old2new_M_HU_ID.put(oldHUId, newHUId);
		old2new_HU.put(oldHUId, newHU);
	}

	private void copyHUItem(@NonNull final I_M_HU_Item oldItem)
	{
		final HuId oldHUId = HuId.ofRepoId(oldItem.getM_HU_ID());
		final HuId newHUId = old2new_M_HU_ID.get(oldHUId);
		if (newHUId == null)
		{
			throw new AdempiereException("HU was not already cloned for " + oldHUId);
		}

		final I_M_HU_Item newItem = InterfaceWrapperHelper.newInstance(I_M_HU_Item.class);
		InterfaceWrapperHelper.copyValues(oldItem, newItem);
		newItem.setM_HU_ID(newHUId.getRepoId());
		InterfaceWrapperHelper.saveRecord(newItem);

		final HuItemId oldItemId = HuItemId.ofRepoId(oldItem.getM_HU_Item_ID());
		final HuItemId newItemId = HuItemId.ofRepoId(newItem.getM_HU_Item_ID());
		old2new_M_HU_Item_ID.put(oldItemId, newItemId);
	}

	private void copyHUAttribute(final I_M_HU_Attribute oldHUAttribute)
	{
		final HuId oldHUId = HuId.ofRepoId(oldHUAttribute.getM_HU_ID());
		final HuId newHUId = old2new_M_HU_ID.get(oldHUId);
		if (newHUId == null)
		{
			throw new AdempiereException("HU was not already cloned for " + oldHUId);
		}

		final I_M_HU_Attribute newHUAttribute = InterfaceWrapperHelper.newInstance(I_M_HU_Attribute.class);
		InterfaceWrapperHelper.copyValues(oldHUAttribute, newHUAttribute);
		newHUAttribute.setM_HU_ID(newHUId.getRepoId());
		newHUAttribute.setValue(oldHUAttribute.getValue()); // programmatically setting this because the framework handles it as a search key and avoids copying it
		InterfaceWrapperHelper.save(newHUAttribute);
	}

	private void copyHUStorage(final I_M_HU_Storage oldHUStorage)
	{
		final HuId oldHUId = HuId.ofRepoId(oldHUStorage.getM_HU_ID());
		final HuId newHUId = old2new_M_HU_ID.get(oldHUId);
		if (newHUId == null)
		{
			throw new AdempiereException("HU was not already cloned for " + oldHUId);
		}

		final I_M_HU_Storage newHUStorage = InterfaceWrapperHelper.newInstance(I_M_HU_Storage.class);
		InterfaceWrapperHelper.copyValues(oldHUStorage, newHUStorage);
		newHUStorage.setM_HU_ID(newHUId.getRepoId());
		InterfaceWrapperHelper.save(newHUStorage);
	}

	private void copyHUItemStorage(final I_M_HU_Item_Storage oldHUItemStorage)
	{
		final HuItemId oldHUItemId = HuItemId.ofRepoId(oldHUItemStorage.getM_HU_Item_ID());
		final HuItemId newHUItemId = old2new_M_HU_Item_ID.get(oldHUItemId);
		if (newHUItemId == null)
		{
			throw new AdempiereException("HU Item was not already cloned for " + oldHUItemId);
		}

		final I_M_HU_Item_Storage newHUItemStorage = InterfaceWrapperHelper.newInstance(I_M_HU_Item_Storage.class);
		InterfaceWrapperHelper.copyValues(oldHUItemStorage, newHUItemStorage);
		newHUItemStorage.setM_HU_Item_ID(newHUItemId.getRepoId());
		InterfaceWrapperHelper.save(newHUItemStorage);
	}

}
