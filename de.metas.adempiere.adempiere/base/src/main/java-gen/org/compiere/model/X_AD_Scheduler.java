/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for AD_Scheduler
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_AD_Scheduler extends org.compiere.model.PO implements I_AD_Scheduler, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -1861109657L;

    /** Standard Constructor */
    public X_AD_Scheduler (Properties ctx, int AD_Scheduler_ID, String trxName)
    {
      super (ctx, AD_Scheduler_ID, trxName);
      /** if (AD_Scheduler_ID == 0)
        {
			setAD_Scheduler_ID (0);
			setEntityType (null);
// 'de.metas.swat'
			setKeepLogDays (0);
// 7
			setName (null);
			setSchedulerProcessType (null);
// P
			setScheduleType (null);
// F
			setStatus (null);
// NEW
			setSupervisor_ID (0);
        } */
    }

    /** Load Constructor */
    public X_AD_Scheduler (Properties ctx, ResultSet rs, String trxName)
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
	public org.compiere.model.I_AD_Process getAD_Process() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_Process_ID, org.compiere.model.I_AD_Process.class);
	}

	@Override
	public void setAD_Process(org.compiere.model.I_AD_Process AD_Process)
	{
		set_ValueFromPO(COLUMNNAME_AD_Process_ID, org.compiere.model.I_AD_Process.class, AD_Process);
	}

	/** Set Prozess.
		@param AD_Process_ID 
		Process or Report
	  */
	@Override
	public void setAD_Process_ID (int AD_Process_ID)
	{
		if (AD_Process_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Process_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Process_ID, Integer.valueOf(AD_Process_ID));
	}

	/** Get Prozess.
		@return Process or Report
	  */
	@Override
	public int getAD_Process_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Process_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_AD_Role getAD_Role() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_Role_ID, org.compiere.model.I_AD_Role.class);
	}

	@Override
	public void setAD_Role(org.compiere.model.I_AD_Role AD_Role)
	{
		set_ValueFromPO(COLUMNNAME_AD_Role_ID, org.compiere.model.I_AD_Role.class, AD_Role);
	}

	/** Set Rolle.
		@param AD_Role_ID 
		Responsibility Role
	  */
	@Override
	public void setAD_Role_ID (int AD_Role_ID)
	{
		if (AD_Role_ID < 0) 
			set_Value (COLUMNNAME_AD_Role_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Role_ID, Integer.valueOf(AD_Role_ID));
	}

	/** Get Rolle.
		@return Responsibility Role
	  */
	@Override
	public int getAD_Role_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Role_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Ablaufsteuerung.
		@param AD_Scheduler_ID 
		Schedule Processes
	  */
	@Override
	public void setAD_Scheduler_ID (int AD_Scheduler_ID)
	{
		if (AD_Scheduler_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Scheduler_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Scheduler_ID, Integer.valueOf(AD_Scheduler_ID));
	}

	/** Get Ablaufsteuerung.
		@return Schedule Processes
	  */
	@Override
	public int getAD_Scheduler_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Scheduler_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_AD_Task getAD_Task() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_Task_ID, org.compiere.model.I_AD_Task.class);
	}

	@Override
	public void setAD_Task(org.compiere.model.I_AD_Task AD_Task)
	{
		set_ValueFromPO(COLUMNNAME_AD_Task_ID, org.compiere.model.I_AD_Task.class, AD_Task);
	}

	/** Set Externer Prozess.
		@param AD_Task_ID 
		Ausserhalb von ADempiere zu startender Prozess
	  */
	@Override
	public void setAD_Task_ID (int AD_Task_ID)
	{
		if (AD_Task_ID < 1) 
			set_Value (COLUMNNAME_AD_Task_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Task_ID, Integer.valueOf(AD_Task_ID));
	}

	/** Get Externer Prozess.
		@return Ausserhalb von ADempiere zu startender Prozess
	  */
	@Override
	public int getAD_Task_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Task_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Cron-Zeitplan.
		@param CronPattern 
		Cron pattern to define when the process should be invoked.
	  */
	@Override
	public void setCronPattern (java.lang.String CronPattern)
	{
		set_Value (COLUMNNAME_CronPattern, CronPattern);
	}

	/** Get Cron-Zeitplan.
		@return Cron pattern to define when the process should be invoked.
	  */
	@Override
	public java.lang.String getCronPattern () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_CronPattern);
	}

	/** Set Tag letzter Lauf.
		@param DateLastRun 
		Date the process was last run.
	  */
	@Override
	public void setDateLastRun (java.sql.Timestamp DateLastRun)
	{
		set_Value (COLUMNNAME_DateLastRun, DateLastRun);
	}

	/** Get Tag letzter Lauf.
		@return Date the process was last run.
	  */
	@Override
	public java.sql.Timestamp getDateLastRun () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_DateLastRun);
	}

	/** Set Tag nächster Lauf.
		@param DateNextRun 
		Date the process will run next
	  */
	@Override
	public void setDateNextRun (java.sql.Timestamp DateNextRun)
	{
		set_Value (COLUMNNAME_DateNextRun, DateNextRun);
	}

	/** Get Tag nächster Lauf.
		@return Date the process will run next
	  */
	@Override
	public java.sql.Timestamp getDateNextRun () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_DateNextRun);
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

	/** 
	 * EntityType AD_Reference_ID=389
	 * Reference name: _EntityTypeNew
	 */
	public static final int ENTITYTYPE_AD_Reference_ID=389;
	/** Set Entitäts-Art.
		@param EntityType 
		Dictionary Entity Type; Determines ownership and synchronization
	  */
	@Override
	public void setEntityType (java.lang.String EntityType)
	{

		set_Value (COLUMNNAME_EntityType, EntityType);
	}

	/** Get Entitäts-Art.
		@return Dictionary Entity Type; Determines ownership and synchronization
	  */
	@Override
	public java.lang.String getEntityType () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_EntityType);
	}

	/** Set Häufigkeit.
		@param Frequency 
		Frequency of events
	  */
	@Override
	public void setFrequency (int Frequency)
	{
		set_Value (COLUMNNAME_Frequency, Integer.valueOf(Frequency));
	}

	/** Get Häufigkeit.
		@return Frequency of events
	  */
	@Override
	public int getFrequency () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Frequency);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** 
	 * FrequencyType AD_Reference_ID=221
	 * Reference name: _Frequency Type
	 */
	public static final int FREQUENCYTYPE_AD_Reference_ID=221;
	/** Minute = M */
	public static final String FREQUENCYTYPE_Minute = "M";
	/** Hour = H */
	public static final String FREQUENCYTYPE_Hour = "H";
	/** Day = D */
	public static final String FREQUENCYTYPE_Day = "D";
	/** Set Häufigkeitsart.
		@param FrequencyType 
		Frequency of event
	  */
	@Override
	public void setFrequencyType (java.lang.String FrequencyType)
	{

		set_Value (COLUMNNAME_FrequencyType, FrequencyType);
	}

	/** Get Häufigkeitsart.
		@return Frequency of event
	  */
	@Override
	public java.lang.String getFrequencyType () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_FrequencyType);
	}

	/** Set Ignore Processing Time.
		@param IsIgnoreProcessingTime 
		Do not include processing time for the DateNextRun calculation
	  */
	@Override
	public void setIsIgnoreProcessingTime (boolean IsIgnoreProcessingTime)
	{
		set_Value (COLUMNNAME_IsIgnoreProcessingTime, Boolean.valueOf(IsIgnoreProcessingTime));
	}

	/** Get Ignore Processing Time.
		@return Do not include processing time for the DateNextRun calculation
	  */
	@Override
	public boolean isIgnoreProcessingTime () 
	{
		Object oo = get_Value(COLUMNNAME_IsIgnoreProcessingTime);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Tage Protokoll aufheben.
		@param KeepLogDays 
		Number of days to keep the log entries
	  */
	@Override
	public void setKeepLogDays (int KeepLogDays)
	{
		set_Value (COLUMNNAME_KeepLogDays, Integer.valueOf(KeepLogDays));
	}

	/** Get Tage Protokoll aufheben.
		@return Number of days to keep the log entries
	  */
	@Override
	public int getKeepLogDays () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_KeepLogDays);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Dienst-Verwaltung.
		@param ManageScheduler Dienst-Verwaltung	  */
	@Override
	public void setManageScheduler (java.lang.String ManageScheduler)
	{
		set_Value (COLUMNNAME_ManageScheduler, ManageScheduler);
	}

	/** Get Dienst-Verwaltung.
		@return Dienst-Verwaltung	  */
	@Override
	public java.lang.String getManageScheduler () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ManageScheduler);
	}

	/** Set Day of the Month.
		@param MonthDay 
		Day of the month 1 to 28/29/30/31
	  */
	@Override
	public void setMonthDay (int MonthDay)
	{
		set_Value (COLUMNNAME_MonthDay, Integer.valueOf(MonthDay));
	}

	/** Get Day of the Month.
		@return Day of the month 1 to 28/29/30/31
	  */
	@Override
	public int getMonthDay () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_MonthDay);
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

	/** Set Verarbeiten.
		@param Processing Verarbeiten	  */
	@Override
	public void setProcessing (boolean Processing)
	{
		set_Value (COLUMNNAME_Processing, Boolean.valueOf(Processing));
	}

	/** Get Verarbeiten.
		@return Verarbeiten	  */
	@Override
	public boolean isProcessing () 
	{
		Object oo = get_Value(COLUMNNAME_Processing);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** 
	 * SchedulerProcessType AD_Reference_ID=53381
	 * Reference name: AD_SchedulerProcessType
	 */
	public static final int SCHEDULERPROCESSTYPE_AD_Reference_ID=53381;
	/** Process = P */
	public static final String SCHEDULERPROCESSTYPE_Process = "P";
	/** Report = R */
	public static final String SCHEDULERPROCESSTYPE_Report = "R";
	/** Task = T */
	public static final String SCHEDULERPROCESSTYPE_Task = "T";
	/** Set Prozess-Art.
		@param SchedulerProcessType Prozess-Art	  */
	@Override
	public void setSchedulerProcessType (java.lang.String SchedulerProcessType)
	{

		set_Value (COLUMNNAME_SchedulerProcessType, SchedulerProcessType);
	}

	/** Get Prozess-Art.
		@return Prozess-Art	  */
	@Override
	public java.lang.String getSchedulerProcessType () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_SchedulerProcessType);
	}

	/** 
	 * ScheduleType AD_Reference_ID=318
	 * Reference name: AD_Scheduler Type
	 */
	public static final int SCHEDULETYPE_AD_Reference_ID=318;
	/** Frequency = F */
	public static final String SCHEDULETYPE_Frequency = "F";
	/** WeekDay = W */
	public static final String SCHEDULETYPE_WeekDay = "W";
	/** MonthDay = M */
	public static final String SCHEDULETYPE_MonthDay = "M";
	/** CronSchedulingPattern = C */
	public static final String SCHEDULETYPE_CronSchedulingPattern = "C";
	/** Set Planungs-Art.
		@param ScheduleType 
		Type of schedule
	  */
	@Override
	public void setScheduleType (java.lang.String ScheduleType)
	{

		set_Value (COLUMNNAME_ScheduleType, ScheduleType);
	}

	/** Get Planungs-Art.
		@return Type of schedule
	  */
	@Override
	public java.lang.String getScheduleType () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ScheduleType);
	}

	/** 
	 * Status AD_Reference_ID=540218
	 * Reference name: AD_Scheduler Status
	 */
	public static final int STATUS_AD_Reference_ID=540218;
	/** New = NEW */
	public static final String STATUS_New = "NEW";
	/** Started = STARTED */
	public static final String STATUS_Started = "STARTED";
	/** Running = RUNNING */
	public static final String STATUS_Running = "RUNNING";
	/** Stopped = STOPPED */
	public static final String STATUS_Stopped = "STOPPED";
	/** Set Status.
		@param Status 
		Status of the currently running check
	  */
	@Override
	public void setStatus (java.lang.String Status)
	{

		set_Value (COLUMNNAME_Status, Status);
	}

	/** Get Status.
		@return Status of the currently running check
	  */
	@Override
	public java.lang.String getStatus () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Status);
	}

	@Override
	public org.compiere.model.I_AD_User getSupervisor() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_Supervisor_ID, org.compiere.model.I_AD_User.class);
	}

	@Override
	public void setSupervisor(org.compiere.model.I_AD_User Supervisor)
	{
		set_ValueFromPO(COLUMNNAME_Supervisor_ID, org.compiere.model.I_AD_User.class, Supervisor);
	}

	/** Set Vorgesetzter.
		@param Supervisor_ID 
		Supervisor for this user/organization - used for escalation and approval
	  */
	@Override
	public void setSupervisor_ID (int Supervisor_ID)
	{
		if (Supervisor_ID < 1) 
			set_Value (COLUMNNAME_Supervisor_ID, null);
		else 
			set_Value (COLUMNNAME_Supervisor_ID, Integer.valueOf(Supervisor_ID));
	}

	/** Get Vorgesetzter.
		@return Supervisor for this user/organization - used for escalation and approval
	  */
	@Override
	public int getSupervisor_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Supervisor_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** 
	 * WeekDay AD_Reference_ID=167
	 * Reference name: Weekdays
	 */
	public static final int WEEKDAY_AD_Reference_ID=167;
	/** Sonntag = 7 */
	public static final String WEEKDAY_Sonntag = "7";
	/** Montag = 1 */
	public static final String WEEKDAY_Montag = "1";
	/** Dienstag = 2 */
	public static final String WEEKDAY_Dienstag = "2";
	/** Mittwoch = 3 */
	public static final String WEEKDAY_Mittwoch = "3";
	/** Donnerstag = 4 */
	public static final String WEEKDAY_Donnerstag = "4";
	/** Freitag = 5 */
	public static final String WEEKDAY_Freitag = "5";
	/** Samstag = 6 */
	public static final String WEEKDAY_Samstag = "6";
	/** Set Day of the Week.
		@param WeekDay 
		Day of the Week
	  */
	@Override
	public void setWeekDay (java.lang.String WeekDay)
	{

		set_Value (COLUMNNAME_WeekDay, WeekDay);
	}

	/** Get Day of the Week.
		@return Day of the Week
	  */
	@Override
	public java.lang.String getWeekDay () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_WeekDay);
	}
}