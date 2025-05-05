package org.compiere.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;

/** Generated Interface for AD_Sequence
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_AD_Sequence 
{

	String Table_Name = "AD_Sequence";

//	/** AD_Table_ID=115 */
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
	 * Set Sequence.
	 * Document Sequence
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_Sequence_ID (int AD_Sequence_ID);

	/**
	 * Get Sequence.
	 * Document Sequence
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Sequence_ID();

	ModelColumn<I_AD_Sequence, Object> COLUMN_AD_Sequence_ID = new ModelColumn<>(I_AD_Sequence.class, "AD_Sequence_ID", null);
	String COLUMNNAME_AD_Sequence_ID = "AD_Sequence_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_AD_Sequence, Object> COLUMN_Created = new ModelColumn<>(I_AD_Sequence.class, "Created", null);
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
	 * Set Current Next.
	 * The next number to be used
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setCurrentNext (int CurrentNext);

	/**
	 * Get Current Next.
	 * The next number to be used
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getCurrentNext();

	ModelColumn<I_AD_Sequence, Object> COLUMN_CurrentNext = new ModelColumn<>(I_AD_Sequence.class, "CurrentNext", null);
	String COLUMNNAME_CurrentNext = "CurrentNext";

	/**
	 * Set Current Next (System).
	 * Next sequence for system use
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setCurrentNextSys (int CurrentNextSys);

	/**
	 * Get Current Next (System).
	 * Next sequence for system use
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getCurrentNextSys();

	ModelColumn<I_AD_Sequence, Object> COLUMN_CurrentNextSys = new ModelColumn<>(I_AD_Sequence.class, "CurrentNextSys", null);
	String COLUMNNAME_CurrentNextSys = "CurrentNextSys";

	/**
	 * Set Custom sequence number provider.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCustomSequenceNoProvider_JavaClass_ID (int CustomSequenceNoProvider_JavaClass_ID);

	/**
	 * Get Custom sequence number provider.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getCustomSequenceNoProvider_JavaClass_ID();

	ModelColumn<I_AD_Sequence, Object> COLUMN_CustomSequenceNoProvider_JavaClass_ID = new ModelColumn<>(I_AD_Sequence.class, "CustomSequenceNoProvider_JavaClass_ID", null);
	String COLUMNNAME_CustomSequenceNoProvider_JavaClass_ID = "CustomSequenceNoProvider_JavaClass_ID";

	/**
	 * Set Date Column.
	 * Fully qualified date column
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDateColumn (@Nullable java.lang.String DateColumn);

	/**
	 * Get Date Column.
	 * Fully qualified date column
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDateColumn();

	ModelColumn<I_AD_Sequence, Object> COLUMN_DateColumn = new ModelColumn<>(I_AD_Sequence.class, "DateColumn", null);
	String COLUMNNAME_DateColumn = "DateColumn";

	/**
	 * Set Decimal Pattern.
	 * Java Decimal Pattern
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDecimalPattern (@Nullable java.lang.String DecimalPattern);

	/**
	 * Get Decimal Pattern.
	 * Java Decimal Pattern
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDecimalPattern();

	ModelColumn<I_AD_Sequence, Object> COLUMN_DecimalPattern = new ModelColumn<>(I_AD_Sequence.class, "DecimalPattern", null);
	String COLUMNNAME_DecimalPattern = "DecimalPattern";

	/**
	 * Set Description.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDescription (@Nullable java.lang.String Description);

	/**
	 * Get Description.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDescription();

	ModelColumn<I_AD_Sequence, Object> COLUMN_Description = new ModelColumn<>(I_AD_Sequence.class, "Description", null);
	String COLUMNNAME_Description = "Description";

	/**
	 * Set Increment.
	 * The number to increment the last document number by
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIncrementNo (int IncrementNo);

	/**
	 * Get Increment.
	 * The number to increment the last document number by
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getIncrementNo();

	ModelColumn<I_AD_Sequence, Object> COLUMN_IncrementNo = new ModelColumn<>(I_AD_Sequence.class, "IncrementNo", null);
	String COLUMNNAME_IncrementNo = "IncrementNo";

	/**
	 * Set Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIsActive (boolean IsActive);

	/**
	 * Get Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isActive();

	ModelColumn<I_AD_Sequence, Object> COLUMN_IsActive = new ModelColumn<>(I_AD_Sequence.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Activate Audit.
	 * Activate Audit Trail of what numbers are generated
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIsAudited (boolean IsAudited);

	/**
	 * Get Activate Audit.
	 * Activate Audit Trail of what numbers are generated
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isAudited();

	ModelColumn<I_AD_Sequence, Object> COLUMN_IsAudited = new ModelColumn<>(I_AD_Sequence.class, "IsAudited", null);
	String COLUMNNAME_IsAudited = "IsAudited";

	/**
	 * Set Auto numbering.
	 * Automatically assign the next number
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsAutoSequence (boolean IsAutoSequence);

	/**
	 * Get Auto numbering.
	 * Automatically assign the next number
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isAutoSequence();

	ModelColumn<I_AD_Sequence, Object> COLUMN_IsAutoSequence = new ModelColumn<>(I_AD_Sequence.class, "IsAutoSequence", null);
	String COLUMNNAME_IsAutoSequence = "IsAutoSequence";

	/**
	 * Set Used for Record ID.
	 * The document number  will be used as the record key
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIsTableID (boolean IsTableID);

	/**
	 * Get Used for Record ID.
	 * The document number  will be used as the record key
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isTableID();

	ModelColumn<I_AD_Sequence, Object> COLUMN_IsTableID = new ModelColumn<>(I_AD_Sequence.class, "IsTableID", null);
	String COLUMNNAME_IsTableID = "IsTableID";

	/**
	 * Set Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setName (java.lang.String Name);

	/**
	 * Get Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getName();

	ModelColumn<I_AD_Sequence, Object> COLUMN_Name = new ModelColumn<>(I_AD_Sequence.class, "Name", null);
	String COLUMNNAME_Name = "Name";

	/**
	 * Set Prefix.
	 * Prefix before the sequence number
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPrefix (@Nullable java.lang.String Prefix);

	/**
	 * Get Prefix.
	 * Prefix before the sequence number
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getPrefix();

	ModelColumn<I_AD_Sequence, Object> COLUMN_Prefix = new ModelColumn<>(I_AD_Sequence.class, "Prefix", null);
	String COLUMNNAME_Prefix = "Prefix";

	/**
	 * Set Restart sequence every Month.
	 * Restart the sequence with Start on every 1st of the month
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setStartNewMonth (boolean StartNewMonth);

	/**
	 * Get Restart sequence every Month.
	 * Restart the sequence with Start on every 1st of the month
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isStartNewMonth();

	ModelColumn<I_AD_Sequence, Object> COLUMN_StartNewMonth = new ModelColumn<>(I_AD_Sequence.class, "StartNewMonth", null);
	String COLUMNNAME_StartNewMonth = "StartNewMonth";

	/**
	 * Set Restart sequence every Year.
	 * Restart the sequence with Start on every 1/1
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setStartNewYear (boolean StartNewYear);

	/**
	 * Get Restart sequence every Year.
	 * Restart the sequence with Start on every 1/1
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isStartNewYear();

	ModelColumn<I_AD_Sequence, Object> COLUMN_StartNewYear = new ModelColumn<>(I_AD_Sequence.class, "StartNewYear", null);
	String COLUMNNAME_StartNewYear = "StartNewYear";

	/**
	 * Set Start No.
	 * Starting number/position
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setStartNo (int StartNo);

	/**
	 * Get Start No.
	 * Starting number/position
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getStartNo();

	ModelColumn<I_AD_Sequence, Object> COLUMN_StartNo = new ModelColumn<>(I_AD_Sequence.class, "StartNo", null);
	String COLUMNNAME_StartNo = "StartNo";

	/**
	 * Set Suffix.
	 * Suffix after the number
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setSuffix (@Nullable java.lang.String Suffix);

	/**
	 * Get Suffix.
	 * Suffix after the number
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getSuffix();

	ModelColumn<I_AD_Sequence, Object> COLUMN_Suffix = new ModelColumn<>(I_AD_Sequence.class, "Suffix", null);
	String COLUMNNAME_Suffix = "Suffix";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_AD_Sequence, Object> COLUMN_Updated = new ModelColumn<>(I_AD_Sequence.class, "Updated", null);
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

	/**
	 * Set Value Format.
	 * Format of the value;
 Can contain fixed format elements, Variables: "_lLoOaAcCa09"
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setVFormat (@Nullable java.lang.String VFormat);

	/**
	 * Get Value Format.
	 * Format of the value;
 Can contain fixed format elements, Variables: "_lLoOaAcCa09"
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getVFormat();

	ModelColumn<I_AD_Sequence, Object> COLUMN_VFormat = new ModelColumn<>(I_AD_Sequence.class, "VFormat", null);
	String COLUMNNAME_VFormat = "VFormat";
}
