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
	private static final long serialVersionUID = 1843690221L;

    /** Standard Constructor */
    public X_EDI_DesadvLine (Properties ctx, int EDI_DesadvLine_ID, String trxName)
    {
      super (ctx, EDI_DesadvLine_ID, trxName);
      /** if (EDI_DesadvLine_ID == 0)
        {
			setC_UOM_ID (0);
			setEDI_Desadv_ID (0);
			setEDI_DesadvLine_ID (0);
			setIsSubsequentDeliveryPlanned (false);
			setM_Product_ID (0);
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

	/** Set EDI_DesadvLine.
		@param EDI_DesadvLine_ID EDI_DesadvLine	  */
	@Override
	public void setEDI_DesadvLine_ID (int EDI_DesadvLine_ID)
	{
		if (EDI_DesadvLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_EDI_DesadvLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_EDI_DesadvLine_ID, Integer.valueOf(EDI_DesadvLine_ID));
	}

	/** Get EDI_DesadvLine.
		@return EDI_DesadvLine	  */
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

	/** Set Bewegungs-Menge.
		@param MovementQty 
		Menge eines bewegten Produktes.
	  */
	@Override
	public void setMovementQty (java.math.BigDecimal MovementQty)
	{
		set_Value (COLUMNNAME_MovementQty, MovementQty);
	}

	/** Get Bewegungs-Menge.
		@return Menge eines bewegten Produktes.
	  */
	@Override
	public java.math.BigDecimal getMovementQty () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_MovementQty);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
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