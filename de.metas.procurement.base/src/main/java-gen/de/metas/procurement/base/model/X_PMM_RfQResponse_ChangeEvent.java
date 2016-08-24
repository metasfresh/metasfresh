/** Generated Model - DO NOT CHANGE */
package de.metas.procurement.base.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.util.Env;

/** Generated Model for PMM_RfQResponse_ChangeEvent
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_PMM_RfQResponse_ChangeEvent extends org.compiere.model.PO implements I_PMM_RfQResponse_ChangeEvent, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 2119708342L;

    /** Standard Constructor */
    public X_PMM_RfQResponse_ChangeEvent (Properties ctx, int PMM_RfQResponse_ChangeEvent_ID, String trxName)
    {
      super (ctx, PMM_RfQResponse_ChangeEvent_ID, trxName);
      /** if (PMM_RfQResponse_ChangeEvent_ID == 0)
        {
			setIsError (false);
// N
			setPMM_RfQResponse_ChangeEvent_ID (0);
			setProcessed (false);
// N
			setType (null);
        } */
    }

    /** Load Constructor */
    public X_PMM_RfQResponse_ChangeEvent (Properties ctx, ResultSet rs, String trxName)
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
	public de.metas.rfq.model.I_C_RfQResponseLine getC_RfQResponseLine() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_RfQResponseLine_ID, de.metas.rfq.model.I_C_RfQResponseLine.class);
	}

	@Override
	public void setC_RfQResponseLine(de.metas.rfq.model.I_C_RfQResponseLine C_RfQResponseLine)
	{
		set_ValueFromPO(COLUMNNAME_C_RfQResponseLine_ID, de.metas.rfq.model.I_C_RfQResponseLine.class, C_RfQResponseLine);
	}

	/** Set RfQ Response Line.
		@param C_RfQResponseLine_ID 
		Request for Quotation Response Line
	  */
	@Override
	public void setC_RfQResponseLine_ID (int C_RfQResponseLine_ID)
	{
		if (C_RfQResponseLine_ID < 1) 
			set_Value (COLUMNNAME_C_RfQResponseLine_ID, null);
		else 
			set_Value (COLUMNNAME_C_RfQResponseLine_ID, Integer.valueOf(C_RfQResponseLine_ID));
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

	/** Set RfQ Response Line (UUID).
		@param C_RfQResponseLine_UUID 
		Request for Quotation Response Line (UUID)
	  */
	@Override
	public void setC_RfQResponseLine_UUID (java.lang.String C_RfQResponseLine_UUID)
	{
		set_Value (COLUMNNAME_C_RfQResponseLine_UUID, C_RfQResponseLine_UUID);
	}

	/** Get RfQ Response Line (UUID).
		@return Request for Quotation Response Line (UUID)
	  */
	@Override
	public java.lang.String getC_RfQResponseLine_UUID () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_C_RfQResponseLine_UUID);
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
			set_Value (COLUMNNAME_C_RfQResponseLineQty_ID, null);
		else 
			set_Value (COLUMNNAME_C_RfQResponseLineQty_ID, Integer.valueOf(C_RfQResponseLineQty_ID));
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

	/** Set Zugesagter Termin.
		@param DatePromised 
		Zugesagter Termin für diesen Auftrag
	  */
	@Override
	public void setDatePromised (java.sql.Timestamp DatePromised)
	{
		set_Value (COLUMNNAME_DatePromised, DatePromised);
	}

	/** Get Zugesagter Termin.
		@return Zugesagter Termin für diesen Auftrag
	  */
	@Override
	public java.sql.Timestamp getDatePromised () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_DatePromised);
	}

	/** Set Fehlermeldung.
		@param ErrorMsg Fehlermeldung	  */
	@Override
	public void setErrorMsg (java.lang.String ErrorMsg)
	{
		set_Value (COLUMNNAME_ErrorMsg, ErrorMsg);
	}

	/** Get Fehlermeldung.
		@return Fehlermeldung	  */
	@Override
	public java.lang.String getErrorMsg () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ErrorMsg);
	}

	/** Set Event UUID.
		@param Event_UUID Event UUID	  */
	@Override
	public void setEvent_UUID (java.lang.String Event_UUID)
	{
		set_Value (COLUMNNAME_Event_UUID, Event_UUID);
	}

	/** Get Event UUID.
		@return Event UUID	  */
	@Override
	public java.lang.String getEvent_UUID () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Event_UUID);
	}

	/** Set Fehler.
		@param IsError 
		Ein Fehler ist bei der Durchführung aufgetreten
	  */
	@Override
	public void setIsError (boolean IsError)
	{
		set_Value (COLUMNNAME_IsError, Boolean.valueOf(IsError));
	}

	/** Get Fehler.
		@return Ein Fehler ist bei der Durchführung aufgetreten
	  */
	@Override
	public boolean isError () 
	{
		Object oo = get_Value(COLUMNNAME_IsError);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	@Override
	public de.metas.procurement.base.model.I_PMM_Product getPMM_Product() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_PMM_Product_ID, de.metas.procurement.base.model.I_PMM_Product.class);
	}

	@Override
	public void setPMM_Product(de.metas.procurement.base.model.I_PMM_Product PMM_Product)
	{
		set_ValueFromPO(COLUMNNAME_PMM_Product_ID, de.metas.procurement.base.model.I_PMM_Product.class, PMM_Product);
	}

	/** Set Lieferprodukt.
		@param PMM_Product_ID Lieferprodukt	  */
	@Override
	public void setPMM_Product_ID (int PMM_Product_ID)
	{
		if (PMM_Product_ID < 1) 
			set_Value (COLUMNNAME_PMM_Product_ID, null);
		else 
			set_Value (COLUMNNAME_PMM_Product_ID, Integer.valueOf(PMM_Product_ID));
	}

	/** Get Lieferprodukt.
		@return Lieferprodukt	  */
	@Override
	public int getPMM_Product_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PMM_Product_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set RfQ Response Change Event.
		@param PMM_RfQResponse_ChangeEvent_ID RfQ Response Change Event	  */
	@Override
	public void setPMM_RfQResponse_ChangeEvent_ID (int PMM_RfQResponse_ChangeEvent_ID)
	{
		if (PMM_RfQResponse_ChangeEvent_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_PMM_RfQResponse_ChangeEvent_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_PMM_RfQResponse_ChangeEvent_ID, Integer.valueOf(PMM_RfQResponse_ChangeEvent_ID));
	}

	/** Get RfQ Response Change Event.
		@return RfQ Response Change Event	  */
	@Override
	public int getPMM_RfQResponse_ChangeEvent_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PMM_RfQResponse_ChangeEvent_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Preis.
		@param Price 
		Preis
	  */
	@Override
	public void setPrice (java.math.BigDecimal Price)
	{
		set_Value (COLUMNNAME_Price, Price);
	}

	/** Get Preis.
		@return Preis
	  */
	@Override
	public java.math.BigDecimal getPrice () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Price);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Preis (old).
		@param Price_Old Preis (old)	  */
	@Override
	public void setPrice_Old (java.math.BigDecimal Price_Old)
	{
		set_Value (COLUMNNAME_Price_Old, Price_Old);
	}

	/** Get Preis (old).
		@return Preis (old)	  */
	@Override
	public java.math.BigDecimal getPrice_Old () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Price_Old);
		if (bd == null)
			 return Env.ZERO;
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

	/** Set Produkt UUID.
		@param Product_UUID Produkt UUID	  */
	@Override
	public void setProduct_UUID (java.lang.String Product_UUID)
	{
		set_Value (COLUMNNAME_Product_UUID, Product_UUID);
	}

	/** Get Produkt UUID.
		@return Produkt UUID	  */
	@Override
	public java.lang.String getProduct_UUID () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Product_UUID);
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
			 return Env.ZERO;
		return bd;
	}

	/** Set Menge (old).
		@param Qty_Old Menge (old)	  */
	@Override
	public void setQty_Old (java.math.BigDecimal Qty_Old)
	{
		set_Value (COLUMNNAME_Qty_Old, Qty_Old);
	}

	/** Get Menge (old).
		@return Menge (old)	  */
	@Override
	public java.math.BigDecimal getQty_Old () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Qty_Old);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** 
	 * Type AD_Reference_ID=540660
	 * Reference name: PMM_RfQResponse_ChangeEvent_Type
	 */
	public static final int TYPE_AD_Reference_ID=540660;
	/** Price = P */
	public static final String TYPE_Price = "P";
	/** Quantity = Q */
	public static final String TYPE_Quantity = "Q";
	/** Set Art.
		@param Type 
		Type of Validation (SQL, Java Script, Java Language)
	  */
	@Override
	public void setType (java.lang.String Type)
	{

		set_Value (COLUMNNAME_Type, Type);
	}

	/** Get Art.
		@return Type of Validation (SQL, Java Script, Java Language)
	  */
	@Override
	public java.lang.String getType () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Type);
	}
}