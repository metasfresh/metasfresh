package de.metas.esb.edi.model;

<<<<<<< HEAD
=======
import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;
import java.math.BigDecimal;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

/** Generated Interface for EDI_cctop_invoic_500_v
 *  @author metasfresh (generated) 
 */
<<<<<<< HEAD
@SuppressWarnings("javadoc")
public interface I_EDI_cctop_invoic_500_v 
{

    /** TableName=EDI_cctop_invoic_500_v */
    public static final String Table_Name = "EDI_cctop_invoic_500_v";

    /** AD_Table_ID=540463 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);
=======
@SuppressWarnings("unused")
public interface I_EDI_cctop_invoic_500_v 
{

	String Table_Name = "EDI_cctop_invoic_500_v";

//	/** AD_Table_ID=540463 */
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

	/**
	 * Set Buyer_EAN_CU.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setBuyer_EAN_CU (@Nullable java.lang.String Buyer_EAN_CU);

	/**
	 * Get Buyer_EAN_CU.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getBuyer_EAN_CU();

	ModelColumn<I_EDI_cctop_invoic_500_v, Object> COLUMN_Buyer_EAN_CU = new ModelColumn<>(I_EDI_cctop_invoic_500_v.class, "Buyer_EAN_CU", null);
	String COLUMNNAME_Buyer_EAN_CU = "Buyer_EAN_CU";

	/**
	 * Set Buyer_GTIN_CU.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setBuyer_GTIN_CU (@Nullable java.lang.String Buyer_GTIN_CU);

	/**
	 * Get Buyer_GTIN_CU.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getBuyer_GTIN_CU();

	ModelColumn<I_EDI_cctop_invoic_500_v, Object> COLUMN_Buyer_GTIN_CU = new ModelColumn<>(I_EDI_cctop_invoic_500_v.class, "Buyer_GTIN_CU", null);
	String COLUMNNAME_Buyer_GTIN_CU = "Buyer_GTIN_CU";

	/**
	 * Set Buyer_GTIN_TU.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setBuyer_GTIN_TU (@Nullable java.lang.String Buyer_GTIN_TU);

	/**
	 * Get Buyer_GTIN_TU.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getBuyer_GTIN_TU();

	ModelColumn<I_EDI_cctop_invoic_500_v, Object> COLUMN_Buyer_GTIN_TU = new ModelColumn<>(I_EDI_cctop_invoic_500_v.class, "Buyer_GTIN_TU", null);
	String COLUMNNAME_Buyer_GTIN_TU = "Buyer_GTIN_TU";
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
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_500_v, org.compiere.model.I_C_Invoice> COLUMN_C_Invoice_ID = new org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_500_v, org.compiere.model.I_C_Invoice>(I_EDI_cctop_invoic_500_v.class, "C_Invoice_ID", org.compiere.model.I_C_Invoice.class);
    /** Column name C_Invoice_ID */
    public static final String COLUMNNAME_C_Invoice_ID = "C_Invoice_ID";
=======
	int getC_Invoice_ID();

	@Nullable org.compiere.model.I_C_Invoice getC_Invoice();

	void setC_Invoice(@Nullable org.compiere.model.I_C_Invoice C_Invoice);

	ModelColumn<I_EDI_cctop_invoic_500_v, org.compiere.model.I_C_Invoice> COLUMN_C_Invoice_ID = new ModelColumn<>(I_EDI_cctop_invoic_500_v.class, "C_Invoice_ID", org.compiere.model.I_C_Invoice.class);
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
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_500_v, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_500_v, Object>(I_EDI_cctop_invoic_500_v.class, "Created", null);
    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";
=======
	java.sql.Timestamp getCreated();

	ModelColumn<I_EDI_cctop_invoic_500_v, Object> COLUMN_Created = new ModelColumn<>(I_EDI_cctop_invoic_500_v.class, "Created", null);
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
=======
	int getCreatedBy();

	String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set BPartner UOM.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_UOM_BPartner_ID (int C_UOM_BPartner_ID);

