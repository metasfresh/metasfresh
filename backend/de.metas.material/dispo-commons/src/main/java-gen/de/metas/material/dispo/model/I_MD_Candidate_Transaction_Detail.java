package de.metas.material.dispo.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;
import java.math.BigDecimal;

/** Generated Interface for MD_Candidate_Transaction_Detail
 *  @author metasfresh (generated) 
 */
public interface I_MD_Candidate_Transaction_Detail 
{

	String Table_Name = "MD_Candidate_Transaction_Detail";

//	/** AD_Table_ID=540850 */
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
	 * Set Prozesslauf "Lagerbestand zurücksetzen".
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_PInstance_ResetStock_ID (int AD_PInstance_ResetStock_ID);

	/**
	 * Get Prozesslauf "Lagerbestand zurücksetzen".
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_PInstance_ResetStock_ID();

	@Nullable org.compiere.model.I_AD_PInstance getAD_PInstance_ResetStock();

	void setAD_PInstance_ResetStock(@Nullable org.compiere.model.I_AD_PInstance AD_PInstance_ResetStock);

	ModelColumn<I_MD_Candidate_Transaction_Detail, org.compiere.model.I_AD_PInstance> COLUMN_AD_PInstance_ResetStock_ID = new ModelColumn<>(I_MD_Candidate_Transaction_Detail.class, "AD_PInstance_ResetStock_ID", org.compiere.model.I_AD_PInstance.class);
	String COLUMNNAME_AD_PInstance_ResetStock_ID = "AD_PInstance_ResetStock_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_MD_Candidate_Transaction_Detail, Object> COLUMN_Created = new ModelColumn<>(I_MD_Candidate_Transaction_Detail.class, "Created", null);
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

	ModelColumn<I_MD_Candidate_Transaction_Detail, Object> COLUMN_IsActive = new ModelColumn<>(I_MD_Candidate_Transaction_Detail.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Dispositionskandidat.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setMD_Candidate_ID (int MD_Candidate_ID);

	/**
	 * Get Dispositionskandidat.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getMD_Candidate_ID();

	de.metas.material.dispo.model.I_MD_Candidate getMD_Candidate();

	void setMD_Candidate(de.metas.material.dispo.model.I_MD_Candidate MD_Candidate);

	ModelColumn<I_MD_Candidate_Transaction_Detail, de.metas.material.dispo.model.I_MD_Candidate> COLUMN_MD_Candidate_ID = new ModelColumn<>(I_MD_Candidate_Transaction_Detail.class, "MD_Candidate_ID", de.metas.material.dispo.model.I_MD_Candidate.class);
	String COLUMNNAME_MD_Candidate_ID = "MD_Candidate_ID";

	/**
	 * Set Rebooked from.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setMD_Candidate_RebookedFrom_ID (int MD_Candidate_RebookedFrom_ID);

	/**
	 * Get Rebooked from.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getMD_Candidate_RebookedFrom_ID();

	@Nullable de.metas.material.dispo.model.I_MD_Candidate getMD_Candidate_RebookedFrom();

	void setMD_Candidate_RebookedFrom(@Nullable de.metas.material.dispo.model.I_MD_Candidate MD_Candidate_RebookedFrom);

	ModelColumn<I_MD_Candidate_Transaction_Detail, de.metas.material.dispo.model.I_MD_Candidate> COLUMN_MD_Candidate_RebookedFrom_ID = new ModelColumn<>(I_MD_Candidate_Transaction_Detail.class, "MD_Candidate_RebookedFrom_ID", de.metas.material.dispo.model.I_MD_Candidate.class);
	String COLUMNNAME_MD_Candidate_RebookedFrom_ID = "MD_Candidate_RebookedFrom_ID";

	/**
	 * Set Dispo-Transaktionsdetail.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setMD_Candidate_Transaction_Detail_ID (int MD_Candidate_Transaction_Detail_ID);

	/**
	 * Get Dispo-Transaktionsdetail.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getMD_Candidate_Transaction_Detail_ID();

	ModelColumn<I_MD_Candidate_Transaction_Detail, Object> COLUMN_MD_Candidate_Transaction_Detail_ID = new ModelColumn<>(I_MD_Candidate_Transaction_Detail.class, "MD_Candidate_Transaction_Detail_ID", null);
	String COLUMNNAME_MD_Candidate_Transaction_Detail_ID = "MD_Candidate_Transaction_Detail_ID";

	/**
	 * Set Bestand.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setMD_Stock_ID (int MD_Stock_ID);

	/**
	 * Get Bestand.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getMD_Stock_ID();

	ModelColumn<I_MD_Candidate_Transaction_Detail, Object> COLUMN_MD_Stock_ID = new ModelColumn<>(I_MD_Candidate_Transaction_Detail.class, "MD_Stock_ID", null);
	String COLUMNNAME_MD_Stock_ID = "MD_Stock_ID";

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
	void setM_InOutLine_ID (int M_InOutLine_ID);

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
	int getM_InOutLine_ID();

	@Deprecated
	@Nullable org.compiere.model.I_M_InOutLine getM_InOutLine();

	@Deprecated
	void setM_InOutLine(@Nullable org.compiere.model.I_M_InOutLine M_InOutLine);

	ModelColumn<I_MD_Candidate_Transaction_Detail, org.compiere.model.I_M_InOutLine> COLUMN_M_InOutLine_ID = new ModelColumn<>(I_MD_Candidate_Transaction_Detail.class, "M_InOutLine_ID", org.compiere.model.I_M_InOutLine.class);
	String COLUMNNAME_M_InOutLine_ID = "M_InOutLine_ID";

	/**
	 * Set Qty.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setMovementQty (BigDecimal MovementQty);

	/**
	 * Get Qty.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getMovementQty();

	ModelColumn<I_MD_Candidate_Transaction_Detail, Object> COLUMN_MovementQty = new ModelColumn<>(I_MD_Candidate_Transaction_Detail.class, "MovementQty", null);
	String COLUMNNAME_MovementQty = "MovementQty";

	/**
	 * Set Bestands-Transaktion.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_Transaction_ID (int M_Transaction_ID);

	/**
	 * Get Bestands-Transaktion.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_Transaction_ID();

	@Nullable org.compiere.model.I_M_Transaction getM_Transaction();

	void setM_Transaction(@Nullable org.compiere.model.I_M_Transaction M_Transaction);

	ModelColumn<I_MD_Candidate_Transaction_Detail, org.compiere.model.I_M_Transaction> COLUMN_M_Transaction_ID = new ModelColumn<>(I_MD_Candidate_Transaction_Detail.class, "M_Transaction_ID", org.compiere.model.I_M_Transaction.class);
	String COLUMNNAME_M_Transaction_ID = "M_Transaction_ID";

	/**
	 * Set Transaktionsdatum.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setTransactionDate (@Nullable java.sql.Timestamp TransactionDate);

	/**
	 * Get Transaktionsdatum.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getTransactionDate();

	ModelColumn<I_MD_Candidate_Transaction_Detail, Object> COLUMN_TransactionDate = new ModelColumn<>(I_MD_Candidate_Transaction_Detail.class, "TransactionDate", null);
	String COLUMNNAME_TransactionDate = "TransactionDate";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_MD_Candidate_Transaction_Detail, Object> COLUMN_Updated = new ModelColumn<>(I_MD_Candidate_Transaction_Detail.class, "Updated", null);
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
