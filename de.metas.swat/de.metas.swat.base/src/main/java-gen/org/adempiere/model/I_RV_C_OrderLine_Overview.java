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
package org.adempiere.model;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.MTable;
import org.compiere.util.KeyNamePair;

/** Generated Interface for RV_C_OrderLine_Overview
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a
 */
public interface I_RV_C_OrderLine_Overview 
{

    /** TableName=RV_C_OrderLine_Overview */
    public static final String Table_Name = "RV_C_OrderLine_Overview";

    /** AD_Table_ID=540261 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(3);

    /** Load Meta Data */

    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/** Get Mandant.
	  * Mandant fuer diese Installation.
	  */
	public int getAD_Client_ID();

    /** Column name AD_OrgTrx_ID */
    public static final String COLUMNNAME_AD_OrgTrx_ID = "AD_OrgTrx_ID";

	/** Set Buchende Organisation.
	  * Durchfuehrende oder ausloesende Organisation
	  */
	public void setAD_OrgTrx_ID (int AD_OrgTrx_ID);

	/** Get Buchende Organisation.
	  * Durchfuehrende oder ausloesende Organisation
	  */
	public int getAD_OrgTrx_ID();

	public org.compiere.model.I_AD_Org getAD_OrgTrx() throws RuntimeException;

    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/** Set Organisation.
	  * Organisatorische Einheit des Mandanten
	  */
	public void setAD_Org_ID (int AD_Org_ID);

	/** Get Organisation.
	  * Organisatorische Einheit des Mandanten
	  */
	public int getAD_Org_ID();

    /** Column name C_Activity_ID */
    public static final String COLUMNNAME_C_Activity_ID = "C_Activity_ID";

	/** Set Activity.
	  * Business Activity
	  */
	public void setC_Activity_ID (int C_Activity_ID);

	/** Get Activity.
	  * Business Activity
	  */
	public int getC_Activity_ID();

	public org.compiere.model.I_C_Activity getC_Activity() throws RuntimeException;

    /** Column name C_BPartner_ID */
    public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/** Set Geschaeftspartner.
	  * Bezeichnet einen Geschaeftspartner
	  */
	public void setC_BPartner_ID (int C_BPartner_ID);

	/** Get Geschaeftspartner.
	  * Bezeichnet einen Geschaeftspartner
	  */
	public int getC_BPartner_ID();

	public org.compiere.model.I_C_BPartner getC_BPartner() throws RuntimeException;

    /** Column name C_BPartner_Location_ID */
    public static final String COLUMNNAME_C_BPartner_Location_ID = "C_BPartner_Location_ID";

	/** Set Standort.
	  * Identifiziert die (Liefer-) Adresse des Geschaeftspartners
	  */
	public void setC_BPartner_Location_ID (int C_BPartner_Location_ID);

	/** Get Standort.
	  * Identifiziert die (Liefer-) Adresse des Geschaeftspartners
	  */
	public int getC_BPartner_Location_ID();

	public org.compiere.model.I_C_BPartner_Location getC_BPartner_Location() throws RuntimeException;

    /** Column name C_Campaign_ID */
    public static final String COLUMNNAME_C_Campaign_ID = "C_Campaign_ID";

	/** Set Campaign.
	  * Marketing Campaign
	  */
	public void setC_Campaign_ID (int C_Campaign_ID);

	/** Get Campaign.
	  * Marketing Campaign
	  */
	public int getC_Campaign_ID();

	public org.compiere.model.I_C_Campaign getC_Campaign() throws RuntimeException;

    /** Column name C_Charge_ID */
    public static final String COLUMNNAME_C_Charge_ID = "C_Charge_ID";

	/** Set Kosten.
	  * Zusaetzliche Kosten
	  */
	public void setC_Charge_ID (int C_Charge_ID);

	/** Get Kosten.
	  * Zusaetzliche Kosten
	  */
	public int getC_Charge_ID();

	public org.compiere.model.I_C_Charge getC_Charge() throws RuntimeException;

    /** Column name C_Currency_ID */
    public static final String COLUMNNAME_C_Currency_ID = "C_Currency_ID";

	/** Set Waehrung.
	  * Die Waehrung fuer diesen Eintrag
	  */
	public void setC_Currency_ID (int C_Currency_ID);

	/** Get Waehrung.
	  * Die Waehrung fuer diesen Eintrag
	  */
	public int getC_Currency_ID();

