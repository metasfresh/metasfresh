package org.adempiere.ad.persistence;

/*
 * #%L
 * ADempiere ERP - Base
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


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.DBException;
import org.adempiere.util.Check;
import org.compiere.model.I_AD_EntityType;
import org.compiere.util.CCache;
import org.compiere.util.CLogger;
import org.compiere.util.DB;

import com.google.common.annotations.VisibleForTesting;

/**
 * Default implementation of {@link IEntityTypesCache}, which is retrieving the entity types from database.
 * 
 * @author tsa
 * 
 */
class EntityTypesCache implements IEntityTypesCache
{
	// services
	private final transient CLogger logger = CLogger.getCLogger(getClass());

	private final CCache<String, EntityTypeEntry> cache = new CCache<>(
			I_AD_EntityType.Table_Name + "#EntityTypeEntry" // cacheName
			, 50 // initialCapacity
			, 0 // NoExpire (NOTE: this is important because we don't want some of our records to get stale, because we would never load them again partially)
	);

	EntityTypesCache()
	{
		super();

		// NOTE: we are lazy loading the cache because it might be that initially we don't have a database connection anyways
		// loadIfNeeded();
	}

	private void loadIfNeeded()
	{
		//
		// Check if it's loaded
		if (!cache.isEmpty())
		{
			return;
		}

		//
		// Preload the cache will all entity types
		synchronized (cache)
		{
			if (cache.isEmpty())
			{
				final Map<String, EntityTypeEntry> entityTypeEntries = retrieveEntityTypeEntries();
				cache.clear();
				cache.putAll(entityTypeEntries);
			}
		}
	}

	/**
	 * Retrieves all entity types from underlying data source.
	 * 
	 * @return all entity types as a map of EntityType to {@link EntityTypeEntry}.
	 */
	@VisibleForTesting
	Map<String, EntityTypeEntry> retrieveEntityTypeEntries()
	{
		final Map<String, EntityTypeEntry> entityTypeEntries = new HashMap<>(50);
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		final String sql = "SELECT EntityType, ModelPackage FROM AD_EntityType WHERE IsActive=? ORDER BY AD_EntityType_ID";
		final Object[] params = new Object[] { true };
		try
		{
			pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_None);
			DB.setParameters(pstmt, params);
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				final EntityTypeEntry entry = loadEntityTypeEntry(rs);
				entityTypeEntries.put(entry.getEntityType(), entry);
			}
		}
		catch (SQLException e)
		{
			throw new DBException(e, sql, params);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}

		return entityTypeEntries;
	}

	private final EntityTypeEntry loadEntityTypeEntry(final ResultSet rs) throws SQLException
	{
		final String entityType = rs.getString("EntityType");
		final String modelPackage = rs.getString("ModelPackage");
		final EntityTypeEntry entry = new EntityTypeEntry(entityType, modelPackage);
		return entry;
	}

	@Override
	public List<String> getEntityTypeNames()
	{
		loadIfNeeded();

		return new ArrayList<>(cache.keySet());
	}

	private EntityTypeEntry getEntityTypeEntry(final String entityType)
	{
		Check.assumeNotEmpty(entityType, "entityType not empty");

		loadIfNeeded();

		final EntityTypeEntry entry = cache.get(entityType);
		if (entry == null)
		{
			final AdempiereException ex = new AdempiereException("No EntityType entry found for entity type: " + entityType
					+ "\n Available EntityTypes are: " + cache.keySet());
			logger.log(Level.WARNING, ex.getLocalizedMessage(), ex);
		}
		return entry;
	}

	@Override
	public String getModelPackage(final String entityType)
	{
		final EntityTypeEntry entry = getEntityTypeEntry(entityType);
		if (entry == null)
		{
			return null;
		}

		return entry.getModelPackage();
	}

	@Override
	public String toString()
	{
		return "EntityTypesCache [cache=" + cache + "]";
	}

	static class EntityTypeEntry
	{
		// public static final EntityTypeEntry NULL = new EntityTypeEntry("NULL", "NULL");

		private final String entityType;
		private final String modelPackage;

		public EntityTypeEntry(final String entityType, final String modelPackage)
		{
			super();

			Check.assumeNotEmpty(entityType, "entityType not empty");
			this.entityType = entityType;

			this.modelPackage = modelPackage;
		}

		public String getEntityType()
		{
			return entityType;
		}

		public String getModelPackage()
		{
			return modelPackage;
		}

		@Override
		public String toString()
		{
			return "EntityTypeEntry [entityType=" + entityType + ", modelPackage=" + modelPackage + "]";
		}
	}
}
