package org.compiere.model;

import java.math.BigDecimal;
import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for C_TaxDeclarationLine
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_TaxDeclarationLine 
{

	String Table_Name = "C_TaxDeclarationLine";

//	/** AD_Table_ID=819 */
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
	 * Set null.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAmount (@Nullable BigDecimal Amount);

	/**
	 * Get null.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getAmount();

	ModelColumn<I_C_TaxDeclarationLine, Object> COLUMN_Amount = new ModelColumn<>(I_C_TaxDeclarationLine.class, "Amount", null);
	String COLUMNNAME_Amount = "Amount";

	/**
	 * Set Betragsart.
	 * Type of amount to report
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAmountType (@Nullable java.lang.String AmountType);

	/**
	 * Get Betragsart.
	 * Type of amount to report
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getAmountType();

	ModelColumn<I_C_TaxDeclarationLine, Object> COLUMN_AmountType = new ModelColumn<>(I_C_TaxDeclarationLine.class, "AmountType", null);
	String COLUMNNAME_AmountType = "AmountType";

	/**
	 * Set Currency.
	 * The Currency for this record
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Currency_ID (int C_Currency_ID);

	/**
	 * Get Currency.
	 * The Currency for this record
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Currency_ID();

	String COLUMNNAME_C_Currency_ID = "C_Currency_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_C_TaxDeclarationLine, Object> COLUMN_Created = new ModelColumn<>(I_C_TaxDeclarationLine.class, "Created", null);
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
	 * Set Tax Declaration.
	 * Define the declaration to the tax authorities
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_TaxDeclaration_ID (int C_TaxDeclaration_ID);

	/**
	 * Get Tax Declaration.
	 * Define the declaration to the tax authorities
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_TaxDeclaration_ID();

	org.compiere.model.I_C_TaxDeclaration getC_TaxDeclaration();

	void setC_TaxDeclaration(org.compiere.model.I_C_TaxDeclaration C_TaxDeclaration);

	ModelColumn<I_C_TaxDeclarationLine, org.compiere.model.I_C_TaxDeclaration> COLUMN_C_TaxDeclaration_ID = new ModelColumn<>(I_C_TaxDeclarationLine.class, "C_TaxDeclaration_ID", org.compiere.model.I_C_TaxDeclaration.class);
	String COLUMNNAME_C_TaxDeclaration_ID = "C_TaxDeclaration_ID";

	/**
	 * Set Tax Declaration Line.
	 * Tax Declaration Document Information
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_TaxDeclarationLine_ID (int C_TaxDeclarationLine_ID);

	/**
	 * Get Tax Declaration Line.
	 * Tax Declaration Document Information
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_TaxDeclarationLine_ID();

	ModelColumn<I_C_TaxDeclarationLine, Object> COLUMN_C_TaxDeclarationLine_ID = new ModelColumn<>(I_C_TaxDeclarationLine.class, "C_TaxDeclarationLine_ID", null);
	String COLUMNNAME_C_TaxDeclarationLine_ID = "C_TaxDeclarationLine_ID";

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

	ModelColumn<I_C_TaxDeclarationLine, Object> COLUMN_Description = new ModelColumn<>(I_C_TaxDeclarationLine.class, "Description", null);
	String COLUMNNAME_Description = "Description";

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

	ModelColumn<I_C_TaxDeclarationLine, Object> COLUMN_IsActive = new ModelColumn<>(I_C_TaxDeclarationLine.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set SeqNo..
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setLine (int Line);

	/**
	 * Get SeqNo..
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getLine();

	ModelColumn<I_C_TaxDeclarationLine, Object> COLUMN_Line = new ModelColumn<>(I_C_TaxDeclarationLine.class, "Line", null);
	String COLUMNNAME_Line = "Line";

	/**
	 * Set null.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setLineCount (int LineCount);

	/**
	 * Get null.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getLineCount();

	ModelColumn<I_C_TaxDeclarationLine, Object> COLUMN_LineCount = new ModelColumn<>(I_C_TaxDeclarationLine.class, "LineCount", null);
	String COLUMNNAME_LineCount = "LineCount";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_C_TaxDeclarationLine, Object> COLUMN_Updated = new ModelColumn<>(I_C_TaxDeclarationLine.class, "Updated", null);
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
