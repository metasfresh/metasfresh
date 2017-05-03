/** Generated Model - DO NOT CHANGE */
package de.metas.material.dispo.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for MD_EventStore
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_MD_EventStore extends org.compiere.model.PO implements I_MD_EventStore, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -465363475L;

    /** Standard Constructor */
    public X_MD_EventStore (Properties ctx, int MD_EventStore_ID, String trxName)
    {
      super (ctx, MD_EventStore_ID, trxName);
      /** if (MD_EventStore_ID == 0)
        {
			setEventData (null);
			setEventTime (new Timestamp( System.currentTimeMillis() ));
			setMD_EventStore_ID (0);
			setProcessed (false);
// N
        } */
    }

    /** Load Constructor */
    public X_MD_EventStore (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Daten.
		@param EventData Daten	  */
	@Override
	public void setEventData (java.lang.String EventData)
	{
		set_ValueNoCheck (COLUMNNAME_EventData, EventData);
	}

	/** Get Daten.
		@return Daten	  */
	@Override
	public java.lang.String getEventData () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_EventData);
	}

	/** Set Zeitpunkt.
		@param EventTime Zeitpunkt	  */
	@Override
	public void setEventTime (java.sql.Timestamp EventTime)
	{
		set_ValueNoCheck (COLUMNNAME_EventTime, EventTime);
	}

	/** Get Zeitpunkt.
		@return Zeitpunkt	  */
	@Override
	public java.sql.Timestamp getEventTime () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_EventTime);
	}

	@Override
	public de.metas.material.dispo.model.I_MD_Candidate getMD_Candidate() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_MD_Candidate_ID, de.metas.material.dispo.model.I_MD_Candidate.class);
	}

	@Override
	public void setMD_Candidate(de.metas.material.dispo.model.I_MD_Candidate MD_Candidate)
	{
		set_ValueFromPO(COLUMNNAME_MD_Candidate_ID, de.metas.material.dispo.model.I_MD_Candidate.class, MD_Candidate);
	}

	/** Set Dispositionskandidat.
		@param MD_Candidate_ID Dispositionskandidat	  */
	@Override
	public void setMD_Candidate_ID (int MD_Candidate_ID)
	{
		if (MD_Candidate_ID < 1) 
			set_Value (COLUMNNAME_MD_Candidate_ID, null);
		else 
			set_Value (COLUMNNAME_MD_Candidate_ID, Integer.valueOf(MD_Candidate_ID));
	}

	/** Get Dispositionskandidat.
		@return Dispositionskandidat	  */
	@Override
	public int getMD_Candidate_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_MD_Candidate_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Material-Dispo-Eventstore.
		@param MD_EventStore_ID Material-Dispo-Eventstore	  */
	@Override
	public void setMD_EventStore_ID (int MD_EventStore_ID)
	{
		if (MD_EventStore_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_MD_EventStore_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_MD_EventStore_ID, Integer.valueOf(MD_EventStore_ID));
	}

	/** Get Material-Dispo-Eventstore.
		@return Material-Dispo-Eventstore	  */
	@Override
	public int getMD_EventStore_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_MD_EventStore_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Verarbeitet.
		@param Processed 
		Checkbox sagt aus, ob der Beleg verarbeitet wurde. 
	  */
	@Override
	public void setProcessed (boolean Processed)
	{
		set_ValueNoCheck (COLUMNNAME_Processed, Boolean.valueOf(Processed));
	}

	/** Get Verarbeitet.
		@return Checkbox sagt aus, ob der Beleg verarbeitet wurde. 
	  */
	@Override
	public boolean isProcessed () 
	{
		Object oo = get_Value(COLUMNNAME_Processed);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}
}