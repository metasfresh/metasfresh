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

import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_AD_Role;
import org.compiere.model.I_AD_Workflow;
import org.compiere.model.X_AD_Workflow_Access;


/**
 *	Worflow Access Model
 *	
 *  @author Jorg Janke
 *  @version $Id: MWorkflowAccess.java,v 1.3 2006/07/30 00:51:05 jjanke Exp $
 */
public class MWorkflowAccess extends X_AD_Workflow_Access
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2598861248782340850L;

	/**
	 * 	Standard Constructor
	 *	@param ctx context
	 *	@param ignored -
	 * 	@param trxName transaction
	 */
	public MWorkflowAccess (Properties ctx, int ignored, String trxName)
	{
		super(ctx, 0, trxName);
		if (ignored != 0)
			throw new IllegalArgumentException("Multi-Key");
		else
		{
		//	setAD_Role_ID (0);
		//	setAD_Workflow_ID (0);
			setIsReadWrite (true);
		}
	}	//	MWorkflowAccess

	/**
	 * 	Load Constructor
	 *	@param ctx context
	 *	@param rs result set
	 * 	@param trxName transaction
	 */
	public MWorkflowAccess (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MWorkflowAccess
	
	/**
	 * 	Parent Constructor
	 *	@param parent parent
	 *	@param AD_Role_ID role id
	 */
	public MWorkflowAccess(final I_AD_Workflow parent, final I_AD_Role role)
	{
		super(InterfaceWrapperHelper.getCtx(parent), 0, InterfaceWrapperHelper.getTrxName(parent));

		setClientOrgFromModel(role);

		setAD_Workflow_ID(parent.getAD_Workflow_ID());
		setAD_Role_ID(role.getAD_Role_ID());
	}	//	MWorkflowAccess

}	//	MWorkflowAccess
