package de.metas.i18n.po;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import de.metas.cache.CacheMgt;
import de.metas.i18n.IModelTranslation;
import de.metas.i18n.IModelTranslationMap;
import de.metas.i18n.Language;
import de.metas.i18n.impl.ModelTranslation;
import de.metas.i18n.impl.NullModelTranslation;
import de.metas.i18n.impl.NullModelTranslationMap;
import de.metas.logging.LogManager;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.DBException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.service.IClientDAO;
import org.compiere.model.I_AD_Language;
import org.compiere.model.PO;
import org.compiere.model.POInfo;
import org.compiere.util.DB;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2017 metas GmbH
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
 * Creates/Updates/Deletes PO translations.
 *
 * @author metas-dev <dev@metasfresh.com>
 */
public class POTrlRepository
{
	public static final POTrlRepository instance = new POTrlRepository();

	private static final Logger logger = LogManager.getLogger(POTrlRepository.class);
	private IClientDAO clientsRepo; // lazy

	private static final String TRL_TABLE_SUFFIX = "_Trl";
	private static final String COLUMNNAME_AD_Language = "AD_Language";

	private POTrlRepository()
	{
	}

	private IClientDAO clientsRepo()
	{
		IClientDAO clientsRepo = this.clientsRepo;
		if (clientsRepo == null)
		{
			clientsRepo = this.clientsRepo = Services.get(IClientDAO.class);
		}
		return clientsRepo;
	}

	public static String toTrlTableName(final String tableName)
	{
		return tableName + TRL_TABLE_SUFFIX;
	}

	@Nullable
	private static String toBaseTableNameOrNull(@NonNull final String tableName)
	{
		final int idx = tableName.lastIndexOf(TRL_TABLE_SUFFIX);
		if (idx <= 0)
		{
			return null;
		}

		return tableName.substring(0, idx);
	}

	public static boolean isTrlTableName(@NonNull final String tableName)
	{
		return tableName.endsWith(TRL_TABLE_SUFFIX);
	}

	public final POTrlInfo createPOTrlInfo(final String tableName, final String keyColumnName, final List<String> translatedColumnNames)
	{
		if (translatedColumnNames.isEmpty())
		{
			return POTrlInfo.NOT_TRANSLATED;
		}

		return POTrlInfo.builder()
				.translated(true)
				.tableName(tableName)
				.keyColumnName(keyColumnName)
				.translatedColumnNames(ImmutableList.copyOf(translatedColumnNames))
				.sqlSelectTrlById(buildSqlSelectTrl(tableName, keyColumnName, translatedColumnNames, false))
				.sqlSelectTrlByIdAndLanguage(buildSqlSelectTrl(tableName, keyColumnName, translatedColumnNames, true))
				.build();
	}

	/**
	 * Insert (missing) translation records
	 *
	 * @return true if something was updated
	 */
	public final boolean onBaseRecordCreated(@NonNull final POTrlInfo trlInfo, final int recordId)
	{
		if (!trlInfo.isTranslated())
		{
			return false;
		}

		//
		final StringBuilder iColumns = new StringBuilder();
		final StringBuilder sColumns = new StringBuilder();
		for (final String columnName : trlInfo.getTranslatedColumnNames())
		{
			iColumns.append(columnName).append(",");
			sColumns.append("t.").append(columnName).append(",");
		}
		if (iColumns.length() == 0)
		{
			return false;
		}

		final String tableName = trlInfo.getTableName();
		final String keyColumn = trlInfo.getKeyColumnName();

		final String sql = "INSERT INTO " + tableName + "_Trl (AD_Language," + keyColumn + ", " + iColumns
				+ " IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) "
				+ "SELECT l." + I_AD_Language.COLUMNNAME_AD_Language + ", t." + keyColumn + ", " + sColumns
				+ " 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' "
				+ "FROM AD_Language l, " + tableName + " t "
				+ "WHERE l."
				+ I_AD_Language.COLUMNNAME_IsActive
				+ "='Y'"
				+ "AND (l." + I_AD_Language.COLUMNNAME_IsSystemLanguage + "='Y'"
				+ " OR l." + I_AD_Language.COLUMNNAME_IsBaseLanguage + "='Y'"
				+ ")"
				+ " AND t."
				+ keyColumn + "=" + recordId
				+ " AND NOT EXISTS (SELECT 1 FROM " + tableName + "_Trl tt WHERE tt.AD_Language=l."
				+ I_AD_Language.COLUMNNAME_AD_Language
				+ " AND tt."
				+ keyColumn + "=t." + keyColumn + ")";
		final int no = DB.executeUpdateEx(sql, ITrx.TRXNAME_ThreadInherited);
		logger.debug("Inserted {} translation records for {}", no, this);
		return no > 0;
	}

