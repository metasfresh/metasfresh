package de.metas.document.references;

import java.util.Optional;
import java.util.function.Supplier;

import javax.annotation.Nullable;

import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_AD_Table;
import org.compiere.model.MQuery;
import org.compiere.model.MQuery.Operator;
import org.compiere.model.POInfo;
import org.compiere.util.DB;
import org.slf4j.Logger;

import de.metas.cache.CCache;
import de.metas.lang.SOTrx;
import de.metas.logging.LogManager;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.ToString;
import lombok.Value;

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
 *
 * Use case:
 * <ul>
 * <li>create a new instance using one of the static builders
 * <li>get the actual window ID: {@link #findAdWindowId()}
 * <li>get the {@link MQuery} to be used when the window is opened: {@link #createZoomQuery()}
 * </ul>
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@ToString(doNotUseGetters = false)
public class RecordZoomWindowFinder
{
	public static final RecordZoomWindowFinder newInstance(final String tableName, final int recordId)
	{
		return new RecordZoomWindowFinder(tableName, recordId);
	}

	public static final RecordZoomWindowFinder newInstance(@NonNull final TableRecordReference record)
	{
		return new RecordZoomWindowFinder(record.getTableName(), record.getRecord_ID());
	}

	public static final RecordZoomWindowFinder newInstance(final String tableName)
	{
		return new RecordZoomWindowFinder(tableName);
	}

	public static final Optional<AdWindowId> findAdWindowId(final TableRecordReference record)
	{
		return newInstance(record).findAdWindowId();
	}

	public static final Optional<AdWindowId> findAdWindowId(final String tableName)
	{
		return newInstance(tableName).findAdWindowId();
	}

	public static final RecordZoomWindowFinder newInstance(final MQuery query)
	{
		return new RecordZoomWindowFinder(query);
	}

	public static final RecordZoomWindowFinder newInstance(final MQuery query, final AdWindowId windowIdToUse)
	{
		return new RecordZoomWindowFinder(query, windowIdToUse);

	}

	private static final Logger logger = LogManager.getLogger(RecordZoomWindowFinder.class);

	//
	// Parameters
	private final String _tableName;
	private final int _recordId;
	private final MQuery _query_Provided;
	//
	private AdWindowId _soWindowId_Provided = null;
	private AdWindowId _poWindowId_Provided = null;
	private SOTrx _soTrx_Provided = null;

	//
	// Effective values
	private String _keyColumnName;
	private WindowIds _windowIdsEffective = null;
	private SOTrx _soTrx_Effective = null;

	//
	// Cache
	private static final transient CCache<String, WindowIds> tableName2defaultWindowIds = new CCache<>(I_AD_Table.Table_Name + "_DefaultWindowIds", 50, 0);

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
	}

	private RecordZoomWindowFinder(final String tableName)
	{
		Check.assumeNotEmpty(tableName, "tableName is not empty");
		_tableName = tableName;
		_recordId = -1;
		_query_Provided = null;
	}

	private RecordZoomWindowFinder(@NonNull final MQuery query)
	{
		final String tableName = query.getTableName();
		Check.assumeNotEmpty(tableName, "tableName is not empty for {}", query);
		_tableName = tableName;
		_recordId = -1;
		_query_Provided = query;

		//
		// Load additional infos from given query
		_soTrx_Provided = query.isSOTrxOrNull();

	}

	private RecordZoomWindowFinder(
			@NonNull final MQuery query,
			@Nullable final AdWindowId adWindowId)
	{
		final String tableName = query.getTableName();
		Check.assumeNotEmpty(tableName, "tableName is not empty for {}", query);
		_tableName = tableName;
		_recordId = -1;
		_query_Provided = query;

		//
		// Load additional infos from given query
		_soTrx_Provided = query.isSOTrxOrNull();

		// task #1062
		// suggested window is for both trx
		// to be extended if we need 2 windows later
		_windowIdsEffective = WindowIds.of(adWindowId, adWindowId);
	}

	public Optional<AdWindowId> findAdWindowId()
	{
		final WindowIds windowIds = getEffectiveWindowIds();
		return windowIds.getAdWindowIdBySOTrx(this::getSOTrxEffective);
	}

	@Deprecated
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

	public RecordZoomWindowFinder soWindowId(@Nullable final AdWindowId soWindowId)
	{
		_soWindowId_Provided = soWindowId;
		resetEffectiveValues();
		return this;
	}

	public RecordZoomWindowFinder poWindowId(@Nullable final AdWindowId poWindowId)
	{
		_poWindowId_Provided = poWindowId;
		resetEffectiveValues();
		return this;
	}

	public RecordZoomWindowFinder soTrx(@Nullable final SOTrx soTrx)
	{
		_soTrx_Provided = soTrx;
		resetEffectiveValues();
		return this;
	}

	private final void resetEffectiveValues()
	{
		_windowIdsEffective = null;
		_soTrx_Effective = null;
	}

	private String getTableName()
	{
		return _tableName;
	}

	private int getRecord_ID()
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

		final int recordId = getRecord_ID();
		if (recordId < 0)
		{
			return null;
		}

		final String keyColumnName = getKeyColumnName();
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
		final AdWindowId soWindowId_Provided = _soWindowId_Provided;
		final AdWindowId poWindowId_Provided = _poWindowId_Provided;

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

	private SOTrx getSOTrxEffective()
	{
		if (_soTrx_Effective == null)
		{
			_soTrx_Effective = computeSOTrxEffective();
		}
		return _soTrx_Effective;
	}

	private SOTrx computeSOTrxEffective()
	{
		final SOTrx soTrxProvided = _soTrx_Provided;
		if (soTrxProvided != null)
		{
			return soTrxProvided;
		}

		final String tableName = getTableName();
		final String sqlWhereClause = getRecordWhereClause(); // might be null
		if (Check.isEmpty(sqlWhereClause, true))
		{
			return SOTrx.SALES;
		}

		return DB.retrieveRecordSOTrx(tableName, sqlWhereClause).orElse(SOTrx.SALES);
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

	private static final WindowIds retrieveDefaultWindowIds(@Nullable final String tableName)
	{
		final I_AD_Table table = Services.get(IADTableDAO.class).retrieveTableOrNull(tableName);
		if (table == null)
		{
			return WindowIds.NONE;
		}

		final AdWindowId soWindowId = AdWindowId.ofRepoIdOrNull(table.getAD_Window_ID());
		final AdWindowId poWindowId = AdWindowId.ofRepoIdOrNull(table.getPO_Window_ID());
		return WindowIds.of(soWindowId, poWindowId);
	}

	/**
	 * Internal class used to store PO/SO AD_Window_ID pairs.
	 *
	 * It also implements some helpful methods for deciding what AD_Window_ID to pick.
	 */
	@Value
	private static final class WindowIds
	{
		public static WindowIds of(
				@Nullable final AdWindowId soWindowId,
				@Nullable final AdWindowId poWindowId)
		{
			if (soWindowId == null && poWindowId == null)
			{
				return NONE;
			}
			return new WindowIds(soWindowId, poWindowId);
		}

		public static WindowIds of(
				@Nullable final AdWindowId soWindowId,
				@Nullable final AdWindowId poWindowId,
				@NonNull final WindowIds defaults)
		{
			final AdWindowId soWindowId_Effective = soWindowId != null ? soWindowId : defaults.getSoWindowId().orElse(null);
			final AdWindowId poWindowId_Effective = poWindowId != null ? poWindowId : defaults.getPoWindowId().orElse(null);
			return of(soWindowId_Effective, poWindowId_Effective);
		}

		public static final WindowIds NONE = new WindowIds();

		private final Optional<AdWindowId> soWindowId;
		private final Optional<AdWindowId> poWindowId;

		private WindowIds(final AdWindowId soWindowId, final AdWindowId poWindowId)
		{
			this.soWindowId = Optional.ofNullable(soWindowId);
			this.poWindowId = Optional.ofNullable(poWindowId);
		}

		private WindowIds()
		{
			this.soWindowId = Optional.empty();
			this.poWindowId = Optional.empty();
		}

		public Optional<AdWindowId> getAdWindowIdBySOTrx(final Supplier<SOTrx> soTrxSupplier)
		{
			if (poWindowId.isPresent())
			{
				final SOTrx soTrx = soTrxSupplier.get();
				return soTrx.isSales() ? soWindowId : poWindowId;
			}
			else
			{
				return soWindowId;
			}
		}
	}
}
