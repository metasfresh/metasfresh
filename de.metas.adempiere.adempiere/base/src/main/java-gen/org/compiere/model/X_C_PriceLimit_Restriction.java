/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_PriceLimit_Restriction
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_PriceLimit_Restriction extends org.compiere.model.PO implements I_C_PriceLimit_Restriction, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -385640433L;

    /** Standard Constructor */
    public X_C_PriceLimit_Restriction (Properties ctx, int C_PriceLimit_Restriction_ID, String trxName)
    {
      super (ctx, C_PriceLimit_Restriction_ID, trxName);
      /** if (C_PriceLimit_Restriction_ID == 0)
        {
			setBase_PricingSystem_ID (0);
			setC_PriceLimit_Restriction_ID (0);
        } */
    }

    /** Load Constructor */
    public X_C_PriceLimit_Restriction (Properties ctx, ResultSet rs, String trxName)
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
	public org.compiere.model.I_M_PricingSystem getBase_PricingSystem() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_Base_PricingSystem_ID, org.compiere.model.I_M_PricingSystem.class);
	}

	@Override
	public void setBase_PricingSystem(org.compiere.model.I_M_PricingSystem Base_PricingSystem)
	{
		set_ValueFromPO(COLUMNNAME_Base_PricingSystem_ID, org.compiere.model.I_M_PricingSystem.class, Base_PricingSystem);
	}

	/** Set Base_PricingSystem_ID.
		@param Base_PricingSystem_ID Base_PricingSystem_ID	  */
	@Override
	public void setBase_PricingSystem_ID (int Base_PricingSystem_ID)
	{
		if (Base_PricingSystem_ID < 1) 
			set_Value (COLUMNNAME_Base_PricingSystem_ID, null);
		else 
			set_Value (COLUMNNAME_Base_PricingSystem_ID, Integer.valueOf(Base_PricingSystem_ID));
	}

	/** Get Base_PricingSystem_ID.
		@return Base_PricingSystem_ID	  */
	@Override
	public int getBase_PricingSystem_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Base_PricingSystem_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Price Limit Restriction.
		@param C_PriceLimit_Restriction_ID Price Limit Restriction	  */
	@Override
	public void setC_PriceLimit_Restriction_ID (int C_PriceLimit_Restriction_ID)
	{
		if (C_PriceLimit_Restriction_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_PriceLimit_Restriction_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_PriceLimit_Restriction_ID, Integer.valueOf(C_PriceLimit_Restriction_ID));
	}

	/** Get Price Limit Restriction.
		@return Price Limit Restriction	  */
	@Override
	public int getC_PriceLimit_Restriction_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_PriceLimit_Restriction_ID);
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

	/** Set Rabatt %.
		@param Discount 
		Abschlag in Prozent
	  */
	@Override
	public void setDiscount (java.math.BigDecimal Discount)
	{
		set_Value (COLUMNNAME_Discount, Discount);
	}

	/** Get Rabatt %.
		@return Abschlag in Prozent
	  */
	@Override
	public java.math.BigDecimal getDiscount () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Discount);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Aufschlag auf Standardpreis.
		@param Std_AddAmt 
		Amount added to a price as a surcharge
	  */
	@Override
	public void setStd_AddAmt (java.math.BigDecimal Std_AddAmt)
	{
		set_Value (COLUMNNAME_Std_AddAmt, Std_AddAmt);
	}

	/** Get Aufschlag auf Standardpreis.
		@return Amount added to a price as a surcharge
	  */
	@Override
	public java.math.BigDecimal getStd_AddAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Std_AddAmt);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}
}