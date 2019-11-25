package de.metas.procurement.base.model;


/** Generated Interface for PMM_PurchaseCandidate_OrderLine
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_PMM_PurchaseCandidate_OrderLine 
{

    /** TableName=PMM_PurchaseCandidate_OrderLine */
    public static final String Table_Name = "PMM_PurchaseCandidate_OrderLine";

    /** AD_Table_ID=540746 */
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
	 * Set Auftragsposition.
	 * Auftragsposition
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_OrderLine_ID (int C_OrderLine_ID);

	/**
	 * Get Auftragsposition.
	 * Auftragsposition
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_OrderLine_ID();

	public org.compiere.model.I_C_OrderLine getC_OrderLine();

	public void setC_OrderLine(org.compiere.model.I_C_OrderLine C_OrderLine);

    /** Column definition for C_OrderLine_ID */
    public static final org.adempiere.model.ModelColumn<I_PMM_PurchaseCandidate_OrderLine, org.compiere.model.I_C_OrderLine> COLUMN_C_OrderLine_ID = new org.adempiere.model.ModelColumn<I_PMM_PurchaseCandidate_OrderLine, org.compiere.model.I_C_OrderLine>(I_PMM_PurchaseCandidate_OrderLine.class, "C_OrderLine_ID", org.compiere.model.I_C_OrderLine.class);
    /** Column name C_OrderLine_ID */
    public static final String COLUMNNAME_C_OrderLine_ID = "C_OrderLine_ID";

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
    public static final org.adempiere.model.ModelColumn<I_PMM_PurchaseCandidate_OrderLine, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_PMM_PurchaseCandidate_OrderLine, Object>(I_PMM_PurchaseCandidate_OrderLine.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_PMM_PurchaseCandidate_OrderLine, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_PMM_PurchaseCandidate_OrderLine, Object>(I_PMM_PurchaseCandidate_OrderLine.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Bestellkandidat.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setPMM_PurchaseCandidate_ID (int PMM_PurchaseCandidate_ID);

	/**
	 * Get Bestellkandidat.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getPMM_PurchaseCandidate_ID();

	public de.metas.procurement.base.model.I_PMM_PurchaseCandidate getPMM_PurchaseCandidate();

	public void setPMM_PurchaseCandidate(de.metas.procurement.base.model.I_PMM_PurchaseCandidate PMM_PurchaseCandidate);

    /** Column definition for PMM_PurchaseCandidate_ID */
    public static final org.adempiere.model.ModelColumn<I_PMM_PurchaseCandidate_OrderLine, de.metas.procurement.base.model.I_PMM_PurchaseCandidate> COLUMN_PMM_PurchaseCandidate_ID = new org.adempiere.model.ModelColumn<I_PMM_PurchaseCandidate_OrderLine, de.metas.procurement.base.model.I_PMM_PurchaseCandidate>(I_PMM_PurchaseCandidate_OrderLine.class, "PMM_PurchaseCandidate_ID", de.metas.procurement.base.model.I_PMM_PurchaseCandidate.class);
    /** Column name PMM_PurchaseCandidate_ID */
    public static final String COLUMNNAME_PMM_PurchaseCandidate_ID = "PMM_PurchaseCandidate_ID";

	/**
	 * Set Purchase order candidate - order line.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setPMM_PurchaseCandidate_OrderLine_ID (int PMM_PurchaseCandidate_OrderLine_ID);

	/**
	 * Get Purchase order candidate - order line.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getPMM_PurchaseCandidate_OrderLine_ID();

    /** Column definition for PMM_PurchaseCandidate_OrderLine_ID */
    public static final org.adempiere.model.ModelColumn<I_PMM_PurchaseCandidate_OrderLine, Object> COLUMN_PMM_PurchaseCandidate_OrderLine_ID = new org.adempiere.model.ModelColumn<I_PMM_PurchaseCandidate_OrderLine, Object>(I_PMM_PurchaseCandidate_OrderLine.class, "PMM_PurchaseCandidate_OrderLine_ID", null);
    /** Column name PMM_PurchaseCandidate_OrderLine_ID */
    public static final String COLUMNNAME_PMM_PurchaseCandidate_OrderLine_ID = "PMM_PurchaseCandidate_OrderLine_ID";

	/**
	 * Set Bestellt/ Beauftragt.
	 * Bestellt/ Beauftragt
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setQtyOrdered (java.math.BigDecimal QtyOrdered);

	/**
	 * Get Bestellt/ Beauftragt.
	 * Bestellt/ Beauftragt
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyOrdered();

    /** Column definition for QtyOrdered */
    public static final org.adempiere.model.ModelColumn<I_PMM_PurchaseCandidate_OrderLine, Object> COLUMN_QtyOrdered = new org.adempiere.model.ModelColumn<I_PMM_PurchaseCandidate_OrderLine, Object>(I_PMM_PurchaseCandidate_OrderLine.class, "QtyOrdered", null);
    /** Column name QtyOrdered */
    public static final String COLUMNNAME_QtyOrdered = "QtyOrdered";

	/**
	 * Set Bestellte Menge (TU).
	 * Bestellte Menge (TU)
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setQtyOrdered_TU (java.math.BigDecimal QtyOrdered_TU);

	/**
	 * Get Bestellte Menge (TU).
	 * Bestellte Menge (TU)
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyOrdered_TU();

    /** Column definition for QtyOrdered_TU */
    public static final org.adempiere.model.ModelColumn<I_PMM_PurchaseCandidate_OrderLine, Object> COLUMN_QtyOrdered_TU = new org.adempiere.model.ModelColumn<I_PMM_PurchaseCandidate_OrderLine, Object>(I_PMM_PurchaseCandidate_OrderLine.class, "QtyOrdered_TU", null);
    /** Column name QtyOrdered_TU */
    public static final String COLUMNNAME_QtyOrdered_TU = "QtyOrdered_TU";

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
    public static final org.adempiere.model.ModelColumn<I_PMM_PurchaseCandidate_OrderLine, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_PMM_PurchaseCandidate_OrderLine, Object>(I_PMM_PurchaseCandidate_OrderLine.class, "Updated", null);
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
