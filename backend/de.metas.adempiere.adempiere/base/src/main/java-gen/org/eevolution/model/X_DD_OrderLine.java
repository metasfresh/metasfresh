// Generated Model - DO NOT CHANGE
package org.eevolution.model;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for DD_OrderLine
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_DD_OrderLine extends org.compiere.model.PO implements I_DD_OrderLine, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -125206032L;

    /** Standard Constructor */
    public X_DD_OrderLine (final Properties ctx, final int DD_OrderLine_ID, @Nullable final String trxName)
    {
      super (ctx, DD_OrderLine_ID, trxName);
    }

    /** Load Constructor */
    public X_DD_OrderLine (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setAD_OrgTrx_ID (final int AD_OrgTrx_ID)
	{
		if (AD_OrgTrx_ID < 1) 
			set_Value (COLUMNNAME_AD_OrgTrx_ID, null);
		else 
			set_Value (COLUMNNAME_AD_OrgTrx_ID, AD_OrgTrx_ID);
	}

	@Override
	public int getAD_OrgTrx_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_OrgTrx_ID);
	}

	@Override
	public void setC_Activity_ID (final int C_Activity_ID)
	{
		if (C_Activity_ID < 1) 
			set_Value (COLUMNNAME_C_Activity_ID, null);
		else 
			set_Value (COLUMNNAME_C_Activity_ID, C_Activity_ID);
	}

	@Override
	public int getC_Activity_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Activity_ID);
	}

	@Override
	public void setC_BPartner_ID (final int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_ID, C_BPartner_ID);
	}

	@Override
	public int getC_BPartner_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_ID);
	}

	@Override
	public org.compiere.model.I_C_Campaign getC_Campaign()
	{
		return get_ValueAsPO(COLUMNNAME_C_Campaign_ID, org.compiere.model.I_C_Campaign.class);
	}

	@Override
	public void setC_Campaign(final org.compiere.model.I_C_Campaign C_Campaign)
	{
		set_ValueFromPO(COLUMNNAME_C_Campaign_ID, org.compiere.model.I_C_Campaign.class, C_Campaign);
	}

	@Override
	public void setC_Campaign_ID (final int C_Campaign_ID)
	{
		if (C_Campaign_ID < 1) 
			set_Value (COLUMNNAME_C_Campaign_ID, null);
		else 
			set_Value (COLUMNNAME_C_Campaign_ID, C_Campaign_ID);
	}

	@Override
	public int getC_Campaign_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Campaign_ID);
	}

	@Override
	public void setC_Charge_ID (final int C_Charge_ID)
	{
		if (C_Charge_ID < 1) 
			set_Value (COLUMNNAME_C_Charge_ID, null);
		else 
			set_Value (COLUMNNAME_C_Charge_ID, C_Charge_ID);
	}

	@Override
	public int getC_Charge_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Charge_ID);
	}

	@Override
	public org.compiere.model.I_C_OrderLine getC_OrderLineSO()
	{
		return get_ValueAsPO(COLUMNNAME_C_OrderLineSO_ID, org.compiere.model.I_C_OrderLine.class);
	}

	@Override
	public void setC_OrderLineSO(final org.compiere.model.I_C_OrderLine C_OrderLineSO)
	{
		set_ValueFromPO(COLUMNNAME_C_OrderLineSO_ID, org.compiere.model.I_C_OrderLine.class, C_OrderLineSO);
	}

	@Override
	public void setC_OrderLineSO_ID (final int C_OrderLineSO_ID)
	{
		if (C_OrderLineSO_ID < 1) 
			set_Value (COLUMNNAME_C_OrderLineSO_ID, null);
		else 
			set_Value (COLUMNNAME_C_OrderLineSO_ID, C_OrderLineSO_ID);
	}

	@Override
	public int getC_OrderLineSO_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_OrderLineSO_ID);
	}

	@Override
	public void setC_Project_ID (final int C_Project_ID)
	{
		if (C_Project_ID < 1) 
			set_Value (COLUMNNAME_C_Project_ID, null);
		else 
			set_Value (COLUMNNAME_C_Project_ID, C_Project_ID);
	}

	@Override
	public int getC_Project_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Project_ID);
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
	public void setConfirmedQty (final @Nullable BigDecimal ConfirmedQty)
	{
		set_Value (COLUMNNAME_ConfirmedQty, ConfirmedQty);
	}

	@Override
	public BigDecimal getConfirmedQty() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_ConfirmedQty);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setDateDelivered (final @Nullable java.sql.Timestamp DateDelivered)
	{
		set_Value (COLUMNNAME_DateDelivered, DateDelivered);
	}

	@Override
	public java.sql.Timestamp getDateDelivered() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DateDelivered);
	}

	@Override
	public void setDateOrdered (final @Nullable java.sql.Timestamp DateOrdered)
	{
		set_Value (COLUMNNAME_DateOrdered, DateOrdered);
	}

	@Override
	public java.sql.Timestamp getDateOrdered() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DateOrdered);
	}

	@Override
	public void setDatePromised (final @Nullable java.sql.Timestamp DatePromised)
	{
		set_Value (COLUMNNAME_DatePromised, DatePromised);
	}

	@Override
	public java.sql.Timestamp getDatePromised() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DatePromised);
	}

	@Override
	public void setDD_AllowPush (final boolean DD_AllowPush)
	{
		set_Value (COLUMNNAME_DD_AllowPush, DD_AllowPush);
	}

	@Override
	public boolean isDD_AllowPush() 
	{
		return get_ValueAsBoolean(COLUMNNAME_DD_AllowPush);
	}

	@Override
	public org.eevolution.model.I_DD_NetworkDistributionLine getDD_NetworkDistributionLine()
	{
		return get_ValueAsPO(COLUMNNAME_DD_NetworkDistributionLine_ID, org.eevolution.model.I_DD_NetworkDistributionLine.class);
	}

	@Override
	public void setDD_NetworkDistributionLine(final org.eevolution.model.I_DD_NetworkDistributionLine DD_NetworkDistributionLine)
	{
		set_ValueFromPO(COLUMNNAME_DD_NetworkDistributionLine_ID, org.eevolution.model.I_DD_NetworkDistributionLine.class, DD_NetworkDistributionLine);
	}

	@Override
	public void setDD_NetworkDistributionLine_ID (final int DD_NetworkDistributionLine_ID)
	{
		if (DD_NetworkDistributionLine_ID < 1) 
			set_Value (COLUMNNAME_DD_NetworkDistributionLine_ID, null);
		else 
			set_Value (COLUMNNAME_DD_NetworkDistributionLine_ID, DD_NetworkDistributionLine_ID);
	}

	@Override
	public int getDD_NetworkDistributionLine_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_DD_NetworkDistributionLine_ID);
	}

	@Override
	public org.eevolution.model.I_DD_Order getDD_Order()
	{
		return get_ValueAsPO(COLUMNNAME_DD_Order_ID, org.eevolution.model.I_DD_Order.class);
	}

	@Override
	public void setDD_Order(final org.eevolution.model.I_DD_Order DD_Order)
	{
		set_ValueFromPO(COLUMNNAME_DD_Order_ID, org.eevolution.model.I_DD_Order.class, DD_Order);
	}

	@Override
	public void setDD_Order_ID (final int DD_Order_ID)
	{
		if (DD_Order_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_DD_Order_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_DD_Order_ID, DD_Order_ID);
	}

	@Override
	public int getDD_Order_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_DD_Order_ID);
	}

	@Override
	public void setDD_OrderLine_ID (final int DD_OrderLine_ID)
	{
		if (DD_OrderLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_DD_OrderLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_DD_OrderLine_ID, DD_OrderLine_ID);
	}

	@Override
	public int getDD_OrderLine_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_DD_OrderLine_ID);
	}

	@Override
	public void setDescription (final @Nullable java.lang.String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	@Override
	public java.lang.String getDescription() 
	{
		return get_ValueAsString(COLUMNNAME_Description);
	}

	@Override
	public void setFreightAmt (final @Nullable BigDecimal FreightAmt)
	{
		set_Value (COLUMNNAME_FreightAmt, FreightAmt);
	}

	@Override
	public BigDecimal getFreightAmt() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_FreightAmt);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setIsDelivered (final boolean IsDelivered)
	{
		set_Value (COLUMNNAME_IsDelivered, IsDelivered);
	}

	@Override
	public boolean isDelivered() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsDelivered);
	}

	/** 
	 * IsDelivered_Override AD_Reference_ID=319
	 * Reference name: _YesNo
	 */
	public static final int ISDELIVERED_OVERRIDE_AD_Reference_ID=319;
	/** Yes = Y */
	public static final String ISDELIVERED_OVERRIDE_Yes = "Y";
	/** No = N */
	public static final String ISDELIVERED_OVERRIDE_No = "N";
	@Override
	public void setIsDelivered_Override (final @Nullable java.lang.String IsDelivered_Override)
	{
		set_Value (COLUMNNAME_IsDelivered_Override, IsDelivered_Override);
	}

	@Override
	public java.lang.String getIsDelivered_Override() 
	{
		return get_ValueAsString(COLUMNNAME_IsDelivered_Override);
	}

	@Override
	public void setIsDescription (final boolean IsDescription)
	{
		set_Value (COLUMNNAME_IsDescription, IsDescription);
	}

	@Override
	public boolean isDescription() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsDescription);
	}

	@Override
	public void setIsInvoiced (final boolean IsInvoiced)
	{
		set_Value (COLUMNNAME_IsInvoiced, IsInvoiced);
	}

	@Override
	public boolean isInvoiced() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsInvoiced);
	}

	@Override
	public void setIsKeepTargetPlant (final boolean IsKeepTargetPlant)
	{
		set_Value (COLUMNNAME_IsKeepTargetPlant, IsKeepTargetPlant);
	}

	@Override
	public boolean isKeepTargetPlant() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsKeepTargetPlant);
	}

	@Override
	public void setLine (final int Line)
	{
		set_Value (COLUMNNAME_Line, Line);
	}

	@Override
	public int getLine() 
	{
		return get_ValueAsInt(COLUMNNAME_Line);
	}

	@Override
	public void setLineNetAmt (final @Nullable BigDecimal LineNetAmt)
	{
		set_Value (COLUMNNAME_LineNetAmt, LineNetAmt);
	}

	@Override
	public BigDecimal getLineNetAmt() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_LineNetAmt);
		return bd != null ? bd : BigDecimal.ZERO;
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
	public org.compiere.model.I_M_AttributeSetInstance getM_AttributeSetInstanceTo()
	{
		return get_ValueAsPO(COLUMNNAME_M_AttributeSetInstanceTo_ID, org.compiere.model.I_M_AttributeSetInstance.class);
	}

	@Override
	public void setM_AttributeSetInstanceTo(final org.compiere.model.I_M_AttributeSetInstance M_AttributeSetInstanceTo)
	{
		set_ValueFromPO(COLUMNNAME_M_AttributeSetInstanceTo_ID, org.compiere.model.I_M_AttributeSetInstance.class, M_AttributeSetInstanceTo);
	}

	@Override
	public void setM_AttributeSetInstanceTo_ID (final int M_AttributeSetInstanceTo_ID)
	{
		if (M_AttributeSetInstanceTo_ID < 1) 
			set_Value (COLUMNNAME_M_AttributeSetInstanceTo_ID, null);
		else 
			set_Value (COLUMNNAME_M_AttributeSetInstanceTo_ID, M_AttributeSetInstanceTo_ID);
	}

	@Override
	public int getM_AttributeSetInstanceTo_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_AttributeSetInstanceTo_ID);
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
	public void setM_LocatorTo_ID (final int M_LocatorTo_ID)
	{
		if (M_LocatorTo_ID < 1) 
			set_Value (COLUMNNAME_M_LocatorTo_ID, null);
		else 
			set_Value (COLUMNNAME_M_LocatorTo_ID, M_LocatorTo_ID);
	}

	@Override
	public int getM_LocatorTo_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_LocatorTo_ID);
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
	public void setM_Warehouse_ID (final int M_Warehouse_ID)
	{
		throw new IllegalArgumentException ("M_Warehouse_ID is virtual column");	}

	@Override
	public int getM_Warehouse_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Warehouse_ID);
	}

	@Override
	public void setM_WarehouseTo_ID (final int M_WarehouseTo_ID)
	{
		throw new IllegalArgumentException ("M_WarehouseTo_ID is virtual column");	}

	@Override
	public int getM_WarehouseTo_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_WarehouseTo_ID);
	}

	@Override
	public void setPickedQty (final @Nullable BigDecimal PickedQty)
	{
		set_Value (COLUMNNAME_PickedQty, PickedQty);
	}

	@Override
	public BigDecimal getPickedQty() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_PickedQty);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public org.compiere.model.I_S_Resource getPP_Plant_From()
	{
		return get_ValueAsPO(COLUMNNAME_PP_Plant_From_ID, org.compiere.model.I_S_Resource.class);
	}

	@Override
	public void setPP_Plant_From(final org.compiere.model.I_S_Resource PP_Plant_From)
	{
		set_ValueFromPO(COLUMNNAME_PP_Plant_From_ID, org.compiere.model.I_S_Resource.class, PP_Plant_From);
	}

	@Override
	public void setPP_Plant_From_ID (final int PP_Plant_From_ID)
	{
		if (PP_Plant_From_ID < 1) 
			set_Value (COLUMNNAME_PP_Plant_From_ID, null);
		else 
			set_Value (COLUMNNAME_PP_Plant_From_ID, PP_Plant_From_ID);
	}

	@Override
	public int getPP_Plant_From_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_PP_Plant_From_ID);
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
	public void setQtyDelivered (final @Nullable BigDecimal QtyDelivered)
	{
		set_Value (COLUMNNAME_QtyDelivered, QtyDelivered);
	}

	@Override
	public BigDecimal getQtyDelivered() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyDelivered);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyEntered (final BigDecimal QtyEntered)
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
	public void setQtyInTransit (final BigDecimal QtyInTransit)
	{
		set_Value (COLUMNNAME_QtyInTransit, QtyInTransit);
	}

	@Override
	public BigDecimal getQtyInTransit() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyInTransit);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyOrdered (final BigDecimal QtyOrdered)
	{
		set_Value (COLUMNNAME_QtyOrdered, QtyOrdered);
	}

	@Override
	public BigDecimal getQtyOrdered() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyOrdered);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyReserved (final @Nullable BigDecimal QtyReserved)
	{
		set_Value (COLUMNNAME_QtyReserved, QtyReserved);
	}

	@Override
	public BigDecimal getQtyReserved() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyReserved);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setScrappedQty (final @Nullable BigDecimal ScrappedQty)
	{
		set_Value (COLUMNNAME_ScrappedQty, ScrappedQty);
	}

	@Override
	public BigDecimal getScrappedQty() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_ScrappedQty);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setTargetQty (final @Nullable BigDecimal TargetQty)
	{
		set_Value (COLUMNNAME_TargetQty, TargetQty);
	}

	@Override
	public BigDecimal getTargetQty() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_TargetQty);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public org.compiere.model.I_C_ElementValue getUser1()
	{
		return get_ValueAsPO(COLUMNNAME_User1_ID, org.compiere.model.I_C_ElementValue.class);
	}

	@Override
	public void setUser1(final org.compiere.model.I_C_ElementValue User1)
	{
		set_ValueFromPO(COLUMNNAME_User1_ID, org.compiere.model.I_C_ElementValue.class, User1);
	}

	@Override
	public void setUser1_ID (final int User1_ID)
	{
		if (User1_ID < 1) 
			set_Value (COLUMNNAME_User1_ID, null);
		else 
			set_Value (COLUMNNAME_User1_ID, User1_ID);
	}

	@Override
	public int getUser1_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_User1_ID);
	}

	@Override
	public org.compiere.model.I_C_ElementValue getUser2()
	{
		return get_ValueAsPO(COLUMNNAME_User2_ID, org.compiere.model.I_C_ElementValue.class);
	}

	@Override
	public void setUser2(final org.compiere.model.I_C_ElementValue User2)
	{
		set_ValueFromPO(COLUMNNAME_User2_ID, org.compiere.model.I_C_ElementValue.class, User2);
	}

	@Override
	public void setUser2_ID (final int User2_ID)
	{
		if (User2_ID < 1) 
			set_Value (COLUMNNAME_User2_ID, null);
		else 
			set_Value (COLUMNNAME_User2_ID, User2_ID);
	}

	@Override
	public int getUser2_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_User2_ID);
	}
}