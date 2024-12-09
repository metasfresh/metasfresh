<<<<<<< HEAD
/** Generated Model - DO NOT CHANGE */
=======
// Generated Model - DO NOT CHANGE
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
<<<<<<< HEAD

/** Generated Model for M_DiscountSchema
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_M_DiscountSchema extends org.compiere.model.PO implements I_M_DiscountSchema, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -336442302L;

    /** Standard Constructor */
    public X_M_DiscountSchema (Properties ctx, int M_DiscountSchema_ID, String trxName)
    {
      super (ctx, M_DiscountSchema_ID, trxName);
      /** if (M_DiscountSchema_ID == 0)
        {
			setBreakValueType (null); // Q
			setDiscountType (null);
			setIsBPartnerFlatDiscount (false);
			setM_DiscountSchema_ID (0);
			setName (null);
			setValidFrom (new Timestamp( System.currentTimeMillis() ));
        } */
    }

    /** Load Constructor */
    public X_M_DiscountSchema (Properties ctx, ResultSet rs, String trxName)
=======
import javax.annotation.Nullable;

/** Generated Model for M_DiscountSchema
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_M_DiscountSchema extends org.compiere.model.PO implements I_M_DiscountSchema, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 174899977L;

    /** Standard Constructor */
    public X_M_DiscountSchema (final Properties ctx, final int M_DiscountSchema_ID, @Nullable final String trxName)
    {
      super (ctx, M_DiscountSchema_ID, trxName);
    }

    /** Load Constructor */
    public X_M_DiscountSchema (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
    {
      super (ctx, rs, trxName);
    }


<<<<<<< HEAD
    /** Load Meta Data */
    @Override
    protected org.compiere.model.POInfo initPO (Properties ctx)
    {
      org.compiere.model.POInfo poi = org.compiere.model.POInfo.getPOInfo (ctx, Table_Name, get_TrxName());
      return poi;
    }

	@Override
	public org.compiere.model.I_M_Attribute getBreakValue_Attribute() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_BreakValue_Attribute_ID, org.compiere.model.I_M_Attribute.class);
	}

	@Override
	public void setBreakValue_Attribute(org.compiere.model.I_M_Attribute BreakValue_Attribute)
	{
		set_ValueFromPO(COLUMNNAME_BreakValue_Attribute_ID, org.compiere.model.I_M_Attribute.class, BreakValue_Attribute);
	}

	/** Set Merkmal.
		@param BreakValue_Attribute_ID Merkmal	  */
	@Override
	public void setBreakValue_Attribute_ID (int BreakValue_Attribute_ID)
=======
	/** Load Meta Data */
	@Override
	protected org.compiere.model.POInfo initPO(final Properties ctx)
	{
		return org.compiere.model.POInfo.getPOInfo(Table_Name);
	}

	@Override
	public void setBreakValue_Attribute_ID (final int BreakValue_Attribute_ID)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		if (BreakValue_Attribute_ID < 1) 
			set_Value (COLUMNNAME_BreakValue_Attribute_ID, null);
		else 
<<<<<<< HEAD
			set_Value (COLUMNNAME_BreakValue_Attribute_ID, Integer.valueOf(BreakValue_Attribute_ID));
	}

	/** Get Merkmal.
		@return Merkmal	  */
	@Override
	public int getBreakValue_Attribute_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_BreakValue_Attribute_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
=======
			set_Value (COLUMNNAME_BreakValue_Attribute_ID, BreakValue_Attribute_ID);
	}

	@Override
	public int getBreakValue_Attribute_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_BreakValue_Attribute_ID);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}

	/** 
	 * BreakValueType AD_Reference_ID=540886
	 * Reference name: M_DiscountSchema_BreakValueType
	 */
	public static final int BREAKVALUETYPE_AD_Reference_ID=540886;
	/** Quantity = Q */
	public static final String BREAKVALUETYPE_Quantity = "Q";
	/** Amount = A */
	public static final String BREAKVALUETYPE_Amount = "A";
	/** Attribute = T */
	public static final String BREAKVALUETYPE_Attribute = "T";
<<<<<<< HEAD
	/** Set Break Value Type.
		@param BreakValueType Break Value Type	  */
	@Override
	public void setBreakValueType (java.lang.String BreakValueType)
	{

		set_Value (COLUMNNAME_BreakValueType, BreakValueType);
	}

	/** Get Break Value Type.
		@return Break Value Type	  */
	@Override
	public java.lang.String getBreakValueType () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_BreakValueType);
=======
	@Override
	public void setBreakValueType (final java.lang.String BreakValueType)
	{
		set_Value (COLUMNNAME_BreakValueType, BreakValueType);
	}

	@Override
	public java.lang.String getBreakValueType() 
	{
		return get_ValueAsString(COLUMNNAME_BreakValueType);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}

	/** 
	 * CumulativeLevel AD_Reference_ID=246
	 * Reference name: M_Discount CumulativeLevel
	 */
	public static final int CUMULATIVELEVEL_AD_Reference_ID=246;
	/** Position = L */
	public static final String CUMULATIVELEVEL_Position = "L";
<<<<<<< HEAD
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
=======
	@Override
	public void setCumulativeLevel (final @Nullable java.lang.String CumulativeLevel)
	{
		set_Value (COLUMNNAME_CumulativeLevel, CumulativeLevel);
	}

	@Override
	public java.lang.String getCumulativeLevel() 
	{
		return get_ValueAsString(COLUMNNAME_CumulativeLevel);
	}

	@Override
	public void setDescription (final @Nullable java.lang.String Description)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		set_Value (COLUMNNAME_Description, Description);
	}

