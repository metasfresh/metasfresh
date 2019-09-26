/** Generated Model - DO NOT CHANGE */
package de.metas.pricing.attributebased;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for M_ProductPrice_Attribute_Line
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_M_ProductPrice_Attribute_Line extends org.compiere.model.PO implements I_M_ProductPrice_Attribute_Line, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1374564116L;

    /** Standard Constructor */
    public X_M_ProductPrice_Attribute_Line (Properties ctx, int M_ProductPrice_Attribute_Line_ID, String trxName)
    {
      super (ctx, M_ProductPrice_Attribute_Line_ID, trxName);
      /** if (M_ProductPrice_Attribute_Line_ID == 0)
        {
			setM_Attribute_ID (0);
			setM_ProductPrice_Attribute_ID (0);
			setM_ProductPrice_Attribute_Line_ID (0);
        } */
    }

    /** Load Constructor */
    public X_M_ProductPrice_Attribute_Line (Properties ctx, ResultSet rs, String trxName)
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
		Produkt-Merkmal
	  */
	@Override
	public void setM_Attribute_ID (int M_Attribute_ID)
	{
		if (M_Attribute_ID < 1) 
			set_Value (COLUMNNAME_M_Attribute_ID, null);
		else 
			set_Value (COLUMNNAME_M_Attribute_ID, Integer.valueOf(M_Attribute_ID));
	}

	/** Get Merkmal.
		@return Produkt-Merkmal
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

	@Override
	public de.metas.pricing.attributebased.I_M_ProductPrice_Attribute getM_ProductPrice_Attribute()
	{
		return get_ValueAsPO(COLUMNNAME_M_ProductPrice_Attribute_ID, de.metas.pricing.attributebased.I_M_ProductPrice_Attribute.class);
	}

	@Override
	public void setM_ProductPrice_Attribute(de.metas.pricing.attributebased.I_M_ProductPrice_Attribute M_ProductPrice_Attribute)
	{
		set_ValueFromPO(COLUMNNAME_M_ProductPrice_Attribute_ID, de.metas.pricing.attributebased.I_M_ProductPrice_Attribute.class, M_ProductPrice_Attribute);
	}

	/** Set Attribute price.
		@param M_ProductPrice_Attribute_ID Attribute price	  */
	@Override
	public void setM_ProductPrice_Attribute_ID (int M_ProductPrice_Attribute_ID)
	{
		if (M_ProductPrice_Attribute_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_ProductPrice_Attribute_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_ProductPrice_Attribute_ID, Integer.valueOf(M_ProductPrice_Attribute_ID));
	}

	/** Get Attribute price.
		@return Attribute price	  */
	@Override
	public int getM_ProductPrice_Attribute_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_ProductPrice_Attribute_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Attribute price line.
		@param M_ProductPrice_Attribute_Line_ID Attribute price line	  */
	@Override
	public void setM_ProductPrice_Attribute_Line_ID (int M_ProductPrice_Attribute_Line_ID)
	{
		if (M_ProductPrice_Attribute_Line_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_ProductPrice_Attribute_Line_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_ProductPrice_Attribute_Line_ID, Integer.valueOf(M_ProductPrice_Attribute_Line_ID));
	}

	/** Get Attribute price line.
		@return Attribute price line	  */
	@Override
	public int getM_ProductPrice_Attribute_Line_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_ProductPrice_Attribute_Line_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}