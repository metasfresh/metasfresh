// Generated Model - DO NOT CHANGE
package org.compiere.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for M_Product_Ingredients
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_M_Product_Ingredients extends org.compiere.model.PO implements I_M_Product_Ingredients, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -755050698L;

    /** Standard Constructor */
    public X_M_Product_Ingredients (final Properties ctx, final int M_Product_Ingredients_ID, @Nullable final String trxName)
    {
      super (ctx, M_Product_Ingredients_ID, trxName);
    }

    /** Load Constructor */
    public X_M_Product_Ingredients (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
    {
      super (ctx, rs, trxName);
    }


	/** Load Meta Data */
	@Override
	protected org.compiere.model.POInfo initPO(final Properties ctx)
	{
		return org.compiere.model.POInfo.getPOInfo(Table_Name);
	}

	@Override
	public void setC_UOM_ID (final int C_UOM_ID)
	{
		if (C_UOM_ID < 1) 
			set_Value (COLUMNNAME_C_UOM_ID, null);
		else 
			set_Value (COLUMNNAME_C_UOM_ID, C_UOM_ID);
	}

	@Override
	public int getC_UOM_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_UOM_ID);
	}

	@Override
	public org.compiere.model.I_M_Ingredients getM_Ingredients()
	{
		return get_ValueAsPO(COLUMNNAME_M_Ingredients_ID, org.compiere.model.I_M_Ingredients.class);
	}

	@Override
	public void setM_Ingredients(final org.compiere.model.I_M_Ingredients M_Ingredients)
	{
		set_ValueFromPO(COLUMNNAME_M_Ingredients_ID, org.compiere.model.I_M_Ingredients.class, M_Ingredients);
	}

	@Override
	public void setM_Ingredients_ID (final int M_Ingredients_ID)
	{
		if (M_Ingredients_ID < 1) 
			set_Value (COLUMNNAME_M_Ingredients_ID, null);
		else 
			set_Value (COLUMNNAME_M_Ingredients_ID, M_Ingredients_ID);
	}

	@Override
	public int getM_Ingredients_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Ingredients_ID);
	}

	@Override
	public void setM_Product_ID (final int M_Product_ID)
	{
		if (M_Product_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Product_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Product_ID, M_Product_ID);
	}

	@Override
	public int getM_Product_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Product_ID);
	}

	@Override
	public void setM_Product_Ingredients_ID (final int M_Product_Ingredients_ID)
	{
		if (M_Product_Ingredients_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Product_Ingredients_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Product_Ingredients_ID, M_Product_Ingredients_ID);
	}

	@Override
	public int getM_Product_Ingredients_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Product_Ingredients_ID);
	}

	@Override
	public void setNRV (final int NRV)
	{
		set_Value (COLUMNNAME_NRV, NRV);
	}

	@Override
	public int getNRV() 
	{
		return get_ValueAsInt(COLUMNNAME_NRV);
	}

	@Override
	public org.compiere.model.I_M_Ingredients getParentElement()
	{
		return get_ValueAsPO(COLUMNNAME_ParentElement_ID, org.compiere.model.I_M_Ingredients.class);
	}

	@Override
	public void setParentElement(final org.compiere.model.I_M_Ingredients ParentElement)
	{
		set_ValueFromPO(COLUMNNAME_ParentElement_ID, org.compiere.model.I_M_Ingredients.class, ParentElement);
	}

	@Override
	public void setParentElement_ID (final int ParentElement_ID)
	{
		if (ParentElement_ID < 1) 
			set_Value (COLUMNNAME_ParentElement_ID, null);
		else 
			set_Value (COLUMNNAME_ParentElement_ID, ParentElement_ID);
	}

	@Override
	public int getParentElement_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_ParentElement_ID);
	}

	@Override
	public void setPrecision (final int Precision)
	{
		set_Value (COLUMNNAME_Precision, Precision);
	}

	@Override
	public int getPrecision() 
	{
		return get_ValueAsInt(COLUMNNAME_Precision);
	}

	@Override
	public void setQty (final @Nullable java.lang.String Qty)
	{
		set_Value (COLUMNNAME_Qty, Qty);
	}

	@Override
	public java.lang.String getQty() 
	{
		return get_ValueAsString(COLUMNNAME_Qty);
	}

	@Override
	public void setSeqNo (final int SeqNo)
	{
		set_Value (COLUMNNAME_SeqNo, SeqNo);
	}

	@Override
	public int getSeqNo() 
	{
		return get_ValueAsInt(COLUMNNAME_SeqNo);
	}
}