package org.adempiere.ad.security.model.validator;

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


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.security.IRoleDAO;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.DBException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_AD_Role_Included;
import org.compiere.model.ModelValidator;
import org.compiere.util.DB;

import de.metas.util.Services;

@Interceptor(I_AD_Role_Included.class)
public class AD_Role_Included
{
	public static final transient AD_Role_Included instance = new AD_Role_Included();

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE })
	public void validate(I_AD_Role_Included roleIncluded)
	{
		if (roleIncluded.getAD_Role_ID() == roleIncluded.getIncluded_Role_ID())
		{
			throw new AdempiereException("@AD_Role_ID@ == @Included_Role_ID@");
		}
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE }
			, ifColumnsChanged = I_AD_Role_Included.COLUMNNAME_Included_Role_ID)
	public void assertNoLoops(final I_AD_Role_Included roleIncluded)
	{
		final String trxName = InterfaceWrapperHelper.getTrxName(roleIncluded);
		List<Integer> trace = new ArrayList<Integer>();
		if (hasLoop(I_AD_Role_Included.Table_Name,
				I_AD_Role_Included.COLUMNNAME_Included_Role_ID,
				I_AD_Role_Included.COLUMNNAME_AD_Role_ID,
				roleIncluded.getIncluded_Role_ID(),
				trace,
				trxName))
		{
			final Properties ctx = InterfaceWrapperHelper.getCtx(roleIncluded);
			final IRoleDAO roleDAO = Services.get(IRoleDAO.class);
			final StringBuilder roles = new StringBuilder();
			for (int role_id : trace)
			{
				if (roles.length() > 0)
					roles.append(" - ");
				roles.append(roleDAO.retrieveRoleName(ctx, role_id));
			}
			throw new AdempiereException("Loop has detected " + roles);
		}
	}

	/**
	 * Check if there is a loop in the tree defined in given table
	 * 
	 * @param tableName
	 * @param idColumnName Node_ID column name
	 * @param parentIdColumnName Parent_ID column name
	 * @param nodeId current Node_ID
	 * @param trace current tree path (optional)
	 * @param trxName transaction name
	 * @return true if loop detected. If you specified not null trace, you will have in that list the IDs from the loop
	 */
	// TODO: refactor this method and move into org.compiere.util.DB class because it's general and usefull of others too
	// TODO2: use recursive WITH sql clause
	private static boolean hasLoop(String tableName, String idColumnName, String parentIdColumnName,
			int nodeId, List<Integer> trace,
			String trxName)
	{
		final List<Integer> trace2;
		if (trace == null)
		{
			trace2 = new ArrayList<Integer>(10);
		}
		else
		{
			trace2 = new ArrayList<Integer>(trace);
		}
		trace2.add(nodeId);
		//
		final String sql = "SELECT " + idColumnName + "," + parentIdColumnName + " FROM " + tableName + " WHERE " + parentIdColumnName + "=?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, trxName);
			pstmt.setInt(1, nodeId);
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				final int childId = rs.getInt(1);
				if (trace2.contains(childId))
				{
					trace.clear();
					trace.addAll(trace2);
					trace.add(childId);
					return true;
				}
				if (hasLoop(tableName, idColumnName, parentIdColumnName, childId, trace2, trxName))
				{
					trace.clear();
					trace.addAll(trace2);
					return true;
				}
			}
		}
		catch (SQLException e)
		{
			throw new DBException(e, sql);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		//
		return false;
	}

}
