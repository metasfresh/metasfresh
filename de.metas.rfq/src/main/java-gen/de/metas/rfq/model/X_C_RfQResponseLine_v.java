/** Generated Model - DO NOT CHANGE */
package de.metas.rfq.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.util.Env;

/** Generated Model for C_RfQResponseLine_v
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_RfQResponseLine_v extends org.compiere.model.PO implements I_C_RfQResponseLine_v, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -2082167581L;

    /** Standard Constructor */
    public X_C_RfQResponseLine_v (Properties ctx, int C_RfQResponseLine_v_ID, String trxName)
    {
      super (ctx, C_RfQResponseLine_v_ID, trxName);
      /** if (C_RfQResponseLine_v_ID == 0)
        {
			setBenchmarkPrice (Env.ZERO);
			setC_RfQLine_ID (0);
			setC_RfQLineQty_ID (0);
			setC_RfQResponse_ID (0);
			setC_RfQResponseLine_ID (0);
			setC_RfQResponseLineQty_ID (0);
			setC_UOM_ID (0);
			setLine (0);
			setPrice (Env.ZERO);
			setQty (Env.ZERO);
        } */
    }

    /** Load Constructor */
    public X_C_RfQResponseLine_v (Properties ctx, ResultSet rs, String trxName)
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

	/** 
	 * AD_Language AD_Reference_ID=106
	 * Reference name: AD_Language
	 */
	public static final int AD_LANGUAGE_AD_Reference_ID=106;
	/** Set Sprache.
		@param AD_Language 
		Language for this entity
	  */
	@Override
	public void setAD_Language (java.lang.String AD_Language)
	{

		set_ValueNoCheck (COLUMNNAME_AD_Language, AD_Language);
	}

	/** Get Sprache.
		@return Language for this entity
	  */
	@Override
	public java.lang.String getAD_Language () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_AD_Language);
	}

	/** Set Benchmark Price.
		@param BenchmarkPrice 
		Price to compare responses to
	  */
	@Override
	public void setBenchmarkPrice (java.math.BigDecimal BenchmarkPrice)
	{
		set_ValueNoCheck (COLUMNNAME_BenchmarkPrice, BenchmarkPrice);
	}

	/** Get Benchmark Price.
		@return Price to compare responses to
	  */
	@Override
	public java.math.BigDecimal getBenchmarkPrice () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_BenchmarkPrice);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	@Override
	public de.metas.rfq.model.I_C_RfQLine getC_RfQLine() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_RfQLine_ID, de.metas.rfq.model.I_C_RfQLine.class);
	}

	@Override
	public void setC_RfQLine(de.metas.rfq.model.I_C_RfQLine C_RfQLine)
	{
		set_ValueFromPO(COLUMNNAME_C_RfQLine_ID, de.metas.rfq.model.I_C_RfQLine.class, C_RfQLine);
	}

	/** Set RfQ Line.
		@param C_RfQLine_ID 
		Request for Quotation Line
	  */
	@Override
	public void setC_RfQLine_ID (int C_RfQLine_ID)
	{
		if (C_RfQLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_RfQLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_RfQLine_ID, Integer.valueOf(C_RfQLine_ID));
	}

	/** Get RfQ Line.
		@return Request for Quotation Line
	  */
	@Override
	public int getC_RfQLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_RfQLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.rfq.model.I_C_RfQLineQty getC_RfQLineQty() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_RfQLineQty_ID, de.metas.rfq.model.I_C_RfQLineQty.class);
	}

	@Override
	public void setC_RfQLineQty(de.metas.rfq.model.I_C_RfQLineQty C_RfQLineQty)
	{
		set_ValueFromPO(COLUMNNAME_C_RfQLineQty_ID, de.metas.rfq.model.I_C_RfQLineQty.class, C_RfQLineQty);
	}

	/** Set RfQ Line Quantity.
		@param C_RfQLineQty_ID 
		Request for Quotation Line Quantity
	  */
	@Override
	public void setC_RfQLineQty_ID (int C_RfQLineQty_ID)
	{
		if (C_RfQLineQty_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_RfQLineQty_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_RfQLineQty_ID, Integer.valueOf(C_RfQLineQty_ID));
	}

	/** Get RfQ Line Quantity.
		@return Request for Quotation Line Quantity
	  */
	@Override
	public int getC_RfQLineQty_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_RfQLineQty_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.rfq.model.I_C_RfQResponse getC_RfQResponse() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_RfQResponse_ID, de.metas.rfq.model.I_C_RfQResponse.class);
	}

	@Override
	public void setC_RfQResponse(de.metas.rfq.model.I_C_RfQResponse C_RfQResponse)
	{
		set_ValueFromPO(COLUMNNAME_C_RfQResponse_ID, de.metas.rfq.model.I_C_RfQResponse.class, C_RfQResponse);
	}

	/** Set Ausschreibungs-Antwort.
		@param C_RfQResponse_ID 
		Request for Quotation Response from a potential Vendor
	  */
	@Override
	public void setC_RfQResponse_ID (int C_RfQResponse_ID)
	{
		if (C_RfQResponse_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_RfQResponse_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_RfQResponse_ID, Integer.valueOf(C_RfQResponse_ID));
	}

	/** Get Ausschreibungs-Antwort.
		@return Request for Quotation Response from a potential Vendor
	  */
	@Override
	public int getC_RfQResponse_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_RfQResponse_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set RfQ Response Line.
		@param C_RfQResponseLine_ID 
		Request for Quotation Response Line
	  */
	@Override
	public void setC_RfQResponseLine_ID (int C_RfQResponseLine_ID)
	{
		if (C_RfQResponseLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_RfQResponseLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_RfQResponseLine_ID, Integer.valueOf(C_RfQResponseLine_ID));
	}

	/** Get RfQ Response Line.
		@return Request for Quotation Response Line
	  */
	@Override
	public int getC_RfQResponseLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_RfQResponseLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.rfq.model.I_C_RfQResponseLineQty getC_RfQResponseLineQty() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_RfQResponseLineQty_ID, de.metas.rfq.model.I_C_RfQResponseLineQty.class);
	}

	@Override
	public void setC_RfQResponseLineQty(de.metas.rfq.model.I_C_RfQResponseLineQty C_RfQResponseLineQty)
	{
		set_ValueFromPO(COLUMNNAME_C_RfQResponseLineQty_ID, de.metas.rfq.model.I_C_RfQResponseLineQty.class, C_RfQResponseLineQty);
	}

	/** Set RfQ Response Line Qty.
		@param C_RfQResponseLineQty_ID 
		Request for Quotation Response Line Quantity
	  */
	@Override
	public void setC_RfQResponseLineQty_ID (int C_RfQResponseLineQty_ID)
	{
		if (C_RfQResponseLineQty_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_RfQResponseLineQty_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_RfQResponseLineQty_ID, Integer.valueOf(C_RfQResponseLineQty_ID));
	}

	/** Get RfQ Response Line Qty.
		@return Request for Quotation Response Line Quantity
	  */
	@Override
	public int getC_RfQResponseLineQty_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_RfQResponseLineQty_ID);
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
		Unit of Measure
	  */
	@Override
	public void setC_UOM_ID (int C_UOM_ID)
	{
		if (C_UOM_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_UOM_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_UOM_ID, Integer.valueOf(C_UOM_ID));
	}

	/** Get Maßeinheit.
		@return Unit of Measure
	  */
	@Override
	public int getC_UOM_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_UOM_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Arbeitsbeginn.
		@param DateWorkStart 
		Date when work is (planned to be) started
	  */
	@Override
	public void setDateWorkStart (java.sql.Timestamp DateWorkStart)
	{
		set_ValueNoCheck (COLUMNNAME_DateWorkStart, DateWorkStart);
	}

	/** Get Arbeitsbeginn.
		@return Date when work is (planned to be) started
	  */
	@Override
	public java.sql.Timestamp getDateWorkStart () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_DateWorkStart);
	}

	/** Set Auslieferungstage.
		@param DeliveryDays 
		Number of Days (planned) until Delivery
	  */
	@Override
	public void setDeliveryDays (int DeliveryDays)
	{
		set_ValueNoCheck (COLUMNNAME_DeliveryDays, Integer.valueOf(DeliveryDays));
	}

	/** Get Auslieferungstage.
		@return Number of Days (planned) until Delivery
	  */
	@Override
	public int getDeliveryDays () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DeliveryDays);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Beschreibung.
		@param Description Beschreibung	  */
	@Override
	public void setDescription (java.lang.String Description)
	{
		set_ValueNoCheck (COLUMNNAME_Description, Description);
	}

	/** Get Beschreibung.
		@return Beschreibung	  */
	@Override
	public java.lang.String getDescription () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Description);
	}

	/** Set Rabatt %.
		@param Discount 
		Discount in percent
	  */
	@Override
	public void setDiscount (java.math.BigDecimal Discount)
	{
		set_ValueNoCheck (COLUMNNAME_Discount, Discount);
	}

	/** Get Rabatt %.
		@return Discount in percent
	  */
	@Override
	public java.math.BigDecimal getDiscount () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Discount);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Notiz / Zeilentext.
		@param DocumentNote 
		Additional information for a Document
	  */
	@Override
	public void setDocumentNote (java.lang.String DocumentNote)
	{
		set_ValueNoCheck (COLUMNNAME_DocumentNote, DocumentNote);
	}

	/** Get Notiz / Zeilentext.
		@return Additional information for a Document
	  */
	@Override
	public java.lang.String getDocumentNote () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DocumentNote);
	}

	/** Set Kommentar/Hilfe.
		@param Help 
		Comment or Hint
	  */
	@Override
	public void setHelp (java.lang.String Help)
	{
		set_ValueNoCheck (COLUMNNAME_Help, Help);
	}

	/** Get Kommentar/Hilfe.
		@return Comment or Hint
	  */
	@Override
	public java.lang.String getHelp () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Help);
	}

	/** Set Zeile Nr..
		@param Line 
		Unique line for this document
	  */
	@Override
	public void setLine (int Line)
	{
		set_ValueNoCheck (COLUMNNAME_Line, Integer.valueOf(Line));
	}

	/** Get Zeile Nr..
		@return Unique line for this document
	  */
	@Override
	public int getLine () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Line);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_M_AttributeSetInstance getM_AttributeSetInstance() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_AttributeSetInstance_ID, org.compiere.model.I_M_AttributeSetInstance.class);
	}

	@Override
	public void setM_AttributeSetInstance(org.compiere.model.I_M_AttributeSetInstance M_AttributeSetInstance)
	{
		set_ValueFromPO(COLUMNNAME_M_AttributeSetInstance_ID, org.compiere.model.I_M_AttributeSetInstance.class, M_AttributeSetInstance);
	}

	/** Set Ausprägung Merkmals-Satz.
		@param M_AttributeSetInstance_ID 
		Product Attribute Set Instance
	  */
	@Override
	public void setM_AttributeSetInstance_ID (int M_AttributeSetInstance_ID)
	{
		if (M_AttributeSetInstance_ID < 0) 
			set_ValueNoCheck (COLUMNNAME_M_AttributeSetInstance_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_AttributeSetInstance_ID, Integer.valueOf(M_AttributeSetInstance_ID));
	}

	/** Get Ausprägung Merkmals-Satz.
		@return Product Attribute Set Instance
	  */
	@Override
	public int getM_AttributeSetInstance_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_AttributeSetInstance_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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
			set_ValueNoCheck (COLUMNNAME_M_Product_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Product_ID, Integer.valueOf(M_Product_ID));
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

	/** Set Name.
		@param Name 
		Alphanumeric identifier of the entity
	  */
	@Override
	public void setName (java.lang.String Name)
	{
		set_ValueNoCheck (COLUMNNAME_Name, Name);
	}

	/** Get Name.
		@return Alphanumeric identifier of the entity
	  */
	@Override
	public java.lang.String getName () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Name);
	}

	/** Set Preis.
		@param Price 
		Price
	  */
	@Override
	public void setPrice (java.math.BigDecimal Price)
	{
		set_ValueNoCheck (COLUMNNAME_Price, Price);
	}

	/** Get Preis.
		@return Price
	  */
	@Override
	public java.math.BigDecimal getPrice () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Price);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Produktschlüssel.
		@param ProductValue 
		Key of the Product
	  */
	@Override
	public void setProductValue (java.lang.String ProductValue)
	{
		set_ValueNoCheck (COLUMNNAME_ProductValue, ProductValue);
	}

	/** Get Produktschlüssel.
		@return Key of the Product
	  */
	@Override
	public java.lang.String getProductValue () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ProductValue);
	}

	/** Set Menge.
		@param Qty 
		Quantity
	  */
	@Override
	public void setQty (java.math.BigDecimal Qty)
	{
		set_ValueNoCheck (COLUMNNAME_Qty, Qty);
	}

	/** Get Menge.
		@return Quantity
	  */
	@Override
	public java.math.BigDecimal getQty () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Qty);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set SKU.
		@param SKU 
		Stock Keeping Unit
	  */
	@Override
	public void setSKU (java.lang.String SKU)
	{
		set_ValueNoCheck (COLUMNNAME_SKU, SKU);
	}

	/** Get SKU.
		@return Stock Keeping Unit
	  */
	@Override
	public java.lang.String getSKU () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_SKU);
	}

	/** Set Symbol.
		@param UOMSymbol 
		Symbol for a Unit of Measure
	  */
	@Override
	public void setUOMSymbol (java.lang.String UOMSymbol)
	{
		set_ValueNoCheck (COLUMNNAME_UOMSymbol, UOMSymbol);
	}

	/** Get Symbol.
		@return Symbol for a Unit of Measure
	  */
	@Override
	public java.lang.String getUOMSymbol () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_UOMSymbol);
	}

	/** Set UPC/EAN.
		@param UPC 
		Bar Code (Universal Product Code or its superset European Article Number)
	  */
	@Override
	public void setUPC (java.lang.String UPC)
	{
		set_ValueNoCheck (COLUMNNAME_UPC, UPC);
	}

	/** Get UPC/EAN.
		@return Bar Code (Universal Product Code or its superset European Article Number)
	  */
	@Override
	public java.lang.String getUPC () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_UPC);
	}
}