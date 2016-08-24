/** Generated Model - DO NOT CHANGE */
package de.metas.rfq.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.util.Env;

/** Generated Model for C_RfQResponseLine
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_RfQResponseLine extends org.compiere.model.PO implements I_C_RfQResponseLine, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -1748113897L;

    /** Standard Constructor */
    public X_C_RfQResponseLine (Properties ctx, int C_RfQResponseLine_ID, String trxName)
    {
      super (ctx, C_RfQResponseLine_ID, trxName);
      /** if (C_RfQResponseLine_ID == 0)
        {
			setC_RfQLine_ID (0);
			setC_RfQResponse_ID (0);
			setC_RfQResponseLine_ID (0);
			setDocStatus (null);
			setIsSelectedWinner (false);
			setIsSelfService (false);
			setPrice (Env.ZERO);
// 0
			setProcessed (false);
// N
			setQtyPromised (Env.ZERO);
// 0
			setQtyRequiered (Env.ZERO);
// 0
			setUseLineQty (false);
// N
        } */
    }

    /** Load Constructor */
    public X_C_RfQResponseLine (Properties ctx, ResultSet rs, String trxName)
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
	public org.compiere.model.I_C_BPartner getC_BPartner() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_BPartner_ID, org.compiere.model.I_C_BPartner.class);
	}

	@Override
	public void setC_BPartner(org.compiere.model.I_C_BPartner C_BPartner)
	{
		set_ValueFromPO(COLUMNNAME_C_BPartner_ID, org.compiere.model.I_C_BPartner.class, C_BPartner);
	}

	/** Set Geschäftspartner.
		@param C_BPartner_ID 
		Bezeichnet einen Geschäftspartner
	  */
	@Override
	public void setC_BPartner_ID (int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
	}

	/** Get Geschäftspartner.
		@return Bezeichnet einen Geschäftspartner
	  */
	@Override
	public int getC_BPartner_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_ID);
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
			set_ValueNoCheck (COLUMNNAME_C_Currency_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Currency_ID, Integer.valueOf(C_Currency_ID));
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
	public de.metas.rfq.model.I_C_RfQ getC_RfQ() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_RfQ_ID, de.metas.rfq.model.I_C_RfQ.class);
	}

	@Override
	public void setC_RfQ(de.metas.rfq.model.I_C_RfQ C_RfQ)
	{
		set_ValueFromPO(COLUMNNAME_C_RfQ_ID, de.metas.rfq.model.I_C_RfQ.class, C_RfQ);
	}

	/** Set Ausschreibung.
		@param C_RfQ_ID 
		Anfrage für ein Angebot
	  */
	@Override
	public void setC_RfQ_ID (int C_RfQ_ID)
	{
		if (C_RfQ_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_RfQ_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_RfQ_ID, Integer.valueOf(C_RfQ_ID));
	}

	/** Get Ausschreibung.
		@return Anfrage für ein Angebot
	  */
	@Override
	public int getC_RfQ_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_RfQ_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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
			set_ValueNoCheck (COLUMNNAME_C_UOM_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_UOM_ID, Integer.valueOf(C_UOM_ID));
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

	/** Set Invited.
		@param DateInvited 
		Date when (last) invitation was sent
	  */
	@Override
	public void setDateInvited (java.sql.Timestamp DateInvited)
	{
		throw new IllegalArgumentException ("DateInvited is virtual column");	}

	/** Get Invited.
		@return Date when (last) invitation was sent
	  */
	@Override
	public java.sql.Timestamp getDateInvited () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_DateInvited);
	}

	/** Set Antwort-datum.
		@param DateResponse 
		Datum der Antwort
	  */
	@Override
	public void setDateResponse (java.sql.Timestamp DateResponse)
	{
		set_ValueNoCheck (COLUMNNAME_DateResponse, DateResponse);
	}

	/** Get Antwort-datum.
		@return Datum der Antwort
	  */
	@Override
	public java.sql.Timestamp getDateResponse () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_DateResponse);
	}

	/** Set Arbeit fertiggestellt.
		@param DateWorkComplete 
		Date when work is (planned to be) complete
	  */
	@Override
	public void setDateWorkComplete (java.sql.Timestamp DateWorkComplete)
	{
		set_Value (COLUMNNAME_DateWorkComplete, DateWorkComplete);
	}

	/** Get Arbeit fertiggestellt.
		@return Date when work is (planned to be) complete
	  */
	@Override
	public java.sql.Timestamp getDateWorkComplete () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_DateWorkComplete);
	}

	/** Set Arbeitsbeginn.
		@param DateWorkStart 
		Date when work is (planned to be) started
	  */
	@Override
	public void setDateWorkStart (java.sql.Timestamp DateWorkStart)
	{
		set_Value (COLUMNNAME_DateWorkStart, DateWorkStart);
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
		set_Value (COLUMNNAME_DeliveryDays, Integer.valueOf(DeliveryDays));
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
		set_Value (COLUMNNAME_Description, Description);
	}

	/** Get Beschreibung.
		@return Beschreibung	  */
	@Override
	public java.lang.String getDescription () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Description);
	}

	/** 
	 * DocStatus AD_Reference_ID=131
	 * Reference name: _Document Status
	 */
	public static final int DOCSTATUS_AD_Reference_ID=131;
	/** Drafted = DR */
	public static final String DOCSTATUS_Drafted = "DR";
	/** Completed = CO */
	public static final String DOCSTATUS_Completed = "CO";
	/** Approved = AP */
	public static final String DOCSTATUS_Approved = "AP";
	/** NotApproved = NA */
	public static final String DOCSTATUS_NotApproved = "NA";
	/** Voided = VO */
	public static final String DOCSTATUS_Voided = "VO";
	/** Invalid = IN */
	public static final String DOCSTATUS_Invalid = "IN";
	/** Reversed = RE */
	public static final String DOCSTATUS_Reversed = "RE";
	/** Closed = CL */
	public static final String DOCSTATUS_Closed = "CL";
	/** Unknown = ?? */
	public static final String DOCSTATUS_Unknown = "??";
	/** InProgress = IP */
	public static final String DOCSTATUS_InProgress = "IP";
	/** WaitingPayment = WP */
	public static final String DOCSTATUS_WaitingPayment = "WP";
	/** WaitingConfirmation = WC */
	public static final String DOCSTATUS_WaitingConfirmation = "WC";
	/** Set Belegstatus.
		@param DocStatus 
		The current status of the document
	  */
	@Override
	public void setDocStatus (java.lang.String DocStatus)
	{

		set_Value (COLUMNNAME_DocStatus, DocStatus);
	}

	/** Get Belegstatus.
		@return The current status of the document
	  */
	@Override
	public java.lang.String getDocStatus () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DocStatus);
	}

	/** Set Kommentar/Hilfe.
		@param Help 
		Comment or Hint
	  */
	@Override
	public void setHelp (java.lang.String Help)
	{
		set_Value (COLUMNNAME_Help, Help);
	}

	/** Get Kommentar/Hilfe.
		@return Comment or Hint
	  */
	@Override
	public java.lang.String getHelp () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Help);
	}

	/** Set Selected Winner.
		@param IsSelectedWinner 
		The resonse is the selected winner
	  */
	@Override
	public void setIsSelectedWinner (boolean IsSelectedWinner)
	{
		set_Value (COLUMNNAME_IsSelectedWinner, Boolean.valueOf(IsSelectedWinner));
	}

	/** Get Selected Winner.
		@return The resonse is the selected winner
	  */
	@Override
	public boolean isSelectedWinner () 
	{
		Object oo = get_Value(COLUMNNAME_IsSelectedWinner);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Selbstbedienung.
		@param IsSelfService 
		This is a Self-Service entry or this entry can be changed via Self-Service
	  */
	@Override
	public void setIsSelfService (boolean IsSelfService)
	{
		set_Value (COLUMNNAME_IsSelfService, Boolean.valueOf(IsSelfService));
	}

	/** Get Selbstbedienung.
		@return This is a Self-Service entry or this entry can be changed via Self-Service
	  */
	@Override
	public boolean isSelfService () 
	{
		Object oo = get_Value(COLUMNNAME_IsSelfService);
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

	/** Set Zusagbar.
		@param QtyPromised Zusagbar	  */
	@Override
	public void setQtyPromised (java.math.BigDecimal QtyPromised)
	{
		set_Value (COLUMNNAME_QtyPromised, QtyPromised);
	}

	/** Get Zusagbar.
		@return Zusagbar	  */
	@Override
	public java.math.BigDecimal getQtyPromised () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyPromised);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Qty Requiered.
		@param QtyRequiered Qty Requiered	  */
	@Override
	public void setQtyRequiered (java.math.BigDecimal QtyRequiered)
	{
		set_ValueNoCheck (COLUMNNAME_QtyRequiered, QtyRequiered);
	}

	/** Get Qty Requiered.
		@return Qty Requiered	  */
	@Override
	public java.math.BigDecimal getQtyRequiered () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyRequiered);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Use line quantity.
		@param UseLineQty Use line quantity	  */
	@Override
	public void setUseLineQty (boolean UseLineQty)
	{
		set_Value (COLUMNNAME_UseLineQty, Boolean.valueOf(UseLineQty));
	}

	/** Get Use line quantity.
		@return Use line quantity	  */
	@Override
	public boolean isUseLineQty () 
	{
		Object oo = get_Value(COLUMNNAME_UseLineQty);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}
}