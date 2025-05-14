// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for Mobile_Application
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_Mobile_Application extends org.compiere.model.PO implements I_Mobile_Application, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -744557906L;

    /** Standard Constructor */
    public X_Mobile_Application (final Properties ctx, final int Mobile_Application_ID, @Nullable final String trxName)
    {
      super (ctx, Mobile_Application_ID, trxName);
    }

    /** Load Constructor */
    public X_Mobile_Application (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setIsShowInMainMenu (final boolean IsShowInMainMenu)
	{
		set_Value (COLUMNNAME_IsShowInMainMenu, IsShowInMainMenu);
	}

	@Override
	public boolean isShowInMainMenu() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsShowInMainMenu);
	}

	@Override
	public void setMobile_Application_ID (final int Mobile_Application_ID)
	{
		if (Mobile_Application_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Mobile_Application_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Mobile_Application_ID, Mobile_Application_ID);
	}

	@Override
	public int getMobile_Application_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Mobile_Application_ID);
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