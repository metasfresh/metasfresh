/** Generated Model - DO NOT CHANGE */
package de.metas.esb.edi.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for EDI_Desadv
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("javadoc")
public class X_EDI_Desadv extends org.compiere.model.PO implements I_EDI_Desadv, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1216478244L;

    /** Standard Constructor */
    public X_EDI_Desadv (Properties ctx, int EDI_Desadv_ID, String trxName)
    {
      super (ctx, EDI_Desadv_ID, trxName);
    }

    /** Load Constructor */
    public X_EDI_Desadv (Properties ctx, ResultSet rs, String trxName)
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
	public void setBill_Location_ID (int Bill_Location_ID)
	{
		if (Bill_Location_ID < 1) 
			set_Value (COLUMNNAME_Bill_Location_ID, null);
		else 
			set_Value (COLUMNNAME_Bill_Location_ID, Integer.valueOf(Bill_Location_ID));
	}

	@Override
	public int getBill_Location_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Bill_Location_ID);
	}

	@Override
	public void setC_BPartner_ID (int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
	}

	@Override
	public int getC_BPartner_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_ID);
	}

	@Override
	public void setC_BPartner_Location_ID (int C_BPartner_Location_ID)
	{
		if (C_BPartner_Location_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_Location_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_Location_ID, Integer.valueOf(C_BPartner_Location_ID));
	}

	@Override
	public int getC_BPartner_Location_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_Location_ID);
	}

	@Override
	public void setC_Currency_ID (int C_Currency_ID)
	{
		if (C_Currency_ID < 1) 
			set_Value (COLUMNNAME_C_Currency_ID, null);
		else 
			set_Value (COLUMNNAME_C_Currency_ID, Integer.valueOf(C_Currency_ID));
	}

	@Override
	public int getC_Currency_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Currency_ID);
	}

	@Override
	public void setDateOrdered (java.sql.Timestamp DateOrdered)
	{
		set_Value (COLUMNNAME_DateOrdered, DateOrdered);
	}

	@Override
	public java.sql.Timestamp getDateOrdered() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DateOrdered);
	}

	@Override
	public void setDocumentNo (java.lang.String DocumentNo)
	{
		set_Value (COLUMNNAME_DocumentNo, DocumentNo);
	}

	@Override
	public java.lang.String getDocumentNo() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DocumentNo);
	}

	@Override
	public void setDropShip_BPartner_ID (int DropShip_BPartner_ID)
	{
		if (DropShip_BPartner_ID < 1) 
			set_Value (COLUMNNAME_DropShip_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_DropShip_BPartner_ID, Integer.valueOf(DropShip_BPartner_ID));
	}

	@Override
	public int getDropShip_BPartner_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_DropShip_BPartner_ID);
	}

	@Override
	public void setDropShip_Location_ID (int DropShip_Location_ID)
	{
		if (DropShip_Location_ID < 1) 
			set_Value (COLUMNNAME_DropShip_Location_ID, null);
		else 
			set_Value (COLUMNNAME_DropShip_Location_ID, Integer.valueOf(DropShip_Location_ID));
	}

	@Override
	public int getDropShip_Location_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_DropShip_Location_ID);
	}

	@Override
	public void setEDI_Desadv_ID (int EDI_Desadv_ID)
	{
		if (EDI_Desadv_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_EDI_Desadv_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_EDI_Desadv_ID, Integer.valueOf(EDI_Desadv_ID));
	}

	@Override
	public int getEDI_Desadv_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_EDI_Desadv_ID);
	}

	@Override
	public void setEDIErrorMsg (java.lang.String EDIErrorMsg)
	{
		set_Value (COLUMNNAME_EDIErrorMsg, EDIErrorMsg);
	}

	@Override
	public java.lang.String getEDIErrorMsg() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_EDIErrorMsg);
	}

	/** 
	 * EDI_ExportStatus AD_Reference_ID=540381
	 * Reference name: EDI_ExportStatus
	 */
	public static final int EDI_EXPORTSTATUS_AD_Reference_ID=540381;
	/** Invalid = I */
	public static final String EDI_EXPORTSTATUS_Invalid = "I";
	/** Pending = P */
	public static final String EDI_EXPORTSTATUS_Pending = "P";
	/** Sent = S */
	public static final String EDI_EXPORTSTATUS_Sent = "S";
	/** SendingStarted = D */
	public static final String EDI_EXPORTSTATUS_SendingStarted = "D";
	/** Error = E */
	public static final String EDI_EXPORTSTATUS_Error = "E";
	/** Enqueued = U */
	public static final String EDI_EXPORTSTATUS_Enqueued = "U";
	/** DontSend = N */
	public static final String EDI_EXPORTSTATUS_DontSend = "N";
	@Override
	public void setEDI_ExportStatus (java.lang.String EDI_ExportStatus)
	{

		set_Value (COLUMNNAME_EDI_ExportStatus, EDI_ExportStatus);
	}

	@Override
	public java.lang.String getEDI_ExportStatus() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_EDI_ExportStatus);
	}

	@Override
	public void setFulfillmentPercent (java.math.BigDecimal FulfillmentPercent)
	{
		set_ValueNoCheck (COLUMNNAME_FulfillmentPercent, FulfillmentPercent);
	}

	@Override
	public java.math.BigDecimal getFulfillmentPercent() 
	{
		BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_FulfillmentPercent);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setFulfillmentPercentMin (java.math.BigDecimal FulfillmentPercentMin)
	{
		set_Value (COLUMNNAME_FulfillmentPercentMin, FulfillmentPercentMin);
	}

	@Override
	public java.math.BigDecimal getFulfillmentPercentMin() 
	{
		BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_FulfillmentPercentMin);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setHandOver_Location_ID (int HandOver_Location_ID)
	{
		if (HandOver_Location_ID < 1) 
			set_Value (COLUMNNAME_HandOver_Location_ID, null);
		else 
			set_Value (COLUMNNAME_HandOver_Location_ID, Integer.valueOf(HandOver_Location_ID));
	}

	@Override
	public int getHandOver_Location_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_HandOver_Location_ID);
	}

	@Override
	public void setHandOver_Partner_ID (int HandOver_Partner_ID)
	{
		if (HandOver_Partner_ID < 1) 
			set_Value (COLUMNNAME_HandOver_Partner_ID, null);
		else 
			set_Value (COLUMNNAME_HandOver_Partner_ID, Integer.valueOf(HandOver_Partner_ID));
	}

	@Override
	public int getHandOver_Partner_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_HandOver_Partner_ID);
	}

	@Override
	public void setMovementDate (java.sql.Timestamp MovementDate)
	{
		set_Value (COLUMNNAME_MovementDate, MovementDate);
	}

	@Override
	public java.sql.Timestamp getMovementDate() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_MovementDate);
	}

	@Override
	public void setPOReference (java.lang.String POReference)
	{
		set_Value (COLUMNNAME_POReference, POReference);
	}

	@Override
	public java.lang.String getPOReference() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_POReference);
	}

	@Override
	public void setProcessed (boolean Processed)
	{
		set_Value (COLUMNNAME_Processed, Boolean.valueOf(Processed));
	}

	@Override
	public boolean isProcessed() 
	{
		return get_ValueAsBoolean(COLUMNNAME_Processed);
	}

	@Override
	public void setProcessing (boolean Processing)
	{
		set_Value (COLUMNNAME_Processing, Boolean.valueOf(Processing));
	}

	@Override
	public boolean isProcessing() 
	{
		return get_ValueAsBoolean(COLUMNNAME_Processing);
	}

	@Override
	public void setSumDeliveredInStockingUOM (java.math.BigDecimal SumDeliveredInStockingUOM)
	{
		set_Value (COLUMNNAME_SumDeliveredInStockingUOM, SumDeliveredInStockingUOM);
	}

	@Override
	public java.math.BigDecimal getSumDeliveredInStockingUOM() 
	{
		BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_SumDeliveredInStockingUOM);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setSumOrderedInStockingUOM (java.math.BigDecimal SumOrderedInStockingUOM)
	{
		set_Value (COLUMNNAME_SumOrderedInStockingUOM, SumOrderedInStockingUOM);
	}

	@Override
	public java.math.BigDecimal getSumOrderedInStockingUOM() 
	{
		BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_SumOrderedInStockingUOM);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setUserFlag (java.lang.String UserFlag)
	{
		set_Value (COLUMNNAME_UserFlag, UserFlag);
	}

	@Override
	public java.lang.String getUserFlag() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_UserFlag);
	}
}