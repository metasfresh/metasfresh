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

import org.adempiere.model.InterfaceWrapperHelper;


/**
 *	Form Access Model
 *	
 *  @author Jorg Janke
 *  @version $Id: MFormAccess.java,v 1.3 2006/07/30 00:51:03 jjanke Exp $
 */
public class MFormAccess extends X_AD_Form_Access
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7818255846843514899L;

	/**
	 * 	Standard Constructor
	 *	@param ctx context
	 *	@param ignored id=0
	 *	@param trxName trx
	 */
	public MFormAccess (Properties ctx, int ignored, String trxName)
	{
		super(ctx, 0, trxName);
		if (ignored != 0)
			throw new IllegalArgumentException("Multi-Key");
		else
		{
		//	setAD_Form_ID (0);
		//	setAD_Role_ID (0);
			setIsReadWrite (true);
		}
	}	//	MFormAccess

	/**
	 * 	Load Constructor
	 *	@param ctx context
	 *	@param rs result set
	 *	@param trxName transaction
	 */
	public MFormAccess (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MFormAccess
	
	/**
	 * 	Parent Constructor
	 *	@param parent parent
	 *	@param AD_Role_ID role id
	 */
	public MFormAccess(final I_AD_Form parent, final I_AD_Role role)
	{
		super(InterfaceWrapperHelper.getCtx(parent), 0, InterfaceWrapperHelper.getTrxName(parent));

		setClientOrgFromModel(role);

		setAD_Form_ID(parent.getAD_Form_ID());
		setAD_Role_ID(role.getAD_Role_ID());
	}	//	MFormAccess

	
}	//	MFormAccess
