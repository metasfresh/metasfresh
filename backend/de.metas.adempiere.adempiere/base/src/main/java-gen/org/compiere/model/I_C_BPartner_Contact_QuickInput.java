package org.compiere.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;

/** Generated Interface for C_BPartner_Contact_QuickInput
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_BPartner_Contact_QuickInput 
{

	String Table_Name = "C_BPartner_Contact_QuickInput";

//	/** AD_Table_ID=541668 */
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
	 * Set Birthday.
	 * Birthday or Anniversary day
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setBirthday (@Nullable java.sql.Timestamp Birthday);

	/**
	 * Get Birthday.
	 * Birthday or Anniversary day
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getBirthday();

	ModelColumn<I_C_BPartner_Contact_QuickInput, Object> COLUMN_Birthday = new ModelColumn<>(I_C_BPartner_Contact_QuickInput.class, "Birthday", null);
	String COLUMNNAME_Birthday = "Birthday";

	/**
	 * Set Contact.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_BPartner_Contact_QuickInput_ID (int C_BPartner_Contact_QuickInput_ID);

	/**
	 * Get Contact.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_BPartner_Contact_QuickInput_ID();

	ModelColumn<I_C_BPartner_Contact_QuickInput, Object> COLUMN_C_BPartner_Contact_QuickInput_ID = new ModelColumn<>(I_C_BPartner_Contact_QuickInput.class, "C_BPartner_Contact_QuickInput_ID", null);
	String COLUMNNAME_C_BPartner_Contact_QuickInput_ID = "C_BPartner_Contact_QuickInput_ID";

	/**
	 * Set New BPartner quick input.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_BPartner_QuickInput_ID (int C_BPartner_QuickInput_ID);

	/**
	 * Get New BPartner quick input.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_BPartner_QuickInput_ID();

	org.compiere.model.I_C_BPartner_QuickInput getC_BPartner_QuickInput();

	void setC_BPartner_QuickInput(org.compiere.model.I_C_BPartner_QuickInput C_BPartner_QuickInput);

	ModelColumn<I_C_BPartner_Contact_QuickInput, org.compiere.model.I_C_BPartner_QuickInput> COLUMN_C_BPartner_QuickInput_ID = new ModelColumn<>(I_C_BPartner_Contact_QuickInput.class, "C_BPartner_QuickInput_ID", org.compiere.model.I_C_BPartner_QuickInput.class);
	String COLUMNNAME_C_BPartner_QuickInput_ID = "C_BPartner_QuickInput_ID";

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
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_C_BPartner_Contact_QuickInput, Object> COLUMN_Created = new ModelColumn<>(I_C_BPartner_Contact_QuickInput.class, "Created", null);
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

	ModelColumn<I_C_BPartner_Contact_QuickInput, Object> COLUMN_EMail = new ModelColumn<>(I_C_BPartner_Contact_QuickInput.class, "EMail", null);
	String COLUMNNAME_EMail = "EMail";

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

	ModelColumn<I_C_BPartner_Contact_QuickInput, Object> COLUMN_Firstname = new ModelColumn<>(I_C_BPartner_Contact_QuickInput.class, "Firstname", null);
	String COLUMNNAME_Firstname = "Firstname";

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

	ModelColumn<I_C_BPartner_Contact_QuickInput, Object> COLUMN_IsActive = new ModelColumn<>(I_C_BPartner_Contact_QuickInput.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

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

	ModelColumn<I_C_BPartner_Contact_QuickInput, Object> COLUMN_IsDefaultContact = new ModelColumn<>(I_C_BPartner_Contact_QuickInput.class, "IsDefaultContact", null);
	String COLUMNNAME_IsDefaultContact = "IsDefaultContact";

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

	ModelColumn<I_C_BPartner_Contact_QuickInput, Object> COLUMN_IsInvoiceEmailEnabled = new ModelColumn<>(I_C_BPartner_Contact_QuickInput.class, "IsInvoiceEmailEnabled", null);
	String COLUMNNAME_IsInvoiceEmailEnabled = "IsInvoiceEmailEnabled";

	/**
	 * Set Is Membership Contact.
	 * Doppelnamen
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsMembershipContact (boolean IsMembershipContact);

	/**
	 * Get Is Membership Contact.
	 * Doppelnamen
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isMembershipContact();

	ModelColumn<I_C_BPartner_Contact_QuickInput, Object> COLUMN_IsMembershipContact = new ModelColumn<>(I_C_BPartner_Contact_QuickInput.class, "IsMembershipContact", null);
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

	ModelColumn<I_C_BPartner_Contact_QuickInput, Object> COLUMN_IsNewsletter = new ModelColumn<>(I_C_BPartner_Contact_QuickInput.class, "IsNewsletter", null);
	String COLUMNNAME_IsNewsletter = "IsNewsletter";

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

	ModelColumn<I_C_BPartner_Contact_QuickInput, Object> COLUMN_Lastname = new ModelColumn<>(I_C_BPartner_Contact_QuickInput.class, "Lastname", null);
	String COLUMNNAME_Lastname = "Lastname";

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

	ModelColumn<I_C_BPartner_Contact_QuickInput, Object> COLUMN_Phone = new ModelColumn<>(I_C_BPartner_Contact_QuickInput.class, "Phone", null);
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

	ModelColumn<I_C_BPartner_Contact_QuickInput, Object> COLUMN_Phone2 = new ModelColumn<>(I_C_BPartner_Contact_QuickInput.class, "Phone2", null);
	String COLUMNNAME_Phone2 = "Phone2";

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

	ModelColumn<I_C_BPartner_Contact_QuickInput, Object> COLUMN_Title = new ModelColumn<>(I_C_BPartner_Contact_QuickInput.class, "Title", null);
	String COLUMNNAME_Title = "Title";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_C_BPartner_Contact_QuickInput, Object> COLUMN_Updated = new ModelColumn<>(I_C_BPartner_Contact_QuickInput.class, "Updated", null);
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
