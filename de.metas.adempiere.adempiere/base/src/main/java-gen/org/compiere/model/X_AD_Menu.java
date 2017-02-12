/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for AD_Menu
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_AD_Menu extends org.compiere.model.PO implements I_AD_Menu, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -1841125837L;

    /** Standard Constructor */
    public X_AD_Menu (Properties ctx, int AD_Menu_ID, String trxName)
    {
      super (ctx, AD_Menu_ID, trxName);
      /** if (AD_Menu_ID == 0)
        {
			setAD_Menu_ID (0);
			setEntityType (null);
// U
			setInternalName (null);
			setIsCreateNew (false);
// N
			setIsReadOnly (false);
// N
			setIsSOTrx (false);
			setIsSummary (false);
			setName (null);
        } */
    }

    /** Load Constructor */
    public X_AD_Menu (Properties ctx, ResultSet rs, String trxName)
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
	 * Action AD_Reference_ID=104
	 * Reference name: AD_Menu Action
	 */
	public static final int ACTION_AD_Reference_ID=104;
	/** Window = W */
	public static final String ACTION_Window = "W";
	/** Task = T */
	public static final String ACTION_Task = "T";
	/** WorkFlow = F */
	public static final String ACTION_WorkFlow = "F";
	/** Process = P */
	public static final String ACTION_Process = "P";
	/** Report = R */
	public static final String ACTION_Report = "R";
	/** Form = X */
	public static final String ACTION_Form = "X";
	/** Workbench = B */
	public static final String ACTION_Workbench = "B";
	/** Set Aktion.
		@param Action 
		Indicates the Action to be performed
	  */
	@Override
	public void setAction (java.lang.String Action)
	{

		set_Value (COLUMNNAME_Action, Action);
	}

	/** Get Aktion.
		@return Indicates the Action to be performed
	  */
	@Override
	public java.lang.String getAction () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Action);
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

	/** Set Menü.
		@param AD_Menu_ID 
		Identifies a Menu
	  */
	@Override
	public void setAD_Menu_ID (int AD_Menu_ID)
	{
		if (AD_Menu_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Menu_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Menu_ID, Integer.valueOf(AD_Menu_ID));
	}

	/** Get Menü.
		@return Identifies a Menu
	  */
	@Override
	public int getAD_Menu_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Menu_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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
		Operation System Task
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
		@return Operation System Task
	  */
	@Override
	public int getAD_Task_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Task_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_AD_Window getAD_Window() throws RuntimeException
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

	@Override
	public org.compiere.model.I_AD_Workbench getAD_Workbench() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_Workbench_ID, org.compiere.model.I_AD_Workbench.class);
	}

	@Override
	public void setAD_Workbench(org.compiere.model.I_AD_Workbench AD_Workbench)
	{
		set_ValueFromPO(COLUMNNAME_AD_Workbench_ID, org.compiere.model.I_AD_Workbench.class, AD_Workbench);
	}

	/** Set Workbench.
		@param AD_Workbench_ID 
		Collection of windows, reports
	  */
	@Override
	public void setAD_Workbench_ID (int AD_Workbench_ID)
	{
		if (AD_Workbench_ID < 1) 
			set_Value (COLUMNNAME_AD_Workbench_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Workbench_ID, Integer.valueOf(AD_Workbench_ID));
	}

	/** Get Workbench.
		@return Collection of windows, reports
	  */
	@Override
	public int getAD_Workbench_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Workbench_ID);
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

	/** Set Interner Name.
		@param InternalName 
		Generally used to give records a name that can be safely referenced from code.
	  */
	@Override
	public void setInternalName (java.lang.String InternalName)
	{
		set_Value (COLUMNNAME_InternalName, InternalName);
	}

	/** Get Interner Name.
		@return Generally used to give records a name that can be safely referenced from code.
	  */
	@Override
	public java.lang.String getInternalName () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_InternalName);
	}

	/** Set Create new.
		@param IsCreateNew Create new	  */
	@Override
	public void setIsCreateNew (boolean IsCreateNew)
	{
		set_Value (COLUMNNAME_IsCreateNew, Boolean.valueOf(IsCreateNew));
	}

	/** Get Create new.
		@return Create new	  */
	@Override
	public boolean isCreateNew () 
	{
		Object oo = get_Value(COLUMNNAME_IsCreateNew);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Schreibgeschützt.
		@param IsReadOnly 
		Field is read only
	  */
	@Override
	public void setIsReadOnly (boolean IsReadOnly)
	{
		set_Value (COLUMNNAME_IsReadOnly, Boolean.valueOf(IsReadOnly));
	}

	/** Get Schreibgeschützt.
		@return Field is read only
	  */
	@Override
	public boolean isReadOnly () 
	{
		Object oo = get_Value(COLUMNNAME_IsReadOnly);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Verkaufs-Transaktion.
		@param IsSOTrx 
		This is a Sales Transaction
	  */
	@Override
	public void setIsSOTrx (boolean IsSOTrx)
	{
		set_Value (COLUMNNAME_IsSOTrx, Boolean.valueOf(IsSOTrx));
	}

	/** Get Verkaufs-Transaktion.
		@return This is a Sales Transaction
	  */
	@Override
	public boolean isSOTrx () 
	{
		Object oo = get_Value(COLUMNNAME_IsSOTrx);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Zusammenfassungseintrag.
		@param IsSummary 
		This is a summary entity
	  */
	@Override
	public void setIsSummary (boolean IsSummary)
	{
		set_Value (COLUMNNAME_IsSummary, Boolean.valueOf(IsSummary));
	}

	/** Get Zusammenfassungseintrag.
		@return This is a summary entity
	  */
	@Override
	public boolean isSummary () 
	{
		Object oo = get_Value(COLUMNNAME_IsSummary);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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

	/** Set Browse name.
		@param WEBUI_NameBrowse Browse name	  */
	@Override
	public void setWEBUI_NameBrowse (java.lang.String WEBUI_NameBrowse)
	{
		set_Value (COLUMNNAME_WEBUI_NameBrowse, WEBUI_NameBrowse);
	}

	/** Get Browse name.
		@return Browse name	  */
	@Override
	public java.lang.String getWEBUI_NameBrowse () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_WEBUI_NameBrowse);
	}

	/** Set New record name.
		@param WEBUI_NameNew New record name	  */
	@Override
	public void setWEBUI_NameNew (java.lang.String WEBUI_NameNew)
	{
		set_Value (COLUMNNAME_WEBUI_NameNew, WEBUI_NameNew);
	}

	/** Get New record name.
		@return New record name	  */
	@Override
	public java.lang.String getWEBUI_NameNew () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_WEBUI_NameNew);
	}

	/** Set New record name (breadcrumb).
		@param WEBUI_NameNewBreadcrumb New record name (breadcrumb)	  */
	@Override
	public void setWEBUI_NameNewBreadcrumb (java.lang.String WEBUI_NameNewBreadcrumb)
	{
		set_Value (COLUMNNAME_WEBUI_NameNewBreadcrumb, WEBUI_NameNewBreadcrumb);
	}

	/** Get New record name (breadcrumb).
		@return New record name (breadcrumb)	  */
	@Override
	public java.lang.String getWEBUI_NameNewBreadcrumb () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_WEBUI_NameNewBreadcrumb);
	}
}