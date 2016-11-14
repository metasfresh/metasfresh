/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for AD_Process
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_AD_Process extends org.compiere.model.PO implements I_AD_Process, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1939563412L;

    /** Standard Constructor */
    public X_AD_Process (Properties ctx, int AD_Process_ID, String trxName)
    {
      super (ctx, AD_Process_ID, trxName);
      /** if (AD_Process_ID == 0)
        {
			setAccessLevel (null);
			setAD_Process_ID (0);
			setAllowProcessReRun (true);
// Y
			setEntityType (null);
// U
			setIsApplySecuritySettings (false);
// N
			setIsBetaFunctionality (false);
			setIsReport (false);
			setIsServerProcess (false);
			setIsUseBPartnerLanguage (true);
// Y
			setName (null);
			setRefreshAllAfterExecution (false);
// N
			setValue (null);
        } */
    }

    /** Load Constructor */
    public X_AD_Process (Properties ctx, ResultSet rs, String trxName)
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

	/** 
	 * AccessLevel AD_Reference_ID=5
	 * Reference name: AD_Table Access Levels
	 */
	public static final int ACCESSLEVEL_AD_Reference_ID=5;
	/** Organization = 1 */
	public static final String ACCESSLEVEL_Organization = "1";
	/** ClientPlusOrganization = 3 */
	public static final String ACCESSLEVEL_ClientPlusOrganization = "3";
	/** SystemOnly = 4 */
	public static final String ACCESSLEVEL_SystemOnly = "4";
	/** All = 7 */
	public static final String ACCESSLEVEL_All = "7";
	/** SystemPlusClient = 6 */
	public static final String ACCESSLEVEL_SystemPlusClient = "6";
	/** ClientOnly = 2 */
	public static final String ACCESSLEVEL_ClientOnly = "2";
	/** Set Berechtigungsstufe.
		@param AccessLevel 
		Access Level required
	  */
	@Override
	public void setAccessLevel (java.lang.String AccessLevel)
	{

		set_Value (COLUMNNAME_AccessLevel, AccessLevel);
	}

	/** Get Berechtigungsstufe.
		@return Access Level required
	  */
	@Override
	public java.lang.String getAccessLevel () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_AccessLevel);
	}

	@Override
	public org.compiere.model.I_AD_Form getAD_Form() throws RuntimeException
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

	@Override
	public org.compiere.model.I_AD_PrintFormat getAD_PrintFormat() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_PrintFormat_ID, org.compiere.model.I_AD_PrintFormat.class);
	}

	@Override
	public void setAD_PrintFormat(org.compiere.model.I_AD_PrintFormat AD_PrintFormat)
	{
		set_ValueFromPO(COLUMNNAME_AD_PrintFormat_ID, org.compiere.model.I_AD_PrintFormat.class, AD_PrintFormat);
	}

	/** Set Druck - Format.
		@param AD_PrintFormat_ID 
		Data Print Format
	  */
	@Override
	public void setAD_PrintFormat_ID (int AD_PrintFormat_ID)
	{
		if (AD_PrintFormat_ID < 1) 
			set_Value (COLUMNNAME_AD_PrintFormat_ID, null);
		else 
			set_Value (COLUMNNAME_AD_PrintFormat_ID, Integer.valueOf(AD_PrintFormat_ID));
	}

	/** Get Druck - Format.
		@return Data Print Format
	  */
	@Override
	public int getAD_PrintFormat_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_PrintFormat_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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
	public org.compiere.model.I_AD_ReportView getAD_ReportView() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_ReportView_ID, org.compiere.model.I_AD_ReportView.class);
	}

	@Override
	public void setAD_ReportView(org.compiere.model.I_AD_ReportView AD_ReportView)
	{
		set_ValueFromPO(COLUMNNAME_AD_ReportView_ID, org.compiere.model.I_AD_ReportView.class, AD_ReportView);
	}

	/** Set Berichts-View.
		@param AD_ReportView_ID 
		View used to generate this report
	  */
	@Override
	public void setAD_ReportView_ID (int AD_ReportView_ID)
	{
		if (AD_ReportView_ID < 1) 
			set_Value (COLUMNNAME_AD_ReportView_ID, null);
		else 
			set_Value (COLUMNNAME_AD_ReportView_ID, Integer.valueOf(AD_ReportView_ID));
	}

	/** Get Berichts-View.
		@return View used to generate this report
	  */
	@Override
	public int getAD_ReportView_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_ReportView_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_AD_Workflow getAD_Workflow() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_Workflow_ID, org.compiere.model.I_AD_Workflow.class);
	}

	@Override
	public void setAD_Workflow(org.compiere.model.I_AD_Workflow AD_Workflow)
	{
		set_ValueFromPO(COLUMNNAME_AD_Workflow_ID, org.compiere.model.I_AD_Workflow.class, AD_Workflow);
	}

	/** Set Workflow.
		@param AD_Workflow_ID 
		Workflow or combination of tasks
	  */
	@Override
	public void setAD_Workflow_ID (int AD_Workflow_ID)
	{
		if (AD_Workflow_ID < 1) 
			set_Value (COLUMNNAME_AD_Workflow_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Workflow_ID, Integer.valueOf(AD_Workflow_ID));
	}

	/** Get Workflow.
		@return Workflow or combination of tasks
	  */
	@Override
	public int getAD_Workflow_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Workflow_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Mehrfachausführung erlaubt.
		@param AllowProcessReRun 
		Allows this process to be executed again. If enabled, the "Back" button will be displayed in process panel.
	  */
	@Override
	public void setAllowProcessReRun (boolean AllowProcessReRun)
	{
		set_Value (COLUMNNAME_AllowProcessReRun, Boolean.valueOf(AllowProcessReRun));
	}

	/** Get Mehrfachausführung erlaubt.
		@return Allows this process to be executed again. If enabled, the "Back" button will be displayed in process panel.
	  */
	@Override
	public boolean isAllowProcessReRun () 
	{
		Object oo = get_Value(COLUMNNAME_AllowProcessReRun);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Java-Klasse.
		@param Classname Java-Klasse	  */
	@Override
	public void setClassname (java.lang.String Classname)
	{
		set_Value (COLUMNNAME_Classname, Classname);
	}

	/** Get Java-Klasse.
		@return Java-Klasse	  */
	@Override
	public java.lang.String getClassname () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Classname);
	}

	/** Set Copy From Report and Process.
		@param CopyFromProcess 
		Copy settings from one report and process to another.
	  */
	@Override
	public void setCopyFromProcess (java.lang.String CopyFromProcess)
	{
		set_Value (COLUMNNAME_CopyFromProcess, CopyFromProcess);
	}

	/** Get Copy From Report and Process.
		@return Copy settings from one report and process to another.
	  */
	@Override
	public java.lang.String getCopyFromProcess () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_CopyFromProcess);
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

	/** Set Kommentar/Hilfe.
		@param Help 
		Comment or Hint
	  */
	@Override
	public void setHelp (java.lang.String Help)
	{
		set_Value (COLUMNNAME_Help, Help);
	}

	/** Get Kommentar/Hilfe.
		@return Comment or Hint
	  */
	@Override
	public java.lang.String getHelp () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Help);
	}

	/** Set IsApplySecuritySettings.
		@param IsApplySecuritySettings IsApplySecuritySettings	  */
	@Override
	public void setIsApplySecuritySettings (boolean IsApplySecuritySettings)
	{
		set_Value (COLUMNNAME_IsApplySecuritySettings, Boolean.valueOf(IsApplySecuritySettings));
	}

	/** Get IsApplySecuritySettings.
		@return IsApplySecuritySettings	  */
	@Override
	public boolean isApplySecuritySettings () 
	{
		Object oo = get_Value(COLUMNNAME_IsApplySecuritySettings);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Beta-Funktionalität.
		@param IsBetaFunctionality 
		This functionality is considered Beta
	  */
	@Override
	public void setIsBetaFunctionality (boolean IsBetaFunctionality)
	{
		set_Value (COLUMNNAME_IsBetaFunctionality, Boolean.valueOf(IsBetaFunctionality));
	}

	/** Get Beta-Funktionalität.
		@return This functionality is considered Beta
	  */
	@Override
	public boolean isBetaFunctionality () 
	{
		Object oo = get_Value(COLUMNNAME_IsBetaFunctionality);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Direct print.
		@param IsDirectPrint 
		Print without dialog
	  */
	@Override
	public void setIsDirectPrint (boolean IsDirectPrint)
	{
		set_Value (COLUMNNAME_IsDirectPrint, Boolean.valueOf(IsDirectPrint));
	}

	/** Get Direct print.
		@return Print without dialog
	  */
	@Override
	public boolean isDirectPrint () 
	{
		Object oo = get_Value(COLUMNNAME_IsDirectPrint);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Bericht.
		@param IsReport 
		Indicates a Report record
	  */
	@Override
	public void setIsReport (boolean IsReport)
	{
		set_Value (COLUMNNAME_IsReport, Boolean.valueOf(IsReport));
	}

	/** Get Bericht.
		@return Indicates a Report record
	  */
	@Override
	public boolean isReport () 
	{
		Object oo = get_Value(COLUMNNAME_IsReport);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Server Process.
		@param IsServerProcess 
		Run this Process on Server only
	  */
	@Override
	public void setIsServerProcess (boolean IsServerProcess)
	{
		set_Value (COLUMNNAME_IsServerProcess, Boolean.valueOf(IsServerProcess));
	}

	/** Get Server Process.
		@return Run this Process on Server only
	  */
	@Override
	public boolean isServerProcess () 
	{
		Object oo = get_Value(COLUMNNAME_IsServerProcess);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set IsUseBPartnerLanguage.
		@param IsUseBPartnerLanguage IsUseBPartnerLanguage	  */
	@Override
	public void setIsUseBPartnerLanguage (boolean IsUseBPartnerLanguage)
	{
		set_Value (COLUMNNAME_IsUseBPartnerLanguage, Boolean.valueOf(IsUseBPartnerLanguage));
	}

	/** Get IsUseBPartnerLanguage.
		@return IsUseBPartnerLanguage	  */
	@Override
	public boolean isUseBPartnerLanguage () 
	{
		Object oo = get_Value(COLUMNNAME_IsUseBPartnerLanguage);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Jasper Report.
		@param JasperReport Jasper Report	  */
	@Override
	public void setJasperReport (java.lang.String JasperReport)
	{
		set_Value (COLUMNNAME_JasperReport, JasperReport);
	}

	/** Get Jasper Report.
		@return Jasper Report	  */
	@Override
	public java.lang.String getJasperReport () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_JasperReport);
	}

	/** Set Jasper Report (tabular).
		@param JasperReport_Tabular 
		Jasper report to optimized for tabular preview. If specified, this report will be used when the report needs to be exported to XLS, CSV or any other tabular formats.
	  */
	@Override
	public void setJasperReport_Tabular (java.lang.String JasperReport_Tabular)
	{
		set_Value (COLUMNNAME_JasperReport_Tabular, JasperReport_Tabular);
	}

	/** Get Jasper Report (tabular).
		@return Jasper report to optimized for tabular preview. If specified, this report will be used when the report needs to be exported to XLS, CSV or any other tabular formats.
	  */
	@Override
	public java.lang.String getJasperReport_Tabular () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_JasperReport_Tabular);
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

	/** Set Procedure.
		@param ProcedureName 
		Name of the Database Procedure
	  */
	@Override
	public void setProcedureName (java.lang.String ProcedureName)
	{
		set_Value (COLUMNNAME_ProcedureName, ProcedureName);
	}

	/** Get Procedure.
		@return Name of the Database Procedure
	  */
	@Override
	public java.lang.String getProcedureName () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ProcedureName);
	}

	/** Set Refresh All After Execution.
		@param RefreshAllAfterExecution 
		If true, then refresh all entries after a process's execution; otherwise, refresh only current selection
	  */
	@Override
	public void setRefreshAllAfterExecution (boolean RefreshAllAfterExecution)
	{
		set_Value (COLUMNNAME_RefreshAllAfterExecution, Boolean.valueOf(RefreshAllAfterExecution));
	}

	/** Get Refresh All After Execution.
		@return If true, then refresh all entries after a process's execution; otherwise, refresh only current selection
	  */
	@Override
	public boolean isRefreshAllAfterExecution () 
	{
		Object oo = get_Value(COLUMNNAME_RefreshAllAfterExecution);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** 
	 * ShowHelp AD_Reference_ID=50007
	 * Reference name: ShowHelp List
	 */
	public static final int SHOWHELP_AD_Reference_ID=50007;
	/** Ask user (for future use) = A */
	public static final String SHOWHELP_AskUserForFutureUse = "A";
	/** Don't show help = N */
	public static final String SHOWHELP_DonTShowHelp = "N";
	/** Show Help = Y */
	public static final String SHOWHELP_ShowHelp = "Y";
	/** Run silently - Take Defaults = S */
	public static final String SHOWHELP_RunSilently_TakeDefaults = "S";
	/** Set Show Help.
		@param ShowHelp Show Help	  */
	@Override
	public void setShowHelp (java.lang.String ShowHelp)
	{

		set_Value (COLUMNNAME_ShowHelp, ShowHelp);
	}

	/** Get Show Help.
		@return Show Help	  */
	@Override
	public java.lang.String getShowHelp () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ShowHelp);
	}

	/** Set Suchschlüssel.
		@param Value 
		Search key for the record in the format required - must be unique
	  */
	@Override
	public void setValue (java.lang.String Value)
	{
		set_Value (COLUMNNAME_Value, Value);
	}

	/** Get Suchschlüssel.
		@return Search key for the record in the format required - must be unique
	  */
	@Override
	public java.lang.String getValue () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Value);
	}

	/** Set Workflow Key.
		@param WorkflowValue 
		Key of the Workflow to start
	  */
	@Override
	public void setWorkflowValue (java.lang.String WorkflowValue)
	{
		set_Value (COLUMNNAME_WorkflowValue, WorkflowValue);
	}

	/** Get Workflow Key.
		@return Key of the Workflow to start
	  */
	@Override
	public java.lang.String getWorkflowValue () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_WorkflowValue);
	}
}