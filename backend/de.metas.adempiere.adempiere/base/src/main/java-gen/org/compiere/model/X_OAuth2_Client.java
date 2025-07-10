// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for OAuth2_Client
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_OAuth2_Client extends org.compiere.model.PO implements I_OAuth2_Client, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 1925523060L;

    /** Standard Constructor */
    public X_OAuth2_Client (final Properties ctx, final int OAuth2_Client_ID, @Nullable final String trxName)
    {
      super (ctx, OAuth2_Client_ID, trxName);
    }

    /** Load Constructor */
    public X_OAuth2_Client (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setInternalName (final java.lang.String InternalName)
	{
		set_Value (COLUMNNAME_InternalName, InternalName);
	}

	@Override
	public java.lang.String getInternalName() 
	{
		return get_ValueAsString(COLUMNNAME_InternalName);
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
	public void setOAuth2_Authorization_URI (final java.lang.String OAuth2_Authorization_URI)
	{
		set_Value (COLUMNNAME_OAuth2_Authorization_URI, OAuth2_Authorization_URI);
	}

	@Override
	public java.lang.String getOAuth2_Authorization_URI() 
	{
		return get_ValueAsString(COLUMNNAME_OAuth2_Authorization_URI);
	}

	@Override
	public void setOAuth2_ClientId (final java.lang.String OAuth2_ClientId)
	{
		set_Value (COLUMNNAME_OAuth2_ClientId, OAuth2_ClientId);
	}

	@Override
	public java.lang.String getOAuth2_ClientId() 
	{
		return get_ValueAsString(COLUMNNAME_OAuth2_ClientId);
	}

	@Override
	public void setOAuth2_Client_ID (final int OAuth2_Client_ID)
	{
		if (OAuth2_Client_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_OAuth2_Client_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_OAuth2_Client_ID, OAuth2_Client_ID);
	}

	@Override
	public int getOAuth2_Client_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_OAuth2_Client_ID);
	}

	@Override
	public void setOAuth2_Client_Secret (final @Nullable java.lang.String OAuth2_Client_Secret)
	{
		set_Value (COLUMNNAME_OAuth2_Client_Secret, OAuth2_Client_Secret);
	}

	@Override
	public java.lang.String getOAuth2_Client_Secret() 
	{
		return get_ValueAsString(COLUMNNAME_OAuth2_Client_Secret);
	}

	@Override
	public void setOAuth2_Issuer_URI (final java.lang.String OAuth2_Issuer_URI)
	{
		set_Value (COLUMNNAME_OAuth2_Issuer_URI, OAuth2_Issuer_URI);
	}

	@Override
	public java.lang.String getOAuth2_Issuer_URI() 
	{
		return get_ValueAsString(COLUMNNAME_OAuth2_Issuer_URI);
	}

	@Override
	public void setOAuth2_JWKS_URI (final java.lang.String OAuth2_JWKS_URI)
	{
		set_Value (COLUMNNAME_OAuth2_JWKS_URI, OAuth2_JWKS_URI);
	}

	@Override
	public java.lang.String getOAuth2_JWKS_URI() 
	{
		return get_ValueAsString(COLUMNNAME_OAuth2_JWKS_URI);
	}

	@Override
	public void setOAuth2_Logo_URI (final @Nullable java.lang.String OAuth2_Logo_URI)
	{
		set_Value (COLUMNNAME_OAuth2_Logo_URI, OAuth2_Logo_URI);
	}

	@Override
	public java.lang.String getOAuth2_Logo_URI() 
	{
		return get_ValueAsString(COLUMNNAME_OAuth2_Logo_URI);
	}

	@Override
	public void setOAuth2_Token_URI (final java.lang.String OAuth2_Token_URI)
	{
		set_Value (COLUMNNAME_OAuth2_Token_URI, OAuth2_Token_URI);
	}

	@Override
	public java.lang.String getOAuth2_Token_URI() 
	{
		return get_ValueAsString(COLUMNNAME_OAuth2_Token_URI);
	}

	@Override
	public void setOAuth2_UserInfo_URI (final java.lang.String OAuth2_UserInfo_URI)
	{
		set_Value (COLUMNNAME_OAuth2_UserInfo_URI, OAuth2_UserInfo_URI);
	}

	@Override
	public java.lang.String getOAuth2_UserInfo_URI() 
	{
		return get_ValueAsString(COLUMNNAME_OAuth2_UserInfo_URI);
	}
}