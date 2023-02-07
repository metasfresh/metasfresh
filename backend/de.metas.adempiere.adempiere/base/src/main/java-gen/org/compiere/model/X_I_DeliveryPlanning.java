// Generated Model - DO NOT CHANGE
package org.compiere.model;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for I_DeliveryPlanning
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_I_DeliveryPlanning extends org.compiere.model.PO implements I_I_DeliveryPlanning, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -2004566134L;

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
	public void setActualDeliveryDate (final @Nullable java.sql.Timestamp ActualDeliveryDate)
	{
		set_Value (COLUMNNAME_ActualDeliveryDate, ActualDeliveryDate);
	}

	@Override
	public java.sql.Timestamp getActualDeliveryDate() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_ActualDeliveryDate);
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
	public void setActualLoadingDate (final @Nullable java.sql.Timestamp ActualLoadingDate)
	{
		set_Value (COLUMNNAME_ActualLoadingDate, ActualLoadingDate);
	}

	@Override
	public java.sql.Timestamp getActualLoadingDate() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_ActualLoadingDate);
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
	public org.compiere.model.I_C_DataImport getC_DataImport()
	{
		return get_ValueAsPO(COLUMNNAME_C_DataImport_ID, org.compiere.model.I_C_DataImport.class);
	}

	@Override
	public void setC_DataImport(final org.compiere.model.I_C_DataImport C_DataImport)
	{
		set_ValueFromPO(COLUMNNAME_C_DataImport_ID, org.compiere.model.I_C_DataImport.class, C_DataImport);
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
	public org.compiere.model.I_C_DataImport_Run getC_DataImport_Run()
	{
		return get_ValueAsPO(COLUMNNAME_C_DataImport_Run_ID, org.compiere.model.I_C_DataImport_Run.class);
	}

	@Override
	public void setC_DataImport_Run(final org.compiere.model.I_C_DataImport_Run C_DataImport_Run)
	{
		set_ValueFromPO(COLUMNNAME_C_DataImport_Run_ID, org.compiere.model.I_C_DataImport_Run.class, C_DataImport_Run);
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
	public org.compiere.model.I_I_DeliveryPlanning_Data getI_DeliveryPlanning_Data()
	{
		return get_ValueAsPO(COLUMNNAME_I_DeliveryPlanning_Data_ID, org.compiere.model.I_I_DeliveryPlanning_Data.class);
	}

	@Override
	public void setI_DeliveryPlanning_Data(final org.compiere.model.I_I_DeliveryPlanning_Data I_DeliveryPlanning_Data)
	{
		set_ValueFromPO(COLUMNNAME_I_DeliveryPlanning_Data_ID, org.compiere.model.I_I_DeliveryPlanning_Data.class, I_DeliveryPlanning_Data);
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
	public org.compiere.model.I_M_Delivery_Planning getM_Delivery_Planning()
	{
		return get_ValueAsPO(COLUMNNAME_M_Delivery_Planning_ID, org.compiere.model.I_M_Delivery_Planning.class);
	}

	@Override
	public void setM_Delivery_Planning(final org.compiere.model.I_M_Delivery_Planning M_Delivery_Planning)
	{
		set_ValueFromPO(COLUMNNAME_M_Delivery_Planning_ID, org.compiere.model.I_M_Delivery_Planning.class, M_Delivery_Planning);
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
	public void setPlannedDeliveryDate (final @Nullable java.sql.Timestamp PlannedDeliveryDate)
	{
		set_Value (COLUMNNAME_PlannedDeliveryDate, PlannedDeliveryDate);
	}

	@Override
	public java.sql.Timestamp getPlannedDeliveryDate() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_PlannedDeliveryDate);
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
	public void setPlannedLoadingDate (final @Nullable java.sql.Timestamp PlannedLoadingDate)
	{
		set_Value (COLUMNNAME_PlannedLoadingDate, PlannedLoadingDate);
	}

	@Override
	public java.sql.Timestamp getPlannedLoadingDate() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_PlannedLoadingDate);
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