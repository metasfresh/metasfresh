// Generated Model - DO NOT CHANGE
package de.metas.handlingunits.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for M_PickingSlot_Trx
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_M_PickingSlot_Trx extends org.compiere.model.PO implements I_M_PickingSlot_Trx, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 244760188L;

    /** Standard Constructor */
    public X_M_PickingSlot_Trx (final Properties ctx, final int M_PickingSlot_Trx_ID, @Nullable final String trxName)
    {
      super (ctx, M_PickingSlot_Trx_ID, trxName);
    }

    /** Load Constructor */
    public X_M_PickingSlot_Trx (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
    {
      super (ctx, rs, trxName);
    }


	/** Load Meta Data */
	@Override
	protected org.compiere.model.POInfo initPO(final Properties ctx)
	{
		return org.compiere.model.POInfo.getPOInfo(Table_Name);
	}

	/** 
	 * Action AD_Reference_ID=540497
	 * Reference name: PickingSlot_Trx_Action
	 */
	public static final int ACTION_AD_Reference_ID=540497;
	/** Add_HU_To_Queue = A */
	public static final String ACTION_Add_HU_To_Queue = "A";
	/** Remove_HU_From_Queue = R */
	public static final String ACTION_Remove_HU_From_Queue = "R";
	/** Set_Current_HU = S */
	public static final String ACTION_Set_Current_HU = "S";
	/** Close_Current_HU = C */
	public static final String ACTION_Close_Current_HU = "C";
	@Override
	public void setAction (final @Nullable java.lang.String Action)
	{
		set_Value (COLUMNNAME_Action, Action);
	}

	@Override
	public java.lang.String getAction() 
	{
		return get_ValueAsString(COLUMNNAME_Action);
	}

	@Override
	public void setIsUserAction (final boolean IsUserAction)
	{
		set_Value (COLUMNNAME_IsUserAction, IsUserAction);
	}

	@Override
	public boolean isUserAction() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsUserAction);
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
			set_Value (COLUMNNAME_M_HU_ID, null);
		else 
			set_Value (COLUMNNAME_M_HU_ID, M_HU_ID);
	}

	@Override
	public int getM_HU_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_HU_ID);
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

	@Override
	public void setM_PickingSlot_Trx_ID (final int M_PickingSlot_Trx_ID)
	{
		if (M_PickingSlot_Trx_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_PickingSlot_Trx_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_PickingSlot_Trx_ID, M_PickingSlot_Trx_ID);
	}

	@Override
	public int getM_PickingSlot_Trx_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_PickingSlot_Trx_ID);
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
}