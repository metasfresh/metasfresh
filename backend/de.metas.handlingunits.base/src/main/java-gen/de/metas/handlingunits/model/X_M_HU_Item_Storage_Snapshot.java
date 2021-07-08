// Generated Model - DO NOT CHANGE
package de.metas.handlingunits.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for M_HU_Item_Storage_Snapshot
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_M_HU_Item_Storage_Snapshot extends org.compiere.model.PO implements I_M_HU_Item_Storage_Snapshot, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 765163741L;

    /** Standard Constructor */
    public X_M_HU_Item_Storage_Snapshot (final Properties ctx, final int M_HU_Item_Storage_Snapshot_ID, @Nullable final String trxName)
    {
      super (ctx, M_HU_Item_Storage_Snapshot_ID, trxName);
    }

    /** Load Constructor */
    public X_M_HU_Item_Storage_Snapshot (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public de.metas.handlingunits.model.I_M_HU_Item getM_HU_Item()
	{
		return get_ValueAsPO(COLUMNNAME_M_HU_Item_ID, de.metas.handlingunits.model.I_M_HU_Item.class);
	}

	@Override
	public void setM_HU_Item(final de.metas.handlingunits.model.I_M_HU_Item M_HU_Item)
	{
		set_ValueFromPO(COLUMNNAME_M_HU_Item_ID, de.metas.handlingunits.model.I_M_HU_Item.class, M_HU_Item);
	}

	@Override
	public void setM_HU_Item_ID (final int M_HU_Item_ID)
	{
		if (M_HU_Item_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_HU_Item_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_HU_Item_ID, M_HU_Item_ID);
	}

	@Override
	public int getM_HU_Item_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_HU_Item_ID);
	}

	@Override
	public de.metas.handlingunits.model.I_M_HU_Item_Storage getM_HU_Item_Storage()
	{
		return get_ValueAsPO(COLUMNNAME_M_HU_Item_Storage_ID, de.metas.handlingunits.model.I_M_HU_Item_Storage.class);
	}

	@Override
	public void setM_HU_Item_Storage(final de.metas.handlingunits.model.I_M_HU_Item_Storage M_HU_Item_Storage)
	{
		set_ValueFromPO(COLUMNNAME_M_HU_Item_Storage_ID, de.metas.handlingunits.model.I_M_HU_Item_Storage.class, M_HU_Item_Storage);
	}

	@Override
	public void setM_HU_Item_Storage_ID (final int M_HU_Item_Storage_ID)
	{
		if (M_HU_Item_Storage_ID < 1) 
			set_Value (COLUMNNAME_M_HU_Item_Storage_ID, null);
		else 
			set_Value (COLUMNNAME_M_HU_Item_Storage_ID, M_HU_Item_Storage_ID);
	}

	@Override
	public int getM_HU_Item_Storage_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_HU_Item_Storage_ID);
	}

	@Override
	public void setM_HU_Item_Storage_Snapshot_ID (final int M_HU_Item_Storage_Snapshot_ID)
	{
		if (M_HU_Item_Storage_Snapshot_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_HU_Item_Storage_Snapshot_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_HU_Item_Storage_Snapshot_ID, M_HU_Item_Storage_Snapshot_ID);
	}

	@Override
	public int getM_HU_Item_Storage_Snapshot_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_HU_Item_Storage_Snapshot_ID);
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
	public void setQty (final BigDecimal Qty)
	{
		set_Value (COLUMNNAME_Qty, Qty);
	}

	@Override
	public BigDecimal getQty() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_Qty);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setSnapshot_UUID (final java.lang.String Snapshot_UUID)
	{
		set_Value (COLUMNNAME_Snapshot_UUID, Snapshot_UUID);
	}

	@Override
	public java.lang.String getSnapshot_UUID() 
	{
		return get_ValueAsString(COLUMNNAME_Snapshot_UUID);
	}
}