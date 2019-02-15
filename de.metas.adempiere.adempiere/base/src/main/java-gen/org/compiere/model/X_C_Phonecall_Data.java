/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_Phonecall_Data
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_Phonecall_Data extends org.compiere.model.PO implements I_C_Phonecall_Data, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 385651007L;

    /** Standard Constructor */
    public X_C_Phonecall_Data (Properties ctx, int C_Phonecall_Data_ID, String trxName)
    {
      super (ctx, C_Phonecall_Data_ID, trxName);
      /** if (C_Phonecall_Data_ID == 0)
        {
			setAD_User_ID (0);
			setC_BPartner_ID (0);
			setC_BPartner_Location_ID (0);
			setC_Phonecall_Data_ID (0);
			setIsManualPhonecall (false); // N
			setProcessed (false); // N
        } */
    }

    /** Load Constructor */
    public X_C_Phonecall_Data (Properties ctx, ResultSet rs, String trxName)
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
		Bezeichnet einen Geschäftspartner
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
		@return Bezeichnet einen Geschäftspartner
	  */
	@Override
	public int getC_BPartner_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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
		Identifiziert die (Liefer-) Adresse des Geschäftspartners
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
		@return Identifiziert die (Liefer-) Adresse des Geschäftspartners
	  */
	@Override
	public int getC_BPartner_Location_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_Location_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Anruf.
		@param C_Phonecall_Data_ID Anruf	  */
	@Override
	public void setC_Phonecall_Data_ID (int C_Phonecall_Data_ID)
	{
		if (C_Phonecall_Data_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Phonecall_Data_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Phonecall_Data_ID, Integer.valueOf(C_Phonecall_Data_ID));
	}

	/** Get Anruf.
		@return Anruf	  */
	@Override
	public int getC_Phonecall_Data_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Phonecall_Data_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_Phonecall_Schema_Version getC_Phonecall_Schema_Version() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Phonecall_Schema_Version_ID, org.compiere.model.I_C_Phonecall_Schema_Version.class);
	}

	@Override
	public void setC_Phonecall_Schema_Version(org.compiere.model.I_C_Phonecall_Schema_Version C_Phonecall_Schema_Version)
	{
		set_ValueFromPO(COLUMNNAME_C_Phonecall_Schema_Version_ID, org.compiere.model.I_C_Phonecall_Schema_Version.class, C_Phonecall_Schema_Version);
	}

	/** Set Anruflistenversion.
		@param C_Phonecall_Schema_Version_ID Anruflistenversion	  */
	@Override
	public void setC_Phonecall_Schema_Version_ID (int C_Phonecall_Schema_Version_ID)
	{
		if (C_Phonecall_Schema_Version_ID < 1) 
			set_Value (COLUMNNAME_C_Phonecall_Schema_Version_ID, null);
		else 
			set_Value (COLUMNNAME_C_Phonecall_Schema_Version_ID, Integer.valueOf(C_Phonecall_Schema_Version_ID));
	}

	/** Get Anruflistenversion.
		@return Anruflistenversion	  */
	@Override
	public int getC_Phonecall_Schema_Version_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Phonecall_Schema_Version_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Manuell.
		@param IsManualPhonecall Manuell	  */
	@Override
	public void setIsManualPhonecall (boolean IsManualPhonecall)
	{
		set_Value (COLUMNNAME_IsManualPhonecall, Boolean.valueOf(IsManualPhonecall));
	}

	/** Get Manuell.
		@return Manuell	  */
	@Override
	public boolean isManualPhonecall () 
	{
		Object oo = get_Value(COLUMNNAME_IsManualPhonecall);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Anrufdatum.
		@param PhonecallDateTime Anrufdatum	  */
	@Override
	public void setPhonecallDateTime (java.sql.Timestamp PhonecallDateTime)
	{
		set_Value (COLUMNNAME_PhonecallDateTime, PhonecallDateTime);
	}

	/** Get Anrufdatum.
		@return Anrufdatum	  */
	@Override
	public java.sql.Timestamp getPhonecallDateTime () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_PhonecallDateTime);
	}

	/** Set Anrufzeit (inkl. Puffer).
		@param PhonecallDateTimeMax Anrufzeit (inkl. Puffer)	  */
	@Override
	public void setPhonecallDateTimeMax (java.sql.Timestamp PhonecallDateTimeMax)
	{
		set_Value (COLUMNNAME_PhonecallDateTimeMax, PhonecallDateTimeMax);
	}

	/** Get Anrufzeit (inkl. Puffer).
		@return Anrufzeit (inkl. Puffer)	  */
	@Override
	public java.sql.Timestamp getPhonecallDateTimeMax () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_PhonecallDateTimeMax);
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
}