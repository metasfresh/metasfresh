// Generated Model - DO NOT CHANGE
package de.metas.externalsystem.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for ExternalSystem_Outbound_Endpoint
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_ExternalSystem_Outbound_Endpoint extends org.compiere.model.PO implements I_ExternalSystem_Outbound_Endpoint, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1515673395L;

    /** Standard Constructor */
    public X_ExternalSystem_Outbound_Endpoint (final Properties ctx, final int ExternalSystem_Outbound_Endpoint_ID, @Nullable final String trxName)
    {
      super (ctx, ExternalSystem_Outbound_Endpoint_ID, trxName);
    }

    /** Load Constructor */
    public X_ExternalSystem_Outbound_Endpoint (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setAuthToken (final @Nullable String AuthToken)
	{
		set_Value (COLUMNNAME_AuthToken, AuthToken);
	}

	@Override
	public String getAuthToken() 
	{
		return get_ValueAsString(COLUMNNAME_AuthToken);
	}

	/** 
	 * AuthType AD_Reference_ID=542017
	 * Reference name: ExternalSystem_Outbound_Endpoint_AuthType
	 */
	public static final int AUTHTYPE_AD_Reference_ID=542017;
	/** Token = Token */
	public static final String AUTHTYPE_Token = "Token";
	/** Basic = Basic */
	public static final String AUTHTYPE_Basic = "Basic";
	/** OAuth = OAuth */
	public static final String AUTHTYPE_OAuth = "OAuth";
	/** SAS = SAS */
	public static final String AUTHTYPE_SAS = "SAS";
	@Override
	public void setAuthType (final String AuthType)
	{
		set_Value (COLUMNNAME_AuthType, AuthType);
	}

	@Override
	public String getAuthType() 
	{
		return get_ValueAsString(COLUMNNAME_AuthType);
	}

	@Override
	public void setClientId (final @Nullable String ClientId)
	{
		set_Value (COLUMNNAME_ClientId, ClientId);
	}

	@Override
	public String getClientId() 
	{
		return get_ValueAsString(COLUMNNAME_ClientId);
	}

	@Override
	public void setClientSecret (final @Nullable String ClientSecret)
	{
		set_Value (COLUMNNAME_ClientSecret, ClientSecret);
	}

	@Override
	public String getClientSecret() 
	{
		return get_ValueAsString(COLUMNNAME_ClientSecret);
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
	public void setExternalSystem_Outbound_Endpoint_ID (final int ExternalSystem_Outbound_Endpoint_ID)
	{
		if (ExternalSystem_Outbound_Endpoint_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_ExternalSystem_Outbound_Endpoint_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_ExternalSystem_Outbound_Endpoint_ID, ExternalSystem_Outbound_Endpoint_ID);
	}

	@Override
	public int getExternalSystem_Outbound_Endpoint_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_ExternalSystem_Outbound_Endpoint_ID);
	}

	@Override
	public void setLoginUsername (final @Nullable String LoginUsername)
	{
		set_Value (COLUMNNAME_LoginUsername, LoginUsername);
	}

	@Override
	public String getLoginUsername() 
	{
		return get_ValueAsString(COLUMNNAME_LoginUsername);
	}

	@Override
	public void setOutboundHttpEP (final String OutboundHttpEP)
	{
		set_Value (COLUMNNAME_OutboundHttpEP, OutboundHttpEP);
	}

	@Override
	public String getOutboundHttpEP() 
	{
		return get_ValueAsString(COLUMNNAME_OutboundHttpEP);
	}

	@Override
	public void setOutboundHttpMethod (final String OutboundHttpMethod)
	{
		set_Value (COLUMNNAME_OutboundHttpMethod, OutboundHttpMethod);
	}

	@Override
	public String getOutboundHttpMethod() 
	{
		return get_ValueAsString(COLUMNNAME_OutboundHttpMethod);
	}

	@Override
	public void setPassword (final @Nullable String Password)
	{
		set_Value (COLUMNNAME_Password, Password);
	}

	@Override
	public String getPassword() 
	{
		return get_ValueAsString(COLUMNNAME_Password);
	}

	@Override
	public void setSasSignature (final @Nullable String SasSignature)
	{
		set_Value (COLUMNNAME_SasSignature, SasSignature);
	}

	@Override
	public String getSasSignature() 
	{
		return get_ValueAsString(COLUMNNAME_SasSignature);
	}

	/** 
	 * Type AD_Reference_ID=542016
	 * Reference name: ExternalSystem_Outbound_Endpoint_EndpointType
	 */
	public static final int TYPE_AD_Reference_ID=542016;
	/** HTTP = HTTP */
	public static final String TYPE_HTTP = "HTTP";
	/** SFTP = SFTP */
	public static final String TYPE_SFTP = "SFTP";
	/** FILE = FILE */
	public static final String TYPE_FILE = "FILE";
	/** EMAIL = EMAIL */
	public static final String TYPE_EMAIL = "EMAIL";
	/** TCP = TCP */
	public static final String TYPE_TCP = "TCP";
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