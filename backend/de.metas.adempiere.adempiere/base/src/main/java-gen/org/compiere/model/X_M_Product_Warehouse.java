// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for M_Product_Warehouse
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_M_Product_Warehouse extends org.compiere.model.PO implements I_M_Product_Warehouse, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1822324138L;

    /** Standard Constructor */
    public X_M_Product_Warehouse (final Properties ctx, final int M_Product_Warehouse_ID, @Nullable final String trxName)
    {
      super (ctx, M_Product_Warehouse_ID, trxName);
    }

    /** Load Constructor */
    public X_M_Product_Warehouse (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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

	@Override
	public void setM_Product_Warehouse_ID (final int M_Product_Warehouse_ID)
	{
		if (M_Product_Warehouse_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Product_Warehouse_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Product_Warehouse_ID, M_Product_Warehouse_ID);
	}

	@Override
	public int getM_Product_Warehouse_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Product_Warehouse_ID);
	}

	@Override
	public void setM_Warehouse_ID (final int M_Warehouse_ID)
	{
		if (M_Warehouse_ID < 1) 
			set_Value (COLUMNNAME_M_Warehouse_ID, null);
		else 
			set_Value (COLUMNNAME_M_Warehouse_ID, M_Warehouse_ID);
	}

	@Override
	public int getM_Warehouse_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Warehouse_ID);
	}
}