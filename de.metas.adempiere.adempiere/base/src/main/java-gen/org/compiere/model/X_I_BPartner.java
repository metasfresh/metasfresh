/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for I_BPartner
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_I_BPartner extends org.compiere.model.PO implements I_I_BPartner, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 937420894L;

    /** Standard Constructor */
    public X_I_BPartner (Properties ctx, int I_BPartner_ID, String trxName)
    {
      super (ctx, I_BPartner_ID, trxName);
      /** if (I_BPartner_ID == 0)
        {
			setI_BPartner_ID (0);
			setI_IsImported (false); // N
			setIsActiveStatus (true); // Y
			setIsBillTo (false); // N
			setIsBillToContact_Default (false); // N
			setIsBillToDefault (false); // N
			setIsDefaultContact (false); // N
			setIsSEPASigned (false); // N
			setIsShipTo (false); // N
			setIsShipToContact_Default (false); // N
			setIsShipToDefault (false); // N
        } */
    }

    /** Load Constructor */
    public X_I_BPartner (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Sprache.
		@param AD_Language 
		Sprache für diesen Eintrag
	  */
	@Override
	public void setAD_Language (java.lang.String AD_Language)
	{
		set_Value (COLUMNNAME_AD_Language, AD_Language);
	}

	/** Get Sprache.
		@return Sprache für diesen Eintrag
	  */
	@Override
	public java.lang.String getAD_Language () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_AD_Language);
	}

	@Override
	public org.compiere.model.I_AD_PrintFormat getAD_PrintFormat() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_PrintFormat_ID, org.compiere.model.I_AD_PrintFormat.class);
	}

	@Override
	public void setAD_PrintFormat(org.compiere.model.I_AD_PrintFormat AD_PrintFormat)
	{
		set_ValueFromPO(COLUMNNAME_AD_PrintFormat_ID, org.compiere.model.I_AD_PrintFormat.class, AD_PrintFormat);
	}

	/** Set Druck - Format.
		@param AD_PrintFormat_ID 
		Data Print Format
	  */
	@Override
	public void setAD_PrintFormat_ID (int AD_PrintFormat_ID)
	{
		if (AD_PrintFormat_ID < 1) 
			set_Value (COLUMNNAME_AD_PrintFormat_ID, null);
		else 
			set_Value (COLUMNNAME_AD_PrintFormat_ID, Integer.valueOf(AD_PrintFormat_ID));
	}

	/** Get Druck - Format.
		@return Data Print Format
	  */
	@Override
	public int getAD_PrintFormat_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_PrintFormat_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Benutzer ExternalId.
		@param AD_User_ExternalId Benutzer ExternalId	  */
	@Override
	public void setAD_User_ExternalId (java.lang.String AD_User_ExternalId)
	{
		set_Value (COLUMNNAME_AD_User_ExternalId, AD_User_ExternalId);
	}

	/** Get Benutzer ExternalId.
		@return Benutzer ExternalId	  */
	@Override
	public java.lang.String getAD_User_ExternalId () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_AD_User_ExternalId);
	}

	@Override
	public org.compiere.model.I_AD_User getAD_User() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_User_ID, org.compiere.model.I_AD_User.class);
	}

	@Override
	public void setAD_User(org.compiere.model.I_AD_User AD_User)
	{
		set_ValueFromPO(COLUMNNAME_AD_User_ID, org.compiere.model.I_AD_User.class, AD_User);
	}

	/** Set Ansprechpartner.
		@param AD_User_ID 
		User within the system - Internal or Business Partner Contact
	  */
	@Override
	public void setAD_User_ID (int AD_User_ID)
	{
		if (AD_User_ID < 0) 
			set_Value (COLUMNNAME_AD_User_ID, null);
		else 
			set_Value (COLUMNNAME_AD_User_ID, Integer.valueOf(AD_User_ID));
	}

	/** Get Ansprechpartner.
		@return User within the system - Internal or Business Partner Contact
	  */
	@Override
	public int getAD_User_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_User_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set AD_User_Memo1.
		@param AD_User_Memo1 
		Memo Text
	  */
	@Override
	public void setAD_User_Memo1 (java.lang.String AD_User_Memo1)
	{
		set_Value (COLUMNNAME_AD_User_Memo1, AD_User_Memo1);
	}

	/** Get AD_User_Memo1.
		@return Memo Text
	  */
	@Override
	public java.lang.String getAD_User_Memo1 () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_AD_User_Memo1);
	}

	/** Set AD_User_Memo2.
		@param AD_User_Memo2 
		Memo Text
	  */
	@Override
	public void setAD_User_Memo2 (java.lang.String AD_User_Memo2)
	{
		set_Value (COLUMNNAME_AD_User_Memo2, AD_User_Memo2);
	}

	/** Get AD_User_Memo2.
		@return Memo Text
	  */
	@Override
	public java.lang.String getAD_User_Memo2 () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_AD_User_Memo2);
	}

	/** Set AD_User_Memo3.
		@param AD_User_Memo3 
		Memo Text
	  */
	@Override
	public void setAD_User_Memo3 (java.lang.String AD_User_Memo3)
	{
		set_Value (COLUMNNAME_AD_User_Memo3, AD_User_Memo3);
	}

	/** Get AD_User_Memo3.
		@return Memo Text
	  */
	@Override
	public java.lang.String getAD_User_Memo3 () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_AD_User_Memo3);
	}

	/** Set AD_User_Memo4.
		@param AD_User_Memo4 
		Memo Text
	  */
	@Override
	public void setAD_User_Memo4 (java.lang.String AD_User_Memo4)
	{
		set_Value (COLUMNNAME_AD_User_Memo4, AD_User_Memo4);
	}

	/** Get AD_User_Memo4.
		@return Memo Text
	  */
	@Override
	public java.lang.String getAD_User_Memo4 () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_AD_User_Memo4);
	}

	/** Set Straße und Nr..
		@param Address1 
		Adresszeile 1 für diesen Standort
	  */
	@Override
	public void setAddress1 (java.lang.String Address1)
	{
		set_Value (COLUMNNAME_Address1, Address1);
	}

	/** Get Straße und Nr..
		@return Adresszeile 1 für diesen Standort
	  */
	@Override
	public java.lang.String getAddress1 () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Address1);
	}

	/** Set Adresszusatz.
		@param Address2 
		Adresszeile 2 für diesen Standort
	  */
	@Override
	public void setAddress2 (java.lang.String Address2)
	{
		set_Value (COLUMNNAME_Address2, Address2);
	}

	/** Get Adresszusatz.
		@return Adresszeile 2 für diesen Standort
	  */
	@Override
	public java.lang.String getAddress2 () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Address2);
	}

	/** Set Adresszeile 3.
		@param Address3 
		Adresszeilee 3 für diesen Standort
	  */
	@Override
	public void setAddress3 (java.lang.String Address3)
	{
		set_Value (COLUMNNAME_Address3, Address3);
	}

	/** Get Adresszeile 3.
		@return Adresszeilee 3 für diesen Standort
	  */
	@Override
	public java.lang.String getAddress3 () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Address3);
	}

	/** Set Adresszusatz.
		@param Address4 
		Adresszeile 4 für diesen Standort
	  */
	@Override
	public void setAddress4 (java.lang.String Address4)
	{
		set_Value (COLUMNNAME_Address4, Address4);
	}

	/** Get Adresszusatz.
		@return Adresszeile 4 für diesen Standort
	  */
	@Override
	public java.lang.String getAddress4 () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Address4);
	}

	/** Set Aggregation Name.
		@param AggregationName Aggregation Name	  */
	@Override
	public void setAggregationName (java.lang.String AggregationName)
	{
		set_Value (COLUMNNAME_AggregationName, AggregationName);
	}

	/** Get Aggregation Name.
		@return Aggregation Name	  */
	@Override
	public java.lang.String getAggregationName () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_AggregationName);
	}

	/** Set Geburtstag.
		@param Birthday 
		Birthday or Anniversary day
	  */
	@Override
	public void setBirthday (java.sql.Timestamp Birthday)
	{
		set_Value (COLUMNNAME_Birthday, Birthday);
	}

	/** Get Geburtstag.
		@return Birthday or Anniversary day
	  */
	@Override
	public java.sql.Timestamp getBirthday () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_Birthday);
	}

	/** Set Kontakt-Anrede.
		@param BPContactGreeting 
		Greeting for Business Partner Contact
	  */
	@Override
	public void setBPContactGreeting (java.lang.String BPContactGreeting)
	{
		set_Value (COLUMNNAME_BPContactGreeting, BPContactGreeting);
	}

	/** Get Kontakt-Anrede.
		@return Greeting for Business Partner Contact
	  */
	@Override
	public java.lang.String getBPContactGreeting () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_BPContactGreeting);
	}

	/** Set Nr..
		@param BPValue 
		Sponsor-Nr.
	  */
	@Override
	public void setBPValue (java.lang.String BPValue)
	{
		set_Value (COLUMNNAME_BPValue, BPValue);
	}

	/** Get Nr..
		@return Sponsor-Nr.
	  */
	@Override
	public java.lang.String getBPValue () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_BPValue);
	}

	/** Set Aggregation Definition.
		@param C_Aggregation_ID Aggregation Definition	  */
	@Override
	public void setC_Aggregation_ID (int C_Aggregation_ID)
	{
		if (C_Aggregation_ID < 1) 
			set_Value (COLUMNNAME_C_Aggregation_ID, null);
		else 
			set_Value (COLUMNNAME_C_Aggregation_ID, Integer.valueOf(C_Aggregation_ID));
	}

	/** Get Aggregation Definition.
		@return Aggregation Definition	  */
	@Override
	public int getC_Aggregation_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Aggregation_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_BP_BankAccount getC_BP_BankAccount() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_BP_BankAccount_ID, org.compiere.model.I_C_BP_BankAccount.class);
	}

	@Override
	public void setC_BP_BankAccount(org.compiere.model.I_C_BP_BankAccount C_BP_BankAccount)
	{
		set_ValueFromPO(COLUMNNAME_C_BP_BankAccount_ID, org.compiere.model.I_C_BP_BankAccount.class, C_BP_BankAccount);
	}

	/** Set Bankverbindung.
		@param C_BP_BankAccount_ID 
		Bankverbindung des Geschäftspartners
	  */
	@Override
	public void setC_BP_BankAccount_ID (int C_BP_BankAccount_ID)
	{
		if (C_BP_BankAccount_ID < 1) 
			set_Value (COLUMNNAME_C_BP_BankAccount_ID, null);
		else 
			set_Value (COLUMNNAME_C_BP_BankAccount_ID, Integer.valueOf(C_BP_BankAccount_ID));
	}

	/** Get Bankverbindung.
		@return Bankverbindung des Geschäftspartners
	  */
	@Override
	public int getC_BP_BankAccount_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BP_BankAccount_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_BP_Group getC_BP_Group() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_BP_Group_ID, org.compiere.model.I_C_BP_Group.class);
	}

	@Override
	public void setC_BP_Group(org.compiere.model.I_C_BP_Group C_BP_Group)
	{
		set_ValueFromPO(COLUMNNAME_C_BP_Group_ID, org.compiere.model.I_C_BP_Group.class, C_BP_Group);
	}

	/** Set Geschäftspartnergruppe.
		@param C_BP_Group_ID 
		Business Partner Group
	  */
	@Override
	public void setC_BP_Group_ID (int C_BP_Group_ID)
	{
		if (C_BP_Group_ID < 1) 
			set_Value (COLUMNNAME_C_BP_Group_ID, null);
		else 
			set_Value (COLUMNNAME_C_BP_Group_ID, Integer.valueOf(C_BP_Group_ID));
	}

	/** Get Geschäftspartnergruppe.
		@return Business Partner Group
	  */
	@Override
	public int getC_BP_Group_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BP_Group_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_BP_PrintFormat getC_BP_PrintFormat() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_BP_PrintFormat_ID, org.compiere.model.I_C_BP_PrintFormat.class);
	}

	@Override
	public void setC_BP_PrintFormat(org.compiere.model.I_C_BP_PrintFormat C_BP_PrintFormat)
	{
		set_ValueFromPO(COLUMNNAME_C_BP_PrintFormat_ID, org.compiere.model.I_C_BP_PrintFormat.class, C_BP_PrintFormat);
	}

	/** Set Geschäftspartner - Druck - Format.
		@param C_BP_PrintFormat_ID Geschäftspartner - Druck - Format	  */
	@Override
	public void setC_BP_PrintFormat_ID (int C_BP_PrintFormat_ID)
	{
		if (C_BP_PrintFormat_ID < 1) 
			set_Value (COLUMNNAME_C_BP_PrintFormat_ID, null);
		else 
			set_Value (COLUMNNAME_C_BP_PrintFormat_ID, Integer.valueOf(C_BP_PrintFormat_ID));
	}

	/** Get Geschäftspartner - Druck - Format.
		@return Geschäftspartner - Druck - Format	  */
	@Override
	public int getC_BP_PrintFormat_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BP_PrintFormat_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Geschaftspartner ExternalId.
		@param C_BPartner_ExternalId Geschaftspartner ExternalId	  */
	@Override
	public void setC_BPartner_ExternalId (java.lang.String C_BPartner_ExternalId)
	{
		set_Value (COLUMNNAME_C_BPartner_ExternalId, C_BPartner_ExternalId);
	}

	/** Get Geschaftspartner ExternalId.
		@return Geschaftspartner ExternalId	  */
	@Override
	public java.lang.String getC_BPartner_ExternalId () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_C_BPartner_ExternalId);
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

	/** Set Standort ExternalId.
		@param C_BPartner_Location_ExternalId Standort ExternalId	  */
	@Override
	public void setC_BPartner_Location_ExternalId (java.lang.String C_BPartner_Location_ExternalId)
	{
		set_Value (COLUMNNAME_C_BPartner_Location_ExternalId, C_BPartner_Location_ExternalId);
	}

	/** Get Standort ExternalId.
		@return Standort ExternalId	  */
	@Override
	public java.lang.String getC_BPartner_Location_ExternalId () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_C_BPartner_Location_ExternalId);
	}

	@Override
	public org.compiere.model.I_C_BPartner_Location getC_BPartner_Location() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_BPartner_Location_ID, org.compiere.model.I_C_BPartner_Location.class);
	}

	@Override
	public void setC_BPartner_Location(org.compiere.model.I_C_BPartner_Location C_BPartner_Location)
	{
		set_ValueFromPO(COLUMNNAME_C_BPartner_Location_ID, org.compiere.model.I_C_BPartner_Location.class, C_BPartner_Location);
	}

	/** Set Standort.
		@param C_BPartner_Location_ID 
		Identifies the (ship to) address for this Business Partner
	  */
	@Override
	public void setC_BPartner_Location_ID (int C_BPartner_Location_ID)
	{
		if (C_BPartner_Location_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_Location_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_Location_ID, Integer.valueOf(C_BPartner_Location_ID));
	}

	/** Get Standort.
		@return Identifies the (ship to) address for this Business Partner
	  */
	@Override
	public int getC_BPartner_Location_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_Location_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set C_BPartner_Memo.
		@param C_BPartner_Memo 
		Memo Text
	  */
	@Override
	public void setC_BPartner_Memo (java.lang.String C_BPartner_Memo)
	{
		set_Value (COLUMNNAME_C_BPartner_Memo, C_BPartner_Memo);
	}

	/** Get C_BPartner_Memo.
		@return Memo Text
	  */
	@Override
	public java.lang.String getC_BPartner_Memo () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_C_BPartner_Memo);
	}

	@Override
	public org.compiere.model.I_C_Country getC_Country() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Country_ID, org.compiere.model.I_C_Country.class);
	}

	@Override
	public void setC_Country(org.compiere.model.I_C_Country C_Country)
	{
		set_ValueFromPO(COLUMNNAME_C_Country_ID, org.compiere.model.I_C_Country.class, C_Country);
	}

	/** Set Land.
		@param C_Country_ID 
		Country 
	  */
	@Override
	public void setC_Country_ID (int C_Country_ID)
	{
		if (C_Country_ID < 1) 
			set_Value (COLUMNNAME_C_Country_ID, null);
		else 
			set_Value (COLUMNNAME_C_Country_ID, Integer.valueOf(C_Country_ID));
	}

	/** Get Land.
		@return Country 
	  */
	@Override
	public int getC_Country_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Country_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_DataImport getC_DataImport() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_DataImport_ID, org.compiere.model.I_C_DataImport.class);
	}

	@Override
	public void setC_DataImport(org.compiere.model.I_C_DataImport C_DataImport)
	{
		set_ValueFromPO(COLUMNNAME_C_DataImport_ID, org.compiere.model.I_C_DataImport.class, C_DataImport);
	}

	/** Set Data import.
		@param C_DataImport_ID Data import	  */
	@Override
	public void setC_DataImport_ID (int C_DataImport_ID)
	{
		if (C_DataImport_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_DataImport_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_DataImport_ID, Integer.valueOf(C_DataImport_ID));
	}

	/** Get Data import.
		@return Data import	  */
	@Override
	public int getC_DataImport_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_DataImport_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_Greeting getC_Greeting() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Greeting_ID, org.compiere.model.I_C_Greeting.class);
	}

	@Override
	public void setC_Greeting(org.compiere.model.I_C_Greeting C_Greeting)
	{
		set_ValueFromPO(COLUMNNAME_C_Greeting_ID, org.compiere.model.I_C_Greeting.class, C_Greeting);
	}

	/** Set Anrede (ID).
		@param C_Greeting_ID 
		Anrede zum Druck auf Korrespondenz
	  */
	@Override
	public void setC_Greeting_ID (int C_Greeting_ID)
	{
		if (C_Greeting_ID < 1) 
			set_Value (COLUMNNAME_C_Greeting_ID, null);
		else 
			set_Value (COLUMNNAME_C_Greeting_ID, Integer.valueOf(C_Greeting_ID));
	}

	/** Get Anrede (ID).
		@return Anrede zum Druck auf Korrespondenz
	  */
	@Override
	public int getC_Greeting_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Greeting_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_InvoiceSchedule getC_InvoiceSchedule() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_InvoiceSchedule_ID, org.compiere.model.I_C_InvoiceSchedule.class);
	}

	@Override
	public void setC_InvoiceSchedule(org.compiere.model.I_C_InvoiceSchedule C_InvoiceSchedule)
	{
		set_ValueFromPO(COLUMNNAME_C_InvoiceSchedule_ID, org.compiere.model.I_C_InvoiceSchedule.class, C_InvoiceSchedule);
	}

	/** Set Terminplan Rechnung.
		@param C_InvoiceSchedule_ID 
		Plan für die Rechnungsstellung
	  */
	@Override
	public void setC_InvoiceSchedule_ID (int C_InvoiceSchedule_ID)
	{
		if (C_InvoiceSchedule_ID < 1) 
			set_Value (COLUMNNAME_C_InvoiceSchedule_ID, null);
		else 
			set_Value (COLUMNNAME_C_InvoiceSchedule_ID, Integer.valueOf(C_InvoiceSchedule_ID));
	}

	/** Get Terminplan Rechnung.
		@return Plan für die Rechnungsstellung
	  */
	@Override
	public int getC_InvoiceSchedule_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_InvoiceSchedule_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_Job getC_Job() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Job_ID, org.compiere.model.I_C_Job.class);
	}

	@Override
	public void setC_Job(org.compiere.model.I_C_Job C_Job)
	{
		set_ValueFromPO(COLUMNNAME_C_Job_ID, org.compiere.model.I_C_Job.class, C_Job);
	}

	/** Set Position.
		@param C_Job_ID 
		Position in der Firma
	  */
	@Override
	public void setC_Job_ID (int C_Job_ID)
	{
		if (C_Job_ID < 1) 
			set_Value (COLUMNNAME_C_Job_ID, null);
		else 
			set_Value (COLUMNNAME_C_Job_ID, Integer.valueOf(C_Job_ID));
	}

	/** Get Position.
		@return Position in der Firma
	  */
	@Override
	public int getC_Job_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Job_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_Region getC_Region() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Region_ID, org.compiere.model.I_C_Region.class);
	}

	@Override
	public void setC_Region(org.compiere.model.I_C_Region C_Region)
	{
		set_ValueFromPO(COLUMNNAME_C_Region_ID, org.compiere.model.I_C_Region.class, C_Region);
	}

	/** Set Region.
		@param C_Region_ID 
		Identifies a geographical Region
	  */
	@Override
	public void setC_Region_ID (int C_Region_ID)
	{
		if (C_Region_ID < 1) 
			set_Value (COLUMNNAME_C_Region_ID, null);
		else 
			set_Value (COLUMNNAME_C_Region_ID, Integer.valueOf(C_Region_ID));
	}

	/** Get Region.
		@return Identifies a geographical Region
	  */
	@Override
	public int getC_Region_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Region_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Ort.
		@param City 
		Identifies a City
	  */
	@Override
	public void setCity (java.lang.String City)
	{
		set_Value (COLUMNNAME_City, City);
	}

	/** Get Ort.
		@return Identifies a City
	  */
	@Override
	public java.lang.String getCity () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_City);
	}

	/** Set Bemerkungen.
		@param Comments 
		Comments or additional information
	  */
	@Override
	public void setComments (java.lang.String Comments)
	{
		set_Value (COLUMNNAME_Comments, Comments);
	}

	/** Get Bemerkungen.
		@return Comments or additional information
	  */
	@Override
	public java.lang.String getComments () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Comments);
	}

	/** Set Firmenname.
		@param Companyname Firmenname	  */
	@Override
	public void setCompanyname (java.lang.String Companyname)
	{
		set_Value (COLUMNNAME_Companyname, Companyname);
	}

	/** Get Firmenname.
		@return Firmenname	  */
	@Override
	public java.lang.String getCompanyname () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Companyname);
	}

	/** Set Kontakt-Beschreibung.
		@param ContactDescription 
		Description of Contact
	  */
	@Override
	public void setContactDescription (java.lang.String ContactDescription)
	{
		set_Value (COLUMNNAME_ContactDescription, ContactDescription);
	}

	/** Get Kontakt-Beschreibung.
		@return Description of Contact
	  */
	@Override
	public java.lang.String getContactDescription () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ContactDescription);
	}

	/** Set Kontakt-Name.
		@param ContactName 
		Business Partner Contact Name
	  */
	@Override
	public void setContactName (java.lang.String ContactName)
	{
		set_Value (COLUMNNAME_ContactName, ContactName);
	}

	/** Get Kontakt-Name.
		@return Business Partner Contact Name
	  */
	@Override
	public java.lang.String getContactName () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ContactName);
	}

	/** Set ISO Ländercode.
		@param CountryCode 
		Upper-case two-letter alphanumeric ISO Country code according to ISO 3166-1 - http://www.chemie.fu-berlin.de/diverse/doc/ISO_3166.html
	  */
	@Override
	public void setCountryCode (java.lang.String CountryCode)
	{
		set_Value (COLUMNNAME_CountryCode, CountryCode);
	}

	/** Get ISO Ländercode.
		@return Upper-case two-letter alphanumeric ISO Country code according to ISO 3166-1 - http://www.chemie.fu-berlin.de/diverse/doc/ISO_3166.html
	  */
	@Override
	public java.lang.String getCountryCode () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_CountryCode);
	}

	/** Set Land.
		@param CountryName 
		Land
	  */
	@Override
	public void setCountryName (java.lang.String CountryName)
	{
		set_Value (COLUMNNAME_CountryName, CountryName);
	}

	/** Get Land.
		@return Land
	  */
	@Override
	public java.lang.String getCountryName () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_CountryName);
	}

	/** Set Credit limit.
		@param CreditLimit 
		Amount of Credit allowed
	  */
	@Override
	public void setCreditLimit (java.math.BigDecimal CreditLimit)
	{
		set_Value (COLUMNNAME_CreditLimit, CreditLimit);
	}

	/** Get Credit limit.
		@return Amount of Credit allowed
	  */
	@Override
	public java.math.BigDecimal getCreditLimit () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_CreditLimit);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Credit limit 2.
		@param CreditLimit2 
		Amount of Credit allowed
	  */
	@Override
	public void setCreditLimit2 (java.math.BigDecimal CreditLimit2)
	{
		set_Value (COLUMNNAME_CreditLimit2, CreditLimit2);
	}

	/** Get Credit limit 2.
		@return Amount of Credit allowed
	  */
	@Override
	public java.math.BigDecimal getCreditLimit2 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_CreditLimit2);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Kreditoren-Nr.
		@param CreditorId Kreditoren-Nr	  */
	@Override
	public void setCreditorId (int CreditorId)
	{
		set_Value (COLUMNNAME_CreditorId, Integer.valueOf(CreditorId));
	}

	/** Get Kreditoren-Nr.
		@return Kreditoren-Nr	  */
	@Override
	public int getCreditorId () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_CreditorId);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Eigene-Kd. Nr. .
		@param CustomerNoAtVendor Eigene-Kd. Nr. 	  */
	@Override
	public void setCustomerNoAtVendor (java.lang.String CustomerNoAtVendor)
	{
		set_Value (COLUMNNAME_CustomerNoAtVendor, CustomerNoAtVendor);
	}

	/** Get Eigene-Kd. Nr. .
		@return Eigene-Kd. Nr. 	  */
	@Override
	public java.lang.String getCustomerNoAtVendor () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_CustomerNoAtVendor);
	}

	/** Set Debitoren-Nr.
		@param DebtorId Debitoren-Nr	  */
	@Override
	public void setDebtorId (int DebtorId)
	{
		set_Value (COLUMNNAME_DebtorId, Integer.valueOf(DebtorId));
	}

	/** Get Debitoren-Nr.
		@return Debitoren-Nr	  */
	@Override
	public int getDebtorId () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DebtorId);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** 
	 * DeliveryViaRule AD_Reference_ID=152
	 * Reference name: C_Order DeliveryViaRule
	 */
	public static final int DELIVERYVIARULE_AD_Reference_ID=152;
	/** Pickup = P */
	public static final String DELIVERYVIARULE_Pickup = "P";
	/** Delivery = D */
	public static final String DELIVERYVIARULE_Delivery = "D";
	/** Shipper = S */
	public static final String DELIVERYVIARULE_Shipper = "S";
	/** Set Lieferung.
		@param DeliveryViaRule 
		Wie der Auftrag geliefert wird
	  */
	@Override
	public void setDeliveryViaRule (java.lang.String DeliveryViaRule)
	{

		set_Value (COLUMNNAME_DeliveryViaRule, DeliveryViaRule);
	}

	/** Get Lieferung.
		@return Wie der Auftrag geliefert wird
	  */
	@Override
	public java.lang.String getDeliveryViaRule () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DeliveryViaRule);
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

	/** Set D-U-N-S.
		@param DUNS 
		Dun & Bradstreet Number
	  */
	@Override
	public void setDUNS (java.lang.String DUNS)
	{
		set_Value (COLUMNNAME_DUNS, DUNS);
	}

	/** Get D-U-N-S.
		@return Dun & Bradstreet Number
	  */
	@Override
	public java.lang.String getDUNS () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DUNS);
	}

	/** Set eMail.
		@param EMail 
		EMail-Adresse
	  */
	@Override
	public void setEMail (java.lang.String EMail)
	{
		set_Value (COLUMNNAME_EMail, EMail);
	}

	/** Get eMail.
		@return EMail-Adresse
	  */
	@Override
	public java.lang.String getEMail () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_EMail);
	}

	/** Set Fax.
		@param Fax 
		Facsimile number
	  */
	@Override
	public void setFax (java.lang.String Fax)
	{
		set_Value (COLUMNNAME_Fax, Fax);
	}

	/** Get Fax.
		@return Facsimile number
	  */
	@Override
	public java.lang.String getFax () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Fax);
	}

	/** Set Vorname.
		@param Firstname 
		Vorname
	  */
	@Override
	public void setFirstname (java.lang.String Firstname)
	{
		set_Value (COLUMNNAME_Firstname, Firstname);
	}

	/** Get Vorname.
		@return Vorname
	  */
	@Override
	public java.lang.String getFirstname () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Firstname);
	}

	/** Set Erster Verkauf.
		@param FirstSale 
		Datum des Ersten Verkaufs
	  */
	@Override
	public void setFirstSale (java.sql.Timestamp FirstSale)
	{
		set_Value (COLUMNNAME_FirstSale, FirstSale);
	}

	/** Get Erster Verkauf.
		@return Datum des Ersten Verkaufs
	  */
	@Override
	public java.sql.Timestamp getFirstSale () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_FirstSale);
	}

	/** Set GLN.
		@param GLN GLN	  */
	@Override
	public void setGLN (java.lang.String GLN)
	{
		set_Value (COLUMNNAME_GLN, GLN);
	}

	/** Get GLN.
		@return GLN	  */
	@Override
	public java.lang.String getGLN () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_GLN);
	}

	/** Set Global ID.
		@param GlobalID Global ID	  */
	@Override
	public void setGlobalID (java.lang.String GlobalID)
	{
		set_Value (COLUMNNAME_GlobalID, GlobalID);
	}

	/** Get Global ID.
		@return Global ID	  */
	@Override
	public java.lang.String getGlobalID () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_GlobalID);
	}

	/** Set Gruppen-Schlüssel.
		@param GroupValue 
		Business Partner Group Key
	  */
	@Override
	public void setGroupValue (java.lang.String GroupValue)
	{
		set_Value (COLUMNNAME_GroupValue, GroupValue);
	}

	/** Get Gruppen-Schlüssel.
		@return Business Partner Group Key
	  */
	@Override
	public java.lang.String getGroupValue () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_GroupValue);
	}

	/** Set Import - Geschäftspartner.
		@param I_BPartner_ID Import - Geschäftspartner	  */
	@Override
	public void setI_BPartner_ID (int I_BPartner_ID)
	{
		if (I_BPartner_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_I_BPartner_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_I_BPartner_ID, Integer.valueOf(I_BPartner_ID));
	}

	/** Get Import - Geschäftspartner.
		@return Import - Geschäftspartner	  */
	@Override
	public int getI_BPartner_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_I_BPartner_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Import-Fehlermeldung.
		@param I_ErrorMsg 
		Messages generated from import process
	  */
	@Override
	public void setI_ErrorMsg (java.lang.String I_ErrorMsg)
	{
		set_Value (COLUMNNAME_I_ErrorMsg, I_ErrorMsg);
	}

	/** Get Import-Fehlermeldung.
		@return Messages generated from import process
	  */
	@Override
	public java.lang.String getI_ErrorMsg () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_I_ErrorMsg);
	}

	/** Set Importiert.
		@param I_IsImported 
		Has this import been processed
	  */
	@Override
	public void setI_IsImported (boolean I_IsImported)
	{
		set_Value (COLUMNNAME_I_IsImported, Boolean.valueOf(I_IsImported));
	}

	/** Get Importiert.
		@return Has this import been processed
	  */
	@Override
	public boolean isI_IsImported () 
	{
		Object oo = get_Value(COLUMNNAME_I_IsImported);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set IBAN.
		@param IBAN 
		International Bank Account Number
	  */
	@Override
	public void setIBAN (java.lang.String IBAN)
	{
		set_Value (COLUMNNAME_IBAN, IBAN);
	}

	/** Get IBAN.
		@return International Bank Account Number
	  */
	@Override
	public java.lang.String getIBAN () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_IBAN);
	}

	/** Set Interessengebiet.
		@param InterestAreaName 
		Name of the Interest Area
	  */
	@Override
	public void setInterestAreaName (java.lang.String InterestAreaName)
	{
		set_Value (COLUMNNAME_InterestAreaName, InterestAreaName);
	}

	/** Get Interessengebiet.
		@return Name of the Interest Area
	  */
	@Override
	public java.lang.String getInterestAreaName () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_InterestAreaName);
	}

	/** Set Status Terminplan.
		@param InvoiceSchedule Status Terminplan	  */
	@Override
	public void setInvoiceSchedule (java.lang.String InvoiceSchedule)
	{
		set_Value (COLUMNNAME_InvoiceSchedule, InvoiceSchedule);
	}

	/** Get Status Terminplan.
		@return Status Terminplan	  */
	@Override
	public java.lang.String getInvoiceSchedule () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_InvoiceSchedule);
	}

	/** Set Active Status.
		@param IsActiveStatus Active Status	  */
	@Override
	public void setIsActiveStatus (boolean IsActiveStatus)
	{
		set_Value (COLUMNNAME_IsActiveStatus, Boolean.valueOf(IsActiveStatus));
	}

	/** Get Active Status.
		@return Active Status	  */
	@Override
	public boolean isActiveStatus () 
	{
		Object oo = get_Value(COLUMNNAME_IsActiveStatus);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Vorbelegung Rechnung.
		@param IsBillTo 
		Rechnungs-Adresse für diesen Geschäftspartner
	  */
	@Override
	public void setIsBillTo (boolean IsBillTo)
	{
		set_Value (COLUMNNAME_IsBillTo, Boolean.valueOf(IsBillTo));
	}

	/** Get Vorbelegung Rechnung.
		@return Rechnungs-Adresse für diesen Geschäftspartner
	  */
	@Override
	public boolean isBillTo () 
	{
		Object oo = get_Value(COLUMNNAME_IsBillTo);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Rechnungskontakt.
		@param IsBillToContact_Default Rechnungskontakt	  */
	@Override
	public void setIsBillToContact_Default (boolean IsBillToContact_Default)
	{
		set_Value (COLUMNNAME_IsBillToContact_Default, Boolean.valueOf(IsBillToContact_Default));
	}

	/** Get Rechnungskontakt.
		@return Rechnungskontakt	  */
	@Override
	public boolean isBillToContact_Default () 
	{
		Object oo = get_Value(COLUMNNAME_IsBillToContact_Default);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Rechnung Standard Adresse.
		@param IsBillToDefault Rechnung Standard Adresse	  */
	@Override
	public void setIsBillToDefault (boolean IsBillToDefault)
	{
		set_Value (COLUMNNAME_IsBillToDefault, Boolean.valueOf(IsBillToDefault));
	}

	/** Get Rechnung Standard Adresse.
		@return Rechnung Standard Adresse	  */
	@Override
	public boolean isBillToDefault () 
	{
		Object oo = get_Value(COLUMNNAME_IsBillToDefault);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Customer.
		@param IsCustomer 
		Indicates if this Business Partner is a Customer
	  */
	@Override
	public void setIsCustomer (boolean IsCustomer)
	{
		set_Value (COLUMNNAME_IsCustomer, Boolean.valueOf(IsCustomer));
	}

	/** Get Customer.
		@return Indicates if this Business Partner is a Customer
	  */
	@Override
	public boolean isCustomer () 
	{
		Object oo = get_Value(COLUMNNAME_IsCustomer);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Standard-Ansprechpartner.
		@param IsDefaultContact Standard-Ansprechpartner	  */
	@Override
	public void setIsDefaultContact (boolean IsDefaultContact)
	{
		set_Value (COLUMNNAME_IsDefaultContact, Boolean.valueOf(IsDefaultContact));
	}

	/** Get Standard-Ansprechpartner.
		@return Standard-Ansprechpartner	  */
	@Override
	public boolean isDefaultContact () 
	{
		Object oo = get_Value(COLUMNNAME_IsDefaultContact);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Employee.
		@param IsEmployee 
		Indicates if  this Business Partner is an employee
	  */
	@Override
	public void setIsEmployee (boolean IsEmployee)
	{
		set_Value (COLUMNNAME_IsEmployee, Boolean.valueOf(IsEmployee));
	}

	/** Get Employee.
		@return Indicates if  this Business Partner is an employee
	  */
	@Override
	public boolean isEmployee () 
	{
		Object oo = get_Value(COLUMNNAME_IsEmployee);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set SEPA Signed.
		@param IsSEPASigned SEPA Signed	  */
	@Override
	public void setIsSEPASigned (boolean IsSEPASigned)
	{
		set_Value (COLUMNNAME_IsSEPASigned, Boolean.valueOf(IsSEPASigned));
	}

	/** Get SEPA Signed.
		@return SEPA Signed	  */
	@Override
	public boolean isSEPASigned () 
	{
		Object oo = get_Value(COLUMNNAME_IsSEPASigned);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Lieferstandard.
		@param IsShipTo 
		Liefer-Adresse für den Geschäftspartner
	  */
	@Override
	public void setIsShipTo (boolean IsShipTo)
	{
		set_Value (COLUMNNAME_IsShipTo, Boolean.valueOf(IsShipTo));
	}

	/** Get Lieferstandard.
		@return Liefer-Adresse für den Geschäftspartner
	  */
	@Override
	public boolean isShipTo () 
	{
		Object oo = get_Value(COLUMNNAME_IsShipTo);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Lieferkontakt.
		@param IsShipToContact_Default Lieferkontakt	  */
	@Override
	public void setIsShipToContact_Default (boolean IsShipToContact_Default)
	{
		set_Value (COLUMNNAME_IsShipToContact_Default, Boolean.valueOf(IsShipToContact_Default));
	}

	/** Get Lieferkontakt.
		@return Lieferkontakt	  */
	@Override
	public boolean isShipToContact_Default () 
	{
		Object oo = get_Value(COLUMNNAME_IsShipToContact_Default);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Liefer Standard Adresse.
		@param IsShipToDefault Liefer Standard Adresse	  */
	@Override
	public void setIsShipToDefault (boolean IsShipToDefault)
	{
		set_Value (COLUMNNAME_IsShipToDefault, Boolean.valueOf(IsShipToDefault));
	}

	/** Get Liefer Standard Adresse.
		@return Liefer Standard Adresse	  */
	@Override
	public boolean isShipToDefault () 
	{
		Object oo = get_Value(COLUMNNAME_IsShipToDefault);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Vendor.
		@param IsVendor 
		Indicates if this Business Partner is a Vendor
	  */
	@Override
	public void setIsVendor (boolean IsVendor)
	{
		set_Value (COLUMNNAME_IsVendor, Boolean.valueOf(IsVendor));
	}

	/** Get Vendor.
		@return Indicates if this Business Partner is a Vendor
	  */
	@Override
	public boolean isVendor () 
	{
		Object oo = get_Value(COLUMNNAME_IsVendor);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set JobName.
		@param JobName JobName	  */
	@Override
	public void setJobName (java.lang.String JobName)
	{
		set_Value (COLUMNNAME_JobName, JobName);
	}

	/** Get JobName.
		@return JobName	  */
	@Override
	public java.lang.String getJobName () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_JobName);
	}

	/** Set Nachname.
		@param Lastname Nachname	  */
	@Override
	public void setLastname (java.lang.String Lastname)
	{
		set_Value (COLUMNNAME_Lastname, Lastname);
	}

	/** Get Nachname.
		@return Nachname	  */
	@Override
	public java.lang.String getLastname () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Lastname);
	}

	/** Set Lead Time Offset.
		@param LeadTimeOffset 
		Optional Lead Time offest before starting production
	  */
	@Override
	public void setLeadTimeOffset (int LeadTimeOffset)
	{
		set_Value (COLUMNNAME_LeadTimeOffset, Integer.valueOf(LeadTimeOffset));
	}

	/** Get Lead Time Offset.
		@return Optional Lead Time offest before starting production
	  */
	@Override
	public int getLeadTimeOffset () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_LeadTimeOffset);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_M_PricingSystem getM_PricingSystem() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_PricingSystem_ID, org.compiere.model.I_M_PricingSystem.class);
	}

	@Override
	public void setM_PricingSystem(org.compiere.model.I_M_PricingSystem M_PricingSystem)
	{
		set_ValueFromPO(COLUMNNAME_M_PricingSystem_ID, org.compiere.model.I_M_PricingSystem.class, M_PricingSystem);
	}

	/** Set Preissystem.
		@param M_PricingSystem_ID 
		Ein Preissystem enthält beliebig viele, Länder-abhängige Preislisten.
	  */
	@Override
	public void setM_PricingSystem_ID (int M_PricingSystem_ID)
	{
		if (M_PricingSystem_ID < 1) 
			set_Value (COLUMNNAME_M_PricingSystem_ID, null);
		else 
			set_Value (COLUMNNAME_M_PricingSystem_ID, Integer.valueOf(M_PricingSystem_ID));
	}

	/** Get Preissystem.
		@return Ein Preissystem enthält beliebig viele, Länder-abhängige Preislisten.
	  */
	@Override
	public int getM_PricingSystem_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_PricingSystem_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_M_Shipper getM_Shipper() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_Shipper_ID, org.compiere.model.I_M_Shipper.class);
	}

	@Override
	public void setM_Shipper(org.compiere.model.I_M_Shipper M_Shipper)
	{
		set_ValueFromPO(COLUMNNAME_M_Shipper_ID, org.compiere.model.I_M_Shipper.class, M_Shipper);
	}

	/** Set Lieferweg.
		@param M_Shipper_ID 
		Methode oder Art der Warenlieferung
	  */
	@Override
	public void setM_Shipper_ID (int M_Shipper_ID)
	{
		if (M_Shipper_ID < 1) 
			set_Value (COLUMNNAME_M_Shipper_ID, null);
		else 
			set_Value (COLUMNNAME_M_Shipper_ID, Integer.valueOf(M_Shipper_ID));
	}

	/** Get Lieferweg.
		@return Methode oder Art der Warenlieferung
	  */
	@Override
	public int getM_Shipper_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Shipper_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Memo_Delivery.
		@param Memo_Delivery 
		Memo Lieferung
	  */
	@Override
	public void setMemo_Delivery (java.lang.String Memo_Delivery)
	{
		set_Value (COLUMNNAME_Memo_Delivery, Memo_Delivery);
	}

	/** Get Memo_Delivery.
		@return Memo Lieferung
	  */
	@Override
	public java.lang.String getMemo_Delivery () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Memo_Delivery);
	}

	/** Set Memo_Invoicing.
		@param Memo_Invoicing 
		Memo Abrechnung
	  */
	@Override
	public void setMemo_Invoicing (java.lang.String Memo_Invoicing)
	{
		set_Value (COLUMNNAME_Memo_Invoicing, Memo_Invoicing);
	}

	/** Get Memo_Invoicing.
		@return Memo Abrechnung
	  */
	@Override
	public java.lang.String getMemo_Invoicing () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Memo_Invoicing);
	}

	/** Set NAICS/SIC.
		@param NAICS 
		Standard Industry Code or its successor NAIC - http://www.osha.gov/oshstats/sicser.html
	  */
	@Override
	public void setNAICS (java.lang.String NAICS)
	{
		set_Value (COLUMNNAME_NAICS, NAICS);
	}

	/** Get NAICS/SIC.
		@return Standard Industry Code or its successor NAIC - http://www.osha.gov/oshstats/sicser.html
	  */
	@Override
	public java.lang.String getNAICS () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_NAICS);
	}

	/** Set Name.
		@param Name Name	  */
	@Override
	public void setName (java.lang.String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	/** Get Name.
		@return Name	  */
	@Override
	public java.lang.String getName () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Name);
	}

	/** Set Name Zusatz.
		@param Name2 
		Zusätzliche Bezeichnung
	  */
	@Override
	public void setName2 (java.lang.String Name2)
	{
		set_Value (COLUMNNAME_Name2, Name2);
	}

	/** Get Name Zusatz.
		@return Zusätzliche Bezeichnung
	  */
	@Override
	public java.lang.String getName2 () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Name2);
	}

	/** Set Name3.
		@param Name3 
		Zusätzliche Bezeichnung
	  */
	@Override
	public void setName3 (java.lang.String Name3)
	{
		set_Value (COLUMNNAME_Name3, Name3);
	}

	/** Get Name3.
		@return Zusätzliche Bezeichnung
	  */
	@Override
	public java.lang.String getName3 () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Name3);
	}

	/** Set Organisations-Schlüssel.
		@param OrgValue 
		Suchschlüssel der Organisation
	  */
	@Override
	public void setOrgValue (java.lang.String OrgValue)
	{
		set_Value (COLUMNNAME_OrgValue, OrgValue);
	}

	/** Get Organisations-Schlüssel.
		@return Suchschlüssel der Organisation
	  */
	@Override
	public java.lang.String getOrgValue () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_OrgValue);
	}

	/** Set Kennwort.
		@param Password Kennwort	  */
	@Override
	public void setPassword (java.lang.String Password)
	{
		set_Value (COLUMNNAME_Password, Password);
	}

	/** Get Kennwort.
		@return Kennwort	  */
	@Override
	public java.lang.String getPassword () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Password);
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
	/** Set Zahlungsweise.
		@param PaymentRule 
		Wie die Rechnung bezahlt wird
	  */
	@Override
	public void setPaymentRule (java.lang.String PaymentRule)
	{

		set_Value (COLUMNNAME_PaymentRule, PaymentRule);
	}

	/** Get Zahlungsweise.
		@return Wie die Rechnung bezahlt wird
	  */
	@Override
	public java.lang.String getPaymentRule () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_PaymentRule);
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
	/** Set Zahlungsweise.
		@param PaymentRulePO 
		Möglichkeiten der Bezahlung einer Bestellung
	  */
	@Override
	public void setPaymentRulePO (java.lang.String PaymentRulePO)
	{

		set_Value (COLUMNNAME_PaymentRulePO, PaymentRulePO);
	}

	/** Get Zahlungsweise.
		@return Möglichkeiten der Bezahlung einer Bestellung
	  */
	@Override
	public java.lang.String getPaymentRulePO () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_PaymentRulePO);
	}

	/** Set Zahlungskondition.
		@param PaymentTerm 
		Zahlungskondition
	  */
	@Override
	public void setPaymentTerm (java.lang.String PaymentTerm)
	{
		set_Value (COLUMNNAME_PaymentTerm, PaymentTerm);
	}

	/** Get Zahlungskondition.
		@return Zahlungskondition
	  */
	@Override
	public java.lang.String getPaymentTerm () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_PaymentTerm);
	}

	/** Set Telefon.
		@param Phone 
		Beschreibt eine Telefon Nummer
	  */
	@Override
	public void setPhone (java.lang.String Phone)
	{
		set_Value (COLUMNNAME_Phone, Phone);
	}

	/** Get Telefon.
		@return Beschreibt eine Telefon Nummer
	  */
	@Override
	public java.lang.String getPhone () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Phone);
	}

	/** Set Mobil.
		@param Phone2 
		Alternative Mobile Telefonnummer
	  */
	@Override
	public void setPhone2 (java.lang.String Phone2)
	{
		set_Value (COLUMNNAME_Phone2, Phone2);
	}

	/** Get Mobil.
		@return Alternative Mobile Telefonnummer
	  */
	@Override
	public java.lang.String getPhone2 () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Phone2);
	}

	@Override
	public org.compiere.model.I_C_PaymentTerm getPO_PaymentTerm() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_PO_PaymentTerm_ID, org.compiere.model.I_C_PaymentTerm.class);
	}

	@Override
	public void setPO_PaymentTerm(org.compiere.model.I_C_PaymentTerm PO_PaymentTerm)
	{
		set_ValueFromPO(COLUMNNAME_PO_PaymentTerm_ID, org.compiere.model.I_C_PaymentTerm.class, PO_PaymentTerm);
	}

	/** Set Zahlungskondition.
		@param PO_PaymentTerm_ID 
		Zahlungskondition für die Bestellung
	  */
	@Override
	public void setPO_PaymentTerm_ID (int PO_PaymentTerm_ID)
	{
		if (PO_PaymentTerm_ID < 1) 
			set_Value (COLUMNNAME_PO_PaymentTerm_ID, null);
		else 
			set_Value (COLUMNNAME_PO_PaymentTerm_ID, Integer.valueOf(PO_PaymentTerm_ID));
	}

	/** Get Zahlungskondition.
		@return Zahlungskondition für die Bestellung
	  */
	@Override
	public int getPO_PaymentTerm_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PO_PaymentTerm_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_M_PricingSystem getPO_PricingSystem() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_PO_PricingSystem_ID, org.compiere.model.I_M_PricingSystem.class);
	}

	@Override
	public void setPO_PricingSystem(org.compiere.model.I_M_PricingSystem PO_PricingSystem)
	{
		set_ValueFromPO(COLUMNNAME_PO_PricingSystem_ID, org.compiere.model.I_M_PricingSystem.class, PO_PricingSystem);
	}

	/** Set Einkaufspreissystem.
		@param PO_PricingSystem_ID Einkaufspreissystem	  */
	@Override
	public void setPO_PricingSystem_ID (int PO_PricingSystem_ID)
	{
		if (PO_PricingSystem_ID < 1) 
			set_Value (COLUMNNAME_PO_PricingSystem_ID, null);
		else 
			set_Value (COLUMNNAME_PO_PricingSystem_ID, Integer.valueOf(PO_PricingSystem_ID));
	}

	/** Get Einkaufspreissystem.
		@return Einkaufspreissystem	  */
	@Override
	public int getPO_PricingSystem_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PO_PricingSystem_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set PO_PricingSystem_Value.
		@param PO_PricingSystem_Value PO_PricingSystem_Value	  */
	@Override
	public void setPO_PricingSystem_Value (java.lang.String PO_PricingSystem_Value)
	{
		set_Value (COLUMNNAME_PO_PricingSystem_Value, PO_PricingSystem_Value);
	}

	/** Get PO_PricingSystem_Value.
		@return PO_PricingSystem_Value	  */
	@Override
	public java.lang.String getPO_PricingSystem_Value () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_PO_PricingSystem_Value);
	}

	/** Set Postfach.
		@param POBox Postfach	  */
	@Override
	public void setPOBox (java.lang.String POBox)
	{
		set_Value (COLUMNNAME_POBox, POBox);
	}

	/** Get Postfach.
		@return Postfach	  */
	@Override
	public java.lang.String getPOBox () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_POBox);
	}

	/** Set PLZ.
		@param Postal 
		Postal code
	  */
	@Override
	public void setPostal (java.lang.String Postal)
	{
		set_Value (COLUMNNAME_Postal, Postal);
	}

	/** Get PLZ.
		@return Postal code
	  */
	@Override
	public java.lang.String getPostal () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Postal);
	}

	/** Set -.
		@param Postal_Add 
		Additional ZIP or Postal code
	  */
	@Override
	public void setPostal_Add (java.lang.String Postal_Add)
	{
		set_Value (COLUMNNAME_Postal_Add, Postal_Add);
	}

	/** Get -.
		@return Additional ZIP or Postal code
	  */
	@Override
	public java.lang.String getPostal_Add () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Postal_Add);
	}

	/** Set PricingSystem_Value.
		@param PricingSystem_Value PricingSystem_Value	  */
	@Override
	public void setPricingSystem_Value (java.lang.String PricingSystem_Value)
	{
		set_Value (COLUMNNAME_PricingSystem_Value, PricingSystem_Value);
	}

	/** Get PricingSystem_Value.
		@return PricingSystem_Value	  */
	@Override
	public java.lang.String getPricingSystem_Value () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_PricingSystem_Value);
	}

	/** Set PrintFormat_Name.
		@param PrintFormat_Name PrintFormat_Name	  */
	@Override
	public void setPrintFormat_Name (java.lang.String PrintFormat_Name)
	{
		set_Value (COLUMNNAME_PrintFormat_Name, PrintFormat_Name);
	}

	/** Get PrintFormat_Name.
		@return PrintFormat_Name	  */
	@Override
	public java.lang.String getPrintFormat_Name () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_PrintFormat_Name);
	}

	/** Set Verarbeitet.
		@param Processed 
		Checkbox sagt aus, ob der Beleg verarbeitet wurde. 
	  */
	@Override
	public void setProcessed (boolean Processed)
	{
		set_Value (COLUMNNAME_Processed, Boolean.valueOf(Processed));
	}

	/** Get Verarbeitet.
		@return Checkbox sagt aus, ob der Beleg verarbeitet wurde. 
	  */
	@Override
	public boolean isProcessed () 
	{
		Object oo = get_Value(COLUMNNAME_Processed);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Verarbeiten.
		@param Processing Verarbeiten	  */
	@Override
	public void setProcessing (boolean Processing)
	{
		set_Value (COLUMNNAME_Processing, Boolean.valueOf(Processing));
	}

	/** Get Verarbeiten.
		@return Verarbeiten	  */
	@Override
	public boolean isProcessing () 
	{
		Object oo = get_Value(COLUMNNAME_Processing);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Qualification .
		@param Qualification Qualification 	  */
	@Override
	public void setQualification (java.lang.String Qualification)
	{
		set_Value (COLUMNNAME_Qualification, Qualification);
	}

	/** Get Qualification .
		@return Qualification 	  */
	@Override
	public java.lang.String getQualification () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Qualification);
	}

	@Override
	public org.compiere.model.I_R_InterestArea getR_InterestArea() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_R_InterestArea_ID, org.compiere.model.I_R_InterestArea.class);
	}

	@Override
	public void setR_InterestArea(org.compiere.model.I_R_InterestArea R_InterestArea)
	{
		set_ValueFromPO(COLUMNNAME_R_InterestArea_ID, org.compiere.model.I_R_InterestArea.class, R_InterestArea);
	}

	/** Set Interessengebiet.
		@param R_InterestArea_ID 
		Interest Area or Topic
	  */
	@Override
	public void setR_InterestArea_ID (int R_InterestArea_ID)
	{
		if (R_InterestArea_ID < 1) 
			set_Value (COLUMNNAME_R_InterestArea_ID, null);
		else 
			set_Value (COLUMNNAME_R_InterestArea_ID, Integer.valueOf(R_InterestArea_ID));
	}

	/** Get Interessengebiet.
		@return Interest Area or Topic
	  */
	@Override
	public int getR_InterestArea_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_R_InterestArea_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Region.
		@param RegionName 
		Name of the Region
	  */
	@Override
	public void setRegionName (java.lang.String RegionName)
	{
		set_Value (COLUMNNAME_RegionName, RegionName);
	}

	/** Get Region.
		@return Name of the Region
	  */
	@Override
	public java.lang.String getRegionName () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_RegionName);
	}

	/** Set Statistik Gruppe.
		@param Salesgroup Statistik Gruppe	  */
	@Override
	public void setSalesgroup (java.lang.String Salesgroup)
	{
		set_Value (COLUMNNAME_Salesgroup, Salesgroup);
	}

	/** Get Statistik Gruppe.
		@return Statistik Gruppe	  */
	@Override
	public java.lang.String getSalesgroup () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Salesgroup);
	}

	/** Set Mindesthaltbarkeit Tage.
		@param ShelfLifeMinDays 
		Mindesthaltbarkeit in Tagen, bezogen auf das Mindesthaltbarkeitsdatum einer Produktinstanz
	  */
	@Override
	public void setShelfLifeMinDays (int ShelfLifeMinDays)
	{
		set_Value (COLUMNNAME_ShelfLifeMinDays, Integer.valueOf(ShelfLifeMinDays));
	}

	/** Get Mindesthaltbarkeit Tage.
		@return Mindesthaltbarkeit in Tagen, bezogen auf das Mindesthaltbarkeitsdatum einer Produktinstanz
	  */
	@Override
	public int getShelfLifeMinDays () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ShelfLifeMinDays);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Shipper name.
		@param ShipperName Shipper name	  */
	@Override
	public void setShipperName (java.lang.String ShipperName)
	{
		set_Value (COLUMNNAME_ShipperName, ShipperName);
	}

	/** Get Shipper name.
		@return Shipper name	  */
	@Override
	public java.lang.String getShipperName () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ShipperName);
	}

	/** Set Short Description.
		@param ShortDescription Short Description	  */
	@Override
	public void setShortDescription (java.lang.String ShortDescription)
	{
		set_Value (COLUMNNAME_ShortDescription, ShortDescription);
	}

	/** Get Short Description.
		@return Short Description	  */
	@Override
	public java.lang.String getShortDescription () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ShortDescription);
	}

	/** Set Swift code.
		@param SwiftCode 
		Swift Code or BIC
	  */
	@Override
	public void setSwiftCode (java.lang.String SwiftCode)
	{
		set_Value (COLUMNNAME_SwiftCode, SwiftCode);
	}

	/** Get Swift code.
		@return Swift Code or BIC
	  */
	@Override
	public java.lang.String getSwiftCode () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_SwiftCode);
	}

	/** Set Steuer-ID.
		@param TaxID 
		Tax Identification
	  */
	@Override
	public void setTaxID (java.lang.String TaxID)
	{
		set_Value (COLUMNNAME_TaxID, TaxID);
	}

	/** Get Steuer-ID.
		@return Tax Identification
	  */
	@Override
	public java.lang.String getTaxID () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_TaxID);
	}

	/** Set Titel.
		@param Title 
		Name this entity is referred to as
	  */
	@Override
	public void setTitle (java.lang.String Title)
	{
		set_Value (COLUMNNAME_Title, Title);
	}

	/** Get Titel.
		@return Name this entity is referred to as
	  */
	@Override
	public java.lang.String getTitle () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Title);
	}

	/** Set URL.
		@param URL 
		Full URL address - e.g. http://www.adempiere.org
	  */
	@Override
	public void setURL (java.lang.String URL)
	{
		set_Value (COLUMNNAME_URL, URL);
	}

	/** Get URL.
		@return Full URL address - e.g. http://www.adempiere.org
	  */
	@Override
	public java.lang.String getURL () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_URL);
	}

	/** Set Lieferanten Kategorie.
		@param VendorCategory 
		Lieferanten Kategorie
	  */
	@Override
	public void setVendorCategory (java.lang.String VendorCategory)
	{
		set_Value (COLUMNNAME_VendorCategory, VendorCategory);
	}

	/** Get Lieferanten Kategorie.
		@return Lieferanten Kategorie
	  */
	@Override
	public java.lang.String getVendorCategory () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_VendorCategory);
	}
}