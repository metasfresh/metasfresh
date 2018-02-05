/** Generated Model - DO NOT CHANGE */
package de.metas.vertical.pharma.vendor.gateway.mvs3.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for MSV3_VerfuegbarkeitsantwortArtikel
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_MSV3_VerfuegbarkeitsantwortArtikel extends org.compiere.model.PO implements I_MSV3_VerfuegbarkeitsantwortArtikel, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1121230409L;

    /** Standard Constructor */
    public X_MSV3_VerfuegbarkeitsantwortArtikel (Properties ctx, int MSV3_VerfuegbarkeitsantwortArtikel_ID, String trxName)
    {
      super (ctx, MSV3_VerfuegbarkeitsantwortArtikel_ID, trxName);
      /** if (MSV3_VerfuegbarkeitsantwortArtikel_ID == 0)
        {
			setMSV3_VerfuegbarkeitsanfrageEinzelneAntwort_ID (0);
			setMSV3_VerfuegbarkeitsantwortArtikel_ID (0);
        } */
    }

    /** Load Constructor */
    public X_MSV3_VerfuegbarkeitsantwortArtikel (Properties ctx, ResultSet rs, String trxName)
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
	public org.compiere.model.I_C_OrderLine getC_OrderLineSO() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_OrderLineSO_ID, org.compiere.model.I_C_OrderLine.class);
	}

	@Override
	public void setC_OrderLineSO(org.compiere.model.I_C_OrderLine C_OrderLineSO)
	{
		set_ValueFromPO(COLUMNNAME_C_OrderLineSO_ID, org.compiere.model.I_C_OrderLine.class, C_OrderLineSO);
	}

	/** Set Auftragsposition.
		@param C_OrderLineSO_ID 
		Auftragsposition
	  */
	@Override
	public void setC_OrderLineSO_ID (int C_OrderLineSO_ID)
	{
		if (C_OrderLineSO_ID < 1) 
			set_Value (COLUMNNAME_C_OrderLineSO_ID, null);
		else 
			set_Value (COLUMNNAME_C_OrderLineSO_ID, Integer.valueOf(C_OrderLineSO_ID));
	}

	/** Get Auftragsposition.
		@return Auftragsposition
	  */
	@Override
	public int getC_OrderLineSO_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_OrderLineSO_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Purchase candidate.
		@param C_PurchaseCandidate_ID Purchase candidate	  */
	@Override
	public void setC_PurchaseCandidate_ID (int C_PurchaseCandidate_ID)
	{
		if (C_PurchaseCandidate_ID < 1) 
			set_Value (COLUMNNAME_C_PurchaseCandidate_ID, null);
		else 
			set_Value (COLUMNNAME_C_PurchaseCandidate_ID, Integer.valueOf(C_PurchaseCandidate_ID));
	}

	/** Get Purchase candidate.
		@return Purchase candidate	  */
	@Override
	public int getC_PurchaseCandidate_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_PurchaseCandidate_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set AnfrageMenge.
		@param MSV3_AnfrageMenge AnfrageMenge	  */
	@Override
	public void setMSV3_AnfrageMenge (int MSV3_AnfrageMenge)
	{
		set_Value (COLUMNNAME_MSV3_AnfrageMenge, Integer.valueOf(MSV3_AnfrageMenge));
	}

	/** Get AnfrageMenge.
		@return AnfrageMenge	  */
	@Override
	public int getMSV3_AnfrageMenge () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_MSV3_AnfrageMenge);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set AnfragePzn.
		@param MSV3_AnfragePzn AnfragePzn	  */
	@Override
	public void setMSV3_AnfragePzn (java.lang.String MSV3_AnfragePzn)
	{
		set_Value (COLUMNNAME_MSV3_AnfragePzn, MSV3_AnfragePzn);
	}

	/** Get AnfragePzn.
		@return AnfragePzn	  */
	@Override
	public java.lang.String getMSV3_AnfragePzn () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_MSV3_AnfragePzn);
	}

	@Override
	public de.metas.vertical.pharma.vendor.gateway.mvs3.model.I_MSV3_VerfuegbarkeitsanfrageEinzelneAntwort getMSV3_VerfuegbarkeitsanfrageEinzelneAntwort() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_MSV3_VerfuegbarkeitsanfrageEinzelneAntwort_ID, de.metas.vertical.pharma.vendor.gateway.mvs3.model.I_MSV3_VerfuegbarkeitsanfrageEinzelneAntwort.class);
	}

	@Override
	public void setMSV3_VerfuegbarkeitsanfrageEinzelneAntwort(de.metas.vertical.pharma.vendor.gateway.mvs3.model.I_MSV3_VerfuegbarkeitsanfrageEinzelneAntwort MSV3_VerfuegbarkeitsanfrageEinzelneAntwort)
	{
		set_ValueFromPO(COLUMNNAME_MSV3_VerfuegbarkeitsanfrageEinzelneAntwort_ID, de.metas.vertical.pharma.vendor.gateway.mvs3.model.I_MSV3_VerfuegbarkeitsanfrageEinzelneAntwort.class, MSV3_VerfuegbarkeitsanfrageEinzelneAntwort);
	}

	/** Set MSV3_VerfuegbarkeitsanfrageEinzelneAntwort.
		@param MSV3_VerfuegbarkeitsanfrageEinzelneAntwort_ID MSV3_VerfuegbarkeitsanfrageEinzelneAntwort	  */
	@Override
	public void setMSV3_VerfuegbarkeitsanfrageEinzelneAntwort_ID (int MSV3_VerfuegbarkeitsanfrageEinzelneAntwort_ID)
	{
		if (MSV3_VerfuegbarkeitsanfrageEinzelneAntwort_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_MSV3_VerfuegbarkeitsanfrageEinzelneAntwort_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_MSV3_VerfuegbarkeitsanfrageEinzelneAntwort_ID, Integer.valueOf(MSV3_VerfuegbarkeitsanfrageEinzelneAntwort_ID));
	}

	/** Get MSV3_VerfuegbarkeitsanfrageEinzelneAntwort.
		@return MSV3_VerfuegbarkeitsanfrageEinzelneAntwort	  */
	@Override
	public int getMSV3_VerfuegbarkeitsanfrageEinzelneAntwort_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_MSV3_VerfuegbarkeitsanfrageEinzelneAntwort_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	@Override
	public de.metas.vertical.pharma.vendor.gateway.mvs3.model.I_MSV3_Substitution getMSV3_VerfuegbarkeitSubstitution() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_MSV3_VerfuegbarkeitSubstitution_ID, de.metas.vertical.pharma.vendor.gateway.mvs3.model.I_MSV3_Substitution.class);
	}

	@Override
	public void setMSV3_VerfuegbarkeitSubstitution(de.metas.vertical.pharma.vendor.gateway.mvs3.model.I_MSV3_Substitution MSV3_VerfuegbarkeitSubstitution)
	{
		set_ValueFromPO(COLUMNNAME_MSV3_VerfuegbarkeitSubstitution_ID, de.metas.vertical.pharma.vendor.gateway.mvs3.model.I_MSV3_Substitution.class, MSV3_VerfuegbarkeitSubstitution);
	}

	/** Set VerfuegbarkeitSubstitution.
		@param MSV3_VerfuegbarkeitSubstitution_ID VerfuegbarkeitSubstitution	  */
	@Override
	public void setMSV3_VerfuegbarkeitSubstitution_ID (int MSV3_VerfuegbarkeitSubstitution_ID)
	{
		if (MSV3_VerfuegbarkeitSubstitution_ID < 1) 
			set_Value (COLUMNNAME_MSV3_VerfuegbarkeitSubstitution_ID, null);
		else 
			set_Value (COLUMNNAME_MSV3_VerfuegbarkeitSubstitution_ID, Integer.valueOf(MSV3_VerfuegbarkeitSubstitution_ID));
	}

	/** Get VerfuegbarkeitSubstitution.
		@return VerfuegbarkeitSubstitution	  */
	@Override
	public int getMSV3_VerfuegbarkeitSubstitution_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_MSV3_VerfuegbarkeitSubstitution_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}