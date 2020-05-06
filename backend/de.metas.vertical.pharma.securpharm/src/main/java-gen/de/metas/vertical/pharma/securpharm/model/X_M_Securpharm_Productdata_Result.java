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
	 *
	 */
	private static final long serialVersionUID = 2079408842L;

    /** Standard Constructor */
    public X_M_Securpharm_Productdata_Result (Properties ctx, int M_Securpharm_Productdata_Result_ID, String trxName)
    {
      super (ctx, M_Securpharm_Productdata_Result_ID, trxName);
      /** if (M_Securpharm_Productdata_Result_ID == 0)
        {
			setIsDecommissioned (false); // N
			setIsError (false); // N
			setIsPackVerified (false); // N
			setM_Securpharm_Productdata_Result_ID (0);
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

	/** 
	 * ActiveStatus AD_Reference_ID=319
	 * Reference name: _YesNo
	 */
	public static final int ACTIVESTATUS_AD_Reference_ID=319;
	/** Yes = Y */
	public static final String ACTIVESTATUS_Yes = "Y";
	/** No = N */
	public static final String ACTIVESTATUS_No = "N";
	/** Set Aktiv Status.
		@param ActiveStatus Aktiv Status	  */
	@Override
	public void setActiveStatus (java.lang.String ActiveStatus)
	{

		set_ValueNoCheck (COLUMNNAME_ActiveStatus, ActiveStatus);
	}

	/** Get Aktiv Status.
		@return Aktiv Status	  */
	@Override
	public java.lang.String getActiveStatus () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ActiveStatus);
	}

	/** Set Decommissioned Server Transaction Id.
		@param DecommissionedServerTransactionId Decommissioned Server Transaction Id	  */
	@Override
	public void setDecommissionedServerTransactionId (java.lang.String DecommissionedServerTransactionId)
	{
		set_Value (COLUMNNAME_DecommissionedServerTransactionId, DecommissionedServerTransactionId);
	}

	/** Get Decommissioned Server Transaction Id.
		@return Decommissioned Server Transaction Id	  */
	@Override
	public java.lang.String getDecommissionedServerTransactionId () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DecommissionedServerTransactionId);
	}

	/** Set Mindesthaltbarkeit.
		@param ExpirationDate Mindesthaltbarkeit	  */
	@Override
	public void setExpirationDate (java.sql.Timestamp ExpirationDate)
	{
		set_ValueNoCheck (COLUMNNAME_ExpirationDate, ExpirationDate);
	}

	/** Get Mindesthaltbarkeit.
		@return Mindesthaltbarkeit	  */
	@Override
	public java.sql.Timestamp getExpirationDate () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_ExpirationDate);
	}

	/** Set Inaktiv Grund.
		@param InactiveReason Inaktiv Grund	  */
	@Override
	public void setInactiveReason (java.lang.String InactiveReason)
	{
		set_ValueNoCheck (COLUMNNAME_InactiveReason, InactiveReason);
	}

	/** Get Inaktiv Grund.
		@return Inaktiv Grund	  */
	@Override
	public java.lang.String getInactiveReason () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_InactiveReason);
	}

	/** Set Decommissioned.
		@param IsDecommissioned Decommissioned	  */
	@Override
	public void setIsDecommissioned (boolean IsDecommissioned)
	{
		set_Value (COLUMNNAME_IsDecommissioned, Boolean.valueOf(IsDecommissioned));
	}

	/** Get Decommissioned.
		@return Decommissioned	  */
	@Override
	public boolean isDecommissioned () 
	{
		Object oo = get_Value(COLUMNNAME_IsDecommissioned);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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

	/** Set Verified Pack.
		@param IsPackVerified Verified Pack	  */
	@Override
	public void setIsPackVerified (boolean IsPackVerified)
	{
		set_Value (COLUMNNAME_IsPackVerified, Boolean.valueOf(IsPackVerified));
	}

	/** Get Verified Pack.
		@return Verified Pack	  */
	@Override
	public boolean isPackVerified () 
	{
		Object oo = get_Value(COLUMNNAME_IsPackVerified);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Last Result Code.
		@param LastResultCode Last Result Code	  */
	@Override
	public void setLastResultCode (java.lang.String LastResultCode)
	{
		set_Value (COLUMNNAME_LastResultCode, LastResultCode);
	}

	/** Get Last Result Code.
		@return Last Result Code	  */
	@Override
	public java.lang.String getLastResultCode () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_LastResultCode);
	}

	/** Set Last Result Message.
		@param LastResultMessage Last Result Message	  */
	@Override
	public void setLastResultMessage (java.lang.String LastResultMessage)
	{
		set_Value (COLUMNNAME_LastResultMessage, LastResultMessage);
	}

	/** Get Last Result Message.
		@return Last Result Message	  */
	@Override
	public java.lang.String getLastResultMessage () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_LastResultMessage);
	}

	/** Set Chargennummer.
		@param LotNumber Chargennummer	  */
	@Override
	public void setLotNumber (java.lang.String LotNumber)
	{
		set_ValueNoCheck (COLUMNNAME_LotNumber, LotNumber);
	}

	/** Get Chargennummer.
		@return Chargennummer	  */
	@Override
	public java.lang.String getLotNumber () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_LotNumber);
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

	/** Set Securpharm Produktdaten Ergebnise.
		@param M_Securpharm_Productdata_Result_ID Securpharm Produktdaten Ergebnise	  */
	@Override
	public void setM_Securpharm_Productdata_Result_ID (int M_Securpharm_Productdata_Result_ID)
	{
		if (M_Securpharm_Productdata_Result_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Securpharm_Productdata_Result_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Securpharm_Productdata_Result_ID, Integer.valueOf(M_Securpharm_Productdata_Result_ID));
	}

	/** Get Securpharm Produktdaten Ergebnise.
		@return Securpharm Produktdaten Ergebnise	  */
	@Override
	public int getM_Securpharm_Productdata_Result_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Securpharm_Productdata_Result_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Verification Code.
		@param PackVerificationCode Verification Code	  */
	@Override
	public void setPackVerificationCode (java.lang.String PackVerificationCode)
	{
		set_Value (COLUMNNAME_PackVerificationCode, PackVerificationCode);
	}

	/** Get Verification Code.
		@return Verification Code	  */
	@Override
	public java.lang.String getPackVerificationCode () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_PackVerificationCode);
	}

	/** Set Verification Message.
		@param PackVerificationMessage Verification Message	  */
	@Override
	public void setPackVerificationMessage (java.lang.String PackVerificationMessage)
	{
		set_Value (COLUMNNAME_PackVerificationMessage, PackVerificationMessage);
	}

	/** Get Verification Message.
		@return Verification Message	  */
	@Override
	public java.lang.String getPackVerificationMessage () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_PackVerificationMessage);
	}

	/** Set Produktcode.
		@param ProductCode Produktcode	  */
	@Override
	public void setProductCode (java.lang.String ProductCode)
	{
		set_ValueNoCheck (COLUMNNAME_ProductCode, ProductCode);
	}

	/** Get Produktcode.
		@return Produktcode	  */
	@Override
	public java.lang.String getProductCode () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ProductCode);
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

	/** Get Kodierungskennzeichen.
		@return Kodierungskennzeichen
	  */
	@Override
	public java.lang.String getProductCodeType () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ProductCodeType);
	}

	/** 
	 * SerialNumber AD_Reference_ID=110
	 * Reference name: AD_User
	 */
	public static final int SERIALNUMBER_AD_Reference_ID=110;
	/** Set Seriennummer.
		@param SerialNumber Seriennummer	  */
	@Override
	public void setSerialNumber (java.lang.String SerialNumber)
	{

		set_ValueNoCheck (COLUMNNAME_SerialNumber, SerialNumber);
	}

	/** Get Seriennummer.
		@return Seriennummer	  */
	@Override
	public java.lang.String getSerialNumber () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_SerialNumber);
	}
}