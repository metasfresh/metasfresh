package de.metas.fresh.model;


/** Generated Interface for C_Order_MFGWarehouse_Report
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_C_Order_MFGWarehouse_Report 
{

    /** TableName=C_Order_MFGWarehouse_Report */
    public static final String Table_Name = "C_Order_MFGWarehouse_Report";

    /** AD_Table_ID=540683 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(3);

    /** Load Meta Data */

	/**
	 * Get Mandant.
	 * Mandant für diese Installation.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Client_ID();

	public org.compiere.model.I_AD_Client getAD_Client();

    /** Column definition for AD_Client_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Order_MFGWarehouse_Report, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_C_Order_MFGWarehouse_Report, org.compiere.model.I_AD_Client>(I_C_Order_MFGWarehouse_Report.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_C_Order_MFGWarehouse_Report, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_C_Order_MFGWarehouse_Report, org.compiere.model.I_AD_Org>(I_C_Order_MFGWarehouse_Report.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Verantwortlicher Benutzer.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_User_Responsible_ID (int AD_User_Responsible_ID);

	/**
	 * Get Verantwortlicher Benutzer.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_User_Responsible_ID();

	public org.compiere.model.I_AD_User getAD_User_Responsible();

	public void setAD_User_Responsible(org.compiere.model.I_AD_User AD_User_Responsible);

    /** Column definition for AD_User_Responsible_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Order_MFGWarehouse_Report, org.compiere.model.I_AD_User> COLUMN_AD_User_Responsible_ID = new org.adempiere.model.ModelColumn<I_C_Order_MFGWarehouse_Report, org.compiere.model.I_AD_User>(I_C_Order_MFGWarehouse_Report.class, "AD_User_Responsible_ID", org.compiere.model.I_AD_User.class);
    /** Column name AD_User_Responsible_ID */
    public static final String COLUMNNAME_AD_User_Responsible_ID = "AD_User_Responsible_ID";

	/**
	 * Set Geschäftspartner.
	 * Bezeichnet einen Geschäftspartner
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_BPartner_ID (int C_BPartner_ID);

	/**
	 * Get Geschäftspartner.
	 * Bezeichnet einen Geschäftspartner
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_BPartner_ID();

	public org.compiere.model.I_C_BPartner getC_BPartner();

	public void setC_BPartner(org.compiere.model.I_C_BPartner C_BPartner);

    /** Column definition for C_BPartner_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Order_MFGWarehouse_Report, org.compiere.model.I_C_BPartner> COLUMN_C_BPartner_ID = new org.adempiere.model.ModelColumn<I_C_Order_MFGWarehouse_Report, org.compiere.model.I_C_BPartner>(I_C_Order_MFGWarehouse_Report.class, "C_BPartner_ID", org.compiere.model.I_C_BPartner.class);
    /** Column name C_BPartner_ID */
    public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/**
	 * Set Auftrag.
	 * Auftrag
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_Order_ID (int C_Order_ID);

	/**
	 * Get Auftrag.
	 * Auftrag
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_Order_ID();

	public org.compiere.model.I_C_Order getC_Order();

	public void setC_Order(org.compiere.model.I_C_Order C_Order);

    /** Column definition for C_Order_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Order_MFGWarehouse_Report, org.compiere.model.I_C_Order> COLUMN_C_Order_ID = new org.adempiere.model.ModelColumn<I_C_Order_MFGWarehouse_Report, org.compiere.model.I_C_Order>(I_C_Order_MFGWarehouse_Report.class, "C_Order_ID", org.compiere.model.I_C_Order.class);
    /** Column name C_Order_ID */
    public static final String COLUMNNAME_C_Order_ID = "C_Order_ID";

	/**
	 * Set Order / MFG Warehouse report.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_Order_MFGWarehouse_Report_ID (int C_Order_MFGWarehouse_Report_ID);

	/**
	 * Get Order / MFG Warehouse report.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_Order_MFGWarehouse_Report_ID();

    /** Column definition for C_Order_MFGWarehouse_Report_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Order_MFGWarehouse_Report, Object> COLUMN_C_Order_MFGWarehouse_Report_ID = new org.adempiere.model.ModelColumn<I_C_Order_MFGWarehouse_Report, Object>(I_C_Order_MFGWarehouse_Report.class, "C_Order_MFGWarehouse_Report_ID", null);
    /** Column name C_Order_MFGWarehouse_Report_ID */
    public static final String COLUMNNAME_C_Order_MFGWarehouse_Report_ID = "C_Order_MFGWarehouse_Report_ID";

	/**
	 * Get Erstellt.
	 * Datum, an dem dieser Eintrag erstellt wurde
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getCreated();

    /** Column definition for Created */
    public static final org.adempiere.model.ModelColumn<I_C_Order_MFGWarehouse_Report, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_C_Order_MFGWarehouse_Report, Object>(I_C_Order_MFGWarehouse_Report.class, "Created", null);
    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/**
	 * Get Erstellt durch.
	 * Nutzer, der diesen Eintrag erstellt hat
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getCreatedBy();

    /** Column definition for CreatedBy */
    public static final org.adempiere.model.ModelColumn<I_C_Order_MFGWarehouse_Report, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_C_Order_MFGWarehouse_Report, org.compiere.model.I_AD_User>(I_C_Order_MFGWarehouse_Report.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Belegart.
	 * Belegart
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDocumentType (java.lang.String DocumentType);

	/**
	 * Get Belegart.
	 * Belegart
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDocumentType();

    /** Column definition for DocumentType */
    public static final org.adempiere.model.ModelColumn<I_C_Order_MFGWarehouse_Report, Object> COLUMN_DocumentType = new org.adempiere.model.ModelColumn<I_C_Order_MFGWarehouse_Report, Object>(I_C_Order_MFGWarehouse_Report.class, "DocumentType", null);
    /** Column name DocumentType */
    public static final String COLUMNNAME_DocumentType = "DocumentType";

	/**
	 * Set Aktiv.
	 * Der Eintrag ist im System aktiv
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsActive (boolean IsActive);

	/**
	 * Get Aktiv.
	 * Der Eintrag ist im System aktiv
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isActive();

    /** Column definition for IsActive */
    public static final org.adempiere.model.ModelColumn<I_C_Order_MFGWarehouse_Report, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_C_Order_MFGWarehouse_Report, Object>(I_C_Order_MFGWarehouse_Report.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Lager.
	 * Lager oder Ort für Dienstleistung
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_Warehouse_ID (int M_Warehouse_ID);

	/**
	 * Get Lager.
	 * Lager oder Ort für Dienstleistung
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_Warehouse_ID();

	public org.compiere.model.I_M_Warehouse getM_Warehouse();

	public void setM_Warehouse(org.compiere.model.I_M_Warehouse M_Warehouse);

    /** Column definition for M_Warehouse_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Order_MFGWarehouse_Report, org.compiere.model.I_M_Warehouse> COLUMN_M_Warehouse_ID = new org.adempiere.model.ModelColumn<I_C_Order_MFGWarehouse_Report, org.compiere.model.I_M_Warehouse>(I_C_Order_MFGWarehouse_Report.class, "M_Warehouse_ID", org.compiere.model.I_M_Warehouse.class);
    /** Column name M_Warehouse_ID */
    public static final String COLUMNNAME_M_Warehouse_ID = "M_Warehouse_ID";

	/**
	 * Set Produktionsstätte.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPP_Plant_ID (int PP_Plant_ID);

	/**
	 * Get Produktionsstätte.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getPP_Plant_ID();

	public org.compiere.model.I_S_Resource getPP_Plant();

	public void setPP_Plant(org.compiere.model.I_S_Resource PP_Plant);

    /** Column definition for PP_Plant_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Order_MFGWarehouse_Report, org.compiere.model.I_S_Resource> COLUMN_PP_Plant_ID = new org.adempiere.model.ModelColumn<I_C_Order_MFGWarehouse_Report, org.compiere.model.I_S_Resource>(I_C_Order_MFGWarehouse_Report.class, "PP_Plant_ID", org.compiere.model.I_S_Resource.class);
    /** Column name PP_Plant_ID */
    public static final String COLUMNNAME_PP_Plant_ID = "PP_Plant_ID";

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
    public static final org.adempiere.model.ModelColumn<I_C_Order_MFGWarehouse_Report, Object> COLUMN_Processed = new org.adempiere.model.ModelColumn<I_C_Order_MFGWarehouse_Report, Object>(I_C_Order_MFGWarehouse_Report.class, "Processed", null);
    /** Column name Processed */
    public static final String COLUMNNAME_Processed = "Processed";

	/**
	 * Get Aktualisiert.
	 * Datum, an dem dieser Eintrag aktualisiert wurde
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getUpdated();

    /** Column definition for Updated */
    public static final org.adempiere.model.ModelColumn<I_C_Order_MFGWarehouse_Report, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_C_Order_MFGWarehouse_Report, Object>(I_C_Order_MFGWarehouse_Report.class, "Updated", null);
    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Aktualisiert durch.
	 * Nutzer, der diesen Eintrag aktualisiert hat
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getUpdatedBy();

    /** Column definition for UpdatedBy */
    public static final org.adempiere.model.ModelColumn<I_C_Order_MFGWarehouse_Report, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_C_Order_MFGWarehouse_Report, org.compiere.model.I_AD_User>(I_C_Order_MFGWarehouse_Report.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
