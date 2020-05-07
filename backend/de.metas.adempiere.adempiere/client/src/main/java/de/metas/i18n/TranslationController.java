package de.metas.i18n;

/*
 * #%L
 * de.metas.adempiere.adempiere.client
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

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.DBException;
import org.adempiere.service.IClientDAO;
import org.adempiere.util.Services;
import org.compiere.apps.IStatusBar;
import org.compiere.model.I_AD_Client;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.compiere.util.ValueNamePair;

/**
 * 
 * @author metas-dev <dev@metasfresh.com>
 * @author based on initial version of Low Heng Sin, Idalica
 *
 */
public class TranslationController
{
	/** Window No */
	protected int m_WindowNo = Env.WINDOW_None;

	protected TranslationController()
	{
	}

	protected List<KeyNamePair> getClientList()
	{
		final List<KeyNamePair> list = new ArrayList<>();

		list.add(new KeyNamePair(-1, ""));

		Services.get(IClientDAO.class).retrieveAllClients(Env.getCtx())
				.stream()
				.filter(I_AD_Client::isActive)
				.map(adClient -> KeyNamePair.of(adClient.getAD_Client_ID(), adClient.getName()))
				.forEach(list::add);

		return list;
	}

	protected List<ValueNamePair> getLanguageList()
	{
		return Services.get(ILanguageDAO.class).retrieveAvailableLanguages().toValueNamePairs();
	}

	protected List<ValueNamePair> getTableList()
	{
		final String sql = "SELECT Name, TableName "
				+ " FROM AD_Table "
				+ " WHERE TableName LIKE '%_Trl' AND TableName<>'AD_Column_Trl' "
				+ " ORDER BY Name";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_None);
			rs = pstmt.executeQuery();

			final List<ValueNamePair> list = new ArrayList<>();
			list.add(new ValueNamePair("", ""));

			while (rs.next())
			{
				final ValueNamePair vp = new ValueNamePair(rs.getString(2), rs.getString(1));
				list.add(vp);
			}

			return list;
		}
		catch (final SQLException e)
		{
			throw new DBException(e, sql);
		}
		finally
		{
			DB.close(rs, pstmt);
		}

	}

	protected void clearStatusBar(final IStatusBar statusBar)
	{
		statusBar.setStatusLine(" ");
		statusBar.setStatusDB(" ");
	}
}
