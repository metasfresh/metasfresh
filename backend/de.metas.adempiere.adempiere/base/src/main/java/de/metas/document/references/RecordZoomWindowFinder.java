package de.metas.document.references;

import de.metas.cache.CCache;
import de.metas.document.references.generic.DefaultGenericZoomIntoTableInfoRepository;
import de.metas.document.references.generic.GenericZoomIntoTableInfo;
import de.metas.document.references.generic.GenericZoomIntoTableInfoRepository;
import de.metas.document.references.generic.GenericZoomIntoTableWindow;
import de.metas.document.references.generic.TestingGenericZoomIntoTableInfoRepository;
import de.metas.lang.SOTrx;
import de.metas.logging.LogManager;
import de.metas.util.Check;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.Adempiere;
import org.compiere.model.I_AD_Column;
import org.compiere.model.I_AD_Tab;
import org.compiere.model.I_AD_Table;
import org.compiere.model.I_AD_Window;
import org.compiere.model.MQuery;
import org.compiere.model.MQuery.Operator;
import org.compiere.util.DB;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Optional;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2016 metas GmbH
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

/**
 * Helper class used to find the actual AD_Window_ID to be used for a given record.
 * <p>
 * Use case:
 * <ul>
 * <li>create a new instance using one of the static builders
 * <li>get the actual window ID: {@link #findAdWindowId()}
 * <li>get the {@link MQuery} to be used when the window is opened: {@link #createZoomQuery()}
 * </ul>
 *
 * @author metas-dev <dev@metasfresh.com>
 */
@ToString
public class RecordZoomWindowFinder
{
	public static RecordZoomWindowFinder newInstance(final String tableName, final int recordId)
	{
		return new RecordZoomWindowFinder(tableName, recordId);
	}

	public static RecordZoomWindowFinder newInstance(@NonNull final TableRecordReference record)
	{
		return new RecordZoomWindowFinder(record.getTableName(), record.getRecord_ID());
	}

	public static RecordZoomWindowFinder newInstance(final String tableName)
	{
		return new RecordZoomWindowFinder(tableName);
	}

	public static Optional<AdWindowId> findAdWindowId(final TableRecordReference record)
	{
		return newInstance(record).findAdWindowId();
	}

	public static Optional<AdWindowId> findAdWindowId(final String tableName)
	{
		return newInstance(tableName).findAdWindowId();
	}

	@Deprecated
	public static RecordZoomWindowFinder newInstance(@NonNull final MQuery query)
	{
		return new RecordZoomWindowFinder(query);
	}

	@Deprecated
	public static RecordZoomWindowFinder newInstance(@NonNull final MQuery query, final AdWindowId alreadyKnownWindowId)
	{
		return new RecordZoomWindowFinder(query, alreadyKnownWindowId);
	}

	private static final Logger logger = LogManager.getLogger(RecordZoomWindowFinder.class);
	private final GenericZoomIntoTableInfoRepository tableInfoRepository = newGenericZoomIntoTableInfoRepository();

	//
	// Parameters
	@NonNull private final String _tableName;
	private final int _recordId;
	private boolean checkRecordPresentInWindow = false; // false to be backwards compatible
	@Deprecated
	@Nullable private final MQuery _query_Provided;
	@Deprecated
	@Nullable private final AdWindowId alreadyKnownWindowId;

	//
	// Effective values
	@Nullable private GenericZoomIntoTableInfo _tableInfo;
	@Nullable private SOTrx _recordSOTrx_Effective = null;

	private static final CCache<String, GenericZoomIntoTableInfo> tableInfoByTableName = CCache.<String, GenericZoomIntoTableInfo>builder()
			.tableName(I_AD_Table.Table_Name)
			.additionalTableNameToResetFor(I_AD_Column.Table_Name)
			.additionalTableNameToResetFor(I_AD_Window.Table_Name)
			.additionalTableNameToResetFor(I_AD_Tab.Table_Name)
			.expireMinutes(CCache.EXPIREMINUTES_Never)
			.build();

	private final HashMap<String, Boolean> checkRecordIsMatchingWhereClauseCache = new HashMap<>();

	private RecordZoomWindowFinder(final String tableName, final int recordId)
	{
		Check.assumeNotEmpty(tableName, "tableName is not empty");

		_tableName = tableName;
		_recordId = recordId;
		if (_recordId < 0)
		{
			logger.warn("No Record_ID provided to detect the AD_Window_ID by TableName={}. Going forward.", _tableName);
		}
		_query_Provided = null;
		alreadyKnownWindowId = null;
	}

	private RecordZoomWindowFinder(final String tableName)
	{
		Check.assumeNotEmpty(tableName, "tableName is not empty");
		_tableName = tableName;
		_recordId = -1;
		_query_Provided = null;
		alreadyKnownWindowId = null;
	}

	@Deprecated
	private RecordZoomWindowFinder(@NonNull final MQuery query)
	{
		final String tableName = query.getTableName();
		Check.assumeNotEmpty(tableName, "tableName is not empty for {}", query);
		_tableName = tableName;
		_recordId = -1;

		_query_Provided = query;
		alreadyKnownWindowId = null;
	}

	@Deprecated
	private RecordZoomWindowFinder(
			@NonNull final MQuery query,
			@Nullable final AdWindowId alreadyKnownWindowId)
	{
		final String tableName = query.getTableName();
		Check.assumeNotEmpty(tableName, "tableName is not empty for {}", query);
		_tableName = tableName;
		_recordId = -1;
		_query_Provided = query;

		// task #1062
		// suggested window is for both trx
		// to be extended if we need 2 windows later
		this.alreadyKnownWindowId = alreadyKnownWindowId;
	}

