package org.compiere.model;

import java.math.BigDecimal;
import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for C_Tax
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_Tax 
{

	String Table_Name = "C_Tax";

//	/** AD_Table_ID=261 */
//	int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);


	/**
	 * Set Boiler Plate.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_BoilerPlate_ID (int AD_BoilerPlate_ID);

	/**
	 * Get Boiler Plate.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_BoilerPlate_ID();

	ModelColumn<I_C_Tax, Object> COLUMN_AD_BoilerPlate_ID = new ModelColumn<>(I_C_Tax.class, "AD_BoilerPlate_ID", null);
	String COLUMNNAME_AD_BoilerPlate_ID = "AD_BoilerPlate_ID";

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
	 * Set Regel.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_Rule_ID (int AD_Rule_ID);

	/**
	 * Get Regel.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_Rule_ID();

	@Nullable org.compiere.model.I_AD_Rule getAD_Rule();

	void setAD_Rule(@Nullable org.compiere.model.I_AD_Rule AD_Rule);

	ModelColumn<I_C_Tax, org.compiere.model.I_AD_Rule> COLUMN_AD_Rule_ID = new ModelColumn<>(I_C_Tax.class, "AD_Rule_ID", org.compiere.model.I_AD_Rule.class);
	String COLUMNNAME_AD_Rule_ID = "AD_Rule_ID";

	/**
	 * Set Country.
	 * Country
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Country_ID (int C_Country_ID);

	/**
	 * Get Country.
	 * Country
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Country_ID();

	@Nullable org.compiere.model.I_C_Country getC_Country();

	void setC_Country(@Nullable org.compiere.model.I_C_Country C_Country);

	ModelColumn<I_C_Tax, org.compiere.model.I_C_Country> COLUMN_C_Country_ID = new ModelColumn<>(I_C_Tax.class, "C_Country_ID", org.compiere.model.I_C_Country.class);
	String COLUMNNAME_C_Country_ID = "C_Country_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_C_Tax, Object> COLUMN_Created = new ModelColumn<>(I_C_Tax.class, "Created", null);
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
	 * Set Region.
	 * Identifies a geographical Region
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Region_ID (int C_Region_ID);

	/**
	 * Get Region.
	 * Identifies a geographical Region
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Region_ID();

	@Nullable org.compiere.model.I_C_Region getC_Region();

	void setC_Region(@Nullable org.compiere.model.I_C_Region C_Region);

	ModelColumn<I_C_Tax, org.compiere.model.I_C_Region> COLUMN_C_Region_ID = new ModelColumn<>(I_C_Tax.class, "C_Region_ID", org.compiere.model.I_C_Region.class);
	String COLUMNNAME_C_Region_ID = "C_Region_ID";

	/**
	 * Set Tax Category.
	 * Tax Category
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_TaxCategory_ID (int C_TaxCategory_ID);

	/**
	 * Get Tax Category.
	 * Tax Category
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_TaxCategory_ID();

	String COLUMNNAME_C_TaxCategory_ID = "C_TaxCategory_ID";

	/**
	 * Set Tax.
	 * Tax identifier
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Tax_ID (int C_Tax_ID);

	/**
	 * Get Tax.
	 * Tax identifier
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Tax_ID();

	ModelColumn<I_C_Tax, Object> COLUMN_C_Tax_ID = new ModelColumn<>(I_C_Tax.class, "C_Tax_ID", null);
	String COLUMNNAME_C_Tax_ID = "C_Tax_ID";

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

	ModelColumn<I_C_Tax, Object> COLUMN_Description = new ModelColumn<>(I_C_Tax.class, "Description", null);
	String COLUMNNAME_Description = "Description";

	/**
	 * Set Steuer kopieren.
	 * Starte das kopieren des Steuersatzes
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDuplicateTax (@Nullable java.lang.String DuplicateTax);

	/**
	 * Get Steuer kopieren.
	 * Starte das kopieren des Steuersatzes
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDuplicateTax();

	ModelColumn<I_C_Tax, Object> COLUMN_DuplicateTax = new ModelColumn<>(I_C_Tax.class, "DuplicateTax", null);
	String COLUMNNAME_DuplicateTax = "DuplicateTax";

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

	ModelColumn<I_C_Tax, Object> COLUMN_IsActive = new ModelColumn<>(I_C_Tax.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Default.
	 * Default value
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsDefault (boolean IsDefault);

	/**
	 * Get Default.
	 * Default value
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isDefault();

	ModelColumn<I_C_Tax, Object> COLUMN_IsDefault = new ModelColumn<>(I_C_Tax.class, "IsDefault", null);
	String COLUMNNAME_IsDefault = "IsDefault";

	/**
	 * Set Dokumentbasiert.
	 * Steuer wird dokumentbasiert berechnet (abweichend wäre zeilenweise)
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsDocumentLevel (boolean IsDocumentLevel);

	/**
	 * Get Dokumentbasiert.
	 * Steuer wird dokumentbasiert berechnet (abweichend wäre zeilenweise)
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isDocumentLevel();

	ModelColumn<I_C_Tax, Object> COLUMN_IsDocumentLevel = new ModelColumn<>(I_C_Tax.class, "IsDocumentLevel", null);
	String COLUMNNAME_IsDocumentLevel = "IsDocumentLevel";

	/**
	 * Set Fiscal representation.
	 * Matches only if the respective org has a fiscal representation in the destination country.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIsFiscalRepresentation (@Nullable java.lang.String IsFiscalRepresentation);

	/**
	 * Get Fiscal representation.
	 * Matches only if the respective org has a fiscal representation in the destination country.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getIsFiscalRepresentation();

	ModelColumn<I_C_Tax, Object> COLUMN_IsFiscalRepresentation = new ModelColumn<>(I_C_Tax.class, "IsFiscalRepresentation", null);
	String COLUMNNAME_IsFiscalRepresentation = "IsFiscalRepresentation";

	/**
	 * Set VK Steuer.
	 * Dies ist eine VK Steuer
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsSalesTax (boolean IsSalesTax);

	/**
	 * Get VK Steuer.
	 * Dies ist eine VK Steuer
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isSalesTax();

	ModelColumn<I_C_Tax, Object> COLUMN_IsSalesTax = new ModelColumn<>(I_C_Tax.class, "IsSalesTax", null);
	String COLUMNNAME_IsSalesTax = "IsSalesTax";

	/**
	 * Set Small business.
	 * If set to "yes", then the respective business partner needs to have a small business tax exemption in order for the tax record to match. If set to "No", then there may be no such exception.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIsSmallbusiness (@Nullable java.lang.String IsSmallbusiness);

	/**
	 * Get Small business.
	 * If set to "yes", then the respective business partner needs to have a small business tax exemption in order for the tax record to match. If set to "No", then there may be no such exception.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getIsSmallbusiness();

	ModelColumn<I_C_Tax, Object> COLUMN_IsSmallbusiness = new ModelColumn<>(I_C_Tax.class, "IsSmallbusiness", null);
	String COLUMNNAME_IsSmallbusiness = "IsSmallbusiness";

	/**
	 * Set Zusammenfassungseintrag.
	 * This is a summary entity
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsSummary (boolean IsSummary);

	/**
	 * Get Zusammenfassungseintrag.
	 * This is a summary entity
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isSummary();

	ModelColumn<I_C_Tax, Object> COLUMN_IsSummary = new ModelColumn<>(I_C_Tax.class, "IsSummary", null);
	String COLUMNNAME_IsSummary = "IsSummary";

	/**
	 * Set steuerbefreit.
	 * Steuersatz steuerbefreit
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsTaxExempt (boolean IsTaxExempt);

	/**
	 * Get steuerbefreit.
	 * Steuersatz steuerbefreit
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isTaxExempt();

	ModelColumn<I_C_Tax, Object> COLUMN_IsTaxExempt = new ModelColumn<>(I_C_Tax.class, "IsTaxExempt", null);
	String COLUMNNAME_IsTaxExempt = "IsTaxExempt";

	/**
	 * Set Whole Tax.
	 * If the flag is set, this tax can be used in documents where an entire line amount is a tax amount. Used, e.g., when a tax charge needs to be paid to a customs office.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsWholeTax (boolean IsWholeTax);

	/**
	 * Get Whole Tax.
	 * If the flag is set, this tax can be used in documents where an entire line amount is a tax amount. Used, e.g., when a tax charge needs to be paid to a customs office.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isWholeTax();

	ModelColumn<I_C_Tax, Object> COLUMN_IsWholeTax = new ModelColumn<>(I_C_Tax.class, "IsWholeTax", null);
	String COLUMNNAME_IsWholeTax = "IsWholeTax";

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

	ModelColumn<I_C_Tax, Object> COLUMN_Name = new ModelColumn<>(I_C_Tax.class, "Name", null);
	String COLUMNNAME_Name = "Name";

	/**
	 * Set Übergeordnete Steuer.
	 * Setzt sich die Steuer aus mehreren Steuersätzen zusammen, wird dies mit übergeordneten Steuersätzen definiert.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setParent_Tax_ID (int Parent_Tax_ID);

	/**
	 * Get Übergeordnete Steuer.
	 * Setzt sich die Steuer aus mehreren Steuersätzen zusammen, wird dies mit übergeordneten Steuersätzen definiert.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getParent_Tax_ID();

	String COLUMNNAME_Parent_Tax_ID = "Parent_Tax_ID";

	/**
	 * Set Satz.
	 * Rate or Tax or Exchange
	 *
	 * <br>Type: Number
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setRate (BigDecimal Rate);

	/**
	 * Get Satz.
	 * Rate or Tax or Exchange
	 *
	 * <br>Type: Number
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getRate();

	ModelColumn<I_C_Tax, Object> COLUMN_Rate = new ModelColumn<>(I_C_Tax.class, "Rate", null);
	String COLUMNNAME_Rate = "Rate";

	/**
	 * Set erfordert Steuer-ID.
	 * Dieser Steuersatz erfordert eine Steuer-ID beim Geschäftspartner,.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setRequiresTaxCertificate (java.lang.String RequiresTaxCertificate);

	/**
	 * Get erfordert Steuer-ID.
	 * Dieser Steuersatz erfordert eine Steuer-ID beim Geschäftspartner,.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getRequiresTaxCertificate();

	ModelColumn<I_C_Tax, Object> COLUMN_RequiresTaxCertificate = new ModelColumn<>(I_C_Tax.class, "RequiresTaxCertificate", null);
	String COLUMNNAME_RequiresTaxCertificate = "RequiresTaxCertificate";

	/**
	 * Set SeqNo.
	 * Method of ordering records;
 lowest number comes first
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setSeqNo (int SeqNo);

	/**
	 * Get SeqNo.
	 * Method of ordering records;
 lowest number comes first
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getSeqNo();

	ModelColumn<I_C_Tax, Object> COLUMN_SeqNo = new ModelColumn<>(I_C_Tax.class, "SeqNo", null);
	String COLUMNNAME_SeqNo = "SeqNo";

	/**
	 * Set VK/ EK Typ.
	 * Steuer für Einkauf und/ oder Verkauf Transaktionen.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setSOPOType (java.lang.String SOPOType);

	/**
	 * Get VK/ EK Typ.
	 * Steuer für Einkauf und/ oder Verkauf Transaktionen.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getSOPOType();

	ModelColumn<I_C_Tax, Object> COLUMN_SOPOType = new ModelColumn<>(I_C_Tax.class, "SOPOType", null);
	String COLUMNNAME_SOPOType = "SOPOType";

	/**
	 * Set Steuer-Indikator.
	 * Short form for Tax to be printed on documents
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setTaxIndicator (@Nullable java.lang.String TaxIndicator);

	/**
	 * Get Steuer-Indikator.
	 * Short form for Tax to be printed on documents
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getTaxIndicator();

	ModelColumn<I_C_Tax, Object> COLUMN_TaxIndicator = new ModelColumn<>(I_C_Tax.class, "TaxIndicator", null);
	String COLUMNNAME_TaxIndicator = "TaxIndicator";

	/**
	 * Set To.
	 * The business partner's country. Usually this is the country in which the service is provided or to which the goods are delivered.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setTo_Country_ID (int To_Country_ID);

	/**
	 * Get To.
	 * The business partner's country. Usually this is the country in which the service is provided or to which the goods are delivered.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getTo_Country_ID();

	@Nullable org.compiere.model.I_C_Country getTo_Country();

	void setTo_Country(@Nullable org.compiere.model.I_C_Country To_Country);

	ModelColumn<I_C_Tax, org.compiere.model.I_C_Country> COLUMN_To_Country_ID = new ModelColumn<>(I_C_Tax.class, "To_Country_ID", org.compiere.model.I_C_Country.class);
	String COLUMNNAME_To_Country_ID = "To_Country_ID";

	/**
	 * Set An.
	 * Receiving Region
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setTo_Region_ID (int To_Region_ID);

	/**
	 * Get An.
	 * Receiving Region
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getTo_Region_ID();

	@Nullable org.compiere.model.I_C_Region getTo_Region();

	void setTo_Region(@Nullable org.compiere.model.I_C_Region To_Region);

	ModelColumn<I_C_Tax, org.compiere.model.I_C_Region> COLUMN_To_Region_ID = new ModelColumn<>(I_C_Tax.class, "To_Region_ID", org.compiere.model.I_C_Region.class);
	String COLUMNNAME_To_Region_ID = "To_Region_ID";

	/**
	 * Set Type dest. country.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setTypeOfDestCountry (@Nullable java.lang.String TypeOfDestCountry);

	/**
	 * Get Type dest. country.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getTypeOfDestCountry();

	ModelColumn<I_C_Tax, Object> COLUMN_TypeOfDestCountry = new ModelColumn<>(I_C_Tax.class, "TypeOfDestCountry", null);
	String COLUMNNAME_TypeOfDestCountry = "TypeOfDestCountry";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_C_Tax, Object> COLUMN_Updated = new ModelColumn<>(I_C_Tax.class, "Updated", null);
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

	ModelColumn<I_C_Tax, Object> COLUMN_ValidFrom = new ModelColumn<>(I_C_Tax.class, "ValidFrom", null);
	String COLUMNNAME_ValidFrom = "ValidFrom";

	/**
	 * Set Valid to.
	 * Valid to including this date (last day)
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setValidTo (java.sql.Timestamp ValidTo);

	/**
	 * Get Valid to.
	 * Valid to including this date (last day)
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getValidTo();

	ModelColumn<I_C_Tax, Object> COLUMN_ValidTo = new ModelColumn<>(I_C_Tax.class, "ValidTo", null);
	String COLUMNNAME_ValidTo = "ValidTo";
}
