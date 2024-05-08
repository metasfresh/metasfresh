// Generated Model - DO NOT CHANGE
package de.metas.picking.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for M_Picking_Config_V2
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_M_Picking_Config_V2 extends org.compiere.model.PO implements I_M_Picking_Config_V2, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 502645024L;

    /** Standard Constructor */
    public X_M_Picking_Config_V2 (final Properties ctx, final int M_Picking_Config_V2_ID, @Nullable final String trxName)
    {
      super (ctx, M_Picking_Config_V2_ID, trxName);
    }

    /** Load Constructor */
    public X_M_Picking_Config_V2 (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setIsConsiderAttributes (final boolean IsConsiderAttributes)
	{
		set_Value (COLUMNNAME_IsConsiderAttributes, IsConsiderAttributes);
	}

	@Override
	public boolean isConsiderAttributes() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsConsiderAttributes);
	}

	@Override
	public void setIsPickingReviewRequired (final boolean IsPickingReviewRequired)
	{
		set_Value (COLUMNNAME_IsPickingReviewRequired, IsPickingReviewRequired);
	}

	@Override
	public boolean isPickingReviewRequired() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsPickingReviewRequired);
	}

	@Override
	public void setM_Picking_Config_V2_ID (final int M_Picking_Config_V2_ID)
	{
		if (M_Picking_Config_V2_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Picking_Config_V2_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Picking_Config_V2_ID, M_Picking_Config_V2_ID);
	}

	@Override
	public int getM_Picking_Config_V2_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Picking_Config_V2_ID);
	}
}