	/**
	 * Get BPartner UOM.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_UOM_BPartner_ID();

	String COLUMNNAME_C_UOM_BPartner_ID = "C_UOM_BPartner_ID";

	/**
	 * Set Customer Product No.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCustomerProductNo (@Nullable java.lang.String CustomerProductNo);

	/**
	 * Get Customer Product No.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getCustomerProductNo();

	ModelColumn<I_EDI_cctop_invoic_500_v, Object> COLUMN_CustomerProductNo = new ModelColumn<>(I_EDI_cctop_invoic_500_v.class, "CustomerProductNo", null);
	String COLUMNNAME_CustomerProductNo = "CustomerProductNo";

	/**
	 * Set Discount %.
	 * Discount in percent
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDiscount (@Nullable BigDecimal Discount);

	/**
	 * Get Discount %.
	 * Discount in percent
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getDiscount();

	ModelColumn<I_EDI_cctop_invoic_500_v, Object> COLUMN_Discount = new ModelColumn<>(I_EDI_cctop_invoic_500_v.class, "Discount", null);
	String COLUMNNAME_Discount = "Discount";
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Set Ordered UOM.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setEanCom_Ordered_UOM (java.lang.String EanCom_Ordered_UOM);
=======
	void setEanCom_Ordered_UOM (@Nullable java.lang.String EanCom_Ordered_UOM);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Get Ordered UOM.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public java.lang.String getEanCom_Ordered_UOM();

    /** Column definition for EanCom_Ordered_UOM */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_500_v, Object> COLUMN_EanCom_Ordered_UOM = new org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_500_v, Object>(I_EDI_cctop_invoic_500_v.class, "EanCom_Ordered_UOM", null);
    /** Column name EanCom_Ordered_UOM */
    public static final String COLUMNNAME_EanCom_Ordered_UOM = "EanCom_Ordered_UOM";
=======
	@Nullable java.lang.String getEanCom_Ordered_UOM();

	ModelColumn<I_EDI_cctop_invoic_500_v, Object> COLUMN_EanCom_Ordered_UOM = new ModelColumn<>(I_EDI_cctop_invoic_500_v.class, "EanCom_Ordered_UOM", null);
	String COLUMNNAME_EanCom_Ordered_UOM = "EanCom_Ordered_UOM";
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Set EanCom_Price_UOM.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setEanCom_Price_UOM (java.lang.String EanCom_Price_UOM);
=======
	void setEanCom_Price_UOM (@Nullable java.lang.String EanCom_Price_UOM);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Get EanCom_Price_UOM.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public java.lang.String getEanCom_Price_UOM();

    /** Column definition for EanCom_Price_UOM */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_500_v, Object> COLUMN_EanCom_Price_UOM = new org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_500_v, Object>(I_EDI_cctop_invoic_500_v.class, "EanCom_Price_UOM", null);
    /** Column name EanCom_Price_UOM */
    public static final String COLUMNNAME_EanCom_Price_UOM = "EanCom_Price_UOM";
=======
	@Nullable java.lang.String getEanCom_Price_UOM();

	ModelColumn<I_EDI_cctop_invoic_500_v, Object> COLUMN_EanCom_Price_UOM = new ModelColumn<>(I_EDI_cctop_invoic_500_v.class, "EanCom_Price_UOM", null);
	String COLUMNNAME_EanCom_Price_UOM = "EanCom_Price_UOM";
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Set eancom_uom.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setEanCom_UOM (java.lang.String EanCom_UOM);
=======
	void setEanCom_UOM (@Nullable java.lang.String EanCom_UOM);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Get eancom_uom.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public java.lang.String getEanCom_UOM();

    /** Column definition for EanCom_UOM */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_500_v, Object> COLUMN_EanCom_UOM = new org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_500_v, Object>(I_EDI_cctop_invoic_500_v.class, "EanCom_UOM", null);
    /** Column name EanCom_UOM */
    public static final String COLUMNNAME_EanCom_UOM = "EanCom_UOM";
=======
	@Nullable java.lang.String getEanCom_UOM();

	ModelColumn<I_EDI_cctop_invoic_500_v, Object> COLUMN_EanCom_UOM = new ModelColumn<>(I_EDI_cctop_invoic_500_v.class, "EanCom_UOM", null);
	String COLUMNNAME_EanCom_UOM = "EanCom_UOM";
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Set CU-EAN.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setEAN_CU (java.lang.String EAN_CU);
=======
	void setEAN_CU (@Nullable java.lang.String EAN_CU);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Get CU-EAN.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public java.lang.String getEAN_CU();

    /** Column definition for EAN_CU */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_500_v, Object> COLUMN_EAN_CU = new org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_500_v, Object>(I_EDI_cctop_invoic_500_v.class, "EAN_CU", null);
    /** Column name EAN_CU */
    public static final String COLUMNNAME_EAN_CU = "EAN_CU";
=======
	@Nullable java.lang.String getEAN_CU();

