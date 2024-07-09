// Generated Model - DO NOT CHANGE
package de.metas.contracts.model;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_Flatrate_Conditions
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_Flatrate_Conditions extends org.compiere.model.PO implements I_C_Flatrate_Conditions, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1611038437L;

    /** Standard Constructor */
    public X_C_Flatrate_Conditions (final Properties ctx, final int C_Flatrate_Conditions_ID, @Nullable final String trxName)
    {
      super (ctx, C_Flatrate_Conditions_ID, trxName);
    }

    /** Load Constructor */
    public X_C_Flatrate_Conditions (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setC_Customer_Trade_Margin_ID (final int C_Customer_Trade_Margin_ID)
	{
		if (C_Customer_Trade_Margin_ID < 1) 
			set_Value (COLUMNNAME_C_Customer_Trade_Margin_ID, null);
		else 
			set_Value (COLUMNNAME_C_Customer_Trade_Margin_ID, C_Customer_Trade_Margin_ID);
	}

	@Override
	public int getC_Customer_Trade_Margin_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Customer_Trade_Margin_ID);
	}

	@Override
	public void setC_Flatrate_Conditions_ID (final int C_Flatrate_Conditions_ID)
	{
		if (C_Flatrate_Conditions_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Flatrate_Conditions_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Flatrate_Conditions_ID, C_Flatrate_Conditions_ID);
	}

	@Override
	public int getC_Flatrate_Conditions_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Flatrate_Conditions_ID);
	}

	@Override
	public void setC_Flatrate_Matching_IncludedT (final @Nullable java.lang.String C_Flatrate_Matching_IncludedT)
	{
		throw new IllegalArgumentException ("C_Flatrate_Matching_IncludedT is virtual column");	}

	@Override
	public java.lang.String getC_Flatrate_Matching_IncludedT() 
	{
		return get_ValueAsString(COLUMNNAME_C_Flatrate_Matching_IncludedT);
	}

	@Override
	public de.metas.contracts.model.I_C_Flatrate_Transition getC_Flatrate_Transition()
	{
		return get_ValueAsPO(COLUMNNAME_C_Flatrate_Transition_ID, de.metas.contracts.model.I_C_Flatrate_Transition.class);
	}

	@Override
	public void setC_Flatrate_Transition(final de.metas.contracts.model.I_C_Flatrate_Transition C_Flatrate_Transition)
	{
		set_ValueFromPO(COLUMNNAME_C_Flatrate_Transition_ID, de.metas.contracts.model.I_C_Flatrate_Transition.class, C_Flatrate_Transition);
	}

	@Override
	public void setC_Flatrate_Transition_ID (final int C_Flatrate_Transition_ID)
	{
		if (C_Flatrate_Transition_ID < 1) 
			set_Value (COLUMNNAME_C_Flatrate_Transition_ID, null);
		else 
			set_Value (COLUMNNAME_C_Flatrate_Transition_ID, C_Flatrate_Transition_ID);
	}

	@Override
	public int getC_Flatrate_Transition_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Flatrate_Transition_ID);
	}

	@Override
	public void setC_HierarchyCommissionSettings_ID(final int C_HierarchyCommissionSettings_ID)
	{
		if (C_HierarchyCommissionSettings_ID < 1)
			set_Value(COLUMNNAME_C_HierarchyCommissionSettings_ID, null);
		else
			set_Value(COLUMNNAME_C_HierarchyCommissionSettings_ID, C_HierarchyCommissionSettings_ID);
	}

	@Override
	public int getC_HierarchyCommissionSettings_ID()
	{
		return get_ValueAsInt(COLUMNNAME_C_HierarchyCommissionSettings_ID);
	}

	/** 
	 * ClearingAmtBaseOn AD_Reference_ID=540278
	 * Reference name: ClearingAmtBaseOn
	 */
	public static final int CLEARINGAMTBASEON_AD_Reference_ID=540278;
	/** ProductPrice = ProductPrice */
	public static final String CLEARINGAMTBASEON_ProductPrice = "ProductPrice";
	/** FlatrateAmount = FlatrateAmount */
	public static final String CLEARINGAMTBASEON_FlatrateAmount = "FlatrateAmount";
	@Override
	public void setClearingAmtBaseOn (final @Nullable java.lang.String ClearingAmtBaseOn)
	{
		set_Value (COLUMNNAME_ClearingAmtBaseOn, ClearingAmtBaseOn);
	}

	@Override
	public java.lang.String getClearingAmtBaseOn() 
	{
		return get_ValueAsString(COLUMNNAME_ClearingAmtBaseOn);
	}

	@Override
	public void setC_LicenseFeeSettings_ID (final int C_LicenseFeeSettings_ID)
	{
		if (C_LicenseFeeSettings_ID < 1) 
			set_Value (COLUMNNAME_C_LicenseFeeSettings_ID, null);
		else 
			set_Value (COLUMNNAME_C_LicenseFeeSettings_ID, C_LicenseFeeSettings_ID);
	}

	@Override
	public int getC_LicenseFeeSettings_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_LicenseFeeSettings_ID);
	}

	@Override
	public void setC_MediatedCommissionSettings_ID (final int C_MediatedCommissionSettings_ID)
	{
		if (C_MediatedCommissionSettings_ID < 1) 
			set_Value (COLUMNNAME_C_MediatedCommissionSettings_ID, null);
		else 
			set_Value (COLUMNNAME_C_MediatedCommissionSettings_ID, C_MediatedCommissionSettings_ID);
	}

	@Override
	public int getC_MediatedCommissionSettings_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_MediatedCommissionSettings_ID);
	}

	@Override
	public de.metas.contracts.model.I_C_SubscrDiscount getC_SubscrDiscount()
	{
		return get_ValueAsPO(COLUMNNAME_C_SubscrDiscount_ID, de.metas.contracts.model.I_C_SubscrDiscount.class);
	}

	@Override
	public void setC_SubscrDiscount(final de.metas.contracts.model.I_C_SubscrDiscount C_SubscrDiscount)
	{
		set_ValueFromPO(COLUMNNAME_C_SubscrDiscount_ID, de.metas.contracts.model.I_C_SubscrDiscount.class, C_SubscrDiscount);
	}

	@Override
	public void setC_SubscrDiscount_ID (final int C_SubscrDiscount_ID)
	{
		if (C_SubscrDiscount_ID < 1) 
			set_Value (COLUMNNAME_C_SubscrDiscount_ID, null);
		else 
			set_Value (COLUMNNAME_C_SubscrDiscount_ID, C_SubscrDiscount_ID);
	}

	@Override
	public int getC_SubscrDiscount_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_SubscrDiscount_ID);
	}

	@Override
	public void setC_UOM_ID (final int C_UOM_ID)
	{
		if (C_UOM_ID < 1)
			set_Value(COLUMNNAME_C_UOM_ID, null);
		else
			set_Value(COLUMNNAME_C_UOM_ID, C_UOM_ID);
	}

	@Override
	public int getC_UOM_ID()
	{
		return get_ValueAsInt(COLUMNNAME_C_UOM_ID);
	}

	@Override
	public void setDescription(final @Nullable java.lang.String Description)
	{
		set_Value(COLUMNNAME_Description, Description);
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
	public static final int DOCACTION_AD_Reference_ID = 135;
	/**
	 * Complete = CO
	 */
	public static final String DOCACTION_Complete = "CO";
	/**
	 * Approve = AP
	 */
	public static final String DOCACTION_Approve = "AP";
	/**
	 * Reject = RJ
	 */
	public static final String DOCACTION_Reject = "RJ";
	/**
	 * Post = PO
	 */
	public static final String DOCACTION_Post = "PO";
	/**
	 * Void = VO
	 */
	public static final String DOCACTION_Void = "VO";
	/**
	 * Close = CL
	 */
	public static final String DOCACTION_Close = "CL";
	/**
	 * Reverse_Correct = RC
	 */
	public static final String DOCACTION_Reverse_Correct = "RC";
	/**
	 * Reverse_Accrual = RA
	 */
	public static final String DOCACTION_Reverse_Accrual = "RA";
	/**
	 * Invalidate = IN
	 */
	public static final String DOCACTION_Invalidate = "IN";
	/**
	 * Re_Activate = RE
	 */
	public static final String DOCACTION_Re_Activate = "RE";
	/**
	 * None = --
	 */
	public static final String DOCACTION_None = "--";
	/**
	 * Prepare = PR
	 */
	public static final String DOCACTION_Prepare = "PR";
	/** Unlock = XL */
	public static final String DOCACTION_Unlock = "XL";
	/** WaitComplete = WC */
	public static final String DOCACTION_WaitComplete = "WC";
	/** UnClose = UC */
	public static final String DOCACTION_UnClose = "UC";
	@Override
	public void setDocAction (final java.lang.String DocAction)
	{
		set_Value (COLUMNNAME_DocAction, DocAction);
	}

	@Override
	public java.lang.String getDocAction() 
	{
		return get_ValueAsString(COLUMNNAME_DocAction);
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
	public void setDocStatus (final java.lang.String DocStatus)
	{
		set_Value (COLUMNNAME_DocStatus, DocStatus);
	}

	@Override
	public java.lang.String getDocStatus() 
	{
		return get_ValueAsString(COLUMNNAME_DocStatus);
	}

	/** 
	 * InvoiceRule AD_Reference_ID=150
	 * Reference name: C_Order InvoiceRule
	 */
	public static final int INVOICERULE_AD_Reference_ID=150;
	/** AfterOrderDelivered = O */
	public static final String INVOICERULE_AfterOrderDelivered = "O";
	/** AfterDelivery = D */
	public static final String INVOICERULE_AfterDelivery = "D";
	/** CustomerScheduleAfterDelivery = S */
	public static final String INVOICERULE_CustomerScheduleAfterDelivery = "S";
	/** Immediate = I */
	public static final String INVOICERULE_Immediate = "I";
	/** OrderCompletelyDelivered = C */
	public static final String INVOICERULE_OrderCompletelyDelivered = "C";
	/** After Pick = P */
	public static final String INVOICERULE_AfterPick = "P";
	@Override
	public void setInvoiceRule (final java.lang.String InvoiceRule)
	{
		set_Value (COLUMNNAME_InvoiceRule, InvoiceRule);
	}

	@Override
	public java.lang.String getInvoiceRule() 
	{
		return get_ValueAsString(COLUMNNAME_InvoiceRule);
	}

	@Override
	public void setIsClosingWithActualSum (final boolean IsClosingWithActualSum)
	{
		set_Value (COLUMNNAME_IsClosingWithActualSum, IsClosingWithActualSum);
	}

	@Override
	public boolean isClosingWithActualSum() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsClosingWithActualSum);
	}

	@Override
	public void setIsClosingWithCorrectionSum (final boolean IsClosingWithCorrectionSum)
	{
		set_Value (COLUMNNAME_IsClosingWithCorrectionSum, IsClosingWithCorrectionSum);
	}

	@Override
	public boolean isClosingWithCorrectionSum() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsClosingWithCorrectionSum);
	}

	@Override
	public void setIsCorrectionAmtAtClosing (final boolean IsCorrectionAmtAtClosing)
	{
		set_Value (COLUMNNAME_IsCorrectionAmtAtClosing, IsCorrectionAmtAtClosing);
	}

	@Override
	public boolean isCorrectionAmtAtClosing() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsCorrectionAmtAtClosing);
	}

	@Override
	public void setIsCreateNoInvoice (final boolean IsCreateNoInvoice)
	{
		set_Value (COLUMNNAME_IsCreateNoInvoice, IsCreateNoInvoice);
	}

	@Override
	public boolean isCreateNoInvoice() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsCreateNoInvoice);
	}

	@Override
	public void setIsFreeOfCharge (final boolean IsFreeOfCharge)
	{
		set_Value (COLUMNNAME_IsFreeOfCharge, IsFreeOfCharge);
	}

	@Override
	public boolean isFreeOfCharge() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsFreeOfCharge);
	}

	@Override
	public void setIsManualPrice (final boolean IsManualPrice)
	{
		set_Value (COLUMNNAME_IsManualPrice, IsManualPrice);
	}

	@Override
	public boolean isManualPrice() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsManualPrice);
	}

	@Override
	public void setIsSimulation (final boolean IsSimulation)
	{
		set_Value (COLUMNNAME_IsSimulation, IsSimulation);
	}

	@Override
	public boolean isSimulation() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsSimulation);
	}

	@Override
	public void setMargin_Max (final BigDecimal Margin_Max)
	{
		set_Value (COLUMNNAME_Margin_Max, Margin_Max);
	}

	@Override
	public BigDecimal getMargin_Max() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_Margin_Max);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setMargin_Min (final BigDecimal Margin_Min)
	{
		set_Value (COLUMNNAME_Margin_Min, Margin_Min);
	}

	@Override
	public BigDecimal getMargin_Min() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_Margin_Min);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public de.metas.contracts.model.I_ModCntr_Settings getModCntr_Settings()
	{
		return get_ValueAsPO(COLUMNNAME_ModCntr_Settings_ID, de.metas.contracts.model.I_ModCntr_Settings.class);
	}

	@Override
	public void setModCntr_Settings(final de.metas.contracts.model.I_ModCntr_Settings ModCntr_Settings)
	{
		set_ValueFromPO(COLUMNNAME_ModCntr_Settings_ID, de.metas.contracts.model.I_ModCntr_Settings.class, ModCntr_Settings);
	}

	@Override
	public void setModCntr_Settings_ID (final int ModCntr_Settings_ID)
	{
		if (ModCntr_Settings_ID < 1) 
			set_Value (COLUMNNAME_ModCntr_Settings_ID, null);
		else 
			set_Value (COLUMNNAME_ModCntr_Settings_ID, ModCntr_Settings_ID);
	}

	@Override
	public int getModCntr_Settings_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_ModCntr_Settings_ID);
	}

	@Override
	public void setM_PricingSystem_ID (final int M_PricingSystem_ID)
	{
		if (M_PricingSystem_ID < 1) 
			set_Value (COLUMNNAME_M_PricingSystem_ID, null);
		else 
			set_Value (COLUMNNAME_M_PricingSystem_ID, M_PricingSystem_ID);
	}

	@Override
	public int getM_PricingSystem_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_PricingSystem_ID);
	}

	@Override
	public void setM_Product_Actual_ID (final int M_Product_Actual_ID)
	{
		if (M_Product_Actual_ID < 1) 
			set_Value (COLUMNNAME_M_Product_Actual_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_Actual_ID, M_Product_Actual_ID);
	}

	@Override
	public int getM_Product_Actual_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Product_Actual_ID);
	}

	@Override
	public void setM_Product_Correction_ID (final int M_Product_Correction_ID)
	{
		if (M_Product_Correction_ID < 1) 
			set_Value (COLUMNNAME_M_Product_Correction_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_Correction_ID, M_Product_Correction_ID);
	}

	@Override
	public int getM_Product_Correction_ID()
	{
		return get_ValueAsInt(COLUMNNAME_M_Product_Correction_ID);
	}

	@Override
	public void setM_Product_Flatrate_ID(final int M_Product_Flatrate_ID)
	{
		if (M_Product_Flatrate_ID < 1)
			set_Value(COLUMNNAME_M_Product_Flatrate_ID, null);
		else
			set_Value(COLUMNNAME_M_Product_Flatrate_ID, M_Product_Flatrate_ID);
	}

	@Override
	public int getM_Product_Flatrate_ID()
	{
		return get_ValueAsInt(COLUMNNAME_M_Product_Flatrate_ID);
	}

	@Override
	public void setM_QualityInsp_LagerKonf_ID(final int M_QualityInsp_LagerKonf_ID)
	{
		if (M_QualityInsp_LagerKonf_ID < 1)
			set_Value(COLUMNNAME_M_QualityInsp_LagerKonf_ID, null);
		else
			set_Value(COLUMNNAME_M_QualityInsp_LagerKonf_ID, M_QualityInsp_LagerKonf_ID);
	}

	@Override
	public int getM_QualityInsp_LagerKonf_ID()
	{
		return get_ValueAsInt(COLUMNNAME_M_QualityInsp_LagerKonf_ID);
	}

	@Override
	public void setName(final java.lang.String Name)
	{
		set_Value(COLUMNNAME_Name, Name);
	}

	@Override
	public java.lang.String getName() 
	{
		return get_ValueAsString(COLUMNNAME_Name);
	}

	/** 
	 * OnFlatrateTermExtend AD_Reference_ID=540853
	 * Reference name: Conditions_BehaviourWhenExtending
	 */
	public static final int ONFLATRATETERMEXTEND_AD_Reference_ID=540853;
	/** CopyPrice = Co */
	public static final String ONFLATRATETERMEXTEND_CopyPrice = "Co";
	/** CalculatePrice = Ca */
	public static final String ONFLATRATETERMEXTEND_CalculatePrice = "Ca";
	/** Extension Not Allowed = Ex */
	public static final String ONFLATRATETERMEXTEND_ExtensionNotAllowed = "Ex";
	@Override
	public void setOnFlatrateTermExtend (final java.lang.String OnFlatrateTermExtend)
	{
		set_Value (COLUMNNAME_OnFlatrateTermExtend, OnFlatrateTermExtend);
	}

	@Override
	public java.lang.String getOnFlatrateTermExtend() 
	{
		return get_ValueAsString(COLUMNNAME_OnFlatrateTermExtend);
	}

	@Override
	public void setPrintName (final @Nullable java.lang.String PrintName)
	{
		set_Value (COLUMNNAME_PrintName, PrintName);
	}

	@Override
	public java.lang.String getPrintName() 
	{
		return get_ValueAsString(COLUMNNAME_PrintName);
	}

	@Override
	public void setProcessed (final boolean Processed)
	{
		set_Value (COLUMNNAME_Processed, Processed);
	}

	@Override
	public boolean isProcessed() 
	{
		return get_ValueAsBoolean(COLUMNNAME_Processed);
	}

	@Override
	public void setProcessing (final boolean Processing)
	{
		set_Value (COLUMNNAME_Processing, Processing);
	}

	@Override
	public boolean isProcessing() 
	{
		return get_ValueAsBoolean(COLUMNNAME_Processing);
	}

	/** 
	 * Type_Clearing AD_Reference_ID=540265
	 * Reference name: Type_Clearing
	 */
	public static final int TYPE_CLEARING_AD_Reference_ID=540265;
	/** Complete = CO */
	public static final String TYPE_CLEARING_Complete = "CO";
	/** Exceeding = EX */
	public static final String TYPE_CLEARING_Exceeding = "EX";
	@Override
	public void setType_Clearing (final java.lang.String Type_Clearing)
	{
		set_Value (COLUMNNAME_Type_Clearing, Type_Clearing);
	}

	@Override
	public java.lang.String getType_Clearing() 
	{
		return get_ValueAsString(COLUMNNAME_Type_Clearing);
	}

	/** 
	 * Type_Conditions AD_Reference_ID=540271
	 * Reference name: Type_Conditions
	 */
	public static final int TYPE_CONDITIONS_AD_Reference_ID=540271;
	/** FlatFee = FlatFee */
	public static final String TYPE_CONDITIONS_FlatFee = "FlatFee";
	/** HoldingFee = HoldingFee */
	public static final String TYPE_CONDITIONS_HoldingFee = "HoldingFee";
	/** Subscription = Subscr */
	public static final String TYPE_CONDITIONS_Subscription = "Subscr";
	/** Refundable = Refundable */
	public static final String TYPE_CONDITIONS_Refundable = "Refundable";
	/** QualityBasedInvoicing = QualityBsd */
	public static final String TYPE_CONDITIONS_QualityBasedInvoicing = "QualityBsd";
	/** Procurement = Procuremnt */
	public static final String TYPE_CONDITIONS_Procurement = "Procuremnt";
	/** Refund = Refund */
	public static final String TYPE_CONDITIONS_Refund = "Refund";
	/** Commission = Commission */
	public static final String TYPE_CONDITIONS_Commission = "Commission";
	/** MarginCommission = MarginCommission */
	public static final String TYPE_CONDITIONS_MarginCommission = "MarginCommission";
	/** Mediated commission = MediatedCommission */
	public static final String TYPE_CONDITIONS_MediatedCommission = "MediatedCommission";
	/** LicenseFee = LicenseFee */
	public static final String TYPE_CONDITIONS_LicenseFee = "LicenseFee";
	/** CallOrder = CallOrder */
	public static final String TYPE_CONDITIONS_CallOrder = "CallOrder";
	/** InterimInvoice = InterimInvoice */
	public static final String TYPE_CONDITIONS_InterimInvoice = "InterimInvoice";
	/** ModularContract = ModularContract */
	public static final String TYPE_CONDITIONS_ModularContract = "ModularContract";
	@Override
	public void setType_Conditions (final java.lang.String Type_Conditions)
	{
		set_ValueNoCheck (COLUMNNAME_Type_Conditions, Type_Conditions);
	}

	@Override
	public java.lang.String getType_Conditions() 
	{
		return get_ValueAsString(COLUMNNAME_Type_Conditions);
	}

	/** 
	 * Type_Flatrate AD_Reference_ID=540264
	 * Reference name: Type_Flatrate
	 */
	public static final int TYPE_FLATRATE_AD_Reference_ID=540264;
	/** NONE = NONE */
	public static final String TYPE_FLATRATE_NONE = "NONE";
	/** Corridor_Percent = LIPE */
	public static final String TYPE_FLATRATE_Corridor_Percent = "LIPE";
	/** Reported Quantity = RPTD */
	public static final String TYPE_FLATRATE_ReportedQuantity = "RPTD";
	@Override
	public void setType_Flatrate (final java.lang.String Type_Flatrate)
	{
		set_Value (COLUMNNAME_Type_Flatrate, Type_Flatrate);
	}

	@Override
	public java.lang.String getType_Flatrate() 
	{
		return get_ValueAsString(COLUMNNAME_Type_Flatrate);
	}
	
}