	private static GenericZoomIntoTableInfoRepository newGenericZoomIntoTableInfoRepository()
	{
		return !Adempiere.isUnitTestMode()
				? new DefaultGenericZoomIntoTableInfoRepository()
				: new TestingGenericZoomIntoTableInfoRepository();
	}

	public RecordZoomWindowFinder checkRecordPresentInWindow()
	{
		this.checkRecordPresentInWindow = true;
		return this;
	}

	public Optional<AdWindowId> findAdWindowId()
	{
		//
		// Before trying, check if we have a preset windowId.
		// This is not a common case, mainly used by old APIs. In future we consider dropping it.
		if (alreadyKnownWindowId != null)
		{
			return Optional.of(alreadyKnownWindowId);
		}

		//
		// First try: check by IsSOTrx
		final GenericZoomIntoTableInfo tableInfo = getTableInfo();
		if (tableInfo.isHasIsSOTrxColumn()
				&& (tableInfo.getDefaultSOWindow() != null && tableInfo.getDefaultPOWindow() != null))
		{
			final SOTrx soTrx = getRecordSOTrxEffective();
			final GenericZoomIntoTableWindow window = soTrx.isSales() ? tableInfo.getDefaultSOWindow() : tableInfo.getDefaultPOWindow();
			if (isRecordPresentInWindow(window))
			{
				return Optional.of(window.getAdWindowId());
			}
		}

		//
		// Check default window
		final GenericZoomIntoTableWindow defaultWindow = tableInfo.getDefaultWindow();
		if (defaultWindow != null && isRecordPresentInWindow(defaultWindow))
		{
			return Optional.of(defaultWindow.getAdWindowId());
		}

		//
		// Check the other windows and pick first
		for (final GenericZoomIntoTableWindow window : tableInfo.getOtherWindows())
		{
			if (window != null && isRecordPresentInWindow(window))
			{
				return Optional.of(window.getAdWindowId());
			}
		}

		//
		// Nothing found
		return Optional.empty();
	}

	private boolean isRecordPresentInWindow(@NonNull final GenericZoomIntoTableWindow window)
	{
		if (!checkRecordPresentInWindow)
		{
			return true;
		}

		final String recordWhereClause = getRecordWhereClause();
		if (Check.isBlank(recordWhereClause))
		{
			// Case: there is not particular record provided, so we can accept the window blindly.
			return true;
		}

		final StringBuilder sqlWhereClauseFinal = new StringBuilder()
				.append("\n /* record where clause */ (").append(recordWhereClause).append(")");

		final String tabWhereClause = window.getTabSqlWhereClause();
		if (!Check.isBlank(tabWhereClause))
		{
			sqlWhereClauseFinal.append("\n /* window tab where clause */ AND (").append(tabWhereClause).append(")");
		}

		return checkRecordIsMatchingWhereClauseCache.computeIfAbsent(
				sqlWhereClauseFinal.toString(),
				this::checkRecordIsMatchingWhereClause);
	}

	private boolean checkRecordIsMatchingWhereClause(@NonNull final String sqlWhereClause)
	{
		final String tableName = getTableInfo().getTableName();
		final String sql = "SELECT 111 FROM " + tableName + " WHERE " + sqlWhereClause;

		try
		{
			final int retValue = DB.getSQLValueEx(ITrx.TRXNAME_ThreadInherited, sql);
			return retValue == 111;
		}
		catch (final Exception ex)
		{
			logger.warn("Got error while executing {}", sql, ex);
			return false;
		}
	}

	@Deprecated
	public MQuery createZoomQuery()
	{
		if (_query_Provided != null)
		{
			return _query_Provided;
		}

		final GenericZoomIntoTableInfo tableInfo = getTableInfo();
		final String tableName = tableInfo.getTableName();
		final String keyColumnName = tableInfo.getKeyColumnName();
		final int recordId = getRecord_ID();

		final MQuery query = new MQuery(tableName);
		query.addRestriction(keyColumnName, Operator.EQUAL, recordId);
		query.setZoomTableName(tableName);
		query.setZoomColumnName(keyColumnName);
		query.setZoomValue(recordId);
		query.setRecordCount(1); // metas: notify FindPanel that only one record will be expected

		return query;
	}

	private String getTableName()
	{
		return _tableName;
	}

	private int getRecord_ID()
	{
		return _recordId;
	}

	@Nullable
	private String getRecordWhereClause()
	{
		if (_query_Provided != null)
		{
			final boolean fullyQualified = false;
			return _query_Provided.getWhereClause(fullyQualified);
		}
		else
		{
			final int recordId = getRecord_ID();
			if (recordId < 0)
			{
				return null;
			}

			final String keyColumnName = getTableInfo().getKeyColumnName();
			return keyColumnName + "=" + recordId;
		}
	}

	private GenericZoomIntoTableInfo getTableInfo()
	{
		if (_tableInfo == null)
		{
			_tableInfo = tableInfoByTableName.getOrLoad(getTableName(), tableInfoRepository::retrieveTableInfo);
		}
		return _tableInfo;
	}

	private SOTrx getRecordSOTrxEffective()
	{
		if (_recordSOTrx_Effective == null)
		{
			_recordSOTrx_Effective = computeRecordSOTrxEffective();
		}
		return _recordSOTrx_Effective;
	}

	private SOTrx computeRecordSOTrxEffective()
	{
		final String tableName = getTableInfo().getTableName();
		final String sqlWhereClause = getRecordWhereClause(); // might be null
		if (sqlWhereClause == null || Check.isBlank(sqlWhereClause))
		{
			return SOTrx.SALES;
		}

		return DB.retrieveRecordSOTrx(tableName, sqlWhereClause).orElse(SOTrx.SALES);
	}
}
