/** Generated Model - DO NOT CHANGE */
package de.metas.handlingunits.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for M_PickingSlot_HU
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_M_PickingSlot_HU extends org.compiere.model.PO implements I_M_PickingSlot_HU, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -126175437L;

    /** Standard Constructor */
    public X_M_PickingSlot_HU (Properties ctx, int M_PickingSlot_HU_ID, String trxName)
    {
      super (ctx, M_PickingSlot_HU_ID, trxName);
      /** if (M_PickingSlot_HU_ID == 0)
        {
			setM_HU_ID (0);
			setM_PickingSlot_HU_ID (0);
			setM_PickingSlot_ID (0);
        } */
    }

    /** Load Constructor */
    public X_M_PickingSlot_HU (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }


    /** Load Meta Data */
    @Override
    protected org.compiere.model.POInfo initPO (Properties ctx)
    {
      org.compiere.model.POInfo poi = org.compiere.model.POInfo.getPOInfo (ctx, Table_Name, get_TrxName());
      return poi;
    }

	@Override
	public de.metas.handlingunits.model.I_M_HU getM_HU() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_HU_ID, de.metas.handlingunits.model.I_M_HU.class);
	}

	@Override
	public void setM_HU(de.metas.handlingunits.model.I_M_HU M_HU)
	{
		set_ValueFromPO(COLUMNNAME_M_HU_ID, de.metas.handlingunits.model.I_M_HU.class, M_HU);
	}

	/** Set Handling Units.
		@param M_HU_ID Handling Units	  */
	@Override
	public void setM_HU_ID (int M_HU_ID)
	{
		if (M_HU_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_HU_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_HU_ID, Integer.valueOf(M_HU_ID));
	}

	/** Get Handling Units.
		@return Handling Units	  */
	@Override
	public int getM_HU_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_HU_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Picking Slot HU.
		@param M_PickingSlot_HU_ID Picking Slot HU	  */
	@Override
	public void setM_PickingSlot_HU_ID (int M_PickingSlot_HU_ID)
	{
		if (M_PickingSlot_HU_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_PickingSlot_HU_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_PickingSlot_HU_ID, Integer.valueOf(M_PickingSlot_HU_ID));
	}

	/** Get Picking Slot HU.
		@return Picking Slot HU	  */
	@Override
	public int getM_PickingSlot_HU_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_PickingSlot_HU_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Picking Slot.
		@param M_PickingSlot_ID Picking Slot	  */
	@Override
	public void setM_PickingSlot_ID (int M_PickingSlot_ID)
	{
		if (M_PickingSlot_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_PickingSlot_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_PickingSlot_ID, Integer.valueOf(M_PickingSlot_ID));
	}

	/** Get Picking Slot.
		@return Picking Slot	  */
	@Override
	public int getM_PickingSlot_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_PickingSlot_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}