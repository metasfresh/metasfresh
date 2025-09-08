// Generated Model - DO NOT CHANGE
package de.metas.esb.edi.model;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for M_InOut_Desadv_V
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_M_InOut_Desadv_V extends org.compiere.model.PO implements I_M_InOut_Desadv_V, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -610049738L;

    /** Standard Constructor */
    public X_M_InOut_Desadv_V (final Properties ctx, final int M_InOut_Desadv_V_ID, @Nullable final String trxName)
    {
      super (ctx, M_InOut_Desadv_V_ID, trxName);
    }

    /** Load Constructor */
    public X_M_InOut_Desadv_V (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setBill_Location_ID (final int Bill_Location_ID)
	{
		if (Bill_Location_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Bill_Location_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Bill_Location_ID, Bill_Location_ID);
	}

	@Override
	public int getBill_Location_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Bill_Location_ID);
	}

	@Override
	public void setC_BPartner_ID (final int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, C_BPartner_ID);
	}

	@Override
	public int getC_BPartner_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_ID);
	}

	@Override
	public void setC_BPartner_Location_ID (final int C_BPartner_Location_ID)
	{
		if (C_BPartner_Location_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_Location_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_Location_ID, C_BPartner_Location_ID);
	}

	@Override
	public int getC_BPartner_Location_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_Location_ID);
	}

	@Override
	public void setC_Currency_ID (final int C_Currency_ID)
	{
		if (C_Currency_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Currency_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Currency_ID, C_Currency_ID);
	}

	@Override
	public int getC_Currency_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Currency_ID);
	}

	@Override
	public void setDateOrdered (final @Nullable java.sql.Timestamp DateOrdered)
	{
		set_ValueNoCheck (COLUMNNAME_DateOrdered, DateOrdered);
	}

	@Override
	public java.sql.Timestamp getDateOrdered() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DateOrdered);
	}

	@Override
	public void setDeliveryViaRule (final @Nullable java.lang.String DeliveryViaRule)
	{
		set_ValueNoCheck (COLUMNNAME_DeliveryViaRule, DeliveryViaRule);
	}

	@Override
	public java.lang.String getDeliveryViaRule() 
	{
		return get_ValueAsString(COLUMNNAME_DeliveryViaRule);
	}

	@Override
	public void setDocumentNo (final @Nullable java.lang.String DocumentNo)
	{
		set_ValueNoCheck (COLUMNNAME_DocumentNo, DocumentNo);
	}

	@Override
	public java.lang.String getDocumentNo() 
	{
		return get_ValueAsString(COLUMNNAME_DocumentNo);
	}

	@Override
	public void setDropShip_BPartner_ID (final int DropShip_BPartner_ID)
	{
		if (DropShip_BPartner_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_DropShip_BPartner_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_DropShip_BPartner_ID, DropShip_BPartner_ID);
	}

	@Override
	public int getDropShip_BPartner_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_DropShip_BPartner_ID);
	}

	@Override
	public void setDropShip_Location_ID (final int DropShip_Location_ID)
	{
		if (DropShip_Location_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_DropShip_Location_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_DropShip_Location_ID, DropShip_Location_ID);
	}

	@Override
	public int getDropShip_Location_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_DropShip_Location_ID);
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
	public void setEDIErrorMsg (final @Nullable java.lang.String EDIErrorMsg)
	{
		set_ValueNoCheck (COLUMNNAME_EDIErrorMsg, EDIErrorMsg);
	}

	@Override
	public java.lang.String getEDIErrorMsg() 
	{
		return get_ValueAsString(COLUMNNAME_EDIErrorMsg);
	}

	@Override
	public void setEDI_ExportStatus (final @Nullable java.lang.String EDI_ExportStatus)
	{
		set_ValueNoCheck (COLUMNNAME_EDI_ExportStatus, EDI_ExportStatus);
	}

	@Override
	public java.lang.String getEDI_ExportStatus() 
	{
		return get_ValueAsString(COLUMNNAME_EDI_ExportStatus);
	}

	@Override
	public void setFulfillmentPercent (final @Nullable BigDecimal FulfillmentPercent)
	{
		set_ValueNoCheck (COLUMNNAME_FulfillmentPercent, FulfillmentPercent);
	}

	@Override
	public BigDecimal getFulfillmentPercent() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_FulfillmentPercent);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setFulfillmentPercentMin (final @Nullable BigDecimal FulfillmentPercentMin)
	{
		set_ValueNoCheck (COLUMNNAME_FulfillmentPercentMin, FulfillmentPercentMin);
	}

	@Override
	public BigDecimal getFulfillmentPercentMin() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_FulfillmentPercentMin);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setHandOver_Location_ID (final int HandOver_Location_ID)
	{
		if (HandOver_Location_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_HandOver_Location_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_HandOver_Location_ID, HandOver_Location_ID);
	}

	@Override
	public int getHandOver_Location_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_HandOver_Location_ID);
	}

	@Override
	public void setHandOver_Partner_ID (final int HandOver_Partner_ID)
	{
		if (HandOver_Partner_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_HandOver_Partner_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_HandOver_Partner_ID, HandOver_Partner_ID);
	}

	@Override
	public int getHandOver_Partner_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_HandOver_Partner_ID);
	}

	@Override
	public void setInvoicableQtyBasedOn (final @Nullable java.lang.String InvoicableQtyBasedOn)
	{
		set_ValueNoCheck (COLUMNNAME_InvoicableQtyBasedOn, InvoicableQtyBasedOn);
	}

	@Override
	public java.lang.String getInvoicableQtyBasedOn() 
	{
		return get_ValueAsString(COLUMNNAME_InvoicableQtyBasedOn);
	}

	@Override
	public void setM_InOut_ID (final int M_InOut_ID)
	{
		if (M_InOut_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_InOut_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_InOut_ID, M_InOut_ID);
	}

	@Override
	public int getM_InOut_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_InOut_ID);
	}

	@Override
	public void setMovementDate (final @Nullable java.sql.Timestamp MovementDate)
	{
		set_ValueNoCheck (COLUMNNAME_MovementDate, MovementDate);
	}

	@Override
	public java.sql.Timestamp getMovementDate() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_MovementDate);
	}

	@Override
	public void setPOReference (final @Nullable java.lang.String POReference)
	{
		set_ValueNoCheck (COLUMNNAME_POReference, POReference);
	}

	@Override
	public java.lang.String getPOReference() 
	{
		return get_ValueAsString(COLUMNNAME_POReference);
	}

	@Override
	public void setProcessed (final boolean Processed)
	{
		set_ValueNoCheck (COLUMNNAME_Processed, Processed);
	}

	@Override
	public boolean isProcessed() 
	{
		return get_ValueAsBoolean(COLUMNNAME_Processed);
	}

	@Override
	public void setProcessing (final boolean Processing)
	{
		set_ValueNoCheck (COLUMNNAME_Processing, Processing);
	}

	@Override
	public boolean isProcessing() 
	{
		return get_ValueAsBoolean(COLUMNNAME_Processing);
	}

	@Override
	public void setSumDeliveredInStockingUOM (final @Nullable BigDecimal SumDeliveredInStockingUOM)
	{
		set_ValueNoCheck (COLUMNNAME_SumDeliveredInStockingUOM, SumDeliveredInStockingUOM);
	}

	@Override
	public BigDecimal getSumDeliveredInStockingUOM() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_SumDeliveredInStockingUOM);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setSumOrderedInStockingUOM (final @Nullable BigDecimal SumOrderedInStockingUOM)
	{
		set_ValueNoCheck (COLUMNNAME_SumOrderedInStockingUOM, SumOrderedInStockingUOM);
	}

	@Override
	public BigDecimal getSumOrderedInStockingUOM() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_SumOrderedInStockingUOM);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setUserFlag (final @Nullable java.lang.String UserFlag)
	{
		set_ValueNoCheck (COLUMNNAME_UserFlag, UserFlag);
	}

	@Override
	public java.lang.String getUserFlag() 
	{
		return get_ValueAsString(COLUMNNAME_UserFlag);
	}
}