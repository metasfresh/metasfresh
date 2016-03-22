/******************************************************************************
 * Copyright (C) 2009 Low Heng Sin                                            *
 * Copyright (C) 2009 Idalica Corporation                                     *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 *****************************************************************************/
package org.compiere.install;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.slf4j.Logger;
import de.metas.logging.LogManager;

import org.compiere.apps.IStatusBar;
import org.slf4j.Logger;
import de.metas.logging.LogManager;
import org.compiere.util.DB;
import org.compiere.util.KeyNamePair;
import org.compiere.util.ValueNamePair;


public class TranslationController
{
	public TranslationController()
	{
	}
	
	/**	Logger			*/
	public static Logger log = LogManager.getLogger(TranslationController.class);
	/**	Window No			*/
	public int         	m_WindowNo = 0;

	public ArrayList<KeyNamePair> getClientList()
	{
		ArrayList<KeyNamePair> list = new ArrayList<KeyNamePair>();
		
		list.add(new KeyNamePair (-1, ""));
		String sql = "SELECT Name, AD_Client_ID "
			+ "FROM AD_Client "
			+ "WHERE IsActive='Y' "
			+ "ORDER BY AD_Client_ID";
		try
		{
			PreparedStatement pstmt = DB.prepareStatement(sql, null);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next())
			{
				KeyNamePair kp = new KeyNamePair (rs.getInt(2), rs.getString(1));
				list.add(kp);
			}
			rs.close();
			pstmt.close();
		}
		catch (SQLException e)
		{
			log.error(sql, e);
		}
		
		return list;
	}
	
	public ArrayList<ValueNamePair> getLanguageList()
	{
		ArrayList<ValueNamePair> list = new ArrayList<ValueNamePair>();

		//	Fill Language
		String sql = "SELECT Name, AD_Language "
			+ "FROM AD_Language "
			+ "WHERE IsActive='Y' AND (IsSystemLanguage='Y' OR IsBaseLanguage='Y')"
			+ "ORDER BY Name";
		try
		{
			PreparedStatement pstmt = DB.prepareStatement(sql, null);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next())
			{
				ValueNamePair vp = new ValueNamePair (rs.getString(2), rs.getString(1));
				list.add(vp);
			}
			rs.close();
			pstmt.close();
		}
		catch (SQLException e)
		{
			log.error(sql, e);
		}
		
		return list;
	}
	
	public ArrayList<ValueNamePair> getTableList()
	{
		ArrayList<ValueNamePair> list = new ArrayList<ValueNamePair>();

		//	Fill Table
		list.add(new ValueNamePair ("", ""));
		String sql = "SELECT Name, TableName "
			+ "FROM AD_Table "
			+ "WHERE TableName LIKE '%_Trl' AND TableName<>'AD_Column_Trl' "
			+ "ORDER BY Name";
		try
		{
			PreparedStatement pstmt = DB.prepareStatement(sql, null);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next())
			{
				ValueNamePair vp = new ValueNamePair (rs.getString(2), rs.getString(1));
				list.add(vp);
			}
			rs.close();
			pstmt.close();
		}
		catch (SQLException e)
		{
			log.error(sql, e);
		}
		
		return list;
	}
	
	public void setStatusBar(IStatusBar statusBar)
	{
		//	Info
		statusBar.setStatusLine(" ");
		statusBar.setStatusDB(" ");
	}	//	dynInit
}
