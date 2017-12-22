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
	private static final long serialVersionUID = 464401347L;

    /** Standard Constructor */
    public X_C_TaxCategory (Properties ctx, int C_TaxCategory_ID, String trxName)
    {
      super (ctx, C_TaxCategory_ID, trxName);
      /** if (C_TaxCategory_ID == 0)
        {
			setC_TaxCategory_ID (0);
			setIsDefault (false);
			setIsReduced (false); // N
			setIsWithout (false); // N
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

	/** Set Standard.
		@param IsDefault 
		Default value
	  */
	@Override
	public void setIsDefault (boolean IsDefault)
	{
		set_Value (COLUMNNAME_IsDefault, Boolean.valueOf(IsDefault));
	}

	/** Get Standard.
		@return Default value
	  */
	@Override
	public boolean isDefault () 
	{
		Object oo = get_Value(COLUMNNAME_IsDefault);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Ermäßigt.
		@param IsReduced Ermäßigt	  */
	@Override
	public void setIsReduced (boolean IsReduced)
	{
		set_Value (COLUMNNAME_IsReduced, Boolean.valueOf(IsReduced));
	}

	/** Get Ermäßigt.
		@return Ermäßigt	  */
	@Override
	public boolean isReduced () 
	{
		Object oo = get_Value(COLUMNNAME_IsReduced);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Ohne.
		@param IsWithout Ohne	  */
	@Override
	public void setIsWithout (boolean IsWithout)
	{
		set_Value (COLUMNNAME_IsWithout, Boolean.valueOf(IsWithout));
	}

	/** Get Ohne.
		@return Ohne	  */
	@Override
	public boolean isWithout () 
	{
		Object oo = get_Value(COLUMNNAME_IsWithout);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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
}