	public org.compiere.model.I_C_Currency getC_Currency() throws RuntimeException;

    /** Column name C_DocType_ID */
    public static final String COLUMNNAME_C_DocType_ID = "C_DocType_ID";

	/** Set Belegart.
	  * Belegart oder Verarbeitungsvorgaben
	  */
	public void setC_DocType_ID (int C_DocType_ID);

	/** Get Belegart.
	  * Belegart oder Verarbeitungsvorgaben
	  */
	public int getC_DocType_ID();

	public org.compiere.model.I_C_DocType getC_DocType() throws RuntimeException;

    /** Column name C_OrderLine_ID */
    public static final String COLUMNNAME_C_OrderLine_ID = "C_OrderLine_ID";

	/** Set Auftragsposition.
	  * Auftragsposition
	  */
	public void setC_OrderLine_ID (int C_OrderLine_ID);

	/** Get Auftragsposition.
	  * Auftragsposition
	  */
	public int getC_OrderLine_ID();

	public org.compiere.model.I_C_OrderLine getC_OrderLine() throws RuntimeException;

    /** Column name C_Order_ID */
    public static final String COLUMNNAME_C_Order_ID = "C_Order_ID";

	/** Set Order.
	  * Order
	  */
	public void setC_Order_ID (int C_Order_ID);

	/** Get Order.
	  * Order
	  */
	public int getC_Order_ID();

	public org.compiere.model.I_C_Order getC_Order() throws RuntimeException;

    /** Column name C_ProjectPhase_ID */
    public static final String COLUMNNAME_C_ProjectPhase_ID = "C_ProjectPhase_ID";

	/** Set Projekt-Phase.
	  * Phase eines Projektes
	  */
	public void setC_ProjectPhase_ID (int C_ProjectPhase_ID);

	/** Get Projekt-Phase.
	  * Phase eines Projektes
	  */
	public int getC_ProjectPhase_ID();

	public org.compiere.model.I_C_ProjectPhase getC_ProjectPhase() throws RuntimeException;

    /** Column name C_ProjectTask_ID */
    public static final String COLUMNNAME_C_ProjectTask_ID = "C_ProjectTask_ID";

	/** Set Projekt-Aufgabe.
	  * Aktuelle Projekt-Aufgabe in einer Phase
	  */
	public void setC_ProjectTask_ID (int C_ProjectTask_ID);

	/** Get Projekt-Aufgabe.
	  * Aktuelle Projekt-Aufgabe in einer Phase
	  */
	public int getC_ProjectTask_ID();

	public org.compiere.model.I_C_ProjectTask getC_ProjectTask() throws RuntimeException;

    /** Column name C_Project_ID */
    public static final String COLUMNNAME_C_Project_ID = "C_Project_ID";

	/** Set Project.
	  * Financial Project
	  */
	public void setC_Project_ID (int C_Project_ID);

	/** Get Project.
	  * Financial Project
	  */
	public int getC_Project_ID();

	public org.compiere.model.I_C_Project getC_Project() throws RuntimeException;

    /** Column name C_Tax_ID */
    public static final String COLUMNNAME_C_Tax_ID = "C_Tax_ID";

	/** Set Steuer.
	  * Steuerart
	  */
	public void setC_Tax_ID (int C_Tax_ID);

	/** Get Steuer.
	  * Steuerart
	  */
	public int getC_Tax_ID();

	public org.compiere.model.I_C_Tax getC_Tax() throws RuntimeException;

    /** Column name C_UOM_ID */
    public static final String COLUMNNAME_C_UOM_ID = "C_UOM_ID";

	/** Set Masseinheit.
	  * Masseinheit
	  */
	public void setC_UOM_ID (int C_UOM_ID);

	/** Get Masseinheit.
	  * Masseinheit
	  */
	public int getC_UOM_ID();

	public org.compiere.model.I_C_UOM getC_UOM() throws RuntimeException;

    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/** Get Erstellt.
	  * Datum, an dem dieser Eintrag erstellt wurde
	  */
	public Timestamp getCreated();

    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/** Get Erstellt durch.
	  * Nutzer, der diesen Eintrag erstellt hat
	  */
	public int getCreatedBy();

    /** Column name DateDelivered */
    public static final String COLUMNNAME_DateDelivered = "DateDelivered";

