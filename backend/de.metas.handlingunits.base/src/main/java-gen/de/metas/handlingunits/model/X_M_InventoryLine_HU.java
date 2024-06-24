// Generated Model - DO NOT CHANGE
package de.metas.handlingunits.model;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for M_InventoryLine_HU
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_M_InventoryLine_HU extends org.compiere.model.PO implements I_M_InventoryLine_HU, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 459794261L;

    /** Standard Constructor */
    public X_M_InventoryLine_HU (final Properties ctx, final int M_InventoryLine_HU_ID, @Nullable final String trxName)
    {
      super (ctx, M_InventoryLine_HU_ID, trxName);
    }

    /** Load Constructor */
    public X_M_InventoryLine_HU (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public de.metas.handlingunits.model.I_M_HU getM_HU()
	{
		return get_ValueAsPO(COLUMNNAME_M_HU_ID, de.metas.handlingunits.model.I_M_HU.class);
	}

	@Override
	public void setM_HU(final de.metas.handlingunits.model.I_M_HU M_HU)
	{
		set_ValueFromPO(COLUMNNAME_M_HU_ID, de.metas.handlingunits.model.I_M_HU.class, M_HU);
	}

	@Override
	public void setM_HU_ID (final int M_HU_ID)
	{
		if (M_HU_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_HU_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_HU_ID, M_HU_ID);
	}

	@Override
	public int getM_HU_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_HU_ID);
	}

	@Override
	public org.compiere.model.I_M_Inventory getM_Inventory()
	{
		return get_ValueAsPO(COLUMNNAME_M_Inventory_ID, org.compiere.model.I_M_Inventory.class);
	}

	@Override
	public void setM_Inventory(final org.compiere.model.I_M_Inventory M_Inventory)
	{
		set_ValueFromPO(COLUMNNAME_M_Inventory_ID, org.compiere.model.I_M_Inventory.class, M_Inventory);
	}

	@Override
	public void setM_Inventory_ID (final int M_Inventory_ID)
	{
		if (M_Inventory_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Inventory_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Inventory_ID, M_Inventory_ID);
	}

	@Override
	public int getM_Inventory_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Inventory_ID);
	}

	@Override
	public void setM_InventoryLine_HU_ID (final int M_InventoryLine_HU_ID)
	{
		if (M_InventoryLine_HU_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_InventoryLine_HU_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_InventoryLine_HU_ID, M_InventoryLine_HU_ID);
	}

	@Override
	public int getM_InventoryLine_HU_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_InventoryLine_HU_ID);
	}

	@Override
	public org.compiere.model.I_M_InventoryLine getM_InventoryLine()
	{
		return get_ValueAsPO(COLUMNNAME_M_InventoryLine_ID, org.compiere.model.I_M_InventoryLine.class);
	}

	@Override
	public void setM_InventoryLine(final org.compiere.model.I_M_InventoryLine M_InventoryLine)
	{
		set_ValueFromPO(COLUMNNAME_M_InventoryLine_ID, org.compiere.model.I_M_InventoryLine.class, M_InventoryLine);
	}

	@Override
	public void setM_InventoryLine_ID (final int M_InventoryLine_ID)
	{
		if (M_InventoryLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_InventoryLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_InventoryLine_ID, M_InventoryLine_ID);
	}

	@Override
	public int getM_InventoryLine_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_InventoryLine_ID);
	}

	@Override
	public void setQtyBook (final @Nullable BigDecimal QtyBook)
	{
		set_Value (COLUMNNAME_QtyBook, QtyBook);
	}

	@Override
	public BigDecimal getQtyBook() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyBook);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyCount (final @Nullable BigDecimal QtyCount)
	{
		set_Value (COLUMNNAME_QtyCount, QtyCount);
	}

	@Override
	public BigDecimal getQtyCount() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyCount);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyInternalUse (final @Nullable BigDecimal QtyInternalUse)
	{
		set_Value (COLUMNNAME_QtyInternalUse, QtyInternalUse);
	}

	@Override
	public BigDecimal getQtyInternalUse() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyInternalUse);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setRenderedQRCode (final @Nullable java.lang.String RenderedQRCode)
	{
		set_Value (COLUMNNAME_RenderedQRCode, RenderedQRCode);
	}

	@Override
	public java.lang.String getRenderedQRCode() 
	{
		return get_ValueAsString(COLUMNNAME_RenderedQRCode);
	}
}