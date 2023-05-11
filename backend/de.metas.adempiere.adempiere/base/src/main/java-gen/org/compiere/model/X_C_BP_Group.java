// Generated Model - DO NOT CHANGE
package org.compiere.model;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_BP_Group
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_BP_Group extends org.compiere.model.PO implements I_C_BP_Group, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 130660288L;

    /** Standard Constructor */
    public X_C_BP_Group (final Properties ctx, final int C_BP_Group_ID, @Nullable final String trxName)
    {
      super (ctx, C_BP_Group_ID, trxName);
    }

    /** Load Constructor */
    public X_C_BP_Group (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public org.compiere.model.I_AD_PrintColor getAD_PrintColor()
	{
		return get_ValueAsPO(COLUMNNAME_AD_PrintColor_ID, org.compiere.model.I_AD_PrintColor.class);
	}

	@Override
	public void setAD_PrintColor(final org.compiere.model.I_AD_PrintColor AD_PrintColor)
	{
		set_ValueFromPO(COLUMNNAME_AD_PrintColor_ID, org.compiere.model.I_AD_PrintColor.class, AD_PrintColor);
	}

	@Override
	public void setAD_PrintColor_ID (final int AD_PrintColor_ID)
	{
		if (AD_PrintColor_ID < 1) 
			set_Value (COLUMNNAME_AD_PrintColor_ID, null);
		else 
			set_Value (COLUMNNAME_AD_PrintColor_ID, AD_PrintColor_ID);
	}

	@Override
	public int getAD_PrintColor_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_PrintColor_ID);
	}

	/** 
	 * BPNameAndGreetingStrategy AD_Reference_ID=541338
	 * Reference name: Individual business partner's name format
	 */
	public static final int BPNAMEANDGREETINGSTRATEGY_AD_Reference_ID=541338;
	/** First Contact = FC */
	public static final String BPNAMEANDGREETINGSTRATEGY_FirstContact = "FC";
	/** Membership Contact = MC */
	public static final String BPNAMEANDGREETINGSTRATEGY_MembershipContact = "MC";
	/** Do Nothing = XX */
	public static final String BPNAMEANDGREETINGSTRATEGY_DoNothing = "XX";
	@Override
	public void setBPNameAndGreetingStrategy (final @Nullable java.lang.String BPNameAndGreetingStrategy)
	{
		set_Value (COLUMNNAME_BPNameAndGreetingStrategy, BPNameAndGreetingStrategy);
	}

	@Override
	public java.lang.String getBPNameAndGreetingStrategy() 
	{
		return get_ValueAsString(COLUMNNAME_BPNameAndGreetingStrategy);
	}

	@Override
	public void setC_BP_Group_ID (final int C_BP_Group_ID)
	{
		if (C_BP_Group_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BP_Group_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BP_Group_ID, C_BP_Group_ID);
	}

	@Override
	public int getC_BP_Group_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BP_Group_ID);
	}

	@Override
	public org.compiere.model.I_C_Dunning getC_Dunning()
	{
		return get_ValueAsPO(COLUMNNAME_C_Dunning_ID, org.compiere.model.I_C_Dunning.class);
	}

	@Override
	public void setC_Dunning(final org.compiere.model.I_C_Dunning C_Dunning)
	{
		set_ValueFromPO(COLUMNNAME_C_Dunning_ID, org.compiere.model.I_C_Dunning.class, C_Dunning);
	}

	@Override
	public void setC_Dunning_ID (final int C_Dunning_ID)
	{
		if (C_Dunning_ID < 1) 
			set_Value (COLUMNNAME_C_Dunning_ID, null);
		else 
			set_Value (COLUMNNAME_C_Dunning_ID, C_Dunning_ID);
	}

	@Override
	public int getC_Dunning_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Dunning_ID);
	}

	@Override
	public void setCreditWatchPercent (final @Nullable BigDecimal CreditWatchPercent)
	{
		set_Value (COLUMNNAME_CreditWatchPercent, CreditWatchPercent);
	}

	@Override
	public BigDecimal getCreditWatchPercent() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_CreditWatchPercent);
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

	@Override
	public void setIsConfidentialInfo (final boolean IsConfidentialInfo)
	{
		set_Value (COLUMNNAME_IsConfidentialInfo, IsConfidentialInfo);
	}

	@Override
	public boolean isConfidentialInfo() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsConfidentialInfo);
	}

	@Override
	public void setIsCustomer (final boolean IsCustomer)
	{
		set_Value (COLUMNNAME_IsCustomer, IsCustomer);
	}

	@Override
	public boolean isCustomer() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsCustomer);
	}

	@Override
	public void setIsDefault (final boolean IsDefault)
	{
		set_Value (COLUMNNAME_IsDefault, IsDefault);
	}

	@Override
	public boolean isDefault() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsDefault);
	}

	@Override
	public org.compiere.model.I_M_DiscountSchema getM_DiscountSchema()
	{
		return get_ValueAsPO(COLUMNNAME_M_DiscountSchema_ID, org.compiere.model.I_M_DiscountSchema.class);
	}

	@Override
	public void setM_DiscountSchema(final org.compiere.model.I_M_DiscountSchema M_DiscountSchema)
	{
		set_ValueFromPO(COLUMNNAME_M_DiscountSchema_ID, org.compiere.model.I_M_DiscountSchema.class, M_DiscountSchema);
	}

	@Override
	public void setM_DiscountSchema_ID (final int M_DiscountSchema_ID)
	{
		if (M_DiscountSchema_ID < 1) 
			set_Value (COLUMNNAME_M_DiscountSchema_ID, null);
		else 
			set_Value (COLUMNNAME_M_DiscountSchema_ID, M_DiscountSchema_ID);
	}

	@Override
	public int getM_DiscountSchema_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_DiscountSchema_ID);
	}

	@Override
	public void setM_FreightCost_ID (final int M_FreightCost_ID)
	{
		if (M_FreightCost_ID < 1) 
			set_Value (COLUMNNAME_M_FreightCost_ID, null);
		else 
			set_Value (COLUMNNAME_M_FreightCost_ID, M_FreightCost_ID);
	}

	@Override
	public int getM_FreightCost_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_FreightCost_ID);
	}

	@Override
	public void setM_PriceList_ID (final int M_PriceList_ID)
	{
		if (M_PriceList_ID < 1) 
			set_Value (COLUMNNAME_M_PriceList_ID, null);
		else 
			set_Value (COLUMNNAME_M_PriceList_ID, M_PriceList_ID);
	}

	@Override
	public int getM_PriceList_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_PriceList_ID);
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

	/** 
	 * MRP_Exclude AD_Reference_ID=319
	 * Reference name: _YesNo
	 */
	public static final int MRP_EXCLUDE_AD_Reference_ID=319;
	/** Yes = Y */
	public static final String MRP_EXCLUDE_Yes = "Y";
	/** No = N */
	public static final String MRP_EXCLUDE_No = "N";
	@Override
	public void setMRP_Exclude (final @Nullable java.lang.String MRP_Exclude)
	{
		set_Value (COLUMNNAME_MRP_Exclude, MRP_Exclude);
	}

	@Override
	public java.lang.String getMRP_Exclude() 
	{
		return get_ValueAsString(COLUMNNAME_MRP_Exclude);
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

	/** 
	 * PaymentRule AD_Reference_ID=195
	 * Reference name: _Payment Rule
	 */
	public static final int PAYMENTRULE_AD_Reference_ID=195;
	/** Cash = B */
	public static final String PAYMENTRULE_Cash = "B";
	/** CreditCard = K */
	public static final String PAYMENTRULE_CreditCard = "K";
	/** DirectDeposit = T */
	public static final String PAYMENTRULE_DirectDeposit = "T";
	/** Check = S */
	public static final String PAYMENTRULE_Check = "S";
	/** OnCredit = P */
	public static final String PAYMENTRULE_OnCredit = "P";
	/** DirectDebit = D */
	public static final String PAYMENTRULE_DirectDebit = "D";
	/** Mixed = M */
	public static final String PAYMENTRULE_Mixed = "M";
	/** PayPal = L */
	public static final String PAYMENTRULE_PayPal = "L";
	@Override
	public void setPaymentRule (final @Nullable java.lang.String PaymentRule)
	{
		set_Value (COLUMNNAME_PaymentRule, PaymentRule);
	}

	@Override
	public java.lang.String getPaymentRule() 
	{
		return get_ValueAsString(COLUMNNAME_PaymentRule);
	}

	@Override
	public org.compiere.model.I_M_DiscountSchema getPO_DiscountSchema()
	{
		return get_ValueAsPO(COLUMNNAME_PO_DiscountSchema_ID, org.compiere.model.I_M_DiscountSchema.class);
	}

	@Override
	public void setPO_DiscountSchema(final org.compiere.model.I_M_DiscountSchema PO_DiscountSchema)
	{
		set_ValueFromPO(COLUMNNAME_PO_DiscountSchema_ID, org.compiere.model.I_M_DiscountSchema.class, PO_DiscountSchema);
	}

	@Override
	public void setPO_DiscountSchema_ID (final int PO_DiscountSchema_ID)
	{
		if (PO_DiscountSchema_ID < 1) 
			set_Value (COLUMNNAME_PO_DiscountSchema_ID, null);
		else 
			set_Value (COLUMNNAME_PO_DiscountSchema_ID, PO_DiscountSchema_ID);
	}

	@Override
	public int getPO_DiscountSchema_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_PO_DiscountSchema_ID);
	}

	@Override
	public void setPO_PriceList_ID (final int PO_PriceList_ID)
	{
		if (PO_PriceList_ID < 1) 
			set_Value (COLUMNNAME_PO_PriceList_ID, null);
		else 
			set_Value (COLUMNNAME_PO_PriceList_ID, PO_PriceList_ID);
	}

	@Override
	public int getPO_PriceList_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_PO_PriceList_ID);
	}

	@Override
	public void setPO_PricingSystem_ID (final int PO_PricingSystem_ID)
	{
		if (PO_PricingSystem_ID < 1) 
			set_Value (COLUMNNAME_PO_PricingSystem_ID, null);
		else 
			set_Value (COLUMNNAME_PO_PricingSystem_ID, PO_PricingSystem_ID);
	}

	@Override
	public int getPO_PricingSystem_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_PO_PricingSystem_ID);
	}

	@Override
	public void setPriceMatchTolerance (final @Nullable BigDecimal PriceMatchTolerance)
	{
		set_Value (COLUMNNAME_PriceMatchTolerance, PriceMatchTolerance);
	}

	@Override
	public BigDecimal getPriceMatchTolerance() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_PriceMatchTolerance);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	/** 
	 * PriorityBase AD_Reference_ID=350
	 * Reference name: C_BP_Group PriorityBase
	 */
	public static final int PRIORITYBASE_AD_Reference_ID=350;
	/** Same = S */
	public static final String PRIORITYBASE_Same = "S";
	/** Lower = L */
	public static final String PRIORITYBASE_Lower = "L";
	/** Higher = H */
	public static final String PRIORITYBASE_Higher = "H";
	@Override
	public void setPriorityBase (final @Nullable java.lang.String PriorityBase)
	{
		set_Value (COLUMNNAME_PriorityBase, PriorityBase);
	}

	@Override
	public java.lang.String getPriorityBase() 
	{
		return get_ValueAsString(COLUMNNAME_PriorityBase);
	}

	/** 
	 * SOCreditStatus AD_Reference_ID=289
	 * Reference name: C_BPartner SOCreditStatus
	 */
	public static final int SOCREDITSTATUS_AD_Reference_ID=289;
	/** CreditStop = S */
	public static final String SOCREDITSTATUS_CreditStop = "S";
	/** CreditHold = H */
	public static final String SOCREDITSTATUS_CreditHold = "H";
	/** CreditWatch = W */
	public static final String SOCREDITSTATUS_CreditWatch = "W";
	/** NoCreditCheck = X */
	public static final String SOCREDITSTATUS_NoCreditCheck = "X";
	/** CreditOK = O */
	public static final String SOCREDITSTATUS_CreditOK = "O";
	/** NurEineRechnung = I */
	public static final String SOCREDITSTATUS_NurEineRechnung = "I";
	@Override
	public void setSOCreditStatus (final java.lang.String SOCreditStatus)
	{
		set_Value (COLUMNNAME_SOCreditStatus, SOCreditStatus);
	}

	@Override
	public java.lang.String getSOCreditStatus() 
	{
		return get_ValueAsString(COLUMNNAME_SOCreditStatus);
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
}