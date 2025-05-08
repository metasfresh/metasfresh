package org.compiere.model;

import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for C_ScannableCode_Format_Part
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_ScannableCode_Format_Part 
{

	String Table_Name = "C_ScannableCode_Format_Part";

//	/** AD_Table_ID=542476 */
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
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_C_ScannableCode_Format_Part, Object> COLUMN_Created = new ModelColumn<>(I_C_ScannableCode_Format_Part.class, "Created", null);
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
	 * Set Scannable Code Format.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_ScannableCode_Format_ID (int C_ScannableCode_Format_ID);

	/**
	 * Get Scannable Code Format.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_ScannableCode_Format_ID();

	org.compiere.model.I_C_ScannableCode_Format getC_ScannableCode_Format();

	void setC_ScannableCode_Format(org.compiere.model.I_C_ScannableCode_Format C_ScannableCode_Format);

	ModelColumn<I_C_ScannableCode_Format_Part, org.compiere.model.I_C_ScannableCode_Format> COLUMN_C_ScannableCode_Format_ID = new ModelColumn<>(I_C_ScannableCode_Format_Part.class, "C_ScannableCode_Format_ID", org.compiere.model.I_C_ScannableCode_Format.class);
	String COLUMNNAME_C_ScannableCode_Format_ID = "C_ScannableCode_Format_ID";

	/**
	 * Set Scannable Code Format Part.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_ScannableCode_Format_Part_ID (int C_ScannableCode_Format_Part_ID);

	/**
	 * Get Scannable Code Format Part.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_ScannableCode_Format_Part_ID();

	ModelColumn<I_C_ScannableCode_Format_Part, Object> COLUMN_C_ScannableCode_Format_Part_ID = new ModelColumn<>(I_C_ScannableCode_Format_Part.class, "C_ScannableCode_Format_Part_ID", null);
	String COLUMNNAME_C_ScannableCode_Format_Part_ID = "C_ScannableCode_Format_Part_ID";

	/**
	 * Set Data Format.
	 * Format String in Java Notation, e.g. ddMMyy
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDataFormat (@Nullable java.lang.String DataFormat);

	/**
	 * Get Data Format.
	 * Format String in Java Notation, e.g. ddMMyy
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDataFormat();

	ModelColumn<I_C_ScannableCode_Format_Part, Object> COLUMN_DataFormat = new ModelColumn<>(I_C_ScannableCode_Format_Part.class, "DataFormat", null);
	String COLUMNNAME_DataFormat = "DataFormat";

	/**
	 * Set Data Type.
	 * Type of data
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDataType (java.lang.String DataType);

	/**
	 * Get Data Type.
	 * Type of data
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getDataType();

	ModelColumn<I_C_ScannableCode_Format_Part, Object> COLUMN_DataType = new ModelColumn<>(I_C_ScannableCode_Format_Part.class, "DataType", null);
	String COLUMNNAME_DataType = "DataType";

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

	ModelColumn<I_C_ScannableCode_Format_Part, Object> COLUMN_Description = new ModelColumn<>(I_C_ScannableCode_Format_Part.class, "Description", null);
	String COLUMNNAME_Description = "Description";

	/**
	 * Set End No.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setEndNo (int EndNo);

	/**
	 * Get End No.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getEndNo();

	ModelColumn<I_C_ScannableCode_Format_Part, Object> COLUMN_EndNo = new ModelColumn<>(I_C_ScannableCode_Format_Part.class, "EndNo", null);
	String COLUMNNAME_EndNo = "EndNo";

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

	ModelColumn<I_C_ScannableCode_Format_Part, Object> COLUMN_IsActive = new ModelColumn<>(I_C_ScannableCode_Format_Part.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

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

	ModelColumn<I_C_ScannableCode_Format_Part, Object> COLUMN_StartNo = new ModelColumn<>(I_C_ScannableCode_Format_Part.class, "StartNo", null);
	String COLUMNNAME_StartNo = "StartNo";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_C_ScannableCode_Format_Part, Object> COLUMN_Updated = new ModelColumn<>(I_C_ScannableCode_Format_Part.class, "Updated", null);
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