	ModelColumn<I_EDI_cctop_invoic_500_v, Object> COLUMN_EAN_CU = new ModelColumn<>(I_EDI_cctop_invoic_500_v.class, "EAN_CU", null);
	String COLUMNNAME_EAN_CU = "EAN_CU";
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Set TU-EAN.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setEAN_TU (java.lang.String EAN_TU);
=======
	void setEAN_TU (@Nullable java.lang.String EAN_TU);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Get TU-EAN.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public java.lang.String getEAN_TU();

    /** Column definition for EAN_TU */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_500_v, Object> COLUMN_EAN_TU = new org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_500_v, Object>(I_EDI_cctop_invoic_500_v.class, "EAN_TU", null);
    /** Column name EAN_TU */
    public static final String COLUMNNAME_EAN_TU = "EAN_TU";
=======
	@Nullable java.lang.String getEAN_TU();

	ModelColumn<I_EDI_cctop_invoic_500_v, Object> COLUMN_EAN_TU = new ModelColumn<>(I_EDI_cctop_invoic_500_v.class, "EAN_TU", null);
	String COLUMNNAME_EAN_TU = "EAN_TU";
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Set EDI_cctop_invoic_500_v.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setEDI_cctop_invoic_500_v_ID (int EDI_cctop_invoic_500_v_ID);
=======
	void setEDI_cctop_invoic_500_v_ID (int EDI_cctop_invoic_500_v_ID);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Get EDI_cctop_invoic_500_v.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public int getEDI_cctop_invoic_500_v_ID();

    /** Column definition for EDI_cctop_invoic_500_v_ID */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_500_v, Object> COLUMN_EDI_cctop_invoic_500_v_ID = new org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_500_v, Object>(I_EDI_cctop_invoic_500_v.class, "EDI_cctop_invoic_500_v_ID", null);
    /** Column name EDI_cctop_invoic_500_v_ID */
    public static final String COLUMNNAME_EDI_cctop_invoic_500_v_ID = "EDI_cctop_invoic_500_v_ID";
=======
	int getEDI_cctop_invoic_500_v_ID();

	ModelColumn<I_EDI_cctop_invoic_500_v, Object> COLUMN_EDI_cctop_invoic_500_v_ID = new ModelColumn<>(I_EDI_cctop_invoic_500_v.class, "EDI_cctop_invoic_500_v_ID", null);
	String COLUMNNAME_EDI_cctop_invoic_500_v_ID = "EDI_cctop_invoic_500_v_ID";
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
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_500_v, de.metas.esb.edi.model.I_EDI_cctop_invoic_v> COLUMN_EDI_cctop_invoic_v_ID = new org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_500_v, de.metas.esb.edi.model.I_EDI_cctop_invoic_v>(I_EDI_cctop_invoic_500_v.class, "EDI_cctop_invoic_v_ID", de.metas.esb.edi.model.I_EDI_cctop_invoic_v.class);
    /** Column name EDI_cctop_invoic_v_ID */
    public static final String COLUMNNAME_EDI_cctop_invoic_v_ID = "EDI_cctop_invoic_v_ID";
=======
	int getEDI_cctop_invoic_v_ID();

	@Nullable de.metas.esb.edi.model.I_EDI_cctop_invoic_v getEDI_cctop_invoic_v();

	void setEDI_cctop_invoic_v(@Nullable de.metas.esb.edi.model.I_EDI_cctop_invoic_v EDI_cctop_invoic_v);

	ModelColumn<I_EDI_cctop_invoic_500_v, de.metas.esb.edi.model.I_EDI_cctop_invoic_v> COLUMN_EDI_cctop_invoic_v_ID = new ModelColumn<>(I_EDI_cctop_invoic_500_v.class, "EDI_cctop_invoic_v_ID", de.metas.esb.edi.model.I_EDI_cctop_invoic_v.class);
	String COLUMNNAME_EDI_cctop_invoic_v_ID = "EDI_cctop_invoic_v_ID";

