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

import org.adempiere.util.Services;
import org.compiere.model.PO;
import org.compiere.model.Query;
import org.compiere.model.X_AD_WF_NodeNext;

import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;

/**
 *	Workflow Node Next - Transition
 *
 * 	@author 	Jorg Janke
 * 	@version 	$Id: MWFNodeNext.java,v 1.3 2006/10/06 00:42:24 jjanke Exp $
 */
public class MWFNodeNext extends X_AD_WF_NodeNext
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7925133581626319200L;

	/**
	 * 	Standard Costructor
	 *	@param ctx context
	 *	@param AD_WF_NodeNext_ID id
	 *	@param trxName transaction
	 */
	public MWFNodeNext (Properties ctx, int AD_WF_NodeNext_ID, String trxName)
	{
		super (ctx, AD_WF_NodeNext_ID, trxName);
		if (AD_WF_NodeNext_ID == 0)
		{
		//	setAD_WF_Next_ID (0);
		//	setAD_WF_Node_ID (0);
			setEntityType (ENTITYTYPE_UserMaintained);	// U
			setIsStdUserWorkflow (false);
			setSeqNo (10);	// 10
		}
	}	//	MWFNodeNext
	
	/**
	 * 	Default Constructor
	 * 	@param ctx context
	 * 	@param rs result set to load info from
	 *	@param trxName transaction
	 */
	public MWFNodeNext (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MWFNodeNext

	/**
	 * 	Parent constructor
	 * 	@param parent patent
	 * 	@param AD_WF_Next_ID Next
	 */
	public MWFNodeNext (MWFNode parent, int AD_WF_Next_ID)
	{
		this(parent.getCtx(), 0, parent.get_TrxName());
		setClientOrg(parent);
		setAD_WF_Node_ID(parent.getAD_WF_Node_ID());
		setAD_WF_Next_ID(AD_WF_Next_ID);
	}	//	MWFNodeNext

	/** Transition Conditions			*/
	private MWFNextCondition[] 	m_conditions = null;
	/**	From (Split Eleemnt) is AND		*/
	public Boolean				m_fromSplitAnd = null;
	/**	To (Join Element) is AND		*/
	public Boolean				m_toJoinAnd = null;
	
	/**
	 * 	Set Client Org
	 *	@param AD_Client_ID client
	 *	@param AD_Org_ID org
	 */
	@Override
	public void setClientOrg (int AD_Client_ID, int AD_Org_ID)
	{
		super.setClientOrg (AD_Client_ID, AD_Org_ID);
	}	//	setClientOrg
	
	/**
	 * 	String Representation
	 *	@return info
	 */
	@Override
	public String toString ()
	{
		StringBuffer sb = new StringBuffer ("MWFNodeNext[");
		sb.append(getSeqNo())
			.append(":Node=").append(getAD_WF_Node_ID()).append("->Next=").append(getAD_WF_Next_ID());
		if (m_conditions != null)
			sb.append(",#").append(m_conditions.length);
		if (getDescription() != null && getDescription().length() > 0)
			sb.append(",").append(getDescription());
		sb.append ("]");
		return sb.toString ();
	}	//	toString
	
	
	/*************************************************************************
	 * 	Get Conditions
	 * 	@param requery true if requery
	 *	@return Array of Conditions
	 */
	private MWFNextCondition[] getConditions(boolean requery)
	{
		if (!requery && m_conditions != null)
			return m_conditions;
		//
		final String whereClause = MWFNextCondition.COLUMNNAME_AD_WF_NodeNext_ID+"=?";
		List<MWFNextCondition> list = new Query(getCtx(), MWFNextCondition.Table_Name, whereClause, get_TrxName())
				.setParameters(new Object[]{getAD_WF_NodeNext_ID()})
				.setOnlyActiveRecords(true)
				.setOrderBy(MWFNextCondition.COLUMNNAME_SeqNo)
				.list(MWFNextCondition.class);
		m_conditions = new MWFNextCondition[list.size()];
		list.toArray (m_conditions);
		return m_conditions;
	}	//	getConditions

	/**
	 * 	Unconditional
	 *	@return true if no conditions
	 */
	public boolean isUnconditional()
	{
		return !isStdUserWorkflow() && getConditions(false).length == 0;
	}	//	isUnconditional
	
	/**
	 * 	Is this a Valid Transition For ..
	 * 	@param activity activity
	 *	@return true if valid
	 */
	public boolean isValidFor (MWFActivity activity)
	{
		if (isStdUserWorkflow())
		{
			final PO po = activity.getPO();
			final IDocument da = Services.get(IDocumentBL.class).getDocumentOrNull(po);
			if (da != null)
			{
				String docStatus = da.getDocStatus();
				String docAction = da.getDocAction();
				if (!IDocument.ACTION_Complete.equals(docAction)
					|| IDocument.STATUS_Completed.equals(docStatus)
					|| IDocument.STATUS_WaitingConfirmation.equals(docStatus)
					|| IDocument.STATUS_WaitingPayment.equals(docStatus)
					|| IDocument.STATUS_Voided.equals(docStatus)
					|| IDocument.STATUS_Closed.equals(docStatus)
					|| IDocument.STATUS_Reversed.equals(docStatus) )
					/*
					|| DocAction.ACTION_Complete.equals(docAction)	
					|| DocAction.ACTION_ReActivate.equals(docAction)	
					|| DocAction.ACTION_None.equals(docAction)
					|| DocAction.ACTION_Post.equals(docAction)
					|| DocAction.ACTION_Unlock.equals(docAction)
					|| DocAction.ACTION_Invalidate.equals(docAction)	) */
				{
					log.debug("isValidFor =NO= StdUserWF - Status=" + docStatus + " - Action=" + docAction);
					return false;
				}
			}
		}
		//	No Conditions
		if (getConditions(false).length == 0)
		{
			log.debug("#0 " + toString());
			return true;
		}
		//	First condition always AND
		boolean ok = m_conditions[0].evaluate(activity);
		for (int i = 1; i < m_conditions.length; i++)
		{
			if (m_conditions[i].isOr())
				ok = ok || m_conditions[i].evaluate(activity);
			else
				ok = ok && m_conditions[i].evaluate(activity);
		}	//	for all conditions
		log.debug("isValidFor (" + ok + ") " + toString());
		return ok;
	}	//	isValidFor
	
	
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
	 * 	Set by MWFNode.loadNodes
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
		if (m_toJoinAnd == null && getAD_WF_Next_ID() != 0)
		{
			MWFNode next = MWFNode.get(getCtx(), getAD_WF_Next_ID());
			setToJoinAnd(MWFNode.JOINELEMENT_AND.equals(next.getJoinElement()));
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

}	//	MWFNodeNext
