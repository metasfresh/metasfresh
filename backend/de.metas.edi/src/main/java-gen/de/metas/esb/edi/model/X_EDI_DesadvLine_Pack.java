// Generated Model - DO NOT CHANGE
package de.metas.esb.edi.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for EDI_DesadvLine_Pack
 *  @author metasfresh (generated) 
 */
public class X_EDI_DesadvLine_Pack extends org.compiere.model.PO implements I_EDI_DesadvLine_Pack, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 1690473119L;

    /** Standard Constructor */
    public X_EDI_DesadvLine_Pack (final Properties ctx, final int EDI_DesadvLine_Pack_ID, @Nullable final String trxName)
    {
      super (ctx, EDI_DesadvLine_Pack_ID, trxName);
    }

    /** Load Constructor */
    public X_EDI_DesadvLine_Pack (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setBestBeforeDate (final java.sql.Timestamp BestBeforeDate)
	{
		set_Value (COLUMNNAME_BestBeforeDate, BestBeforeDate);
	}

	@Override
	public java.sql.Timestamp getBestBeforeDate() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_BestBeforeDate);
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
	public de.metas.esb.edi.model.I_EDI_Desadv getEDI_Desadv()
	{
		return get_ValueAsPO(COLUMNNAME_EDI_Desadv_ID, de.metas.esb.edi.model.I_EDI_Desadv.class);
	}

	@Override
	public void setEDI_Desadv(final de.metas.esb.edi.model.I_EDI_Desadv EDI_Desadv)
	{
		set_ValueFromPO(COLUMNNAME_EDI_Desadv_ID, de.metas.esb.edi.model.I_EDI_Desadv.class, EDI_Desadv);
	}

	@Override
	public void setEDI_Desadv_ID (final int EDI_Desadv_ID)
	{
		if (EDI_Desadv_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_EDI_Desadv_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_EDI_Desadv_ID, EDI_Desadv_ID);
	}

	@Override
	public int getEDI_Desadv_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_EDI_Desadv_ID);
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
	public void setEDI_DesadvLine_Pack_ID (final int EDI_DesadvLine_Pack_ID)
	{
		if (EDI_DesadvLine_Pack_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_EDI_DesadvLine_Pack_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_EDI_DesadvLine_Pack_ID, EDI_DesadvLine_Pack_ID);
	}

	@Override
	public int getEDI_DesadvLine_Pack_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_EDI_DesadvLine_Pack_ID);
	}

	@Override
	public void setGTIN_LU_PackingMaterial (final java.lang.String GTIN_LU_PackingMaterial)
	{
		set_Value (COLUMNNAME_GTIN_LU_PackingMaterial, GTIN_LU_PackingMaterial);
	}

	@Override
	public java.lang.String getGTIN_LU_PackingMaterial() 
	{
		return get_ValueAsString(COLUMNNAME_GTIN_LU_PackingMaterial);
	}

	@Override
	public void setGTIN_TU_PackingMaterial (final java.lang.String GTIN_TU_PackingMaterial)
	{
		set_Value (COLUMNNAME_GTIN_TU_PackingMaterial, GTIN_TU_PackingMaterial);
	}

	@Override
	public java.lang.String getGTIN_TU_PackingMaterial() 
	{
		return get_ValueAsString(COLUMNNAME_GTIN_TU_PackingMaterial);
	}

	@Override
	public void setIPA_SSCC18 (final java.lang.String IPA_SSCC18)
	{
		set_Value (COLUMNNAME_IPA_SSCC18, IPA_SSCC18);
	}

	@Override
	public java.lang.String getIPA_SSCC18() 
	{
		return get_ValueAsString(COLUMNNAME_IPA_SSCC18);
	}

	@Override
	public void setIsManual_IPA_SSCC18 (final boolean IsManual_IPA_SSCC18)
	{
		set_Value (COLUMNNAME_IsManual_IPA_SSCC18, IsManual_IPA_SSCC18);
	}

	@Override
	public boolean isManual_IPA_SSCC18() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsManual_IPA_SSCC18);
	}

	@Override
	public void setLotNumber (final java.lang.String LotNumber)
	{
		set_Value (COLUMNNAME_LotNumber, LotNumber);
	}

	@Override
	public java.lang.String getLotNumber() 
	{
		return get_ValueAsString(COLUMNNAME_LotNumber);
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
	public void setM_HU_PackagingCode_LU_ID (final int M_HU_PackagingCode_LU_ID)
	{
		if (M_HU_PackagingCode_LU_ID < 1) 
			set_Value (COLUMNNAME_M_HU_PackagingCode_LU_ID, null);
		else 
			set_Value (COLUMNNAME_M_HU_PackagingCode_LU_ID, M_HU_PackagingCode_LU_ID);
	}

	@Override
	public int getM_HU_PackagingCode_LU_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_HU_PackagingCode_LU_ID);
	}

	@Override
	public void setM_HU_PackagingCode_LU_Text (final java.lang.String M_HU_PackagingCode_LU_Text)
	{
		throw new IllegalArgumentException ("M_HU_PackagingCode_LU_Text is virtual column");	}

	@Override
	public java.lang.String getM_HU_PackagingCode_LU_Text() 
	{
		return get_ValueAsString(COLUMNNAME_M_HU_PackagingCode_LU_Text);
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
	public void setM_HU_PackagingCode_TU_Text (final java.lang.String M_HU_PackagingCode_TU_Text)
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
	public void setQtyCU (final BigDecimal QtyCU)
	{
		set_Value (COLUMNNAME_QtyCU, QtyCU);
	}

	@Override
	public BigDecimal getQtyCU() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyCU);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyCUsPerLU (final BigDecimal QtyCUsPerLU)
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
	public void setQtyItemCapacity (final BigDecimal QtyItemCapacity)
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