	/**
	 * Update PO's translations if any.
	 *
	 * @return true if something was updated
	 */
	public boolean onBaseRecordChanged(@NonNull final PO baseRecord)
	{
		final POTrlInfo trlInfo = baseRecord.getPOInfo().getTrlInfo();
		if (!trlInfo.isTranslated())
		{
			return false;
		}

		final StringBuilder sqlSet = new StringBuilder();
		for (final String columnName : trlInfo.getTranslatedColumnNames())
		{
			if (!InterfaceWrapperHelper.isValueChanged(baseRecord, columnName))
			{
				continue;
			}

			final String sqlValue = convertValueToSql(baseRecord.get_Value(columnName));
			if (sqlSet.length() > 0)
			{
				sqlSet.append(",");
			}
			sqlSet.append(columnName).append("=").append(sqlValue);
		}
		if (sqlSet.length() == 0)
		{
			return false;
		}

		final String tableName = trlInfo.getTableName();
		final String keyColumnName = trlInfo.getKeyColumnName();
		final int keyColumnValue = baseRecord.get_ID();
		final ClientId clientId = ClientId.ofRepoId(baseRecord.getAD_Client_ID());
		final String sqlWhereClause;
		if (isMasterdataTable(clientId, tableName))
		{
			sqlWhereClause = "(IsTranslated='N' OR AD_Language=" + DB.TO_STRING(Language.getBaseAD_Language()) + ")";
		}
		else
		{
			// Application dictionary tables
			sqlWhereClause = "AD_Language=" + DB.TO_STRING(Language.getBaseAD_Language());
		}

		//
		// Build the SQL to update all "TableName_Trl" records
		// NOTE: don't use parameterized SQLs because we need to log this command to migration scripts
		final String sql = "UPDATE " + toTrlTableName(tableName) + " trl SET " + sqlSet
				+ " WHERE " + keyColumnName + "=" + keyColumnValue + " AND " + sqlWhereClause;

		final int updatedCount = DB.executeUpdateEx(sql, ITrx.TRXNAME_ThreadInherited);
		logger.debug("Updated {} translation records for {}", updatedCount, baseRecord);

		final CacheMgt cacheMgt = CacheMgt.get();
		cacheMgt.reset(toTrlTableName(tableName), keyColumnValue);

		return updatedCount > 0;
	}

