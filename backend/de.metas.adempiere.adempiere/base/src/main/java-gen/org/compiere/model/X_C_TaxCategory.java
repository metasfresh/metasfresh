/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_TaxCategory
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_TaxCategory extends org.compiere.model.PO implements I_C_TaxCategory, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1985832056L;

    /** Standard Constructor */
    public X_C_TaxCategory (Properties ctx, int C_TaxCategory_ID, String trxName)
    {
      super (ctx, C_TaxCategory_ID, trxName);
      /** if (C_TaxCategory_ID == 0)
        {
			setC_TaxCategory_ID (0);
			setName (null);
        } */
    }

    /** Load Constructor */
    public X_C_TaxCategory (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Steuerkategorie.
		@param C_TaxCategory_ID 
		Tax Category
	  */
	@Override
	public void setC_TaxCategory_ID (int C_TaxCategory_ID)
	{
		if (C_TaxCategory_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_TaxCategory_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_TaxCategory_ID, Integer.valueOf(C_TaxCategory_ID));
	}

	/** Get Steuerkategorie.
		@return Tax Category
	  */
	@Override
	public int getC_TaxCategory_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_TaxCategory_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Statistische Warennummer.
		@param CommodityCode 
		Commodity code used for tax calculation
	  */
	@Override
	public void setCommodityCode (java.lang.String CommodityCode)
	{
		set_Value (COLUMNNAME_CommodityCode, CommodityCode);
	}

	/** Get Statistische Warennummer.
		@return Commodity code used for tax calculation
	  */
	@Override
	public java.lang.String getCommodityCode () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_CommodityCode);
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
	 * VATType AD_Reference_ID=540842
	 * Reference name: VATType
	 */
	public static final int VATTYPE_AD_Reference_ID=540842;
	/** RegularVAT = N */
	public static final String VATTYPE_RegularVAT = "N";
	/** ReducedVAT = R */
	public static final String VATTYPE_ReducedVAT = "R";
	/** TaxExempt = E */
	public static final String VATTYPE_TaxExempt = "E";
	/** Set MwSt-Typ.
		@param VATType MwSt-Typ	  */
	@Override
	public void setVATType (java.lang.String VATType)
	{

		set_Value (COLUMNNAME_VATType, VATType);
	}

	/** Get MwSt-Typ.
		@return MwSt-Typ	  */
	@Override
	public java.lang.String getVATType () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_VATType);
	}
}