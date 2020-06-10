/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for AD_Issue
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_AD_Issue extends org.compiere.model.PO implements I_AD_Issue, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -311114464L;

    /** Standard Constructor */
    public X_AD_Issue (Properties ctx, int AD_Issue_ID, String trxName)
    {
      super (ctx, AD_Issue_ID, trxName);
      /** if (AD_Issue_ID == 0)
        {
			setAD_Issue_ID (0);
			setIssueSummary (null);
			setName (null); // .
			setProcessed (false); // N
			setReleaseNo (null); // .
			setSystemStatus (null); // E
			setUserName (null); // .
			setVersion (null); // .
        } */
    }

    /** Load Constructor */
    public X_AD_Issue (Properties ctx, ResultSet rs, String trxName)
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
	public org.compiere.model.I_A_Asset getA_Asset()
	{
		return get_ValueAsPO(COLUMNNAME_A_Asset_ID, org.compiere.model.I_A_Asset.class);
	}

	@Override
	public void setA_Asset(org.compiere.model.I_A_Asset A_Asset)
	{
		set_ValueFromPO(COLUMNNAME_A_Asset_ID, org.compiere.model.I_A_Asset.class, A_Asset);
	}

	/** Set Asset.
		@param A_Asset_ID 
		Asset used internally or by customers
	  */
	@Override
	public void setA_Asset_ID (int A_Asset_ID)
	{
		if (A_Asset_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_A_Asset_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_A_Asset_ID, Integer.valueOf(A_Asset_ID));
	}

	/** Get Asset.
		@return Asset used internally or by customers
	  */
	@Override
	public int getA_Asset_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_A_Asset_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_AD_Form getAD_Form()
	{
		return get_ValueAsPO(COLUMNNAME_AD_Form_ID, org.compiere.model.I_AD_Form.class);
	}

	@Override
	public void setAD_Form(org.compiere.model.I_AD_Form AD_Form)
	{
		set_ValueFromPO(COLUMNNAME_AD_Form_ID, org.compiere.model.I_AD_Form.class, AD_Form);
	}

	/** Set Special Form.
		@param AD_Form_ID 
		Special Form
	  */
	@Override
	public void setAD_Form_ID (int AD_Form_ID)
	{
		if (AD_Form_ID < 1) 
			set_Value (COLUMNNAME_AD_Form_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Form_ID, Integer.valueOf(AD_Form_ID));
	}

	/** Get Special Form.
		@return Special Form
	  */
	@Override
	public int getAD_Form_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Form_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set System-Problem.
		@param AD_Issue_ID 
		Automatically created or manually entered System Issue
	  */
	@Override
	public void setAD_Issue_ID (int AD_Issue_ID)
	{
		if (AD_Issue_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Issue_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Issue_ID, Integer.valueOf(AD_Issue_ID));
	}

	/** Get System-Problem.
		@return Automatically created or manually entered System Issue
	  */
	@Override
	public int getAD_Issue_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Issue_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_AD_PInstance getAD_PInstance()
	{
		return get_ValueAsPO(COLUMNNAME_AD_PInstance_ID, org.compiere.model.I_AD_PInstance.class);
	}

	@Override
	public void setAD_PInstance(org.compiere.model.I_AD_PInstance AD_PInstance)
	{
		set_ValueFromPO(COLUMNNAME_AD_PInstance_ID, org.compiere.model.I_AD_PInstance.class, AD_PInstance);
	}

	/** Set Prozess-Instanz.
		@param AD_PInstance_ID 
		Instanz eines Prozesses
	  */
	@Override
	public void setAD_PInstance_ID (int AD_PInstance_ID)
	{
		if (AD_PInstance_ID < 1) 
			set_Value (COLUMNNAME_AD_PInstance_ID, null);
		else 
			set_Value (COLUMNNAME_AD_PInstance_ID, Integer.valueOf(AD_PInstance_ID));
	}

	/** Get Prozess-Instanz.
		@return Instanz eines Prozesses
	  */
	@Override
	public int getAD_PInstance_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_PInstance_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_AD_Process getAD_Process()
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
			set_Value (COLUMNNAME_AD_Process_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Process_ID, Integer.valueOf(AD_Process_ID));
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
	public org.compiere.model.I_AD_Window getAD_Window()
	{
		return get_ValueAsPO(COLUMNNAME_AD_Window_ID, org.compiere.model.I_AD_Window.class);
	}

	@Override
	public void setAD_Window(org.compiere.model.I_AD_Window AD_Window)
	{
		set_ValueFromPO(COLUMNNAME_AD_Window_ID, org.compiere.model.I_AD_Window.class, AD_Window);
	}

	/** Set Fenster.
		@param AD_Window_ID 
		Data entry or display window
	  */
	@Override
	public void setAD_Window_ID (int AD_Window_ID)
	{
		if (AD_Window_ID < 1) 
			set_Value (COLUMNNAME_AD_Window_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Window_ID, Integer.valueOf(AD_Window_ID));
	}

	/** Get Fenster.
		@return Data entry or display window
	  */
	@Override
	public int getAD_Window_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Window_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Bemerkungen.
		@param Comments 
		Comments or additional information
	  */
	@Override
	public void setComments (java.lang.String Comments)
	{
		set_Value (COLUMNNAME_Comments, Comments);
	}

	/** Get Bemerkungen.
		@return Comments or additional information
	  */
	@Override
	public java.lang.String getComments () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Comments);
	}

	/** Set Datenbank.
		@param DatabaseInfo 
		Database Information
	  */
	@Override
	public void setDatabaseInfo (java.lang.String DatabaseInfo)
	{
		set_ValueNoCheck (COLUMNNAME_DatabaseInfo, DatabaseInfo);
	}

	/** Get Datenbank.
		@return Database Information
	  */
	@Override
	public java.lang.String getDatabaseInfo () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DatabaseInfo);
	}

	/** Set DB Address.
		@param DBAddress 
		JDBC URL of the database server
	  */
	@Override
	public void setDBAddress (java.lang.String DBAddress)
	{
		set_ValueNoCheck (COLUMNNAME_DBAddress, DBAddress);
	}

	/** Get DB Address.
		@return JDBC URL of the database server
	  */
	@Override
	public java.lang.String getDBAddress () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DBAddress);
	}

	/** Set Error Trace.
		@param ErrorTrace 
		System Error Trace
	  */
	@Override
	public void setErrorTrace (java.lang.String ErrorTrace)
	{
		set_Value (COLUMNNAME_ErrorTrace, ErrorTrace);
	}

	/** Get Error Trace.
		@return System Error Trace
	  */
	@Override
	public java.lang.String getErrorTrace () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ErrorTrace);
	}

	/** 
	 * IsReproducible AD_Reference_ID=319
	 * Reference name: _YesNo
	 */
	public static final int ISREPRODUCIBLE_AD_Reference_ID=319;
	/** Yes = Y */
	public static final String ISREPRODUCIBLE_Yes = "Y";
	/** No = N */
	public static final String ISREPRODUCIBLE_No = "N";
	/** Set Reproducible.
		@param IsReproducible 
		Problem can re reproduced in Gardenworld
	  */
	@Override
	public void setIsReproducible (java.lang.String IsReproducible)
	{

		set_Value (COLUMNNAME_IsReproducible, IsReproducible);
	}

	/** Get Reproducible.
		@return Problem can re reproduced in Gardenworld
	  */
	@Override
	public java.lang.String getIsReproducible () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_IsReproducible);
	}

	/** 
	 * IssueSource AD_Reference_ID=104
	 * Reference name: AD_Menu Action
	 */
	public static final int ISSUESOURCE_AD_Reference_ID=104;
	/** Window = W */
	public static final String ISSUESOURCE_Window = "W";
	/** Task = T */
	public static final String ISSUESOURCE_Task = "T";
	/** WorkFlow = F */
	public static final String ISSUESOURCE_WorkFlow = "F";
	/** Process = P */
	public static final String ISSUESOURCE_Process = "P";
	/** Report = R */
	public static final String ISSUESOURCE_Report = "R";
	/** Form = X */
	public static final String ISSUESOURCE_Form = "X";
	/** Workbench = B */
	public static final String ISSUESOURCE_Workbench = "B";
	/** Board = K */
	public static final String ISSUESOURCE_Board = "K";
	/** Set Source.
		@param IssueSource 
		Issue Source
	  */
	@Override
	public void setIssueSource (java.lang.String IssueSource)
	{

		set_Value (COLUMNNAME_IssueSource, IssueSource);
	}

	/** Get Source.
		@return Issue Source
	  */
	@Override
	public java.lang.String getIssueSource () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_IssueSource);
	}

	/** Set Issue Summary.
		@param IssueSummary 
		Issue Summary
	  */
	@Override
	public void setIssueSummary (java.lang.String IssueSummary)
	{
		set_Value (COLUMNNAME_IssueSummary, IssueSummary);
	}

	/** Get Issue Summary.
		@return Issue Summary
	  */
	@Override
	public java.lang.String getIssueSummary () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_IssueSummary);
	}

	/** 
	 * IsVanillaSystem AD_Reference_ID=319
	 * Reference name: _YesNo
	 */
	public static final int ISVANILLASYSTEM_AD_Reference_ID=319;
	/** Yes = Y */
	public static final String ISVANILLASYSTEM_Yes = "Y";
	/** No = N */
	public static final String ISVANILLASYSTEM_No = "N";
	/** Set Vanilla System.
		@param IsVanillaSystem 
		The system was NOT compiled from Source - i.e. standard distribution
	  */
	@Override
	public void setIsVanillaSystem (java.lang.String IsVanillaSystem)
	{

		set_Value (COLUMNNAME_IsVanillaSystem, IsVanillaSystem);
	}

	/** Get Vanilla System.
		@return The system was NOT compiled from Source - i.e. standard distribution
	  */
	@Override
	public java.lang.String getIsVanillaSystem () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_IsVanillaSystem);
	}

	/** Set Java Info.
		@param JavaInfo 
		Java Version Info
	  */
	@Override
	public void setJavaInfo (java.lang.String JavaInfo)
	{
		set_ValueNoCheck (COLUMNNAME_JavaInfo, JavaInfo);
	}

	/** Get Java Info.
		@return Java Version Info
	  */
	@Override
	public java.lang.String getJavaInfo () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_JavaInfo);
	}

	/** Set Position.
		@param LineNo 
		Line No
	  */
	@Override
	public void setLineNo (int LineNo)
	{
		set_Value (COLUMNNAME_LineNo, Integer.valueOf(LineNo));
	}

	/** Get Position.
		@return Line No
	  */
	@Override
	public int getLineNo () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_LineNo);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Local Host.
		@param Local_Host 
		Local Host Info
	  */
	@Override
	public void setLocal_Host (java.lang.String Local_Host)
	{
		set_ValueNoCheck (COLUMNNAME_Local_Host, Local_Host);
	}

	/** Get Local Host.
		@return Local Host Info
	  */
	@Override
	public java.lang.String getLocal_Host () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Local_Host);
	}

	/** Set Logger.
		@param LoggerName 
		Logger Name
	  */
	@Override
	public void setLoggerName (java.lang.String LoggerName)
	{
		set_Value (COLUMNNAME_LoggerName, LoggerName);
	}

	/** Get Logger.
		@return Logger Name
	  */
	@Override
	public java.lang.String getLoggerName () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_LoggerName);
	}

	/** Set Name.
		@param Name Name	  */
	@Override
	public void setName (java.lang.String Name)
	{
		set_ValueNoCheck (COLUMNNAME_Name, Name);
	}

	/** Get Name.
		@return Name	  */
	@Override
	public java.lang.String getName () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Name);
	}

	/** Set Operating System.
		@param OperatingSystemInfo 
		Operating System Info
	  */
	@Override
	public void setOperatingSystemInfo (java.lang.String OperatingSystemInfo)
	{
		set_ValueNoCheck (COLUMNNAME_OperatingSystemInfo, OperatingSystemInfo);
	}

	/** Get Operating System.
		@return Operating System Info
	  */
	@Override
	public java.lang.String getOperatingSystemInfo () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_OperatingSystemInfo);
	}

	/** Set Verarbeitet.
		@param Processed 
		Checkbox sagt aus, ob der Datensatz verarbeitet wurde. 
	  */
	@Override
	public void setProcessed (boolean Processed)
	{
		set_ValueNoCheck (COLUMNNAME_Processed, Boolean.valueOf(Processed));
	}

	/** Get Verarbeitet.
		@return Checkbox sagt aus, ob der Datensatz verarbeitet wurde. 
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

	@Override
	public org.compiere.model.I_R_IssueKnown getR_IssueKnown()
	{
		return get_ValueAsPO(COLUMNNAME_R_IssueKnown_ID, org.compiere.model.I_R_IssueKnown.class);
	}

	@Override
	public void setR_IssueKnown(org.compiere.model.I_R_IssueKnown R_IssueKnown)
	{
		set_ValueFromPO(COLUMNNAME_R_IssueKnown_ID, org.compiere.model.I_R_IssueKnown.class, R_IssueKnown);
	}

	/** Set Bekanntes Problem.
		@param R_IssueKnown_ID 
		Known Issue
	  */
	@Override
	public void setR_IssueKnown_ID (int R_IssueKnown_ID)
	{
		if (R_IssueKnown_ID < 1) 
			set_Value (COLUMNNAME_R_IssueKnown_ID, null);
		else 
			set_Value (COLUMNNAME_R_IssueKnown_ID, Integer.valueOf(R_IssueKnown_ID));
	}

	/** Get Bekanntes Problem.
		@return Known Issue
	  */
	@Override
	public int getR_IssueKnown_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_R_IssueKnown_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_R_IssueProject getR_IssueProject()
	{
		return get_ValueAsPO(COLUMNNAME_R_IssueProject_ID, org.compiere.model.I_R_IssueProject.class);
	}

	@Override
	public void setR_IssueProject(org.compiere.model.I_R_IssueProject R_IssueProject)
	{
		set_ValueFromPO(COLUMNNAME_R_IssueProject_ID, org.compiere.model.I_R_IssueProject.class, R_IssueProject);
	}

	/** Set Projekt-Problem.
		@param R_IssueProject_ID 
		Implementation Projects
	  */
	@Override
	public void setR_IssueProject_ID (int R_IssueProject_ID)
	{
		if (R_IssueProject_ID < 1) 
			set_Value (COLUMNNAME_R_IssueProject_ID, null);
		else 
			set_Value (COLUMNNAME_R_IssueProject_ID, Integer.valueOf(R_IssueProject_ID));
	}

	/** Get Projekt-Problem.
		@return Implementation Projects
	  */
	@Override
	public int getR_IssueProject_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_R_IssueProject_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_R_IssueSystem getR_IssueSystem()
	{
		return get_ValueAsPO(COLUMNNAME_R_IssueSystem_ID, org.compiere.model.I_R_IssueSystem.class);
	}

	@Override
	public void setR_IssueSystem(org.compiere.model.I_R_IssueSystem R_IssueSystem)
	{
		set_ValueFromPO(COLUMNNAME_R_IssueSystem_ID, org.compiere.model.I_R_IssueSystem.class, R_IssueSystem);
	}

	/** Set System-Problem.
		@param R_IssueSystem_ID 
		System creating the issue
	  */
	@Override
	public void setR_IssueSystem_ID (int R_IssueSystem_ID)
	{
		if (R_IssueSystem_ID < 1) 
			set_Value (COLUMNNAME_R_IssueSystem_ID, null);
		else 
			set_Value (COLUMNNAME_R_IssueSystem_ID, Integer.valueOf(R_IssueSystem_ID));
	}

	/** Get System-Problem.
		@return System creating the issue
	  */
	@Override
	public int getR_IssueSystem_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_R_IssueSystem_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_R_IssueUser getR_IssueUser()
	{
		return get_ValueAsPO(COLUMNNAME_R_IssueUser_ID, org.compiere.model.I_R_IssueUser.class);
	}

	@Override
	public void setR_IssueUser(org.compiere.model.I_R_IssueUser R_IssueUser)
	{
		set_ValueFromPO(COLUMNNAME_R_IssueUser_ID, org.compiere.model.I_R_IssueUser.class, R_IssueUser);
	}

	/** Set IssueUser.
		@param R_IssueUser_ID 
		User who reported issues
	  */
	@Override
	public void setR_IssueUser_ID (int R_IssueUser_ID)
	{
		if (R_IssueUser_ID < 1) 
			set_Value (COLUMNNAME_R_IssueUser_ID, null);
		else 
			set_Value (COLUMNNAME_R_IssueUser_ID, Integer.valueOf(R_IssueUser_ID));
	}

	/** Get IssueUser.
		@return User who reported issues
	  */
	@Override
	public int getR_IssueUser_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_R_IssueUser_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_R_Request getR_Request()
	{
		return get_ValueAsPO(COLUMNNAME_R_Request_ID, org.compiere.model.I_R_Request.class);
	}

	@Override
	public void setR_Request(org.compiere.model.I_R_Request R_Request)
	{
		set_ValueFromPO(COLUMNNAME_R_Request_ID, org.compiere.model.I_R_Request.class, R_Request);
	}

	/** Set Tätigkeit.
		@param R_Request_ID 
		Request from a Business Partner or Prospect
	  */
	@Override
	public void setR_Request_ID (int R_Request_ID)
	{
		if (R_Request_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_R_Request_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_R_Request_ID, Integer.valueOf(R_Request_ID));
	}

	/** Get Tätigkeit.
		@return Request from a Business Partner or Prospect
	  */
	@Override
	public int getR_Request_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_R_Request_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Datensatz-ID.
		@param Record_ID 
		Direct internal record ID
	  */
	@Override
	public void setRecord_ID (int Record_ID)
	{
		if (Record_ID < 0) 
			set_ValueNoCheck (COLUMNNAME_Record_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Record_ID, Integer.valueOf(Record_ID));
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

	/** Set Release Tag.
		@param ReleaseTag 
		Release Tag
	  */
	@Override
	public void setReleaseTag (java.lang.String ReleaseTag)
	{
		set_Value (COLUMNNAME_ReleaseTag, ReleaseTag);
	}

	/** Get Release Tag.
		@return Release Tag
	  */
	@Override
	public java.lang.String getReleaseTag () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ReleaseTag);
	}

	/** Set Remote Addr.
		@param Remote_Addr 
		Remote Address
	  */
	@Override
	public void setRemote_Addr (java.lang.String Remote_Addr)
	{
		set_ValueNoCheck (COLUMNNAME_Remote_Addr, Remote_Addr);
	}

	/** Get Remote Addr.
		@return Remote Address
	  */
	@Override
	public java.lang.String getRemote_Addr () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Remote_Addr);
	}

	/** Set Remote Host.
		@param Remote_Host 
		Remote host Info
	  */
	@Override
	public void setRemote_Host (java.lang.String Remote_Host)
	{
		set_ValueNoCheck (COLUMNNAME_Remote_Host, Remote_Host);
	}

	/** Get Remote Host.
		@return Remote host Info
	  */
	@Override
	public java.lang.String getRemote_Host () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Remote_Host);
	}

	/** Set Request Document No.
		@param RequestDocumentNo 
		Adempiere Request Document No
	  */
	@Override
	public void setRequestDocumentNo (java.lang.String RequestDocumentNo)
	{
		set_ValueNoCheck (COLUMNNAME_RequestDocumentNo, RequestDocumentNo);
	}

	/** Get Request Document No.
		@return Adempiere Request Document No
	  */
	@Override
	public java.lang.String getRequestDocumentNo () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_RequestDocumentNo);
	}

	/** Set Antwort-Text.
		@param ResponseText 
		Request Response Text
	  */
	@Override
	public void setResponseText (java.lang.String ResponseText)
	{
		set_ValueNoCheck (COLUMNNAME_ResponseText, ResponseText);
	}

	/** Get Antwort-Text.
		@return Request Response Text
	  */
	@Override
	public java.lang.String getResponseText () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ResponseText);
	}

	/** Set Source Class.
		@param SourceClassName 
		Source Class Name
	  */
	@Override
	public void setSourceClassName (java.lang.String SourceClassName)
	{
		set_Value (COLUMNNAME_SourceClassName, SourceClassName);
	}

	/** Get Source Class.
		@return Source Class Name
	  */
	@Override
	public java.lang.String getSourceClassName () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_SourceClassName);
	}

	/** Set Source Method.
		@param SourceMethodName 
		Source Method Name
	  */
	@Override
	public void setSourceMethodName (java.lang.String SourceMethodName)
	{
		set_Value (COLUMNNAME_SourceMethodName, SourceMethodName);
	}

	/** Get Source Method.
		@return Source Method Name
	  */
	@Override
	public java.lang.String getSourceMethodName () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_SourceMethodName);
	}

	/** Set Stack Trace.
		@param StackTrace 
		System Log Trace
	  */
	@Override
	public void setStackTrace (java.lang.String StackTrace)
	{
		set_Value (COLUMNNAME_StackTrace, StackTrace);
	}

	/** Get Stack Trace.
		@return System Log Trace
	  */
	@Override
	public java.lang.String getStackTrace () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_StackTrace);
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

	/** Set Nutzer-ID/Login.
		@param UserName Nutzer-ID/Login	  */
	@Override
	public void setUserName (java.lang.String UserName)
	{
		set_ValueNoCheck (COLUMNNAME_UserName, UserName);
	}

	/** Get Nutzer-ID/Login.
		@return Nutzer-ID/Login	  */
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
}