package de.metas.material.dispo.model;


/** Generated Interface for MD_Candidate_Purchase_Detail
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_MD_Candidate_Purchase_Detail 
{

    /** TableName=MD_Candidate_Purchase_Detail */
    public static final String Table_Name = "MD_Candidate_Purchase_Detail";

    /** AD_Table_ID=540983 */
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
	 * Set C_BPartner_Vendor_ID.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_BPartner_Vendor_ID (int C_BPartner_Vendor_ID);

	/**
	 * Get C_BPartner_Vendor_ID.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_BPartner_Vendor_ID();

    /** Column name C_BPartner_Vendor_ID */
    public static final String COLUMNNAME_C_BPartner_Vendor_ID = "C_BPartner_Vendor_ID";

	/**
	 * Set Bestellposition.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_OrderLinePO_ID (int C_OrderLinePO_ID);

	/**
	 * Get Bestellposition.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_OrderLinePO_ID();

	public org.compiere.model.I_C_OrderLine getC_OrderLinePO();

	public void setC_OrderLinePO(org.compiere.model.I_C_OrderLine C_OrderLinePO);

    /** Column definition for C_OrderLinePO_ID */
    public static final org.adempiere.model.ModelColumn<I_MD_Candidate_Purchase_Detail, org.compiere.model.I_C_OrderLine> COLUMN_C_OrderLinePO_ID = new org.adempiere.model.ModelColumn<I_MD_Candidate_Purchase_Detail, org.compiere.model.I_C_OrderLine>(I_MD_Candidate_Purchase_Detail.class, "C_OrderLinePO_ID", org.compiere.model.I_C_OrderLine.class);
    /** Column name C_OrderLinePO_ID */
    public static final String COLUMNNAME_C_OrderLinePO_ID = "C_OrderLinePO_ID";

	/**
	 * Set Purchase candidate.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_PurchaseCandidate_ID (int C_PurchaseCandidate_ID);

	/**
	 * Get Purchase candidate.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_PurchaseCandidate_ID();

    /** Column definition for C_PurchaseCandidate_ID */
    public static final org.adempiere.model.ModelColumn<I_MD_Candidate_Purchase_Detail, Object> COLUMN_C_PurchaseCandidate_ID = new org.adempiere.model.ModelColumn<I_MD_Candidate_Purchase_Detail, Object>(I_MD_Candidate_Purchase_Detail.class, "C_PurchaseCandidate_ID", null);
    /** Column name C_PurchaseCandidate_ID */
    public static final String COLUMNNAME_C_PurchaseCandidate_ID = "C_PurchaseCandidate_ID";

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
    public static final org.adempiere.model.ModelColumn<I_MD_Candidate_Purchase_Detail, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_MD_Candidate_Purchase_Detail, Object>(I_MD_Candidate_Purchase_Detail.class, "Created", null);
    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/**
	 * Get Created By.
	 * User who created this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getCreatedBy();

    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

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
    public static final org.adempiere.model.ModelColumn<I_MD_Candidate_Purchase_Detail, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_MD_Candidate_Purchase_Detail, Object>(I_MD_Candidate_Purchase_Detail.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Vom System vorgeschlagen.
	 * Ja bedeutet, dass es zumindest ursprünglich kein entsprechendes Dokument (z.B. Produktionsauftrag) gab, sondern dass das System einen Beleg vorgeschlagen hatte.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsAdvised (boolean IsAdvised);

	/**
	 * Get Vom System vorgeschlagen.
	 * Ja bedeutet, dass es zumindest ursprünglich kein entsprechendes Dokument (z.B. Produktionsauftrag) gab, sondern dass das System einen Beleg vorgeschlagen hatte.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isAdvised();

    /** Column definition for IsAdvised */
    public static final org.adempiere.model.ModelColumn<I_MD_Candidate_Purchase_Detail, Object> COLUMN_IsAdvised = new org.adempiere.model.ModelColumn<I_MD_Candidate_Purchase_Detail, Object>(I_MD_Candidate_Purchase_Detail.class, "IsAdvised", null);
    /** Column name IsAdvised */
    public static final String COLUMNNAME_IsAdvised = "IsAdvised";

	/**
	 * Set Dispositionskandidat.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setMD_Candidate_ID (int MD_Candidate_ID);

	/**
	 * Get Dispositionskandidat.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getMD_Candidate_ID();

	public de.metas.material.dispo.model.I_MD_Candidate getMD_Candidate();

	public void setMD_Candidate(de.metas.material.dispo.model.I_MD_Candidate MD_Candidate);

    /** Column definition for MD_Candidate_ID */
    public static final org.adempiere.model.ModelColumn<I_MD_Candidate_Purchase_Detail, de.metas.material.dispo.model.I_MD_Candidate> COLUMN_MD_Candidate_ID = new org.adempiere.model.ModelColumn<I_MD_Candidate_Purchase_Detail, de.metas.material.dispo.model.I_MD_Candidate>(I_MD_Candidate_Purchase_Detail.class, "MD_Candidate_ID", de.metas.material.dispo.model.I_MD_Candidate.class);
    /** Column name MD_Candidate_ID */
    public static final String COLUMNNAME_MD_Candidate_ID = "MD_Candidate_ID";

	/**
	 * Set Dispo-Einkaufsdetail.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setMD_Candidate_Purchase_Detail_ID (int MD_Candidate_Purchase_Detail_ID);

	/**
	 * Get Dispo-Einkaufsdetail.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getMD_Candidate_Purchase_Detail_ID();

    /** Column definition for MD_Candidate_Purchase_Detail_ID */
    public static final org.adempiere.model.ModelColumn<I_MD_Candidate_Purchase_Detail, Object> COLUMN_MD_Candidate_Purchase_Detail_ID = new org.adempiere.model.ModelColumn<I_MD_Candidate_Purchase_Detail, Object>(I_MD_Candidate_Purchase_Detail.class, "MD_Candidate_Purchase_Detail_ID", null);
    /** Column name MD_Candidate_Purchase_Detail_ID */
    public static final String COLUMNNAME_MD_Candidate_Purchase_Detail_ID = "MD_Candidate_Purchase_Detail_ID";

	/**
	 * Set Material Receipt Candidates.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_ReceiptSchedule_ID (int M_ReceiptSchedule_ID);

	/**
	 * Get Material Receipt Candidates.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_ReceiptSchedule_ID();

    /** Column definition for M_ReceiptSchedule_ID */
    public static final org.adempiere.model.ModelColumn<I_MD_Candidate_Purchase_Detail, Object> COLUMN_M_ReceiptSchedule_ID = new org.adempiere.model.ModelColumn<I_MD_Candidate_Purchase_Detail, Object>(I_MD_Candidate_Purchase_Detail.class, "M_ReceiptSchedule_ID", null);
    /** Column name M_ReceiptSchedule_ID */
    public static final String COLUMNNAME_M_ReceiptSchedule_ID = "M_ReceiptSchedule_ID";

	/**
	 * Set Geplante Menge.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPlannedQty (java.math.BigDecimal PlannedQty);

	/**
	 * Get Geplante Menge.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getPlannedQty();

    /** Column definition for PlannedQty */
    public static final org.adempiere.model.ModelColumn<I_MD_Candidate_Purchase_Detail, Object> COLUMN_PlannedQty = new org.adempiere.model.ModelColumn<I_MD_Candidate_Purchase_Detail, Object>(I_MD_Candidate_Purchase_Detail.class, "PlannedQty", null);
    /** Column name PlannedQty */
    public static final String COLUMNNAME_PlannedQty = "PlannedQty";

	/**
	 * Set Product Planning.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPP_Product_Planning_ID (int PP_Product_Planning_ID);

	/**
	 * Get Product Planning.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getPP_Product_Planning_ID();

	/** Column definition for PP_Product_Planning_ID */
    public static final org.adempiere.model.ModelColumn<I_MD_Candidate_Purchase_Detail, org.eevolution.model.I_PP_Product_Planning> COLUMN_PP_Product_Planning_ID = new org.adempiere.model.ModelColumn<I_MD_Candidate_Purchase_Detail, org.eevolution.model.I_PP_Product_Planning>(I_MD_Candidate_Purchase_Detail.class, "PP_Product_Planning_ID", org.eevolution.model.I_PP_Product_Planning.class);
    /** Column name PP_Product_Planning_ID */
    public static final String COLUMNNAME_PP_Product_Planning_ID = "PP_Product_Planning_ID";

	/**
	 * Set Qty Ordered.
	 * Qty Ordered
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setQtyOrdered (java.math.BigDecimal QtyOrdered);

	/**
	 * Get Qty Ordered.
	 * Qty Ordered
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyOrdered();

    /** Column definition for QtyOrdered */
    public static final org.adempiere.model.ModelColumn<I_MD_Candidate_Purchase_Detail, Object> COLUMN_QtyOrdered = new org.adempiere.model.ModelColumn<I_MD_Candidate_Purchase_Detail, Object>(I_MD_Candidate_Purchase_Detail.class, "QtyOrdered", null);
    /** Column name QtyOrdered */
    public static final String COLUMNNAME_QtyOrdered = "QtyOrdered";

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
    public static final org.adempiere.model.ModelColumn<I_MD_Candidate_Purchase_Detail, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_MD_Candidate_Purchase_Detail, Object>(I_MD_Candidate_Purchase_Detail.class, "Updated", null);
    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Updated By.
	 * User who updated this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getUpdatedBy();

    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
