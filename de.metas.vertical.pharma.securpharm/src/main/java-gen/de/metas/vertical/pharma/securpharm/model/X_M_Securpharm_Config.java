/** Generated Model - DO NOT CHANGE */
package de.metas.vertical.pharma.securpharm.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for M_Securpharm_Config
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_M_Securpharm_Config extends org.compiere.model.PO implements I_M_Securpharm_Config, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 2060146244L;

    /** Standard Constructor */
    public X_M_Securpharm_Config (Properties ctx, int M_Securpharm_Config_ID, String trxName)
    {
      super (ctx, M_Securpharm_Config_ID, trxName);
      /** if (M_Securpharm_Config_ID == 0)
        {
			setApplicationUUID (null);
			setAuthPharmaRestApiBaseURL (null);
			setCertificatePath (null);
			setM_Securpharm_Config_ID (0);
			setPharmaRestApiBaseURL (null);
			setSupport_User_ID (0);
			setTanPassword (null);
        } */
    }

    /** Load Constructor */
    public X_M_Securpharm_Config (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Application UUID.
		@param ApplicationUUID Application UUID	  */
	@Override
	public void setApplicationUUID (java.lang.String ApplicationUUID)
	{
		set_Value (COLUMNNAME_ApplicationUUID, ApplicationUUID);
	}

	/** Get Application UUID.
		@return Application UUID	  */
	@Override
	public java.lang.String getApplicationUUID () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ApplicationUUID);
	}

	/** Set Pharma Auth REST API URL.
		@param AuthPharmaRestApiBaseURL Pharma Auth REST API URL	  */
	@Override
	public void setAuthPharmaRestApiBaseURL (java.lang.String AuthPharmaRestApiBaseURL)
	{
		set_Value (COLUMNNAME_AuthPharmaRestApiBaseURL, AuthPharmaRestApiBaseURL);
	}

	/** Get Pharma Auth REST API URL.
		@return Pharma Auth REST API URL	  */
	@Override
	public java.lang.String getAuthPharmaRestApiBaseURL () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_AuthPharmaRestApiBaseURL);
	}

	/** Set Zertifikatpfad.
		@param CertificatePath Zertifikatpfad	  */
	@Override
	public void setCertificatePath (java.lang.String CertificatePath)
	{
		set_Value (COLUMNNAME_CertificatePath, CertificatePath);
	}

	/** Get Zertifikatpfad.
		@return Zertifikatpfad	  */
	@Override
	public java.lang.String getCertificatePath () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_CertificatePath);
	}

	/** Set SecurPharm Einstellungen.
		@param M_Securpharm_Config_ID SecurPharm Einstellungen	  */
	@Override
	public void setM_Securpharm_Config_ID (int M_Securpharm_Config_ID)
	{
		if (M_Securpharm_Config_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Securpharm_Config_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Securpharm_Config_ID, Integer.valueOf(M_Securpharm_Config_ID));
	}

	/** Get SecurPharm Einstellungen.
		@return SecurPharm Einstellungen	  */
	@Override
	public int getM_Securpharm_Config_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Securpharm_Config_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Pharma REST API URL.
		@param PharmaRestApiBaseURL Pharma REST API URL	  */
	@Override
	public void setPharmaRestApiBaseURL (java.lang.String PharmaRestApiBaseURL)
	{
		set_Value (COLUMNNAME_PharmaRestApiBaseURL, PharmaRestApiBaseURL);
	}

	/** Get Pharma REST API URL.
		@return Pharma REST API URL	  */
	@Override
	public java.lang.String getPharmaRestApiBaseURL () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_PharmaRestApiBaseURL);
	}

	/** Set Support Benutzer.
		@param Support_User_ID 
		Benutzer für Benachrichtigungen 
	  */
	@Override
	public void setSupport_User_ID (int Support_User_ID)
	{
		if (Support_User_ID < 1) 
			set_Value (COLUMNNAME_Support_User_ID, null);
		else 
			set_Value (COLUMNNAME_Support_User_ID, Integer.valueOf(Support_User_ID));
	}

	/** Get Support Benutzer.
		@return Benutzer für Benachrichtigungen 
	  */
	@Override
	public int getSupport_User_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Support_User_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set TAN Passwort.
		@param TanPassword 
		TAN Passwort benutzt für Authentifizierung
	  */
	@Override
	public void setTanPassword (java.lang.String TanPassword)
	{
		set_Value (COLUMNNAME_TanPassword, TanPassword);
	}

	/** Get TAN Passwort.
		@return TAN Passwort benutzt für Authentifizierung
	  */
	@Override
	public java.lang.String getTanPassword () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_TanPassword);
	}
}