/** Generated Model - DO NOT CHANGE */
package de.metas.vertical.pharma.vendor.gateway.msv3.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for MSV3_BestellungAnteil
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_MSV3_BestellungAnteil extends org.compiere.model.PO implements I_MSV3_BestellungAnteil, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -2101837388L;

    /** Standard Constructor */
    public X_MSV3_BestellungAnteil (Properties ctx, int MSV3_BestellungAnteil_ID, String trxName)
    {
      super (ctx, MSV3_BestellungAnteil_ID, trxName);
      /** if (MSV3_BestellungAnteil_ID == 0)
        {
			setMSV3_BestellungAnteil_ID (0);
			setMSV3_BestellungAntwortPosition_ID (0);
			setMSV3_Grund (null);
			setMSV3_Tourabweichung (false); // N
			setMSV3_Typ (null);
        } */
    }

    /** Load Constructor */
    public X_MSV3_BestellungAnteil (Properties ctx, ResultSet rs, String trxName)
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
	public org.compiere.model.I_C_OrderLine getC_OrderLinePO() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_OrderLinePO_ID, org.compiere.model.I_C_OrderLine.class);
	}

	@Override
	public void setC_OrderLinePO(org.compiere.model.I_C_OrderLine C_OrderLinePO)
	{
		set_ValueFromPO(COLUMNNAME_C_OrderLinePO_ID, org.compiere.model.I_C_OrderLine.class, C_OrderLinePO);
	}

	/** Set Bestellposition.
		@param C_OrderLinePO_ID Bestellposition	  */
	@Override
	public void setC_OrderLinePO_ID (int C_OrderLinePO_ID)
	{
		if (C_OrderLinePO_ID < 1) 
			set_Value (COLUMNNAME_C_OrderLinePO_ID, null);
		else 
			set_Value (COLUMNNAME_C_OrderLinePO_ID, Integer.valueOf(C_OrderLinePO_ID));
	}

	/** Get Bestellposition.
		@return Bestellposition	  */
	@Override
	public int getC_OrderLinePO_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_OrderLinePO_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set MSV3_BestellungAnteil.
		@param MSV3_BestellungAnteil_ID MSV3_BestellungAnteil	  */
	@Override
	public void setMSV3_BestellungAnteil_ID (int MSV3_BestellungAnteil_ID)
	{
		if (MSV3_BestellungAnteil_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_MSV3_BestellungAnteil_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_MSV3_BestellungAnteil_ID, Integer.valueOf(MSV3_BestellungAnteil_ID));
	}

	/** Get MSV3_BestellungAnteil.
		@return MSV3_BestellungAnteil	  */
	@Override
	public int getMSV3_BestellungAnteil_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_MSV3_BestellungAnteil_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.vertical.pharma.vendor.gateway.msv3.model.I_MSV3_BestellungAntwortPosition getMSV3_BestellungAntwortPosition() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_MSV3_BestellungAntwortPosition_ID, de.metas.vertical.pharma.vendor.gateway.msv3.model.I_MSV3_BestellungAntwortPosition.class);
	}

	@Override
	public void setMSV3_BestellungAntwortPosition(de.metas.vertical.pharma.vendor.gateway.msv3.model.I_MSV3_BestellungAntwortPosition MSV3_BestellungAntwortPosition)
	{
		set_ValueFromPO(COLUMNNAME_MSV3_BestellungAntwortPosition_ID, de.metas.vertical.pharma.vendor.gateway.msv3.model.I_MSV3_BestellungAntwortPosition.class, MSV3_BestellungAntwortPosition);
	}

	/** Set MSV3_BestellungAntwortPosition.
		@param MSV3_BestellungAntwortPosition_ID MSV3_BestellungAntwortPosition	  */
	@Override
	public void setMSV3_BestellungAntwortPosition_ID (int MSV3_BestellungAntwortPosition_ID)
	{
		if (MSV3_BestellungAntwortPosition_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_MSV3_BestellungAntwortPosition_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_MSV3_BestellungAntwortPosition_ID, Integer.valueOf(MSV3_BestellungAntwortPosition_ID));
	}

	/** Get MSV3_BestellungAntwortPosition.
		@return MSV3_BestellungAntwortPosition	  */
	@Override
	public int getMSV3_BestellungAntwortPosition_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_MSV3_BestellungAntwortPosition_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** 
	 * MSV3_Grund AD_Reference_ID=540824
	 * Reference name: MSV3_BestellungDefektgrund
	 */
	public static final int MSV3_GRUND_AD_Reference_ID=540824;
	/** KeineAngabe = KeineAngabe */
	public static final String MSV3_GRUND_KeineAngabe = "KeineAngabe";
	/** FehltZurzeit = FehltZurzeit */
	public static final String MSV3_GRUND_FehltZurzeit = "FehltZurzeit";
	/** HerstellerNichtLieferbar = HerstellerNichtLieferbar */
	public static final String MSV3_GRUND_HerstellerNichtLieferbar = "HerstellerNichtLieferbar";
	/** NurDirekt = NurDirekt */
	public static final String MSV3_GRUND_NurDirekt = "NurDirekt";
	/** NichtGefuehrt = NichtGefuehrt */
	public static final String MSV3_GRUND_NichtGefuehrt = "NichtGefuehrt";
	/** ArtikelNrUnbekannt = ArtikelNrUnbekannt */
	public static final String MSV3_GRUND_ArtikelNrUnbekannt = "ArtikelNrUnbekannt";
	/** AusserHandel = AusserHandel */
	public static final String MSV3_GRUND_AusserHandel = "AusserHandel";
	/** KeinBezug = KeinBezug */
	public static final String MSV3_GRUND_KeinBezug = "KeinBezug";
	/** Transportausschluss = Transportausschluss */
	public static final String MSV3_GRUND_Transportausschluss = "Transportausschluss";
	/** Set Grund.
		@param MSV3_Grund Grund	  */
	@Override
	public void setMSV3_Grund (java.lang.String MSV3_Grund)
	{

		set_Value (COLUMNNAME_MSV3_Grund, MSV3_Grund);
	}

	/** Get Grund.
		@return Grund	  */
	@Override
	public java.lang.String getMSV3_Grund () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_MSV3_Grund);
	}

	/** Set Lieferzeitpunkt.
		@param MSV3_Lieferzeitpunkt Lieferzeitpunkt	  */
	@Override
	public void setMSV3_Lieferzeitpunkt (java.sql.Timestamp MSV3_Lieferzeitpunkt)
	{
		set_Value (COLUMNNAME_MSV3_Lieferzeitpunkt, MSV3_Lieferzeitpunkt);
	}

	/** Get Lieferzeitpunkt.
		@return Lieferzeitpunkt	  */
	@Override
	public java.sql.Timestamp getMSV3_Lieferzeitpunkt () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_MSV3_Lieferzeitpunkt);
	}

	/** Set MSV3_Menge.
		@param MSV3_Menge MSV3_Menge	  */
	@Override
	public void setMSV3_Menge (int MSV3_Menge)
	{
		set_Value (COLUMNNAME_MSV3_Menge, Integer.valueOf(MSV3_Menge));
	}

	/** Get MSV3_Menge.
		@return MSV3_Menge	  */
	@Override
	public int getMSV3_Menge () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_MSV3_Menge);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Tourabweichung.
		@param MSV3_Tourabweichung Tourabweichung	  */
	@Override
	public void setMSV3_Tourabweichung (boolean MSV3_Tourabweichung)
	{
		set_Value (COLUMNNAME_MSV3_Tourabweichung, Boolean.valueOf(MSV3_Tourabweichung));
	}

	/** Get Tourabweichung.
		@return Tourabweichung	  */
	@Override
	public boolean isMSV3_Tourabweichung () 
	{
		Object oo = get_Value(COLUMNNAME_MSV3_Tourabweichung);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** 
	 * MSV3_Typ AD_Reference_ID=540823
	 * Reference name: MSV3_BestellungRueckmeldungTyp
	 */
	public static final int MSV3_TYP_AD_Reference_ID=540823;
	/** Normal = Normal */
	public static final String MSV3_TYP_Normal = "Normal";
	/** Verbund = Verbund */
	public static final String MSV3_TYP_Verbund = "Verbund";
	/** Nachlieferung = Nachlieferung */
	public static final String MSV3_TYP_Nachlieferung = "Nachlieferung";
	/** Dispo = Dispo */
	public static final String MSV3_TYP_Dispo = "Dispo";
	/** KeineLieferungAberNormalMoeglich = KeineLieferungAberNormalMoeglich */
	public static final String MSV3_TYP_KeineLieferungAberNormalMoeglich = "KeineLieferungAberNormalMoeglich";
	/** KeineLieferungAberVerbundMoeglich = KeineLieferungAberVerbundMoeglich */
	public static final String MSV3_TYP_KeineLieferungAberVerbundMoeglich = "KeineLieferungAberVerbundMoeglich";
	/** KeineLieferungAberNachlieferungMoeglich = KeineLieferungAberNachlieferungMoeglich */
	public static final String MSV3_TYP_KeineLieferungAberNachlieferungMoeglich = "KeineLieferungAberNachlieferungMoeglich";
	/** KeineLieferungAberDispoMoeglich = KeineLieferungAberDispoMoeglich */
	public static final String MSV3_TYP_KeineLieferungAberDispoMoeglich = "KeineLieferungAberDispoMoeglich";
	/** NichtLieferbar = NichtLieferbar */
	public static final String MSV3_TYP_NichtLieferbar = "NichtLieferbar";
	/** Set Typ.
		@param MSV3_Typ Typ	  */
	@Override
	public void setMSV3_Typ (java.lang.String MSV3_Typ)
	{

		set_Value (COLUMNNAME_MSV3_Typ, MSV3_Typ);
	}

	/** Get Typ.
		@return Typ	  */
	@Override
	public java.lang.String getMSV3_Typ () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_MSV3_Typ);
	}
}