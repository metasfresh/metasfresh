package org.compiere.model;

import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for C_BPartner_Export
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_BPartner_Export 
{

	String Table_Name = "C_BPartner_Export";

//	/** AD_Table_ID=541731 */
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
	 * Set Straße und Nr..
	 * Adresszeile 1 für diesen Standort
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAddress1 (@Nullable java.lang.String Address1);

	/**
	 * Get Straße und Nr..
	 * Adresszeile 1 für diesen Standort
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getAddress1();

	ModelColumn<I_C_BPartner_Export, Object> COLUMN_Address1 = new ModelColumn<>(I_C_BPartner_Export.class, "Address1", null);
	String COLUMNNAME_Address1 = "Address1";

	/**
	 * Set Adresszusatz.
	 * Adresszeile 2 für diesen Standort
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAddress2 (@Nullable java.lang.String Address2);

	/**
	 * Get Adresszusatz.
	 * Adresszeile 2 für diesen Standort
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getAddress2();

	ModelColumn<I_C_BPartner_Export, Object> COLUMN_Address2 = new ModelColumn<>(I_C_BPartner_Export.class, "Address2", null);
	String COLUMNNAME_Address2 = "Address2";

	/**
	 * Set Adresszeile 3.
	 * Adresszeilee 3 für diesen Standort
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAddress3 (@Nullable java.lang.String Address3);

	/**
	 * Get Adresszeile 3.
	 * Adresszeilee 3 für diesen Standort
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getAddress3();

	ModelColumn<I_C_BPartner_Export, Object> COLUMN_Address3 = new ModelColumn<>(I_C_BPartner_Export.class, "Address3", null);
	String COLUMNNAME_Address3 = "Address3";

	/**
	 * Set Adresszusatz.
	 * Adresszeile 4 für diesen Standort
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAddress4 (@Nullable java.lang.String Address4);

	/**
	 * Get Adresszusatz.
	 * Adresszeile 4 für diesen Standort
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getAddress4();

	ModelColumn<I_C_BPartner_Export, Object> COLUMN_Address4 = new ModelColumn<>(I_C_BPartner_Export.class, "Address4", null);
	String COLUMNNAME_Address4 = "Address4";

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

	ModelColumn<I_C_BPartner_Export, Object> COLUMN_AD_Language = new ModelColumn<>(I_C_BPartner_Export.class, "AD_Language", null);
	String COLUMNNAME_AD_Language = "AD_Language";

	/**
	 * Set Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_Org_ID();

	String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Geburtstag.
	 * Geburtstag oder Jahrestag
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setBirthday (@Nullable java.sql.Timestamp Birthday);

	/**
	 * Get Geburtstag.
	 * Geburtstag oder Jahrestag
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getBirthday();

	ModelColumn<I_C_BPartner_Export, Object> COLUMN_Birthday = new ModelColumn<>(I_C_BPartner_Export.class, "Birthday", null);
	String COLUMNNAME_Birthday = "Birthday";

	/**
	 * Set Name.
	 * Name des Sponsors.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setBPName (@Nullable java.lang.String BPName);

	/**
	 * Get Name.
	 * Name des Sponsors.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getBPName();

	ModelColumn<I_C_BPartner_Export, Object> COLUMN_BPName = new ModelColumn<>(I_C_BPartner_Export.class, "BPName", null);
	String COLUMNNAME_BPName = "BPName";

	/**
	 * Set Nr..
	 * Sponsor-Nr.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setBPValue (@Nullable java.lang.String BPValue);

	/**
	 * Get Nr..
	 * Sponsor-Nr.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getBPValue();

	ModelColumn<I_C_BPartner_Export, Object> COLUMN_BPValue = new ModelColumn<>(I_C_BPartner_Export.class, "BPValue", null);
	String COLUMNNAME_BPValue = "BPValue";

	/**
	 * Set Kategorie.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCategory (@Nullable java.lang.String Category);

	/**
	 * Get Kategorie.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getCategory();

	ModelColumn<I_C_BPartner_Export, Object> COLUMN_Category = new ModelColumn<>(I_C_BPartner_Export.class, "Category", null);
	String COLUMNNAME_Category = "Category";

	/**
	 * Set Partner Export.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_BPartner_Export_ID (int C_BPartner_Export_ID);

	/**
	 * Get Partner Export.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_BPartner_Export_ID();

	ModelColumn<I_C_BPartner_Export, Object> COLUMN_C_BPartner_Export_ID = new ModelColumn<>(I_C_BPartner_Export.class, "C_BPartner_Export_ID", null);
	String COLUMNNAME_C_BPartner_Export_ID = "C_BPartner_Export_ID";

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
	 * Set Compensation Group Schema.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_CompensationGroup_Schema_ID (int C_CompensationGroup_Schema_ID);

	/**
	 * Get Compensation Group Schema.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_CompensationGroup_Schema_ID();

	@Nullable de.metas.order.model.I_C_CompensationGroup_Schema getC_CompensationGroup_Schema();

	void setC_CompensationGroup_Schema(@Nullable de.metas.order.model.I_C_CompensationGroup_Schema C_CompensationGroup_Schema);

	ModelColumn<I_C_BPartner_Export, de.metas.order.model.I_C_CompensationGroup_Schema> COLUMN_C_CompensationGroup_Schema_ID = new ModelColumn<>(I_C_BPartner_Export.class, "C_CompensationGroup_Schema_ID", de.metas.order.model.I_C_CompensationGroup_Schema.class);
	String COLUMNNAME_C_CompensationGroup_Schema_ID = "C_CompensationGroup_Schema_ID";

	/**
	 * Set Land.
	 * Land
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Country_ID (int C_Country_ID);

	/**
	 * Get Land.
	 * Land
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Country_ID();

	@Nullable org.compiere.model.I_C_Country getC_Country();

	void setC_Country(@Nullable org.compiere.model.I_C_Country C_Country);

	ModelColumn<I_C_BPartner_Export, org.compiere.model.I_C_Country> COLUMN_C_Country_ID = new ModelColumn<>(I_C_BPartner_Export.class, "C_Country_ID", org.compiere.model.I_C_Country.class);
	String COLUMNNAME_C_Country_ID = "C_Country_ID";

	/**
	 * Set Anrede (ID).
	 * Anrede zum Druck auf Korrespondenz
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Greeting_ID (int C_Greeting_ID);

	/**
	 * Get Anrede (ID).
	 * Anrede zum Druck auf Korrespondenz
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Greeting_ID();

	String COLUMNNAME_C_Greeting_ID = "C_Greeting_ID";

	/**
	 * Set Ort.
	 * Name des Ortes
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCity (@Nullable java.lang.String City);

	/**
	 * Get Ort.
	 * Name des Ortes
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getCity();

	ModelColumn<I_C_BPartner_Export, Object> COLUMN_City = new ModelColumn<>(I_C_BPartner_Export.class, "City", null);
	String COLUMNNAME_City = "City";

	/**
	 * Set Company Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCompanyname (@Nullable java.lang.String Companyname);

	/**
	 * Get Company Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getCompanyname();

	ModelColumn<I_C_BPartner_Export, Object> COLUMN_Companyname = new ModelColumn<>(I_C_BPartner_Export.class, "Companyname", null);
	String COLUMNNAME_Companyname = "Companyname";

	/**
	 * Set Sales order.
	 * Order
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Order_ID (int C_Order_ID);

	/**
	 * Get Sales order.
	 * Order
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Order_ID();

	@Nullable org.compiere.model.I_C_Order getC_Order();

	void setC_Order(@Nullable org.compiere.model.I_C_Order C_Order);

	ModelColumn<I_C_BPartner_Export, org.compiere.model.I_C_Order> COLUMN_C_Order_ID = new ModelColumn<>(I_C_BPartner_Export.class, "C_Order_ID", org.compiere.model.I_C_Order.class);
	String COLUMNNAME_C_Order_ID = "C_Order_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_C_BPartner_Export, Object> COLUMN_Created = new ModelColumn<>(I_C_BPartner_Export.class, "Created", null);
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
	 * Set EMail Nutzer-ID.
	 * Nutzer-Name/Konto (ID) im EMail-System
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setEMailUser (@Nullable java.lang.String EMailUser);

	/**
	 * Get EMail Nutzer-ID.
	 * Nutzer-Name/Konto (ID) im EMail-System
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getEMailUser();

	ModelColumn<I_C_BPartner_Export, Object> COLUMN_EMailUser = new ModelColumn<>(I_C_BPartner_Export.class, "EMailUser", null);
	String COLUMNNAME_EMailUser = "EMailUser";

	/**
	 * Set Exclude From Promotions.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setExcludeFromPromotions (boolean ExcludeFromPromotions);

	/**
	 * Get Exclude From Promotions.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isExcludeFromPromotions();

	ModelColumn<I_C_BPartner_Export, Object> COLUMN_ExcludeFromPromotions = new ModelColumn<>(I_C_BPartner_Export.class, "ExcludeFromPromotions", null);
	String COLUMNNAME_ExcludeFromPromotions = "ExcludeFromPromotions";

	/**
	 * Set Vorname.
	 * Vorname
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setFirstname (@Nullable java.lang.String Firstname);

	/**
	 * Get Vorname.
	 * Vorname
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getFirstname();

	ModelColumn<I_C_BPartner_Export, Object> COLUMN_Firstname = new ModelColumn<>(I_C_BPartner_Export.class, "Firstname", null);
	String COLUMNNAME_Firstname = "Firstname";

	/**
	 * Set Anrede.
	 * Für Briefe - z.B. "Sehr geehrter 
{
0}
" oder "Sehr geehrter Herr 
{
0}
" - Zur Laufzeit wird  "
{
0}
" durch den Namen ersetzt
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setGreeting (@Nullable java.lang.String Greeting);

	/**
	 * Get Anrede.
	 * Für Briefe - z.B. "Sehr geehrter 
{
0}
" oder "Sehr geehrter Herr 
{
0}
" - Zur Laufzeit wird  "
{
0}
" durch den Namen ersetzt
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getGreeting();

	ModelColumn<I_C_BPartner_Export, Object> COLUMN_Greeting = new ModelColumn<>(I_C_BPartner_Export.class, "Greeting", null);
	String COLUMNNAME_Greeting = "Greeting";

	/**
	 * Set Abweichende Rechnungsaddresse.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setHasDifferentBillPartner (boolean HasDifferentBillPartner);

	/**
	 * Get Abweichende Rechnungsaddresse.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isHasDifferentBillPartner();

	ModelColumn<I_C_BPartner_Export, Object> COLUMN_HasDifferentBillPartner = new ModelColumn<>(I_C_BPartner_Export.class, "HasDifferentBillPartner", null);
	String COLUMNNAME_HasDifferentBillPartner = "HasDifferentBillPartner";

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

	ModelColumn<I_C_BPartner_Export, Object> COLUMN_IsActive = new ModelColumn<>(I_C_BPartner_Export.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Nachname.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setLastname (@Nullable java.lang.String Lastname);

	/**
	 * Get Nachname.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getLastname();

	ModelColumn<I_C_BPartner_Export, Object> COLUMN_Lastname = new ModelColumn<>(I_C_BPartner_Export.class, "Lastname", null);
	String COLUMNNAME_Lastname = "Lastname";

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

	ModelColumn<I_C_BPartner_Export, Object> COLUMN_Letter_Salutation = new ModelColumn<>(I_C_BPartner_Export.class, "Letter_Salutation", null);
	String COLUMNNAME_Letter_Salutation = "Letter_Salutation";

	/**
	 * Set Master End Date.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setMasterEndDate (@Nullable java.sql.Timestamp MasterEndDate);

	/**
	 * Get Master End Date.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getMasterEndDate();

	ModelColumn<I_C_BPartner_Export, Object> COLUMN_MasterEndDate = new ModelColumn<>(I_C_BPartner_Export.class, "MasterEndDate", null);
	String COLUMNNAME_MasterEndDate = "MasterEndDate";

	/**
	 * Set Master Start Date.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setMasterStartDate (@Nullable java.sql.Timestamp MasterStartDate);

	/**
	 * Get Master Start Date.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getMasterStartDate();

	ModelColumn<I_C_BPartner_Export, Object> COLUMN_MasterStartDate = new ModelColumn<>(I_C_BPartner_Export.class, "MasterStartDate", null);
	String COLUMNNAME_MasterStartDate = "MasterStartDate";

	/**
	 * Set PLZ.
	 * Postleitzahl
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPostal (@Nullable java.lang.String Postal);

	/**
	 * Get PLZ.
	 * Postleitzahl
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getPostal();

	ModelColumn<I_C_BPartner_Export, Object> COLUMN_Postal = new ModelColumn<>(I_C_BPartner_Export.class, "Postal", null);
	String COLUMNNAME_Postal = "Postal";

	/**
	 * Set Termination Reason.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setTerminationReason (@Nullable java.lang.String TerminationReason);

	/**
	 * Get Termination Reason.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getTerminationReason();

	ModelColumn<I_C_BPartner_Export, Object> COLUMN_TerminationReason = new ModelColumn<>(I_C_BPartner_Export.class, "TerminationReason", null);
	String COLUMNNAME_TerminationReason = "TerminationReason";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_C_BPartner_Export, Object> COLUMN_Updated = new ModelColumn<>(I_C_BPartner_Export.class, "Updated", null);
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
