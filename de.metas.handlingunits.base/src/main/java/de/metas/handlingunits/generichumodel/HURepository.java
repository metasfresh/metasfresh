package de.metas.handlingunits.generichumodel;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.adempiere.util.lang.IMutable;

import com.google.common.collect.ImmutableList;

import de.metas.handlingunits.HUIteratorListenerAdapter;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHUIteratorListener;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.IHUIteratorListener.Result;
import de.metas.handlingunits.generichumodel.HU.HUBuilder;
import de.metas.handlingunits.impl.HUIterator;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.X_M_HU_Item;
import de.metas.handlingunits.storage.IHUItemStorage;
import de.metas.util.Services;
import de.metas.util.collections.CollectionUtils;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2019 metas GmbH
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

public class HURepository
{
	private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	private final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);

	public HU getbyId(HuId id)
	{
		final I_M_HU huRecord = handlingUnitsDAO.getById(id);
		return CollectionUtils.singleElement(ofRecord(huRecord));
	}

	private HU ofRecord2(final I_M_HU huRecord)
	{

		final HUIteratorListener listener = new HUIteratorListener(huRecord);
		new HUIterator()
				.setEnableStorageIteration(true)
				.setListener(listener)
				.iterate(huRecord);

		return listener.getResult();
	}

	private ImmutableList<HU> ofRecord(final I_M_HU huRecord)
	{
		final ImmutableList.Builder<HU> hus = ImmutableList.builder();

		// final List<I_M_HU> includedHUs = handlingUnitsDAO.retrieveIncludedHUs(huRecord);
		final List<I_M_HU_Item> huItemRecords = handlingUnitsDAO.retrieveItems(huRecord);

		// for (final I_M_HU includedHURecord : includedHUs)
		for (final I_M_HU_Item huItemRecord : huItemRecords)
		{
			final String itemType = huItemRecord.getItemType();

			if (X_M_HU_Item.ITEMTYPE_HandlingUnit.equals(itemType))
			{

			}

			final boolean aggregateItem = X_M_HU_Item.ITEMTYPE_HUAggregate.equals(itemType);

			if (aggregateItem)
			{
				final int logicalNumberOfTUs = includedHURecord.getM_HU_Item_Parent().getQty().intValue();
				for (int i = 0; i < logicalNumberOfTUs; i++)
				{

				}
			}
			else
			{
				hu.childHUs(ofRecord(includedHURecord));
			}
		}

		return hus.add(hu.build()).build();
	}

	private static class HUIteratorListener extends HUIteratorListenerAdapter
	{
		private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
		private final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);

		private final HUBuilder rootHu;

		private final ArrayList<HuId> huIds = new ArrayList<HuId>();
		private final LinkedHashMap<HuId, HUBuilder> hus = new LinkedHashMap<HuId, HU.HUBuilder>();

		public HUIteratorListener(I_M_HU rootHuRecord)
		{
			this.rootHu = createHU(rootHuRecord);
			final HuId huId = extractId(rootHuRecord);
			this.huIds.add(huId);
			this.hus.put(huId, rootHu);
		}

		private HuId extractId(I_M_HU rootHuRecord)
		{
			return HuId.ofRepoId(rootHuRecord.getM_HU_ID());
		}

		private HUBuilder createHU(I_M_HU rootHuRecord)
		{
			return HU.builder()
					.type(HUType.ofCode(handlingUnitsBL.getHU_UnitType(rootHuRecord)))
					.productStorages(handlingUnitsBL.getStorageFactory().getStorage(rootHuRecord).getProductStorages());
		}

		@Override
		public Result beforeHU(final IMutable<I_M_HU> hu)
		{
			return getDefaultResult();
		}

		@Override
		public Result afterHU(final I_M_HU hu)
		{
			final HuId currentChildId = this.huIds.remove(huIds.size()-1);
			final HUBuilder currentChild = this.hus.remove(extractId(hu));

			return Result.CONTINUE;
		}

		@Override
		public Result beforeHUItem(final IMutable<I_M_HU_Item> item)
		{
			return getDefaultResult();
		}

		@Override
		public Result afterHUItem(final I_M_HU_Item item)
		{
			return getDefaultResult();
		}

		@Override
		public Result beforeHUItemStorage(final IMutable<IHUItemStorage> itemStorage)
		{
			return getDefaultResult();
		}

		@Override
		public Result afterHUItemStorage(final IHUItemStorage itemStorage)
		{
			return getDefaultResult();
		}

		public HU getResult()
		{
			return rootHu.build();
		}
	}

}
