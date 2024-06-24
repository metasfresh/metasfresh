// Generated Model - DO NOT CHANGE
package de.metas.esb.edi.model;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for EDI_Desadv_Pack_Item
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_EDI_Desadv_Pack_Item extends org.compiere.model.PO implements I_EDI_Desadv_Pack_Item, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -354804449L;

    /** Standard Constructor */
    public X_EDI_Desadv_Pack_Item (final Properties ctx, final int EDI_Desadv_Pack_Item_ID, @Nullable final String trxName)
    {
      super (ctx, EDI_Desadv_Pack_Item_ID, trxName);
    }

    /** Load Constructor */
    public X_EDI_Desadv_Pack_Item (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setBestBeforeDate (final @Nullable java.sql.Timestamp BestBeforeDate)
	{
		set_Value (COLUMNNAME_BestBeforeDate, BestBeforeDate);
	}

	@Override
	public java.sql.Timestamp getBestBeforeDate() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_BestBeforeDate);
	}

	@Override
	public de.metas.esb.edi.model.I_EDI_DesadvLine getEDI_DesadvLine()
	{
		return get_ValueAsPO(COLUMNNAME_EDI_DesadvLine_ID, de.metas.esb.edi.model.I_EDI_DesadvLine.class);
	}

	@Override
	public void setEDI_DesadvLine(final de.metas.esb.edi.model.I_EDI_DesadvLine EDI_DesadvLine)
	{
		set_ValueFromPO(COLUMNNAME_EDI_DesadvLine_ID, de.metas.esb.edi.model.I_EDI_DesadvLine.class, EDI_DesadvLine);
	}

	@Override
	public void setEDI_DesadvLine_ID (final int EDI_DesadvLine_ID)
	{
		if (EDI_DesadvLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_EDI_DesadvLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_EDI_DesadvLine_ID, EDI_DesadvLine_ID);
	}

	@Override
	public int getEDI_DesadvLine_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_EDI_DesadvLine_ID);
	}

	@Override
	public de.metas.esb.edi.model.I_EDI_Desadv_Pack getEDI_Desadv_Pack()
	{
		return get_ValueAsPO(COLUMNNAME_EDI_Desadv_Pack_ID, de.metas.esb.edi.model.I_EDI_Desadv_Pack.class);
	}

	@Override
	public void setEDI_Desadv_Pack(final de.metas.esb.edi.model.I_EDI_Desadv_Pack EDI_Desadv_Pack)
	{
		set_ValueFromPO(COLUMNNAME_EDI_Desadv_Pack_ID, de.metas.esb.edi.model.I_EDI_Desadv_Pack.class, EDI_Desadv_Pack);
	}

	@Override
	public void setEDI_Desadv_Pack_ID (final int EDI_Desadv_Pack_ID)
	{
		if (EDI_Desadv_Pack_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_EDI_Desadv_Pack_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_EDI_Desadv_Pack_ID, EDI_Desadv_Pack_ID);
	}

	@Override
	public int getEDI_Desadv_Pack_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_EDI_Desadv_Pack_ID);
	}

	@Override
	public void setEDI_Desadv_Pack_Item_ID (final int EDI_Desadv_Pack_Item_ID)
	{
		if (EDI_Desadv_Pack_Item_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_EDI_Desadv_Pack_Item_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_EDI_Desadv_Pack_Item_ID, EDI_Desadv_Pack_Item_ID);
	}

	@Override
	public int getEDI_Desadv_Pack_Item_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_EDI_Desadv_Pack_Item_ID);
	}

	@Override
	public void setGTIN_TU_PackingMaterial (final @Nullable java.lang.String GTIN_TU_PackingMaterial)
	{
		set_Value (COLUMNNAME_GTIN_TU_PackingMaterial, GTIN_TU_PackingMaterial);
	}

	@Override
	public java.lang.String getGTIN_TU_PackingMaterial() 
	{
		return get_ValueAsString(COLUMNNAME_GTIN_TU_PackingMaterial);
	}

	@Override
	public void setLotNumber (final @Nullable java.lang.String LotNumber)
	{
		set_Value (COLUMNNAME_LotNumber, LotNumber);
	}

	@Override
	public java.lang.String getLotNumber() 
	{
		return get_ValueAsString(COLUMNNAME_LotNumber);
	}

	@Override
	public void setM_HU_PackagingCode_TU_ID (final int M_HU_PackagingCode_TU_ID)
	{
		if (M_HU_PackagingCode_TU_ID < 1) 
			set_Value (COLUMNNAME_M_HU_PackagingCode_TU_ID, null);
		else 
			set_Value (COLUMNNAME_M_HU_PackagingCode_TU_ID, M_HU_PackagingCode_TU_ID);
	}

	@Override
	public int getM_HU_PackagingCode_TU_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_HU_PackagingCode_TU_ID);
	}

	@Override
	public void setM_HU_PackagingCode_TU_Text (final @Nullable java.lang.String M_HU_PackagingCode_TU_Text)
	{
		throw new IllegalArgumentException ("M_HU_PackagingCode_TU_Text is virtual column");	}

	@Override
	public java.lang.String getM_HU_PackagingCode_TU_Text() 
	{
		return get_ValueAsString(COLUMNNAME_M_HU_PackagingCode_TU_Text);
	}

	@Override
	public org.compiere.model.I_M_InOut getM_InOut()
	{
		return get_ValueAsPO(COLUMNNAME_M_InOut_ID, org.compiere.model.I_M_InOut.class);
	}

	@Override
	public void setM_InOut(final org.compiere.model.I_M_InOut M_InOut)
	{
		set_ValueFromPO(COLUMNNAME_M_InOut_ID, org.compiere.model.I_M_InOut.class, M_InOut);
	}

	@Override
	public void setM_InOut_ID (final int M_InOut_ID)
	{
		if (M_InOut_ID < 1) 
			set_Value (COLUMNNAME_M_InOut_ID, null);
		else 
			set_Value (COLUMNNAME_M_InOut_ID, M_InOut_ID);
	}

	@Override
	public int getM_InOut_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_InOut_ID);
	}

	@Override
	public org.compiere.model.I_M_InOutLine getM_InOutLine()
	{
		return get_ValueAsPO(COLUMNNAME_M_InOutLine_ID, org.compiere.model.I_M_InOutLine.class);
	}

	@Override
	public void setM_InOutLine(final org.compiere.model.I_M_InOutLine M_InOutLine)
	{
		set_ValueFromPO(COLUMNNAME_M_InOutLine_ID, org.compiere.model.I_M_InOutLine.class, M_InOutLine);
	}

	@Override
	public void setM_InOutLine_ID (final int M_InOutLine_ID)
	{
		if (M_InOutLine_ID < 1) 
			set_Value (COLUMNNAME_M_InOutLine_ID, null);
		else 
			set_Value (COLUMNNAME_M_InOutLine_ID, M_InOutLine_ID);
	}

	@Override
	public int getM_InOutLine_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_InOutLine_ID);
	}

	@Override
	public void setMovementQty (final BigDecimal MovementQty)
	{
		set_Value (COLUMNNAME_MovementQty, MovementQty);
	}

	@Override
	public BigDecimal getMovementQty() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_MovementQty);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyCUsPerLU (final @Nullable BigDecimal QtyCUsPerLU)
	{
		set_Value (COLUMNNAME_QtyCUsPerLU, QtyCUsPerLU);
	}

	@Override
	public BigDecimal getQtyCUsPerLU() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyCUsPerLU);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyCUsPerLU_InInvoiceUOM (final @Nullable BigDecimal QtyCUsPerLU_InInvoiceUOM)
	{
		set_Value (COLUMNNAME_QtyCUsPerLU_InInvoiceUOM, QtyCUsPerLU_InInvoiceUOM);
	}

	@Override
	public BigDecimal getQtyCUsPerLU_InInvoiceUOM() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyCUsPerLU_InInvoiceUOM);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyCUsPerTU (final @Nullable BigDecimal QtyCUsPerTU)
	{
		set_Value (COLUMNNAME_QtyCUsPerTU, QtyCUsPerTU);
	}

	@Override
	public BigDecimal getQtyCUsPerTU() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyCUsPerTU);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyCUsPerTU_InInvoiceUOM (final @Nullable BigDecimal QtyCUsPerTU_InInvoiceUOM)
	{
		set_Value (COLUMNNAME_QtyCUsPerTU_InInvoiceUOM, QtyCUsPerTU_InInvoiceUOM);
	}

	@Override
	public BigDecimal getQtyCUsPerTU_InInvoiceUOM() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyCUsPerTU_InInvoiceUOM);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyItemCapacity (final @Nullable BigDecimal QtyItemCapacity)
	{
		set_Value (COLUMNNAME_QtyItemCapacity, QtyItemCapacity);
	}

	@Override
	public BigDecimal getQtyItemCapacity() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyItemCapacity);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyTU (final int QtyTU)
	{
		set_Value (COLUMNNAME_QtyTU, QtyTU);
	}

	@Override
	public int getQtyTU() 
	{
		return get_ValueAsInt(COLUMNNAME_QtyTU);
	}
}