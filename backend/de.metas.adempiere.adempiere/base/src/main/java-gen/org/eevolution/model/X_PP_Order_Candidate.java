// Generated Model - DO NOT CHANGE
package org.eevolution.model;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for PP_Order_Candidate
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_PP_Order_Candidate extends org.compiere.model.PO implements I_PP_Order_Candidate, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 63995316L;

    /** Standard Constructor */
    public X_PP_Order_Candidate (final Properties ctx, final int PP_Order_Candidate_ID, @Nullable final String trxName)
    {
      super (ctx, PP_Order_Candidate_ID, trxName);
    }

    /** Load Constructor */
    public X_PP_Order_Candidate (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setAD_Workflow_ID (final int AD_Workflow_ID)
	{
		throw new IllegalArgumentException ("AD_Workflow_ID is virtual column");	}

	@Override
	public int getAD_Workflow_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Workflow_ID);
	}

	@Override
	public org.compiere.model.I_C_OrderLine getC_OrderLine()
	{
		return get_ValueAsPO(COLUMNNAME_C_OrderLine_ID, org.compiere.model.I_C_OrderLine.class);
	}

	@Override
	public void setC_OrderLine(final org.compiere.model.I_C_OrderLine C_OrderLine)
	{
		set_ValueFromPO(COLUMNNAME_C_OrderLine_ID, org.compiere.model.I_C_OrderLine.class, C_OrderLine);
	}

	@Override
	public void setC_OrderLine_ID (final int C_OrderLine_ID)
	{
		if (C_OrderLine_ID < 1) 
			set_Value (COLUMNNAME_C_OrderLine_ID, null);
		else 
			set_Value (COLUMNNAME_C_OrderLine_ID, C_OrderLine_ID);
	}

	@Override
	public int getC_OrderLine_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_OrderLine_ID);
	}

	@Override
	public void setC_UOM_ID (final int C_UOM_ID)
	{
		if (C_UOM_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_UOM_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_UOM_ID, C_UOM_ID);
	}

	@Override
	public int getC_UOM_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_UOM_ID);
	}

	@Override
	public void setDatePromised (final java.sql.Timestamp DatePromised)
	{
		set_Value (COLUMNNAME_DatePromised, DatePromised);
	}

	@Override
	public java.sql.Timestamp getDatePromised() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DatePromised);
	}

	@Override
	public void setDateStartSchedule (final java.sql.Timestamp DateStartSchedule)
	{
		set_Value (COLUMNNAME_DateStartSchedule, DateStartSchedule);
	}

	@Override
	public java.sql.Timestamp getDateStartSchedule() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DateStartSchedule);
	}

	@Override
	public void setIsClosed (final boolean IsClosed)
	{
		set_Value (COLUMNNAME_IsClosed, IsClosed);
	}

	@Override
	public boolean isClosed() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsClosed);
	}

	@Override
	public void setIsMaturing (final boolean IsMaturing)
	{
		set_Value (COLUMNNAME_IsMaturing, IsMaturing);
	}

	@Override
	public boolean isMaturing() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsMaturing);
	}

	@Override
	public void setIsSimulated (final boolean IsSimulated)
	{
		set_Value (COLUMNNAME_IsSimulated, IsSimulated);
	}

	@Override
	public boolean isSimulated() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsSimulated);
	}

	@Override
	public void setIssue_HU_ID (final int Issue_HU_ID)
	{
		if (Issue_HU_ID < 1) 
			set_Value (COLUMNNAME_Issue_HU_ID, null);
		else 
			set_Value (COLUMNNAME_Issue_HU_ID, Issue_HU_ID);
	}

	@Override
	public int getIssue_HU_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Issue_HU_ID);
	}

	@Override
	public org.compiere.model.I_M_AttributeSetInstance getM_AttributeSetInstance()
	{
		return get_ValueAsPO(COLUMNNAME_M_AttributeSetInstance_ID, org.compiere.model.I_M_AttributeSetInstance.class);
	}

	@Override
	public void setM_AttributeSetInstance(final org.compiere.model.I_M_AttributeSetInstance M_AttributeSetInstance)
	{
		set_ValueFromPO(COLUMNNAME_M_AttributeSetInstance_ID, org.compiere.model.I_M_AttributeSetInstance.class, M_AttributeSetInstance);
	}

	@Override
	public void setM_AttributeSetInstance_ID (final int M_AttributeSetInstance_ID)
	{
		if (M_AttributeSetInstance_ID < 0) 
			set_Value (COLUMNNAME_M_AttributeSetInstance_ID, null);
		else 
			set_Value (COLUMNNAME_M_AttributeSetInstance_ID, M_AttributeSetInstance_ID);
	}

	@Override
	public int getM_AttributeSetInstance_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_AttributeSetInstance_ID);
	}

	@Override
	public void setM_HU_PI_Item_Product_ID (final int M_HU_PI_Item_Product_ID)
	{
		if (M_HU_PI_Item_Product_ID < 1) 
			set_Value (COLUMNNAME_M_HU_PI_Item_Product_ID, null);
		else 
			set_Value (COLUMNNAME_M_HU_PI_Item_Product_ID, M_HU_PI_Item_Product_ID);
	}

	@Override
	public int getM_HU_PI_Item_Product_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_HU_PI_Item_Product_ID);
	}

	@Override
	public org.compiere.model.I_M_Maturing_Configuration getM_Maturing_Configuration()
	{
		return get_ValueAsPO(COLUMNNAME_M_Maturing_Configuration_ID, org.compiere.model.I_M_Maturing_Configuration.class);
	}

	@Override
	public void setM_Maturing_Configuration(final org.compiere.model.I_M_Maturing_Configuration M_Maturing_Configuration)
	{
		set_ValueFromPO(COLUMNNAME_M_Maturing_Configuration_ID, org.compiere.model.I_M_Maturing_Configuration.class, M_Maturing_Configuration);
	}

	@Override
	public void setM_Maturing_Configuration_ID (final int M_Maturing_Configuration_ID)
	{
		if (M_Maturing_Configuration_ID < 1) 
			set_Value (COLUMNNAME_M_Maturing_Configuration_ID, null);
		else 
			set_Value (COLUMNNAME_M_Maturing_Configuration_ID, M_Maturing_Configuration_ID);
	}

	@Override
	public int getM_Maturing_Configuration_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Maturing_Configuration_ID);
	}

	@Override
	public org.compiere.model.I_M_Maturing_Configuration_Line getM_Maturing_Configuration_Line()
	{
		return get_ValueAsPO(COLUMNNAME_M_Maturing_Configuration_Line_ID, org.compiere.model.I_M_Maturing_Configuration_Line.class);
	}

	@Override
	public void setM_Maturing_Configuration_Line(final org.compiere.model.I_M_Maturing_Configuration_Line M_Maturing_Configuration_Line)
	{
		set_ValueFromPO(COLUMNNAME_M_Maturing_Configuration_Line_ID, org.compiere.model.I_M_Maturing_Configuration_Line.class, M_Maturing_Configuration_Line);
	}

	@Override
	public void setM_Maturing_Configuration_Line_ID (final int M_Maturing_Configuration_Line_ID)
	{
		if (M_Maturing_Configuration_Line_ID < 1) 
			set_Value (COLUMNNAME_M_Maturing_Configuration_Line_ID, null);
		else 
			set_Value (COLUMNNAME_M_Maturing_Configuration_Line_ID, M_Maturing_Configuration_Line_ID);
	}

	@Override
	public int getM_Maturing_Configuration_Line_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Maturing_Configuration_Line_ID);
	}

	@Override
	public void setM_Product_ID (final int M_Product_ID)
	{
		if (M_Product_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Product_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Product_ID, M_Product_ID);
	}

	@Override
	public int getM_Product_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Product_ID);
	}

	@Override
	public void setM_ShipmentSchedule_ID (final int M_ShipmentSchedule_ID)
	{
		if (M_ShipmentSchedule_ID < 1) 
			set_Value (COLUMNNAME_M_ShipmentSchedule_ID, null);
		else 
			set_Value (COLUMNNAME_M_ShipmentSchedule_ID, M_ShipmentSchedule_ID);
	}

	@Override
	public int getM_ShipmentSchedule_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_ShipmentSchedule_ID);
	}

	@Override
	public void setM_Warehouse_ID (final int M_Warehouse_ID)
	{
		if (M_Warehouse_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Warehouse_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Warehouse_ID, M_Warehouse_ID);
	}

	@Override
	public int getM_Warehouse_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Warehouse_ID);
	}

	@Override
	public void setNumberOfResources_ToProcess (final int NumberOfResources_ToProcess)
	{
		throw new IllegalArgumentException ("NumberOfResources_ToProcess is virtual column");	}

	@Override
	public int getNumberOfResources_ToProcess() 
	{
		return get_ValueAsInt(COLUMNNAME_NumberOfResources_ToProcess);
	}

	@Override
	public void setPP_Order_Candidate_ID (final int PP_Order_Candidate_ID)
	{
		if (PP_Order_Candidate_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_PP_Order_Candidate_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_PP_Order_Candidate_ID, PP_Order_Candidate_ID);
	}

	@Override
	public int getPP_Order_Candidate_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_PP_Order_Candidate_ID);
	}

	@Override
	public org.eevolution.model.I_PP_Product_BOM getPP_Product_BOM()
	{
		return get_ValueAsPO(COLUMNNAME_PP_Product_BOM_ID, org.eevolution.model.I_PP_Product_BOM.class);
	}

	@Override
	public void setPP_Product_BOM(final org.eevolution.model.I_PP_Product_BOM PP_Product_BOM)
	{
		set_ValueFromPO(COLUMNNAME_PP_Product_BOM_ID, org.eevolution.model.I_PP_Product_BOM.class, PP_Product_BOM);
	}

	@Override
	public void setPP_Product_BOM_ID (final int PP_Product_BOM_ID)
	{
		if (PP_Product_BOM_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_PP_Product_BOM_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_PP_Product_BOM_ID, PP_Product_BOM_ID);
	}

	@Override
	public int getPP_Product_BOM_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_PP_Product_BOM_ID);
	}

	@Override
	public void setPP_Product_Planning_ID (final int PP_Product_Planning_ID)
	{
		if (PP_Product_Planning_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_PP_Product_Planning_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_PP_Product_Planning_ID, PP_Product_Planning_ID);
	}

	@Override
	public int getPP_Product_Planning_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_PP_Product_Planning_ID);
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
	public void setQtyEntered (final @Nullable BigDecimal QtyEntered)
	{
		set_Value (COLUMNNAME_QtyEntered, QtyEntered);
	}

	@Override
	public BigDecimal getQtyEntered() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyEntered);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyProcessed (final @Nullable BigDecimal QtyProcessed)
	{
		set_Value (COLUMNNAME_QtyProcessed, QtyProcessed);
	}

	@Override
	public BigDecimal getQtyProcessed() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyProcessed);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyToProcess (final @Nullable BigDecimal QtyToProcess)
	{
		set_Value (COLUMNNAME_QtyToProcess, QtyToProcess);
	}

	@Override
	public BigDecimal getQtyToProcess() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyToProcess);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setSeqNo (final int SeqNo)
	{
		set_Value (COLUMNNAME_SeqNo, SeqNo);
	}

	@Override
	public int getSeqNo() 
	{
		return get_ValueAsInt(COLUMNNAME_SeqNo);
	}

	@Override
	public org.compiere.model.I_S_Resource getS_Resource()
	{
		return get_ValueAsPO(COLUMNNAME_S_Resource_ID, org.compiere.model.I_S_Resource.class);
	}

	@Override
	public void setS_Resource(final org.compiere.model.I_S_Resource S_Resource)
	{
		set_ValueFromPO(COLUMNNAME_S_Resource_ID, org.compiere.model.I_S_Resource.class, S_Resource);
	}

	@Override
	public void setS_Resource_ID (final int S_Resource_ID)
	{
		if (S_Resource_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_S_Resource_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_S_Resource_ID, S_Resource_ID);
	}

	@Override
	public int getS_Resource_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_S_Resource_ID);
	}

	@Override
	public org.compiere.model.I_S_Resource getWorkStation()
	{
		return get_ValueAsPO(COLUMNNAME_WorkStation_ID, org.compiere.model.I_S_Resource.class);
	}

	@Override
	public void setWorkStation(final org.compiere.model.I_S_Resource WorkStation)
	{
		set_ValueFromPO(COLUMNNAME_WorkStation_ID, org.compiere.model.I_S_Resource.class, WorkStation);
	}

	@Override
	public void setWorkStation_ID (final int WorkStation_ID)
	{
		if (WorkStation_ID < 1) 
			set_Value (COLUMNNAME_WorkStation_ID, null);
		else 
			set_Value (COLUMNNAME_WorkStation_ID, WorkStation_ID);
	}

	@Override
	public int getWorkStation_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_WorkStation_ID);
	}
}