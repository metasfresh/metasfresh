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

import java.awt.Point;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.DBException;
import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.util.LegacyAdapters;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_WF_NodeNext;
import org.compiere.model.MColumn;
import org.compiere.model.X_AD_WF_Node;
import org.compiere.util.CCache;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.wf.api.IADWorkflowDAO;

/**
 *	Workflow Node Model
 *
 * 	@author 	Jorg Janke
 * 	@version 	$Id: MWFNode.java,v 1.2 2006/07/30 00:51:05 jjanke Exp $
 * 
 * @author Teo Sarca, www.arhipac.ro
 * 			<li>FR [ 2214883 ] Remove SQL code and Replace for Query
 * 			<li>BF [ 2815732 ] MWFNode.getWorkflow not working in trx
 * 				https://sourceforge.net/tracker/?func=detail&aid=2815732&group_id=176962&atid=879332 
 */
public class MWFNode extends X_AD_WF_Node
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4330589837679937718L;


	/**
	 * 	Get WF Node from Cache
	 *	@param ctx context
	 *	@param AD_WF_Node_ID id
	 *	@return MWFNode
	 */
	public static MWFNode get (Properties ctx, int AD_WF_Node_ID)
	{
		Integer key = new Integer (AD_WF_Node_ID);
		MWFNode retValue = (MWFNode) s_cache.get (key);
		if (retValue != null)
			return retValue;
		retValue = new MWFNode (ctx, AD_WF_Node_ID, null);
		if (retValue.get_ID () != 0)
			s_cache.put (key, retValue);
		return retValue;
	}	//	get

	/**	Cache						*/
	private static CCache<Integer,MWFNode>	s_cache	= new CCache<Integer,MWFNode> (Table_Name, 50);
	
	
	/**************************************************************************
	 * 	Standard Constructor - save to cache
	 *	@param ctx context
	 *	@param AD_WF_Node_ID id
	 *	@param trxName transaction
	 */
	public MWFNode (Properties ctx, int AD_WF_Node_ID, String trxName)
	{
		super (ctx, AD_WF_Node_ID, trxName);
		if (AD_WF_Node_ID == 0)
		{
		//	setAD_WF_Node_ID (0);
		//	setAD_Workflow_ID (0);
		//	setValue (null);
		//	setName (null);
			setAction (ACTION_WaitSleep);
			setCost (Env.ZERO);
			setDuration (0);
			setEntityType (ENTITYTYPE_UserMaintained);	// U
			setIsCentrallyMaintained (true);	// Y
			setJoinElement (JOINELEMENT_XOR);	// X
			setDurationLimit (0);
			setSplitElement (SPLITELEMENT_XOR);	// X
			setWaitingTime (0);
			setXPosition (0);
			setYPosition (0);
		}
		//	Save to Cache
		if (get_ID() != 0)
			s_cache.put (new Integer(getAD_WF_Node_ID()), this);
	}	//	MWFNode

	/**
	 * 	Parent Constructor
	 *	@param wf workflow (parent)
	 *	@param Value value
	 *	@param Name name
	 */
	public MWFNode (MWorkflow wf, String Value, String Name)
	{
		this (wf.getCtx(), 0, wf.get_TrxName());
		setClientOrg(wf);
		setAD_Workflow_ID (wf.getAD_Workflow_ID());
		setValue (Value);
		setName (Name);
		m_durationBaseMS = wf.getDurationBaseSec() * 1000;
	}	//	MWFNode
	
	/**
	 * 	Load Constructor - save to cache
	 * 	@param ctx context
	 * 	@param rs result set to load info from
	 *	@param trxName transaction
	 */
	public MWFNode (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
		loadNext();
		loadTrl();
		//	Save to Cache
		s_cache.put (get_ID(), this);
	}	//	MWFNode

	
	
	/**	Next Modes				*/
	private List<MWFNodeNext>	m_next = new ArrayList<MWFNodeNext>();
	/**	Translated Name			*/
	private String			m_name_trl = null;
	/**	Translated Description	*/
	private String			m_description_trl = null;
	/**	Translated Help			*/
	private String			m_help_trl = null;
	/**	Translation Flag		*/
	private boolean			m_translated = false;
	/**	Column					*/
	private MColumn		m_column = null;
	/**	Process Parameters		*/
	private MWFNodePara[]	m_paras = null;
	/** Duration Base MS		*/
	private long			m_durationBaseMS = -1;
	
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
	 * 	Load Next
	 */
	private void loadNext()
	{
		final List<I_AD_WF_NodeNext> nexts = Services.get(IADWorkflowDAO.class).retrieveNodeNexts(this);
		m_next = LegacyAdapters.convertToPOList(nexts);
		boolean splitAnd = SPLITELEMENT_AND.equals(getSplitElement());
		for (MWFNodeNext next : m_next)
		{
			next.setFromSplitAnd(splitAnd);
		}
		log.debug("#" + m_next.size());
	}	//	loadNext

	/**
	 * 	Load Translation
	 */
	private void loadTrl()
	{
		if (Env.isBaseLanguage(getCtx(), "AD_Workflow") || get_ID() == 0)
			return;
		final String sql = "SELECT Name, Description, Help FROM AD_WF_Node_Trl"
							+" WHERE AD_WF_Node_ID=? AND AD_Language=?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, get_TrxName());
			pstmt.setInt(1, get_ID());
			pstmt.setString(2, Env.getAD_Language(getCtx()));
			rs = pstmt.executeQuery();
			if (rs.next())
			{
				m_name_trl = rs.getString(1);
				m_description_trl = rs.getString(2);
				m_help_trl = rs.getString(3);
				m_translated = true;
			}
		}
		catch (SQLException e)
		{
			//log.error(sql, e);
			throw new DBException(e, sql);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		log.debug("Trl=" + m_translated);
	}	//	loadTrl

	/**
	 * 	Get Number of Next Nodes
	 * 	@return number of next nodes
	 */
	public int getNextNodeCount()
	{
		return m_next.size();
	}	//	getNextNodeCount

	/**
	 * 	Get the transitions
	 * 	@param AD_Client_ID for client
	 * 	@return array of next nodes
	 */
	public MWFNodeNext[] getTransitions(int AD_Client_ID)
	{
		ArrayList<MWFNodeNext> list = new ArrayList<MWFNodeNext>();
		for (int i = 0; i < m_next.size(); i++)
		{
			MWFNodeNext next = m_next.get(i);
			if (next.getAD_Client_ID() == 0 || next.getAD_Client_ID() == AD_Client_ID)
				list.add(next);
		}
		MWFNodeNext[] retValue = new MWFNodeNext [list.size()];
		list.toArray(retValue);
		return retValue;
	}	//	getNextNodes

	
	/**************************************************************************
	 * 	Get Name
	 * 	@param translated translated
	 * 	@return Name
	 */
	public String getName(boolean translated)
	{
		if (translated && m_translated)
			return m_name_trl;
		return getName();
	}	//	getName

	/**
	 * 	Get Description
	 * 	@param translated translated
	 * 	@return Description
	 */
	public String getDescription(boolean translated)
	{
		if (translated && m_translated)
			return m_description_trl;
		return getDescription();
	}	//	getDescription

	/**
	 * 	Get Help
	 * 	@param translated translated
	 * 	@return Name
	 */
	public String getHelp(boolean translated)
	{
		if (translated && m_translated)
			return m_help_trl;
		return getHelp();
	}	//	getHelp

	/**
	 * 	Set Position
	 * 	@param position point
	 */
	public void setPosition (Point position)
	{
		setPosition(position.x, position.y);
	}	//	setPosition

	/**
	 * 	Set Position
	 * 	@param x x
	 * 	@param y y
	 */
	public void setPosition (int x, int y)
	{
		setXPosition(x);
		setYPosition(y);
	}	//	setPosition

	/**
	 * 	Get Position
	 * 	@return position point
	 */
	public Point getPosition ()
	{
		return new Point (getXPosition(), getYPosition());
	}	//	getPosition

	/**
	 * 	Get Action Info
	 *	@return info
	 */
	public String getActionInfo()
	{
		String action = getAction();
		if (ACTION_AppsProcess.equals(action))
			return "Process:AD_Process_ID=" + getAD_Process_ID();
		else if (ACTION_DocumentAction.equals(action))
			return "DocumentAction=" + getDocAction();
		else if (ACTION_AppsReport.equals(action))
			return "Report:AD_Process_ID=" + getAD_Process_ID();
		else if (ACTION_AppsTask.equals(action))
			return "Task:AD_Task_ID=" + getAD_Task_ID();
		else if (ACTION_SetVariable.equals(action))
			return "SetVariable:AD_Column_ID=" + getAD_Column_ID();
		else if (ACTION_SubWorkflow.equals(action))
			return "Workflow:AD_Workflow_ID=" + getAD_Workflow_ID();
		else if (ACTION_UserChoice.equals(action))
			return "UserChoice:AD_Column_ID=" + getAD_Column_ID();
		/*
		else if (ACTION_UserWorkbench.equals(action))
			return "Workbench:?";*/
		else if (ACTION_UserForm.equals(action))
			return "Form:AD_Form_ID=" + getAD_Form_ID();
		else if (ACTION_UserWindow.equals(action))
			return "Window:AD_Window_ID=" + getAD_Window_ID();
		else if (ACTION_WaitSleep.equals(action))
			return "Sleep:WaitTime=" + getWaitTime();
		return "??";
	}	//	getActionInfo
	
	
	/**
	 * 	Get Attribute Name
	 *	@see org.compiere.model.X_AD_WF_Node#getAttributeName()
	 *	@return Attribute Name
	 */
	@Override
	public String getAttributeName ()
	{
		if (getAD_Column_ID() == 0)
			return super.getAttributeName();
		//	We have a column
		String attribute = super.getAttributeName();
		if (attribute != null && attribute.length() > 0)
			return attribute;
		setAttributeName(getColumn().getColumnName());
		return super.getAttributeName ();
	}	//	getAttributeName
	
	
	/**
	 * 	Get Column
	 *	@return column if valid
	 */
	public MColumn getColumn()
	{
		if (getAD_Column_ID() == 0)
			return null;
		if (m_column == null)
			m_column = MColumn.get(getCtx(), getAD_Column_ID());
		return m_column;
	}	//	getColumn
	
	/**
	 * 	Is this an Approval setp?
	 *	@return true if User Approval
	 */
	public boolean isUserApproval()
	{
		if (!ACTION_UserChoice.equals(getAction()))
			return false;
		return getColumn() != null 
			&& "IsApproved".equals(getColumn().getColumnName());
	}	//	isApproval

	/**
	 * 	Is this a User Choice step?
	 *	@return true if User Choice
	 */
	public boolean isUserChoice()
	{
		return ACTION_UserChoice.equals(getAction());
	}	//	isUserChoice
	
	/**
	 * 	Is this a Manual user step?
	 *	@return true if Window/Form/Workbench
	 */
	public boolean isUserManual()
	{
		if (ACTION_UserForm.equals(getAction())
			|| ACTION_UserWindow.equals(getAction())
			/*|| ACTION_UserWorkbench.equals(getAction())*/)
			return true;
		return false;
	}	//	isUserManual
	
	
	/**
	 * 	Get Duration in ms
	 *	@return duration in ms
	 */
	public long getDurationMS ()
	{
		long duration = super.getDuration ();
		if (duration == 0)
			return 0;
		if (m_durationBaseMS == -1)
			m_durationBaseMS = getAD_Workflow().getDurationBaseSec() * 1000;
		return duration * m_durationBaseMS;
	}	//	getDurationMS
	
	/**
	 * 	Get Duration Limit in ms
	 *	@return duration limit in ms
	 */
	public long getLimitMS ()
	{
		long limit = super.getDurationLimit ();
		if (limit == 0)
			return 0;
		if (m_durationBaseMS == -1)
			m_durationBaseMS = getAD_Workflow().getDurationBaseSec() * 1000;
		return limit * m_durationBaseMS;
	}	//	getLimitMS
	
	/**
	 * 	Get Duration CalendarField
	 *	@return Calendar.MINUTE, etc.
	 */
	public int getDurationCalendarField()
	{
		return getAD_Workflow().getDurationCalendarField();
	}	//	getDirationCalendarField
	
	/**
	 * 	Calculate Dynamic Priority
	 * 	@param seconds second after created
	 *	@return dyn prio
	 */
	public int calculateDynamicPriority (int seconds)
	{
		if (seconds == 0 || getDynPriorityUnit() == null 
			|| getDynPriorityChange() == null 
			|| Env.ZERO.compareTo(getDynPriorityChange()) == 0)
			return 0;
		//
		BigDecimal divide = Env.ZERO;
		if (DYNPRIORITYUNIT_Minute.equals(getDynPriorityUnit()))
			divide = new BigDecimal (60);
		else if (DYNPRIORITYUNIT_Hour.equals(getDynPriorityUnit()))
			divide = new BigDecimal (3600);
		else if (DYNPRIORITYUNIT_Day.equals(getDynPriorityUnit()))
			divide = new BigDecimal (86400);
		else
			return 0;
		//
		BigDecimal change = new BigDecimal (seconds)
			.divide(divide, BigDecimal.ROUND_DOWN)
			.multiply(getDynPriorityChange());
		return change.intValue();
	}	//	calculateDynamicPriority
	
	/**************************************************************************
	 * 	Get Parameters
	 *	@return array of parameters
	 */
	public MWFNodePara[] getParameters()
	{
		if (m_paras == null)
			m_paras = MWFNodePara.getParameters(getCtx(), getAD_WF_Node_ID());
		return m_paras;
	}	//	getParameters

	/**
	 * Get Workflow
	 * @return workflow
	 * @deprecated please use {@link #getAD_Window()}
	 */
	@Override
	public MWorkflow getWorkflow()
	{
		return getAD_Workflow();
	}	//	getWorkflow
	
	@Override
	public MWorkflow getAD_Workflow()
	{
		if (get_TrxName() == null)
			return MWorkflow.get(getCtx(), getAD_Workflow_ID());
		else 
			return (MWorkflow)super.getAD_Workflow();
	}
	
	/**
	 * 	String Representation
	 *	@return info
	 */
	@Override
	public String toString ()
	{
		StringBuffer sb = new StringBuffer ("MWFNode[");
		sb.append(get_ID())
			.append("-").append(getName())
			.append(",Action=").append(getActionInfo())
			.append ("]");
		return sb.toString ();
	}	//	toString

	/**
	 * 	User String Representation
	 *	@return info
	 */
	public String toStringX ()
	{
		StringBuffer sb = new StringBuffer ("MWFNode[");
		sb.append(getName())
			.append("-").append(getActionInfo())
			.append("]");
		return sb.toString ();
	}	//	toStringX
	
	/**
	 * 	Before Save
	 *	@param newRecord new
	 *	@return true if can be saved
	 */
	@Override
	protected boolean beforeSave (boolean newRecord)
	{
		if(MWorkflow.WORKFLOWTYPE_Manufacturing.equals(getAD_Workflow().getWorkflowType()))
		{
			setAction(MWFNode.ACTION_WaitSleep);
			return true;
		}
		
		String action = getAction();
		if (action.equals(ACTION_WaitSleep))
			;
		else if (action.equals(ACTION_AppsProcess) || action.equals(ACTION_AppsReport)) 
		{
			if (getAD_Process_ID() <= 0)
			{
				throw new FillMandatoryException(COLUMNNAME_AD_Process_ID);
			}
		}
		else if (action.equals(ACTION_AppsTask)) 
		{
			if (getAD_Task_ID() <= 0)
			{
				throw new FillMandatoryException("AD_Task_ID");
			}
		}
		else if (action.equals(ACTION_DocumentAction)) 
		{
			if (getDocAction() == null || getDocAction().length() == 0)
			{
				throw new FillMandatoryException("DocAction");
			}
		}
		else if (action.equals(ACTION_EMail))
		{
			if (getR_MailText_ID() <= 0)
			{
				throw new FillMandatoryException("R_MailText_ID");
			}
		}
		else if (action.equals(ACTION_SetVariable)) 
		{
			if (getAttributeValue() == null)
			{
				throw new FillMandatoryException("AttributeValue");
			}
		}
		else if (action.equals(ACTION_SubWorkflow))
		{
			if (getAD_Workflow_ID() <= 0)
			{
				throw new FillMandatoryException("AD_Workflow_ID");
			}
		}
		else if (action.equals(ACTION_UserChoice)) 
		{
			if (getAD_Column_ID() <= 0)
			{
				throw new FillMandatoryException("AD_Column_ID");
			}
		}
		else if (action.equals(ACTION_UserForm)) 
		{
			if (getAD_Form_ID() <= 0)
			{
				throw new FillMandatoryException("AD_Form_ID");
			}
		}
		else if (action.equals(ACTION_UserWindow)) 
		{
			if (getAD_Window_ID() <= 0)
			{
				throw new FillMandatoryException("AD_Window_ID");
			}
		}
//		else if (action.equals(ACTION_UserWorkbench)) 
//		{
//		&& getAD_Workbench_ID() == 0)
//			log.error("FillMandatory", Msg.getElement(getCtx(), "AD_Workbench_ID"));
//			return false;
//		}
		
		return true;
	}	//	beforeSave
}	//	M_WFNext
