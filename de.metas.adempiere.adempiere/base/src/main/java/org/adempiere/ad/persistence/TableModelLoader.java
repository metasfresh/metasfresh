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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.lang.reflect.Constructor;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

import org.adempiere.ad.dao.cache.IModelCacheService;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.GenericPO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.PO;
import org.compiere.model.POInfo;
import org.compiere.util.DB;
import org.slf4j.Logger;

import de.metas.adempiere.util.cache.CacheInterceptor;
import de.metas.logging.LogManager;
import de.metas.logging.MetasfreshLastError;

/**
 * Class responsible for loading {@link PO}.
 *
 * @author tsa
 *
 */
public final class TableModelLoader
{
	public static final transient TableModelLoader instance = new TableModelLoader();

	private static final Logger log = LogManager.getLogger(TableModelLoader.class);
	private final TableModelClassLoader tableModelClassLoader = TableModelClassLoader.instance;

	private TableModelLoader()
	{
		super();
	}

	public PO newPO(final Properties ctx, final String tableName, final String trxName)
	{
		final int recordId = 0; // marker for new records
		final PO po = retrievePO(ctx, tableName, recordId, trxName);
		return po;
	}

	public PO getPO(final Properties ctx, final String tableName, final int Record_ID, final String trxName)
	{
		boolean checkCache = true;

		// Respect cache interceptor's temporary disabled flag
		if (CacheInterceptor.isCacheDisabled())
		{
			checkCache = false;
		}

		return getPO(ctx, tableName, Record_ID, checkCache, trxName);
	}

	/**
	 *
	 * @param Record_ID
	 * @param checkCache true if object shall be checked in cache first
	 * @param trxName
	 * @return loaded PO
	 */
	public PO getPO(final Properties ctx, final String tableName, final int Record_ID, final boolean checkCache, final String trxName)
	{
		final IModelCacheService modelCacheService = Services.get(IModelCacheService.class);
		if (checkCache)
		{
			final PO poCached = modelCacheService.retrieveObject(ctx, tableName, Record_ID, trxName);
			if (poCached != null)
			{
				return poCached;
			}
		}

		final PO po = retrievePO(ctx, tableName, Record_ID, trxName);
		modelCacheService.addToCache(po);

		return po;
	}

	/**
	 * Loads the PO from database.
	 * In case some errors were encountered, they will be logged and <code>null</code> will be returned.
	 *
	 * @param ctx
	 * @param tableName
	 * @param Record_ID
	 * @param trxName
	 * @return PO or null
	 */
	private final PO retrievePO(final Properties ctx, final String tableName, int Record_ID, String trxName)
	{
		final POInfo poInfo = POInfo.getPOInfo(tableName);
		if (Record_ID > 0 && poInfo.getKeyColumnName() == null)
		{
			log.warn("(id) - Multi-Key " + tableName);
			return null;
		}

		final Class<?> clazz = tableModelClassLoader.getClass(tableName);
		if (clazz == null)
		{
			log.info("Using GenericPO for {}", tableName);
			final GenericPO po = new GenericPO(tableName, ctx, Record_ID, trxName);
			return po;
		}

		boolean errorLogged = false;
		try
		{
			final Constructor<?> constructor = tableModelClassLoader.getIDConstructor(clazz);
			final PO po = (PO)constructor.newInstance(ctx, Record_ID, trxName);
			if (po != null && po.get_ID() != Record_ID && Record_ID > 0)
			{
				return null;
			}
			return po;
		}
		catch (Exception e)
		{
			final Throwable cause = e.getCause() == null ? e : e.getCause();
			log.error("(id) - Table=" + tableName + ",Class=" + clazz, cause);
			MetasfreshLastError.saveError(log, "Error", cause);
			errorLogged = true;
		}

		if (!errorLogged)
		{
			log.error("(id) - Not found - Table=" + tableName + ", Record_ID=" + Record_ID);
		}

		return null;
	}	// getPO

	/**
	 * Get PO Class Instance
	 *
	 * @param ctx
	 * @param tableName
	 * @param rs result set
	 * @param trxName transaction
	 * @return PO for Record; never return null
	 */
	public PO getPO(final Properties ctx, final String tableName, ResultSet rs, String trxName)
	{
		final PO po = retrievePO(ctx, tableName, rs, trxName);

		final IModelCacheService modelCacheService = Services.get(IModelCacheService.class);
		modelCacheService.addToCache(po);

		return po;
	}

