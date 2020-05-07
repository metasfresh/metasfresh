/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
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
 * Copyright (C) 2003-2007 e-Evolution,SC. All Rights Reserved.               *
 * Contributor(s): Victor Perez www.e-evolution.com                           *
 *                 Teo Sarca, www.arhipac.ro                                  *
 *****************************************************************************/
package org.eevolution.model;

/*
 * #%L
 * de.metas.adempiere.libero.libero
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.util.DB;

/**
 * PP Order Workflow Node Next - Transition
 *
 * @author Victor Perez www.e-evolution.com     
 * @author Teo Sarca, http://www.arhipac.ro
 */
public class MPPOrderNodeNext extends X_PP_Order_NodeNext
{
	private static final long serialVersionUID = 1L;

	/**
	 * 	Standard Costructor
	 *	@param ctx context
	 *	@param PP_OrderNodeNext_ID id
	 *	@param trxName transaction
	 */
	public MPPOrderNodeNext (Properties ctx, int PP_OrderNodeNext_ID, String trxName)
	{
		super (ctx, PP_OrderNodeNext_ID, trxName);
		if (PP_OrderNodeNext_ID == 0)
		{
		//	setPP_OrderNext_ID (0);
		//	setPP_OrderNode_ID (0);
			setEntityType (ENTITYTYPE_UserMaintained);	// U
			setIsStdUserWorkflow (false);
			setSeqNo (10);	// 10
		}
	}	//	MPPOrderNodeNext
	
	/**
	 * 	Default Constructor
	 * 	@param ctx context
	 * 	@param rs result set to load info from
	 *	@param trxName transaction
	 */
	public MPPOrderNodeNext (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MPPOrderNodeNext

//	/**
//	 * 	Parent constructor
//	 * 	@param parent patent
//	 * 	@param PP_OrderNext_ID Next
//	 */
//	public MPPOrderNodeNext (MPPOrderNode parent, int PP_Order_Next_ID)
//	{
//		this(parent.getCtx(), 0, parent.get_TrxName());
//		setClientOrg(parent);
//		setPP_Order_ID(parent.getPP_Order_ID());
//		setPP_Order_Node_ID(parent.get_ID());
//		setPP_Order_Next_ID(PP_Order_Next_ID);
//	}	//	MPPOrderNodeNext
//
//	/**
//	 * Peer constructor
//	 * @param wfNodeNext
//	 * @param parent
//	 * @param trxName
//	 */
//	public MPPOrderNodeNext (MWFNodeNext wfNodeNext, MPPOrderNode parent)
//	{
//		this(parent, 0);
//		//
//		setAD_WF_Node_ID(wfNodeNext.getAD_WF_Node_ID());
//		setAD_WF_Next_ID(wfNodeNext.getAD_WF_Next_ID());
//		setDescription(wfNodeNext.getDescription());
//		setEntityType(wfNodeNext.getEntityType());
//		setIsStdUserWorkflow(wfNodeNext.isStdUserWorkflow());
//		setSeqNo(wfNodeNext.getSeqNo());
//		setTransitionCode(wfNodeNext.getTransitionCode());
//	}

	/**	From (Split Eleemnt) is AND		*/
	public Boolean				m_fromSplitAnd = null;
	/**	To (Join Element) is AND		*/
	public Boolean				m_toJoinAnd = null;
	
	@Override
	public String toString()
	{
		StringBuffer sb = new StringBuffer ("MPPOrderNodeNext[");
		sb.append(getSeqNo())
			.append(":Node=").append(getPP_Order_Node_ID()).append("->Next=").append(getPP_Order_Next_ID());
		//if (m_conditions != null)
		//	sb.append(",#").append(m_conditions.length);
		if (getDescription() != null && getDescription().length() > 0)
			sb.append(",").append(getDescription());
		sb.append ("]");
		return sb.toString ();
	}	//	toString
	
	/**
	 * 	Split Element is AND
	 * 	@return Returns the from Split And.
	 */
	public boolean isFromSplitAnd()
	{
		if (m_fromSplitAnd != null)
			return m_fromSplitAnd.booleanValue();
		return false;
	}	//	getFromSplitAnd

	/**
	 * 	Split Element is AND.
	 * 	Set by MPPOrderNode.loadNodes
	 *	@param fromSplitAnd The from Split And
	 */
	public void setFromSplitAnd (boolean fromSplitAnd)
	{
		m_fromSplitAnd = new Boolean(fromSplitAnd);
	}	//	setFromSplitAnd

	/**
	 * 	Join Element is AND
	 *	@return Returns the to Join And.
	 */
	public boolean isToJoinAnd ()
	{
		if (m_toJoinAnd == null && getPP_Order_Next_ID() != 0)
		{
			MPPOrderNode next = MPPOrderNode.get(getCtx(), getPP_Order_Next_ID(), get_TrxName());
			setToJoinAnd(MPPOrderNode.JOINELEMENT_AND.equals(next.getJoinElement()));
		}
		if (m_toJoinAnd != null)
			return m_toJoinAnd.booleanValue();
		return false;
	}	//	getToJoinAnd

	/**
	 * 	Join Element is AND.
	 *	@param toJoinAnd The to Join And to set.
	 */
	private void setToJoinAnd (boolean toJoinAnd)
	{
		m_toJoinAnd = new Boolean(toJoinAnd);
	}	//	setToJoinAnd

	public void setPP_Order_Next_ID()
	{
		final String sql = "SELECT PP_Order_Node_ID FROM PP_Order_Node "
							+ " WHERE PP_Order_ID=? AND AD_WF_Node_ID=? AND AD_Client_ID=?";
		int id = DB.getSQLValueEx(get_TrxName(), sql, getPP_Order_ID(), getAD_WF_Next_ID(), getAD_Client_ID());
		setPP_Order_Next_ID(id > 0 ? id : 0);
	}
}	//	MPPOrderNodeNext
