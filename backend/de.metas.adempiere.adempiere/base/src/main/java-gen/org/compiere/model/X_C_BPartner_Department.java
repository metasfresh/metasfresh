// Generated Model - DO NOT CHANGE
package org.compiere.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_BPartner_Department
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_BPartner_Department extends org.compiere.model.PO implements I_C_BPartner_Department, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1284889818L;

    /** Standard Constructor */
    public X_C_BPartner_Department (final Properties ctx, final int C_BPartner_Department_ID, @Nullable final String trxName)
    {
      super (ctx, C_BPartner_Department_ID, trxName);
    }

    /** Load Constructor */
    public X_C_BPartner_Department (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setC_BPartner_Department_ID (final int C_BPartner_Department_ID)
	{
		if (C_BPartner_Department_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_Department_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_Department_ID, C_BPartner_Department_ID);
	}

	@Override
	public int getC_BPartner_Department_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_Department_ID);
	}

	@Override
	public void setC_BPartner_ID (final int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, C_BPartner_ID);
	}

	@Override
	public int getC_BPartner_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_ID);
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
	public void setName (final java.lang.String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	@Override
	public java.lang.String getName() 
	{
		return get_ValueAsString(COLUMNNAME_Name);
	}

	@Override
	public void setValue (final java.lang.String Value)
	{
		set_Value (COLUMNNAME_Value, Value);
	}

	@Override
	public java.lang.String getValue() 
	{
		return get_ValueAsString(COLUMNNAME_Value);
	}
}