	/** Set Lieferdatum.
	  * Datum, zu dem die Ware geliefert wurde
	  */
	public void setDateDelivered (Timestamp DateDelivered);

	/** Get Lieferdatum.
	  * Datum, zu dem die Ware geliefert wurde
	  */
	public Timestamp getDateDelivered();

    /** Column name DateInvoiced */
    public static final String COLUMNNAME_DateInvoiced = "DateInvoiced";

	/** Set Rechnungsdatum.
	  * Datum auf der Rechnung
	  */
	public void setDateInvoiced (Timestamp DateInvoiced);

	/** Get Rechnungsdatum.
	  * Datum auf der Rechnung
	  */
	public Timestamp getDateInvoiced();

    /** Column name DateOrdered */
    public static final String COLUMNNAME_DateOrdered = "DateOrdered";

	/** Set Auftragsdatum.
	  * Datum des Auftrags
	  */
	public void setDateOrdered (Timestamp DateOrdered);

	/** Get Auftragsdatum.
	  * Datum des Auftrags
	  */
	public Timestamp getDateOrdered();

    /** Column name DatePromised */
    public static final String COLUMNNAME_DatePromised = "DatePromised";

	/** Set Zugesagter Termin.
	  * Zugesagter Termin fuer diesen Auftrag
	  */
	public void setDatePromised (Timestamp DatePromised);

	/** Get Zugesagter Termin.
	  * Zugesagter Termin fuer diesen Auftrag
	  */
	public Timestamp getDatePromised();

    /** Column name DaysDue */
    public static final String COLUMNNAME_DaysDue = "DaysDue";

	/** Set Tage faellig.
	  * Anzahl der Tage der Faelligkeit (negativ: Faellig in Anzahl Tagen)
	  */
	public void setDaysDue (int DaysDue);

	/** Get Tage faellig.
	  * Anzahl der Tage der Faelligkeit (negativ: Faellig in Anzahl Tagen)
	  */
	public int getDaysDue();

    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/** Set Beschreibung.
	  * Optional short description of the record
	  */
	public void setDescription (String Description);

	/** Get Beschreibung.
	  * Optional short description of the record
	  */
	public String getDescription();

    /** Column name Discount */
    public static final String COLUMNNAME_Discount = "Discount";

	/** Set Rabatt %.
	  * Abschlag in Prozent
	  */
	public void setDiscount (BigDecimal Discount);

	/** Get Rabatt %.
	  * Abschlag in Prozent
	  */
	public BigDecimal getDiscount();

    /** Column name DocStatus */
    public static final String COLUMNNAME_DocStatus = "DocStatus";

	/** Set Document Status.
	  * The current status of the document
	  */
	public void setDocStatus (String DocStatus);

	/** Get Document Status.
	  * The current status of the document
	  */
	public String getDocStatus();

    /** Column name DocumentNo */
    public static final String COLUMNNAME_DocumentNo = "DocumentNo";

	/** Set Document No.
	  * Document sequence number of the document
	  */
	public void setDocumentNo (String DocumentNo);

	/** Get Document No.
	  * Document sequence number of the document
	  */
	public String getDocumentNo();

    /** Column name DueType */
    public static final String COLUMNNAME_DueType = "DueType";

	/** Set Due type.
	  * Status of the next action for this Request
	  */
	public void setDueType (String DueType);

	/** Get Due type.
	  * Status of the next action for this Request
	  */
	public String getDueType();

    /** Column name FreightAmt */
    public static final String COLUMNNAME_FreightAmt = "FreightAmt";

	/** Set Frachtbetrag.
	  * Frachtbetrag
	  */
	public void setFreightAmt (BigDecimal FreightAmt);

	/** Get Frachtbetrag.
	  * Frachtbetrag
	  */
	public BigDecimal getFreightAmt();

    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/** Set Aktiv.
	  * Der Eintrag ist im System aktiv
	  */
	public void setIsActive (boolean IsActive);

	/** Get Aktiv.
	  * Der Eintrag ist im System aktiv
	  */
	public boolean isActive();

    /** Column name IsDelivered */
    public static final String COLUMNNAME_IsDelivered = "IsDelivered";

	/** Set Zugestellt	  */
	public void setIsDelivered (boolean IsDelivered);

	/** Get Zugestellt	  */
	public boolean isDelivered();

    /** Column name IsDescription */
    public static final String COLUMNNAME_IsDescription = "IsDescription";