	/**
	 * Set ExternalSeqNo.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setExternalSeqNo (int ExternalSeqNo);

	/**
	 * Get ExternalSeqNo.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getExternalSeqNo();

	ModelColumn<I_EDI_cctop_invoic_500_v, Object> COLUMN_ExternalSeqNo = new ModelColumn<>(I_EDI_cctop_invoic_500_v.class, "ExternalSeqNo", null);
	String COLUMNNAME_ExternalSeqNo = "ExternalSeqNo";
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Set GTIN.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setGTIN (java.lang.String GTIN);
=======
	void setGTIN (@Nullable java.lang.String GTIN);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Get GTIN.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public java.lang.String getGTIN();

    /** Column definition for GTIN */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_500_v, Object> COLUMN_GTIN = new org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_500_v, Object>(I_EDI_cctop_invoic_500_v.class, "GTIN", null);
    /** Column name GTIN */
    public static final String COLUMNNAME_GTIN = "GTIN";
=======
	@Nullable java.lang.String getGTIN();

	ModelColumn<I_EDI_cctop_invoic_500_v, Object> COLUMN_GTIN = new ModelColumn<>(I_EDI_cctop_invoic_500_v.class, "GTIN", null);
	String COLUMNNAME_GTIN = "GTIN";

	/**
	 * Set Invoicable Quantity per.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setInvoicableQtyBasedOn (@Nullable java.lang.String InvoicableQtyBasedOn);

	/**
	 * Get Invoicable Quantity per.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getInvoicableQtyBasedOn();

	ModelColumn<I_EDI_cctop_invoic_500_v, Object> COLUMN_InvoicableQtyBasedOn = new ModelColumn<>(I_EDI_cctop_invoic_500_v.class, "InvoicableQtyBasedOn", null);
	String COLUMNNAME_InvoicableQtyBasedOn = "InvoicableQtyBasedOn";
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
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_500_v, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_500_v, Object>(I_EDI_cctop_invoic_500_v.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set ISO Währungscode.
	 * Dreibuchstabiger ISO 4217 Code für die Währung
=======
	boolean isActive();

	ModelColumn<I_EDI_cctop_invoic_500_v, Object> COLUMN_IsActive = new ModelColumn<>(I_EDI_cctop_invoic_500_v.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set ISO Currency Code.
	 * Three letter ISO 4217 Code of the Currency
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setISO_Code (java.lang.String ISO_Code);

	/**
	 * Get ISO Währungscode.
	 * Dreibuchstabiger ISO 4217 Code für die Währung
=======
	void setISO_Code (@Nullable java.lang.String ISO_Code);

	/**
	 * Get ISO Currency Code.
	 * Three letter ISO 4217 Code of the Currency
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public java.lang.String getISO_Code();

    /** Column definition for ISO_Code */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_500_v, Object> COLUMN_ISO_Code = new org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_500_v, Object>(I_EDI_cctop_invoic_500_v.class, "ISO_Code", null);
    /** Column name ISO_Code */
    public static final String COLUMNNAME_ISO_Code = "ISO_Code";
=======
	@Nullable java.lang.String getISO_Code();

	ModelColumn<I_EDI_cctop_invoic_500_v, Object> COLUMN_ISO_Code = new ModelColumn<>(I_EDI_cctop_invoic_500_v.class, "ISO_Code", null);
	String COLUMNNAME_ISO_Code = "ISO_Code";

	/**
	 * Set SO Tax exempt.
	 * Business partner is exempt from tax on sales
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsTaxExempt (boolean IsTaxExempt);

	/**
	 * Get SO Tax exempt.
	 * Business partner is exempt from tax on sales
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isTaxExempt();

	ModelColumn<I_EDI_cctop_invoic_500_v, Object> COLUMN_IsTaxExempt = new ModelColumn<>(I_EDI_cctop_invoic_500_v.class, "IsTaxExempt", null);
	String COLUMNNAME_IsTaxExempt = "IsTaxExempt";
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Set Leergut.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setLeergut (java.lang.String Leergut);
=======
	void setLeergut (@Nullable java.lang.String Leergut);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Get Leergut.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public java.lang.String getLeergut();

    /** Column definition for Leergut */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_500_v, Object> COLUMN_Leergut = new org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_500_v, Object>(I_EDI_cctop_invoic_500_v.class, "Leergut", null);
    /** Column name Leergut */
    public static final String COLUMNNAME_Leergut = "Leergut";
=======
	@Nullable java.lang.String getLeergut();

	ModelColumn<I_EDI_cctop_invoic_500_v, Object> COLUMN_Leergut = new ModelColumn<>(I_EDI_cctop_invoic_500_v.class, "Leergut", null);
	String COLUMNNAME_Leergut = "Leergut";
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Set SeqNo..
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setLine (int Line);
=======
	void setLine (int Line);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Get SeqNo..
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public int getLine();

