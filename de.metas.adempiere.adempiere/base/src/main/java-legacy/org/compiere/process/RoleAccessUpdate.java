/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package org.compiere.process;

import java.util.List;
import java.util.Properties;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.security.IUserRolePermissionsDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.ILoggable;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_Role;
import org.compiere.util.CacheMgt;

import de.metas.process.ProcessInfoParameter;
import de.metas.process.SvrProcess;

/**
 * Update Role Access
 * 
 * @author Jorg Janke
 * @version $Id: RoleAccessUpdate.java,v 1.3 2006/07/30 00:51:02 jjanke Exp $
 */
public class RoleAccessUpdate extends SvrProcess
{
	/** Update Role */
	private int p_AD_Role_ID = 0;
	/** Update Roles of Client */
	private int p_AD_Client_ID = 0;

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
		log.info("AD_Client_ID=" + p_AD_Client_ID + ", AD_Role_ID=" + p_AD_Role_ID);
		//
		if (p_AD_Role_ID > 0)
		{
			final I_AD_Role role = InterfaceWrapperHelper.create(getCtx(), p_AD_Role_ID, I_AD_Role.class, ITrx.TRXNAME_ThreadInherited);
			updateRole(role, this);
		}
		else
		{
			updateAllRoles(getCtx(), this, p_AD_Client_ID);
		}

		//
		// Reset role related cache (i.e. UserRolePermissions)
		CacheMgt.get().reset(I_AD_Role.Table_Name);

		return MSG_OK;
	}	// doIt

	public static void updateAllRoles(final Properties ctx, final ILoggable loggable)
	{
		final int adClientId = -1; // all
		updateAllRoles(ctx, loggable, adClientId);
	}

	public static void updateAllRoles(final Properties ctx, final ILoggable loggable, final int adClientId)
	{
		final IQueryBuilder<I_AD_Role> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_Role.class, ctx, ITrx.TRXNAME_ThreadInherited)
				.addOnlyActiveRecordsFilter();
		if (adClientId > 0)
		{
			queryBuilder.addEqualsFilter(I_AD_Role.COLUMNNAME_AD_Client_ID, adClientId);
		}
		queryBuilder.orderBy()
				.addColumn(I_AD_Role.COLUMNNAME_AD_Client_ID)
				.addColumn(I_AD_Role.COLUMNNAME_Name);
		
		final List<I_AD_Role> roles = queryBuilder.create().list();
		
		for (final I_AD_Role role : roles)
		{
			updateRole(role, loggable);
		}
	}

	/**
	 * Update Role
	 * 
	 * @param role role
	 */
	private static void updateRole(final I_AD_Role role, final ILoggable loggable)
	{
		final String result = Services.get(IUserRolePermissionsDAO.class).updateAccessRecords(role);
		loggable.addLog("Role access updated: " + role.getName() + ": " + result);
	}	// updateRole
}
