/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for AD_Table_Process
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_AD_Table_Process extends org.compiere.model.PO implements I_AD_Table_Process, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -456624343L;

    /** Standard Constructor */
    public X_AD_Table_Process (Properties ctx, int AD_Table_Process_ID, String trxName)
    {
      super (ctx, AD_Table_Process_ID, trxName);
      /** if (AD_Table_Process_ID == 0)
        {
			setAD_Process_ID (0);
			setAD_Table_ID (0);
			setAD_Table_Process_ID (0);
			setEntityType (null);
			setWEBUI_DocumentAction (true); // Y
			setWEBUI_IncludedTabTopAction (false); // N
			setWEBUI_ViewAction (true); // Y
			setWEBUI_ViewQuickAction (false); // N
			setWEBUI_ViewQuickAction_Default (false); // N
        } */
    }

    /** Load Constructor */
    public X_AD_Table_Process (Properties ctx, ResultSet rs, String trxName)
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
		Prozess oder Bericht
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
		@return Prozess oder Bericht
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
	public org.compiere.model.I_AD_Tab getAD_Tab() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_Tab_ID, org.compiere.model.I_AD_Tab.class);
	}

	@Override
	public void setAD_Tab(org.compiere.model.I_AD_Tab AD_Tab)
	{
		set_ValueFromPO(COLUMNNAME_AD_Tab_ID, org.compiere.model.I_AD_Tab.class, AD_Tab);
	}

	/** Set Register.
		@param AD_Tab_ID 
		Register auf einem Fenster
	  */
	@Override
	public void setAD_Tab_ID (int AD_Tab_ID)
	{
		if (AD_Tab_ID < 1) 
			set_Value (COLUMNNAME_AD_Tab_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Tab_ID, Integer.valueOf(AD_Tab_ID));
	}

	/** Get Register.
		@return Register auf einem Fenster
	  */
	@Override
	public int getAD_Tab_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Tab_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_AD_Table getAD_Table() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_Table_ID, org.compiere.model.I_AD_Table.class);
	}

	@Override
	public void setAD_Table(org.compiere.model.I_AD_Table AD_Table)
	{
		set_ValueFromPO(COLUMNNAME_AD_Table_ID, org.compiere.model.I_AD_Table.class, AD_Table);
	}

	/** Set DB-Tabelle.
		@param AD_Table_ID 
		Database Table information
	  */
	@Override
	public void setAD_Table_ID (int AD_Table_ID)
	{
		if (AD_Table_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Table_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Table_ID, Integer.valueOf(AD_Table_ID));
	}

	/** Get DB-Tabelle.
		@return Database Table information
	  */
	@Override
	public int getAD_Table_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Table_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Table Process.
		@param AD_Table_Process_ID Table Process	  */
	@Override
	public void setAD_Table_Process_ID (int AD_Table_Process_ID)
	{
		if (AD_Table_Process_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Table_Process_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Table_Process_ID, Integer.valueOf(AD_Table_Process_ID));
	}

	/** Get Table Process.
		@return Table Process	  */
	@Override
	public int getAD_Table_Process_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Table_Process_ID);
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
		Eingabe- oder Anzeige-Fenster
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
		@return Eingabe- oder Anzeige-Fenster
	  */
	@Override
	public int getAD_Window_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Window_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Is Single Document Action.
		@param WEBUI_DocumentAction Is Single Document Action	  */
	@Override
	public void setWEBUI_DocumentAction (boolean WEBUI_DocumentAction)
	{
		set_Value (COLUMNNAME_WEBUI_DocumentAction, Boolean.valueOf(WEBUI_DocumentAction));
	}

	/** Get Is Single Document Action.
		@return Is Single Document Action	  */
	@Override
	public boolean isWEBUI_DocumentAction () 
	{
		Object oo = get_Value(COLUMNNAME_WEBUI_DocumentAction);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Is Included Tab Top Action.
		@param WEBUI_IncludedTabTopAction Is Included Tab Top Action	  */
	@Override
	public void setWEBUI_IncludedTabTopAction (boolean WEBUI_IncludedTabTopAction)
	{
		set_Value (COLUMNNAME_WEBUI_IncludedTabTopAction, Boolean.valueOf(WEBUI_IncludedTabTopAction));
	}

	/** Get Is Included Tab Top Action.
		@return Is Included Tab Top Action	  */
	@Override
	public boolean isWEBUI_IncludedTabTopAction () 
	{
		Object oo = get_Value(COLUMNNAME_WEBUI_IncludedTabTopAction);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Shortcut.
		@param WEBUI_Shortcut Shortcut	  */
	@Override
	public void setWEBUI_Shortcut (java.lang.String WEBUI_Shortcut)
	{
		set_Value (COLUMNNAME_WEBUI_Shortcut, WEBUI_Shortcut);
	}

	/** Get Shortcut.
		@return Shortcut	  */
	@Override
	public java.lang.String getWEBUI_Shortcut () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_WEBUI_Shortcut);
	}

	/** Set Is View Action.
		@param WEBUI_ViewAction Is View Action	  */
	@Override
	public void setWEBUI_ViewAction (boolean WEBUI_ViewAction)
	{
		set_Value (COLUMNNAME_WEBUI_ViewAction, Boolean.valueOf(WEBUI_ViewAction));
	}

	/** Get Is View Action.
		@return Is View Action	  */
	@Override
	public boolean isWEBUI_ViewAction () 
	{
		Object oo = get_Value(COLUMNNAME_WEBUI_ViewAction);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Quick action.
		@param WEBUI_ViewQuickAction Quick action	  */
	@Override
	public void setWEBUI_ViewQuickAction (boolean WEBUI_ViewQuickAction)
	{
		set_Value (COLUMNNAME_WEBUI_ViewQuickAction, Boolean.valueOf(WEBUI_ViewQuickAction));
	}

	/** Get Quick action.
		@return Quick action	  */
	@Override
	public boolean isWEBUI_ViewQuickAction () 
	{
		Object oo = get_Value(COLUMNNAME_WEBUI_ViewQuickAction);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Default quick action.
		@param WEBUI_ViewQuickAction_Default Default quick action	  */
	@Override
	public void setWEBUI_ViewQuickAction_Default (boolean WEBUI_ViewQuickAction_Default)
	{
		set_Value (COLUMNNAME_WEBUI_ViewQuickAction_Default, Boolean.valueOf(WEBUI_ViewQuickAction_Default));
	}

	/** Get Default quick action.
		@return Default quick action	  */
	@Override
	public boolean isWEBUI_ViewQuickAction_Default () 
	{
		Object oo = get_Value(COLUMNNAME_WEBUI_ViewQuickAction_Default);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}
}