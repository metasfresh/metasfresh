// Generated Model - DO NOT CHANGE
package de.metas.inoutcandidate.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for M_ShipmentSchedule_Split
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_M_ShipmentSchedule_Split extends org.compiere.model.PO implements I_M_ShipmentSchedule_Split, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -732637516L;

    /** Standard Constructor */
    public X_M_ShipmentSchedule_Split (final Properties ctx, final int M_ShipmentSchedule_Split_ID, @Nullable final String trxName)
    {
      super (ctx, M_ShipmentSchedule_Split_ID, trxName);
    }

    /** Load Constructor */
    public X_M_ShipmentSchedule_Split (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setDeliveryDate (final java.sql.Timestamp DeliveryDate)
	{
		set_Value (COLUMNNAME_DeliveryDate, DeliveryDate);
	}

	@Override
	public java.sql.Timestamp getDeliveryDate() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DeliveryDate);
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
	public de.metas.inoutcandidate.model.I_M_ShipmentSchedule getM_ShipmentSchedule()
	{
		return get_ValueAsPO(COLUMNNAME_M_ShipmentSchedule_ID, de.metas.inoutcandidate.model.I_M_ShipmentSchedule.class);
	}

	@Override
	public void setM_ShipmentSchedule(final de.metas.inoutcandidate.model.I_M_ShipmentSchedule M_ShipmentSchedule)
	{
		set_ValueFromPO(COLUMNNAME_M_ShipmentSchedule_ID, de.metas.inoutcandidate.model.I_M_ShipmentSchedule.class, M_ShipmentSchedule);
	}

	@Override
	public void setM_ShipmentSchedule_ID (final int M_ShipmentSchedule_ID)
	{
		if (M_ShipmentSchedule_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_ShipmentSchedule_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_ShipmentSchedule_ID, M_ShipmentSchedule_ID);
	}

	@Override
	public int getM_ShipmentSchedule_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_ShipmentSchedule_ID);
	}

	@Override
	public void setM_ShipmentSchedule_Split_ID (final int M_ShipmentSchedule_Split_ID)
	{
		if (M_ShipmentSchedule_Split_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_ShipmentSchedule_Split_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_ShipmentSchedule_Split_ID, M_ShipmentSchedule_Split_ID);
	}

	@Override
	public int getM_ShipmentSchedule_Split_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_ShipmentSchedule_Split_ID);
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
	public void setQtyToDeliver (final BigDecimal QtyToDeliver)
	{
		set_Value (COLUMNNAME_QtyToDeliver, QtyToDeliver);
	}

	@Override
	public BigDecimal getQtyToDeliver() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyToDeliver);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setUserElementNumber1 (final @Nullable BigDecimal UserElementNumber1)
	{
		set_Value (COLUMNNAME_UserElementNumber1, UserElementNumber1);
	}

	@Override
	public BigDecimal getUserElementNumber1()
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_UserElementNumber1);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setUserElementNumber2 (final @Nullable BigDecimal UserElementNumber2)
	{
		set_Value (COLUMNNAME_UserElementNumber2, UserElementNumber2);
	}

	@Override
	public BigDecimal getUserElementNumber2()
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_UserElementNumber2);
		return bd != null ? bd : BigDecimal.ZERO;
	}
}