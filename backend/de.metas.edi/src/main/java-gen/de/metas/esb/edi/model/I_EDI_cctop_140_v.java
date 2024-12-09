package de.metas.esb.edi.model;

<<<<<<< HEAD
=======
import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;
import java.math.BigDecimal;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

/** Generated Interface for EDI_cctop_140_v
 *  @author metasfresh (generated) 
 */
<<<<<<< HEAD
@SuppressWarnings("javadoc")
public interface I_EDI_cctop_140_v 
{

    /** TableName=EDI_cctop_140_v */
    public static final String Table_Name = "EDI_cctop_140_v";

    /** AD_Table_ID=540468 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);
=======
@SuppressWarnings("unused")
public interface I_EDI_cctop_140_v 
{

	String Table_Name = "EDI_cctop_140_v";

//	/** AD_Table_ID=540468 */
//	int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))


	/**
	 * Get Client.
	 * Client/Tenant for this installation.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public int getAD_Client_ID();

    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";
=======
	int getAD_Client_ID();

	String COLUMNNAME_AD_Client_ID = "AD_Client_ID";
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Set Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setAD_Org_ID (int AD_Org_ID);
=======
	void setAD_Org_ID (int AD_Org_ID);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Get Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public int getAD_Org_ID();

    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";
=======
	int getAD_Org_ID();

	String COLUMNNAME_AD_Org_ID = "AD_Org_ID";
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Set Invoice.
	 * Invoice Identifier
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setC_Invoice_ID (int C_Invoice_ID);
=======
	void setC_Invoice_ID (int C_Invoice_ID);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Get Invoice.
	 * Invoice Identifier
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public int getC_Invoice_ID();

	public org.compiere.model.I_C_Invoice getC_Invoice();

	public void setC_Invoice(org.compiere.model.I_C_Invoice C_Invoice);

    /** Column definition for C_Invoice_ID */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_140_v, org.compiere.model.I_C_Invoice> COLUMN_C_Invoice_ID = new org.adempiere.model.ModelColumn<I_EDI_cctop_140_v, org.compiere.model.I_C_Invoice>(I_EDI_cctop_140_v.class, "C_Invoice_ID", org.compiere.model.I_C_Invoice.class);
    /** Column name C_Invoice_ID */
    public static final String COLUMNNAME_C_Invoice_ID = "C_Invoice_ID";
=======
	int getC_Invoice_ID();

	@Nullable org.compiere.model.I_C_Invoice getC_Invoice();

	void setC_Invoice(@Nullable org.compiere.model.I_C_Invoice C_Invoice);

	ModelColumn<I_EDI_cctop_140_v, org.compiere.model.I_C_Invoice> COLUMN_C_Invoice_ID = new ModelColumn<>(I_EDI_cctop_140_v.class, "C_Invoice_ID", org.compiere.model.I_C_Invoice.class);
	String COLUMNNAME_C_Invoice_ID = "C_Invoice_ID";
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public java.sql.Timestamp getCreated();

    /** Column definition for Created */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_140_v, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_EDI_cctop_140_v, Object>(I_EDI_cctop_140_v.class, "Created", null);
    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";
=======
	java.sql.Timestamp getCreated();

	ModelColumn<I_EDI_cctop_140_v, Object> COLUMN_Created = new ModelColumn<>(I_EDI_cctop_140_v.class, "Created", null);
	String COLUMNNAME_Created = "Created";
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Get Created By.
	 * User who created this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public int getCreatedBy();

    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Rabatt %.
	 * Abschlag in Prozent
=======
	int getCreatedBy();

	String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Discount %.
	 * Discount in percent
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setDiscount (int Discount);

	/**
	 * Get Rabatt %.
	 * Abschlag in Prozent
=======
	void setDiscount (int Discount);

	/**
	 * Get Discount %.
	 * Discount in percent
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public int getDiscount();

    /** Column definition for Discount */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_140_v, Object> COLUMN_Discount = new org.adempiere.model.ModelColumn<I_EDI_cctop_140_v, Object>(I_EDI_cctop_140_v.class, "Discount", null);
    /** Column name Discount */
    public static final String COLUMNNAME_Discount = "Discount";