    /** Column definition for Line */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_500_v, Object> COLUMN_Line = new org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_500_v, Object>(I_EDI_cctop_invoic_500_v.class, "Line", null);
    /** Column name Line */
    public static final String COLUMNNAME_Line = "Line";
=======
	int getLine();

	ModelColumn<I_EDI_cctop_invoic_500_v, Object> COLUMN_Line = new ModelColumn<>(I_EDI_cctop_invoic_500_v.class, "Line", null);
	String COLUMNNAME_Line = "Line";
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Set Line Net Amount.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setLineNetAmt (java.math.BigDecimal LineNetAmt);
=======
	void setLineNetAmt (@Nullable BigDecimal LineNetAmt);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Get Line Net Amount.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public java.math.BigDecimal getLineNetAmt();

    /** Column definition for LineNetAmt */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_500_v, Object> COLUMN_LineNetAmt = new org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_500_v, Object>(I_EDI_cctop_invoic_500_v.class, "LineNetAmt", null);
    /** Column name LineNetAmt */
    public static final String COLUMNNAME_LineNetAmt = "LineNetAmt";
=======
	BigDecimal getLineNetAmt();

	ModelColumn<I_EDI_cctop_invoic_500_v, Object> COLUMN_LineNetAmt = new ModelColumn<>(I_EDI_cctop_invoic_500_v.class, "LineNetAmt", null);
	String COLUMNNAME_LineNetAmt = "LineNetAmt";
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Set Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setName (java.lang.String Name);
=======
	void setName (@Nullable java.lang.String Name);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Get Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public java.lang.String getName();

    /** Column definition for Name */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_500_v, Object> COLUMN_Name = new org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_500_v, Object>(I_EDI_cctop_invoic_500_v.class, "Name", null);
    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

	/**
	 * Set Name 2.
	 * Zusätzliche Bezeichnung
=======
	@Nullable java.lang.String getName();

	ModelColumn<I_EDI_cctop_invoic_500_v, Object> COLUMN_Name = new ModelColumn<>(I_EDI_cctop_invoic_500_v.class, "Name", null);
	String COLUMNNAME_Name = "Name";

	/**
	 * Set Name 2.
	 * Additional Name
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setName2 (java.lang.String Name2);

	/**
	 * Get Name 2.
	 * Zusätzliche Bezeichnung
=======
	void setName2 (@Nullable java.lang.String Name2);

	/**
	 * Get Name 2.
	 * Additional Name
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public java.lang.String getName2();

    /** Column definition for Name2 */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_500_v, Object> COLUMN_Name2 = new org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_500_v, Object>(I_EDI_cctop_invoic_500_v.class, "Name2", null);
    /** Column name Name2 */
    public static final String COLUMNNAME_Name2 = "Name2";
=======
	@Nullable java.lang.String getName2();

	ModelColumn<I_EDI_cctop_invoic_500_v, Object> COLUMN_Name2 = new ModelColumn<>(I_EDI_cctop_invoic_500_v.class, "Name2", null);
	String COLUMNNAME_Name2 = "Name2";
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Set Order Line.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setOrderLine (int OrderLine);
=======
	void setOrderLine (int OrderLine);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Get Order Line.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public int getOrderLine();

    /** Column definition for OrderLine */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_500_v, Object> COLUMN_OrderLine = new org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_500_v, Object>(I_EDI_cctop_invoic_500_v.class, "OrderLine", null);
    /** Column name OrderLine */
    public static final String COLUMNNAME_OrderLine = "OrderLine";
=======
	int getOrderLine();

	ModelColumn<I_EDI_cctop_invoic_500_v, Object> COLUMN_OrderLine = new ModelColumn<>(I_EDI_cctop_invoic_500_v.class, "OrderLine", null);
	String COLUMNNAME_OrderLine = "OrderLine";
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Set Auftragsreferenz.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setOrderPOReference (java.lang.String OrderPOReference);
=======
	void setOrderPOReference (@Nullable java.lang.String OrderPOReference);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Get Auftragsreferenz.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public java.lang.String getOrderPOReference();

    /** Column definition for OrderPOReference */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_500_v, Object> COLUMN_OrderPOReference = new org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_500_v, Object>(I_EDI_cctop_invoic_500_v.class, "OrderPOReference", null);
    /** Column name OrderPOReference */
    public static final String COLUMNNAME_OrderPOReference = "OrderPOReference";
=======
	@Nullable java.lang.String getOrderPOReference();

