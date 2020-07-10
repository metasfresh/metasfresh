/** Generated Model - DO NOT CHANGE */
package de.metas.serviceprovider.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for S_FailedTimeBooking
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_S_FailedTimeBooking extends org.compiere.model.PO implements I_S_FailedTimeBooking, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 341789135L;

    /** Standard Constructor */
    public X_S_FailedTimeBooking (Properties ctx, int S_FailedTimeBooking_ID, String trxName)
    {
      super (ctx, S_FailedTimeBooking_ID, trxName);
      /** if (S_FailedTimeBooking_ID == 0)
        {
			setExternalId (null);
			setS_FailedTimeBooking_ID (0);
        } */
    }

    /** Load Constructor */
    public X_S_FailedTimeBooking (Properties ctx, ResultSet rs, String trxName)
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

	/** Set External ID.
		@param ExternalId External ID	  */
	@Override
	public void setExternalId (java.lang.String ExternalId)
	{
		set_Value (COLUMNNAME_ExternalId, ExternalId);
	}

	/** Get External ID.
		@return External ID	  */
	@Override
	public java.lang.String getExternalId () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ExternalId);
	}

	/** 
	 * ExternalSystem AD_Reference_ID=541117
	 * Reference name: ExternalSystem
	 */
	public static final int EXTERNALSYSTEM_AD_Reference_ID=541117;
	/** Github = Github */
	public static final String EXTERNALSYSTEM_Github = "Github";
	/** Everhour = Everhour */
	public static final String EXTERNALSYSTEM_Everhour = "Everhour";
	/** Set External system.
		@param ExternalSystem 
		Name of an external system (e.g. Github )
	  */
	@Override
	public void setExternalSystem (java.lang.String ExternalSystem)
	{

		set_Value (COLUMNNAME_ExternalSystem, ExternalSystem);
	}

	/** Get External system.
		@return Name of an external system (e.g. Github )
	  */
	@Override
	public java.lang.String getExternalSystem () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ExternalSystem);
	}

	/** Set Import-Fehler.
		@param ImportErrorMsg 
		Fehler beim Einlesen der Datei, z.B. Fehler im Format eines Datums
	  */
	@Override
	public void setImportErrorMsg (java.lang.String ImportErrorMsg)
	{
		set_Value (COLUMNNAME_ImportErrorMsg, ImportErrorMsg);
	}

	/** Get Import-Fehler.
		@return Fehler beim Einlesen der Datei, z.B. Fehler im Format eines Datums
	  */
	@Override
	public java.lang.String getImportErrorMsg () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ImportErrorMsg);
	}

	/** Set JSON value.
		@param JSONValue JSON value	  */
	@Override
	public void setJSONValue (java.lang.String JSONValue)
	{
		set_Value (COLUMNNAME_JSONValue, JSONValue);
	}

	/** Get JSON value.
		@return JSON value	  */
	@Override
	public java.lang.String getJSONValue () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_JSONValue);
	}

	/** Set Failed time booking.
		@param S_FailedTimeBooking_ID Failed time booking	  */
	@Override
	public void setS_FailedTimeBooking_ID (int S_FailedTimeBooking_ID)
	{
		if (S_FailedTimeBooking_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_S_FailedTimeBooking_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_S_FailedTimeBooking_ID, Integer.valueOf(S_FailedTimeBooking_ID));
	}

	/** Get Failed time booking.
		@return Failed time booking	  */
	@Override
	public int getS_FailedTimeBooking_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_S_FailedTimeBooking_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}