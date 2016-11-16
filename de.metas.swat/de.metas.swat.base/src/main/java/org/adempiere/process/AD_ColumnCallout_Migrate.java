/**
 * 
 */
package org.adempiere.process;

/*
 * #%L
 * de.metas.swat.base
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
import java.util.List;
import java.util.StringTokenizer;

import org.adempiere.exceptions.DBException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_AD_ColumnCallout;
import org.compiere.model.Query;
import org.compiere.util.DB;

import de.metas.process.SvrProcess;

/**
 * Migrate AD_Column.Callout fields to new approach, by using AD_ColumnCallout table
 * @author Teo Sarca, teo.sarca@gmail.com
 */
public class AD_ColumnCallout_Migrate extends SvrProcess
{
	@Override
	protected void prepare()
	{
	}

	@Override
	protected String doIt() throws Exception
	{
		final String sql = "SELECT c.AD_Table_ID, c.AD_Column_ID, t.TableName, c.ColumnName, c.Callout, c.EntityType"
			+" FROM AD_Column c"
			+" INNER JOIN AD_Table t ON (t.AD_Table_ID=c.AD_Table_ID)"
			+" WHERE c.Callout IS NOT NULL"
			+" ORDER BY t.TableName, c.ColumnName";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int cnt_ok = 0; int cnt_err = 0;
		try
		{
			pstmt = DB.prepareStatement(sql, get_TrxName());
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				//final int AD_Table_ID = rs.getInt("AD_Table_ID");
				final int AD_Column_ID = rs.getInt("AD_Column_ID");
				final String TableName = rs.getString("TableName");
				final String ColumnName = rs.getString("ColumnName");
				final String Callout = rs.getString("Callout");
				final String EntityType = rs.getString("EntityType");
				try
				{
					migrate(AD_Column_ID, Callout, EntityType);
					addLog("Migrate "+TableName+"."+ColumnName);
					cnt_ok++;
				}
				catch(Exception e)
				{
					addLog("Error on "+TableName+"."+ColumnName+": "+e.getLocalizedMessage());
					cnt_err++;
				}
			}
		}
		catch(SQLException e)
		{
			throw new DBException(e, sql);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		//
		return "@Created@ OK="+cnt_ok+" / Error="+cnt_err;
	}

	private void migrate(int AD_Column_ID, String callouts, String entityType)
	{
		int seqNo = 10;
		for (String classname : toCalloutsList(callouts))
		{
			I_AD_ColumnCallout cc = getColumnCallout(AD_Column_ID, classname);
			if (cc == null)
			{
				cc = InterfaceWrapperHelper.create(getCtx(), I_AD_ColumnCallout.class, get_TrxName());
				cc.setAD_Column_ID(AD_Column_ID);
				cc.setClassname(classname);
				cc.setEntityType(entityType);
				cc.setAD_Org_ID(0);
			}
			cc.setIsActive(true);
			cc.setSeqNo(seqNo);
			InterfaceWrapperHelper.save(cc);
			seqNo += 10;
		}
//		// Update AD_Column.Callout 
//		DB.executeUpdateEx(
//				"UPDATE AD_Column SET Callout=NULL, Updated=now(), UpdatedBy=? WHERE AD_Column_ID=?",
//				new Object[]{getAD_User_ID(), AD_Column_ID},
//				get_TrxName());
	}
	
	private List<String> toCalloutsList(String callouts)
	{
		List<String> list = new ArrayList<String>();
		if (callouts == null)
			return list;
		StringTokenizer st = new StringTokenizer(callouts, ";,", false);
		while (st.hasMoreTokens())      //  for each callout
		{
			String classname = st.nextToken().trim();
			list.add(classname);
		}
		return list;
	}
	
	private I_AD_ColumnCallout getColumnCallout(int AD_Column_ID, String classname)
	{
		String whereClause = I_AD_ColumnCallout.COLUMNNAME_AD_Column_ID+"=?"
		+" AND "+I_AD_ColumnCallout.COLUMNNAME_Classname+"=?"
		+" AND "+I_AD_ColumnCallout.COLUMNNAME_AD_Client_ID+"=0"
		+" AND "+I_AD_ColumnCallout.COLUMNNAME_AD_Org_ID+"=0";
		return new Query(getCtx(), I_AD_ColumnCallout.Table_Name, whereClause, get_TrxName())
		.setParameters(AD_Column_ID, classname)
		.firstOnly(I_AD_ColumnCallout.class);
	}
}