	public void onTrlRecordChanged(@NonNull final PO trlRecord)
	{
		final String trlTableName = trlRecord.get_TableName();
		final String baseTableName = toBaseTableNameOrNull(trlTableName);
		if (baseTableName == null)
		{
			return;
		}

		final POTrlInfo trlInfo = POInfo.getPOInfoNotNull(baseTableName).getTrlInfo();

		final String recordAdLanguage = trlRecord.get_ValueAsString(COLUMNNAME_AD_Language);
		if (!Language.isBaseLanguage(recordAdLanguage))
		{
			return;
		}

		final StringBuilder sqlSet = new StringBuilder();
		for (final String columnName : trlInfo.getTranslatedColumnNames())
		{
			if (!InterfaceWrapperHelper.isValueChanged(trlRecord, columnName))
			{
				continue;
			}

			final String sqlValue = convertValueToSql(trlRecord.get_Value(columnName));
			if (sqlSet.length() > 0)
			{
				sqlSet.append(", ");
			}
			sqlSet.append(columnName).append("=").append(sqlValue);
		}

		if (sqlSet.length() == 0)
		{
			return;
		}

		final String keyColumnName = trlInfo.getKeyColumnName();
		final Object keyColumnValue = trlRecord.get_Value(keyColumnName);

		final String sql = "UPDATE " + baseTableName + " SET " + sqlSet + " WHERE " + keyColumnName + "=" + convertValueToSql(keyColumnValue);
		final int updatedCount = DB.executeUpdateEx(sql, ITrx.TRXNAME_ThreadInherited);
		logger.debug("Updated {} base records for {}", updatedCount, trlRecord);

		//
		final CacheMgt cacheMgt = CacheMgt.get();
		if (keyColumnValue instanceof Integer)
		{
			cacheMgt.reset(baseTableName, (int)keyColumnValue);
		}
		else
		{
			cacheMgt.reset(baseTableName);
		}
	}

	private static String convertValueToSql(@Nullable final Object value)
	{
		if (value == null)
		{
			return "NULL";
		}
		else if (value instanceof String)
		{
			return DB.TO_STRING((String)value);
		}
		else if (value instanceof Boolean)
		{
			return DB.TO_BOOLEAN((Boolean)value);
		}
		else if (value instanceof Timestamp)
		{
			return DB.TO_DATE((Timestamp)value);
		}
		else if (value instanceof Integer)
		{
			return value.toString();
		}
		else if (value instanceof BigDecimal)
		{
			return value.toString();
		}
		else
		{
			return DB.TO_STRING(value.toString());
		}
	}

	/**
	 * Particularly updates the translatable column for a given recordId and adLanguage.
	 */
	public void updateTranslation(@NonNull final POTrlInfo trlInfo, final int recordId, @NonNull final String adLanguage, @NonNull final String columnName, final String value)
	{
		final String tableName = trlInfo.getTableName();
		final String keyColumn = trlInfo.getKeyColumnName();

		final String sqlSet = columnName + "=" + DB.TO_STRING(value) + ", IsTranslated='Y'";

		final String sql = "UPDATE " + toTrlTableName(tableName) + " SET " + sqlSet
				+ " WHERE " + keyColumn + "=" + recordId
				+ " AND AD_Language=" + DB.TO_STRING(adLanguage);

		final int updatedCount = DB.executeUpdateEx(sql, ITrx.TRXNAME_ThreadInherited);
		logger.debug("Updated {} translation records for {}/{}/{}", updatedCount, trlInfo, recordId, adLanguage);
		final CacheMgt cacheMgt = CacheMgt.get();
		cacheMgt.reset(toTrlTableName(tableName));
	}

	/**
	 * @return true if all translations shall be updated from base record
	 */
	private boolean isMasterdataTable(final ClientId adClientId, final String tableName)
	{
		if (tableName == null)
		{
			return false;
		}

		// Not Multi-Lingual Documents - only Doc Related
		if (adClientId.isSystem() && tableName.startsWith("AD_"))
		{
			return false;
		}

		return clientsRepo().isMultilingualDocumentsEnabled(adClientId);
	}

	/**
	 * Delete Translation Records
	 *
	 * @return false if error (true if no translation or success)
	 */
	public boolean deleteTranslations(final POTrlInfo trlInfo, final int recordId)
	{
		if (!trlInfo.isTranslated())
		{
			return true;
		}

		final String tableName = trlInfo.getTableName();
		final String keyColumn = trlInfo.getKeyColumnName();
		final int no = DB.executeUpdateEx(
				"DELETE FROM  " + toTrlTableName(tableName) + " WHERE " + keyColumn + "=" + recordId,
				ITrx.TRXNAME_ThreadInherited);
		logger.debug("Deleted {} translation records for {}/{}", no, trlInfo, recordId);
		return no >= 0;
	}

