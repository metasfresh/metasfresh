/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for AD_System
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_AD_System extends org.compiere.model.PO implements I_AD_System, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -317855642L;

    /** Standard Constructor */
    public X_AD_System (Properties ctx, int AD_System_ID, String trxName)
    {
      super (ctx, AD_System_ID, trxName);
      /** if (AD_System_ID == 0)
        {
			setAD_System_ID (0); // 0
			setInfo (null);
			setIsAllowStatistics (false);
			setIsAutoErrorReport (true); // Y
			setIsFailOnBuildDiffer (false); // N
			setIsFailOnMissingModelValidator (true); // Y
			setName (null);
			setPassword (null);
			setReplicationType (null); // L
			setSystemStatus (null); // E
			setUserName (null);
			setVersion (null);
        } */
    }

    /** Load Constructor */
    public X_AD_System (Properties ctx, ResultSet rs, String trxName)
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

	/** Set System.
		@param AD_System_ID 
		System Definition
	  */
	@Override
	public void setAD_System_ID (int AD_System_ID)
	{
		if (AD_System_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_System_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_System_ID, Integer.valueOf(AD_System_ID));
	}

	/** Get System.
		@return System Definition
	  */
	@Override
	public int getAD_System_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_System_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Custom Prefix.
		@param CustomPrefix 
		Prefix for Custom entities
	  */
	@Override
	public void setCustomPrefix (java.lang.String CustomPrefix)
	{
		set_Value (COLUMNNAME_CustomPrefix, CustomPrefix);
	}

	/** Get Custom Prefix.
		@return Prefix for Custom entities
	  */
	@Override
	public java.lang.String getCustomPrefix () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_CustomPrefix);
	}

	/** Set DB Address.
		@param DBAddress 
		JDBC URL of the database server
	  */
	@Override
	public void setDBAddress (java.lang.String DBAddress)
	{
		set_Value (COLUMNNAME_DBAddress, DBAddress);
	}

	/** Get DB Address.
		@return JDBC URL of the database server
	  */
	@Override
	public java.lang.String getDBAddress () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DBAddress);
	}

	/** Set Database Name.
		@param DBInstance 
		Database Name
	  */
	@Override
	public void setDBInstance (java.lang.String DBInstance)
	{
		set_Value (COLUMNNAME_DBInstance, DBInstance);
	}

	/** Get Database Name.
		@return Database Name
	  */
	@Override
	public java.lang.String getDBInstance () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DBInstance);
	}

	/** Set DBVersion.
		@param DBVersion 
		version of the SQL migration scripts that were last rolled out
	  */
	@Override
	public void setDBVersion (java.lang.String DBVersion)
	{
		set_Value (COLUMNNAME_DBVersion, DBVersion);
	}

	/** Get DBVersion.
		@return version of the SQL migration scripts that were last rolled out
	  */
	@Override
	public java.lang.String getDBVersion () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DBVersion);
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

	/** Set Chiffrier-Schlüssel.
		@param EncryptionKey 
		Encryption Class used for securing data content
	  */
	@Override
	public void setEncryptionKey (java.lang.String EncryptionKey)
	{
		set_ValueNoCheck (COLUMNNAME_EncryptionKey, EncryptionKey);
	}

	/** Get Chiffrier-Schlüssel.
		@return Encryption Class used for securing data content
	  */
	@Override
	public java.lang.String getEncryptionKey () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_EncryptionKey);
	}

	/** Set ID Range End.
		@param IDRangeEnd 
		End if the ID Range used
	  */
	@Override
	public void setIDRangeEnd (java.math.BigDecimal IDRangeEnd)
	{
		set_Value (COLUMNNAME_IDRangeEnd, IDRangeEnd);
	}

	/** Get ID Range End.
		@return End if the ID Range used
	  */
	@Override
	public java.math.BigDecimal getIDRangeEnd () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_IDRangeEnd);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set ID Range Start.
		@param IDRangeStart 
		Start of the ID Range used
	  */
	@Override
	public void setIDRangeStart (java.math.BigDecimal IDRangeStart)
	{
		set_Value (COLUMNNAME_IDRangeStart, IDRangeStart);
	}

	/** Get ID Range Start.
		@return Start of the ID Range used
	  */
	@Override
	public java.math.BigDecimal getIDRangeStart () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_IDRangeStart);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Info.
		@param Info 
		Information
	  */
	@Override
	public void setInfo (java.lang.String Info)
	{
		set_ValueNoCheck (COLUMNNAME_Info, Info);
	}

	/** Get Info.
		@return Information
	  */
	@Override
	public java.lang.String getInfo () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Info);
	}

	/** Set Maintain Statistics.
		@param IsAllowStatistics 
		Maintain general statistics
	  */
	@Override
	public void setIsAllowStatistics (boolean IsAllowStatistics)
	{
		set_Value (COLUMNNAME_IsAllowStatistics, Boolean.valueOf(IsAllowStatistics));
	}

	/** Get Maintain Statistics.
		@return Maintain general statistics
	  */
	@Override
	public boolean isAllowStatistics () 
	{
		Object oo = get_Value(COLUMNNAME_IsAllowStatistics);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Error Reporting.
		@param IsAutoErrorReport 
		Automatically report Errors
	  */
	@Override
	public void setIsAutoErrorReport (boolean IsAutoErrorReport)
	{
		set_Value (COLUMNNAME_IsAutoErrorReport, Boolean.valueOf(IsAutoErrorReport));
	}

	/** Get Error Reporting.
		@return Automatically report Errors
	  */
	@Override
	public boolean isAutoErrorReport () 
	{
		Object oo = get_Value(COLUMNNAME_IsAutoErrorReport);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Fail if Build Differ.
		@param IsFailOnBuildDiffer Fail if Build Differ	  */
	@Override
	public void setIsFailOnBuildDiffer (boolean IsFailOnBuildDiffer)
	{
		set_Value (COLUMNNAME_IsFailOnBuildDiffer, Boolean.valueOf(IsFailOnBuildDiffer));
	}

	/** Get Fail if Build Differ.
		@return Fail if Build Differ	  */
	@Override
	public boolean isFailOnBuildDiffer () 
	{
		Object oo = get_Value(COLUMNNAME_IsFailOnBuildDiffer);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Fail on Missing Model Validator.
		@param IsFailOnMissingModelValidator Fail on Missing Model Validator	  */
	@Override
	public void setIsFailOnMissingModelValidator (boolean IsFailOnMissingModelValidator)
	{
		set_Value (COLUMNNAME_IsFailOnMissingModelValidator, Boolean.valueOf(IsFailOnMissingModelValidator));
	}

	/** Get Fail on Missing Model Validator.
		@return Fail on Missing Model Validator	  */
	@Override
	public boolean isFailOnMissingModelValidator () 
	{
		Object oo = get_Value(COLUMNNAME_IsFailOnMissingModelValidator);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Just Migrated.
		@param IsJustMigrated 
		Value set by Migration for post-Migation tasks.
	  */
	@Override
	public void setIsJustMigrated (boolean IsJustMigrated)
	{
		set_Value (COLUMNNAME_IsJustMigrated, Boolean.valueOf(IsJustMigrated));
	}

	/** Get Just Migrated.
		@return Value set by Migration for post-Migation tasks.
	  */
	@Override
	public boolean isJustMigrated () 
	{
		Object oo = get_Value(COLUMNNAME_IsJustMigrated);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Last Build Info.
		@param LastBuildInfo Last Build Info	  */
	@Override
	public void setLastBuildInfo (java.lang.String LastBuildInfo)
	{
		set_Value (COLUMNNAME_LastBuildInfo, LastBuildInfo);
	}

	/** Get Last Build Info.
		@return Last Build Info	  */
	@Override
	public java.lang.String getLastBuildInfo () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_LastBuildInfo);
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

	/** Set Processors.
		@param NoProcessors 
		Number of Database Processors
	  */
	@Override
	public void setNoProcessors (int NoProcessors)
	{
		set_Value (COLUMNNAME_NoProcessors, Integer.valueOf(NoProcessors));
	}

	/** Get Processors.
		@return Number of Database Processors
	  */
	@Override
	public int getNoProcessors () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_NoProcessors);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Old Name.
		@param OldName Old Name	  */
	@Override
	public void setOldName (java.lang.String OldName)
	{
		set_ValueNoCheck (COLUMNNAME_OldName, OldName);
	}

	/** Get Old Name.
		@return Old Name	  */
	@Override
	public java.lang.String getOldName () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_OldName);
	}

	/** Set Kennwort.
		@param Password 
		Password of any length (case sensitive)
	  */
	@Override
	public void setPassword (java.lang.String Password)
	{
		set_Value (COLUMNNAME_Password, Password);
	}

	/** Get Kennwort.
		@return Password of any length (case sensitive)
	  */
	@Override
	public java.lang.String getPassword () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Password);
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

	/** Set Profile.
		@param ProfileInfo 
		Information to help profiling the system for solving support issues
	  */
	@Override
	public void setProfileInfo (java.lang.String ProfileInfo)
	{
		set_ValueNoCheck (COLUMNNAME_ProfileInfo, ProfileInfo);
	}

	/** Get Profile.
		@return Information to help profiling the system for solving support issues
	  */
	@Override
	public java.lang.String getProfileInfo () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ProfileInfo);
	}

	/** Set Datensatz-ID.
		@param Record_ID 
		Direct internal record ID
	  */
	@Override
	public void setRecord_ID (int Record_ID)
	{
		if (Record_ID < 0) 
			set_Value (COLUMNNAME_Record_ID, null);
		else 
			set_Value (COLUMNNAME_Record_ID, Integer.valueOf(Record_ID));
	}

	/** Get Datensatz-ID.
		@return Direct internal record ID
	  */
	@Override
	public int getRecord_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Record_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Release No.
		@param ReleaseNo 
		Internal Release Number
	  */
	@Override
	public void setReleaseNo (java.lang.String ReleaseNo)
	{
		set_ValueNoCheck (COLUMNNAME_ReleaseNo, ReleaseNo);
	}

	/** Get Release No.
		@return Internal Release Number
	  */
	@Override
	public java.lang.String getReleaseNo () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ReleaseNo);
	}

	/** 
	 * ReplicationType AD_Reference_ID=126
	 * Reference name: AD_Table Replication Type
	 */
	public static final int REPLICATIONTYPE_AD_Reference_ID=126;
	/** Local = L */
	public static final String REPLICATIONTYPE_Local = "L";
	/** Merge = M */
	public static final String REPLICATIONTYPE_Merge = "M";
	/** Reference = R */
	public static final String REPLICATIONTYPE_Reference = "R";
	/** Set Replication Type.
		@param ReplicationType 
		Type of Data Replication
	  */
	@Override
	public void setReplicationType (java.lang.String ReplicationType)
	{

		set_Value (COLUMNNAME_ReplicationType, ReplicationType);
	}

	/** Get Replication Type.
		@return Type of Data Replication
	  */
	@Override
	public java.lang.String getReplicationType () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ReplicationType);
	}

	/** Set Statistik.
		@param StatisticsInfo 
		Information to help profiling the system for solving support issues
	  */
	@Override
	public void setStatisticsInfo (java.lang.String StatisticsInfo)
	{
		set_ValueNoCheck (COLUMNNAME_StatisticsInfo, StatisticsInfo);
	}

	/** Get Statistik.
		@return Information to help profiling the system for solving support issues
	  */
	@Override
	public java.lang.String getStatisticsInfo () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_StatisticsInfo);
	}

	/** Set Zusammenfassung.
		@param Summary 
		Textual summary of this request
	  */
	@Override
	public void setSummary (java.lang.String Summary)
	{
		set_Value (COLUMNNAME_Summary, Summary);
	}

	/** Get Zusammenfassung.
		@return Textual summary of this request
	  */
	@Override
	public java.lang.String getSummary () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Summary);
	}

	/** Set Support EMail.
		@param SupportEMail 
		EMail address to send support information and updates to
	  */
	@Override
	public void setSupportEMail (java.lang.String SupportEMail)
	{
		set_Value (COLUMNNAME_SupportEMail, SupportEMail);
	}

	/** Get Support EMail.
		@return EMail address to send support information and updates to
	  */
	@Override
	public java.lang.String getSupportEMail () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_SupportEMail);
	}

	/** Set Support Expires.
		@param SupportExpDate 
		Date when the Adempiere support expires
	  */
	@Override
	public void setSupportExpDate (java.sql.Timestamp SupportExpDate)
	{
		set_ValueNoCheck (COLUMNNAME_SupportExpDate, SupportExpDate);
	}

	/** Get Support Expires.
		@return Date when the Adempiere support expires
	  */
	@Override
	public java.sql.Timestamp getSupportExpDate () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_SupportExpDate);
	}

	/** Set Internal Users.
		@param SupportUnits 
		Number of Internal Users for Adempiere Support
	  */
	@Override
	public void setSupportUnits (int SupportUnits)
	{
		set_ValueNoCheck (COLUMNNAME_SupportUnits, Integer.valueOf(SupportUnits));
	}

	/** Get Internal Users.
		@return Number of Internal Users for Adempiere Support
	  */
	@Override
	public int getSupportUnits () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SupportUnits);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** 
	 * SystemStatus AD_Reference_ID=374
	 * Reference name: AD_System Status
	 */
	public static final int SYSTEMSTATUS_AD_Reference_ID=374;
	/** Evaluation = E */
	public static final String SYSTEMSTATUS_Evaluation = "E";
	/** Implementation = I */
	public static final String SYSTEMSTATUS_Implementation = "I";
	/** Production = P */
	public static final String SYSTEMSTATUS_Production = "P";
	/** Set System Status.
		@param SystemStatus 
		Status of the system - Support priority depends on system status
	  */
	@Override
	public void setSystemStatus (java.lang.String SystemStatus)
	{

		set_Value (COLUMNNAME_SystemStatus, SystemStatus);
	}

	/** Get System Status.
		@return Status of the system - Support priority depends on system status
	  */
	@Override
	public java.lang.String getSystemStatus () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_SystemStatus);
	}

	/** Set Registered EMail.
		@param UserName 
		Email of the responsible for the System
	  */
	@Override
	public void setUserName (java.lang.String UserName)
	{
		set_Value (COLUMNNAME_UserName, UserName);
	}

	/** Get Registered EMail.
		@return Email of the responsible for the System
	  */
	@Override
	public java.lang.String getUserName () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_UserName);
	}

	/** Set Version.
		@param Version 
		Version of the table definition
	  */
	@Override
	public void setVersion (java.lang.String Version)
	{
		set_ValueNoCheck (COLUMNNAME_Version, Version);
	}

	/** Get Version.
		@return Version of the table definition
	  */
	@Override
	public java.lang.String getVersion () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Version);
	}

	/** Set ZK WebUI URL.
		@param WebUI_URL 
		ZK WebUI root URL
	  */
	@Override
	public void setWebUI_URL (java.lang.String WebUI_URL)
	{
		set_Value (COLUMNNAME_WebUI_URL, WebUI_URL);
	}

	/** Get ZK WebUI URL.
		@return ZK WebUI root URL
	  */
	@Override
	public java.lang.String getWebUI_URL () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_WebUI_URL);
	}
}