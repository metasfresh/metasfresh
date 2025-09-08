// Generated Model - DO NOT CHANGE
package org.compiere.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_AcctSchema
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_AcctSchema extends org.compiere.model.PO implements I_C_AcctSchema, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 855496023L;

    /** Standard Constructor */
    public X_C_AcctSchema (final Properties ctx, final int C_AcctSchema_ID, @Nullable final String trxName)
    {
      super (ctx, C_AcctSchema_ID, trxName);
    }

    /** Load Constructor */
    public X_C_AcctSchema (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setAD_OrgOnly_ID (final int AD_OrgOnly_ID)
	{
		if (AD_OrgOnly_ID < 1) 
			set_Value (COLUMNNAME_AD_OrgOnly_ID, null);
		else 
			set_Value (COLUMNNAME_AD_OrgOnly_ID, AD_OrgOnly_ID);
	}

	@Override
	public int getAD_OrgOnly_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_OrgOnly_ID);
	}

	@Override
	public void setAutoPeriodControl (final boolean AutoPeriodControl)
	{
		set_Value (COLUMNNAME_AutoPeriodControl, AutoPeriodControl);
	}

	@Override
	public boolean isAutoPeriodControl() 
	{
		return get_ValueAsBoolean(COLUMNNAME_AutoPeriodControl);
	}

	@Override
	public void setC_AcctSchema_ID (final int C_AcctSchema_ID)
	{
		if (C_AcctSchema_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_AcctSchema_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_AcctSchema_ID, C_AcctSchema_ID);
	}

	@Override
	public int getC_AcctSchema_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_AcctSchema_ID);
	}

	@Override
	public void setC_Currency_ID (final int C_Currency_ID)
	{
		if (C_Currency_ID < 1) 
			set_Value (COLUMNNAME_C_Currency_ID, null);
		else 
			set_Value (COLUMNNAME_C_Currency_ID, C_Currency_ID);
	}

	@Override
	public int getC_Currency_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Currency_ID);
	}

	/** 
	 * CommitmentType AD_Reference_ID=359
	 * Reference name: C_AcctSchema CommitmentType
	 */
	public static final int COMMITMENTTYPE_AD_Reference_ID=359;
	/** POCommitmentOnly = C */
	public static final String COMMITMENTTYPE_POCommitmentOnly = "C";
	/** POCommitmentReservation = B */
	public static final String COMMITMENTTYPE_POCommitmentReservation = "B";
	/** None = N */
	public static final String COMMITMENTTYPE_None = "N";
	/** POSOCommitmentReservation = A */
	public static final String COMMITMENTTYPE_POSOCommitmentReservation = "A";
	/** SOCommitmentOnly = S */
	public static final String COMMITMENTTYPE_SOCommitmentOnly = "S";
	/** POSOCommitment = O */
	public static final String COMMITMENTTYPE_POSOCommitment = "O";
	@Override
	public void setCommitmentType (final java.lang.String CommitmentType)
	{
		set_Value (COLUMNNAME_CommitmentType, CommitmentType);
	}

	@Override
	public java.lang.String getCommitmentType() 
	{
		return get_ValueAsString(COLUMNNAME_CommitmentType);
	}

	/** 
	 * CostingLevel AD_Reference_ID=355
	 * Reference name: C_AcctSchema CostingLevel
	 */
	public static final int COSTINGLEVEL_AD_Reference_ID=355;
	/** Client = C */
	public static final String COSTINGLEVEL_Client = "C";
	/** Organization = O */
	public static final String COSTINGLEVEL_Organization = "O";
	/** BatchLot = B */
	public static final String COSTINGLEVEL_BatchLot = "B";
	@Override
	public void setCostingLevel (final java.lang.String CostingLevel)
	{
		set_Value (COLUMNNAME_CostingLevel, CostingLevel);
	}

	@Override
	public java.lang.String getCostingLevel() 
	{
		return get_ValueAsString(COLUMNNAME_CostingLevel);
	}

	/** 
	 * CostingMethod AD_Reference_ID=122
	 * Reference name: C_AcctSchema Costing Method
	 */
	public static final int COSTINGMETHOD_AD_Reference_ID=122;
	/** StandardCosting = S */
	public static final String COSTINGMETHOD_StandardCosting = "S";
	/** AveragePO = A */
	public static final String COSTINGMETHOD_AveragePO = "A";
	/** Lifo = L */
	public static final String COSTINGMETHOD_Lifo = "L";
	/** Fifo = F */
	public static final String COSTINGMETHOD_Fifo = "F";
	/** LastPOPrice = p */
	public static final String COSTINGMETHOD_LastPOPrice = "p";
	/** AverageInvoice = I */
	public static final String COSTINGMETHOD_AverageInvoice = "I";
	/** LastInvoice = i */
	public static final String COSTINGMETHOD_LastInvoice = "i";
	/** UserDefined = U */
	public static final String COSTINGMETHOD_UserDefined = "U";
	/** _ = x */
	public static final String COSTINGMETHOD__ = "x";
	@Override
	public void setCostingMethod (final java.lang.String CostingMethod)
	{
		set_Value (COLUMNNAME_CostingMethod, CostingMethod);
	}

	@Override
	public java.lang.String getCostingMethod() 
	{
		return get_ValueAsString(COLUMNNAME_CostingMethod);
	}

	@Override
	public org.compiere.model.I_C_Period getC_Period()
	{
		return get_ValueAsPO(COLUMNNAME_C_Period_ID, org.compiere.model.I_C_Period.class);
	}

	@Override
	public void setC_Period(final org.compiere.model.I_C_Period C_Period)
	{
		set_ValueFromPO(COLUMNNAME_C_Period_ID, org.compiere.model.I_C_Period.class, C_Period);
	}

	@Override
	public void setC_Period_ID (final int C_Period_ID)
	{
		if (C_Period_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Period_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Period_ID, C_Period_ID);
	}

	@Override
	public int getC_Period_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Period_ID);
	}

	@Override
	public void setCreditorIdPrefix (final int CreditorIdPrefix)
	{
		set_Value (COLUMNNAME_CreditorIdPrefix, CreditorIdPrefix);
	}

	@Override
	public int getCreditorIdPrefix() 
	{
		return get_ValueAsInt(COLUMNNAME_CreditorIdPrefix);
	}

	@Override
	public void setDebtorIdPrefix (final int DebtorIdPrefix)
	{
		set_Value (COLUMNNAME_DebtorIdPrefix, DebtorIdPrefix);
	}

	@Override
	public int getDebtorIdPrefix() 
	{
		return get_ValueAsInt(COLUMNNAME_DebtorIdPrefix);
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
	 * GAAP AD_Reference_ID=123
	 * Reference name: C_AcctSchema GAAP
	 */
	public static final int GAAP_AD_Reference_ID=123;
	/** InternationalGAAP = UN */
	public static final String GAAP_InternationalGAAP = "UN";
	/** USGAAP = US */
	public static final String GAAP_USGAAP = "US";
	/** GermanHGB = DE */
	public static final String GAAP_GermanHGB = "DE";
	/** FrenchAccountingStandard = FR */
	public static final String GAAP_FrenchAccountingStandard = "FR";
	/** CustomAccountingRules = XX */
	public static final String GAAP_CustomAccountingRules = "XX";
	@Override
	public void setGAAP (final java.lang.String GAAP)
	{
		set_Value (COLUMNNAME_GAAP, GAAP);
	}

	@Override
	public java.lang.String getGAAP() 
	{
		return get_ValueAsString(COLUMNNAME_GAAP);
	}

	@Override
	public void setHasAlias (final boolean HasAlias)
	{
		set_Value (COLUMNNAME_HasAlias, HasAlias);
	}

	@Override
	public boolean isHasAlias() 
	{
		return get_ValueAsBoolean(COLUMNNAME_HasAlias);
	}

	@Override
	public void setHasCombination (final boolean HasCombination)
	{
		set_Value (COLUMNNAME_HasCombination, HasCombination);
	}

	@Override
	public boolean isHasCombination() 
	{
		return get_ValueAsBoolean(COLUMNNAME_HasCombination);
	}

	@Override
	public void setIsAccrual (final boolean IsAccrual)
	{
		set_Value (COLUMNNAME_IsAccrual, IsAccrual);
	}

	@Override
	public boolean isAccrual() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsAccrual);
	}

	@Override
	public void setIsAdjustCOGS (final boolean IsAdjustCOGS)
	{
		set_Value (COLUMNNAME_IsAdjustCOGS, IsAdjustCOGS);
	}

	@Override
	public boolean isAdjustCOGS() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsAdjustCOGS);
	}

	@Override
	public void setIsAllowMultiDebitAndCredit (final boolean IsAllowMultiDebitAndCredit)
	{
		set_Value (COLUMNNAME_IsAllowMultiDebitAndCredit, IsAllowMultiDebitAndCredit);
	}

	@Override
	public boolean isAllowMultiDebitAndCredit() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsAllowMultiDebitAndCredit);
	}

	@Override
	public void setIsAllowNegativePosting (final boolean IsAllowNegativePosting)
	{
		set_Value (COLUMNNAME_IsAllowNegativePosting, IsAllowNegativePosting);
	}

	@Override
	public boolean isAllowNegativePosting() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsAllowNegativePosting);
	}

	@Override
	public void setIsAutoSetDebtoridAndCreditorid (final boolean IsAutoSetDebtoridAndCreditorid)
	{
		set_Value (COLUMNNAME_IsAutoSetDebtoridAndCreditorid, IsAutoSetDebtoridAndCreditorid);
	}

	@Override
	public boolean isAutoSetDebtoridAndCreditorid() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsAutoSetDebtoridAndCreditorid);
	}

	@Override
	public void setIsDiscountCorrectsTax (final boolean IsDiscountCorrectsTax)
	{
		set_Value (COLUMNNAME_IsDiscountCorrectsTax, IsDiscountCorrectsTax);
	}

	@Override
	public boolean isDiscountCorrectsTax() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsDiscountCorrectsTax);
	}

	@Override
	public void setIsExplicitCostAdjustment (final boolean IsExplicitCostAdjustment)
	{
		set_Value (COLUMNNAME_IsExplicitCostAdjustment, IsExplicitCostAdjustment);
	}

	@Override
	public boolean isExplicitCostAdjustment() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsExplicitCostAdjustment);
	}

	@Override
	public void setIsPostIfClearingEqual (final boolean IsPostIfClearingEqual)
	{
		set_Value (COLUMNNAME_IsPostIfClearingEqual, IsPostIfClearingEqual);
	}

	@Override
	public boolean isPostIfClearingEqual() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsPostIfClearingEqual);
	}

	@Override
	public void setIsPostServices (final boolean IsPostServices)
	{
		set_Value (COLUMNNAME_IsPostServices, IsPostServices);
	}

	@Override
	public boolean isPostServices() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsPostServices);
	}

	@Override
	public void setIsTradeDiscountPosted (final boolean IsTradeDiscountPosted)
	{
		set_Value (COLUMNNAME_IsTradeDiscountPosted, IsTradeDiscountPosted);
	}

	@Override
	public boolean isTradeDiscountPosted() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsTradeDiscountPosted);
	}

	@Override
	public org.compiere.model.I_M_CostType getM_CostType()
	{
		return get_ValueAsPO(COLUMNNAME_M_CostType_ID, org.compiere.model.I_M_CostType.class);
	}

	@Override
	public void setM_CostType(final org.compiere.model.I_M_CostType M_CostType)
	{
		set_ValueFromPO(COLUMNNAME_M_CostType_ID, org.compiere.model.I_M_CostType.class, M_CostType);
	}

	@Override
	public void setM_CostType_ID (final int M_CostType_ID)
	{
		if (M_CostType_ID < 1) 
			set_Value (COLUMNNAME_M_CostType_ID, null);
		else 
			set_Value (COLUMNNAME_M_CostType_ID, M_CostType_ID);
	}

	@Override
	public int getM_CostType_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_CostType_ID);
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
	public void setPeriod_OpenFuture (final int Period_OpenFuture)
	{
		set_Value (COLUMNNAME_Period_OpenFuture, Period_OpenFuture);
	}

	@Override
	public int getPeriod_OpenFuture() 
	{
		return get_ValueAsInt(COLUMNNAME_Period_OpenFuture);
	}

	@Override
	public void setPeriod_OpenHistory (final int Period_OpenHistory)
	{
		set_Value (COLUMNNAME_Period_OpenHistory, Period_OpenHistory);
	}

	@Override
	public int getPeriod_OpenHistory() 
	{
		return get_ValueAsInt(COLUMNNAME_Period_OpenHistory);
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

	@Override
	public void setSeparator (final java.lang.String Separator)
	{
		set_Value (COLUMNNAME_Separator, Separator);
	}

	@Override
	public java.lang.String getSeparator() 
	{
		return get_ValueAsString(COLUMNNAME_Separator);
	}

	/** 
	 * TaxCorrectionType AD_Reference_ID=392
	 * Reference name: C_AcctSchema TaxCorrectionType
	 */
	public static final int TAXCORRECTIONTYPE_AD_Reference_ID=392;
	/** None = N */
	public static final String TAXCORRECTIONTYPE_None = "N";
	/** Write_OffOnly = W */
	public static final String TAXCORRECTIONTYPE_Write_OffOnly = "W";
	/** DiscountOnly = D */
	public static final String TAXCORRECTIONTYPE_DiscountOnly = "D";
	/** Write_OffAndDiscount = B */
	public static final String TAXCORRECTIONTYPE_Write_OffAndDiscount = "B";
	@Override
	public void setTaxCorrectionType (final java.lang.String TaxCorrectionType)
	{
		set_Value (COLUMNNAME_TaxCorrectionType, TaxCorrectionType);
	}

	@Override
	public java.lang.String getTaxCorrectionType() 
	{
		return get_ValueAsString(COLUMNNAME_TaxCorrectionType);
	}
}