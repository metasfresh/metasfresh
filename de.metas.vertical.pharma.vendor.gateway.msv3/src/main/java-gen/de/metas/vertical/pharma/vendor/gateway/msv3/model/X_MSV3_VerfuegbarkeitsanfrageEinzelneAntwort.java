/** Generated Model - DO NOT CHANGE */
package de.metas.vertical.pharma.vendor.gateway.msv3.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for MSV3_VerfuegbarkeitsanfrageEinzelneAntwort
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_MSV3_VerfuegbarkeitsanfrageEinzelneAntwort extends org.compiere.model.PO implements I_MSV3_VerfuegbarkeitsanfrageEinzelneAntwort, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -1853011009L;

    /** Standard Constructor */
    public X_MSV3_VerfuegbarkeitsanfrageEinzelneAntwort (Properties ctx, int MSV3_VerfuegbarkeitsanfrageEinzelneAntwort_ID, String trxName)
    {
      super (ctx, MSV3_VerfuegbarkeitsanfrageEinzelneAntwort_ID, trxName);
      /** if (MSV3_VerfuegbarkeitsanfrageEinzelneAntwort_ID == 0)
        {
			setMSV3_Id (null);
			setMSV3_VerfuegbarkeitsanfrageEinzelneAntwort_ID (0);
			setMSV3_VerfuegbarkeitTyp (null);
        } */
    }

    /** Load Constructor */
    public X_MSV3_VerfuegbarkeitsanfrageEinzelneAntwort (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Id.
		@param MSV3_Id Id	  */
	@Override
	public void setMSV3_Id (java.lang.String MSV3_Id)
	{
		set_Value (COLUMNNAME_MSV3_Id, MSV3_Id);
	}

	/** Get Id.
		@return Id	  */
	@Override
	public java.lang.String getMSV3_Id () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_MSV3_Id);
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

	/** 
	 * MSV3_VerfuegbarkeitTyp AD_Reference_ID=540817
	 * Reference name: MSV3_VerfuegbarkeitTyp
	 */
	public static final int MSV3_VERFUEGBARKEITTYP_AD_Reference_ID=540817;
	/** Spezifisch = Spezifisch */
	public static final String MSV3_VERFUEGBARKEITTYP_Spezifisch = "Spezifisch";
	/** Unspezifisch = Unspezifisch */
	public static final String MSV3_VERFUEGBARKEITTYP_Unspezifisch = "Unspezifisch";
	/** Set VerfuegbarkeitTyp.
		@param MSV3_VerfuegbarkeitTyp VerfuegbarkeitTyp	  */
	@Override
	public void setMSV3_VerfuegbarkeitTyp (java.lang.String MSV3_VerfuegbarkeitTyp)
	{

		set_Value (COLUMNNAME_MSV3_VerfuegbarkeitTyp, MSV3_VerfuegbarkeitTyp);
	}

	/** Get VerfuegbarkeitTyp.
		@return VerfuegbarkeitTyp	  */
	@Override
	public java.lang.String getMSV3_VerfuegbarkeitTyp () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_MSV3_VerfuegbarkeitTyp);
	}
}