package de.metas.storage.spi.hu.impl;

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
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.adempiere.mm.attributes.api.IAttributeSet;
import org.adempiere.model.IContextAware;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.IQuery;
import org.compiere.model.I_M_AttributeSetInstance;
import org.slf4j.Logger;

import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHUContextFactory;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.attribute.storage.IAttributeStorageFactory;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Storage;
import de.metas.logging.LogManager;
import de.metas.storage.IStorageEngine;
import de.metas.storage.IStorageQuery;
import de.metas.storage.IStorageRecord;

public class HUStorageEngine implements IStorageEngine
{
	public static final transient HUStorageEngine instance = new HUStorageEngine();

	private static final Logger logger = LogManager.getLogger(HUStorageEngine.class);

	private static final transient String SYSCONFIG_QueriesPerChunk = "de.metas.storage.spi.hu.impl.HUStorageEngine.QueriesPerChunk";
	private static final transient int DEFAULT_QueriesPerChunk = 500;

	private HUStorageEngine()
	{
		super();
	}

	@Override
	public IStorageQuery newStorageQuery()
	{
		final HUStorageQuery huStorageQuery = new HUStorageQuery();
		return huStorageQuery;
	}

	@Override
	public List<IStorageRecord> retrieveStorageRecords(final IContextAware context, final IStorageQuery storageQuery)
	{
		//
		// Retrieve HU Storages that are matching given storage query
		final IQuery<I_M_HU_Storage> huStoragesQuery = HUStorageQuery.cast(storageQuery)
				.createQueryBuilder_for_M_HU_Storages(context)
				.create();
		final List<I_M_HU_Storage> huStorages = huStoragesQuery.list();
		return createHUStorageRecords(context, huStorages);
	}

	@Override
	public Set<IStorageRecord> retrieveStorageRecords(final IContextAware context, final Set<IStorageQuery> storageQueries)
	{
		Check.assumeNotEmpty(storageQueries, "storageQueries not empty");

		logger.debug("Retrieving storage records for {}", storageQueries);

		final int queriesPerChunk = getQueriesPerChunk();
		logger.debug("queriesPerChunk: {}", queriesPerChunk);

		IQuery<I_M_HU_Storage> queryAgg = null;
		int queriesCount = 0;
		final Set<IStorageRecord> storageRecords = new HashSet<>();

		for (final IStorageQuery storageQuery : storageQueries)
		{
			//
			// Retrieve stoarge records for current query aggregation / chunk
			if (queriesPerChunk > 0 && queriesCount >= queriesPerChunk)
			{
				final List<I_M_HU_Storage> huStoragesPerChunk = queryAgg.list();
				final List<IStorageRecord> storageRecordsPerChunk = createHUStorageRecords(context, huStoragesPerChunk);
				storageRecords.addAll(storageRecordsPerChunk);
				queryAgg = null;
				queriesCount = 0;
			}

			//
			// Create query
			final HUStorageQuery huStorageQuery = HUStorageQuery.cast(storageQuery);
			final IQuery<I_M_HU_Storage> query = huStorageQuery.createQueryBuilder_for_M_HU_Storages(context)
					.create();

			//
			// Aggregate the query
			if (queryAgg == null)
			{
				queryAgg = query;
			}
			else
			{
				final boolean distinct = false;
				queryAgg.addUnion(query, distinct);
			}
			queriesCount++;
		}

		//
		// Retrieve storage records for last chunk
		if (queryAgg != null)
		{
			final List<I_M_HU_Storage> huStoragesPerChunk = queryAgg.list();
			final List<IStorageRecord> storageRecordsPerChunk = createHUStorageRecords(context, huStoragesPerChunk);
			storageRecords.addAll(storageRecordsPerChunk);
			queryAgg = null;
			queriesCount = 0;
		}

		logger.debug("Returning {} storage records", storageRecords.size());
		return storageRecords;
	}

	private final List<IStorageRecord> createHUStorageRecords(final IContextAware context, final Collection<I_M_HU_Storage> huStorages)
	{
		if (huStorages == null || huStorages.isEmpty())
		{
			return Collections.emptyList();
		}

		//
		// Get the attribute storage factory
		final IHUContextFactory huContextFactory = Services.get(IHUContextFactory.class);
		final IHUContext huContext = huContextFactory.createMutableHUContext(context);
		final IAttributeStorageFactory huAttributeStorageFactory = huContext.getHUAttributeStorageFactory();

		//
		// Iterate them and build up the IStorageRecord list
		final Map<Integer, HUStorageRecord_HUPart> huId2huPart = new HashMap<>();
		final List<IStorageRecord> huStorageRecords = new ArrayList<>();
		for (final I_M_HU_Storage huStorage : huStorages)
		{
			// Get/Create the HU Part of the Storage Record
			// NOTE: we are using a map to re-use the HUPart for same HU (optimization)
			final int huId = huStorage.getM_HU_ID();
			HUStorageRecord_HUPart huPart = huId2huPart.get(huId);
			if (huPart == null)
			{
				final I_M_HU hu = huStorage.getM_HU();
				final IAttributeStorage huAttributes = huAttributeStorageFactory.getAttributeStorage(hu);
				huPart = new HUStorageRecord_HUPart(hu, huAttributes);
				huId2huPart.put(huId, huPart);
			}

			// Create the Storage Record and collect it
			final HUStorageRecord huStorageRecord = new HUStorageRecord(huPart, huStorage);
			huStorageRecords.add(huStorageRecord);
		}

		return huStorageRecords;
	}

	@Override
	public IAttributeSet getAttributeSet(final I_M_AttributeSetInstance asi)
	{
		Check.assumeNotNull(asi, "asi not null");
		final IHUContextFactory huContextFactory = Services.get(IHUContextFactory.class);

		final IContextAware contextProvider = InterfaceWrapperHelper.getContextAware(asi);
		final IHUContext huContext = huContextFactory.createMutableHUContext(contextProvider);
		return huContext.getHUAttributeStorageFactory().getAttributeStorage(asi);
	}

	private final int getQueriesPerChunk()
	{
		final int queriesPerChunk = Services.get(ISysConfigBL.class).getIntValue(SYSCONFIG_QueriesPerChunk, DEFAULT_QueriesPerChunk);
		return queriesPerChunk;
	}

	@Override
	public String toString()
	{
		return "HUStorageEngine []";
	}
}
