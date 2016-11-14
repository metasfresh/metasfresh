package org.adempiere.process;

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
import java.util.Properties;
import org.compiere.model.MRoleMenu;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;

public class UpdateRoleMenu extends SvrProcess
{
	private int p_role_id = 0;
	
	@Override
	protected void prepare() 
	{
		ProcessInfoParameter[] para = getParametersAsArray();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			
			if (para[i].getParameter() == null)
				;
			else if (name.equals("AD_Role_ID"))
			{
				p_role_id = para[i].getParameterAsInt();
			}
			else
			{
				log.error("Unknown Parameter: " + name);
			}
		}

	}
	
	private MRoleMenu addUpdateRole(Properties ctx, int roleId, int menuId, boolean active, String trxName)
	{
		String whereClause = "AD_Role_ID=" + roleId + " AND U_WebMenu_ID=" + menuId;
		
		int roleMenuIds[] = MRoleMenu.getAllIDs(MRoleMenu.Table_Name, whereClause, trxName);
		
		
		MRoleMenu roleMenu;
		if ( roleMenuIds.length == 1)
		{
			roleMenu = new MRoleMenu(ctx, roleMenuIds[0],trxName);
		}
		else if ( roleMenuIds.length == 0)
		{
			roleMenu = new MRoleMenu(ctx, 0,trxName);
		}
		else
		{
			throw new IllegalStateException("More than one role menu defined.");
		}
		
		roleMenu.setAD_Role_ID(roleId);
		roleMenu.setU_WebMenu_ID(menuId);
		roleMenu.setIsActive(active);
		
		if (!roleMenu.save())
		{
			throw new IllegalStateException("Could not create/update role menu, RoleMenuId: " + roleMenu.get_ID());
		}
		
		return roleMenu;
	}
	
	@Override
	protected String doIt() throws Exception 
	{
		if (p_role_id == 0)
		{
			throw new Exception("No Role defined or cannot assign menus to System Administrator");
		}
		
		String sqlStmt = "SELECT U_WebMenu_ID, IsActive FROM U_WebMenu";
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sqlStmt, get_TrxName());
			rs = pstmt.executeQuery();
			
			while (rs.next())
			{
				int menuId = rs.getInt(1);
				boolean active = "Y".equals(rs.getString(2));
				addUpdateRole(getCtx(), p_role_id, menuId, active, get_TrxName());
			}
		}
		finally
		{
			DB.close(rs, pstmt);
		}
		
		return "Role updated successfully";
	}
}
