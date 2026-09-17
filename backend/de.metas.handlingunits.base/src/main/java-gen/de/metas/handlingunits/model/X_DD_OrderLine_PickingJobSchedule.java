// Generated Model - DO NOT CHANGE
package de.metas.handlingunits.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for DD_OrderLine_PickingJobSchedule
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_DD_OrderLine_PickingJobSchedule extends org.compiere.model.PO implements I_DD_OrderLine_PickingJobSchedule, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1186321366L;

    /** Standard Constructor */
    public X_DD_OrderLine_PickingJobSchedule (final Properties ctx, final int DD_OrderLine_PickingJobSchedule_ID, @Nullable final String trxName)
    {
      super (ctx, DD_OrderLine_PickingJobSchedule_ID, trxName);
    }

    /** Load Constructor */
    public X_DD_OrderLine_PickingJobSchedule (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setDD_OrderLine_PickingJobSchedule_ID (final int DD_OrderLine_PickingJobSchedule_ID)
	{
		if (DD_OrderLine_PickingJobSchedule_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_DD_OrderLine_PickingJobSchedule_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_DD_OrderLine_PickingJobSchedule_ID, DD_OrderLine_PickingJobSchedule_ID);
	}

	@Override
	public int getDD_OrderLine_PickingJobSchedule_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_DD_OrderLine_PickingJobSchedule_ID);
	}

	@Override
	public void setM_Picking_Job_Schedule_ID (final int M_Picking_Job_Schedule_ID)
	{
		if (M_Picking_Job_Schedule_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Picking_Job_Schedule_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Picking_Job_Schedule_ID, M_Picking_Job_Schedule_ID);
	}

	@Override
	public int getM_Picking_Job_Schedule_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Picking_Job_Schedule_ID);
	}

	@Override
	public void setQty (final BigDecimal Qty)
	{
		set_ValueNoCheck (COLUMNNAME_Qty, Qty);
	}

	@Override
	public BigDecimal getQty() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_Qty);
		return bd != null ? bd : BigDecimal.ZERO;
	}
}