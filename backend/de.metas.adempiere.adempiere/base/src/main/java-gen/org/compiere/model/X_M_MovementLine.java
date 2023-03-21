// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for M_MovementLine
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_M_MovementLine extends org.compiere.model.PO implements I_M_MovementLine, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1431674431L;

    /** Standard Constructor */
    public X_M_MovementLine (final Properties ctx, final int M_MovementLine_ID, @Nullable final String trxName)
    {
      super (ctx, M_MovementLine_ID, trxName);
    }

    /** Load Constructor */
    public X_M_MovementLine (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setC_ActivityFrom_ID (final int C_ActivityFrom_ID)
	{
		if (C_ActivityFrom_ID < 1) 
			set_Value (COLUMNNAME_C_ActivityFrom_ID, null);
		else 
			set_Value (COLUMNNAME_C_ActivityFrom_ID, C_ActivityFrom_ID);
	}

	@Override
	public int getC_ActivityFrom_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_ActivityFrom_ID);
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
	public org.eevolution.model.I_DD_OrderLine_Alternative getDD_OrderLine_Alternative()
	{
		return get_ValueAsPO(COLUMNNAME_DD_OrderLine_Alternative_ID, org.eevolution.model.I_DD_OrderLine_Alternative.class);
	}

	@Override
	public void setDD_OrderLine_Alternative(final org.eevolution.model.I_DD_OrderLine_Alternative DD_OrderLine_Alternative)
	{
		set_ValueFromPO(COLUMNNAME_DD_OrderLine_Alternative_ID, org.eevolution.model.I_DD_OrderLine_Alternative.class, DD_OrderLine_Alternative);
	}

	@Override
	public void setDD_OrderLine_Alternative_ID (final int DD_OrderLine_Alternative_ID)
	{
		if (DD_OrderLine_Alternative_ID < 1) 
			set_Value (COLUMNNAME_DD_OrderLine_Alternative_ID, null);
		else 
			set_Value (COLUMNNAME_DD_OrderLine_Alternative_ID, DD_OrderLine_Alternative_ID);
	}

	@Override
	public int getDD_OrderLine_Alternative_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_DD_OrderLine_Alternative_ID);
	}

	@Override
	public org.eevolution.model.I_DD_OrderLine getDD_OrderLine()
	{
		return get_ValueAsPO(COLUMNNAME_DD_OrderLine_ID, org.eevolution.model.I_DD_OrderLine.class);
	}

	@Override
	public void setDD_OrderLine(final org.eevolution.model.I_DD_OrderLine DD_OrderLine)
	{
		set_ValueFromPO(COLUMNNAME_DD_OrderLine_ID, org.eevolution.model.I_DD_OrderLine.class, DD_OrderLine);
	}

	@Override
	public void setDD_OrderLine_ID (final int DD_OrderLine_ID)
	{
		if (DD_OrderLine_ID < 1) 
			set_Value (COLUMNNAME_DD_OrderLine_ID, null);
		else 
			set_Value (COLUMNNAME_DD_OrderLine_ID, DD_OrderLine_ID);
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
			set_ValueNoCheck (COLUMNNAME_M_AttributeSetInstanceTo_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_AttributeSetInstanceTo_ID, M_AttributeSetInstanceTo_ID);
	}

	@Override
	public int getM_AttributeSetInstanceTo_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_AttributeSetInstanceTo_ID);
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
	public org.compiere.model.I_M_Movement getM_Movement()
	{
		return get_ValueAsPO(COLUMNNAME_M_Movement_ID, org.compiere.model.I_M_Movement.class);
	}

	@Override
	public void setM_Movement(final org.compiere.model.I_M_Movement M_Movement)
	{
		set_ValueFromPO(COLUMNNAME_M_Movement_ID, org.compiere.model.I_M_Movement.class, M_Movement);
	}

	@Override
	public void setM_Movement_ID (final int M_Movement_ID)
	{
		if (M_Movement_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Movement_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Movement_ID, M_Movement_ID);
	}

	@Override
	public int getM_Movement_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Movement_ID);
	}

	@Override
	public void setM_MovementLine_ID (final int M_MovementLine_ID)
	{
		if (M_MovementLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_MovementLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_MovementLine_ID, M_MovementLine_ID);
	}

	@Override
	public int getM_MovementLine_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_MovementLine_ID);
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
	public org.compiere.model.I_M_SectionCode getM_SectionCode()
	{
		return get_ValueAsPO(COLUMNNAME_M_SectionCode_ID, org.compiere.model.I_M_SectionCode.class);
	}

	@Override
	public void setM_SectionCode(final org.compiere.model.I_M_SectionCode M_SectionCode)
	{
		set_ValueFromPO(COLUMNNAME_M_SectionCode_ID, org.compiere.model.I_M_SectionCode.class, M_SectionCode);
	}

	@Override
	public void setM_SectionCode_ID (final int M_SectionCode_ID)
	{
		if (M_SectionCode_ID < 1) 
			set_Value (COLUMNNAME_M_SectionCode_ID, null);
		else 
			set_Value (COLUMNNAME_M_SectionCode_ID, M_SectionCode_ID);
	}

	@Override
	public int getM_SectionCode_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_SectionCode_ID);
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
	public org.compiere.model.I_M_MovementLine getReversalLine()
	{
		return get_ValueAsPO(COLUMNNAME_ReversalLine_ID, org.compiere.model.I_M_MovementLine.class);
	}

	@Override
	public void setReversalLine(final org.compiere.model.I_M_MovementLine ReversalLine)
	{
		set_ValueFromPO(COLUMNNAME_ReversalLine_ID, org.compiere.model.I_M_MovementLine.class, ReversalLine);
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
	public void setTargetQty (final BigDecimal TargetQty)
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
	public void setValue (final @Nullable java.lang.String Value)
	{
		throw new IllegalArgumentException ("Value is virtual column");	}

	@Override
	public java.lang.String getValue() 
	{
		return get_ValueAsString(COLUMNNAME_Value);
	}
}