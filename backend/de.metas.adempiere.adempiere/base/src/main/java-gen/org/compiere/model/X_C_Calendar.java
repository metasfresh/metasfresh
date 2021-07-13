/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_Calendar
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_Calendar extends org.compiere.model.PO implements I_C_Calendar, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1401646905L;

    /** Standard Constructor */
    public X_C_Calendar (Properties ctx, int C_Calendar_ID, String trxName)
    {
      super (ctx, C_Calendar_ID, trxName);
    }

    /** Load Constructor */
    public X_C_Calendar (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }


	/** Load Meta Data */
	@Override
	protected org.compiere.model.POInfo initPO(Properties ctx)
	{
		return org.compiere.model.POInfo.getPOInfo(Table_Name);
	}

	@Override
	public void setC_Calendar_ID (int C_Calendar_ID)
	{
		if (C_Calendar_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Calendar_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Calendar_ID, Integer.valueOf(C_Calendar_ID));
	}

	@Override
	public int getC_Calendar_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Calendar_ID);
	}

	@Override
	public void setDescription (java.lang.String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	@Override
	public java.lang.String getDescription() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Description);
	}

	@Override
	public void setIsDefault (boolean IsDefault)
	{
		set_Value (COLUMNNAME_IsDefault, Boolean.valueOf(IsDefault));
	}

	@Override
	public boolean isDefault() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsDefault);
	}

	@Override
	public void setName (java.lang.String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	@Override
	public java.lang.String getName() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Name);
	}
}