// Generated Model - DO NOT CHANGE
package de.metas.externalsystem.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for ExternalSystem_Config
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_ExternalSystem_Config extends org.compiere.model.PO implements I_ExternalSystem_Config, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 483096565L;

    /** Standard Constructor */
    public X_ExternalSystem_Config (final Properties ctx, final int ExternalSystem_Config_ID, @Nullable final String trxName)
    {
      super (ctx, ExternalSystem_Config_ID, trxName);
    }

    /** Load Constructor */
    public X_ExternalSystem_Config (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setCamelURL (final java.lang.String CamelURL)
	{
		set_Value (COLUMNNAME_CamelURL, CamelURL);
	}

	@Override
	public java.lang.String getCamelURL() 
	{
		return get_ValueAsString(COLUMNNAME_CamelURL);
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
	public void setExternalSystem_Config_ID (final int ExternalSystem_Config_ID)
	{
		if (ExternalSystem_Config_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_ExternalSystem_Config_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_ExternalSystem_Config_ID, ExternalSystem_Config_ID);
	}

	@Override
	public int getExternalSystem_Config_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_ExternalSystem_Config_ID);
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

	/** 
	 * Type AD_Reference_ID=541255
	 * Reference name: Type
	 */
	public static final int TYPE_AD_Reference_ID=541255;
	/** Alberta = A */
	public static final String TYPE_Alberta = "A";
	@Override
	public void setType (final java.lang.String Type)
	{
		set_Value (COLUMNNAME_Type, Type);
	}

	@Override
	public java.lang.String getType() 
	{
		return get_ValueAsString(COLUMNNAME_Type);
	}
}