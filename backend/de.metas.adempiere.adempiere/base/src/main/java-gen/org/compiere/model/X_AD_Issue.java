/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for AD_Issue
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("javadoc")
public class X_AD_Issue extends org.compiere.model.PO implements I_AD_Issue, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1132870364L;

    /** Standard Constructor */
    public X_AD_Issue (Properties ctx, int AD_Issue_ID, String trxName)
    {
      super (ctx, AD_Issue_ID, trxName);
    }

    /** Load Constructor */
    public X_AD_Issue (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }


	/** Load Meta Data */
	@Override
	protected org.compiere.model.POInfo initPO(Properties ctx)
	{
		return org.compiere.model.POInfo.getPOInfo(Table_Name);
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

	@Override
	public void setA_Asset_ID (int A_Asset_ID)
	{
		if (A_Asset_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_A_Asset_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_A_Asset_ID, Integer.valueOf(A_Asset_ID));
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
	public void setAD_Form(org.compiere.model.I_AD_Form AD_Form)
	{
		set_ValueFromPO(COLUMNNAME_AD_Form_ID, org.compiere.model.I_AD_Form.class, AD_Form);
	}

	@Override
	public void setAD_Form_ID (int AD_Form_ID)
	{
		if (AD_Form_ID < 1) 
			set_Value (COLUMNNAME_AD_Form_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Form_ID, Integer.valueOf(AD_Form_ID));
	}

	@Override
	public int getAD_Form_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Form_ID);
	}

	@Override
	public void setAD_Issue_ID (int AD_Issue_ID)
	{
		if (AD_Issue_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Issue_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Issue_ID, Integer.valueOf(AD_Issue_ID));
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
	public void setAD_PInstance(org.compiere.model.I_AD_PInstance AD_PInstance)
	{
		set_ValueFromPO(COLUMNNAME_AD_PInstance_ID, org.compiere.model.I_AD_PInstance.class, AD_PInstance);
	}

	@Override
	public void setAD_PInstance_ID (int AD_PInstance_ID)
	{
		if (AD_PInstance_ID < 1) 
			set_Value (COLUMNNAME_AD_PInstance_ID, null);
		else 
			set_Value (COLUMNNAME_AD_PInstance_ID, Integer.valueOf(AD_PInstance_ID));
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
	public void setAD_Process(org.compiere.model.I_AD_Process AD_Process)
	{
		set_ValueFromPO(COLUMNNAME_AD_Process_ID, org.compiere.model.I_AD_Process.class, AD_Process);
	}

	@Override
	public void setAD_Process_ID (int AD_Process_ID)
	{
		if (AD_Process_ID < 1) 
			set_Value (COLUMNNAME_AD_Process_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Process_ID, Integer.valueOf(AD_Process_ID));
	}

	@Override
	public int getAD_Process_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Process_ID);
	}

	@Override
	public void setAD_Table_ID (int AD_Table_ID)
	{
		if (AD_Table_ID < 1) 
			set_Value (COLUMNNAME_AD_Table_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Table_ID, Integer.valueOf(AD_Table_ID));
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
	public void setAD_Window(org.compiere.model.I_AD_Window AD_Window)
	{
		set_ValueFromPO(COLUMNNAME_AD_Window_ID, org.compiere.model.I_AD_Window.class, AD_Window);
	}

	@Override
	public void setAD_Window_ID (int AD_Window_ID)
	{
		if (AD_Window_ID < 1) 
			set_Value (COLUMNNAME_AD_Window_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Window_ID, Integer.valueOf(AD_Window_ID));
	}

	@Override
	public int getAD_Window_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Window_ID);
	}

	@Override
	public void setComments (java.lang.String Comments)
	{
		set_Value (COLUMNNAME_Comments, Comments);
	}

	@Override
	public java.lang.String getComments() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Comments);
	}

	@Override
	public void setDatabaseInfo (java.lang.String DatabaseInfo)
	{
		set_ValueNoCheck (COLUMNNAME_DatabaseInfo, DatabaseInfo);
	}

	@Override
	public java.lang.String getDatabaseInfo() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DatabaseInfo);
	}

	@Override
	public void setDBAddress (java.lang.String DBAddress)
	{
		set_ValueNoCheck (COLUMNNAME_DBAddress, DBAddress);
	}

	@Override
	public java.lang.String getDBAddress() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DBAddress);
	}

	@Override
	public void setErrorTrace (java.lang.String ErrorTrace)
	{
		set_Value (COLUMNNAME_ErrorTrace, ErrorTrace);
	}

	@Override
	public java.lang.String getErrorTrace() 
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
	@Override
	public void setIsReproducible (java.lang.String IsReproducible)
	{

		set_Value (COLUMNNAME_IsReproducible, IsReproducible);
	}

	@Override
	public java.lang.String getIsReproducible() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_IsReproducible);
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
	@Override
	public void setIssueCategory (java.lang.String IssueCategory)
	{

		set_Value (COLUMNNAME_IssueCategory, IssueCategory);
	}

	@Override
	public java.lang.String getIssueCategory() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_IssueCategory);
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
	public void setIssueSource (java.lang.String IssueSource)
	{

		set_Value (COLUMNNAME_IssueSource, IssueSource);
	}

	@Override
	public java.lang.String getIssueSource() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_IssueSource);
	}

	@Override
	public void setIssueSummary (java.lang.String IssueSummary)
	{
		set_Value (COLUMNNAME_IssueSummary, IssueSummary);
	}

	@Override
	public java.lang.String getIssueSummary() 
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
	@Override
	public void setIsVanillaSystem (java.lang.String IsVanillaSystem)
	{

		set_Value (COLUMNNAME_IsVanillaSystem, IsVanillaSystem);
	}

	@Override
	public java.lang.String getIsVanillaSystem() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_IsVanillaSystem);
	}

	@Override
	public void setJavaInfo (java.lang.String JavaInfo)
	{
		set_ValueNoCheck (COLUMNNAME_JavaInfo, JavaInfo);
	}

	@Override
	public java.lang.String getJavaInfo() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_JavaInfo);
	}

	@Override
	public void setLineNo (int LineNo)
	{
		set_Value (COLUMNNAME_LineNo, Integer.valueOf(LineNo));
	}

	@Override
	public int getLineNo() 
	{
		return get_ValueAsInt(COLUMNNAME_LineNo);
	}

	@Override
	public void setLocal_Host (java.lang.String Local_Host)
	{
		set_ValueNoCheck (COLUMNNAME_Local_Host, Local_Host);
	}

	@Override
	public java.lang.String getLocal_Host() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Local_Host);
	}

	@Override
	public void setLoggerName (java.lang.String LoggerName)
	{
		set_Value (COLUMNNAME_LoggerName, LoggerName);
	}

	@Override
	public java.lang.String getLoggerName() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_LoggerName);
	}

	@Override
	public void setName (java.lang.String Name)
	{
		set_ValueNoCheck (COLUMNNAME_Name, Name);
	}

	@Override
	public java.lang.String getName() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Name);
	}

	@Override
	public void setOperatingSystemInfo (java.lang.String OperatingSystemInfo)
	{
		set_ValueNoCheck (COLUMNNAME_OperatingSystemInfo, OperatingSystemInfo);
	}

	@Override
	public java.lang.String getOperatingSystemInfo() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_OperatingSystemInfo);
	}

	@Override
	public void setProcessed (boolean Processed)
	{
		set_ValueNoCheck (COLUMNNAME_Processed, Boolean.valueOf(Processed));
	}

	@Override
	public boolean isProcessed() 
	{
		return get_ValueAsBoolean(COLUMNNAME_Processed);
	}

	@Override
	public void setProcessing (boolean Processing)
	{
		set_Value (COLUMNNAME_Processing, Boolean.valueOf(Processing));
	}

	@Override
	public boolean isProcessing() 
	{
		return get_ValueAsBoolean(COLUMNNAME_Processing);
	}

	@Override
	public void setProfileInfo (java.lang.String ProfileInfo)
	{
		set_ValueNoCheck (COLUMNNAME_ProfileInfo, ProfileInfo);
	}

	@Override
	public java.lang.String getProfileInfo() 
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

	@Override
	public void setR_IssueKnown_ID (int R_IssueKnown_ID)
	{
		if (R_IssueKnown_ID < 1) 
			set_Value (COLUMNNAME_R_IssueKnown_ID, null);
		else 
			set_Value (COLUMNNAME_R_IssueKnown_ID, Integer.valueOf(R_IssueKnown_ID));
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
	public void setR_IssueProject(org.compiere.model.I_R_IssueProject R_IssueProject)
	{
		set_ValueFromPO(COLUMNNAME_R_IssueProject_ID, org.compiere.model.I_R_IssueProject.class, R_IssueProject);
	}

	@Override
	public void setR_IssueProject_ID (int R_IssueProject_ID)
	{
		if (R_IssueProject_ID < 1) 
			set_Value (COLUMNNAME_R_IssueProject_ID, null);
		else 
			set_Value (COLUMNNAME_R_IssueProject_ID, Integer.valueOf(R_IssueProject_ID));
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
	public void setR_IssueSystem(org.compiere.model.I_R_IssueSystem R_IssueSystem)
	{
		set_ValueFromPO(COLUMNNAME_R_IssueSystem_ID, org.compiere.model.I_R_IssueSystem.class, R_IssueSystem);
	}

	@Override
	public void setR_IssueSystem_ID (int R_IssueSystem_ID)
	{
		if (R_IssueSystem_ID < 1) 
			set_Value (COLUMNNAME_R_IssueSystem_ID, null);
		else 
			set_Value (COLUMNNAME_R_IssueSystem_ID, Integer.valueOf(R_IssueSystem_ID));
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
	public void setR_IssueUser(org.compiere.model.I_R_IssueUser R_IssueUser)
	{
		set_ValueFromPO(COLUMNNAME_R_IssueUser_ID, org.compiere.model.I_R_IssueUser.class, R_IssueUser);
	}

	@Override
	public void setR_IssueUser_ID (int R_IssueUser_ID)
	{
		if (R_IssueUser_ID < 1) 
			set_Value (COLUMNNAME_R_IssueUser_ID, null);
		else 
			set_Value (COLUMNNAME_R_IssueUser_ID, Integer.valueOf(R_IssueUser_ID));
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
	public void setR_Request(org.compiere.model.I_R_Request R_Request)
	{
		set_ValueFromPO(COLUMNNAME_R_Request_ID, org.compiere.model.I_R_Request.class, R_Request);
	}

	@Override
	public void setR_Request_ID (int R_Request_ID)
	{
		if (R_Request_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_R_Request_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_R_Request_ID, Integer.valueOf(R_Request_ID));
	}

	@Override
	public int getR_Request_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_R_Request_ID);
	}

	@Override
	public void setRecord_ID (int Record_ID)
	{
		if (Record_ID < 0) 
			set_ValueNoCheck (COLUMNNAME_Record_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Record_ID, Integer.valueOf(Record_ID));
	}

	@Override
	public int getRecord_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Record_ID);
	}

	@Override
	public void setReleaseNo (java.lang.String ReleaseNo)
	{
		set_ValueNoCheck (COLUMNNAME_ReleaseNo, ReleaseNo);
	}

	@Override
	public java.lang.String getReleaseNo() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ReleaseNo);
	}

	@Override
	public void setReleaseTag (java.lang.String ReleaseTag)
	{
		set_Value (COLUMNNAME_ReleaseTag, ReleaseTag);
	}

	@Override
	public java.lang.String getReleaseTag() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ReleaseTag);
	}

	@Override
	public void setRemote_Addr (java.lang.String Remote_Addr)
	{
		set_ValueNoCheck (COLUMNNAME_Remote_Addr, Remote_Addr);
	}

	@Override
	public java.lang.String getRemote_Addr() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Remote_Addr);
	}

	@Override
	public void setRemote_Host (java.lang.String Remote_Host)
	{
		set_ValueNoCheck (COLUMNNAME_Remote_Host, Remote_Host);
	}

	@Override
	public java.lang.String getRemote_Host() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Remote_Host);
	}

	@Override
	public void setRequestDocumentNo (java.lang.String RequestDocumentNo)
	{
		set_ValueNoCheck (COLUMNNAME_RequestDocumentNo, RequestDocumentNo);
	}

	@Override
	public java.lang.String getRequestDocumentNo() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_RequestDocumentNo);
	}

	@Override
	public void setResponseText (java.lang.String ResponseText)
	{
		set_ValueNoCheck (COLUMNNAME_ResponseText, ResponseText);
	}

	@Override
	public java.lang.String getResponseText() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ResponseText);
	}

	@Override
	public void setSourceClassName (java.lang.String SourceClassName)
	{
		set_Value (COLUMNNAME_SourceClassName, SourceClassName);
	}

	@Override
	public java.lang.String getSourceClassName() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_SourceClassName);
	}

	@Override
	public void setSourceMethodName (java.lang.String SourceMethodName)
	{
		set_Value (COLUMNNAME_SourceMethodName, SourceMethodName);
	}

	@Override
	public java.lang.String getSourceMethodName() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_SourceMethodName);
	}

	@Override
	public void setStackTrace (java.lang.String StackTrace)
	{
		set_Value (COLUMNNAME_StackTrace, StackTrace);
	}

	@Override
	public java.lang.String getStackTrace() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_StackTrace);
	}

	@Override
	public void setStatisticsInfo (java.lang.String StatisticsInfo)
	{
		set_ValueNoCheck (COLUMNNAME_StatisticsInfo, StatisticsInfo);
	}

	@Override
	public java.lang.String getStatisticsInfo() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_StatisticsInfo);
	}

	@Override
	public void setSupportEMail (java.lang.String SupportEMail)
	{
		set_Value (COLUMNNAME_SupportEMail, SupportEMail);
	}

	@Override
	public java.lang.String getSupportEMail() 
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
	@Override
	public void setSystemStatus (java.lang.String SystemStatus)
	{

		set_Value (COLUMNNAME_SystemStatus, SystemStatus);
	}

	@Override
	public java.lang.String getSystemStatus() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_SystemStatus);
	}

	@Override
	public void setUserName (java.lang.String UserName)
	{
		set_ValueNoCheck (COLUMNNAME_UserName, UserName);
	}

	@Override
	public java.lang.String getUserName() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_UserName);
	}

	@Override
	public void setVersion (java.lang.String Version)
	{
		set_ValueNoCheck (COLUMNNAME_Version, Version);
	}

	@Override
	public java.lang.String getVersion() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Version);
	}
}