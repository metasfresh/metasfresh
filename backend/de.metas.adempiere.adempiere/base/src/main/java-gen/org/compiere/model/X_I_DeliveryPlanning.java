// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for I_DeliveryPlanning
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_I_DeliveryPlanning extends org.compiere.model.PO implements I_I_DeliveryPlanning, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 354399226L;

    /** Standard Constructor */
    public X_I_DeliveryPlanning (final Properties ctx, final int I_DeliveryPlanning_ID, @Nullable final String trxName)
    {
      super (ctx, I_DeliveryPlanning_ID, trxName);
    }

    /** Load Constructor */
    public X_I_DeliveryPlanning (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setActualDischargeQuantity (final @Nullable BigDecimal ActualDischargeQuantity)
	{
		set_Value (COLUMNNAME_ActualDischargeQuantity, ActualDischargeQuantity);
	}

	@Override
	public BigDecimal getActualDischargeQuantity() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_ActualDischargeQuantity);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setActualLoadQty (final @Nullable BigDecimal ActualLoadQty)
	{
		set_Value (COLUMNNAME_ActualLoadQty, ActualLoadQty);
	}

	@Override
	public BigDecimal getActualLoadQty() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_ActualLoadQty);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setAD_Issue_ID (final int AD_Issue_ID)
	{
		if (AD_Issue_ID < 1) 
			set_Value (COLUMNNAME_AD_Issue_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Issue_ID, AD_Issue_ID);
	}

	@Override
	public int getAD_Issue_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Issue_ID);
	}

	@Override
	public void setATA (final @Nullable java.sql.Timestamp ATA)
	{
		set_Value (COLUMNNAME_ATA, ATA);
	}

	@Override
	public java.sql.Timestamp getATA() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_ATA);
	}

	@Override
	public void setATD (final @Nullable java.sql.Timestamp ATD)
	{
		set_Value (COLUMNNAME_ATD, ATD);
	}

	@Override
	public java.sql.Timestamp getATD() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_ATD);
	}

	@Override
	public void setBatch (final @Nullable java.lang.String Batch)
	{
		set_Value (COLUMNNAME_Batch, Batch);
	}

	@Override
	public java.lang.String getBatch() 
	{
		return get_ValueAsString(COLUMNNAME_Batch);
	}

	@Override
	public void setC_DataImport_ID (final int C_DataImport_ID)
	{
		if (C_DataImport_ID < 1) 
			set_Value (COLUMNNAME_C_DataImport_ID, null);
		else 
			set_Value (COLUMNNAME_C_DataImport_ID, C_DataImport_ID);
	}

	@Override
	public int getC_DataImport_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_DataImport_ID);
	}

	@Override
	public void setC_DataImport_Run_ID (final int C_DataImport_Run_ID)
	{
		if (C_DataImport_Run_ID < 1) 
			set_Value (COLUMNNAME_C_DataImport_Run_ID, null);
		else 
			set_Value (COLUMNNAME_C_DataImport_Run_ID, C_DataImport_Run_ID);
	}

	@Override
	public int getC_DataImport_Run_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_DataImport_Run_ID);
	}

	@Override
	public void setDocumentNo (final @Nullable java.lang.String DocumentNo)
	{
		set_Value (COLUMNNAME_DocumentNo, DocumentNo);
	}

	@Override
	public java.lang.String getDocumentNo() 
	{
		return get_ValueAsString(COLUMNNAME_DocumentNo);
	}

	@Override
	public void setETA (final @Nullable java.sql.Timestamp ETA)
	{
		set_Value (COLUMNNAME_ETA, ETA);
	}

	@Override
	public java.sql.Timestamp getETA() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_ETA);
	}

	@Override
	public void setETD (final @Nullable java.sql.Timestamp ETD)
	{
		set_Value (COLUMNNAME_ETD, ETD);
	}

	@Override
	public java.sql.Timestamp getETD() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_ETD);
	}

	@Override
	public void setI_DeliveryPlanning_Data_ID (final int I_DeliveryPlanning_Data_ID)
	{
		if (I_DeliveryPlanning_Data_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_I_DeliveryPlanning_Data_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_I_DeliveryPlanning_Data_ID, I_DeliveryPlanning_Data_ID);
	}

	@Override
	public int getI_DeliveryPlanning_Data_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_I_DeliveryPlanning_Data_ID);
	}

	@Override
	public void setI_DeliveryPlanning_ID (final int I_DeliveryPlanning_ID)
	{
		if (I_DeliveryPlanning_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_I_DeliveryPlanning_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_I_DeliveryPlanning_ID, I_DeliveryPlanning_ID);
	}

	@Override
	public int getI_DeliveryPlanning_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_I_DeliveryPlanning_ID);
	}

	@Override
	public void setI_ErrorMsg (final @Nullable java.lang.String I_ErrorMsg)
	{
		set_Value (COLUMNNAME_I_ErrorMsg, I_ErrorMsg);
	}

	@Override
	public java.lang.String getI_ErrorMsg() 
	{
		return get_ValueAsString(COLUMNNAME_I_ErrorMsg);
	}

	/** 
	 * I_IsImported AD_Reference_ID=540745
	 * Reference name: I_IsImported
	 */
	public static final int I_ISIMPORTED_AD_Reference_ID=540745;
	/** NotImported = N */
	public static final String I_ISIMPORTED_NotImported = "N";
	/** Imported = Y */
	public static final String I_ISIMPORTED_Imported = "Y";
	/** ImportFailed = E */
	public static final String I_ISIMPORTED_ImportFailed = "E";
	@Override
	public void setI_IsImported (final java.lang.String I_IsImported)
	{
		set_Value (COLUMNNAME_I_IsImported, I_IsImported);
	}

	@Override
	public java.lang.String getI_IsImported() 
	{
		return get_ValueAsString(COLUMNNAME_I_IsImported);
	}

	@Override
	public void setI_LineNo (final int I_LineNo)
	{
		set_Value (COLUMNNAME_I_LineNo, I_LineNo);
	}

	@Override
	public int getI_LineNo() 
	{
		return get_ValueAsInt(COLUMNNAME_I_LineNo);
	}

	@Override
	public void setM_Delivery_Planning_ID (final int M_Delivery_Planning_ID)
	{
		if (M_Delivery_Planning_ID < 1) 
			set_Value (COLUMNNAME_M_Delivery_Planning_ID, null);
		else 
			set_Value (COLUMNNAME_M_Delivery_Planning_ID, M_Delivery_Planning_ID);
	}

	@Override
	public int getM_Delivery_Planning_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Delivery_Planning_ID);
	}

	@Override
	public void setOriginCountry (final @Nullable java.lang.String OriginCountry)
	{
		set_Value (COLUMNNAME_OriginCountry, OriginCountry);
	}

	@Override
	public java.lang.String getOriginCountry() 
	{
		return get_ValueAsString(COLUMNNAME_OriginCountry);
	}

	@Override
	public void setPlannedDischargeQuantity (final @Nullable BigDecimal PlannedDischargeQuantity)
	{
		set_Value (COLUMNNAME_PlannedDischargeQuantity, PlannedDischargeQuantity);
	}

	@Override
	public BigDecimal getPlannedDischargeQuantity() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_PlannedDischargeQuantity);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setPlannedLoadedQuantity (final @Nullable BigDecimal PlannedLoadedQuantity)
	{
		set_Value (COLUMNNAME_PlannedLoadedQuantity, PlannedLoadedQuantity);
	}

	@Override
	public BigDecimal getPlannedLoadedQuantity() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_PlannedLoadedQuantity);
		return bd != null ? bd : BigDecimal.ZERO;
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
	public void setProcessing (final boolean Processing)
	{
		set_Value (COLUMNNAME_Processing, Processing);
	}

	@Override
	public boolean isProcessing() 
	{
		return get_ValueAsBoolean(COLUMNNAME_Processing);
	}

	@Override
	public void setProductName (final @Nullable java.lang.String ProductName)
	{
		set_Value (COLUMNNAME_ProductName, ProductName);
	}

	@Override
	public java.lang.String getProductName() 
	{
		return get_ValueAsString(COLUMNNAME_ProductName);
	}

	@Override
	public void setReleaseNo (final @Nullable java.lang.String ReleaseNo)
	{
		set_Value (COLUMNNAME_ReleaseNo, ReleaseNo);
	}

	@Override
	public java.lang.String getReleaseNo() 
	{
		return get_ValueAsString(COLUMNNAME_ReleaseNo);
	}

	@Override
	public void setShipToLocation_Name (final @Nullable java.lang.String ShipToLocation_Name)
	{
		set_Value (COLUMNNAME_ShipToLocation_Name, ShipToLocation_Name);
	}

	@Override
	public java.lang.String getShipToLocation_Name() 
	{
		return get_ValueAsString(COLUMNNAME_ShipToLocation_Name);
	}

	@Override
	public void setWarehouseName (final @Nullable java.lang.String WarehouseName)
	{
		set_Value (COLUMNNAME_WarehouseName, WarehouseName);
	}

	@Override
	public java.lang.String getWarehouseName() 
	{
		return get_ValueAsString(COLUMNNAME_WarehouseName);
	}
}