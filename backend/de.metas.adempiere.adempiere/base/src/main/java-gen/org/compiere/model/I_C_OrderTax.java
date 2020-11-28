package org.compiere.model;


/** Generated Interface for C_OrderTax
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_C_OrderTax 
{

    /** TableName=C_OrderTax */
    public static final String Table_Name = "C_OrderTax";

    /** AD_Table_ID=314 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 1 - Org
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(1);

    /** Load Meta Data */

	/**
	 * Get Mandant.
	 * Client/Tenant for this installation.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Client_ID();

	public org.compiere.model.I_AD_Client getAD_Client();

    /** Column definition for AD_Client_ID */
    public static final org.adempiere.model.ModelColumn<I_C_OrderTax, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_C_OrderTax, org.compiere.model.I_AD_Client>(I_C_OrderTax.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Org_ID();

	public org.compiere.model.I_AD_Org getAD_Org();

	public void setAD_Org(org.compiere.model.I_AD_Org AD_Org);

    /** Column definition for AD_Org_ID */
    public static final org.adempiere.model.ModelColumn<I_C_OrderTax, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_C_OrderTax, org.compiere.model.I_AD_Org>(I_C_OrderTax.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Auftrag.
	 * Order
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_Order_ID (int C_Order_ID);

	/**
	 * Get Auftrag.
	 * Order
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_Order_ID();

	public org.compiere.model.I_C_Order getC_Order();

	public void setC_Order(org.compiere.model.I_C_Order C_Order);

    /** Column definition for C_Order_ID */
    public static final org.adempiere.model.ModelColumn<I_C_OrderTax, org.compiere.model.I_C_Order> COLUMN_C_Order_ID = new org.adempiere.model.ModelColumn<I_C_OrderTax, org.compiere.model.I_C_Order>(I_C_OrderTax.class, "C_Order_ID", org.compiere.model.I_C_Order.class);
    /** Column name C_Order_ID */
    public static final String COLUMNNAME_C_Order_ID = "C_Order_ID";

	/**
	 * Set Order Tax.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_OrderTax_ID (int C_OrderTax_ID);

	/**
	 * Get Order Tax.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_OrderTax_ID();

    /** Column definition for C_OrderTax_ID */
    public static final org.adempiere.model.ModelColumn<I_C_OrderTax, Object> COLUMN_C_OrderTax_ID = new org.adempiere.model.ModelColumn<I_C_OrderTax, Object>(I_C_OrderTax.class, "C_OrderTax_ID", null);
    /** Column name C_OrderTax_ID */
    public static final String COLUMNNAME_C_OrderTax_ID = "C_OrderTax_ID";

	/**
	 * Set Steuer.
	 * Tax identifier
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_Tax_ID (int C_Tax_ID);

	/**
	 * Get Steuer.
	 * Tax identifier
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_Tax_ID();

	public org.compiere.model.I_C_Tax getC_Tax();

	public void setC_Tax(org.compiere.model.I_C_Tax C_Tax);

    /** Column definition for C_Tax_ID */
    public static final org.adempiere.model.ModelColumn<I_C_OrderTax, org.compiere.model.I_C_Tax> COLUMN_C_Tax_ID = new org.adempiere.model.ModelColumn<I_C_OrderTax, org.compiere.model.I_C_Tax>(I_C_OrderTax.class, "C_Tax_ID", org.compiere.model.I_C_Tax.class);
    /** Column name C_Tax_ID */
    public static final String COLUMNNAME_C_Tax_ID = "C_Tax_ID";

	/**
	 * Get Erstellt.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getCreated();

    /** Column definition for Created */
    public static final org.adempiere.model.ModelColumn<I_C_OrderTax, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_C_OrderTax, Object>(I_C_OrderTax.class, "Created", null);
    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/**
	 * Get Erstellt durch.
	 * User who created this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getCreatedBy();

    /** Column definition for CreatedBy */
    public static final org.adempiere.model.ModelColumn<I_C_OrderTax, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_C_OrderTax, org.compiere.model.I_AD_User>(I_C_OrderTax.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Aktiv.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsActive (boolean IsActive);

	/**
	 * Get Aktiv.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isActive();

    /** Column definition for IsActive */
    public static final org.adempiere.model.ModelColumn<I_C_OrderTax, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_C_OrderTax, Object>(I_C_OrderTax.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Packaging Tax.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsPackagingTax (boolean IsPackagingTax);

	/**
	 * Get Packaging Tax.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isPackagingTax();

    /** Column definition for IsPackagingTax */
    public static final org.adempiere.model.ModelColumn<I_C_OrderTax, Object> COLUMN_IsPackagingTax = new org.adempiere.model.ModelColumn<I_C_OrderTax, Object>(I_C_OrderTax.class, "IsPackagingTax", null);
    /** Column name IsPackagingTax */
    public static final String COLUMNNAME_IsPackagingTax = "IsPackagingTax";

	/**
	 * Set Preis inklusive Steuern.
	 * Tax is included in the price
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsTaxIncluded (boolean IsTaxIncluded);

	/**
	 * Get Preis inklusive Steuern.
	 * Tax is included in the price
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isTaxIncluded();

    /** Column definition for IsTaxIncluded */
    public static final org.adempiere.model.ModelColumn<I_C_OrderTax, Object> COLUMN_IsTaxIncluded = new org.adempiere.model.ModelColumn<I_C_OrderTax, Object>(I_C_OrderTax.class, "IsTaxIncluded", null);
    /** Column name IsTaxIncluded */
    public static final String COLUMNNAME_IsTaxIncluded = "IsTaxIncluded";

	/**
	 * Set Whole Tax.
	 * If this flag is set, in a tax aware document (e.g. Invoice, Order) this tax will absorb the whole amount, leaving 0 for base amount
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsWholeTax (boolean IsWholeTax);

	/**
	 * Get Whole Tax.
	 * If this flag is set, in a tax aware document (e.g. Invoice, Order) this tax will absorb the whole amount, leaving 0 for base amount
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isWholeTax();

    /** Column definition for IsWholeTax */
    public static final org.adempiere.model.ModelColumn<I_C_OrderTax, Object> COLUMN_IsWholeTax = new org.adempiere.model.ModelColumn<I_C_OrderTax, Object>(I_C_OrderTax.class, "IsWholeTax", null);
    /** Column name IsWholeTax */
    public static final String COLUMNNAME_IsWholeTax = "IsWholeTax";

	/**
	 * Set Verarbeitet.
	 * Checkbox sagt aus, ob der Beleg verarbeitet wurde.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setProcessed (boolean Processed);

	/**
	 * Get Verarbeitet.
	 * Checkbox sagt aus, ob der Beleg verarbeitet wurde.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isProcessed();

    /** Column definition for Processed */
    public static final org.adempiere.model.ModelColumn<I_C_OrderTax, Object> COLUMN_Processed = new org.adempiere.model.ModelColumn<I_C_OrderTax, Object>(I_C_OrderTax.class, "Processed", null);
    /** Column name Processed */
    public static final String COLUMNNAME_Processed = "Processed";

	/**
	 * Set Steuerbetrag.
	 * Tax Amount for a document
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setTaxAmt (java.math.BigDecimal TaxAmt);

	/**
	 * Get Steuerbetrag.
	 * Tax Amount for a document
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getTaxAmt();

    /** Column definition for TaxAmt */
    public static final org.adempiere.model.ModelColumn<I_C_OrderTax, Object> COLUMN_TaxAmt = new org.adempiere.model.ModelColumn<I_C_OrderTax, Object>(I_C_OrderTax.class, "TaxAmt", null);
    /** Column name TaxAmt */
    public static final String COLUMNNAME_TaxAmt = "TaxAmt";

	/**
	 * Set Bezugswert.
	 * Base for calculating the tax amount
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setTaxBaseAmt (java.math.BigDecimal TaxBaseAmt);

	/**
	 * Get Bezugswert.
	 * Base for calculating the tax amount
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getTaxBaseAmt();

    /** Column definition for TaxBaseAmt */
    public static final org.adempiere.model.ModelColumn<I_C_OrderTax, Object> COLUMN_TaxBaseAmt = new org.adempiere.model.ModelColumn<I_C_OrderTax, Object>(I_C_OrderTax.class, "TaxBaseAmt", null);
    /** Column name TaxBaseAmt */
    public static final String COLUMNNAME_TaxBaseAmt = "TaxBaseAmt";

	/**
	 * Get Aktualisiert.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getUpdated();

    /** Column definition for Updated */
    public static final org.adempiere.model.ModelColumn<I_C_OrderTax, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_C_OrderTax, Object>(I_C_OrderTax.class, "Updated", null);
    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Aktualisiert durch.
	 * User who updated this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getUpdatedBy();

    /** Column definition for UpdatedBy */
    public static final org.adempiere.model.ModelColumn<I_C_OrderTax, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_C_OrderTax, org.compiere.model.I_AD_User>(I_C_OrderTax.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
