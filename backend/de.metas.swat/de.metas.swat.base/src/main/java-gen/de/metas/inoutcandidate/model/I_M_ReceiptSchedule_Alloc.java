package de.metas.inoutcandidate.model;


/** Generated Interface for M_ReceiptSchedule_Alloc
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_M_ReceiptSchedule_Alloc 
{

    /** TableName=M_ReceiptSchedule_Alloc */
    public static final String Table_Name = "M_ReceiptSchedule_Alloc";

    /** AD_Table_ID=540530 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);


	/**
	 * Get Client.
	 * Client/Tenant for this installation.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Client_ID();

    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Org_ID();

    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Catch UOM.
	 * Catch weight UOM as taken from the product master data.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setCatch_UOM_ID (int Catch_UOM_ID);

	/**
	 * Get Catch UOM.
	 * Catch weight UOM as taken from the product master data.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getCatch_UOM_ID();

    /** Column name Catch_UOM_ID */
    public static final String COLUMNNAME_Catch_UOM_ID = "Catch_UOM_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getCreated();

    /** Column definition for Created */
    public static final org.adempiere.model.ModelColumn<I_M_ReceiptSchedule_Alloc, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_M_ReceiptSchedule_Alloc, Object>(I_M_ReceiptSchedule_Alloc.class, "Created", null);
    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/**
	 * Get Created By.
	 * User who created this records
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getCreatedBy();

    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Status.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	public void setDocStatus (java.lang.String DocStatus);

	/**
	 * Get Status.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	public java.lang.String getDocStatus();

    /** Column definition for DocStatus */
    public static final org.adempiere.model.ModelColumn<I_M_ReceiptSchedule_Alloc, Object> COLUMN_DocStatus = new org.adempiere.model.ModelColumn<I_M_ReceiptSchedule_Alloc, Object>(I_M_ReceiptSchedule_Alloc.class, "DocStatus", null);
    /** Column name DocStatus */
    public static final String COLUMNNAME_DocStatus = "DocStatus";

	/**
	 * Set Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsActive (boolean IsActive);

	/**
	 * Get Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isActive();

    /** Column definition for IsActive */
    public static final org.adempiere.model.ModelColumn<I_M_ReceiptSchedule_Alloc, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_M_ReceiptSchedule_Alloc, Object>(I_M_ReceiptSchedule_Alloc.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Shipment/ Receipt.
	 * Material Shipment Document
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	public void setM_InOut_ID (int M_InOut_ID);

	/**
	 * Get Shipment/ Receipt.
	 * Material Shipment Document
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	public int getM_InOut_ID();

	@Deprecated
	public org.compiere.model.I_M_InOut getM_InOut();

	@Deprecated
	public void setM_InOut(org.compiere.model.I_M_InOut M_InOut);

    /** Column definition for M_InOut_ID */
    public static final org.adempiere.model.ModelColumn<I_M_ReceiptSchedule_Alloc, org.compiere.model.I_M_InOut> COLUMN_M_InOut_ID = new org.adempiere.model.ModelColumn<I_M_ReceiptSchedule_Alloc, org.compiere.model.I_M_InOut>(I_M_ReceiptSchedule_Alloc.class, "M_InOut_ID", org.compiere.model.I_M_InOut.class);
    /** Column name M_InOut_ID */
    public static final String COLUMNNAME_M_InOut_ID = "M_InOut_ID";

	/**
	 * Set Receipt Line.
	 * Line on Receipt document
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_InOutLine_ID (int M_InOutLine_ID);

	/**
	 * Get Receipt Line.
	 * Line on Receipt document
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_InOutLine_ID();

	public org.compiere.model.I_M_InOutLine getM_InOutLine();

	public void setM_InOutLine(org.compiere.model.I_M_InOutLine M_InOutLine);

    /** Column definition for M_InOutLine_ID */
    public static final org.adempiere.model.ModelColumn<I_M_ReceiptSchedule_Alloc, org.compiere.model.I_M_InOutLine> COLUMN_M_InOutLine_ID = new org.adempiere.model.ModelColumn<I_M_ReceiptSchedule_Alloc, org.compiere.model.I_M_InOutLine>(I_M_ReceiptSchedule_Alloc.class, "M_InOutLine_ID", org.compiere.model.I_M_InOutLine.class);
    /** Column name M_InOutLine_ID */
    public static final String COLUMNNAME_M_InOutLine_ID = "M_InOutLine_ID";

	/**
	 * Set Wareneingangsdispo - Wareneingangszeile.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_ReceiptSchedule_Alloc_ID (int M_ReceiptSchedule_Alloc_ID);

	/**
	 * Get Wareneingangsdispo - Wareneingangszeile.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_ReceiptSchedule_Alloc_ID();

    /** Column definition for M_ReceiptSchedule_Alloc_ID */
    public static final org.adempiere.model.ModelColumn<I_M_ReceiptSchedule_Alloc, Object> COLUMN_M_ReceiptSchedule_Alloc_ID = new org.adempiere.model.ModelColumn<I_M_ReceiptSchedule_Alloc, Object>(I_M_ReceiptSchedule_Alloc.class, "M_ReceiptSchedule_Alloc_ID", null);
    /** Column name M_ReceiptSchedule_Alloc_ID */
    public static final String COLUMNNAME_M_ReceiptSchedule_Alloc_ID = "M_ReceiptSchedule_Alloc_ID";

	/**
	 * Set Material Receipt Candidates.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_ReceiptSchedule_ID (int M_ReceiptSchedule_ID);

	/**
	 * Get Material Receipt Candidates.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_ReceiptSchedule_ID();

	public de.metas.inoutcandidate.model.I_M_ReceiptSchedule getM_ReceiptSchedule();

	public void setM_ReceiptSchedule(de.metas.inoutcandidate.model.I_M_ReceiptSchedule M_ReceiptSchedule);

    /** Column definition for M_ReceiptSchedule_ID */
    public static final org.adempiere.model.ModelColumn<I_M_ReceiptSchedule_Alloc, de.metas.inoutcandidate.model.I_M_ReceiptSchedule> COLUMN_M_ReceiptSchedule_ID = new org.adempiere.model.ModelColumn<I_M_ReceiptSchedule_Alloc, de.metas.inoutcandidate.model.I_M_ReceiptSchedule>(I_M_ReceiptSchedule_Alloc.class, "M_ReceiptSchedule_ID", de.metas.inoutcandidate.model.I_M_ReceiptSchedule.class);
    /** Column name M_ReceiptSchedule_ID */
    public static final String COLUMNNAME_M_ReceiptSchedule_ID = "M_ReceiptSchedule_ID";

	/**
	 * Set Zugewiesene Menge.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setQtyAllocated (java.math.BigDecimal QtyAllocated);

	/**
	 * Get Zugewiesene Menge.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyAllocated();

    /** Column definition for QtyAllocated */
    public static final org.adempiere.model.ModelColumn<I_M_ReceiptSchedule_Alloc, Object> COLUMN_QtyAllocated = new org.adempiere.model.ModelColumn<I_M_ReceiptSchedule_Alloc, Object>(I_M_ReceiptSchedule_Alloc.class, "QtyAllocated", null);
    /** Column name QtyAllocated */
    public static final String COLUMNNAME_QtyAllocated = "QtyAllocated";

	/**
	 * Set Allocated catch.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setQtyAllocatedInCatchUOM (java.math.BigDecimal QtyAllocatedInCatchUOM);

	/**
	 * Get Allocated catch.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyAllocatedInCatchUOM();

    /** Column definition for QtyAllocatedInCatchUOM */
    public static final org.adempiere.model.ModelColumn<I_M_ReceiptSchedule_Alloc, Object> COLUMN_QtyAllocatedInCatchUOM = new org.adempiere.model.ModelColumn<I_M_ReceiptSchedule_Alloc, Object>(I_M_ReceiptSchedule_Alloc.class, "QtyAllocatedInCatchUOM", null);
    /** Column name QtyAllocatedInCatchUOM */
    public static final String COLUMNNAME_QtyAllocatedInCatchUOM = "QtyAllocatedInCatchUOM";

	/**
	 * Set Minderwertige Menge.
	 * Mengen-Summe der zugeordneten Lieferzeilen, die mit "im Disput" markiert sind und nicht fakturiert werden sollen.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setQtyWithIssues (java.math.BigDecimal QtyWithIssues);

	/**
	 * Get Minderwertige Menge.
	 * Mengen-Summe der zugeordneten Lieferzeilen, die mit "im Disput" markiert sind und nicht fakturiert werden sollen.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyWithIssues();

    /** Column definition for QtyWithIssues */
    public static final org.adempiere.model.ModelColumn<I_M_ReceiptSchedule_Alloc, Object> COLUMN_QtyWithIssues = new org.adempiere.model.ModelColumn<I_M_ReceiptSchedule_Alloc, Object>(I_M_ReceiptSchedule_Alloc.class, "QtyWithIssues", null);
    /** Column name QtyWithIssues */
    public static final String COLUMNNAME_QtyWithIssues = "QtyWithIssues";

	/**
	 * Set Catch quantity with issues.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setQtyWithIssuesInCatchUOM (java.math.BigDecimal QtyWithIssuesInCatchUOM);

	/**
	 * Get Catch quantity with issues.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyWithIssuesInCatchUOM();

    /** Column definition for QtyWithIssuesInCatchUOM */
    public static final org.adempiere.model.ModelColumn<I_M_ReceiptSchedule_Alloc, Object> COLUMN_QtyWithIssuesInCatchUOM = new org.adempiere.model.ModelColumn<I_M_ReceiptSchedule_Alloc, Object>(I_M_ReceiptSchedule_Alloc.class, "QtyWithIssuesInCatchUOM", null);
    /** Column name QtyWithIssuesInCatchUOM */
    public static final String COLUMNNAME_QtyWithIssuesInCatchUOM = "QtyWithIssuesInCatchUOM";

	/**
	 * Set Qualitätsabzug %.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	public void setQualityDiscountPercent (java.math.BigDecimal QualityDiscountPercent);

	/**
	 * Get Qualitätsabzug %.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	public java.math.BigDecimal getQualityDiscountPercent();

    /** Column definition for QualityDiscountPercent */
    public static final org.adempiere.model.ModelColumn<I_M_ReceiptSchedule_Alloc, Object> COLUMN_QualityDiscountPercent = new org.adempiere.model.ModelColumn<I_M_ReceiptSchedule_Alloc, Object>(I_M_ReceiptSchedule_Alloc.class, "QualityDiscountPercent", null);
    /** Column name QualityDiscountPercent */
    public static final String COLUMNNAME_QualityDiscountPercent = "QualityDiscountPercent";

	/**
	 * Set Qualität-Notiz.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	public void setQualityNote (java.lang.String QualityNote);

	/**
	 * Get Qualität-Notiz.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	public java.lang.String getQualityNote();

    /** Column definition for QualityNote */
    public static final org.adempiere.model.ModelColumn<I_M_ReceiptSchedule_Alloc, Object> COLUMN_QualityNote = new org.adempiere.model.ModelColumn<I_M_ReceiptSchedule_Alloc, Object>(I_M_ReceiptSchedule_Alloc.class, "QualityNote", null);
    /** Column name QualityNote */
    public static final String COLUMNNAME_QualityNote = "QualityNote";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getUpdated();

    /** Column definition for Updated */
    public static final org.adempiere.model.ModelColumn<I_M_ReceiptSchedule_Alloc, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_M_ReceiptSchedule_Alloc, Object>(I_M_ReceiptSchedule_Alloc.class, "Updated", null);
    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Updated By.
	 * User who updated this records
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getUpdatedBy();

    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
