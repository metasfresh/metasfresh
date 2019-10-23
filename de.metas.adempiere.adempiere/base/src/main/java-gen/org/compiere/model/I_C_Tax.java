/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2007 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software, you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY, without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program, if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package org.compiere.model;


/** Generated Interface for C_Tax
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_C_Tax 
{

    /** TableName=C_Tax */
    public static final String Table_Name = "C_Tax";

    /** AD_Table_ID=261 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 2 - Client 
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(2);

    /** Load Meta Data */

	/** Get Mandant.
	  * Client/Tenant for this installation.
	  */
	public int getAD_Client_ID();

	public org.compiere.model.I_AD_Client getAD_Client() throws RuntimeException;

    /** Column definition for AD_Client_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Tax, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_C_Tax, org.compiere.model.I_AD_Client>(I_C_Tax.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/** Set Sektion.
	  * Organisatorische Einheit des Mandanten
	  */
	public void setAD_Org_ID (int AD_Org_ID);

	/** Get Sektion.
	  * Organisatorische Einheit des Mandanten
	  */
	public int getAD_Org_ID();

	public org.compiere.model.I_AD_Org getAD_Org() throws RuntimeException;

	public void setAD_Org(org.compiere.model.I_AD_Org AD_Org);

    /** Column definition for AD_Org_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Tax, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_C_Tax, org.compiere.model.I_AD_Org>(I_C_Tax.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/** Set Regel	  */
	public void setAD_Rule_ID (int AD_Rule_ID);

	/** Get Regel	  */
	public int getAD_Rule_ID();

	public org.compiere.model.I_AD_Rule getAD_Rule() throws RuntimeException;

	public void setAD_Rule(org.compiere.model.I_AD_Rule AD_Rule);

    /** Column definition for AD_Rule_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Tax, org.compiere.model.I_AD_Rule> COLUMN_AD_Rule_ID = new org.adempiere.model.ModelColumn<I_C_Tax, org.compiere.model.I_AD_Rule>(I_C_Tax.class, "AD_Rule_ID", org.compiere.model.I_AD_Rule.class);
    /** Column name AD_Rule_ID */
    public static final String COLUMNNAME_AD_Rule_ID = "AD_Rule_ID";

	/** Set Land.
	  * Country 
	  */
	public void setC_Country_ID (int C_Country_ID);

	/** Get Land.
	  * Country 
	  */
	public int getC_Country_ID();

	public org.compiere.model.I_C_Country getC_Country() throws RuntimeException;

	public void setC_Country(org.compiere.model.I_C_Country C_Country);

    /** Column definition for C_Country_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Tax, org.compiere.model.I_C_Country> COLUMN_C_Country_ID = new org.adempiere.model.ModelColumn<I_C_Tax, org.compiere.model.I_C_Country>(I_C_Tax.class, "C_Country_ID", org.compiere.model.I_C_Country.class);
    /** Column name C_Country_ID */
    public static final String COLUMNNAME_C_Country_ID = "C_Country_ID";

	/** Set Region.
	  * Identifies a geographical Region
	  */
	public void setC_Region_ID (int C_Region_ID);

	/** Get Region.
	  * Identifies a geographical Region
	  */
	public int getC_Region_ID();

	public org.compiere.model.I_C_Region getC_Region() throws RuntimeException;

	public void setC_Region(org.compiere.model.I_C_Region C_Region);

    /** Column definition for C_Region_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Tax, org.compiere.model.I_C_Region> COLUMN_C_Region_ID = new org.adempiere.model.ModelColumn<I_C_Tax, org.compiere.model.I_C_Region>(I_C_Tax.class, "C_Region_ID", org.compiere.model.I_C_Region.class);
    /** Column name C_Region_ID */
    public static final String COLUMNNAME_C_Region_ID = "C_Region_ID";

	/** Set Steuer.
	  * Tax identifier
	  */
	public void setC_Tax_ID (int C_Tax_ID);

	/** Get Steuer.
	  * Tax identifier
	  */
	public int getC_Tax_ID();

    /** Column definition for C_Tax_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Tax, Object> COLUMN_C_Tax_ID = new org.adempiere.model.ModelColumn<I_C_Tax, Object>(I_C_Tax.class, "C_Tax_ID", null);
    /** Column name C_Tax_ID */
    public static final String COLUMNNAME_C_Tax_ID = "C_Tax_ID";

	/** Set Steuerkategorie.
	  * Tax Category
	  */
	public void setC_TaxCategory_ID (int C_TaxCategory_ID);

	/** Get Steuerkategorie.
	  * Tax Category
	  */
	public int getC_TaxCategory_ID();

	public org.compiere.model.I_C_TaxCategory getC_TaxCategory() throws RuntimeException;

	public void setC_TaxCategory(org.compiere.model.I_C_TaxCategory C_TaxCategory);

    /** Column definition for C_TaxCategory_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Tax, org.compiere.model.I_C_TaxCategory> COLUMN_C_TaxCategory_ID = new org.adempiere.model.ModelColumn<I_C_Tax, org.compiere.model.I_C_TaxCategory>(I_C_Tax.class, "C_TaxCategory_ID", org.compiere.model.I_C_TaxCategory.class);
    /** Column name C_TaxCategory_ID */
    public static final String COLUMNNAME_C_TaxCategory_ID = "C_TaxCategory_ID";

	/** Get Erstellt.
	  * Date this record was created
	  */
	public java.sql.Timestamp getCreated();

    /** Column definition for Created */
    public static final org.adempiere.model.ModelColumn<I_C_Tax, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_C_Tax, Object>(I_C_Tax.class, "Created", null);
    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/** Get Erstellt durch.
	  * User who created this records
	  */
	public int getCreatedBy();

    /** Column definition for CreatedBy */
    public static final org.adempiere.model.ModelColumn<I_C_Tax, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_C_Tax, org.compiere.model.I_AD_User>(I_C_Tax.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/** Set Beschreibung	  */
	public void setDescription (java.lang.String Description);

	/** Get Beschreibung	  */
	public java.lang.String getDescription();

    /** Column definition for Description */
    public static final org.adempiere.model.ModelColumn<I_C_Tax, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_C_Tax, Object>(I_C_Tax.class, "Description", null);
    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/** Set Steuer kopieren.
	  * Starte das kopieren des Steuersatzes
	  */
	public void setDuplicateTax (java.lang.String DuplicateTax);

	/** Get Steuer kopieren.
	  * Starte das kopieren des Steuersatzes
	  */
	public java.lang.String getDuplicateTax();

    /** Column definition for DuplicateTax */
    public static final org.adempiere.model.ModelColumn<I_C_Tax, Object> COLUMN_DuplicateTax = new org.adempiere.model.ModelColumn<I_C_Tax, Object>(I_C_Tax.class, "DuplicateTax", null);
    /** Column name DuplicateTax */
    public static final String COLUMNNAME_DuplicateTax = "DuplicateTax";

	/** Set Aktiv.
	  * The record is active in the system
	  */
	public void setIsActive (boolean IsActive);

	/** Get Aktiv.
	  * The record is active in the system
	  */
	public boolean isActive();

    /** Column definition for IsActive */
    public static final org.adempiere.model.ModelColumn<I_C_Tax, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_C_Tax, Object>(I_C_Tax.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/** Set Standard.
	  * Default value
	  */
	public void setIsDefault (boolean IsDefault);

	/** Get Standard.
	  * Default value
	  */
	public boolean isDefault();

    /** Column definition for IsDefault */
    public static final org.adempiere.model.ModelColumn<I_C_Tax, Object> COLUMN_IsDefault = new org.adempiere.model.ModelColumn<I_C_Tax, Object>(I_C_Tax.class, "IsDefault", null);
    /** Column name IsDefault */
    public static final String COLUMNNAME_IsDefault = "IsDefault";

	/** Set Dokumentbasiert.
	  * Steuer wird dokumentbasiert berechnet (abweichend wäre zeilenweise)
	  */
	public void setIsDocumentLevel (boolean IsDocumentLevel);

	/** Get Dokumentbasiert.
	  * Steuer wird dokumentbasiert berechnet (abweichend wäre zeilenweise)
	  */
	public boolean isDocumentLevel();

    /** Column definition for IsDocumentLevel */
    public static final org.adempiere.model.ModelColumn<I_C_Tax, Object> COLUMN_IsDocumentLevel = new org.adempiere.model.ModelColumn<I_C_Tax, Object>(I_C_Tax.class, "IsDocumentLevel", null);
    /** Column name IsDocumentLevel */
    public static final String COLUMNNAME_IsDocumentLevel = "IsDocumentLevel";

	/** Set VK Steuer.
	  * Dies ist eine VK Steuer
	  */
	public void setIsSalesTax (boolean IsSalesTax);

	/** Get VK Steuer.
	  * Dies ist eine VK Steuer
	  */
	public boolean isSalesTax();

    /** Column definition for IsSalesTax */
    public static final org.adempiere.model.ModelColumn<I_C_Tax, Object> COLUMN_IsSalesTax = new org.adempiere.model.ModelColumn<I_C_Tax, Object>(I_C_Tax.class, "IsSalesTax", null);
    /** Column name IsSalesTax */
    public static final String COLUMNNAME_IsSalesTax = "IsSalesTax";

	/** Set Zusammenfassungseintrag.
	  * This is a summary entity
	  */
	public void setIsSummary (boolean IsSummary);

	/** Get Zusammenfassungseintrag.
	  * This is a summary entity
	  */
	public boolean isSummary();

    /** Column definition for IsSummary */
    public static final org.adempiere.model.ModelColumn<I_C_Tax, Object> COLUMN_IsSummary = new org.adempiere.model.ModelColumn<I_C_Tax, Object>(I_C_Tax.class, "IsSummary", null);
    /** Column name IsSummary */
    public static final String COLUMNNAME_IsSummary = "IsSummary";

	/** Set steuerbefreit.
	  * Steuersatz steuerbefreit
	  */
	public void setIsTaxExempt (boolean IsTaxExempt);

	/** Get steuerbefreit.
	  * Steuersatz steuerbefreit
	  */
	public boolean isTaxExempt();

    /** Column definition for IsTaxExempt */
    public static final org.adempiere.model.ModelColumn<I_C_Tax, Object> COLUMN_IsTaxExempt = new org.adempiere.model.ModelColumn<I_C_Tax, Object>(I_C_Tax.class, "IsTaxExempt", null);
    /** Column name IsTaxExempt */
    public static final String COLUMNNAME_IsTaxExempt = "IsTaxExempt";

	/** Set Nach EU.
	  * Das Zielland befindet sich in der EU
	  */
	public void setIsToEULocation (boolean IsToEULocation);

	/** Get Nach EU.
	  * Das Zielland befindet sich in der EU
	  */
	public boolean isToEULocation();

    /** Column definition for IsToEULocation */
    public static final org.adempiere.model.ModelColumn<I_C_Tax, Object> COLUMN_IsToEULocation = new org.adempiere.model.ModelColumn<I_C_Tax, Object>(I_C_Tax.class, "IsToEULocation", null);
    /** Column name IsToEULocation */
    public static final String COLUMNNAME_IsToEULocation = "IsToEULocation";

	/** Set Whole Tax.
	  * If this flag is set, in a tax aware document (e.g. Invoice, Order) this tax will absorb the whole amount, leaving 0 for base amount
	  */
	public void setIsWholeTax (boolean IsWholeTax);

	/** Get Whole Tax.
	  * If this flag is set, in a tax aware document (e.g. Invoice, Order) this tax will absorb the whole amount, leaving 0 for base amount
	  */
	public boolean isWholeTax();

    /** Column definition for IsWholeTax */
    public static final org.adempiere.model.ModelColumn<I_C_Tax, Object> COLUMN_IsWholeTax = new org.adempiere.model.ModelColumn<I_C_Tax, Object>(I_C_Tax.class, "IsWholeTax", null);
    /** Column name IsWholeTax */
    public static final String COLUMNNAME_IsWholeTax = "IsWholeTax";

	/** Set Name.
	  * Alphanumeric identifier of the entity
	  */
	public void setName (java.lang.String Name);

	/** Get Name.
	  * Alphanumeric identifier of the entity
	  */
	public java.lang.String getName();

    /** Column definition for Name */
    public static final org.adempiere.model.ModelColumn<I_C_Tax, Object> COLUMN_Name = new org.adempiere.model.ModelColumn<I_C_Tax, Object>(I_C_Tax.class, "Name", null);
    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

	/** Set Übergeordnete Steuer.
	  * Setzt sich die Steuer aus mehreren Steuersätzen zusammen, wird dies mit übergeordneten Steuersätzen definiert.
	  */
	public void setParent_Tax_ID (int Parent_Tax_ID);

	/** Get Übergeordnete Steuer.
	  * Setzt sich die Steuer aus mehreren Steuersätzen zusammen, wird dies mit übergeordneten Steuersätzen definiert.
	  */
	public int getParent_Tax_ID();

	public org.compiere.model.I_C_Tax getParent_Tax() throws RuntimeException;

	public void setParent_Tax(org.compiere.model.I_C_Tax Parent_Tax);

    /** Column definition for Parent_Tax_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Tax, org.compiere.model.I_C_Tax> COLUMN_Parent_Tax_ID = new org.adempiere.model.ModelColumn<I_C_Tax, org.compiere.model.I_C_Tax>(I_C_Tax.class, "Parent_Tax_ID", org.compiere.model.I_C_Tax.class);
    /** Column name Parent_Tax_ID */
    public static final String COLUMNNAME_Parent_Tax_ID = "Parent_Tax_ID";

	/** Set Satz.
	  * Rate or Tax or Exchange
	  */
	public void setRate (java.math.BigDecimal Rate);

	/** Get Satz.
	  * Rate or Tax or Exchange
	  */
	public java.math.BigDecimal getRate();

    /** Column definition for Rate */
    public static final org.adempiere.model.ModelColumn<I_C_Tax, Object> COLUMN_Rate = new org.adempiere.model.ModelColumn<I_C_Tax, Object>(I_C_Tax.class, "Rate", null);
    /** Column name Rate */
    public static final String COLUMNNAME_Rate = "Rate";

	/** Set erfordert Steuer-ID.
	  * Dieser Steuersatz erfordert eine Steuer-ID beim Geschäftspartner,.
	  */
	public void setRequiresTaxCertificate (boolean RequiresTaxCertificate);

	/** Get erfordert Steuer-ID.
	  * Dieser Steuersatz erfordert eine Steuer-ID beim Geschäftspartner,.
	  */
	public boolean isRequiresTaxCertificate();

    /** Column definition for RequiresTaxCertificate */
    public static final org.adempiere.model.ModelColumn<I_C_Tax, Object> COLUMN_RequiresTaxCertificate = new org.adempiere.model.ModelColumn<I_C_Tax, Object>(I_C_Tax.class, "RequiresTaxCertificate", null);
    /** Column name RequiresTaxCertificate */
    public static final String COLUMNNAME_RequiresTaxCertificate = "RequiresTaxCertificate";

	/** Set VK/ EK Typ.
	  * Steuer für Einkauf und/ oder Verkauf Transaktionen.
	  */
	public void setSOPOType (java.lang.String SOPOType);

	/** Get VK/ EK Typ.
	  * Steuer für Einkauf und/ oder Verkauf Transaktionen.
	  */
	public java.lang.String getSOPOType();

    /** Column definition for SOPOType */
    public static final org.adempiere.model.ModelColumn<I_C_Tax, Object> COLUMN_SOPOType = new org.adempiere.model.ModelColumn<I_C_Tax, Object>(I_C_Tax.class, "SOPOType", null);
    /** Column name SOPOType */
    public static final String COLUMNNAME_SOPOType = "SOPOType";

	/** Set Steuer-Indikator.
	  * Short form for Tax to be printed on documents
	  */
	public void setTaxIndicator (java.lang.String TaxIndicator);

	/** Get Steuer-Indikator.
	  * Short form for Tax to be printed on documents
	  */
	public java.lang.String getTaxIndicator();

    /** Column definition for TaxIndicator */
    public static final org.adempiere.model.ModelColumn<I_C_Tax, Object> COLUMN_TaxIndicator = new org.adempiere.model.ModelColumn<I_C_Tax, Object>(I_C_Tax.class, "TaxIndicator", null);
    /** Column name TaxIndicator */
    public static final String COLUMNNAME_TaxIndicator = "TaxIndicator";

	/** Set An.
	  * Receiving Country
	  */
	public void setTo_Country_ID (int To_Country_ID);

	/** Get An.
	  * Receiving Country
	  */
	public int getTo_Country_ID();

	public org.compiere.model.I_C_Country getTo_Country() throws RuntimeException;

	public void setTo_Country(org.compiere.model.I_C_Country To_Country);

    /** Column definition for To_Country_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Tax, org.compiere.model.I_C_Country> COLUMN_To_Country_ID = new org.adempiere.model.ModelColumn<I_C_Tax, org.compiere.model.I_C_Country>(I_C_Tax.class, "To_Country_ID", org.compiere.model.I_C_Country.class);
    /** Column name To_Country_ID */
    public static final String COLUMNNAME_To_Country_ID = "To_Country_ID";

	/** Set An.
	  * Receiving Region
	  */
	public void setTo_Region_ID (int To_Region_ID);

	/** Get An.
	  * Receiving Region
	  */
	public int getTo_Region_ID();

	public org.compiere.model.I_C_Region getTo_Region() throws RuntimeException;

	public void setTo_Region(org.compiere.model.I_C_Region To_Region);

    /** Column definition for To_Region_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Tax, org.compiere.model.I_C_Region> COLUMN_To_Region_ID = new org.adempiere.model.ModelColumn<I_C_Tax, org.compiere.model.I_C_Region>(I_C_Tax.class, "To_Region_ID", org.compiere.model.I_C_Region.class);
    /** Column name To_Region_ID */
    public static final String COLUMNNAME_To_Region_ID = "To_Region_ID";

	/** Get Aktualisiert.
	  * Date this record was updated
	  */
	public java.sql.Timestamp getUpdated();

    /** Column definition for Updated */
    public static final org.adempiere.model.ModelColumn<I_C_Tax, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_C_Tax, Object>(I_C_Tax.class, "Updated", null);
    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/** Get Aktualisiert durch.
	  * User who updated this records
	  */
	public int getUpdatedBy();

    /** Column definition for UpdatedBy */
    public static final org.adempiere.model.ModelColumn<I_C_Tax, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_C_Tax, org.compiere.model.I_AD_User>(I_C_Tax.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/** Set Gültig ab.
	  * Valid from including this date (first day)
	  */
	public void setValidFrom (java.sql.Timestamp ValidFrom);

	/** Get Gültig ab.
	  * Valid from including this date (first day)
	  */
	public java.sql.Timestamp getValidFrom();

    /** Column definition for ValidFrom */
    public static final org.adempiere.model.ModelColumn<I_C_Tax, Object> COLUMN_ValidFrom = new org.adempiere.model.ModelColumn<I_C_Tax, Object>(I_C_Tax.class, "ValidFrom", null);
    /** Column name ValidFrom */
    public static final String COLUMNNAME_ValidFrom = "ValidFrom";
}
