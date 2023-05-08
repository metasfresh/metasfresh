// Generated Model - DO NOT CHANGE
package org.compiere.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for AD_Menu
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_AD_Menu extends org.compiere.model.PO implements I_AD_Menu, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -789007860L;

    /** Standard Constructor */
    public X_AD_Menu (final Properties ctx, final int AD_Menu_ID, @Nullable final String trxName)
    {
      super (ctx, AD_Menu_ID, trxName);
    }

    /** Load Constructor */
    public X_AD_Menu (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
    {
      super (ctx, rs, trxName);
    }


	/** Load Meta Data */
	@Override
	protected org.compiere.model.POInfo initPO(final Properties ctx)
	{
		return org.compiere.model.POInfo.getPOInfo(Table_Name);
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
	/** Board = K */
	public static final String ACTION_Board = "K";
	/** Calendar = C */
	public static final String ACTION_Calendar = "C";
	@Override
	public void setAction (final @Nullable java.lang.String Action)
	{
		set_Value (COLUMNNAME_Action, Action);
	}

	@Override
	public java.lang.String getAction() 
	{
		return get_ValueAsString(COLUMNNAME_Action);
	}

	@Override
	public org.compiere.model.I_AD_Element getAD_Element()
	{
		return get_ValueAsPO(COLUMNNAME_AD_Element_ID, org.compiere.model.I_AD_Element.class);
	}

	@Override
	public void setAD_Element(final org.compiere.model.I_AD_Element AD_Element)
	{
		set_ValueFromPO(COLUMNNAME_AD_Element_ID, org.compiere.model.I_AD_Element.class, AD_Element);
	}

	@Override
	public void setAD_Element_ID (final int AD_Element_ID)
	{
		if (AD_Element_ID < 1) 
			set_Value (COLUMNNAME_AD_Element_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Element_ID, AD_Element_ID);
	}

	@Override
	public int getAD_Element_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Element_ID);
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
	public void setAD_Menu_ID (final int AD_Menu_ID)
	{
		if (AD_Menu_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Menu_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Menu_ID, AD_Menu_ID);
	}

	@Override
	public int getAD_Menu_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Menu_ID);
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
	public org.compiere.model.I_AD_Task getAD_Task()
	{
		return get_ValueAsPO(COLUMNNAME_AD_Task_ID, org.compiere.model.I_AD_Task.class);
	}

	@Override
	public void setAD_Task(final org.compiere.model.I_AD_Task AD_Task)
	{
		set_ValueFromPO(COLUMNNAME_AD_Task_ID, org.compiere.model.I_AD_Task.class, AD_Task);
	}

	@Override
	public void setAD_Task_ID (final int AD_Task_ID)
	{
		if (AD_Task_ID < 1) 
			set_Value (COLUMNNAME_AD_Task_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Task_ID, AD_Task_ID);
	}

	@Override
	public int getAD_Task_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Task_ID);
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
	public org.compiere.model.I_AD_Workbench getAD_Workbench()
	{
		return get_ValueAsPO(COLUMNNAME_AD_Workbench_ID, org.compiere.model.I_AD_Workbench.class);
	}

	@Override
	public void setAD_Workbench(final org.compiere.model.I_AD_Workbench AD_Workbench)
	{
		set_ValueFromPO(COLUMNNAME_AD_Workbench_ID, org.compiere.model.I_AD_Workbench.class, AD_Workbench);
	}

	@Override
	public void setAD_Workbench_ID (final int AD_Workbench_ID)
	{
		if (AD_Workbench_ID < 1) 
			set_Value (COLUMNNAME_AD_Workbench_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Workbench_ID, AD_Workbench_ID);
	}

	@Override
	public int getAD_Workbench_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Workbench_ID);
	}

	@Override
	public void setAD_Workflow_ID (final int AD_Workflow_ID)
	{
		if (AD_Workflow_ID < 1) 
			set_Value (COLUMNNAME_AD_Workflow_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Workflow_ID, AD_Workflow_ID);
	}

	@Override
	public int getAD_Workflow_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Workflow_ID);
	}

	@Override
	public void setDescription (final @Nullable java.lang.String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	@Override
	public java.lang.String getDescription() 
	{
		return get_ValueAsString(COLUMNNAME_Description);
	}

	/** 
	 * EntityType AD_Reference_ID=389
	 * Reference name: _EntityTypeNew
	 */
	public static final int ENTITYTYPE_AD_Reference_ID=389;
	@Override
	public void setEntityType (final java.lang.String EntityType)
	{
		set_Value (COLUMNNAME_EntityType, EntityType);
	}

	@Override
	public java.lang.String getEntityType() 
	{
		return get_ValueAsString(COLUMNNAME_EntityType);
	}

	@Override
	public void setInternalName (final java.lang.String InternalName)
	{
		set_Value (COLUMNNAME_InternalName, InternalName);
	}

	@Override
	public java.lang.String getInternalName() 
	{
		return get_ValueAsString(COLUMNNAME_InternalName);
	}

	@Override
	public void setIsCreateNew (final boolean IsCreateNew)
	{
		set_Value (COLUMNNAME_IsCreateNew, IsCreateNew);
	}

	@Override
	public boolean isCreateNew() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsCreateNew);
	}

	@Override
	public void setIsReadOnly (final boolean IsReadOnly)
	{
		set_Value (COLUMNNAME_IsReadOnly, IsReadOnly);
	}

	@Override
	public boolean isReadOnly() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsReadOnly);
	}

	@Override
	public void setIsSOTrx (final boolean IsSOTrx)
	{
		set_Value (COLUMNNAME_IsSOTrx, IsSOTrx);
	}

	@Override
	public boolean isSOTrx() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsSOTrx);
	}

	@Override
	public void setIsSummary (final boolean IsSummary)
	{
		set_Value (COLUMNNAME_IsSummary, IsSummary);
	}

	@Override
	public boolean isSummary() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsSummary);
	}

	@Override
	public void setName (final java.lang.String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	@Override
	public java.lang.String getName() 
	{
		return get_ValueAsString(COLUMNNAME_Name);
	}

	@Override
	public void setWEBUI_Board_ID (final int WEBUI_Board_ID)
	{
		if (WEBUI_Board_ID < 1) 
			set_Value (COLUMNNAME_WEBUI_Board_ID, null);
		else 
			set_Value (COLUMNNAME_WEBUI_Board_ID, WEBUI_Board_ID);
	}

	@Override
	public int getWEBUI_Board_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_WEBUI_Board_ID);
	}

	@Override
	public void setWEBUI_NameBrowse (final @Nullable java.lang.String WEBUI_NameBrowse)
	{
		set_Value (COLUMNNAME_WEBUI_NameBrowse, WEBUI_NameBrowse);
	}

	@Override
	public java.lang.String getWEBUI_NameBrowse() 
	{
		return get_ValueAsString(COLUMNNAME_WEBUI_NameBrowse);
	}

	@Override
	public void setWEBUI_NameNew (final @Nullable java.lang.String WEBUI_NameNew)
	{
		set_Value (COLUMNNAME_WEBUI_NameNew, WEBUI_NameNew);
	}

	@Override
	public java.lang.String getWEBUI_NameNew() 
	{
		return get_ValueAsString(COLUMNNAME_WEBUI_NameNew);
	}

	@Override
	public void setWEBUI_NameNewBreadcrumb (final @Nullable java.lang.String WEBUI_NameNewBreadcrumb)
	{
		set_Value (COLUMNNAME_WEBUI_NameNewBreadcrumb, WEBUI_NameNewBreadcrumb);
	}

	@Override
	public java.lang.String getWEBUI_NameNewBreadcrumb() 
	{
		return get_ValueAsString(COLUMNNAME_WEBUI_NameNewBreadcrumb);
	}
}