/** Generated Model - DO NOT CHANGE */
package de.metas.esb.edi.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for EDI_cctop_invoic_500_v
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_EDI_cctop_invoic_500_v extends org.compiere.model.PO implements I_EDI_cctop_invoic_500_v, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -587829026L;

    /** Standard Constructor */
    public X_EDI_cctop_invoic_500_v (Properties ctx, int EDI_cctop_invoic_500_v_ID, String trxName)
    {
      super (ctx, EDI_cctop_invoic_500_v_ID, trxName);
      /** if (EDI_cctop_invoic_500_v_ID == 0)
        {
        } */
    }

    /** Load Constructor */
    public X_EDI_cctop_invoic_500_v (Properties ctx, ResultSet rs, String trxName)
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
	public org.compiere.model.I_C_Invoice getC_Invoice()
	{
		return get_ValueAsPO(COLUMNNAME_C_Invoice_ID, org.compiere.model.I_C_Invoice.class);
	}

	@Override
	public void setC_Invoice(org.compiere.model.I_C_Invoice C_Invoice)
	{
		set_ValueFromPO(COLUMNNAME_C_Invoice_ID, org.compiere.model.I_C_Invoice.class, C_Invoice);
	}

	/** Set Invoice.
		@param C_Invoice_ID 
		Invoice Identifier
	  */
	@Override
	public void setC_Invoice_ID (int C_Invoice_ID)
	{
		if (C_Invoice_ID < 1) 
			set_Value (COLUMNNAME_C_Invoice_ID, null);
		else 
			set_Value (COLUMNNAME_C_Invoice_ID, Integer.valueOf(C_Invoice_ID));
	}

	/** Get Invoice.
		@return Invoice Identifier
	  */
	@Override
	public int getC_Invoice_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Invoice_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set EanCom_Price_UOM.
		@param EanCom_Price_UOM EanCom_Price_UOM	  */
	@Override
	public void setEanCom_Price_UOM (java.lang.String EanCom_Price_UOM)
	{
		set_ValueNoCheck (COLUMNNAME_EanCom_Price_UOM, EanCom_Price_UOM);
	}

	/** Get EanCom_Price_UOM.
		@return EanCom_Price_UOM	  */
	@Override
	public java.lang.String getEanCom_Price_UOM () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_EanCom_Price_UOM);
	}

	/** Set eancom_uom.
		@param eancom_uom eancom_uom	  */
	@Override
	public void seteancom_uom (java.lang.String eancom_uom)
	{
		set_Value (COLUMNNAME_eancom_uom, eancom_uom);
	}

	/** Get eancom_uom.
		@return eancom_uom	  */
	@Override
	public java.lang.String geteancom_uom () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_eancom_uom);
	}

	/** Set EDI_cctop_invoic_500_v.
		@param EDI_cctop_invoic_500_v_ID EDI_cctop_invoic_500_v	  */
	@Override
	public void setEDI_cctop_invoic_500_v_ID (int EDI_cctop_invoic_500_v_ID)
	{
		if (EDI_cctop_invoic_500_v_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_EDI_cctop_invoic_500_v_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_EDI_cctop_invoic_500_v_ID, Integer.valueOf(EDI_cctop_invoic_500_v_ID));
	}

	/** Get EDI_cctop_invoic_500_v.
		@return EDI_cctop_invoic_500_v	  */
	@Override
	public int getEDI_cctop_invoic_500_v_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_EDI_cctop_invoic_500_v_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.esb.edi.model.I_EDI_cctop_invoic_v getEDI_cctop_invoic_v()
	{
		return get_ValueAsPO(COLUMNNAME_EDI_cctop_invoic_v_ID, de.metas.esb.edi.model.I_EDI_cctop_invoic_v.class);
	}

	@Override
	public void setEDI_cctop_invoic_v(de.metas.esb.edi.model.I_EDI_cctop_invoic_v EDI_cctop_invoic_v)
	{
		set_ValueFromPO(COLUMNNAME_EDI_cctop_invoic_v_ID, de.metas.esb.edi.model.I_EDI_cctop_invoic_v.class, EDI_cctop_invoic_v);
	}

	/** Set EDI_cctop_invoic_v.
		@param EDI_cctop_invoic_v_ID EDI_cctop_invoic_v	  */
	@Override
	public void setEDI_cctop_invoic_v_ID (int EDI_cctop_invoic_v_ID)
	{
		if (EDI_cctop_invoic_v_ID < 1) 
			set_Value (COLUMNNAME_EDI_cctop_invoic_v_ID, null);
		else 
			set_Value (COLUMNNAME_EDI_cctop_invoic_v_ID, Integer.valueOf(EDI_cctop_invoic_v_ID));
	}

	/** Get EDI_cctop_invoic_v.
		@return EDI_cctop_invoic_v	  */
	@Override
	public int getEDI_cctop_invoic_v_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_EDI_cctop_invoic_v_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set ISO Währungscode.
		@param ISO_Code 
		Dreibuchstabiger ISO 4217 Code für die Währung
	  */
	@Override
	public void setISO_Code (java.lang.String ISO_Code)
	{
		set_Value (COLUMNNAME_ISO_Code, ISO_Code);
	}

	/** Get ISO Währungscode.
		@return Dreibuchstabiger ISO 4217 Code für die Währung
	  */
	@Override
	public java.lang.String getISO_Code () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ISO_Code);
	}

	/** Set Leergut.
		@param Leergut Leergut	  */
	@Override
	public void setLeergut (java.lang.String Leergut)
	{
		set_Value (COLUMNNAME_Leergut, Leergut);
	}

	/** Get Leergut.
		@return Leergut	  */
	@Override
	public java.lang.String getLeergut () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Leergut);
	}

	/** Set Zeile Nr..
		@param Line 
		Einzelne Zeile in dem Dokument
	  */
	@Override
	public void setLine (int Line)
	{
		set_Value (COLUMNNAME_Line, Integer.valueOf(Line));
	}

	/** Get Zeile Nr..
		@return Einzelne Zeile in dem Dokument
	  */
	@Override
	public int getLine () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Line);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Zeilennetto.
		@param LineNetAmt 
		Nettowert Zeile (Menge * Einzelpreis) ohne Fracht und Gebühren
	  */
	@Override
	public void setLineNetAmt (java.math.BigDecimal LineNetAmt)
	{
		set_Value (COLUMNNAME_LineNetAmt, LineNetAmt);
	}

	/** Get Zeilennetto.
		@return Nettowert Zeile (Menge * Einzelpreis) ohne Fracht und Gebühren
	  */
	@Override
	public java.math.BigDecimal getLineNetAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_LineNetAmt);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Name.
		@param Name Name	  */
	@Override
	public void setName (java.lang.String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	/** Get Name.
		@return Name	  */
	@Override
	public java.lang.String getName () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Name);
	}

	/** Set Name Zusatz.
		@param Name2 
		Zusätzliche Bezeichnung
	  */
	@Override
	public void setName2 (java.lang.String Name2)
	{
		set_Value (COLUMNNAME_Name2, Name2);
	}

	/** Get Name Zusatz.
		@return Zusätzliche Bezeichnung
	  */
	@Override
	public java.lang.String getName2 () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Name2);
	}

	/** Set Order Line.
		@param OrderLine Order Line	  */
	@Override
	public void setOrderLine (int OrderLine)
	{
		set_Value (COLUMNNAME_OrderLine, Integer.valueOf(OrderLine));
	}

	/** Get Order Line.
		@return Order Line	  */
	@Override
	public int getOrderLine () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_OrderLine);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Auftragsreferenz.
		@param OrderPOReference Auftragsreferenz	  */
	@Override
	public void setOrderPOReference (java.lang.String OrderPOReference)
	{
		set_ValueNoCheck (COLUMNNAME_OrderPOReference, OrderPOReference);
	}

	/** Get Auftragsreferenz.
		@return Auftragsreferenz	  */
	@Override
	public java.lang.String getOrderPOReference () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_OrderPOReference);
	}

	/** Set Einzelpreis.
		@param PriceActual 
		Effektiver Preis
	  */
	@Override
	public void setPriceActual (java.math.BigDecimal PriceActual)
	{
		set_Value (COLUMNNAME_PriceActual, PriceActual);
	}

	/** Get Einzelpreis.
		@return Effektiver Preis
	  */
	@Override
	public java.math.BigDecimal getPriceActual () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PriceActual);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Auszeichnungspreis.
		@param PriceList 
		Auszeichnungspreis
	  */
	@Override
	public void setPriceList (java.math.BigDecimal PriceList)
	{
		set_Value (COLUMNNAME_PriceList, PriceList);
	}

	/** Get Auszeichnungspreis.
		@return Auszeichnungspreis
	  */
	@Override
	public java.math.BigDecimal getPriceList () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PriceList);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Produktbeschreibung.
		@param ProductDescription 
		Produktbeschreibung
	  */
	@Override
	public void setProductDescription (java.lang.String ProductDescription)
	{
		set_Value (COLUMNNAME_ProductDescription, ProductDescription);
	}

	/** Get Produktbeschreibung.
		@return Produktbeschreibung
	  */
	@Override
	public java.lang.String getProductDescription () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ProductDescription);
	}

	/** Set Berechn. Menge.
		@param QtyInvoiced 
		Menge in Produkt-Maßeinheit, die bereits in Rechnung gestellt wurde.
	  */
	@Override
	public void setQtyInvoiced (java.math.BigDecimal QtyInvoiced)
	{
		set_Value (COLUMNNAME_QtyInvoiced, QtyInvoiced);
	}

	/** Get Berechn. Menge.
		@return Menge in Produkt-Maßeinheit, die bereits in Rechnung gestellt wurde.
	  */
	@Override
	public java.math.BigDecimal getQtyInvoiced () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyInvoiced);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Satz.
		@param Rate 
		Rate or Tax or Exchange
	  */
	@Override
	public void setRate (java.math.BigDecimal Rate)
	{
		set_Value (COLUMNNAME_Rate, Rate);
	}

	/** Get Satz.
		@return Rate or Tax or Exchange
	  */
	@Override
	public java.math.BigDecimal getRate () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Rate);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Positions-Steuer.
		@param TaxAmtInfo 
		Betrag der enthaltenen oder zuzgl. Steuer in einer Rechungs- oder Auftragsposition
	  */
	@Override
	public void setTaxAmtInfo (java.math.BigDecimal TaxAmtInfo)
	{
		set_Value (COLUMNNAME_TaxAmtInfo, TaxAmtInfo);
	}

	/** Get Positions-Steuer.
		@return Betrag der enthaltenen oder zuzgl. Steuer in einer Rechungs- oder Auftragsposition
	  */
	@Override
	public java.math.BigDecimal getTaxAmtInfo () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TaxAmtInfo);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set taxfree.
		@param taxfree taxfree	  */
	@Override
	public void settaxfree (boolean taxfree)
	{
		set_Value (COLUMNNAME_taxfree, Boolean.valueOf(taxfree));
	}

	/** Get taxfree.
		@return taxfree	  */
	@Override
	public boolean istaxfree () 
	{
		Object oo = get_Value(COLUMNNAME_taxfree);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set UPC/EAN.
		@param UPC UPC/EAN	  */
	@Override
	public void setUPC (java.lang.String UPC)
	{
		set_Value (COLUMNNAME_UPC, UPC);
	}

	/** Get UPC/EAN.
		@return UPC/EAN	  */
	@Override
	public java.lang.String getUPC () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_UPC);
	}

	/** Set Suchschlüssel.
		@param Value 
		Suchschlüssel für den Eintrag im erforderlichen Format - muss eindeutig sein
	  */
	@Override
	public void setValue (java.lang.String Value)
	{
		set_Value (COLUMNNAME_Value, Value);
	}

	/** Get Suchschlüssel.
		@return Suchschlüssel für den Eintrag im erforderlichen Format - muss eindeutig sein
	  */
	@Override
	public java.lang.String getValue () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Value);
	}

	/** Set Produkt-Nr. Geschäftspartner.
		@param VendorProductNo 
		Produkt-Nr. beim Geschäftspartner
	  */
	@Override
	public void setVendorProductNo (java.lang.String VendorProductNo)
	{
		set_Value (COLUMNNAME_VendorProductNo, VendorProductNo);
	}

	/** Get Produkt-Nr. Geschäftspartner.
		@return Produkt-Nr. beim Geschäftspartner
	  */
	@Override
	public java.lang.String getVendorProductNo () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_VendorProductNo);
	}
}