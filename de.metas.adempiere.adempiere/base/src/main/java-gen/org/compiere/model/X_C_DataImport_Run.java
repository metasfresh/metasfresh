/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_DataImport_Run
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_DataImport_Run extends org.compiere.model.PO implements I_C_DataImport_Run, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -1174657128L;

    /** Standard Constructor */
    public X_C_DataImport_Run (Properties ctx, int C_DataImport_Run_ID, String trxName)
    {
      super (ctx, C_DataImport_Run_ID, trxName);
      /** if (C_DataImport_Run_ID == 0)
        {
			setAD_User_ID (0);
			setC_DataImport_Run_ID (0);
			setIsDocComplete (false); // N
        } */
    }

    /** Load Constructor */
    public X_C_DataImport_Run (Properties ctx, ResultSet rs, String trxName)
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
	public org.compiere.model.I_AD_ImpFormat getAD_ImpFormat()
	{
		return get_ValueAsPO(COLUMNNAME_AD_ImpFormat_ID, org.compiere.model.I_AD_ImpFormat.class);
	}

	@Override
	public void setAD_ImpFormat(org.compiere.model.I_AD_ImpFormat AD_ImpFormat)
	{
		set_ValueFromPO(COLUMNNAME_AD_ImpFormat_ID, org.compiere.model.I_AD_ImpFormat.class, AD_ImpFormat);
	}

	/** Set Import-Format.
		@param AD_ImpFormat_ID Import-Format	  */
	@Override
	public void setAD_ImpFormat_ID (int AD_ImpFormat_ID)
	{
		if (AD_ImpFormat_ID < 1) 
			set_Value (COLUMNNAME_AD_ImpFormat_ID, null);
		else 
			set_Value (COLUMNNAME_AD_ImpFormat_ID, Integer.valueOf(AD_ImpFormat_ID));
	}

	/** Get Import-Format.
		@return Import-Format	  */
	@Override
	public int getAD_ImpFormat_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_ImpFormat_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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
	public org.compiere.model.I_C_DataImport getC_DataImport()
	{
		return get_ValueAsPO(COLUMNNAME_C_DataImport_ID, org.compiere.model.I_C_DataImport.class);
	}

	@Override
	public void setC_DataImport(org.compiere.model.I_C_DataImport C_DataImport)
	{
		set_ValueFromPO(COLUMNNAME_C_DataImport_ID, org.compiere.model.I_C_DataImport.class, C_DataImport);
	}

	/** Set Daten Import.
		@param C_DataImport_ID Daten Import	  */
	@Override
	public void setC_DataImport_ID (int C_DataImport_ID)
	{
		if (C_DataImport_ID < 1) 
			set_Value (COLUMNNAME_C_DataImport_ID, null);
		else 
			set_Value (COLUMNNAME_C_DataImport_ID, Integer.valueOf(C_DataImport_ID));
	}

	/** Get Daten Import.
		@return Daten Import	  */
	@Override
	public int getC_DataImport_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_DataImport_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Data Import Run.
		@param C_DataImport_Run_ID Data Import Run	  */
	@Override
	public void setC_DataImport_Run_ID (int C_DataImport_Run_ID)
	{
		if (C_DataImport_Run_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_DataImport_Run_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_DataImport_Run_ID, Integer.valueOf(C_DataImport_Run_ID));
	}

	/** Get Data Import Run.
		@return Data Import Run	  */
	@Override
	public int getC_DataImport_Run_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_DataImport_Run_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Beleg fertig stellen.
		@param IsDocComplete 
		Legt fest, ob ggf erstellte Belege (z.B. Produktionsaufträge) auch direkt automatisch fertig gestellt werden sollen.
	  */
	@Override
	public void setIsDocComplete (boolean IsDocComplete)
	{
		set_Value (COLUMNNAME_IsDocComplete, Boolean.valueOf(IsDocComplete));
	}

	/** Get Beleg fertig stellen.
		@return Legt fest, ob ggf erstellte Belege (z.B. Produktionsaufträge) auch direkt automatisch fertig gestellt werden sollen.
	  */
	@Override
	public boolean isDocComplete () 
	{
		Object oo = get_Value(COLUMNNAME_IsDocComplete);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}
}