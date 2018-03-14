package de.metas.ordercandidate.model;


/** Generated Interface for C_OLCandAggAndOrder
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_C_OLCandAggAndOrder 
{

    /** TableName=C_OLCandAggAndOrder */
    public static final String Table_Name = "C_OLCandAggAndOrder";

    /** AD_Table_ID=540246 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 1 - Org
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(1);

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
    public static final org.adempiere.model.ModelColumn<I_C_OLCandAggAndOrder, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_C_OLCandAggAndOrder, org.compiere.model.I_AD_Client>(I_C_OLCandAggAndOrder.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Auftragskand. Spalte.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Column_OLCand_ID (int AD_Column_OLCand_ID);

	/**
	 * Get Auftragskand. Spalte.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Column_OLCand_ID();

	public org.compiere.model.I_AD_Column getAD_Column_OLCand();

	public void setAD_Column_OLCand(org.compiere.model.I_AD_Column AD_Column_OLCand);

    /** Column definition for AD_Column_OLCand_ID */
    public static final org.adempiere.model.ModelColumn<I_C_OLCandAggAndOrder, org.compiere.model.I_AD_Column> COLUMN_AD_Column_OLCand_ID = new org.adempiere.model.ModelColumn<I_C_OLCandAggAndOrder, org.compiere.model.I_AD_Column>(I_C_OLCandAggAndOrder.class, "AD_Column_OLCand_ID", org.compiere.model.I_AD_Column.class);
    /** Column name AD_Column_OLCand_ID */
    public static final String COLUMNNAME_AD_Column_OLCand_ID = "AD_Column_OLCand_ID";

	/**
	 * Set Eingabequelle.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_InputDataSource_ID (int AD_InputDataSource_ID);

	/**
	 * Get Eingabequelle.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_InputDataSource_ID();

    /** Column definition for AD_InputDataSource_ID */
    public static final org.adempiere.model.ModelColumn<I_C_OLCandAggAndOrder, Object> COLUMN_AD_InputDataSource_ID = new org.adempiere.model.ModelColumn<I_C_OLCandAggAndOrder, Object>(I_C_OLCandAggAndOrder.class, "AD_InputDataSource_ID", null);
    /** Column name AD_InputDataSource_ID */
    public static final String COLUMNNAME_AD_InputDataSource_ID = "AD_InputDataSource_ID";

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
    public static final org.adempiere.model.ModelColumn<I_C_OLCandAggAndOrder, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_C_OLCandAggAndOrder, org.compiere.model.I_AD_Org>(I_C_OLCandAggAndOrder.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Referenz-ID der Auftragskand. Spalte.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	public void setAD_Reference_OLCand_ID (int AD_Reference_OLCand_ID);

	/**
	 * Get Referenz-ID der Auftragskand. Spalte.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	public int getAD_Reference_OLCand_ID();

    /** Column definition for AD_Reference_OLCand_ID */
    public static final org.adempiere.model.ModelColumn<I_C_OLCandAggAndOrder, Object> COLUMN_AD_Reference_OLCand_ID = new org.adempiere.model.ModelColumn<I_C_OLCandAggAndOrder, Object>(I_C_OLCandAggAndOrder.class, "AD_Reference_OLCand_ID", null);
    /** Column name AD_Reference_OLCand_ID */
    public static final String COLUMNNAME_AD_Reference_OLCand_ID = "AD_Reference_OLCand_ID";

	/**
	 * Set Auftragskand. Aggregieren.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_OLCandAggAndOrder_ID (int C_OLCandAggAndOrder_ID);

	/**
	 * Get Auftragskand. Aggregieren.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_OLCandAggAndOrder_ID();

    /** Column definition for C_OLCandAggAndOrder_ID */
    public static final org.adempiere.model.ModelColumn<I_C_OLCandAggAndOrder, Object> COLUMN_C_OLCandAggAndOrder_ID = new org.adempiere.model.ModelColumn<I_C_OLCandAggAndOrder, Object>(I_C_OLCandAggAndOrder.class, "C_OLCandAggAndOrder_ID", null);
    /** Column name C_OLCandAggAndOrder_ID */
    public static final String COLUMNNAME_C_OLCandAggAndOrder_ID = "C_OLCandAggAndOrder_ID";

	/**
	 * Set Auftragskand. Verarb..
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_OLCandProcessor_ID (int C_OLCandProcessor_ID);

	/**
	 * Get Auftragskand. Verarb..
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_OLCandProcessor_ID();

	public de.metas.ordercandidate.model.I_C_OLCandProcessor getC_OLCandProcessor();

	public void setC_OLCandProcessor(de.metas.ordercandidate.model.I_C_OLCandProcessor C_OLCandProcessor);

    /** Column definition for C_OLCandProcessor_ID */
    public static final org.adempiere.model.ModelColumn<I_C_OLCandAggAndOrder, de.metas.ordercandidate.model.I_C_OLCandProcessor> COLUMN_C_OLCandProcessor_ID = new org.adempiere.model.ModelColumn<I_C_OLCandAggAndOrder, de.metas.ordercandidate.model.I_C_OLCandProcessor>(I_C_OLCandAggAndOrder.class, "C_OLCandProcessor_ID", de.metas.ordercandidate.model.I_C_OLCandProcessor.class);
    /** Column name C_OLCandProcessor_ID */
    public static final String COLUMNNAME_C_OLCandProcessor_ID = "C_OLCandProcessor_ID";

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
    public static final org.adempiere.model.ModelColumn<I_C_OLCandAggAndOrder, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_C_OLCandAggAndOrder, Object>(I_C_OLCandAggAndOrder.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_OLCandAggAndOrder, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_C_OLCandAggAndOrder, org.compiere.model.I_AD_User>(I_C_OLCandAggAndOrder.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Beschreibung.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDescription (java.lang.String Description);

	/**
	 * Get Beschreibung.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDescription();

    /** Column definition for Description */
    public static final org.adempiere.model.ModelColumn<I_C_OLCandAggAndOrder, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_C_OLCandAggAndOrder, Object>(I_C_OLCandAggAndOrder.class, "Description", null);
    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/**
	 * Set Agg.-Ebene.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setGranularity (java.lang.String Granularity);

	/**
	 * Get Agg.-Ebene.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getGranularity();

    /** Column definition for Granularity */
    public static final org.adempiere.model.ModelColumn<I_C_OLCandAggAndOrder, Object> COLUMN_Granularity = new org.adempiere.model.ModelColumn<I_C_OLCandAggAndOrder, Object>(I_C_OLCandAggAndOrder.class, "Granularity", null);
    /** Column name Granularity */
    public static final String COLUMNNAME_Granularity = "Granularity";

	/**
	 * Set Aggregieren.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setGroupBy (boolean GroupBy);

	/**
	 * Get Aggregieren.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isGroupBy();

    /** Column definition for GroupBy */
    public static final org.adempiere.model.ModelColumn<I_C_OLCandAggAndOrder, Object> COLUMN_GroupBy = new org.adempiere.model.ModelColumn<I_C_OLCandAggAndOrder, Object>(I_C_OLCandAggAndOrder.class, "GroupBy", null);
    /** Column name GroupBy */
    public static final String COLUMNNAME_GroupBy = "GroupBy";

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
    public static final org.adempiere.model.ModelColumn<I_C_OLCandAggAndOrder, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_C_OLCandAggAndOrder, Object>(I_C_OLCandAggAndOrder.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Sortier-Reihenfolge.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setOrderBySeqNo (int OrderBySeqNo);

	/**
	 * Get Sortier-Reihenfolge.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getOrderBySeqNo();

    /** Column definition for OrderBySeqNo */
    public static final org.adempiere.model.ModelColumn<I_C_OLCandAggAndOrder, Object> COLUMN_OrderBySeqNo = new org.adempiere.model.ModelColumn<I_C_OLCandAggAndOrder, Object>(I_C_OLCandAggAndOrder.class, "OrderBySeqNo", null);
    /** Column name OrderBySeqNo */
    public static final String COLUMNNAME_OrderBySeqNo = "OrderBySeqNo";

	/**
	 * Set In eig. Auftrag.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setSplitOrder (boolean SplitOrder);

	/**
	 * Get In eig. Auftrag.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isSplitOrder();

    /** Column definition for SplitOrder */
    public static final org.adempiere.model.ModelColumn<I_C_OLCandAggAndOrder, Object> COLUMN_SplitOrder = new org.adempiere.model.ModelColumn<I_C_OLCandAggAndOrder, Object>(I_C_OLCandAggAndOrder.class, "SplitOrder", null);
    /** Column name SplitOrder */
    public static final String COLUMNNAME_SplitOrder = "SplitOrder";

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
    public static final org.adempiere.model.ModelColumn<I_C_OLCandAggAndOrder, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_C_OLCandAggAndOrder, Object>(I_C_OLCandAggAndOrder.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_OLCandAggAndOrder, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_C_OLCandAggAndOrder, org.compiere.model.I_AD_User>(I_C_OLCandAggAndOrder.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
