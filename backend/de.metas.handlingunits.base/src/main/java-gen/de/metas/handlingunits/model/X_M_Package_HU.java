// Generated Model - DO NOT CHANGE
package de.metas.handlingunits.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for M_Package_HU
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_M_Package_HU extends org.compiere.model.PO implements I_M_Package_HU, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 576149678L;

    /** Standard Constructor */
    public X_M_Package_HU (final Properties ctx, final int M_Package_HU_ID, @Nullable final String trxName)
    {
      super (ctx, M_Package_HU_ID, trxName);
    }

    /** Load Constructor */
    public X_M_Package_HU (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public de.metas.handlingunits.model.I_M_HU getM_HU()
	{
		return get_ValueAsPO(COLUMNNAME_M_HU_ID, de.metas.handlingunits.model.I_M_HU.class);
	}

	@Override
	public void setM_HU(final de.metas.handlingunits.model.I_M_HU M_HU)
	{
		set_ValueFromPO(COLUMNNAME_M_HU_ID, de.metas.handlingunits.model.I_M_HU.class, M_HU);
	}

	@Override
	public void setM_HU_ID (final int M_HU_ID)
	{
		if (M_HU_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_HU_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_HU_ID, M_HU_ID);
	}

	@Override
	public int getM_HU_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_HU_ID);
	}

	@Override
	public void setM_Package_HU_ID (final int M_Package_HU_ID)
	{
		if (M_Package_HU_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Package_HU_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Package_HU_ID, M_Package_HU_ID);
	}

	@Override
	public int getM_Package_HU_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Package_HU_ID);
	}

	@Override
	public org.compiere.model.I_M_Package getM_Package()
	{
		return get_ValueAsPO(COLUMNNAME_M_Package_ID, org.compiere.model.I_M_Package.class);
	}

	@Override
	public void setM_Package(final org.compiere.model.I_M_Package M_Package)
	{
		set_ValueFromPO(COLUMNNAME_M_Package_ID, org.compiere.model.I_M_Package.class, M_Package);
	}

	@Override
	public void setM_Package_ID (final int M_Package_ID)
	{
		if (M_Package_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Package_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Package_ID, M_Package_ID);
	}

	@Override
	public int getM_Package_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Package_ID);
	}

	@Override
	public void setM_PickingSlot_ID (final int M_PickingSlot_ID)
	{
		if (M_PickingSlot_ID < 1) 
			set_Value (COLUMNNAME_M_PickingSlot_ID, null);
		else 
			set_Value (COLUMNNAME_M_PickingSlot_ID, M_PickingSlot_ID);
	}

	@Override
	public int getM_PickingSlot_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_PickingSlot_ID);
	}
}