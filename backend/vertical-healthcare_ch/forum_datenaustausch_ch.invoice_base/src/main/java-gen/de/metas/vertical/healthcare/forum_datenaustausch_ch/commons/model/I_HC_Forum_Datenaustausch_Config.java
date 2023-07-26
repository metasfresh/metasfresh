package de.metas.vertical.healthcare.forum_datenaustausch_ch.commons.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;

/** Generated Interface for HC_Forum_Datenaustausch_Config
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_HC_Forum_Datenaustausch_Config 
{

	String Table_Name = "HC_Forum_Datenaustausch_Config";

//	/** AD_Table_ID=541145 */
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
	 * Set Bill Business Partner.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setBill_BPartner_ID (int Bill_BPartner_ID);

	/**
	 * Get Bill Business Partner.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getBill_BPartner_ID();

	String COLUMNNAME_Bill_BPartner_ID = "Bill_BPartner_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_HC_Forum_Datenaustausch_Config, Object> COLUMN_Created = new ModelColumn<>(I_HC_Forum_Datenaustausch_Config.class, "Created", null);
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
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDescription (@Nullable java.lang.String Description);

	/**
	 * Get Description.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDescription();

	ModelColumn<I_HC_Forum_Datenaustausch_Config, Object> COLUMN_Description = new ModelColumn<>(I_HC_Forum_Datenaustausch_Config.class, "Description", null);
	String COLUMNNAME_Description = "Description";

	/**
	 * Set Modus der Exportdateien.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setExportedXmlMode (java.lang.String ExportedXmlMode);

	/**
	 * Get Modus der Exportdateien.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getExportedXmlMode();

	ModelColumn<I_HC_Forum_Datenaustausch_Config, Object> COLUMN_ExportedXmlMode = new ModelColumn<>(I_HC_Forum_Datenaustausch_Config.class, "ExportedXmlMode", null);
	String COLUMNNAME_ExportedXmlMode = "ExportedXmlMode";

	/**
	 * Set Export XML Version.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setExportedXmlVersion (java.lang.String ExportedXmlVersion);

	/**
	 * Get Export XML Version.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getExportedXmlVersion();

	ModelColumn<I_HC_Forum_Datenaustausch_Config, Object> COLUMN_ExportedXmlVersion = new ModelColumn<>(I_HC_Forum_Datenaustausch_Config.class, "ExportedXmlVersion", null);
	String COLUMNNAME_ExportedXmlVersion = "ExportedXmlVersion";

	/**
	 * Set Absender EAN.
	 * Falls angegeben ersetzt dieser Wert den Wert des request/processing/transport from Attributes in der exportierten XML-Datei
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setFrom_EAN (@Nullable java.lang.String From_EAN);

	/**
	 * Get Absender EAN.
	 * Falls angegeben ersetzt dieser Wert den Wert des request/processing/transport from Attributes in der exportierten XML-Datei
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getFrom_EAN();

	ModelColumn<I_HC_Forum_Datenaustausch_Config, Object> COLUMN_From_EAN = new ModelColumn<>(I_HC_Forum_Datenaustausch_Config.class, "From_EAN", null);
	String COLUMNNAME_From_EAN = "From_EAN";

	/**
	 * Set HC_Forum_Datenaustausch.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setHC_Forum_Datenaustausch_Config_ID (int HC_Forum_Datenaustausch_Config_ID);

	/**
	 * Get HC_Forum_Datenaustausch.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getHC_Forum_Datenaustausch_Config_ID();

	ModelColumn<I_HC_Forum_Datenaustausch_Config, Object> COLUMN_HC_Forum_Datenaustausch_Config_ID = new ModelColumn<>(I_HC_Forum_Datenaustausch_Config.class, "HC_Forum_Datenaustausch_Config_ID", null);
	String COLUMNNAME_HC_Forum_Datenaustausch_Config_ID = "HC_Forum_Datenaustausch_Config_ID";

	/**
	 * Set Sprache.
	 * Sprache für einen Geschäftspartner, der beim Import neu angelegt oder aktualisiert wird
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setImportedBPartnerLanguage (@Nullable java.lang.String ImportedBPartnerLanguage);

	/**
	 * Get Sprache.
	 * Sprache für einen Geschäftspartner, der beim Import neu angelegt oder aktualisiert wird
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getImportedBPartnerLanguage();

	ModelColumn<I_HC_Forum_Datenaustausch_Config, Object> COLUMN_ImportedBPartnerLanguage = new ModelColumn<>(I_HC_Forum_Datenaustausch_Config.class, "ImportedBPartnerLanguage", null);
	String COLUMNNAME_ImportedBPartnerLanguage = "ImportedBPartnerLanguage";

	/**
	 * Set Gemeinden-Kundengruppe.
	 * Kundengruppe für eine Gemeinde, die beim Import neu angelegt oder aktualisiert wird
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setImportedMunicipalityBP_Group_ID (int ImportedMunicipalityBP_Group_ID);

	/**
	 * Get Gemeinden-Kundengruppe.
	 * Kundengruppe für eine Gemeinde, die beim Import neu angelegt oder aktualisiert wird
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getImportedMunicipalityBP_Group_ID();

	@Nullable org.compiere.model.I_C_BP_Group getImportedMunicipalityBP_Group();

	void setImportedMunicipalityBP_Group(@Nullable org.compiere.model.I_C_BP_Group ImportedMunicipalityBP_Group);

	ModelColumn<I_HC_Forum_Datenaustausch_Config, org.compiere.model.I_C_BP_Group> COLUMN_ImportedMunicipalityBP_Group_ID = new ModelColumn<>(I_HC_Forum_Datenaustausch_Config.class, "ImportedMunicipalityBP_Group_ID", org.compiere.model.I_C_BP_Group.class);
	String COLUMNNAME_ImportedMunicipalityBP_Group_ID = "ImportedMunicipalityBP_Group_ID";

	/**
	 * Set Patienten-Kundengruppe.
	 * Kundengruppe für einen Patienten, der beim Import neu angelegt oder aktualisiert wird
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setImportedPartientBP_Group_ID (int ImportedPartientBP_Group_ID);

	/**
	 * Get Patienten-Kundengruppe.
	 * Kundengruppe für einen Patienten, der beim Import neu angelegt oder aktualisiert wird
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getImportedPartientBP_Group_ID();

	@Nullable org.compiere.model.I_C_BP_Group getImportedPartientBP_Group();

	void setImportedPartientBP_Group(@Nullable org.compiere.model.I_C_BP_Group ImportedPartientBP_Group);

	ModelColumn<I_HC_Forum_Datenaustausch_Config, org.compiere.model.I_C_BP_Group> COLUMN_ImportedPartientBP_Group_ID = new ModelColumn<>(I_HC_Forum_Datenaustausch_Config.class, "ImportedPartientBP_Group_ID", org.compiere.model.I_C_BP_Group.class);
	String COLUMNNAME_ImportedPartientBP_Group_ID = "ImportedPartientBP_Group_ID";

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

	ModelColumn<I_HC_Forum_Datenaustausch_Config, Object> COLUMN_IsActive = new ModelColumn<>(I_HC_Forum_Datenaustausch_Config.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Speicherverzeichnis.
	 * Verzeichnis, in dem exportierte Dateien gespeichert werden.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setStoreDirectory (@Nullable java.lang.String StoreDirectory);

	/**
	 * Get Speicherverzeichnis.
	 * Verzeichnis, in dem exportierte Dateien gespeichert werden.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getStoreDirectory();

	ModelColumn<I_HC_Forum_Datenaustausch_Config, Object> COLUMN_StoreDirectory = new ModelColumn<>(I_HC_Forum_Datenaustausch_Config.class, "StoreDirectory", null);
	String COLUMNNAME_StoreDirectory = "StoreDirectory";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_HC_Forum_Datenaustausch_Config, Object> COLUMN_Updated = new ModelColumn<>(I_HC_Forum_Datenaustausch_Config.class, "Updated", null);
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
	 * Set Via EAN.
	 * Dieser Wert wird bei request/processing/transport/via in die exportierten XML-Datei eingefügt. Evtl. vorhandene "via" Knoten werden entfernt.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setVia_EAN (java.lang.String Via_EAN);

	/**
	 * Get Via EAN.
	 * Dieser Wert wird bei request/processing/transport/via in die exportierten XML-Datei eingefügt. Evtl. vorhandene "via" Knoten werden entfernt.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getVia_EAN();

	ModelColumn<I_HC_Forum_Datenaustausch_Config, Object> COLUMN_Via_EAN = new ModelColumn<>(I_HC_Forum_Datenaustausch_Config.class, "Via_EAN", null);
	String COLUMNNAME_Via_EAN = "Via_EAN";
}