	ModelColumn<I_EDI_cctop_invoic_500_v, Object> COLUMN_OrderPOReference = new ModelColumn<>(I_EDI_cctop_invoic_500_v.class, "OrderPOReference", null);
	String COLUMNNAME_OrderPOReference = "OrderPOReference";
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Set Price Actual.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setPriceActual (java.math.BigDecimal PriceActual);
=======
	void setPriceActual (@Nullable BigDecimal PriceActual);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Get Price Actual.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public java.math.BigDecimal getPriceActual();

    /** Column definition for PriceActual */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_500_v, Object> COLUMN_PriceActual = new org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_500_v, Object>(I_EDI_cctop_invoic_500_v.class, "PriceActual", null);
    /** Column name PriceActual */
    public static final String COLUMNNAME_PriceActual = "PriceActual";

	/**
	 * Set Auszeichnungspreis.
	 * Auszeichnungspreis
=======
	BigDecimal getPriceActual();

	ModelColumn<I_EDI_cctop_invoic_500_v, Object> COLUMN_PriceActual = new ModelColumn<>(I_EDI_cctop_invoic_500_v.class, "PriceActual", null);
	String COLUMNNAME_PriceActual = "PriceActual";

	/**
	 * Set List Price.
	 * List Price
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setPriceList (java.math.BigDecimal PriceList);

	/**
	 * Get Auszeichnungspreis.
	 * Auszeichnungspreis
=======
	void setPriceList (@Nullable BigDecimal PriceList);

	/**
	 * Get List Price.
	 * List Price
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public java.math.BigDecimal getPriceList();

    /** Column definition for PriceList */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_500_v, Object> COLUMN_PriceList = new org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_500_v, Object>(I_EDI_cctop_invoic_500_v.class, "PriceList", null);
    /** Column name PriceList */
    public static final String COLUMNNAME_PriceList = "PriceList";

	/**
	 * Set Produktbeschreibung.
	 * Produktbeschreibung
=======
	BigDecimal getPriceList();

	ModelColumn<I_EDI_cctop_invoic_500_v, Object> COLUMN_PriceList = new ModelColumn<>(I_EDI_cctop_invoic_500_v.class, "PriceList", null);
	String COLUMNNAME_PriceList = "PriceList";

	/**
	 * Set Product Description.
	 * Product Description
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setProductDescription (java.lang.String ProductDescription);

	/**
	 * Get Produktbeschreibung.
	 * Produktbeschreibung
=======
	void setProductDescription (@Nullable java.lang.String ProductDescription);

	/**
	 * Get Product Description.
	 * Product Description
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public java.lang.String getProductDescription();

    /** Column definition for ProductDescription */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_500_v, Object> COLUMN_ProductDescription = new org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_500_v, Object>(I_EDI_cctop_invoic_500_v.class, "ProductDescription", null);
    /** Column name ProductDescription */
    public static final String COLUMNNAME_ProductDescription = "ProductDescription";
=======
	@Nullable java.lang.String getProductDescription();

	ModelColumn<I_EDI_cctop_invoic_500_v, Object> COLUMN_ProductDescription = new ModelColumn<>(I_EDI_cctop_invoic_500_v.class, "ProductDescription", null);
	String COLUMNNAME_ProductDescription = "ProductDescription";

	/**
	 * Set Qty Entered In BPartner UOM.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtyEnteredInBPartnerUOM (@Nullable BigDecimal QtyEnteredInBPartnerUOM);

	/**
	 * Get Qty Entered In BPartner UOM.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyEnteredInBPartnerUOM();

	ModelColumn<I_EDI_cctop_invoic_500_v, Object> COLUMN_QtyEnteredInBPartnerUOM = new ModelColumn<>(I_EDI_cctop_invoic_500_v.class, "QtyEnteredInBPartnerUOM", null);
	String COLUMNNAME_QtyEnteredInBPartnerUOM = "QtyEnteredInBPartnerUOM";
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Set Quantity Invoiced.
	 * Invoiced quantity in the product's UOM.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setQtyInvoiced (java.math.BigDecimal QtyInvoiced);
=======
	void setQtyInvoiced (@Nullable BigDecimal QtyInvoiced);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Get Quantity Invoiced.
	 * Invoiced quantity in the product's UOM.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public java.math.BigDecimal getQtyInvoiced();

    /** Column definition for QtyInvoiced */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_500_v, Object> COLUMN_QtyInvoiced = new org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_500_v, Object>(I_EDI_cctop_invoic_500_v.class, "QtyInvoiced", null);
    /** Column name QtyInvoiced */
    public static final String COLUMNNAME_QtyInvoiced = "QtyInvoiced";
=======
	BigDecimal getQtyInvoiced();

