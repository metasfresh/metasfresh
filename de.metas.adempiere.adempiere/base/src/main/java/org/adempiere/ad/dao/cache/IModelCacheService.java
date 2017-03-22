package org.adempiere.ad.dao.cache;

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

import java.util.Properties;

import org.adempiere.ad.dao.cache.ITableCacheConfig.TrxLevel;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.ISingletonService;
import org.compiere.model.PO;

/**
 * Model level caching service. Use it to enable e.g.
 * {@link InterfaceWrapperHelper#create(Properties, int, Class, String)} to take advantage of caching.
 *
 * @author tsa
 *
 */
public interface IModelCacheService extends ISingletonService
{
	ITableCacheConfigBuilder createTableCacheConfigBuilder(String tableName);

	ITableCacheConfigBuilder createTableCacheConfigBuilder(Class<?> modelClass);

	/**
	 * Creates and adds a simple caching profile (using {@link #createDefaultTableCacheConfig(Class)}) which enables caching for <code>modelClass</code>'s TableName.
	 *
	 * If a caching configuration already exists, it will be overwritten.
	 *
	 * @param modelClass
	 * @see #createDefaultTableCacheConfig(Class)
	 */
	void addTableCacheConfig(Class<?> modelClass);

	/**
	 * Same as {@link #addTableCacheConfig(Class)} but the caching configuration will be added only if there is no one already.
	 *
	 * @param modelClass
	 * @see #createDefaultTableCacheConfig(Class)
	 */
	void addTableCacheConfigIfAbsent(Class<?> modelClass);

	/**
	 * Creates a default caching configuration for given <code>modelClass</code>.
	 *
	 * The default caching configuration will
	 * <ul>
	 * <li>enabled
	 * <li>caching transaction level will be {@link TrxLevel#All}
	 * </ul>
	 *
	 * @param modelClass
	 * @return caching configuration.
	 */
	ITableCacheConfig createDefaultTableCacheConfig(Class<?> modelClass);

	/**
	 * Retrieves {@link PO} object from cache.
	 *
	 * @param ctx
	 * @param tableName
	 * @param Record_ID
	 * @param trxName
	 * @return cached {@link PO} object or null
	 */
	PO retrieveObject(Properties ctx, String tableName, int Record_ID, String trxName);

	/**
	 * Add given {@link PO} object to cache.
	 *
	 * NOTE: call this method ONLY after you <b>"freshly"</b>retrieved your object from DB, and did not yet do any changes to it.
	 *
	 * @param po persistent object. In case it's null, it will be silently ignored.
	 */
	void addToCache(PO po);
}
