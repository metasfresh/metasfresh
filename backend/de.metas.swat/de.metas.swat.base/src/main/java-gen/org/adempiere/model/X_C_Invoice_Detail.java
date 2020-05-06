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
package org.adempiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_Invoice_Detail
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_Invoice_Detail extends org.compiere.model.PO implements I_C_Invoice_Detail, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 2037536679L;

    /** Standard Constructor */
    public X_C_Invoice_Detail (Properties ctx, int C_Invoice_Detail_ID, String trxName)
    {
      super (ctx, C_Invoice_Detail_ID, trxName);
      /** if (C_Invoice_Detail_ID == 0)
        {
			setC_Invoice_Detail_ID (0);
			setIsDetailOverridesLine (false); // N
			setIsPrintBefore (false); // N
			setIsPrinted (true); // Y
			setSeqNo (0); // 0
        } */
    }

    /** Load Constructor */
    public X_C_Invoice_Detail (Properties ctx, ResultSet rs, String trxName)
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
    public String toString()
    {
      StringBuilder sb = new StringBuilder ("X_C_Invoice_Detail[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Invoice detailed informations.
		@param C_Invoice_Detail_ID Invoice detailed informations	  */
	@Override
	public void setC_Invoice_Detail_ID (int C_Invoice_Detail_ID)
	{
		if (C_Invoice_Detail_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Invoice_Detail_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Invoice_Detail_ID, Integer.valueOf(C_Invoice_Detail_ID));
	}

	/** Get Invoice detailed informations.
		@return Invoice detailed informations	  */
	@Override
	public int getC_Invoice_Detail_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Invoice_Detail_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Rechnung.
		@param C_Invoice_ID 
		Invoice Identifier
	  */
	@Override
	public void setC_Invoice_ID (int C_Invoice_ID)
	{
		if (C_Invoice_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Invoice_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Invoice_ID, Integer.valueOf(C_Invoice_ID));
	}

	/** Get Rechnung.
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

	@Override
	public org.compiere.model.I_C_InvoiceLine getC_InvoiceLine()
	{
		return get_ValueAsPO(COLUMNNAME_C_InvoiceLine_ID, org.compiere.model.I_C_InvoiceLine.class);
	}

	@Override
	public void setC_InvoiceLine(org.compiere.model.I_C_InvoiceLine C_InvoiceLine)
	{
		set_ValueFromPO(COLUMNNAME_C_InvoiceLine_ID, org.compiere.model.I_C_InvoiceLine.class, C_InvoiceLine);
	}

	/** Set Rechnungsposition.
		@param C_InvoiceLine_ID 
		Rechnungszeile
	  */
	@Override
	public void setC_InvoiceLine_ID (int C_InvoiceLine_ID)
	{
		if (C_InvoiceLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_InvoiceLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_InvoiceLine_ID, Integer.valueOf(C_InvoiceLine_ID));
	}

	/** Get Rechnungsposition.
		@return Rechnungszeile
	  */
	@Override
	public int getC_InvoiceLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_InvoiceLine_ID);
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

	/** Set Beschreibung.
		@param Description Beschreibung	  */
	@Override
	public void setDescription (java.lang.String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
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
		Abschlag in Prozent
	  */
	@Override
	public void setDiscount (java.math.BigDecimal Discount)
	{
		set_Value (COLUMNNAME_Discount, Discount);
	}

	/** Get Rabatt %.
		@return Abschlag in Prozent
	  */
	@Override
	public java.math.BigDecimal getDiscount () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Discount);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Detail-Info statt Rechnungszeile andrucken.
		@param IsDetailOverridesLine Detail-Info statt Rechnungszeile andrucken	  */
	@Override
	public void setIsDetailOverridesLine (boolean IsDetailOverridesLine)
	{
		set_Value (COLUMNNAME_IsDetailOverridesLine, Boolean.valueOf(IsDetailOverridesLine));
	}

	/** Get Detail-Info statt Rechnungszeile andrucken.
		@return Detail-Info statt Rechnungszeile andrucken	  */
	@Override
	public boolean isDetailOverridesLine () 
	{
		Object oo = get_Value(COLUMNNAME_IsDetailOverridesLine);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set davor andrucken.
		@param IsPrintBefore davor andrucken	  */
	@Override
	public void setIsPrintBefore (boolean IsPrintBefore)
	{
		set_Value (COLUMNNAME_IsPrintBefore, Boolean.valueOf(IsPrintBefore));
	}

	/** Get davor andrucken.
		@return davor andrucken	  */
	@Override
	public boolean isPrintBefore () 
	{
		Object oo = get_Value(COLUMNNAME_IsPrintBefore);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set andrucken.
		@param IsPrinted 
		Indicates if this document / line is printed
	  */
	@Override
	public void setIsPrinted (boolean IsPrinted)
	{
		set_Value (COLUMNNAME_IsPrinted, Boolean.valueOf(IsPrinted));
	}

	/** Get andrucken.
		@return Indicates if this document / line is printed
	  */
	@Override
	public boolean isPrinted () 
	{
		Object oo = get_Value(COLUMNNAME_IsPrinted);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Label.
		@param Label Label	  */
	@Override
	public void setLabel (java.lang.String Label)
	{
		set_Value (COLUMNNAME_Label, Label);
	}

	/** Get Label.
		@return Label	  */
	@Override
	public java.lang.String getLabel () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Label);
	}

	@Override
	public org.compiere.model.I_M_AttributeSetInstance getM_AttributeSetInstance()
	{
		return get_ValueAsPO(COLUMNNAME_M_AttributeSetInstance_ID, org.compiere.model.I_M_AttributeSetInstance.class);
	}

	@Override
	public void setM_AttributeSetInstance(org.compiere.model.I_M_AttributeSetInstance M_AttributeSetInstance)
	{
		set_ValueFromPO(COLUMNNAME_M_AttributeSetInstance_ID, org.compiere.model.I_M_AttributeSetInstance.class, M_AttributeSetInstance);
	}

	/** Set Merkmale.
		@param M_AttributeSetInstance_ID 
		Merkmals Ausprägungen zum Produkt
	  */
	@Override
	public void setM_AttributeSetInstance_ID (int M_AttributeSetInstance_ID)
	{
		if (M_AttributeSetInstance_ID < 0) 
			set_Value (COLUMNNAME_M_AttributeSetInstance_ID, null);
		else 
			set_Value (COLUMNNAME_M_AttributeSetInstance_ID, Integer.valueOf(M_AttributeSetInstance_ID));
	}

	/** Get Merkmale.
		@return Merkmals Ausprägungen zum Produkt
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

	/** Set Notiz.
		@param Note 
		Optional weitere Information
	  */
	@Override
	public void setNote (java.lang.String Note)
	{
		set_Value (COLUMNNAME_Note, Note);
	}

	/** Get Notiz.
		@return Optional weitere Information
	  */
	@Override
	public java.lang.String getNote () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Note);
	}

	/** Set Anteil.
		@param Percentage Anteil	  */
	@Override
	public void setPercentage (java.math.BigDecimal Percentage)
	{
		set_Value (COLUMNNAME_Percentage, Percentage);
	}

	/** Get Anteil.
		@return Anteil	  */
	@Override
	public java.math.BigDecimal getPercentage () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Percentage);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
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

	/** Set Preis.
		@param PriceEntered 
		Eingegebener Preis - der Preis basierend auf der gewählten Mengeneinheit
	  */
	@Override
	public void setPriceEntered (java.math.BigDecimal PriceEntered)
	{
		set_Value (COLUMNNAME_PriceEntered, PriceEntered);
	}

	/** Get Preis.
		@return Eingegebener Preis - der Preis basierend auf der gewählten Mengeneinheit
	  */
	@Override
	public java.math.BigDecimal getPriceEntered () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PriceEntered);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	@Override
	public org.compiere.model.I_C_UOM getPrice_UOM() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_Price_UOM_ID, org.compiere.model.I_C_UOM.class);
	}

	@Override
	public void setPrice_UOM(org.compiere.model.I_C_UOM Price_UOM)
	{
		set_ValueFromPO(COLUMNNAME_Price_UOM_ID, org.compiere.model.I_C_UOM.class, Price_UOM);
	}

	/** Set Preiseinheit.
		@param Price_UOM_ID Preiseinheit	  */
	@Override
	public void setPrice_UOM_ID (int Price_UOM_ID)
	{
		if (Price_UOM_ID < 1)
			set_Value (COLUMNNAME_Price_UOM_ID, null);
		else
			set_Value (COLUMNNAME_Price_UOM_ID, Integer.valueOf(Price_UOM_ID));
	}

	/** Get Preiseinheit.
		@return Preiseinheit	  */
	@Override
	public int getPrice_UOM_ID ()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Price_UOM_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Menge.
		@param Qty 
		Menge
	  */
	@Override
	public void setQty (java.math.BigDecimal Qty)
	{
		set_Value (COLUMNNAME_Qty, Qty);
	}

	/** Get Menge.
		@return Menge
	  */
	@Override
	public java.math.BigDecimal getQty () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Qty);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Bestellte Menge in Preiseinheit.
		@param QtyEnteredInPriceUOM 
		Bestellte Menge in Preiseinheit
	  */
	@Override
	public void setQtyEnteredInPriceUOM (java.math.BigDecimal QtyEnteredInPriceUOM)
	{
		set_Value (COLUMNNAME_QtyEnteredInPriceUOM, QtyEnteredInPriceUOM);
	}

	/** Get Bestellte Menge in Preiseinheit.
		@return Bestellte Menge in Preiseinheit
	  */
	@Override
	public java.math.BigDecimal getQtyEnteredInPriceUOM () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyEnteredInPriceUOM);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Reihenfolge.
		@param SeqNo 
		Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst
	  */
	@Override
	public void setSeqNo (int SeqNo)
	{
		set_Value (COLUMNNAME_SeqNo, Integer.valueOf(SeqNo));
	}

	/** Get Reihenfolge.
		@return Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst
	  */
	@Override
	public int getSeqNo () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SeqNo);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}
