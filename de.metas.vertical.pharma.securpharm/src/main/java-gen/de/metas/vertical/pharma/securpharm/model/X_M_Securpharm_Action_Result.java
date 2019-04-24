/** Generated Model - DO NOT CHANGE */
package de.metas.vertical.pharma.securpharm.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for M_Securpharm_Action_Result
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_M_Securpharm_Action_Result extends org.compiere.model.PO implements I_M_Securpharm_Action_Result, org.compiere.model.I_Persistent 
{

	/**
	 * Action AD_Reference_ID=110
	 * Reference name: AD_User
	 */
	public static final int ACTION_AD_Reference_ID=110;
	/**
	 *
	 */
	private static final long serialVersionUID = -480055222L;

    /** Standard Constructor */
    public X_M_Securpharm_Action_Result (Properties ctx, int M_Securpharm_Action_Result_ID, String trxName)
    {
      super (ctx, M_Securpharm_Action_Result_ID, trxName);
      /** if (M_Securpharm_Action_Result_ID == 0)
        {
			setAction (null);
			setIsError (false); // N
			setM_Securpharm_Action_Result_ID (0);
			setM_Securpharm_Productdata_Result_ID (0);
			setRequestEndTime (new Timestamp( System.currentTimeMillis() ));
			setRequestStartTime (new Timestamp( System.currentTimeMillis() ));
			setRequestUrl (null);
			setTransactionIDClient (null);
        } */
    }


    /** Load Constructor */
    public X_M_Securpharm_Action_Result (Properties ctx, ResultSet rs, String trxName)
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

	/** Get Aktion.
		@return Aktion	  */
	@Override
	public java.lang.String getAction ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_Action);
	}

	/** Set Aktion.
		@param Action Aktion	  */
	@Override
	public void setAction (java.lang.String Action)
	{

		set_ValueNoCheck (COLUMNNAME_Action, Action);
	}

	/** Set Fehler.
		@param IsError 
		Ein Fehler ist bei der Durchführung aufgetreten
	  */
	@Override
	public void setIsError (boolean IsError)
	{
		set_ValueNoCheck (COLUMNNAME_IsError, Boolean.valueOf(IsError));
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

	@Override
	public org.compiere.model.I_M_Inventory getM_Inventory() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_Inventory_ID, org.compiere.model.I_M_Inventory.class);
	}

	@Override
	public void setM_Inventory(org.compiere.model.I_M_Inventory M_Inventory)
	{
		set_ValueFromPO(COLUMNNAME_M_Inventory_ID, org.compiere.model.I_M_Inventory.class, M_Inventory);
	}

	/** Get Inventur.
		@return Parameter für eine physische Inventur
	  */
	@Override
	public int getM_Inventory_ID ()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Inventory_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Inventur.
		@param M_Inventory_ID
		Parameter für eine physische Inventur
	  */
	@Override
	public void setM_Inventory_ID (int M_Inventory_ID)
	{
		if (M_Inventory_ID < 1)
			set_Value (COLUMNNAME_M_Inventory_ID, null);
		else
			set_Value (COLUMNNAME_M_Inventory_ID, Integer.valueOf(M_Inventory_ID));
	}

	/** Get SecurPharm action result.
		@return SecurPharm action result	  */
	@Override
	public int getM_Securpharm_Action_Result_ID ()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Securpharm_Action_Result_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set SecurPharm action result.
		@param M_Securpharm_Action_Result_ID SecurPharm action result	  */
	@Override
	public void setM_Securpharm_Action_Result_ID (int M_Securpharm_Action_Result_ID)
	{
		if (M_Securpharm_Action_Result_ID < 1)
			set_ValueNoCheck (COLUMNNAME_M_Securpharm_Action_Result_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_M_Securpharm_Action_Result_ID, Integer.valueOf(M_Securpharm_Action_Result_ID));
	}

	@Override
	public de.metas.vertical.pharma.securpharm.model.I_M_Securpharm_Productdata_Result getM_Securpharm_Productdata_Result() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_Securpharm_Productdata_Result_ID, de.metas.vertical.pharma.securpharm.model.I_M_Securpharm_Productdata_Result.class);
	}

	@Override
	public void setM_Securpharm_Productdata_Result(de.metas.vertical.pharma.securpharm.model.I_M_Securpharm_Productdata_Result M_Securpharm_Productdata_Result)
	{
		set_ValueFromPO(COLUMNNAME_M_Securpharm_Productdata_Result_ID, de.metas.vertical.pharma.securpharm.model.I_M_Securpharm_Productdata_Result.class, M_Securpharm_Productdata_Result);
	}

	/** Get SecurPharm product data result.
		@return SecurPharm product data result	  */
	@Override
	public int getM_Securpharm_Productdata_Result_ID ()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Securpharm_Productdata_Result_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set SecurPharm product data result.
		@param M_Securpharm_Productdata_Result_ID SecurPharm product data result	  */
	@Override
	public void setM_Securpharm_Productdata_Result_ID (int M_Securpharm_Productdata_Result_ID)
	{
		if (M_Securpharm_Productdata_Result_ID < 1)
			set_Value (COLUMNNAME_M_Securpharm_Productdata_Result_ID, null);
		else
			set_Value (COLUMNNAME_M_Securpharm_Productdata_Result_ID, Integer.valueOf(M_Securpharm_Productdata_Result_ID));
	}

	/** Get Anfrage Ende.
		@return Anfrage Ende	  */
	@Override
	public java.sql.Timestamp getRequestEndTime ()
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_RequestEndTime);
	}

	/** Set Anfrage Ende.
		@param RequestEndTime Anfrage Ende	  */
	@Override
	public void setRequestEndTime (java.sql.Timestamp RequestEndTime)
	{
		set_ValueNoCheck (COLUMNNAME_RequestEndTime, RequestEndTime);
	}

	/** Get Anfrage Start .
		@return Anfrage Start 	  */
	@Override
	public java.sql.Timestamp getRequestStartTime ()
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_RequestStartTime);
	}

	/** Set Anfrage Start .
		@param RequestStartTime Anfrage Start 	  */
	@Override
	public void setRequestStartTime (java.sql.Timestamp RequestStartTime)
	{
		set_ValueNoCheck (COLUMNNAME_RequestStartTime, RequestStartTime);
	}

	/** Get Abfrage.
		@return Abfrage	  */
	@Override
	public java.lang.String getRequestUrl ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_RequestUrl);
	}

	/** Set Abfrage.
		@param RequestUrl Abfrage	  */
	@Override
	public void setRequestUrl (java.lang.String RequestUrl)
	{
		set_ValueNoCheck (COLUMNNAME_RequestUrl, RequestUrl);
	}

	/** Get TransaktionsID Client.
		@return TransaktionsID Client	  */
	@Override
	public java.lang.String getTransactionIDClient ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_TransactionIDClient);
	}

	/** Set TransaktionsID Client.
		@param TransactionIDClient TransaktionsID Client	  */
	@Override
	public void setTransactionIDClient (java.lang.String TransactionIDClient)
	{
		set_ValueNoCheck (COLUMNNAME_TransactionIDClient, TransactionIDClient);
	}

	/** Get TransaktionsID Server.
		@return TransaktionsID Server	  */
	@Override
	public java.lang.String getTransactionIDServer ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_TransactionIDServer);
	}

	/** Set TransaktionsID Server.
		@param TransactionIDServer TransaktionsID Server	  */
	@Override
	public void setTransactionIDServer (java.lang.String TransactionIDServer)
	{
		set_ValueNoCheck (COLUMNNAME_TransactionIDServer, TransactionIDServer);
	}
}
