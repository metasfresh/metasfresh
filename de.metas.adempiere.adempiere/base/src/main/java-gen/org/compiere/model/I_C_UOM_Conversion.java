package org.compiere.model;


/** Generated Interface for C_UOM_Conversion
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_C_UOM_Conversion 
{

    /** TableName=C_UOM_Conversion */
    public static final String Table_Name = "C_UOM_Conversion";

    /** AD_Table_ID=175 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 6 - System - Client
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(6);

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

    /** Column definition for AD_Client_ID */
    public static final org.adempiere.model.ModelColumn<I_C_UOM_Conversion, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_C_UOM_Conversion, org.compiere.model.I_AD_Client>(I_C_UOM_Conversion.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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

    /** Column definition for AD_Org_ID */
    public static final org.adempiere.model.ModelColumn<I_C_UOM_Conversion, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_C_UOM_Conversion, org.compiere.model.I_AD_Org>(I_C_UOM_Conversion.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

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
    public static final org.adempiere.model.ModelColumn<I_C_UOM_Conversion, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_C_UOM_Conversion, Object>(I_C_UOM_Conversion.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_UOM_Conversion, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_C_UOM_Conversion, org.compiere.model.I_AD_User>(I_C_UOM_Conversion.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Umrechnung Maßeinheit.
	 * Unit of Measure Conversion
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_UOM_Conversion_ID (int C_UOM_Conversion_ID);

	/**
	 * Get Umrechnung Maßeinheit.
	 * Unit of Measure Conversion
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_UOM_Conversion_ID();

    /** Column definition for C_UOM_Conversion_ID */
    public static final org.adempiere.model.ModelColumn<I_C_UOM_Conversion, Object> COLUMN_C_UOM_Conversion_ID = new org.adempiere.model.ModelColumn<I_C_UOM_Conversion, Object>(I_C_UOM_Conversion.class, "C_UOM_Conversion_ID", null);
    /** Column name C_UOM_Conversion_ID */
    public static final String COLUMNNAME_C_UOM_Conversion_ID = "C_UOM_Conversion_ID";

	/**
	 * Set Maßeinheit.
	 * Unit of Measure
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_UOM_ID (int C_UOM_ID);

	/**
	 * Get Maßeinheit.
	 * Unit of Measure
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_UOM_ID();

    /** Column definition for C_UOM_ID */
    public static final org.adempiere.model.ModelColumn<I_C_UOM_Conversion, org.compiere.model.I_C_UOM> COLUMN_C_UOM_ID = new org.adempiere.model.ModelColumn<I_C_UOM_Conversion, org.compiere.model.I_C_UOM>(I_C_UOM_Conversion.class, "C_UOM_ID", org.compiere.model.I_C_UOM.class);
    /** Column name C_UOM_ID */
    public static final String COLUMNNAME_C_UOM_ID = "C_UOM_ID";

	/**
	 * Set Ziel-Maßeinheit.
	 * Maßeinheit, in die eine bestimmte Menge konvertiert werden soll
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_UOM_To_ID (int C_UOM_To_ID);

	/**
	 * Get Ziel-Maßeinheit.
	 * Maßeinheit, in die eine bestimmte Menge konvertiert werden soll
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_UOM_To_ID();

    /** Column definition for C_UOM_To_ID */
    public static final org.adempiere.model.ModelColumn<I_C_UOM_Conversion, org.compiere.model.I_C_UOM> COLUMN_C_UOM_To_ID = new org.adempiere.model.ModelColumn<I_C_UOM_Conversion, org.compiere.model.I_C_UOM>(I_C_UOM_Conversion.class, "C_UOM_To_ID", org.compiere.model.I_C_UOM.class);
    /** Column name C_UOM_To_ID */
    public static final String COLUMNNAME_C_UOM_To_ID = "C_UOM_To_ID";

	/**
	 * Set Divisor.
	 * Der Divisor ist der Kehrwert des Umrechnungsfaktors.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDivideRate (java.math.BigDecimal DivideRate);

	/**
	 * Get Divisor.
	 * Der Divisor ist der Kehrwert des Umrechnungsfaktors.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getDivideRate();

    /** Column definition for DivideRate */
    public static final org.adempiere.model.ModelColumn<I_C_UOM_Conversion, Object> COLUMN_DivideRate = new org.adempiere.model.ModelColumn<I_C_UOM_Conversion, Object>(I_C_UOM_Conversion.class, "DivideRate", null);
    /** Column name DivideRate */
    public static final String COLUMNNAME_DivideRate = "DivideRate";

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
    public static final org.adempiere.model.ModelColumn<I_C_UOM_Conversion, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_C_UOM_Conversion, Object>(I_C_UOM_Conversion.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Ziel ist Catch-Maßeinheit.
	 * Legt fest ob die Ziel-Maßeinheit die Parallel-Maßeinheit des Produktes ist, auf die bei einer Catch-Weight-Abrechnung zurückgegriffen wird
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsCatchUOMForProduct (boolean IsCatchUOMForProduct);

	/**
	 * Get Ziel ist Catch-Maßeinheit.
	 * Legt fest ob die Ziel-Maßeinheit die Parallel-Maßeinheit des Produktes ist, auf die bei einer Catch-Weight-Abrechnung zurückgegriffen wird
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isCatchUOMForProduct();

    /** Column definition for IsCatchUOMForProduct */
    public static final org.adempiere.model.ModelColumn<I_C_UOM_Conversion, Object> COLUMN_IsCatchUOMForProduct = new org.adempiere.model.ModelColumn<I_C_UOM_Conversion, Object>(I_C_UOM_Conversion.class, "IsCatchUOMForProduct", null);
    /** Column name IsCatchUOMForProduct */
    public static final String COLUMNNAME_IsCatchUOMForProduct = "IsCatchUOMForProduct";

	/**
	 * Set Produkt.
	 * Produkt, Leistung, Artikel
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_Product_ID (int M_Product_ID);

	/**
	 * Get Produkt.
	 * Produkt, Leistung, Artikel
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_Product_ID();

    /** Column definition for M_Product_ID */
    public static final org.adempiere.model.ModelColumn<I_C_UOM_Conversion, org.compiere.model.I_M_Product> COLUMN_M_Product_ID = new org.adempiere.model.ModelColumn<I_C_UOM_Conversion, org.compiere.model.I_M_Product>(I_C_UOM_Conversion.class, "M_Product_ID", org.compiere.model.I_M_Product.class);
    /** Column name M_Product_ID */
    public static final String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/**
	 * Set Faktor.
	 * Rate to multiple the source by to calculate the target.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setMultiplyRate (java.math.BigDecimal MultiplyRate);

	/**
	 * Get Faktor.
	 * Rate to multiple the source by to calculate the target.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getMultiplyRate();

    /** Column definition for MultiplyRate */
    public static final org.adempiere.model.ModelColumn<I_C_UOM_Conversion, Object> COLUMN_MultiplyRate = new org.adempiere.model.ModelColumn<I_C_UOM_Conversion, Object>(I_C_UOM_Conversion.class, "MultiplyRate", null);
    /** Column name MultiplyRate */
    public static final String COLUMNNAME_MultiplyRate = "MultiplyRate";

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
    public static final org.adempiere.model.ModelColumn<I_C_UOM_Conversion, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_C_UOM_Conversion, Object>(I_C_UOM_Conversion.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_UOM_Conversion, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_C_UOM_Conversion, org.compiere.model.I_AD_User>(I_C_UOM_Conversion.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
