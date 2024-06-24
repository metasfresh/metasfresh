// Generated Model - DO NOT CHANGE
package org.eevolution.model;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for PP_Order_Node
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_PP_Order_Node extends org.compiere.model.PO implements I_PP_Order_Node, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1712971244L;

    /** Standard Constructor */
    public X_PP_Order_Node (final Properties ctx, final int PP_Order_Node_ID, @Nullable final String trxName)
    {
      super (ctx, PP_Order_Node_ID, trxName);
    }

    /** Load Constructor */
    public X_PP_Order_Node (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
    {
      super (ctx, rs, trxName);
    }


	/** Load Meta Data */
	@Override
	protected org.compiere.model.POInfo initPO(final Properties ctx)
	{
		return org.compiere.model.POInfo.getPOInfo(Table_Name);
	}

	@Override
	public void setAD_WF_Node_ID (final int AD_WF_Node_ID)
	{
		if (AD_WF_Node_ID < 1) 
			set_Value (COLUMNNAME_AD_WF_Node_ID, null);
		else 
			set_Value (COLUMNNAME_AD_WF_Node_ID, AD_WF_Node_ID);
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
	public void setAD_Workflow_ID (final int AD_Workflow_ID)
	{
		if (AD_Workflow_ID < 1) 
			set_Value (COLUMNNAME_AD_Workflow_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Workflow_ID, AD_Workflow_ID);
	}

	@Override
	public int getAD_Workflow_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Workflow_ID);
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
	public void setC_UOM_ID (final int C_UOM_ID)
	{
		if (C_UOM_ID < 1) 
			set_Value (COLUMNNAME_C_UOM_ID, null);
		else 
			set_Value (COLUMNNAME_C_UOM_ID, C_UOM_ID);
	}

	@Override
	public int getC_UOM_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_UOM_ID);
	}

	@Override
	public void setDateFinish (final @Nullable java.sql.Timestamp DateFinish)
	{
		set_Value (COLUMNNAME_DateFinish, DateFinish);
	}

	@Override
	public java.sql.Timestamp getDateFinish() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DateFinish);
	}

	@Override
	public void setDateFinishSchedule (final @Nullable java.sql.Timestamp DateFinishSchedule)
	{
		set_Value (COLUMNNAME_DateFinishSchedule, DateFinishSchedule);
	}

	@Override
	public java.sql.Timestamp getDateFinishSchedule() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DateFinishSchedule);
	}

	@Override
	public void setDateStart (final @Nullable java.sql.Timestamp DateStart)
	{
		set_Value (COLUMNNAME_DateStart, DateStart);
	}

	@Override
	public java.sql.Timestamp getDateStart() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DateStart);
	}

	@Override
	public void setDateStartSchedule (final @Nullable java.sql.Timestamp DateStartSchedule)
	{
		set_Value (COLUMNNAME_DateStartSchedule, DateStartSchedule);
	}

	@Override
	public java.sql.Timestamp getDateStartSchedule() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DateStartSchedule);
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
	@Override
	public void setDocStatus (final @Nullable java.lang.String DocStatus)
	{
		set_Value (COLUMNNAME_DocStatus, DocStatus);
	}

	@Override
	public java.lang.String getDocStatus() 
	{
		return get_ValueAsString(COLUMNNAME_DocStatus);
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
	public void setDurationReal (final int DurationReal)
	{
		set_Value (COLUMNNAME_DurationReal, DurationReal);
	}

	@Override
	public int getDurationReal() 
	{
		return get_ValueAsInt(COLUMNNAME_DurationReal);
	}

	@Override
	public void setDurationRequiered (final int DurationRequiered)
	{
		set_Value (COLUMNNAME_DurationRequiered, DurationRequiered);
	}

	@Override
	public int getDurationRequiered() 
	{
		return get_ValueAsInt(COLUMNNAME_DurationRequiered);
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
	@Override
	public void setPP_Activity_Type (final java.lang.String PP_Activity_Type)
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
	public org.eevolution.model.I_PP_Order getPP_Order()
	{
		return get_ValueAsPO(COLUMNNAME_PP_Order_ID, org.eevolution.model.I_PP_Order.class);
	}

	@Override
	public void setPP_Order(final org.eevolution.model.I_PP_Order PP_Order)
	{
		set_ValueFromPO(COLUMNNAME_PP_Order_ID, org.eevolution.model.I_PP_Order.class, PP_Order);
	}

	@Override
	public void setPP_Order_ID (final int PP_Order_ID)
	{
		if (PP_Order_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_PP_Order_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_PP_Order_ID, PP_Order_ID);
	}

	@Override
	public int getPP_Order_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_PP_Order_ID);
	}

	@Override
	public void setPP_Order_Node_ID (final int PP_Order_Node_ID)
	{
		if (PP_Order_Node_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_PP_Order_Node_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_PP_Order_Node_ID, PP_Order_Node_ID);
	}

	@Override
	public int getPP_Order_Node_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_PP_Order_Node_ID);
	}

	@Override
	public org.eevolution.model.I_PP_Order_Workflow getPP_Order_Workflow()
	{
		return get_ValueAsPO(COLUMNNAME_PP_Order_Workflow_ID, org.eevolution.model.I_PP_Order_Workflow.class);
	}

	@Override
	public void setPP_Order_Workflow(final org.eevolution.model.I_PP_Order_Workflow PP_Order_Workflow)
	{
		set_ValueFromPO(COLUMNNAME_PP_Order_Workflow_ID, org.eevolution.model.I_PP_Order_Workflow.class, PP_Order_Workflow);
	}

	@Override
	public void setPP_Order_Workflow_ID (final int PP_Order_Workflow_ID)
	{
		if (PP_Order_Workflow_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_PP_Order_Workflow_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_PP_Order_Workflow_ID, PP_Order_Workflow_ID);
	}

	@Override
	public int getPP_Order_Workflow_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_PP_Order_Workflow_ID);
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
	public void setQtyDelivered (final @Nullable BigDecimal QtyDelivered)
	{
		set_Value (COLUMNNAME_QtyDelivered, QtyDelivered);
	}

	@Override
	public BigDecimal getQtyDelivered() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyDelivered);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyReject (final @Nullable BigDecimal QtyReject)
	{
		set_Value (COLUMNNAME_QtyReject, QtyReject);
	}

	@Override
	public BigDecimal getQtyReject() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyReject);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyRequiered (final @Nullable BigDecimal QtyRequiered)
	{
		set_Value (COLUMNNAME_QtyRequiered, QtyRequiered);
	}

	@Override
	public BigDecimal getQtyRequiered() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyRequiered);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyScrap (final @Nullable BigDecimal QtyScrap)
	{
		set_Value (COLUMNNAME_QtyScrap, QtyScrap);
	}

	@Override
	public BigDecimal getQtyScrap() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyScrap);
		return bd != null ? bd : BigDecimal.ZERO;
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
	public org.compiere.model.I_S_Resource getS_Resource()
	{
		return get_ValueAsPO(COLUMNNAME_S_Resource_ID, org.compiere.model.I_S_Resource.class);
	}

	@Override
	public void setS_Resource(final org.compiere.model.I_S_Resource S_Resource)
	{
		set_ValueFromPO(COLUMNNAME_S_Resource_ID, org.compiere.model.I_S_Resource.class, S_Resource);
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

	@Override
	public void setScannedQRCode (final @Nullable java.lang.String ScannedQRCode)
	{
		set_Value (COLUMNNAME_ScannedQRCode, ScannedQRCode);
	}

	@Override
	public java.lang.String getScannedQRCode()
	{
		return get_ValueAsString(COLUMNNAME_ScannedQRCode);
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

	@Override
	public void setSetupTimeReal (final int SetupTimeReal)
	{
		set_Value (COLUMNNAME_SetupTimeReal, SetupTimeReal);
	}

	@Override
	public int getSetupTimeReal() 
	{
		return get_ValueAsInt(COLUMNNAME_SetupTimeReal);
	}

	@Override
	public void setSetupTimeRequiered (final int SetupTimeRequiered)
	{
		set_Value (COLUMNNAME_SetupTimeRequiered, SetupTimeRequiered);
	}

	@Override
	public int getSetupTimeRequiered() 
	{
		return get_ValueAsInt(COLUMNNAME_SetupTimeRequiered);
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
	public void setYield (final int Yield)
	{
		set_Value (COLUMNNAME_Yield, Yield);
	}

	@Override
	public int getYield() 
	{
		return get_ValueAsInt(COLUMNNAME_Yield);
	}
}