<<<<<<< HEAD
	/** Get Beschreibung.
		@return Beschreibung	  */
	@Override
	public java.lang.String getDescription () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Description);
=======
	@Override
	public java.lang.String getDescription() 
	{
		return get_ValueAsString(COLUMNNAME_Description);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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
<<<<<<< HEAD
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
=======
	@Override
	public void setDiscountType (final java.lang.String DiscountType)
	{
		set_Value (COLUMNNAME_DiscountType, DiscountType);
	}

	@Override
	public java.lang.String getDiscountType() 
	{
		return get_ValueAsString(COLUMNNAME_DiscountType);
	}

	@Override
	public void setDoNotChangeZeroPrices (final boolean DoNotChangeZeroPrices)
	{
		set_Value (COLUMNNAME_DoNotChangeZeroPrices, DoNotChangeZeroPrices);
	}

	@Override
	public boolean isDoNotChangeZeroPrices() 
	{
		return get_ValueAsBoolean(COLUMNNAME_DoNotChangeZeroPrices);
	}

	@Override
	public void setFlatDiscount (final @Nullable BigDecimal FlatDiscount)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		set_Value (COLUMNNAME_FlatDiscount, FlatDiscount);
	}

<<<<<<< HEAD
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

	/** Set Rabatt Schema.
		@param M_DiscountSchema_ID 
		Schema um den prozentualen Rabatt zu berechnen
	  */
	@Override
	public void setM_DiscountSchema_ID (int M_DiscountSchema_ID)
=======
	@Override
	public BigDecimal getFlatDiscount() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_FlatDiscount);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setIsBPartnerFlatDiscount (final boolean IsBPartnerFlatDiscount)
	{
		set_Value (COLUMNNAME_IsBPartnerFlatDiscount, IsBPartnerFlatDiscount);
	}

	@Override
	public boolean isBPartnerFlatDiscount() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsBPartnerFlatDiscount);
	}

	@Override
	public org.compiere.model.I_M_DiscountSchema_Calculated_Surcharge getM_DiscountSchema_Calculated_Surcharge()
	{
		return get_ValueAsPO(COLUMNNAME_M_DiscountSchema_Calculated_Surcharge_ID, org.compiere.model.I_M_DiscountSchema_Calculated_Surcharge.class);
	}

	@Override
	public void setM_DiscountSchema_Calculated_Surcharge(final org.compiere.model.I_M_DiscountSchema_Calculated_Surcharge M_DiscountSchema_Calculated_Surcharge)
	{
		set_ValueFromPO(COLUMNNAME_M_DiscountSchema_Calculated_Surcharge_ID, org.compiere.model.I_M_DiscountSchema_Calculated_Surcharge.class, M_DiscountSchema_Calculated_Surcharge);
	}

	@Override
	public void setM_DiscountSchema_Calculated_Surcharge_ID (final int M_DiscountSchema_Calculated_Surcharge_ID)
	{
		if (M_DiscountSchema_Calculated_Surcharge_ID < 1) 
			set_Value (COLUMNNAME_M_DiscountSchema_Calculated_Surcharge_ID, null);
		else 
			set_Value (COLUMNNAME_M_DiscountSchema_Calculated_Surcharge_ID, M_DiscountSchema_Calculated_Surcharge_ID);
	}

	@Override
	public int getM_DiscountSchema_Calculated_Surcharge_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_DiscountSchema_Calculated_Surcharge_ID);
	}

	@Override
	public void setM_DiscountSchema_ID (final int M_DiscountSchema_ID)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		if (M_DiscountSchema_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_DiscountSchema_ID, null);
		else 
<<<<<<< HEAD
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
=======
			set_ValueNoCheck (COLUMNNAME_M_DiscountSchema_ID, M_DiscountSchema_ID);
	}

	@Override
	public int getM_DiscountSchema_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_DiscountSchema_ID);
	}

	@Override
	public void setName (final java.lang.String Name)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		set_Value (COLUMNNAME_Name, Name);
	}

<<<<<<< HEAD
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
=======
	@Override
	public java.lang.String getName() 
	{
		return get_ValueAsString(COLUMNNAME_Name);
	}

	@Override
	public void setProcessing (final boolean Processing)
	{
		set_Value (COLUMNNAME_Processing, Processing);
	}

	@Override
	public boolean isProcessing() 
	{
		return get_ValueAsBoolean(COLUMNNAME_Processing);
	}

	@Override
	public void setScript (final @Nullable java.lang.String Script)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		set_Value (COLUMNNAME_Script, Script);
	}

<<<<<<< HEAD
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
=======
	@Override
	public java.lang.String getScript() 
	{
		return get_ValueAsString(COLUMNNAME_Script);
	}

	@Override
	public void setValidFrom (final java.sql.Timestamp ValidFrom)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		set_Value (COLUMNNAME_ValidFrom, ValidFrom);
	}

<<<<<<< HEAD
	/** Get Gültig ab.
		@return Valid from including this date (first day)
	  */
	@Override
	public java.sql.Timestamp getValidFrom () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_ValidFrom);
=======
	@Override
	public java.sql.Timestamp getValidFrom() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_ValidFrom);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}
}