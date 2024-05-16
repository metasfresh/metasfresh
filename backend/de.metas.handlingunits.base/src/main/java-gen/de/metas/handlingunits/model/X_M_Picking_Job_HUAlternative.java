// Generated Model - DO NOT CHANGE
package de.metas.handlingunits.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for M_Picking_Job_HUAlternative
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_M_Picking_Job_HUAlternative extends org.compiere.model.PO implements I_M_Picking_Job_HUAlternative, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1560565620L;

    /** Standard Constructor */
    public X_M_Picking_Job_HUAlternative (final Properties ctx, final int M_Picking_Job_HUAlternative_ID, @Nullable final String trxName)
    {
      super (ctx, M_Picking_Job_HUAlternative_ID, trxName);
    }

    /** Load Constructor */
    public X_M_Picking_Job_HUAlternative (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setM_Picking_Job_HUAlternative_ID (final int M_Picking_Job_HUAlternative_ID)
	{
		if (M_Picking_Job_HUAlternative_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Picking_Job_HUAlternative_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Picking_Job_HUAlternative_ID, M_Picking_Job_HUAlternative_ID);
	}

	@Override
	public int getM_Picking_Job_HUAlternative_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Picking_Job_HUAlternative_ID);
	}

	@Override
	public de.metas.handlingunits.model.I_M_Picking_Job getM_Picking_Job()
	{
		return get_ValueAsPO(COLUMNNAME_M_Picking_Job_ID, de.metas.handlingunits.model.I_M_Picking_Job.class);
	}

	@Override
	public void setM_Picking_Job(final de.metas.handlingunits.model.I_M_Picking_Job M_Picking_Job)
	{
		set_ValueFromPO(COLUMNNAME_M_Picking_Job_ID, de.metas.handlingunits.model.I_M_Picking_Job.class, M_Picking_Job);
	}

	@Override
	public void setM_Picking_Job_ID (final int M_Picking_Job_ID)
	{
		if (M_Picking_Job_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Picking_Job_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Picking_Job_ID, M_Picking_Job_ID);
	}

	@Override
	public int getM_Picking_Job_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Picking_Job_ID);
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
	public de.metas.handlingunits.model.I_M_HU getPickFrom_HU()
	{
		return get_ValueAsPO(COLUMNNAME_PickFrom_HU_ID, de.metas.handlingunits.model.I_M_HU.class);
	}

	@Override
	public void setPickFrom_HU(final de.metas.handlingunits.model.I_M_HU PickFrom_HU)
	{
		set_ValueFromPO(COLUMNNAME_PickFrom_HU_ID, de.metas.handlingunits.model.I_M_HU.class, PickFrom_HU);
	}

	@Override
	public void setPickFrom_HU_ID (final int PickFrom_HU_ID)
	{
		if (PickFrom_HU_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_PickFrom_HU_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_PickFrom_HU_ID, PickFrom_HU_ID);
	}

	@Override
	public int getPickFrom_HU_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_PickFrom_HU_ID);
	}

	@Override
	public void setPickFrom_Locator_ID (final int PickFrom_Locator_ID)
	{
		if (PickFrom_Locator_ID < 1) 
			set_Value (COLUMNNAME_PickFrom_Locator_ID, null);
		else 
			set_Value (COLUMNNAME_PickFrom_Locator_ID, PickFrom_Locator_ID);
	}

	@Override
	public int getPickFrom_Locator_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_PickFrom_Locator_ID);
	}

	@Override
	public void setPickFrom_Warehouse_ID (final int PickFrom_Warehouse_ID)
	{
		if (PickFrom_Warehouse_ID < 1) 
			set_Value (COLUMNNAME_PickFrom_Warehouse_ID, null);
		else 
			set_Value (COLUMNNAME_PickFrom_Warehouse_ID, PickFrom_Warehouse_ID);
	}

	@Override
	public int getPickFrom_Warehouse_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_PickFrom_Warehouse_ID);
	}

	@Override
	public void setQtyAvailable (final BigDecimal QtyAvailable)
	{
		set_Value (COLUMNNAME_QtyAvailable, QtyAvailable);
	}

	@Override
	public BigDecimal getQtyAvailable() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyAvailable);
		return bd != null ? bd : BigDecimal.ZERO;
	}
}