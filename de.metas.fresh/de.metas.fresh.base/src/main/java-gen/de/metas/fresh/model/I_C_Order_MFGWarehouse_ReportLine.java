package de.metas.fresh.model;


/** Generated Interface for C_Order_MFGWarehouse_ReportLine
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_C_Order_MFGWarehouse_ReportLine 
{

    /** TableName=C_Order_MFGWarehouse_ReportLine */
    public static final String Table_Name = "C_Order_MFGWarehouse_ReportLine";

    /** AD_Table_ID=540684 */
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
    public static final org.adempiere.model.ModelColumn<I_C_Order_MFGWarehouse_ReportLine, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_C_Order_MFGWarehouse_ReportLine, org.compiere.model.I_AD_Client>(I_C_Order_MFGWarehouse_ReportLine.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_C_Order_MFGWarehouse_ReportLine, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_C_Order_MFGWarehouse_ReportLine, org.compiere.model.I_AD_Org>(I_C_Order_MFGWarehouse_ReportLine.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Auftragsposition.
	 * Auftragsposition
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_OrderLine_ID (int C_OrderLine_ID);

	/**
	 * Get Auftragsposition.
	 * Auftragsposition
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_OrderLine_ID();

	public org.compiere.model.I_C_OrderLine getC_OrderLine();

	public void setC_OrderLine(org.compiere.model.I_C_OrderLine C_OrderLine);

    /** Column definition for C_OrderLine_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Order_MFGWarehouse_ReportLine, org.compiere.model.I_C_OrderLine> COLUMN_C_OrderLine_ID = new org.adempiere.model.ModelColumn<I_C_Order_MFGWarehouse_ReportLine, org.compiere.model.I_C_OrderLine>(I_C_Order_MFGWarehouse_ReportLine.class, "C_OrderLine_ID", org.compiere.model.I_C_OrderLine.class);
    /** Column name C_OrderLine_ID */
    public static final String COLUMNNAME_C_OrderLine_ID = "C_OrderLine_ID";

	/**
	 * Set Order / MFG Warehouse report.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_Order_MFGWarehouse_Report_ID (int C_Order_MFGWarehouse_Report_ID);

	/**
	 * Get Order / MFG Warehouse report.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_Order_MFGWarehouse_Report_ID();

	public de.metas.fresh.model.I_C_Order_MFGWarehouse_Report getC_Order_MFGWarehouse_Report();

	public void setC_Order_MFGWarehouse_Report(de.metas.fresh.model.I_C_Order_MFGWarehouse_Report C_Order_MFGWarehouse_Report);

    /** Column definition for C_Order_MFGWarehouse_Report_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Order_MFGWarehouse_ReportLine, de.metas.fresh.model.I_C_Order_MFGWarehouse_Report> COLUMN_C_Order_MFGWarehouse_Report_ID = new org.adempiere.model.ModelColumn<I_C_Order_MFGWarehouse_ReportLine, de.metas.fresh.model.I_C_Order_MFGWarehouse_Report>(I_C_Order_MFGWarehouse_ReportLine.class, "C_Order_MFGWarehouse_Report_ID", de.metas.fresh.model.I_C_Order_MFGWarehouse_Report.class);
    /** Column name C_Order_MFGWarehouse_Report_ID */
    public static final String COLUMNNAME_C_Order_MFGWarehouse_Report_ID = "C_Order_MFGWarehouse_Report_ID";

	/**
	 * Set Order / MFG Warehouse report line.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_Order_MFGWarehouse_ReportLine_ID (int C_Order_MFGWarehouse_ReportLine_ID);

	/**
	 * Get Order / MFG Warehouse report line.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_Order_MFGWarehouse_ReportLine_ID();

    /** Column definition for C_Order_MFGWarehouse_ReportLine_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Order_MFGWarehouse_ReportLine, Object> COLUMN_C_Order_MFGWarehouse_ReportLine_ID = new org.adempiere.model.ModelColumn<I_C_Order_MFGWarehouse_ReportLine, Object>(I_C_Order_MFGWarehouse_ReportLine.class, "C_Order_MFGWarehouse_ReportLine_ID", null);
    /** Column name C_Order_MFGWarehouse_ReportLine_ID */
    public static final String COLUMNNAME_C_Order_MFGWarehouse_ReportLine_ID = "C_Order_MFGWarehouse_ReportLine_ID";

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
    public static final org.adempiere.model.ModelColumn<I_C_Order_MFGWarehouse_ReportLine, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_C_Order_MFGWarehouse_ReportLine, Object>(I_C_Order_MFGWarehouse_ReportLine.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_Order_MFGWarehouse_ReportLine, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_C_Order_MFGWarehouse_ReportLine, org.compiere.model.I_AD_User>(I_C_Order_MFGWarehouse_ReportLine.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

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
    public static final org.adempiere.model.ModelColumn<I_C_Order_MFGWarehouse_ReportLine, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_C_Order_MFGWarehouse_ReportLine, Object>(I_C_Order_MFGWarehouse_ReportLine.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Produkt.
	 * Produkt, Leistung, Artikel
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_Product_ID (int M_Product_ID);

	/**
	 * Get Produkt.
	 * Produkt, Leistung, Artikel
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_Product_ID();

	public org.compiere.model.I_M_Product getM_Product();

	public void setM_Product(org.compiere.model.I_M_Product M_Product);

    /** Column definition for M_Product_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Order_MFGWarehouse_ReportLine, org.compiere.model.I_M_Product> COLUMN_M_Product_ID = new org.adempiere.model.ModelColumn<I_C_Order_MFGWarehouse_ReportLine, org.compiere.model.I_M_Product>(I_C_Order_MFGWarehouse_ReportLine.class, "M_Product_ID", org.compiere.model.I_M_Product.class);
    /** Column name M_Product_ID */
    public static final String COLUMNNAME_M_Product_ID = "M_Product_ID";

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
    public static final org.adempiere.model.ModelColumn<I_C_Order_MFGWarehouse_ReportLine, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_C_Order_MFGWarehouse_ReportLine, Object>(I_C_Order_MFGWarehouse_ReportLine.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_Order_MFGWarehouse_ReportLine, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_C_Order_MFGWarehouse_ReportLine, org.compiere.model.I_AD_User>(I_C_Order_MFGWarehouse_ReportLine.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
