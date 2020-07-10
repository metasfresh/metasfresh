package de.metas.security.model.interceptor;

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

import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.DBException;
import org.compiere.model.I_AD_Role_Included;
import org.compiere.model.ModelValidator;
import org.compiere.util.DB;

import de.metas.security.IRoleDAO;
import de.metas.security.RoleId;
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

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE }, ifColumnsChanged = I_AD_Role_Included.COLUMNNAME_Included_Role_ID)
	public void assertNoLoops(final I_AD_Role_Included roleIncluded)
	{
		final RoleId includedRoleId = RoleId.ofRepoIdOrNull(roleIncluded.getIncluded_Role_ID());
		final List<RoleId> trace = new ArrayList<>();
		if (hasLoop(includedRoleId, trace))
		{
			final IRoleDAO roleDAO = Services.get(IRoleDAO.class);
			final StringBuilder roles = new StringBuilder();
			for (final RoleId roleId : trace)
			{
				if (roles.length() > 0)
					roles.append(" - ");
				roles.append(roleDAO.getRoleName(roleId));
			}
			throw new AdempiereException("Loop has detected: " + roles);
		}
	}

	/**
	 * @return true if loop detected. If you specified not null trace, you will have in that list the IDs from the loop
	 */
	// TODO: use recursive WITH sql clause
	private static boolean hasLoop(final RoleId roleId, final List<RoleId> trace)
	{
		final List<RoleId> trace2;
		if (trace == null)
		{
			trace2 = new ArrayList<>();
		}
		else
		{
			trace2 = new ArrayList<>(trace);
		}
		trace2.add(roleId);
		//
		final String sql = "SELECT "
				+ I_AD_Role_Included.COLUMNNAME_Included_Role_ID
				+ "," + I_AD_Role_Included.COLUMNNAME_AD_Role_ID
				+ " FROM " + I_AD_Role_Included.Table_Name
				+ " WHERE " + I_AD_Role_Included.COLUMNNAME_AD_Role_ID + "=?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_ThreadInherited);
			DB.setParameters(pstmt, roleId);
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				final RoleId childId = RoleId.ofRepoId(rs.getInt(1));
				if (trace2.contains(childId))
				{
					trace.clear();
					trace.addAll(trace2);
					trace.add(childId);
					return true;
				}
				if (hasLoop(childId, trace2))
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
