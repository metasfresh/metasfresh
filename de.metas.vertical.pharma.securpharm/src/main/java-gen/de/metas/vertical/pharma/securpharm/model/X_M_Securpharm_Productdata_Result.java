/** Generated Model - DO NOT CHANGE */
package de.metas.vertical.pharma.securpharm.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for M_Securpharm_Productdata_Result
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_M_Securpharm_Productdata_Result extends org.compiere.model.PO implements I_M_Securpharm_Productdata_Result, org.compiere.model.I_Persistent 
{

	/**
	 * SerialNumber AD_Reference_ID=110
	 * Reference name: AD_User
	 */
	public static final int SERIALNUMBER_AD_Reference_ID=110;
	/**
	 *
	 */
	private static final long serialVersionUID = -829984081L;

    /** Standard Constructor */
    public X_M_Securpharm_Productdata_Result (Properties ctx, int M_Securpharm_Productdata_Result_ID, String trxName)
    {
      super (ctx, M_Securpharm_Productdata_Result_ID, trxName);
      /** if (M_Securpharm_Productdata_Result_ID == 0)
        {
			sethasActiveStatus (false); // N
			setIsError (false); // N
			setM_Securpharm_Productdata_Result_ID (0);
			setRequestEndTime (new Timestamp( System.currentTimeMillis() ));
			setRequestStartTime (new Timestamp( System.currentTimeMillis() ));
			setRequestUrl (null);
			setTransactionIDClient (null);
        } */
    }


    /** Load Constructor */
    public X_M_Securpharm_Productdata_Result (Properties ctx, ResultSet rs, String trxName)
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

	/** Get Mindesthaltbarkeit.
		@return Mindesthaltbarkeit	  */
	@Override
	public java.sql.Timestamp getExpirationDate () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_ExpirationDate);
	}

	/** Set Mindesthaltbarkeit.
		@param ExpirationDate Mindesthaltbarkeit	  */
	@Override
	public void setExpirationDate (java.sql.Timestamp ExpirationDate)
	{
		set_ValueNoCheck (COLUMNNAME_ExpirationDate, ExpirationDate);
	}

	/** Set Aktiv Status.
		@param hasActiveStatus Aktiv Status	  */
	@Override
	public void sethasActiveStatus (boolean hasActiveStatus)
	{
		set_ValueNoCheck (COLUMNNAME_hasActiveStatus, Boolean.valueOf(hasActiveStatus));
	}

	/** Get Aktiv Status.
		@return Aktiv Status	  */
	@Override
	public boolean ishasActiveStatus ()
	{
		Object oo = get_Value(COLUMNNAME_hasActiveStatus);
		if (oo != null)
		{
			 if (oo instanceof Boolean)
				 return ((Boolean)oo).booleanValue();
			return "Y".equals(oo);
		}
		return false;
	}

	/** Get Inaktiv Grund.
		@return Inaktiv Grund	  */
	@Override
	public java.lang.String getInactiveReason () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_InactiveReason);
	}

	/** Set Inaktiv Grund.
		@param InactiveReason Inaktiv Grund	  */
	@Override
	public void setInactiveReason (java.lang.String InactiveReason)
	{
		set_ValueNoCheck (COLUMNNAME_InactiveReason, InactiveReason);
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

	/** Get Chargennummer.
		@return Chargennummer	  */
	@Override
	public java.lang.String getLotNumber () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_LotNumber);
	}

	/** Set Chargennummer.
		@param LotNumber Chargennummer	  */
	@Override
	public void setLotNumber (java.lang.String LotNumber)
	{
		set_ValueNoCheck (COLUMNNAME_LotNumber, LotNumber);
	}

	/** Get Handling Unit.
		@return Handling Unit	  */
	@Override
	public int getM_HU_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_HU_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Handling Unit.
		@param M_HU_ID Handling Unit	  */
	@Override
	public void setM_HU_ID (int M_HU_ID)
	{
		if (M_HU_ID < 1)
			set_ValueNoCheck (COLUMNNAME_M_HU_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_M_HU_ID, Integer.valueOf(M_HU_ID));
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
			set_ValueNoCheck (COLUMNNAME_M_Securpharm_Productdata_Result_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_M_Securpharm_Productdata_Result_ID, Integer.valueOf(M_Securpharm_Productdata_Result_ID));
	}

	/** Get Produktcode.
		@return Produktcode	  */
	@Override
	public java.lang.String getProductCode () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ProductCode);
	}

	/** Set Produktcode.
		@param ProductCode Produktcode	  */
	@Override
	public void setProductCode (java.lang.String ProductCode)
	{
		set_ValueNoCheck (COLUMNNAME_ProductCode, ProductCode);
	}

	/** Get Kodierungskennzeichen.
		@return Kodierungskennzeichen
	  */
	@Override
	public java.lang.String getProductCodeType () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ProductCodeType);
	}

	/** Set Kodierungskennzeichen.
		@param ProductCodeType
		Kodierungskennzeichen
	  */
	@Override
	public void setProductCodeType (java.lang.String ProductCodeType)
	{
		set_ValueNoCheck (COLUMNNAME_ProductCodeType, ProductCodeType);
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

	/** Get Seriennummer.
		@return Seriennummer	  */
	@Override
	public java.lang.String getSerialNumber ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_SerialNumber);
	}

	/** Set Seriennummer.
		@param SerialNumber Seriennummer	  */
	@Override
	public void setSerialNumber (java.lang.String SerialNumber)
	{

		set_ValueNoCheck (COLUMNNAME_SerialNumber, SerialNumber);
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
