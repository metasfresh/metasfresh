package org.adempiere.ad.persistence;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.DBException;
import org.adempiere.util.Check;
import org.compiere.model.I_AD_EntityType;
import org.compiere.util.CCache;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.slf4j.Logger;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.logging.LogManager;

/**
 * Default implementation of {@link IEntityTypesCache}, which is retrieving the entity types from database.
 * 
 * @author tsa
 * 
 */
public class EntityTypesCache implements IEntityTypesCache
{
	/** Shared instance */
	public static final transient EntityTypesCache instance = new EntityTypesCache();

	// services
	private final transient Logger logger = LogManager.getLogger(getClass());

	/** Cache: EntityType to {@link EntityTypeEntry} */
	private final CCache<String, EntityTypeEntry> cache = new CCache<>(
			I_AD_EntityType.Table_Name + "#EntityTypeEntry" // cacheName
			, 50 // initialCapacity
			, 0 // NoExpire (NOTE: this is important because we don't want some of our records to get stale, because we would never load them again partially)
	);

	// FIXME HARDCODED. We shall introduce AD_EntityType.IsSystemMaintained... or better, AD_EntityType_Dependency table
	private static final Set<String> SYSTEM_MAINTAINED_ENTITY_TYPES = ImmutableSet.<String> builder()
			.add("D")
			.add("C")
			.add("U")
			.add("CUST")
			.add("A")
			.add("EXT")
			.add("XX")
			.add("EE01")
			.add("EE02")
			.add("EE04")
			.add("EE05")
			.add("de.metas.order")
			.build();

	@VisibleForTesting
	EntityTypesCache()
	{
		super();

		// NOTE: we are lazy loading the cache because it might be that initially we don't have a database connection anyways
		// loadIfNeeded();
	}

	@Override
	public String toString()
	{
		return "EntityTypesCache [cache=" + cache + "]";
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
		final String sql = "SELECT * FROM AD_EntityType WHERE IsActive=? ORDER BY AD_EntityType_ID";
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
		final String entityType = rs.getString(I_AD_EntityType.COLUMNNAME_EntityType);
		final String modelPackage = rs.getString(I_AD_EntityType.COLUMNNAME_ModelPackage);
		final String webUIServletListenerClass = rs.getString(I_AD_EntityType.COLUMNNAME_WebUIServletListenerClass);
		final boolean displayedInUI = DisplayType.toBoolean(rs.getString(I_AD_EntityType.COLUMNNAME_IsDisplayed));
		final boolean systemMaintained = extractSystemMaintainedEntityType(entityType);

		final EntityTypeEntry entry = EntityTypeEntry.builder()
				.setEntityType(entityType)
				.setModelPackage(modelPackage)
				.setWebUIServletListenerClass(webUIServletListenerClass)
				.setDisplayedInUI(displayedInUI)
				.setSystemMaintained(systemMaintained)
				.build();
		return entry;
	}

	private static final boolean extractSystemMaintainedEntityType(final String entityType)
	{
		return SYSTEM_MAINTAINED_ENTITY_TYPES.contains(entityType);
	}

	@Override
	public List<String> getEntityTypeNames()
	{
		loadIfNeeded();
		return ImmutableList.copyOf(cache.keySet());
	}

	public Set<String> getSystemMaintainedEntityTypeNames()
	{
		return SYSTEM_MAINTAINED_ENTITY_TYPES;
	}

	private EntityTypeEntry getEntityTypeEntryOrNull(final String entityType)
	{
		Check.assumeNotEmpty(entityType, "entityType not empty"); // fail because in most of the cases is a development error

		loadIfNeeded();

		final EntityTypeEntry entry = cache.get(entityType);
		return entry;
	}

