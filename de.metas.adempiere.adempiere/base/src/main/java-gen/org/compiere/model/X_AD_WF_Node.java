/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2007 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software, you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY, without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program, if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;

/** Generated Model for AD_WF_Node
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_AD_WF_Node extends PO implements I_AD_WF_Node, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_AD_WF_Node (Properties ctx, int AD_WF_Node_ID, String trxName)
    {
      super (ctx, AD_WF_Node_ID, trxName);
      /** if (AD_WF_Node_ID == 0)
        {
			setAction (null);
// Z
			setAD_WF_Node_ID (0);
			setAD_Workflow_ID (0);
			setCost (Env.ZERO);
			setDuration (0);
			setEntityType (null);
// 'U'
			setIsCentrallyMaintained (true);
// Y
			setJoinElement (null);
// X
			setLimit (0);
			setName (null);
			setSplitElement (null);
// X
			setValue (null);
			setWaitingTime (0);
			setXPosition (0);
			setYPosition (0);
        } */
    }

    /** Load Constructor */
    public X_AD_WF_Node (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 6 - System - Client 
      */
    protected int get_AccessLevel()
    {
      return accessLevel.intValue();
    }

    /** Load Meta Data */
    protected POInfo initPO (Properties ctx)
    {
      POInfo poi = POInfo.getPOInfo (ctx, Table_ID, get_TrxName());
      return poi;
    }

    public String toString()
    {
      StringBuffer sb = new StringBuffer ("X_AD_WF_Node[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Action AD_Reference_ID=302 */
	public static final int ACTION_AD_Reference_ID=302;
	/** Wait (Sleep) = Z */
	public static final String ACTION_WaitSleep = "Z";
	/** User Choice = C */
	public static final String ACTION_UserChoice = "C";
	/** Sub Workflow = F */
	public static final String ACTION_SubWorkflow = "F";
	/** Set Variable = V */
	public static final String ACTION_SetVariable = "V";
	/** User Window = W */
	public static final String ACTION_UserWindow = "W";
	/** User Form = X */
	public static final String ACTION_UserForm = "X";
	/** Apps Task = T */
	public static final String ACTION_AppsTask = "T";
	/** Apps Report = R */
	public static final String ACTION_AppsReport = "R";
	/** Apps Process = P */
	public static final String ACTION_AppsProcess = "P";
	/** Document Action = D */
	public static final String ACTION_DocumentAction = "D";
	/** EMail = M */
	public static final String ACTION_EMail = "M";
	/** User Workbench = B */
	public static final String ACTION_UserWorkbench = "B";
	/** Set Action.
		@param Action 
		Indicates the Action to be performed
	  */
	public void setAction (String Action)
	{

		set_Value (COLUMNNAME_Action, Action);
	}

	/** Get Action.
		@return Indicates the Action to be performed
	  */
	public String getAction () 
	{
		return (String)get_Value(COLUMNNAME_Action);
	}

	public I_AD_Column getAD_Column() throws RuntimeException
    {
		return (I_AD_Column)MTable.get(getCtx(), I_AD_Column.Table_Name)
			.getPO(getAD_Column_ID(), get_TrxName());	}

	/** Set Column.
		@param AD_Column_ID 
		Column in the table
	  */
	public void setAD_Column_ID (int AD_Column_ID)
	{
		if (AD_Column_ID < 1) 
			set_Value (COLUMNNAME_AD_Column_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Column_ID, Integer.valueOf(AD_Column_ID));
	}

	/** Get Column.
		@return Column in the table
	  */
	public int getAD_Column_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Column_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_AD_Form getAD_Form() throws RuntimeException
    {
		return (I_AD_Form)MTable.get(getCtx(), I_AD_Form.Table_Name)
			.getPO(getAD_Form_ID(), get_TrxName());	}

	/** Set Special Form.
		@param AD_Form_ID 
		Special Form
	  */
	public void setAD_Form_ID (int AD_Form_ID)
	{
		if (AD_Form_ID < 1) 
			set_Value (COLUMNNAME_AD_Form_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Form_ID, Integer.valueOf(AD_Form_ID));
	}

	/** Get Special Form.
		@return Special Form
	  */
	public int getAD_Form_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Form_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_AD_Image getAD_Image() throws RuntimeException
    {
		return (I_AD_Image)MTable.get(getCtx(), I_AD_Image.Table_Name)
			.getPO(getAD_Image_ID(), get_TrxName());	}

	/** Set Image.
		@param AD_Image_ID 
		Image or Icon
	  */
	public void setAD_Image_ID (int AD_Image_ID)
	{
		if (AD_Image_ID < 1) 
			set_Value (COLUMNNAME_AD_Image_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Image_ID, Integer.valueOf(AD_Image_ID));
	}

	/** Get Image.
		@return Image or Icon
	  */
	public int getAD_Image_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Image_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_AD_Process getAD_Process() throws RuntimeException
    {
		return (I_AD_Process)MTable.get(getCtx(), I_AD_Process.Table_Name)
			.getPO(getAD_Process_ID(), get_TrxName());	}

	/** Set Process.
		@param AD_Process_ID 
		Process or Report
	  */
	public void setAD_Process_ID (int AD_Process_ID)
	{
		if (AD_Process_ID < 1) 
			set_Value (COLUMNNAME_AD_Process_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Process_ID, Integer.valueOf(AD_Process_ID));
	}

	/** Get Process.
		@return Process or Report
	  */
	public int getAD_Process_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Process_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_AD_Task getAD_Task() throws RuntimeException
    {
		return (I_AD_Task)MTable.get(getCtx(), I_AD_Task.Table_Name)
			.getPO(getAD_Task_ID(), get_TrxName());	}

	/** Set OS Task.
		@param AD_Task_ID 
		Operation System Task
	  */
	public void setAD_Task_ID (int AD_Task_ID)
	{
		if (AD_Task_ID < 1) 
			set_Value (COLUMNNAME_AD_Task_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Task_ID, Integer.valueOf(AD_Task_ID));
	}

	/** Get OS Task.
		@return Operation System Task
	  */
	public int getAD_Task_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Task_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_AD_WF_Block getAD_WF_Block() throws RuntimeException
    {
		return (I_AD_WF_Block)MTable.get(getCtx(), I_AD_WF_Block.Table_Name)
			.getPO(getAD_WF_Block_ID(), get_TrxName());	}

	/** Set Workflow Block.
		@param AD_WF_Block_ID 
		Workflow Transaction Execution Block
	  */
	public void setAD_WF_Block_ID (int AD_WF_Block_ID)
	{
		if (AD_WF_Block_ID < 1) 
			set_Value (COLUMNNAME_AD_WF_Block_ID, null);
		else 
			set_Value (COLUMNNAME_AD_WF_Block_ID, Integer.valueOf(AD_WF_Block_ID));
	}

	/** Get Workflow Block.
		@return Workflow Transaction Execution Block
	  */
	public int getAD_WF_Block_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_WF_Block_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Node.
		@param AD_WF_Node_ID 
		Workflow Node (activity), step or process
	  */
	public void setAD_WF_Node_ID (int AD_WF_Node_ID)
	{
		if (AD_WF_Node_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_WF_Node_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_WF_Node_ID, Integer.valueOf(AD_WF_Node_ID));
	}

	/** Get Node.
		@return Workflow Node (activity), step or process
	  */
	public int getAD_WF_Node_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_WF_Node_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_AD_WF_Responsible getAD_WF_Responsible() throws RuntimeException
    {
		return (I_AD_WF_Responsible)MTable.get(getCtx(), I_AD_WF_Responsible.Table_Name)
			.getPO(getAD_WF_Responsible_ID(), get_TrxName());	}

	/** Set Workflow Responsible.
		@param AD_WF_Responsible_ID 
		Responsible for Workflow Execution
	  */
	public void setAD_WF_Responsible_ID (int AD_WF_Responsible_ID)
	{
		if (AD_WF_Responsible_ID < 1) 
			set_Value (COLUMNNAME_AD_WF_Responsible_ID, null);
		else 
			set_Value (COLUMNNAME_AD_WF_Responsible_ID, Integer.valueOf(AD_WF_Responsible_ID));
	}

	/** Get Workflow Responsible.
		@return Responsible for Workflow Execution
	  */
	public int getAD_WF_Responsible_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_WF_Responsible_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_AD_Window getAD_Window() throws RuntimeException
    {
		return (I_AD_Window)MTable.get(getCtx(), I_AD_Window.Table_Name)
			.getPO(getAD_Window_ID(), get_TrxName());	}

	/** Set Window.
		@param AD_Window_ID 
		Data entry or display window
	  */
	public void setAD_Window_ID (int AD_Window_ID)
	{
		if (AD_Window_ID < 1) 
			set_Value (COLUMNNAME_AD_Window_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Window_ID, Integer.valueOf(AD_Window_ID));
	}

	/** Get Window.
		@return Data entry or display window
	  */
	public int getAD_Window_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Window_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_AD_Workflow getAD_Workflow() throws RuntimeException
    {
		return (I_AD_Workflow)MTable.get(getCtx(), I_AD_Workflow.Table_Name)
			.getPO(getAD_Workflow_ID(), get_TrxName());	}

	/** Set Workflow.
		@param AD_Workflow_ID 
		Workflow or combination of tasks
	  */
	public void setAD_Workflow_ID (int AD_Workflow_ID)
	{
		if (AD_Workflow_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Workflow_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Workflow_ID, Integer.valueOf(AD_Workflow_ID));
	}

	/** Get Workflow.
		@return Workflow or combination of tasks
	  */
	public int getAD_Workflow_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Workflow_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Attribute Name.
		@param AttributeName 
		Name of the Attribute
	  */
	public void setAttributeName (String AttributeName)
	{
		set_Value (COLUMNNAME_AttributeName, AttributeName);
	}

	/** Get Attribute Name.
		@return Name of the Attribute
	  */
	public String getAttributeName () 
	{
		return (String)get_Value(COLUMNNAME_AttributeName);
	}

	/** Set Attribute Value.
		@param AttributeValue 
		Value of the Attribute
	  */
	public void setAttributeValue (String AttributeValue)
	{
		set_Value (COLUMNNAME_AttributeValue, AttributeValue);
	}

	/** Get Attribute Value.
		@return Value of the Attribute
	  */
	public String getAttributeValue () 
	{
		return (String)get_Value(COLUMNNAME_AttributeValue);
	}

	public I_C_BPartner getC_BPartner() throws RuntimeException
    {
		return (I_C_BPartner)MTable.get(getCtx(), I_C_BPartner.Table_Name)
			.getPO(getC_BPartner_ID(), get_TrxName());	}

	/** Set Business Partner .
		@param C_BPartner_ID 
		Identifies a Business Partner
	  */
	public void setC_BPartner_ID (int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
	}

	/** Get Business Partner .
		@return Identifies a Business Partner
	  */
	public int getC_BPartner_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Cost.
		@param Cost 
		Cost information
	  */
	public void setCost (BigDecimal Cost)
	{
		set_Value (COLUMNNAME_Cost, Cost);
	}

	/** Get Cost.
		@return Cost information
	  */
	public BigDecimal getCost () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Cost);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Description.
		@param Description 
		Optional short description of the record
	  */
	public void setDescription (String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	/** Get Description.
		@return Optional short description of the record
	  */
	public String getDescription () 
	{
		return (String)get_Value(COLUMNNAME_Description);
	}

	/** DocAction AD_Reference_ID=135 */
	public static final int DOCACTION_AD_Reference_ID=135;
	/** Complete = CO */
	public static final String DOCACTION_Complete = "CO";
	/** Approve = AP */
	public static final String DOCACTION_Approve = "AP";
	/** Reject = RJ */
	public static final String DOCACTION_Reject = "RJ";
	/** Post = PO */
	public static final String DOCACTION_Post = "PO";
	/** Void = VO */
	public static final String DOCACTION_Void = "VO";
	/** Close = CL */
	public static final String DOCACTION_Close = "CL";
	/** Reverse - Correct = RC */
	public static final String DOCACTION_Reverse_Correct = "RC";
	/** Reverse - Accrual = RA */
	public static final String DOCACTION_Reverse_Accrual = "RA";
	/** Invalidate = IN */
	public static final String DOCACTION_Invalidate = "IN";
	/** Re-activate = RE */
	public static final String DOCACTION_Re_Activate = "RE";
	/** <None> = -- */
	public static final String DOCACTION_None = "--";
	/** Prepare = PR */
	public static final String DOCACTION_Prepare = "PR";
	/** Unlock = XL */
	public static final String DOCACTION_Unlock = "XL";
	/** Wait Complete = WC */
	public static final String DOCACTION_WaitComplete = "WC";
	/** Set Document Action.
		@param DocAction 
		The targeted status of the document
	  */
	public void setDocAction (String DocAction)
	{

		set_Value (COLUMNNAME_DocAction, DocAction);
	}

	/** Get Document Action.
		@return The targeted status of the document
	  */
	public String getDocAction () 
	{
		return (String)get_Value(COLUMNNAME_DocAction);
	}

	/** Set Duration.
		@param Duration 
		Normal Duration in Duration Unit
	  */
	public void setDuration (int Duration)
	{
		set_Value (COLUMNNAME_Duration, Integer.valueOf(Duration));
	}

	/** Get Duration.
		@return Normal Duration in Duration Unit
	  */
	public int getDuration () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Duration);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Dynamic Priority Change.
		@param DynPriorityChange 
		Change of priority when Activity is suspended waiting for user
	  */
	public void setDynPriorityChange (BigDecimal DynPriorityChange)
	{
		set_Value (COLUMNNAME_DynPriorityChange, DynPriorityChange);
	}

	/** Get Dynamic Priority Change.
		@return Change of priority when Activity is suspended waiting for user
	  */
	public BigDecimal getDynPriorityChange () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_DynPriorityChange);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** DynPriorityUnit AD_Reference_ID=221 */
	public static final int DYNPRIORITYUNIT_AD_Reference_ID=221;
	/** Minute = M */
	public static final String DYNPRIORITYUNIT_Minute = "M";
	/** Hour = H */
	public static final String DYNPRIORITYUNIT_Hour = "H";
	/** Day = D */
	public static final String DYNPRIORITYUNIT_Day = "D";
	/** Set Dynamic Priority Unit.
		@param DynPriorityUnit 
		Change of priority when Activity is suspended waiting for user
	  */
	public void setDynPriorityUnit (String DynPriorityUnit)
	{

		set_Value (COLUMNNAME_DynPriorityUnit, DynPriorityUnit);
	}

	/** Get Dynamic Priority Unit.
		@return Change of priority when Activity is suspended waiting for user
	  */
	public String getDynPriorityUnit () 
	{
		return (String)get_Value(COLUMNNAME_DynPriorityUnit);
	}

	/** Set EMail Address.
		@param EMail 
		Electronic Mail Address
	  */
	public void setEMail (String EMail)
	{
		set_Value (COLUMNNAME_EMail, EMail);
	}

	/** Get EMail Address.
		@return Electronic Mail Address
	  */
	public String getEMail () 
	{
		return (String)get_Value(COLUMNNAME_EMail);
	}

	/** EMailRecipient AD_Reference_ID=363 */
	public static final int EMAILRECIPIENT_AD_Reference_ID=363;
	/** Document Owner = D */
	public static final String EMAILRECIPIENT_DocumentOwner = "D";
	/** Document Business Partner = B */
	public static final String EMAILRECIPIENT_DocumentBusinessPartner = "B";
	/** WF Responsible = R */
	public static final String EMAILRECIPIENT_WFResponsible = "R";
	/** Set EMail Recipient.
		@param EMailRecipient 
		Recipient of the EMail
	  */
	public void setEMailRecipient (String EMailRecipient)
	{

		set_Value (COLUMNNAME_EMailRecipient, EMailRecipient);
	}

	/** Get EMail Recipient.
		@return Recipient of the EMail
	  */
	public String getEMailRecipient () 
	{
		return (String)get_Value(COLUMNNAME_EMailRecipient);
	}

	/** EntityType AD_Reference_ID=389 */
	public static final int ENTITYTYPE_AD_Reference_ID=389;
	/** Set Entity Type.
		@param EntityType 
		Dictionary Entity Type; Determines ownership and synchronization
	  */
	public void setEntityType (String EntityType)
	{

		set_Value (COLUMNNAME_EntityType, EntityType);
	}

	/** Get Entity Type.
		@return Dictionary Entity Type; Determines ownership and synchronization
	  */
	public String getEntityType () 
	{
		return (String)get_Value(COLUMNNAME_EntityType);
	}

	/** FinishMode AD_Reference_ID=303 */
	public static final int FINISHMODE_AD_Reference_ID=303;
	/** Automatic = A */
	public static final String FINISHMODE_Automatic = "A";
	/** Manual = M */
	public static final String FINISHMODE_Manual = "M";
	/** Set Finish Mode.
		@param FinishMode 
		Workflow Activity Finish Mode
	  */
	public void setFinishMode (String FinishMode)
	{

		set_Value (COLUMNNAME_FinishMode, FinishMode);
	}

	/** Get Finish Mode.
		@return Workflow Activity Finish Mode
	  */
	public String getFinishMode () 
	{
		return (String)get_Value(COLUMNNAME_FinishMode);
	}

	/** Set Comment/Help.
		@param Help 
		Comment or Hint
	  */
	public void setHelp (String Help)
	{
		set_Value (COLUMNNAME_Help, Help);
	}

	/** Get Comment/Help.
		@return Comment or Hint
	  */
	public String getHelp () 
	{
		return (String)get_Value(COLUMNNAME_Help);
	}

	/** Set Centrally maintained.
		@param IsCentrallyMaintained 
		Information maintained in System Element table
	  */
	public void setIsCentrallyMaintained (boolean IsCentrallyMaintained)
	{
		set_Value (COLUMNNAME_IsCentrallyMaintained, Boolean.valueOf(IsCentrallyMaintained));
	}

	/** Get Centrally maintained.
		@return Information maintained in System Element table
	  */
	public boolean isCentrallyMaintained () 
	{
		Object oo = get_Value(COLUMNNAME_IsCentrallyMaintained);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Is Milestone.
		@param IsMilestone Is Milestone	  */
	public void setIsMilestone (boolean IsMilestone)
	{
		set_Value (COLUMNNAME_IsMilestone, Boolean.valueOf(IsMilestone));
	}

	/** Get Is Milestone.
		@return Is Milestone	  */
	public boolean isMilestone () 
	{
		Object oo = get_Value(COLUMNNAME_IsMilestone);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Is Subcontracting.
		@param IsSubcontracting Is Subcontracting	  */
	public void setIsSubcontracting (boolean IsSubcontracting)
	{
		set_Value (COLUMNNAME_IsSubcontracting, Boolean.valueOf(IsSubcontracting));
	}

	/** Get Is Subcontracting.
		@return Is Subcontracting	  */
	public boolean isSubcontracting () 
	{
		Object oo = get_Value(COLUMNNAME_IsSubcontracting);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** JoinElement AD_Reference_ID=301 */
	public static final int JOINELEMENT_AD_Reference_ID=301;
	/** AND = A */
	public static final String JOINELEMENT_AND = "A";
	/** XOR = X */
	public static final String JOINELEMENT_XOR = "X";
	/** Set Join Element.
		@param JoinElement 
		Semantics for multiple incoming Transitions
	  */
	public void setJoinElement (String JoinElement)
	{

		set_Value (COLUMNNAME_JoinElement, JoinElement);
	}

	/** Get Join Element.
		@return Semantics for multiple incoming Transitions
	  */
	public String getJoinElement () 
	{
		return (String)get_Value(COLUMNNAME_JoinElement);
	}

	/** Set Duration Limit.
		@param DurationLimit 
		Maximum Duration in Duration Unit
	  */
	public void setDurationLimit (int DurationLimit)
	{
		set_Value (COLUMNNAME_DurationLimit, Integer.valueOf(DurationLimit));
	}

	/** Get Duration Limit.
		@return Maximum Duration in Duration Unit
	  */
	public int getDurationLimit () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DurationLimit);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Moving Time.
		@param MovingTime Moving Time	  */
	public void setMovingTime (int MovingTime)
	{
		set_Value (COLUMNNAME_MovingTime, Integer.valueOf(MovingTime));
	}

	/** Get Moving Time.
		@return Moving Time	  */
	public int getMovingTime () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_MovingTime);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Name.
		@param Name 
		Alphanumeric identifier of the entity
	  */
	public void setName (String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	/** Get Name.
		@return Alphanumeric identifier of the entity
	  */
	public String getName () 
	{
		return (String)get_Value(COLUMNNAME_Name);
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), getName());
    }

	/** Set Overlap Units.
		@param OverlapUnits 
		Overlap Units are number of units that must be completed before they are moved the next activity
	  */
	public void setOverlapUnits (int OverlapUnits)
	{
		set_Value (COLUMNNAME_OverlapUnits, Integer.valueOf(OverlapUnits));
	}

	/** Get Overlap Units.
		@return Overlap Units are number of units that must be completed before they are moved the next activity
	  */
	public int getOverlapUnits () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_OverlapUnits);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Priority.
		@param Priority 
		Indicates if this request is of a high, medium or low priority.
	  */
	public void setPriority (int Priority)
	{
		set_Value (COLUMNNAME_Priority, Integer.valueOf(Priority));
	}

	/** Get Priority.
		@return Indicates if this request is of a high, medium or low priority.
	  */
	public int getPriority () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Priority);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Queuing Time.
		@param QueuingTime Queuing Time	  */
	public void setQueuingTime (int QueuingTime)
	{
		set_Value (COLUMNNAME_QueuingTime, Integer.valueOf(QueuingTime));
	}

	/** Get Queuing Time.
		@return Queuing Time	  */
	public int getQueuingTime () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_QueuingTime);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_R_MailText getR_MailText() throws RuntimeException
    {
		return (I_R_MailText)MTable.get(getCtx(), I_R_MailText.Table_Name)
			.getPO(getR_MailText_ID(), get_TrxName());	}

	/** Set Mail Template.
		@param R_MailText_ID 
		Text templates for mailings
	  */
	public void setR_MailText_ID (int R_MailText_ID)
	{
		if (R_MailText_ID < 1) 
			set_Value (COLUMNNAME_R_MailText_ID, null);
		else 
			set_Value (COLUMNNAME_R_MailText_ID, Integer.valueOf(R_MailText_ID));
	}

	/** Get Mail Template.
		@return Text templates for mailings
	  */
	public int getR_MailText_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_R_MailText_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Setup Time.
		@param SetupTime 
		Setup time before starting Production
	  */
	public void setSetupTime (int SetupTime)
	{
		set_Value (COLUMNNAME_SetupTime, Integer.valueOf(SetupTime));
	}

	/** Get Setup Time.
		@return Setup time before starting Production
	  */
	public int getSetupTime () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SetupTime);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** SplitElement AD_Reference_ID=301 */
	public static final int SPLITELEMENT_AD_Reference_ID=301;
	/** AND = A */
	public static final String SPLITELEMENT_AND = "A";
	/** XOR = X */
	public static final String SPLITELEMENT_XOR = "X";
	/** Set Split Element.
		@param SplitElement 
		Semantics for multiple outgoing Transitions
	  */
	public void setSplitElement (String SplitElement)
	{

		set_Value (COLUMNNAME_SplitElement, SplitElement);
	}

	/** Get Split Element.
		@return Semantics for multiple outgoing Transitions
	  */
	public String getSplitElement () 
	{
		return (String)get_Value(COLUMNNAME_SplitElement);
	}

	public I_S_Resource getS_Resource() throws RuntimeException
    {
		return (I_S_Resource)MTable.get(getCtx(), I_S_Resource.Table_Name)
			.getPO(getS_Resource_ID(), get_TrxName());	}

	/** Set Resource.
		@param S_Resource_ID 
		Resource
	  */
	public void setS_Resource_ID (int S_Resource_ID)
	{
		if (S_Resource_ID < 1) 
			set_Value (COLUMNNAME_S_Resource_ID, null);
		else 
			set_Value (COLUMNNAME_S_Resource_ID, Integer.valueOf(S_Resource_ID));
	}

	/** Get Resource.
		@return Resource
	  */
	public int getS_Resource_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_S_Resource_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** StartMode AD_Reference_ID=303 */
	public static final int STARTMODE_AD_Reference_ID=303;
	/** Automatic = A */
	public static final String STARTMODE_Automatic = "A";
	/** Manual = M */
	public static final String STARTMODE_Manual = "M";
	/** Set Start Mode.
		@param StartMode 
		Workflow Activity Start Mode 
	  */
	public void setStartMode (String StartMode)
	{

		set_Value (COLUMNNAME_StartMode, StartMode);
	}

	/** Get Start Mode.
		@return Workflow Activity Start Mode 
	  */
	public String getStartMode () 
	{
		return (String)get_Value(COLUMNNAME_StartMode);
	}

	/** SubflowExecution AD_Reference_ID=307 */
	public static final int SUBFLOWEXECUTION_AD_Reference_ID=307;
	/** Asynchronously = A */
	public static final String SUBFLOWEXECUTION_Asynchronously = "A";
	/** Synchronously = S */
	public static final String SUBFLOWEXECUTION_Synchronously = "S";
	/** Set Subflow Execution.
		@param SubflowExecution 
		Mode how the sub-workflow is executed
	  */
	public void setSubflowExecution (String SubflowExecution)
	{

		set_Value (COLUMNNAME_SubflowExecution, SubflowExecution);
	}

	/** Get Subflow Execution.
		@return Mode how the sub-workflow is executed
	  */
	public String getSubflowExecution () 
	{
		return (String)get_Value(COLUMNNAME_SubflowExecution);
	}

	/** Set Units by Cycles.
		@param UnitsCycles 
		The Units by Cycles are defined for process type  Flow Repetitive Dedicated and  indicated the product to be manufactured on a production line for duration unit.
	  */
	public void setUnitsCycles (BigDecimal UnitsCycles)
	{
		set_Value (COLUMNNAME_UnitsCycles, UnitsCycles);
	}

	/** Get Units by Cycles.
		@return The Units by Cycles are defined for process type  Flow Repetitive Dedicated and  indicated the product to be manufactured on a production line for duration unit.
	  */
	public BigDecimal getUnitsCycles () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_UnitsCycles);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Valid from.
		@param ValidFrom 
		Valid from including this date (first day)
	  */
	public void setValidFrom (Timestamp ValidFrom)
	{
		set_Value (COLUMNNAME_ValidFrom, ValidFrom);
	}

	/** Get Valid from.
		@return Valid from including this date (first day)
	  */
	public Timestamp getValidFrom () 
	{
		return (Timestamp)get_Value(COLUMNNAME_ValidFrom);
	}

	/** Set Valid to.
		@param ValidTo 
		Valid to including this date (last day)
	  */
	public void setValidTo (Timestamp ValidTo)
	{
		set_Value (COLUMNNAME_ValidTo, ValidTo);
	}

	/** Get Valid to.
		@return Valid to including this date (last day)
	  */
	public Timestamp getValidTo () 
	{
		return (Timestamp)get_Value(COLUMNNAME_ValidTo);
	}

	/** Set Search Key.
		@param Value 
		Search key for the record in the format required - must be unique
	  */
	public void setValue (String Value)
	{
		set_Value (COLUMNNAME_Value, Value);
	}

	/** Get Search Key.
		@return Search key for the record in the format required - must be unique
	  */
	public String getValue () 
	{
		return (String)get_Value(COLUMNNAME_Value);
	}

	/** Set Waiting Time.
		@param WaitingTime 
		Workflow Simulation Waiting time
	  */
	public void setWaitingTime (int WaitingTime)
	{
		set_Value (COLUMNNAME_WaitingTime, Integer.valueOf(WaitingTime));
	}

	/** Get Waiting Time.
		@return Workflow Simulation Waiting time
	  */
	public int getWaitingTime () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_WaitingTime);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Wait Time.
		@param WaitTime 
		Time in minutes to wait (sleep)
	  */
	public void setWaitTime (int WaitTime)
	{
		set_Value (COLUMNNAME_WaitTime, Integer.valueOf(WaitTime));
	}

	/** Get Wait Time.
		@return Time in minutes to wait (sleep)
	  */
	public int getWaitTime () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_WaitTime);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_AD_Workflow getWorkflow() throws RuntimeException
    {
		return (I_AD_Workflow)MTable.get(getCtx(), I_AD_Workflow.Table_Name)
			.getPO(getWorkflow_ID(), get_TrxName());	}

	/** Set Workflow.
		@param Workflow_ID 
		Workflow or tasks
	  */
	public void setWorkflow_ID (int Workflow_ID)
	{
		if (Workflow_ID < 1) 
			set_Value (COLUMNNAME_Workflow_ID, null);
		else 
			set_Value (COLUMNNAME_Workflow_ID, Integer.valueOf(Workflow_ID));
	}

	/** Get Workflow.
		@return Workflow or tasks
	  */
	public int getWorkflow_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Workflow_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Working Time.
		@param WorkingTime 
		Workflow Simulation Execution Time
	  */
	public void setWorkingTime (int WorkingTime)
	{
		set_Value (COLUMNNAME_WorkingTime, Integer.valueOf(WorkingTime));
	}

	/** Get Working Time.
		@return Workflow Simulation Execution Time
	  */
	public int getWorkingTime () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_WorkingTime);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set X Position.
		@param XPosition 
		Absolute X (horizontal) position in 1/72 of an inch
	  */
	public void setXPosition (int XPosition)
	{
		set_Value (COLUMNNAME_XPosition, Integer.valueOf(XPosition));
	}

	/** Get X Position.
		@return Absolute X (horizontal) position in 1/72 of an inch
	  */
	public int getXPosition () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_XPosition);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Yield %.
		@param Yield 
		The Yield is the percentage of a lot that is expected to be of acceptable wuality may fall below 100 percent
	  */
	public void setYield (int Yield)
	{
		set_Value (COLUMNNAME_Yield, Integer.valueOf(Yield));
	}

	/** Get Yield %.
		@return The Yield is the percentage of a lot that is expected to be of acceptable wuality may fall below 100 percent
	  */
	public int getYield () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Yield);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Y Position.
		@param YPosition 
		Absolute Y (vertical) position in 1/72 of an inch
	  */
	public void setYPosition (int YPosition)
	{
		set_Value (COLUMNNAME_YPosition, Integer.valueOf(YPosition));
	}

	/** Get Y Position.
		@return Absolute Y (vertical) position in 1/72 of an inch
	  */
	public int getYPosition () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_YPosition);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}