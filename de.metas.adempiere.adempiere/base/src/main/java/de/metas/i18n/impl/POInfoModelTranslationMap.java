package de.metas.i18n.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.DBException;
import org.compiere.model.POInfo;
import org.compiere.util.DB;
import org.slf4j.Logger;

import com.google.common.base.MoreObjects;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.ImmutableMap;

import de.metas.i18n.IModelTranslation;
import de.metas.i18n.IModelTranslationMap;
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
 * {@link IModelTranslationMap} implementation which lazy loads all translations of a given database record, identified by {@link POInfo} and a given recordId.
 * 
 * @author metas-dev <dev@metas-fresh.com>
 *
 */
public class POInfoModelTranslationMap implements IModelTranslationMap
{
	public static POInfoModelTranslationMap of(final String tableName, final int recordId)
	{
		final POInfo poInfo = POInfo.getPOInfo(tableName);
		return new POInfoModelTranslationMap(poInfo, recordId);
	}

	public static POInfoModelTranslationMap of(final POInfo poInfo, final int recordId)
	{
		return new POInfoModelTranslationMap(poInfo, recordId);
	}

	private static final Logger logger = LogManager.getLogger(POInfoModelTranslationMap.class);

	private final POInfo poInfo;

	private int _recordId;
	private final LoadingCache<String, IModelTranslation> _trlsByLanguage = CacheBuilder.newBuilder()
			.build(new CacheLoader<String, IModelTranslation>()
			{

				@Override
				public IModelTranslation load(final String adLanguage) throws Exception
				{
					return retriveByIdAndLanguage(getRecord_ID(), adLanguage);
				}

			});
	private boolean _trlsByLanguage_FullyLoaded = false;

	private POInfoModelTranslationMap(final POInfo poInfo, final int recordId)
	{
		super();
		this.poInfo = poInfo;
		_recordId = recordId;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("poInfo", poInfo)
				.add("recordId", _recordId)
				.toString();
	}

	@Override
	public IModelTranslation getTranslation(final String adLanguage)
	{
		return getTranslation(getRecord_ID(), adLanguage);
	}

	public IModelTranslation getTranslation(final int recordId, final String adLanguage)
	{
		setRecord_ID(recordId);
		try
		{
			return _trlsByLanguage.get(adLanguage);
		}
		catch (final ExecutionException e)
		{
			logger.warn("Failed loading translation for recordId={}, adLanguage={} ({})", recordId, adLanguage, this);
			logger.warn("", e.getCause());
			return NullModelTranslation.instance;
		}
	}

	@Override
	public Map<String, IModelTranslation> getAllTranslations()
	{
		return getAllTranslations(getRecord_ID());
	}

	public Map<String, IModelTranslation> getAllTranslations(final int recordId)
	{
		setRecord_ID(recordId);

		final Map<String, IModelTranslation> translations;
		if (!_trlsByLanguage_FullyLoaded)
		{
			translations = retriveById(recordId);
			_trlsByLanguage.invalidateAll();
			_trlsByLanguage.putAll(translations);
		}
		else
		{
			translations = ImmutableMap.copyOf(_trlsByLanguage.asMap());
		}

		return translations;
	}

	private void setRecord_ID(final int recordId)
	{
		if (_recordId == recordId)
		{
			return;
		}

		_recordId = recordId;
		_trlsByLanguage.invalidateAll();
		_trlsByLanguage_FullyLoaded = false;
	}

	private int getRecord_ID()
	{
		return _recordId;
	}

	private IModelTranslation retriveByIdAndLanguage(final int recordId, final String AD_Language)
	{
		final String sql = poInfo.getSqlSelectTrlByIdAndLanguage();
		if (sql == null)
		{
			return NullModelTranslation.instance;
		}

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		final Object[] params = new Object[] { recordId, AD_Language };
		try
		{
			pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_ThreadInherited);
			DB.setParameters(pstmt, params);
			rs = pstmt.executeQuery();
			if (rs.next())
			{
				return retrieveTrl(rs);
			}
			else
			{
				return NullModelTranslation.instance;
			}
		}
		catch (final SQLException e)
		{
			throw new DBException(e, sql, params);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
	}

	private Map<String, IModelTranslation> retriveById(final int recordId)
	{
		final String sql = poInfo.getSqlSelectTrlById();
		if (sql == null)
		{
			return ImmutableMap.of();
		}

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		final Object[] params = new Object[] { recordId };
		try
		{
			pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_ThreadInherited);
			DB.setParameters(pstmt, params);
			rs = pstmt.executeQuery();

			final ImmutableMap.Builder<String, IModelTranslation> translations = ImmutableMap.builder();
			while (rs.next())
			{
				final IModelTranslation trl = retrieveTrl(rs);
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
			throw new DBException(e, sql.toString(), params);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
	}

	private final IModelTranslation retrieveTrl(final ResultSet rs) throws SQLException
	{
		final ImmutableMap.Builder<String, String> trlMapBuilder = ImmutableMap.builder();
		for (final String columnName : poInfo.getTranslatedColumnNames())
		{
			final String value = rs.getString(columnName);
			if (value != null)
			{
				trlMapBuilder.put(columnName, value);
			}
		}
		final Map<String, String> trlMap = trlMapBuilder.build();

		final String adLanguage = rs.getString(POInfo.COLUMNNAME_AD_Language);
		if (adLanguage == null)
		{
			// shall not happen
			logger.warn("No AD_Language found for translation row={}\nTrlMap={}", trlMap, this);
			return null;
		}

		return ModelTranslation.of(adLanguage, trlMap);
	}
}
