/**
 * 
 */
package de.metas.callcenter.model;

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

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.DBException;
import org.compiere.model.X_R_Group;
import org.compiere.util.DB;

/**
 * @author teo_sarca
 *
 */
public class BundleUtil
{
	public static final String R_RequestType_CCM_Default = "CCM_Default";
	
	public static final String R_Group_R_Category_ID = "R_Category_ID";
	public static final String R_Group_CCM_Bundle_Status = "CCM_Bundle_Status";
	
	public static final String CCM_Bundle_Status_New = "NW";
	public static final String CCM_Bundle_Status_InProgress = "IP";
	public static final String CCM_Bundle_Status_Done = "CO";
	public static final String CCM_Bundle_Status_Closed = "CL";
	
	
	public static void updateCCM_Bundle_Status(X_R_Group bundle)
	{
		updateCCM_Bundle_Status(bundle.getR_Group_ID(), bundle.get_TrxName());
	}
	
	/**
	 * 
	 * @param R_Group_ID
	 * @param trxName
	 * @return true if update, false if status remains the same
	 */
	public static boolean updateCCM_Bundle_Status(int R_Group_ID, String trxName)
	{
		final boolean isActive = "Y".equals(DB.getSQLValueStringEx(trxName, "SELECT IsActive FROM R_Group WHERE R_Group_ID=?", R_Group_ID));
		//
		final String sql = "SELECT"
			+"  b.IsActive"
			+", b."+R_Group_CCM_Bundle_Status
			+", COALESCE(rs.IsClosed, 'N') AS IsClosed"
			+", COUNT(*) AS cnt"
			+" FROM R_Group b"
			+" INNER JOIN "+MRGroupProspect.Table_Name+" c ON (c.R_Group_ID=b.R_Group_ID)"
			+" LEFT OUTER JOIN R_Request rq ON (rq.R_Request_ID=c.R_Request_ID)"
			+" LEFT OUTER JOIN R_Status rs ON (rs.R_Status_ID=rq.R_Status_ID)"
			+" WHERE b.R_Group_ID=?"
			+" GROUP BY "
			+"  b.IsActive"
			+", b."+R_Group_CCM_Bundle_Status
			+", COALESCE(rs.IsClosed, 'N')"
		;
		int countAll = 0;
		int countClosed = 0;
		String oldStatus = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, trxName);
			DB.setParameters(pstmt, new Object[]{R_Group_ID});
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				oldStatus = rs.getString(2);
				final boolean isClosed = "Y".equals(rs.getString(3));
				final int no = rs.getInt(4);
				if (isClosed)
					countClosed += no;
				countAll += no;
			}
		}
		catch (SQLException e)
		{
			throw new DBException(e, sql);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		//
		final String newStatus;
		// Closed = field "active" is unchecked
		if (!isActive)
		{
			newStatus = CCM_Bundle_Status_Closed;
		}
		//  New = no contacts added yet
		else if (countAll == 0)
		{
			newStatus = CCM_Bundle_Status_New;
		}
		// In Progress = contacts added and there are still open requests
		else if (countAll > 0 && countAll != countClosed)
		{
			newStatus = CCM_Bundle_Status_InProgress;
		}
		// Done(Completed) = all requests are closed
		else if (countAll > 0 && countAll == countClosed)
		{
			newStatus = CCM_Bundle_Status_Done;
		}
		else
		{
			// should not happen
			throw new AdempiereException("Unhandled status. Please contact your System Administrator"
					+" (CountAll="+countAll+", CountClosed="+countClosed+", R_Status_ID="+R_Group_ID+")");
		}

		// If status not found, try it directly
		// Case: the bundle has no contacts
		if (oldStatus == null)
		{
			oldStatus = DB.getSQLValueStringEx(trxName,
					"SELECT "+R_Group_CCM_Bundle_Status
					+" FROM R_Group WHERE R_Group_ID=?",
					R_Group_ID);
		}
		
		boolean updated = false;
		if (oldStatus == null || newStatus.compareTo(oldStatus) != 0)
		{
			int no = DB.executeUpdateAndThrowExceptionOnFail(
					"UPDATE R_Group SET "+R_Group_CCM_Bundle_Status+"=? WHERE R_Group_ID=?",
					new Object[]{newStatus, R_Group_ID},
					trxName);
			if (no != 1)
				throw new AdempiereException("Cannot update bundle "+R_Group_ID);
			updated = true;
		}
		
		return updated;
	}
}
