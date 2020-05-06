/** Generated Model - DO NOT CHANGE */
package de.metas.marketing.base.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for MKTG_Campaign
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_MKTG_Campaign extends org.compiere.model.PO implements I_MKTG_Campaign, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 724096920L;

    /** Standard Constructor */
    public X_MKTG_Campaign (Properties ctx, int MKTG_Campaign_ID, String trxName)
    {
      super (ctx, MKTG_Campaign_ID, trxName);
      /** if (MKTG_Campaign_ID == 0)
        {
			setIsDefaultNewsletter (false); // N
			setMKTG_Campaign_ID (0);
			setMKTG_Platform_ID (0);
			setName (null);
        } */
    }

    /** Load Constructor */
    public X_MKTG_Campaign (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Textbaustein.
		@param AD_BoilerPlate_ID Textbaustein	  */
	@Override
	public void setAD_BoilerPlate_ID (int AD_BoilerPlate_ID)
	{
		if (AD_BoilerPlate_ID < 1) 
			set_Value (COLUMNNAME_AD_BoilerPlate_ID, null);
		else 
			set_Value (COLUMNNAME_AD_BoilerPlate_ID, Integer.valueOf(AD_BoilerPlate_ID));
	}

	/** Get Textbaustein.
		@return Textbaustein	  */
	@Override
	public int getAD_BoilerPlate_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_BoilerPlate_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Beschreibung.
		@param Description Beschreibung	  */
	@Override
	public void setDescription (java.lang.String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	/** Get Beschreibung.
		@return Beschreibung	  */
	@Override
	public java.lang.String getDescription () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Description);
	}

	/** Set Enddatum.
		@param EndDate 
		Last effective date (inclusive)
	  */
	@Override
	public void setEndDate (java.sql.Timestamp EndDate)
	{
		set_Value (COLUMNNAME_EndDate, EndDate);
	}

	/** Get Enddatum.
		@return Last effective date (inclusive)
	  */
	@Override
	public java.sql.Timestamp getEndDate () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_EndDate);
	}

	/** Set IsDefaultNewsletter.
		@param IsDefaultNewsletter IsDefaultNewsletter	  */
	@Override
	public void setIsDefaultNewsletter (boolean IsDefaultNewsletter)
	{
		set_Value (COLUMNNAME_IsDefaultNewsletter, Boolean.valueOf(IsDefaultNewsletter));
	}

	/** Get IsDefaultNewsletter.
		@return IsDefaultNewsletter	  */
	@Override
	public boolean isDefaultNewsletter () 
	{
		Object oo = get_Value(COLUMNNAME_IsDefaultNewsletter);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Synchronisierungstatus-Detail.
		@param LastSyncDetailMessage Synchronisierungstatus-Detail	  */
	@Override
	public void setLastSyncDetailMessage (java.lang.String LastSyncDetailMessage)
	{
		set_Value (COLUMNNAME_LastSyncDetailMessage, LastSyncDetailMessage);
	}

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
		set_Value (COLUMNNAME_LastSyncOfLocalToRemote, LastSyncOfLocalToRemote);
	}

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
		set_Value (COLUMNNAME_LastSyncOfRemoteToLocal, LastSyncOfRemoteToLocal);
	}

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
		set_Value (COLUMNNAME_LastSyncStatus, LastSyncStatus);
	}

	/** Get Letzter Synchronisierungsstatus.
		@return Letzter Synchronisierungsstatus	  */
	@Override
	public java.lang.String getLastSyncStatus () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_LastSyncStatus);
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
		if (MKTG_Platform_ID < 1) 
			set_Value (COLUMNNAME_MKTG_Platform_ID, null);
		else 
			set_Value (COLUMNNAME_MKTG_Platform_ID, Integer.valueOf(MKTG_Platform_ID));
	}

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
		set_Value (COLUMNNAME_Name, Name);
	}

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
		set_Value (COLUMNNAME_RemoteRecordId, RemoteRecordId);
	}

	/** Get Externe Datensatz-ID.
		@return Externe Datensatz-ID	  */
	@Override
	public java.lang.String getRemoteRecordId () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_RemoteRecordId);
	}

	/** Set Anfangsdatum.
		@param StartDate 
		First effective day (inclusive)
	  */
	@Override
	public void setStartDate (java.sql.Timestamp StartDate)
	{
		set_Value (COLUMNNAME_StartDate, StartDate);
	}

	/** Get Anfangsdatum.
		@return First effective day (inclusive)
	  */
	@Override
	public java.sql.Timestamp getStartDate () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_StartDate);
	}
}