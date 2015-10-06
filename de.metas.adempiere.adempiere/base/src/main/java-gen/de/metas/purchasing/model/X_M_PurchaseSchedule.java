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
/** Generated Model - DO NOT CHANGE */
package de.metas.purchasing.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.model.*;
import org.compiere.util.Env;

/** Generated Model for M_PurchaseSchedule
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_M_PurchaseSchedule extends PO implements I_M_PurchaseSchedule, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20110426L;

    /** Standard Constructor */
    public X_M_PurchaseSchedule (Properties ctx, int M_PurchaseSchedule_ID, String trxName)
    {
      super (ctx, M_PurchaseSchedule_ID, trxName);
      /** if (M_PurchaseSchedule_ID == 0)
        {
			setIsIndividualPOSchedule (false);
// N
			setM_PurchaseSchedule_ID (0);
			setM_Warehouse_ID (0);
			setValue (null);
// @M_PurchaseSchedule_ID@
        } */
    }

    /** Load Constructor */
    public X_M_PurchaseSchedule (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 3 - Client - Org 
      */
    protected int get_AccessLevel()
    {
      return accessLevel.intValue();
    }

    /** Load Meta Data */
    protected POInfo initPO (Properties ctx)
    {
      POInfo poi = POInfo.getPOInfo (ctx, Table_ID, get_TrxName());
      return poi;
    }

    public String toString()
    {
      StringBuffer sb = new StringBuffer ("X_M_PurchaseSchedule[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public org.compiere.model.I_C_BPartner getC_BPartner() throws RuntimeException
    {
		return (org.compiere.model.I_C_BPartner)MTable.get(getCtx(), org.compiere.model.I_C_BPartner.Table_Name)
			.getPO(getC_BPartner_ID(), get_TrxName());	}

	/** Set Geschaeftspartner.
		@param C_BPartner_ID 
		Bezeichnet einen Gesch�ftspartner
	  */
	public void setC_BPartner_ID (int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
	}

	/** Get Geschaeftspartner.
		@return Bezeichnet einen Gesch�ftspartner
	  */
	public int getC_BPartner_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_OrderLine getC_OrderLine_Individual() throws RuntimeException
    {
		return (org.compiere.model.I_C_OrderLine)MTable.get(getCtx(), org.compiere.model.I_C_OrderLine.Table_Name)
			.getPO(getC_OrderLine_Individual_ID(), get_TrxName());	}

	/** Set Sonder-Auftragsposition.
		@param C_OrderLine_Individual_ID Sonder-Auftragsposition	  */
	public void setC_OrderLine_Individual_ID (int C_OrderLine_Individual_ID)
	{
		if (C_OrderLine_Individual_ID < 1) 
			set_Value (COLUMNNAME_C_OrderLine_Individual_ID, null);
		else 
			set_Value (COLUMNNAME_C_OrderLine_Individual_ID, Integer.valueOf(C_OrderLine_Individual_ID));
	}

	/** Get Sonder-Auftragsposition.
		@return Sonder-Auftragsposition	  */
	public int getC_OrderLine_Individual_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_OrderLine_Individual_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_Order getC_OrderPO() throws RuntimeException
    {
		return (org.compiere.model.I_C_Order)MTable.get(getCtx(), org.compiere.model.I_C_Order.Table_Name)
			.getPO(getC_OrderPO_ID(), get_TrxName());	}

	/** Set Bestellung.
		@param C_OrderPO_ID 
		Bestellung
	  */
	public void setC_OrderPO_ID (int C_OrderPO_ID)
	{
		if (C_OrderPO_ID < 1) 
			set_Value (COLUMNNAME_C_OrderPO_ID, null);
		else 
			set_Value (COLUMNNAME_C_OrderPO_ID, Integer.valueOf(C_OrderPO_ID));
	}

	/** Get Bestellung.
		@return Bestellung
	  */
	public int getC_OrderPO_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_OrderPO_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Bestellung anlegen.
		@param CreatePO 
		Bestellung anlegen
	  */
	public void setCreatePO (String CreatePO)
	{
		set_Value (COLUMNNAME_CreatePO, CreatePO);
	}

	/** Get Bestellung anlegen.
		@return Bestellung anlegen
	  */
	public String getCreatePO () 
	{
		return (String)get_Value(COLUMNNAME_CreatePO);
	}

	/** Set Beschreibung.
		@param Description 
		Optional short description of the record
	  */
	public void setDescription (String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	/** Get Beschreibung.
		@return Optional short description of the record
	  */
	public String getDescription () 
	{
		return (String)get_Value(COLUMNNAME_Description);
	}

	public org.compiere.model.I_C_BPartner getDropShip_BPartner() throws RuntimeException
    {
		return (org.compiere.model.I_C_BPartner)MTable.get(getCtx(), org.compiere.model.I_C_BPartner.Table_Name)
			.getPO(getDropShip_BPartner_ID(), get_TrxName());	}

	/** Set Streckengeschaeft-Kunde.
		@param DropShip_BPartner_ID 
		Business Partner to ship to
	  */
	public void setDropShip_BPartner_ID (int DropShip_BPartner_ID)
	{
		if (DropShip_BPartner_ID < 1) 
			set_Value (COLUMNNAME_DropShip_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_DropShip_BPartner_ID, Integer.valueOf(DropShip_BPartner_ID));
	}

	/** Get Streckengeschaeft-Kunde.
		@return Business Partner to ship to
	  */
	public int getDropShip_BPartner_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DropShip_BPartner_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_BPartner_Location getDropShip_Location() throws RuntimeException
    {
		return (org.compiere.model.I_C_BPartner_Location)MTable.get(getCtx(), org.compiere.model.I_C_BPartner_Location.Table_Name)
			.getPO(getDropShip_Location_ID(), get_TrxName());	}

	/** Set Streckengeschaeft-Ort.
		@param DropShip_Location_ID 
		Business Partner Location for shipping to
	  */
	public void setDropShip_Location_ID (int DropShip_Location_ID)
	{
		if (DropShip_Location_ID < 1) 
			set_Value (COLUMNNAME_DropShip_Location_ID, null);
		else 
			set_Value (COLUMNNAME_DropShip_Location_ID, Integer.valueOf(DropShip_Location_ID));
	}

	/** Get Streckengeschaeft-Ort.
		@return Business Partner Location for shipping to
	  */
	public int getDropShip_Location_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DropShip_Location_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_AD_User getDropShip_User() throws RuntimeException
    {
		return (org.compiere.model.I_AD_User)MTable.get(getCtx(), org.compiere.model.I_AD_User.Table_Name)
			.getPO(getDropShip_User_ID(), get_TrxName());	}

	/** Set Streckengeschaeft-Ansprechpartner.
		@param DropShip_User_ID 
		Business Partner Contact for drop shipment
	  */
	public void setDropShip_User_ID (int DropShip_User_ID)
	{
		if (DropShip_User_ID < 1) 
			set_Value (COLUMNNAME_DropShip_User_ID, null);
		else 
			set_Value (COLUMNNAME_DropShip_User_ID, Integer.valueOf(DropShip_User_ID));
	}

	/** Get Streckengeschaeft-Ansprechpartner.
		@return Business Partner Contact for drop shipment
	  */
	public int getDropShip_User_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DropShip_User_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Bestellen.
		@param IncludeInPurchase 
		In Bestellung aufnehmen
	  */
	public void setIncludeInPurchase (boolean IncludeInPurchase)
	{
		set_Value (COLUMNNAME_IncludeInPurchase, Boolean.valueOf(IncludeInPurchase));
	}

	/** Get Bestellen.
		@return In Bestellung aufnehmen
	  */
	public boolean isIncludeInPurchase () 
	{
		Object oo = get_Value(COLUMNNAME_IncludeInPurchase);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Streckengeschaeft.
		@param IsDropShip 
		Beim Streckengeschaeft wird die Ware direkt vom Lieferanten zum Kunden geliefert
	  */
	public void setIsDropShip (boolean IsDropShip)
	{
		set_Value (COLUMNNAME_IsDropShip, Boolean.valueOf(IsDropShip));
	}

	/** Get Streckengeschaeft.
		@return Beim Streckengeschaeft wird die Ware direkt vom Lieferanten zum Kunden geliefert
	  */
	public boolean isDropShip () 
	{
		Object oo = get_Value(COLUMNNAME_IsDropShip);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Einzel-/Sonderbestellung.
		@param IsIndividualPOSchedule 
		Bestellung soll einzeln Disponiert werden
	  */
	public void setIsIndividualPOSchedule (boolean IsIndividualPOSchedule)
	{
		set_Value (COLUMNNAME_IsIndividualPOSchedule, Boolean.valueOf(IsIndividualPOSchedule));
	}

	/** Get Einzel-/Sonderbestellung.
		@return Bestellung soll einzeln Disponiert werden
	  */
	public boolean isIndividualPOSchedule () 
	{
		Object oo = get_Value(COLUMNNAME_IsIndividualPOSchedule);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	public I_M_AttributeSetInstance getM_AttributeSetInstance() throws RuntimeException
    {
		return (I_M_AttributeSetInstance)MTable.get(getCtx(), I_M_AttributeSetInstance.Table_Name)
			.getPO(getM_AttributeSetInstance_ID(), get_TrxName());	}

	/** Set Auspraegung Merkmals-Satz.
		@param M_AttributeSetInstance_ID 
		Instanz des Merkmals-Satzes zum Produkt
	  */
	public void setM_AttributeSetInstance_ID (int M_AttributeSetInstance_ID)
	{
		if (M_AttributeSetInstance_ID < 0) 
			set_Value (COLUMNNAME_M_AttributeSetInstance_ID, null);
		else 
			set_Value (COLUMNNAME_M_AttributeSetInstance_ID, Integer.valueOf(M_AttributeSetInstance_ID));
	}

	/** Get Auspraegung Merkmals-Satz.
		@return Instanz des Merkmals-Satzes zum Produkt
	  */
	public int getM_AttributeSetInstance_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_AttributeSetInstance_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_M_Product getM_Product() throws RuntimeException
    {
		return (org.compiere.model.I_M_Product)MTable.get(getCtx(), org.compiere.model.I_M_Product.Table_Name)
			.getPO(getM_Product_ID(), get_TrxName());	}

	/** Set Produkt.
		@param M_Product_ID 
		Produkt, Leistung, Artikel
	  */
	public void setM_Product_ID (int M_Product_ID)
	{
		if (M_Product_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Product_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Product_ID, Integer.valueOf(M_Product_ID));
	}

	/** Get Produkt.
		@return Produkt, Leistung, Artikel
	  */
	public int getM_Product_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Product_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Bestellplan.
		@param M_PurchaseSchedule_ID Bestellplan	  */
	public void setM_PurchaseSchedule_ID (int M_PurchaseSchedule_ID)
	{
		if (M_PurchaseSchedule_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_PurchaseSchedule_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_PurchaseSchedule_ID, Integer.valueOf(M_PurchaseSchedule_ID));
	}

	/** Get Bestellplan.
		@return Bestellplan	  */
	public int getM_PurchaseSchedule_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_PurchaseSchedule_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_M_Warehouse getM_Warehouse() throws RuntimeException
    {
		return (org.compiere.model.I_M_Warehouse)MTable.get(getCtx(), org.compiere.model.I_M_Warehouse.Table_Name)
			.getPO(getM_Warehouse_ID(), get_TrxName());	}

	/** Set Lager.
		@param M_Warehouse_ID 
		Lager oder Ort f�r Dienstleistung
	  */
	public void setM_Warehouse_ID (int M_Warehouse_ID)
	{
		if (M_Warehouse_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Warehouse_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Warehouse_ID, Integer.valueOf(M_Warehouse_ID));
	}

	/** Get Lager.
		@return Lager oder Ort f�r Dienstleistung
	  */
	public int getM_Warehouse_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Warehouse_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Einkaufspreis.
		@param PricePO 
		Preis, basierend auf Bestellung
	  */
	public void setPricePO (BigDecimal PricePO)
	{
		set_Value (COLUMNNAME_PricePO, PricePO);
	}

	/** Get Einkaufspreis.
		@return Preis, basierend auf Bestellung
	  */
	public BigDecimal getPricePO () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PricePO);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Menge.
		@param Qty 
		Menge
	  */
	public void setQty (BigDecimal Qty)
	{
		set_Value (COLUMNNAME_Qty, Qty);
	}

	/** Get Menge.
		@return Menge
	  */
	public BigDecimal getQty () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Qty);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Bestand.
		@param QtyOnHand 
		Bestand
	  */
	public void setQtyOnHand (BigDecimal QtyOnHand)
	{
		set_Value (COLUMNNAME_QtyOnHand, QtyOnHand);
	}

	/** Get Bestand.
		@return Bestand
	  */
	public BigDecimal getQtyOnHand () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyOnHand);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Bestellte Menge.
		@param QtyOrdered 
		Bestellte Menge
	  */
	public void setQtyOrdered (BigDecimal QtyOrdered)
	{
		set_Value (COLUMNNAME_QtyOrdered, QtyOrdered);
	}

	/** Get Bestellte Menge.
		@return Bestellte Menge
	  */
	public BigDecimal getQtyOrdered () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyOrdered);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Reservierte Menge.
		@param QtyReserved 
		Reservierte Menge
	  */
	public void setQtyReserved (BigDecimal QtyReserved)
	{
		set_Value (COLUMNNAME_QtyReserved, QtyReserved);
	}

	/** Get Reservierte Menge.
		@return Reservierte Menge
	  */
	public BigDecimal getQtyReserved () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyReserved);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Quantity to Order.
		@param QtyToOrder Quantity to Order	  */
	public void setQtyToOrder (BigDecimal QtyToOrder)
	{
		set_ValueNoCheck (COLUMNNAME_QtyToOrder, QtyToOrder);
	}

	/** Get Quantity to Order.
		@return Quantity to Order	  */
	public BigDecimal getQtyToOrder () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyToOrder);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Suchschl?ssel.
		@param Value 
		Suchschl�ssel f�r den Eintrag im erforderlichen Format - muss eindeutig sein
	  */
	public void setValue (String Value)
	{
		set_ValueNoCheck (COLUMNNAME_Value, Value);
	}

	/** Get Suchschl?ssel.
		@return Suchschl�ssel f�r den Eintrag im erforderlichen Format - muss eindeutig sein
	  */
	public String getValue () 
	{
		return (String)get_Value(COLUMNNAME_Value);
	}
}