/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for M_Warehouse_PickingGroup
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_M_Warehouse_PickingGroup extends org.compiere.model.PO implements I_M_Warehouse_PickingGroup, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1480135430L;

    /** Standard Constructor */
    public X_M_Warehouse_PickingGroup (Properties ctx, int M_Warehouse_PickingGroup_ID, String trxName)
    {
      super (ctx, M_Warehouse_PickingGroup_ID, trxName);
      /** if (M_Warehouse_PickingGroup_ID == 0)
        {
			setM_Warehouse_PickingGroup_ID (0);
			setName (null);
        } */
    }

    /** Load Constructor */
    public X_M_Warehouse_PickingGroup (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Beschreibung.
		@param Description Beschreibung	  */
	@Override
	public void setDescription (java.lang.String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	/** Get Beschreibung.
		@return Beschreibung	  */
	@Override
	public java.lang.String getDescription () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Description);
	}

	/** Set Warehouse Picking Group.
		@param M_Warehouse_PickingGroup_ID Warehouse Picking Group	  */
	@Override
	public void setM_Warehouse_PickingGroup_ID (int M_Warehouse_PickingGroup_ID)
	{
		if (M_Warehouse_PickingGroup_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Warehouse_PickingGroup_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Warehouse_PickingGroup_ID, Integer.valueOf(M_Warehouse_PickingGroup_ID));
	}

	/** Get Warehouse Picking Group.
		@return Warehouse Picking Group	  */
	@Override
	public int getM_Warehouse_PickingGroup_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Warehouse_PickingGroup_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Name.
		@param Name 
		Alphanumeric identifier of the entity
	  */
	@Override
	public void setName (java.lang.String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	/** Get Name.
		@return Alphanumeric identifier of the entity
	  */
	@Override
	public java.lang.String getName () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Name);
	}
}