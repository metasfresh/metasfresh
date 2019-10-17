/** Generated Model - DO NOT CHANGE */
package de.metas.picking.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for M_Picking_Config_V2
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_M_Picking_Config_V2 extends org.compiere.model.PO implements I_M_Picking_Config_V2, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -468603884L;

    /** Standard Constructor */
    public X_M_Picking_Config_V2 (Properties ctx, int M_Picking_Config_V2_ID, String trxName)
    {
      super (ctx, M_Picking_Config_V2_ID, trxName);
      /** if (M_Picking_Config_V2_ID == 0)
        {
			setIsConsiderAttributes (false); // N
			setIsPickingReviewRequired (true); // Y
			setM_Picking_Config_V2_ID (0);
        } */
    }

    /** Load Constructor */
    public X_M_Picking_Config_V2 (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Attribute berücksichtigen.
		@param IsConsiderAttributes Attribute berücksichtigen	  */
	@Override
	public void setIsConsiderAttributes (boolean IsConsiderAttributes)
	{
		set_Value (COLUMNNAME_IsConsiderAttributes, Boolean.valueOf(IsConsiderAttributes));
	}

	/** Get Attribute berücksichtigen.
		@return Attribute berücksichtigen	  */
	@Override
	public boolean isConsiderAttributes () 
	{
		Object oo = get_Value(COLUMNNAME_IsConsiderAttributes);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Kommissionierprüfung erforderlich.
		@param IsPickingReviewRequired Kommissionierprüfung erforderlich	  */
	@Override
	public void setIsPickingReviewRequired (boolean IsPickingReviewRequired)
	{
		set_Value (COLUMNNAME_IsPickingReviewRequired, Boolean.valueOf(IsPickingReviewRequired));
	}

	/** Get Kommissionierprüfung erforderlich.
		@return Kommissionierprüfung erforderlich	  */
	@Override
	public boolean isPickingReviewRequired () 
	{
		Object oo = get_Value(COLUMNNAME_IsPickingReviewRequired);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Kommissionierkonfiguration (V2).
		@param M_Picking_Config_V2_ID Kommissionierkonfiguration (V2)	  */
	@Override
	public void setM_Picking_Config_V2_ID (int M_Picking_Config_V2_ID)
	{
		if (M_Picking_Config_V2_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Picking_Config_V2_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Picking_Config_V2_ID, Integer.valueOf(M_Picking_Config_V2_ID));
	}

	/** Get Kommissionierkonfiguration (V2).
		@return Kommissionierkonfiguration (V2)	  */
	@Override
	public int getM_Picking_Config_V2_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Picking_Config_V2_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}