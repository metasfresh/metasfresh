package org.compiere.model;

import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for AD_User
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_AD_User 
{

	String Table_Name = "AD_User";

//	/** AD_Table_ID=114 */
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
	 * Set Language.
	 * Language for this entity
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_Language (@Nullable java.lang.String AD_Language);

	/**
	 * Get Language.
	 * Language for this entity
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getAD_Language();

	ModelColumn<I_AD_User, Object> COLUMN_AD_Language = new ModelColumn<>(I_AD_User.class, "AD_Language", null);
	String COLUMNNAME_AD_Language = "AD_Language";

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
	 * Set Org Mapping.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_Org_Mapping_ID (int AD_Org_Mapping_ID);

	/**
	 * Get Org Mapping.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_Org_Mapping_ID();

	@Nullable org.compiere.model.I_AD_Org_Mapping getAD_Org_Mapping();

	void setAD_Org_Mapping(@Nullable org.compiere.model.I_AD_Org_Mapping AD_Org_Mapping);

	ModelColumn<I_AD_User, org.compiere.model.I_AD_Org_Mapping> COLUMN_AD_Org_Mapping_ID = new ModelColumn<>(I_AD_User.class, "AD_Org_Mapping_ID", org.compiere.model.I_AD_Org_Mapping.class);
	String COLUMNNAME_AD_Org_Mapping_ID = "AD_Org_Mapping_ID";

	/**
	 * Set Trx Organization.
	 * Performing or initiating organization
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_OrgTrx_ID (int AD_OrgTrx_ID);

	/**
	 * Get Trx Organization.
	 * Performing or initiating organization
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_OrgTrx_ID();

	String COLUMNNAME_AD_OrgTrx_ID = "AD_OrgTrx_ID";

	/**
	 * Set Contact.
	 * User within the system - Internal or Business Partner Contact
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_User_ID (int AD_User_ID);

	/**
	 * Get Contact.
	 * User within the system - Internal or Business Partner Contact
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_User_ID();

	ModelColumn<I_AD_User, Object> COLUMN_AD_User_ID = new ModelColumn<>(I_AD_User.class, "AD_User_ID", null);
	String COLUMNNAME_AD_User_ID = "AD_User_ID";

	/**
	 * Set Responsible.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_User_InCharge_ID (int AD_User_InCharge_ID);

	/**
	 * Get Responsible.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_User_InCharge_ID();

	String COLUMNNAME_AD_User_InCharge_ID = "AD_User_InCharge_ID";

	/**
	 * Set Title.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setAlbertaTitle (@Nullable java.lang.String AlbertaTitle);

	/**
	 * Get Title.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	@Nullable java.lang.String getAlbertaTitle();

	ModelColumn<I_AD_User, Object> COLUMN_AlbertaTitle = new ModelColumn<>(I_AD_User.class, "AlbertaTitle", null);
	String COLUMNNAME_AlbertaTitle = "AlbertaTitle";

	/**
	 * Set Avatar.
	 *
	 * <br>Type: Image
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAvatar_ID (int Avatar_ID);

	/**
	 * Get Avatar.
	 *
	 * <br>Type: Image
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAvatar_ID();

	@Nullable org.compiere.model.I_AD_Image getAvatar();

	void setAvatar(@Nullable org.compiere.model.I_AD_Image Avatar);

	ModelColumn<I_AD_User, org.compiere.model.I_AD_Image> COLUMN_Avatar_ID = new ModelColumn<>(I_AD_User.class, "Avatar_ID", org.compiere.model.I_AD_Image.class);
	String COLUMNNAME_Avatar_ID = "Avatar_ID";

	/**
	 * Set Geburtstag.
	 * Birthday or Anniversary day
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setBirthday (@Nullable java.sql.Timestamp Birthday);

	/**
	 * Get Geburtstag.
	 * Birthday or Anniversary day
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getBirthday();

	ModelColumn<I_AD_User, Object> COLUMN_Birthday = new ModelColumn<>(I_AD_User.class, "Birthday", null);
	String COLUMNNAME_Birthday = "Birthday";

	/**
	 * Set Partner Location.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_BPartner_Alt_Location_ID (int C_BPartner_Alt_Location_ID);

	/**
	 * Get Partner Location.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_BPartner_Alt_Location_ID();

	String COLUMNNAME_C_BPartner_Alt_Location_ID = "C_BPartner_Alt_Location_ID";

	/**
	 * Set Business Partner.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_BPartner_ID (int C_BPartner_ID);

	/**
	 * Get Business Partner.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_BPartner_ID();

	String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/**
	 * Set Location.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_BPartner_Location_ID (int C_BPartner_Location_ID);

	/**
	 * Get Location.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_BPartner_Location_ID();

	String COLUMNNAME_C_BPartner_Location_ID = "C_BPartner_Location_ID";

	/**
	 * Set Greeting (ID).
	 * Greeting to print on correspondence
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Greeting_ID (int C_Greeting_ID);

	/**
	 * Get Greeting (ID).
	 * Greeting to print on correspondence
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Greeting_ID();

	String COLUMNNAME_C_Greeting_ID = "C_Greeting_ID";

	/**
	 * Set Position.
	 * Job Position
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Job_ID (int C_Job_ID);

	/**
	 * Get Position.
	 * Job Position
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Job_ID();

	@Nullable org.compiere.model.I_C_Job getC_Job();

	void setC_Job(@Nullable org.compiere.model.I_C_Job C_Job);

	ModelColumn<I_AD_User, org.compiere.model.I_C_Job> COLUMN_C_Job_ID = new ModelColumn<>(I_AD_User.class, "C_Job_ID", org.compiere.model.I_C_Job.class);
	String COLUMNNAME_C_Job_ID = "C_Job_ID";

	/**
	 * Set Comments.
	 * Comments or additional information
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setComments (@Nullable java.lang.String Comments);

	/**
	 * Get Comments.
	 * Comments or additional information
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getComments();

	ModelColumn<I_AD_User, Object> COLUMN_Comments = new ModelColumn<>(I_AD_User.class, "Comments", null);
	String COLUMNNAME_Comments = "Comments";

	/**
	 * Set Company Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setCompanyname (@Nullable java.lang.String Companyname);

	/**
	 * Get Company Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	@Nullable java.lang.String getCompanyname();

	ModelColumn<I_AD_User, Object> COLUMN_Companyname = new ModelColumn<>(I_AD_User.class, "Companyname", null);
	String COLUMNNAME_Companyname = "Companyname";

	/**
	 * Set Verbindungsart.
	 * How a Java Client connects to the server(s)
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setConnectionProfile (@Nullable java.lang.String ConnectionProfile);

	/**
	 * Get Verbindungsart.
	 * How a Java Client connects to the server(s)
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getConnectionProfile();

	ModelColumn<I_AD_User, Object> COLUMN_ConnectionProfile = new ModelColumn<>(I_AD_User.class, "ConnectionProfile", null);
	String COLUMNNAME_ConnectionProfile = "ConnectionProfile";

	/**
	 * Set Kontakt Einschränkung.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setContactLimitation (@Nullable java.lang.String ContactLimitation);

	/**
	 * Get Kontakt Einschränkung.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getContactLimitation();

	ModelColumn<I_AD_User, Object> COLUMN_ContactLimitation = new ModelColumn<>(I_AD_User.class, "ContactLimitation", null);
	String COLUMNNAME_ContactLimitation = "ContactLimitation";

	/**
	 * Set Begründung.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setContactLimitationReason (@Nullable java.lang.String ContactLimitationReason);

	/**
	 * Get Begründung.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getContactLimitationReason();

	ModelColumn<I_AD_User, Object> COLUMN_ContactLimitationReason = new ModelColumn<>(I_AD_User.class, "ContactLimitationReason", null);
	String COLUMNNAME_ContactLimitationReason = "ContactLimitationReason";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_AD_User, Object> COLUMN_Created = new ModelColumn<>(I_AD_User.class, "Created", null);
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
	 * Set Titel.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Title_ID (int C_Title_ID);

	/**
	 * Get Titel.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Title_ID();

	@Nullable org.compiere.model.I_C_Title getC_Title();

	void setC_Title(@Nullable org.compiere.model.I_C_Title C_Title);

	ModelColumn<I_AD_User, org.compiere.model.I_C_Title> COLUMN_C_Title_ID = new ModelColumn<>(I_AD_User.class, "C_Title_ID", org.compiere.model.I_C_Title.class);
	String COLUMNNAME_C_Title_ID = "C_Title_ID";

	/**
	 * Set Löschdatum.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDelDate (@Nullable java.sql.Timestamp DelDate);

	/**
	 * Get Löschdatum.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getDelDate();

	ModelColumn<I_AD_User, Object> COLUMN_DelDate = new ModelColumn<>(I_AD_User.class, "DelDate", null);
	String COLUMNNAME_DelDate = "DelDate";

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

	ModelColumn<I_AD_User, Object> COLUMN_Description = new ModelColumn<>(I_AD_User.class, "Description", null);
	String COLUMNNAME_Description = "Description";

	/**
	 * Set eMail.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setEMail (@Nullable java.lang.String EMail);

	/**
	 * Get eMail.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getEMail();

	ModelColumn<I_AD_User, Object> COLUMN_EMail = new ModelColumn<>(I_AD_User.class, "EMail", null);
	String COLUMNNAME_EMail = "EMail";

	/**
	 * Set EMail Nutzer-ID.
	 * User Name (ID) in the Mail System
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setEMailUser (@Nullable java.lang.String EMailUser);

	/**
	 * Get EMail Nutzer-ID.
	 * User Name (ID) in the Mail System
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getEMailUser();

	ModelColumn<I_AD_User, Object> COLUMN_EMailUser = new ModelColumn<>(I_AD_User.class, "EMailUser", null);
	String COLUMNNAME_EMailUser = "EMailUser";

	/**
	 * Set Passwort EMail-Nutzer.
	 * Passwort Ihrer EMail Nutzer-ID
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setEMailUserPW (@Nullable java.lang.String EMailUserPW);

	/**
	 * Get Passwort EMail-Nutzer.
	 * Passwort Ihrer EMail Nutzer-ID
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getEMailUserPW();

	ModelColumn<I_AD_User, Object> COLUMN_EMailUserPW = new ModelColumn<>(I_AD_User.class, "EMailUserPW", null);
	String COLUMNNAME_EMailUserPW = "EMailUserPW";

	/**
	 * Set Überprüfung EMail.
	 * Verification information of EMail Address
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setEMailVerify (@Nullable java.lang.String EMailVerify);

	/**
	 * Get Überprüfung EMail.
	 * Verification information of EMail Address
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getEMailVerify();

	ModelColumn<I_AD_User, Object> COLUMN_EMailVerify = new ModelColumn<>(I_AD_User.class, "EMailVerify", null);
	String COLUMNNAME_EMailVerify = "EMailVerify";

	/**
	 * Set EMail überprüft.
	 * Date Email was verified
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setEMailVerifyDate (@Nullable java.sql.Timestamp EMailVerifyDate);

	/**
	 * Get EMail überprüft.
	 * Date Email was verified
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getEMailVerifyDate();

	ModelColumn<I_AD_User, Object> COLUMN_EMailVerifyDate = new ModelColumn<>(I_AD_User.class, "EMailVerifyDate", null);
	String COLUMNNAME_EMailVerifyDate = "EMailVerifyDate";

	/**
	 * Set External ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setExternalId (@Nullable java.lang.String ExternalId);

	/**
	 * Get External ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getExternalId();

	ModelColumn<I_AD_User, Object> COLUMN_ExternalId = new ModelColumn<>(I_AD_User.class, "ExternalId", null);
	String COLUMNNAME_ExternalId = "ExternalId";

	/**
	 * Set Fax.
	 * Facsimile number
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setFax (@Nullable java.lang.String Fax);

	/**
	 * Get Fax.
	 * Facsimile number
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getFax();

	ModelColumn<I_AD_User, Object> COLUMN_Fax = new ModelColumn<>(I_AD_User.class, "Fax", null);
	String COLUMNNAME_Fax = "Fax";

	/**
	 * Set Firstname.
	 * Firstname
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setFirstname (@Nullable java.lang.String Firstname);

	/**
	 * Get Firstname.
	 * Firstname
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getFirstname();

	ModelColumn<I_AD_User, Object> COLUMN_Firstname = new ModelColumn<>(I_AD_User.class, "Firstname", null);
	String COLUMNNAME_Firstname = "Firstname";

	/**
	 * Set Christmas Gift.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setFresh_xmas_Gift (@Nullable java.lang.String Fresh_xmas_Gift);

	/**
	 * Get Christmas Gift.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getFresh_xmas_Gift();

	ModelColumn<I_AD_User, Object> COLUMN_Fresh_xmas_Gift = new ModelColumn<>(I_AD_User.class, "Fresh_xmas_Gift", null);
	String COLUMNNAME_Fresh_xmas_Gift = "Fresh_xmas_Gift";

	/**
	 * Set Gender.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setGender (@Nullable java.lang.String Gender);

	/**
	 * Get Gender.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	@Nullable java.lang.String getGender();

	ModelColumn<I_AD_User, Object> COLUMN_Gender = new ModelColumn<>(I_AD_User.class, "Gender", null);
	String COLUMNNAME_Gender = "Gender";

	/**
	 * Set Included Tab.
	 * Included Tab in this Tab (Master Dateail)
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIncluded_Tab_ID (@Nullable java.lang.String Included_Tab_ID);

	/**
	 * Get Included Tab.
	 * Included Tab in this Tab (Master Dateail)
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getIncluded_Tab_ID();

	ModelColumn<I_AD_User, Object> COLUMN_Included_Tab_ID = new ModelColumn<>(I_AD_User.class, "Included_Tab_ID", null);
	String COLUMNNAME_Included_Tab_ID = "Included_Tab_ID";

	/**
	 * Set Account locked.
	 * Kennzeichen das anzeigt ob der Zugang gesperrt wurde
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIsAccountLocked (boolean IsAccountLocked);

	/**
	 * Get Account locked.
	 * Kennzeichen das anzeigt ob der Zugang gesperrt wurde
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isAccountLocked();

	ModelColumn<I_AD_User, Object> COLUMN_IsAccountLocked = new ModelColumn<>(I_AD_User.class, "IsAccountLocked", null);
	String COLUMNNAME_IsAccountLocked = "IsAccountLocked";

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

	ModelColumn<I_AD_User, Object> COLUMN_IsActive = new ModelColumn<>(I_AD_User.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Activ.
	 * Shows if the partner is active
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setIsActivePartner (boolean IsActivePartner);

	/**
	 * Get Activ.
	 * Shows if the partner is active
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	boolean isActivePartner();

	ModelColumn<I_AD_User, Object> COLUMN_IsActivePartner = new ModelColumn<>(I_AD_User.class, "IsActivePartner", null);
	String COLUMNNAME_IsActivePartner = "IsActivePartner";

	/**
	 * Set Authorized signatory.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsAuthorizedSignatory (boolean IsAuthorizedSignatory);

	/**
	 * Get Authorized signatory.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isAuthorizedSignatory();

	ModelColumn<I_AD_User, Object> COLUMN_IsAuthorizedSignatory = new ModelColumn<>(I_AD_User.class, "IsAuthorizedSignatory", null);
	String COLUMNNAME_IsAuthorizedSignatory = "IsAuthorizedSignatory";

	/**
	 * Set BillTo Contact Default.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsBillToContact_Default (boolean IsBillToContact_Default);

	/**
	 * Get BillTo Contact Default.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isBillToContact_Default();

	ModelColumn<I_AD_User, Object> COLUMN_IsBillToContact_Default = new ModelColumn<>(I_AD_User.class, "IsBillToContact_Default", null);
	String COLUMNNAME_IsBillToContact_Default = "IsBillToContact_Default";

	/**
	 * Set Default Contact.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsDefaultContact (boolean IsDefaultContact);

	/**
	 * Get Default Contact.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isDefaultContact();

	ModelColumn<I_AD_User, Object> COLUMN_IsDefaultContact = new ModelColumn<>(I_AD_User.class, "IsDefaultContact", null);
	String COLUMNNAME_IsDefaultContact = "IsDefaultContact";

	/**
	 * Set Full BP Access.
	 * The user/contact has full access to Business Partner information and resources
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsFullBPAccess (boolean IsFullBPAccess);

	/**
	 * Get Full BP Access.
	 * The user/contact has full access to Business Partner information and resources
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isFullBPAccess();

	ModelColumn<I_AD_User, Object> COLUMN_IsFullBPAccess = new ModelColumn<>(I_AD_User.class, "IsFullBPAccess", null);
	String COLUMNNAME_IsFullBPAccess = "IsFullBPAccess";

	/**
	 * Set Is In Payroll.
	 * Defined if any User Contact will be used for Calculate Payroll
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsInPayroll (boolean IsInPayroll);

	/**
	 * Get Is In Payroll.
	 * Defined if any User Contact will be used for Calculate Payroll
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isInPayroll();

	ModelColumn<I_AD_User, Object> COLUMN_IsInPayroll = new ModelColumn<>(I_AD_User.class, "IsInPayroll", null);
	String COLUMNNAME_IsInPayroll = "IsInPayroll";

	/**
	 * Set Invoice Email Enabled.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIsInvoiceEmailEnabled (@Nullable java.lang.String IsInvoiceEmailEnabled);

	/**
	 * Get Invoice Email Enabled.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getIsInvoiceEmailEnabled();

	ModelColumn<I_AD_User, Object> COLUMN_IsInvoiceEmailEnabled = new ModelColumn<>(I_AD_User.class, "IsInvoiceEmailEnabled", null);
	String COLUMNNAME_IsInvoiceEmailEnabled = "IsInvoiceEmailEnabled";

	/**
	 * Set Use Login As Printing HostKey.
	 * Wenn gesetzt und ein Nutzer meldet sich an, dann wird immer der jeweilige User-Login als Hostkey benutzt, egal von welchem Computer aus sich der Nutzer anmeldet
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIsLoginAsHostKey (boolean IsLoginAsHostKey);

	/**
	 * Get Use Login As Printing HostKey.
	 * Wenn gesetzt und ein Nutzer meldet sich an, dann wird immer der jeweilige User-Login als Hostkey benutzt, egal von welchem Computer aus sich der Nutzer anmeldet
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isLoginAsHostKey();

	ModelColumn<I_AD_User, Object> COLUMN_IsLoginAsHostKey = new ModelColumn<>(I_AD_User.class, "IsLoginAsHostKey", null);
	String COLUMNNAME_IsLoginAsHostKey = "IsLoginAsHostKey";

	/**
	 * Set Is Membership Contact.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsMembershipContact (boolean IsMembershipContact);

	/**
	 * Get Is Membership Contact.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isMembershipContact();

	ModelColumn<I_AD_User, Object> COLUMN_IsMembershipContact = new ModelColumn<>(I_AD_User.class, "IsMembershipContact", null);
	String COLUMNNAME_IsMembershipContact = "IsMembershipContact";

	/**
	 * Set Newsletter.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsNewsletter (boolean IsNewsletter);

	/**
	 * Get Newsletter.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isNewsletter();

	ModelColumn<I_AD_User, Object> COLUMN_IsNewsletter = new ModelColumn<>(I_AD_User.class, "IsNewsletter", null);
	String COLUMNNAME_IsNewsletter = "IsNewsletter";

	/**
	 * Set Purchasing Contact.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIsPurchaseContact (boolean IsPurchaseContact);

	/**
	 * Get Purchasing Contact.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isPurchaseContact();

	ModelColumn<I_AD_User, Object> COLUMN_IsPurchaseContact = new ModelColumn<>(I_AD_User.class, "IsPurchaseContact", null);
	String COLUMNNAME_IsPurchaseContact = "IsPurchaseContact";

	/**
	 * Set IsPurchaseContact_Default.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsPurchaseContact_Default (boolean IsPurchaseContact_Default);

	/**
	 * Get IsPurchaseContact_Default.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isPurchaseContact_Default();

	ModelColumn<I_AD_User, Object> COLUMN_IsPurchaseContact_Default = new ModelColumn<>(I_AD_User.class, "IsPurchaseContact_Default", null);
	String COLUMNNAME_IsPurchaseContact_Default = "IsPurchaseContact_Default";

	/**
	 * Set IsSalesContact.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIsSalesContact (boolean IsSalesContact);

	/**
	 * Get IsSalesContact.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isSalesContact();

	ModelColumn<I_AD_User, Object> COLUMN_IsSalesContact = new ModelColumn<>(I_AD_User.class, "IsSalesContact", null);
	String COLUMNNAME_IsSalesContact = "IsSalesContact";

	/**
	 * Set IsSalesContact_Default.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsSalesContact_Default (boolean IsSalesContact_Default);

	/**
	 * Get IsSalesContact_Default.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isSalesContact_Default();

	ModelColumn<I_AD_User, Object> COLUMN_IsSalesContact_Default = new ModelColumn<>(I_AD_User.class, "IsSalesContact_Default", null);
	String COLUMNNAME_IsSalesContact_Default = "IsSalesContact_Default";

	/**
	 * Set ShipTo Contact Default.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsShipToContact_Default (boolean IsShipToContact_Default);

	/**
	 * Get ShipTo Contact Default.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isShipToContact_Default();

	ModelColumn<I_AD_User, Object> COLUMN_IsShipToContact_Default = new ModelColumn<>(I_AD_User.class, "IsShipToContact_Default", null);
	String COLUMNNAME_IsShipToContact_Default = "IsShipToContact_Default";

	/**
	 * Set Subject matter contact.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIsSubjectMatterContact (boolean IsSubjectMatterContact);

	/**
	 * Get Subject matter contact.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isSubjectMatterContact();

	ModelColumn<I_AD_User, Object> COLUMN_IsSubjectMatterContact = new ModelColumn<>(I_AD_User.class, "IsSubjectMatterContact", null);
	String COLUMNNAME_IsSubjectMatterContact = "IsSubjectMatterContact";

	/**
	 * Set System User.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsSystemUser (boolean IsSystemUser);

	/**
	 * Get System User.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isSystemUser();

	ModelColumn<I_AD_User, Object> COLUMN_IsSystemUser = new ModelColumn<>(I_AD_User.class, "IsSystemUser", null);
	String COLUMNNAME_IsSystemUser = "IsSystemUser";

	/**
	 * Set Lastname.
	 * Lastname
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setLastname (@Nullable java.lang.String Lastname);

	/**
	 * Get Lastname.
	 * Lastname
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getLastname();

	ModelColumn<I_AD_User, Object> COLUMN_Lastname = new ModelColumn<>(I_AD_User.class, "Lastname", null);
	String COLUMNNAME_Lastname = "Lastname";

	/**
	 * Set Locked From IP.
	 * Client IP address that was used when this account was locked
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setLockedFromIP (@Nullable java.lang.String LockedFromIP);

	/**
	 * Get Locked From IP.
	 * Client IP address that was used when this account was locked
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getLockedFromIP();

	ModelColumn<I_AD_User, Object> COLUMN_LockedFromIP = new ModelColumn<>(I_AD_User.class, "LockedFromIP", null);
	String COLUMNNAME_LockedFromIP = "LockedFromIP";

	/**
	 * Set Login.
	 * Used for login. See Help.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setLogin (@Nullable java.lang.String Login);

	/**
	 * Get Login.
	 * Used for login. See Help.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getLogin();

	ModelColumn<I_AD_User, Object> COLUMN_Login = new ModelColumn<>(I_AD_User.class, "Login", null);
	String COLUMNNAME_Login = "Login";

	/**
	 * Set Login Failure Count.
	 * Anzahl Login Fehlversuche
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setLoginFailureCount (int LoginFailureCount);

	/**
	 * Get Login Failure Count.
	 * Anzahl Login Fehlversuche
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getLoginFailureCount();

	ModelColumn<I_AD_User, Object> COLUMN_LoginFailureCount = new ModelColumn<>(I_AD_User.class, "LoginFailureCount", null);
	String COLUMNNAME_LoginFailureCount = "LoginFailureCount";

	/**
	 * Set Login Failure Date.
	 * Datum Login Fehler
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setLoginFailureDate (@Nullable java.sql.Timestamp LoginFailureDate);

	/**
	 * Get Login Failure Date.
	 * Datum Login Fehler
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getLoginFailureDate();

	ModelColumn<I_AD_User, Object> COLUMN_LoginFailureDate = new ModelColumn<>(I_AD_User.class, "LoginFailureDate", null);
	String COLUMNNAME_LoginFailureDate = "LoginFailureDate";

	/**
	 * Set Memo.
	 * Memo Text
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setMemo (@Nullable java.lang.String Memo);

	/**
	 * Get Memo.
	 * Memo Text
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getMemo();

	ModelColumn<I_AD_User, Object> COLUMN_Memo = new ModelColumn<>(I_AD_User.class, "Memo", null);
	String COLUMNNAME_Memo = "Memo";

	/**
	 * Set Mobile Phone.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setMobilePhone (@Nullable java.lang.String MobilePhone);

	/**
	 * Get Mobile Phone.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getMobilePhone();

	ModelColumn<I_AD_User, Object> COLUMN_MobilePhone = new ModelColumn<>(I_AD_User.class, "MobilePhone", null);
	String COLUMNNAME_MobilePhone = "MobilePhone";

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

	ModelColumn<I_AD_User, Object> COLUMN_Name = new ModelColumn<>(I_AD_User.class, "Name", null);
	String COLUMNNAME_Name = "Name";

	/**
	 * Set Benachrichtigungs-Art.
	 * Type of Notifications
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setNotificationType (java.lang.String NotificationType);

	/**
	 * Get Benachrichtigungs-Art.
	 * Type of Notifications
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getNotificationType();

	ModelColumn<I_AD_User, Object> COLUMN_NotificationType = new ModelColumn<>(I_AD_User.class, "NotificationType", null);
	String COLUMNNAME_NotificationType = "NotificationType";

	/**
	 * Set Password.
	 * Password of any length (case sensitive)
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPassword (@Nullable java.lang.String Password);

	/**
	 * Get Password.
	 * Password of any length (case sensitive)
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getPassword();

	ModelColumn<I_AD_User, Object> COLUMN_Password = new ModelColumn<>(I_AD_User.class, "Password", null);
	String COLUMNNAME_Password = "Password";

	/**
	 * Set Portalpasswort.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setpasswordportal (@Nullable java.lang.String passwordportal);

	/**
	 * Get Portalpasswort.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getpasswordportal();

	ModelColumn<I_AD_User, Object> COLUMN_passwordportal = new ModelColumn<>(I_AD_User.class, "passwordportal", null);
	String COLUMNNAME_passwordportal = "passwordportal";

	/**
	 * Set Code für Passwort-Änderung.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPasswordResetCode (@Nullable java.lang.String PasswordResetCode);

	/**
	 * Get Code für Passwort-Änderung.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getPasswordResetCode();

	ModelColumn<I_AD_User, Object> COLUMN_PasswordResetCode = new ModelColumn<>(I_AD_User.class, "PasswordResetCode", null);
	String COLUMNNAME_PasswordResetCode = "PasswordResetCode";

	/**
	 * Set Phone.
	 * Identifies a telephone number
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPhone (@Nullable java.lang.String Phone);

	/**
	 * Get Phone.
	 * Identifies a telephone number
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getPhone();

	ModelColumn<I_AD_User, Object> COLUMN_Phone = new ModelColumn<>(I_AD_User.class, "Phone", null);
	String COLUMNNAME_Phone = "Phone";

	/**
	 * Set Phone (alternative).
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPhone2 (@Nullable java.lang.String Phone2);

	/**
	 * Get Phone (alternative).
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getPhone2();

	ModelColumn<I_AD_User, Object> COLUMN_Phone2 = new ModelColumn<>(I_AD_User.class, "Phone2", null);
	String COLUMNNAME_Phone2 = "Phone2";

	/**
	 * Set Process Now.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setProcessing (boolean Processing);

	/**
	 * Get Process Now.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isProcessing();

	ModelColumn<I_AD_User, Object> COLUMN_Processing = new ModelColumn<>(I_AD_User.class, "Processing", null);
	String COLUMNNAME_Processing = "Processing";

	/**
	 * Set Handelsregister.
	 * Handelsregister
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setRegistry (@Nullable java.lang.String Registry);

	/**
	 * Get Handelsregister.
	 * Handelsregister
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getRegistry();

	ModelColumn<I_AD_User, Object> COLUMN_Registry = new ModelColumn<>(I_AD_User.class, "Registry", null);
	String COLUMNNAME_Registry = "Registry";

	/**
	 * Set SeqNo.
	 * Method of ordering records;
 lowest number comes first
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setSeqNo (int SeqNo);

	/**
	 * Get SeqNo.
	 * Method of ordering records;
 lowest number comes first
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getSeqNo();

	ModelColumn<I_AD_User, Object> COLUMN_SeqNo = new ModelColumn<>(I_AD_User.class, "SeqNo", null);
	String COLUMNNAME_SeqNo = "SeqNo";

	/**
	 * Set Vorgesetzter.
	 * Supervisor for this user/organization - used for escalation and approval
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setSupervisor_ID (int Supervisor_ID);

	/**
	 * Get Vorgesetzter.
	 * Supervisor for this user/organization - used for escalation and approval
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getSupervisor_ID();

	String COLUMNNAME_Supervisor_ID = "Supervisor_ID";

	/**
	 * Set Timestamp.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setTimestamp (@Nullable java.sql.Timestamp Timestamp);

	/**
	 * Get Timestamp.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	@Nullable java.sql.Timestamp getTimestamp();

	ModelColumn<I_AD_User, Object> COLUMN_Timestamp = new ModelColumn<>(I_AD_User.class, "Timestamp", null);
	String COLUMNNAME_Timestamp = "Timestamp";

	/**
	 * Set Title.
	 * Name this entity is referred to as
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setTitle (@Nullable java.lang.String Title);

	/**
	 * Get Title.
	 * Name this entity is referred to as
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getTitle();

	ModelColumn<I_AD_User, Object> COLUMN_Title = new ModelColumn<>(I_AD_User.class, "Title", null);
	String COLUMNNAME_Title = "Title";

	/**
	 * Set Unlock Account.
	 * Button that will call a process to unlock current selected account
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setUnlockAccount (@Nullable java.lang.String UnlockAccount);

	/**
	 * Get Unlock Account.
	 * Button that will call a process to unlock current selected account
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getUnlockAccount();

	ModelColumn<I_AD_User, Object> COLUMN_UnlockAccount = new ModelColumn<>(I_AD_User.class, "UnlockAccount", null);
	String COLUMNNAME_UnlockAccount = "UnlockAccount";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_AD_User, Object> COLUMN_Updated = new ModelColumn<>(I_AD_User.class, "Updated", null);
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
	 * Set User PIN.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setUserPIN (@Nullable java.lang.String UserPIN);

	/**
	 * Get User PIN.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getUserPIN();

	ModelColumn<I_AD_User, Object> COLUMN_UserPIN = new ModelColumn<>(I_AD_User.class, "UserPIN", null);
	String COLUMNNAME_UserPIN = "UserPIN";

	/**
	 * Set Search Key.
	 * Search key for the record in the format required - must be unique
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setValue (@Nullable java.lang.String Value);

	/**
	 * Get Search Key.
	 * Search key for the record in the format required - must be unique
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getValue();

	ModelColumn<I_AD_User, Object> COLUMN_Value = new ModelColumn<>(I_AD_User.class, "Value", null);
	String COLUMNNAME_Value = "Value";
}
