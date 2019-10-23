package de.metas.dataentry.data.impexp;

import java.util.concurrent.ExecutionException;

import org.adempiere.ad.table.api.AdTableId;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.POInfo;
import org.compiere.util.DB;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import de.metas.dataentry.model.I_I_DataEntry_Record;
import de.metas.util.Check;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2019 metas GmbH
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

final class RecordIdLookup
{
	private static final String COLUMNNAME_Value = "Value";
	private static final String COLUMNNAME_ExternalId = I_I_DataEntry_Record.COLUMNNAME_ExternalId;

	private final LoadingCache<TableAndLookupKey, Integer> recordIdsByExternalIdCache = CacheBuilder.newBuilder()
			.maximumSize(100)
			.build(new CacheLoader<TableAndLookupKey, Integer>()
			{
				@Override
				public Integer load(TableAndLookupKey key)
				{
					return retrieveRecordId(key);
				}
			});

	public int getRecordId(@NonNull final AdTableId adTableId, @NonNull final String lookupKey)
	{
		final TableAndLookupKey key = new TableAndLookupKey(adTableId, lookupKey);
		try
		{
			return recordIdsByExternalIdCache.get(key);
		}
		catch (ExecutionException e)
		{
			throw AdempiereException.wrapIfNeeded(e)
					.setParameter("key", key);
		}
	}

	private int retrieveRecordId(@NonNull final TableAndLookupKey key)
	{
		final POInfo poInfo = POInfo.getPOInfo(key.getAdTableId());
		final String lookupKey = key.getLookupKey();

		int recordId = retrieveRecordIdByExternalId(poInfo, lookupKey);
		if (recordId < 0)
		{
			recordId = retrieveRecordIdByValue(poInfo, lookupKey);
		}
		if (recordId < 0)
		{
			throw new AdempiereException("@NotFound@ @Record_ID@: " + key);
		}

		return recordId;
	}

	private int retrieveRecordIdByExternalId(@NonNull final POInfo poInfo, @NonNull final String externalId)
	{
		if (!poInfo.hasColumnName(COLUMNNAME_ExternalId))
		{
			return -1;
		}

		final String tableName = poInfo.getTableName();
		final String keyColumnName = poInfo.getKeyColumnName();
		if (keyColumnName == null)
		{
			throw new AdempiereException("No key column found: " + tableName);
		}

		final String sql = "SELECT " + keyColumnName
				+ " FROM " + tableName
				+ " WHERE " + I_I_DataEntry_Record.COLUMNNAME_ExternalId + "=?"
				+ " ORDER BY " + keyColumnName;
		final int recordId = DB.getSQLValueEx(ITrx.TRXNAME_ThreadInherited, sql, externalId);
		return recordId;
	}

	private int retrieveRecordIdByValue(@NonNull final POInfo poInfo, @NonNull final String value)
	{
		if (!poInfo.hasColumnName(COLUMNNAME_Value))
		{
			return -1;
		}

		final String tableName = poInfo.getTableName();
		final String keyColumnName = poInfo.getKeyColumnName();
		if (keyColumnName == null)
		{
			throw new AdempiereException("No key column found: " + tableName);
		}

		final String sql = "SELECT " + keyColumnName
				+ " FROM " + tableName
				+ " WHERE " + COLUMNNAME_Value + "=?"
				+ " ORDER BY " + keyColumnName;
		final int recordId = DB.getSQLValueEx(ITrx.TRXNAME_ThreadInherited, sql, value);
		return recordId;
	}

	@Value
	private static class TableAndLookupKey
	{
		@NonNull
		AdTableId adTableId;
		@NonNull
		String lookupKey;

		private TableAndLookupKey(
				@NonNull final AdTableId adTableId,
				@NonNull final String lookupKey)
		{
			Check.assumeNotEmpty(lookupKey, "lookupKey is not empty");

			this.adTableId = adTableId;
			this.lookupKey = lookupKey.trim();
		}
	}
}
