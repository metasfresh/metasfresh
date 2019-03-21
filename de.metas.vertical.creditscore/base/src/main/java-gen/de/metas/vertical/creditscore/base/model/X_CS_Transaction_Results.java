/** Generated Model - DO NOT CHANGE */
package de.metas.vertical.creditscore.base.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for CS_Transaction_Results
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_CS_Transaction_Results extends org.compiere.model.PO implements I_CS_Transaction_Results, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1263214602L;

    /** Standard Constructor */
    public X_CS_Transaction_Results (Properties ctx, int CS_Transaction_Results_ID, String trxName)
    {
      super (ctx, CS_Transaction_Results_ID, trxName);
      /** if (CS_Transaction_Results_ID == 0)
        {
			setCS_Transaction_Results_ID (0);
			setRequestEndTime (new Timestamp( System.currentTimeMillis() ));
			setRequestStartTime (new Timestamp( System.currentTimeMillis() ));
			setResponseCode (BigDecimal.ZERO); // 0
			setResponseCodeText (null);
			setTransactionCustomerId (null); // 0
			setTransactionIdAPI (null);
        } */
    }

    /** Load Constructor */
    public X_CS_Transaction_Results (Properties ctx, ResultSet rs, String trxName)
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
	public org.compiere.model.I_C_BPartner getC_BPartner() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_BPartner_ID, org.compiere.model.I_C_BPartner.class);
	}

	@Override
	public void setC_BPartner(org.compiere.model.I_C_BPartner C_BPartner)
	{
		set_ValueFromPO(COLUMNNAME_C_BPartner_ID, org.compiere.model.I_C_BPartner.class, C_BPartner);
	}

	/** Set Geschäftspartner.
		@param C_BPartner_ID 
		Bezeichnet einen Geschäftspartner
	  */
	@Override
	public void setC_BPartner_ID (int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
	}

	/** Get Geschäftspartner.
		@return Bezeichnet einen Geschäftspartner
	  */
	@Override
	public int getC_BPartner_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_Order getC_Order() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Order_ID, org.compiere.model.I_C_Order.class);
	}

	@Override
	public void setC_Order(org.compiere.model.I_C_Order C_Order)
	{
		set_ValueFromPO(COLUMNNAME_C_Order_ID, org.compiere.model.I_C_Order.class, C_Order);
	}

	/** Set Auftrag.
		@param C_Order_ID 
		Auftrag
	  */
	@Override
	public void setC_Order_ID (int C_Order_ID)
	{
		if (C_Order_ID < 1) 
			set_Value (COLUMNNAME_C_Order_ID, null);
		else 
			set_Value (COLUMNNAME_C_Order_ID, Integer.valueOf(C_Order_ID));
	}

	/** Get Auftrag.
		@return Auftrag
	  */
	@Override
	public int getC_Order_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Order_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set CS_Transaction_Results.
		@param CS_Transaction_Results_ID CS_Transaction_Results	  */
	@Override
	public void setCS_Transaction_Results_ID (int CS_Transaction_Results_ID)
	{
		if (CS_Transaction_Results_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_CS_Transaction_Results_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_CS_Transaction_Results_ID, Integer.valueOf(CS_Transaction_Results_ID));
	}

	/** Get CS_Transaction_Results.
		@return CS_Transaction_Results	  */
	@Override
	public int getCS_Transaction_Results_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_CS_Transaction_Results_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Anfrage Ende.
		@param RequestEndTime Anfrage Ende	  */
	@Override
	public void setRequestEndTime (java.sql.Timestamp RequestEndTime)
	{
		set_ValueNoCheck (COLUMNNAME_RequestEndTime, RequestEndTime);
	}

	/** Get Anfrage Ende.
		@return Anfrage Ende	  */
	@Override
	public java.sql.Timestamp getRequestEndTime () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_RequestEndTime);
	}

	/** Set Anfrage Start .
		@param RequestStartTime Anfrage Start 	  */
	@Override
	public void setRequestStartTime (java.sql.Timestamp RequestStartTime)
	{
		set_ValueNoCheck (COLUMNNAME_RequestStartTime, RequestStartTime);
	}

	/** Get Anfrage Start .
		@return Anfrage Start 	  */
	@Override
	public java.sql.Timestamp getRequestStartTime () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_RequestStartTime);
	}

	/** Set Antwort Code.
		@param ResponseCode Antwort Code	  */
	@Override
	public void setResponseCode (java.math.BigDecimal ResponseCode)
	{
		set_ValueNoCheck (COLUMNNAME_ResponseCode, ResponseCode);
	}

	/** Get Antwort Code.
		@return Antwort Code	  */
	@Override
	public java.math.BigDecimal getResponseCode () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ResponseCode);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Antwort Text.
		@param ResponseCodeText Antwort Text	  */
	@Override
	public void setResponseCodeText (java.lang.String ResponseCodeText)
	{
		set_ValueNoCheck (COLUMNNAME_ResponseCodeText, ResponseCodeText);
	}

	/** Get Antwort Text.
		@return Antwort Text	  */
	@Override
	public java.lang.String getResponseCodeText () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ResponseCodeText);
	}

	/** Set Antwort Details.
		@param ResponseDetails Antwort Details	  */
	@Override
	public void setResponseDetails (java.lang.String ResponseDetails)
	{
		set_ValueNoCheck (COLUMNNAME_ResponseDetails, ResponseDetails);
	}

	/** Get Antwort Details.
		@return Antwort Details	  */
	@Override
	public java.lang.String getResponseDetails () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ResponseDetails);
	}

	/** 
	 * TransactionCustomerId AD_Reference_ID=110
	 * Reference name: AD_User
	 */
	public static final int TRANSACTIONCUSTOMERID_AD_Reference_ID=110;
	/** Set Transaktionsreferenz .
		@param TransactionCustomerId Transaktionsreferenz 	  */
	@Override
	public void setTransactionCustomerId (java.lang.String TransactionCustomerId)
	{

		set_ValueNoCheck (COLUMNNAME_TransactionCustomerId, TransactionCustomerId);
	}

	/** Get Transaktionsreferenz .
		@return Transaktionsreferenz 	  */
	@Override
	public java.lang.String getTransactionCustomerId () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_TransactionCustomerId);
	}

	/** Set Transaktionsreferenz .
		@param TransactionIdAPI Transaktionsreferenz 	  */
	@Override
	public void setTransactionIdAPI (java.lang.String TransactionIdAPI)
	{
		set_ValueNoCheck (COLUMNNAME_TransactionIdAPI, TransactionIdAPI);
	}

	/** Get Transaktionsreferenz .
		@return Transaktionsreferenz 	  */
	@Override
	public java.lang.String getTransactionIdAPI () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_TransactionIdAPI);
	}
}