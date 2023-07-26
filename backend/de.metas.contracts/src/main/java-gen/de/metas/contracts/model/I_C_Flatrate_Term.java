package de.metas.contracts.model;

import java.math.BigDecimal;
import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for C_Flatrate_Term
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_Flatrate_Term 
{

	String Table_Name = "C_Flatrate_Term";

//	/** AD_Table_ID=540320 */
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
	 * Set Verarbeitung zum Laufzeitende.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_PInstance_EndOfTerm_ID (int AD_PInstance_EndOfTerm_ID);

	/**
	 * Get Verarbeitung zum Laufzeitende.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_PInstance_EndOfTerm_ID();

	@Nullable org.compiere.model.I_AD_PInstance getAD_PInstance_EndOfTerm();

	void setAD_PInstance_EndOfTerm(@Nullable org.compiere.model.I_AD_PInstance AD_PInstance_EndOfTerm);

	ModelColumn<I_C_Flatrate_Term, org.compiere.model.I_AD_PInstance> COLUMN_AD_PInstance_EndOfTerm_ID = new ModelColumn<>(I_C_Flatrate_Term.class, "AD_PInstance_EndOfTerm_ID", org.compiere.model.I_AD_PInstance.class);
	String COLUMNNAME_AD_PInstance_EndOfTerm_ID = "AD_PInstance_EndOfTerm_ID";

	/**
	 * Set Betreuer.
	 * Person, die bei einem fachlichen Problem vom System informiert wird.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_User_InCharge_ID (int AD_User_InCharge_ID);

	/**
	 * Get Betreuer.
	 * Person, die bei einem fachlichen Problem vom System informiert wird.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_User_InCharge_ID();

	String COLUMNNAME_AD_User_InCharge_ID = "AD_User_InCharge_ID";

	/**
	 * Set Bill Business Partner.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setBill_BPartner_ID (int Bill_BPartner_ID);

	/**
	 * Get Bill Business Partner.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getBill_BPartner_ID();

	String COLUMNNAME_Bill_BPartner_ID = "Bill_BPartner_ID";

	/**
	 * Set Bill Location.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setBill_Location_ID (int Bill_Location_ID);

	/**
	 * Get Bill Location.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getBill_Location_ID();

	String COLUMNNAME_Bill_Location_ID = "Bill_Location_ID";

	/**
	 * Set Bill Contact.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setBill_User_ID (int Bill_User_ID);

	/**
	 * Get Bill Contact.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getBill_User_ID();

	String COLUMNNAME_Bill_User_ID = "Bill_User_ID";

	/**
	 * Set Currency.
	 * The Currency for this record
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Currency_ID (int C_Currency_ID);

	/**
	 * Get Currency.
	 * The Currency for this record
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Currency_ID();

	String COLUMNNAME_C_Currency_ID = "C_Currency_ID";

	/**
	 * Set Contract Terms.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Flatrate_Conditions_ID (int C_Flatrate_Conditions_ID);

	/**
	 * Get Contract Terms.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Flatrate_Conditions_ID();

	de.metas.contracts.model.I_C_Flatrate_Conditions getC_Flatrate_Conditions();

	void setC_Flatrate_Conditions(de.metas.contracts.model.I_C_Flatrate_Conditions C_Flatrate_Conditions);

	ModelColumn<I_C_Flatrate_Term, de.metas.contracts.model.I_C_Flatrate_Conditions> COLUMN_C_Flatrate_Conditions_ID = new ModelColumn<>(I_C_Flatrate_Term.class, "C_Flatrate_Conditions_ID", de.metas.contracts.model.I_C_Flatrate_Conditions.class);
	String COLUMNNAME_C_Flatrate_Conditions_ID = "C_Flatrate_Conditions_ID";

	/**
	 * Set Datenerfassung.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Flatrate_Data_ID (int C_Flatrate_Data_ID);

	/**
	 * Get Datenerfassung.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Flatrate_Data_ID();

	de.metas.contracts.model.I_C_Flatrate_Data getC_Flatrate_Data();

	void setC_Flatrate_Data(de.metas.contracts.model.I_C_Flatrate_Data C_Flatrate_Data);

	ModelColumn<I_C_Flatrate_Term, de.metas.contracts.model.I_C_Flatrate_Data> COLUMN_C_Flatrate_Data_ID = new ModelColumn<>(I_C_Flatrate_Term.class, "C_Flatrate_Data_ID", de.metas.contracts.model.I_C_Flatrate_Data.class);
	String COLUMNNAME_C_Flatrate_Data_ID = "C_Flatrate_Data_ID";

	/**
	 * Set Flatrate Term.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Flatrate_Term_ID (int C_Flatrate_Term_ID);

	/**
	 * Get Flatrate Term.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Flatrate_Term_ID();

	ModelColumn<I_C_Flatrate_Term, Object> COLUMN_C_Flatrate_Term_ID = new ModelColumn<>(I_C_Flatrate_Term.class, "C_Flatrate_Term_ID", null);
	String COLUMNNAME_C_Flatrate_Term_ID = "C_Flatrate_Term_ID";

	/**
	 * Set Nachfolgende Vertragsperiode.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_FlatrateTerm_Next_ID (int C_FlatrateTerm_Next_ID);

	/**
	 * Get Nachfolgende Vertragsperiode.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_FlatrateTerm_Next_ID();

	@Nullable de.metas.contracts.model.I_C_Flatrate_Term getC_FlatrateTerm_Next();

	void setC_FlatrateTerm_Next(@Nullable de.metas.contracts.model.I_C_Flatrate_Term C_FlatrateTerm_Next);

	ModelColumn<I_C_Flatrate_Term, de.metas.contracts.model.I_C_Flatrate_Term> COLUMN_C_FlatrateTerm_Next_ID = new ModelColumn<>(I_C_Flatrate_Term.class, "C_FlatrateTerm_Next_ID", de.metas.contracts.model.I_C_Flatrate_Term.class);
	String COLUMNNAME_C_FlatrateTerm_Next_ID = "C_FlatrateTerm_Next_ID";

	/**
	 * Set Vertragsverlängerung/-übergang.
	 * Regelt z.B. die Vertragslaufzeit, Kündigungsfristen, autmatische Verlängerung usw.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setC_Flatrate_Transition_ID (int C_Flatrate_Transition_ID);

	/**
	 * Get Vertragsverlängerung/-übergang.
	 * Regelt z.B. die Vertragslaufzeit, Kündigungsfristen, autmatische Verlängerung usw.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	int getC_Flatrate_Transition_ID();

	@Nullable de.metas.contracts.model.I_C_Flatrate_Transition getC_Flatrate_Transition();

	@Deprecated
	void setC_Flatrate_Transition(@Nullable de.metas.contracts.model.I_C_Flatrate_Transition C_Flatrate_Transition);

	ModelColumn<I_C_Flatrate_Term, de.metas.contracts.model.I_C_Flatrate_Transition> COLUMN_C_Flatrate_Transition_ID = new ModelColumn<>(I_C_Flatrate_Term.class, "C_Flatrate_Transition_ID", de.metas.contracts.model.I_C_Flatrate_Transition.class);
	String COLUMNNAME_C_Flatrate_Transition_ID = "C_Flatrate_Transition_ID";

	/**
	 * Set Ändern oder Kündigen.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setChangeOrCancelTerm (@Nullable java.lang.String ChangeOrCancelTerm);

	/**
	 * Get Ändern oder Kündigen.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getChangeOrCancelTerm();

	ModelColumn<I_C_Flatrate_Term, Object> COLUMN_ChangeOrCancelTerm = new ModelColumn<>(I_C_Flatrate_Term.class, "ChangeOrCancelTerm", null);
	String COLUMNNAME_ChangeOrCancelTerm = "ChangeOrCancelTerm";

	/**
	 * Set Vertrags-Status.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setContractStatus (@Nullable java.lang.String ContractStatus);

	/**
	 * Get Vertrags-Status.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getContractStatus();

	ModelColumn<I_C_Flatrate_Term, Object> COLUMN_ContractStatus = new ModelColumn<>(I_C_Flatrate_Term.class, "ContractStatus", null);
	String COLUMNNAME_ContractStatus = "ContractStatus";

	/**
	 * Set Änderungs-Auftragszeile.
	 * Auftragszeile, mit der der Vertrag vor dem regulären Ende gekündigt oder umgewandelt wurde
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_OrderLine_TermChange_ID (int C_OrderLine_TermChange_ID);

	/**
	 * Get Änderungs-Auftragszeile.
	 * Auftragszeile, mit der der Vertrag vor dem regulären Ende gekündigt oder umgewandelt wurde
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_OrderLine_TermChange_ID();

	@Nullable org.compiere.model.I_C_OrderLine getC_OrderLine_TermChange();

	void setC_OrderLine_TermChange(@Nullable org.compiere.model.I_C_OrderLine C_OrderLine_TermChange);

	ModelColumn<I_C_Flatrate_Term, org.compiere.model.I_C_OrderLine> COLUMN_C_OrderLine_TermChange_ID = new ModelColumn<>(I_C_Flatrate_Term.class, "C_OrderLine_TermChange_ID", org.compiere.model.I_C_OrderLine.class);
	String COLUMNNAME_C_OrderLine_TermChange_ID = "C_OrderLine_TermChange_ID";

	/**
	 * Set Vertrags-Auftragszeile.
	 * Auftragszeile, mit der der Vertrag abgeschlossen wurde
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_OrderLine_Term_ID (int C_OrderLine_Term_ID);

	/**
	 * Get Vertrags-Auftragszeile.
	 * Auftragszeile, mit der der Vertrag abgeschlossen wurde
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_OrderLine_Term_ID();

	@Nullable org.compiere.model.I_C_OrderLine getC_OrderLine_Term();

	void setC_OrderLine_Term(@Nullable org.compiere.model.I_C_OrderLine C_OrderLine_Term);

	ModelColumn<I_C_Flatrate_Term, org.compiere.model.I_C_OrderLine> COLUMN_C_OrderLine_Term_ID = new ModelColumn<>(I_C_Flatrate_Term.class, "C_OrderLine_Term_ID", org.compiere.model.I_C_OrderLine.class);
	String COLUMNNAME_C_OrderLine_Term_ID = "C_OrderLine_Term_ID";

	/**
	 * Set Änderungs-Auftrag.
	 * Auftrag, mit der der Vertrag vor dem regulären Ende gekündigt oder umgewandelt wurde
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setC_Order_TermChange_ID (int C_Order_TermChange_ID);

	/**
	 * Get Änderungs-Auftrag.
	 * Auftrag, mit der der Vertrag vor dem regulären Ende gekündigt oder umgewandelt wurde
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	int getC_Order_TermChange_ID();

	@Nullable org.compiere.model.I_C_Order getC_Order_TermChange();

	@Deprecated
	void setC_Order_TermChange(@Nullable org.compiere.model.I_C_Order C_Order_TermChange);

	ModelColumn<I_C_Flatrate_Term, org.compiere.model.I_C_Order> COLUMN_C_Order_TermChange_ID = new ModelColumn<>(I_C_Flatrate_Term.class, "C_Order_TermChange_ID", org.compiere.model.I_C_Order.class);
	String COLUMNNAME_C_Order_TermChange_ID = "C_Order_TermChange_ID";

	/**
	 * Set Vertrags-Auftrag.
	 * Auftrag, mit der der Vertrag abgeschlossen wurde
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Order_Term_ID (int C_Order_Term_ID);

	/**
	 * Get Vertrags-Auftrag.
	 * Auftrag, mit der der Vertrag abgeschlossen wurde
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Order_Term_ID();

	@Nullable org.compiere.model.I_C_Order getC_Order_Term();

	void setC_Order_Term(@Nullable org.compiere.model.I_C_Order C_Order_Term);

	ModelColumn<I_C_Flatrate_Term, org.compiere.model.I_C_Order> COLUMN_C_Order_Term_ID = new ModelColumn<>(I_C_Flatrate_Term.class, "C_Order_Term_ID", org.compiere.model.I_C_Order.class);
	String COLUMNNAME_C_Order_Term_ID = "C_Order_Term_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_C_Flatrate_Term, Object> COLUMN_Created = new ModelColumn<>(I_C_Flatrate_Term.class, "Created", null);
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
	 * Set Steuerkategorie.
	 * Steuerkategorie
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_TaxCategory_ID (int C_TaxCategory_ID);

	/**
	 * Get Steuerkategorie.
	 * Steuerkategorie
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_TaxCategory_ID();

	String COLUMNNAME_C_TaxCategory_ID = "C_TaxCategory_ID";

	/**
	 * Set UOM.
	 * Unit of Measure
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_UOM_ID (int C_UOM_ID);

	/**
	 * Get UOM.
	 * Unit of Measure
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_UOM_ID();

	String COLUMNNAME_C_UOM_ID = "C_UOM_ID";

	/**
	 * Set Vertrag Datum.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDateContracted (@Nullable java.sql.Timestamp DateContracted);

	/**
	 * Get Vertrag Datum.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getDateContracted();

	ModelColumn<I_C_Flatrate_Term, Object> COLUMN_DateContracted = new ModelColumn<>(I_C_Flatrate_Term.class, "DateContracted", null);
	String COLUMNNAME_DateContracted = "DateContracted";

	/**
	 * Set Lieferart.
	 * Definiert die zeitliche Steuerung von Lieferungen
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDeliveryRule (@Nullable java.lang.String DeliveryRule);

	/**
	 * Get Lieferart.
	 * Definiert die zeitliche Steuerung von Lieferungen
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDeliveryRule();

	ModelColumn<I_C_Flatrate_Term, Object> COLUMN_DeliveryRule = new ModelColumn<>(I_C_Flatrate_Term.class, "DeliveryRule", null);
	String COLUMNNAME_DeliveryRule = "DeliveryRule";

	/**
	 * Set Lieferung.
	 * Wie der Auftrag geliefert wird
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDeliveryViaRule (@Nullable java.lang.String DeliveryViaRule);

	/**
	 * Get Lieferung.
	 * Wie der Auftrag geliefert wird
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDeliveryViaRule();

	ModelColumn<I_C_Flatrate_Term, Object> COLUMN_DeliveryViaRule = new ModelColumn<>(I_C_Flatrate_Term.class, "DeliveryViaRule", null);
	String COLUMNNAME_DeliveryViaRule = "DeliveryViaRule";

	/**
	 * Set Process Batch.
	 * Der zukünftige Status des Belegs
	 *
	 * <br>Type: Button
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDocAction (java.lang.String DocAction);

	/**
	 * Get Process Batch.
	 * Der zukünftige Status des Belegs
	 *
	 * <br>Type: Button
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getDocAction();

	ModelColumn<I_C_Flatrate_Term, Object> COLUMN_DocAction = new ModelColumn<>(I_C_Flatrate_Term.class, "DocAction", null);
	String COLUMNNAME_DocAction = "DocAction";

	/**
	 * Set Status.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDocStatus (java.lang.String DocStatus);

	/**
	 * Get Status.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getDocStatus();

	ModelColumn<I_C_Flatrate_Term, Object> COLUMN_DocStatus = new ModelColumn<>(I_C_Flatrate_Term.class, "DocStatus", null);
	String COLUMNNAME_DocStatus = "DocStatus";

	/**
	 * Set Document No.
	 * Document sequence number of the document
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDocumentNo (java.lang.String DocumentNo);

	/**
	 * Get Document No.
	 * Document sequence number of the document
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getDocumentNo();

	ModelColumn<I_C_Flatrate_Term, Object> COLUMN_DocumentNo = new ModelColumn<>(I_C_Flatrate_Term.class, "DocumentNo", null);
	String COLUMNNAME_DocumentNo = "DocumentNo";

	/**
	 * Set Ship Business Partner.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDropShip_BPartner_ID (int DropShip_BPartner_ID);

	/**
	 * Get Ship Business Partner.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getDropShip_BPartner_ID();

	String COLUMNNAME_DropShip_BPartner_ID = "DropShip_BPartner_ID";

	/**
	 * Set Ship Location.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDropShip_Location_ID (int DropShip_Location_ID);

	/**
	 * Get Ship Location.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getDropShip_Location_ID();

	String COLUMNNAME_DropShip_Location_ID = "DropShip_Location_ID";

	/**
	 * Set Ship Contact.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDropShip_User_ID (int DropShip_User_ID);

	/**
	 * Get Ship Contact.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getDropShip_User_ID();

	String COLUMNNAME_DropShip_User_ID = "DropShip_User_ID";

	/**
	 * Set Enddatum.
	 * Last effective date (inclusive)
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setEndDate (@Nullable java.sql.Timestamp EndDate);

	/**
	 * Get Enddatum.
	 * Last effective date (inclusive)
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getEndDate();

	ModelColumn<I_C_Flatrate_Term, Object> COLUMN_EndDate = new ModelColumn<>(I_C_Flatrate_Term.class, "EndDate", null);
	String COLUMNNAME_EndDate = "EndDate";

	/**
	 * Set Vertrag jetzt verlängern.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setExtendTerm (@Nullable java.lang.String ExtendTerm);

	/**
	 * Get Vertrag jetzt verlängern.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getExtendTerm();

	ModelColumn<I_C_Flatrate_Term, Object> COLUMN_ExtendTerm = new ModelColumn<>(I_C_Flatrate_Term.class, "ExtendTerm", null);
	String COLUMNNAME_ExtendTerm = "ExtendTerm";

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

	ModelColumn<I_C_Flatrate_Term, Object> COLUMN_IsActive = new ModelColumn<>(I_C_Flatrate_Term.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Vertrag autom. verlängern.
	 * Wenn dieser Haken gesetzt ist, werden laufende Verträge automatisch verlängert
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsAutoRenew (boolean IsAutoRenew);

	/**
	 * Get Vertrag autom. verlängern.
	 * Wenn dieser Haken gesetzt ist, werden laufende Verträge automatisch verlängert
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isAutoRenew();

	ModelColumn<I_C_Flatrate_Term, Object> COLUMN_IsAutoRenew = new ModelColumn<>(I_C_Flatrate_Term.class, "IsAutoRenew", null);
	String COLUMNNAME_IsAutoRenew = "IsAutoRenew";

	/**
	 * Set Rechnungskandidat schließen.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIsCloseInvoiceCandidate (boolean IsCloseInvoiceCandidate);

	/**
	 * Get Rechnungskandidat schließen.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isCloseInvoiceCandidate();

	ModelColumn<I_C_Flatrate_Term, Object> COLUMN_IsCloseInvoiceCandidate = new ModelColumn<>(I_C_Flatrate_Term.class, "IsCloseInvoiceCandidate", null);
	String COLUMNNAME_IsCloseInvoiceCandidate = "IsCloseInvoiceCandidate";

	/**
	 * Set Gegenüberstellung mit erbr. Leist..
	 * Legt fest, ob die pauschal abgerechenten Beträge den tatsächlich erbrachten Leistungen gegenüber gestellt werden sollen
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setIsClosingWithActualSum (boolean IsClosingWithActualSum);

	/**
	 * Get Gegenüberstellung mit erbr. Leist..
	 * Legt fest, ob die pauschal abgerechenten Beträge den tatsächlich erbrachten Leistungen gegenüber gestellt werden sollen
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	boolean isClosingWithActualSum();

	ModelColumn<I_C_Flatrate_Term, Object> COLUMN_IsClosingWithActualSum = new ModelColumn<>(I_C_Flatrate_Term.class, "IsClosingWithActualSum", null);
	String COLUMNNAME_IsClosingWithActualSum = "IsClosingWithActualSum";

	/**
	 * Set Abschlusskorrektur vorsehen.
	 * Legt fest, ob innerhalb der Vertragslaufzeit (in der Regel zu deren Ende) noch korrigierte Pauschalen-Mengen erfasst werden können
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setIsClosingWithCorrectionSum (boolean IsClosingWithCorrectionSum);

	/**
	 * Get Abschlusskorrektur vorsehen.
	 * Legt fest, ob innerhalb der Vertragslaufzeit (in der Regel zu deren Ende) noch korrigierte Pauschalen-Mengen erfasst werden können
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	boolean isClosingWithCorrectionSum();

	ModelColumn<I_C_Flatrate_Term, Object> COLUMN_IsClosingWithCorrectionSum = new ModelColumn<>(I_C_Flatrate_Term.class, "IsClosingWithCorrectionSum", null);
	String COLUMNNAME_IsClosingWithCorrectionSum = "IsClosingWithCorrectionSum";

	/**
	 * Set Simulation.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsSimulation (boolean IsSimulation);

	/**
	 * Get Simulation.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isSimulation();

	ModelColumn<I_C_Flatrate_Term, Object> COLUMN_IsSimulation = new ModelColumn<>(I_C_Flatrate_Term.class, "IsSimulation", null);
	String COLUMNNAME_IsSimulation = "IsSimulation";

	/**
	 * Set Preis inklusive Steuern.
	 * Tax is included in the price
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsTaxIncluded (boolean IsTaxIncluded);

	/**
	 * Get Preis inklusive Steuern.
	 * Tax is included in the price
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isTaxIncluded();

	ModelColumn<I_C_Flatrate_Term, Object> COLUMN_IsTaxIncluded = new ModelColumn<>(I_C_Flatrate_Term.class, "IsTaxIncluded", null);
	String COLUMNNAME_IsTaxIncluded = "IsTaxIncluded";

	/**
	 * Set Vertrag Nr..
	 * Document sequence number of the document
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setMasterDocumentNo (@Nullable java.lang.String MasterDocumentNo);

	/**
	 * Get Vertrag Nr..
	 * Document sequence number of the document
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getMasterDocumentNo();

	ModelColumn<I_C_Flatrate_Term, Object> COLUMN_MasterDocumentNo = new ModelColumn<>(I_C_Flatrate_Term.class, "MasterDocumentNo", null);
	String COLUMNNAME_MasterDocumentNo = "MasterDocumentNo";

	/**
	 * Set Contract End Date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setMasterEndDate (@Nullable java.sql.Timestamp MasterEndDate);

	/**
	 * Get Contract End Date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getMasterEndDate();

	ModelColumn<I_C_Flatrate_Term, Object> COLUMN_MasterEndDate = new ModelColumn<>(I_C_Flatrate_Term.class, "MasterEndDate", null);
	String COLUMNNAME_MasterEndDate = "MasterEndDate";

	/**
	 * Set Contract Start Date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setMasterStartDate (@Nullable java.sql.Timestamp MasterStartDate);

	/**
	 * Get Contract Start Date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getMasterStartDate();

	ModelColumn<I_C_Flatrate_Term, Object> COLUMN_MasterStartDate = new ModelColumn<>(I_C_Flatrate_Term.class, "MasterStartDate", null);
	String COLUMNNAME_MasterStartDate = "MasterStartDate";

	/**
	 * Set Attributes.
	 * Attribute Instances for Products
	 *
	 * <br>Type: PAttribute
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_AttributeSetInstance_ID (int M_AttributeSetInstance_ID);

	/**
	 * Get Attributes.
	 * Attribute Instances for Products
	 *
	 * <br>Type: PAttribute
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_AttributeSetInstance_ID();

	@Nullable org.compiere.model.I_M_AttributeSetInstance getM_AttributeSetInstance();

	void setM_AttributeSetInstance(@Nullable org.compiere.model.I_M_AttributeSetInstance M_AttributeSetInstance);

	ModelColumn<I_C_Flatrate_Term, org.compiere.model.I_M_AttributeSetInstance> COLUMN_M_AttributeSetInstance_ID = new ModelColumn<>(I_C_Flatrate_Term.class, "M_AttributeSetInstance_ID", org.compiere.model.I_M_AttributeSetInstance.class);
	String COLUMNNAME_M_AttributeSetInstance_ID = "M_AttributeSetInstance_ID";

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
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_Product_ID (int M_Product_ID);

	/**
	 * Get Product.
	 * Product, Service, Item
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_Product_ID();

	/** Column definition for M_Product_ID */
	org.adempiere.model.ModelColumn<I_C_Flatrate_Term, org.compiere.model.I_M_Product> COLUMN_M_Product_ID = new org.adempiere.model.ModelColumn<>(I_C_Flatrate_Term.class, "M_Product_ID", org.compiere.model.I_M_Product.class);

	String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/**
	 * Set Note.
	 * Optional additional user defined information
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setNote (@Nullable java.lang.String Note);

	/**
	 * Get Note.
	 * Optional additional user defined information
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getNote();

	ModelColumn<I_C_Flatrate_Term, Object> COLUMN_Note = new ModelColumn<>(I_C_Flatrate_Term.class, "Note", null);
	String COLUMNNAME_Note = "Note";

	/**
	 * Set Kündigungs/Benachrichtigungsfrist.
	 * Datum vor Ende der Vertragslaufzeit, an dem der laufende Vertrag automatisch verlängert oder aber der Betreuer informiert wird.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setNoticeDate (@Nullable java.sql.Timestamp NoticeDate);

	/**
	 * Get Kündigungs/Benachrichtigungsfrist.
	 * Datum vor Ende der Vertragslaufzeit, an dem der laufende Vertrag automatisch verlängert oder aber der Betreuer informiert wird.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getNoticeDate();

	ModelColumn<I_C_Flatrate_Term, Object> COLUMN_NoticeDate = new ModelColumn<>(I_C_Flatrate_Term.class, "NoticeDate", null);
	String COLUMNNAME_NoticeDate = "NoticeDate";

	/**
	 * Set Planmenge pro Maßeinheit.
	 * Geplante Menge der zu erbringenden Leistung (z.B. zu liefernde Teile), pro pauschal abzurechnender Einheit (z.B. Pflegetag).
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPlannedQtyPerUnit (BigDecimal PlannedQtyPerUnit);

	/**
	 * Get Planmenge pro Maßeinheit.
	 * Geplante Menge der zu erbringenden Leistung (z.B. zu liefernde Teile), pro pauschal abzurechnender Einheit (z.B. Pflegetag).
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getPlannedQtyPerUnit();

	ModelColumn<I_C_Flatrate_Term, Object> COLUMN_PlannedQtyPerUnit = new ModelColumn<>(I_C_Flatrate_Term.class, "PlannedQtyPerUnit", null);
	String COLUMNNAME_PlannedQtyPerUnit = "PlannedQtyPerUnit";

	/**
	 * Set Abschlusskorrektur vorbereiten.
	 * Prozess zum erstellen eines Abrechnungs-Korrektur-Datensatzes und/oder eines Abrechnungs-Verrechnungs-Datensatzes
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPrepareClosing (@Nullable java.lang.String PrepareClosing);

	/**
	 * Get Abschlusskorrektur vorbereiten.
	 * Prozess zum erstellen eines Abrechnungs-Korrektur-Datensatzes und/oder eines Abrechnungs-Verrechnungs-Datensatzes
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getPrepareClosing();

	ModelColumn<I_C_Flatrate_Term, Object> COLUMN_PrepareClosing = new ModelColumn<>(I_C_Flatrate_Term.class, "PrepareClosing", null);
	String COLUMNNAME_PrepareClosing = "PrepareClosing";

	/**
	 * Set Price Actual.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPriceActual (@Nullable BigDecimal PriceActual);

	/**
	 * Get Price Actual.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getPriceActual();

	ModelColumn<I_C_Flatrate_Term, Object> COLUMN_PriceActual = new ModelColumn<>(I_C_Flatrate_Term.class, "PriceActual", null);
	String COLUMNNAME_PriceActual = "PriceActual";

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

	ModelColumn<I_C_Flatrate_Term, Object> COLUMN_Processed = new ModelColumn<>(I_C_Flatrate_Term.class, "Processed", null);
	String COLUMNNAME_Processed = "Processed";

	/**
	 * Set Process Now.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setProcessing (boolean Processing);

	/**
	 * Get Process Now.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isProcessing();

	ModelColumn<I_C_Flatrate_Term, Object> COLUMN_Processing = new ModelColumn<>(I_C_Flatrate_Term.class, "Processing", null);
	String COLUMNNAME_Processing = "Processing";

	/**
	 * Set Anfangsdatum.
	 * First effective day (inclusive)
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setStartDate (java.sql.Timestamp StartDate);

	/**
	 * Get Anfangsdatum.
	 * First effective day (inclusive)
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getStartDate();

	ModelColumn<I_C_Flatrate_Term, Object> COLUMN_StartDate = new ModelColumn<>(I_C_Flatrate_Term.class, "StartDate", null);
	String COLUMNNAME_StartDate = "StartDate";

	/**
	 * Set Termination Date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setTerminationDate (@Nullable java.sql.Timestamp TerminationDate);

	/**
	 * Get Termination Date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getTerminationDate();

	ModelColumn<I_C_Flatrate_Term, Object> COLUMN_TerminationDate = new ModelColumn<>(I_C_Flatrate_Term.class, "TerminationDate", null);
	String COLUMNNAME_TerminationDate = "TerminationDate";

	/**
	 * Set Termination Memo.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setTerminationMemo (@Nullable java.lang.String TerminationMemo);

	/**
	 * Get Termination Memo.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getTerminationMemo();

	ModelColumn<I_C_Flatrate_Term, Object> COLUMN_TerminationMemo = new ModelColumn<>(I_C_Flatrate_Term.class, "TerminationMemo", null);
	String COLUMNNAME_TerminationMemo = "TerminationMemo";

	/**
	 * Set Termination Reason.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setTerminationReason (@Nullable java.lang.String TerminationReason);

	/**
	 * Get Termination Reason.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getTerminationReason();

	ModelColumn<I_C_Flatrate_Term, Object> COLUMN_TerminationReason = new ModelColumn<>(I_C_Flatrate_Term.class, "TerminationReason", null);
	String COLUMNNAME_TerminationReason = "TerminationReason";

	/**
	 * Set Vertragsart.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setType_Conditions (java.lang.String Type_Conditions);

	/**
	 * Get Vertragsart.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getType_Conditions();

	ModelColumn<I_C_Flatrate_Term, Object> COLUMN_Type_Conditions = new ModelColumn<>(I_C_Flatrate_Term.class, "Type_Conditions", null);
	String COLUMNNAME_Type_Conditions = "Type_Conditions";

	/**
	 * Set Verrechnungsart.
	 * Art der Verrechnung bei der Gegenüberstellung mit tatsächliche erbrachten Leistungen
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setType_Flatrate (@Nullable java.lang.String Type_Flatrate);

	/**
	 * Get Verrechnungsart.
	 * Art der Verrechnung bei der Gegenüberstellung mit tatsächliche erbrachten Leistungen
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	@Nullable java.lang.String getType_Flatrate();

	ModelColumn<I_C_Flatrate_Term, Object> COLUMN_Type_Flatrate = new ModelColumn<>(I_C_Flatrate_Term.class, "Type_Flatrate", null);
	String COLUMNNAME_Type_Flatrate = "Type_Flatrate";

	/**
	 * Set Einheiten-Typ.
	 * Dient der Zusammenfassung ähnlicher Maßeinheiten
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setUOMType (@Nullable java.lang.String UOMType);

	/**
	 * Get Einheiten-Typ.
	 * Dient der Zusammenfassung ähnlicher Maßeinheiten
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	@Nullable java.lang.String getUOMType();

	ModelColumn<I_C_Flatrate_Term, Object> COLUMN_UOMType = new ModelColumn<>(I_C_Flatrate_Term.class, "UOMType", null);
	String COLUMNNAME_UOMType = "UOMType";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_C_Flatrate_Term, Object> COLUMN_Updated = new ModelColumn<>(I_C_Flatrate_Term.class, "Updated", null);
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
