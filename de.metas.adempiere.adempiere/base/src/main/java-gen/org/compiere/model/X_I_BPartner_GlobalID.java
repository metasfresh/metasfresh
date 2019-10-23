/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for I_BPartner_GlobalID
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_I_BPartner_GlobalID extends org.compiere.model.PO implements I_I_BPartner_GlobalID, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 779311387L;

    /** Standard Constructor */
    public X_I_BPartner_GlobalID (Properties ctx, int I_BPartner_GlobalID_ID, String trxName)
    {
      super (ctx, I_BPartner_GlobalID_ID, trxName);
      /** if (I_BPartner_GlobalID_ID == 0)
        {
			setI_BPartner_GlobalID_ID (0);
			setI_IsImported (false); // N
        } */
    }

    /** Load Constructor */
    public X_I_BPartner_GlobalID (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Global ID.
		@param GlobalId Global ID	  */
	@Override
	public void setGlobalId (java.lang.String GlobalId)
	{
		set_Value (COLUMNNAME_GlobalId, GlobalId);
	}

	/** Get Global ID.
		@return Global ID	  */
	@Override
	public java.lang.String getGlobalId () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_GlobalId);
	}

	/** Set Import BPartnerr Global ID.
		@param I_BPartner_GlobalID_ID Import BPartnerr Global ID	  */
	@Override
	public void setI_BPartner_GlobalID_ID (int I_BPartner_GlobalID_ID)
	{
		if (I_BPartner_GlobalID_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_I_BPartner_GlobalID_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_I_BPartner_GlobalID_ID, Integer.valueOf(I_BPartner_GlobalID_ID));
	}

	/** Get Import BPartnerr Global ID.
		@return Import BPartnerr Global ID	  */
	@Override
	public int getI_BPartner_GlobalID_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_I_BPartner_GlobalID_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Import-Fehlermeldung.
		@param I_ErrorMsg 
		Meldungen, die durch den Importprozess generiert wurden
	  */
	@Override
	public void setI_ErrorMsg (java.lang.String I_ErrorMsg)
	{
		set_Value (COLUMNNAME_I_ErrorMsg, I_ErrorMsg);
	}

	/** Get Import-Fehlermeldung.
		@return Meldungen, die durch den Importprozess generiert wurden
	  */
	@Override
	public java.lang.String getI_ErrorMsg () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_I_ErrorMsg);
	}

	/** Set Importiert.
		@param I_IsImported 
		Ist dieser Import verarbeitet worden?
	  */
	@Override
	public void setI_IsImported (boolean I_IsImported)
	{
		set_Value (COLUMNNAME_I_IsImported, Boolean.valueOf(I_IsImported));
	}

	/** Get Importiert.
		@return Ist dieser Import verarbeitet worden?
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

	/** Set Process Now.
		@param Processing Process Now	  */
	@Override
	public void setProcessing (boolean Processing)
	{
		set_Value (COLUMNNAME_Processing, Boolean.valueOf(Processing));
	}

	/** Get Process Now.
		@return Process Now	  */
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

	/** Set URL3.
		@param URL3 
		Vollständige Web-Addresse, z.B. https://metasfresh.com/
	  */
	@Override
	public void setURL3 (java.lang.String URL3)
	{
		set_Value (COLUMNNAME_URL3, URL3);
	}

	/** Get URL3.
		@return Vollständige Web-Addresse, z.B. https://metasfresh.com/
	  */
	@Override
	public java.lang.String getURL3 () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_URL3);
	}
}