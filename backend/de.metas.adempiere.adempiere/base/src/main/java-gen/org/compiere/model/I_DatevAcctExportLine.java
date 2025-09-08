package org.compiere.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;

/** Generated Interface for DatevAcctExportLine
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_DatevAcctExportLine 
{

	String Table_Name = "DatevAcctExportLine";

//	/** AD_Table_ID=541523 */
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
	 * Set Belegfeld 1.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setBelegfeld_1 (@Nullable java.lang.String Belegfeld_1);

	/**
	 * Get Belegfeld 1.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getBelegfeld_1();

	ModelColumn<I_DatevAcctExportLine, Object> COLUMN_Belegfeld_1 = new ModelColumn<>(I_DatevAcctExportLine.class, "Belegfeld_1", null);
	String COLUMNNAME_Belegfeld_1 = "Belegfeld_1";

	/**
	 * Set Belegfeld 2.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setBelegfeld_2 (@Nullable java.lang.String Belegfeld_2);

	/**
	 * Get Belegfeld 2.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getBelegfeld_2();

	ModelColumn<I_DatevAcctExportLine, Object> COLUMN_Belegfeld_2 = new ModelColumn<>(I_DatevAcctExportLine.class, "Belegfeld_2", null);
	String COLUMNNAME_Belegfeld_2 = "Belegfeld_2";

	/**
	 * Set BU.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setBU (@Nullable java.lang.String BU);

	/**
	 * Get BU.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getBU();

	ModelColumn<I_DatevAcctExportLine, Object> COLUMN_BU = new ModelColumn<>(I_DatevAcctExportLine.class, "BU", null);
	String COLUMNNAME_BU = "BU";

	/**
	 * Set Buchungstext.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setBuchungstext (@Nullable java.lang.String Buchungstext);

	/**
	 * Get Buchungstext.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getBuchungstext();

	ModelColumn<I_DatevAcctExportLine, Object> COLUMN_Buchungstext = new ModelColumn<>(I_DatevAcctExportLine.class, "Buchungstext", null);
	String COLUMNNAME_Buchungstext = "Buchungstext";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_DatevAcctExportLine, Object> COLUMN_Created = new ModelColumn<>(I_DatevAcctExportLine.class, "Created", null);
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
	 * Set Datev Accounting Export.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDatevAcctExport_ID (int DatevAcctExport_ID);

	/**
	 * Get Datev Accounting Export.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getDatevAcctExport_ID();

	org.compiere.model.I_DatevAcctExport getDatevAcctExport();

	void setDatevAcctExport(org.compiere.model.I_DatevAcctExport DatevAcctExport);

	ModelColumn<I_DatevAcctExportLine, org.compiere.model.I_DatevAcctExport> COLUMN_DatevAcctExport_ID = new ModelColumn<>(I_DatevAcctExportLine.class, "DatevAcctExport_ID", org.compiere.model.I_DatevAcctExport.class);
	String COLUMNNAME_DatevAcctExport_ID = "DatevAcctExport_ID";

	/**
	 * Set Datev Accounting Export Line.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDatevAcctExportLine_ID (int DatevAcctExportLine_ID);

	/**
	 * Get Datev Accounting Export Line.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getDatevAcctExportLine_ID();

	ModelColumn<I_DatevAcctExportLine, Object> COLUMN_DatevAcctExportLine_ID = new ModelColumn<>(I_DatevAcctExportLine.class, "DatevAcctExportLine_ID", null);
	String COLUMNNAME_DatevAcctExportLine_ID = "DatevAcctExportLine_ID";

	/**
	 * Set Datev Kost1.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDatev_Kost1 (int Datev_Kost1);

	/**
	 * Get Datev Kost1.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getDatev_Kost1();

	ModelColumn<I_DatevAcctExportLine, Object> COLUMN_Datev_Kost1 = new ModelColumn<>(I_DatevAcctExportLine.class, "Datev_Kost1", null);
	String COLUMNNAME_Datev_Kost1 = "Datev_Kost1";

	/**
	 * Set Datev Kost2.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDatev_Kost2 (int Datev_Kost2);

	/**
	 * Get Datev Kost2.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getDatev_Kost2();

	ModelColumn<I_DatevAcctExportLine, Object> COLUMN_Datev_Kost2 = new ModelColumn<>(I_DatevAcctExportLine.class, "Datev_Kost2", null);
	String COLUMNNAME_Datev_Kost2 = "Datev_Kost2";

	/**
	 * Set Datum.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDatum (@Nullable java.lang.String Datum);

	/**
	 * Get Datum.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDatum();

	ModelColumn<I_DatevAcctExportLine, Object> COLUMN_Datum = new ModelColumn<>(I_DatevAcctExportLine.class, "Datum", null);
	String COLUMNNAME_Datum = "Datum";

	/**
	 * Set FS.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setFS (int FS);

	/**
	 * Get FS.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getFS();

	ModelColumn<I_DatevAcctExportLine, Object> COLUMN_FS = new ModelColumn<>(I_DatevAcctExportLine.class, "FS", null);
	String COLUMNNAME_FS = "FS";

	/**
	 * Set Gegenkonto.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setGegenkonto (@Nullable java.lang.String Gegenkonto);

	/**
	 * Get Gegenkonto.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getGegenkonto();

	ModelColumn<I_DatevAcctExportLine, Object> COLUMN_Gegenkonto = new ModelColumn<>(I_DatevAcctExportLine.class, "Gegenkonto", null);
	String COLUMNNAME_Gegenkonto = "Gegenkonto";

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

	ModelColumn<I_DatevAcctExportLine, Object> COLUMN_IsActive = new ModelColumn<>(I_DatevAcctExportLine.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Konto.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setKonto (@Nullable java.lang.String Konto);

	/**
	 * Get Konto.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getKonto();

	ModelColumn<I_DatevAcctExportLine, Object> COLUMN_Konto = new ModelColumn<>(I_DatevAcctExportLine.class, "Konto", null);
	String COLUMNNAME_Konto = "Konto";

	/**
	 * Set Leistungsdatum.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setLeistungsdatum (@Nullable java.lang.String Leistungsdatum);

	/**
	 * Get Leistungsdatum.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getLeistungsdatum();

	ModelColumn<I_DatevAcctExportLine, Object> COLUMN_Leistungsdatum = new ModelColumn<>(I_DatevAcctExportLine.class, "Leistungsdatum", null);
	String COLUMNNAME_Leistungsdatum = "Leistungsdatum";

	/**
	 * Set Skonto.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setSkonto (@Nullable java.lang.String Skonto);

	/**
	 * Get Skonto.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getSkonto();

	ModelColumn<I_DatevAcctExportLine, Object> COLUMN_Skonto = new ModelColumn<>(I_DatevAcctExportLine.class, "Skonto", null);
	String COLUMNNAME_Skonto = "Skonto";

	/**
	 * Set Umsatz.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setUmsatz (@Nullable java.lang.String Umsatz);

	/**
	 * Get Umsatz.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getUmsatz();

	ModelColumn<I_DatevAcctExportLine, Object> COLUMN_Umsatz = new ModelColumn<>(I_DatevAcctExportLine.class, "Umsatz", null);
	String COLUMNNAME_Umsatz = "Umsatz";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_DatevAcctExportLine, Object> COLUMN_Updated = new ModelColumn<>(I_DatevAcctExportLine.class, "Updated", null);
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
	 * Set ZI-Art.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setZI_Art (@Nullable java.lang.String ZI_Art);

	/**
	 * Get ZI-Art.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getZI_Art();

	ModelColumn<I_DatevAcctExportLine, Object> COLUMN_ZI_Art = new ModelColumn<>(I_DatevAcctExportLine.class, "ZI_Art", null);
	String COLUMNNAME_ZI_Art = "ZI_Art";

	/**
	 * Set ZI-Inhalt.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setZI_Inhalt (@Nullable java.lang.String ZI_Inhalt);

	/**
	 * Get ZI-Inhalt.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getZI_Inhalt();

	ModelColumn<I_DatevAcctExportLine, Object> COLUMN_ZI_Inhalt = new ModelColumn<>(I_DatevAcctExportLine.class, "ZI_Inhalt", null);
	String COLUMNNAME_ZI_Inhalt = "ZI_Inhalt";
}
