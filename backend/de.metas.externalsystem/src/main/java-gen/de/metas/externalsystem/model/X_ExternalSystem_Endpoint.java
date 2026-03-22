// Generated Model - DO NOT CHANGE
package de.metas.externalsystem.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for ExternalSystem_Endpoint
 *  @author metasfresh (generated)
 */
@SuppressWarnings("unused")
public class X_ExternalSystem_Endpoint extends org.compiere.model.PO implements I_ExternalSystem_Endpoint, org.compiere.model.I_Persistent
{

	private static final long serialVersionUID = -551340614L;

    /** Standard Constructor */
    public X_ExternalSystem_Endpoint (final Properties ctx, final int ExternalSystem_Endpoint_ID, @Nullable final String trxName)
    {
      super (ctx, ExternalSystem_Endpoint_ID, trxName);
    }

    /** Load Constructor */
    public X_ExternalSystem_Endpoint (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setAuthToken (final @Nullable java.lang.String AuthToken)
	{
		set_Value (COLUMNNAME_AuthToken, AuthToken);
	}

	@Override
	public java.lang.String getAuthToken()
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
	public void setAuthType (final java.lang.String AuthType)
	{
		set_Value (COLUMNNAME_AuthType, AuthType);
	}

	@Override
	public java.lang.String getAuthType()
	{
		return get_ValueAsString(COLUMNNAME_AuthType);
	}

	@Override
	public void setClientId (final @Nullable java.lang.String ClientId)
	{
		set_Value (COLUMNNAME_ClientId, ClientId);
	}

	@Override
	public java.lang.String getClientId()
	{
		return get_ValueAsString(COLUMNNAME_ClientId);
	}

	@Override
	public void setClientSecret (final @Nullable java.lang.String ClientSecret)
	{
		set_Value (COLUMNNAME_ClientSecret, ClientSecret);
	}

	@Override
	public java.lang.String getClientSecret()
	{
		return get_ValueAsString(COLUMNNAME_ClientSecret);
	}

	/**
	 * ContentType AD_Reference_ID=542065
	 * Reference name: ExternalSystemOutboundEndpointContentType
	 */
	public static final int CONTENTTYPE_AD_Reference_ID=542065;
	/** application_json = application/json */
	public static final String CONTENTTYPE_Application_json = "application/json";
	/** application_xml = application/xml */
	public static final String CONTENTTYPE_Application_xml = "application/xml";
	/** text_xml = text/xml */
	public static final String CONTENTTYPE_Text_xml = "text/xml";
	@Override
	public void setContentType (final @Nullable java.lang.String ContentType)
	{
		set_Value (COLUMNNAME_ContentType, ContentType);
	}

	@Override
	public java.lang.String getContentType()
	{
		return get_ValueAsString(COLUMNNAME_ContentType);
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
	public void setExternalSystem_Endpoint_ID (final int ExternalSystem_Endpoint_ID)
	{
		if (ExternalSystem_Endpoint_ID < 1)
			set_ValueNoCheck (COLUMNNAME_ExternalSystem_Endpoint_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_ExternalSystem_Endpoint_ID, ExternalSystem_Endpoint_ID);
	}

	@Override
	public int getExternalSystem_Endpoint_ID()
	{
		return get_ValueAsInt(COLUMNNAME_ExternalSystem_Endpoint_ID);
	}

	@Override
	public void setLoginUsername (final @Nullable java.lang.String LoginUsername)
	{
		set_Value (COLUMNNAME_LoginUsername, LoginUsername);
	}

	@Override
	public java.lang.String getLoginUsername()
	{
		return get_ValueAsString(COLUMNNAME_LoginUsername);
	}

	@Override
	public void setOutboundHttpEP (final @Nullable java.lang.String OutboundHttpEP)
	{
		set_Value (COLUMNNAME_OutboundHttpEP, OutboundHttpEP);
	}

	@Override
	public java.lang.String getOutboundHttpEP()
	{
		return get_ValueAsString(COLUMNNAME_OutboundHttpEP);
	}

	/**
	 * OutboundHttpMethod AD_Reference_ID=541306
	 * Reference name: Http_Method
	 */
	public static final int OUTBOUNDHTTPMETHOD_AD_Reference_ID=541306;
	/** GET = GET */
	public static final String OUTBOUNDHTTPMETHOD_GET = "GET";
	/** POST = POST */
	public static final String OUTBOUNDHTTPMETHOD_POST = "POST";
	/** PUT = PUT */
	public static final String OUTBOUNDHTTPMETHOD_PUT = "PUT";
	/** DELETE = DELETE */
	public static final String OUTBOUNDHTTPMETHOD_DELETE = "DELETE";
	/** OPTIONS = OPTIONS */
	public static final String OUTBOUNDHTTPMETHOD_OPTIONS = "OPTIONS";
	/** PATCH = PATCH */
	public static final String OUTBOUNDHTTPMETHOD_PATCH = "PATCH";
	/** HEAD = HEAD */
	public static final String OUTBOUNDHTTPMETHOD_HEAD = "HEAD";
	/** TRACE = TRACE */
	public static final String OUTBOUNDHTTPMETHOD_TRACE = "TRACE";
	/** CONNECT = CONNECT */
	public static final String OUTBOUNDHTTPMETHOD_CONNECT = "CONNECT";
	@Override
	public void setOutboundHttpMethod (final @Nullable java.lang.String OutboundHttpMethod)
	{
		set_Value (COLUMNNAME_OutboundHttpMethod, OutboundHttpMethod);
	}

	@Override
	public java.lang.String getOutboundHttpMethod()
	{
		return get_ValueAsString(COLUMNNAME_OutboundHttpMethod);
	}

	@Override
	public void setPassword (final @Nullable java.lang.String Password)
	{
		set_Value (COLUMNNAME_Password, Password);
	}

	@Override
	public java.lang.String getPassword()
	{
		return get_ValueAsString(COLUMNNAME_Password);
	}

	@Override
	public void setSasSignature (final @Nullable java.lang.String SasSignature)
	{
		set_Value (COLUMNNAME_SasSignature, SasSignature);
	}

	@Override
	public java.lang.String getSasSignature()
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
	public void setType (final java.lang.String Type)
	{
		set_Value (COLUMNNAME_Type, Type);
	}

	@Override
	public java.lang.String getType()
	{
		return get_ValueAsString(COLUMNNAME_Type);
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

	/**
	 * TransportType AD_Reference_ID=542069
	 * Reference name: ExternalSystem_Endpoint_TransportType
	 */
	public static final int TRANSPORTTYPE_AD_Reference_ID=542069;
	/** HTTP = HTTP */
	public static final String TRANSPORTTYPE_HTTP = "HTTP";
	/** SFTP = SFTP */
	public static final String TRANSPORTTYPE_SFTP = "SFTP";
	@Override
	public void setTransportType (final java.lang.String TransportType)
	{
		set_Value (COLUMNNAME_TransportType, TransportType);
	}

	@Override
	public java.lang.String getTransportType()
	{
		return get_ValueAsString(COLUMNNAME_TransportType);
	}

	@Override
	public void setSftpHost (final @Nullable java.lang.String SftpHost)
	{
		set_Value (COLUMNNAME_SftpHost, SftpHost);
	}

	@Override
	public java.lang.String getSftpHost()
	{
		return get_ValueAsString(COLUMNNAME_SftpHost);
	}

	@Override
	public void setSftpPort (final int SftpPort)
	{
		set_Value (COLUMNNAME_SftpPort, SftpPort);
	}

	@Override
	public int getSftpPort()
	{
		return get_ValueAsInt(COLUMNNAME_SftpPort);
	}

	@Override
	public void setSftpUsername (final @Nullable java.lang.String SftpUsername)
	{
		set_Value (COLUMNNAME_SftpUsername, SftpUsername);
	}

	@Override
	public java.lang.String getSftpUsername()
	{
		return get_ValueAsString(COLUMNNAME_SftpUsername);
	}

	/**
	 * SftpAuthType AD_Reference_ID=542070
	 * Reference name: ExternalSystem_Endpoint_SftpAuthType
	 */
	public static final int SFTPAUTHTYPE_AD_Reference_ID=542070;
	/** PASSWORD = PASSWORD */
	public static final String SFTPAUTHTYPE_PASSWORD = "PASSWORD";
	/** SSH_KEY = SSH_KEY */
	public static final String SFTPAUTHTYPE_SSH_KEY = "SSH_KEY";
	@Override
	public void setSftpAuthType (final @Nullable java.lang.String SftpAuthType)
	{
		set_Value (COLUMNNAME_SftpAuthType, SftpAuthType);
	}

	@Override
	public java.lang.String getSftpAuthType()
	{
		return get_ValueAsString(COLUMNNAME_SftpAuthType);
	}

	@Override
	public void setSshPrivateKey (final @Nullable java.lang.String SshPrivateKey)
	{
		set_Value (COLUMNNAME_SshPrivateKey, SshPrivateKey);
	}

	@Override
	public java.lang.String getSshPrivateKey()
	{
		return get_ValueAsString(COLUMNNAME_SshPrivateKey);
	}

	@Override
	public void setSftpRemotePath (final @Nullable java.lang.String SftpRemotePath)
	{
		set_Value (COLUMNNAME_SftpRemotePath, SftpRemotePath);
	}

	@Override
	public java.lang.String getSftpRemotePath()
	{
		return get_ValueAsString(COLUMNNAME_SftpRemotePath);
	}

	@Override
	public void setSftpFilenamePattern (final @Nullable java.lang.String SftpFilenamePattern)
	{
		set_Value (COLUMNNAME_SftpFilenamePattern, SftpFilenamePattern);
	}

	@Override
	public java.lang.String getSftpFilenamePattern()
	{
		return get_ValueAsString(COLUMNNAME_SftpFilenamePattern);
	}
}
