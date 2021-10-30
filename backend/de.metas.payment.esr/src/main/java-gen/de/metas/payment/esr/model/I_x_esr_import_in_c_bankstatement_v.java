package de.metas.payment.esr.model;

import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for x_esr_import_in_c_bankstatement_v
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_x_esr_import_in_c_bankstatement_v 
{

	String Table_Name = "x_esr_import_in_c_bankstatement_v";

//	/** AD_Table_ID=540685 */
//	int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);


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
	 * Set Bank Statement.
	 * Bank Statement of account
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_BankStatement_ID (int C_BankStatement_ID);

	/**
	 * Get Bank Statement.
	 * Bank Statement of account
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_BankStatement_ID();

	String COLUMNNAME_C_BankStatement_ID = "C_BankStatement_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getCreated();

	ModelColumn<I_x_esr_import_in_c_bankstatement_v, Object> COLUMN_Created = new ModelColumn<>(I_x_esr_import_in_c_bankstatement_v.class, "Created", null);
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
	 * Set Document Date.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDateDoc (@Nullable java.sql.Timestamp DateDoc);

	/**
	 * Get Document Date.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getDateDoc();

	ModelColumn<I_x_esr_import_in_c_bankstatement_v, Object> COLUMN_DateDoc = new ModelColumn<>(I_x_esr_import_in_c_bankstatement_v.class, "DateDoc", null);
	String COLUMNNAME_DateDoc = "DateDoc";

	/**
	 * Set ESR Payment Import.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setESR_Import_ID (int ESR_Import_ID);

	/**
	 * Get ESR Payment Import.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getESR_Import_ID();

	@Nullable de.metas.payment.esr.model.I_ESR_Import getESR_Import();

	void setESR_Import(@Nullable de.metas.payment.esr.model.I_ESR_Import ESR_Import);

	ModelColumn<I_x_esr_import_in_c_bankstatement_v, de.metas.payment.esr.model.I_ESR_Import> COLUMN_ESR_Import_ID = new ModelColumn<>(I_x_esr_import_in_c_bankstatement_v.class, "ESR_Import_ID", de.metas.payment.esr.model.I_ESR_Import.class);
	String COLUMNNAME_ESR_Import_ID = "ESR_Import_ID";
}
