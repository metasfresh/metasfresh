/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for M_AttributeValue
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_M_AttributeValue extends org.compiere.model.PO implements I_M_AttributeValue, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1102034661L;

    /** Standard Constructor */
    public X_M_AttributeValue (Properties ctx, int M_AttributeValue_ID, String trxName)
    {
      super (ctx, M_AttributeValue_ID, trxName);
      /** if (M_AttributeValue_ID == 0)
        {
			setIsNullFieldValue (false);
// N
			setM_Attribute_ID (0);
			setM_AttributeValue_ID (0);
			setName (null);
			setValue (null);
        } */
    }

    /** Load Constructor */
    public X_M_AttributeValue (Properties ctx, ResultSet rs, String trxName)
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
	 * AvailableTrx AD_Reference_ID=540470
	 * Reference name: Available Trx
	 */
	public static final int AVAILABLETRX_AD_Reference_ID=540470;
	/** SO = SO */
	public static final String AVAILABLETRX_SO = "SO";
	/** PO = PO */
	public static final String AVAILABLETRX_PO = "PO";
	/** Set Available Transaction.
		@param AvailableTrx Available Transaction	  */
	@Override
	public void setAvailableTrx (java.lang.String AvailableTrx)
	{

		set_Value (COLUMNNAME_AvailableTrx, AvailableTrx);
	}

	/** Get Available Transaction.
		@return Available Transaction	  */
	@Override
	public java.lang.String getAvailableTrx () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_AvailableTrx);
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

	/** Set Null Value.
		@param IsNullFieldValue Null Value	  */
	@Override
	public void setIsNullFieldValue (boolean IsNullFieldValue)
	{
		set_Value (COLUMNNAME_IsNullFieldValue, Boolean.valueOf(IsNullFieldValue));
	}

	/** Get Null Value.
		@return Null Value	  */
	@Override
	public boolean isNullFieldValue () 
	{
		Object oo = get_Value(COLUMNNAME_IsNullFieldValue);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	@Override
	public org.compiere.model.I_M_Attribute getM_Attribute() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_Attribute_ID, org.compiere.model.I_M_Attribute.class);
	}

	@Override
	public void setM_Attribute(org.compiere.model.I_M_Attribute M_Attribute)
	{
		set_ValueFromPO(COLUMNNAME_M_Attribute_ID, org.compiere.model.I_M_Attribute.class, M_Attribute);
	}

	/** Set Merkmal.
		@param M_Attribute_ID 
		Product Attribute
	  */
	@Override
	public void setM_Attribute_ID (int M_Attribute_ID)
	{
		if (M_Attribute_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Attribute_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Attribute_ID, Integer.valueOf(M_Attribute_ID));
	}

	/** Get Merkmal.
		@return Product Attribute
	  */
	@Override
	public int getM_Attribute_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Attribute_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Merkmals-Wert.
		@param M_AttributeValue_ID 
		Product Attribute Value
	  */
	@Override
	public void setM_AttributeValue_ID (int M_AttributeValue_ID)
	{
		if (M_AttributeValue_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_AttributeValue_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_AttributeValue_ID, Integer.valueOf(M_AttributeValue_ID));
	}

	/** Get Merkmals-Wert.
		@return Product Attribute Value
	  */
	@Override
	public int getM_AttributeValue_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_AttributeValue_ID);
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

	/** Set Suchschlüssel.
		@param Value 
		Search key for the record in the format required - must be unique
	  */
	@Override
	public void setValue (java.lang.String Value)
	{
		set_ValueNoCheck (COLUMNNAME_Value, Value);
	}

	/** Get Suchschlüssel.
		@return Search key for the record in the format required - must be unique
	  */
	@Override
	public java.lang.String getValue () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Value);
	}
}