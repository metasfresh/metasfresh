package org.compiere.model;

import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for OAuth2_Client
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_OAuth2_Client 
{

	String Table_Name = "OAuth2_Client";

//	/** AD_Table_ID=542509 */
//	int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);


	/**
	 * Get Client.
	 * Client/Tenant for this installation.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Client_ID();

	String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Org_ID();

	String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_OAuth2_Client, Object> COLUMN_Created = new ModelColumn<>(I_OAuth2_Client.class, "Created", null);
	String COLUMNNAME_Created = "Created";

	/**
	 * Get Created By.
	 * User who created this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getCreatedBy();

	String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Description.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDescription (@Nullable java.lang.String Description);

	/**
	 * Get Description.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDescription();

	ModelColumn<I_OAuth2_Client, Object> COLUMN_Description = new ModelColumn<>(I_OAuth2_Client.class, "Description", null);
	String COLUMNNAME_Description = "Description";

	/**
	 * Set Internal Name.
	 * Generally used to give records a name that can be safely referenced from code.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setInternalName (java.lang.String InternalName);

	/**
	 * Get Internal Name.
	 * Generally used to give records a name that can be safely referenced from code.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getInternalName();

	ModelColumn<I_OAuth2_Client, Object> COLUMN_InternalName = new ModelColumn<>(I_OAuth2_Client.class, "InternalName", null);
	String COLUMNNAME_InternalName = "InternalName";

	/**
	 * Set Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsActive (boolean IsActive);

	/**
	 * Get Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isActive();

	ModelColumn<I_OAuth2_Client, Object> COLUMN_IsActive = new ModelColumn<>(I_OAuth2_Client.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setName (java.lang.String Name);

	/**
	 * Get Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getName();

	ModelColumn<I_OAuth2_Client, Object> COLUMN_Name = new ModelColumn<>(I_OAuth2_Client.class, "Name", null);
	String COLUMNNAME_Name = "Name";

	/**
	 * Set Authorization URI.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setOAuth2_Authorization_URI (java.lang.String OAuth2_Authorization_URI);

	/**
	 * Get Authorization URI.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getOAuth2_Authorization_URI();

	ModelColumn<I_OAuth2_Client, Object> COLUMN_OAuth2_Authorization_URI = new ModelColumn<>(I_OAuth2_Client.class, "OAuth2_Authorization_URI", null);
	String COLUMNNAME_OAuth2_Authorization_URI = "OAuth2_Authorization_URI";

	/**
	 * Set Client ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setOAuth2_ClientId (java.lang.String OAuth2_ClientId);

	/**
	 * Get Client ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getOAuth2_ClientId();

	ModelColumn<I_OAuth2_Client, Object> COLUMN_OAuth2_ClientId = new ModelColumn<>(I_OAuth2_Client.class, "OAuth2_ClientId", null);
	String COLUMNNAME_OAuth2_ClientId = "OAuth2_ClientId";

	/**
	 * Set OAuth2 Client.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setOAuth2_Client_ID (int OAuth2_Client_ID);

	/**
	 * Get OAuth2 Client.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getOAuth2_Client_ID();

	ModelColumn<I_OAuth2_Client, Object> COLUMN_OAuth2_Client_ID = new ModelColumn<>(I_OAuth2_Client.class, "OAuth2_Client_ID", null);
	String COLUMNNAME_OAuth2_Client_ID = "OAuth2_Client_ID";

	/**
	 * Set Client Secret.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setOAuth2_Client_Secret (@Nullable java.lang.String OAuth2_Client_Secret);

	/**
	 * Get Client Secret.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getOAuth2_Client_Secret();

	ModelColumn<I_OAuth2_Client, Object> COLUMN_OAuth2_Client_Secret = new ModelColumn<>(I_OAuth2_Client.class, "OAuth2_Client_Secret", null);
	String COLUMNNAME_OAuth2_Client_Secret = "OAuth2_Client_Secret";

	/**
	 * Set Issuer URI.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setOAuth2_Issuer_URI (java.lang.String OAuth2_Issuer_URI);

	/**
	 * Get Issuer URI.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getOAuth2_Issuer_URI();

	ModelColumn<I_OAuth2_Client, Object> COLUMN_OAuth2_Issuer_URI = new ModelColumn<>(I_OAuth2_Client.class, "OAuth2_Issuer_URI", null);
	String COLUMNNAME_OAuth2_Issuer_URI = "OAuth2_Issuer_URI";

	/**
	 * Set JWK Set URI.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setOAuth2_JWKS_URI (java.lang.String OAuth2_JWKS_URI);

	/**
	 * Get JWK Set URI.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getOAuth2_JWKS_URI();

	ModelColumn<I_OAuth2_Client, Object> COLUMN_OAuth2_JWKS_URI = new ModelColumn<>(I_OAuth2_Client.class, "OAuth2_JWKS_URI", null);
	String COLUMNNAME_OAuth2_JWKS_URI = "OAuth2_JWKS_URI";

	/**
	 * Set Token URI.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setOAuth2_Token_URI (java.lang.String OAuth2_Token_URI);

	/**
	 * Get Token URI.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getOAuth2_Token_URI();

	ModelColumn<I_OAuth2_Client, Object> COLUMN_OAuth2_Token_URI = new ModelColumn<>(I_OAuth2_Client.class, "OAuth2_Token_URI", null);
	String COLUMNNAME_OAuth2_Token_URI = "OAuth2_Token_URI";

	/**
	 * Set User Info URI.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setOAuth2_UserInfo_URI (java.lang.String OAuth2_UserInfo_URI);

	/**
	 * Get User Info URI.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getOAuth2_UserInfo_URI();

	ModelColumn<I_OAuth2_Client, Object> COLUMN_OAuth2_UserInfo_URI = new ModelColumn<>(I_OAuth2_Client.class, "OAuth2_UserInfo_URI", null);
	String COLUMNNAME_OAuth2_UserInfo_URI = "OAuth2_UserInfo_URI";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_OAuth2_Client, Object> COLUMN_Updated = new ModelColumn<>(I_OAuth2_Client.class, "Updated", null);
	String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Updated By.
	 * User who updated this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getUpdatedBy();

	String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
