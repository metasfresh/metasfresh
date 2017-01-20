package org.adempiere.ad.language.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryOrderBy;
import org.adempiere.ad.dao.IQueryOrderBy.Direction;
import org.adempiere.ad.dao.IQueryOrderBy.Nulls;

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

import org.adempiere.ad.language.ILanguageDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.GuavaCollectors;
import org.adempiere.util.Services;
import org.adempiere.util.proxy.Cached;
import org.compiere.model.I_AD_Language;
import org.compiere.model.I_AD_Table;
import org.compiere.model.POInfo;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.slf4j.Logger;

import de.metas.adempiere.util.CacheCtx;
import de.metas.logging.LogManager;

public class LanguageDAO implements ILanguageDAO
{
	private static final Logger logger = LogManager.getLogger(LanguageDAO.class);

	private static final String SQL_retriveBaseLanguage_1P = "SELECT " + I_AD_Language.COLUMNNAME_AD_Language
			+ " FROM " + I_AD_Language.Table_Name
			+ " WHERE " + I_AD_Language.COLUMNNAME_IsBaseLanguage + "=?";

	private static final IQueryOrderBy ORDERBY_BaseLanguage_SystemLanguage_First = Services.get(IQueryBL.class)
			.createQueryOrderByBuilder(I_AD_Language.class)
			.addColumn(I_AD_Language.COLUMNNAME_IsBaseLanguage, Direction.Descending, Nulls.Last)
			.addColumn(I_AD_Language.COLUMNNAME_IsSystemLanguage, Direction.Descending, Nulls.Last)
			.addColumn(I_AD_Language.COLUMNNAME_AD_Language, Direction.Ascending, Nulls.Last)
			.createQueryOrderBy();

	@Override
	@Cached(cacheName = I_AD_Language.Table_Name)
	public List<I_AD_Language> retrieveAvailableLanguages(@CacheCtx final Properties ctx, final int clientId)
	{
		final IQueryBuilder<I_AD_Language> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_Language.class, ctx, ITrx.TRXNAME_None)
				.addInArrayOrAllFilter(I_AD_Language.COLUMNNAME_AD_Client_ID, 0, clientId)
				.addOnlyActiveRecordsFilter();

		// Only Base or System languages
		queryBuilder.addCompositeQueryFilter()
				.setJoinOr()
				.addEqualsFilter(I_AD_Language.COLUMNNAME_IsBaseLanguage, true)
				.addEqualsFilter(I_AD_Language.COLUMNNAME_IsSystemLanguage, true);

		// Ordered by Name
		queryBuilder.orderBy()
				.addColumn(I_AD_Language.COLUMNNAME_Name)
				.addColumn(I_AD_Language.COLUMNNAME_AD_Language);

