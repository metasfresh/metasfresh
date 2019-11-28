package de.metas.esb.edi.model;


/** Generated Interface for EDI_DesadvLine_SSCC
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_EDI_DesadvLine_SSCC 
{

    /** TableName=EDI_DesadvLine_SSCC */
    public static final String Table_Name = "EDI_DesadvLine_SSCC";

    /** AD_Table_ID=540676 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 1 - Org
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(1);

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
	 * Set Mindesthaltbarkeitsdatum.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setBestBeforeDate (java.sql.Timestamp BestBeforeDate);

	/**
	 * Get Mindesthaltbarkeitsdatum.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getBestBeforeDate();

    /** Column definition for BestBeforeDate */
    public static final org.adempiere.model.ModelColumn<I_EDI_DesadvLine_SSCC, Object> COLUMN_BestBeforeDate = new org.adempiere.model.ModelColumn<I_EDI_DesadvLine_SSCC, Object>(I_EDI_DesadvLine_SSCC.class, "BestBeforeDate", null);
    /** Column name BestBeforeDate */
    public static final String COLUMNNAME_BestBeforeDate = "BestBeforeDate";

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
    public static final org.adempiere.model.ModelColumn<I_EDI_DesadvLine_SSCC, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_EDI_DesadvLine_SSCC, Object>(I_EDI_DesadvLine_SSCC.class, "Created", null);
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
	 * Set DESADV.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setEDI_Desadv_ID (int EDI_Desadv_ID);

	/**
	 * Get DESADV.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getEDI_Desadv_ID();

	public de.metas.esb.edi.model.I_EDI_Desadv getEDI_Desadv();

	public void setEDI_Desadv(de.metas.esb.edi.model.I_EDI_Desadv EDI_Desadv);

    /** Column definition for EDI_Desadv_ID */
    public static final org.adempiere.model.ModelColumn<I_EDI_DesadvLine_SSCC, de.metas.esb.edi.model.I_EDI_Desadv> COLUMN_EDI_Desadv_ID = new org.adempiere.model.ModelColumn<I_EDI_DesadvLine_SSCC, de.metas.esb.edi.model.I_EDI_Desadv>(I_EDI_DesadvLine_SSCC.class, "EDI_Desadv_ID", de.metas.esb.edi.model.I_EDI_Desadv.class);
    /** Column name EDI_Desadv_ID */
    public static final String COLUMNNAME_EDI_Desadv_ID = "EDI_Desadv_ID";

	/**
	 * Set EDI_DesadvLine.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setEDI_DesadvLine_ID (int EDI_DesadvLine_ID);

	/**
	 * Get EDI_DesadvLine.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getEDI_DesadvLine_ID();

	public de.metas.esb.edi.model.I_EDI_DesadvLine getEDI_DesadvLine();

	public void setEDI_DesadvLine(de.metas.esb.edi.model.I_EDI_DesadvLine EDI_DesadvLine);

    /** Column definition for EDI_DesadvLine_ID */
    public static final org.adempiere.model.ModelColumn<I_EDI_DesadvLine_SSCC, de.metas.esb.edi.model.I_EDI_DesadvLine> COLUMN_EDI_DesadvLine_ID = new org.adempiere.model.ModelColumn<I_EDI_DesadvLine_SSCC, de.metas.esb.edi.model.I_EDI_DesadvLine>(I_EDI_DesadvLine_SSCC.class, "EDI_DesadvLine_ID", de.metas.esb.edi.model.I_EDI_DesadvLine.class);
    /** Column name EDI_DesadvLine_ID */
    public static final String COLUMNNAME_EDI_DesadvLine_ID = "EDI_DesadvLine_ID";

	/**
	 * Set EDI Lieferavis SSCC (DESADV).
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setEDI_DesadvLine_SSCC_ID (int EDI_DesadvLine_SSCC_ID);

	/**
	 * Get EDI Lieferavis SSCC (DESADV).
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getEDI_DesadvLine_SSCC_ID();

    /** Column definition for EDI_DesadvLine_SSCC_ID */
    public static final org.adempiere.model.ModelColumn<I_EDI_DesadvLine_SSCC, Object> COLUMN_EDI_DesadvLine_SSCC_ID = new org.adempiere.model.ModelColumn<I_EDI_DesadvLine_SSCC, Object>(I_EDI_DesadvLine_SSCC.class, "EDI_DesadvLine_SSCC_ID", null);
    /** Column name EDI_DesadvLine_SSCC_ID */
    public static final String COLUMNNAME_EDI_DesadvLine_SSCC_ID = "EDI_DesadvLine_SSCC_ID";

	/**
	 * Set SSCC18.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIPA_SSCC18 (java.lang.String IPA_SSCC18);

	/**
	 * Get SSCC18.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getIPA_SSCC18();

    /** Column definition for IPA_SSCC18 */
    public static final org.adempiere.model.ModelColumn<I_EDI_DesadvLine_SSCC, Object> COLUMN_IPA_SSCC18 = new org.adempiere.model.ModelColumn<I_EDI_DesadvLine_SSCC, Object>(I_EDI_DesadvLine_SSCC.class, "IPA_SSCC18", null);
    /** Column name IPA_SSCC18 */
    public static final String COLUMNNAME_IPA_SSCC18 = "IPA_SSCC18";

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
    public static final org.adempiere.model.ModelColumn<I_EDI_DesadvLine_SSCC, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_EDI_DesadvLine_SSCC, Object>(I_EDI_DesadvLine_SSCC.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set manuelle SSCC18.
	 * Wenn der jeweiligen Lieferzeile keine HU zugeordnet ist, dann setzt metasfresh dieses Feld auf "Ja" und der Nutzer kann manuell eine SSCC18 Nummer eintragen.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsManual_IPA_SSCC18 (boolean IsManual_IPA_SSCC18);

	/**
	 * Get manuelle SSCC18.
	 * Wenn der jeweiligen Lieferzeile keine HU zugeordnet ist, dann setzt metasfresh dieses Feld auf "Ja" und der Nutzer kann manuell eine SSCC18 Nummer eintragen.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isManual_IPA_SSCC18();

    /** Column definition for IsManual_IPA_SSCC18 */
    public static final org.adempiere.model.ModelColumn<I_EDI_DesadvLine_SSCC, Object> COLUMN_IsManual_IPA_SSCC18 = new org.adempiere.model.ModelColumn<I_EDI_DesadvLine_SSCC, Object>(I_EDI_DesadvLine_SSCC.class, "IsManual_IPA_SSCC18", null);
    /** Column name IsManual_IPA_SSCC18 */
    public static final String COLUMNNAME_IsManual_IPA_SSCC18 = "IsManual_IPA_SSCC18";

	/**
	 * Set Handling Unit.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_HU_ID (int M_HU_ID);

	/**
	 * Get Handling Unit.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_HU_ID();

    /** Column definition for M_HU_ID */
    public static final org.adempiere.model.ModelColumn<I_EDI_DesadvLine_SSCC, Object> COLUMN_M_HU_ID = new org.adempiere.model.ModelColumn<I_EDI_DesadvLine_SSCC, Object>(I_EDI_DesadvLine_SSCC.class, "M_HU_ID", null);
    /** Column name M_HU_ID */
    public static final String COLUMNNAME_M_HU_ID = "M_HU_ID";

	/**
	 * Set LU Verpackungscode.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_HU_PackagingCode_LU_ID (int M_HU_PackagingCode_LU_ID);

	/**
	 * Get LU Verpackungscode.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_HU_PackagingCode_LU_ID();

    /** Column definition for M_HU_PackagingCode_LU_ID */
    public static final org.adempiere.model.ModelColumn<I_EDI_DesadvLine_SSCC, Object> COLUMN_M_HU_PackagingCode_LU_ID = new org.adempiere.model.ModelColumn<I_EDI_DesadvLine_SSCC, Object>(I_EDI_DesadvLine_SSCC.class, "M_HU_PackagingCode_LU_ID", null);
    /** Column name M_HU_PackagingCode_LU_ID */
    public static final String COLUMNNAME_M_HU_PackagingCode_LU_ID = "M_HU_PackagingCode_LU_ID";

	/**
	 * Set M_HU_PackagingCode_LU_Text.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	public void setM_HU_PackagingCode_LU_Text (java.lang.String M_HU_PackagingCode_LU_Text);

	/**
	 * Get M_HU_PackagingCode_LU_Text.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	public java.lang.String getM_HU_PackagingCode_LU_Text();

    /** Column definition for M_HU_PackagingCode_LU_Text */
    public static final org.adempiere.model.ModelColumn<I_EDI_DesadvLine_SSCC, Object> COLUMN_M_HU_PackagingCode_LU_Text = new org.adempiere.model.ModelColumn<I_EDI_DesadvLine_SSCC, Object>(I_EDI_DesadvLine_SSCC.class, "M_HU_PackagingCode_LU_Text", null);
    /** Column name M_HU_PackagingCode_LU_Text */
    public static final String COLUMNNAME_M_HU_PackagingCode_LU_Text = "M_HU_PackagingCode_LU_Text";

	/**
	 * Set TU Verpackungscode.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_HU_PackagingCode_TU_ID (int M_HU_PackagingCode_TU_ID);

	/**
	 * Get TU Verpackungscode.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_HU_PackagingCode_TU_ID();

    /** Column definition for M_HU_PackagingCode_TU_ID */
    public static final org.adempiere.model.ModelColumn<I_EDI_DesadvLine_SSCC, Object> COLUMN_M_HU_PackagingCode_TU_ID = new org.adempiere.model.ModelColumn<I_EDI_DesadvLine_SSCC, Object>(I_EDI_DesadvLine_SSCC.class, "M_HU_PackagingCode_TU_ID", null);
    /** Column name M_HU_PackagingCode_TU_ID */
    public static final String COLUMNNAME_M_HU_PackagingCode_TU_ID = "M_HU_PackagingCode_TU_ID";

	/**
	 * Set M_HU_PackagingCode_TU_Text.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	public void setM_HU_PackagingCode_TU_Text (java.lang.String M_HU_PackagingCode_TU_Text);

	/**
	 * Get M_HU_PackagingCode_TU_Text.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	public java.lang.String getM_HU_PackagingCode_TU_Text();

    /** Column definition for M_HU_PackagingCode_TU_Text */
    public static final org.adempiere.model.ModelColumn<I_EDI_DesadvLine_SSCC, Object> COLUMN_M_HU_PackagingCode_TU_Text = new org.adempiere.model.ModelColumn<I_EDI_DesadvLine_SSCC, Object>(I_EDI_DesadvLine_SSCC.class, "M_HU_PackagingCode_TU_Text", null);
    /** Column name M_HU_PackagingCode_TU_Text */
    public static final String COLUMNNAME_M_HU_PackagingCode_TU_Text = "M_HU_PackagingCode_TU_Text";

	/**
	 * Set Menge CU.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setQtyCU (java.math.BigDecimal QtyCU);

	/**
	 * Get Menge CU.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyCU();

    /** Column definition for QtyCU */
    public static final org.adempiere.model.ModelColumn<I_EDI_DesadvLine_SSCC, Object> COLUMN_QtyCU = new org.adempiere.model.ModelColumn<I_EDI_DesadvLine_SSCC, Object>(I_EDI_DesadvLine_SSCC.class, "QtyCU", null);
    /** Column name QtyCU */
    public static final String COLUMNNAME_QtyCU = "QtyCU";

	/**
	 * Set Menge CU/LU.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setQtyCUsPerLU (java.math.BigDecimal QtyCUsPerLU);

	/**
	 * Get Menge CU/LU.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyCUsPerLU();

    /** Column definition for QtyCUsPerLU */
    public static final org.adempiere.model.ModelColumn<I_EDI_DesadvLine_SSCC, Object> COLUMN_QtyCUsPerLU = new org.adempiere.model.ModelColumn<I_EDI_DesadvLine_SSCC, Object>(I_EDI_DesadvLine_SSCC.class, "QtyCUsPerLU", null);
    /** Column name QtyCUsPerLU */
    public static final String COLUMNNAME_QtyCUsPerLU = "QtyCUsPerLU";

	/**
	 * Set TU Anzahl.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setQtyTU (int QtyTU);

	/**
	 * Get TU Anzahl.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getQtyTU();

    /** Column definition for QtyTU */
    public static final org.adempiere.model.ModelColumn<I_EDI_DesadvLine_SSCC, Object> COLUMN_QtyTU = new org.adempiere.model.ModelColumn<I_EDI_DesadvLine_SSCC, Object>(I_EDI_DesadvLine_SSCC.class, "QtyTU", null);
    /** Column name QtyTU */
    public static final String COLUMNNAME_QtyTU = "QtyTU";

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
    public static final org.adempiere.model.ModelColumn<I_EDI_DesadvLine_SSCC, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_EDI_DesadvLine_SSCC, Object>(I_EDI_DesadvLine_SSCC.class, "Updated", null);
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
