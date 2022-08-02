// Generated Model - DO NOT CHANGE
package de.metas.handlingunits.model;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for M_HU_Reservation
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_M_HU_Reservation extends org.compiere.model.PO implements I_M_HU_Reservation, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -100137566L;

    /** Standard Constructor */
    public X_M_HU_Reservation (final Properties ctx, final int M_HU_Reservation_ID, @Nullable final String trxName)
    {
      super (ctx, M_HU_Reservation_ID, trxName);
    }

    /** Load Constructor */
    public X_M_HU_Reservation (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setC_BPartner_Customer_ID (final int C_BPartner_Customer_ID)
	{
		if (C_BPartner_Customer_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_Customer_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_Customer_ID, C_BPartner_Customer_ID);
	}

	@Override
	public int getC_BPartner_Customer_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_Customer_ID);
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
	public void setM_HU_Reservation_ID (final int M_HU_Reservation_ID)
	{
		if (M_HU_Reservation_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_HU_Reservation_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_HU_Reservation_ID, M_HU_Reservation_ID);
	}

	@Override
	public int getM_HU_Reservation_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_HU_Reservation_ID);
	}

	@Override
	public de.metas.handlingunits.model.I_M_Picking_Job_Step getM_Picking_Job_Step()
	{
		return get_ValueAsPO(COLUMNNAME_M_Picking_Job_Step_ID, de.metas.handlingunits.model.I_M_Picking_Job_Step.class);
	}

	@Override
	public void setM_Picking_Job_Step(final de.metas.handlingunits.model.I_M_Picking_Job_Step M_Picking_Job_Step)
	{
		set_ValueFromPO(COLUMNNAME_M_Picking_Job_Step_ID, de.metas.handlingunits.model.I_M_Picking_Job_Step.class, M_Picking_Job_Step);
	}

	@Override
	public void setM_Picking_Job_Step_ID (final int M_Picking_Job_Step_ID)
	{
		if (M_Picking_Job_Step_ID < 1) 
			set_Value (COLUMNNAME_M_Picking_Job_Step_ID, null);
		else 
			set_Value (COLUMNNAME_M_Picking_Job_Step_ID, M_Picking_Job_Step_ID);
	}

	@Override
	public int getM_Picking_Job_Step_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Picking_Job_Step_ID);
	}

	@Override
	public void setQtyReserved (final BigDecimal QtyReserved)
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
	public de.metas.handlingunits.model.I_M_HU getVHU()
	{
		return get_ValueAsPO(COLUMNNAME_VHU_ID, de.metas.handlingunits.model.I_M_HU.class);
	}

	@Override
	public void setVHU(final de.metas.handlingunits.model.I_M_HU VHU)
	{
		set_ValueFromPO(COLUMNNAME_VHU_ID, de.metas.handlingunits.model.I_M_HU.class, VHU);
	}

	@Override
	public void setVHU_ID (final int VHU_ID)
	{
		if (VHU_ID < 1) 
			set_Value (COLUMNNAME_VHU_ID, null);
		else 
			set_Value (COLUMNNAME_VHU_ID, VHU_ID);
	}

	@Override
	public int getVHU_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_VHU_ID);
	}
}