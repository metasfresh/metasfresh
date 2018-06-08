/** Generated Model - DO NOT CHANGE */
package de.metas.tax.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_VAT_SmallBusiness
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_VAT_SmallBusiness extends org.compiere.model.PO implements I_C_VAT_SmallBusiness, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -1161208034L;

    /** Standard Constructor */
    public X_C_VAT_SmallBusiness (Properties ctx, int C_VAT_SmallBusiness_ID, String trxName)
    {
      super (ctx, C_VAT_SmallBusiness_ID, trxName);
      /** if (C_VAT_SmallBusiness_ID == 0)
        {
			setC_BPartner_ID (0);
			setC_VAT_SmallBusiness_ID (0);
			setValidFrom (new Timestamp( System.currentTimeMillis() ));
			setValidTo (new Timestamp( System.currentTimeMillis() ));
        } */
    }

    /** Load Constructor */
    public X_C_VAT_SmallBusiness (Properties ctx, ResultSet rs, String trxName)
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
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
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

	/** Set Kleinunternehmer-Steuerbefreiung .
		@param C_VAT_SmallBusiness_ID Kleinunternehmer-Steuerbefreiung 	  */
	@Override
	public void setC_VAT_SmallBusiness_ID (int C_VAT_SmallBusiness_ID)
	{
		if (C_VAT_SmallBusiness_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_VAT_SmallBusiness_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_VAT_SmallBusiness_ID, Integer.valueOf(C_VAT_SmallBusiness_ID));
	}

	/** Get Kleinunternehmer-Steuerbefreiung .
		@return Kleinunternehmer-Steuerbefreiung 	  */
	@Override
	public int getC_VAT_SmallBusiness_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_VAT_SmallBusiness_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Notiz.
		@param Note 
		Optional weitere Information für ein Dokument
	  */
	@Override
	public void setNote (java.lang.String Note)
	{
		set_Value (COLUMNNAME_Note, Note);
	}

	/** Get Notiz.
		@return Optional weitere Information für ein Dokument
	  */
	@Override
	public java.lang.String getNote () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Note);
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

	/** Set Gültig bis.
		@param ValidTo 
		Gültig bis inklusiv (letzter Tag)
	  */
	@Override
	public void setValidTo (java.sql.Timestamp ValidTo)
	{
		set_Value (COLUMNNAME_ValidTo, ValidTo);
	}

	/** Get Gültig bis.
		@return Gültig bis inklusiv (letzter Tag)
	  */
	@Override
	public java.sql.Timestamp getValidTo () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_ValidTo);
	}

	/** Set Umsatzsteuer-ID.
		@param VATaxID Umsatzsteuer-ID	  */
	@Override
	public void setVATaxID (java.lang.String VATaxID)
	{
		set_Value (COLUMNNAME_VATaxID, VATaxID);
	}

	/** Get Umsatzsteuer-ID.
		@return Umsatzsteuer-ID	  */
	@Override
	public java.lang.String getVATaxID () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_VATaxID);
	}
}