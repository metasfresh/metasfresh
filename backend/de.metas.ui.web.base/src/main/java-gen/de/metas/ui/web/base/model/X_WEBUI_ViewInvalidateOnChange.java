// Generated Model - DO NOT CHANGE
package de.metas.ui.web.base.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for WEBUI_ViewInvalidateOnChange
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_WEBUI_ViewInvalidateOnChange extends org.compiere.model.PO implements I_WEBUI_ViewInvalidateOnChange, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1872080585L;

    /** Standard Constructor */
    public X_WEBUI_ViewInvalidateOnChange (final Properties ctx, final int WEBUI_ViewInvalidateOnChange_ID, @Nullable final String trxName)
    {
      super (ctx, WEBUI_ViewInvalidateOnChange_ID, trxName);
    }

    /** Load Constructor */
    public X_WEBUI_ViewInvalidateOnChange (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setAD_Table_ID (final int AD_Table_ID)
	{
		if (AD_Table_ID < 1) 
			set_Value (COLUMNNAME_AD_Table_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Table_ID, AD_Table_ID);
	}

	@Override
	public int getAD_Table_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Table_ID);
	}

	@Override
	public void setAD_Window_ID (final int AD_Window_ID)
	{
		if (AD_Window_ID < 1) 
			set_Value (COLUMNNAME_AD_Window_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Window_ID, AD_Window_ID);
	}

	@Override
	public int getAD_Window_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Window_ID);
	}

	@Override
	public void setWEBUI_ViewInvalidateOnChange_ID (final int WEBUI_ViewInvalidateOnChange_ID)
	{
		if (WEBUI_ViewInvalidateOnChange_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_WEBUI_ViewInvalidateOnChange_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_WEBUI_ViewInvalidateOnChange_ID, WEBUI_ViewInvalidateOnChange_ID);
	}

	@Override
	public int getWEBUI_ViewInvalidateOnChange_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_WEBUI_ViewInvalidateOnChange_ID);
	}
}