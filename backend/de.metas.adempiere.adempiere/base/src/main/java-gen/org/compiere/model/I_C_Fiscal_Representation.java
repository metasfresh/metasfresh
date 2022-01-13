package org.compiere.model;

import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for C_Fiscal_Representation
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_Fiscal_Representation 
{

	String Table_Name = "C_Fiscal_Representation";

//	/** AD_Table_ID=541641 */
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
	 * Set Represented by.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_BPartner_Representative_ID (int C_BPartner_Representative_ID);

	/**
	 * Get Represented by.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_BPartner_Representative_ID();

	String COLUMNNAME_C_BPartner_Representative_ID = "C_BPartner_Representative_ID";

	/**
	 * Set Fiscal Representation.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Fiscal_Representation_ID (int C_Fiscal_Representation_ID);

	/**
	 * Get Fiscal Representation.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Fiscal_Representation_ID();

	ModelColumn<I_C_Fiscal_Representation, Object> COLUMN_C_Fiscal_Representation_ID = new ModelColumn<>(I_C_Fiscal_Representation.class, "C_Fiscal_Representation_ID", null);
	String COLUMNNAME_C_Fiscal_Representation_ID = "C_Fiscal_Representation_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_C_Fiscal_Representation, Object> COLUMN_Created = new ModelColumn<>(I_C_Fiscal_Representation.class, "Created", null);
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

	ModelColumn<I_C_Fiscal_Representation, Object> COLUMN_IsActive = new ModelColumn<>(I_C_Fiscal_Representation.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Tax ID.
	 * Tax Identification
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setTaxID (@Nullable java.lang.String TaxID);

	/**
	 * Get Tax ID.
	 * Tax Identification
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getTaxID();

	ModelColumn<I_C_Fiscal_Representation, Object> COLUMN_TaxID = new ModelColumn<>(I_C_Fiscal_Representation.class, "TaxID", null);
	String COLUMNNAME_TaxID = "TaxID";

	/**
	 * Set To.
	 * Receiving Country
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setTo_Country_ID (int To_Country_ID);

	/**
	 * Get To.
	 * Receiving Country
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getTo_Country_ID();

	org.compiere.model.I_C_Country getTo_Country();

	void setTo_Country(org.compiere.model.I_C_Country To_Country);

	ModelColumn<I_C_Fiscal_Representation, org.compiere.model.I_C_Country> COLUMN_To_Country_ID = new ModelColumn<>(I_C_Fiscal_Representation.class, "To_Country_ID", org.compiere.model.I_C_Country.class);
	String COLUMNNAME_To_Country_ID = "To_Country_ID";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_C_Fiscal_Representation, Object> COLUMN_Updated = new ModelColumn<>(I_C_Fiscal_Representation.class, "Updated", null);
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
	 * Set Valid From.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setValidFrom (java.sql.Timestamp ValidFrom);

	/**
	 * Get Valid From.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getValidFrom();

	ModelColumn<I_C_Fiscal_Representation, Object> COLUMN_ValidFrom = new ModelColumn<>(I_C_Fiscal_Representation.class, "ValidFrom", null);
	String COLUMNNAME_ValidFrom = "ValidFrom";

	/**
	 * Set Valid to.
	 * Valid to including this date (last day)
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setValidTo (@Nullable java.sql.Timestamp ValidTo);

	/**
	 * Get Valid to.
	 * Valid to including this date (last day)
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getValidTo();

	ModelColumn<I_C_Fiscal_Representation, Object> COLUMN_ValidTo = new ModelColumn<>(I_C_Fiscal_Representation.class, "ValidTo", null);
	String COLUMNNAME_ValidTo = "ValidTo";

	/**
	 * Set VAT ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setVATaxID (java.lang.String VATaxID);

	/**
	 * Get VAT ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getVATaxID();

	ModelColumn<I_C_Fiscal_Representation, Object> COLUMN_VATaxID = new ModelColumn<>(I_C_Fiscal_Representation.class, "VATaxID", null);
	String COLUMNNAME_VATaxID = "VATaxID";
}
