package org.compiere.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;

/** Generated Interface for C_Greeting
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_Greeting 
{

	String Table_Name = "C_Greeting";

//	/** AD_Table_ID=346 */
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
	 * Set Greeting (ID).
	 * Greeting to print on correspondence
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Greeting_ID (int C_Greeting_ID);

	/**
	 * Get Greeting (ID).
	 * Greeting to print on correspondence
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Greeting_ID();

	ModelColumn<I_C_Greeting, Object> COLUMN_C_Greeting_ID = new ModelColumn<>(I_C_Greeting.class, "C_Greeting_ID", null);
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

	ModelColumn<I_C_Greeting, Object> COLUMN_Created = new ModelColumn<>(I_C_Greeting.class, "Created", null);
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
	 * Set Greeting.
	 * For letters, e.g. "Dear 
{
0}
" or "Dear Mr. 
{
0}
" - At runtime, "
{
0}
" is replaced by the name
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setGreeting (@Nullable java.lang.String Greeting);

	/**
	 * Get Greeting.
	 * For letters, e.g. "Dear 
{
0}
" or "Dear Mr. 
{
0}
" - At runtime, "
{
0}
" is replaced by the name
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getGreeting();

	ModelColumn<I_C_Greeting, Object> COLUMN_Greeting = new ModelColumn<>(I_C_Greeting.class, "Greeting", null);
	String COLUMNNAME_Greeting = "Greeting";

	/**
	 * Set Greeting Standard Type.
	 * This field defines the salutation in the letter / document based on the type of member contacts.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setGreetingStandardType (@Nullable java.lang.String GreetingStandardType);

	/**
	 * Get Greeting Standard Type.
	 * This field defines the salutation in the letter / document based on the type of member contacts.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getGreetingStandardType();

	ModelColumn<I_C_Greeting, Object> COLUMN_GreetingStandardType = new ModelColumn<>(I_C_Greeting.class, "GreetingStandardType", null);
	String COLUMNNAME_GreetingStandardType = "GreetingStandardType";

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

	ModelColumn<I_C_Greeting, Object> COLUMN_IsActive = new ModelColumn<>(I_C_Greeting.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Default.
	 * Default value
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsDefault (boolean IsDefault);

	/**
	 * Get Default.
	 * Default value
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isDefault();

	ModelColumn<I_C_Greeting, Object> COLUMN_IsDefault = new ModelColumn<>(I_C_Greeting.class, "IsDefault", null);
	String COLUMNNAME_IsDefault = "IsDefault";

	/**
	 * Set Nur erster Name.
	 * Nur den ersten Namen bei Anreden drucken
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsFirstNameOnly (boolean IsFirstNameOnly);

	/**
	 * Get Nur erster Name.
	 * Nur den ersten Namen bei Anreden drucken
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isFirstNameOnly();

	ModelColumn<I_C_Greeting, Object> COLUMN_IsFirstNameOnly = new ModelColumn<>(I_C_Greeting.class, "IsFirstNameOnly", null);
	String COLUMNNAME_IsFirstNameOnly = "IsFirstNameOnly";

	/**
	 * Set Briefanrede.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setLetter_Salutation (@Nullable java.lang.String Letter_Salutation);

	/**
	 * Get Briefanrede.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getLetter_Salutation();

	ModelColumn<I_C_Greeting, Object> COLUMN_Letter_Salutation = new ModelColumn<>(I_C_Greeting.class, "Letter_Salutation", null);
	String COLUMNNAME_Letter_Salutation = "Letter_Salutation";

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

	ModelColumn<I_C_Greeting, Object> COLUMN_Name = new ModelColumn<>(I_C_Greeting.class, "Name", null);
	String COLUMNNAME_Name = "Name";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_C_Greeting, Object> COLUMN_Updated = new ModelColumn<>(I_C_Greeting.class, "Updated", null);
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
