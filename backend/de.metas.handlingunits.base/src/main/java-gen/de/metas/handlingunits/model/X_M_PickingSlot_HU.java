// Generated Model - DO NOT CHANGE
package de.metas.handlingunits.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for M_PickingSlot_HU
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_M_PickingSlot_HU extends org.compiere.model.PO implements I_M_PickingSlot_HU, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 1800807011L;

    /** Standard Constructor */
    public X_M_PickingSlot_HU (final Properties ctx, final int M_PickingSlot_HU_ID, @Nullable final String trxName)
    {
      super (ctx, M_PickingSlot_HU_ID, trxName);
    }

    /** Load Constructor */
    public X_M_PickingSlot_HU (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setM_PickingSlot_HU_ID (final int M_PickingSlot_HU_ID)
	{
		if (M_PickingSlot_HU_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_PickingSlot_HU_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_PickingSlot_HU_ID, M_PickingSlot_HU_ID);
	}

	@Override
	public int getM_PickingSlot_HU_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_PickingSlot_HU_ID);
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
}