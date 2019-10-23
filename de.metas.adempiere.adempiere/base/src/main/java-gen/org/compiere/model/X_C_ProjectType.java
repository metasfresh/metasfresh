/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_ProjectType
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_ProjectType extends org.compiere.model.PO implements I_C_ProjectType, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -339227184L;

    /** Standard Constructor */
    public X_C_ProjectType (Properties ctx, int C_ProjectType_ID, String trxName)
    {
      super (ctx, C_ProjectType_ID, trxName);
      /** if (C_ProjectType_ID == 0)
        {
			setC_ProjectType_ID (0);
			setName (null);
			setProjectCategory (null); // N
        } */
    }

    /** Load Constructor */
    public X_C_ProjectType (Properties ctx, ResultSet rs, String trxName)
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
	public org.compiere.model.I_AD_Sequence getAD_Sequence_ProjectValue() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_Sequence_ProjectValue_ID, org.compiere.model.I_AD_Sequence.class);
	}

	@Override
	public void setAD_Sequence_ProjectValue(org.compiere.model.I_AD_Sequence AD_Sequence_ProjectValue)
	{
		set_ValueFromPO(COLUMNNAME_AD_Sequence_ProjectValue_ID, org.compiere.model.I_AD_Sequence.class, AD_Sequence_ProjectValue);
	}

	/** Set Projekt-Nummernfolge.
		@param AD_Sequence_ProjectValue_ID 
		Nummernfolge für Projekt-Suchschlüssel
	  */
	@Override
	public void setAD_Sequence_ProjectValue_ID (int AD_Sequence_ProjectValue_ID)
	{
		if (AD_Sequence_ProjectValue_ID < 1) 
			set_Value (COLUMNNAME_AD_Sequence_ProjectValue_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Sequence_ProjectValue_ID, Integer.valueOf(AD_Sequence_ProjectValue_ID));
	}

	/** Get Projekt-Nummernfolge.
		@return Nummernfolge für Projekt-Suchschlüssel
	  */
	@Override
	public int getAD_Sequence_ProjectValue_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Sequence_ProjectValue_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Projektart.
		@param C_ProjectType_ID 
		Type of the project
	  */
	@Override
	public void setC_ProjectType_ID (int C_ProjectType_ID)
	{
		if (C_ProjectType_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_ProjectType_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_ProjectType_ID, Integer.valueOf(C_ProjectType_ID));
	}

	/** Get Projektart.
		@return Type of the project
	  */
	@Override
	public int getC_ProjectType_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_ProjectType_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Kommentar/Hilfe.
		@param Help 
		Comment or Hint
	  */
	@Override
	public void setHelp (java.lang.String Help)
	{
		set_Value (COLUMNNAME_Help, Help);
	}

	/** Get Kommentar/Hilfe.
		@return Comment or Hint
	  */
	@Override
	public java.lang.String getHelp () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Help);
	}

	/** Set Name.
		@param Name 
		Alphanumeric identifier of the entity
	  */
	@Override
	public void setName (java.lang.String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	/** Get Name.
		@return Alphanumeric identifier of the entity
	  */
	@Override
	public java.lang.String getName () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Name);
	}

	/** 
	 * ProjectCategory AD_Reference_ID=288
	 * Reference name: C_ProjectType Category
	 */
	public static final int PROJECTCATEGORY_AD_Reference_ID=288;
	/** Allgemein = N */
	public static final String PROJECTCATEGORY_Allgemein = "N";
	/** Asset-Projekt = A */
	public static final String PROJECTCATEGORY_Asset_Projekt = "A";
	/** Arbeitsauftrag (Job) = W */
	public static final String PROJECTCATEGORY_ArbeitsauftragJob = "W";
	/** Service (Charge) Project = S */
	public static final String PROJECTCATEGORY_ServiceChargeProject = "S";
	/** Set Project Category.
		@param ProjectCategory 
		Project Category
	  */
	@Override
	public void setProjectCategory (java.lang.String ProjectCategory)
	{

		set_ValueNoCheck (COLUMNNAME_ProjectCategory, ProjectCategory);
	}

	/** Get Project Category.
		@return Project Category
	  */
	@Override
	public java.lang.String getProjectCategory () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ProjectCategory);
	}
}