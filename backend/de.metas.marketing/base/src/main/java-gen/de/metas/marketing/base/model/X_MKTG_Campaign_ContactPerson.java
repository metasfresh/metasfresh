/** Generated Model - DO NOT CHANGE */
package de.metas.marketing.base.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for MKTG_Campaign_ContactPerson
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_MKTG_Campaign_ContactPerson extends org.compiere.model.PO implements I_MKTG_Campaign_ContactPerson, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 870930022L;

    /** Standard Constructor */
    public X_MKTG_Campaign_ContactPerson (Properties ctx, int MKTG_Campaign_ContactPerson_ID, String trxName)
    {
      super (ctx, MKTG_Campaign_ContactPerson_ID, trxName);
      /** if (MKTG_Campaign_ContactPerson_ID == 0)
        {
			setMKTG_Campaign_ContactPerson_ID (0);
			setMKTG_Campaign_ID (0);
			setMKTG_ContactPerson_ID (0);
        } */
    }

    /** Load Constructor */
    public X_MKTG_Campaign_ContactPerson (Properties ctx, ResultSet rs, String trxName)
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
	public org.compiere.model.I_AD_User getAD_User() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_User_ID, org.compiere.model.I_AD_User.class);
	}

	@Override
	public void setAD_User(org.compiere.model.I_AD_User AD_User)
	{
		set_ValueFromPO(COLUMNNAME_AD_User_ID, org.compiere.model.I_AD_User.class, AD_User);
	}

	/** Set Ansprechpartner.
		@param AD_User_ID 
		User within the system - Internal or Business Partner Contact
	  */
	@Override
	public void setAD_User_ID (int AD_User_ID)
	{
		if (AD_User_ID < 0) 
			set_Value (COLUMNNAME_AD_User_ID, null);
		else 
			set_Value (COLUMNNAME_AD_User_ID, Integer.valueOf(AD_User_ID));
	}

	/** Get Ansprechpartner.
		@return User within the system - Internal or Business Partner Contact
	  */
	@Override
	public int getAD_User_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_User_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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
		throw new IllegalArgumentException ("C_BPartner_ID is virtual column");	}

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
	public org.compiere.model.I_C_Location getC_Location() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Location_ID, org.compiere.model.I_C_Location.class);
	}

	@Override
	public void setC_Location(org.compiere.model.I_C_Location C_Location)
	{
		set_ValueFromPO(COLUMNNAME_C_Location_ID, org.compiere.model.I_C_Location.class, C_Location);
	}

	/** Set Anschrift.
		@param C_Location_ID 
		Adresse oder Anschrift
	  */
	@Override
	public void setC_Location_ID (int C_Location_ID)
	{
		throw new IllegalArgumentException ("C_Location_ID is virtual column");	}

	/** Get Anschrift.
		@return Adresse oder Anschrift
	  */
	@Override
	public int getC_Location_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Location_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** 
	 * DeactivatedOnRemotePlatform AD_Reference_ID=540861
	 * Reference name: Yes_No_Unknown
	 */
	public static final int DEACTIVATEDONREMOTEPLATFORM_AD_Reference_ID=540861;
	/** YES = Y */
	public static final String DEACTIVATEDONREMOTEPLATFORM_YES = "Y";
	/** NO = N */
	public static final String DEACTIVATEDONREMOTEPLATFORM_NO = "N";
	/** UNKNOWN = Unknown */
	public static final String DEACTIVATEDONREMOTEPLATFORM_UNKNOWN = "Unknown";
	/** Set Platformseitig deaktiviert.
		@param DeactivatedOnRemotePlatform Platformseitig deaktiviert	  */
	@Override
	public void setDeactivatedOnRemotePlatform (java.lang.String DeactivatedOnRemotePlatform)
	{

		throw new IllegalArgumentException ("DeactivatedOnRemotePlatform is virtual column");	}

	/** Get Platformseitig deaktiviert.
		@return Platformseitig deaktiviert	  */
	@Override
	public java.lang.String getDeactivatedOnRemotePlatform () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DeactivatedOnRemotePlatform);
	}

	/** Set eMail.
		@param EMail 
		EMail-Adresse
	  */
	@Override
	public void setEMail (java.lang.String EMail)
	{
		throw new IllegalArgumentException ("EMail is virtual column");	}

	/** Get eMail.
		@return EMail-Adresse
	  */
	@Override
	public java.lang.String getEMail () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_EMail);
	}

	/** Set Synchronisierungstatus-Detail.
		@param LastSyncDetailMessage Synchronisierungstatus-Detail	  */
	@Override
	public void setLastSyncDetailMessage (java.lang.String LastSyncDetailMessage)
	{
		throw new IllegalArgumentException ("LastSyncDetailMessage is virtual column");	}

	/** Get Synchronisierungstatus-Detail.
		@return Synchronisierungstatus-Detail	  */
	@Override
	public java.lang.String getLastSyncDetailMessage () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_LastSyncDetailMessage);
	}

	/** Set Zuletzt auf Platform hochgeladen.
		@param LastSyncOfLocalToRemote Zuletzt auf Platform hochgeladen	  */
	@Override
	public void setLastSyncOfLocalToRemote (java.sql.Timestamp LastSyncOfLocalToRemote)
	{
		throw new IllegalArgumentException ("LastSyncOfLocalToRemote is virtual column");	}

	/** Get Zuletzt auf Platform hochgeladen.
		@return Zuletzt auf Platform hochgeladen	  */
	@Override
	public java.sql.Timestamp getLastSyncOfLocalToRemote () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_LastSyncOfLocalToRemote);
	}

	/** Set Zuletzt von Platform runtergeladen.
		@param LastSyncOfRemoteToLocal Zuletzt von Platform runtergeladen	  */
	@Override
	public void setLastSyncOfRemoteToLocal (java.sql.Timestamp LastSyncOfRemoteToLocal)
	{
		throw new IllegalArgumentException ("LastSyncOfRemoteToLocal is virtual column");	}

	/** Get Zuletzt von Platform runtergeladen.
		@return Zuletzt von Platform runtergeladen	  */
	@Override
	public java.sql.Timestamp getLastSyncOfRemoteToLocal () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_LastSyncOfRemoteToLocal);
	}

	/** Set Letzter Synchronisierungsstatus.
		@param LastSyncStatus Letzter Synchronisierungsstatus	  */
	@Override
	public void setLastSyncStatus (java.lang.String LastSyncStatus)
	{
		throw new IllegalArgumentException ("LastSyncStatus is virtual column");	}

	/** Get Letzter Synchronisierungsstatus.
		@return Letzter Synchronisierungsstatus	  */
	@Override
	public java.lang.String getLastSyncStatus () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_LastSyncStatus);
	}

	/** Set MKTG_Campaign_ContactPerson.
		@param MKTG_Campaign_ContactPerson_ID MKTG_Campaign_ContactPerson	  */
	@Override
	public void setMKTG_Campaign_ContactPerson_ID (int MKTG_Campaign_ContactPerson_ID)
	{
		if (MKTG_Campaign_ContactPerson_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_MKTG_Campaign_ContactPerson_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_MKTG_Campaign_ContactPerson_ID, Integer.valueOf(MKTG_Campaign_ContactPerson_ID));
	}

	/** Get MKTG_Campaign_ContactPerson.
		@return MKTG_Campaign_ContactPerson	  */
	@Override
	public int getMKTG_Campaign_ContactPerson_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_MKTG_Campaign_ContactPerson_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.marketing.base.model.I_MKTG_Campaign getMKTG_Campaign() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_MKTG_Campaign_ID, de.metas.marketing.base.model.I_MKTG_Campaign.class);
	}

	@Override
	public void setMKTG_Campaign(de.metas.marketing.base.model.I_MKTG_Campaign MKTG_Campaign)
	{
		set_ValueFromPO(COLUMNNAME_MKTG_Campaign_ID, de.metas.marketing.base.model.I_MKTG_Campaign.class, MKTG_Campaign);
	}

	/** Set MKTG_Campaign.
		@param MKTG_Campaign_ID MKTG_Campaign	  */
	@Override
	public void setMKTG_Campaign_ID (int MKTG_Campaign_ID)
	{
		if (MKTG_Campaign_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_MKTG_Campaign_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_MKTG_Campaign_ID, Integer.valueOf(MKTG_Campaign_ID));
	}

	/** Get MKTG_Campaign.
		@return MKTG_Campaign	  */
	@Override
	public int getMKTG_Campaign_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_MKTG_Campaign_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.marketing.base.model.I_MKTG_ContactPerson getMKTG_ContactPerson() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_MKTG_ContactPerson_ID, de.metas.marketing.base.model.I_MKTG_ContactPerson.class);
	}

	@Override
	public void setMKTG_ContactPerson(de.metas.marketing.base.model.I_MKTG_ContactPerson MKTG_ContactPerson)
	{
		set_ValueFromPO(COLUMNNAME_MKTG_ContactPerson_ID, de.metas.marketing.base.model.I_MKTG_ContactPerson.class, MKTG_ContactPerson);
	}

	/** Set MKTG_ContactPerson.
		@param MKTG_ContactPerson_ID MKTG_ContactPerson	  */
	@Override
	public void setMKTG_ContactPerson_ID (int MKTG_ContactPerson_ID)
	{
		if (MKTG_ContactPerson_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_MKTG_ContactPerson_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_MKTG_ContactPerson_ID, Integer.valueOf(MKTG_ContactPerson_ID));
	}

	/** Get MKTG_ContactPerson.
		@return MKTG_ContactPerson	  */
	@Override
	public int getMKTG_ContactPerson_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_MKTG_ContactPerson_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.marketing.base.model.I_MKTG_Platform getMKTG_Platform() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_MKTG_Platform_ID, de.metas.marketing.base.model.I_MKTG_Platform.class);
	}

	@Override
	public void setMKTG_Platform(de.metas.marketing.base.model.I_MKTG_Platform MKTG_Platform)
	{
		set_ValueFromPO(COLUMNNAME_MKTG_Platform_ID, de.metas.marketing.base.model.I_MKTG_Platform.class, MKTG_Platform);
	}

	/** Set MKTG_Platform.
		@param MKTG_Platform_ID MKTG_Platform	  */
	@Override
	public void setMKTG_Platform_ID (int MKTG_Platform_ID)
	{
		throw new IllegalArgumentException ("MKTG_Platform_ID is virtual column");	}

	/** Get MKTG_Platform.
		@return MKTG_Platform	  */
	@Override
	public int getMKTG_Platform_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_MKTG_Platform_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Name.
		@param Name 
		Alphanumeric identifier of the entity
	  */
	@Override
	public void setName (java.lang.String Name)
	{
		throw new IllegalArgumentException ("Name is virtual column");	}

	/** Get Name.
		@return Alphanumeric identifier of the entity
	  */
	@Override
	public java.lang.String getName () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Name);
	}

	/** Set Externe Datensatz-ID.
		@param RemoteRecordId Externe Datensatz-ID	  */
	@Override
	public void setRemoteRecordId (java.lang.String RemoteRecordId)
	{
		throw new IllegalArgumentException ("RemoteRecordId is virtual column");	}

	/** Get Externe Datensatz-ID.
		@return Externe Datensatz-ID	  */
	@Override
	public java.lang.String getRemoteRecordId () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_RemoteRecordId);
	}
}