	private EntityTypeEntry getEntityTypeEntry(final String entityType)
	{
		final EntityTypeEntry entry = getEntityTypeEntryOrNull(entityType);
		if (entry == null)
		{
			final AdempiereException ex = new AdempiereException("No EntityType entry found for entity type: " + entityType
					+ "\n Available EntityTypes are: " + cache.keySet());
			logger.warn(ex.getLocalizedMessage(), ex);
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
	public boolean isActive(final String entityType)
	{
		final EntityTypeEntry entry = getEntityTypeEntryOrNull(entityType);
		if (entry == null)
		{
			return false;
		}
		return true;
	}

	@Override
	public boolean isDisplayedInUI(final String entityType)
	{
		final EntityTypeEntry entry = getEntityTypeEntryOrNull(entityType);
		if (entry == null)
		{
			return false;
		}
		return entry.isDisplayedInUI();
	}

	public final String getDisplayedInUIEntityTypeSQLWhereClause(final String joinEntityTypeColumn)
	{
		return "exists (select 1 from AD_EntityType et where et.EntityType=" + joinEntityTypeColumn
				+ " and et.IsActive='Y'" // EntityType shall be active
				+ " and et." + I_AD_EntityType.COLUMNNAME_IsDisplayed + "='Y'" // shall be displayed in UI
				+ ")";
	}

	public boolean isSystemMaintained(final String entityType)
	{
		return getEntityTypeEntry(entityType).isSystemMaintained();
	}

	public String getWebUIServletListenerClass(final String entityType)
	{
		return getEntityTypeEntry(entityType).getWebUIServletListenerClass();
	}

	public static final class EntityTypeEntry
	{
		@VisibleForTesting
		static final Builder builder()
		{
			return new Builder();
		}

		private final String entityType;
		private final String modelPackage;
		private final boolean displayedInUI;
		private final boolean systemMaintained;
		private final String webUIServletListenerClass;

		private EntityTypeEntry(final Builder builder)
		{
			super();

			this.entityType = builder.entityType;
			Check.assumeNotEmpty(entityType, "entityType not empty");

			this.modelPackage = builder.modelPackage;
			this.displayedInUI = builder.displayedInUI;
			this.systemMaintained = builder.systemMaintained;
			this.webUIServletListenerClass = builder.webUIServletListenerClass;
		}

		@Override
		public String toString()
		{
			return "EntityTypeEntry [entityType=" + entityType + ", modelPackage=" + modelPackage + "]";
		}

		public String getEntityType()
		{
			return entityType;
		}

		public String getModelPackage()
		{
			return modelPackage;
		}

		public boolean isDisplayedInUI()
		{
			return displayedInUI;
		}

		public String getWebUIServletListenerClass()
		{
			return webUIServletListenerClass;
		}

		/**
		 * Is System Maintained. Any Entity Type with ID < 1000000.
		 * 
		 * @return true if D/C/U/CUST/A/EXT/XX (ID < 1000000)
		 */
		public boolean isSystemMaintained()
		{
			return systemMaintained;
		}

		public static final class Builder
		{
			private String entityType;
			private String modelPackage;
			private boolean displayedInUI = true; // default true
			private boolean systemMaintained = false;
			public String webUIServletListenerClass;

			private Builder()
			{
				super();
			}

			public EntityTypeEntry build()
			{
				return new EntityTypeEntry(this);
			}

			public Builder setEntityType(String entityType)
			{
				this.entityType = entityType;
				return this;
			}

			public Builder setModelPackage(String modelPackage)
			{
				this.modelPackage = modelPackage;
				return this;
			}

			public Builder setDisplayedInUI(boolean displayedInUI)
			{
				this.displayedInUI = displayedInUI;
				return this;
			}

			public Builder setSystemMaintained(boolean systemMaintained)
			{
				this.systemMaintained = systemMaintained;
				return this;
			}

			public Builder setWebUIServletListenerClass(String webUIServletListenerClass)
			{
				this.webUIServletListenerClass = webUIServletListenerClass;
				return this;
			}
		}
	}
}
