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
package org.eevolution.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.util.Env;

/** Generated Model for PP_Order_Node
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_PP_Order_Node extends org.compiere.model.PO implements I_PP_Order_Node, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -1064748885L;

    /** Standard Constructor */
    public X_PP_Order_Node (Properties ctx, int PP_Order_Node_ID, String trxName)
    {
      super (ctx, PP_Order_Node_ID, trxName);
      /** if (PP_Order_Node_ID == 0)
        {
			setAction (null);
// Z
			setAD_WF_Node_ID (0);
			setAD_Workflow_ID (0);
			setCost (Env.ZERO);
			setDurationBeforeClose (0);
// 0
			setDurationLimit (0);
			setEntityType (null);
// U
			setIsCentrallyMaintained (false);
			setJoinElement (null);
// X
			setName (null);
			setPP_Order_ID (0);
			setPP_Order_Node_ID (0);
			setPP_Order_Workflow_ID (0);
			setPriority (0);
			setQtyBeforeClose (Env.ZERO);
// 0
			setSplitElement (null);
// X
			setValue (null);
			setWaitingTime (0);
			setWorkingTime (0);
			setXPosition (0);
			setYPosition (0);
        } */
    }

    /** Load Constructor */
    public X_PP_Order_Node (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }


    /** Load Meta Data */
    @Override
    protected org.compiere.model.POInfo initPO (Properties ctx)
    {
      org.compiere.model.POInfo poi = org.compiere.model.POInfo.getPOInfo (ctx, Table_Name, get_TrxName());
      return poi;
    }

	/** 
	 * Action AD_Reference_ID=302
	 * Reference name: WF_Action
	 */
	public static final int ACTION_AD_Reference_ID=302;
	/** WaitSleep = Z */
	public static final String ACTION_WaitSleep = "Z";
	/** User Choice = C */
	public static final String ACTION_UserChoice = "C";
	/** Sub Workflow = F */
	public static final String ACTION_SubWorkflow = "F";
	/** Set Variable = V */
	public static final String ACTION_SetVariable = "V";
	/** UserWindow = W */
	public static final String ACTION_UserWindow = "W";
	/** UserForm = X */
	public static final String ACTION_UserForm = "X";
	/** Apps Task = T */
	public static final String ACTION_AppsTask = "T";
	/** Apps Report = R */
	public static final String ACTION_AppsReport = "R";
	/** Apps Process = P */
	public static final String ACTION_AppsProcess = "P";
	/** DocumentAction = D */
	public static final String ACTION_DocumentAction = "D";
	/** EMail = M */
	public static final String ACTION_EMail = "M";
	/** UserWorkbench = B */
	public static final String ACTION_UserWorkbench = "B";
	/** Set Aktion.
		@param Action 
		Indicates the Action to be performed
	  */
	@Override
	public void setAction (java.lang.String Action)
	{

		set_Value (COLUMNNAME_Action, Action);
	}

	/** Get Aktion.
		@return Indicates the Action to be performed
	  */
	@Override
	public java.lang.String getAction () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Action);
	}

	@Override
	public org.compiere.model.I_AD_Column getAD_Column() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_Column_ID, org.compiere.model.I_AD_Column.class);
	}

	@Override
	public void setAD_Column(org.compiere.model.I_AD_Column AD_Column)
	{
		set_ValueFromPO(COLUMNNAME_AD_Column_ID, org.compiere.model.I_AD_Column.class, AD_Column);
	}

	/** Set Spalte.
		@param AD_Column_ID 
		Column in the table
	  */
	@Override
	public void setAD_Column_ID (int AD_Column_ID)
	{
		if (AD_Column_ID < 1) 
			set_Value (COLUMNNAME_AD_Column_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Column_ID, Integer.valueOf(AD_Column_ID));
	}

	/** Get Spalte.
		@return Column in the table
	  */
	@Override
	public int getAD_Column_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Column_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_AD_Form getAD_Form() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_Form_ID, org.compiere.model.I_AD_Form.class);
	}

	@Override
	public void setAD_Form(org.compiere.model.I_AD_Form AD_Form)
	{
		set_ValueFromPO(COLUMNNAME_AD_Form_ID, org.compiere.model.I_AD_Form.class, AD_Form);
	}

	/** Set Special Form.
		@param AD_Form_ID 
		Special Form
	  */
	@Override
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
	@Override
	public int getAD_Form_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Form_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_AD_Image getAD_Image() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_Image_ID, org.compiere.model.I_AD_Image.class);
	}

	@Override
	public void setAD_Image(org.compiere.model.I_AD_Image AD_Image)
	{
		set_ValueFromPO(COLUMNNAME_AD_Image_ID, org.compiere.model.I_AD_Image.class, AD_Image);
	}

	/** Set Bild.
		@param AD_Image_ID 
		Image or Icon
	  */
	@Override
	public void setAD_Image_ID (int AD_Image_ID)
	{
		if (AD_Image_ID < 1) 
			set_Value (COLUMNNAME_AD_Image_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Image_ID, Integer.valueOf(AD_Image_ID));
	}

	/** Get Bild.
		@return Image or Icon
	  */
	@Override
	public int getAD_Image_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Image_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_AD_Process getAD_Process() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_Process_ID, org.compiere.model.I_AD_Process.class);
	}

	@Override
	public void setAD_Process(org.compiere.model.I_AD_Process AD_Process)
	{
		set_ValueFromPO(COLUMNNAME_AD_Process_ID, org.compiere.model.I_AD_Process.class, AD_Process);
	}

	/** Set Prozess.
		@param AD_Process_ID 
		Process or Report
	  */
	@Override
	public void setAD_Process_ID (int AD_Process_ID)
	{
		if (AD_Process_ID < 1) 
			set_Value (COLUMNNAME_AD_Process_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Process_ID, Integer.valueOf(AD_Process_ID));
	}

	/** Get Prozess.
		@return Process or Report
	  */
	@Override
	public int getAD_Process_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Process_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_AD_Task getAD_Task() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_Task_ID, org.compiere.model.I_AD_Task.class);
	}

	@Override
	public void setAD_Task(org.compiere.model.I_AD_Task AD_Task)
	{
		set_ValueFromPO(COLUMNNAME_AD_Task_ID, org.compiere.model.I_AD_Task.class, AD_Task);
	}

	/** Set Externer Prozess.
		@param AD_Task_ID 
		Operation System Task
	  */
	@Override
	public void setAD_Task_ID (int AD_Task_ID)
	{
		if (AD_Task_ID < 1) 
			set_Value (COLUMNNAME_AD_Task_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Task_ID, Integer.valueOf(AD_Task_ID));
	}

	/** Get Externer Prozess.
		@return Operation System Task
	  */
	@Override
	public int getAD_Task_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Task_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_AD_WF_Block getAD_WF_Block() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_WF_Block_ID, org.compiere.model.I_AD_WF_Block.class);
	}

	@Override
	public void setAD_WF_Block(org.compiere.model.I_AD_WF_Block AD_WF_Block)
	{
		set_ValueFromPO(COLUMNNAME_AD_WF_Block_ID, org.compiere.model.I_AD_WF_Block.class, AD_WF_Block);
	}

	/** Set Workflow Block.
		@param AD_WF_Block_ID 
		Workflow Transaction Execution Block
	  */
	@Override
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
	@Override
	public int getAD_WF_Block_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_WF_Block_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_AD_WF_Node getAD_WF_Node() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_WF_Node_ID, org.compiere.model.I_AD_WF_Node.class);
	}

	@Override
	public void setAD_WF_Node(org.compiere.model.I_AD_WF_Node AD_WF_Node)
	{
		set_ValueFromPO(COLUMNNAME_AD_WF_Node_ID, org.compiere.model.I_AD_WF_Node.class, AD_WF_Node);
	}

	/** Set Knoten.
		@param AD_WF_Node_ID 
		Workflow Node (activity), step or process
	  */
	@Override
	public void setAD_WF_Node_ID (int AD_WF_Node_ID)
	{
		if (AD_WF_Node_ID < 1) 
			set_Value (COLUMNNAME_AD_WF_Node_ID, null);
		else 
			set_Value (COLUMNNAME_AD_WF_Node_ID, Integer.valueOf(AD_WF_Node_ID));
	}

	/** Get Knoten.
		@return Workflow Node (activity), step or process
	  */
	@Override
	public int getAD_WF_Node_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_WF_Node_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_AD_WF_Responsible getAD_WF_Responsible() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_WF_Responsible_ID, org.compiere.model.I_AD_WF_Responsible.class);
	}

	@Override
	public void setAD_WF_Responsible(org.compiere.model.I_AD_WF_Responsible AD_WF_Responsible)
	{
		set_ValueFromPO(COLUMNNAME_AD_WF_Responsible_ID, org.compiere.model.I_AD_WF_Responsible.class, AD_WF_Responsible);
	}

	/** Set Workflow - Verantwortlicher.
		@param AD_WF_Responsible_ID 
		Responsible for Workflow Execution
	  */
	@Override
	public void setAD_WF_Responsible_ID (int AD_WF_Responsible_ID)
	{
		if (AD_WF_Responsible_ID < 1) 
			set_Value (COLUMNNAME_AD_WF_Responsible_ID, null);
		else 
			set_Value (COLUMNNAME_AD_WF_Responsible_ID, Integer.valueOf(AD_WF_Responsible_ID));
	}

	/** Get Workflow - Verantwortlicher.
		@return Responsible for Workflow Execution
	  */
	@Override
	public int getAD_WF_Responsible_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_WF_Responsible_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_AD_Window getAD_Window() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_Window_ID, org.compiere.model.I_AD_Window.class);
	}

	@Override
	public void setAD_Window(org.compiere.model.I_AD_Window AD_Window)
	{
		set_ValueFromPO(COLUMNNAME_AD_Window_ID, org.compiere.model.I_AD_Window.class, AD_Window);
	}

	/** Set Fenster.
		@param AD_Window_ID 
		Data entry or display window
	  */
	@Override
	public void setAD_Window_ID (int AD_Window_ID)
	{
		if (AD_Window_ID < 1) 
			set_Value (COLUMNNAME_AD_Window_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Window_ID, Integer.valueOf(AD_Window_ID));
	}

	/** Get Fenster.
		@return Data entry or display window
	  */
	@Override
	public int getAD_Window_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Window_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_AD_Workflow getAD_Workflow() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_Workflow_ID, org.compiere.model.I_AD_Workflow.class);
	}

	@Override
	public void setAD_Workflow(org.compiere.model.I_AD_Workflow AD_Workflow)
	{
		set_ValueFromPO(COLUMNNAME_AD_Workflow_ID, org.compiere.model.I_AD_Workflow.class, AD_Workflow);
	}

	/** Set Workflow.
		@param AD_Workflow_ID 
		Workflow or combination of tasks
	  */
	@Override
	public void setAD_Workflow_ID (int AD_Workflow_ID)
	{
		if (AD_Workflow_ID < 1) 
			set_Value (COLUMNNAME_AD_Workflow_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Workflow_ID, Integer.valueOf(AD_Workflow_ID));
	}

	/** Get Workflow.
		@return Workflow or combination of tasks
	  */
	@Override
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
	@Override
	public void setAttributeName (java.lang.String AttributeName)
	{
		set_Value (COLUMNNAME_AttributeName, AttributeName);
	}

	/** Get Attribute Name.
		@return Name of the Attribute
	  */
	@Override
	public java.lang.String getAttributeName () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_AttributeName);
	}

	/** Set Merkmals-Wert.
		@param AttributeValue 
		Value of the Attribute
	  */
	@Override
	public void setAttributeValue (java.lang.String AttributeValue)
	{
		set_Value (COLUMNNAME_AttributeValue, AttributeValue);
	}

	/** Get Merkmals-Wert.
		@return Value of the Attribute
	  */
	@Override
	public java.lang.String getAttributeValue () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_AttributeValue);
	}

	@Override
	public org.compiere.model.I_C_BPartner getC_BPartner() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_BPartner_ID, org.compiere.model.I_C_BPartner.class);
	}

	@Override
	public void setC_BPartner(org.compiere.model.I_C_BPartner C_BPartner)
	{
		set_ValueFromPO(COLUMNNAME_C_BPartner_ID, org.compiere.model.I_C_BPartner.class, C_BPartner);
	}

	/** Set Geschäftspartner.
		@param C_BPartner_ID 
		Identifies a Business Partner
	  */
	@Override
	public void setC_BPartner_ID (int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
	}

	/** Get Geschäftspartner.
		@return Identifies a Business Partner
	  */
	@Override
	public int getC_BPartner_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Kosten.
		@param Cost 
		Cost information
	  */
	@Override
	public void setCost (java.math.BigDecimal Cost)
	{
		set_Value (COLUMNNAME_Cost, Cost);
	}

	/** Get Kosten.
		@return Cost information
	  */
	@Override
	public java.math.BigDecimal getCost () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Cost);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Projektabschluss.
		@param DateFinish 
		Finish or (planned) completion date
	  */
	@Override
	public void setDateFinish (java.sql.Timestamp DateFinish)
	{
		set_Value (COLUMNNAME_DateFinish, DateFinish);
	}

	/** Get Projektabschluss.
		@return Finish or (planned) completion date
	  */
	@Override
	public java.sql.Timestamp getDateFinish () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_DateFinish);
	}

	/** Set DateFinishSchedule.
		@param DateFinishSchedule DateFinishSchedule	  */
	@Override
	public void setDateFinishSchedule (java.sql.Timestamp DateFinishSchedule)
	{
		set_Value (COLUMNNAME_DateFinishSchedule, DateFinishSchedule);
	}

	/** Get DateFinishSchedule.
		@return DateFinishSchedule	  */
	@Override
	public java.sql.Timestamp getDateFinishSchedule () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_DateFinishSchedule);
	}

	/** Set DateStart.
		@param DateStart DateStart	  */
	@Override
	public void setDateStart (java.sql.Timestamp DateStart)
	{
		set_Value (COLUMNNAME_DateStart, DateStart);
	}

	/** Get DateStart.
		@return DateStart	  */
	@Override
	public java.sql.Timestamp getDateStart () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_DateStart);
	}

	/** Set DateStartSchedule.
		@param DateStartSchedule DateStartSchedule	  */
	@Override
	public void setDateStartSchedule (java.sql.Timestamp DateStartSchedule)
	{
		set_Value (COLUMNNAME_DateStartSchedule, DateStartSchedule);
	}

	/** Get DateStartSchedule.
		@return DateStartSchedule	  */
	@Override
	public java.sql.Timestamp getDateStartSchedule () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_DateStartSchedule);
	}

	/** Set Beschreibung.
		@param Description Beschreibung	  */
	@Override
	public void setDescription (java.lang.String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	/** Get Beschreibung.
		@return Beschreibung	  */
	@Override
	public java.lang.String getDescription () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Description);
	}

	/** 
	 * DocAction AD_Reference_ID=135
	 * Reference name: _Document Action
	 */
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
	/** Reverse_Correct = RC */
	public static final String DOCACTION_Reverse_Correct = "RC";
	/** Reverse_Accrual = RA */
	public static final String DOCACTION_Reverse_Accrual = "RA";
	/** Invalidate = IN */
	public static final String DOCACTION_Invalidate = "IN";
	/** Re_Activate = RE */
	public static final String DOCACTION_Re_Activate = "RE";
	/** None = -- */
	public static final String DOCACTION_None = "--";
	/** Prepare = PR */
	public static final String DOCACTION_Prepare = "PR";
	/** Unlock = XL */
	public static final String DOCACTION_Unlock = "XL";
	/** WaitComplete = WC */
	public static final String DOCACTION_WaitComplete = "WC";
	/** Set Belegverarbeitung.
		@param DocAction 
		The targeted status of the document
	  */
	@Override
	public void setDocAction (java.lang.String DocAction)
	{

		set_Value (COLUMNNAME_DocAction, DocAction);
	}

	/** Get Belegverarbeitung.
		@return The targeted status of the document
	  */
	@Override
	public java.lang.String getDocAction () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DocAction);
	}

	/** 
	 * DocStatus AD_Reference_ID=131
	 * Reference name: _Document Status
	 */
	public static final int DOCSTATUS_AD_Reference_ID=131;
	/** Drafted = DR */
	public static final String DOCSTATUS_Drafted = "DR";
	/** Completed = CO */
	public static final String DOCSTATUS_Completed = "CO";
	/** Approved = AP */
	public static final String DOCSTATUS_Approved = "AP";
	/** NotApproved = NA */
	public static final String DOCSTATUS_NotApproved = "NA";
	/** Voided = VO */
	public static final String DOCSTATUS_Voided = "VO";
	/** Invalid = IN */
	public static final String DOCSTATUS_Invalid = "IN";
	/** Reversed = RE */
	public static final String DOCSTATUS_Reversed = "RE";
	/** Closed = CL */
	public static final String DOCSTATUS_Closed = "CL";
	/** Unknown = ?? */
	public static final String DOCSTATUS_Unknown = "??";
	/** InProgress = IP */
	public static final String DOCSTATUS_InProgress = "IP";
	/** WaitingPayment = WP */
	public static final String DOCSTATUS_WaitingPayment = "WP";
	/** WaitingConfirmation = WC */
	public static final String DOCSTATUS_WaitingConfirmation = "WC";
	/** Set Belegstatus.
		@param DocStatus 
		The current status of the document
	  */
	@Override
	public void setDocStatus (java.lang.String DocStatus)
	{

		set_Value (COLUMNNAME_DocStatus, DocStatus);
	}

	/** Get Belegstatus.
		@return The current status of the document
	  */
	@Override
	public java.lang.String getDocStatus () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DocStatus);
	}

	/** Set Duration.
		@param Duration 
		Normal Duration in Duration Unit
	  */
	@Override
	public void setDuration (int Duration)
	{
		set_Value (COLUMNNAME_Duration, Integer.valueOf(Duration));
	}

	/** Get Duration.
		@return Normal Duration in Duration Unit
	  */
	@Override
	public int getDuration () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Duration);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Duration before close.
		@param DurationBeforeClose Duration before close	  */
	@Override
	public void setDurationBeforeClose (int DurationBeforeClose)
	{
		set_Value (COLUMNNAME_DurationBeforeClose, Integer.valueOf(DurationBeforeClose));
	}

	/** Get Duration before close.
		@return Duration before close	  */
	@Override
	public int getDurationBeforeClose () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DurationBeforeClose);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Duration Limit.
		@param DurationLimit 
		Maximum Duration in Duration Unit
	  */
	@Override
	public void setDurationLimit (int DurationLimit)
	{
		set_Value (COLUMNNAME_DurationLimit, Integer.valueOf(DurationLimit));
	}

	/** Get Duration Limit.
		@return Maximum Duration in Duration Unit
	  */
	@Override
	public int getDurationLimit () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DurationLimit);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Duration Real.
		@param DurationReal Duration Real	  */
	@Override
	public void setDurationReal (int DurationReal)
	{
		set_Value (COLUMNNAME_DurationReal, Integer.valueOf(DurationReal));
	}

	/** Get Duration Real.
		@return Duration Real	  */
	@Override
	public int getDurationReal () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DurationReal);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Duration Requiered.
		@param DurationRequiered Duration Requiered	  */
	@Override
	public void setDurationRequiered (int DurationRequiered)
	{
		set_Value (COLUMNNAME_DurationRequiered, Integer.valueOf(DurationRequiered));
	}

	/** Get Duration Requiered.
		@return Duration Requiered	  */
	@Override
	public int getDurationRequiered () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DurationRequiered);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** 
	 * EntityType AD_Reference_ID=389
	 * Reference name: _EntityTypeNew
	 */
	public static final int ENTITYTYPE_AD_Reference_ID=389;
	/** Set Entitäts-Art.
		@param EntityType 
		Dictionary Entity Type; Determines ownership and synchronization
	  */
	@Override
	public void setEntityType (java.lang.String EntityType)
	{

		set_Value (COLUMNNAME_EntityType, EntityType);
	}

	/** Get Entitäts-Art.
		@return Dictionary Entity Type; Determines ownership and synchronization
	  */
	@Override
	public java.lang.String getEntityType () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_EntityType);
	}

	/** 
	 * FinishMode AD_Reference_ID=303
	 * Reference name: WF_Start-Finish Mode
	 */
	public static final int FINISHMODE_AD_Reference_ID=303;
	/** Automatisch = A */
	public static final String FINISHMODE_Automatisch = "A";
	/** Manuell = M */
	public static final String FINISHMODE_Manuell = "M";
	/** Set Finish Mode.
		@param FinishMode 
		Workflow Activity Finish Mode
	  */
	@Override
	public void setFinishMode (java.lang.String FinishMode)
	{

		set_Value (COLUMNNAME_FinishMode, FinishMode);
	}

	/** Get Finish Mode.
		@return Workflow Activity Finish Mode
	  */
	@Override
	public java.lang.String getFinishMode () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_FinishMode);
	}

	/** Set Kommentar/Hilfe.
		@param Help 
		Comment or Hint
	  */
	@Override
	public void setHelp (java.lang.String Help)
	{
		set_Value (COLUMNNAME_Help, Help);
	}

	/** Get Kommentar/Hilfe.
		@return Comment or Hint
	  */
	@Override
	public java.lang.String getHelp () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Help);
	}

	/** Set Zentral verwaltet.
		@param IsCentrallyMaintained 
		Information maintained in System Element table
	  */
	@Override
	public void setIsCentrallyMaintained (boolean IsCentrallyMaintained)
	{
		set_Value (COLUMNNAME_IsCentrallyMaintained, Boolean.valueOf(IsCentrallyMaintained));
	}

	/** Get Zentral verwaltet.
		@return Information maintained in System Element table
	  */
	@Override
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
	@Override
	public void setIsMilestone (boolean IsMilestone)
	{
		set_Value (COLUMNNAME_IsMilestone, Boolean.valueOf(IsMilestone));
	}

	/** Get Is Milestone.
		@return Is Milestone	  */
	@Override
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
	@Override
	public void setIsSubcontracting (boolean IsSubcontracting)
	{
		set_Value (COLUMNNAME_IsSubcontracting, Boolean.valueOf(IsSubcontracting));
	}

	/** Get Is Subcontracting.
		@return Is Subcontracting	  */
	@Override
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

	/** 
	 * JoinElement AD_Reference_ID=301
	 * Reference name: WF_Join_Split
	 */
	public static final int JOINELEMENT_AD_Reference_ID=301;
	/** AND = A */
	public static final String JOINELEMENT_AND = "A";
	/** XOR = X */
	public static final String JOINELEMENT_XOR = "X";
	/** Set Join Element.
		@param JoinElement 
		Semantics for multiple incoming Transitions
	  */
	@Override
	public void setJoinElement (java.lang.String JoinElement)
	{

		set_Value (COLUMNNAME_JoinElement, JoinElement);
	}

	/** Get Join Element.
		@return Semantics for multiple incoming Transitions
	  */
	@Override
	public java.lang.String getJoinElement () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_JoinElement);
	}

	/** Set Moving Time.
		@param MovingTime Moving Time	  */
	@Override
	public void setMovingTime (int MovingTime)
	{
		set_Value (COLUMNNAME_MovingTime, Integer.valueOf(MovingTime));
	}

	/** Get Moving Time.
		@return Moving Time	  */
	@Override
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
	@Override
	public void setName (java.lang.String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	/** Get Name.
		@return Alphanumeric identifier of the entity
	  */
	@Override
	public java.lang.String getName () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Name);
	}

	/** Set Overlap Units.
		@param OverlapUnits 
		Overlap Units are number of units that must be completed before they are moved the next activity
	  */
	@Override
	public void setOverlapUnits (int OverlapUnits)
	{
		set_Value (COLUMNNAME_OverlapUnits, Integer.valueOf(OverlapUnits));
	}

	/** Get Overlap Units.
		@return Overlap Units are number of units that must be completed before they are moved the next activity
	  */
	@Override
	public int getOverlapUnits () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_OverlapUnits);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.eevolution.model.I_PP_Order getPP_Order() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_PP_Order_ID, org.eevolution.model.I_PP_Order.class);
	}

	@Override
	public void setPP_Order(org.eevolution.model.I_PP_Order PP_Order)
	{
		set_ValueFromPO(COLUMNNAME_PP_Order_ID, org.eevolution.model.I_PP_Order.class, PP_Order);
	}

	/** Set Produktionsauftrag.
		@param PP_Order_ID Produktionsauftrag	  */
	@Override
	public void setPP_Order_ID (int PP_Order_ID)
	{
		if (PP_Order_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_PP_Order_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_PP_Order_ID, Integer.valueOf(PP_Order_ID));
	}

	/** Get Produktionsauftrag.
		@return Produktionsauftrag	  */
	@Override
	public int getPP_Order_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PP_Order_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Manufacturing Order Activity.
		@param PP_Order_Node_ID 
		Workflow Node (activity), step or process
	  */
	@Override
	public void setPP_Order_Node_ID (int PP_Order_Node_ID)
	{
		if (PP_Order_Node_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_PP_Order_Node_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_PP_Order_Node_ID, Integer.valueOf(PP_Order_Node_ID));
	}

	/** Get Manufacturing Order Activity.
		@return Workflow Node (activity), step or process
	  */
	@Override
	public int getPP_Order_Node_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PP_Order_Node_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.eevolution.model.I_PP_Order_Workflow getPP_Order_Workflow() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_PP_Order_Workflow_ID, org.eevolution.model.I_PP_Order_Workflow.class);
	}

	@Override
	public void setPP_Order_Workflow(org.eevolution.model.I_PP_Order_Workflow PP_Order_Workflow)
	{
		set_ValueFromPO(COLUMNNAME_PP_Order_Workflow_ID, org.eevolution.model.I_PP_Order_Workflow.class, PP_Order_Workflow);
	}

	/** Set Manufacturing Order Workflow.
		@param PP_Order_Workflow_ID Manufacturing Order Workflow	  */
	@Override
	public void setPP_Order_Workflow_ID (int PP_Order_Workflow_ID)
	{
		if (PP_Order_Workflow_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_PP_Order_Workflow_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_PP_Order_Workflow_ID, Integer.valueOf(PP_Order_Workflow_ID));
	}

	/** Get Manufacturing Order Workflow.
		@return Manufacturing Order Workflow	  */
	@Override
	public int getPP_Order_Workflow_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PP_Order_Workflow_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Priorität.
		@param Priority 
		Indicates if this request is of a high, medium or low priority.
	  */
	@Override
	public void setPriority (int Priority)
	{
		set_Value (COLUMNNAME_Priority, Integer.valueOf(Priority));
	}

	/** Get Priorität.
		@return Indicates if this request is of a high, medium or low priority.
	  */
	@Override
	public int getPriority () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Priority);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Qty before close.
		@param QtyBeforeClose Qty before close	  */
	@Override
	public void setQtyBeforeClose (java.math.BigDecimal QtyBeforeClose)
	{
		set_Value (COLUMNNAME_QtyBeforeClose, QtyBeforeClose);
	}

	/** Get Qty before close.
		@return Qty before close	  */
	@Override
	public java.math.BigDecimal getQtyBeforeClose () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyBeforeClose);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Gelieferte Menge.
		@param QtyDelivered 
		Delivered Quantity
	  */
	@Override
	public void setQtyDelivered (java.math.BigDecimal QtyDelivered)
	{
		set_Value (COLUMNNAME_QtyDelivered, QtyDelivered);
	}

	/** Get Gelieferte Menge.
		@return Delivered Quantity
	  */
	@Override
	public java.math.BigDecimal getQtyDelivered () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyDelivered);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Qty Reject.
		@param QtyReject Qty Reject	  */
	@Override
	public void setQtyReject (java.math.BigDecimal QtyReject)
	{
		set_Value (COLUMNNAME_QtyReject, QtyReject);
	}

	/** Get Qty Reject.
		@return Qty Reject	  */
	@Override
	public java.math.BigDecimal getQtyReject () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyReject);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Qty Requiered.
		@param QtyRequiered Qty Requiered	  */
	@Override
	public void setQtyRequiered (java.math.BigDecimal QtyRequiered)
	{
		set_Value (COLUMNNAME_QtyRequiered, QtyRequiered);
	}

	/** Get Qty Requiered.
		@return Qty Requiered	  */
	@Override
	public java.math.BigDecimal getQtyRequiered () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyRequiered);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set QtyScrap.
		@param QtyScrap 
		Scrap Quantity for this componet
	  */
	@Override
	public void setQtyScrap (java.math.BigDecimal QtyScrap)
	{
		set_Value (COLUMNNAME_QtyScrap, QtyScrap);
	}

	/** Get QtyScrap.
		@return Scrap Quantity for this componet
	  */
	@Override
	public java.math.BigDecimal getQtyScrap () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyScrap);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Queuing Time.
		@param QueuingTime Queuing Time	  */
	@Override
	public void setQueuingTime (int QueuingTime)
	{
		set_Value (COLUMNNAME_QueuingTime, Integer.valueOf(QueuingTime));
	}

	/** Get Queuing Time.
		@return Queuing Time	  */
	@Override
	public int getQueuingTime () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_QueuingTime);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_S_Resource getS_Resource() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_S_Resource_ID, org.compiere.model.I_S_Resource.class);
	}

	@Override
	public void setS_Resource(org.compiere.model.I_S_Resource S_Resource)
	{
		set_ValueFromPO(COLUMNNAME_S_Resource_ID, org.compiere.model.I_S_Resource.class, S_Resource);
	}

	/** Set Ressource.
		@param S_Resource_ID 
		Resource
	  */
	@Override
	public void setS_Resource_ID (int S_Resource_ID)
	{
		if (S_Resource_ID < 1) 
			set_Value (COLUMNNAME_S_Resource_ID, null);
		else 
			set_Value (COLUMNNAME_S_Resource_ID, Integer.valueOf(S_Resource_ID));
	}

	/** Get Ressource.
		@return Resource
	  */
	@Override
	public int getS_Resource_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_S_Resource_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Setup Time.
		@param SetupTime 
		Setup time before starting Production
	  */
	@Override
	public void setSetupTime (int SetupTime)
	{
		set_Value (COLUMNNAME_SetupTime, Integer.valueOf(SetupTime));
	}

	/** Get Setup Time.
		@return Setup time before starting Production
	  */
	@Override
	public int getSetupTime () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SetupTime);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Setup Time Real.
		@param SetupTimeReal Setup Time Real	  */
	@Override
	public void setSetupTimeReal (int SetupTimeReal)
	{
		set_Value (COLUMNNAME_SetupTimeReal, Integer.valueOf(SetupTimeReal));
	}

	/** Get Setup Time Real.
		@return Setup Time Real	  */
	@Override
	public int getSetupTimeReal () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SetupTimeReal);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Setup Time Requiered.
		@param SetupTimeRequiered Setup Time Requiered	  */
	@Override
	public void setSetupTimeRequiered (int SetupTimeRequiered)
	{
		set_Value (COLUMNNAME_SetupTimeRequiered, Integer.valueOf(SetupTimeRequiered));
	}

	/** Get Setup Time Requiered.
		@return Setup Time Requiered	  */
	@Override
	public int getSetupTimeRequiered () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SetupTimeRequiered);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** 
	 * SplitElement AD_Reference_ID=301
	 * Reference name: WF_Join_Split
	 */
	public static final int SPLITELEMENT_AD_Reference_ID=301;
	/** AND = A */
	public static final String SPLITELEMENT_AND = "A";
	/** XOR = X */
	public static final String SPLITELEMENT_XOR = "X";
	/** Set Split Element.
		@param SplitElement 
		Semantics for multiple outgoing Transitions
	  */
	@Override
	public void setSplitElement (java.lang.String SplitElement)
	{

		set_Value (COLUMNNAME_SplitElement, SplitElement);
	}

	/** Get Split Element.
		@return Semantics for multiple outgoing Transitions
	  */
	@Override
	public java.lang.String getSplitElement () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_SplitElement);
	}

	/** 
	 * StartMode AD_Reference_ID=303
	 * Reference name: WF_Start-Finish Mode
	 */
	public static final int STARTMODE_AD_Reference_ID=303;
	/** Automatisch = A */
	public static final String STARTMODE_Automatisch = "A";
	/** Manuell = M */
	public static final String STARTMODE_Manuell = "M";
	/** Set Start Mode.
		@param StartMode 
		Workflow Activity Start Mode 
	  */
	@Override
	public void setStartMode (java.lang.String StartMode)
	{

		set_Value (COLUMNNAME_StartMode, StartMode);
	}

	/** Get Start Mode.
		@return Workflow Activity Start Mode 
	  */
	@Override
	public java.lang.String getStartMode () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_StartMode);
	}

	/** 
	 * SubflowExecution AD_Reference_ID=307
	 * Reference name: WF_SubFlow Execution
	 */
	public static final int SUBFLOWEXECUTION_AD_Reference_ID=307;
	/** Asynchronously = A */
	public static final String SUBFLOWEXECUTION_Asynchronously = "A";
	/** Synchronously = S */
	public static final String SUBFLOWEXECUTION_Synchronously = "S";
	/** Set Subflow Execution.
		@param SubflowExecution 
		Mode how the sub-workflow is executed
	  */
	@Override
	public void setSubflowExecution (java.lang.String SubflowExecution)
	{

		set_Value (COLUMNNAME_SubflowExecution, SubflowExecution);
	}

	/** Get Subflow Execution.
		@return Mode how the sub-workflow is executed
	  */
	@Override
	public java.lang.String getSubflowExecution () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_SubflowExecution);
	}

	/** Set Units by Cycles.
		@param UnitsCycles 
		The Units by Cycles are defined for process type  Flow Repetitive Dedicated and  indicated the product to be manufactured on a production line for duration unit.
	  */
	@Override
	public void setUnitsCycles (int UnitsCycles)
	{
		set_Value (COLUMNNAME_UnitsCycles, Integer.valueOf(UnitsCycles));
	}

	/** Get Units by Cycles.
		@return The Units by Cycles are defined for process type  Flow Repetitive Dedicated and  indicated the product to be manufactured on a production line for duration unit.
	  */
	@Override
	public int getUnitsCycles () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UnitsCycles);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Gültig ab.
		@param ValidFrom 
		Valid from including this date (first day)
	  */
	@Override
	public void setValidFrom (java.sql.Timestamp ValidFrom)
	{
		set_Value (COLUMNNAME_ValidFrom, ValidFrom);
	}

	/** Get Gültig ab.
		@return Valid from including this date (first day)
	  */
	@Override
	public java.sql.Timestamp getValidFrom () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_ValidFrom);
	}

	/** Set Gültig bis.
		@param ValidTo 
		Valid to including this date (last day)
	  */
	@Override
	public void setValidTo (java.sql.Timestamp ValidTo)
	{
		set_Value (COLUMNNAME_ValidTo, ValidTo);
	}

	/** Get Gültig bis.
		@return Valid to including this date (last day)
	  */
	@Override
	public java.sql.Timestamp getValidTo () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_ValidTo);
	}

	/** Set Suchschlüssel.
		@param Value 
		Search key for the record in the format required - must be unique
	  */
	@Override
	public void setValue (java.lang.String Value)
	{
		set_Value (COLUMNNAME_Value, Value);
	}

	/** Get Suchschlüssel.
		@return Search key for the record in the format required - must be unique
	  */
	@Override
	public java.lang.String getValue () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Value);
	}

	/** Set Waiting Time.
		@param WaitingTime 
		Workflow Simulation Waiting time
	  */
	@Override
	public void setWaitingTime (int WaitingTime)
	{
		set_Value (COLUMNNAME_WaitingTime, Integer.valueOf(WaitingTime));
	}

	/** Get Waiting Time.
		@return Workflow Simulation Waiting time
	  */
	@Override
	public int getWaitingTime () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_WaitingTime);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_AD_Workflow getWorkflow() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_Workflow_ID, org.compiere.model.I_AD_Workflow.class);
	}

	@Override
	public void setWorkflow(org.compiere.model.I_AD_Workflow Workflow)
	{
		set_ValueFromPO(COLUMNNAME_Workflow_ID, org.compiere.model.I_AD_Workflow.class, Workflow);
	}

	/** Set Workflow.
		@param Workflow_ID 
		Workflow or tasks
	  */
	@Override
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
	@Override
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
	@Override
	public void setWorkingTime (int WorkingTime)
	{
		set_Value (COLUMNNAME_WorkingTime, Integer.valueOf(WorkingTime));
	}

	/** Get Working Time.
		@return Workflow Simulation Execution Time
	  */
	@Override
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
	@Override
	public void setXPosition (int XPosition)
	{
		set_Value (COLUMNNAME_XPosition, Integer.valueOf(XPosition));
	}

	/** Get X Position.
		@return Absolute X (horizontal) position in 1/72 of an inch
	  */
	@Override
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
	@Override
	public void setYield (int Yield)
	{
		set_Value (COLUMNNAME_Yield, Integer.valueOf(Yield));
	}

	/** Get Yield %.
		@return The Yield is the percentage of a lot that is expected to be of acceptable wuality may fall below 100 percent
	  */
	@Override
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
	@Override
	public void setYPosition (int YPosition)
	{
		set_Value (COLUMNNAME_YPosition, Integer.valueOf(YPosition));
	}

	/** Get Y Position.
		@return Absolute Y (vertical) position in 1/72 of an inch
	  */
	@Override
	public int getYPosition () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_YPosition);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}