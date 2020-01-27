package de.metas.handlingunits.model;


/** Generated Interface for M_Picking_Candidate_IssueToOrder
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_M_Picking_Candidate_IssueToOrder 
{

    /** TableName=M_Picking_Candidate_IssueToOrder */
    public static final String Table_Name = "M_Picking_Candidate_IssueToOrder";

    /** AD_Table_ID=541430 */
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

    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Org_ID();

    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Maßeinheit.
	 * Maßeinheit
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_UOM_ID (int C_UOM_ID);

	/**
	 * Get Maßeinheit.
	 * Maßeinheit
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_UOM_ID();

    /** Column name C_UOM_ID */
    public static final String COLUMNNAME_C_UOM_ID = "C_UOM_ID";

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
    public static final org.adempiere.model.ModelColumn<I_M_Picking_Candidate_IssueToOrder, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_M_Picking_Candidate_IssueToOrder, Object>(I_M_Picking_Candidate_IssueToOrder.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_M_Picking_Candidate_IssueToOrder, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_M_Picking_Candidate_IssueToOrder, Object>(I_M_Picking_Candidate_IssueToOrder.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Handling Unit.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_HU_ID (int M_HU_ID);

	/**
	 * Get Handling Unit.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_HU_ID();

	public de.metas.handlingunits.model.I_M_HU getM_HU();

	public void setM_HU(de.metas.handlingunits.model.I_M_HU M_HU);

    /** Column definition for M_HU_ID */
    public static final org.adempiere.model.ModelColumn<I_M_Picking_Candidate_IssueToOrder, de.metas.handlingunits.model.I_M_HU> COLUMN_M_HU_ID = new org.adempiere.model.ModelColumn<I_M_Picking_Candidate_IssueToOrder, de.metas.handlingunits.model.I_M_HU>(I_M_Picking_Candidate_IssueToOrder.class, "M_HU_ID", de.metas.handlingunits.model.I_M_HU.class);
    /** Column name M_HU_ID */
    public static final String COLUMNNAME_M_HU_ID = "M_HU_ID";

	/**
	 * Set Picking candidate.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_Picking_Candidate_ID (int M_Picking_Candidate_ID);

	/**
	 * Get Picking candidate.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_Picking_Candidate_ID();

	public de.metas.handlingunits.model.I_M_Picking_Candidate getM_Picking_Candidate();

	public void setM_Picking_Candidate(de.metas.handlingunits.model.I_M_Picking_Candidate M_Picking_Candidate);

    /** Column definition for M_Picking_Candidate_ID */
    public static final org.adempiere.model.ModelColumn<I_M_Picking_Candidate_IssueToOrder, de.metas.handlingunits.model.I_M_Picking_Candidate> COLUMN_M_Picking_Candidate_ID = new org.adempiere.model.ModelColumn<I_M_Picking_Candidate_IssueToOrder, de.metas.handlingunits.model.I_M_Picking_Candidate>(I_M_Picking_Candidate_IssueToOrder.class, "M_Picking_Candidate_ID", de.metas.handlingunits.model.I_M_Picking_Candidate.class);
    /** Column name M_Picking_Candidate_ID */
    public static final String COLUMNNAME_M_Picking_Candidate_ID = "M_Picking_Candidate_ID";

	/**
	 * Set Picking Candidate - issue to manufacturing order.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_Picking_Candidate_IssueToOrder_ID (int M_Picking_Candidate_IssueToOrder_ID);

	/**
	 * Get Picking Candidate - issue to manufacturing order.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_Picking_Candidate_IssueToOrder_ID();

    /** Column definition for M_Picking_Candidate_IssueToOrder_ID */
    public static final org.adempiere.model.ModelColumn<I_M_Picking_Candidate_IssueToOrder, Object> COLUMN_M_Picking_Candidate_IssueToOrder_ID = new org.adempiere.model.ModelColumn<I_M_Picking_Candidate_IssueToOrder, Object>(I_M_Picking_Candidate_IssueToOrder.class, "M_Picking_Candidate_IssueToOrder_ID", null);
    /** Column name M_Picking_Candidate_IssueToOrder_ID */
    public static final String COLUMNNAME_M_Picking_Candidate_IssueToOrder_ID = "M_Picking_Candidate_IssueToOrder_ID";

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

    /** Column name M_Product_ID */
    public static final String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/**
	 * Set Manufacturing Cost Collector.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPP_Cost_Collector_ID (int PP_Cost_Collector_ID);

	/**
	 * Get Manufacturing Cost Collector.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getPP_Cost_Collector_ID();

	public org.eevolution.model.I_PP_Cost_Collector getPP_Cost_Collector();

	public void setPP_Cost_Collector(org.eevolution.model.I_PP_Cost_Collector PP_Cost_Collector);

    /** Column definition for PP_Cost_Collector_ID */
    public static final org.adempiere.model.ModelColumn<I_M_Picking_Candidate_IssueToOrder, org.eevolution.model.I_PP_Cost_Collector> COLUMN_PP_Cost_Collector_ID = new org.adempiere.model.ModelColumn<I_M_Picking_Candidate_IssueToOrder, org.eevolution.model.I_PP_Cost_Collector>(I_M_Picking_Candidate_IssueToOrder.class, "PP_Cost_Collector_ID", org.eevolution.model.I_PP_Cost_Collector.class);
    /** Column name PP_Cost_Collector_ID */
    public static final String COLUMNNAME_PP_Cost_Collector_ID = "PP_Cost_Collector_ID";

	/**
	 * Set Manufacturing Order BOM Line.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setPP_Order_BOMLine_ID (int PP_Order_BOMLine_ID);

	/**
	 * Get Manufacturing Order BOM Line.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getPP_Order_BOMLine_ID();

	public org.eevolution.model.I_PP_Order_BOMLine getPP_Order_BOMLine();

	public void setPP_Order_BOMLine(org.eevolution.model.I_PP_Order_BOMLine PP_Order_BOMLine);

    /** Column definition for PP_Order_BOMLine_ID */
    public static final org.adempiere.model.ModelColumn<I_M_Picking_Candidate_IssueToOrder, org.eevolution.model.I_PP_Order_BOMLine> COLUMN_PP_Order_BOMLine_ID = new org.adempiere.model.ModelColumn<I_M_Picking_Candidate_IssueToOrder, org.eevolution.model.I_PP_Order_BOMLine>(I_M_Picking_Candidate_IssueToOrder.class, "PP_Order_BOMLine_ID", org.eevolution.model.I_PP_Order_BOMLine.class);
    /** Column name PP_Order_BOMLine_ID */
    public static final String COLUMNNAME_PP_Order_BOMLine_ID = "PP_Order_BOMLine_ID";

	/**
	 * Set Quantity To Issue.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setQtyToIssue (java.math.BigDecimal QtyToIssue);

	/**
	 * Get Quantity To Issue.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyToIssue();

    /** Column definition for QtyToIssue */
    public static final org.adempiere.model.ModelColumn<I_M_Picking_Candidate_IssueToOrder, Object> COLUMN_QtyToIssue = new org.adempiere.model.ModelColumn<I_M_Picking_Candidate_IssueToOrder, Object>(I_M_Picking_Candidate_IssueToOrder.class, "QtyToIssue", null);
    /** Column name QtyToIssue */
    public static final String COLUMNNAME_QtyToIssue = "QtyToIssue";

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
    public static final org.adempiere.model.ModelColumn<I_M_Picking_Candidate_IssueToOrder, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_M_Picking_Candidate_IssueToOrder, Object>(I_M_Picking_Candidate_IssueToOrder.class, "Updated", null);
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

    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