	/**
	 * Retrieve model from given result set.
	 *
	 * @param ctx
	 * @param tableName
	 * @param rs
	 * @param trxName
	 * @return loaded model; never return null
	 */
	private final PO retrievePO(final Properties ctx, final String tableName, final ResultSet rs, final String trxName)
	{
		final Class<?> clazz = tableModelClassLoader.getClass(tableName);
		if (clazz == null)
		{
			log.info("Using GenericPO for {}", tableName);
			final GenericPO po = new GenericPO(tableName, ctx, rs, trxName);
			return po;
		}

		try
		{
			final Constructor<?> constructor = tableModelClassLoader.getResultSetConstructor(clazz);

			final PO po = (PO)constructor.newInstance(ctx, rs, trxName);
			return po;
		}
		catch (final Exception e)
		{
			throw new AdempiereException("Error while loading model from ResultSet"
					+ "\n@TableName@: " + tableName
					+ "\nClass: " + clazz
					, e);
		}
	}	// getPO

	/**
	 * Get PO Class Instance
	 *
	 * @param whereClause where clause
	 * @param trxName transaction
	 * @return PO for Record or null
	 */
	public PO getPO(final Properties ctx, final String tableName, String whereClause, String trxName)
	{
		final Object[] params = null;
		return getPO(ctx, tableName, whereClause, params, trxName);
	}	// getPO

	/**
	 * Get PO class instance
	 *
	 * @param whereClause
	 * @param params
	 * @param trxName
	 * @return
	 */
	public PO getPO(final Properties ctx, final String tableName, String whereClause, Object[] params, String trxName)
	{
		final PO po = retrievePO(ctx, tableName, whereClause, params, trxName);
		if (po != null)
		{
			final IModelCacheService modelCacheService = Services.get(IModelCacheService.class);
			modelCacheService.addToCache(po);
		}

		return po;
	}

	private final PO retrievePO(final Properties ctx, final String tableName, final String whereClause, final Object[] params, final String trxName)
	{
		if (whereClause == null || whereClause.length() == 0)
		{
			return null;
		}

		//
		PO po = null;
		final POInfo info = POInfo.getPOInfo(tableName);
		if (info == null)
		{
			return null;
		}

		final StringBuilder sqlBuffer = info.buildSelect();
		sqlBuffer.append(" WHERE ").append(whereClause);
		final String sql = sqlBuffer.toString();

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, trxName);
			DB.setParameters(pstmt, params);

			rs = pstmt.executeQuery();
			if (rs.next())
			{
				po = getPO(ctx, tableName, rs, trxName);
			}
		}
		catch (Exception e)
		{
			log.error(sql, e);
			MetasfreshLastError.saveError(log, "Error", e);
		}
		finally
		{
			DB.close(rs, pstmt);
		}

		return po;
	}

	public final <ModelType> ModelType retrieveModel(final Properties ctx, final String tableName, final Class<?> modelClass, final ResultSet rs, final String trxName)
	{
		final PO po = getPO(ctx, tableName, rs, trxName);

		//
		// Case: we have a modelClass specified
		if (modelClass != null)
		{
			final Class<? extends PO> poClass = po.getClass();
			if (poClass.isAssignableFrom(modelClass))
			{
				@SuppressWarnings("unchecked")
				final ModelType model = (ModelType)po;
				return model;
			}
			else
			{
				@SuppressWarnings("unchecked")
				final ModelType model = (ModelType)InterfaceWrapperHelper.create(po, modelClass);
				return model;
			}
		}
		//
		// Case: no "clazz" and no "modelClass"
		else
		{
			if (log.isDebugEnabled())
			{
				final AdempiereException ex = new AdempiereException("Query does not have a modelClass defined and no 'clazz' was specified as parameter."
						+ "We need to avoid this case, but for now we are trying to do a force casting"
						+ "\nQuery: " + this
						+ "\nPO: " + po);
				log.debug(ex.getLocalizedMessage(), ex);
			}

			@SuppressWarnings("unchecked")
			final ModelType model = (ModelType)po;
			return model;
		}
	}

}
