// Generated Model - DO NOT CHANGE
package de.metas.picking.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for M_PickingSlot
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_M_PickingSlot extends org.compiere.model.PO implements I_M_PickingSlot, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 1328306123L;

    /** Standard Constructor */
    public X_M_PickingSlot (final Properties ctx, final int M_PickingSlot_ID, @Nullable final String trxName)
    {
      super (ctx, M_PickingSlot_ID, trxName);
    }

    /** Load Constructor */
    public X_M_PickingSlot (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setC_BPartner_Location_ID (final int C_BPartner_Location_ID)
	{
		if (C_BPartner_Location_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_Location_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_Location_ID, C_BPartner_Location_ID);
	}

	@Override
	public int getC_BPartner_Location_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_Location_ID);
	}

	@Override
	public void setIsDynamic (final boolean IsDynamic)
	{
		set_Value (COLUMNNAME_IsDynamic, IsDynamic);
	}

	@Override
	public boolean isDynamic() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsDynamic);
	}

	@Override
	public void setIsPickingRackSystem (final boolean IsPickingRackSystem)
	{
		set_Value (COLUMNNAME_IsPickingRackSystem, IsPickingRackSystem);
	}

	@Override
	public boolean isPickingRackSystem() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsPickingRackSystem);
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
	public void setM_Picking_Job_ID (final int M_Picking_Job_ID)
	{
		if (M_Picking_Job_ID < 1) 
			set_Value (COLUMNNAME_M_Picking_Job_ID, null);
		else 
			set_Value (COLUMNNAME_M_Picking_Job_ID, M_Picking_Job_ID);
	}

	@Override
	public int getM_Picking_Job_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Picking_Job_ID);
	}

	@Override
	public void setM_PickingSlot_ID (final int M_PickingSlot_ID)
	{
		if (M_PickingSlot_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_PickingSlot_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_PickingSlot_ID, M_PickingSlot_ID);
	}

	@Override
	public int getM_PickingSlot_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_PickingSlot_ID);
	}

	@Override
	public void setM_Warehouse_ID (final int M_Warehouse_ID)
	{
		if (M_Warehouse_ID < 1) 
			set_Value (COLUMNNAME_M_Warehouse_ID, null);
		else 
			set_Value (COLUMNNAME_M_Warehouse_ID, M_Warehouse_ID);
	}

	@Override
	public int getM_Warehouse_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Warehouse_ID);
	}

	@Override
	public void setPickingSlot (final String PickingSlot)
	{
		set_Value (COLUMNNAME_PickingSlot, PickingSlot);
	}

	@Override
	public String getPickingSlot() 
	{
		return get_ValueAsString(COLUMNNAME_PickingSlot);
	}
}