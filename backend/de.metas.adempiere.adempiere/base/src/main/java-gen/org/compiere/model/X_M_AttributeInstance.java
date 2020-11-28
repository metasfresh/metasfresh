/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for M_AttributeInstance
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_M_AttributeInstance extends org.compiere.model.PO implements I_M_AttributeInstance, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -1734582988L;

    /** Standard Constructor */
    public X_M_AttributeInstance (Properties ctx, int M_AttributeInstance_ID, String trxName)
    {
      super (ctx, M_AttributeInstance_ID, trxName);
      /** if (M_AttributeInstance_ID == 0)
        {
			setM_Attribute_ID (0);
			setM_AttributeInstance_ID (0);
			setM_AttributeSetInstance_ID (0);
        } */
    }

    /** Load Constructor */
    public X_M_AttributeInstance (Properties ctx, ResultSet rs, String trxName)
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

	/** Set M_AttributeInstance.
		@param M_AttributeInstance_ID M_AttributeInstance	  */
	@Override
	public void setM_AttributeInstance_ID (int M_AttributeInstance_ID)
	{
		if (M_AttributeInstance_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_AttributeInstance_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_AttributeInstance_ID, Integer.valueOf(M_AttributeInstance_ID));
	}

	/** Get M_AttributeInstance.
		@return M_AttributeInstance	  */
	@Override
	public int getM_AttributeInstance_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_AttributeInstance_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_M_AttributeSetInstance getM_AttributeSetInstance()
	{
		return get_ValueAsPO(COLUMNNAME_M_AttributeSetInstance_ID, org.compiere.model.I_M_AttributeSetInstance.class);
	}

	@Override
	public void setM_AttributeSetInstance(org.compiere.model.I_M_AttributeSetInstance M_AttributeSetInstance)
	{
		set_ValueFromPO(COLUMNNAME_M_AttributeSetInstance_ID, org.compiere.model.I_M_AttributeSetInstance.class, M_AttributeSetInstance);
	}

	/** Set Merkmale.
		@param M_AttributeSetInstance_ID 
		Merkmals Ausprägungen zum Produkt
	  */
	@Override
	public void setM_AttributeSetInstance_ID (int M_AttributeSetInstance_ID)
	{
		if (M_AttributeSetInstance_ID < 0) 
			set_ValueNoCheck (COLUMNNAME_M_AttributeSetInstance_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_AttributeSetInstance_ID, Integer.valueOf(M_AttributeSetInstance_ID));
	}

	/** Get Merkmale.
		@return Merkmals Ausprägungen zum Produkt
	  */
	@Override
	public int getM_AttributeSetInstance_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_AttributeSetInstance_ID);
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
			set_Value (COLUMNNAME_M_AttributeValue_ID, null);
		else 
			set_Value (COLUMNNAME_M_AttributeValue_ID, Integer.valueOf(M_AttributeValue_ID));
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

	/** Set Suchschlüssel.
		@param Value 
		Search key for the record in the format required - must be unique
	  */
	@Override
	public void setValue (java.lang.String Value)
	{
		set_Value (COLUMNNAME_Value, Value);
	}

	/** Get Suchschlüssel.
		@return Search key for the record in the format required - must be unique
	  */
	@Override
	public java.lang.String getValue () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Value);
	}

	/** Set Datum.
		@param ValueDate Datum	  */
	@Override
	public void setValueDate (java.sql.Timestamp ValueDate)
	{
		set_Value (COLUMNNAME_ValueDate, ValueDate);
	}

	/** Get Datum.
		@return Datum	  */
	@Override
	public java.sql.Timestamp getValueDate () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_ValueDate);
	}

	/** Set Zahlwert.
		@param ValueNumber 
		Numeric Value
	  */
	@Override
	public void setValueNumber (java.math.BigDecimal ValueNumber)
	{
		set_Value (COLUMNNAME_ValueNumber, ValueNumber);
	}

	/** Get Zahlwert.
		@return Numeric Value
	  */
	@Override
	public java.math.BigDecimal getValueNumber () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ValueNumber);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}
}