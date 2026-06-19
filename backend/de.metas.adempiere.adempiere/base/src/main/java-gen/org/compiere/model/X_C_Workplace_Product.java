// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for C_Workplace_Product
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_Workplace_Product extends org.compiere.model.PO implements I_C_Workplace_Product, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -684761921L;

    /** Standard Constructor */
    public X_C_Workplace_Product (final Properties ctx, final int C_Workplace_Product_ID, @Nullable final String trxName)
    {
      super (ctx, C_Workplace_Product_ID, trxName);
    }

    /** Load Constructor */
    public X_C_Workplace_Product (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setC_Workplace_ID (final int C_Workplace_ID)
	{
		if (C_Workplace_ID < 1) 
			set_Value (COLUMNNAME_C_Workplace_ID, null);
		else 
			set_Value (COLUMNNAME_C_Workplace_ID, C_Workplace_ID);
	}

	@Override
	public int getC_Workplace_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Workplace_ID);
	}

	@Override
	public void setC_Workplace_Product_ID (final int C_Workplace_Product_ID)
	{
		if (C_Workplace_Product_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Workplace_Product_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Workplace_Product_ID, C_Workplace_Product_ID);
	}

	@Override
	public int getC_Workplace_Product_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Workplace_Product_ID);
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