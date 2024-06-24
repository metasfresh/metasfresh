// Generated Model - DO NOT CHANGE
package org.compiere.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for AD_Issue
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_AD_Issue extends org.compiere.model.PO implements I_AD_Issue, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 1169265162L;

    /** Standard Constructor */
    public X_AD_Issue (final Properties ctx, final int AD_Issue_ID, @Nullable final String trxName)
    {
      super (ctx, AD_Issue_ID, trxName);
    }

    /** Load Constructor */
    public X_AD_Issue (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
    {
      super (ctx, rs, trxName);
    }


	/** Load Meta Data */
	@Override
	protected org.compiere.model.POInfo initPO(final Properties ctx)
	{
		return org.compiere.model.POInfo.getPOInfo(Table_Name);
	}

	@Override
	public org.compiere.model.I_A_Asset getA_Asset()
	{
		return get_ValueAsPO(COLUMNNAME_A_Asset_ID, org.compiere.model.I_A_Asset.class);
	}

	@Override
	public void setA_Asset(final org.compiere.model.I_A_Asset A_Asset)
	{
		set_ValueFromPO(COLUMNNAME_A_Asset_ID, org.compiere.model.I_A_Asset.class, A_Asset);
	}

	@Override
	public void setA_Asset_ID (final int A_Asset_ID)
	{
		if (A_Asset_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_A_Asset_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_A_Asset_ID, A_Asset_ID);
	}

	@Override
	public int getA_Asset_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_A_Asset_ID);
	}

	@Override
	public org.compiere.model.I_AD_Form getAD_Form()
	{
		return get_ValueAsPO(COLUMNNAME_AD_Form_ID, org.compiere.model.I_AD_Form.class);
	}

	@Override
	public void setAD_Form(final org.compiere.model.I_AD_Form AD_Form)
	{
		set_ValueFromPO(COLUMNNAME_AD_Form_ID, org.compiere.model.I_AD_Form.class, AD_Form);
	}

	@Override
	public void setAD_Form_ID (final int AD_Form_ID)
	{
		if (AD_Form_ID < 1) 
			set_Value (COLUMNNAME_AD_Form_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Form_ID, AD_Form_ID);
	}

	@Override
	public int getAD_Form_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Form_ID);
	}

	@Override
	public void setAD_Issue_ID (final int AD_Issue_ID)
	{
		if (AD_Issue_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Issue_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Issue_ID, AD_Issue_ID);
	}

	@Override
	public int getAD_Issue_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Issue_ID);
	}

	@Override
	public org.compiere.model.I_AD_PInstance getAD_PInstance()
	{
		return get_ValueAsPO(COLUMNNAME_AD_PInstance_ID, org.compiere.model.I_AD_PInstance.class);
	}

	@Override
	public void setAD_PInstance(final org.compiere.model.I_AD_PInstance AD_PInstance)
	{
		set_ValueFromPO(COLUMNNAME_AD_PInstance_ID, org.compiere.model.I_AD_PInstance.class, AD_PInstance);
	}

	@Override
	public void setAD_PInstance_ID (final int AD_PInstance_ID)
	{
		if (AD_PInstance_ID < 1) 
			set_Value (COLUMNNAME_AD_PInstance_ID, null);
		else 
			set_Value (COLUMNNAME_AD_PInstance_ID, AD_PInstance_ID);
	}

	@Override
	public int getAD_PInstance_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_PInstance_ID);
	}

	@Override
	public org.compiere.model.I_AD_Process getAD_Process()
	{
		return get_ValueAsPO(COLUMNNAME_AD_Process_ID, org.compiere.model.I_AD_Process.class);
	}

	@Override
	public void setAD_Process(final org.compiere.model.I_AD_Process AD_Process)
	{
		set_ValueFromPO(COLUMNNAME_AD_Process_ID, org.compiere.model.I_AD_Process.class, AD_Process);
	}

	@Override
	public void setAD_Process_ID (final int AD_Process_ID)
	{
		if (AD_Process_ID < 1) 
			set_Value (COLUMNNAME_AD_Process_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Process_ID, AD_Process_ID);
	}

	@Override
	public int getAD_Process_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Process_ID);
	}

	@Override
	public void setAD_Table_ID (final int AD_Table_ID)
	{
		if (AD_Table_ID < 1) 
			set_Value (COLUMNNAME_AD_Table_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Table_ID, AD_Table_ID);
	}

	@Override
	public int getAD_Table_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Table_ID);
	}

	@Override
	public org.compiere.model.I_AD_Window getAD_Window()
	{
		return get_ValueAsPO(COLUMNNAME_AD_Window_ID, org.compiere.model.I_AD_Window.class);
	}

	@Override
	public void setAD_Window(final org.compiere.model.I_AD_Window AD_Window)
	{
		set_ValueFromPO(COLUMNNAME_AD_Window_ID, org.compiere.model.I_AD_Window.class, AD_Window);
	}

	@Override
	public void setAD_Window_ID (final int AD_Window_ID)
	{
		if (AD_Window_ID < 1) 
			set_Value (COLUMNNAME_AD_Window_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Window_ID, AD_Window_ID);
	}

	@Override
	public int getAD_Window_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Window_ID);
	}

	@Override
	public void setComments (final @Nullable java.lang.String Comments)
	{
		set_Value (COLUMNNAME_Comments, Comments);
	}

	@Override
	public java.lang.String getComments() 
	{
		return get_ValueAsString(COLUMNNAME_Comments);
	}

	@Override
	public void setDatabaseInfo (final @Nullable java.lang.String DatabaseInfo)
	{
		set_ValueNoCheck (COLUMNNAME_DatabaseInfo, DatabaseInfo);
	}

	@Override
	public java.lang.String getDatabaseInfo() 
	{
		return get_ValueAsString(COLUMNNAME_DatabaseInfo);
	}

	@Override
	public void setDBAddress (final @Nullable java.lang.String DBAddress)
	{
		set_ValueNoCheck (COLUMNNAME_DBAddress, DBAddress);
	}

	@Override
	public java.lang.String getDBAddress() 
	{
		return get_ValueAsString(COLUMNNAME_DBAddress);
	}

	@Override
	public void setErrorTrace (final @Nullable java.lang.String ErrorTrace)
	{
		set_Value (COLUMNNAME_ErrorTrace, ErrorTrace);
	}

	@Override
	public java.lang.String getErrorTrace() 
	{
		return get_ValueAsString(COLUMNNAME_ErrorTrace);
	}

	@Override
	public void setFrontendURL (final @Nullable java.lang.String FrontendURL)
	{
		set_Value (COLUMNNAME_FrontendURL, FrontendURL);
	}

	@Override
	public java.lang.String getFrontendURL() 
	{
		return get_ValueAsString(COLUMNNAME_FrontendURL);
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
	@Override
	public void setIsReproducible (final @Nullable java.lang.String IsReproducible)
	{
		set_Value (COLUMNNAME_IsReproducible, IsReproducible);
	}

	@Override
	public java.lang.String getIsReproducible() 
	{
		return get_ValueAsString(COLUMNNAME_IsReproducible);
	}

	/** 
	 * IssueCategory AD_Reference_ID=541151
	 * Reference name: AD_Issue_Category
	 */
	public static final int ISSUECATEGORY_AD_Reference_ID=541151;
	/** Accounting = ACCT */
	public static final String ISSUECATEGORY_Accounting = "ACCT";
	/** Other = OTHER */
	public static final String ISSUECATEGORY_Other = "OTHER";
	/** MOBILEUI = MOBILEUI */
	public static final String ISSUECATEGORY_MOBILEUI = "MOBILEUI";
	@Override
	public void setIssueCategory (final java.lang.String IssueCategory)
	{
		set_Value (COLUMNNAME_IssueCategory, IssueCategory);
	}

	@Override
	public java.lang.String getIssueCategory() 
	{
		return get_ValueAsString(COLUMNNAME_IssueCategory);
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
	@Override
	public void setIssueSource (final @Nullable java.lang.String IssueSource)
	{
		set_Value (COLUMNNAME_IssueSource, IssueSource);
	}

	@Override
	public java.lang.String getIssueSource() 
	{
		return get_ValueAsString(COLUMNNAME_IssueSource);
	}

	@Override
	public void setIssueSummary (final java.lang.String IssueSummary)
	{
		set_Value (COLUMNNAME_IssueSummary, IssueSummary);
	}

	@Override
	public java.lang.String getIssueSummary() 
	{
		return get_ValueAsString(COLUMNNAME_IssueSummary);
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
	@Override
	public void setIsVanillaSystem (final @Nullable java.lang.String IsVanillaSystem)
	{
		set_Value (COLUMNNAME_IsVanillaSystem, IsVanillaSystem);
	}

	@Override
	public java.lang.String getIsVanillaSystem() 
	{
		return get_ValueAsString(COLUMNNAME_IsVanillaSystem);
	}

	@Override
	public void setJavaInfo (final @Nullable java.lang.String JavaInfo)
	{
		set_ValueNoCheck (COLUMNNAME_JavaInfo, JavaInfo);
	}

	@Override
	public java.lang.String getJavaInfo() 
	{
		return get_ValueAsString(COLUMNNAME_JavaInfo);
	}

	@Override
	public void setLineNo (final int LineNo)
	{
		set_Value (COLUMNNAME_LineNo, LineNo);
	}

	@Override
	public int getLineNo() 
	{
		return get_ValueAsInt(COLUMNNAME_LineNo);
	}

	@Override
	public void setLocal_Host (final @Nullable java.lang.String Local_Host)
	{
		set_ValueNoCheck (COLUMNNAME_Local_Host, Local_Host);
	}

	@Override
	public java.lang.String getLocal_Host() 
	{
		return get_ValueAsString(COLUMNNAME_Local_Host);
	}

	@Override
	public void setLoggerName (final @Nullable java.lang.String LoggerName)
	{
		set_Value (COLUMNNAME_LoggerName, LoggerName);
	}

	@Override
	public java.lang.String getLoggerName() 
	{
		return get_ValueAsString(COLUMNNAME_LoggerName);
	}

	@Override
	public void setName (final java.lang.String Name)
	{
		set_ValueNoCheck (COLUMNNAME_Name, Name);
	}

	@Override
	public java.lang.String getName() 
	{
		return get_ValueAsString(COLUMNNAME_Name);
	}

	@Override
	public void setOperatingSystemInfo (final @Nullable java.lang.String OperatingSystemInfo)
	{
		set_ValueNoCheck (COLUMNNAME_OperatingSystemInfo, OperatingSystemInfo);
	}

	@Override
	public java.lang.String getOperatingSystemInfo() 
	{
		return get_ValueAsString(COLUMNNAME_OperatingSystemInfo);
	}

	@Override
	public void setProcessed (final boolean Processed)
	{
		set_ValueNoCheck (COLUMNNAME_Processed, Processed);
	}

	@Override
	public boolean isProcessed() 
	{
		return get_ValueAsBoolean(COLUMNNAME_Processed);
	}

	@Override
	public void setProcessing (final boolean Processing)
	{
		set_Value (COLUMNNAME_Processing, Processing);
	}

	@Override
	public boolean isProcessing() 
	{
		return get_ValueAsBoolean(COLUMNNAME_Processing);
	}

	@Override
	public void setProfileInfo (final @Nullable java.lang.String ProfileInfo)
	{
		set_ValueNoCheck (COLUMNNAME_ProfileInfo, ProfileInfo);
	}

	@Override
	public java.lang.String getProfileInfo() 
	{
		return get_ValueAsString(COLUMNNAME_ProfileInfo);
	}

	@Override
	public void setRecord_ID (final int Record_ID)
	{
		if (Record_ID < 0) 
			set_ValueNoCheck (COLUMNNAME_Record_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Record_ID, Record_ID);
	}

	@Override
	public int getRecord_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Record_ID);
	}

	@Override
	public void setReleaseNo (final java.lang.String ReleaseNo)
	{
		set_ValueNoCheck (COLUMNNAME_ReleaseNo, ReleaseNo);
	}

	@Override
	public java.lang.String getReleaseNo() 
	{
		return get_ValueAsString(COLUMNNAME_ReleaseNo);
	}

	@Override
	public void setReleaseTag (final @Nullable java.lang.String ReleaseTag)
	{
		set_Value (COLUMNNAME_ReleaseTag, ReleaseTag);
	}

	@Override
	public java.lang.String getReleaseTag() 
	{
		return get_ValueAsString(COLUMNNAME_ReleaseTag);
	}

	@Override
	public void setRemote_Addr (final @Nullable java.lang.String Remote_Addr)
	{
		set_ValueNoCheck (COLUMNNAME_Remote_Addr, Remote_Addr);
	}

	@Override
	public java.lang.String getRemote_Addr() 
	{
		return get_ValueAsString(COLUMNNAME_Remote_Addr);
	}

	@Override
	public void setRemote_Host (final @Nullable java.lang.String Remote_Host)
	{
		set_ValueNoCheck (COLUMNNAME_Remote_Host, Remote_Host);
	}

	@Override
	public java.lang.String getRemote_Host() 
	{
		return get_ValueAsString(COLUMNNAME_Remote_Host);
	}

	@Override
	public void setRequestDocumentNo (final @Nullable java.lang.String RequestDocumentNo)
	{
		set_ValueNoCheck (COLUMNNAME_RequestDocumentNo, RequestDocumentNo);
	}

	@Override
	public java.lang.String getRequestDocumentNo() 
	{
		return get_ValueAsString(COLUMNNAME_RequestDocumentNo);
	}

	@Override
	public void setResponseText (final @Nullable java.lang.String ResponseText)
	{
		set_ValueNoCheck (COLUMNNAME_ResponseText, ResponseText);
	}

	@Override
	public java.lang.String getResponseText() 
	{
		return get_ValueAsString(COLUMNNAME_ResponseText);
	}

	@Override
	public org.compiere.model.I_R_IssueKnown getR_IssueKnown()
	{
		return get_ValueAsPO(COLUMNNAME_R_IssueKnown_ID, org.compiere.model.I_R_IssueKnown.class);
	}

	@Override
	public void setR_IssueKnown(final org.compiere.model.I_R_IssueKnown R_IssueKnown)
	{
		set_ValueFromPO(COLUMNNAME_R_IssueKnown_ID, org.compiere.model.I_R_IssueKnown.class, R_IssueKnown);
	}

	@Override
	public void setR_IssueKnown_ID (final int R_IssueKnown_ID)
	{
		if (R_IssueKnown_ID < 1) 
			set_Value (COLUMNNAME_R_IssueKnown_ID, null);
		else 
			set_Value (COLUMNNAME_R_IssueKnown_ID, R_IssueKnown_ID);
	}

	@Override
	public int getR_IssueKnown_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_R_IssueKnown_ID);
	}

	@Override
	public org.compiere.model.I_R_IssueProject getR_IssueProject()
	{
		return get_ValueAsPO(COLUMNNAME_R_IssueProject_ID, org.compiere.model.I_R_IssueProject.class);
	}

	@Override
	public void setR_IssueProject(final org.compiere.model.I_R_IssueProject R_IssueProject)
	{
		set_ValueFromPO(COLUMNNAME_R_IssueProject_ID, org.compiere.model.I_R_IssueProject.class, R_IssueProject);
	}

	@Override
	public void setR_IssueProject_ID (final int R_IssueProject_ID)
	{
		if (R_IssueProject_ID < 1) 
			set_Value (COLUMNNAME_R_IssueProject_ID, null);
		else 
			set_Value (COLUMNNAME_R_IssueProject_ID, R_IssueProject_ID);
	}

	@Override
	public int getR_IssueProject_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_R_IssueProject_ID);
	}

	@Override
	public org.compiere.model.I_R_IssueSystem getR_IssueSystem()
	{
		return get_ValueAsPO(COLUMNNAME_R_IssueSystem_ID, org.compiere.model.I_R_IssueSystem.class);
	}

	@Override
	public void setR_IssueSystem(final org.compiere.model.I_R_IssueSystem R_IssueSystem)
	{
		set_ValueFromPO(COLUMNNAME_R_IssueSystem_ID, org.compiere.model.I_R_IssueSystem.class, R_IssueSystem);
	}

	@Override
	public void setR_IssueSystem_ID (final int R_IssueSystem_ID)
	{
		if (R_IssueSystem_ID < 1) 
			set_Value (COLUMNNAME_R_IssueSystem_ID, null);
		else 
			set_Value (COLUMNNAME_R_IssueSystem_ID, R_IssueSystem_ID);
	}

	@Override
	public int getR_IssueSystem_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_R_IssueSystem_ID);
	}

	@Override
	public org.compiere.model.I_R_IssueUser getR_IssueUser()
	{
		return get_ValueAsPO(COLUMNNAME_R_IssueUser_ID, org.compiere.model.I_R_IssueUser.class);
	}

	@Override
	public void setR_IssueUser(final org.compiere.model.I_R_IssueUser R_IssueUser)
	{
		set_ValueFromPO(COLUMNNAME_R_IssueUser_ID, org.compiere.model.I_R_IssueUser.class, R_IssueUser);
	}

	@Override
	public void setR_IssueUser_ID (final int R_IssueUser_ID)
	{
		if (R_IssueUser_ID < 1) 
			set_Value (COLUMNNAME_R_IssueUser_ID, null);
		else 
			set_Value (COLUMNNAME_R_IssueUser_ID, R_IssueUser_ID);
	}

	@Override
	public int getR_IssueUser_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_R_IssueUser_ID);
	}

	@Override
	public org.compiere.model.I_R_Request getR_Request()
	{
		return get_ValueAsPO(COLUMNNAME_R_Request_ID, org.compiere.model.I_R_Request.class);
	}

	@Override
	public void setR_Request(final org.compiere.model.I_R_Request R_Request)
	{
		set_ValueFromPO(COLUMNNAME_R_Request_ID, org.compiere.model.I_R_Request.class, R_Request);
	}

	@Override
	public void setR_Request_ID (final int R_Request_ID)
	{
		if (R_Request_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_R_Request_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_R_Request_ID, R_Request_ID);
	}

	@Override
	public int getR_Request_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_R_Request_ID);
	}

	@Override
	public void setSourceClassName (final @Nullable java.lang.String SourceClassName)
	{
		set_Value (COLUMNNAME_SourceClassName, SourceClassName);
	}

	@Override
	public java.lang.String getSourceClassName() 
	{
		return get_ValueAsString(COLUMNNAME_SourceClassName);
	}

	@Override
	public void setSourceMethodName (final @Nullable java.lang.String SourceMethodName)
	{
		set_Value (COLUMNNAME_SourceMethodName, SourceMethodName);
	}

	@Override
	public java.lang.String getSourceMethodName() 
	{
		return get_ValueAsString(COLUMNNAME_SourceMethodName);
	}

	@Override
	public void setStackTrace (final @Nullable java.lang.String StackTrace)
	{
		set_Value (COLUMNNAME_StackTrace, StackTrace);
	}

	@Override
	public java.lang.String getStackTrace() 
	{
		return get_ValueAsString(COLUMNNAME_StackTrace);
	}

	@Override
	public void setStatisticsInfo (final @Nullable java.lang.String StatisticsInfo)
	{
		set_ValueNoCheck (COLUMNNAME_StatisticsInfo, StatisticsInfo);
	}

	@Override
	public java.lang.String getStatisticsInfo() 
	{
		return get_ValueAsString(COLUMNNAME_StatisticsInfo);
	}

	@Override
	public void setSupportEMail (final @Nullable java.lang.String SupportEMail)
	{
		set_Value (COLUMNNAME_SupportEMail, SupportEMail);
	}

	@Override
	public java.lang.String getSupportEMail() 
	{
		return get_ValueAsString(COLUMNNAME_SupportEMail);
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
	@Override
	public void setSystemStatus (final java.lang.String SystemStatus)
	{
		set_Value (COLUMNNAME_SystemStatus, SystemStatus);
	}

	@Override
	public java.lang.String getSystemStatus() 
	{
		return get_ValueAsString(COLUMNNAME_SystemStatus);
	}

	@Override
	public void setUserAgent (final @Nullable java.lang.String UserAgent)
	{
		set_Value (COLUMNNAME_UserAgent, UserAgent);
	}

	@Override
	public java.lang.String getUserAgent() 
	{
		return get_ValueAsString(COLUMNNAME_UserAgent);
	}

	@Override
	public void setUserName (final java.lang.String UserName)
	{
		set_ValueNoCheck (COLUMNNAME_UserName, UserName);
	}

	@Override
	public java.lang.String getUserName() 
	{
		return get_ValueAsString(COLUMNNAME_UserName);
	}

	@Override
	public void setVersion (final java.lang.String Version)
	{
		set_ValueNoCheck (COLUMNNAME_Version, Version);
	}

	@Override
	public java.lang.String getVersion() 
	{
		return get_ValueAsString(COLUMNNAME_Version);
	}
}