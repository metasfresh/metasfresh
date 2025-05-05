package org.compiere.model;

import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for C_Bank
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_Bank 
{

	String Table_Name = "C_Bank";

//	/** AD_Table_ID=296 */
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
	 * Set Bank.
	 * Bank
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Bank_ID (int C_Bank_ID);

	/**
	 * Get Bank.
	 * Bank
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Bank_ID();

	ModelColumn<I_C_Bank, Object> COLUMN_C_Bank_ID = new ModelColumn<>(I_C_Bank.class, "C_Bank_ID", null);
	String COLUMNNAME_C_Bank_ID = "C_Bank_ID";

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

	ModelColumn<I_C_Bank, org.compiere.model.I_C_DataImport> COLUMN_C_DataImport_ID = new ModelColumn<>(I_C_Bank.class, "C_DataImport_ID", org.compiere.model.I_C_DataImport.class);
	String COLUMNNAME_C_DataImport_ID = "C_DataImport_ID";

	/**
	 * Set Location.
	 * Location or Address
	 *
	 * <br>Type: Location
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Location_ID (int C_Location_ID);

	/**
	 * Get Location.
	 * Location or Address
	 *
	 * <br>Type: Location
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Location_ID();

	@Nullable org.compiere.model.I_C_Location getC_Location();

	void setC_Location(@Nullable org.compiere.model.I_C_Location C_Location);

	ModelColumn<I_C_Bank, org.compiere.model.I_C_Location> COLUMN_C_Location_ID = new ModelColumn<>(I_C_Bank.class, "C_Location_ID", org.compiere.model.I_C_Location.class);
	String COLUMNNAME_C_Location_ID = "C_Location_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_C_Bank, Object> COLUMN_Created = new ModelColumn<>(I_C_Bank.class, "Created", null);
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

	ModelColumn<I_C_Bank, Object> COLUMN_Description = new ModelColumn<>(I_C_Bank.class, "Description", null);
	String COLUMNNAME_Description = "Description";

	/**
	 * Set Bank ist die Postfinance-Bank.
	 * Markiert die Postfinanz-Bank. Hinweis: auch andere Banken können am ESR-Verfahren teilnehmen, ohne selbst die Postfinance-Bank zu sein.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setESR_PostBank (boolean ESR_PostBank);

	/**
	 * Get Bank ist die Postfinance-Bank.
	 * Markiert die Postfinanz-Bank. Hinweis: auch andere Banken können am ESR-Verfahren teilnehmen, ohne selbst die Postfinance-Bank zu sein.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isESR_PostBank();

	ModelColumn<I_C_Bank, Object> COLUMN_ESR_PostBank = new ModelColumn<>(I_C_Bank.class, "ESR_PostBank", null);
	String COLUMNNAME_ESR_PostBank = "ESR_PostBank";

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

	ModelColumn<I_C_Bank, Object> COLUMN_IsActive = new ModelColumn<>(I_C_Bank.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Cash Bank.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsCashBank (boolean IsCashBank);

	/**
	 * Get Cash Bank.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isCashBank();

	ModelColumn<I_C_Bank, Object> COLUMN_IsCashBank = new ModelColumn<>(I_C_Bank.class, "IsCashBank", null);
	String COLUMNNAME_IsCashBank = "IsCashBank";

	/**
	 * Set Import As Summary Line.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsImportAsSingleSummaryLine (boolean IsImportAsSingleSummaryLine);

	/**
	 * Get Import As Summary Line.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isImportAsSingleSummaryLine();

	ModelColumn<I_C_Bank, Object> COLUMN_IsImportAsSingleSummaryLine = new ModelColumn<>(I_C_Bank.class, "IsImportAsSingleSummaryLine", null);
	String COLUMNNAME_IsImportAsSingleSummaryLine = "IsImportAsSingleSummaryLine";

	/**
	 * Set Own Bank.
	 * Bank for this Organization
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsOwnBank (boolean IsOwnBank);

	/**
	 * Get Own Bank.
	 * Bank for this Organization
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isOwnBank();

	ModelColumn<I_C_Bank, Object> COLUMN_IsOwnBank = new ModelColumn<>(I_C_Bank.class, "IsOwnBank", null);
	String COLUMNNAME_IsOwnBank = "IsOwnBank";

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

	ModelColumn<I_C_Bank, Object> COLUMN_Name = new ModelColumn<>(I_C_Bank.class, "Name", null);
	String COLUMNNAME_Name = "Name";

	/**
	 * Set Routing No.
	 * Bank Routing Number
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setRoutingNo (java.lang.String RoutingNo);

	/**
	 * Get Routing No.
	 * Bank Routing Number
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getRoutingNo();

	ModelColumn<I_C_Bank, Object> COLUMN_RoutingNo = new ModelColumn<>(I_C_Bank.class, "RoutingNo", null);
	String COLUMNNAME_RoutingNo = "RoutingNo";

	/**
	 * Set Swift code.
	 * Swift Code or BIC
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setSwiftCode (@Nullable java.lang.String SwiftCode);

	/**
	 * Get Swift code.
	 * Swift Code or BIC
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getSwiftCode();

	ModelColumn<I_C_Bank, Object> COLUMN_SwiftCode = new ModelColumn<>(I_C_Bank.class, "SwiftCode", null);
	String COLUMNNAME_SwiftCode = "SwiftCode";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_C_Bank, Object> COLUMN_Updated = new ModelColumn<>(I_C_Bank.class, "Updated", null);
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
