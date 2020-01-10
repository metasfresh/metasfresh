/** Generated Model - DO NOT CHANGE */
package de.metas.esb.edi.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for EDI_DesadvLine
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_EDI_DesadvLine extends org.compiere.model.PO implements I_EDI_DesadvLine, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -301018082L;

    /** Standard Constructor */
    public X_EDI_DesadvLine (Properties ctx, int EDI_DesadvLine_ID, String trxName)
    {
      super (ctx, EDI_DesadvLine_ID, trxName);
      /** if (EDI_DesadvLine_ID == 0)
        {
			setC_UOM_ID (0);
			setEDI_Desadv_ID (0);
			setEDI_DesadvLine_ID (0);
			setInvoicableQtyBasedOn (null);
			setIsSubsequentDeliveryPlanned (false);
			setM_Product_ID (0);
			setQtyOrdered (BigDecimal.ZERO);
        } */
    }

    /** Load Constructor */
    public X_EDI_DesadvLine (Properties ctx, ResultSet rs, String trxName)
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

	/** Set CU-EAN.
		@param EAN_CU CU-EAN	  */
	@Override
	public void setEAN_CU (java.lang.String EAN_CU)
	{
		set_Value (COLUMNNAME_EAN_CU, EAN_CU);
	}

	/** Get CU-EAN.
		@return CU-EAN	  */
	@Override
	public java.lang.String getEAN_CU () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_EAN_CU);
	}

	/** Set TU-EAN.
		@param EAN_TU TU-EAN	  */
	@Override
	public void setEAN_TU (java.lang.String EAN_TU)
	{
		set_Value (COLUMNNAME_EAN_TU, EAN_TU);
	}

	/** Get TU-EAN.
		@return TU-EAN	  */
	@Override
	public java.lang.String getEAN_TU () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_EAN_TU);
	}

	@Override
	public de.metas.esb.edi.model.I_EDI_Desadv getEDI_Desadv()
	{
		return get_ValueAsPO(COLUMNNAME_EDI_Desadv_ID, de.metas.esb.edi.model.I_EDI_Desadv.class);
	}

	@Override
	public void setEDI_Desadv(de.metas.esb.edi.model.I_EDI_Desadv EDI_Desadv)
	{
		set_ValueFromPO(COLUMNNAME_EDI_Desadv_ID, de.metas.esb.edi.model.I_EDI_Desadv.class, EDI_Desadv);
	}

	/** Set DESADV.
		@param EDI_Desadv_ID DESADV	  */
	@Override
	public void setEDI_Desadv_ID (int EDI_Desadv_ID)
	{
		if (EDI_Desadv_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_EDI_Desadv_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_EDI_Desadv_ID, Integer.valueOf(EDI_Desadv_ID));
	}

	/** Get DESADV.
		@return DESADV	  */
	@Override
	public int getEDI_Desadv_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_EDI_Desadv_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set DESADV-Position.
		@param EDI_DesadvLine_ID DESADV-Position	  */
	@Override
	public void setEDI_DesadvLine_ID (int EDI_DesadvLine_ID)
	{
		if (EDI_DesadvLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_EDI_DesadvLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_EDI_DesadvLine_ID, Integer.valueOf(EDI_DesadvLine_ID));
	}

	/** Get DESADV-Position.
		@return DESADV-Position	  */
	@Override
	public int getEDI_DesadvLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_EDI_DesadvLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set GTIN.
		@param GTIN GTIN	  */
	@Override
	public void setGTIN (java.lang.String GTIN)
	{
		set_Value (COLUMNNAME_GTIN, GTIN);
	}

	/** Get GTIN.
		@return GTIN	  */
	@Override
	public java.lang.String getGTIN () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_GTIN);
	}

	/** 
	 * InvoicableQtyBasedOn AD_Reference_ID=541023
	 * Reference name: InvoicableQtyBasedOn
	 */
	public static final int INVOICABLEQTYBASEDON_AD_Reference_ID=541023;
	/** Nominal = Nominal */
	public static final String INVOICABLEQTYBASEDON_Nominal = "Nominal";
	/** CatchWeight = CatchWeight */
	public static final String INVOICABLEQTYBASEDON_CatchWeight = "CatchWeight";
	/** Set Abr. Menge basiert auf.
		@param InvoicableQtyBasedOn 
		Legt fest wie die abrechenbare Menge ermittelt wird, wenn die tatsächlich gelieferte Menge von der mominal gelieferten Menge abweicht.
	  */
	@Override
	public void setInvoicableQtyBasedOn (java.lang.String InvoicableQtyBasedOn)
	{

		set_Value (COLUMNNAME_InvoicableQtyBasedOn, InvoicableQtyBasedOn);
	}

	/** Get Abr. Menge basiert auf.
		@return Legt fest wie die abrechenbare Menge ermittelt wird, wenn die tatsächlich gelieferte Menge von der mominal gelieferten Menge abweicht.
	  */
	@Override
	public java.lang.String getInvoicableQtyBasedOn () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_InvoicableQtyBasedOn);
	}

	/** Set Spätere Nachlieferung.
		@param IsSubsequentDeliveryPlanned 
		Falls "ja", wird das Feld "Abweichungscode" in der DESADV-Datei auf "BP" (back order to follow) gesetzt, d.h. es wird signalisiert, das später noch eine Nachliefrung erfolgen wird.
	  */
	@Override
	public void setIsSubsequentDeliveryPlanned (boolean IsSubsequentDeliveryPlanned)
	{
		set_Value (COLUMNNAME_IsSubsequentDeliveryPlanned, Boolean.valueOf(IsSubsequentDeliveryPlanned));
	}

	/** Get Spätere Nachlieferung.
		@return Falls "ja", wird das Feld "Abweichungscode" in der DESADV-Datei auf "BP" (back order to follow) gesetzt, d.h. es wird signalisiert, das später noch eine Nachliefrung erfolgen wird.
	  */
	@Override
	public boolean isSubsequentDeliveryPlanned () 
	{
		Object oo = get_Value(COLUMNNAME_IsSubsequentDeliveryPlanned);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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

	/** Set Produktnummer.
		@param ProductNo Produktnummer	  */
	@Override
	public void setProductNo (java.lang.String ProductNo)
	{
		set_Value (COLUMNNAME_ProductNo, ProductNo);
	}

	/** Get Produktnummer.
		@return Produktnummer	  */
	@Override
	public java.lang.String getProductNo () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ProductNo);
	}

	/** Set Geliefert (Lagereinheit).
		@param QtyDeliveredInStockingUOM Geliefert (Lagereinheit)	  */
	@Override
	public void setQtyDeliveredInStockingUOM (java.math.BigDecimal QtyDeliveredInStockingUOM)
	{
		set_Value (COLUMNNAME_QtyDeliveredInStockingUOM, QtyDeliveredInStockingUOM);
	}

	/** Get Geliefert (Lagereinheit).
		@return Geliefert (Lagereinheit)	  */
	@Override
	public java.math.BigDecimal getQtyDeliveredInStockingUOM () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyDeliveredInStockingUOM);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Liefermenge.
		@param QtyDeliveredInUOM 
		Liefermenge in der Maßeinheit der jeweiligen Zeile (kann von der Maßeinheit des betreffenden Produktes abweichen)
	  */
	@Override
	public void setQtyDeliveredInUOM (java.math.BigDecimal QtyDeliveredInUOM)
	{
		set_Value (COLUMNNAME_QtyDeliveredInUOM, QtyDeliveredInUOM);
	}

	/** Get Liefermenge.
		@return Liefermenge in der Maßeinheit der jeweiligen Zeile (kann von der Maßeinheit des betreffenden Produktes abweichen)
	  */
	@Override
	public java.math.BigDecimal getQtyDeliveredInUOM () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyDeliveredInUOM);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Menge.
		@param QtyEntered 
		Die Eingegebene Menge basiert auf der gewählten Mengeneinheit
	  */
	@Override
	public void setQtyEntered (java.math.BigDecimal QtyEntered)
	{
		set_Value (COLUMNNAME_QtyEntered, QtyEntered);
	}

	/** Get Menge.
		@return Die Eingegebene Menge basiert auf der gewählten Mengeneinheit
	  */
	@Override
	public java.math.BigDecimal getQtyEntered () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyEntered);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Bestellt/ Beauftragt.
		@param QtyOrdered 
		Bestellt/ Beauftragt
	  */
	@Override
	public void setQtyOrdered (java.math.BigDecimal QtyOrdered)
	{
		set_Value (COLUMNNAME_QtyOrdered, QtyOrdered);
	}

	/** Get Bestellt/ Beauftragt.
		@return Bestellt/ Beauftragt
	  */
	@Override
	public java.math.BigDecimal getQtyOrdered () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyOrdered);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set CU-UPC.
		@param UPC_CU CU-UPC	  */
	@Override
	public void setUPC_CU (java.lang.String UPC_CU)
	{
		set_Value (COLUMNNAME_UPC_CU, UPC_CU);
	}

	/** Get CU-UPC.
		@return CU-UPC	  */
	@Override
	public java.lang.String getUPC_CU () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_UPC_CU);
	}

	/** Set TU-UPC.
		@param UPC_TU TU-UPC	  */
	@Override
	public void setUPC_TU (java.lang.String UPC_TU)
	{
		set_Value (COLUMNNAME_UPC_TU, UPC_TU);
	}

	/** Get TU-UPC.
		@return TU-UPC	  */
	@Override
	public java.lang.String getUPC_TU () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_UPC_TU);
	}
}