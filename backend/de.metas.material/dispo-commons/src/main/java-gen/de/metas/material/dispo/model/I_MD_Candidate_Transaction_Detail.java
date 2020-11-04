package de.metas.material.dispo.model;


/** Generated Interface for MD_Candidate_Transaction_Detail
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_MD_Candidate_Transaction_Detail 
{

    /** TableName=MD_Candidate_Transaction_Detail */
    public static final String Table_Name = "MD_Candidate_Transaction_Detail";

    /** AD_Table_ID=540850 */
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
	 * Set Prozesslauf "Lagerbestand zurücksetzen".
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_PInstance_ResetStock_ID (int AD_PInstance_ResetStock_ID);

	/**
	 * Get Prozesslauf "Lagerbestand zurücksetzen".
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_PInstance_ResetStock_ID();

	public org.compiere.model.I_AD_PInstance getAD_PInstance_ResetStock();

	public void setAD_PInstance_ResetStock(org.compiere.model.I_AD_PInstance AD_PInstance_ResetStock);

    /** Column definition for AD_PInstance_ResetStock_ID */
    public static final org.adempiere.model.ModelColumn<I_MD_Candidate_Transaction_Detail, org.compiere.model.I_AD_PInstance> COLUMN_AD_PInstance_ResetStock_ID = new org.adempiere.model.ModelColumn<I_MD_Candidate_Transaction_Detail, org.compiere.model.I_AD_PInstance>(I_MD_Candidate_Transaction_Detail.class, "AD_PInstance_ResetStock_ID", org.compiere.model.I_AD_PInstance.class);
    /** Column name AD_PInstance_ResetStock_ID */
    public static final String COLUMNNAME_AD_PInstance_ResetStock_ID = "AD_PInstance_ResetStock_ID";

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
    public static final org.adempiere.model.ModelColumn<I_MD_Candidate_Transaction_Detail, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_MD_Candidate_Transaction_Detail, Object>(I_MD_Candidate_Transaction_Detail.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_MD_Candidate_Transaction_Detail, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_MD_Candidate_Transaction_Detail, Object>(I_MD_Candidate_Transaction_Detail.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

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
    public static final org.adempiere.model.ModelColumn<I_MD_Candidate_Transaction_Detail, de.metas.material.dispo.model.I_MD_Candidate> COLUMN_MD_Candidate_ID = new org.adempiere.model.ModelColumn<I_MD_Candidate_Transaction_Detail, de.metas.material.dispo.model.I_MD_Candidate>(I_MD_Candidate_Transaction_Detail.class, "MD_Candidate_ID", de.metas.material.dispo.model.I_MD_Candidate.class);
    /** Column name MD_Candidate_ID */
    public static final String COLUMNNAME_MD_Candidate_ID = "MD_Candidate_ID";

	/**
	 * Set Dispo-Transaktionsdetail.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setMD_Candidate_Transaction_Detail_ID (int MD_Candidate_Transaction_Detail_ID);

	/**
	 * Get Dispo-Transaktionsdetail.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getMD_Candidate_Transaction_Detail_ID();

    /** Column definition for MD_Candidate_Transaction_Detail_ID */
    public static final org.adempiere.model.ModelColumn<I_MD_Candidate_Transaction_Detail, Object> COLUMN_MD_Candidate_Transaction_Detail_ID = new org.adempiere.model.ModelColumn<I_MD_Candidate_Transaction_Detail, Object>(I_MD_Candidate_Transaction_Detail.class, "MD_Candidate_Transaction_Detail_ID", null);
    /** Column name MD_Candidate_Transaction_Detail_ID */
    public static final String COLUMNNAME_MD_Candidate_Transaction_Detail_ID = "MD_Candidate_Transaction_Detail_ID";

	/**
	 * Set Bestand.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setMD_Stock_ID (int MD_Stock_ID);

	/**
	 * Get Bestand.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getMD_Stock_ID();

    /** Column definition for MD_Stock_ID */
    public static final org.adempiere.model.ModelColumn<I_MD_Candidate_Transaction_Detail, Object> COLUMN_MD_Stock_ID = new org.adempiere.model.ModelColumn<I_MD_Candidate_Transaction_Detail, Object>(I_MD_Candidate_Transaction_Detail.class, "MD_Stock_ID", null);
    /** Column name MD_Stock_ID */
    public static final String COLUMNNAME_MD_Stock_ID = "MD_Stock_ID";

	/**
	 * Set Receipt Line.
	 * Line on Receipt document
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	public void setM_InOutLine_ID (int M_InOutLine_ID);

	/**
	 * Get Receipt Line.
	 * Line on Receipt document
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	public int getM_InOutLine_ID();

	@Deprecated
	public org.compiere.model.I_M_InOutLine getM_InOutLine();

	@Deprecated
	public void setM_InOutLine(org.compiere.model.I_M_InOutLine M_InOutLine);

    /** Column definition for M_InOutLine_ID */
    public static final org.adempiere.model.ModelColumn<I_MD_Candidate_Transaction_Detail, org.compiere.model.I_M_InOutLine> COLUMN_M_InOutLine_ID = new org.adempiere.model.ModelColumn<I_MD_Candidate_Transaction_Detail, org.compiere.model.I_M_InOutLine>(I_MD_Candidate_Transaction_Detail.class, "M_InOutLine_ID", org.compiere.model.I_M_InOutLine.class);
    /** Column name M_InOutLine_ID */
    public static final String COLUMNNAME_M_InOutLine_ID = "M_InOutLine_ID";

	/**
	 * Set Qty.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setMovementQty (java.math.BigDecimal MovementQty);

	/**
	 * Get Qty.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getMovementQty();

    /** Column definition for MovementQty */
    public static final org.adempiere.model.ModelColumn<I_MD_Candidate_Transaction_Detail, Object> COLUMN_MovementQty = new org.adempiere.model.ModelColumn<I_MD_Candidate_Transaction_Detail, Object>(I_MD_Candidate_Transaction_Detail.class, "MovementQty", null);
    /** Column name MovementQty */
    public static final String COLUMNNAME_MovementQty = "MovementQty";

	/**
	 * Set Bestands-Transaktion.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_Transaction_ID (int M_Transaction_ID);

	/**
	 * Get Bestands-Transaktion.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_Transaction_ID();

	public org.compiere.model.I_M_Transaction getM_Transaction();

	public void setM_Transaction(org.compiere.model.I_M_Transaction M_Transaction);

    /** Column definition for M_Transaction_ID */
    public static final org.adempiere.model.ModelColumn<I_MD_Candidate_Transaction_Detail, org.compiere.model.I_M_Transaction> COLUMN_M_Transaction_ID = new org.adempiere.model.ModelColumn<I_MD_Candidate_Transaction_Detail, org.compiere.model.I_M_Transaction>(I_MD_Candidate_Transaction_Detail.class, "M_Transaction_ID", org.compiere.model.I_M_Transaction.class);
    /** Column name M_Transaction_ID */
    public static final String COLUMNNAME_M_Transaction_ID = "M_Transaction_ID";

	/**
	 * Set Transaktionsdatum.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setTransactionDate (java.sql.Timestamp TransactionDate);

	/**
	 * Get Transaktionsdatum.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getTransactionDate();

    /** Column definition for TransactionDate */
    public static final org.adempiere.model.ModelColumn<I_MD_Candidate_Transaction_Detail, Object> COLUMN_TransactionDate = new org.adempiere.model.ModelColumn<I_MD_Candidate_Transaction_Detail, Object>(I_MD_Candidate_Transaction_Detail.class, "TransactionDate", null);
    /** Column name TransactionDate */
    public static final String COLUMNNAME_TransactionDate = "TransactionDate";

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
    public static final org.adempiere.model.ModelColumn<I_MD_Candidate_Transaction_Detail, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_MD_Candidate_Transaction_Detail, Object>(I_MD_Candidate_Transaction_Detail.class, "Updated", null);
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
