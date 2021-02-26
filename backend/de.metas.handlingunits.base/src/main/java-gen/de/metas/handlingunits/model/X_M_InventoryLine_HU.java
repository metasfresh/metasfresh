/** Generated Model - DO NOT CHANGE */
package de.metas.handlingunits.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for M_InventoryLine_HU
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("javadoc")
public class X_M_InventoryLine_HU extends org.compiere.model.PO implements I_M_InventoryLine_HU, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -2046922925L;

    /** Standard Constructor */
    public X_M_InventoryLine_HU (Properties ctx, int M_InventoryLine_HU_ID, String trxName)
    {
      super (ctx, M_InventoryLine_HU_ID, trxName);
    }

    /** Load Constructor */
    public X_M_InventoryLine_HU (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }


	/** Load Meta Data */
	@Override
	protected org.compiere.model.POInfo initPO(Properties ctx)
	{
		return org.compiere.model.POInfo.getPOInfo(Table_Name);
	}

	@Override
	public void setC_UOM_ID (int C_UOM_ID)
	{
		if (C_UOM_ID < 1) 
			set_Value (COLUMNNAME_C_UOM_ID, null);
		else 
			set_Value (COLUMNNAME_C_UOM_ID, Integer.valueOf(C_UOM_ID));
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
	public void setM_HU(de.metas.handlingunits.model.I_M_HU M_HU)
	{
		set_ValueFromPO(COLUMNNAME_M_HU_ID, de.metas.handlingunits.model.I_M_HU.class, M_HU);
	}

	@Override
	public void setM_HU_ID (int M_HU_ID)
	{
		if (M_HU_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_HU_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_HU_ID, Integer.valueOf(M_HU_ID));
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
	public void setM_Inventory(org.compiere.model.I_M_Inventory M_Inventory)
	{
		set_ValueFromPO(COLUMNNAME_M_Inventory_ID, org.compiere.model.I_M_Inventory.class, M_Inventory);
	}

	@Override
	public void setM_Inventory_ID (int M_Inventory_ID)
	{
		if (M_Inventory_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Inventory_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Inventory_ID, Integer.valueOf(M_Inventory_ID));
	}

	@Override
	public int getM_Inventory_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Inventory_ID);
	}

	@Override
	public void setM_InventoryLine_HU_ID (int M_InventoryLine_HU_ID)
	{
		if (M_InventoryLine_HU_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_InventoryLine_HU_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_InventoryLine_HU_ID, Integer.valueOf(M_InventoryLine_HU_ID));
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
	public void setM_InventoryLine(org.compiere.model.I_M_InventoryLine M_InventoryLine)
	{
		set_ValueFromPO(COLUMNNAME_M_InventoryLine_ID, org.compiere.model.I_M_InventoryLine.class, M_InventoryLine);
	}

	@Override
	public void setM_InventoryLine_ID (int M_InventoryLine_ID)
	{
		if (M_InventoryLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_InventoryLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_InventoryLine_ID, Integer.valueOf(M_InventoryLine_ID));
	}

	@Override
	public int getM_InventoryLine_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_InventoryLine_ID);
	}

	@Override
	public void setQtyBook (java.math.BigDecimal QtyBook)
	{
		set_Value (COLUMNNAME_QtyBook, QtyBook);
	}

	@Override
	public java.math.BigDecimal getQtyBook() 
	{
		BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyBook);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyCount (java.math.BigDecimal QtyCount)
	{
		set_Value (COLUMNNAME_QtyCount, QtyCount);
	}

	@Override
	public java.math.BigDecimal getQtyCount() 
	{
		BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyCount);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyInternalUse (java.math.BigDecimal QtyInternalUse)
	{
		set_Value (COLUMNNAME_QtyInternalUse, QtyInternalUse);
	}

	@Override
	public java.math.BigDecimal getQtyInternalUse() 
	{
		BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyInternalUse);
		return bd != null ? bd : BigDecimal.ZERO;
	}
}