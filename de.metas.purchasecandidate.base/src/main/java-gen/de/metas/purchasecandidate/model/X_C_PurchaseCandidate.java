/** Generated Model - DO NOT CHANGE */
package de.metas.purchasecandidate.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_PurchaseCandidate
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_PurchaseCandidate extends org.compiere.model.PO implements I_C_PurchaseCandidate, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 996874402L;

    /** Standard Constructor */
    public X_C_PurchaseCandidate (Properties ctx, int C_PurchaseCandidate_ID, String trxName)
    {
      super (ctx, C_PurchaseCandidate_ID, trxName);
      /** if (C_PurchaseCandidate_ID == 0)
        {
			setC_OrderLineSO_ID (0);
			setC_OrderSO_ID (0);
			setC_PurchaseCandidate_ID (0);
			setC_UOM_ID (0);
			setDateRequired (new Timestamp( System.currentTimeMillis() ));
			setIsAggregatePO (false); // N
			setM_Product_ID (0);
			setM_WarehousePO_ID (0);
			setProcessed (false); // N
			setProcessing (false); // N
			setQtyToPurchase (BigDecimal.ZERO);
			setVendor_ID (0);
        } */
    }

    /** Load Constructor */
    public X_C_PurchaseCandidate (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }


    /** Load Meta Data */
    @Override
    protected org.compiere.model.POInfo initPO (Properties ctx)
    {
      org.compiere.model.POInfo poi = org.compiere.model.POInfo.getPOInfo (ctx, Table_Name, get_TrxName());
      return poi;
    }

	@Override
	public org.compiere.model.I_C_BPartner_Product getC_BPartner_Product() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_BPartner_Product_ID, org.compiere.model.I_C_BPartner_Product.class);
	}

	@Override
	public void setC_BPartner_Product(org.compiere.model.I_C_BPartner_Product C_BPartner_Product)
	{
		set_ValueFromPO(COLUMNNAME_C_BPartner_Product_ID, org.compiere.model.I_C_BPartner_Product.class, C_BPartner_Product);
	}

	/** Set Geschäftspartner-Produkt.
		@param C_BPartner_Product_ID Geschäftspartner-Produkt	  */
	@Override
	public void setC_BPartner_Product_ID (int C_BPartner_Product_ID)
	{
		if (C_BPartner_Product_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_Product_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_Product_ID, Integer.valueOf(C_BPartner_Product_ID));
	}

	/** Get Geschäftspartner-Produkt.
		@return Geschäftspartner-Produkt	  */
	@Override
	public int getC_BPartner_Product_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_Product_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_Currency getC_Currency() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Currency_ID, org.compiere.model.I_C_Currency.class);
	}

	@Override
	public void setC_Currency(org.compiere.model.I_C_Currency C_Currency)
	{
		set_ValueFromPO(COLUMNNAME_C_Currency_ID, org.compiere.model.I_C_Currency.class, C_Currency);
	}

	/** Set Währung.
		@param C_Currency_ID 
		Die Währung für diesen Eintrag
	  */
	@Override
	public void setC_Currency_ID (int C_Currency_ID)
	{
		if (C_Currency_ID < 1) 
			set_Value (COLUMNNAME_C_Currency_ID, null);
		else 
			set_Value (COLUMNNAME_C_Currency_ID, Integer.valueOf(C_Currency_ID));
	}

	/** Get Währung.
		@return Die Währung für diesen Eintrag
	  */
	@Override
	public int getC_Currency_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Currency_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_OrderLine getC_OrderLineSO() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_OrderLineSO_ID, org.compiere.model.I_C_OrderLine.class);
	}

	@Override
	public void setC_OrderLineSO(org.compiere.model.I_C_OrderLine C_OrderLineSO)
	{
		set_ValueFromPO(COLUMNNAME_C_OrderLineSO_ID, org.compiere.model.I_C_OrderLine.class, C_OrderLineSO);
	}

	/** Set Auftragsposition.
		@param C_OrderLineSO_ID 
		Auftragsposition
	  */
	@Override
	public void setC_OrderLineSO_ID (int C_OrderLineSO_ID)
	{
		if (C_OrderLineSO_ID < 1) 
			set_Value (COLUMNNAME_C_OrderLineSO_ID, null);
		else 
			set_Value (COLUMNNAME_C_OrderLineSO_ID, Integer.valueOf(C_OrderLineSO_ID));
	}

	/** Get Auftragsposition.
		@return Auftragsposition
	  */
	@Override
	public int getC_OrderLineSO_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_OrderLineSO_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_Order getC_OrderSO() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_OrderSO_ID, org.compiere.model.I_C_Order.class);
	}

	@Override
	public void setC_OrderSO(org.compiere.model.I_C_Order C_OrderSO)
	{
		set_ValueFromPO(COLUMNNAME_C_OrderSO_ID, org.compiere.model.I_C_Order.class, C_OrderSO);
	}

	/** Set Auftrag.
		@param C_OrderSO_ID 
		Auftrag
	  */
	@Override
	public void setC_OrderSO_ID (int C_OrderSO_ID)
	{
		if (C_OrderSO_ID < 1) 
			set_Value (COLUMNNAME_C_OrderSO_ID, null);
		else 
			set_Value (COLUMNNAME_C_OrderSO_ID, Integer.valueOf(C_OrderSO_ID));
	}

	/** Get Auftrag.
		@return Auftrag
	  */
	@Override
	public int getC_OrderSO_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_OrderSO_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Purchase candidate.
		@param C_PurchaseCandidate_ID Purchase candidate	  */
	@Override
	public void setC_PurchaseCandidate_ID (int C_PurchaseCandidate_ID)
	{
		if (C_PurchaseCandidate_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_PurchaseCandidate_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_PurchaseCandidate_ID, Integer.valueOf(C_PurchaseCandidate_ID));
	}

	/** Get Purchase candidate.
		@return Purchase candidate	  */
	@Override
	public int getC_PurchaseCandidate_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_PurchaseCandidate_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_UOM getC_UOM() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_UOM_ID, org.compiere.model.I_C_UOM.class);
	}

	@Override
	public void setC_UOM(org.compiere.model.I_C_UOM C_UOM)
	{
		set_ValueFromPO(COLUMNNAME_C_UOM_ID, org.compiere.model.I_C_UOM.class, C_UOM);
	}

	/** Set Maßeinheit.
		@param C_UOM_ID 
		Maßeinheit
	  */
	@Override
	public void setC_UOM_ID (int C_UOM_ID)
	{
		if (C_UOM_ID < 1) 
			set_Value (COLUMNNAME_C_UOM_ID, null);
		else 
			set_Value (COLUMNNAME_C_UOM_ID, Integer.valueOf(C_UOM_ID));
	}

	/** Get Maßeinheit.
		@return Maßeinheit
	  */
	@Override
	public int getC_UOM_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_UOM_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Kd-Rohertragspreis.
		@param CustomerPriceGrossProfit 
		Effektiver Verkaufspreis minus Skonto und Rückerstattung
	  */
	@Override
	public void setCustomerPriceGrossProfit (java.math.BigDecimal CustomerPriceGrossProfit)
	{
		set_Value (COLUMNNAME_CustomerPriceGrossProfit, CustomerPriceGrossProfit);
	}

	/** Get Kd-Rohertragspreis.
		@return Effektiver Verkaufspreis minus Skonto und Rückerstattung
	  */
	@Override
	public java.math.BigDecimal getCustomerPriceGrossProfit () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_CustomerPriceGrossProfit);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Zieldatum.
		@param DateRequired Zieldatum	  */
	@Override
	public void setDateRequired (java.sql.Timestamp DateRequired)
	{
		set_Value (COLUMNNAME_DateRequired, DateRequired);
	}

	/** Get Zieldatum.
		@return Zieldatum	  */
	@Override
	public java.sql.Timestamp getDateRequired () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_DateRequired);
	}

	/** Set Aggregate Purchase Orders.
		@param IsAggregatePO Aggregate Purchase Orders	  */
	@Override
	public void setIsAggregatePO (boolean IsAggregatePO)
	{
		set_Value (COLUMNNAME_IsAggregatePO, Boolean.valueOf(IsAggregatePO));
	}

	/** Get Aggregate Purchase Orders.
		@return Aggregate Purchase Orders	  */
	@Override
	public boolean isAggregatePO () 
	{
		Object oo = get_Value(COLUMNNAME_IsAggregatePO);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	@Override
	public org.compiere.model.I_M_Product getM_Product() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_Product_ID, org.compiere.model.I_M_Product.class);
	}

	@Override
	public void setM_Product(org.compiere.model.I_M_Product M_Product)
	{
		set_ValueFromPO(COLUMNNAME_M_Product_ID, org.compiere.model.I_M_Product.class, M_Product);
	}

	/** Set Produkt.
		@param M_Product_ID 
		Produkt, Leistung, Artikel
	  */
	@Override
	public void setM_Product_ID (int M_Product_ID)
	{
		if (M_Product_ID < 1) 
			set_Value (COLUMNNAME_M_Product_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_ID, Integer.valueOf(M_Product_ID));
	}

	/** Get Produkt.
		@return Produkt, Leistung, Artikel
	  */
	@Override
	public int getM_Product_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Product_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_M_Warehouse getM_WarehousePO() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_WarehousePO_ID, org.compiere.model.I_M_Warehouse.class);
	}

	@Override
	public void setM_WarehousePO(org.compiere.model.I_M_Warehouse M_WarehousePO)
	{
		set_ValueFromPO(COLUMNNAME_M_WarehousePO_ID, org.compiere.model.I_M_Warehouse.class, M_WarehousePO);
	}

	/** Set Liefer-Lager.
		@param M_WarehousePO_ID 
		Lager, an das der Lieferant eine Bestellung liefern soll.
	  */
	@Override
	public void setM_WarehousePO_ID (int M_WarehousePO_ID)
	{
		if (M_WarehousePO_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_WarehousePO_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_WarehousePO_ID, Integer.valueOf(M_WarehousePO_ID));
	}

	/** Get Liefer-Lager.
		@return Lager, an das der Lieferant eine Bestellung liefern soll.
	  */
	@Override
	public int getM_WarehousePO_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_WarehousePO_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Rohertragspreis.
		@param PriceGrossProfit 
		Endpreis pro Einheit nach Abzug des erwarteten Rohertrages (Skonto, Rückvergütung usw).
	  */
	@Override
	public void setPriceGrossProfit (java.math.BigDecimal PriceGrossProfit)
	{
		set_Value (COLUMNNAME_PriceGrossProfit, PriceGrossProfit);
	}

	/** Get Rohertragspreis.
		@return Endpreis pro Einheit nach Abzug des erwarteten Rohertrages (Skonto, Rückvergütung usw).
	  */
	@Override
	public java.math.BigDecimal getPriceGrossProfit () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PriceGrossProfit);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Verarbeitet.
		@param Processed 
		Checkbox sagt aus, ob der Beleg verarbeitet wurde. 
	  */
	@Override
	public void setProcessed (boolean Processed)
	{
		set_Value (COLUMNNAME_Processed, Boolean.valueOf(Processed));
	}

	/** Get Verarbeitet.
		@return Checkbox sagt aus, ob der Beleg verarbeitet wurde. 
	  */
	@Override
	public boolean isProcessed () 
	{
		Object oo = get_Value(COLUMNNAME_Processed);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Process Now.
		@param Processing Process Now	  */
	@Override
	public void setProcessing (boolean Processing)
	{
		set_Value (COLUMNNAME_Processing, Boolean.valueOf(Processing));
	}

	/** Get Process Now.
		@return Process Now	  */
	@Override
	public boolean isProcessing () 
	{
		Object oo = get_Value(COLUMNNAME_Processing);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Bestellte Menge.
		@param PurchasedQty Bestellte Menge	  */
	@Override
	public void setPurchasedQty (java.math.BigDecimal PurchasedQty)
	{
		set_Value (COLUMNNAME_PurchasedQty, PurchasedQty);
	}

	/** Get Bestellte Menge.
		@return Bestellte Menge	  */
	@Override
	public java.math.BigDecimal getPurchasedQty () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PurchasedQty);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set VK Preis netto.
		@param PurchasePriceActual 
		Effektiver Verkaufspreis
	  */
	@Override
	public void setPurchasePriceActual (java.math.BigDecimal PurchasePriceActual)
	{
		set_Value (COLUMNNAME_PurchasePriceActual, PurchasePriceActual);
	}

	/** Get VK Preis netto.
		@return Effektiver Verkaufspreis
	  */
	@Override
	public java.math.BigDecimal getPurchasePriceActual () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PurchasePriceActual);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Bestellmenge.
		@param QtyToPurchase Bestellmenge	  */
	@Override
	public void setQtyToPurchase (java.math.BigDecimal QtyToPurchase)
	{
		set_Value (COLUMNNAME_QtyToPurchase, QtyToPurchase);
	}

	/** Get Bestellmenge.
		@return Bestellmenge	  */
	@Override
	public java.math.BigDecimal getQtyToPurchase () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyToPurchase);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Wiedervorlage Datum.
		@param ReminderDate Wiedervorlage Datum	  */
	@Override
	public void setReminderDate (java.sql.Timestamp ReminderDate)
	{
		set_Value (COLUMNNAME_ReminderDate, ReminderDate);
	}

	/** Get Wiedervorlage Datum.
		@return Wiedervorlage Datum	  */
	@Override
	public java.sql.Timestamp getReminderDate () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_ReminderDate);
	}

	@Override
	public org.compiere.model.I_C_BPartner getVendor() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_Vendor_ID, org.compiere.model.I_C_BPartner.class);
	}

	@Override
	public void setVendor(org.compiere.model.I_C_BPartner Vendor)
	{
		set_ValueFromPO(COLUMNNAME_Vendor_ID, org.compiere.model.I_C_BPartner.class, Vendor);
	}

	/** Set Lieferant.
		@param Vendor_ID 
		The Vendor of the product/service
	  */
	@Override
	public void setVendor_ID (int Vendor_ID)
	{
		if (Vendor_ID < 1) 
			set_Value (COLUMNNAME_Vendor_ID, null);
		else 
			set_Value (COLUMNNAME_Vendor_ID, Integer.valueOf(Vendor_ID));
	}

	/** Get Lieferant.
		@return The Vendor of the product/service
	  */
	@Override
	public int getVendor_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Vendor_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}