	/**
	 * Set Skontodatum.
=======
	int getDiscount();

	ModelColumn<I_EDI_cctop_140_v, Object> COLUMN_Discount = new ModelColumn<>(I_EDI_cctop_140_v.class, "Discount", null);
	String COLUMNNAME_Discount = "Discount";

	/**
	 * Set Discount.
	 * Calculated amount of discount
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDiscountAmt (@Nullable BigDecimal DiscountAmt);

	/**
	 * Get Discount.
	 * Calculated amount of discount
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getDiscountAmt();

	ModelColumn<I_EDI_cctop_140_v, Object> COLUMN_DiscountAmt = new ModelColumn<>(I_EDI_cctop_140_v.class, "DiscountAmt", null);
	String COLUMNNAME_DiscountAmt = "DiscountAmt";

	/**
	 * Set DiscountBaseAmt.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDiscountBaseAmt (@Nullable BigDecimal DiscountBaseAmt);

	/**
	 * Get DiscountBaseAmt.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getDiscountBaseAmt();

	ModelColumn<I_EDI_cctop_140_v, Object> COLUMN_DiscountBaseAmt = new ModelColumn<>(I_EDI_cctop_140_v.class, "DiscountBaseAmt", null);
	String COLUMNNAME_DiscountBaseAmt = "DiscountBaseAmt";

	/**
	 * Set Discount Date.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 * Last Date for payments with discount
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setDiscountDate (java.sql.Timestamp DiscountDate);

	/**
	 * Get Skontodatum.
=======
	void setDiscountDate (@Nullable java.sql.Timestamp DiscountDate);

	/**
	 * Get Discount Date.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 * Last Date for payments with discount
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public java.sql.Timestamp getDiscountDate();

    /** Column definition for DiscountDate */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_140_v, Object> COLUMN_DiscountDate = new org.adempiere.model.ModelColumn<I_EDI_cctop_140_v, Object>(I_EDI_cctop_140_v.class, "DiscountDate", null);
    /** Column name DiscountDate */
    public static final String COLUMNNAME_DiscountDate = "DiscountDate";

	/**
	 * Set Rabattfrist.
	 * Anzahl der Tage ab Rechnungsstellung innerhalb derer der Rabatt gewährt wird
=======
	@Nullable java.sql.Timestamp getDiscountDate();

	ModelColumn<I_EDI_cctop_140_v, Object> COLUMN_DiscountDate = new ModelColumn<>(I_EDI_cctop_140_v.class, "DiscountDate", null);
	String COLUMNNAME_DiscountDate = "DiscountDate";

	/**
	 * Set Discount Days.
	 * Number of days from invoice date to be eligible for discount
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setDiscountDays (int DiscountDays);

	/**
	 * Get Rabattfrist.
	 * Anzahl der Tage ab Rechnungsstellung innerhalb derer der Rabatt gewährt wird
=======
	void setDiscountDays (int DiscountDays);

	/**
	 * Get Discount Days.
	 * Number of days from invoice date to be eligible for discount
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public int getDiscountDays();

    /** Column definition for DiscountDays */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_140_v, Object> COLUMN_DiscountDays = new org.adempiere.model.ModelColumn<I_EDI_cctop_140_v, Object>(I_EDI_cctop_140_v.class, "DiscountDays", null);
    /** Column name DiscountDays */
    public static final String COLUMNNAME_DiscountDays = "DiscountDays";
=======
	int getDiscountDays();

	ModelColumn<I_EDI_cctop_140_v, Object> COLUMN_DiscountDays = new ModelColumn<>(I_EDI_cctop_140_v.class, "DiscountDays", null);
	String COLUMNNAME_DiscountDays = "DiscountDays";
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Set EDI_cctop_140_v.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setEDI_cctop_140_v_ID (int EDI_cctop_140_v_ID);
=======
	void setEDI_cctop_140_v_ID (int EDI_cctop_140_v_ID);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Get EDI_cctop_140_v.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public int getEDI_cctop_140_v_ID();

    /** Column definition for EDI_cctop_140_v_ID */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_140_v, Object> COLUMN_EDI_cctop_140_v_ID = new org.adempiere.model.ModelColumn<I_EDI_cctop_140_v, Object>(I_EDI_cctop_140_v.class, "EDI_cctop_140_v_ID", null);
    /** Column name EDI_cctop_140_v_ID */
    public static final String COLUMNNAME_EDI_cctop_140_v_ID = "EDI_cctop_140_v_ID";
=======
	int getEDI_cctop_140_v_ID();

	ModelColumn<I_EDI_cctop_140_v, Object> COLUMN_EDI_cctop_140_v_ID = new ModelColumn<>(I_EDI_cctop_140_v.class, "EDI_cctop_140_v_ID", null);
	String COLUMNNAME_EDI_cctop_140_v_ID = "EDI_cctop_140_v_ID";
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Set EDI_cctop_invoic_v.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setEDI_cctop_invoic_v_ID (int EDI_cctop_invoic_v_ID);
=======
	void setEDI_cctop_invoic_v_ID (int EDI_cctop_invoic_v_ID);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Get EDI_cctop_invoic_v.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public int getEDI_cctop_invoic_v_ID();

