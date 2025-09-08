// Generated Model - DO NOT CHANGE
package de.metas.handlingunits.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for M_Picking_Job_Schedule
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_M_Picking_Job_Schedule extends org.compiere.model.PO implements I_M_Picking_Job_Schedule, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -156272813L;

    /** Standard Constructor */
    public X_M_Picking_Job_Schedule (final Properties ctx, final int M_Picking_Job_Schedule_ID, @Nullable final String trxName)
    {
      super (ctx, M_Picking_Job_Schedule_ID, trxName);
    }

    /** Load Constructor */
    public X_M_Picking_Job_Schedule (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public org.compiere.model.I_C_Workplace getC_Workplace()
	{
		return get_ValueAsPO(COLUMNNAME_C_Workplace_ID, org.compiere.model.I_C_Workplace.class);
	}

	@Override
	public void setC_Workplace(final org.compiere.model.I_C_Workplace C_Workplace)
	{
		set_ValueFromPO(COLUMNNAME_C_Workplace_ID, org.compiere.model.I_C_Workplace.class, C_Workplace);
	}

	@Override
	public void setC_Workplace_ID (final int C_Workplace_ID)
	{
		if (C_Workplace_ID < 1) 
			set_Value (COLUMNNAME_C_Workplace_ID, null);
		else 
			set_Value (COLUMNNAME_C_Workplace_ID, C_Workplace_ID);
	}

	@Override
	public int getC_Workplace_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Workplace_ID);
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
	public void setQtyToPick (final BigDecimal QtyToPick)
	{
		set_Value (COLUMNNAME_QtyToPick, QtyToPick);
	}

	@Override
	public BigDecimal getQtyToPick() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyToPick);
		return bd != null ? bd : BigDecimal.ZERO;
	}
}