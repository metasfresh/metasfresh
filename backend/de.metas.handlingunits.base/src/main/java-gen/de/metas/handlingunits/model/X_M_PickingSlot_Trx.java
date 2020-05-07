/** Generated Model - DO NOT CHANGE */
package de.metas.handlingunits.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for M_PickingSlot_Trx
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_M_PickingSlot_Trx extends org.compiere.model.PO implements I_M_PickingSlot_Trx, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -968489584L;

    /** Standard Constructor */
    public X_M_PickingSlot_Trx (Properties ctx, int M_PickingSlot_Trx_ID, String trxName)
    {
      super (ctx, M_PickingSlot_Trx_ID, trxName);
      /** if (M_PickingSlot_Trx_ID == 0)
        {
			setIsUserAction (false); // N
			setM_HU_ID (0);
			setM_PickingSlot_ID (0);
			setM_PickingSlot_Trx_ID (0);
        } */
    }

    /** Load Constructor */
    public X_M_PickingSlot_Trx (Properties ctx, ResultSet rs, String trxName)
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
	/** Set Aktion.
		@param Action 
		Zeigt die durchzuführende Aktion an
	  */
	@Override
	public void setAction (java.lang.String Action)
	{

		set_Value (COLUMNNAME_Action, Action);
	}

	/** Get Aktion.
		@return Zeigt die durchzuführende Aktion an
	  */
	@Override
	public java.lang.String getAction () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Action);
	}

	/** Set IsUserAction.
		@param IsUserAction IsUserAction	  */
	@Override
	public void setIsUserAction (boolean IsUserAction)
	{
		set_Value (COLUMNNAME_IsUserAction, Boolean.valueOf(IsUserAction));
	}

	/** Get IsUserAction.
		@return IsUserAction	  */
	@Override
	public boolean isUserAction () 
	{
		Object oo = get_Value(COLUMNNAME_IsUserAction);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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
			set_Value (COLUMNNAME_M_HU_ID, null);
		else 
			set_Value (COLUMNNAME_M_HU_ID, Integer.valueOf(M_HU_ID));
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

	/** Set Picking Slot.
		@param M_PickingSlot_ID Picking Slot	  */
	@Override
	public void setM_PickingSlot_ID (int M_PickingSlot_ID)
	{
		if (M_PickingSlot_ID < 1) 
			set_Value (COLUMNNAME_M_PickingSlot_ID, null);
		else 
			set_Value (COLUMNNAME_M_PickingSlot_ID, Integer.valueOf(M_PickingSlot_ID));
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

	/** Set Picking Slot Transaction.
		@param M_PickingSlot_Trx_ID Picking Slot Transaction	  */
	@Override
	public void setM_PickingSlot_Trx_ID (int M_PickingSlot_Trx_ID)
	{
		if (M_PickingSlot_Trx_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_PickingSlot_Trx_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_PickingSlot_Trx_ID, Integer.valueOf(M_PickingSlot_Trx_ID));
	}

	/** Get Picking Slot Transaction.
		@return Picking Slot Transaction	  */
	@Override
	public int getM_PickingSlot_Trx_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_PickingSlot_Trx_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Verarbeitet.
		@param Processed 
		Checkbox sagt aus, ob der Beleg verarbeitet wurde. 
	  */
	@Override
	public void setProcessed (boolean Processed)
	{
		set_Value (COLUMNNAME_Processed, Boolean.valueOf(Processed));
	}

	/** Get Verarbeitet.
		@return Checkbox sagt aus, ob der Beleg verarbeitet wurde. 
	  */
	@Override
	public boolean isProcessed () 
	{
		Object oo = get_Value(COLUMNNAME_Processed);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}
}