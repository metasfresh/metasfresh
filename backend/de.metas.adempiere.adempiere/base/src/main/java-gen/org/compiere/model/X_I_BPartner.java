// Generated Model - DO NOT CHANGE
package org.compiere.model;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for I_BPartner
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_I_BPartner extends org.compiere.model.PO implements I_I_BPartner, org.compiere.model.I_Persistent
{

	private static final long serialVersionUID = 1546991868L;

    /** Standard Constructor */
    public X_I_BPartner (final Properties ctx, final int I_BPartner_ID, @Nullable final String trxName)
    {
      super (ctx, I_BPartner_ID, trxName);
    }

    /** Load Constructor */
    public X_I_BPartner (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setAccountNo (final @Nullable java.lang.String AccountNo)
	{
		set_Value (COLUMNNAME_AccountNo, AccountNo);
	}

	@Override
	public java.lang.String getAccountNo()
	{
		return get_ValueAsString(COLUMNNAME_AccountNo);
	}

	@Override
	public void setAddress1 (final @Nullable java.lang.String Address1)
	{
		set_Value (COLUMNNAME_Address1, Address1);
	}

	@Override
	public java.lang.String getAddress1()
	{
		return get_ValueAsString(COLUMNNAME_Address1);
	}

	@Override
	public void setAddress2 (final @Nullable java.lang.String Address2)
	{
		set_Value (COLUMNNAME_Address2, Address2);
	}

	@Override
	public java.lang.String getAddress2()
	{
		return get_ValueAsString(COLUMNNAME_Address2);
	}

	@Override
	public void setAddress3 (final @Nullable java.lang.String Address3)
	{
		set_Value (COLUMNNAME_Address3, Address3);
	}

	@Override
	public java.lang.String getAddress3()
	{
		return get_ValueAsString(COLUMNNAME_Address3);
	}

	@Override
	public void setAddress4 (final @Nullable java.lang.String Address4)
	{
		set_Value (COLUMNNAME_Address4, Address4);
	}

	@Override
	public java.lang.String getAddress4()
	{
		return get_ValueAsString(COLUMNNAME_Address4);
	}

	@Override
	public void setAD_Issue_ID (final int AD_Issue_ID)
	{
		if (AD_Issue_ID < 1) 
			set_Value (COLUMNNAME_AD_Issue_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Issue_ID, AD_Issue_ID);
	}

	@Override
	public int getAD_Issue_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Issue_ID);
	}

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
	public org.compiere.model.I_AD_PrintFormat getAD_PrintFormat()
	{
		return get_ValueAsPO(COLUMNNAME_AD_PrintFormat_ID, org.compiere.model.I_AD_PrintFormat.class);
	}

	@Override
	public void setAD_PrintFormat(final org.compiere.model.I_AD_PrintFormat AD_PrintFormat)
	{
		set_ValueFromPO(COLUMNNAME_AD_PrintFormat_ID, org.compiere.model.I_AD_PrintFormat.class, AD_PrintFormat);
	}

	@Override
	public void setAD_PrintFormat_ID (final int AD_PrintFormat_ID)
	{
		if (AD_PrintFormat_ID < 1) 
			set_Value (COLUMNNAME_AD_PrintFormat_ID, null);
		else 
			set_Value (COLUMNNAME_AD_PrintFormat_ID, AD_PrintFormat_ID);
	}

	@Override
	public int getAD_PrintFormat_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_PrintFormat_ID);
	}

	@Override
	public void setAD_User_ExternalId (final @Nullable java.lang.String AD_User_ExternalId)
	{
		set_Value (COLUMNNAME_AD_User_ExternalId, AD_User_ExternalId);
	}

	@Override
	public java.lang.String getAD_User_ExternalId() 
	{
		return get_ValueAsString(COLUMNNAME_AD_User_ExternalId);
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
	public void setAD_User_Memo1 (final @Nullable java.lang.String AD_User_Memo1)
	{
		set_Value (COLUMNNAME_AD_User_Memo1, AD_User_Memo1);
	}

	@Override
	public java.lang.String getAD_User_Memo1() 
	{
		return get_ValueAsString(COLUMNNAME_AD_User_Memo1);
	}

	@Override
	public void setAD_User_Memo2 (final @Nullable java.lang.String AD_User_Memo2)
	{
		set_Value (COLUMNNAME_AD_User_Memo2, AD_User_Memo2);
	}

	@Override
	public java.lang.String getAD_User_Memo2() 
	{
		return get_ValueAsString(COLUMNNAME_AD_User_Memo2);
	}

	@Override
	public void setAD_User_Memo3 (final @Nullable java.lang.String AD_User_Memo3)
	{
		set_Value (COLUMNNAME_AD_User_Memo3, AD_User_Memo3);
	}

	@Override
	public java.lang.String getAD_User_Memo3() 
	{
		return get_ValueAsString(COLUMNNAME_AD_User_Memo3);
	}

	@Override
	public void setAD_User_Memo4 (final @Nullable java.lang.String AD_User_Memo4)
	{
		set_Value (COLUMNNAME_AD_User_Memo4, AD_User_Memo4);
	}

	@Override
	public java.lang.String getAD_User_Memo4() 
	{
		return get_ValueAsString(COLUMNNAME_AD_User_Memo4);
	}

	@Override
	public void setAggregationName (final @Nullable java.lang.String AggregationName)
	{
		set_Value (COLUMNNAME_AggregationName, AggregationName);
	}

	@Override
	public java.lang.String getAggregationName() 
	{
		return get_ValueAsString(COLUMNNAME_AggregationName);
	}

	@Override
	public void setA_Name (final @Nullable java.lang.String A_Name)
	{
		set_Value (COLUMNNAME_A_Name, A_Name);
	}

	@Override
	public java.lang.String getA_Name()
	{
		return get_ValueAsString(COLUMNNAME_A_Name);
	}

	@Override
	public void setBankDetails (final @Nullable java.lang.String BankDetails)
	{
		set_Value (COLUMNNAME_BankDetails, BankDetails);
	}

	@Override
	public java.lang.String getBankDetails()
	{
		return get_ValueAsString(COLUMNNAME_BankDetails);
	}

	@Override
	public void setBirthday (final @Nullable java.sql.Timestamp Birthday)
	{
		set_Value (COLUMNNAME_Birthday, Birthday);
	}

	@Override
	public java.sql.Timestamp getBirthday() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_Birthday);
	}

	@Override
	public void setBPContactGreeting (final @Nullable java.lang.String BPContactGreeting)
	{
		set_Value (COLUMNNAME_BPContactGreeting, BPContactGreeting);
	}

	@Override
	public java.lang.String getBPContactGreeting() 
	{
		return get_ValueAsString(COLUMNNAME_BPContactGreeting);
	}

	@Override
	public void setBPValue (final @Nullable java.lang.String BPValue)
	{
		set_Value (COLUMNNAME_BPValue, BPValue);
	}

	@Override
	public java.lang.String getBPValue() 
	{
		return get_ValueAsString(COLUMNNAME_BPValue);
	}

	@Override
	public void setC_Aggregation_ID (final int C_Aggregation_ID)
	{
		if (C_Aggregation_ID < 1) 
			set_Value (COLUMNNAME_C_Aggregation_ID, null);
		else 
			set_Value (COLUMNNAME_C_Aggregation_ID, C_Aggregation_ID);
	}

	@Override
	public int getC_Aggregation_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Aggregation_ID);
	}

	@Override
	public void setC_BPartner_ExternalId (final @Nullable java.lang.String C_BPartner_ExternalId)
	{
		set_Value (COLUMNNAME_C_BPartner_ExternalId, C_BPartner_ExternalId);
	}

	@Override
	public java.lang.String getC_BPartner_ExternalId()
	{
		return get_ValueAsString(COLUMNNAME_C_BPartner_ExternalId);
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
	public void setC_BPartner_Location_ExternalId (final @Nullable java.lang.String C_BPartner_Location_ExternalId)
	{
		set_Value (COLUMNNAME_C_BPartner_Location_ExternalId, C_BPartner_Location_ExternalId);
	}

	@Override
	public java.lang.String getC_BPartner_Location_ExternalId()
	{
		return get_ValueAsString(COLUMNNAME_C_BPartner_Location_ExternalId);
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
	public void setC_BPartner_Memo (final @Nullable java.lang.String C_BPartner_Memo)
	{
		set_Value (COLUMNNAME_C_BPartner_Memo, C_BPartner_Memo);
	}

	@Override
	public java.lang.String getC_BPartner_Memo()
	{
		return get_ValueAsString(COLUMNNAME_C_BPartner_Memo);
	}

	@Override
	public void setC_BP_BankAccount_ID (final int C_BP_BankAccount_ID)
	{
		if (C_BP_BankAccount_ID < 1) 
			set_Value (COLUMNNAME_C_BP_BankAccount_ID, null);
		else 
			set_Value (COLUMNNAME_C_BP_BankAccount_ID, C_BP_BankAccount_ID);
	}

	@Override
	public int getC_BP_BankAccount_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BP_BankAccount_ID);
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
	public org.compiere.model.I_C_BP_PrintFormat getC_BP_PrintFormat()
	{
		return get_ValueAsPO(COLUMNNAME_C_BP_PrintFormat_ID, org.compiere.model.I_C_BP_PrintFormat.class);
	}

	@Override
	public void setC_BP_PrintFormat(final org.compiere.model.I_C_BP_PrintFormat C_BP_PrintFormat)
	{
		set_ValueFromPO(COLUMNNAME_C_BP_PrintFormat_ID, org.compiere.model.I_C_BP_PrintFormat.class, C_BP_PrintFormat);
	}

	@Override
	public void setC_BP_PrintFormat_ID (final int C_BP_PrintFormat_ID)
	{
		if (C_BP_PrintFormat_ID < 1) 
			set_Value (COLUMNNAME_C_BP_PrintFormat_ID, null);
		else 
			set_Value (COLUMNNAME_C_BP_PrintFormat_ID, C_BP_PrintFormat_ID);
	}

	@Override
	public int getC_BP_PrintFormat_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BP_PrintFormat_ID);
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
			set_Value (COLUMNNAME_C_Country_ID, null);
		else 
			set_Value (COLUMNNAME_C_Country_ID, C_Country_ID);
	}

	@Override
	public int getC_Country_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Country_ID);
	}

	@Override
	public org.compiere.model.I_C_DataImport getC_DataImport()
	{
		return get_ValueAsPO(COLUMNNAME_C_DataImport_ID, org.compiere.model.I_C_DataImport.class);
	}

	@Override
	public void setC_DataImport(final org.compiere.model.I_C_DataImport C_DataImport)
	{
		set_ValueFromPO(COLUMNNAME_C_DataImport_ID, org.compiere.model.I_C_DataImport.class, C_DataImport);
	}

	@Override
	public void setC_DataImport_ID (final int C_DataImport_ID)
	{
		if (C_DataImport_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_DataImport_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_DataImport_ID, C_DataImport_ID);
	}

	@Override
	public int getC_DataImport_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_DataImport_ID);
	}

	@Override
	public org.compiere.model.I_C_DataImport_Run getC_DataImport_Run()
	{
		return get_ValueAsPO(COLUMNNAME_C_DataImport_Run_ID, org.compiere.model.I_C_DataImport_Run.class);
	}

	@Override
	public void setC_DataImport_Run(final org.compiere.model.I_C_DataImport_Run C_DataImport_Run)
	{
		set_ValueFromPO(COLUMNNAME_C_DataImport_Run_ID, org.compiere.model.I_C_DataImport_Run.class, C_DataImport_Run);
	}

	@Override
	public void setC_DataImport_Run_ID (final int C_DataImport_Run_ID)
	{
		if (C_DataImport_Run_ID < 1) 
			set_Value (COLUMNNAME_C_DataImport_Run_ID, null);
		else 
			set_Value (COLUMNNAME_C_DataImport_Run_ID, C_DataImport_Run_ID);
	}

	@Override
	public int getC_DataImport_Run_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_DataImport_Run_ID);
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
	public org.compiere.model.I_C_InvoiceSchedule getC_InvoiceSchedule()
	{
		return get_ValueAsPO(COLUMNNAME_C_InvoiceSchedule_ID, org.compiere.model.I_C_InvoiceSchedule.class);
	}

	@Override
	public void setC_InvoiceSchedule(final org.compiere.model.I_C_InvoiceSchedule C_InvoiceSchedule)
	{
		set_ValueFromPO(COLUMNNAME_C_InvoiceSchedule_ID, org.compiere.model.I_C_InvoiceSchedule.class, C_InvoiceSchedule);
	}

	@Override
	public void setC_InvoiceSchedule_ID (final int C_InvoiceSchedule_ID)
	{
		if (C_InvoiceSchedule_ID < 1) 
			set_Value (COLUMNNAME_C_InvoiceSchedule_ID, null);
		else 
			set_Value (COLUMNNAME_C_InvoiceSchedule_ID, C_InvoiceSchedule_ID);
	}

	@Override
	public int getC_InvoiceSchedule_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_InvoiceSchedule_ID);
	}

	@Override
	public void setCity (final @Nullable java.lang.String City)
	{
		set_Value (COLUMNNAME_City, City);
	}

	@Override
	public java.lang.String getCity()
	{
		return get_ValueAsString(COLUMNNAME_City);
	}

	@Override
	public org.compiere.model.I_C_Job getC_Job()
	{
		return get_ValueAsPO(COLUMNNAME_C_Job_ID, org.compiere.model.I_C_Job.class);
	}

	@Override
	public void setC_Job(final org.compiere.model.I_C_Job C_Job)
	{
		set_ValueFromPO(COLUMNNAME_C_Job_ID, org.compiere.model.I_C_Job.class, C_Job);
	}

	@Override
	public void setC_Job_ID (final int C_Job_ID)
	{
		if (C_Job_ID < 1) 
			set_Value (COLUMNNAME_C_Job_ID, null);
		else 
			set_Value (COLUMNNAME_C_Job_ID, C_Job_ID);
	}

	@Override
	public int getC_Job_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Job_ID);
	}

	@Override
	public void setComments (final @Nullable java.lang.String Comments)
	{
		set_Value (COLUMNNAME_Comments, Comments);
	}

	@Override
	public java.lang.String getComments() 
	{
		return get_ValueAsString(COLUMNNAME_Comments);
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
	public void setContactDescription (final @Nullable java.lang.String ContactDescription)
	{
		set_Value (COLUMNNAME_ContactDescription, ContactDescription);
	}

	@Override
	public java.lang.String getContactDescription() 
	{
		return get_ValueAsString(COLUMNNAME_ContactDescription);
	}

	@Override
	public void setContactName (final @Nullable java.lang.String ContactName)
	{
		set_Value (COLUMNNAME_ContactName, ContactName);
	}

	@Override
	public java.lang.String getContactName() 
	{
		return get_ValueAsString(COLUMNNAME_ContactName);
	}

	@Override
	public void setCountryCode (final @Nullable java.lang.String CountryCode)
	{
		set_Value (COLUMNNAME_CountryCode, CountryCode);
	}

	@Override
	public java.lang.String getCountryCode() 
	{
		return get_ValueAsString(COLUMNNAME_CountryCode);
	}

	@Override
	public void setCountryName (final @Nullable java.lang.String CountryName)
	{
		set_Value (COLUMNNAME_CountryName, CountryName);
	}

	@Override
	public java.lang.String getCountryName() 
	{
		return get_ValueAsString(COLUMNNAME_CountryName);
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
	public void setCreditLimit (final @Nullable BigDecimal CreditLimit)
	{
		set_Value (COLUMNNAME_CreditLimit, CreditLimit);
	}

	@Override
	public BigDecimal getCreditLimit() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_CreditLimit);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setCreditLimit2 (final @Nullable BigDecimal CreditLimit2)
	{
		set_Value (COLUMNNAME_CreditLimit2, CreditLimit2);
	}

	@Override
	public BigDecimal getCreditLimit2() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_CreditLimit2);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setCreditorId (final int CreditorId)
	{
		set_Value (COLUMNNAME_CreditorId, CreditorId);
	}

	@Override
	public int getCreditorId() 
	{
		return get_ValueAsInt(COLUMNNAME_CreditorId);
	}

	@Override
	public org.compiere.model.I_C_Region getC_Region()
	{
		return get_ValueAsPO(COLUMNNAME_C_Region_ID, org.compiere.model.I_C_Region.class);
	}

	@Override
	public void setC_Region(final org.compiere.model.I_C_Region C_Region)
	{
		set_ValueFromPO(COLUMNNAME_C_Region_ID, org.compiere.model.I_C_Region.class, C_Region);
	}

	@Override
	public void setC_Region_ID (final int C_Region_ID)
	{
		if (C_Region_ID < 1)
			set_Value (COLUMNNAME_C_Region_ID, null);
		else
			set_Value (COLUMNNAME_C_Region_ID, C_Region_ID);
	}

	@Override
	public int getC_Region_ID()
	{
		return get_ValueAsInt(COLUMNNAME_C_Region_ID);
	}

	@Override
	public void setCustomerNoAtVendor (final @Nullable java.lang.String CustomerNoAtVendor)
	{
		set_Value (COLUMNNAME_CustomerNoAtVendor, CustomerNoAtVendor);
	}

	@Override
	public java.lang.String getCustomerNoAtVendor() 
	{
		return get_ValueAsString(COLUMNNAME_CustomerNoAtVendor);
	}

	@Override
	public void setDebtorId (final int DebtorId)
	{
		set_Value(COLUMNNAME_DebtorId, DebtorId);
	}

	@Override
	public int getDebtorId()
	{
		return get_ValueAsInt(COLUMNNAME_DebtorId);
	}

	@Override
	public void setDelivery_Info(final @Nullable java.lang.String Delivery_Info)
	{
		set_Value(COLUMNNAME_Delivery_Info, Delivery_Info);
	}

	@Override
	public java.lang.String getDelivery_Info()
	{
		return get_ValueAsString(COLUMNNAME_Delivery_Info);
	}

	/**
	 * DeliveryViaRule AD_Reference_ID=152
	 * Reference name: C_Order DeliveryViaRule
	 */
	public static final int DELIVERYVIARULE_AD_Reference_ID = 152;
	/**
	 * Pickup = P
	 */
	public static final String DELIVERYVIARULE_Pickup = "P";
	/**
	 * Delivery = D
	 */
	public static final String DELIVERYVIARULE_Delivery = "D";
	/** Shipper = S */
	public static final String DELIVERYVIARULE_Shipper = "S";
	/** Normalpost = NP */
	public static final String DELIVERYVIARULE_Normalpost = "NP";
	/** Luftpost = LU */
	public static final String DELIVERYVIARULE_Luftpost = "LU";
	@Override
	public void setDeliveryViaRule (final @Nullable java.lang.String DeliveryViaRule)
	{
		set_Value (COLUMNNAME_DeliveryViaRule, DeliveryViaRule);
	}

	@Override
	public java.lang.String getDeliveryViaRule() 
	{
		return get_ValueAsString(COLUMNNAME_DeliveryViaRule);
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
	public void setDUNS (final @Nullable java.lang.String DUNS)
	{
		set_Value (COLUMNNAME_DUNS, DUNS);
	}

	@Override
	public java.lang.String getDUNS() 
	{
		return get_ValueAsString(COLUMNNAME_DUNS);
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
	public void setFax (final @Nullable java.lang.String Fax)
	{
		set_Value (COLUMNNAME_Fax, Fax);
	}

	@Override
	public java.lang.String getFax() 
	{
		return get_ValueAsString(COLUMNNAME_Fax);
	}

	@Override
	public void setFirstname (final @Nullable java.lang.String Firstname)
	{
		set_Value (COLUMNNAME_Firstname, Firstname);
	}

	@Override
	public java.lang.String getFirstname() 
	{
		return get_ValueAsString(COLUMNNAME_Firstname);
	}

	@Override
	public void setFirstSale (final @Nullable java.sql.Timestamp FirstSale)
	{
		set_Value (COLUMNNAME_FirstSale, FirstSale);
	}

	@Override
	public java.sql.Timestamp getFirstSale() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_FirstSale);
	}

	@Override
	public void setGLN (final @Nullable java.lang.String GLN)
	{
		set_Value (COLUMNNAME_GLN, GLN);
	}

	@Override
	public java.lang.String getGLN() 
	{
		return get_ValueAsString(COLUMNNAME_GLN);
	}

	@Override
	public void setGlobalId (final @Nullable java.lang.String GlobalId)
	{
		set_Value (COLUMNNAME_GlobalId, GlobalId);
	}

	@Override
	public java.lang.String getGlobalId() 
	{
		return get_ValueAsString(COLUMNNAME_GlobalId);
	}

	@Override
	public void setGroupValue (final @Nullable java.lang.String GroupValue)
	{
		set_Value (COLUMNNAME_GroupValue, GroupValue);
	}

	@Override
	public java.lang.String getGroupValue() 
	{
		return get_ValueAsString(COLUMNNAME_GroupValue);
	}

	@Override
	public void setIBAN (final @Nullable java.lang.String IBAN)
	{
		set_Value (COLUMNNAME_IBAN, IBAN);
	}

	@Override
	public java.lang.String getIBAN()
	{
		return get_ValueAsString(COLUMNNAME_IBAN);
	}

	@Override
	public void setI_BPartner_ID (final int I_BPartner_ID)
	{
		if (I_BPartner_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_I_BPartner_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_I_BPartner_ID, I_BPartner_ID);
	}

	@Override
	public int getI_BPartner_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_I_BPartner_ID);
	}

	@Override
	public void setI_ErrorMsg (final @Nullable java.lang.String I_ErrorMsg)
	{
		set_Value (COLUMNNAME_I_ErrorMsg, I_ErrorMsg);
	}

	@Override
	public java.lang.String getI_ErrorMsg() 
	{
		return get_ValueAsString(COLUMNNAME_I_ErrorMsg);
	}

	@Override
	public void setI_IsImported (final boolean I_IsImported)
	{
		set_Value (COLUMNNAME_I_IsImported, I_IsImported);
	}

	@Override
	public boolean isI_IsImported() 
	{
		return get_ValueAsBoolean(COLUMNNAME_I_IsImported);
	}

	@Override
	public void setI_LineContent (final @Nullable java.lang.String I_LineContent)
	{
		set_Value (COLUMNNAME_I_LineContent, I_LineContent);
	}

	@Override
	public java.lang.String getI_LineContent() 
	{
		return get_ValueAsString(COLUMNNAME_I_LineContent);
	}

	@Override
	public void setI_LineNo (final int I_LineNo)
	{
		set_Value (COLUMNNAME_I_LineNo, I_LineNo);
	}

	@Override
	public int getI_LineNo() 
	{
		return get_ValueAsInt(COLUMNNAME_I_LineNo);
	}

	@Override
	public void setInterestAreaName (final @Nullable java.lang.String InterestAreaName)
	{
		set_Value (COLUMNNAME_InterestAreaName, InterestAreaName);
	}

	@Override
	public java.lang.String getInterestAreaName() 
	{
		return get_ValueAsString(COLUMNNAME_InterestAreaName);
	}

	@Override
	public void setInvoiceSchedule (final @Nullable java.lang.String InvoiceSchedule)
	{
		set_Value (COLUMNNAME_InvoiceSchedule, InvoiceSchedule);
	}

	@Override
	public java.lang.String getInvoiceSchedule() 
	{
		return get_ValueAsString(COLUMNNAME_InvoiceSchedule);
	}

	@Override
	public void setIsActiveStatus (final boolean IsActiveStatus)
	{
		set_Value (COLUMNNAME_IsActiveStatus, IsActiveStatus);
	}

	@Override
	public boolean isActiveStatus() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsActiveStatus);
	}

	@Override
	public void setIsBillTo (final boolean IsBillTo)
	{
		set_Value (COLUMNNAME_IsBillTo, IsBillTo);
	}

	@Override
	public boolean isBillTo() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsBillTo);
	}

	@Override
	public void setIsBillToContact_Default (final boolean IsBillToContact_Default)
	{
		set_Value (COLUMNNAME_IsBillToContact_Default, IsBillToContact_Default);
	}

	@Override
	public boolean isBillToContact_Default() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsBillToContact_Default);
	}

	@Override
	public void setIsBillToDefault (final boolean IsBillToDefault)
	{
		set_Value (COLUMNNAME_IsBillToDefault, IsBillToDefault);
	}

	@Override
	public boolean isBillToDefault() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsBillToDefault);
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
	public void setIsDefaultContact (final boolean IsDefaultContact)
	{
		set_Value (COLUMNNAME_IsDefaultContact, IsDefaultContact);
	}

	@Override
	public boolean isDefaultContact() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsDefaultContact);
	}

	@Override
	public void setIsEmployee (final boolean IsEmployee)
	{
		set_Value (COLUMNNAME_IsEmployee, IsEmployee);
	}

	@Override
	public boolean isEmployee() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsEmployee);
	}

	@Override
	public void setIsManuallyCreated (final boolean IsManuallyCreated)
	{
		set_Value (COLUMNNAME_IsManuallyCreated, IsManuallyCreated);
	}

	@Override
	public boolean isManuallyCreated()
	{
		return get_ValueAsBoolean(COLUMNNAME_IsManuallyCreated);
	}

	@Override
	public void setISO_Code (final @Nullable java.lang.String ISO_Code)
	{
		set_Value (COLUMNNAME_ISO_Code, ISO_Code);
	}

	@Override
	public java.lang.String getISO_Code()
	{
		return get_ValueAsString(COLUMNNAME_ISO_Code);
	}

	@Override
	public void setIsSEPASigned (final boolean IsSEPASigned)
	{
		set_Value (COLUMNNAME_IsSEPASigned, IsSEPASigned);
	}

	@Override
	public boolean isSEPASigned() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsSEPASigned);
	}

	@Override
	public void setIsShipTo (final boolean IsShipTo)
	{
		set_Value (COLUMNNAME_IsShipTo, IsShipTo);
	}

	@Override
	public boolean isShipTo() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsShipTo);
	}

	@Override
	public void setIsShipToContact_Default (final boolean IsShipToContact_Default)
	{
		set_Value (COLUMNNAME_IsShipToContact_Default, IsShipToContact_Default);
	}

	@Override
	public boolean isShipToContact_Default() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsShipToContact_Default);
	}

	@Override
	public void setIsShipToDefault (final boolean IsShipToDefault)
	{
		set_Value (COLUMNNAME_IsShipToDefault, IsShipToDefault);
	}

	@Override
	public boolean isShipToDefault() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsShipToDefault);
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
	public void setJobName (final @Nullable java.lang.String JobName)
	{
		set_Value (COLUMNNAME_JobName, JobName);
	}

	@Override
	public java.lang.String getJobName() 
	{
		return get_ValueAsString(COLUMNNAME_JobName);
	}

	@Override
	public void setLastname (final @Nullable java.lang.String Lastname)
	{
		set_Value (COLUMNNAME_Lastname, Lastname);
	}

	@Override
	public java.lang.String getLastname() 
	{
		return get_ValueAsString(COLUMNNAME_Lastname);
	}

	@Override
	public void setLeadTimeOffset (final int LeadTimeOffset)
	{
		set_Value (COLUMNNAME_LeadTimeOffset, LeadTimeOffset);
	}

	@Override
	public int getLeadTimeOffset() 
	{
		return get_ValueAsInt(COLUMNNAME_LeadTimeOffset);
	}

	@Override
	public void setlocation_bpartner_name (final @Nullable java.lang.String location_bpartner_name)
	{
		set_Value (COLUMNNAME_location_bpartner_name, location_bpartner_name);
	}

	@Override
	public java.lang.String getlocation_bpartner_name()
	{
		return get_ValueAsString(COLUMNNAME_location_bpartner_name);
	}

	@Override
	public void setlocation_name (final @Nullable java.lang.String location_name)
	{
		set_Value (COLUMNNAME_location_name, location_name);
	}

	@Override
	public java.lang.String getlocation_name()
	{
		return get_ValueAsString(COLUMNNAME_location_name);
	}

	@Override
	public void setMemo_Delivery (final @Nullable java.lang.String Memo_Delivery)
	{
		set_Value (COLUMNNAME_Memo_Delivery, Memo_Delivery);
	}

	@Override
	public java.lang.String getMemo_Delivery()
	{
		return get_ValueAsString(COLUMNNAME_Memo_Delivery);
	}

	@Override
	public void setMemo_Invoicing (final @Nullable java.lang.String Memo_Invoicing)
	{
		set_Value (COLUMNNAME_Memo_Invoicing, Memo_Invoicing);
	}

	@Override
	public java.lang.String getMemo_Invoicing()
	{
		return get_ValueAsString(COLUMNNAME_Memo_Invoicing);
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
	public org.compiere.model.I_M_Shipper getM_Shipper()
	{
		return get_ValueAsPO(COLUMNNAME_M_Shipper_ID, org.compiere.model.I_M_Shipper.class);
	}

	@Override
	public void setM_Shipper(final org.compiere.model.I_M_Shipper M_Shipper)
	{
		set_ValueFromPO(COLUMNNAME_M_Shipper_ID, org.compiere.model.I_M_Shipper.class, M_Shipper);
	}

	@Override
	public void setM_Shipper_ID (final int M_Shipper_ID)
	{
		if (M_Shipper_ID < 1) 
			set_Value (COLUMNNAME_M_Shipper_ID, null);
		else 
			set_Value (COLUMNNAME_M_Shipper_ID, M_Shipper_ID);
	}

	@Override
	public int getM_Shipper_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Shipper_ID);
	}

	@Override
	public void setNAICS (final @Nullable java.lang.String NAICS)
	{
		set_Value (COLUMNNAME_NAICS, NAICS);
	}

	@Override
	public java.lang.String getNAICS() 
	{
		return get_ValueAsString(COLUMNNAME_NAICS);
	}

	@Override
	public void setName (final @Nullable java.lang.String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	@Override
	public java.lang.String getName() 
	{
		return get_ValueAsString(COLUMNNAME_Name);
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
	public void setName3 (final @Nullable java.lang.String Name3)
	{
		set_Value (COLUMNNAME_Name3, Name3);
	}

	@Override
	public java.lang.String getName3() 
	{
		return get_ValueAsString(COLUMNNAME_Name3);
	}

	@Override
	public void setOrgValue (final @Nullable java.lang.String OrgValue)
	{
		set_Value (COLUMNNAME_OrgValue, OrgValue);
	}

	@Override
	public java.lang.String getOrgValue() 
	{
		return get_ValueAsString(COLUMNNAME_OrgValue);
	}

	@Override
	public void setPassword (final @Nullable java.lang.String Password)
	{
		set_Value (COLUMNNAME_Password, Password);
	}

	@Override
	public java.lang.String getPassword() 
	{
		return get_ValueAsString(COLUMNNAME_Password);
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
	/** PayPal Extern = V */
	public static final String PAYMENTRULE_PayPalExtern = "V";
	/** Kreditkarte Extern = U */
	public static final String PAYMENTRULE_KreditkarteExtern = "U";
	/** Sofortüberweisung = R */
	public static final String PAYMENTRULE_Sofortueberweisung = "R";
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

	/** 
	 * PaymentRulePO AD_Reference_ID=195
	 * Reference name: _Payment Rule
	 */
	public static final int PAYMENTRULEPO_AD_Reference_ID=195;
	/** Cash = B */
	public static final String PAYMENTRULEPO_Cash = "B";
	/** CreditCard = K */
	public static final String PAYMENTRULEPO_CreditCard = "K";
	/** DirectDeposit = T */
	public static final String PAYMENTRULEPO_DirectDeposit = "T";
	/** Check = S */
	public static final String PAYMENTRULEPO_Check = "S";
	/** OnCredit = P */
	public static final String PAYMENTRULEPO_OnCredit = "P";
	/** DirectDebit = D */
	public static final String PAYMENTRULEPO_DirectDebit = "D";
	/** Mixed = M */
	public static final String PAYMENTRULEPO_Mixed = "M";
	/** PayPal = L */
	public static final String PAYMENTRULEPO_PayPal = "L";
	/** PayPal Extern = V */
	public static final String PAYMENTRULEPO_PayPalExtern = "V";
	/** Kreditkarte Extern = U */
	public static final String PAYMENTRULEPO_KreditkarteExtern = "U";
	/** Sofortüberweisung = R */
	public static final String PAYMENTRULEPO_Sofortueberweisung = "R";
	@Override
	public void setPaymentRulePO (final @Nullable java.lang.String PaymentRulePO)
	{
		set_Value (COLUMNNAME_PaymentRulePO, PaymentRulePO);
	}

	@Override
	public java.lang.String getPaymentRulePO() 
	{
		return get_ValueAsString(COLUMNNAME_PaymentRulePO);
	}

	@Override
	public void setPaymentTerm (final @Nullable java.lang.String PaymentTerm)
	{
		set_Value (COLUMNNAME_PaymentTerm, PaymentTerm);
	}

	@Override
	public java.lang.String getPaymentTerm() 
	{
		return get_ValueAsString(COLUMNNAME_PaymentTerm);
	}

	@Override
	public void setPaymentTermValue (final @Nullable java.lang.String PaymentTermValue)
	{
		set_Value (COLUMNNAME_PaymentTermValue, PaymentTermValue);
	}

	@Override
	public java.lang.String getPaymentTermValue() 
	{
		return get_ValueAsString(COLUMNNAME_PaymentTermValue);
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
	public void setPhone2 (final @Nullable java.lang.String Phone2)
	{
		set_Value (COLUMNNAME_Phone2, Phone2);
	}

	@Override
	public java.lang.String getPhone2() 
	{
		return get_ValueAsString(COLUMNNAME_Phone2);
	}

	@Override
	public void setPOBox (final @Nullable java.lang.String POBox)
	{
		set_Value (COLUMNNAME_POBox, POBox);
	}

	@Override
	public java.lang.String getPOBox()
	{
		return get_ValueAsString(COLUMNNAME_POBox);
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
	public void setPO_PricingSystem_Value (final @Nullable java.lang.String PO_PricingSystem_Value)
	{
		set_Value (COLUMNNAME_PO_PricingSystem_Value, PO_PricingSystem_Value);
	}

	@Override
	public java.lang.String getPO_PricingSystem_Value() 
	{
		return get_ValueAsString(COLUMNNAME_PO_PricingSystem_Value);
	}

	@Override
	public void setPostal (final @Nullable java.lang.String Postal)
	{
		set_Value (COLUMNNAME_Postal, Postal);
	}

	@Override
	public java.lang.String getPostal() 
	{
		return get_ValueAsString(COLUMNNAME_Postal);
	}

	@Override
	public void setPostal_Add (final @Nullable java.lang.String Postal_Add)
	{
		set_Value (COLUMNNAME_Postal_Add, Postal_Add);
	}

	@Override
	public java.lang.String getPostal_Add() 
	{
		return get_ValueAsString(COLUMNNAME_Postal_Add);
	}

	@Override
	public void setPricingSystem_Value (final @Nullable java.lang.String PricingSystem_Value)
	{
		set_Value (COLUMNNAME_PricingSystem_Value, PricingSystem_Value);
	}

	@Override
	public java.lang.String getPricingSystem_Value() 
	{
		return get_ValueAsString(COLUMNNAME_PricingSystem_Value);
	}

	@Override
	public void setPrintFormat_Name (final @Nullable java.lang.String PrintFormat_Name)
	{
		set_Value (COLUMNNAME_PrintFormat_Name, PrintFormat_Name);
	}

	@Override
	public java.lang.String getPrintFormat_Name() 
	{
		return get_ValueAsString(COLUMNNAME_PrintFormat_Name);
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

	@Override
	public void setQR_IBAN (final @Nullable java.lang.String QR_IBAN)
	{
		set_Value (COLUMNNAME_QR_IBAN, QR_IBAN);
	}

	@Override
	public java.lang.String getQR_IBAN()
	{
		return get_ValueAsString(COLUMNNAME_QR_IBAN);
	}

	@Override
	public void setQualification (final @Nullable java.lang.String Qualification)
	{
		set_Value (COLUMNNAME_Qualification, Qualification);
	}

	@Override
	public java.lang.String getQualification() 
	{
		return get_ValueAsString(COLUMNNAME_Qualification);
	}

	@Override
	public void setRegionName (final @Nullable java.lang.String RegionName)
	{
		set_Value (COLUMNNAME_RegionName, RegionName);
	}

	@Override
	public java.lang.String getRegionName()
	{
		return get_ValueAsString(COLUMNNAME_RegionName);
	}

	@Override
	public org.compiere.model.I_R_InterestArea getR_InterestArea()
	{
		return get_ValueAsPO(COLUMNNAME_R_InterestArea_ID, org.compiere.model.I_R_InterestArea.class);
	}

	@Override
	public void setR_InterestArea(final org.compiere.model.I_R_InterestArea R_InterestArea)
	{
		set_ValueFromPO(COLUMNNAME_R_InterestArea_ID, org.compiere.model.I_R_InterestArea.class, R_InterestArea);
	}

	@Override
	public void setR_InterestArea_ID (final int R_InterestArea_ID)
	{
		if (R_InterestArea_ID < 1) 
			set_Value (COLUMNNAME_R_InterestArea_ID, null);
		else 
			set_Value (COLUMNNAME_R_InterestArea_ID, R_InterestArea_ID);
	}

	@Override
	public int getR_InterestArea_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_R_InterestArea_ID);
	}

	@Override
	public void setSalesgroup (final @Nullable java.lang.String Salesgroup)
	{
		set_Value (COLUMNNAME_Salesgroup, Salesgroup);
	}

	@Override
	public java.lang.String getSalesgroup() 
	{
		return get_ValueAsString(COLUMNNAME_Salesgroup);
	}

	@Override
	public void setShelfLifeMinDays (final int ShelfLifeMinDays)
	{
		set_Value (COLUMNNAME_ShelfLifeMinDays, ShelfLifeMinDays);
	}

	@Override
	public int getShelfLifeMinDays() 
	{
		return get_ValueAsInt(COLUMNNAME_ShelfLifeMinDays);
	}

	@Override
	public void setShipperName (final @Nullable java.lang.String ShipperName)
	{
		set_Value (COLUMNNAME_ShipperName, ShipperName);
	}

	@Override
	public java.lang.String getShipperName() 
	{
		return get_ValueAsString(COLUMNNAME_ShipperName);
	}

	@Override
	public void setShortDescription (final @Nullable java.lang.String ShortDescription)
	{
		set_Value (COLUMNNAME_ShortDescription, ShortDescription);
	}

	@Override
	public java.lang.String getShortDescription() 
	{
		return get_ValueAsString(COLUMNNAME_ShortDescription);
	}

	@Override
	public void setSwiftCode (final @Nullable java.lang.String SwiftCode)
	{
		set_Value (COLUMNNAME_SwiftCode, SwiftCode);
	}

	@Override
	public java.lang.String getSwiftCode() 
	{
		return get_ValueAsString(COLUMNNAME_SwiftCode);
	}

	@Override
	public void setTaxID (final @Nullable java.lang.String TaxID)
	{
		set_Value (COLUMNNAME_TaxID, TaxID);
	}

	@Override
	public java.lang.String getTaxID() 
	{
		return get_ValueAsString(COLUMNNAME_TaxID);
	}

	@Override
	public void setTitle (final @Nullable java.lang.String Title)
	{
		set_Value (COLUMNNAME_Title, Title);
	}

	@Override
	public java.lang.String getTitle() 
	{
		return get_ValueAsString(COLUMNNAME_Title);
	}

	@Override
	public void setURL (final @Nullable java.lang.String URL)
	{
		set_Value (COLUMNNAME_URL, URL);
	}

	@Override
	public java.lang.String getURL() 
	{
		return get_ValueAsString(COLUMNNAME_URL);
	}

	@Override
	public void setURL3 (final @Nullable java.lang.String URL3)
	{
		set_Value (COLUMNNAME_URL3, URL3);
	}

	@Override
	public java.lang.String getURL3() 
	{
		return get_ValueAsString(COLUMNNAME_URL3);
	}

	@Override
	public void setVendorCategory (final @Nullable java.lang.String VendorCategory)
	{
		set_Value (COLUMNNAME_VendorCategory, VendorCategory);
	}

	@Override
	public java.lang.String getVendorCategory() 
	{
		return get_ValueAsString(COLUMNNAME_VendorCategory);
	}
}