	/** Set Description Only.
	  * if true, the line is just description and no transaction
	  */
	public void setIsDescription (boolean IsDescription);

	/** Get Description Only.
	  * if true, the line is just description and no transaction
	  */
	public boolean isDescription();

    /** Column name IsInvoiced */
    public static final String COLUMNNAME_IsInvoiced = "IsInvoiced";

	/** Set Invoiced.
	  * Is this invoiced?
	  */
	public void setIsInvoiced (boolean IsInvoiced);

	/** Get Invoiced.
	  * Is this invoiced?
	  */
	public boolean isInvoiced();

    /** Column name IsPaid */
    public static final String COLUMNNAME_IsPaid = "IsPaid";

	/** Set Gezahlt.
	  * Der Beleg ist bezahlt
	  */
	public void setIsPaid (String IsPaid);

	/** Get Gezahlt.
	  * Der Beleg ist bezahlt
	  */
	public String getIsPaid();

    /** Column name IsPurchased */
    public static final String COLUMNNAME_IsPurchased = "IsPurchased";

	/** Set Eingekauft.
	  * Die Organisation kauft dieses Produkt ein
	  */
	public void setIsPurchased (String IsPurchased);

	/** Get Eingekauft.
	  * Die Organisation kauft dieses Produkt ein
	  */
	public String getIsPurchased();

    /** Column name IsSOTrx */
    public static final String COLUMNNAME_IsSOTrx = "IsSOTrx";

	/** Set Sales Transaction.
	  * This is a Sales Transaction
	  */
	public void setIsSOTrx (boolean IsSOTrx);

	/** Get Sales Transaction.
	  * This is a Sales Transaction
	  */
	public boolean isSOTrx();

    /** Column name Line */
    public static final String COLUMNNAME_Line = "Line";

	/** Set Zeile Nr..
	  * Einzelne Zeile in dem Dokument
	  */
	public void setLine (int Line);

	/** Get Zeile Nr..
	  * Einzelne Zeile in dem Dokument
	  */
	public int getLine();

    /** Column name LineNetAmt */
    public static final String COLUMNNAME_LineNetAmt = "LineNetAmt";

	/** Set Zeilennetto.
	  * Nettowert Zeile (Menge * Einzelpreis) ohne Fracht und Gebuehren
	  */
	public void setLineNetAmt (BigDecimal LineNetAmt);

	/** Get Zeilennetto.
	  * Nettowert Zeile (Menge * Einzelpreis) ohne Fracht und Gebuehren
	  */
	public BigDecimal getLineNetAmt();

    /** Column name Link_OrderLine_ID */
    public static final String COLUMNNAME_Link_OrderLine_ID = "Link_OrderLine_ID";

	/** Set Linked Order Line.
	  * This field links a sales order line to the purchase order line that is generated from it.
	  */
	public void setLink_OrderLine_ID (int Link_OrderLine_ID);

	/** Get Linked Order Line.
	  * This field links a sales order line to the purchase order line that is generated from it.
	  */
	public int getLink_OrderLine_ID();

	public org.compiere.model.I_C_OrderLine getLink_OrderLine() throws RuntimeException;

    /** Column name M_AttributeSetInstance_ID */
    public static final String COLUMNNAME_M_AttributeSetInstance_ID = "M_AttributeSetInstance_ID";

	/** Set Auspraegung Merkmals-Satz.
	  * Instanz des Merkmals-Satzes zum Produkt
	  */
	public void setM_AttributeSetInstance_ID (int M_AttributeSetInstance_ID);

	/** Get Auspraegung Merkmals-Satz.
	  * Instanz des Merkmals-Satzes zum Produkt
	  */
	public int getM_AttributeSetInstance_ID();

	public I_M_AttributeSetInstance getM_AttributeSetInstance() throws RuntimeException;

    /** Column name M_Product_ID */
    public static final String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/** Set Produkt.
	  * Produkt, Leistung, Artikel
	  */
	public void setM_Product_ID (int M_Product_ID);

	/** Get Produkt.
	  * Produkt, Leistung, Artikel
	  */
	public int getM_Product_ID();

	public org.compiere.model.I_M_Product getM_Product() throws RuntimeException;

    /** Column name M_Promotion_ID */
    public static final String COLUMNNAME_M_Promotion_ID = "M_Promotion_ID";

