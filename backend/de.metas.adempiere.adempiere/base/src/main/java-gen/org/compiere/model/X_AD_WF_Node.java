// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for AD_WF_Node
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_AD_WF_Node extends org.compiere.model.PO implements I_AD_WF_Node, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1041809730L;

    /** Standard Constructor */
    public X_AD_WF_Node (final Properties ctx, final int AD_WF_Node_ID, @Nullable final String trxName)
    {
      super (ctx, AD_WF_Node_ID, trxName);
    }

    /** Load Constructor */
    public X_AD_WF_Node (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
    {
      super (ctx, rs, trxName);
    }


	/** Load Meta Data */
	@Override
	protected org.compiere.model.POInfo initPO(final Properties ctx)
	{
		return org.compiere.model.POInfo.getPOInfo(Table_Name);
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
	@Override
	public void setAction (final java.lang.String Action)
	{
		set_Value (COLUMNNAME_Action, Action);
	}

	@Override
	public java.lang.String getAction() 
	{
		return get_ValueAsString(COLUMNNAME_Action);
	}

	@Override
	public org.compiere.model.I_AD_Column getAD_Column()
	{
		return get_ValueAsPO(COLUMNNAME_AD_Column_ID, org.compiere.model.I_AD_Column.class);
	}

	@Override
	public void setAD_Column(final org.compiere.model.I_AD_Column AD_Column)
	{
		set_ValueFromPO(COLUMNNAME_AD_Column_ID, org.compiere.model.I_AD_Column.class, AD_Column);
	}

	@Override
	public void setAD_Column_ID (final int AD_Column_ID)
	{
		if (AD_Column_ID < 1) 
			set_Value (COLUMNNAME_AD_Column_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Column_ID, AD_Column_ID);
	}

	@Override
	public int getAD_Column_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Column_ID);
	}

	@Override
	public org.compiere.model.I_AD_Form getAD_Form()
	{
		return get_ValueAsPO(COLUMNNAME_AD_Form_ID, org.compiere.model.I_AD_Form.class);
	}

	@Override
	public void setAD_Form(final org.compiere.model.I_AD_Form AD_Form)
	{
		set_ValueFromPO(COLUMNNAME_AD_Form_ID, org.compiere.model.I_AD_Form.class, AD_Form);
	}

	@Override
	public void setAD_Form_ID (final int AD_Form_ID)
	{
		if (AD_Form_ID < 1) 
			set_Value (COLUMNNAME_AD_Form_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Form_ID, AD_Form_ID);
	}

	@Override
	public int getAD_Form_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Form_ID);
	}

	@Override
	public org.compiere.model.I_AD_Image getAD_Image()
	{
		return get_ValueAsPO(COLUMNNAME_AD_Image_ID, org.compiere.model.I_AD_Image.class);
	}

	@Override
	public void setAD_Image(final org.compiere.model.I_AD_Image AD_Image)
	{
		set_ValueFromPO(COLUMNNAME_AD_Image_ID, org.compiere.model.I_AD_Image.class, AD_Image);
	}

	@Override
	public void setAD_Image_ID (final int AD_Image_ID)
	{
		if (AD_Image_ID < 1) 
			set_Value (COLUMNNAME_AD_Image_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Image_ID, AD_Image_ID);
	}

	@Override
	public int getAD_Image_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Image_ID);
	}

	@Override
	public org.compiere.model.I_AD_Process getAD_Process()
	{
		return get_ValueAsPO(COLUMNNAME_AD_Process_ID, org.compiere.model.I_AD_Process.class);
	}

	@Override
	public void setAD_Process(final org.compiere.model.I_AD_Process AD_Process)
	{
		set_ValueFromPO(COLUMNNAME_AD_Process_ID, org.compiere.model.I_AD_Process.class, AD_Process);
	}

	@Override
	public void setAD_Process_ID (final int AD_Process_ID)
	{
		if (AD_Process_ID < 1) 
			set_Value (COLUMNNAME_AD_Process_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Process_ID, AD_Process_ID);
	}

	@Override
	public int getAD_Process_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Process_ID);
	}

	@Override
	public org.compiere.model.I_AD_Task getAD_Task()
	{
		return get_ValueAsPO(COLUMNNAME_AD_Task_ID, org.compiere.model.I_AD_Task.class);
	}

	@Override
	public void setAD_Task(final org.compiere.model.I_AD_Task AD_Task)
	{
		set_ValueFromPO(COLUMNNAME_AD_Task_ID, org.compiere.model.I_AD_Task.class, AD_Task);
	}

	@Override
	public void setAD_Task_ID (final int AD_Task_ID)
	{
		if (AD_Task_ID < 1) 
			set_Value (COLUMNNAME_AD_Task_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Task_ID, AD_Task_ID);
	}

	@Override
	public int getAD_Task_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Task_ID);
	}

	@Override
	public void setAD_WF_Block_ID (final int AD_WF_Block_ID)
	{
		if (AD_WF_Block_ID < 1) 
			set_Value (COLUMNNAME_AD_WF_Block_ID, null);
		else 
			set_Value (COLUMNNAME_AD_WF_Block_ID, AD_WF_Block_ID);
	}

	@Override
	public int getAD_WF_Block_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_WF_Block_ID);
	}

	@Override
	public void setAD_WF_Node_ID (final int AD_WF_Node_ID)
	{
		if (AD_WF_Node_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_WF_Node_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_WF_Node_ID, AD_WF_Node_ID);
	}

	@Override
	public int getAD_WF_Node_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_WF_Node_ID);
	}

	@Override
	public void setAD_WF_Node_Template_ID (final int AD_WF_Node_Template_ID)
	{
		if (AD_WF_Node_Template_ID < 1) 
			set_Value (COLUMNNAME_AD_WF_Node_Template_ID, null);
		else 
			set_Value (COLUMNNAME_AD_WF_Node_Template_ID, AD_WF_Node_Template_ID);
	}

	@Override
	public int getAD_WF_Node_Template_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_WF_Node_Template_ID);
	}

	@Override
	public void setAD_WF_Responsible_ID (final int AD_WF_Responsible_ID)
	{
		if (AD_WF_Responsible_ID < 1) 
			set_Value (COLUMNNAME_AD_WF_Responsible_ID, null);
		else 
			set_Value (COLUMNNAME_AD_WF_Responsible_ID, AD_WF_Responsible_ID);
	}

	@Override
	public int getAD_WF_Responsible_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_WF_Responsible_ID);
	}

	@Override
	public org.compiere.model.I_AD_Window getAD_Window()
	{
		return get_ValueAsPO(COLUMNNAME_AD_Window_ID, org.compiere.model.I_AD_Window.class);
	}

	@Override
	public void setAD_Window(final org.compiere.model.I_AD_Window AD_Window)
	{
		set_ValueFromPO(COLUMNNAME_AD_Window_ID, org.compiere.model.I_AD_Window.class, AD_Window);
	}

	@Override
	public void setAD_Window_ID (final int AD_Window_ID)
	{
		if (AD_Window_ID < 1) 
			set_Value (COLUMNNAME_AD_Window_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Window_ID, AD_Window_ID);
	}

	@Override
	public int getAD_Window_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Window_ID);
	}

	@Override
	public void setAD_Workflow_ID (final int AD_Workflow_ID)
	{
		if (AD_Workflow_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Workflow_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Workflow_ID, AD_Workflow_ID);
	}

	@Override
	public int getAD_Workflow_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Workflow_ID);
	}

	/** 
	 * ApprovalStrategy AD_Reference_ID=541818
	 * Reference name: ApprovalStrategy
	 */
	public static final int APPROVALSTRATEGY_AD_Reference_ID=541818;
	/** Standard = STD */
	public static final String APPROVALSTRATEGY_Standard = "STD";
	/** Requestor Hierarcy / Project Manager + CTO = RH+PM+CTO */
	public static final String APPROVALSTRATEGY_RequestorHierarcyProjectManagerPlusCTO = "RH+PM+CTO";
	@Override
	public void setApprovalStrategy (final @Nullable java.lang.String ApprovalStrategy)
	{
		set_Value (COLUMNNAME_ApprovalStrategy, ApprovalStrategy);
	}

	@Override
	public java.lang.String getApprovalStrategy() 
	{
		return get_ValueAsString(COLUMNNAME_ApprovalStrategy);
	}

	@Override
	public void setAttributeName (final @Nullable java.lang.String AttributeName)
	{
		set_Value (COLUMNNAME_AttributeName, AttributeName);
	}

	@Override
	public java.lang.String getAttributeName() 
	{
		return get_ValueAsString(COLUMNNAME_AttributeName);
	}

	@Override
	public void setAttributeValue (final @Nullable java.lang.String AttributeValue)
	{
		set_Value (COLUMNNAME_AttributeValue, AttributeValue);
	}

	@Override
	public java.lang.String getAttributeValue() 
	{
		return get_ValueAsString(COLUMNNAME_AttributeValue);
	}

	@Override
	public void setC_BPartner_ID (final int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_ID, C_BPartner_ID);
	}

	@Override
	public int getC_BPartner_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_ID);
	}

	@Override
	public void setCost (final BigDecimal Cost)
	{
		set_Value (COLUMNNAME_Cost, Cost);
	}

	@Override
	public BigDecimal getCost() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_Cost);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setDescription (final @Nullable java.lang.String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	@Override
	public java.lang.String getDescription() 
	{
		return get_ValueAsString(COLUMNNAME_Description);
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
	/** UnClose = UC */
	public static final String DOCACTION_UnClose = "UC";
	@Override
	public void setDocAction (final @Nullable java.lang.String DocAction)
	{
		set_Value (COLUMNNAME_DocAction, DocAction);
	}

	@Override
	public java.lang.String getDocAction() 
	{
		return get_ValueAsString(COLUMNNAME_DocAction);
	}

	@Override
	public void setDuration (final int Duration)
	{
		set_Value (COLUMNNAME_Duration, Duration);
	}

	@Override
	public int getDuration() 
	{
		return get_ValueAsInt(COLUMNNAME_Duration);
	}

	@Override
	public void setDurationLimit (final int DurationLimit)
	{
		set_Value (COLUMNNAME_DurationLimit, DurationLimit);
	}

	@Override
	public int getDurationLimit() 
	{
		return get_ValueAsInt(COLUMNNAME_DurationLimit);
	}

	@Override
	public void setDynPriorityChange (final @Nullable BigDecimal DynPriorityChange)
	{
		set_Value (COLUMNNAME_DynPriorityChange, DynPriorityChange);
	}

	@Override
	public BigDecimal getDynPriorityChange() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_DynPriorityChange);
		return bd != null ? bd : BigDecimal.ZERO;
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
	@Override
	public void setDynPriorityUnit (final @Nullable java.lang.String DynPriorityUnit)
	{
		set_Value (COLUMNNAME_DynPriorityUnit, DynPriorityUnit);
	}

	@Override
	public java.lang.String getDynPriorityUnit() 
	{
		return get_ValueAsString(COLUMNNAME_DynPriorityUnit);
	}

	@Override
	public void setEMail (final @Nullable java.lang.String EMail)
	{
		set_Value (COLUMNNAME_EMail, EMail);
	}

	@Override
	public java.lang.String getEMail() 
	{
		return get_ValueAsString(COLUMNNAME_EMail);
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
	@Override
	public void setEMailRecipient (final @Nullable java.lang.String EMailRecipient)
	{
		set_Value (COLUMNNAME_EMailRecipient, EMailRecipient);
	}

	@Override
	public java.lang.String getEMailRecipient() 
	{
		return get_ValueAsString(COLUMNNAME_EMailRecipient);
	}

	/** 
	 * EntityType AD_Reference_ID=389
	 * Reference name: _EntityTypeNew
	 */
	public static final int ENTITYTYPE_AD_Reference_ID=389;
	@Override
	public void setEntityType (final java.lang.String EntityType)
	{
		set_Value (COLUMNNAME_EntityType, EntityType);
	}

	@Override
	public java.lang.String getEntityType() 
	{
		return get_ValueAsString(COLUMNNAME_EntityType);
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
	@Override
	public void setFinishMode (final @Nullable java.lang.String FinishMode)
	{
		set_Value (COLUMNNAME_FinishMode, FinishMode);
	}

	@Override
	public java.lang.String getFinishMode() 
	{
		return get_ValueAsString(COLUMNNAME_FinishMode);
	}

	@Override
	public void setHelp (final @Nullable java.lang.String Help)
	{
		set_Value (COLUMNNAME_Help, Help);
	}

	@Override
	public java.lang.String getHelp() 
	{
		return get_ValueAsString(COLUMNNAME_Help);
	}

	@Override
	public void setIsCentrallyMaintained (final boolean IsCentrallyMaintained)
	{
		set_Value (COLUMNNAME_IsCentrallyMaintained, IsCentrallyMaintained);
	}

	@Override
	public boolean isCentrallyMaintained() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsCentrallyMaintained);
	}

	@Override
	public void setIsMilestone (final boolean IsMilestone)
	{
		set_Value (COLUMNNAME_IsMilestone, IsMilestone);
	}

	@Override
	public boolean isMilestone() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsMilestone);
	}

	@Override
	public void setIsSubcontracting (final boolean IsSubcontracting)
	{
		set_Value (COLUMNNAME_IsSubcontracting, IsSubcontracting);
	}

	@Override
	public boolean isSubcontracting() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsSubcontracting);
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
	@Override
	public void setJoinElement (final java.lang.String JoinElement)
	{
		set_Value (COLUMNNAME_JoinElement, JoinElement);
	}

	@Override
	public java.lang.String getJoinElement() 
	{
		return get_ValueAsString(COLUMNNAME_JoinElement);
	}

	@Override
	public void setMovingTime (final int MovingTime)
	{
		set_Value (COLUMNNAME_MovingTime, MovingTime);
	}

	@Override
	public int getMovingTime() 
	{
		return get_ValueAsInt(COLUMNNAME_MovingTime);
	}

	@Override
	public void setName (final java.lang.String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	@Override
	public java.lang.String getName() 
	{
		return get_ValueAsString(COLUMNNAME_Name);
	}

	@Override
	public void setOverlapUnits (final int OverlapUnits)
	{
		set_Value (COLUMNNAME_OverlapUnits, OverlapUnits);
	}

	@Override
	public int getOverlapUnits() 
	{
		return get_ValueAsInt(COLUMNNAME_OverlapUnits);
	}

	/** 
	 * PP_Activity_Type AD_Reference_ID=541463
	 * Reference name: PP_Activity_Type
	 */
	public static final int PP_ACTIVITY_TYPE_AD_Reference_ID=541463;
	/** RawMaterialsIssue = MI */
	public static final String PP_ACTIVITY_TYPE_RawMaterialsIssue = "MI";
	/** MaterialReceipt = MR */
	public static final String PP_ACTIVITY_TYPE_MaterialReceipt = "MR";
	/** WorkReport = WR */
	public static final String PP_ACTIVITY_TYPE_WorkReport = "WR";
	/** ActivityConfirmation = AC */
	public static final String PP_ACTIVITY_TYPE_ActivityConfirmation = "AC";
	/** Generate HU QR Codes = GenerateHUQRCodes */
	public static final String PP_ACTIVITY_TYPE_GenerateHUQRCodes = "GenerateHUQRCodes";
	/** ScanScaleDevice = ScanScaleDevice */
	public static final String PP_ACTIVITY_TYPE_ScanScaleDevice = "ScanScaleDevice";
	/** RawMaterialsIssueAdjustment = MIA */
	public static final String PP_ACTIVITY_TYPE_RawMaterialsIssueAdjustment = "MIA";
	/** CallExternalSystem = CallExternalSystem */
	public static final String PP_ACTIVITY_TYPE_CallExternalSystem = "CallExternalSystem";
	/** PrintReceivedHUQRCodes = PrintReceivedHUQRCodes */
	public static final String PP_ACTIVITY_TYPE_PrintReceivedHUQRCodes = "PrintReceivedHUQRCodes";
	@Override
	public void setPP_Activity_Type (final @Nullable java.lang.String PP_Activity_Type)
	{
		set_Value (COLUMNNAME_PP_Activity_Type, PP_Activity_Type);
	}

	@Override
	public java.lang.String getPP_Activity_Type() 
	{
		return get_ValueAsString(COLUMNNAME_PP_Activity_Type);
	}

	/** 
	 * PP_AlwaysAvailableToUser AD_Reference_ID=319
	 * Reference name: _YesNo
	 */
	public static final int PP_ALWAYSAVAILABLETOUSER_AD_Reference_ID=319;
	/** Yes = Y */
	public static final String PP_ALWAYSAVAILABLETOUSER_Yes = "Y";
	/** No = N */
	public static final String PP_ALWAYSAVAILABLETOUSER_No = "N";
	@Override
	public void setPP_AlwaysAvailableToUser (final @Nullable java.lang.String PP_AlwaysAvailableToUser)
	{
		set_Value (COLUMNNAME_PP_AlwaysAvailableToUser, PP_AlwaysAvailableToUser);
	}

	@Override
	public java.lang.String getPP_AlwaysAvailableToUser() 
	{
		return get_ValueAsString(COLUMNNAME_PP_AlwaysAvailableToUser);
	}

	@Override
	public void setPP_UserInstructions (final @Nullable java.lang.String PP_UserInstructions)
	{
		set_Value (COLUMNNAME_PP_UserInstructions, PP_UserInstructions);
	}

	@Override
	public java.lang.String getPP_UserInstructions() 
	{
		return get_ValueAsString(COLUMNNAME_PP_UserInstructions);
	}

	@Override
	public void setPriority (final int Priority)
	{
		set_Value (COLUMNNAME_Priority, Priority);
	}

	@Override
	public int getPriority() 
	{
		return get_ValueAsInt(COLUMNNAME_Priority);
	}

	@Override
	public void setQueuingTime (final int QueuingTime)
	{
		set_Value (COLUMNNAME_QueuingTime, QueuingTime);
	}

	@Override
	public int getQueuingTime() 
	{
		return get_ValueAsInt(COLUMNNAME_QueuingTime);
	}

	@Override
	public org.compiere.model.I_R_MailText getR_MailText()
	{
		return get_ValueAsPO(COLUMNNAME_R_MailText_ID, org.compiere.model.I_R_MailText.class);
	}

	@Override
	public void setR_MailText(final org.compiere.model.I_R_MailText R_MailText)
	{
		set_ValueFromPO(COLUMNNAME_R_MailText_ID, org.compiere.model.I_R_MailText.class, R_MailText);
	}

	@Override
	public void setR_MailText_ID (final int R_MailText_ID)
	{
		if (R_MailText_ID < 1) 
			set_Value (COLUMNNAME_R_MailText_ID, null);
		else 
			set_Value (COLUMNNAME_R_MailText_ID, R_MailText_ID);
	}

	@Override
	public int getR_MailText_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_R_MailText_ID);
	}

	@Override
	public void setSetupTime (final int SetupTime)
	{
		set_Value (COLUMNNAME_SetupTime, SetupTime);
	}

	@Override
	public int getSetupTime() 
	{
		return get_ValueAsInt(COLUMNNAME_SetupTime);
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
	@Override
	public void setSplitElement (final java.lang.String SplitElement)
	{
		set_Value (COLUMNNAME_SplitElement, SplitElement);
	}

	@Override
	public java.lang.String getSplitElement() 
	{
		return get_ValueAsString(COLUMNNAME_SplitElement);
	}

	@Override
	public void setS_Resource_ID (final int S_Resource_ID)
	{
		if (S_Resource_ID < 1) 
			set_Value (COLUMNNAME_S_Resource_ID, null);
		else 
			set_Value (COLUMNNAME_S_Resource_ID, S_Resource_ID);
	}

	@Override
	public int getS_Resource_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_S_Resource_ID);
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
	@Override
	public void setStartMode (final @Nullable java.lang.String StartMode)
	{
		set_Value (COLUMNNAME_StartMode, StartMode);
	}

	@Override
	public java.lang.String getStartMode() 
	{
		return get_ValueAsString(COLUMNNAME_StartMode);
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
	@Override
	public void setSubflowExecution (final @Nullable java.lang.String SubflowExecution)
	{
		set_Value (COLUMNNAME_SubflowExecution, SubflowExecution);
	}

	@Override
	public java.lang.String getSubflowExecution() 
	{
		return get_ValueAsString(COLUMNNAME_SubflowExecution);
	}

	@Override
	public void setUnitsCycles (final @Nullable BigDecimal UnitsCycles)
	{
		set_Value (COLUMNNAME_UnitsCycles, UnitsCycles);
	}

	@Override
	public BigDecimal getUnitsCycles() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_UnitsCycles);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setValidFrom (final @Nullable java.sql.Timestamp ValidFrom)
	{
		set_Value (COLUMNNAME_ValidFrom, ValidFrom);
	}

	@Override
	public java.sql.Timestamp getValidFrom() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_ValidFrom);
	}

	@Override
	public void setValidTo (final @Nullable java.sql.Timestamp ValidTo)
	{
		set_Value (COLUMNNAME_ValidTo, ValidTo);
	}

	@Override
	public java.sql.Timestamp getValidTo() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_ValidTo);
	}

	@Override
	public void setValue (final java.lang.String Value)
	{
		set_Value (COLUMNNAME_Value, Value);
	}

	@Override
	public java.lang.String getValue() 
	{
		return get_ValueAsString(COLUMNNAME_Value);
	}

	@Override
	public void setWaitingTime (final int WaitingTime)
	{
		set_Value (COLUMNNAME_WaitingTime, WaitingTime);
	}

	@Override
	public int getWaitingTime() 
	{
		return get_ValueAsInt(COLUMNNAME_WaitingTime);
	}

	@Override
	public void setWaitTime (final int WaitTime)
	{
		set_Value (COLUMNNAME_WaitTime, WaitTime);
	}

	@Override
	public int getWaitTime() 
	{
		return get_ValueAsInt(COLUMNNAME_WaitTime);
	}

	@Override
	public void setWorkflow_ID (final int Workflow_ID)
	{
		if (Workflow_ID < 1) 
			set_Value (COLUMNNAME_Workflow_ID, null);
		else 
			set_Value (COLUMNNAME_Workflow_ID, Workflow_ID);
	}

	@Override
	public int getWorkflow_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Workflow_ID);
	}

	@Override
	public void setWorkingTime (final int WorkingTime)
	{
		set_Value (COLUMNNAME_WorkingTime, WorkingTime);
	}

	@Override
	public int getWorkingTime() 
	{
		return get_ValueAsInt(COLUMNNAME_WorkingTime);
	}

	@Override
	public void setXPosition (final int XPosition)
	{
		set_Value (COLUMNNAME_XPosition, XPosition);
	}

	@Override
	public int getXPosition() 
	{
		return get_ValueAsInt(COLUMNNAME_XPosition);
	}

	@Override
	public void setYield (final int Yield)
	{
		set_Value (COLUMNNAME_Yield, Yield);
	}

	@Override
	public int getYield() 
	{
		return get_ValueAsInt(COLUMNNAME_Yield);
	}

	@Override
	public void setYPosition (final int YPosition)
	{
		set_Value (COLUMNNAME_YPosition, YPosition);
	}

	@Override
	public int getYPosition() 
	{
		return get_ValueAsInt(COLUMNNAME_YPosition);
	}
}