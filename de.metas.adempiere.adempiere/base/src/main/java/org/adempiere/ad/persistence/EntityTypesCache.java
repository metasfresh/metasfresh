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
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.DBException;
import org.compiere.model.I_AD_EntityType;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.slf4j.Logger;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;

import de.metas.cache.CCache;
import de.metas.logging.LogManager;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.ToString;
import lombok.Value;

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
	private static final transient Logger logger = LogManager.getLogger(EntityTypesCache.class);

	/** Cache: EntityType to {@link EntityTypeEntry} */
	private final CCache<Integer, EntityTypesMap> _cache = CCache.<Integer, EntityTypesMap> builder()
			.tableName(I_AD_EntityType.Table_Name)
			.initialCapacity(1)
			.build();

	// FIXME HARDCODED. We shall introduce AD_EntityType.IsSystemMaintained... or better, AD_EntityType_Dependency table
	private static final ImmutableSet<String> SYSTEM_MAINTAINED_ENTITY_TYPES = ImmutableSet.<String> builder()
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
		// NOTE: we are lazy loading the cache because it might be that initially we don't have a database connection anyways
		// loadIfNeeded();
	}

	@Override
	public String toString()
	{
		return "EntityTypesCache [cache=" + _cache + "]";
	}

	private EntityTypesMap getEntityTypesMap()
	{
		return _cache.getOrLoad(0, this::retrieveEntityTypesMap);
	}

	@VisibleForTesting
	protected EntityTypesMap retrieveEntityTypesMap()
	{
		final List<EntityTypeEntry> entries = new ArrayList<>(50);
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		final String sql = "SELECT * FROM AD_EntityType WHERE IsActive=? ORDER BY AD_EntityType_ID";
		final Object[] sqlParams = new Object[] { true };
		try
		{
			pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_None);
			DB.setParameters(pstmt, sqlParams);
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				final EntityTypeEntry entry = loadEntityTypeEntry(rs);
				entries.add(entry);
			}

			return EntityTypesMap.of(entries);
		}
		catch (final SQLException e)
		{
			throw new DBException(e, sql, sqlParams);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
	}

	private final EntityTypeEntry loadEntityTypeEntry(final ResultSet rs) throws SQLException
	{
		final String entityType = rs.getString(I_AD_EntityType.COLUMNNAME_EntityType);
		final String modelPackage = rs.getString(I_AD_EntityType.COLUMNNAME_ModelPackage);
		final String webUIServletListenerClass = rs.getString(I_AD_EntityType.COLUMNNAME_WebUIServletListenerClass);
		final boolean displayedInUI = DisplayType.toBoolean(rs.getString(I_AD_EntityType.COLUMNNAME_IsDisplayed));
		final boolean systemMaintained = extractSystemMaintainedEntityType(entityType);

		final EntityTypeEntry entry = EntityTypeEntry.builder()
				.entityType(entityType)
				.modelPackage(modelPackage)
				.displayedInUI(displayedInUI)
				.systemMaintained(systemMaintained)
				.webUIServletListenerClass(webUIServletListenerClass)
				.build();
		return entry;
	}

	private static final boolean extractSystemMaintainedEntityType(final String entityType)
	{
		return SYSTEM_MAINTAINED_ENTITY_TYPES.contains(entityType);
	}

	@Override
	public Set<String> getEntityTypeNames()
	{
		return getEntityTypesMap().getEntityTypeNames();
	}

	public Set<String> getSystemMaintainedEntityTypeNames()
	{
		return SYSTEM_MAINTAINED_ENTITY_TYPES;
	}

	@Override
	public String getModelPackage(final String entityType)
	{
		return getEntityTypesMap().getModelPackage(entityType);
	}

	@Override
	public boolean isActive(final String entityType)
	{
		return getEntityTypesMap().isActive(entityType);
	}

	@Override
	public boolean isDisplayedInUI(final String entityType)
	{
		return getEntityTypesMap().isDisplayedInUI(entityType);
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
		return getEntityTypesMap().isSystemMaintained(entityType);
	}

	public String getWebUIServletListenerClass(final String entityType)
	{
		return getEntityTypesMap().getWebUIServletListenerClass(entityType);
	}

	@ToString
	@VisibleForTesting
	protected static final class EntityTypesMap
	{
		public static EntityTypesMap of(final Collection<EntityTypeEntry> entries)
		{
			return new EntityTypesMap(Maps.uniqueIndex(entries, EntityTypeEntry::getEntityType));
		}

		private final ImmutableMap<String, EntityTypeEntry> entries;

		public EntityTypesMap(@NonNull final ImmutableMap<String, EntityTypeEntry> entries)
		{
			this.entries = entries;
		}

		public ImmutableSet<String> getEntityTypeNames()
		{
			return entries.keySet();
		}

		public EntityTypeEntry getByEntityTypeOrNull(final String entityType)
		{
			Check.assumeNotEmpty(entityType, "entityType not empty"); // fail because in most of the cases is a development error

			return entries.get(entityType);
		}

		public EntityTypeEntry getByEntityType(final String entityType)
		{
			final EntityTypeEntry entry = getByEntityTypeOrNull(entityType);
			if (entry == null)
			{
				final AdempiereException ex = new AdempiereException("No EntityType entry found for entity type: " + entityType
						+ "\n Available EntityTypes are: " + getEntityTypeNames());
				logger.warn("", ex);
			}
			return entry;
		}

		public boolean isActive(final String entityType)
		{
			return getByEntityTypeOrNull(entityType) != null;
		}

		public boolean isDisplayedInUI(final String entityType)
		{
			final EntityTypeEntry entry = getByEntityTypeOrNull(entityType);
			if (entry == null)
			{
				return false;
			}
			return entry.isDisplayedInUI();
		}

		public String getModelPackage(final String entityType)
		{
			final EntityTypeEntry entry = getByEntityType(entityType);
			if (entry == null)
			{
				return null;
			}

			return entry.getModelPackage();
		}

		public String getWebUIServletListenerClass(final String entityType)
		{
			final EntityTypeEntry entry = getByEntityType(entityType);
			if (entry == null)
			{
				return null;
			}

			return entry.getWebUIServletListenerClass();
		}

		public boolean isSystemMaintained(final String entityType)
		{
			final EntityTypeEntry entry = getByEntityType(entityType);
			if (entry == null)
			{
				return false;
			}

			return entry.isSystemMaintained();
		}
	}

	@Value
	@VisibleForTesting
	public static final class EntityTypeEntry
	{
		private final String entityType;
		private final String modelPackage;
		private final boolean displayedInUI;
		private final boolean systemMaintained;
		private final String webUIServletListenerClass;

		@Builder
		private EntityTypeEntry(
				final String entityType,
				final String modelPackage,
				final boolean displayedInUI,
				final boolean systemMaintained,
				final String webUIServletListenerClass)
		{
			super();
			this.entityType = entityType;
			this.modelPackage = modelPackage;
			this.displayedInUI = displayedInUI;
			this.systemMaintained = systemMaintained;
			this.webUIServletListenerClass = webUIServletListenerClass;
		}
	}
}
