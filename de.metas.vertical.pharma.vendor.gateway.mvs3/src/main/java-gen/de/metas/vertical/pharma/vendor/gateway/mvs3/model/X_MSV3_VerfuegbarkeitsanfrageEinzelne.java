/** Generated Model - DO NOT CHANGE */
package de.metas.vertical.pharma.vendor.gateway.mvs3.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for MSV3_VerfuegbarkeitsanfrageEinzelne
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_MSV3_VerfuegbarkeitsanfrageEinzelne extends org.compiere.model.PO implements I_MSV3_VerfuegbarkeitsanfrageEinzelne, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -999373672L;

    /** Standard Constructor */
    public X_MSV3_VerfuegbarkeitsanfrageEinzelne (Properties ctx, int MSV3_VerfuegbarkeitsanfrageEinzelne_ID, String trxName)
    {
      super (ctx, MSV3_VerfuegbarkeitsanfrageEinzelne_ID, trxName);
      /** if (MSV3_VerfuegbarkeitsanfrageEinzelne_ID == 0)
        {
			setMSV3_Id (null);
			setMSV3_VerfuegbarkeitsanfrageEinzelne_ID (0);
        } */
    }

    /** Load Constructor */
    public X_MSV3_VerfuegbarkeitsanfrageEinzelne (Properties ctx, ResultSet rs, String trxName)
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