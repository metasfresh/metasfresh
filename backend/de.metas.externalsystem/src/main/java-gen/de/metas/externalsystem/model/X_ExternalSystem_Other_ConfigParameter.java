// Generated Model - DO NOT CHANGE
package de.metas.externalsystem.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for ExternalSystem_Other_ConfigParameter
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_ExternalSystem_Other_ConfigParameter extends org.compiere.model.PO implements I_ExternalSystem_Other_ConfigParameter, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1879854408L;

    /** Standard Constructor */
    public X_ExternalSystem_Other_ConfigParameter (final Properties ctx, final int ExternalSystem_Other_ConfigParameter_ID, @Nullable final String trxName)
    {
      super (ctx, ExternalSystem_Other_ConfigParameter_ID, trxName);
    }

    /** Load Constructor */
    public X_ExternalSystem_Other_ConfigParameter (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public I_ExternalSystem_Config getExternalSystem_Config()
	{
		return get_ValueAsPO(COLUMNNAME_ExternalSystem_Config_ID, I_ExternalSystem_Config.class);
	}

	@Override
	public void setExternalSystem_Config(final I_ExternalSystem_Config ExternalSystem_Config)
	{
		set_ValueFromPO(COLUMNNAME_ExternalSystem_Config_ID, I_ExternalSystem_Config.class, ExternalSystem_Config);
	}

	@Override
	public void setExternalSystem_Config_ID (final int ExternalSystem_Config_ID)
	{
		if (ExternalSystem_Config_ID < 1) 
			set_Value (COLUMNNAME_ExternalSystem_Config_ID, null);
		else 
			set_Value (COLUMNNAME_ExternalSystem_Config_ID, ExternalSystem_Config_ID);
	}

	@Override
	public int getExternalSystem_Config_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_ExternalSystem_Config_ID);
	}

	@Override
	public void setExternalSystem_Other_ConfigParameter_ID (final int ExternalSystem_Other_ConfigParameter_ID)
	{
		if (ExternalSystem_Other_ConfigParameter_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_ExternalSystem_Other_ConfigParameter_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_ExternalSystem_Other_ConfigParameter_ID, ExternalSystem_Other_ConfigParameter_ID);
	}

	@Override
	public int getExternalSystem_Other_ConfigParameter_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_ExternalSystem_Other_ConfigParameter_ID);
	}

	@Override
	public void setName (final String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	@Override
	public String getName()
	{
		return get_ValueAsString(COLUMNNAME_Name);
	}

	@Override
	public void setValue (final @Nullable String Value)
	{
		set_Value (COLUMNNAME_Value, Value);
	}

	@Override
	public String getValue()
	{
		return get_ValueAsString(COLUMNNAME_Value);
	}
}