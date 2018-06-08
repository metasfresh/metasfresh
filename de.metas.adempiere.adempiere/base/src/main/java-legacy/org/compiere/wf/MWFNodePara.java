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
import java.util.List;
import java.util.Properties;

import org.compiere.model.Query;
import org.compiere.model.X_AD_WF_Node_Para;


/**
 *	Workflow Node Process Parameter Model
 *	
 *  @author Jorg Janke
 *  @version $Id: MWFNodePara.java,v 1.2 2006/07/30 00:51:05 jjanke Exp $
 */
public class MWFNodePara extends X_AD_WF_Node_Para
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4132254339643230238L;


	/**
	 * 	Get Parameters for a node
	 *	@param ctx context
	 *	@param AD_WF_Node_ID node
	 *	@return array of parameters
	 */
	public static MWFNodePara[] getParameters (Properties ctx, int AD_WF_Node_ID)
	{
		
		List<MWFNodePara> list = new Query(ctx, Table_Name, "AD_WF_Node_ID=?", null)
			.setParameters(new Object[]{AD_WF_Node_ID})
			.list();
		MWFNodePara[] retValue = new MWFNodePara[list.size ()];
		list.toArray (retValue);
		return retValue;
	}	//	getParameters
	
	
	/**************************************************************************
	 * 	Standard Constructor
	 *	@param ctx context
	 *	@param id id
	 *	@param trxName transaction
	 */
	public MWFNodePara (Properties ctx, int id, String trxName)
	{
		super (ctx, id, trxName);
	}	//	MWFNodePara

	/**
	 * 	Load Constructor
	 *	@param ctx context
	 *	@param rs result set
	 *	@param trxName transaction
	 */
	public MWFNodePara (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MWFNodePara
	
	/**
	 * 	Get Attribute Name.
	 * 	If not set - retrieve it
	 *	@return attribute name
	 */
	@Override
	public String getAttributeName ()
	{
		String an = super.getAttributeName ();
		if (an == null || an.length() == 0 && getAD_Process_Para_ID() > 0)
		{
			an = getAD_Process_Para().getColumnName();
			setAttributeName(an);
			save();
		}
		return an;
	}	//	getAttributeName
	
	/**
	 * 	Get Display Type
	 *	@return display type
	 */
	public int getDisplayType()
	{
		return getAD_Process_Para().getAD_Reference_ID();
	}	//	getDisplayType

	/**
	 * 	Is Mandatory
	 *	@return true if mandatory
	 */
	public boolean isMandatory()
	{
		return getAD_Process_Para().isMandatory();
	}	//	isMandatory
	
	/**
	 * 	Set AD_Process_Para_ID
	 *	@param AD_Process_Para_ID id
	 */
	@Override
	public void setAD_Process_Para_ID (int AD_Process_Para_ID)
	{
		super.setAD_Process_Para_ID (AD_Process_Para_ID);
		setAttributeName(null);
	}
	
}	//	MWFNodePara
