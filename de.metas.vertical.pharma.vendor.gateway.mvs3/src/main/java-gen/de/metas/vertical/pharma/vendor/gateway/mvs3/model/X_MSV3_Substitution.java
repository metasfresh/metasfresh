/** Generated Model - DO NOT CHANGE */
package de.metas.vertical.pharma.vendor.gateway.mvs3.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for MSV3_Substitution
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_MSV3_Substitution extends org.compiere.model.PO implements I_MSV3_Substitution, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -1610705684L;

    /** Standard Constructor */
    public X_MSV3_Substitution (Properties ctx, int MSV3_Substitution_ID, String trxName)
    {
      super (ctx, MSV3_Substitution_ID, trxName);
      /** if (MSV3_Substitution_ID == 0)
        {
			setMSV3_Grund (null);
			setMSV3_Substitution_ID (0);
			setMSV3_Substitutionsgrund (null);
        } */
    }

    /** Load Constructor */
    public X_MSV3_Substitution (Properties ctx, ResultSet rs, String trxName)
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

		set_Value (COLUMNNAME_MSV3_Grund, MSV3_Grund);
	}

	/** Get Grund.
		@return Grund	  */
	@Override
	public java.lang.String getMSV3_Grund () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_MSV3_Grund);
	}

	/** Set MSV3_LieferPzn.
		@param MSV3_LieferPzn MSV3_LieferPzn	  */
	@Override
	public void setMSV3_LieferPzn (java.lang.String MSV3_LieferPzn)
	{
		set_Value (COLUMNNAME_MSV3_LieferPzn, MSV3_LieferPzn);
	}

	/** Get MSV3_LieferPzn.
		@return MSV3_LieferPzn	  */
	@Override
	public java.lang.String getMSV3_LieferPzn () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_MSV3_LieferPzn);
	}

	/** Set Substitution.
		@param MSV3_Substitution_ID Substitution	  */
	@Override
	public void setMSV3_Substitution_ID (int MSV3_Substitution_ID)
	{
		if (MSV3_Substitution_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_MSV3_Substitution_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_MSV3_Substitution_ID, Integer.valueOf(MSV3_Substitution_ID));
	}

	/** Get Substitution.
		@return Substitution	  */
	@Override
	public int getMSV3_Substitution_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_MSV3_Substitution_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** 
	 * MSV3_Substitutionsgrund AD_Reference_ID=540818
	 * Reference name: MSV3_Substitutionsgrund
	 */
	public static final int MSV3_SUBSTITUTIONSGRUND_AD_Reference_ID=540818;
	/** Nachfolgeprodukt = Nachfolgeprodukt */
	public static final String MSV3_SUBSTITUTIONSGRUND_Nachfolgeprodukt = "Nachfolgeprodukt";
	/** ReUndParallelImport = ReUndParallelImport */
	public static final String MSV3_SUBSTITUTIONSGRUND_ReUndParallelImport = "ReUndParallelImport";
	/** Vorschlag = Vorschlag */
	public static final String MSV3_SUBSTITUTIONSGRUND_Vorschlag = "Vorschlag";
	/** Set Substitutionsgrund.
		@param MSV3_Substitutionsgrund Substitutionsgrund	  */
	@Override
	public void setMSV3_Substitutionsgrund (java.lang.String MSV3_Substitutionsgrund)
	{

		set_Value (COLUMNNAME_MSV3_Substitutionsgrund, MSV3_Substitutionsgrund);
	}

	/** Get Substitutionsgrund.
		@return Substitutionsgrund	  */
	@Override
	public java.lang.String getMSV3_Substitutionsgrund () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_MSV3_Substitutionsgrund);
	}
}