package org.compiere.model;

import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for C_BP_Relation
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_BP_Relation 
{

	String Table_Name = "C_BP_Relation";

//	/** AD_Table_ID=678 */
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
	 * Set Business Partner.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_BPartner_ID (int C_BPartner_ID);

	/**
	 * Get Business Partner.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_BPartner_ID();

	String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/**
	 * Set Location.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_BPartner_Location_ID (int C_BPartner_Location_ID);

	/**
	 * Get Location.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_BPartner_Location_ID();

	String COLUMNNAME_C_BPartner_Location_ID = "C_BPartner_Location_ID";

	/**
	 * Set Zugehöriger Geschäftspartner.
	 * Related Business Partner
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_BPartnerRelation_ID (int C_BPartnerRelation_ID);

	/**
	 * Get Zugehöriger Geschäftspartner.
	 * Related Business Partner
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_BPartnerRelation_ID();

	String COLUMNNAME_C_BPartnerRelation_ID = "C_BPartnerRelation_ID";

	/**
	 * Set Zugehöriger Standort.
	 * Location of the related Business Partner
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_BPartnerRelation_Location_ID (int C_BPartnerRelation_Location_ID);

	/**
	 * Get Zugehöriger Standort.
	 * Location of the related Business Partner
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_BPartnerRelation_Location_ID();

	String COLUMNNAME_C_BPartnerRelation_Location_ID = "C_BPartnerRelation_Location_ID";

	/**
	 * Set Beziehungen Geschäftspartner.
	 * Business Partner Relation
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_BP_Relation_ID (int C_BP_Relation_ID);

	/**
	 * Get Beziehungen Geschäftspartner.
	 * Business Partner Relation
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_BP_Relation_ID();

	ModelColumn<I_C_BP_Relation, Object> COLUMN_C_BP_Relation_ID = new ModelColumn<>(I_C_BP_Relation.class, "C_BP_Relation_ID", null);
	String COLUMNNAME_C_BP_Relation_ID = "C_BP_Relation_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_C_BP_Relation, Object> COLUMN_Created = new ModelColumn<>(I_C_BP_Relation.class, "Created", null);
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

	ModelColumn<I_C_BP_Relation, Object> COLUMN_Description = new ModelColumn<>(I_C_BP_Relation.class, "Description", null);
	String COLUMNNAME_Description = "Description";

	/**
	 * Set External ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setExternalId (@Nullable java.lang.String ExternalId);

	/**
	 * Get External ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getExternalId();

	ModelColumn<I_C_BP_Relation, Object> COLUMN_ExternalId = new ModelColumn<>(I_C_BP_Relation.class, "ExternalId", null);
	String COLUMNNAME_ExternalId = "ExternalId";

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

	ModelColumn<I_C_BP_Relation, Object> COLUMN_IsActive = new ModelColumn<>(I_C_BP_Relation.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Vorbelegung Rechnung.
	 * Rechnungs-Adresse für diesen Geschäftspartner
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsBillTo (boolean IsBillTo);

	/**
	 * Get Vorbelegung Rechnung.
	 * Rechnungs-Adresse für diesen Geschäftspartner
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isBillTo();

	ModelColumn<I_C_BP_Relation, Object> COLUMN_IsBillTo = new ModelColumn<>(I_C_BP_Relation.class, "IsBillTo", null);
	String COLUMNNAME_IsBillTo = "IsBillTo";

	/**
	 * Set Pickup Location.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsFetchedFrom (boolean IsFetchedFrom);

	/**
	 * Get Pickup Location.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isFetchedFrom();

	ModelColumn<I_C_BP_Relation, Object> COLUMN_IsFetchedFrom = new ModelColumn<>(I_C_BP_Relation.class, "IsFetchedFrom", null);
	String COLUMNNAME_IsFetchedFrom = "IsFetchedFrom";

	/**
	 * Set Zahlungs-Adresse.
	 * Business Partner pays from that address and we'll send dunning letters there
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsPayFrom (boolean IsPayFrom);

	/**
	 * Get Zahlungs-Adresse.
	 * Business Partner pays from that address and we'll send dunning letters there
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isPayFrom();

	ModelColumn<I_C_BP_Relation, Object> COLUMN_IsPayFrom = new ModelColumn<>(I_C_BP_Relation.class, "IsPayFrom", null);
	String COLUMNNAME_IsPayFrom = "IsPayFrom";

	/**
	 * Set Erstattungs-Adresse.
	 * Business Partner payment address
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsRemitTo (boolean IsRemitTo);

	/**
	 * Get Erstattungs-Adresse.
	 * Business Partner payment address
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isRemitTo();

	ModelColumn<I_C_BP_Relation, Object> COLUMN_IsRemitTo = new ModelColumn<>(I_C_BP_Relation.class, "IsRemitTo", null);
	String COLUMNNAME_IsRemitTo = "IsRemitTo";

	/**
	 * Set Lieferstandard.
	 * Liefer-Adresse für den Geschäftspartner
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsShipTo (boolean IsShipTo);

	/**
	 * Get Lieferstandard.
	 * Liefer-Adresse für den Geschäftspartner
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isShipTo();

	ModelColumn<I_C_BP_Relation, Object> COLUMN_IsShipTo = new ModelColumn<>(I_C_BP_Relation.class, "IsShipTo", null);
	String COLUMNNAME_IsShipTo = "IsShipTo";

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

	ModelColumn<I_C_BP_Relation, Object> COLUMN_Name = new ModelColumn<>(I_C_BP_Relation.class, "Name", null);
	String COLUMNNAME_Name = "Name";

	/**
	 * Set Role.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setRole (@Nullable java.lang.String Role);

	/**
	 * Get Role.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getRole();

	ModelColumn<I_C_BP_Relation, Object> COLUMN_Role = new ModelColumn<>(I_C_BP_Relation.class, "Role", null);
	String COLUMNNAME_Role = "Role";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_C_BP_Relation, Object> COLUMN_Updated = new ModelColumn<>(I_C_BP_Relation.class, "Updated", null);
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
