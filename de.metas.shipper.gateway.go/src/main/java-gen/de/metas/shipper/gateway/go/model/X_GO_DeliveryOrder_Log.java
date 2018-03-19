/** Generated Model - DO NOT CHANGE */
package de.metas.shipper.gateway.go.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for GO_DeliveryOrder_Log
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_GO_DeliveryOrder_Log extends org.compiere.model.PO implements I_GO_DeliveryOrder_Log, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -802914886L;

    /** Standard Constructor */
    public X_GO_DeliveryOrder_Log (Properties ctx, int GO_DeliveryOrder_Log_ID, String trxName)
    {
      super (ctx, GO_DeliveryOrder_Log_ID, trxName);
      /** if (GO_DeliveryOrder_Log_ID == 0)
        {
			setGO_DeliveryOrder_Log_ID (0);
			setIsError (false); // N
        } */
    }

    /** Load Constructor */
    public X_GO_DeliveryOrder_Log (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Aktion.
		@param Action 
		Zeigt die durchzuführende Aktion an
	  */
	@Override
	public void setAction (java.lang.String Action)
	{
		set_Value (COLUMNNAME_Action, Action);
	}

	/** Get Aktion.
		@return Zeigt die durchzuführende Aktion an
	  */
	@Override
	public java.lang.String getAction () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Action);
	}

	@Override
	public org.compiere.model.I_AD_Issue getAD_Issue() throws RuntimeException
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

	/** Set Config Summary.
		@param GO_ConfigSummary Config Summary	  */
	@Override
	public void setGO_ConfigSummary (java.lang.String GO_ConfigSummary)
	{
		set_Value (COLUMNNAME_GO_ConfigSummary, GO_ConfigSummary);
	}

	/** Get Config Summary.
		@return Config Summary	  */
	@Override
	public java.lang.String getGO_ConfigSummary () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_GO_ConfigSummary);
	}

	@Override
	public de.metas.shipper.gateway.go.model.I_GO_DeliveryOrder getGO_DeliveryOrder() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_GO_DeliveryOrder_ID, de.metas.shipper.gateway.go.model.I_GO_DeliveryOrder.class);
	}

	@Override
	public void setGO_DeliveryOrder(de.metas.shipper.gateway.go.model.I_GO_DeliveryOrder GO_DeliveryOrder)
	{
		set_ValueFromPO(COLUMNNAME_GO_DeliveryOrder_ID, de.metas.shipper.gateway.go.model.I_GO_DeliveryOrder.class, GO_DeliveryOrder);
	}

	/** Set GO Delivery Order.
		@param GO_DeliveryOrder_ID GO Delivery Order	  */
	@Override
	public void setGO_DeliveryOrder_ID (int GO_DeliveryOrder_ID)
	{
		if (GO_DeliveryOrder_ID < 1) 
			set_Value (COLUMNNAME_GO_DeliveryOrder_ID, null);
		else 
			set_Value (COLUMNNAME_GO_DeliveryOrder_ID, Integer.valueOf(GO_DeliveryOrder_ID));
	}

	/** Get GO Delivery Order.
		@return GO Delivery Order	  */
	@Override
	public int getGO_DeliveryOrder_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_GO_DeliveryOrder_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set GO Delivery Order Log.
		@param GO_DeliveryOrder_Log_ID GO Delivery Order Log	  */
	@Override
	public void setGO_DeliveryOrder_Log_ID (int GO_DeliveryOrder_Log_ID)
	{
		if (GO_DeliveryOrder_Log_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_GO_DeliveryOrder_Log_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_GO_DeliveryOrder_Log_ID, Integer.valueOf(GO_DeliveryOrder_Log_ID));
	}

	/** Get GO Delivery Order Log.
		@return GO Delivery Order Log	  */
	@Override
	public int getGO_DeliveryOrder_Log_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_GO_DeliveryOrder_Log_ID);
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