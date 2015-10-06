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
package de.metas.purchasing.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import org.compiere.model.*;
import org.compiere.util.KeyNamePair;

/** Generated Interface for M_PurchaseSchedule
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a
 */
public interface I_M_PurchaseSchedule 
{

    /** TableName=M_PurchaseSchedule */
    public static final String Table_Name = "M_PurchaseSchedule";

    /** AD_Table_ID=501866 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(3);

    /** Load Meta Data */

    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/** Get Mandant.
	  * Mandant f�r diese Installation.
	  */
	public int getAD_Client_ID();

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

    /** Column name C_BPartner_ID */
    public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/** Set Geschaeftspartner.
	  * Bezeichnet einen Gesch�ftspartner
	  */
	public void setC_BPartner_ID (int C_BPartner_ID);

	/** Get Geschaeftspartner.
	  * Bezeichnet einen Gesch�ftspartner
	  */
	public int getC_BPartner_ID();

	public org.compiere.model.I_C_BPartner getC_BPartner() throws RuntimeException;

    /** Column name C_OrderLine_Individual_ID */
    public static final String COLUMNNAME_C_OrderLine_Individual_ID = "C_OrderLine_Individual_ID";

	/** Set Sonder-Auftragsposition	  */
	public void setC_OrderLine_Individual_ID (int C_OrderLine_Individual_ID);

	/** Get Sonder-Auftragsposition	  */
	public int getC_OrderLine_Individual_ID();

	public org.compiere.model.I_C_OrderLine getC_OrderLine_Individual() throws RuntimeException;

    /** Column name C_OrderPO_ID */
    public static final String COLUMNNAME_C_OrderPO_ID = "C_OrderPO_ID";

	/** Set Bestellung.
	  * Bestellung
	  */
	public void setC_OrderPO_ID (int C_OrderPO_ID);

	/** Get Bestellung.
	  * Bestellung
	  */
	public int getC_OrderPO_ID();

	public org.compiere.model.I_C_Order getC_OrderPO() throws RuntimeException;

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

    /** Column name CreatePO */
    public static final String COLUMNNAME_CreatePO = "CreatePO";

	/** Set Bestellung anlegen.
	  * Bestellung anlegen
	  */
	public void setCreatePO (String CreatePO);

	/** Get Bestellung anlegen.
	  * Bestellung anlegen
	  */
	public String getCreatePO();

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

    /** Column name DropShip_BPartner_ID */
    public static final String COLUMNNAME_DropShip_BPartner_ID = "DropShip_BPartner_ID";

	/** Set Streckengeschaeft-Kunde.
	  * Business Partner to ship to
	  */
	public void setDropShip_BPartner_ID (int DropShip_BPartner_ID);

	/** Get Streckengeschaeft-Kunde.
	  * Business Partner to ship to
	  */
	public int getDropShip_BPartner_ID();

	public org.compiere.model.I_C_BPartner getDropShip_BPartner() throws RuntimeException;

    /** Column name DropShip_Location_ID */
    public static final String COLUMNNAME_DropShip_Location_ID = "DropShip_Location_ID";

	/** Set Streckengeschaeft-Ort.
	  * Business Partner Location for shipping to
	  */
	public void setDropShip_Location_ID (int DropShip_Location_ID);

	/** Get Streckengeschaeft-Ort.
	  * Business Partner Location for shipping to
	  */
	public int getDropShip_Location_ID();

	public org.compiere.model.I_C_BPartner_Location getDropShip_Location() throws RuntimeException;

    /** Column name DropShip_User_ID */
    public static final String COLUMNNAME_DropShip_User_ID = "DropShip_User_ID";

	/** Set Streckengeschaeft-Ansprechpartner.
	  * Business Partner Contact for drop shipment
	  */
	public void setDropShip_User_ID (int DropShip_User_ID);

	/** Get Streckengeschaeft-Ansprechpartner.
	  * Business Partner Contact for drop shipment
	  */
	public int getDropShip_User_ID();

	public org.compiere.model.I_AD_User getDropShip_User() throws RuntimeException;

