package de.metas.esb.edi.model;

<<<<<<< HEAD
=======
import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

/** Generated Interface for EDI_cctop_111_v
 *  @author metasfresh (generated) 
 */
<<<<<<< HEAD
@SuppressWarnings("javadoc")
public interface I_EDI_cctop_111_v 
{

    /** TableName=EDI_cctop_111_v */
    public static final String Table_Name = "EDI_cctop_111_v";

    /** AD_Table_ID=540465 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);
=======
@SuppressWarnings("unused")
public interface I_EDI_cctop_111_v 
{

	String Table_Name = "EDI_cctop_111_v";

//	/** AD_Table_ID=540465 */
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
	 * Set Sales order.
	 * Order
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setC_Order_ID (int C_Order_ID);
=======
	void setC_Order_ID (int C_Order_ID);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Get Sales order.
	 * Order
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public int getC_Order_ID();

	public org.compiere.model.I_C_Order getC_Order();

	public void setC_Order(org.compiere.model.I_C_Order C_Order);

    /** Column definition for C_Order_ID */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_111_v, org.compiere.model.I_C_Order> COLUMN_C_Order_ID = new org.adempiere.model.ModelColumn<I_EDI_cctop_111_v, org.compiere.model.I_C_Order>(I_EDI_cctop_111_v.class, "C_Order_ID", org.compiere.model.I_C_Order.class);
    /** Column name C_Order_ID */
    public static final String COLUMNNAME_C_Order_ID = "C_Order_ID";
=======
	int getC_Order_ID();

	@Nullable org.compiere.model.I_C_Order getC_Order();

	void setC_Order(@Nullable org.compiere.model.I_C_Order C_Order);

	ModelColumn<I_EDI_cctop_111_v, org.compiere.model.I_C_Order> COLUMN_C_Order_ID = new ModelColumn<>(I_EDI_cctop_111_v.class, "C_Order_ID", org.compiere.model.I_C_Order.class);
	String COLUMNNAME_C_Order_ID = "C_Order_ID";
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
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_111_v, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_EDI_cctop_111_v, Object>(I_EDI_cctop_111_v.class, "Created", null);
    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";
=======
	java.sql.Timestamp getCreated();

	ModelColumn<I_EDI_cctop_111_v, Object> COLUMN_Created = new ModelColumn<>(I_EDI_cctop_111_v.class, "Created", null);
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
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Set Date.
	 * Date of Order
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setDateOrdered (java.sql.Timestamp DateOrdered);
=======
	void setDateOrdered (@Nullable java.sql.Timestamp DateOrdered);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Get Date.
	 * Date of Order
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public java.sql.Timestamp getDateOrdered();

    /** Column definition for DateOrdered */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_111_v, Object> COLUMN_DateOrdered = new org.adempiere.model.ModelColumn<I_EDI_cctop_111_v, Object>(I_EDI_cctop_111_v.class, "DateOrdered", null);
    /** Column name DateOrdered */
    public static final String COLUMNNAME_DateOrdered = "DateOrdered";
=======
	@Nullable java.sql.Timestamp getDateOrdered();

	ModelColumn<I_EDI_cctop_111_v, Object> COLUMN_DateOrdered = new ModelColumn<>(I_EDI_cctop_111_v.class, "DateOrdered", null);
	String COLUMNNAME_DateOrdered = "DateOrdered";
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Set EDI_cctop_111_v.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setEDI_cctop_111_v_ID (int EDI_cctop_111_v_ID);
=======
	void setEDI_cctop_111_v_ID (int EDI_cctop_111_v_ID);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Get EDI_cctop_111_v.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public int getEDI_cctop_111_v_ID();

    /** Column definition for EDI_cctop_111_v_ID */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_111_v, Object> COLUMN_EDI_cctop_111_v_ID = new org.adempiere.model.ModelColumn<I_EDI_cctop_111_v, Object>(I_EDI_cctop_111_v.class, "EDI_cctop_111_v_ID", null);
    /** Column name EDI_cctop_111_v_ID */
    public static final String COLUMNNAME_EDI_cctop_111_v_ID = "EDI_cctop_111_v_ID";
=======
	int getEDI_cctop_111_v_ID();

