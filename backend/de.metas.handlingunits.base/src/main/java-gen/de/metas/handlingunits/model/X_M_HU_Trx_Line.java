// Generated Model - DO NOT CHANGE
package de.metas.handlingunits.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for M_HU_Trx_Line
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_M_HU_Trx_Line extends org.compiere.model.PO implements I_M_HU_Trx_Line, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 1416206995L;

    /** Standard Constructor */
    public X_M_HU_Trx_Line (final Properties ctx, final int M_HU_Trx_Line_ID, @Nullable final String trxName)
    {
      super (ctx, M_HU_Trx_Line_ID, trxName);
    }

    /** Load Constructor */
    public X_M_HU_Trx_Line (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setAD_Table_ID (final int AD_Table_ID)
	{
		if (AD_Table_ID < 1) 
			set_Value (COLUMNNAME_AD_Table_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Table_ID, AD_Table_ID);
	}

	@Override
	public int getAD_Table_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Table_ID);
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
	public void setDateTrx (final java.sql.Timestamp DateTrx)
	{
		set_Value (COLUMNNAME_DateTrx, DateTrx);
	}

	@Override
	public java.sql.Timestamp getDateTrx() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DateTrx);
	}

	/** 
	 * HUStatus AD_Reference_ID=540478
	 * Reference name: HUStatus
	 */
	public static final int HUSTATUS_AD_Reference_ID=540478;
	/** Planning = P */
	public static final String HUSTATUS_Planning = "P";
	/** Active = A */
	public static final String HUSTATUS_Active = "A";
	/** Destroyed = D */
	public static final String HUSTATUS_Destroyed = "D";
	/** Picked = S */
	public static final String HUSTATUS_Picked = "S";
	/** Shipped = E */
	public static final String HUSTATUS_Shipped = "E";
	/** Issued = I */
	public static final String HUSTATUS_Issued = "I";
	@Override
	public void setHUStatus (final @Nullable java.lang.String HUStatus)
	{
		set_Value (COLUMNNAME_HUStatus, HUStatus);
	}

	@Override
	public java.lang.String getHUStatus() 
	{
		return get_ValueAsString(COLUMNNAME_HUStatus);
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
			set_Value (COLUMNNAME_M_HU_ID, null);
		else 
			set_Value (COLUMNNAME_M_HU_ID, M_HU_ID);
	}

	@Override
	public int getM_HU_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_HU_ID);
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
			set_Value (COLUMNNAME_M_HU_Item_ID, null);
		else 
			set_Value (COLUMNNAME_M_HU_Item_ID, M_HU_Item_ID);
	}

	@Override
	public int getM_HU_Item_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_HU_Item_ID);
	}

	@Override
	public de.metas.handlingunits.model.I_M_HU_Trx_Hdr getM_HU_Trx_Hdr()
	{
		return get_ValueAsPO(COLUMNNAME_M_HU_Trx_Hdr_ID, de.metas.handlingunits.model.I_M_HU_Trx_Hdr.class);
	}

	@Override
	public void setM_HU_Trx_Hdr(final de.metas.handlingunits.model.I_M_HU_Trx_Hdr M_HU_Trx_Hdr)
	{
		set_ValueFromPO(COLUMNNAME_M_HU_Trx_Hdr_ID, de.metas.handlingunits.model.I_M_HU_Trx_Hdr.class, M_HU_Trx_Hdr);
	}

	@Override
	public void setM_HU_Trx_Hdr_ID (final int M_HU_Trx_Hdr_ID)
	{
		if (M_HU_Trx_Hdr_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_HU_Trx_Hdr_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_HU_Trx_Hdr_ID, M_HU_Trx_Hdr_ID);
	}

	@Override
	public int getM_HU_Trx_Hdr_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_HU_Trx_Hdr_ID);
	}

	@Override
	public void setM_HU_Trx_Line_ID (final int M_HU_Trx_Line_ID)
	{
		if (M_HU_Trx_Line_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_HU_Trx_Line_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_HU_Trx_Line_ID, M_HU_Trx_Line_ID);
	}

	@Override
	public int getM_HU_Trx_Line_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_HU_Trx_Line_ID);
	}

	@Override
	public void setM_Locator_ID (final int M_Locator_ID)
	{
		if (M_Locator_ID < 1) 
			set_Value (COLUMNNAME_M_Locator_ID, null);
		else 
			set_Value (COLUMNNAME_M_Locator_ID, M_Locator_ID);
	}

	@Override
	public int getM_Locator_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Locator_ID);
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
	public de.metas.handlingunits.model.I_M_HU_Trx_Line getParent_HU_Trx_Line()
	{
		return get_ValueAsPO(COLUMNNAME_Parent_HU_Trx_Line_ID, de.metas.handlingunits.model.I_M_HU_Trx_Line.class);
	}

	@Override
	public void setParent_HU_Trx_Line(final de.metas.handlingunits.model.I_M_HU_Trx_Line Parent_HU_Trx_Line)
	{
		set_ValueFromPO(COLUMNNAME_Parent_HU_Trx_Line_ID, de.metas.handlingunits.model.I_M_HU_Trx_Line.class, Parent_HU_Trx_Line);
	}

	@Override
	public void setParent_HU_Trx_Line_ID (final int Parent_HU_Trx_Line_ID)
	{
		if (Parent_HU_Trx_Line_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Parent_HU_Trx_Line_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Parent_HU_Trx_Line_ID, Parent_HU_Trx_Line_ID);
	}

	@Override
	public int getParent_HU_Trx_Line_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Parent_HU_Trx_Line_ID);
	}

	@Override
	public void setProcessed (final boolean Processed)
	{
		set_Value (COLUMNNAME_Processed, Processed);
	}

	@Override
	public boolean isProcessed() 
	{
		return get_ValueAsBoolean(COLUMNNAME_Processed);
	}

	@Override
	public void setQty (final @Nullable BigDecimal Qty)
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
	public void setRecord_ID (final int Record_ID)
	{
		if (Record_ID < 0) 
			set_Value (COLUMNNAME_Record_ID, null);
		else 
			set_Value (COLUMNNAME_Record_ID, Record_ID);
	}

	@Override
	public int getRecord_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Record_ID);
	}

	@Override
	public de.metas.handlingunits.model.I_M_HU_Trx_Line getReversalLine()
	{
		return get_ValueAsPO(COLUMNNAME_ReversalLine_ID, de.metas.handlingunits.model.I_M_HU_Trx_Line.class);
	}

	@Override
	public void setReversalLine(final de.metas.handlingunits.model.I_M_HU_Trx_Line ReversalLine)
	{
		set_ValueFromPO(COLUMNNAME_ReversalLine_ID, de.metas.handlingunits.model.I_M_HU_Trx_Line.class, ReversalLine);
	}

	@Override
	public void setReversalLine_ID (final int ReversalLine_ID)
	{
		if (ReversalLine_ID < 1) 
			set_Value (COLUMNNAME_ReversalLine_ID, null);
		else 
			set_Value (COLUMNNAME_ReversalLine_ID, ReversalLine_ID);
	}

	@Override
	public int getReversalLine_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_ReversalLine_ID);
	}

	@Override
	public de.metas.handlingunits.model.I_M_HU_Item getVHU_Item()
	{
		return get_ValueAsPO(COLUMNNAME_VHU_Item_ID, de.metas.handlingunits.model.I_M_HU_Item.class);
	}

	@Override
	public void setVHU_Item(final de.metas.handlingunits.model.I_M_HU_Item VHU_Item)
	{
		set_ValueFromPO(COLUMNNAME_VHU_Item_ID, de.metas.handlingunits.model.I_M_HU_Item.class, VHU_Item);
	}

	@Override
	public void setVHU_Item_ID (final int VHU_Item_ID)
	{
		if (VHU_Item_ID < 1) 
			set_Value (COLUMNNAME_VHU_Item_ID, null);
		else 
			set_Value (COLUMNNAME_VHU_Item_ID, VHU_Item_ID);
	}

	@Override
	public int getVHU_Item_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_VHU_Item_ID);
	}
}