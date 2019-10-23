/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved. *
 * This program is free software; you can redistribute it and/or modify it *
 * under the terms version 2 of the GNU General Public License as published *
 * by the Free Software Foundation. This program is distributed in the hope *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. *
 * See the GNU General Public License for more details. *
 * You should have received a copy of the GNU General Public License along *
 * with this program; if not, write to the Free Software Foundation, Inc., *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA. *
 * For the text or an alternative of this public license, you may reach us *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA *
 * or via info@compiere.org or http://www.compiere.org/license.html *
 *****************************************************************************/
package org.compiere.process;

import org.adempiere.service.ClientId;
import org.compiere.model.I_AD_Role;

import de.metas.cache.CacheMgt;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessInfoParameter;
import de.metas.security.IRoleDAO;
import de.metas.security.IUserRolePermissionsDAO;
import de.metas.security.Role;
import de.metas.security.RoleId;
import de.metas.util.Loggables;
import de.metas.util.Services;

/**
 * Update Role Access
 * 
 * @author Jorg Janke
 * @version $Id: RoleAccessUpdate.java,v 1.3 2006/07/30 00:51:02 jjanke Exp $
 */
public class RoleAccessUpdate extends JavaProcess
{
	/** Update Role */
	private int p_AD_Role_ID = -1;
	/** Update Roles of Client */
	private int p_AD_Client_ID = -1;

	/**
	 * Prepare
	 */
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
				p_AD_Role_ID = para[i].getParameterAsInt();
			else if (name.equals("AD_Client_ID"))
				p_AD_Client_ID = para[i].getParameterAsInt();
			else
				log.error("Unknown Parameter: " + name);
		}
	}	// prepare

	/**
	 * Process
	 * 
	 * @return info
	 * @throws Exception
	 */
	@Override
	protected String doIt() throws Exception
	{
		if (p_AD_Role_ID > 0)
		{
			final RoleId roleId = RoleId.ofRepoId(p_AD_Role_ID);
			final Role role = Services.get(IRoleDAO.class).getById(roleId);
			updateRole(role);
		}
		else
		{
			final ClientId clientId = ClientId.ofRepoIdOrNull(p_AD_Client_ID);
			updateAllRoles(clientId);
		}

		//
		// Reset role related cache (i.e. UserRolePermissions)
		CacheMgt.get().reset(I_AD_Role.Table_Name);

		return MSG_OK;
	}	// doIt

	public static void updateAllRoles()
	{
		final ClientId clientId = null; // all
		updateAllRoles(clientId);
	}

	public static void updateAllRoles(final ClientId clientId)
	{
		Services.get(IRoleDAO.class).retrieveAllRolesWithAutoMaintenance()
				.stream()
				.filter(role -> isRoleMatchingClientId(role, clientId))
				.forEach(role -> updateRole(role));
	}

	private static boolean isRoleMatchingClientId(final Role role, final ClientId clientId)
	{
		if (clientId == null)
		{
			return true;
		}

		return ClientId.equals(role.getClientId(), clientId);
	}

	/**
	 * Update Role
	 * 
	 * @param role role
	 */
	private static void updateRole(final Role role)
	{
		final String result = Services.get(IUserRolePermissionsDAO.class).updateAccessRecords(role);
		Loggables.addLog("Role access updated: " + role.getName() + ": " + result);
	}	// updateRole
}
