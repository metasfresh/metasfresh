package de.metas.i18n.po;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import de.metas.i18n.IModelTranslation;
import de.metas.i18n.IModelTranslationMap;
import de.metas.i18n.impl.ModelTranslation;
import de.metas.i18n.impl.NullModelTranslation;
import de.metas.i18n.impl.NullModelTranslationMap;
import de.metas.logging.LogManager;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.DBException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.service.IClientDAO;
import org.compiere.model.I_AD_Element;
import org.compiere.model.I_AD_Language;
import org.compiere.model.PO;
import org.compiere.util.DB;
import org.slf4j.Logger;

import javax.annotation.Nullable;
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
	public static final transient POTrlRepository instance = new POTrlRepository();

	private static final Logger logger = LogManager.getLogger(POTrlRepository.class);
	private IClientDAO clientsRepo; // lazy

	private static final String TRL_TABLE_SUFFIX = "_Trl";
	private static final String DYNATTR_TrlUpdateMode_UpdateIdenticalTrls = PO.class.getName() + ".TrlUpdateMode.UpdateIdenticalTrls";
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

	public final POTrlInfo createPOTrlInfo(final String tableName, @Nullable final String keyColumnName, final List<String> translatedColumnNames)
	{
		if (keyColumnName == null || Check.isEmpty(keyColumnName))
		{
			return POTrlInfo.NOT_TRANSLATED;
		}

		if (translatedColumnNames.isEmpty())
		{
			return POTrlInfo.NOT_TRANSLATED;
		}

		return POTrlInfo.builder()
				.translated(!translatedColumnNames.isEmpty())
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
	 * @return false if error (true if no translation or success)
	 */
	public final boolean insertTranslations(@NonNull final POTrlInfo trlInfo, final int recordId)
	{
		if (!trlInfo.isTranslated())
		{
			return true;
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
			return true;
		}

		final String tableName = trlInfo.getTableName();
		final String keyColumn = trlInfo.getKeyColumnName();
		final StringBuilder sql = new StringBuilder("INSERT INTO ").append(tableName).append("_Trl (AD_Language,").append(keyColumn).append(", ").append(iColumns)
				.append(" IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) ")
				.append("SELECT l.").append(I_AD_Language.COLUMNNAME_AD_Language).append(", t.").append(keyColumn).append(", ").append(sColumns)
				.append(" 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy ")
				.append("FROM AD_Language l, ").append(tableName).append(" t ")
				.append("WHERE l."
						+ I_AD_Language.COLUMNNAME_IsActive
						+ "='Y'");

		sql.append("AND (l." + I_AD_Language.COLUMNNAME_IsSystemLanguage + "='Y'");
		if (I_AD_Element.Table_Name.equals(tableName))
		{ // for AD_Element we need to make sure to have the BaseLanguage, no matter if that's also a system-language
			sql.append(" OR l." + I_AD_Language.COLUMNNAME_IsBaseLanguage + "='Y'");
		}
		sql.append(")");

		sql.append(" AND t.")
				.append(keyColumn).append("=").append(recordId)
				.append(" AND NOT EXISTS (SELECT 1 FROM ").append(tableName).append("_Trl tt WHERE tt.AD_Language=l."
				+ I_AD_Language.COLUMNNAME_AD_Language
				+ " AND tt.")
				.append(keyColumn).append("=t.").append(keyColumn).append(")");
		final int no = DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		logger.debug("Inserted {} translation records for {}", no, this);
		return no > 0;
	}

	/**
	 * Update PO's translations if any.
	 *
	 * @return <ul>
	 * <li>true if no translation or success
	 * <li>false if error
	 * </ul>
	 */
	public boolean updateTranslations(@NonNull final PO po)
	{
		final POTrlInfo trlInfo = po.getPOInfo().getTrlInfo();
		if (!trlInfo.isTranslated())
		{
			return true;
		}

		//
		// Check if there is any column which is translatable and it actually changed
		final boolean trlColumnChanged = trlInfo.getTranslatedColumnNames()
				.stream()
				.anyMatch(po::is_ValueChanged);
		if (!trlColumnChanged)
		{
			return true; // OK
		}

		//
		// Build the SQL to update all "TableName_Trl" records
		// NOTE: don't use parameterized SQLs because we need to log this command to migration scripts
		final String tableName = trlInfo.getTableName();
		final String keyColumn = trlInfo.getKeyColumnName();

		final StringBuilder sqlSet = new StringBuilder();

		//
		// If AutoUpdateTrl then copy the changed fields from record to each translation.
		final ClientId clientId = ClientId.ofRepoId(po.getAD_Client_ID());
		if (isAutoUpdateTrl(clientId, tableName))
		{
			for (final String columnName : trlInfo.getTranslatedColumnNames())
			{
				final String sqlValue = convertValueToSql(po.get_Value(columnName));
				sqlSet.append(columnName).append("=").append(sqlValue).append(", ");
			}
			sqlSet.deleteCharAt(sqlSet.lastIndexOf(","));
		}
		//
		// Case: flag all translations as IsTranslated='N',
		// but also copy the changed fields from record to each translation ONLY in case the translation is same as the old field value was.
		//
		// e.g. Consider:
		// AD_Message: MsgText="some msgtext which needs corrections"
		// AD_Message_Trl(de_DE): MsgText=same as AD_Message
		// Now change the AD_Message.MsgText to "some msgtext which is not correct".
		// In this case, the AD_Message_Trl(de_DE).MsgText shall be also updated because it was exactly the same as the AD_Message.MsgText was before.
		else if (po.isDynAttributeTrue(DYNATTR_TrlUpdateMode_UpdateIdenticalTrls))
		{
			for (final String columnName : trlInfo.getTranslatedColumnNames())
			{
				final String sqlValue = convertValueToSql(po.get_Value(columnName));
				final String sqlValueOld = convertValueToSql(po.get_ValueOld(columnName));

				sqlSet.append(columnName).append("=")
						.append("(CASE WHEN " + columnName + " IS NOT DISTINCT FROM " + sqlValueOld
								+ " THEN " + sqlValue
								+ " ELSE " + columnName
								+ " END)")
						.append(", ");
			}

			sqlSet.append("IsTranslated='N'");
		}
		//
		// Standard case: do nothing
		else
		{
			// do nothing, don't touch the IsTranslated flag
			return true; // OK
		}

		//
		final String trlWhereChecks =
				" AND ( trl.isTranslated = 'N' "
						+ " OR "
						+ " exists(select 1 from ad_language lang where lang.ad_language = trl.ad_language and lang.isBaseLanguage = 'Y') )";
		// Execute update
		final StringBuilder sql = new StringBuilder()
				.append("UPDATE ").append(tableName).append("_Trl trl SET ")
				.append(sqlSet)
				.append(" WHERE ").append(keyColumn).append("=").append(po.get_ID()).append(trlWhereChecks);

		final int updatedCount = DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		logger.debug("Updated {} translation records for {}", updatedCount, po);

		//
		return updatedCount >= 0;
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

		final StringBuilder sqlSet = new StringBuilder();
		sqlSet.append(columnName).append("=").append(DB.TO_STRING(value));
		sqlSet.append(", IsTranslated='Y'");

		final StringBuilder sql = new StringBuilder("UPDATE ").append(tableName).append("_Trl SET ").append(sqlSet)
				.append(" WHERE ").append(keyColumn).append("=").append(recordId)
				.append(" AND AD_Language=").append(DB.TO_STRING(adLanguage));

		final int updatedCount = DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		logger.debug("Updated {} translation records for {}/{}/{}", updatedCount, trlInfo, recordId, adLanguage);
	}

	/**
	 * @return true if all translations shall be updated from base record
	 */
	private boolean isAutoUpdateTrl(final ClientId adClientId, final String tableName)
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
		final StringBuilder sql = new StringBuilder("DELETE FROM  ").append(tableName).append("_Trl WHERE ").append(keyColumn).append("=").append(recordId);
		final int no = DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
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
		final String sql = trlInfo.getSqlSelectTrlById().get();

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

	/**
	 * if enabled, flags all translations as IsTranslated='N',
	 * and also copy the changed fields from record to each translation ONLY in case the translation is same as the old field value was.
	 */
	public final void setTrlUpdateModeAsUpdateIdenticalTrls(final Object model, final boolean enabled)
	{
		InterfaceWrapperHelper.setDynAttribute(model, DYNATTR_TrlUpdateMode_UpdateIdenticalTrls, enabled);
	}

}