		return queryBuilder.create().listImmutable(I_AD_Language.class);
	}

	@Override
	@Cached(cacheName = I_AD_Language.Table_Name + "#AvailableForMatching")
	public List<String> retrieveAvailableAD_LanguagesForMatching(@CacheCtx final Properties ctx)
	{
		final int adClientId = Env.getAD_Client_ID(ctx);
		return retrieveAvailableLanguages(ctx, adClientId)
				.stream()
				.sorted(ORDERBY_BaseLanguage_SystemLanguage_First.getComparator())
				.map(adLanguageModel -> adLanguageModel.getAD_Language())
				.collect(GuavaCollectors.toImmutableList());

	}

	@Override
	public String retrieveBaseLanguage()
	{
		// NOTE: because this method is called right after database connection is established
		// we cannot use the Query API which is requiring MLanguage.getBaseLanguage() to be set

		final String baseADLanguage = DB.getSQLValueStringEx(ITrx.TRXNAME_None, SQL_retriveBaseLanguage_1P, true);
		Check.assumeNotEmpty(baseADLanguage, "Base AD_Language shall be defined in database");

		return baseADLanguage;
	}

	@Override
	public I_AD_Language retrieveByAD_Language(final Properties ctx, final String adLanguage)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_Language.class, ctx, ITrx.TRXNAME_None)
				.addEqualsFilter(I_AD_Language.COLUMNNAME_AD_Language, adLanguage)
				.create()
				.firstOnly(I_AD_Language.class);
	}

	private List<I_AD_Language> retrieveSystemLanguages(final Properties ctx)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_Language.class, ctx, ITrx.TRXNAME_None)
				.addEqualsFilter(I_AD_Language.COLUMNNAME_IsSystemLanguage, true)
				.addOnlyActiveRecordsFilter()
				.orderBy()
				.addColumn(I_AD_Language.COLUMNNAME_AD_Language)
				.endOrderBy()
				.create()
				.list(I_AD_Language.class);
	}

	private List<String> retrieveTrlTableNames(final Properties ctx)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_Table.class, ctx, ITrx.TRXNAME_None)
				.addEndsWithQueryFilter(I_AD_Table.COLUMNNAME_TableName, "_Trl")
				.orderBy()
				.addColumn(I_AD_Table.COLUMNNAME_TableName)
				.endOrderBy()
				.create()
				.listDistinct(I_AD_Table.COLUMNNAME_TableName, String.class);
	}

	@Override
	public void addAllMissingTranslations(final Properties ctx)
	{
		final List<I_AD_Language> languages = retrieveSystemLanguages(ctx);

		final List<String> errorLanguages = new ArrayList<>();
		final List<Throwable> errorCauses = new ArrayList<>();
		for (final I_AD_Language language : languages)
		{
			// Skip base language
			if (language.isBaseLanguage())
			{
				continue;
			}

			try
			{
				addRemoveLanguageTranslations(language, true);
			}
			catch (Exception ex)
			{
				// collect error
				errorLanguages.add(language.getAD_Language());
				errorCauses.add(ex);
			}
		}

		// If we got some errors, build and throw an exception
		if (!errorLanguages.isEmpty())
		{
			final AdempiereException ex = new AdempiereException("Adding missing translations failed for following languages: " + errorLanguages);
			for (final Throwable cause : errorCauses)
			{
				ex.addSuppressed(cause);
			}
			throw ex;
		}
	}

	@Override
	public int addMissingTranslations(final I_AD_Language language)
	{
		final boolean add = true;
		return addRemoveLanguageTranslations(language, add);
	}

	@Override
	public int removeTranslations(final I_AD_Language language)
	{
		final boolean add = false;
		return addRemoveLanguageTranslations(language, add);
	}

	private int addRemoveLanguageTranslations(final I_AD_Language language, final boolean add)
	{
		Check.assumeNotNull(language, "language not null");
		final Properties ctx = InterfaceWrapperHelper.getCtx(language);

		final List<String> trlTableNames = retrieveTrlTableNames(ctx);

		int retNo = 0;
		final List<String> errorTables = new ArrayList<>();
		final List<Throwable> errorCauses = new ArrayList<>();
		for (final String trlTableName : trlTableNames)
		{
			try
			{
				if (add)
				{
					retNo += addTableTranslations(language, trlTableName);
				}
				else
				{
					retNo += deleteTableTranslations(language, trlTableName);
				}
			}
			catch (final Exception ex)
			{
				// collect error
				errorTables.add(trlTableName + "(" + language.getAD_Language() + ")");
				errorCauses.add(ex);
			}
		}

		// If we got some errors, build and throw an exception
		if (!errorTables.isEmpty())
		{
			final AdempiereException ex = new AdempiereException((add ? "Adding" : "Removing") + " missing translations failed for following tables: " + errorTables);
			for (final Throwable cause : errorCauses)
			{
				ex.addSuppressed(cause);
			}
			throw ex;
		}

		return retNo;
	}

	/**
	 * Delete Translation
	 *
	 * @param language
	 *
	 * @param trlTableName table name
	 * @return number of records deleted
	 */
	private int deleteTableTranslations(final I_AD_Language language, final String trlTableName)
	{
		final String adLanguage = language.getAD_Language();
		final String sql = "DELETE FROM  " + trlTableName + " WHERE AD_Language=?";
		final int no = DB.executeUpdateEx(sql, new Object[] { adLanguage }, ITrx.TRXNAME_ThreadInherited);
		logger.info("Removed {} translations for {} ({})", no, trlTableName, adLanguage);
		return no;
	}

	/**
	 * Add Translation to table
	 *
	 * @param language
	 *
	 * @param trlTableName table name
	 * @return number of records inserted
	 */
	private int addTableTranslations(final I_AD_Language language, final String trlTableName)
	{
		final String baseTableName = trlTableName.substring(0, trlTableName.length() - 4);
		final POInfo poInfo = POInfo.getPOInfo(baseTableName);
		if (poInfo == null)
		{
			logger.error("No POInfo found for {}", baseTableName);
			return 0;
		}

		final List<String> columns = poInfo.getTranslatedColumnNames();
		if (columns.isEmpty())
		{
			logger.error("No Columns found for {}", baseTableName);
			return 0;
		}

		final String tblAlias = "t";
		final StringBuilder cols = new StringBuilder();
		final StringBuilder colsWithAlias = new StringBuilder();
		for (int i = 0; i < columns.size(); i++)
		{
			cols.append(", ").append(columns.get(i));
			colsWithAlias.append(", ").append(tblAlias + "." + columns.get(i));
		}

		//
		// Insert Statement
		final String trlAlias = "trl";
		final String adLanguage = language.getAD_Language();
		final Properties ctx = InterfaceWrapperHelper.getCtx(language);
		final int AD_User_ID = Env.getAD_User_ID(ctx);
		final String keyColumn = poInfo.getKeyColumnName();
		Check.assumeNotEmpty(keyColumn, "keyColumn not empty for {}", baseTableName); // shall not happen
		//
		final StringBuilder insertSql = new StringBuilder();
		// @formatter:off
		insertSql.append(" INSERT INTO " + trlTableName + "(")
			.append("AD_Language")
			.append(", IsTranslated")
			.append(", AD_Client_ID")
			.append(", AD_Org_ID")
			.append(", Created")
			.append(", CreatedBy")
			.append(", Updated")
			.append(", UpdatedBy")
			.append(", IsActive")
			.append(", " + keyColumn)
			.append(cols)
		.append(")")
		.append("\n SELECT ")
			.append(DB.TO_STRING(adLanguage)) // AD_Language
			.append(", ").append(DB.TO_BOOLEAN(false)) // IsTranslated
			.append(", " + tblAlias + ".AD_Client_ID") // AD_Client_ID
			.append(", " + tblAlias + ".AD_Org_ID") // AD_Org_ID
			.append(", now()") // Created
			.append(", " + AD_User_ID) // CreatedBy
			.append(", now()") // Updated
			.append(", " + AD_User_ID) // UpdatedBy
			.append(", ").append(DB.TO_BOOLEAN(true)) // IsActive
			.append(", " + tblAlias + "." + keyColumn) // KeyColumn
			.append(colsWithAlias)
		.append("\n FROM " + baseTableName + " " + tblAlias)
			.append("\n LEFT JOIN " + baseTableName + "_Trl " + trlAlias)
			.append(" ON (")
			.append(trlAlias + "." + keyColumn + " = " + tblAlias + "." + keyColumn)
			.append(" AND " + trlAlias + ".AD_Language=" + DB.TO_STRING(adLanguage))
			.append(")")
		.append("\n WHERE " + trlAlias + "." + keyColumn + " IS NULL");
		// @formatter:on

		final int no = DB.executeUpdateEx(insertSql.toString(), null, ITrx.TRXNAME_ThreadInherited);
		if (no != 0)
		{
			logger.info("Added {} missing translations for {} ({})", no, trlTableName, adLanguage);
		}
		return no;
	}
}
