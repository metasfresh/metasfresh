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

import java.sql.ResultSet;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.service.IOrgDAO;
import org.adempiere.util.LegacyAdapters;

import de.metas.util.Services;

/**
 *	Organization Info Model
 *	
 *  @author Jorg Janke
 *  @version $Id: MOrgInfo.java,v 1.3 2006/07/30 00:58:37 jjanke Exp $
 *  
 *  @author Teo Sarca, www.arhipac.ro
 *  		<li>BF [ 2107083 ] Caching of MOrgInfo issue
 */
public class MOrgInfo extends X_AD_OrgInfo
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2079223595471129816L;


	/**
	 * Load Constructor
	 *
	 * @param ctx context
	 * @param AD_Org_ID id
	 * @return Org Info
	 * @deprecated Please use {@link IOrgDAO#retrieveOrgInfo(Properties, int, String)}
	 */
	@Deprecated
	public static MOrgInfo get (Properties ctx, int AD_Org_ID)
	{
		return get(ctx, AD_Org_ID, ITrx.TRXNAME_None);
	}

	/**
	 * 
	 * @param ctx
	 * @param AD_Org_ID
	 * @param trxName
	 * @return org info or null
	 * @deprecated Please use {@link IOrgDAO#retrieveOrgInfo(Properties, int, String)}
	 */
	public static MOrgInfo get (Properties ctx, int AD_Org_ID, String trxName)
	{
		final I_AD_OrgInfo orgInfo = Services.get(IOrgDAO.class).retrieveOrgInfo(ctx, AD_Org_ID, trxName);
		return LegacyAdapters.convertToPO(orgInfo);
	}	//	get
	
	/**************************************************************************
	 * 	Load Constructor
	 *	@param ctx context
	 *	@param rs result set
	 *	@param trxName transaction
	 */
	public MOrgInfo (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MOrgInfo

	/**
	 * 	Organization constructor
	 *	@param org org
	 */
	public MOrgInfo (MOrg org)
	{
		super(org.getCtx(), 0, org.get_TrxName());
		setClientOrg(org);
		setDUNS ("?");
		setTaxID ("?");
	}	//	MOrgInfo
	
}	//	MOrgInfo
