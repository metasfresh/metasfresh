package org.compiere.model;

import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for C_DocType_Sequence
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_DocType_Sequence 
{

	String Table_Name = "C_DocType_Sequence";

//	/** AD_Table_ID=540774 */
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
	 * Set Country.
	 * Country
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Country_ID (int C_Country_ID);

	/**
	 * Get Country.
	 * Country
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Country_ID();

	@Nullable org.compiere.model.I_C_Country getC_Country();

	void setC_Country(@Nullable org.compiere.model.I_C_Country C_Country);

	ModelColumn<I_C_DocType_Sequence, org.compiere.model.I_C_Country> COLUMN_C_Country_ID = new ModelColumn<>(I_C_DocType_Sequence.class, "C_Country_ID", org.compiere.model.I_C_Country.class);
	String COLUMNNAME_C_Country_ID = "C_Country_ID";

	/**
	 * Set Document Type.
	 * Document type or rules
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_DocType_ID (int C_DocType_ID);

	/**
	 * Get Document Type.
	 * Document type or rules
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_DocType_ID();

	String COLUMNNAME_C_DocType_ID = "C_DocType_ID";

	/**
	 * Set Document Type Sequence assignment.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_DocType_Sequence_ID (int C_DocType_Sequence_ID);

	/**
	 * Get Document Type Sequence assignment.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_DocType_Sequence_ID();

	ModelColumn<I_C_DocType_Sequence, Object> COLUMN_C_DocType_Sequence_ID = new ModelColumn<>(I_C_DocType_Sequence.class, "C_DocType_Sequence_ID", null);
	String COLUMNNAME_C_DocType_Sequence_ID = "C_DocType_Sequence_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_C_DocType_Sequence, Object> COLUMN_Created = new ModelColumn<>(I_C_DocType_Sequence.class, "Created", null);
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
	 * Set Document Sequence.
	 * Document sequence determines the numbering of documents
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDocNoSequence_ID (int DocNoSequence_ID);

	/**
	 * Get Document Sequence.
	 * Document sequence determines the numbering of documents
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getDocNoSequence_ID();

	org.compiere.model.I_AD_Sequence getDocNoSequence();

	void setDocNoSequence(org.compiere.model.I_AD_Sequence DocNoSequence);

	ModelColumn<I_C_DocType_Sequence, org.compiere.model.I_AD_Sequence> COLUMN_DocNoSequence_ID = new ModelColumn<>(I_C_DocType_Sequence.class, "DocNoSequence_ID", org.compiere.model.I_AD_Sequence.class);
	String COLUMNNAME_DocNoSequence_ID = "DocNoSequence_ID";

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

	ModelColumn<I_C_DocType_Sequence, Object> COLUMN_IsActive = new ModelColumn<>(I_C_DocType_Sequence.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set SeqNo.
	 * Method of ordering records;
 lowest number comes first
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setSeqNo (int SeqNo);

	/**
	 * Get SeqNo.
	 * Method of ordering records;
 lowest number comes first
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getSeqNo();

	ModelColumn<I_C_DocType_Sequence, Object> COLUMN_SeqNo = new ModelColumn<>(I_C_DocType_Sequence.class, "SeqNo", null);
	String COLUMNNAME_SeqNo = "SeqNo";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_C_DocType_Sequence, Object> COLUMN_Updated = new ModelColumn<>(I_C_DocType_Sequence.class, "Updated", null);
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
