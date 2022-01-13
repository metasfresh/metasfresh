// Generated Model - DO NOT CHANGE
package de.metas.ui.web.base.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for WEBUI_Board
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_WEBUI_Board extends org.compiere.model.PO implements I_WEBUI_Board, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -522212501L;

    /** Standard Constructor */
    public X_WEBUI_Board (final Properties ctx, final int WEBUI_Board_ID, @Nullable final String trxName)
    {
      super (ctx, WEBUI_Board_ID, trxName);
    }

    /** Load Constructor */
    public X_WEBUI_Board (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public org.compiere.model.I_AD_Val_Rule getAD_Val_Rule()
	{
		return get_ValueAsPO(COLUMNNAME_AD_Val_Rule_ID, org.compiere.model.I_AD_Val_Rule.class);
	}

	@Override
	public void setAD_Val_Rule(final org.compiere.model.I_AD_Val_Rule AD_Val_Rule)
	{
		set_ValueFromPO(COLUMNNAME_AD_Val_Rule_ID, org.compiere.model.I_AD_Val_Rule.class, AD_Val_Rule);
	}

	@Override
	public void setAD_Val_Rule_ID (final int AD_Val_Rule_ID)
	{
		if (AD_Val_Rule_ID < 1) 
			set_Value (COLUMNNAME_AD_Val_Rule_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Val_Rule_ID, AD_Val_Rule_ID);
	}

	@Override
	public int getAD_Val_Rule_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Val_Rule_ID);
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
	public void setWEBUI_Board_ID (final int WEBUI_Board_ID)
	{
		if (WEBUI_Board_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_WEBUI_Board_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_WEBUI_Board_ID, WEBUI_Board_ID);
	}

	@Override
	public int getWEBUI_Board_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_WEBUI_Board_ID);
	}
}