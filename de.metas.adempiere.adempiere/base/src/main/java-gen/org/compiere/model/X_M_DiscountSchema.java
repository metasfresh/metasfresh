/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for M_DiscountSchema
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_M_DiscountSchema extends org.compiere.model.PO implements I_M_DiscountSchema, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -1059044603L;

    /** Standard Constructor */
    public X_M_DiscountSchema (Properties ctx, int M_DiscountSchema_ID, String trxName)
    {
      super (ctx, M_DiscountSchema_ID, trxName);
      /** if (M_DiscountSchema_ID == 0)
        {
			setDiscountType (null);
			setIsBPartnerFlatDiscount (false);
			setIsQuantityBased (true); // Y
			setM_DiscountSchema_ID (0);
			setName (null);
			setValidFrom (new Timestamp( System.currentTimeMillis() ));
        } */
    }

    /** Load Constructor */
    public X_M_DiscountSchema (Properties ctx, ResultSet rs, String trxName)
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

	/** 
	 * CumulativeLevel AD_Reference_ID=246
	 * Reference name: M_Discount CumulativeLevel
	 */
	public static final int CUMULATIVELEVEL_AD_Reference_ID=246;
	/** Position = L */
	public static final String CUMULATIVELEVEL_Position = "L";
	/** Set Anwenden auf.
		@param CumulativeLevel 
		Level for accumulative calculations
	  */
	@Override
	public void setCumulativeLevel (java.lang.String CumulativeLevel)
	{

		set_Value (COLUMNNAME_CumulativeLevel, CumulativeLevel);
	}

	/** Get Anwenden auf.
		@return Level for accumulative calculations
	  */
	@Override
	public java.lang.String getCumulativeLevel () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_CumulativeLevel);
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

	/** 
	 * DiscountType AD_Reference_ID=247
	 * Reference name: M_Discount Type
	 */
	public static final int DISCOUNTTYPE_AD_Reference_ID=247;
	/** Flat Percent = F */
	public static final String DISCOUNTTYPE_FlatPercent = "F";
	/** Formula = S */
	public static final String DISCOUNTTYPE_Formula = "S";
	/** Breaks = B */
	public static final String DISCOUNTTYPE_Breaks = "B";
	/** Pricelist = P */
	public static final String DISCOUNTTYPE_Pricelist = "P";
	/** Set Rabatt Art.
		@param DiscountType 
		Type of trade discount calculation
	  */
	@Override
	public void setDiscountType (java.lang.String DiscountType)
	{

		set_Value (COLUMNNAME_DiscountType, DiscountType);
	}

	/** Get Rabatt Art.
		@return Type of trade discount calculation
	  */
	@Override
	public java.lang.String getDiscountType () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DiscountType);
	}

	/** Set Fester Rabatt %.
		@param FlatDiscount 
		Flat discount percentage 
	  */
	@Override
	public void setFlatDiscount (java.math.BigDecimal FlatDiscount)
	{
		set_Value (COLUMNNAME_FlatDiscount, FlatDiscount);
	}

	/** Get Fester Rabatt %.
		@return Flat discount percentage 
	  */
	@Override
	public java.math.BigDecimal getFlatDiscount () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_FlatDiscount);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Fester Rabatt.
		@param IsBPartnerFlatDiscount 
		Use flat discount defined on Business Partner Level
	  */
	@Override
	public void setIsBPartnerFlatDiscount (boolean IsBPartnerFlatDiscount)
	{
		set_Value (COLUMNNAME_IsBPartnerFlatDiscount, Boolean.valueOf(IsBPartnerFlatDiscount));
	}

	/** Get Fester Rabatt.
		@return Use flat discount defined on Business Partner Level
	  */
	@Override
	public boolean isBPartnerFlatDiscount () 
	{
		Object oo = get_Value(COLUMNNAME_IsBPartnerFlatDiscount);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Mengen Rabatt.
		@param IsQuantityBased 
		Trade discount break level based on Quantity (not value)
	  */
	@Override
	public void setIsQuantityBased (boolean IsQuantityBased)
	{
		set_Value (COLUMNNAME_IsQuantityBased, Boolean.valueOf(IsQuantityBased));
	}

	/** Get Mengen Rabatt.
		@return Trade discount break level based on Quantity (not value)
	  */
	@Override
	public boolean isQuantityBased () 
	{
		Object oo = get_Value(COLUMNNAME_IsQuantityBased);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Rabatt Schema.
		@param M_DiscountSchema_ID 
		Schema um den prozentualen Rabatt zu berechnen
	  */
	@Override
	public void setM_DiscountSchema_ID (int M_DiscountSchema_ID)
	{
		if (M_DiscountSchema_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_DiscountSchema_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_DiscountSchema_ID, Integer.valueOf(M_DiscountSchema_ID));
	}

	/** Get Rabatt Schema.
		@return Schema um den prozentualen Rabatt zu berechnen
	  */
	@Override
	public int getM_DiscountSchema_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_DiscountSchema_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Verarbeiten.
		@param Processing Verarbeiten	  */
	@Override
	public void setProcessing (boolean Processing)
	{
		set_Value (COLUMNNAME_Processing, Boolean.valueOf(Processing));
	}

	/** Get Verarbeiten.
		@return Verarbeiten	  */
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

	/** Set Skript.
		@param Script 
		Dynamic Java Language Script to calculate result
	  */
	@Override
	public void setScript (java.lang.String Script)
	{
		set_Value (COLUMNNAME_Script, Script);
	}

	/** Get Skript.
		@return Dynamic Java Language Script to calculate result
	  */
	@Override
	public java.lang.String getScript () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Script);
	}

	/** Set Gültig ab.
		@param ValidFrom 
		Valid from including this date (first day)
	  */
	@Override
	public void setValidFrom (java.sql.Timestamp ValidFrom)
	{
		set_Value (COLUMNNAME_ValidFrom, ValidFrom);
	}

	/** Get Gültig ab.
		@return Valid from including this date (first day)
	  */
	@Override
	public java.sql.Timestamp getValidFrom () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_ValidFrom);
	}
}