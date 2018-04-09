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
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.compiere.model.Query;
import org.compiere.model.X_AD_WF_EventAudit;
import org.compiere.util.Env;

/**
 *	Workflow Event Audit
 *	
 *  @author Jorg Janke
 *  @version $Id: MWFEventAudit.java,v 1.3 2006/07/30 00:51:06 jjanke Exp $
 * 
 * @author Teo Sarca, SC ARHIPAC SERVICE SRL
 * 			<li>BF [ 1801842 ] DB connection fix & improvements for concurrent threads
 * 			<li>BF [ 1943723 ] WF Activity History is not translated
 */
public class MWFEventAudit extends X_AD_WF_EventAudit
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3760514881823970823L;


	/**
	 * Get Event Audit for node
	 * @param ctx context
	 * @param AD_WF_Process_ID process
	 * @param AD_WF_Node_ID optional node
	 * @return event audit or null
	 * @deprecated Deprecated since 3.4.0. Use instead {@link #get(Properties, int, int, String)}
	 */
	@Deprecated
	public static MWFEventAudit[] get (Properties ctx, int AD_WF_Process_ID, int AD_WF_Node_ID)
	{
		return get(ctx, AD_WF_Process_ID, AD_WF_Node_ID, null);
	}
	
	/**
	 * Get Event Audit for node
	 * @param ctx context
	 * @param AD_WF_Process_ID process
	 * @param AD_WF_Node_ID optional node
	 * @param trxName
	 * @return event audit or null
	 */
	public static MWFEventAudit[] get (Properties ctx, int AD_WF_Process_ID, int AD_WF_Node_ID, String trxName)
	{
		ArrayList<Object> params = new ArrayList<>();
		StringBuffer whereClause = new StringBuffer("AD_WF_Process_ID=?"); 
		params.add(AD_WF_Process_ID);
		if (AD_WF_Node_ID > 0)
		{
			whereClause.append(" AND AD_WF_Node_ID=?");
			params.add(AD_WF_Node_ID);
		}
		List<MWFEventAudit> list = new Query(ctx, Table_Name, whereClause.toString(), trxName)
					.setParameters(params)
					.setOrderBy(COLUMNNAME_AD_WF_EventAudit_ID)
					.list(MWFEventAudit.class);
		//
		MWFEventAudit[] retValue = new MWFEventAudit[list.size()];
		list.toArray (retValue);
		return retValue;
	}	//	get

	/**
	 * Get Event Audit for node
	 * @param ctx context
	 * @param AD_WF_Process_ID process
	 * @return event audit or null
	 * @deprecated Deprecated since 3.4.0. Use instead {@link #get(Properties, int, String)}
	 */
	@Deprecated
	public static MWFEventAudit[] get (Properties ctx, int AD_WF_Process_ID)
	{
		return get(ctx, AD_WF_Process_ID, null);
	}
	
	/**
	 * Get Event Audit for node
	 * @param ctx context
	 * @param AD_WF_Process_ID process
	 * @param trxName
	 * @return event audit or null
	 */
	public static MWFEventAudit[] get (Properties ctx, int AD_WF_Process_ID, String trxName)
	{
		return get(ctx, AD_WF_Process_ID, 0, trxName);
	}	//	get
	
	
	/**************************************************************************
	 * 	Standard Constructor
	 * 	@param ctx context
	 *	@param AD_WF_EventAudit_ID id
	 * 	@param trxName transaction
	 */
	public MWFEventAudit (Properties ctx, int AD_WF_EventAudit_ID, String trxName)
	{
		super (ctx, AD_WF_EventAudit_ID, trxName);
	}	//	MWFEventAudit

	/**
	 * 	Load Cosntructors
	 *	@param ctx context
	 *	@param rs result set
	 * 	@param trxName transaction
	 */
	public MWFEventAudit (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MWFEventAudit

	/**
	 * 	Activity Constructor
	 *	@param activity activity
	 */
	public MWFEventAudit (MWFActivity activity)
	{
		super (activity.getCtx(), 0, activity.get_TrxName());
		setClientOrg(activity); // metas: tsa: 01799: use activity's client and org
		setAD_WF_Process_ID (activity.getAD_WF_Process_ID());
		setAD_WF_Node_ID (activity.getAD_WF_Node_ID());
		setAD_Table_ID (activity.getAD_Table_ID());
		setRecord_ID (activity.getRecord_ID());
		//
		setAD_WF_Responsible_ID (activity.getAD_WF_Responsible_ID());
		setAD_User_ID(activity.getAD_User_ID());
		//
		setWFState (activity.getWFState());
		setEventType (EVENTTYPE_ProcessCreated);
		setElapsedTimeMS (Env.ZERO);
		//
		MWFNode node = activity.getNode();
		if (node != null && node.get_ID() != 0)
		{
			String action = node.getAction();
			if (MWFNode.ACTION_SetVariable.equals(action)
				|| MWFNode.ACTION_UserChoice.equals(action))
			{
				setAttributeName(node.getAttributeName());
				setOldValue(String.valueOf(activity.getAttributeValue()));
				if (MWFNode.ACTION_SetVariable.equals(action))
					setNewValue(node.getAttributeValue());
			}
		}
	}	//	MWFEventAudit
	
	/**
	 * 	Get Node Name
	 *	@return node name
	 */
	public String getNodeName()
	{
		MWFNode node = MWFNode.get(getCtx(), getAD_WF_Node_ID());
		if (node.get_ID() == 0)
			return "?";
		return node.get_Translation(MWFNode.COLUMNNAME_Name);
	}	//	getNodeName
	
	
}	//	MWFEventAudit
