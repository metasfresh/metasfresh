package de.metas.handlingunits.model;


/** Generated Interface for M_HU_Trace
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_M_HU_Trace 
{

    /** TableName=M_HU_Trace */
    public static final String Table_Name = "M_HU_Trace";

    /** AD_Table_ID=540832 */
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
    public static final org.adempiere.model.ModelColumn<I_M_HU_Trace, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_M_HU_Trace, org.compiere.model.I_AD_Client>(I_M_HU_Trace.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_M_HU_Trace, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_M_HU_Trace, org.compiere.model.I_AD_Org>(I_M_HU_Trace.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Belegart.
	 * Belegart oder Verarbeitungsvorgaben
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_DocType_ID (int C_DocType_ID);

	/**
	 * Get Belegart.
	 * Belegart oder Verarbeitungsvorgaben
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_DocType_ID();

	public org.compiere.model.I_C_DocType getC_DocType();

	public void setC_DocType(org.compiere.model.I_C_DocType C_DocType);

    /** Column definition for C_DocType_ID */
    public static final org.adempiere.model.ModelColumn<I_M_HU_Trace, org.compiere.model.I_C_DocType> COLUMN_C_DocType_ID = new org.adempiere.model.ModelColumn<I_M_HU_Trace, org.compiere.model.I_C_DocType>(I_M_HU_Trace.class, "C_DocType_ID", org.compiere.model.I_C_DocType.class);
    /** Column name C_DocType_ID */
    public static final String COLUMNNAME_C_DocType_ID = "C_DocType_ID";

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
    public static final org.adempiere.model.ModelColumn<I_M_HU_Trace, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_M_HU_Trace, Object>(I_M_HU_Trace.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_M_HU_Trace, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_M_HU_Trace, org.compiere.model.I_AD_User>(I_M_HU_Trace.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Belegstatus.
	 * The current status of the document
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDocStatus (java.lang.String DocStatus);

	/**
	 * Get Belegstatus.
	 * The current status of the document
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDocStatus();

    /** Column definition for DocStatus */
    public static final org.adempiere.model.ModelColumn<I_M_HU_Trace, Object> COLUMN_DocStatus = new org.adempiere.model.ModelColumn<I_M_HU_Trace, Object>(I_M_HU_Trace.class, "DocStatus", null);
    /** Column name DocStatus */
    public static final String COLUMNNAME_DocStatus = "DocStatus";

	/**
	 * Set Zeitpunkt.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setEventTime (java.sql.Timestamp EventTime);

	/**
	 * Get Zeitpunkt.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getEventTime();

    /** Column definition for EventTime */
    public static final org.adempiere.model.ModelColumn<I_M_HU_Trace, Object> COLUMN_EventTime = new org.adempiere.model.ModelColumn<I_M_HU_Trace, Object>(I_M_HU_Trace.class, "EventTime", null);
    /** Column name EventTime */
    public static final String COLUMNNAME_EventTime = "EventTime";

	/**
	 * Set Typ.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setHUTraceType (java.lang.String HUTraceType);

	/**
	 * Get Typ.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getHUTraceType();

    /** Column definition for HUTraceType */
    public static final org.adempiere.model.ModelColumn<I_M_HU_Trace, Object> COLUMN_HUTraceType = new org.adempiere.model.ModelColumn<I_M_HU_Trace, Object>(I_M_HU_Trace.class, "HUTraceType", null);
    /** Column name HUTraceType */
    public static final String COLUMNNAME_HUTraceType = "HUTraceType";

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
    public static final org.adempiere.model.ModelColumn<I_M_HU_Trace, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_M_HU_Trace, Object>(I_M_HU_Trace.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Handling Units.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_HU_ID (int M_HU_ID);

	/**
	 * Get Handling Units.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_HU_ID();

	public de.metas.handlingunits.model.I_M_HU getM_HU();

	public void setM_HU(de.metas.handlingunits.model.I_M_HU M_HU);

    /** Column definition for M_HU_ID */
    public static final org.adempiere.model.ModelColumn<I_M_HU_Trace, de.metas.handlingunits.model.I_M_HU> COLUMN_M_HU_ID = new org.adempiere.model.ModelColumn<I_M_HU_Trace, de.metas.handlingunits.model.I_M_HU>(I_M_HU_Trace.class, "M_HU_ID", de.metas.handlingunits.model.I_M_HU.class);
    /** Column name M_HU_ID */
    public static final String COLUMNNAME_M_HU_ID = "M_HU_ID";

	/**
	 * Set Rückverfolgbarkeit.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_HU_Trace_ID (int M_HU_Trace_ID);

	/**
	 * Get Rückverfolgbarkeit.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_HU_Trace_ID();

    /** Column definition for M_HU_Trace_ID */
    public static final org.adempiere.model.ModelColumn<I_M_HU_Trace, Object> COLUMN_M_HU_Trace_ID = new org.adempiere.model.ModelColumn<I_M_HU_Trace, Object>(I_M_HU_Trace.class, "M_HU_Trace_ID", null);
    /** Column name M_HU_Trace_ID */
    public static final String COLUMNNAME_M_HU_Trace_ID = "M_HU_Trace_ID";

	/**
	 * Set HU-Transaktionszeile.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_HU_Trx_Line_ID (int M_HU_Trx_Line_ID);

	/**
	 * Get HU-Transaktionszeile.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_HU_Trx_Line_ID();

	public de.metas.handlingunits.model.I_M_HU_Trx_Line getM_HU_Trx_Line();

	public void setM_HU_Trx_Line(de.metas.handlingunits.model.I_M_HU_Trx_Line M_HU_Trx_Line);

    /** Column definition for M_HU_Trx_Line_ID */
    public static final org.adempiere.model.ModelColumn<I_M_HU_Trace, de.metas.handlingunits.model.I_M_HU_Trx_Line> COLUMN_M_HU_Trx_Line_ID = new org.adempiere.model.ModelColumn<I_M_HU_Trace, de.metas.handlingunits.model.I_M_HU_Trx_Line>(I_M_HU_Trace.class, "M_HU_Trx_Line_ID", de.metas.handlingunits.model.I_M_HU_Trx_Line.class);
    /** Column name M_HU_Trx_Line_ID */
    public static final String COLUMNNAME_M_HU_Trx_Line_ID = "M_HU_Trx_Line_ID";

	/**
	 * Set Lieferung/Wareneingang.
	 * Material Shipment Document
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_InOut_ID (int M_InOut_ID);

	/**
	 * Get Lieferung/Wareneingang.
	 * Material Shipment Document
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_InOut_ID();

	public org.compiere.model.I_M_InOut getM_InOut();

	public void setM_InOut(org.compiere.model.I_M_InOut M_InOut);

    /** Column definition for M_InOut_ID */
    public static final org.adempiere.model.ModelColumn<I_M_HU_Trace, org.compiere.model.I_M_InOut> COLUMN_M_InOut_ID = new org.adempiere.model.ModelColumn<I_M_HU_Trace, org.compiere.model.I_M_InOut>(I_M_HU_Trace.class, "M_InOut_ID", org.compiere.model.I_M_InOut.class);
    /** Column name M_InOut_ID */
    public static final String COLUMNNAME_M_InOut_ID = "M_InOut_ID";

	/**
	 * Set Warenbewegung.
	 * Bewegung von Warenbestand
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_Movement_ID (int M_Movement_ID);

	/**
	 * Get Warenbewegung.
	 * Bewegung von Warenbestand
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_Movement_ID();

	public org.compiere.model.I_M_Movement getM_Movement();

	public void setM_Movement(org.compiere.model.I_M_Movement M_Movement);

    /** Column definition for M_Movement_ID */
    public static final org.adempiere.model.ModelColumn<I_M_HU_Trace, org.compiere.model.I_M_Movement> COLUMN_M_Movement_ID = new org.adempiere.model.ModelColumn<I_M_HU_Trace, org.compiere.model.I_M_Movement>(I_M_HU_Trace.class, "M_Movement_ID", org.compiere.model.I_M_Movement.class);
    /** Column name M_Movement_ID */
    public static final String COLUMNNAME_M_Movement_ID = "M_Movement_ID";

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
    public static final org.adempiere.model.ModelColumn<I_M_HU_Trace, org.compiere.model.I_M_Product> COLUMN_M_Product_ID = new org.adempiere.model.ModelColumn<I_M_HU_Trace, org.compiere.model.I_M_Product>(I_M_HU_Trace.class, "M_Product_ID", org.compiere.model.I_M_Product.class);
    /** Column name M_Product_ID */
    public static final String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/**
	 * Set Lieferdisposition.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_ShipmentSchedule_ID (int M_ShipmentSchedule_ID);

	/**
	 * Get Lieferdisposition.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_ShipmentSchedule_ID();

    /** Column definition for M_ShipmentSchedule_ID */
    public static final org.adempiere.model.ModelColumn<I_M_HU_Trace, Object> COLUMN_M_ShipmentSchedule_ID = new org.adempiere.model.ModelColumn<I_M_HU_Trace, Object>(I_M_HU_Trace.class, "M_ShipmentSchedule_ID", null);
    /** Column name M_ShipmentSchedule_ID */
    public static final String COLUMNNAME_M_ShipmentSchedule_ID = "M_ShipmentSchedule_ID";

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
    public static final org.adempiere.model.ModelColumn<I_M_HU_Trace, org.eevolution.model.I_PP_Cost_Collector> COLUMN_PP_Cost_Collector_ID = new org.adempiere.model.ModelColumn<I_M_HU_Trace, org.eevolution.model.I_PP_Cost_Collector>(I_M_HU_Trace.class, "PP_Cost_Collector_ID", org.eevolution.model.I_PP_Cost_Collector.class);
    /** Column name PP_Cost_Collector_ID */
    public static final String COLUMNNAME_PP_Cost_Collector_ID = "PP_Cost_Collector_ID";

	/**
	 * Set Produktionsauftrag.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPP_Order_ID (int PP_Order_ID);

	/**
	 * Get Produktionsauftrag.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getPP_Order_ID();

	public org.eevolution.model.I_PP_Order getPP_Order();

	public void setPP_Order(org.eevolution.model.I_PP_Order PP_Order);

    /** Column definition for PP_Order_ID */
    public static final org.adempiere.model.ModelColumn<I_M_HU_Trace, org.eevolution.model.I_PP_Order> COLUMN_PP_Order_ID = new org.adempiere.model.ModelColumn<I_M_HU_Trace, org.eevolution.model.I_PP_Order>(I_M_HU_Trace.class, "PP_Order_ID", org.eevolution.model.I_PP_Order.class);
    /** Column name PP_Order_ID */
    public static final String COLUMNNAME_PP_Order_ID = "PP_Order_ID";

	/**
	 * Set Menge.
	 * Menge
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setQty (java.math.BigDecimal Qty);

	/**
	 * Get Menge.
	 * Menge
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQty();

    /** Column definition for Qty */
    public static final org.adempiere.model.ModelColumn<I_M_HU_Trace, Object> COLUMN_Qty = new org.adempiere.model.ModelColumn<I_M_HU_Trace, Object>(I_M_HU_Trace.class, "Qty", null);
    /** Column name Qty */
    public static final String COLUMNNAME_Qty = "Qty";

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
    public static final org.adempiere.model.ModelColumn<I_M_HU_Trace, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_M_HU_Trace, Object>(I_M_HU_Trace.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_M_HU_Trace, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_M_HU_Trace, org.compiere.model.I_AD_User>(I_M_HU_Trace.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/**
	 * Set CU Handling Unit (VHU).
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setVHU_ID (int VHU_ID);

	/**
	 * Get CU Handling Unit (VHU).
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getVHU_ID();

	public de.metas.handlingunits.model.I_M_HU getVHU();

	public void setVHU(de.metas.handlingunits.model.I_M_HU VHU);

    /** Column definition for VHU_ID */
    public static final org.adempiere.model.ModelColumn<I_M_HU_Trace, de.metas.handlingunits.model.I_M_HU> COLUMN_VHU_ID = new org.adempiere.model.ModelColumn<I_M_HU_Trace, de.metas.handlingunits.model.I_M_HU>(I_M_HU_Trace.class, "VHU_ID", de.metas.handlingunits.model.I_M_HU.class);
    /** Column name VHU_ID */
    public static final String COLUMNNAME_VHU_ID = "VHU_ID";

	/**
	 * Set Ursprungs-VHU.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setVHU_Source_ID (int VHU_Source_ID);

	/**
	 * Get Ursprungs-VHU.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getVHU_Source_ID();

	public de.metas.handlingunits.model.I_M_HU getVHU_Source();

	public void setVHU_Source(de.metas.handlingunits.model.I_M_HU VHU_Source);

    /** Column definition for VHU_Source_ID */
    public static final org.adempiere.model.ModelColumn<I_M_HU_Trace, de.metas.handlingunits.model.I_M_HU> COLUMN_VHU_Source_ID = new org.adempiere.model.ModelColumn<I_M_HU_Trace, de.metas.handlingunits.model.I_M_HU>(I_M_HU_Trace.class, "VHU_Source_ID", de.metas.handlingunits.model.I_M_HU.class);
    /** Column name VHU_Source_ID */
    public static final String COLUMNNAME_VHU_Source_ID = "VHU_Source_ID";

	/**
	 * Set CU (VHU) Gebindestatus.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setVHUStatus (java.lang.String VHUStatus);

	/**
	 * Get CU (VHU) Gebindestatus.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getVHUStatus();

    /** Column definition for VHUStatus */
    public static final org.adempiere.model.ModelColumn<I_M_HU_Trace, Object> COLUMN_VHUStatus = new org.adempiere.model.ModelColumn<I_M_HU_Trace, Object>(I_M_HU_Trace.class, "VHUStatus", null);
    /** Column name VHUStatus */
    public static final String COLUMNNAME_VHUStatus = "VHUStatus";
}
