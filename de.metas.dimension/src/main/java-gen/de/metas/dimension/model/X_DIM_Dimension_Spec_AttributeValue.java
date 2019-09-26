/** Generated Model - DO NOT CHANGE */
package de.metas.dimension.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for DIM_Dimension_Spec_AttributeValue
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_DIM_Dimension_Spec_AttributeValue extends org.compiere.model.PO implements I_DIM_Dimension_Spec_AttributeValue, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 300430341L;

    /** Standard Constructor */
    public X_DIM_Dimension_Spec_AttributeValue (Properties ctx, int DIM_Dimension_Spec_AttributeValue_ID, String trxName)
    {
      super (ctx, DIM_Dimension_Spec_AttributeValue_ID, trxName);
      /** if (DIM_Dimension_Spec_AttributeValue_ID == 0)
        {
			setDIM_Dimension_Spec_Attribute_ID (0);
			setDIM_Dimension_Spec_AttributeValue_ID (0);
			setM_AttributeValue_ID (0);
        } */
    }

    /** Load Constructor */
    public X_DIM_Dimension_Spec_AttributeValue (Properties ctx, ResultSet rs, String trxName)
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
	public de.metas.dimension.model.I_DIM_Dimension_Spec_Attribute getDIM_Dimension_Spec_Attribute()
	{
		return get_ValueAsPO(COLUMNNAME_DIM_Dimension_Spec_Attribute_ID, de.metas.dimension.model.I_DIM_Dimension_Spec_Attribute.class);
	}

	@Override
	public void setDIM_Dimension_Spec_Attribute(de.metas.dimension.model.I_DIM_Dimension_Spec_Attribute DIM_Dimension_Spec_Attribute)
	{
		set_ValueFromPO(COLUMNNAME_DIM_Dimension_Spec_Attribute_ID, de.metas.dimension.model.I_DIM_Dimension_Spec_Attribute.class, DIM_Dimension_Spec_Attribute);
	}

	/** Set Dimensionsspezifikation (Merkmal).
		@param DIM_Dimension_Spec_Attribute_ID Dimensionsspezifikation (Merkmal)	  */
	@Override
	public void setDIM_Dimension_Spec_Attribute_ID (int DIM_Dimension_Spec_Attribute_ID)
	{
		if (DIM_Dimension_Spec_Attribute_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_DIM_Dimension_Spec_Attribute_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_DIM_Dimension_Spec_Attribute_ID, Integer.valueOf(DIM_Dimension_Spec_Attribute_ID));
	}

	/** Get Dimensionsspezifikation (Merkmal).
		@return Dimensionsspezifikation (Merkmal)	  */
	@Override
	public int getDIM_Dimension_Spec_Attribute_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DIM_Dimension_Spec_Attribute_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Dimensionsattributwert.
		@param DIM_Dimension_Spec_AttributeValue_ID Dimensionsattributwert	  */
	@Override
	public void setDIM_Dimension_Spec_AttributeValue_ID (int DIM_Dimension_Spec_AttributeValue_ID)
	{
		if (DIM_Dimension_Spec_AttributeValue_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_DIM_Dimension_Spec_AttributeValue_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_DIM_Dimension_Spec_AttributeValue_ID, Integer.valueOf(DIM_Dimension_Spec_AttributeValue_ID));
	}

	/** Get Dimensionsattributwert.
		@return Dimensionsattributwert	  */
	@Override
	public int getDIM_Dimension_Spec_AttributeValue_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DIM_Dimension_Spec_AttributeValue_ID);
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
}