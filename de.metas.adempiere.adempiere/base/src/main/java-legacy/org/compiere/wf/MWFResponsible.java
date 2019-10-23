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
package org.compiere.wf;

import java.sql.ResultSet;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_AD_Role;
import org.compiere.model.X_AD_WF_Responsible;

import de.metas.cache.CCache;


/**
 *	Workflow Resoinsible
 *	
 *  @author Jorg Janke
 *  @version $Id: MWFResponsible.java,v 1.3 2006/07/30 00:51:05 jjanke Exp $
 */
public class MWFResponsible extends X_AD_WF_Responsible
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8662580480797818563L;


	/**
	 * 	Get WF Responsible from Cache
	 *	@param ctx context
	 *	@param AD_WF_Responsible_ID id
	 *	@return MWFResponsible
	 */
	public static MWFResponsible get (Properties ctx, int AD_WF_Responsible_ID)
	{
		Integer key = new Integer (AD_WF_Responsible_ID);
		MWFResponsible retValue = s_cache.get (key);
		if (retValue != null)
			return retValue;
		retValue = new MWFResponsible (ctx, AD_WF_Responsible_ID, null);
		if (retValue.get_ID () != 0)
			s_cache.put (key, retValue);
		return retValue;
	} //	get

	/**	Cache						*/
	private static CCache<Integer,MWFResponsible>	s_cache	= new CCache<Integer,MWFResponsible>("AD_WF_Responsible", 10);

	
	/**************************************************************************
	 * 	Standard Constructor
	 *	@param ctx context
	 *	@param AD_WF_Responsible_ID id
	 * 	@param trxName transaction
	 */
	public MWFResponsible (Properties ctx, int AD_WF_Responsible_ID, String trxName)
	{
		super (ctx, AD_WF_Responsible_ID, trxName);
	}	//	MWFResponsible

	/**
	 * 	Load Constructor
	 *	@param ctx context
	 *	@param rs result set
	 * 	@param trxName transaction
	 */
	public MWFResponsible (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MWFResponsible
	
	/**
	 * 	Invoker - return true if no user and no role 
	 *	@return true if invoker
	 */
	public boolean isInvoker()
	{
		return getAD_User_ID() == 0 && getAD_Role_ID() == 0;
	}	//	isInvoker
	
	/**
	 * 	Is Role Responsible
	 *	@return true if role
	 */
	public boolean isRole()
	{
		return RESPONSIBLETYPE_Role.equals(getResponsibleType()) 
			&& getAD_Role_ID() != 0;
	}	//	isRole

	/**
	 * 	Is Role Responsible
	 *	@return true if role
	 */
	public I_AD_Role getRole()
	{
		if (!isRole())
			return null;
		return getAD_Role();
	}	//	getRole

	/**
	 * 	Is Human Responsible
	 *	@return true if human
	 */
	public boolean isHuman()
	{
		return RESPONSIBLETYPE_Human.equals(getResponsibleType()) 
			&& getAD_User_ID() != 0;
	}	//	isHuman
	
	/**
	 * 	Is Org Responsible
	 *	@return true if Org
	 */
	public boolean isOrganization()
	{
		return RESPONSIBLETYPE_Organization.equals(getResponsibleType()) 
			&& getAD_Org_ID() != 0;
	}	//	isOrg
	
	/**
	 * 	Before Save
	 *	@param newRecord new
	 *	@return tre if can be saved
	 */
	@Override
	protected boolean beforeSave (boolean newRecord)
	{
	//	if (RESPONSIBLETYPE_Human.equals(getResponsibleType()) && getAD_User_ID() == 0)
	//		return true;
		if (RESPONSIBLETYPE_Role.equals(getResponsibleType())
				&& getAD_Role_ID() <= 0
				&& getAD_Client_ID() > 0)
		{
			throw new AdempiereException("@RequiredEnter@ @AD_Role_ID@");
		}
		//	User not used
		if (!RESPONSIBLETYPE_Human.equals(getResponsibleType()) && getAD_User_ID() > 0)
			setAD_User_ID(0);
		
		//	Role not used
		if (!RESPONSIBLETYPE_Role.equals(getResponsibleType()) && getAD_Role_ID() > 0)
			setAD_Role_ID(0);
		
		return true;
	}	//	beforeSave
	
	/**
	 * 	String Representation
	 *	@return info
	 */
	@Override
	public String toString ()
	{
		StringBuffer sb = new StringBuffer("MWFResponsible[");
		sb.append (get_ID())
			.append("-").append(getName())
			.append(",Type=").append(getResponsibleType());
		if (getAD_User_ID() != 0)
			sb.append(",AD_User_ID=").append(getAD_User_ID());
		if (getAD_Role_ID() != 0)
			sb.append(",AD_Role_ID=").append(getAD_Role_ID());
		sb.append ("]");
		return sb.toString ();
	}	//	toString
	
}	//	MWFResponsible
