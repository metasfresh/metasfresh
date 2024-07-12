// Generated Model - DO NOT CHANGE
package de.metas.material.cockpit.model;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for MD_Stock_WarehouseAndProduct_v
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_MD_Stock_WarehouseAndProduct_v extends org.compiere.model.PO implements I_MD_Stock_WarehouseAndProduct_v, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -831057120L;

    /** Standard Constructor */
    public X_MD_Stock_WarehouseAndProduct_v (final Properties ctx, final int MD_Stock_WarehouseAndProduct_v_ID, @Nullable final String trxName)
    {
      super (ctx, MD_Stock_WarehouseAndProduct_v_ID, trxName);
    }

    /** Load Constructor */
    public X_MD_Stock_WarehouseAndProduct_v (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setLine (final int Line)
	{
		set_ValueNoCheck (COLUMNNAME_Line, Line);
	}

	@Override
	public int getLine() 
	{
		return get_ValueAsInt(COLUMNNAME_Line);
	}

	@Override
	public void setM_Product_Category_ID (final int M_Product_Category_ID)
	{
		if (M_Product_Category_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Product_Category_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Product_Category_ID, M_Product_Category_ID);
	}

	@Override
	public int getM_Product_Category_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Product_Category_ID);
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
	public void setM_Warehouse_ID (final int M_Warehouse_ID)
	{
		if (M_Warehouse_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Warehouse_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Warehouse_ID, M_Warehouse_ID);
	}

	@Override
	public int getM_Warehouse_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Warehouse_ID);
	}

	@Override
	public void setProductValue (final @Nullable java.lang.String ProductValue)
	{
		set_ValueNoCheck (COLUMNNAME_ProductValue, ProductValue);
	}

	@Override
	public java.lang.String getProductValue() 
	{
		return get_ValueAsString(COLUMNNAME_ProductValue);
	}

	@Override
	public void setQtyOnHand (final @Nullable BigDecimal QtyOnHand)
	{
		set_ValueNoCheck (COLUMNNAME_QtyOnHand, QtyOnHand);
	}

	@Override
	public BigDecimal getQtyOnHand() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyOnHand);
		return bd != null ? bd : BigDecimal.ZERO;
	}
}