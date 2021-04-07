package org.compiere.model;

import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for AD_Session
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_AD_Session 
{

	String Table_Name = "AD_Session";

//	/** AD_Table_ID=566 */
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
	 * Set Role.
	 * Responsibility Role
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_Role_ID (int AD_Role_ID);

	/**
	 * Get Role.
	 * Responsibility Role
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_Role_ID();

	@Nullable org.compiere.model.I_AD_Role getAD_Role();

	void setAD_Role(@Nullable org.compiere.model.I_AD_Role AD_Role);

	ModelColumn<I_AD_Session, org.compiere.model.I_AD_Role> COLUMN_AD_Role_ID = new ModelColumn<>(I_AD_Session.class, "AD_Role_ID", org.compiere.model.I_AD_Role.class);
	String COLUMNNAME_AD_Role_ID = "AD_Role_ID";

	/**
	 * Set Nutzersitzung.
	 * User Session Online or Web
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_Session_ID (int AD_Session_ID);

	/**
	 * Get Nutzersitzung.
	 * User Session Online or Web
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Session_ID();

	ModelColumn<I_AD_Session, Object> COLUMN_AD_Session_ID = new ModelColumn<>(I_AD_Session.class, "AD_Session_ID", null);
	String COLUMNNAME_AD_Session_ID = "AD_Session_ID";

	/**
	 * Set Client Info.
	 * Informations about connected client (e.g. web browser name, version etc)
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setClient_Info (@Nullable java.lang.String Client_Info);

	/**
	 * Get Client Info.
	 * Informations about connected client (e.g. web browser name, version etc)
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getClient_Info();

	ModelColumn<I_AD_Session, Object> COLUMN_Client_Info = new ModelColumn<>(I_AD_Session.class, "Client_Info", null);
	String COLUMNNAME_Client_Info = "Client_Info";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_AD_Session, Object> COLUMN_Created = new ModelColumn<>(I_AD_Session.class, "Created", null);
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
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDescription (@Nullable java.lang.String Description);

	/**
	 * Get Description.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDescription();

	ModelColumn<I_AD_Session, Object> COLUMN_Description = new ModelColumn<>(I_AD_Session.class, "Description", null);
	String COLUMNNAME_Description = "Description";

	/**
	 * Set Host key.
	 * Unique identifier of a host
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setHostKey (@Nullable java.lang.String HostKey);

	/**
	 * Get Host key.
	 * Unique identifier of a host
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getHostKey();

	ModelColumn<I_AD_Session, Object> COLUMN_HostKey = new ModelColumn<>(I_AD_Session.class, "HostKey", null);
	String COLUMNNAME_HostKey = "HostKey";

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

	ModelColumn<I_AD_Session, Object> COLUMN_IsActive = new ModelColumn<>(I_AD_Session.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set IsLoginIncorrect.
	 * Flag is yes if  is login incorect
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIsLoginIncorrect (boolean IsLoginIncorrect);

	/**
	 * Get IsLoginIncorrect.
	 * Flag is yes if  is login incorect
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isLoginIncorrect();

	ModelColumn<I_AD_Session, Object> COLUMN_IsLoginIncorrect = new ModelColumn<>(I_AD_Session.class, "IsLoginIncorrect", null);
	String COLUMNNAME_IsLoginIncorrect = "IsLoginIncorrect";

	/**
	 * Set Login date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setLoginDate (@Nullable java.sql.Timestamp LoginDate);

	/**
	 * Get Login date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getLoginDate();

	ModelColumn<I_AD_Session, Object> COLUMN_LoginDate = new ModelColumn<>(I_AD_Session.class, "LoginDate", null);
	String COLUMNNAME_LoginDate = "LoginDate";

	/**
	 * Set Login User Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setLoginUsername (@Nullable java.lang.String LoginUsername);

	/**
	 * Get Login User Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getLoginUsername();

	ModelColumn<I_AD_Session, Object> COLUMN_LoginUsername = new ModelColumn<>(I_AD_Session.class, "LoginUsername", null);
	String COLUMNNAME_LoginUsername = "LoginUsername";

	/**
	 * Set Processed.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setProcessed (boolean Processed);

	/**
	 * Get Processed.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isProcessed();

	ModelColumn<I_AD_Session, Object> COLUMN_Processed = new ModelColumn<>(I_AD_Session.class, "Processed", null);
	String COLUMNNAME_Processed = "Processed";

	/**
	 * Set Remote Addr.
	 * Remote Address
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setRemote_Addr (@Nullable java.lang.String Remote_Addr);

	/**
	 * Get Remote Addr.
	 * Remote Address
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getRemote_Addr();

	ModelColumn<I_AD_Session, Object> COLUMN_Remote_Addr = new ModelColumn<>(I_AD_Session.class, "Remote_Addr", null);
	String COLUMNNAME_Remote_Addr = "Remote_Addr";

	/**
	 * Set Remote Host.
	 * Remote host Info
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setRemote_Host (@Nullable java.lang.String Remote_Host);

	/**
	 * Get Remote Host.
	 * Remote host Info
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getRemote_Host();

	ModelColumn<I_AD_Session, Object> COLUMN_Remote_Host = new ModelColumn<>(I_AD_Session.class, "Remote_Host", null);
	String COLUMNNAME_Remote_Host = "Remote_Host";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_AD_Session, Object> COLUMN_Updated = new ModelColumn<>(I_AD_Session.class, "Updated", null);
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

	/**
	 * Set Web-Sitzung.
	 * Web Session ID
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setWebSession (@Nullable java.lang.String WebSession);

	/**
	 * Get Web-Sitzung.
	 * Web Session ID
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getWebSession();

	ModelColumn<I_AD_Session, Object> COLUMN_WebSession = new ModelColumn<>(I_AD_Session.class, "WebSession", null);
	String COLUMNNAME_WebSession = "WebSession";
}
