package de.metas.materialtracking.model;


/** Generated Interface for M_Material_Tracking_Ref
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_M_Material_Tracking_Ref 
{

    /** TableName=M_Material_Tracking_Ref */
    public static final String Table_Name = "M_Material_Tracking_Ref";

    /** AD_Table_ID=540611 */
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

    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Org_ID();

    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set DB-Tabelle.
	 * Database Table information
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Table_ID (int AD_Table_ID);

	/**
	 * Get DB-Tabelle.
	 * Database Table information
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Table_ID();

    /** Column name AD_Table_ID */
    public static final String COLUMNNAME_AD_Table_ID = "AD_Table_ID";

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
    public static final org.adempiere.model.ModelColumn<I_M_Material_Tracking_Ref, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_M_Material_Tracking_Ref, Object>(I_M_Material_Tracking_Ref.class, "Created", null);
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

    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Nr..
	 * Document sequence number of the document
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	public void setDocumentNo (java.lang.String DocumentNo);

	/**
	 * Get Nr..
	 * Document sequence number of the document
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	public java.lang.String getDocumentNo();

    /** Column definition for DocumentNo */
    public static final org.adempiere.model.ModelColumn<I_M_Material_Tracking_Ref, Object> COLUMN_DocumentNo = new org.adempiere.model.ModelColumn<I_M_Material_Tracking_Ref, Object>(I_M_Material_Tracking_Ref.class, "DocumentNo", null);
    /** Column name DocumentNo */
    public static final String COLUMNNAME_DocumentNo = "DocumentNo";

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
    public static final org.adempiere.model.ModelColumn<I_M_Material_Tracking_Ref, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_M_Material_Tracking_Ref, Object>(I_M_Material_Tracking_Ref.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Quality Inspection Document.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsQualityInspectionDoc (boolean IsQualityInspectionDoc);

	/**
	 * Get Quality Inspection Document.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isQualityInspectionDoc();

    /** Column definition for IsQualityInspectionDoc */
    public static final org.adempiere.model.ModelColumn<I_M_Material_Tracking_Ref, Object> COLUMN_IsQualityInspectionDoc = new org.adempiere.model.ModelColumn<I_M_Material_Tracking_Ref, Object>(I_M_Material_Tracking_Ref.class, "IsQualityInspectionDoc", null);
    /** Column name IsQualityInspectionDoc */
    public static final String COLUMNNAME_IsQualityInspectionDoc = "IsQualityInspectionDoc";

	/**
	 * Set Material-Vorgang-ID.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_Material_Tracking_ID (int M_Material_Tracking_ID);

	/**
	 * Get Material-Vorgang-ID.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_Material_Tracking_ID();

	public de.metas.materialtracking.model.I_M_Material_Tracking getM_Material_Tracking();

	public void setM_Material_Tracking(de.metas.materialtracking.model.I_M_Material_Tracking M_Material_Tracking);

    /** Column definition for M_Material_Tracking_ID */
    public static final org.adempiere.model.ModelColumn<I_M_Material_Tracking_Ref, de.metas.materialtracking.model.I_M_Material_Tracking> COLUMN_M_Material_Tracking_ID = new org.adempiere.model.ModelColumn<I_M_Material_Tracking_Ref, de.metas.materialtracking.model.I_M_Material_Tracking>(I_M_Material_Tracking_Ref.class, "M_Material_Tracking_ID", de.metas.materialtracking.model.I_M_Material_Tracking.class);
    /** Column name M_Material_Tracking_ID */
    public static final String COLUMNNAME_M_Material_Tracking_ID = "M_Material_Tracking_ID";

	/**
	 * Set Material Tracking Reference.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_Material_Tracking_Ref_ID (int M_Material_Tracking_Ref_ID);

	/**
	 * Get Material Tracking Reference.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_Material_Tracking_Ref_ID();

    /** Column definition for M_Material_Tracking_Ref_ID */
    public static final org.adempiere.model.ModelColumn<I_M_Material_Tracking_Ref, Object> COLUMN_M_Material_Tracking_Ref_ID = new org.adempiere.model.ModelColumn<I_M_Material_Tracking_Ref, Object>(I_M_Material_Tracking_Ref.class, "M_Material_Tracking_Ref_ID", null);
    /** Column name M_Material_Tracking_Ref_ID */
    public static final String COLUMNNAME_M_Material_Tracking_Ref_ID = "M_Material_Tracking_Ref_ID";

	/**
	 * Set Bewegungsdatum.
	 * Datum, an dem eine Produkt in oder aus dem Bestand bewegt wurde
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	public void setMovementDate (java.sql.Timestamp MovementDate);

	/**
	 * Get Bewegungsdatum.
	 * Datum, an dem eine Produkt in oder aus dem Bestand bewegt wurde
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	public java.sql.Timestamp getMovementDate();

    /** Column definition for MovementDate */
    public static final org.adempiere.model.ModelColumn<I_M_Material_Tracking_Ref, Object> COLUMN_MovementDate = new org.adempiere.model.ModelColumn<I_M_Material_Tracking_Ref, Object>(I_M_Material_Tracking_Ref.class, "MovementDate", null);
    /** Column name MovementDate */
    public static final String COLUMNNAME_MovementDate = "MovementDate";

	/**
	 * Set Ausgelagerte Menge.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setQtyIssued (java.math.BigDecimal QtyIssued);

	/**
	 * Get Ausgelagerte Menge.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyIssued();

    /** Column definition for QtyIssued */
    public static final org.adempiere.model.ModelColumn<I_M_Material_Tracking_Ref, Object> COLUMN_QtyIssued = new org.adempiere.model.ModelColumn<I_M_Material_Tracking_Ref, Object>(I_M_Material_Tracking_Ref.class, "QtyIssued", null);
    /** Column name QtyIssued */
    public static final String COLUMNNAME_QtyIssued = "QtyIssued";

	/**
	 * Set Empfangene Menge.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	public void setQtyReceived (java.math.BigDecimal QtyReceived);

	/**
	 * Get Empfangene Menge.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	public java.math.BigDecimal getQtyReceived();

    /** Column definition for QtyReceived */
    public static final org.adempiere.model.ModelColumn<I_M_Material_Tracking_Ref, Object> COLUMN_QtyReceived = new org.adempiere.model.ModelColumn<I_M_Material_Tracking_Ref, Object>(I_M_Material_Tracking_Ref.class, "QtyReceived", null);
    /** Column name QtyReceived */
    public static final String COLUMNNAME_QtyReceived = "QtyReceived";

	/**
	 * Set Datensatz-ID.
	 * Direct internal record ID
	 *
	 * <br>Type: Button
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setRecord_ID (int Record_ID);

	/**
	 * Get Datensatz-ID.
	 * Direct internal record ID
	 *
	 * <br>Type: Button
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getRecord_ID();

    /** Column definition for Record_ID */
    public static final org.adempiere.model.ModelColumn<I_M_Material_Tracking_Ref, Object> COLUMN_Record_ID = new org.adempiere.model.ModelColumn<I_M_Material_Tracking_Ref, Object>(I_M_Material_Tracking_Ref.class, "Record_ID", null);
    /** Column name Record_ID */
    public static final String COLUMNNAME_Record_ID = "Record_ID";

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
    public static final org.adempiere.model.ModelColumn<I_M_Material_Tracking_Ref, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_M_Material_Tracking_Ref, Object>(I_M_Material_Tracking_Ref.class, "Updated", null);
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

    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
