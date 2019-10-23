/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_Customer_Retention
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_Customer_Retention extends org.compiere.model.PO implements I_C_Customer_Retention, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 45718785L;

    /** Standard Constructor */
    public X_C_Customer_Retention (Properties ctx, int C_Customer_Retention_ID, String trxName)
    {
      super (ctx, C_Customer_Retention_ID, trxName);
      /** if (C_Customer_Retention_ID == 0)
        {
			setC_Customer_Retention_ID (0);
        } */
    }

    /** Load Constructor */
    public X_C_Customer_Retention (Properties ctx, ResultSet rs, String trxName)
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

	/** Set C_Customer_Retention_ID.
		@param C_Customer_Retention_ID C_Customer_Retention_ID	  */
	@Override
	public void setC_Customer_Retention_ID (int C_Customer_Retention_ID)
	{
		if (C_Customer_Retention_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Customer_Retention_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Customer_Retention_ID, Integer.valueOf(C_Customer_Retention_ID));
	}

	/** Get C_Customer_Retention_ID.
		@return C_Customer_Retention_ID	  */
	@Override
	public int getC_Customer_Retention_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Customer_Retention_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** 
	 * CustomerRetention AD_Reference_ID=540937
	 * Reference name: C_BPartner_TimeSpan_List
	 */
	public static final int CUSTOMERRETENTION_AD_Reference_ID=540937;
	/** Neukunde = N */
	public static final String CUSTOMERRETENTION_Neukunde = "N";
	/** Stammkunde = S */
	public static final String CUSTOMERRETENTION_Stammkunde = "S";
	/** Set Customer Retention.
		@param CustomerRetention Customer Retention	  */
	@Override
	public void setCustomerRetention (java.lang.String CustomerRetention)
	{

		set_Value (COLUMNNAME_CustomerRetention, CustomerRetention);
	}

	/** Get Customer Retention.
		@return Customer Retention	  */
	@Override
	public java.lang.String getCustomerRetention () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_CustomerRetention);
	}
}