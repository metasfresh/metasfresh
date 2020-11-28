/** Generated Model - DO NOT CHANGE */
package de.metas.shipper.gateway.dpd.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for DPD_StoreOrder_Log
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_DPD_StoreOrder_Log extends org.compiere.model.PO implements I_DPD_StoreOrder_Log, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -866472408L;

    /** Standard Constructor */
    public X_DPD_StoreOrder_Log (Properties ctx, int DPD_StoreOrder_Log_ID, String trxName)
    {
      super (ctx, DPD_StoreOrder_Log_ID, trxName);
      /** if (DPD_StoreOrder_Log_ID == 0)
        {
			setDPD_StoreOrder_Log_ID (0);
			setDurationMillis (0); // 0
			setIsError (false); // N
        } */
    }

    /** Load Constructor */
    public X_DPD_StoreOrder_Log (Properties ctx, ResultSet rs, String trxName)
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
	public org.compiere.model.I_AD_Issue getAD_Issue()
	{
		return get_ValueAsPO(COLUMNNAME_AD_Issue_ID, org.compiere.model.I_AD_Issue.class);
	}

	@Override
	public void setAD_Issue(org.compiere.model.I_AD_Issue AD_Issue)
	{
		set_ValueFromPO(COLUMNNAME_AD_Issue_ID, org.compiere.model.I_AD_Issue.class, AD_Issue);
	}

	/** Set System-Problem.
		@param AD_Issue_ID 
		Automatically created or manually entered System Issue
	  */
	@Override
	public void setAD_Issue_ID (int AD_Issue_ID)
	{
		if (AD_Issue_ID < 1) 
			set_Value (COLUMNNAME_AD_Issue_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Issue_ID, Integer.valueOf(AD_Issue_ID));
	}

	/** Get System-Problem.
		@return Automatically created or manually entered System Issue
	  */
	@Override
	public int getAD_Issue_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Issue_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Config Summary.
		@param ConfigSummary Config Summary	  */
	@Override
	public void setConfigSummary (java.lang.String ConfigSummary)
	{
		set_Value (COLUMNNAME_ConfigSummary, ConfigSummary);
	}

	/** Get Config Summary.
		@return Config Summary	  */
	@Override
	public java.lang.String getConfigSummary () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ConfigSummary);
	}

	@Override
	public de.metas.shipper.gateway.dpd.model.I_DPD_StoreOrder getDPD_StoreOrder()
	{
		return get_ValueAsPO(COLUMNNAME_DPD_StoreOrder_ID, de.metas.shipper.gateway.dpd.model.I_DPD_StoreOrder.class);
	}

	@Override
	public void setDPD_StoreOrder(de.metas.shipper.gateway.dpd.model.I_DPD_StoreOrder DPD_StoreOrder)
	{
		set_ValueFromPO(COLUMNNAME_DPD_StoreOrder_ID, de.metas.shipper.gateway.dpd.model.I_DPD_StoreOrder.class, DPD_StoreOrder);
	}

	/** Set DPD StoreOrder.
		@param DPD_StoreOrder_ID DPD StoreOrder	  */
	@Override
	public void setDPD_StoreOrder_ID (int DPD_StoreOrder_ID)
	{
		if (DPD_StoreOrder_ID < 1) 
			set_Value (COLUMNNAME_DPD_StoreOrder_ID, null);
		else 
			set_Value (COLUMNNAME_DPD_StoreOrder_ID, Integer.valueOf(DPD_StoreOrder_ID));
	}

	/** Get DPD StoreOrder.
		@return DPD StoreOrder	  */
	@Override
	public int getDPD_StoreOrder_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DPD_StoreOrder_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Dpd StoreOrder Log.
		@param DPD_StoreOrder_Log_ID Dpd StoreOrder Log	  */
	@Override
	public void setDPD_StoreOrder_Log_ID (int DPD_StoreOrder_Log_ID)
	{
		if (DPD_StoreOrder_Log_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_DPD_StoreOrder_Log_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_DPD_StoreOrder_Log_ID, Integer.valueOf(DPD_StoreOrder_Log_ID));
	}

	/** Get Dpd StoreOrder Log.
		@return Dpd StoreOrder Log	  */
	@Override
	public int getDPD_StoreOrder_Log_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DPD_StoreOrder_Log_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Duration (ms).
		@param DurationMillis Duration (ms)	  */
	@Override
	public void setDurationMillis (int DurationMillis)
	{
		set_Value (COLUMNNAME_DurationMillis, Integer.valueOf(DurationMillis));
	}

	/** Get Duration (ms).
		@return Duration (ms)	  */
	@Override
	public int getDurationMillis () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DurationMillis);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Fehler.
		@param IsError 
		Ein Fehler ist bei der Durchführung aufgetreten
	  */
	@Override
	public void setIsError (boolean IsError)
	{
		set_Value (COLUMNNAME_IsError, Boolean.valueOf(IsError));
	}

	/** Get Fehler.
		@return Ein Fehler ist bei der Durchführung aufgetreten
	  */
	@Override
	public boolean isError () 
	{
		Object oo = get_Value(COLUMNNAME_IsError);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Request Message.
		@param RequestMessage Request Message	  */
	@Override
	public void setRequestMessage (java.lang.String RequestMessage)
	{
		set_Value (COLUMNNAME_RequestMessage, RequestMessage);
	}

	/** Get Request Message.
		@return Request Message	  */
	@Override
	public java.lang.String getRequestMessage () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_RequestMessage);
	}

	/** Set Response Message.
		@param ResponseMessage Response Message	  */
	@Override
	public void setResponseMessage (java.lang.String ResponseMessage)
	{
		set_Value (COLUMNNAME_ResponseMessage, ResponseMessage);
	}

	/** Get Response Message.
		@return Response Message	  */
	@Override
	public java.lang.String getResponseMessage () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ResponseMessage);
	}
}