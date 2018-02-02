/** Generated Model - DO NOT CHANGE */
package de.metas.vertical.pharma.vendor.gateway.mvs3.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for MSV3_Tour
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_MSV3_Tour extends org.compiere.model.PO implements I_MSV3_Tour, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -13567147L;

    /** Standard Constructor */
    public X_MSV3_Tour (Properties ctx, int MSV3_Tour_ID, String trxName)
    {
      super (ctx, MSV3_Tour_ID, trxName);
      /** if (MSV3_Tour_ID == 0)
        {
			setMSV3_Tour (null);
			setMSV3_Tour_ID (0);
        } */
    }

    /** Load Constructor */
    public X_MSV3_Tour (Properties ctx, ResultSet rs, String trxName)
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
	public de.metas.vertical.pharma.vendor.gateway.mvs3.model.I_MSV3_BestellungAnteil getMSV3_BestellungAnteil() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_MSV3_BestellungAnteil_ID, de.metas.vertical.pharma.vendor.gateway.mvs3.model.I_MSV3_BestellungAnteil.class);
	}

	@Override
	public void setMSV3_BestellungAnteil(de.metas.vertical.pharma.vendor.gateway.mvs3.model.I_MSV3_BestellungAnteil MSV3_BestellungAnteil)
	{
		set_ValueFromPO(COLUMNNAME_MSV3_BestellungAnteil_ID, de.metas.vertical.pharma.vendor.gateway.mvs3.model.I_MSV3_BestellungAnteil.class, MSV3_BestellungAnteil);
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

	/** Set Tour.
		@param MSV3_Tour Tour	  */
	@Override
	public void setMSV3_Tour (java.lang.String MSV3_Tour)
	{
		set_Value (COLUMNNAME_MSV3_Tour, MSV3_Tour);
	}

	/** Get Tour.
		@return Tour	  */
	@Override
	public java.lang.String getMSV3_Tour () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_MSV3_Tour);
	}

	/** Set MSV3_Tour.
		@param MSV3_Tour_ID MSV3_Tour	  */
	@Override
	public void setMSV3_Tour_ID (int MSV3_Tour_ID)
	{
		if (MSV3_Tour_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_MSV3_Tour_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_MSV3_Tour_ID, Integer.valueOf(MSV3_Tour_ID));
	}

	/** Get MSV3_Tour.
		@return MSV3_Tour	  */
	@Override
	public int getMSV3_Tour_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_MSV3_Tour_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.vertical.pharma.vendor.gateway.mvs3.model.I_MSV3_VerfuegbarkeitAnteil getMSV3_VerfuegbarkeitAnteil() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_MSV3_VerfuegbarkeitAnteil_ID, de.metas.vertical.pharma.vendor.gateway.mvs3.model.I_MSV3_VerfuegbarkeitAnteil.class);
	}

	@Override
	public void setMSV3_VerfuegbarkeitAnteil(de.metas.vertical.pharma.vendor.gateway.mvs3.model.I_MSV3_VerfuegbarkeitAnteil MSV3_VerfuegbarkeitAnteil)
	{
		set_ValueFromPO(COLUMNNAME_MSV3_VerfuegbarkeitAnteil_ID, de.metas.vertical.pharma.vendor.gateway.mvs3.model.I_MSV3_VerfuegbarkeitAnteil.class, MSV3_VerfuegbarkeitAnteil);
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
}