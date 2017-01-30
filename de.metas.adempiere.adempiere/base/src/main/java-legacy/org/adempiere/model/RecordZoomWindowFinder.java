package org.adempiere.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Supplier;

import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.DBException;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.ITableRecordReference;
import org.compiere.model.I_AD_Table;
import org.compiere.model.MQuery;
import org.compiere.model.MQuery.Operator;
import org.compiere.model.POInfo;
import org.compiere.util.CCache;
import org.compiere.util.DB;
import org.slf4j.Logger;

import com.google.common.base.MoreObjects;

import de.metas.logging.LogManager;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * Helper class used to find the actual AD_Window_ID to be used for a given record.
 *
 * Use case:
 * <ul>
 * <li>create a new instance using one of the static builders
 * <li>get the actual window ID: {@link #findAD_Window_ID()}
 * <li>get the {@link MQuery} to be used when the window is opened: {@link #createZoomQuery()}
 * </ul>
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class RecordZoomWindowFinder
{
	public static final RecordZoomWindowFinder newInstance(final String tableName, final int recordId)
	{
		return new RecordZoomWindowFinder(tableName, recordId);
	}

	public static final RecordZoomWindowFinder newInstance(final int adTableId, final int recordId)
	{
		final String tableName = Services.get(IADTableDAO.class).retrieveTableName(adTableId);
		return new RecordZoomWindowFinder(tableName, recordId);
	}
	
	public static final RecordZoomWindowFinder newInstance(final ITableRecordReference record)
	{
		Check.assumeNotNull(record, "Parameter record is not null");
		return new RecordZoomWindowFinder(record.getTableName(), record.getRecord_ID());
	}
	
	public static final int findAD_Window_ID(final ITableRecordReference record)
	{
		return newInstance(record).findAD_Window_ID();
	}

	public static final RecordZoomWindowFinder newInstance(final MQuery query)
	{
		return new RecordZoomWindowFinder(query);
	}
	
	private static final Logger logger = LogManager.getLogger(RecordZoomWindowFinder.class);

	//
	// Parameters
	private final String _tableName;
	private final int _recordId;
	private final MQuery _query_Provided;
	//
	private Integer _soWindowId_Provided = null;
	private Integer _poWindowId_Provided = null;
	private Boolean _isSOTrx_Provided = null;

	//
	// Effective values
	private String _keyColumnName;
	private WindowIds _windowIdsEffective = null;
	private Boolean _isSOTrx_Effective = null;

	//
	// Cache
	private static final transient CCache<String, WindowIds> tableName2defaultWindowIds = new CCache<>(I_AD_Table.Table_Name + "_DefaultWindowIds", 50, 0);

	private RecordZoomWindowFinder(final String tableName, final int recordId)
	{
		super();

		Check.assumeNotEmpty(tableName, "tableName is not empty");

		_tableName = tableName;
		
		_recordId = recordId;
		if(_recordId < 0)
		{
			logger.warn("No Record_ID provided to detect the AD_Window_ID by TableName={}. Going forward.", _tableName);
		}
			
		_query_Provided = null;
	}

	private RecordZoomWindowFinder(final MQuery query)
	{
		super();

		Check.assumeNotNull(query, "Parameter query is not null");

		final String tableName = query.getTableName();
		Check.assumeNotEmpty(tableName, "tableName is not empty for {}", query);
		_tableName = tableName;
		_recordId = -1;
		_query_Provided = query;

		//
		// Load additional infos from given query
		_isSOTrx_Provided = query.isSOTrxOrNull();
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("tableName", _tableName)
				.add("recordId", _recordId)
				.add("query_Provided", _query_Provided)
				//
				.add("isSOTrx_Provided", _isSOTrx_Provided)
				.add("isSOTrx_Effective", _isSOTrx_Effective)
				//
				.add("WindowIds_Effective", _windowIdsEffective)
				.add("PO_Window_ID_Provided", _poWindowId_Provided)
				.add("SO_Window_ID_Provided", _soWindowId_Provided)
				//
				.toString();
	}

	public int findAD_Window_ID()
	{
		final WindowIds windowIds = getEffectiveWindowIds();
		return windowIds.getAD_Window_ID_ByIsSOTrx(() -> getIsSOTrxEffective());
	}

	public MQuery createZoomQuery()
	{
		if (_query_Provided != null)
		{
			return _query_Provided;
		}

		final String tableName = getTableName();
		final String keyColumnName = getKeyColumnName();
		final int recordId = getRecord_ID();

		final MQuery query = new MQuery(tableName);
		query.addRestriction(keyColumnName, Operator.EQUAL, recordId);
		query.setZoomTableName(tableName);
		query.setZoomColumnName(keyColumnName);
		query.setZoomValue(recordId);
		query.setRecordCount(1); // metas: notify FindPanel that only one record will be expected

		return query;
	}

	public RecordZoomWindowFinder setSO_Window_ID(final int so_AD_Window_ID)
	{
		_soWindowId_Provided = so_AD_Window_ID;
		resetEffectiveValues();
		return this;
	}

	public RecordZoomWindowFinder setPO_Window_ID(final int po_AD_Window_ID)
	{
		_poWindowId_Provided = po_AD_Window_ID;
		resetEffectiveValues();
		return this;
	}

	public RecordZoomWindowFinder setIsSOTrx(final Boolean isSOTrx)
	{
		_isSOTrx_Provided = isSOTrx;
		resetEffectiveValues();
		return this;
	}

	private final void resetEffectiveValues()
	{
		_windowIdsEffective = null;
		_isSOTrx_Effective = null;
	}

	public String getTableName()
	{
		return _tableName;
	}

	public int getRecord_ID()
	{
		return _recordId;
	}

	private String getKeyColumnName()
	{
		if (_keyColumnName == null)
		{
			final String tableName = getTableName();
			_keyColumnName = retrieveKeyColumnName(tableName);
			Check.assumeNotEmpty(_keyColumnName, "KeyColumnName exists for {}", tableName);
		}
		return _keyColumnName;
	}

	private String getRecordWhereClause()
	{
		if (_query_Provided != null)
		{
			final boolean fullyQualified = false;
			return _query_Provided.getWhereClause(fullyQualified);
		}

		final String keyColumnName = getKeyColumnName();
		final int recordId = getRecord_ID();
		final String sqlWhereClause = keyColumnName + "=" + recordId;
		return sqlWhereClause;
	}

	private final WindowIds getEffectiveWindowIds()
	{
		if (_windowIdsEffective == null)
		{
			_windowIdsEffective = retrieveEffectiveWindowIds();
		}
		return _windowIdsEffective;
	}

	private final WindowIds retrieveEffectiveWindowIds()
	{
		final Integer soWindowId_Provided = _soWindowId_Provided;
		final Integer poWindowId_Provided = _poWindowId_Provided;

		//
		// If the SO and PO AD_Window_ID were specified => use them
		if (soWindowId_Provided != null && poWindowId_Provided != null)
		{
			return WindowIds.of(soWindowId_Provided, poWindowId_Provided);
		}

		//
		// Get default AD_Window_IDs
		final WindowIds windowIds_Default = getDefaultWindowIds();

		//
		// Set effective values
		return WindowIds.of(soWindowId_Provided, poWindowId_Provided, windowIds_Default);
	}

	private final WindowIds getDefaultWindowIds()
	{
		final String tableName = getTableName();
		return tableName2defaultWindowIds.getOrLoad(tableName, () -> retrieveDefaultWindowIds(tableName));
	}

	private boolean getIsSOTrxEffective()
	{
		if (_isSOTrx_Effective == null)
		{
			final Boolean isSOTrxProvided = _isSOTrx_Provided;
			if (isSOTrxProvided == null)
			{
				final String tableName = getTableName();
				final String sqlWhereClause = getRecordWhereClause();
				_isSOTrx_Effective = retriveIsSOTrx(tableName, sqlWhereClause);
			}
			else
			{
				_isSOTrx_Effective = isSOTrxProvided;
			}
		}
		return _isSOTrx_Effective;
	}

	//
	//
	// DAO methods
	//
	//

	private static final String retrieveKeyColumnName(final String tableName)
	{
		return POInfo.getPOInfo(tableName).getKeyColumnName();
	}

	private static final WindowIds retrieveDefaultWindowIds(final String tableName)
	{
		//
		// Load SO and PO AD_Window_IDs from AD_Table definition
		final String sql = "SELECT AD_Window_ID, PO_Window_ID FROM AD_Table WHERE TableName=?";
		final Object[] sqlParams = new Object[] { tableName };
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_None);
			DB.setParameters(pstmt, sqlParams);
			rs = pstmt.executeQuery();
			if (rs.next())
			{
				final int soWindowId = rs.getInt(1);
				final int poWindowId = rs.getInt(2);
				return WindowIds.of(soWindowId, poWindowId);
			}
			else
			{
				return WindowIds.NONE;
			}
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

	private static final boolean retriveIsSOTrx(final String tableName, final String sqlWhereClause)
	{
		return DB.isSOTrx(tableName, sqlWhereClause);
	}

	/**
	 * Internal class used to store PO/SO AD_Window_ID pairs.
	 *
	 * It also implements some helpful methods for deciding what AD_Window_ID to pick.
	 */
	private static final class WindowIds
	{
		public static final WindowIds of(final int soWindowId, final int poWindowId)
		{
			if (soWindowId <= 0 && poWindowId <= 0)
			{
				return NONE;
			}
			return new WindowIds(soWindowId, poWindowId);
		}

		public static final WindowIds of(final Integer soWindowId, final Integer poWindowId, final WindowIds defaults)
		{
			final int soWindowId_Effective = soWindowId != null ? soWindowId : defaults.getSO_Window_ID();
			final int poWindowId_Effective = poWindowId != null ? poWindowId : defaults.getPO_Window_ID();
			return of(soWindowId_Effective, poWindowId_Effective);
		}

		public static final WindowIds NONE = new WindowIds(-1, -1);

		private final int soWindowId;
		private final int poWindowId;

		private WindowIds(final int soWindowId, final int poWindowId)
		{
			super();
			this.soWindowId = soWindowId;
			this.poWindowId = poWindowId;
		}

		@Override
		public String toString()
		{
			return MoreObjects.toStringHelper(this)
					.add("soWindowId", soWindowId)
					.add("poWindowId", poWindowId)
					.toString();
		}

		public int getSO_Window_ID()
		{
			return soWindowId;
		}

		public int getPO_Window_ID()
		{
			return poWindowId;
		}

		public int getAD_Window_ID_ByIsSOTrx(final Supplier<Boolean> isSOTrxSupplier)
		{
			if (poWindowId > 0)
			{
				final boolean isSOTrx = isSOTrxSupplier.get();
				return isSOTrx ? soWindowId : poWindowId;
			}
			else
			{
				return soWindowId;
			}
		}
	}
}
