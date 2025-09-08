// Generated Model - DO NOT CHANGE
package org.compiere.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for M_Warehouse_Group
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_M_Warehouse_Group extends org.compiere.model.PO implements I_M_Warehouse_Group, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1409666171L;

    /** Standard Constructor */
    public X_M_Warehouse_Group (final Properties ctx, final int M_Warehouse_Group_ID, @Nullable final String trxName)
    {
      super (ctx, M_Warehouse_Group_ID, trxName);
    }

    /** Load Constructor */
    public X_M_Warehouse_Group (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setDescription (final @Nullable java.lang.String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	@Override
	public java.lang.String getDescription() 
	{
		return get_ValueAsString(COLUMNNAME_Description);
	}

	@Override
	public void setM_Warehouse_Group_ID (final int M_Warehouse_Group_ID)
	{
		if (M_Warehouse_Group_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Warehouse_Group_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Warehouse_Group_ID, M_Warehouse_Group_ID);
	}

	@Override
	public int getM_Warehouse_Group_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Warehouse_Group_ID);
	}

	@Override
	public void setName (final java.lang.String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	@Override
	public java.lang.String getName() 
	{
		return get_ValueAsString(COLUMNNAME_Name);
	}
}