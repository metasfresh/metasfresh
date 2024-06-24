// Generated Model - DO NOT CHANGE
package de.metas.material.cockpit.model;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for MD_Stock_From_HUs_V
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_MD_Stock_From_HUs_V extends org.compiere.model.PO implements I_MD_Stock_From_HUs_V, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 964519981L;

    /** Standard Constructor */
    public X_MD_Stock_From_HUs_V (final Properties ctx, final int MD_Stock_From_HUs_V_ID, @Nullable final String trxName)
    {
      super (ctx, MD_Stock_From_HUs_V_ID, trxName);
    }

    /** Load Constructor */
    public X_MD_Stock_From_HUs_V (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setAttributesKey (final @Nullable java.lang.String AttributesKey)
	{
		set_ValueNoCheck (COLUMNNAME_AttributesKey, AttributesKey);
	}

	@Override
	public java.lang.String getAttributesKey() 
	{
		return get_ValueAsString(COLUMNNAME_AttributesKey);
	}

	@Override
	public void setC_UOM_ID (final int C_UOM_ID)
	{
		if (C_UOM_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_UOM_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_UOM_ID, C_UOM_ID);
	}

	@Override
	public int getC_UOM_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_UOM_ID);
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

	@Override
	public void setQtyOnHandChange (final @Nullable BigDecimal QtyOnHandChange)
	{
		set_ValueNoCheck (COLUMNNAME_QtyOnHandChange, QtyOnHandChange);
	}

	@Override
	public BigDecimal getQtyOnHandChange() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyOnHandChange);
		return bd != null ? bd : BigDecimal.ZERO;
	}
}