	public de.metas.esb.edi.model.I_EDI_cctop_invoic_v getEDI_cctop_invoic_v();

	public void setEDI_cctop_invoic_v(de.metas.esb.edi.model.I_EDI_cctop_invoic_v EDI_cctop_invoic_v);

    /** Column definition for EDI_cctop_invoic_v_ID */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_140_v, de.metas.esb.edi.model.I_EDI_cctop_invoic_v> COLUMN_EDI_cctop_invoic_v_ID = new org.adempiere.model.ModelColumn<I_EDI_cctop_140_v, de.metas.esb.edi.model.I_EDI_cctop_invoic_v>(I_EDI_cctop_140_v.class, "EDI_cctop_invoic_v_ID", de.metas.esb.edi.model.I_EDI_cctop_invoic_v.class);
    /** Column name EDI_cctop_invoic_v_ID */
    public static final String COLUMNNAME_EDI_cctop_invoic_v_ID = "EDI_cctop_invoic_v_ID";
=======
	int getEDI_cctop_invoic_v_ID();

	@Nullable de.metas.esb.edi.model.I_EDI_cctop_invoic_v getEDI_cctop_invoic_v();

	void setEDI_cctop_invoic_v(@Nullable de.metas.esb.edi.model.I_EDI_cctop_invoic_v EDI_cctop_invoic_v);

	ModelColumn<I_EDI_cctop_140_v, de.metas.esb.edi.model.I_EDI_cctop_invoic_v> COLUMN_EDI_cctop_invoic_v_ID = new ModelColumn<>(I_EDI_cctop_140_v.class, "EDI_cctop_invoic_v_ID", de.metas.esb.edi.model.I_EDI_cctop_invoic_v.class);
	String COLUMNNAME_EDI_cctop_invoic_v_ID = "EDI_cctop_invoic_v_ID";
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Set Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setIsActive (boolean IsActive);
=======
	void setIsActive (boolean IsActive);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Get Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public boolean isActive();

    /** Column definition for IsActive */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_140_v, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_EDI_cctop_140_v, Object>(I_EDI_cctop_140_v.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Satz.
=======
	boolean isActive();

	ModelColumn<I_EDI_cctop_140_v, Object> COLUMN_IsActive = new ModelColumn<>(I_EDI_cctop_140_v.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setName (@Nullable java.lang.String Name);

	/**
	 * Get Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getName();

	ModelColumn<I_EDI_cctop_140_v, Object> COLUMN_Name = new ModelColumn<>(I_EDI_cctop_140_v.class, "Name", null);
	String COLUMNNAME_Name = "Name";

	/**
	 * Set Rate.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 * Rate or Tax or Exchange
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setRate (java.math.BigDecimal Rate);

	/**
	 * Get Satz.
=======
	void setRate (@Nullable BigDecimal Rate);

	/**
	 * Get Rate.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 * Rate or Tax or Exchange
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public java.math.BigDecimal getRate();

    /** Column definition for Rate */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_140_v, Object> COLUMN_Rate = new org.adempiere.model.ModelColumn<I_EDI_cctop_140_v, Object>(I_EDI_cctop_140_v.class, "Rate", null);
    /** Column name Rate */
    public static final String COLUMNNAME_Rate = "Rate";
=======
	BigDecimal getRate();

	ModelColumn<I_EDI_cctop_140_v, Object> COLUMN_Rate = new ModelColumn<>(I_EDI_cctop_140_v.class, "Rate", null);
	String COLUMNNAME_Rate = "Rate";
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public java.sql.Timestamp getUpdated();

    /** Column definition for Updated */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_140_v, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_EDI_cctop_140_v, Object>(I_EDI_cctop_140_v.class, "Updated", null);
    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";
=======
	java.sql.Timestamp getUpdated();

	ModelColumn<I_EDI_cctop_140_v, Object> COLUMN_Updated = new ModelColumn<>(I_EDI_cctop_140_v.class, "Updated", null);
	String COLUMNNAME_Updated = "Updated";
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Get Updated By.
	 * User who updated this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public int getUpdatedBy();

    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
=======
	int getUpdatedBy();

	String COLUMNNAME_UpdatedBy = "UpdatedBy";
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
}
