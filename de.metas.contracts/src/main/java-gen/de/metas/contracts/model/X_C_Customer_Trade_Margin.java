/** Generated Model - DO NOT CHANGE */
package de.metas.contracts.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_Customer_Trade_Margin
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_Customer_Trade_Margin extends org.compiere.model.PO implements I_C_Customer_Trade_Margin, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -716220863L;

    /** Standard Constructor */
    public X_C_Customer_Trade_Margin (Properties ctx, int C_Customer_Trade_Margin_ID, String trxName)
    {
      super (ctx, C_Customer_Trade_Margin_ID, trxName);
      /** if (C_Customer_Trade_Margin_ID == 0)
        {
			setC_BPartner_SalesRep_ID (0);
			setC_Customer_Trade_Margin_ID (0);
			setMargin_Percent (BigDecimal.ZERO);
			setValidFrom (new Timestamp( System.currentTimeMillis() ));
        } */
    }

    /** Load Constructor */
    public X_C_Customer_Trade_Margin (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Kunde.
		@param C_BPartner_Customer_ID Kunde	  */
	@Override
	public void setC_BPartner_Customer_ID (int C_BPartner_Customer_ID)
	{
		if (C_BPartner_Customer_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_Customer_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_Customer_ID, Integer.valueOf(C_BPartner_Customer_ID));
	}

	/** Get Kunde.
		@return Kunde	  */
	@Override
	public int getC_BPartner_Customer_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_Customer_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Zugeordneter Vertriebspartner.
		@param C_BPartner_SalesRep_ID Zugeordneter Vertriebspartner	  */
	@Override
	public void setC_BPartner_SalesRep_ID (int C_BPartner_SalesRep_ID)
	{
		if (C_BPartner_SalesRep_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_SalesRep_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_SalesRep_ID, Integer.valueOf(C_BPartner_SalesRep_ID));
	}

	/** Get Zugeordneter Vertriebspartner.
		@return Zugeordneter Vertriebspartner	  */
	@Override
	public int getC_BPartner_SalesRep_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_SalesRep_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set C_Customer_Trade_Margin.
		@param C_Customer_Trade_Margin_ID C_Customer_Trade_Margin	  */
	@Override
	public void setC_Customer_Trade_Margin_ID (int C_Customer_Trade_Margin_ID)
	{
		if (C_Customer_Trade_Margin_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Customer_Trade_Margin_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Customer_Trade_Margin_ID, Integer.valueOf(C_Customer_Trade_Margin_ID));
	}

	/** Get C_Customer_Trade_Margin.
		@return C_Customer_Trade_Margin	  */
	@Override
	public int getC_Customer_Trade_Margin_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Customer_Trade_Margin_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Margin Percent.
		@param Margin_Percent Margin Percent	  */
	@Override
	public void setMargin_Percent (java.math.BigDecimal Margin_Percent)
	{
		set_Value (COLUMNNAME_Margin_Percent, Margin_Percent);
	}

	/** Get Margin Percent.
		@return Margin Percent	  */
	@Override
	public java.math.BigDecimal getMargin_Percent () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Margin_Percent);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Gültig ab.
		@param ValidFrom 
		Gültig ab inklusiv (erster Tag)
	  */
	@Override
	public void setValidFrom (java.sql.Timestamp ValidFrom)
	{
		set_Value (COLUMNNAME_ValidFrom, ValidFrom);
	}

	/** Get Gültig ab.
		@return Gültig ab inklusiv (erster Tag)
	  */
	@Override
	public java.sql.Timestamp getValidFrom () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_ValidFrom);
	}
}