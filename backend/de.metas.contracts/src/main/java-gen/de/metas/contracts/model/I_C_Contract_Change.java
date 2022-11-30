package de.metas.contracts.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;

/** Generated Interface for C_Contract_Change
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_Contract_Change 
{

	String Table_Name = "C_Contract_Change";

//	/** AD_Table_ID=540028 */
//	int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);


	/**
	 * Set Action.
	 * Zeigt die durchzuführende Aktion an
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAction (java.lang.String Action);

	/**
	 * Get Action.
	 * Zeigt die durchzuführende Aktion an
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getAction();

	ModelColumn<I_C_Contract_Change, Object> COLUMN_Action = new ModelColumn<>(I_C_Contract_Change.class, "Action", null);
	String COLUMNNAME_Action = "Action";

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
	 * Set Abowechsel-Konditionen.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Contract_Change_ID (int C_Contract_Change_ID);

	/**
	 * Get Abowechsel-Konditionen.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Contract_Change_ID();

	ModelColumn<I_C_Contract_Change, Object> COLUMN_C_Contract_Change_ID = new ModelColumn<>(I_C_Contract_Change.class, "C_Contract_Change_ID", null);
	String COLUMNNAME_C_Contract_Change_ID = "C_Contract_Change_ID";

	/**
	 * Set Contract Terms.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Flatrate_Conditions_ID (int C_Flatrate_Conditions_ID);

	/**
	 * Get Contract Terms.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Flatrate_Conditions_ID();

	@Nullable de.metas.contracts.model.I_C_Flatrate_Conditions getC_Flatrate_Conditions();

	void setC_Flatrate_Conditions(@Nullable de.metas.contracts.model.I_C_Flatrate_Conditions C_Flatrate_Conditions);

	ModelColumn<I_C_Contract_Change, de.metas.contracts.model.I_C_Flatrate_Conditions> COLUMN_C_Flatrate_Conditions_ID = new ModelColumn<>(I_C_Contract_Change.class, "C_Flatrate_Conditions_ID", de.metas.contracts.model.I_C_Flatrate_Conditions.class);
	String COLUMNNAME_C_Flatrate_Conditions_ID = "C_Flatrate_Conditions_ID";

	/**
	 * Set Nächste Vertragsbedingungen.
	 * Auswahl der Vertragsbedingungen, die bei einer Vertragsverlängerung in der folgenden Vertragsperide anzuwenden sind
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Flatrate_Conditions_Next_ID (int C_Flatrate_Conditions_Next_ID);

	/**
	 * Get Nächste Vertragsbedingungen.
	 * Auswahl der Vertragsbedingungen, die bei einer Vertragsverlängerung in der folgenden Vertragsperide anzuwenden sind
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Flatrate_Conditions_Next_ID();

	@Nullable de.metas.contracts.model.I_C_Flatrate_Conditions getC_Flatrate_Conditions_Next();

	void setC_Flatrate_Conditions_Next(@Nullable de.metas.contracts.model.I_C_Flatrate_Conditions C_Flatrate_Conditions_Next);

	ModelColumn<I_C_Contract_Change, de.metas.contracts.model.I_C_Flatrate_Conditions> COLUMN_C_Flatrate_Conditions_Next_ID = new ModelColumn<>(I_C_Contract_Change.class, "C_Flatrate_Conditions_Next_ID", de.metas.contracts.model.I_C_Flatrate_Conditions.class);
	String COLUMNNAME_C_Flatrate_Conditions_Next_ID = "C_Flatrate_Conditions_Next_ID";

	/**
	 * Set Vertragsverlängerung/-übergang.
	 * Regelt z.B. die Vertragslaufzeit, Kündigungsfristen, autmatische Verlängerung usw.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Flatrate_Transition_ID (int C_Flatrate_Transition_ID);

	/**
	 * Get Vertragsverlängerung/-übergang.
	 * Regelt z.B. die Vertragslaufzeit, Kündigungsfristen, autmatische Verlängerung usw.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Flatrate_Transition_ID();

	de.metas.contracts.model.I_C_Flatrate_Transition getC_Flatrate_Transition();

	void setC_Flatrate_Transition(de.metas.contracts.model.I_C_Flatrate_Transition C_Flatrate_Transition);

	ModelColumn<I_C_Contract_Change, de.metas.contracts.model.I_C_Flatrate_Transition> COLUMN_C_Flatrate_Transition_ID = new ModelColumn<>(I_C_Contract_Change.class, "C_Flatrate_Transition_ID", de.metas.contracts.model.I_C_Flatrate_Transition.class);
	String COLUMNNAME_C_Flatrate_Transition_ID = "C_Flatrate_Transition_ID";

	/**
	 * Set Contract Status.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setContractStatus (@Nullable java.lang.String ContractStatus);

	/**
	 * Get Contract Status.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getContractStatus();

	ModelColumn<I_C_Contract_Change, Object> COLUMN_ContractStatus = new ModelColumn<>(I_C_Contract_Change.class, "ContractStatus", null);
	String COLUMNNAME_ContractStatus = "ContractStatus";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_C_Contract_Change, Object> COLUMN_Created = new ModelColumn<>(I_C_Contract_Change.class, "Created", null);
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
	 * Set Gültigkeitsfrist.
	 * Zeitpunk vor dem Vertragsende, bis zu dem die Änderungskondition gültig ist
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDeadLine (int DeadLine);

	/**
	 * Get Gültigkeitsfrist.
	 * Zeitpunk vor dem Vertragsende, bis zu dem die Änderungskondition gültig ist
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getDeadLine();

	ModelColumn<I_C_Contract_Change, Object> COLUMN_DeadLine = new ModelColumn<>(I_C_Contract_Change.class, "DeadLine", null);
	String COLUMNNAME_DeadLine = "DeadLine";

	/**
	 * Set Einheit der Frist.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDeadLineUnit (java.lang.String DeadLineUnit);

	/**
	 * Get Einheit der Frist.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getDeadLineUnit();

	ModelColumn<I_C_Contract_Change, Object> COLUMN_DeadLineUnit = new ModelColumn<>(I_C_Contract_Change.class, "DeadLineUnit", null);
	String COLUMNNAME_DeadLineUnit = "DeadLineUnit";

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

	ModelColumn<I_C_Contract_Change, Object> COLUMN_Description = new ModelColumn<>(I_C_Contract_Change.class, "Description", null);
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

	ModelColumn<I_C_Contract_Change, Object> COLUMN_IsActive = new ModelColumn<>(I_C_Contract_Change.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Pricing System.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_PricingSystem_ID (int M_PricingSystem_ID);

	/**
	 * Get Pricing System.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_PricingSystem_ID();

	String COLUMNNAME_M_PricingSystem_ID = "M_PricingSystem_ID";

	/**
	 * Set Product.
	 * Product, Service, Item
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_Product_ID (int M_Product_ID);

	/**
	 * Get Product.
	 * Product, Service, Item
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_Product_ID();

	String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_C_Contract_Change, Object> COLUMN_Updated = new ModelColumn<>(I_C_Contract_Change.class, "Updated", null);
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
