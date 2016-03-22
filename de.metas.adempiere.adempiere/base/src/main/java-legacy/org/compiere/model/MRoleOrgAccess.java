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
package org.compiere.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.slf4j.Logger;
import de.metas.logging.LogManager;

import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.util.DB;

/**
 * Role Org Access Model
 *
 * @author Jorg Janke
 * @version $Id: MRoleOrgAccess.java,v 1.3 2006/07/30 00:58:38 jjanke Exp $
 */
public class MRoleOrgAccess extends X_AD_Role_OrgAccess
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4664267788838719168L;

	/**
	 * Get Organizational Access of Org
	 *
	 * @param ctx context
	 * @param AD_Org_ID role
	 * @return array of Role Org Access
	 */
	public static MRoleOrgAccess[] getOfOrg(Properties ctx, int AD_Org_ID)
	{
		return get(ctx, "SELECT * FROM AD_Role_OrgAccess WHERE AD_Org_ID=?", AD_Org_ID);
	}	// getOfOrg

	/**
	 * Get Organizational Info
	 *
	 * @param ctx context
	 * @param sql sql command
	 * @param id id
	 * @return array of Role Org Access
	 */
	private static MRoleOrgAccess[] get(Properties ctx, String sql, int id)
	{
		List<MRoleOrgAccess> list = new ArrayList<MRoleOrgAccess>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			while (rs.next())
				list.add(new MRoleOrgAccess(ctx, rs, null));
		}
		catch (Exception e)
		{
			s_log.error("get", e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		MRoleOrgAccess[] retValue = new MRoleOrgAccess[list.size()];
		list.toArray(retValue);
		return retValue;
	}	// get

	/** Static Logger */
	private static Logger s_log = LogManager.getLogger(MRoleOrgAccess.class);

	/**************************************************************************
	 * Load Constructor
	 *
	 * @param ctx context
	 * @param rs result set
	 * @param trxName transaction
	 */
	public MRoleOrgAccess(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	// MRoleOrgAccess

	/**
	 * Persistency Constructor
	 *
	 * @param ctx context
	 * @param ignored ignored
	 * @param trxName transaction
	 */
	public MRoleOrgAccess(Properties ctx, int ignored, String trxName)
	{
		super(ctx, 0, trxName);
		if (ignored != 0)
			throw new IllegalArgumentException("Multi-Key");
		setIsReadOnly(false);
	}	// MRoleOrgAccess

	/**
	 * Organization Constructor
	 *
	 * @param org org
	 * @param AD_Role_ID role
	 */
	public MRoleOrgAccess(final I_AD_Org org, final int AD_Role_ID)
	{
		super(InterfaceWrapperHelper.getCtx(org), 0, InterfaceWrapperHelper.getTrxName(org));
		setClientOrgFromModel(org);
		setAD_Role_ID(AD_Role_ID);
	}	// MRoleOrgAccess

	/**
	 * Role Constructor
	 *
	 * @param role role
	 * @param AD_Org_ID org
	 */
	public MRoleOrgAccess(MRole role, int AD_Org_ID)
	{
		this(role.getCtx(), 0, role.get_TrxName());
		setClientOrg(role.getAD_Client_ID(), AD_Org_ID);
		setAD_Role_ID(role.getAD_Role_ID());
	}	// MRoleOrgAccess

	/**
	 * String Representation
	 *
	 * @return info
	 */
	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder("MRoleOrgAccess[");
		sb.append("AD_Role_ID=").append(getAD_Role_ID())
				.append(",AD_Client_ID=").append(getAD_Client_ID())
				.append(",AD_Org_ID=").append(getAD_Org_ID())
				.append(",RO=").append(isReadOnly());
		sb.append("]");
		return sb.toString();
	}
}	// MRoleOrgAccess
