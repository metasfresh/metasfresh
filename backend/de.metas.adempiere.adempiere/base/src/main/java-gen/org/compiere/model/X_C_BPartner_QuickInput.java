// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for C_BPartner_QuickInput
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_BPartner_QuickInput extends org.compiere.model.PO implements I_C_BPartner_QuickInput, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -2123287978L;

    /** Standard Constructor */
    public X_C_BPartner_QuickInput (final Properties ctx, final int C_BPartner_QuickInput_ID, @Nullable final String trxName)
    {
      super (ctx, C_BPartner_QuickInput_ID, trxName);
    }

    /** Load Constructor */
    public X_C_BPartner_QuickInput (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	 * AD_Language AD_Reference_ID=327
	 * Reference name: AD_Language System
	 */
	public static final int AD_LANGUAGE_AD_Reference_ID=327;
	@Override
	public void setAD_Language (final @Nullable java.lang.String AD_Language)
	{
		set_Value (COLUMNNAME_AD_Language, AD_Language);
	}

	@Override
	public java.lang.String getAD_Language() 
	{
		return get_ValueAsString(COLUMNNAME_AD_Language);
	}

	@Override
	public void setAD_User_ID (final int AD_User_ID)
	{
		if (AD_User_ID < 0) 
			set_Value (COLUMNNAME_AD_User_ID, null);
		else 
			set_Value (COLUMNNAME_AD_User_ID, AD_User_ID);
	}

	@Override
	public int getAD_User_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_User_ID);
	}

	@Override
	public void setBPartnerName (final @Nullable java.lang.String BPartnerName)
	{
		set_Value (COLUMNNAME_BPartnerName, BPartnerName);
	}

	@Override
	public java.lang.String getBPartnerName() 
	{
		return get_ValueAsString(COLUMNNAME_BPartnerName);
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
	public void setC_BPartner_Location_ID (final int C_BPartner_Location_ID)
	{
		if (C_BPartner_Location_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_Location_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_Location_ID, C_BPartner_Location_ID);
	}

	@Override
	public int getC_BPartner_Location_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_Location_ID);
	}

	@Override
	public void setC_BPartner_QuickInput_ID (final int C_BPartner_QuickInput_ID)
	{
		if (C_BPartner_QuickInput_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_QuickInput_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_QuickInput_ID, C_BPartner_QuickInput_ID);
	}

	@Override
	public int getC_BPartner_QuickInput_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_QuickInput_ID);
	}

	@Override
	public org.compiere.model.I_C_BP_Group getC_BP_Group()
	{
		return get_ValueAsPO(COLUMNNAME_C_BP_Group_ID, org.compiere.model.I_C_BP_Group.class);
	}

	@Override
	public void setC_BP_Group(final org.compiere.model.I_C_BP_Group C_BP_Group)
	{
		set_ValueFromPO(COLUMNNAME_C_BP_Group_ID, org.compiere.model.I_C_BP_Group.class, C_BP_Group);
	}

	@Override
	public void setC_BP_Group_ID (final int C_BP_Group_ID)
	{
		if (C_BP_Group_ID < 1) 
			set_Value (COLUMNNAME_C_BP_Group_ID, null);
		else 
			set_Value (COLUMNNAME_C_BP_Group_ID, C_BP_Group_ID);
	}

	@Override
	public int getC_BP_Group_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BP_Group_ID);
	}

	@Override
	public void setC_Greeting_ID (final int C_Greeting_ID)
	{
		if (C_Greeting_ID < 1) 
			set_Value (COLUMNNAME_C_Greeting_ID, null);
		else 
			set_Value (COLUMNNAME_C_Greeting_ID, C_Greeting_ID);
	}

	@Override
	public int getC_Greeting_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Greeting_ID);
	}

	@Override
	public org.compiere.model.I_C_Location getC_Location()
	{
		return get_ValueAsPO(COLUMNNAME_C_Location_ID, org.compiere.model.I_C_Location.class);
	}

	@Override
	public void setC_Location(final org.compiere.model.I_C_Location C_Location)
	{
		set_ValueFromPO(COLUMNNAME_C_Location_ID, org.compiere.model.I_C_Location.class, C_Location);
	}

	@Override
	public void setC_Location_ID (final int C_Location_ID)
	{
		if (C_Location_ID < 1) 
			set_Value (COLUMNNAME_C_Location_ID, null);
		else 
			set_Value (COLUMNNAME_C_Location_ID, C_Location_ID);
	}

	@Override
	public int getC_Location_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Location_ID);
	}

	@Override
	public void setCompanyname (final @Nullable java.lang.String Companyname)
	{
		set_Value (COLUMNNAME_Companyname, Companyname);
	}

	@Override
	public java.lang.String getCompanyname() 
	{
		return get_ValueAsString(COLUMNNAME_Companyname);
	}

	@Override
	public void setC_PaymentTerm_ID (final int C_PaymentTerm_ID)
	{
		if (C_PaymentTerm_ID < 1) 
			set_Value (COLUMNNAME_C_PaymentTerm_ID, null);
		else 
			set_Value (COLUMNNAME_C_PaymentTerm_ID, C_PaymentTerm_ID);
	}

	@Override
	public int getC_PaymentTerm_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_PaymentTerm_ID);
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

	@Override
	public void setExcludeFromPromotions (final boolean ExcludeFromPromotions)
	{
		set_Value (COLUMNNAME_ExcludeFromPromotions, ExcludeFromPromotions);
	}

	@Override
	public boolean isExcludeFromPromotions() 
	{
		return get_ValueAsBoolean(COLUMNNAME_ExcludeFromPromotions);
	}

	@Override
	public void setIsCompany (final boolean IsCompany)
	{
		set_Value (COLUMNNAME_IsCompany, IsCompany);
	}

	@Override
	public boolean isCompany() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsCompany);
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
	public void setIsNewsletter (final boolean IsNewsletter)
	{
		set_Value (COLUMNNAME_IsNewsletter, IsNewsletter);
	}

	@Override
	public boolean isNewsletter() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsNewsletter);
	}

	@Override
	public void setIsVendor (final boolean IsVendor)
	{
		set_Value (COLUMNNAME_IsVendor, IsVendor);
	}

	@Override
	public boolean isVendor() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsVendor);
	}

	@Override
	public void setMKTG_Campaign_ID (final int MKTG_Campaign_ID)
	{
		if (MKTG_Campaign_ID < 1) 
			set_Value (COLUMNNAME_MKTG_Campaign_ID, null);
		else 
			set_Value (COLUMNNAME_MKTG_Campaign_ID, MKTG_Campaign_ID);
	}

	@Override
	public int getMKTG_Campaign_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_MKTG_Campaign_ID);
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
	public void setName2 (final @Nullable java.lang.String Name2)
	{
		set_Value (COLUMNNAME_Name2, Name2);
	}

	@Override
	public java.lang.String getName2() 
	{
		return get_ValueAsString(COLUMNNAME_Name2);
	}

	@Override
	public void setPhone (final @Nullable java.lang.String Phone)
	{
		set_Value (COLUMNNAME_Phone, Phone);
	}

	@Override
	public java.lang.String getPhone() 
	{
		return get_ValueAsString(COLUMNNAME_Phone);
	}

	@Override
	public void setPO_PaymentTerm_ID (final int PO_PaymentTerm_ID)
	{
		if (PO_PaymentTerm_ID < 1) 
			set_Value (COLUMNNAME_PO_PaymentTerm_ID, null);
		else 
			set_Value (COLUMNNAME_PO_PaymentTerm_ID, PO_PaymentTerm_ID);
	}

	@Override
	public int getPO_PaymentTerm_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_PO_PaymentTerm_ID);
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
	public void setReferrer (final @Nullable java.lang.String Referrer)
	{
		set_Value (COLUMNNAME_Referrer, Referrer);
	}

	@Override
	public java.lang.String getReferrer() 
	{
		return get_ValueAsString(COLUMNNAME_Referrer);
	}
}