	/** Set Promotion	  */
	public void setM_Promotion_ID (int M_Promotion_ID);

	/** Get Promotion	  */
	public int getM_Promotion_ID();

	public org.compiere.model.I_M_Promotion getM_Promotion() throws RuntimeException;

    /** Column name M_Shipper_ID */
    public static final String COLUMNNAME_M_Shipper_ID = "M_Shipper_ID";

	/** Set Lieferweg.
	  * Methode oder Art der Warenlieferung
	  */
	public void setM_Shipper_ID (int M_Shipper_ID);

	/** Get Lieferweg.
	  * Methode oder Art der Warenlieferung
	  */
	public int getM_Shipper_ID();

	public org.compiere.model.I_M_Shipper getM_Shipper() throws RuntimeException;

    /** Column name M_Warehouse_ID */
    public static final String COLUMNNAME_M_Warehouse_ID = "M_Warehouse_ID";

	/** Set Lager.
	  * Lager oder Ort fuer Dienstleistung
	  */
	public void setM_Warehouse_ID (int M_Warehouse_ID);

	/** Get Lager.
	  * Lager oder Ort fuer Dienstleistung
	  */
	public int getM_Warehouse_ID();

	public org.compiere.model.I_M_Warehouse getM_Warehouse() throws RuntimeException;

    /** Column name PP_Cost_Collector_ID */
    public static final String COLUMNNAME_PP_Cost_Collector_ID = "PP_Cost_Collector_ID";

	/** Set Manufacturing Cost Collector	  */
	public void setPP_Cost_Collector_ID (int PP_Cost_Collector_ID);

	/** Get Manufacturing Cost Collector	  */
	public int getPP_Cost_Collector_ID();

	public org.eevolution.model.I_PP_Cost_Collector getPP_Cost_Collector() throws RuntimeException;

    /** Column name PriceActual */
    public static final String COLUMNNAME_PriceActual = "PriceActual";

	/** Set Einzelpreis.
	  * Effektiver Preis
	  */
	public void setPriceActual (BigDecimal PriceActual);

	/** Get Einzelpreis.
	  * Effektiver Preis
	  */
	public BigDecimal getPriceActual();

    /** Column name PriceCost */
    public static final String COLUMNNAME_PriceCost = "PriceCost";

	/** Set Cost Price.
	  * Price per Unit of Measure including all indirect costs (Freight, etc.)
	  */
	public void setPriceCost (BigDecimal PriceCost);

	/** Get Cost Price.
	  * Price per Unit of Measure including all indirect costs (Freight, etc.)
	  */
	public BigDecimal getPriceCost();

    /** Column name PriceEntered */
    public static final String COLUMNNAME_PriceEntered = "PriceEntered";

	/** Set Preis.
	  * Eingegebener Preis - der Preis basierend auf der gewaehlten Mengeneinheit
	  */
	public void setPriceEntered (BigDecimal PriceEntered);

	/** Get Preis.
	  * Eingegebener Preis - der Preis basierend auf der gewaehlten Mengeneinheit
	  */
	public BigDecimal getPriceEntered();

    /** Column name PriceLimit */
    public static final String COLUMNNAME_PriceLimit = "PriceLimit";

	/** Set Mindestpreis.
	  * Unterster Preis fuer Kostendeckung
	  */
	public void setPriceLimit (BigDecimal PriceLimit);

	/** Get Mindestpreis.
	  * Unterster Preis fuer Kostendeckung
	  */
	public BigDecimal getPriceLimit();

    /** Column name PriceList */
    public static final String COLUMNNAME_PriceList = "PriceList";

	/** Set Listenpreis.
	  * Listenpreis
	  */
	public void setPriceList (BigDecimal PriceList);

	/** Get Listenpreis.
	  * Listenpreis
	  */
	public BigDecimal getPriceList();

    /** Column name Processed */
    public static final String COLUMNNAME_Processed = "Processed";

	/** Set Verarbeitet.
	  * The document has been processed
	  */
	public void setProcessed (boolean Processed);

	/** Get Verarbeitet.
	  * The document has been processed
	  */
	public boolean isProcessed();

    /** Column name QtyDelivered */
    public static final String COLUMNNAME_QtyDelivered = "QtyDelivered";

	/** Set Gelieferte Menge.
	  * Gelieferte Menge
	  */
	public void setQtyDelivered (BigDecimal QtyDelivered);

