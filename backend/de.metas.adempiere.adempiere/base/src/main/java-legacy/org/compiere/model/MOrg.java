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

import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.organization.OrgInfoUpdateRequest;
import de.metas.util.Services;
import org.adempiere.util.LegacyAdapters;
import org.compiere.util.DB;

import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

/**
 *	Organization Model
 *
 *  @author Jorg Janke
 *  @version $Id: MOrg.java,v 1.3 2006/07/30 00:58:04 jjanke Exp $
 */
public class MOrg extends X_AD_Org
{
	/**
	 *
	 */
	private static final long serialVersionUID = -5604686137606338725L;


	/**
	 * 	Get Active Organizations Of Client
	 *	@param po persistent object
	 *	@return array of orgs
	 */
	public static MOrg[] getOfClient (PO po)
	{
		return getOfClient(po.getCtx(), po.getAD_Client_ID());
	}
	// metas
	public static MOrg[] getOfClient(Properties ctx, int AD_Client_ID)
	{
		final List<I_AD_Org> clientOrgs = Services.get(IOrgDAO.class).retrieveClientOrgs(ctx, AD_Client_ID);
		return LegacyAdapters.convertToPOArray(clientOrgs, MOrg.class);
	}	//	getOfClient

	@Deprecated
	public static MOrg get (Properties ctx, int AD_Org_ID)
	{
		if (AD_Org_ID < 0)
		{
			return null;
		}

		final I_AD_Org org = Services.get(IOrgDAO.class).retrieveOrg(ctx, AD_Org_ID);
		return LegacyAdapters.convertToPO(org);
	}	//	get

	/**************************************************************************
	 * 	Standard Constructor
	 *	@param ctx context
	 *	@param AD_Org_ID id
	 *	@param trxName transaction
	 */
	public MOrg (Properties ctx, int AD_Org_ID, String trxName)
	{
		super(ctx, AD_Org_ID, trxName);
		if (is_new())
		{
		//	setValue (null);
		//	setName (null);
			setIsSummary (false);
		}
	}	//	MOrg

	/**
	 * 	Load Constructor
	 *	@param ctx context
	 *	@param rs result set
	 *	@param trxName transaction
	 */
	public MOrg (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MOrg

	/**
	 * 	Parent Constructor
	 *	@param client client
	 *	@param name name
	 */
	public MOrg (MClient client, String name)
	{
		this (client.getCtx(), -1, client.get_TrxName());
		setAD_Client_ID (client.getAD_Client_ID());
		setValue (name);
		setName (name);
	}	//	MOrg

	/**	Linked Business Partner			*/
	private Integer 	m_linkedBPartner = null;

	@Override
	protected boolean afterSave (boolean newRecord, boolean success)
	{
		if (!success)
		{
			return success;
		}
		if (newRecord)
		{
			//	Info
			Services.get(IOrgDAO.class).createOrUpdateOrgInfo(OrgInfoUpdateRequest.builder()
					.orgId(OrgId.ofRepoId(getAD_Org_ID()))
					.build());

			//	TreeNode
			// insert_Tree(MTree_Base.TREETYPE_Organization);
		}

		return true;
	}	//	afterSave

	/**
	 * 	Get Linked BPartner
	 *	@return C_BPartner_ID
	 */
	public int getLinkedC_BPartner_ID(String trxName)
	{
		if (m_linkedBPartner == null)
		{
			int C_BPartner_ID = DB.getSQLValue(trxName,
				"SELECT C_BPartner_ID FROM C_BPartner WHERE AD_OrgBP_ID=?",
				getAD_Org_ID());
			if (C_BPartner_ID < 0)
			{
				C_BPartner_ID = 0;
			}
			m_linkedBPartner = new Integer (C_BPartner_ID);
		}
		return m_linkedBPartner.intValue();
	}	//	getLinkedC_BPartner_ID

}	//	MOrg