	ModelColumn<I_EDI_cctop_invoic_500_v, Object> COLUMN_QtyInvoiced = new ModelColumn<>(I_EDI_cctop_invoic_500_v.class, "QtyInvoiced", null);
	String COLUMNNAME_QtyInvoiced = "QtyInvoiced";
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Set Invoiced quantity in ordered UOM.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setQtyInvoicedInOrderedUOM (java.math.BigDecimal QtyInvoicedInOrderedUOM);
=======
	void setQtyInvoicedInOrderedUOM (@Nullable BigDecimal QtyInvoicedInOrderedUOM);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Get Invoiced quantity in ordered UOM.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public java.math.BigDecimal getQtyInvoicedInOrderedUOM();

    /** Column definition for QtyInvoicedInOrderedUOM */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_500_v, Object> COLUMN_QtyInvoicedInOrderedUOM = new org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_500_v, Object>(I_EDI_cctop_invoic_500_v.class, "QtyInvoicedInOrderedUOM", null);
    /** Column name QtyInvoicedInOrderedUOM */
    public static final String COLUMNNAME_QtyInvoicedInOrderedUOM = "QtyInvoicedInOrderedUOM";

	/**
	 * Set Satz.
=======
	BigDecimal getQtyInvoicedInOrderedUOM();

	ModelColumn<I_EDI_cctop_invoic_500_v, Object> COLUMN_QtyInvoicedInOrderedUOM = new ModelColumn<>(I_EDI_cctop_invoic_500_v.class, "QtyInvoicedInOrderedUOM", null);
	String COLUMNNAME_QtyInvoicedInOrderedUOM = "QtyInvoicedInOrderedUOM";

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
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_500_v, Object> COLUMN_Rate = new org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_500_v, Object>(I_EDI_cctop_invoic_500_v.class, "Rate", null);
    /** Column name Rate */
    public static final String COLUMNNAME_Rate = "Rate";
=======
	BigDecimal getRate();

	ModelColumn<I_EDI_cctop_invoic_500_v, Object> COLUMN_Rate = new ModelColumn<>(I_EDI_cctop_invoic_500_v.class, "Rate", null);
	String COLUMNNAME_Rate = "Rate";

	/**
	 * Set Supplier_GTIN_CU.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setSupplier_GTIN_CU (@Nullable java.lang.String Supplier_GTIN_CU);

	/**
	 * Get Supplier_GTIN_CU.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getSupplier_GTIN_CU();

	ModelColumn<I_EDI_cctop_invoic_500_v, Object> COLUMN_Supplier_GTIN_CU = new ModelColumn<>(I_EDI_cctop_invoic_500_v.class, "Supplier_GTIN_CU", null);
	String COLUMNNAME_Supplier_GTIN_CU = "Supplier_GTIN_CU";
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Set Positions-Steuer.
	 * Betrag der enthaltenen oder zuzgl. Steuer in einer Rechungs- oder Auftragsposition
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setTaxAmtInfo (java.math.BigDecimal TaxAmtInfo);
=======
	void setTaxAmtInfo (@Nullable BigDecimal TaxAmtInfo);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Get Positions-Steuer.
	 * Betrag der enthaltenen oder zuzgl. Steuer in einer Rechungs- oder Auftragsposition
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public java.math.BigDecimal getTaxAmtInfo();

    /** Column definition for TaxAmtInfo */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_500_v, Object> COLUMN_TaxAmtInfo = new org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_500_v, Object>(I_EDI_cctop_invoic_500_v.class, "TaxAmtInfo", null);
    /** Column name TaxAmtInfo */
    public static final String COLUMNNAME_TaxAmtInfo = "TaxAmtInfo";
=======
	BigDecimal getTaxAmtInfo();

	ModelColumn<I_EDI_cctop_invoic_500_v, Object> COLUMN_TaxAmtInfo = new ModelColumn<>(I_EDI_cctop_invoic_500_v.class, "TaxAmtInfo", null);
	String COLUMNNAME_TaxAmtInfo = "TaxAmtInfo";
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Set taxfree.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void settaxfree (boolean taxfree);
=======
	void settaxfree (boolean taxfree);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Get taxfree.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public boolean istaxfree();

