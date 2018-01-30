/** Generated Model - DO NOT CHANGE */
package de.metas.vertical.pharma.vendor.gateway.mvs3.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for MSV3_Verfuegbarkeit_Transaction
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_MSV3_Verfuegbarkeit_Transaction extends org.compiere.model.PO implements I_MSV3_Verfuegbarkeit_Transaction, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -653770024L;

    /** Standard Constructor */
    public X_MSV3_Verfuegbarkeit_Transaction (Properties ctx, int MSV3_Verfuegbarkeit_Transaction_ID, String trxName)
    {
      super (ctx, MSV3_Verfuegbarkeit_Transaction_ID, trxName);
      /** if (MSV3_Verfuegbarkeit_Transaction_ID == 0)
        {
			setMSV3_Verfuegbarkeit_Transaction_ID (0);
        } */
    }

    /** Load Constructor */
    public X_MSV3_Verfuegbarkeit_Transaction (Properties ctx, ResultSet rs, String trxName)
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
	public de.metas.vertical.pharma.vendor.gateway.mvs3.model.I_MSV3_FaultInfo getMSV3_FaultInfo() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_MSV3_FaultInfo_ID, de.metas.vertical.pharma.vendor.gateway.mvs3.model.I_MSV3_FaultInfo.class);
	}

	@Override
	public void setMSV3_FaultInfo(de.metas.vertical.pharma.vendor.gateway.mvs3.model.I_MSV3_FaultInfo MSV3_FaultInfo)
	{
		set_ValueFromPO(COLUMNNAME_MSV3_FaultInfo_ID, de.metas.vertical.pharma.vendor.gateway.mvs3.model.I_MSV3_FaultInfo.class, MSV3_FaultInfo);
	}

	/** Set MSV3_FaultInfo.
		@param MSV3_FaultInfo_ID MSV3_FaultInfo	  */
	@Override
	public void setMSV3_FaultInfo_ID (int MSV3_FaultInfo_ID)
	{
		if (MSV3_FaultInfo_ID < 1) 
			set_Value (COLUMNNAME_MSV3_FaultInfo_ID, null);
		else 
			set_Value (COLUMNNAME_MSV3_FaultInfo_ID, Integer.valueOf(MSV3_FaultInfo_ID));
	}

	/** Get MSV3_FaultInfo.
		@return MSV3_FaultInfo	  */
	@Override
	public int getMSV3_FaultInfo_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_MSV3_FaultInfo_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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
			set_Value (COLUMNNAME_MSV3_VerfuegbarkeitsanfrageEinzelneAntwort_ID, null);
		else 
			set_Value (COLUMNNAME_MSV3_VerfuegbarkeitsanfrageEinzelneAntwort_ID, Integer.valueOf(MSV3_VerfuegbarkeitsanfrageEinzelneAntwort_ID));
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
			set_Value (COLUMNNAME_MSV3_VerfuegbarkeitsanfrageEinzelne_ID, null);
		else 
			set_Value (COLUMNNAME_MSV3_VerfuegbarkeitsanfrageEinzelne_ID, Integer.valueOf(MSV3_VerfuegbarkeitsanfrageEinzelne_ID));
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

	/** Set MSV3_Verfuegbarkeit_Transaction.
		@param MSV3_Verfuegbarkeit_Transaction_ID MSV3_Verfuegbarkeit_Transaction	  */
	@Override
	public void setMSV3_Verfuegbarkeit_Transaction_ID (int MSV3_Verfuegbarkeit_Transaction_ID)
	{
		if (MSV3_Verfuegbarkeit_Transaction_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_MSV3_Verfuegbarkeit_Transaction_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_MSV3_Verfuegbarkeit_Transaction_ID, Integer.valueOf(MSV3_Verfuegbarkeit_Transaction_ID));
	}

	/** Get MSV3_Verfuegbarkeit_Transaction.
		@return MSV3_Verfuegbarkeit_Transaction	  */
	@Override
	public int getMSV3_Verfuegbarkeit_Transaction_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_MSV3_Verfuegbarkeit_Transaction_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}