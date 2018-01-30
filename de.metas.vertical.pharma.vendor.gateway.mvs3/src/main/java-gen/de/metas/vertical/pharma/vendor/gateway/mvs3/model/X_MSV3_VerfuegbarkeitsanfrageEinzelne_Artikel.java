/** Generated Model - DO NOT CHANGE */
package de.metas.vertical.pharma.vendor.gateway.mvs3.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for MSV3_VerfuegbarkeitsanfrageEinzelne_Artikel
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_MSV3_VerfuegbarkeitsanfrageEinzelne_Artikel extends org.compiere.model.PO implements I_MSV3_VerfuegbarkeitsanfrageEinzelne_Artikel, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1294244950L;

    /** Standard Constructor */
    public X_MSV3_VerfuegbarkeitsanfrageEinzelne_Artikel (Properties ctx, int MSV3_VerfuegbarkeitsanfrageEinzelne_Artikel_ID, String trxName)
    {
      super (ctx, MSV3_VerfuegbarkeitsanfrageEinzelne_Artikel_ID, trxName);
      /** if (MSV3_VerfuegbarkeitsanfrageEinzelne_Artikel_ID == 0)
        {
			setMSV3_VerfuegbarkeitsanfrageEinzelne_Artikel_ID (0);
			setMSV3_VerfuegbarkeitsanfrageEinzelne_ID (0);
        } */
    }

    /** Load Constructor */
    public X_MSV3_VerfuegbarkeitsanfrageEinzelne_Artikel (Properties ctx, ResultSet rs, String trxName)
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
	 * MSV3_Bedarf AD_Reference_ID=540826
	 * Reference name: MSV3_Bedarf
	 */
	public static final int MSV3_BEDARF_AD_Reference_ID=540826;
	/** direkt = direkt */
	public static final String MSV3_BEDARF_Direkt = "direkt";
	/** einsAusN = einsAusN */
	public static final String MSV3_BEDARF_EinsAusN = "einsAusN";
	/** unspezifisch = unspezifisch */
	public static final String MSV3_BEDARF_Unspezifisch = "unspezifisch";
	/** Set Bedarf.
		@param MSV3_Bedarf Bedarf	  */
	@Override
	public void setMSV3_Bedarf (java.lang.String MSV3_Bedarf)
	{

		set_Value (COLUMNNAME_MSV3_Bedarf, MSV3_Bedarf);
	}

	/** Get Bedarf.
		@return Bedarf	  */
	@Override
	public java.lang.String getMSV3_Bedarf () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_MSV3_Bedarf);
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

	/** Set MSV3_Pzn.
		@param MSV3_Pzn MSV3_Pzn	  */
	@Override
	public void setMSV3_Pzn (java.lang.String MSV3_Pzn)
	{
		set_Value (COLUMNNAME_MSV3_Pzn, MSV3_Pzn);
	}

	/** Get MSV3_Pzn.
		@return MSV3_Pzn	  */
	@Override
	public java.lang.String getMSV3_Pzn () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_MSV3_Pzn);
	}

	/** Set MSV3_VerfuegbarkeitsanfrageEinzelne_Artikel.
		@param MSV3_VerfuegbarkeitsanfrageEinzelne_Artikel_ID MSV3_VerfuegbarkeitsanfrageEinzelne_Artikel	  */
	@Override
	public void setMSV3_VerfuegbarkeitsanfrageEinzelne_Artikel_ID (int MSV3_VerfuegbarkeitsanfrageEinzelne_Artikel_ID)
	{
		if (MSV3_VerfuegbarkeitsanfrageEinzelne_Artikel_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_MSV3_VerfuegbarkeitsanfrageEinzelne_Artikel_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_MSV3_VerfuegbarkeitsanfrageEinzelne_Artikel_ID, Integer.valueOf(MSV3_VerfuegbarkeitsanfrageEinzelne_Artikel_ID));
	}

	/** Get MSV3_VerfuegbarkeitsanfrageEinzelne_Artikel.
		@return MSV3_VerfuegbarkeitsanfrageEinzelne_Artikel	  */
	@Override
	public int getMSV3_VerfuegbarkeitsanfrageEinzelne_Artikel_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_MSV3_VerfuegbarkeitsanfrageEinzelne_Artikel_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.vertical.pharma.vendor.gateway.mvs3.model.I_MSV3_VerfuegbarkeitsanfrageEinzelne getMSV3_VerfuegbarkeitsanfrageEinzelne() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_MSV3_VerfuegbarkeitsanfrageEinzelne_ID, de.metas.vertical.pharma.vendor.gateway.mvs3.model.I_MSV3_VerfuegbarkeitsanfrageEinzelne.class);
	}

	@Override
	public void setMSV3_VerfuegbarkeitsanfrageEinzelne(de.metas.vertical.pharma.vendor.gateway.mvs3.model.I_MSV3_VerfuegbarkeitsanfrageEinzelne MSV3_VerfuegbarkeitsanfrageEinzelne)
	{
		set_ValueFromPO(COLUMNNAME_MSV3_VerfuegbarkeitsanfrageEinzelne_ID, de.metas.vertical.pharma.vendor.gateway.mvs3.model.I_MSV3_VerfuegbarkeitsanfrageEinzelne.class, MSV3_VerfuegbarkeitsanfrageEinzelne);
	}

	/** Set MSV3_VerfuegbarkeitsanfrageEinzelne.
		@param MSV3_VerfuegbarkeitsanfrageEinzelne_ID MSV3_VerfuegbarkeitsanfrageEinzelne	  */
	@Override
	public void setMSV3_VerfuegbarkeitsanfrageEinzelne_ID (int MSV3_VerfuegbarkeitsanfrageEinzelne_ID)
	{
		if (MSV3_VerfuegbarkeitsanfrageEinzelne_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_MSV3_VerfuegbarkeitsanfrageEinzelne_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_MSV3_VerfuegbarkeitsanfrageEinzelne_ID, Integer.valueOf(MSV3_VerfuegbarkeitsanfrageEinzelne_ID));
	}

	/** Get MSV3_VerfuegbarkeitsanfrageEinzelne.
		@return MSV3_VerfuegbarkeitsanfrageEinzelne	  */
	@Override
	public int getMSV3_VerfuegbarkeitsanfrageEinzelne_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_MSV3_VerfuegbarkeitsanfrageEinzelne_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}