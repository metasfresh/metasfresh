/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.util.Env;

/** Generated Model for AD_WF_Node
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_AD_WF_Node extends org.compiere.model.PO implements I_AD_WF_Node, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 575783704L;

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
			setDurationLimit (0);
			setEntityType (null);
// 'U'
			setIsCentrallyMaintained (true);
// Y
			setJoinElement (null);
// X
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

	/** Set Knoten.
		@param AD_WF_Node_ID 
		Workflow Node (activity), step or process
	  */
	@Override
	public void setAD_WF_Node_ID (int AD_WF_Node_ID)
	{
		if (AD_WF_Node_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_WF_Node_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_WF_Node_ID, Integer.valueOf(AD_WF_Node_ID));
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
			set_ValueNoCheck (COLUMNNAME_AD_Workflow_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Workflow_ID, Integer.valueOf(AD_Workflow_ID));
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

	/** Set Dynamic Priority Change.
		@param DynPriorityChange 
		Change of priority when Activity is suspended waiting for user
	  */
	@Override
	public void setDynPriorityChange (java.math.BigDecimal DynPriorityChange)
	{
		set_Value (COLUMNNAME_DynPriorityChange, DynPriorityChange);
	}

	/** Get Dynamic Priority Change.
		@return Change of priority when Activity is suspended waiting for user
	  */
	@Override
	public java.math.BigDecimal getDynPriorityChange () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_DynPriorityChange);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** 
	 * DynPriorityUnit AD_Reference_ID=221
	 * Reference name: _Frequency Type
	 */
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
	@Override
	public void setDynPriorityUnit (java.lang.String DynPriorityUnit)
	{

		set_Value (COLUMNNAME_DynPriorityUnit, DynPriorityUnit);
	}

	/** Get Dynamic Priority Unit.
		@return Change of priority when Activity is suspended waiting for user
	  */
	@Override
	public java.lang.String getDynPriorityUnit () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DynPriorityUnit);
	}

	/** Set EMail.
		@param EMail 
		Electronic Mail Address
	  */
	@Override
	public void setEMail (java.lang.String EMail)
	{
		set_Value (COLUMNNAME_EMail, EMail);
	}

	/** Get EMail.
		@return Electronic Mail Address
	  */
	@Override
	public java.lang.String getEMail () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_EMail);
	}

	/** 
	 * EMailRecipient AD_Reference_ID=363
	 * Reference name: AD_WF_Node EMailRecipient
	 */
	public static final int EMAILRECIPIENT_AD_Reference_ID=363;
	/** DocumentOwner = D */
	public static final String EMAILRECIPIENT_DocumentOwner = "D";
	/** DocumentBusinessPartner = B */
	public static final String EMAILRECIPIENT_DocumentBusinessPartner = "B";
	/** WFResponsible = R */
	public static final String EMAILRECIPIENT_WFResponsible = "R";
	/** Set EMail Recipient.
		@param EMailRecipient 
		Recipient of the EMail
	  */
	@Override
	public void setEMailRecipient (java.lang.String EMailRecipient)
	{

		set_Value (COLUMNNAME_EMailRecipient, EMailRecipient);
	}

	/** Get EMail Recipient.
		@return Recipient of the EMail
	  */
	@Override
	public java.lang.String getEMailRecipient () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_EMailRecipient);
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
	public org.compiere.model.I_R_MailText getR_MailText() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_R_MailText_ID, org.compiere.model.I_R_MailText.class);
	}

	@Override
	public void setR_MailText(org.compiere.model.I_R_MailText R_MailText)
	{
		set_ValueFromPO(COLUMNNAME_R_MailText_ID, org.compiere.model.I_R_MailText.class, R_MailText);
	}

	/** Set EMail-Vorlage.
		@param R_MailText_ID 
		Text templates for mailings
	  */
	@Override
	public void setR_MailText_ID (int R_MailText_ID)
	{
		if (R_MailText_ID < 1) 
			set_Value (COLUMNNAME_R_MailText_ID, null);
		else 
			set_Value (COLUMNNAME_R_MailText_ID, Integer.valueOf(R_MailText_ID));
	}

	/** Get EMail-Vorlage.
		@return Text templates for mailings
	  */
	@Override
	public int getR_MailText_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_R_MailText_ID);
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
	public void setUnitsCycles (java.math.BigDecimal UnitsCycles)
	{
		set_Value (COLUMNNAME_UnitsCycles, UnitsCycles);
	}

	/** Get Units by Cycles.
		@return The Units by Cycles are defined for process type  Flow Repetitive Dedicated and  indicated the product to be manufactured on a production line for duration unit.
	  */
	@Override
	public java.math.BigDecimal getUnitsCycles () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_UnitsCycles);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

	/** Set Wait Time.
		@param WaitTime 
		Time in minutes to wait (sleep)
	  */
	@Override
	public void setWaitTime (int WaitTime)
	{
		set_Value (COLUMNNAME_WaitTime, Integer.valueOf(WaitTime));
	}

	/** Get Wait Time.
		@return Time in minutes to wait (sleep)
	  */
	@Override
	public int getWaitTime () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_WaitTime);
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