    /** Column name IncludeInPurchase */
    public static final String COLUMNNAME_IncludeInPurchase = "IncludeInPurchase";

	/** Set Bestellen.
	  * In Bestellung aufnehmen
	  */
	public void setIncludeInPurchase (boolean IncludeInPurchase);

	/** Get Bestellen.
	  * In Bestellung aufnehmen
	  */
	public boolean isIncludeInPurchase();

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

    /** Column name IsDropShip */
    public static final String COLUMNNAME_IsDropShip = "IsDropShip";

	/** Set Streckengeschaeft.
	  * Beim Streckengeschaeft wird die Ware direkt vom Lieferanten zum Kunden geliefert
	  */
	public void setIsDropShip (boolean IsDropShip);

	/** Get Streckengeschaeft.
	  * Beim Streckengeschaeft wird die Ware direkt vom Lieferanten zum Kunden geliefert
	  */
	public boolean isDropShip();

    /** Column name IsIndividualPOSchedule */
    public static final String COLUMNNAME_IsIndividualPOSchedule = "IsIndividualPOSchedule";

	/** Set Einzel-/Sonderbestellung.
	  * Bestellung soll einzeln Disponiert werden
	  */
	public void setIsIndividualPOSchedule (boolean IsIndividualPOSchedule);

	/** Get Einzel-/Sonderbestellung.
	  * Bestellung soll einzeln Disponiert werden
	  */
	public boolean isIndividualPOSchedule();

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

    /** Column name M_PurchaseSchedule_ID */
    public static final String COLUMNNAME_M_PurchaseSchedule_ID = "M_PurchaseSchedule_ID";

	/** Set Bestellplan	  */
	public void setM_PurchaseSchedule_ID (int M_PurchaseSchedule_ID);

	/** Get Bestellplan	  */
	public int getM_PurchaseSchedule_ID();

    /** Column name M_Warehouse_ID */
    public static final String COLUMNNAME_M_Warehouse_ID = "M_Warehouse_ID";

	/** Set Lager.
	  * Lager oder Ort f�r Dienstleistung
	  */
	public void setM_Warehouse_ID (int M_Warehouse_ID);

	/** Get Lager.
	  * Lager oder Ort f�r Dienstleistung
	  */
	public int getM_Warehouse_ID();

	public org.compiere.model.I_M_Warehouse getM_Warehouse() throws RuntimeException;

    /** Column name PricePO */
    public static final String COLUMNNAME_PricePO = "PricePO";

	/** Set Einkaufspreis.
	  * Preis, basierend auf Bestellung
	  */
	public void setPricePO (BigDecimal PricePO);

	/** Get Einkaufspreis.
	  * Preis, basierend auf Bestellung
	  */
	public BigDecimal getPricePO();

    /** Column name Qty */
    public static final String COLUMNNAME_Qty = "Qty";

	/** Set Menge.
	  * Menge
	  */
	public void setQty (BigDecimal Qty);

	/** Get Menge.
	  * Menge
	  */
	public BigDecimal getQty();

    /** Column name QtyOnHand */
    public static final String COLUMNNAME_QtyOnHand = "QtyOnHand";

	/** Set Bestand.
	  * Bestand
	  */
	public void setQtyOnHand (BigDecimal QtyOnHand);

	/** Get Bestand.
	  * Bestand
	  */
	public BigDecimal getQtyOnHand();

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

    /** Column name QtyToOrder */
    public static final String COLUMNNAME_QtyToOrder = "QtyToOrder";

	/** Set Quantity to Order	  */
	public void setQtyToOrder (BigDecimal QtyToOrder);

	/** Get Quantity to Order	  */
	public BigDecimal getQtyToOrder();

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

    /** Column name Value */
    public static final String COLUMNNAME_Value = "Value";

	/** Set Suchschl?ssel.
	  * Suchschl�ssel f�r den Eintrag im erforderlichen Format - muss eindeutig sein
	  */
	public void setValue (String Value);

	/** Get Suchschl?ssel.
	  * Suchschl�ssel f�r den Eintrag im erforderlichen Format - muss eindeutig sein
	  */
	public String getValue();
}
