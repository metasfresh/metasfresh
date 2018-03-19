/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_DataImport
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_DataImport extends org.compiere.model.PO implements I_C_DataImport, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -997830061L;

    /** Standard Constructor */
    public X_C_DataImport (Properties ctx, int C_DataImport_ID, String trxName)
    {
      super (ctx, C_DataImport_ID, trxName);
      /** if (C_DataImport_ID == 0)
        {
			setAD_ImpFormat_ID (0);
			setC_DataImport_ID (0);
        } */
    }

    /** Load Constructor */
    public X_C_DataImport (Properties ctx, ResultSet rs, String trxName)
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
	public org.compiere.model.I_AD_ImpFormat getAD_ImpFormat() throws RuntimeException
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
}