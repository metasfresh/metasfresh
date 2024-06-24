// Generated Model - DO NOT CHANGE
package org.compiere.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_Workplace_User_Assign
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_Workplace_User_Assign extends org.compiere.model.PO implements I_C_Workplace_User_Assign, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 1619807920L;

    /** Standard Constructor */
    public X_C_Workplace_User_Assign (final Properties ctx, final int C_Workplace_User_Assign_ID, @Nullable final String trxName)
    {
      super (ctx, C_Workplace_User_Assign_ID, trxName);
    }

    /** Load Constructor */
    public X_C_Workplace_User_Assign (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setAD_User_ID (final int AD_User_ID)
	{
		if (AD_User_ID < 0) 
			set_Value (COLUMNNAME_AD_User_ID, null);
		else 
			set_Value (COLUMNNAME_AD_User_ID, AD_User_ID);
	}

	@Override
	public int getAD_User_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_User_ID);
	}

	@Override
	public org.compiere.model.I_C_Workplace getC_Workplace()
	{
		return get_ValueAsPO(COLUMNNAME_C_Workplace_ID, org.compiere.model.I_C_Workplace.class);
	}

	@Override
	public void setC_Workplace(final org.compiere.model.I_C_Workplace C_Workplace)
	{
		set_ValueFromPO(COLUMNNAME_C_Workplace_ID, org.compiere.model.I_C_Workplace.class, C_Workplace);
	}

	@Override
	public void setC_Workplace_ID (final int C_Workplace_ID)
	{
		if (C_Workplace_ID < 1) 
			set_Value (COLUMNNAME_C_Workplace_ID, null);
		else 
			set_Value (COLUMNNAME_C_Workplace_ID, C_Workplace_ID);
	}

	@Override
	public int getC_Workplace_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Workplace_ID);
	}

	@Override
	public void setC_Workplace_User_Assign_ID (final int C_Workplace_User_Assign_ID)
	{
		if (C_Workplace_User_Assign_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Workplace_User_Assign_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Workplace_User_Assign_ID, C_Workplace_User_Assign_ID);
	}

	@Override
	public int getC_Workplace_User_Assign_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Workplace_User_Assign_ID);
	}
}