package org.compiere.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;

/** Generated Interface for I_ElementValue
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_I_ElementValue 
{

	String Table_Name = "I_ElementValue";

//	/** AD_Table_ID=534 */
//	int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);


	/**
	 * Set Kontovorzeichen.
	 * Indicates the Natural Sign of the Account as a Debit or Credit
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAccountSign (@Nullable java.lang.String AccountSign);

	/**
	 * Get Kontovorzeichen.
	 * Indicates the Natural Sign of the Account as a Debit or Credit
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getAccountSign();

	ModelColumn<I_I_ElementValue, Object> COLUMN_AccountSign = new ModelColumn<>(I_I_ElementValue.class, "AccountSign", null);
	String COLUMNNAME_AccountSign = "AccountSign";

	/**
	 * Set Kontenart.
	 * Indicates the type of account
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAccountType (@Nullable java.lang.String AccountType);

	/**
	 * Get Kontenart.
	 * Indicates the type of account
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getAccountType();

	ModelColumn<I_I_ElementValue, Object> COLUMN_AccountType = new ModelColumn<>(I_I_ElementValue.class, "AccountType", null);
	String COLUMNNAME_AccountType = "AccountType";

	/**
	 * Get Client.
	 * Client/Tenant for this installation.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_Client_ID();

	String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Link Column.
	 * Link Column for Multi-Parent tables
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_Column_ID (int AD_Column_ID);

	/**
	 * Get Link Column.
	 * Link Column for Multi-Parent tables
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_Column_ID();

	@Nullable org.compiere.model.I_AD_Column getAD_Column();

	void setAD_Column(@Nullable org.compiere.model.I_AD_Column AD_Column);

	ModelColumn<I_I_ElementValue, org.compiere.model.I_AD_Column> COLUMN_AD_Column_ID = new ModelColumn<>(I_I_ElementValue.class, "AD_Column_ID", org.compiere.model.I_AD_Column.class);
	String COLUMNNAME_AD_Column_ID = "AD_Column_ID";

	/**
	 * Set Issues.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_Issue_ID (int AD_Issue_ID);

	/**
	 * Get Issues.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_Issue_ID();

	String COLUMNNAME_AD_Issue_ID = "AD_Issue_ID";

	/**
	 * Set Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_Org_ID();

	String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Data import.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_DataImport_ID (int C_DataImport_ID);

	/**
	 * Get Data import.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_DataImport_ID();

	@Nullable org.compiere.model.I_C_DataImport getC_DataImport();

	void setC_DataImport(@Nullable org.compiere.model.I_C_DataImport C_DataImport);

	ModelColumn<I_I_ElementValue, org.compiere.model.I_C_DataImport> COLUMN_C_DataImport_ID = new ModelColumn<>(I_I_ElementValue.class, "C_DataImport_ID", org.compiere.model.I_C_DataImport.class);
	String COLUMNNAME_C_DataImport_ID = "C_DataImport_ID";

	/**
	 * Set Data Import Run.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_DataImport_Run_ID (int C_DataImport_Run_ID);

	/**
	 * Get Data Import Run.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_DataImport_Run_ID();

	@Nullable org.compiere.model.I_C_DataImport_Run getC_DataImport_Run();

	void setC_DataImport_Run(@Nullable org.compiere.model.I_C_DataImport_Run C_DataImport_Run);

	ModelColumn<I_I_ElementValue, org.compiere.model.I_C_DataImport_Run> COLUMN_C_DataImport_Run_ID = new ModelColumn<>(I_I_ElementValue.class, "C_DataImport_Run_ID", org.compiere.model.I_C_DataImport_Run.class);
	String COLUMNNAME_C_DataImport_Run_ID = "C_DataImport_Run_ID";

	/**
	 * Set Element.
	 * Accounting Element
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Element_ID (int C_Element_ID);

	/**
	 * Get Element.
	 * Accounting Element
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Element_ID();

	String COLUMNNAME_C_Element_ID = "C_Element_ID";

	/**
	 * Set Kontenart.
	 * Account Element
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_ElementValue_ID (int C_ElementValue_ID);

	/**
	 * Get Kontenart.
	 * Account Element
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_ElementValue_ID();

	@Nullable org.compiere.model.I_C_ElementValue getC_ElementValue();

	void setC_ElementValue(@Nullable org.compiere.model.I_C_ElementValue C_ElementValue);

	ModelColumn<I_I_ElementValue, org.compiere.model.I_C_ElementValue> COLUMN_C_ElementValue_ID = new ModelColumn<>(I_I_ElementValue.class, "C_ElementValue_ID", org.compiere.model.I_C_ElementValue.class);
	String COLUMNNAME_C_ElementValue_ID = "C_ElementValue_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getCreated();

	ModelColumn<I_I_ElementValue, Object> COLUMN_Created = new ModelColumn<>(I_I_ElementValue.class, "Created", null);
	String COLUMNNAME_Created = "Created";

	/**
	 * Get Created By.
	 * User who created this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getCreatedBy();

	String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Default Account.
	 * Name of the Default Account Column
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDefault_Account (@Nullable java.lang.String Default_Account);

	/**
	 * Get Default Account.
	 * Name of the Default Account Column
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDefault_Account();

	ModelColumn<I_I_ElementValue, Object> COLUMN_Default_Account = new ModelColumn<>(I_I_ElementValue.class, "Default_Account", null);
	String COLUMNNAME_Default_Account = "Default_Account";

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

	ModelColumn<I_I_ElementValue, Object> COLUMN_Description = new ModelColumn<>(I_I_ElementValue.class, "Description", null);
	String COLUMNNAME_Description = "Description";

	/**
	 * Set Element Name.
	 * Name of the Element
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setElementName (@Nullable java.lang.String ElementName);

	/**
	 * Get Element Name.
	 * Name of the Element
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getElementName();

	ModelColumn<I_I_ElementValue, Object> COLUMN_ElementName = new ModelColumn<>(I_I_ElementValue.class, "ElementName", null);
	String COLUMNNAME_ElementName = "ElementName";

	/**
	 * Set Import - Kontendefinition.
	 * Import Account Value
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setI_ElementValue_ID (int I_ElementValue_ID);

	/**
	 * Get Import - Kontendefinition.
	 * Import Account Value
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getI_ElementValue_ID();

	ModelColumn<I_I_ElementValue, Object> COLUMN_I_ElementValue_ID = new ModelColumn<>(I_I_ElementValue.class, "I_ElementValue_ID", null);
	String COLUMNNAME_I_ElementValue_ID = "I_ElementValue_ID";

	/**
	 * Set Import Error Message.
	 * Messages generated from import process
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setI_ErrorMsg (@Nullable java.lang.String I_ErrorMsg);

	/**
	 * Get Import Error Message.
	 * Messages generated from import process
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getI_ErrorMsg();

	ModelColumn<I_I_ElementValue, Object> COLUMN_I_ErrorMsg = new ModelColumn<>(I_I_ElementValue.class, "I_ErrorMsg", null);
	String COLUMNNAME_I_ErrorMsg = "I_ErrorMsg";

	/**
	 * Set Imported.
	 * Has this import been processed
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setI_IsImported (boolean I_IsImported);

	/**
	 * Get Imported.
	 * Has this import been processed
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isI_IsImported();

	ModelColumn<I_I_ElementValue, Object> COLUMN_I_IsImported = new ModelColumn<>(I_I_ElementValue.class, "I_IsImported", null);
	String COLUMNNAME_I_IsImported = "I_IsImported";

	/**
	 * Set Import Line Content.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setI_LineContent (@Nullable java.lang.String I_LineContent);

	/**
	 * Get Import Line Content.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getI_LineContent();

	ModelColumn<I_I_ElementValue, Object> COLUMN_I_LineContent = new ModelColumn<>(I_I_ElementValue.class, "I_LineContent", null);
	String COLUMNNAME_I_LineContent = "I_LineContent";

	/**
	 * Set Import Line No.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setI_LineNo (int I_LineNo);

	/**
	 * Get Import Line No.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getI_LineNo();

	ModelColumn<I_I_ElementValue, Object> COLUMN_I_LineNo = new ModelColumn<>(I_I_ElementValue.class, "I_LineNo", null);
	String COLUMNNAME_I_LineNo = "I_LineNo";

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

	ModelColumn<I_I_ElementValue, Object> COLUMN_IsActive = new ModelColumn<>(I_I_ElementValue.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Belegartgesteuert.
	 * Control account - If an account is controlled by a document, you cannot post manually to it
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIsDocControlled (boolean IsDocControlled);

	/**
	 * Get Belegartgesteuert.
	 * Control account - If an account is controlled by a document, you cannot post manually to it
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isDocControlled();

	ModelColumn<I_I_ElementValue, Object> COLUMN_IsDocControlled = new ModelColumn<>(I_I_ElementValue.class, "IsDocControlled", null);
	String COLUMNNAME_IsDocControlled = "IsDocControlled";

	/**
	 * Set Summary Level.
	 * This is a summary entity
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIsSummary (boolean IsSummary);

	/**
	 * Get Summary Level.
	 * This is a summary entity
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isSummary();

	ModelColumn<I_I_ElementValue, Object> COLUMN_IsSummary = new ModelColumn<>(I_I_ElementValue.class, "IsSummary", null);
	String COLUMNNAME_IsSummary = "IsSummary";

	/**
	 * Set Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setName (@Nullable java.lang.String Name);

	/**
	 * Get Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getName();

	ModelColumn<I_I_ElementValue, Object> COLUMN_Name = new ModelColumn<>(I_I_ElementValue.class, "Name", null);
	String COLUMNNAME_Name = "Name";

	/**
	 * Set Übergeordnetes Konto.
	 * The parent (summary) account
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setParentElementValue_ID (int ParentElementValue_ID);

	/**
	 * Get Übergeordnetes Konto.
	 * The parent (summary) account
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getParentElementValue_ID();

	@Nullable org.compiere.model.I_C_ElementValue getParentElementValue();

	void setParentElementValue(@Nullable org.compiere.model.I_C_ElementValue ParentElementValue);

	ModelColumn<I_I_ElementValue, org.compiere.model.I_C_ElementValue> COLUMN_ParentElementValue_ID = new ModelColumn<>(I_I_ElementValue.class, "ParentElementValue_ID", org.compiere.model.I_C_ElementValue.class);
	String COLUMNNAME_ParentElementValue_ID = "ParentElementValue_ID";

	/**
	 * Set Schlüssel Übergeordnetes Konto .
	 * Key if the Parent
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setParentValue (@Nullable java.lang.String ParentValue);

	/**
	 * Get Schlüssel Übergeordnetes Konto .
	 * Key if the Parent
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getParentValue();

	ModelColumn<I_I_ElementValue, Object> COLUMN_ParentValue = new ModelColumn<>(I_I_ElementValue.class, "ParentValue", null);
	String COLUMNNAME_ParentValue = "ParentValue";

	/**
	 * Set Buchen "Ist".
	 * Actual Values can be posted
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPostActual (boolean PostActual);

	/**
	 * Get Buchen "Ist".
	 * Actual Values can be posted
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isPostActual();

	ModelColumn<I_I_ElementValue, Object> COLUMN_PostActual = new ModelColumn<>(I_I_ElementValue.class, "PostActual", null);
	String COLUMNNAME_PostActual = "PostActual";

	/**
	 * Set Buchen "Budget".
	 * Budget values can be posted
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPostBudget (boolean PostBudget);

	/**
	 * Get Buchen "Budget".
	 * Budget values can be posted
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isPostBudget();

	ModelColumn<I_I_ElementValue, Object> COLUMN_PostBudget = new ModelColumn<>(I_I_ElementValue.class, "PostBudget", null);
	String COLUMNNAME_PostBudget = "PostBudget";

	/**
	 * Set Buchen "Reservierung".
	 * Post commitments to this account
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPostEncumbrance (boolean PostEncumbrance);

	/**
	 * Get Buchen "Reservierung".
	 * Post commitments to this account
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isPostEncumbrance();

	ModelColumn<I_I_ElementValue, Object> COLUMN_PostEncumbrance = new ModelColumn<>(I_I_ElementValue.class, "PostEncumbrance", null);
	String COLUMNNAME_PostEncumbrance = "PostEncumbrance";

	/**
	 * Set Buchen "statistisch".
	 * Post statistical quantities to this account?
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPostStatistical (boolean PostStatistical);

	/**
	 * Get Buchen "statistisch".
	 * Post statistical quantities to this account?
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isPostStatistical();

	ModelColumn<I_I_ElementValue, Object> COLUMN_PostStatistical = new ModelColumn<>(I_I_ElementValue.class, "PostStatistical", null);
	String COLUMNNAME_PostStatistical = "PostStatistical";

	/**
	 * Set Processed.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setProcessed (boolean Processed);

	/**
	 * Get Processed.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isProcessed();

	ModelColumn<I_I_ElementValue, Object> COLUMN_Processed = new ModelColumn<>(I_I_ElementValue.class, "Processed", null);
	String COLUMNNAME_Processed = "Processed";

	/**
	 * Set Process Now.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setProcessing (boolean Processing);

	/**
	 * Get Process Now.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isProcessing();

	ModelColumn<I_I_ElementValue, Object> COLUMN_Processing = new ModelColumn<>(I_I_ElementValue.class, "Processing", null);
	String COLUMNNAME_Processing = "Processing";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getUpdated();

	ModelColumn<I_I_ElementValue, Object> COLUMN_Updated = new ModelColumn<>(I_I_ElementValue.class, "Updated", null);
	String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Updated By.
	 * User who updated this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getUpdatedBy();

	String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/**
	 * Set Search Key.
	 * Search key for the record in the format required - must be unique
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setValue (@Nullable java.lang.String Value);

	/**
	 * Get Search Key.
	 * Search key for the record in the format required - must be unique
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getValue();

	ModelColumn<I_I_ElementValue, Object> COLUMN_Value = new ModelColumn<>(I_I_ElementValue.class, "Value", null);
	String COLUMNNAME_Value = "Value";
}
