package de.metas.invoicecandidate.model;

import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for C_Invoice_Candidate_Recompute
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_Invoice_Candidate_Recompute 
{

	String Table_Name = "C_Invoice_Candidate_Recompute";

//	/** AD_Table_ID=540677 */
//	int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);


	/**
	 * Set Process Instance.
	 * Instance of a Process
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_PInstance_ID (int AD_PInstance_ID);

	/**
	 * Get Process Instance.
	 * Instance of a Process
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_PInstance_ID();

	@Nullable org.compiere.model.I_AD_PInstance getAD_PInstance();

	ModelColumn<I_C_Invoice_Candidate_Recompute, org.compiere.model.I_AD_PInstance> COLUMN_AD_PInstance_ID = new ModelColumn<>(I_C_Invoice_Candidate_Recompute.class, "AD_PInstance_ID", org.compiere.model.I_AD_PInstance.class);
	String COLUMNNAME_AD_PInstance_ID = "AD_PInstance_ID";


	void setAD_PInstance(@Nullable org.compiere.model.I_AD_PInstance AD_PInstance);

	ModelColumn<I_C_Invoice_Candidate_Recompute, Object> COLUMN_C_Async_Batch_ID = new ModelColumn<>(I_C_Invoice_Candidate_Recompute.class, "C_Async_Batch_ID", null);
	String COLUMNNAME_C_Async_Batch_ID = "C_Async_Batch_ID";

	/**
	 * Set Invoice candidate.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Invoice_Candidate_ID (int C_Invoice_Candidate_ID);

	/**
	 * Get Invoice candidate.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Invoice_Candidate_ID();

	de.metas.invoicecandidate.model.I_C_Invoice_Candidate getC_Invoice_Candidate();

	ModelColumn<I_C_Invoice_Candidate_Recompute, de.metas.invoicecandidate.model.I_C_Invoice_Candidate> COLUMN_C_Invoice_Candidate_ID = new ModelColumn<>(I_C_Invoice_Candidate_Recompute.class, "C_Invoice_Candidate_ID", de.metas.invoicecandidate.model.I_C_Invoice_Candidate.class);
	String COLUMNNAME_C_Invoice_Candidate_ID = "C_Invoice_Candidate_ID";

	void setC_Invoice_Candidate(de.metas.invoicecandidate.model.I_C_Invoice_Candidate C_Invoice_Candidate);

	/**
	 * Set C_Invoice_Candidate_Recompute.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Invoice_Candidate_Recompute_ID (int C_Invoice_Candidate_Recompute_ID);

	/**
	 * Get C_Invoice_Candidate_Recompute.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Invoice_Candidate_Recompute_ID();

	ModelColumn<I_C_Invoice_Candidate_Recompute, Object> COLUMN_C_Invoice_Candidate_Recompute_ID = new ModelColumn<>(I_C_Invoice_Candidate_Recompute.class, "C_Invoice_Candidate_Recompute_ID", null);
	String COLUMNNAME_C_Invoice_Candidate_Recompute_ID = "C_Invoice_Candidate_Recompute_ID";

	/**
	 * Set Chunk UUID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setChunkUUID (@Nullable String ChunkUUID);

	/**
	 * Get Chunk UUID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable String getChunkUUID();

	ModelColumn<I_C_Invoice_Candidate_Recompute, Object> COLUMN_ChunkUUID = new ModelColumn<>(I_C_Invoice_Candidate_Recompute.class, "ChunkUUID", null);
	String COLUMNNAME_ChunkUUID = "ChunkUUID";
}
