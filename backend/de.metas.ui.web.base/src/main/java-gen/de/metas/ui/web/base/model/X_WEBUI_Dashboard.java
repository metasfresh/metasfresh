// Generated Model - DO NOT CHANGE
package de.metas.ui.web.base.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for WEBUI_Dashboard
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_WEBUI_Dashboard extends org.compiere.model.PO implements I_WEBUI_Dashboard, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 290974613L;

    /** Standard Constructor */
    public X_WEBUI_Dashboard (final Properties ctx, final int WEBUI_Dashboard_ID, @Nullable final String trxName)
    {
      super (ctx, WEBUI_Dashboard_ID, trxName);
    }

    /** Load Constructor */
    public X_WEBUI_Dashboard (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setIsDefault (final boolean IsDefault)
	{
		set_Value (COLUMNNAME_IsDefault, IsDefault);
	}

	@Override
	public boolean isDefault() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsDefault);
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
	public void setWEBUI_Dashboard_ID (final int WEBUI_Dashboard_ID)
	{
		if (WEBUI_Dashboard_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_WEBUI_Dashboard_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_WEBUI_Dashboard_ID, WEBUI_Dashboard_ID);
	}

	@Override
	public int getWEBUI_Dashboard_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_WEBUI_Dashboard_ID);
	}
}