/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2007 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software, you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY, without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program, if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package org.eevolution.model;


/** Generated Interface for PP_MRP
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_PP_MRP 
{

    /** TableName=PP_MRP */
    public static final String Table_Name = "PP_MRP";

    /** AD_Table_ID=53043 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org 
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(3);

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
    public static final org.adempiere.model.ModelColumn<I_PP_MRP, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_PP_MRP, org.compiere.model.I_AD_Client>(I_PP_MRP.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_PP_MRP, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_PP_MRP, org.compiere.model.I_AD_Org>(I_PP_MRP.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Geschäftspartner.
	 * Identifies a Business Partner
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_BPartner_ID (int C_BPartner_ID);

	/**
	 * Get Geschäftspartner.
	 * Identifies a Business Partner
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_BPartner_ID();

	public org.compiere.model.I_C_BPartner getC_BPartner();

	public void setC_BPartner(org.compiere.model.I_C_BPartner C_BPartner);

    /** Column definition for C_BPartner_ID */
    public static final org.adempiere.model.ModelColumn<I_PP_MRP, org.compiere.model.I_C_BPartner> COLUMN_C_BPartner_ID = new org.adempiere.model.ModelColumn<I_PP_MRP, org.compiere.model.I_C_BPartner>(I_PP_MRP.class, "C_BPartner_ID", org.compiere.model.I_C_BPartner.class);
    /** Column name C_BPartner_ID */
    public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/**
	 * Set Auftrag.
	 * Order
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Order_ID (int C_Order_ID);

	/**
	 * Get Auftrag.
	 * Order
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Order_ID();

	public org.compiere.model.I_C_Order getC_Order();

	public void setC_Order(org.compiere.model.I_C_Order C_Order);

    /** Column definition for C_Order_ID */
    public static final org.adempiere.model.ModelColumn<I_PP_MRP, org.compiere.model.I_C_Order> COLUMN_C_Order_ID = new org.adempiere.model.ModelColumn<I_PP_MRP, org.compiere.model.I_C_Order>(I_PP_MRP.class, "C_Order_ID", org.compiere.model.I_C_Order.class);
    /** Column name C_Order_ID */
    public static final String COLUMNNAME_C_Order_ID = "C_Order_ID";

	/**
	 * Set Auftragsposition.
	 * Sales Order Line
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_OrderLine_ID (int C_OrderLine_ID);

	/**
	 * Get Auftragsposition.
	 * Sales Order Line
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_OrderLine_ID();

	public org.compiere.model.I_C_OrderLine getC_OrderLine();

	public void setC_OrderLine(org.compiere.model.I_C_OrderLine C_OrderLine);

    /** Column definition for C_OrderLine_ID */
    public static final org.adempiere.model.ModelColumn<I_PP_MRP, org.compiere.model.I_C_OrderLine> COLUMN_C_OrderLine_ID = new org.adempiere.model.ModelColumn<I_PP_MRP, org.compiere.model.I_C_OrderLine>(I_PP_MRP.class, "C_OrderLine_ID", org.compiere.model.I_C_OrderLine.class);
    /** Column name C_OrderLine_ID */
    public static final String COLUMNNAME_C_OrderLine_ID = "C_OrderLine_ID";

	/**
	 * Set Auftragsposition.
	 * Auftragsposition
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_OrderLineSO_ID (int C_OrderLineSO_ID);

	/**
	 * Get Auftragsposition.
	 * Auftragsposition
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_OrderLineSO_ID();

	public org.compiere.model.I_C_OrderLine getC_OrderLineSO();

	public void setC_OrderLineSO(org.compiere.model.I_C_OrderLine C_OrderLineSO);

    /** Column definition for C_OrderLineSO_ID */
    public static final org.adempiere.model.ModelColumn<I_PP_MRP, org.compiere.model.I_C_OrderLine> COLUMN_C_OrderLineSO_ID = new org.adempiere.model.ModelColumn<I_PP_MRP, org.compiere.model.I_C_OrderLine>(I_PP_MRP.class, "C_OrderLineSO_ID", org.compiere.model.I_C_OrderLine.class);
    /** Column name C_OrderLineSO_ID */
    public static final String COLUMNNAME_C_OrderLineSO_ID = "C_OrderLineSO_ID";

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
    public static final org.adempiere.model.ModelColumn<I_PP_MRP, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_PP_MRP, Object>(I_PP_MRP.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_PP_MRP, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_PP_MRP, org.compiere.model.I_AD_User>(I_PP_MRP.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set DateConfirm.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDateConfirm (java.sql.Timestamp DateConfirm);

	/**
	 * Get DateConfirm.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getDateConfirm();

    /** Column definition for DateConfirm */
    public static final org.adempiere.model.ModelColumn<I_PP_MRP, Object> COLUMN_DateConfirm = new org.adempiere.model.ModelColumn<I_PP_MRP, Object>(I_PP_MRP.class, "DateConfirm", null);
    /** Column name DateConfirm */
    public static final String COLUMNNAME_DateConfirm = "DateConfirm";

	/**
	 * Set DateFinishSchedule.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDateFinishSchedule (java.sql.Timestamp DateFinishSchedule);

	/**
	 * Get DateFinishSchedule.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getDateFinishSchedule();

    /** Column definition for DateFinishSchedule */
    public static final org.adempiere.model.ModelColumn<I_PP_MRP, Object> COLUMN_DateFinishSchedule = new org.adempiere.model.ModelColumn<I_PP_MRP, Object>(I_PP_MRP.class, "DateFinishSchedule", null);
    /** Column name DateFinishSchedule */
    public static final String COLUMNNAME_DateFinishSchedule = "DateFinishSchedule";

	/**
	 * Set Auftragsdatum.
	 * Date of Order
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDateOrdered (java.sql.Timestamp DateOrdered);

	/**
	 * Get Auftragsdatum.
	 * Date of Order
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getDateOrdered();

    /** Column definition for DateOrdered */
    public static final org.adempiere.model.ModelColumn<I_PP_MRP, Object> COLUMN_DateOrdered = new org.adempiere.model.ModelColumn<I_PP_MRP, Object>(I_PP_MRP.class, "DateOrdered", null);
    /** Column name DateOrdered */
    public static final String COLUMNNAME_DateOrdered = "DateOrdered";

	/**
	 * Set Zugesagter Termin.
	 * Date Order was promised
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDatePromised (java.sql.Timestamp DatePromised);

	/**
	 * Get Zugesagter Termin.
	 * Date Order was promised
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getDatePromised();

    /** Column definition for DatePromised */
    public static final org.adempiere.model.ModelColumn<I_PP_MRP, Object> COLUMN_DatePromised = new org.adempiere.model.ModelColumn<I_PP_MRP, Object>(I_PP_MRP.class, "DatePromised", null);
    /** Column name DatePromised */
    public static final String COLUMNNAME_DatePromised = "DatePromised";

	/**
	 * Set DateSimulation.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDateSimulation (java.sql.Timestamp DateSimulation);

	/**
	 * Get DateSimulation.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getDateSimulation();

    /** Column definition for DateSimulation */
    public static final org.adempiere.model.ModelColumn<I_PP_MRP, Object> COLUMN_DateSimulation = new org.adempiere.model.ModelColumn<I_PP_MRP, Object>(I_PP_MRP.class, "DateSimulation", null);
    /** Column name DateSimulation */
    public static final String COLUMNNAME_DateSimulation = "DateSimulation";

	/**
	 * Set DateStart.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDateStart (java.sql.Timestamp DateStart);

	/**
	 * Get DateStart.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getDateStart();

    /** Column definition for DateStart */
    public static final org.adempiere.model.ModelColumn<I_PP_MRP, Object> COLUMN_DateStart = new org.adempiere.model.ModelColumn<I_PP_MRP, Object>(I_PP_MRP.class, "DateStart", null);
    /** Column name DateStart */
    public static final String COLUMNNAME_DateStart = "DateStart";

	/**
	 * Set DateStartSchedule.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDateStartSchedule (java.sql.Timestamp DateStartSchedule);

	/**
	 * Get DateStartSchedule.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getDateStartSchedule();

    /** Column definition for DateStartSchedule */
    public static final org.adempiere.model.ModelColumn<I_PP_MRP, Object> COLUMN_DateStartSchedule = new org.adempiere.model.ModelColumn<I_PP_MRP, Object>(I_PP_MRP.class, "DateStartSchedule", null);
    /** Column name DateStartSchedule */
    public static final String COLUMNNAME_DateStartSchedule = "DateStartSchedule";

	/**
	 * Set Distribution Order.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDD_Order_ID (int DD_Order_ID);

	/**
	 * Get Distribution Order.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getDD_Order_ID();

	public org.eevolution.model.I_DD_Order getDD_Order();

	public void setDD_Order(org.eevolution.model.I_DD_Order DD_Order);

    /** Column definition for DD_Order_ID */
    public static final org.adempiere.model.ModelColumn<I_PP_MRP, org.eevolution.model.I_DD_Order> COLUMN_DD_Order_ID = new org.adempiere.model.ModelColumn<I_PP_MRP, org.eevolution.model.I_DD_Order>(I_PP_MRP.class, "DD_Order_ID", org.eevolution.model.I_DD_Order.class);
    /** Column name DD_Order_ID */
    public static final String COLUMNNAME_DD_Order_ID = "DD_Order_ID";

	/**
	 * Set Distribution Order Line.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDD_OrderLine_ID (int DD_OrderLine_ID);

	/**
	 * Get Distribution Order Line.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getDD_OrderLine_ID();

	public org.eevolution.model.I_DD_OrderLine getDD_OrderLine();

	public void setDD_OrderLine(org.eevolution.model.I_DD_OrderLine DD_OrderLine);

    /** Column definition for DD_OrderLine_ID */
    public static final org.adempiere.model.ModelColumn<I_PP_MRP, org.eevolution.model.I_DD_OrderLine> COLUMN_DD_OrderLine_ID = new org.adempiere.model.ModelColumn<I_PP_MRP, org.eevolution.model.I_DD_OrderLine>(I_PP_MRP.class, "DD_OrderLine_ID", org.eevolution.model.I_DD_OrderLine.class);
    /** Column name DD_OrderLine_ID */
    public static final String COLUMNNAME_DD_OrderLine_ID = "DD_OrderLine_ID";

	/**
	 * Set Beschreibung.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDescription (java.lang.String Description);

	/**
	 * Get Beschreibung.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDescription();

    /** Column definition for Description */
    public static final org.adempiere.model.ModelColumn<I_PP_MRP, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_PP_MRP, Object>(I_PP_MRP.class, "Description", null);
    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

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
    public static final org.adempiere.model.ModelColumn<I_PP_MRP, Object> COLUMN_DocStatus = new org.adempiere.model.ModelColumn<I_PP_MRP, Object>(I_PP_MRP.class, "DocStatus", null);
    /** Column name DocStatus */
    public static final String COLUMNNAME_DocStatus = "DocStatus";

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
    public static final org.adempiere.model.ModelColumn<I_PP_MRP, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_PP_MRP, Object>(I_PP_MRP.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Verfügbar.
	 * Resource is available
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setIsAvailable (boolean IsAvailable);

	/**
	 * Get Verfügbar.
	 * Resource is available
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isAvailable();

    /** Column definition for IsAvailable */
    public static final org.adempiere.model.ModelColumn<I_PP_MRP, Object> COLUMN_IsAvailable = new org.adempiere.model.ModelColumn<I_PP_MRP, Object>(I_PP_MRP.class, "IsAvailable", null);
    /** Column name IsAvailable */
    public static final String COLUMNNAME_IsAvailable = "IsAvailable";

	/**
	 * Set Ausprägung Merkmals-Satz.
	 * Instanz des Merkmals-Satzes zum Produkt
	 *
	 * <br>Type: PAttribute
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_AttributeSetInstance_ID (int M_AttributeSetInstance_ID);

	/**
	 * Get Ausprägung Merkmals-Satz.
	 * Instanz des Merkmals-Satzes zum Produkt
	 *
	 * <br>Type: PAttribute
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_AttributeSetInstance_ID();

	public org.compiere.model.I_M_AttributeSetInstance getM_AttributeSetInstance();

	public void setM_AttributeSetInstance(org.compiere.model.I_M_AttributeSetInstance M_AttributeSetInstance);

    /** Column definition for M_AttributeSetInstance_ID */
    public static final org.adempiere.model.ModelColumn<I_PP_MRP, org.compiere.model.I_M_AttributeSetInstance> COLUMN_M_AttributeSetInstance_ID = new org.adempiere.model.ModelColumn<I_PP_MRP, org.compiere.model.I_M_AttributeSetInstance>(I_PP_MRP.class, "M_AttributeSetInstance_ID", org.compiere.model.I_M_AttributeSetInstance.class);
    /** Column name M_AttributeSetInstance_ID */
    public static final String COLUMNNAME_M_AttributeSetInstance_ID = "M_AttributeSetInstance_ID";

	/**
	 * Set Prognose.
	 * Material Forecast
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_Forecast_ID (int M_Forecast_ID);

	/**
	 * Get Prognose.
	 * Material Forecast
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_Forecast_ID();

	public org.compiere.model.I_M_Forecast getM_Forecast();

	public void setM_Forecast(org.compiere.model.I_M_Forecast M_Forecast);

    /** Column definition for M_Forecast_ID */
    public static final org.adempiere.model.ModelColumn<I_PP_MRP, org.compiere.model.I_M_Forecast> COLUMN_M_Forecast_ID = new org.adempiere.model.ModelColumn<I_PP_MRP, org.compiere.model.I_M_Forecast>(I_PP_MRP.class, "M_Forecast_ID", org.compiere.model.I_M_Forecast.class);
    /** Column name M_Forecast_ID */
    public static final String COLUMNNAME_M_Forecast_ID = "M_Forecast_ID";

	/**
	 * Set Prognose-Position.
	 * Forecast Line
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_ForecastLine_ID (int M_ForecastLine_ID);

	/**
	 * Get Prognose-Position.
	 * Forecast Line
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_ForecastLine_ID();

	public org.compiere.model.I_M_ForecastLine getM_ForecastLine();

	public void setM_ForecastLine(org.compiere.model.I_M_ForecastLine M_ForecastLine);

    /** Column definition for M_ForecastLine_ID */
    public static final org.adempiere.model.ModelColumn<I_PP_MRP, org.compiere.model.I_M_ForecastLine> COLUMN_M_ForecastLine_ID = new org.adempiere.model.ModelColumn<I_PP_MRP, org.compiere.model.I_M_ForecastLine>(I_PP_MRP.class, "M_ForecastLine_ID", org.compiere.model.I_M_ForecastLine.class);
    /** Column name M_ForecastLine_ID */
    public static final String COLUMNNAME_M_ForecastLine_ID = "M_ForecastLine_ID";

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

	public org.compiere.model.I_M_Product getM_Product();

	public void setM_Product(org.compiere.model.I_M_Product M_Product);

    /** Column definition for M_Product_ID */
    public static final org.adempiere.model.ModelColumn<I_PP_MRP, org.compiere.model.I_M_Product> COLUMN_M_Product_ID = new org.adempiere.model.ModelColumn<I_PP_MRP, org.compiere.model.I_M_Product>(I_PP_MRP.class, "M_Product_ID", org.compiere.model.I_M_Product.class);
    /** Column name M_Product_ID */
    public static final String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/**
	 * Set Bedarf.
	 * Material Requisition
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_Requisition_ID (int M_Requisition_ID);

	/**
	 * Get Bedarf.
	 * Material Requisition
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_Requisition_ID();

	public org.compiere.model.I_M_Requisition getM_Requisition();

	public void setM_Requisition(org.compiere.model.I_M_Requisition M_Requisition);

    /** Column definition for M_Requisition_ID */
    public static final org.adempiere.model.ModelColumn<I_PP_MRP, org.compiere.model.I_M_Requisition> COLUMN_M_Requisition_ID = new org.adempiere.model.ModelColumn<I_PP_MRP, org.compiere.model.I_M_Requisition>(I_PP_MRP.class, "M_Requisition_ID", org.compiere.model.I_M_Requisition.class);
    /** Column name M_Requisition_ID */
    public static final String COLUMNNAME_M_Requisition_ID = "M_Requisition_ID";

	/**
	 * Set Bedarfs-Position.
	 * Material Requisition Line
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_RequisitionLine_ID (int M_RequisitionLine_ID);

	/**
	 * Get Bedarfs-Position.
	 * Material Requisition Line
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_RequisitionLine_ID();

	public org.compiere.model.I_M_RequisitionLine getM_RequisitionLine();

	public void setM_RequisitionLine(org.compiere.model.I_M_RequisitionLine M_RequisitionLine);

    /** Column definition for M_RequisitionLine_ID */
    public static final org.adempiere.model.ModelColumn<I_PP_MRP, org.compiere.model.I_M_RequisitionLine> COLUMN_M_RequisitionLine_ID = new org.adempiere.model.ModelColumn<I_PP_MRP, org.compiere.model.I_M_RequisitionLine>(I_PP_MRP.class, "M_RequisitionLine_ID", org.compiere.model.I_M_RequisitionLine.class);
    /** Column name M_RequisitionLine_ID */
    public static final String COLUMNNAME_M_RequisitionLine_ID = "M_RequisitionLine_ID";

	/**
	 * Set Lager.
	 * Storage Warehouse and Service Point
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_Warehouse_ID (int M_Warehouse_ID);

	/**
	 * Get Lager.
	 * Storage Warehouse and Service Point
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_Warehouse_ID();

	public org.compiere.model.I_M_Warehouse getM_Warehouse();

	public void setM_Warehouse(org.compiere.model.I_M_Warehouse M_Warehouse);

    /** Column definition for M_Warehouse_ID */
    public static final org.adempiere.model.ModelColumn<I_PP_MRP, org.compiere.model.I_M_Warehouse> COLUMN_M_Warehouse_ID = new org.adempiere.model.ModelColumn<I_PP_MRP, org.compiere.model.I_M_Warehouse>(I_PP_MRP.class, "M_Warehouse_ID", org.compiere.model.I_M_Warehouse.class);
    /** Column name M_Warehouse_ID */
    public static final String COLUMNNAME_M_Warehouse_ID = "M_Warehouse_ID";

	/**
	 * Set Name.
	 * Alphanumeric identifier of the entity
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setName (java.lang.String Name);

	/**
	 * Get Name.
	 * Alphanumeric identifier of the entity
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getName();

    /** Column definition for Name */
    public static final org.adempiere.model.ModelColumn<I_PP_MRP, Object> COLUMN_Name = new org.adempiere.model.ModelColumn<I_PP_MRP, Object>(I_PP_MRP.class, "Name", null);
    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

	/**
	 * Set OrderType.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setOrderType (java.lang.String OrderType);

	/**
	 * Get OrderType.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getOrderType();

    /** Column definition for OrderType */
    public static final org.adempiere.model.ModelColumn<I_PP_MRP, Object> COLUMN_OrderType = new org.adempiere.model.ModelColumn<I_PP_MRP, Object>(I_PP_MRP.class, "OrderType", null);
    /** Column name OrderType */
    public static final String COLUMNNAME_OrderType = "OrderType";

	/**
	 * Set Planner.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPlanner_ID (int Planner_ID);

	/**
	 * Get Planner.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getPlanner_ID();

	public org.compiere.model.I_AD_User getPlanner();

	public void setPlanner(org.compiere.model.I_AD_User Planner);

    /** Column definition for Planner_ID */
    public static final org.adempiere.model.ModelColumn<I_PP_MRP, org.compiere.model.I_AD_User> COLUMN_Planner_ID = new org.adempiere.model.ModelColumn<I_PP_MRP, org.compiere.model.I_AD_User>(I_PP_MRP.class, "Planner_ID", org.compiere.model.I_AD_User.class);
    /** Column name Planner_ID */
    public static final String COLUMNNAME_Planner_ID = "Planner_ID";

	/**
	 * Set Material Requirement Planning.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setPP_MRP_ID (int PP_MRP_ID);

	/**
	 * Get Material Requirement Planning.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getPP_MRP_ID();

    /** Column definition for PP_MRP_ID */
    public static final org.adempiere.model.ModelColumn<I_PP_MRP, Object> COLUMN_PP_MRP_ID = new org.adempiere.model.ModelColumn<I_PP_MRP, Object>(I_PP_MRP.class, "PP_MRP_ID", null);
    /** Column name PP_MRP_ID */
    public static final String COLUMNNAME_PP_MRP_ID = "PP_MRP_ID";

	/**
	 * Set MRP Parent.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPP_MRP_Parent_ID (int PP_MRP_Parent_ID);

	/**
	 * Get MRP Parent.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getPP_MRP_Parent_ID();

	public org.eevolution.model.I_PP_MRP getPP_MRP_Parent();

	public void setPP_MRP_Parent(org.eevolution.model.I_PP_MRP PP_MRP_Parent);

    /** Column definition for PP_MRP_Parent_ID */
    public static final org.adempiere.model.ModelColumn<I_PP_MRP, org.eevolution.model.I_PP_MRP> COLUMN_PP_MRP_Parent_ID = new org.adempiere.model.ModelColumn<I_PP_MRP, org.eevolution.model.I_PP_MRP>(I_PP_MRP.class, "PP_MRP_Parent_ID", org.eevolution.model.I_PP_MRP.class);
    /** Column name PP_MRP_Parent_ID */
    public static final String COLUMNNAME_PP_MRP_Parent_ID = "PP_MRP_Parent_ID";

	/**
	 * Set Manufacturing Order BOM Line.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPP_Order_BOMLine_ID (int PP_Order_BOMLine_ID);

	/**
	 * Get Manufacturing Order BOM Line.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getPP_Order_BOMLine_ID();

	public org.eevolution.model.I_PP_Order_BOMLine getPP_Order_BOMLine();

	public void setPP_Order_BOMLine(org.eevolution.model.I_PP_Order_BOMLine PP_Order_BOMLine);

    /** Column definition for PP_Order_BOMLine_ID */
    public static final org.adempiere.model.ModelColumn<I_PP_MRP, org.eevolution.model.I_PP_Order_BOMLine> COLUMN_PP_Order_BOMLine_ID = new org.adempiere.model.ModelColumn<I_PP_MRP, org.eevolution.model.I_PP_Order_BOMLine>(I_PP_MRP.class, "PP_Order_BOMLine_ID", org.eevolution.model.I_PP_Order_BOMLine.class);
    /** Column name PP_Order_BOMLine_ID */
    public static final String COLUMNNAME_PP_Order_BOMLine_ID = "PP_Order_BOMLine_ID";

	/**
	 * Set Produktionsauftrag.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPP_Order_ID (int PP_Order_ID);

	/**
	 * Get Produktionsauftrag.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getPP_Order_ID();

	public org.eevolution.model.I_PP_Order getPP_Order();

	public void setPP_Order(org.eevolution.model.I_PP_Order PP_Order);

    /** Column definition for PP_Order_ID */
    public static final org.adempiere.model.ModelColumn<I_PP_MRP, org.eevolution.model.I_PP_Order> COLUMN_PP_Order_ID = new org.adempiere.model.ModelColumn<I_PP_MRP, org.eevolution.model.I_PP_Order>(I_PP_MRP.class, "PP_Order_ID", org.eevolution.model.I_PP_Order.class);
    /** Column name PP_Order_ID */
    public static final String COLUMNNAME_PP_Order_ID = "PP_Order_ID";

	/**
	 * Set Bereitstellungsdatum.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPreparationDate (java.sql.Timestamp PreparationDate);

	/**
	 * Get Bereitstellungsdatum.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getPreparationDate();

    /** Column definition for PreparationDate */
    public static final org.adempiere.model.ModelColumn<I_PP_MRP, Object> COLUMN_PreparationDate = new org.adempiere.model.ModelColumn<I_PP_MRP, Object>(I_PP_MRP.class, "PreparationDate", null);
    /** Column name PreparationDate */
    public static final String COLUMNNAME_PreparationDate = "PreparationDate";

	/**
	 * Set Priorität.
	 * Indicates if this request is of a high, medium or low priority.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPriority (java.lang.String Priority);

	/**
	 * Get Priorität.
	 * Indicates if this request is of a high, medium or low priority.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getPriority();

    /** Column definition for Priority */
    public static final org.adempiere.model.ModelColumn<I_PP_MRP, Object> COLUMN_Priority = new org.adempiere.model.ModelColumn<I_PP_MRP, Object>(I_PP_MRP.class, "Priority", null);
    /** Column name Priority */
    public static final String COLUMNNAME_Priority = "Priority";

	/**
	 * Set Menge.
	 * Menge
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setQty (java.math.BigDecimal Qty);

	/**
	 * Get Menge.
	 * Menge
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQty();

    /** Column definition for Qty */
    public static final org.adempiere.model.ModelColumn<I_PP_MRP, Object> COLUMN_Qty = new org.adempiere.model.ModelColumn<I_PP_MRP, Object>(I_PP_MRP.class, "Qty", null);
    /** Column name Qty */
    public static final String COLUMNNAME_Qty = "Qty";

	/**
	 * Set Qty Requiered.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setQtyRequiered (java.math.BigDecimal QtyRequiered);

	/**
	 * Get Qty Requiered.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyRequiered();

    /** Column definition for QtyRequiered */
    public static final org.adempiere.model.ModelColumn<I_PP_MRP, Object> COLUMN_QtyRequiered = new org.adempiere.model.ModelColumn<I_PP_MRP, Object>(I_PP_MRP.class, "QtyRequiered", null);
    /** Column name QtyRequiered */
    public static final String COLUMNNAME_QtyRequiered = "QtyRequiered";

	/**
	 * Set Ressource.
	 * Resource
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setS_Resource_ID (int S_Resource_ID);

	/**
	 * Get Ressource.
	 * Resource
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getS_Resource_ID();

	public org.compiere.model.I_S_Resource getS_Resource();

	public void setS_Resource(org.compiere.model.I_S_Resource S_Resource);

    /** Column definition for S_Resource_ID */
    public static final org.adempiere.model.ModelColumn<I_PP_MRP, org.compiere.model.I_S_Resource> COLUMN_S_Resource_ID = new org.adempiere.model.ModelColumn<I_PP_MRP, org.compiere.model.I_S_Resource>(I_PP_MRP.class, "S_Resource_ID", org.compiere.model.I_S_Resource.class);
    /** Column name S_Resource_ID */
    public static final String COLUMNNAME_S_Resource_ID = "S_Resource_ID";

	/**
	 * Set TypeMRP.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setTypeMRP (java.lang.String TypeMRP);

	/**
	 * Get TypeMRP.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getTypeMRP();

    /** Column definition for TypeMRP */
    public static final org.adempiere.model.ModelColumn<I_PP_MRP, Object> COLUMN_TypeMRP = new org.adempiere.model.ModelColumn<I_PP_MRP, Object>(I_PP_MRP.class, "TypeMRP", null);
    /** Column name TypeMRP */
    public static final String COLUMNNAME_TypeMRP = "TypeMRP";

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
    public static final org.adempiere.model.ModelColumn<I_PP_MRP, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_PP_MRP, Object>(I_PP_MRP.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_PP_MRP, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_PP_MRP, org.compiere.model.I_AD_User>(I_PP_MRP.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/**
	 * Set Suchschlüssel.
	 * Search key for the record in the format required - must be unique
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setValue (java.lang.String Value);

	/**
	 * Get Suchschlüssel.
	 * Search key for the record in the format required - must be unique
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getValue();

    /** Column definition for Value */
    public static final org.adempiere.model.ModelColumn<I_PP_MRP, Object> COLUMN_Value = new org.adempiere.model.ModelColumn<I_PP_MRP, Object>(I_PP_MRP.class, "Value", null);
    /** Column name Value */
    public static final String COLUMNNAME_Value = "Value";

	/**
	 * Set Version.
	 * Version of the table definition
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setVersion (java.math.BigDecimal Version);

	/**
	 * Get Version.
	 * Version of the table definition
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getVersion();

    /** Column definition for Version */
    public static final org.adempiere.model.ModelColumn<I_PP_MRP, Object> COLUMN_Version = new org.adempiere.model.ModelColumn<I_PP_MRP, Object>(I_PP_MRP.class, "Version", null);
    /** Column name Version */
    public static final String COLUMNNAME_Version = "Version";
}
