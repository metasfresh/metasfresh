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

import com.google.common.collect.ImmutableList;
import de.metas.cache.interceptor.CacheInterceptor;
import de.metas.cache.model.IModelCacheService;
import de.metas.logging.LogManager;
import de.metas.logging.MetasfreshLastError;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.DBException;
import org.adempiere.model.GenericPO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.PO;
import org.compiere.model.POInfo;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.lang.reflect.Constructor;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

/**
 * Class responsible for loading {@link PO}.
 *
 * @author tsa
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
		final String keyColumnName = InterfaceWrapperHelper.getKeyColumnName(tableName);
		final int newRecordId = InterfaceWrapperHelper.getFirstValidIdByColumnName(keyColumnName) - 1;
		final PO po = createOrLoadPO(ctx, tableName, newRecordId, trxName);
		return po;
	}

	public PO newPO(final String tableName)
	{
		return newPO(Env.getCtx(), tableName, ITrx.TRXNAME_ThreadInherited);
	}

	public PO getPO(final Properties ctx, final String tableName, final int Record_ID, @Nullable final String trxName)
	{
		boolean checkCache = true;

		// Respect cache interceptor's temporary disabled flag
		if (CacheInterceptor.isCacheDisabled())
		{
			checkCache = false;
		}

		return getPO(ctx, tableName, Record_ID, checkCache, trxName);
	}

	public PO getPO(final Properties ctx, final int adTableId, final int Record_ID, final String trxName)
	{
		final String tableName = Services.get(IADTableDAO.class).retrieveTableName(adTableId);
		return getPO(ctx, tableName, Record_ID, trxName);
	}

	public PO getPO(@NonNull final TableRecordReference recordRef)
	{
		return getPO(Env.getCtx(), recordRef.getTableName(), recordRef.getRecord_ID(), ITrx.TRXNAME_ThreadInherited);
	}

	/**
	 * @param checkCache true if object shall be checked in cache first
	 * @return loaded PO
	 */
	public PO getPO(final Properties ctx, final String tableName, final int recordId, final boolean checkCache, final String trxName)
	{
		final IModelCacheService modelCacheService = Services.get(IModelCacheService.class);
		if (checkCache)
		{
			final PO poCached = modelCacheService.retrieveObject(ctx, tableName, recordId, trxName);
			if (poCached != null)
			{
				return poCached;
			}
		}

		final PO po = createOrLoadPO(ctx, tableName, recordId, trxName);
		modelCacheService.addToCache(po);

		return po;
	}

	public List<PO> getPOs(
			final Properties ctx,
			@NonNull final String tableName,
			@NonNull final Set<Integer> recordIds,
			final String trxName)
	{
		if (recordIds.isEmpty())
		{
			return ImmutableList.of();
		}

		final IModelCacheService modelCacheService = Services.get(IModelCacheService.class);

		final List<PO> result = new ArrayList<>(recordIds.size());

		//
		// Load from cache as much is possible
		final Set<Integer> recordIdsToLoad;
		final boolean checkCache = !CacheInterceptor.isCacheDisabled();
		if (checkCache)
		{
			recordIdsToLoad = new HashSet<>();
			for (final int recordId : recordIds)
			{
				final PO poCached = modelCacheService.retrieveObject(ctx, tableName, recordId, trxName);
				if (poCached != null)
				{
					result.add(poCached);
				}
				else
				{
					recordIdsToLoad.add(recordId);
				}
			}
		}
		else
		{
			recordIdsToLoad = recordIds;
		}

		//
		// Retrieve from database what was not found in cache
		if (!recordIdsToLoad.isEmpty())
		{
			final POInfo poInfo = POInfo.getPOInfo(tableName);

			final List<Object> sqlParams = new ArrayList<>();
			final String sql = poInfo.buildSelect()
					.append(" WHERE ").append(DB.buildSqlList(poInfo.getSingleKeyColumnName(), recordIdsToLoad, sqlParams))
					.toString();
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			try
			{
				pstmt = DB.prepareStatement(sql, trxName);
				DB.setParameters(pstmt, sqlParams);
				rs = pstmt.executeQuery();
				while (rs.next())
				{
					final PO po = getPO(ctx, tableName, rs, trxName);
					result.add(po);
				}
			}
			catch (Exception ex)
			{
				throw new DBException(ex, sql, sqlParams);
			}
			finally
			{
				DB.close(rs, pstmt);
			}
		}

		//
		return result;
	}

	/**
	 * Creates/Loads the PO from database.
	 * In case some errors were encountered, they will be logged and <code>null</code> will be returned.
	 *
	 * @return PO or null
	 */
	private PO createOrLoadPO(final Properties ctx, final String tableName, final int recordId, final String trxName)
	{
		final POInfo poInfo = POInfo.getPOInfo(tableName);
		if (recordId > 0 && !poInfo.isSingleKeyColumnName())
		{
			log.warn("Cannot retrieve by ID a multi-key record: table={}, recordId={}", tableName, recordId);
			return null;
		}

		final Class<?> clazz = tableModelClassLoader.getClass(tableName);
		if (clazz == null)
		{
			log.info("Using GenericPO for {}", tableName);
			final GenericPO po = new GenericPO(tableName, ctx, recordId, trxName);
			return po;
		}

		boolean errorLogged = false;
		try
		{
			final Constructor<?> constructor = tableModelClassLoader.getIDConstructor(clazz);
			final PO po = (PO)constructor.newInstance(ctx, recordId, trxName);
			if (po != null && po.get_ID() != recordId && recordId > 0)
			{
				return null;
			}
			return po;
		}
		catch (final Exception ex)
		{
			final Throwable cause = ex.getCause() == null ? ex : ex.getCause();
			log.error("Failed fetching record for table={}, recordId={}, class={}", tableName, recordId, clazz, cause);
			MetasfreshLastError.saveError(log, "Error", cause);
			errorLogged = true;
		}

		if (!errorLogged)
		{
			log.error("Failed fetching record for table={}, recordId={}, class={}", tableName, recordId, clazz);
		}

		return null;
	}    // getPO

	/**
	 * Get PO Class Instance
	 *
	 * @param ctx
	 * @param tableName
	 * @param rs        result set
	 * @param trxName   transaction
	 * @return PO for Record; never return null
	 */
	public PO getPO(final Properties ctx, final String tableName, final ResultSet rs, final String trxName)
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
	private PO retrievePO(final Properties ctx, final String tableName, final ResultSet rs, final String trxName)
	{
		final Class<?> clazz = tableModelClassLoader.getClass(tableName);
		if (clazz == null)
		{
			log.debug("Using GenericPO for {}", tableName);
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
					+ "\nClass: " + clazz, e);
		}
	}    // getPO

	/**
	 * Get PO Class Instance
	 *
	 * @param whereClause where clause
	 * @param trxName     transaction
	 * @return PO for Record or null
	 */
	public PO getPO(final Properties ctx, final String tableName, final String whereClause, final String trxName)
	{
		final Object[] params = null;
		return getPO(ctx, tableName, whereClause, params, trxName);
	}    // getPO

	/**
	 * Get PO class instance
	 *
	 * @param whereClause
	 * @param params
	 * @param trxName
	 * @return
	 */
	public PO getPO(final Properties ctx, final String tableName, final String whereClause, final Object[] params, final String trxName)
	{
		final PO po = retrievePO(ctx, tableName, whereClause, params, trxName);
		if (po != null)
		{
			final IModelCacheService modelCacheService = Services.get(IModelCacheService.class);
			modelCacheService.addToCache(po);
		}

		return po;
	}

	private PO retrievePO(final Properties ctx, final String tableName, final String whereClause, final Object[] params, final String trxName)
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

	public <ModelType> ModelType retrieveModel(final Properties ctx, final String tableName, final Class<?> modelClass, final ResultSet rs, final String trxName)
	{
		final PO po = getPO(ctx, tableName, rs, trxName);

		//
		// Case: we have a modelClass specified
		if (modelClass != null)
		{
			final Class<? extends PO> poClass = po.getClass();
			if (poClass.isAssignableFrom(modelClass))
			{
				@SuppressWarnings("unchecked") final ModelType model = (ModelType)po;
				return model;
			}
			else
			{
				@SuppressWarnings("unchecked") final ModelType model = (ModelType)InterfaceWrapperHelper.create(po, modelClass);
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

			@SuppressWarnings("unchecked") final ModelType model = (ModelType)po;
			return model;
		}
	}
}
