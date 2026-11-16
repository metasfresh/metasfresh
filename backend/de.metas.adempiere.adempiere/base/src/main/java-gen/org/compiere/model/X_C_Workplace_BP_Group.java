// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for C_Workplace_BP_Group
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_Workplace_BP_Group extends org.compiere.model.PO implements I_C_Workplace_BP_Group, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -474063636L;

    /** Standard Constructor */
    public X_C_Workplace_BP_Group (final Properties ctx, final int C_Workplace_BP_Group_ID, @Nullable final String trxName)
    {
      super (ctx, C_Workplace_BP_Group_ID, trxName);
    }

    /** Load Constructor */
    public X_C_Workplace_BP_Group (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setC_BP_Group_ID (final int C_BP_Group_ID)
	{
		if (C_BP_Group_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BP_Group_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BP_Group_ID, C_BP_Group_ID);
	}

	@Override
	public int getC_BP_Group_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BP_Group_ID);
	}

	@Override
	public void setC_Workplace_BP_Group_ID (final int C_Workplace_BP_Group_ID)
	{
		if (C_Workplace_BP_Group_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Workplace_BP_Group_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Workplace_BP_Group_ID, C_Workplace_BP_Group_ID);
	}

	@Override
	public int getC_Workplace_BP_Group_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Workplace_BP_Group_ID);
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
}