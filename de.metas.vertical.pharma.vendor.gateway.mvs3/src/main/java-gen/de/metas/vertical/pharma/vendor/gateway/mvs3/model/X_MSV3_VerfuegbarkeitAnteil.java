/** Generated Model - DO NOT CHANGE */
package de.metas.vertical.pharma.vendor.gateway.mvs3.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for MSV3_VerfuegbarkeitAnteil
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_MSV3_VerfuegbarkeitAnteil extends org.compiere.model.PO implements I_MSV3_VerfuegbarkeitAnteil, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1888553787L;

    /** Standard Constructor */
    public X_MSV3_VerfuegbarkeitAnteil (Properties ctx, int MSV3_VerfuegbarkeitAnteil_ID, String trxName)
    {
      super (ctx, MSV3_VerfuegbarkeitAnteil_ID, trxName);
      /** if (MSV3_VerfuegbarkeitAnteil_ID == 0)
        {
			setMSV3_Grund (null);
			setMSV3_Typ (null);
			setMSV3_VerfuegbarkeitAnteil_ID (0);
			setMSV3_VerfuegbarkeitsantwortArtikel_ID (0);
        } */
    }

    /** Load Constructor */
    public X_MSV3_VerfuegbarkeitAnteil (Properties ctx, ResultSet rs, String trxName)
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
	 * MSV3_Grund AD_Reference_ID=540819
	 * Reference name: MSV3_VerfuegbarkeitDefektgrund
	 */
	public static final int MSV3_GRUND_AD_Reference_ID=540819;
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
	/** Teildefekt = Teildefekt */
	public static final String MSV3_GRUND_Teildefekt = "Teildefekt";
	/** Transportausschluss = Transportausschluss */
	public static final String MSV3_GRUND_Transportausschluss = "Transportausschluss";
	/** Set Grund.
		@param MSV3_Grund Grund	  */
	@Override
	public void setMSV3_Grund (java.lang.String MSV3_Grund)
	{

		set_ValueNoCheck (COLUMNNAME_MSV3_Grund, MSV3_Grund);
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
	 * MSV3_Typ AD_Reference_ID=540820
	 * Reference name: MSV3_VerfuegbarkeitRueckmeldungTyp
	 */
	public static final int MSV3_TYP_AD_Reference_ID=540820;
	/** Normal = Normal */
	public static final String MSV3_TYP_Normal = "Normal";
	/** Verbund = Verbund */
	public static final String MSV3_TYP_Verbund = "Verbund";
	/** Nachlieferung = Nachlieferung */
	public static final String MSV3_TYP_Nachlieferung = "Nachlieferung";
	/** Dispo = Dispo */
	public static final String MSV3_TYP_Dispo = "Dispo";
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

	/** Set MSV3_VerfuegbarkeitAnteil.
		@param MSV3_VerfuegbarkeitAnteil_ID MSV3_VerfuegbarkeitAnteil	  */
	@Override
	public void setMSV3_VerfuegbarkeitAnteil_ID (int MSV3_VerfuegbarkeitAnteil_ID)
	{
		if (MSV3_VerfuegbarkeitAnteil_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_MSV3_VerfuegbarkeitAnteil_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_MSV3_VerfuegbarkeitAnteil_ID, Integer.valueOf(MSV3_VerfuegbarkeitAnteil_ID));
	}

	/** Get MSV3_VerfuegbarkeitAnteil.
		@return MSV3_VerfuegbarkeitAnteil	  */
	@Override
	public int getMSV3_VerfuegbarkeitAnteil_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_MSV3_VerfuegbarkeitAnteil_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.vertical.pharma.vendor.gateway.mvs3.model.I_MSV3_VerfuegbarkeitsantwortArtikel getMSV3_VerfuegbarkeitsantwortArtikel() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_MSV3_VerfuegbarkeitsantwortArtikel_ID, de.metas.vertical.pharma.vendor.gateway.mvs3.model.I_MSV3_VerfuegbarkeitsantwortArtikel.class);
	}

	@Override
	public void setMSV3_VerfuegbarkeitsantwortArtikel(de.metas.vertical.pharma.vendor.gateway.mvs3.model.I_MSV3_VerfuegbarkeitsantwortArtikel MSV3_VerfuegbarkeitsantwortArtikel)
	{
		set_ValueFromPO(COLUMNNAME_MSV3_VerfuegbarkeitsantwortArtikel_ID, de.metas.vertical.pharma.vendor.gateway.mvs3.model.I_MSV3_VerfuegbarkeitsantwortArtikel.class, MSV3_VerfuegbarkeitsantwortArtikel);
	}

	/** Set VerfuegbarkeitsantwortArtikel.
		@param MSV3_VerfuegbarkeitsantwortArtikel_ID VerfuegbarkeitsantwortArtikel	  */
	@Override
	public void setMSV3_VerfuegbarkeitsantwortArtikel_ID (int MSV3_VerfuegbarkeitsantwortArtikel_ID)
	{
		if (MSV3_VerfuegbarkeitsantwortArtikel_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_MSV3_VerfuegbarkeitsantwortArtikel_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_MSV3_VerfuegbarkeitsantwortArtikel_ID, Integer.valueOf(MSV3_VerfuegbarkeitsantwortArtikel_ID));
	}

	/** Get VerfuegbarkeitsantwortArtikel.
		@return VerfuegbarkeitsantwortArtikel	  */
	@Override
	public int getMSV3_VerfuegbarkeitsantwortArtikel_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_MSV3_VerfuegbarkeitsantwortArtikel_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}