package de.metas.dunning.model;


/** Generated Interface for C_DunningDoc
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_C_DunningDoc 
{

    /** TableName=C_DunningDoc */
    public static final String Table_Name = "C_DunningDoc";

    /** AD_Table_ID=540401 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(3);

    /** Load Meta Data */

	/**
	 * Get Mandant.
	 * Mandant für diese Installation.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Client_ID();

	public org.compiere.model.I_AD_Client getAD_Client();

    /** Column definition for AD_Client_ID */
    public static final org.adempiere.model.ModelColumn<I_C_DunningDoc, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_C_DunningDoc, org.compiere.model.I_AD_Client>(I_C_DunningDoc.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Org_ID();

	public org.compiere.model.I_AD_Org getAD_Org();

	public void setAD_Org(org.compiere.model.I_AD_Org AD_Org);

    /** Column definition for AD_Org_ID */
    public static final org.adempiere.model.ModelColumn<I_C_DunningDoc, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_C_DunningDoc, org.compiere.model.I_AD_Org>(I_C_DunningDoc.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Anschrift-Text.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setBPartnerAddress (java.lang.String BPartnerAddress);

	/**
	 * Get Anschrift-Text.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getBPartnerAddress();

    /** Column definition for BPartnerAddress */
    public static final org.adempiere.model.ModelColumn<I_C_DunningDoc, Object> COLUMN_BPartnerAddress = new org.adempiere.model.ModelColumn<I_C_DunningDoc, Object>(I_C_DunningDoc.class, "BPartnerAddress", null);
    /** Column name BPartnerAddress */
    public static final String COLUMNNAME_BPartnerAddress = "BPartnerAddress";

	/**
	 * Set Geschäftspartner.
	 * Bezeichnet einen Geschäftspartner
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_BPartner_ID (int C_BPartner_ID);

	/**
	 * Get Geschäftspartner.
	 * Bezeichnet einen Geschäftspartner
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_BPartner_ID();

	public org.compiere.model.I_C_BPartner getC_BPartner();

	public void setC_BPartner(org.compiere.model.I_C_BPartner C_BPartner);

    /** Column definition for C_BPartner_ID */
    public static final org.adempiere.model.ModelColumn<I_C_DunningDoc, org.compiere.model.I_C_BPartner> COLUMN_C_BPartner_ID = new org.adempiere.model.ModelColumn<I_C_DunningDoc, org.compiere.model.I_C_BPartner>(I_C_DunningDoc.class, "C_BPartner_ID", org.compiere.model.I_C_BPartner.class);
    /** Column name C_BPartner_ID */
    public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/**
	 * Set Standort.
	 * Identifiziert die (Liefer-) Adresse des Geschäftspartners
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_BPartner_Location_ID (int C_BPartner_Location_ID);

	/**
	 * Get Standort.
	 * Identifiziert die (Liefer-) Adresse des Geschäftspartners
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_BPartner_Location_ID();

	public org.compiere.model.I_C_BPartner_Location getC_BPartner_Location();

	public void setC_BPartner_Location(org.compiere.model.I_C_BPartner_Location C_BPartner_Location);

    /** Column definition for C_BPartner_Location_ID */
    public static final org.adempiere.model.ModelColumn<I_C_DunningDoc, org.compiere.model.I_C_BPartner_Location> COLUMN_C_BPartner_Location_ID = new org.adempiere.model.ModelColumn<I_C_DunningDoc, org.compiere.model.I_C_BPartner_Location>(I_C_DunningDoc.class, "C_BPartner_Location_ID", org.compiere.model.I_C_BPartner_Location.class);
    /** Column name C_BPartner_Location_ID */
    public static final String COLUMNNAME_C_BPartner_Location_ID = "C_BPartner_Location_ID";

	/**
	 * Set Belegart.
	 * Belegart oder Verarbeitungsvorgaben
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_DocType_ID (int C_DocType_ID);

	/**
	 * Get Belegart.
	 * Belegart oder Verarbeitungsvorgaben
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_DocType_ID();

	public org.compiere.model.I_C_DocType getC_DocType();

	public void setC_DocType(org.compiere.model.I_C_DocType C_DocType);

    /** Column definition for C_DocType_ID */
    public static final org.adempiere.model.ModelColumn<I_C_DunningDoc, org.compiere.model.I_C_DocType> COLUMN_C_DocType_ID = new org.adempiere.model.ModelColumn<I_C_DunningDoc, org.compiere.model.I_C_DocType>(I_C_DunningDoc.class, "C_DocType_ID", org.compiere.model.I_C_DocType.class);
    /** Column name C_DocType_ID */
    public static final String COLUMNNAME_C_DocType_ID = "C_DocType_ID";

	/**
	 * Set Mahnkontakt.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Dunning_Contact_ID (int C_Dunning_Contact_ID);

	/**
	 * Get Mahnkontakt.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Dunning_Contact_ID();

	public org.compiere.model.I_AD_User getC_Dunning_Contact();

	public void setC_Dunning_Contact(org.compiere.model.I_AD_User C_Dunning_Contact);

    /** Column definition for C_Dunning_Contact_ID */
    public static final org.adempiere.model.ModelColumn<I_C_DunningDoc, org.compiere.model.I_AD_User> COLUMN_C_Dunning_Contact_ID = new org.adempiere.model.ModelColumn<I_C_DunningDoc, org.compiere.model.I_AD_User>(I_C_DunningDoc.class, "C_Dunning_Contact_ID", org.compiere.model.I_AD_User.class);
    /** Column name C_Dunning_Contact_ID */
    public static final String COLUMNNAME_C_Dunning_Contact_ID = "C_Dunning_Contact_ID";

	/**
	 * Set Dunning Document.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_DunningDoc_ID (int C_DunningDoc_ID);

	/**
	 * Get Dunning Document.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_DunningDoc_ID();

    /** Column definition for C_DunningDoc_ID */
    public static final org.adempiere.model.ModelColumn<I_C_DunningDoc, Object> COLUMN_C_DunningDoc_ID = new org.adempiere.model.ModelColumn<I_C_DunningDoc, Object>(I_C_DunningDoc.class, "C_DunningDoc_ID", null);
    /** Column name C_DunningDoc_ID */
    public static final String COLUMNNAME_C_DunningDoc_ID = "C_DunningDoc_ID";

	/**
	 * Set Mahnstufe.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_DunningLevel_ID (int C_DunningLevel_ID);

	/**
	 * Get Mahnstufe.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_DunningLevel_ID();

	public org.compiere.model.I_C_DunningLevel getC_DunningLevel();

	public void setC_DunningLevel(org.compiere.model.I_C_DunningLevel C_DunningLevel);

    /** Column definition for C_DunningLevel_ID */
    public static final org.adempiere.model.ModelColumn<I_C_DunningDoc, org.compiere.model.I_C_DunningLevel> COLUMN_C_DunningLevel_ID = new org.adempiere.model.ModelColumn<I_C_DunningDoc, org.compiere.model.I_C_DunningLevel>(I_C_DunningDoc.class, "C_DunningLevel_ID", org.compiere.model.I_C_DunningLevel.class);
    /** Column name C_DunningLevel_ID */
    public static final String COLUMNNAME_C_DunningLevel_ID = "C_DunningLevel_ID";

	/**
	 * Get Erstellt.
	 * Datum, an dem dieser Eintrag erstellt wurde
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getCreated();

    /** Column definition for Created */
    public static final org.adempiere.model.ModelColumn<I_C_DunningDoc, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_C_DunningDoc, Object>(I_C_DunningDoc.class, "Created", null);
    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/**
	 * Get Erstellt durch.
	 * Nutzer, der diesen Eintrag erstellt hat
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getCreatedBy();

    /** Column definition for CreatedBy */
    public static final org.adempiere.model.ModelColumn<I_C_DunningDoc, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_C_DunningDoc, org.compiere.model.I_AD_User>(I_C_DunningDoc.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Beschreibung.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDescription (java.lang.String Description);

	/**
	 * Get Beschreibung.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDescription();

    /** Column definition for Description */
    public static final org.adempiere.model.ModelColumn<I_C_DunningDoc, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_C_DunningDoc, Object>(I_C_DunningDoc.class, "Description", null);
    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/**
	 * Set Belegverarbeitung.
	 * Der zukünftige Status des Belegs
	 *
	 * <br>Type: Button
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDocAction (java.lang.String DocAction);

	/**
	 * Get Belegverarbeitung.
	 * Der zukünftige Status des Belegs
	 *
	 * <br>Type: Button
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDocAction();

    /** Column definition for DocAction */
    public static final org.adempiere.model.ModelColumn<I_C_DunningDoc, Object> COLUMN_DocAction = new org.adempiere.model.ModelColumn<I_C_DunningDoc, Object>(I_C_DunningDoc.class, "DocAction", null);
    /** Column name DocAction */
    public static final String COLUMNNAME_DocAction = "DocAction";

	/**
	 * Set Belegstatus.
	 * The current status of the document
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDocStatus (java.lang.String DocStatus);

	/**
	 * Get Belegstatus.
	 * The current status of the document
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDocStatus();

    /** Column definition for DocStatus */
    public static final org.adempiere.model.ModelColumn<I_C_DunningDoc, Object> COLUMN_DocStatus = new org.adempiere.model.ModelColumn<I_C_DunningDoc, Object>(I_C_DunningDoc.class, "DocStatus", null);
    /** Column name DocStatus */
    public static final String COLUMNNAME_DocStatus = "DocStatus";

	/**
	 * Set Nr..
	 * Document sequence number of the document
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDocumentNo (java.lang.String DocumentNo);

	/**
	 * Get Nr..
	 * Document sequence number of the document
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDocumentNo();

    /** Column definition for DocumentNo */
    public static final org.adempiere.model.ModelColumn<I_C_DunningDoc, Object> COLUMN_DocumentNo = new org.adempiere.model.ModelColumn<I_C_DunningDoc, Object>(I_C_DunningDoc.class, "DocumentNo", null);
    /** Column name DocumentNo */
    public static final String COLUMNNAME_DocumentNo = "DocumentNo";

	/**
	 * Set Dunning Date.
	 * Date of Dunning
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDunningDate (java.sql.Timestamp DunningDate);

	/**
	 * Get Dunning Date.
	 * Date of Dunning
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getDunningDate();

    /** Column definition for DunningDate */
    public static final org.adempiere.model.ModelColumn<I_C_DunningDoc, Object> COLUMN_DunningDate = new org.adempiere.model.ModelColumn<I_C_DunningDoc, Object>(I_C_DunningDoc.class, "DunningDate", null);
    /** Column name DunningDate */
    public static final String COLUMNNAME_DunningDate = "DunningDate";

	/**
	 * Set Aktiv.
	 * Der Eintrag ist im System aktiv
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsActive (boolean IsActive);

	/**
	 * Get Aktiv.
	 * Der Eintrag ist im System aktiv
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isActive();

    /** Column definition for IsActive */
    public static final org.adempiere.model.ModelColumn<I_C_DunningDoc, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_C_DunningDoc, Object>(I_C_DunningDoc.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Benutze abw. Adresse.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsUseBPartnerAddress (boolean IsUseBPartnerAddress);

	/**
	 * Get Benutze abw. Adresse.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isUseBPartnerAddress();

    /** Column definition for IsUseBPartnerAddress */
    public static final org.adempiere.model.ModelColumn<I_C_DunningDoc, Object> COLUMN_IsUseBPartnerAddress = new org.adempiere.model.ModelColumn<I_C_DunningDoc, Object>(I_C_DunningDoc.class, "IsUseBPartnerAddress", null);
    /** Column name IsUseBPartnerAddress */
    public static final String COLUMNNAME_IsUseBPartnerAddress = "IsUseBPartnerAddress";

	/**
	 * Set Massenaustritt.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	public void setIsWriteOff (boolean IsWriteOff);

	/**
	 * Get Massenaustritt.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	public boolean isWriteOff();

    /** Column definition for IsWriteOff */
    public static final org.adempiere.model.ModelColumn<I_C_DunningDoc, Object> COLUMN_IsWriteOff = new org.adempiere.model.ModelColumn<I_C_DunningDoc, Object>(I_C_DunningDoc.class, "IsWriteOff", null);
    /** Column name IsWriteOff */
    public static final String COLUMNNAME_IsWriteOff = "IsWriteOff";

	/**
	 * Set Verarbeitet.
	 * Checkbox sagt aus, ob der Beleg verarbeitet wurde.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setProcessed (boolean Processed);

	/**
	 * Get Verarbeitet.
	 * Checkbox sagt aus, ob der Beleg verarbeitet wurde.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isProcessed();

    /** Column definition for Processed */
    public static final org.adempiere.model.ModelColumn<I_C_DunningDoc, Object> COLUMN_Processed = new org.adempiere.model.ModelColumn<I_C_DunningDoc, Object>(I_C_DunningDoc.class, "Processed", null);
    /** Column name Processed */
    public static final String COLUMNNAME_Processed = "Processed";

	/**
	 * Set Process Now.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setProcessing (boolean Processing);

	/**
	 * Get Process Now.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isProcessing();

    /** Column definition for Processing */
    public static final org.adempiere.model.ModelColumn<I_C_DunningDoc, Object> COLUMN_Processing = new org.adempiere.model.ModelColumn<I_C_DunningDoc, Object>(I_C_DunningDoc.class, "Processing", null);
    /** Column name Processing */
    public static final String COLUMNNAME_Processing = "Processing";

	/**
	 * Get Aktualisiert.
	 * Datum, an dem dieser Eintrag aktualisiert wurde
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getUpdated();

    /** Column definition for Updated */
    public static final org.adempiere.model.ModelColumn<I_C_DunningDoc, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_C_DunningDoc, Object>(I_C_DunningDoc.class, "Updated", null);
    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Aktualisiert durch.
	 * Nutzer, der diesen Eintrag aktualisiert hat
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getUpdatedBy();

    /** Column definition for UpdatedBy */
    public static final org.adempiere.model.ModelColumn<I_C_DunningDoc, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_C_DunningDoc, org.compiere.model.I_AD_User>(I_C_DunningDoc.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
