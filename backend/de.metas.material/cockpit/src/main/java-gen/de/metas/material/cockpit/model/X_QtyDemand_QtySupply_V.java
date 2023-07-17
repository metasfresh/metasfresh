// Generated Model - DO NOT CHANGE
package de.metas.material.cockpit.model;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for QtyDemand_QtySupply_V
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_QtyDemand_QtySupply_V extends org.compiere.model.PO implements I_QtyDemand_QtySupply_V, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1687551232L;

    /** Standard Constructor */
    public X_QtyDemand_QtySupply_V (final Properties ctx, final int QtyDemand_QtySupply_V_ID, @Nullable final String trxName)
    {
      super (ctx, QtyDemand_QtySupply_V_ID, trxName);
    }

    /** Load Constructor */
    public X_QtyDemand_QtySupply_V (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setAttributesKey (final @Nullable String AttributesKey)
	{
		set_ValueNoCheck (COLUMNNAME_AttributesKey, AttributesKey);
	}

	@Override
	public String getAttributesKey() 
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
	public void setQtyReserved (final @Nullable BigDecimal QtyReserved)
	{
		set_ValueNoCheck (COLUMNNAME_QtyReserved, QtyReserved);
	}

	@Override
	public BigDecimal getQtyReserved() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyReserved);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyToMove (final @Nullable BigDecimal QtyToMove)
	{
		set_ValueNoCheck (COLUMNNAME_QtyToMove, QtyToMove);
	}

	@Override
	public BigDecimal getQtyToMove() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyToMove);
		return bd != null ? bd : BigDecimal.ZERO;
	}
}