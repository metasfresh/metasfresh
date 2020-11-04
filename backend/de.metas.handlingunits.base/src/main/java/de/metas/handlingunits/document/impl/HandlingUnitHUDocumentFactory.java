package de.metas.handlingunits.document.impl;

import java.time.ZonedDateTime;

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

import de.metas.common.util.time.SystemTime;
import org.adempiere.util.lang.IMutable;

import de.metas.handlingunits.HUIteratorListenerAdapter;
import de.metas.handlingunits.document.IHUDocument;
import de.metas.handlingunits.document.IHUDocumentLine;
import de.metas.handlingunits.impl.HUIterator;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.storage.IHUItemStorage;
import de.metas.handlingunits.storage.IHUStorageDAO;
import de.metas.handlingunits.storage.IHUStorageFactory;
import de.metas.handlingunits.storage.IProductStorage;
import de.metas.handlingunits.storage.impl.DefaultHUStorageFactory;
import de.metas.handlingunits.storage.impl.SaveDecoupledHUStorageDAO;

/**
 * Creates {@link IHUDocument}s from handling units.
 *
 * @author tsa
 *
 */
public class HandlingUnitHUDocumentFactory extends AbstractHUDocumentFactory<I_M_HU>
{

	public HandlingUnitHUDocumentFactory()
	{
		super(I_M_HU.class);
	}

	@Override
	protected void createHUDocumentsFromTypedModel(final HUDocumentsCollector documentsCollector, final I_M_HU hu)
	{
		final ZonedDateTime dateTrx = SystemTime.asZonedDateTimeAtStartOfDay();

		//
		// Storage Factory
		final IHUStorageDAO storageDAO = new SaveDecoupledHUStorageDAO();
		final IHUStorageFactory storageFactory = new DefaultHUStorageFactory(storageDAO);

		//
		// Iterate HU Structure and create one HU Document for each Handling Unit
		final HUIterator iterator = new HUIterator();
		iterator.setDate(dateTrx);
		iterator.setStorageFactory(storageFactory);
		iterator.setListener(new HUIteratorListenerAdapter()
		{
			final Map<Integer, List<IHUDocumentLine>> huId2documentLines = new HashMap<>();

			@Override
			public Result beforeHU(final IMutable<I_M_HU> hu)
			{
				final int huId = hu.getValue().getM_HU_ID();
				huId2documentLines.put(huId, new ArrayList<IHUDocumentLine>());
				return Result.CONTINUE;
			}

			/**
			 * Create HU document from current document lines
			 */
			@Override
			public Result afterHU(final I_M_HU hu)
			{
				final int huId = hu.getM_HU_ID();
				final List<IHUDocumentLine> documentLines = huId2documentLines.remove(huId);

				// allow empty documents because it's more obvious for user
				// if (documentLines.isEmpty())
				// {
				// return Result.CONTINUE;
				// }

				final IHUDocument doc = createHUDocument(hu, documentLines);
				documentsCollector.getHUDocuments().add(doc);

				return Result.CONTINUE;
			}

			/**
			 * Create a document line for each IProductStorage.
			 */
			@Override
			public Result beforeHUItemStorage(final IMutable<IHUItemStorage> itemStorageMutable)
			{
				final int huId = itemStorageMutable.getValue().getM_HU_Item().getM_HU_ID();
				final List<IHUDocumentLine> documentLines = huId2documentLines.get(huId);

				final IHUItemStorage itemStorage = itemStorageMutable.getValue();
				final I_M_HU_Item item = iterator.getCurrentHUItem();
				final List<IProductStorage> productStorages = itemStorage.getProductStorages(iterator.getDate());
				for (final IProductStorage productStorage : productStorages)
				{
					final HandlingUnitHUDocumentLine documentLine = createHUDocumentLine(item, productStorage);
					documentLines.add(documentLine);
				}
				return Result.SKIP_DOWNSTREAM;
			}

		});

		iterator.iterate(hu);
	}

	protected IHUDocument createHUDocument(final I_M_HU hu, final List<IHUDocumentLine> documentLines)
	{
		final I_M_HU innerHU = getInnerHU(hu);
		return new HandlingUnitHUDocument(hu, innerHU, documentLines);
	}

	protected I_M_HU getInnerHU(final I_M_HU hu)
	{
		// If HU has no parent (i.e. it's a top level HU) then there is nothing to take out
		if (hu.getM_HU_Item_Parent_ID() <= 0)
		{
			return null;
		}
		else
		{
			return hu;
		}
	}

	protected HandlingUnitHUDocumentLine createHUDocumentLine(final I_M_HU_Item item, final IProductStorage productStorage)
	{
		return new HandlingUnitHUDocumentLine(item, productStorage);
	}
}