    /** Column definition for taxfree */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_500_v, Object> COLUMN_taxfree = new org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_500_v, Object>(I_EDI_cctop_invoic_500_v.class, "taxfree", null);
    /** Column name taxfree */
    public static final String COLUMNNAME_taxfree = "taxfree";
=======
	boolean istaxfree();

	ModelColumn<I_EDI_cctop_invoic_500_v, Object> COLUMN_taxfree = new ModelColumn<>(I_EDI_cctop_invoic_500_v.class, "taxfree", null);
	String COLUMNNAME_taxfree = "taxfree";
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Set CU-UPC.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setUPC_CU (java.lang.String UPC_CU);
=======
	void setUPC_CU (@Nullable java.lang.String UPC_CU);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Get CU-UPC.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public java.lang.String getUPC_CU();

    /** Column definition for UPC_CU */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_500_v, Object> COLUMN_UPC_CU = new org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_500_v, Object>(I_EDI_cctop_invoic_500_v.class, "UPC_CU", null);
    /** Column name UPC_CU */
    public static final String COLUMNNAME_UPC_CU = "UPC_CU";
=======
	@Nullable java.lang.String getUPC_CU();

	ModelColumn<I_EDI_cctop_invoic_500_v, Object> COLUMN_UPC_CU = new ModelColumn<>(I_EDI_cctop_invoic_500_v.class, "UPC_CU", null);
	String COLUMNNAME_UPC_CU = "UPC_CU";
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Set TU-UPC.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setUPC_TU (java.lang.String UPC_TU);
=======
	void setUPC_TU (@Nullable java.lang.String UPC_TU);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Get TU-UPC.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public java.lang.String getUPC_TU();

    /** Column definition for UPC_TU */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_500_v, Object> COLUMN_UPC_TU = new org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_500_v, Object>(I_EDI_cctop_invoic_500_v.class, "UPC_TU", null);
    /** Column name UPC_TU */
    public static final String COLUMNNAME_UPC_TU = "UPC_TU";
=======
	@Nullable java.lang.String getUPC_TU();

	ModelColumn<I_EDI_cctop_invoic_500_v, Object> COLUMN_UPC_TU = new ModelColumn<>(I_EDI_cctop_invoic_500_v.class, "UPC_TU", null);
	String COLUMNNAME_UPC_TU = "UPC_TU";
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
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_500_v, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_500_v, Object>(I_EDI_cctop_invoic_500_v.class, "Updated", null);
    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";
=======
	java.sql.Timestamp getUpdated();

	ModelColumn<I_EDI_cctop_invoic_500_v, Object> COLUMN_Updated = new ModelColumn<>(I_EDI_cctop_invoic_500_v.class, "Updated", null);
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

	/**
	 * Set Search Key.
	 * Search key for the record in the format required - must be unique
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setValue (java.lang.String Value);
=======
	void setValue (@Nullable java.lang.String Value);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Get Search Key.
	 * Search key for the record in the format required - must be unique
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public java.lang.String getValue();

    /** Column definition for Value */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_500_v, Object> COLUMN_Value = new org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_500_v, Object>(I_EDI_cctop_invoic_500_v.class, "Value", null);
    /** Column name Value */
    public static final String COLUMNNAME_Value = "Value";

	/**
	 * Set Produkt-Nr. Geschäftspartner.
	 * Produkt-Nr. beim Geschäftspartner
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setVendorProductNo (java.lang.String VendorProductNo);

	/**
	 * Get Produkt-Nr. Geschäftspartner.
	 * Produkt-Nr. beim Geschäftspartner
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getVendorProductNo();

    /** Column definition for VendorProductNo */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_500_v, Object> COLUMN_VendorProductNo = new org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_500_v, Object>(I_EDI_cctop_invoic_500_v.class, "VendorProductNo", null);
    /** Column name VendorProductNo */
    public static final String COLUMNNAME_VendorProductNo = "VendorProductNo";
=======
	@Nullable java.lang.String getValue();

	ModelColumn<I_EDI_cctop_invoic_500_v, Object> COLUMN_Value = new ModelColumn<>(I_EDI_cctop_invoic_500_v.class, "Value", null);
	String COLUMNNAME_Value = "Value";
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
}