	ModelColumn<I_EDI_cctop_111_v, Object> COLUMN_EDI_cctop_111_v_ID = new ModelColumn<>(I_EDI_cctop_111_v.class, "EDI_cctop_111_v_ID", null);
	String COLUMNNAME_EDI_cctop_111_v_ID = "EDI_cctop_111_v_ID";
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
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_111_v, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_EDI_cctop_111_v, Object>(I_EDI_cctop_111_v.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";
=======
	boolean isActive();

	ModelColumn<I_EDI_cctop_111_v, Object> COLUMN_IsActive = new ModelColumn<>(I_EDI_cctop_111_v.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Set Shipment/ Receipt.
	 * Material Shipment Document
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setM_InOut_ID (int M_InOut_ID);
=======
	void setM_InOut_ID (int M_InOut_ID);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Get Shipment/ Receipt.
	 * Material Shipment Document
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public int getM_InOut_ID();

	public org.compiere.model.I_M_InOut getM_InOut();

	public void setM_InOut(org.compiere.model.I_M_InOut M_InOut);

    /** Column definition for M_InOut_ID */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_111_v, org.compiere.model.I_M_InOut> COLUMN_M_InOut_ID = new org.adempiere.model.ModelColumn<I_EDI_cctop_111_v, org.compiere.model.I_M_InOut>(I_EDI_cctop_111_v.class, "M_InOut_ID", org.compiere.model.I_M_InOut.class);
    /** Column name M_InOut_ID */
    public static final String COLUMNNAME_M_InOut_ID = "M_InOut_ID";

	/**
	 * Set Bewegungs-Datum.
	 * Datum, an dem eine Produkt in oder aus dem Bestand bewegt wurde
=======
	int getM_InOut_ID();

	@Nullable org.compiere.model.I_M_InOut getM_InOut();

	void setM_InOut(@Nullable org.compiere.model.I_M_InOut M_InOut);

	ModelColumn<I_EDI_cctop_111_v, org.compiere.model.I_M_InOut> COLUMN_M_InOut_ID = new ModelColumn<>(I_EDI_cctop_111_v.class, "M_InOut_ID", org.compiere.model.I_M_InOut.class);
	String COLUMNNAME_M_InOut_ID = "M_InOut_ID";

	/**
	 * Set Date.
	 * Date a product was moved in or out of inventory
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setMovementDate (java.sql.Timestamp MovementDate);

	/**
	 * Get Bewegungs-Datum.
	 * Datum, an dem eine Produkt in oder aus dem Bestand bewegt wurde
=======
	void setMovementDate (@Nullable java.sql.Timestamp MovementDate);

	/**
	 * Get Date.
	 * Date a product was moved in or out of inventory
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public java.sql.Timestamp getMovementDate();

    /** Column definition for MovementDate */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_111_v, Object> COLUMN_MovementDate = new org.adempiere.model.ModelColumn<I_EDI_cctop_111_v, Object>(I_EDI_cctop_111_v.class, "MovementDate", null);
    /** Column name MovementDate */
    public static final String COLUMNNAME_MovementDate = "MovementDate";
=======
	@Nullable java.sql.Timestamp getMovementDate();

	ModelColumn<I_EDI_cctop_111_v, Object> COLUMN_MovementDate = new ModelColumn<>(I_EDI_cctop_111_v.class, "MovementDate", null);
	String COLUMNNAME_MovementDate = "MovementDate";
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Set Order Reference.
	 * Transaction Reference Number (Sales Order, Purchase Order) of your Business Partner
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setPOReference (java.lang.String POReference);
=======
	void setPOReference (@Nullable java.lang.String POReference);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Get Order Reference.
	 * Transaction Reference Number (Sales Order, Purchase Order) of your Business Partner
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public java.lang.String getPOReference();

    /** Column definition for POReference */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_111_v, Object> COLUMN_POReference = new org.adempiere.model.ModelColumn<I_EDI_cctop_111_v, Object>(I_EDI_cctop_111_v.class, "POReference", null);
    /** Column name POReference */
    public static final String COLUMNNAME_POReference = "POReference";
=======
	@Nullable java.lang.String getPOReference();

	ModelColumn<I_EDI_cctop_111_v, Object> COLUMN_POReference = new ModelColumn<>(I_EDI_cctop_111_v.class, "POReference", null);
	String COLUMNNAME_POReference = "POReference";
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Set shipment_documentno.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setshipment_documentno (java.lang.String shipment_documentno);
=======
	void setShipment_DocumentNo (@Nullable java.lang.String Shipment_DocumentNo);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Get shipment_documentno.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public java.lang.String getshipment_documentno();

    /** Column definition for shipment_documentno */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_111_v, Object> COLUMN_shipment_documentno = new org.adempiere.model.ModelColumn<I_EDI_cctop_111_v, Object>(I_EDI_cctop_111_v.class, "shipment_documentno", null);
    /** Column name shipment_documentno */
    public static final String COLUMNNAME_shipment_documentno = "shipment_documentno";
=======
	@Nullable java.lang.String getShipment_DocumentNo();

	ModelColumn<I_EDI_cctop_111_v, Object> COLUMN_Shipment_DocumentNo = new ModelColumn<>(I_EDI_cctop_111_v.class, "Shipment_DocumentNo", null);
	String COLUMNNAME_Shipment_DocumentNo = "Shipment_DocumentNo";
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
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_111_v, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_EDI_cctop_111_v, Object>(I_EDI_cctop_111_v.class, "Updated", null);
    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";
=======
	java.sql.Timestamp getUpdated();

	ModelColumn<I_EDI_cctop_111_v, Object> COLUMN_Updated = new ModelColumn<>(I_EDI_cctop_111_v.class, "Updated", null);
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