	public IModelTranslationMap retrieveAll(@NonNull final POTrlInfo trlInfo, final int recordId)
	{
		if (!trlInfo.isTranslated())
		{
			return NullModelTranslationMap.instance;
		}

		final ImmutableMap<String, IModelTranslation> trlsByLanguage = POTrlRepository.instance.retriveAllById(trlInfo, recordId);

		return POModelTranslationMap.builder()
				.recordId(recordId)
				.trlsByLanguage(trlsByLanguage)
				.build();
	}

	@Nullable
	private IModelTranslation retrieveTrl(final ResultSet rs, final POTrlInfo trlInfo) throws SQLException
	{
		final ImmutableMap.Builder<String, String> trlMapBuilder = ImmutableMap.builder();
		for (final String columnName : trlInfo.getTranslatedColumnNames())
		{
			final String value = rs.getString(columnName);
			if (value != null)
			{
				trlMapBuilder.put(columnName, value);
			}
		}
		final Map<String, String> trlMap = trlMapBuilder.build();

		final String adLanguage = rs.getString(COLUMNNAME_AD_Language);
		if (adLanguage == null)
		{
			// shall not happen
			logger.warn("Got null AD_Language for translation row={}\nTrlInfo={}", trlMap, trlInfo);
			return null;
		}

		return ModelTranslation.of(adLanguage, trlMap);
	}

	public ImmutableMap<String, IModelTranslation> retriveAllById(final POTrlInfo trlInfo, final int recordId)
	{
		final String sql = trlInfo.getSqlSelectTrlById()
				.orElseThrow(() -> new AdempiereException("No SqlSelectTrlById defined for " + trlInfo));

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		final Object[] params = new Object[] { recordId };
		try
		{
			// retrieve them out of trx; we might have to retrieve an error message related to an already failed trx
			pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_None);
			DB.setParameters(pstmt, params);
			rs = pstmt.executeQuery();

			final ImmutableMap.Builder<String, IModelTranslation> translations = ImmutableMap.builder();
			while (rs.next())
			{
				final IModelTranslation trl = retrieveTrl(rs, trlInfo);
				if (NullModelTranslation.isNull(trl))
				{
					continue;
				}

				final String adLanguage = trl.getAD_Language();
				translations.put(adLanguage, trl);
			}
			return translations.build();
		}
		catch (final SQLException e)
		{
			throw new DBException(e, sql, params);
		}
		finally
		{
			DB.close(rs, pstmt);
		}
	}

	/**
	 * Builds the SQL to select from translation table (i.e. <code>SELECT ... FROM TableName_Trl WHERE KeyColumnName=? [AND AD_Language=?]</code>)
	 *
	 * @param byLanguageToo if <code>true</code> the returned SQL shall also filter by "AD_Language"
	 * @return <ul>
	 * <li>SQL select
	 * <li>or {@link Optional#empty()} if table is not translatable or there are no translation columns
	 * </ul>
	 */
	private Optional<String> buildSqlSelectTrl(final String tableName, final String keyColumnName, final List<String> columnNames, final boolean byLanguageToo)
	{
		if (keyColumnName == null)
		{
			return Optional.empty();
		}

		if (columnNames.isEmpty())
		{
			return Optional.empty();
		}

		final String sqlColumns = Joiner.on(",").join(columnNames);
		final StringBuilder sql = new StringBuilder("SELECT ")
				.append(sqlColumns)
				.append(", ").append(COLUMNNAME_AD_Language)
				.append(" FROM ").append(toTrlTableName(tableName))
				.append(" WHERE ")
				.append(keyColumnName).append("=?");

		if (byLanguageToo)
		{
			sql.append(" AND ").append(COLUMNNAME_AD_Language).append("=?");
		}

		return Optional.of(sql.toString());
	}
}
