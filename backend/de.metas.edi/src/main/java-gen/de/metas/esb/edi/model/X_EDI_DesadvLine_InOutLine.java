// Generated Model - DO NOT CHANGE
package de.metas.esb.edi.model;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for EDI_DesadvLine_InOutLine
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_EDI_DesadvLine_InOutLine extends org.compiere.model.PO implements I_EDI_DesadvLine_InOutLine, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1582274999L;

    /** Standard Constructor */
    public X_EDI_DesadvLine_InOutLine (final Properties ctx, final int EDI_DesadvLine_InOutLine_ID, @Nullable final String trxName)
    {
      super (ctx, EDI_DesadvLine_InOutLine_ID, trxName);
    }

    /** Load Constructor */
    public X_EDI_DesadvLine_InOutLine (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setC_UOM_BPartner_ID (final int C_UOM_BPartner_ID)
	{
		if (C_UOM_BPartner_ID < 1) 
			set_Value (COLUMNNAME_C_UOM_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_C_UOM_BPartner_ID, C_UOM_BPartner_ID);
	}

	@Override
	public int getC_UOM_BPartner_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_UOM_BPartner_ID);
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
	public void setC_UOM_Invoice_ID (final int C_UOM_Invoice_ID)
	{
		if (C_UOM_Invoice_ID < 1) 
			set_Value (COLUMNNAME_C_UOM_Invoice_ID, null);
		else 
			set_Value (COLUMNNAME_C_UOM_Invoice_ID, C_UOM_Invoice_ID);
	}

	@Override
	public int getC_UOM_Invoice_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_UOM_Invoice_ID);
	}

	@Override
	public I_EDI_DesadvLine getEDI_DesadvLine()
	{
		return get_ValueAsPO(COLUMNNAME_EDI_DesadvLine_ID, I_EDI_DesadvLine.class);
	}

	@Override
	public void setEDI_DesadvLine(final I_EDI_DesadvLine EDI_DesadvLine)
	{
		set_ValueFromPO(COLUMNNAME_EDI_DesadvLine_ID, I_EDI_DesadvLine.class, EDI_DesadvLine);
	}

	@Override
	public void setEDI_DesadvLine_ID (final int EDI_DesadvLine_ID)
	{
		if (EDI_DesadvLine_ID < 1) 
			set_Value (COLUMNNAME_EDI_DesadvLine_ID, null);
		else 
			set_Value (COLUMNNAME_EDI_DesadvLine_ID, EDI_DesadvLine_ID);
	}

	@Override
	public int getEDI_DesadvLine_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_EDI_DesadvLine_ID);
	}

	@Override
	public void setEDI_DesadvLine_InOutLine_ID (final int EDI_DesadvLine_InOutLine_ID)
	{
		if (EDI_DesadvLine_InOutLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_EDI_DesadvLine_InOutLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_EDI_DesadvLine_InOutLine_ID, EDI_DesadvLine_InOutLine_ID);
	}

	@Override
	public int getEDI_DesadvLine_InOutLine_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_EDI_DesadvLine_InOutLine_ID);
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
	public void setQtyDeliveredInInvoiceUOM (final @Nullable BigDecimal QtyDeliveredInInvoiceUOM)
	{
		set_Value (COLUMNNAME_QtyDeliveredInInvoiceUOM, QtyDeliveredInInvoiceUOM);
	}

	@Override
	public BigDecimal getQtyDeliveredInInvoiceUOM() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyDeliveredInInvoiceUOM);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyDeliveredInStockingUOM (final BigDecimal QtyDeliveredInStockingUOM)
	{
		set_Value (COLUMNNAME_QtyDeliveredInStockingUOM, QtyDeliveredInStockingUOM);
	}

	@Override
	public BigDecimal getQtyDeliveredInStockingUOM() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyDeliveredInStockingUOM);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyDeliveredInUOM (final BigDecimal QtyDeliveredInUOM)
	{
		set_Value (COLUMNNAME_QtyDeliveredInUOM, QtyDeliveredInUOM);
	}

	@Override
	public BigDecimal getQtyDeliveredInUOM() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyDeliveredInUOM);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyEnteredInBPartnerUOM (final @Nullable BigDecimal QtyEnteredInBPartnerUOM)
	{
		set_Value (COLUMNNAME_QtyEnteredInBPartnerUOM, QtyEnteredInBPartnerUOM);
	}

	@Override
	public BigDecimal getQtyEnteredInBPartnerUOM() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyEnteredInBPartnerUOM);
		return bd != null ? bd : BigDecimal.ZERO;
	}
}