// Generated Model - DO NOT CHANGE
package de.metas.externalsystem.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for ExternalSystem_Service
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_ExternalSystem_Service extends org.compiere.model.PO implements I_ExternalSystem_Service, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1308494502L;

    /** Standard Constructor */
    public X_ExternalSystem_Service (final Properties ctx, final int ExternalSystem_Service_ID, @Nullable final String trxName)
    {
      super (ctx, ExternalSystem_Service_ID, trxName);
    }

    /** Load Constructor */
    public X_ExternalSystem_Service (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setDescription (final @Nullable String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	@Override
	public String getDescription()
	{
		return get_ValueAsString(COLUMNNAME_Description);
	}

	@Override
	public void setDisableCommand (final @Nullable String DisableCommand)
	{
		set_Value (COLUMNNAME_DisableCommand, DisableCommand);
	}

	@Override
	public String getDisableCommand()
	{
		return get_ValueAsString(COLUMNNAME_DisableCommand);
	}

	@Override
	public void setEnableCommand (final @Nullable String EnableCommand)
	{
		set_Value (COLUMNNAME_EnableCommand, EnableCommand);
	}

	@Override
	public String getEnableCommand()
	{
		return get_ValueAsString(COLUMNNAME_EnableCommand);
	}

	@Override
	public void setExternalSystem_Service_ID (final int ExternalSystem_Service_ID)
	{
		if (ExternalSystem_Service_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_ExternalSystem_Service_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_ExternalSystem_Service_ID, ExternalSystem_Service_ID);
	}

	@Override
	public int getExternalSystem_Service_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_ExternalSystem_Service_ID);
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

	/** 
	 * Type AD_Reference_ID=541255
	 * Reference name: Type
	 */
	public static final int TYPE_AD_Reference_ID=541255;
	/** Alberta = A */
	public static final String TYPE_Alberta = "A";
	/** Shopware6 = S6 */
	public static final String TYPE_Shopware6 = "S6";
	/** Other = Other */
	public static final String TYPE_Other = "Other";
	/** WooCommerce = WOO */
	public static final String TYPE_WooCommerce = "WOO";
	/** RabbitMQ REST API = RabbitMQ */
	public static final String TYPE_RabbitMQRESTAPI = "RabbitMQ";
	/** GRSSignum = GRS */
	public static final String TYPE_GRSSignum = "GRS";
	@Override
	public void setType (final String Type)
	{
		set_Value (COLUMNNAME_Type, Type);
	}

	@Override
	public String getType()
	{
		return get_ValueAsString(COLUMNNAME_Type);
	}

	@Override
	public void setValue (final String Value)
	{
		set_Value (COLUMNNAME_Value, Value);
	}

	@Override
	public String getValue()
	{
		return get_ValueAsString(COLUMNNAME_Value);
	}
}