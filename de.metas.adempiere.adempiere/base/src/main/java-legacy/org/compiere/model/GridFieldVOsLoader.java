package org.compiere.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.DBException;
import org.adempiere.util.Check;
import org.compiere.util.DB;
import org.slf4j.Logger;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;

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

/*package*/ final class GridFieldVOsLoader
{
	public static final GridFieldVOsLoader newInstance()
	{
		return new GridFieldVOsLoader();
	}

	private static final Logger logger = LogManager.getLogger(GridFieldVOsLoader.class);

	private Properties _ctx;
	private int _windowNo;
	private int _tabNo;
	private int _adWindowId;
	private int _adTabId;
	private boolean _tabReadOnly;
	private boolean _loadAllLanguages = false;

	private GridFieldVOsLoader()
	{
		super();
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("WindowNo", _windowNo)
				.add("TabNo", _tabNo)
				.add("AD_Window_ID", _adWindowId)
				.add("AD_Tab_ID", _adTabId)
				.add("TabReadonly", _tabReadOnly)
				.add("loadAllLanguages", _loadAllLanguages)
				.toString();
	}

	public List<GridFieldVO> load()
	{
		final Properties ctx = getCtx();
		final int windowNo = getWindowNo();
		final int tabNo = getTabNo();
		final int AD_Window_ID = getAD_Window_ID();
		final int AD_Tab_ID = getAD_Tab_ID();
		final boolean tabReadOnly = isTabReadOnly();
		final boolean loadAllLanguages = isLoadAllLanguages();

		final List<Object> sqlParams = new ArrayList<>();
		final String sql = GridFieldVO.getSQL(ctx, AD_Tab_ID, loadAllLanguages, sqlParams);
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_None);
			DB.setParameters(pstmt, sqlParams);
			rs = pstmt.executeQuery();

			final LinkedHashMap<Integer, GridFieldVO> fields = new LinkedHashMap<>();

			while (rs.next())
			{
				// NOTE: we index by AD_Column_ID because AD_Field_ID might be null for auto-generated fields (check the view definition)
				final int AD_Column_ID = rs.getInt("AD_Column_ID");

				GridFieldVO field = fields.get(AD_Column_ID);
				if (field == null)
				{
					field = GridFieldVO.create(ctx, windowNo, tabNo //
							, AD_Window_ID, AD_Tab_ID //
							, tabReadOnly //
							, loadAllLanguages //
							, rs);
					if (field == null)
					{
						continue;
					}

					fields.put(AD_Column_ID, field);
				}
				else
				{
					field.loadAdditionalLanguage(rs);
				}
			}

			return ImmutableList.copyOf(fields.values());
		}
		catch (final Exception e)
		{
			final DBException dbEx = new DBException(e, sql, sqlParams);
			logger.error("Failed loading fields for {}. Returning empty list.", this, dbEx);
			return ImmutableList.of();
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
	}

	public GridFieldVOsLoader setCtx(final Properties ctx)
	{
		_ctx = ctx;
		return this;
	}

	private Properties getCtx()
	{
		Check.assumeNotNull(_ctx, "Parameter _ctx is not null");
		return _ctx;
	}

	public GridFieldVOsLoader setWindowNo(final int windowNo)
	{
		_windowNo = windowNo;
		return this;
	}

	private int getWindowNo()
	{
		return _windowNo;
	}

	public GridFieldVOsLoader setTabNo(final int tabNo)
	{
		_tabNo = tabNo;
		return this;
	}

	private int getTabNo()
	{
		return _tabNo;
	}

	public GridFieldVOsLoader setAD_Window_ID(final int AD_Window_ID)
	{
		_adWindowId = AD_Window_ID;
		return this;
	}

	private int getAD_Window_ID()
	{
		return _adWindowId;
	}

	public GridFieldVOsLoader setAD_Tab_ID(final int AD_Tab_ID)
	{
		_adTabId = AD_Tab_ID;
		return this;
	}

	private int getAD_Tab_ID()
	{
		return _adTabId;
	}

	public GridFieldVOsLoader setTabReadOnly(final boolean tabReadOnly)
	{
		_tabReadOnly = tabReadOnly;
		return this;
	}

	private boolean isTabReadOnly()
	{
		return _tabReadOnly;
	}

	public GridFieldVOsLoader setLoadAllLanguages(final boolean loadAllLanguages)
	{
		_loadAllLanguages = loadAllLanguages;
		return this;
	}

	private boolean isLoadAllLanguages()
	{
		return _loadAllLanguages;
	}
}
