// Generated Model - DO NOT CHANGE
package org.compiere.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for M_Product_Allergen
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_M_Product_Allergen extends org.compiere.model.PO implements I_M_Product_Allergen, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -2016580125L;

    /** Standard Constructor */
    public X_M_Product_Allergen (final Properties ctx, final int M_Product_Allergen_ID, @Nullable final String trxName)
    {
      super (ctx, M_Product_Allergen_ID, trxName);
    }

    /** Load Constructor */
    public X_M_Product_Allergen (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public org.compiere.model.I_M_Allergen getM_Allergen()
	{
		return get_ValueAsPO(COLUMNNAME_M_Allergen_ID, org.compiere.model.I_M_Allergen.class);
	}

	@Override
	public void setM_Allergen(final org.compiere.model.I_M_Allergen M_Allergen)
	{
		set_ValueFromPO(COLUMNNAME_M_Allergen_ID, org.compiere.model.I_M_Allergen.class, M_Allergen);
	}

	@Override
	public void setM_Allergen_ID (final int M_Allergen_ID)
	{
		if (M_Allergen_ID < 1) 
			set_Value (COLUMNNAME_M_Allergen_ID, null);
		else 
			set_Value (COLUMNNAME_M_Allergen_ID, M_Allergen_ID);
	}

	@Override
	public int getM_Allergen_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Allergen_ID);
	}

	@Override
	public void setM_Product_Allergen_ID (final int M_Product_Allergen_ID)
	{
		if (M_Product_Allergen_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Product_Allergen_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Product_Allergen_ID, M_Product_Allergen_ID);
	}

	@Override
	public int getM_Product_Allergen_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Product_Allergen_ID);
	}

	@Override
	public void setM_Product_ID (final int M_Product_ID)
	{
		if (M_Product_ID < 1) 
			set_Value (COLUMNNAME_M_Product_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_ID, M_Product_ID);
	}

	@Override
	public int getM_Product_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Product_ID);
	}
}