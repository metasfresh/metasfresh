// Generated Model - DO NOT CHANGE
package org.compiere.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_Workplace
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_Workplace extends org.compiere.model.PO implements I_C_Workplace, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 952651378L;

    /** Standard Constructor */
    public X_C_Workplace (final Properties ctx, final int C_Workplace_ID, @Nullable final String trxName)
    {
      super (ctx, C_Workplace_ID, trxName);
    }

    /** Load Constructor */
    public X_C_Workplace (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setC_Workplace_ID (final int C_Workplace_ID)
	{
		if (C_Workplace_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Workplace_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Workplace_ID, C_Workplace_ID);
	}

	@Override
	public int getC_Workplace_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Workplace_ID);
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