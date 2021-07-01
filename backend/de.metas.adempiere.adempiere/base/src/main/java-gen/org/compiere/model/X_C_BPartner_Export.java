// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for C_BPartner_Export
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_BPartner_Export extends org.compiere.model.PO implements I_C_BPartner_Export, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1510431132L;

    /** Standard Constructor */
    public X_C_BPartner_Export (final Properties ctx, final int C_BPartner_Export_ID, @Nullable final String trxName)
    {
      super (ctx, C_BPartner_Export_ID, trxName);
    }

    /** Load Constructor */
    public X_C_BPartner_Export (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setAddress1 (final @Nullable java.lang.String Address1)
	{
		set_ValueNoCheck (COLUMNNAME_Address1, Address1);
	}

	@Override
	public java.lang.String getAddress1() 
	{
		return get_ValueAsString(COLUMNNAME_Address1);
	}

	@Override
	public void setAddress2 (final @Nullable java.lang.String Address2)
	{
		set_ValueNoCheck (COLUMNNAME_Address2, Address2);
	}

	@Override
	public java.lang.String getAddress2() 
	{
		return get_ValueAsString(COLUMNNAME_Address2);
	}

	@Override
	public void setAddress3 (final @Nullable java.lang.String Address3)
	{
		set_ValueNoCheck (COLUMNNAME_Address3, Address3);
	}

	@Override
	public java.lang.String getAddress3() 
	{
		return get_ValueAsString(COLUMNNAME_Address3);
	}

	@Override
	public void setAddress4 (final @Nullable java.lang.String Address4)
	{
		set_ValueNoCheck (COLUMNNAME_Address4, Address4);
	}

	@Override
	public java.lang.String getAddress4() 
	{
		return get_ValueAsString(COLUMNNAME_Address4);
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
	public void setBirthday (final @Nullable java.sql.Timestamp Birthday)
	{
		set_ValueNoCheck (COLUMNNAME_Birthday, Birthday);
	}

	@Override
	public java.sql.Timestamp getBirthday() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_Birthday);
	}

	@Override
	public void setBPName (final @Nullable java.lang.String BPName)
	{
		set_ValueNoCheck (COLUMNNAME_BPName, BPName);
	}

	@Override
	public java.lang.String getBPName() 
	{
		return get_ValueAsString(COLUMNNAME_BPName);
	}

	@Override
	public void setBPValue (final @Nullable java.lang.String BPValue)
	{
		set_ValueNoCheck (COLUMNNAME_BPValue, BPValue);
	}

	@Override
	public java.lang.String getBPValue() 
	{
		return get_ValueAsString(COLUMNNAME_BPValue);
	}

	/** 
	 * Category AD_Reference_ID=541358
	 * Reference name: C_BPartner_Export_Category
	 */
	public static final int CATEGORY_AD_Reference_ID=541358;
	/** Mitglied = Mitglied */
	public static final String CATEGORY_Mitglied = "Mitglied";
	/** Kunde = Kunde */
	public static final String CATEGORY_Kunde = "Kunde";
	/** Abonnent = Abonnent */
	public static final String CATEGORY_Abonnent = "Abonnent";
	@Override
	public void setCategory (final @Nullable java.lang.String Category)
	{
		set_ValueNoCheck (COLUMNNAME_Category, Category);
	}

	@Override
	public java.lang.String getCategory() 
	{
		return get_ValueAsString(COLUMNNAME_Category);
	}

	@Override
	public void setC_BPartner_Export_ID (final int C_BPartner_Export_ID)
	{
		if (C_BPartner_Export_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_Export_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_Export_ID, C_BPartner_Export_ID);
	}

	@Override
	public int getC_BPartner_Export_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_Export_ID);
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
	public de.metas.order.model.I_C_CompensationGroup_Schema getC_CompensationGroup_Schema()
	{
		return get_ValueAsPO(COLUMNNAME_C_CompensationGroup_Schema_ID, de.metas.order.model.I_C_CompensationGroup_Schema.class);
	}

	@Override
	public void setC_CompensationGroup_Schema(final de.metas.order.model.I_C_CompensationGroup_Schema C_CompensationGroup_Schema)
	{
		set_ValueFromPO(COLUMNNAME_C_CompensationGroup_Schema_ID, de.metas.order.model.I_C_CompensationGroup_Schema.class, C_CompensationGroup_Schema);
	}

	@Override
	public void setC_CompensationGroup_Schema_ID (final int C_CompensationGroup_Schema_ID)
	{
		if (C_CompensationGroup_Schema_ID < 1) 
			set_Value (COLUMNNAME_C_CompensationGroup_Schema_ID, null);
		else 
			set_Value (COLUMNNAME_C_CompensationGroup_Schema_ID, C_CompensationGroup_Schema_ID);
	}

	@Override
	public int getC_CompensationGroup_Schema_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_CompensationGroup_Schema_ID);
	}

	@Override
	public org.compiere.model.I_C_Country getC_Country()
	{
		return get_ValueAsPO(COLUMNNAME_C_Country_ID, org.compiere.model.I_C_Country.class);
	}

	@Override
	public void setC_Country(final org.compiere.model.I_C_Country C_Country)
	{
		set_ValueFromPO(COLUMNNAME_C_Country_ID, org.compiere.model.I_C_Country.class, C_Country);
	}

	@Override
	public void setC_Country_ID (final int C_Country_ID)
	{
		if (C_Country_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Country_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Country_ID, C_Country_ID);
	}

	@Override
	public int getC_Country_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Country_ID);
	}

	@Override
	public void setC_Greeting_ID (final int C_Greeting_ID)
	{
		if (C_Greeting_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Greeting_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Greeting_ID, C_Greeting_ID);
	}

	@Override
	public int getC_Greeting_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Greeting_ID);
	}

	@Override
	public void setCity (final @Nullable java.lang.String City)
	{
		set_ValueNoCheck (COLUMNNAME_City, City);
	}

	@Override
	public java.lang.String getCity() 
	{
		return get_ValueAsString(COLUMNNAME_City);
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
	public org.compiere.model.I_C_Order getC_Order()
	{
		return get_ValueAsPO(COLUMNNAME_C_Order_ID, org.compiere.model.I_C_Order.class);
	}

	@Override
	public void setC_Order(final org.compiere.model.I_C_Order C_Order)
	{
		set_ValueFromPO(COLUMNNAME_C_Order_ID, org.compiere.model.I_C_Order.class, C_Order);
	}

	@Override
	public void setC_Order_ID (final int C_Order_ID)
	{
		if (C_Order_ID < 1) 
			set_Value (COLUMNNAME_C_Order_ID, null);
		else 
			set_Value (COLUMNNAME_C_Order_ID, C_Order_ID);
	}

	@Override
	public int getC_Order_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Order_ID);
	}

	@Override
	public void setEMailUser (final @Nullable java.lang.String EMailUser)
	{
		set_ValueNoCheck (COLUMNNAME_EMailUser, EMailUser);
	}

	@Override
	public java.lang.String getEMailUser() 
	{
		return get_ValueAsString(COLUMNNAME_EMailUser);
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
	public void setFirstname (final @Nullable java.lang.String Firstname)
	{
		set_ValueNoCheck (COLUMNNAME_Firstname, Firstname);
	}

	@Override
	public java.lang.String getFirstname() 
	{
		return get_ValueAsString(COLUMNNAME_Firstname);
	}

	@Override
	public void setGreeting (final @Nullable java.lang.String Greeting)
	{
		set_ValueNoCheck (COLUMNNAME_Greeting, Greeting);
	}

	@Override
	public java.lang.String getGreeting() 
	{
		return get_ValueAsString(COLUMNNAME_Greeting);
	}

	@Override
	public void setHasDifferentBillPartner (final boolean HasDifferentBillPartner)
	{
		set_Value (COLUMNNAME_HasDifferentBillPartner, HasDifferentBillPartner);
	}

	@Override
	public boolean isHasDifferentBillPartner() 
	{
		return get_ValueAsBoolean(COLUMNNAME_HasDifferentBillPartner);
	}

	@Override
	public void setLastname (final @Nullable java.lang.String Lastname)
	{
		set_ValueNoCheck (COLUMNNAME_Lastname, Lastname);
	}

	@Override
	public java.lang.String getLastname() 
	{
		return get_ValueAsString(COLUMNNAME_Lastname);
	}

	@Override
	public void setLetter_Salutation (final @Nullable java.lang.String Letter_Salutation)
	{
		set_ValueNoCheck (COLUMNNAME_Letter_Salutation, Letter_Salutation);
	}

	@Override
	public java.lang.String getLetter_Salutation() 
	{
		return get_ValueAsString(COLUMNNAME_Letter_Salutation);
	}

	@Override
	public void setMasterEndDate (final @Nullable java.sql.Timestamp MasterEndDate)
	{
		set_ValueNoCheck (COLUMNNAME_MasterEndDate, MasterEndDate);
	}

	@Override
	public java.sql.Timestamp getMasterEndDate() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_MasterEndDate);
	}

	@Override
	public void setMasterStartDate (final @Nullable java.sql.Timestamp MasterStartDate)
	{
		set_ValueNoCheck (COLUMNNAME_MasterStartDate, MasterStartDate);
	}

	@Override
	public java.sql.Timestamp getMasterStartDate() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_MasterStartDate);
	}

	@Override
	public void setPostal (final @Nullable java.lang.String Postal)
	{
		set_ValueNoCheck (COLUMNNAME_Postal, Postal);
	}

	@Override
	public java.lang.String getPostal() 
	{
		return get_ValueAsString(COLUMNNAME_Postal);
	}

	/** 
	 * TerminationReason AD_Reference_ID=540761
	 * Reference name: Contracts_TerminationaReason
	 */
	public static final int TERMINATIONREASON_AD_Reference_ID=540761;
	/** HighAge = Hi */
	public static final String TERMINATIONREASON_HighAge = "Hi";
	/** DidNotOrder = Dno */
	public static final String TERMINATIONREASON_DidNotOrder = "Dno";
	/** General = Ge */
	public static final String TERMINATIONREASON_General = "Ge";
	/** Religion = Rel */
	public static final String TERMINATIONREASON_Religion = "Rel";
	/** NoTime = Nt */
	public static final String TERMINATIONREASON_NoTime = "Nt";
	/** TooMuchPapers = Tmp */
	public static final String TERMINATIONREASON_TooMuchPapers = "Tmp";
	/** FinancialReasons = Fr */
	public static final String TERMINATIONREASON_FinancialReasons = "Fr";
	/** TooModern = Tm */
	public static final String TERMINATIONREASON_TooModern = "Tm";
	/** NoInterest = Ni */
	public static final String TERMINATIONREASON_NoInterest = "Ni";
	/** NewSubscriptionType = Nst */
	public static final String TERMINATIONREASON_NewSubscriptionType = "Nst";
	/** GiftNotRenewed = Gnr */
	public static final String TERMINATIONREASON_GiftNotRenewed = "Gnr";
	/** StayingForeign = Sf */
	public static final String TERMINATIONREASON_StayingForeign = "Sf";
	/** Died = Di */
	public static final String TERMINATIONREASON_Died = "Di";
	/** Sick = Si */
	public static final String TERMINATIONREASON_Sick = "Si";
	/** DoubleReader = Dr */
	public static final String TERMINATIONREASON_DoubleReader = "Dr";
	/** SubscriptionSwitch = Ss */
	public static final String TERMINATIONREASON_SubscriptionSwitch = "Ss";
	/** LimitedDelivery = Ld */
	public static final String TERMINATIONREASON_LimitedDelivery = "Ld";
	/** PrivateReasons = Pr */
	public static final String TERMINATIONREASON_PrivateReasons = "Pr";
	/** CanNotRead = Cnr */
	public static final String TERMINATIONREASON_CanNotRead = "Cnr";
	/** NotReachable = Nr */
	public static final String TERMINATIONREASON_NotReachable = "Nr";
	/** IncorrectlyRecorded = Err */
	public static final String TERMINATIONREASON_IncorrectlyRecorded = "Err";
	/** OrgChange = Os */
	public static final String TERMINATIONREASON_OrgChange = "Os";
	@Override
	public void setTerminationReason (final @Nullable java.lang.String TerminationReason)
	{
		set_ValueNoCheck (COLUMNNAME_TerminationReason, TerminationReason);
	}

	@Override
	public java.lang.String getTerminationReason() 
	{
		return get_ValueAsString(COLUMNNAME_TerminationReason);
	}
}