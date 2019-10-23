/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_Phonecall_Schema_Version_Line
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_Phonecall_Schema_Version_Line extends org.compiere.model.PO implements I_C_Phonecall_Schema_Version_Line, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -1418880665L;

    /** Standard Constructor */
    public X_C_Phonecall_Schema_Version_Line (Properties ctx, int C_Phonecall_Schema_Version_Line_ID, String trxName)
    {
      super (ctx, C_Phonecall_Schema_Version_Line_ID, trxName);
      /** if (C_Phonecall_Schema_Version_Line_ID == 0)
        {
			setC_BPartner_ID (0);
			setC_BPartner_Location_ID (0);
			setC_Phonecall_Schema_ID (0);
			setC_Phonecall_Schema_Version_ID (0);
			setC_Phonecall_Schema_Version_Line_ID (0);
			setPhonecallTimeMax (new Timestamp( System.currentTimeMillis() ));
			setPhonecallTimeMin (new Timestamp( System.currentTimeMillis() ));
			setSeqNo (0); // 0
        } */
    }

    /** Load Constructor */
    public X_C_Phonecall_Schema_Version_Line (Properties ctx, ResultSet rs, String trxName)
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
	public org.compiere.model.I_AD_User getC_BP_Contact() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_BP_Contact_ID, org.compiere.model.I_AD_User.class);
	}

	@Override
	public void setC_BP_Contact(org.compiere.model.I_AD_User C_BP_Contact)
	{
		set_ValueFromPO(COLUMNNAME_C_BP_Contact_ID, org.compiere.model.I_AD_User.class, C_BP_Contact);
	}

	/** Set Contact.
		@param C_BP_Contact_ID Contact	  */
	@Override
	public void setC_BP_Contact_ID (int C_BP_Contact_ID)
	{
		if (C_BP_Contact_ID < 1) 
			set_Value (COLUMNNAME_C_BP_Contact_ID, null);
		else 
			set_Value (COLUMNNAME_C_BP_Contact_ID, Integer.valueOf(C_BP_Contact_ID));
	}

	/** Get Contact.
		@return Contact	  */
	@Override
	public int getC_BP_Contact_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BP_Contact_ID);
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

	@Override
	public org.compiere.model.I_C_Phonecall_Schema getC_Phonecall_Schema() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Phonecall_Schema_ID, org.compiere.model.I_C_Phonecall_Schema.class);
	}

	@Override
	public void setC_Phonecall_Schema(org.compiere.model.I_C_Phonecall_Schema C_Phonecall_Schema)
	{
		set_ValueFromPO(COLUMNNAME_C_Phonecall_Schema_ID, org.compiere.model.I_C_Phonecall_Schema.class, C_Phonecall_Schema);
	}

	/** Set Anruf Planung.
		@param C_Phonecall_Schema_ID Anruf Planung	  */
	@Override
	public void setC_Phonecall_Schema_ID (int C_Phonecall_Schema_ID)
	{
		if (C_Phonecall_Schema_ID < 1) 
			set_Value (COLUMNNAME_C_Phonecall_Schema_ID, null);
		else 
			set_Value (COLUMNNAME_C_Phonecall_Schema_ID, Integer.valueOf(C_Phonecall_Schema_ID));
	}

	/** Get Anruf Planung.
		@return Anruf Planung	  */
	@Override
	public int getC_Phonecall_Schema_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Phonecall_Schema_ID);
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

	/** Set Anruf Planung Version.
		@param C_Phonecall_Schema_Version_ID Anruf Planung Version	  */
	@Override
	public void setC_Phonecall_Schema_Version_ID (int C_Phonecall_Schema_Version_ID)
	{
		if (C_Phonecall_Schema_Version_ID < 1) 
			set_Value (COLUMNNAME_C_Phonecall_Schema_Version_ID, null);
		else 
			set_Value (COLUMNNAME_C_Phonecall_Schema_Version_ID, Integer.valueOf(C_Phonecall_Schema_Version_ID));
	}

	/** Get Anruf Planung Version.
		@return Anruf Planung Version	  */
	@Override
	public int getC_Phonecall_Schema_Version_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Phonecall_Schema_Version_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Anruf Planung Version Position.
		@param C_Phonecall_Schema_Version_Line_ID Anruf Planung Version Position	  */
	@Override
	public void setC_Phonecall_Schema_Version_Line_ID (int C_Phonecall_Schema_Version_Line_ID)
	{
		if (C_Phonecall_Schema_Version_Line_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Phonecall_Schema_Version_Line_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Phonecall_Schema_Version_Line_ID, Integer.valueOf(C_Phonecall_Schema_Version_Line_ID));
	}

	/** Get Anruf Planung Version Position.
		@return Anruf Planung Version Position	  */
	@Override
	public int getC_Phonecall_Schema_Version_Line_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Phonecall_Schema_Version_Line_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set PhonecallTimeMax.
		@param PhonecallTimeMax PhonecallTimeMax	  */
	@Override
	public void setPhonecallTimeMax (java.sql.Timestamp PhonecallTimeMax)
	{
		set_Value (COLUMNNAME_PhonecallTimeMax, PhonecallTimeMax);
	}

	/** Get PhonecallTimeMax.
		@return PhonecallTimeMax	  */
	@Override
	public java.sql.Timestamp getPhonecallTimeMax () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_PhonecallTimeMax);
	}

	/** Set PhonecallTimeMin.
		@param PhonecallTimeMin PhonecallTimeMin	  */
	@Override
	public void setPhonecallTimeMin (java.sql.Timestamp PhonecallTimeMin)
	{
		set_Value (COLUMNNAME_PhonecallTimeMin, PhonecallTimeMin);
	}

	/** Get PhonecallTimeMin.
		@return PhonecallTimeMin	  */
	@Override
	public java.sql.Timestamp getPhonecallTimeMin () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_PhonecallTimeMin);
	}

	/** Set Reihenfolge.
		@param SeqNo 
		Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst
	  */
	@Override
	public void setSeqNo (int SeqNo)
	{
		set_Value (COLUMNNAME_SeqNo, Integer.valueOf(SeqNo));
	}

	/** Get Reihenfolge.
		@return Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst
	  */
	@Override
	public int getSeqNo () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SeqNo);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}