	/** Get Gelieferte Menge.
	  * Gelieferte Menge
	  */
	public BigDecimal getQtyDelivered();

    /** Column name QtyEntered */
    public static final String COLUMNNAME_QtyEntered = "QtyEntered";

	/** Set Menge.
	  * Die Eingegebene Menge basiert auf der gewaehlten Mengeneinheit
	  */
	public void setQtyEntered (BigDecimal QtyEntered);

	/** Get Menge.
	  * Die Eingegebene Menge basiert auf der gewaehlten Mengeneinheit
	  */
	public BigDecimal getQtyEntered();

    /** Column name QtyInvoiced */
    public static final String COLUMNNAME_QtyInvoiced = "QtyInvoiced";

	/** Set Quantity Invoiced.
	  * Invoiced Quantity
	  */
	public void setQtyInvoiced (BigDecimal QtyInvoiced);

	/** Get Quantity Invoiced.
	  * Invoiced Quantity
	  */
	public BigDecimal getQtyInvoiced();

    /** Column name QtyLostSales */
    public static final String COLUMNNAME_QtyLostSales = "QtyLostSales";

	/** Set Lost Sales Qty.
	  * Quantity of potential sales
	  */
	public void setQtyLostSales (BigDecimal QtyLostSales);

	/** Get Lost Sales Qty.
	  * Quantity of potential sales
	  */
	public BigDecimal getQtyLostSales();

    /** Column name QtyOrdered */
    public static final String COLUMNNAME_QtyOrdered = "QtyOrdered";

	/** Set Bestellte Menge.
	  * Bestellte Menge
	  */
	public void setQtyOrdered (BigDecimal QtyOrdered);

	/** Get Bestellte Menge.
	  * Bestellte Menge
	  */
	public BigDecimal getQtyOrdered();

    /** Column name QtyReserved */
    public static final String COLUMNNAME_QtyReserved = "QtyReserved";

	/** Set Reservierte Menge.
	  * Reservierte Menge
	  */
	public void setQtyReserved (BigDecimal QtyReserved);

	/** Get Reservierte Menge.
	  * Reservierte Menge
	  */
	public BigDecimal getQtyReserved();

    /** Column name RRAmt */
    public static final String COLUMNNAME_RRAmt = "RRAmt";

	/** Set Revenue Recognition Amt.
	  * Revenue Recognition Amount
	  */
	public void setRRAmt (BigDecimal RRAmt);

	/** Get Revenue Recognition Amt.
	  * Revenue Recognition Amount
	  */
	public BigDecimal getRRAmt();

    /** Column name RRStartDate */
    public static final String COLUMNNAME_RRStartDate = "RRStartDate";

	/** Set Revenue Recognition Start.
	  * Revenue Recognition Start Date
	  */
	public void setRRStartDate (Timestamp RRStartDate);

	/** Get Revenue Recognition Start.
	  * Revenue Recognition Start Date
	  */
	public Timestamp getRRStartDate();

    /** Column name Ref_OrderLine_ID */
    public static final String COLUMNNAME_Ref_OrderLine_ID = "Ref_OrderLine_ID";

	/** Set Referenced Order Line.
	  * Reference to corresponding Sales/Purchase Order
	  */
	public void setRef_OrderLine_ID (int Ref_OrderLine_ID);

	/** Get Referenced Order Line.
	  * Reference to corresponding Sales/Purchase Order
	  */
	public int getRef_OrderLine_ID();

	public org.compiere.model.I_C_OrderLine getRef_OrderLine() throws RuntimeException;

    /** Column name S_ResourceAssignment_ID */
    public static final String COLUMNNAME_S_ResourceAssignment_ID = "S_ResourceAssignment_ID";

	/** Set Ressourcenzuordnung.
	  * Ressourcenzuordnung
	  */
	public void setS_ResourceAssignment_ID (int S_ResourceAssignment_ID);

	/** Get Ressourcenzuordnung.
	  * Ressourcenzuordnung
	  */
	public int getS_ResourceAssignment_ID();

    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/** Get Aktualisiert.
	  * Datum, an dem dieser Eintrag aktualisiert wurde
	  */
	public Timestamp getUpdated();

    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/** Get Aktualisiert durch.
	  * Nutzer, der diesen Eintrag aktualisiert hat
	  */
	public int getUpdatedBy();
}
