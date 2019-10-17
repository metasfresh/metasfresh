/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for AD_Tab
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_AD_Tab extends org.compiere.model.PO implements I_AD_Tab, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 644045757L;

    /** Standard Constructor */
    public X_AD_Tab (Properties ctx, int AD_Tab_ID, String trxName)
    {
      super (ctx, AD_Tab_ID, trxName);
      /** if (AD_Tab_ID == 0)
        {
			setAD_Element_ID (0);
			setAD_Tab_ID (0);
			setAD_Table_ID (0);
			setAD_Window_ID (0);
			setAllowQuickInput (true); // Y
			setEntityType (null); // U
			setHasTree (false);
			setIsAdvancedTab (false); // N
			setIsCheckParentsChanged (true); // Y
			setIsGridModeOnly (false); // N
			setIsInsertRecord (true); // Y
			setIsReadOnly (false);
			setIsRefreshAllOnActivate (false); // N
			setIsRefreshViewOnChangeEvents (false); // N
			setIsSearchCollapsed (true); // Y
			setIsSingleRow (false);
			setIsSortTab (false); // N
			setIsTranslationTab (false);
			setName (null);
			setSeqNo (0); // @SQL=SELECT COALESCE(MAX(SeqNo),0)+10 AS DefaultValue FROM AD_Tab WHERE AD_Window_ID=@AD_Window_ID@
			setTabLevel (0);
        } */
    }

    /** Load Constructor */
    public X_AD_Tab (Properties ctx, ResultSet rs, String trxName)
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
	public org.compiere.model.I_AD_Column getAD_Column() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_Column_ID, org.compiere.model.I_AD_Column.class);
	}

	@Override
	public void setAD_Column(org.compiere.model.I_AD_Column AD_Column)
	{
		set_ValueFromPO(COLUMNNAME_AD_Column_ID, org.compiere.model.I_AD_Column.class, AD_Column);
	}

	/** Set Spalte.
		@param AD_Column_ID 
		Column in the table
	  */
	@Override
	public void setAD_Column_ID (int AD_Column_ID)
	{
		if (AD_Column_ID < 1) 
			set_Value (COLUMNNAME_AD_Column_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Column_ID, Integer.valueOf(AD_Column_ID));
	}

	/** Get Spalte.
		@return Column in the table
	  */
	@Override
	public int getAD_Column_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Column_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_AD_Column getAD_ColumnSortOrder() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_ColumnSortOrder_ID, org.compiere.model.I_AD_Column.class);
	}

	@Override
	public void setAD_ColumnSortOrder(org.compiere.model.I_AD_Column AD_ColumnSortOrder)
	{
		set_ValueFromPO(COLUMNNAME_AD_ColumnSortOrder_ID, org.compiere.model.I_AD_Column.class, AD_ColumnSortOrder);
	}

	/** Set Order Column.
		@param AD_ColumnSortOrder_ID 
		Column determining the order
	  */
	@Override
	public void setAD_ColumnSortOrder_ID (int AD_ColumnSortOrder_ID)
	{
		if (AD_ColumnSortOrder_ID < 1) 
			set_Value (COLUMNNAME_AD_ColumnSortOrder_ID, null);
		else 
			set_Value (COLUMNNAME_AD_ColumnSortOrder_ID, Integer.valueOf(AD_ColumnSortOrder_ID));
	}

	/** Get Order Column.
		@return Column determining the order
	  */
	@Override
	public int getAD_ColumnSortOrder_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_ColumnSortOrder_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_AD_Column getAD_ColumnSortYesNo() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_ColumnSortYesNo_ID, org.compiere.model.I_AD_Column.class);
	}

	@Override
	public void setAD_ColumnSortYesNo(org.compiere.model.I_AD_Column AD_ColumnSortYesNo)
	{
		set_ValueFromPO(COLUMNNAME_AD_ColumnSortYesNo_ID, org.compiere.model.I_AD_Column.class, AD_ColumnSortYesNo);
	}

	/** Set Included Column.
		@param AD_ColumnSortYesNo_ID 
		Column determining if a Table Column is included in Ordering
	  */
	@Override
	public void setAD_ColumnSortYesNo_ID (int AD_ColumnSortYesNo_ID)
	{
		if (AD_ColumnSortYesNo_ID < 1) 
			set_Value (COLUMNNAME_AD_ColumnSortYesNo_ID, null);
		else 
			set_Value (COLUMNNAME_AD_ColumnSortYesNo_ID, Integer.valueOf(AD_ColumnSortYesNo_ID));
	}

	/** Get Included Column.
		@return Column determining if a Table Column is included in Ordering
	  */
	@Override
	public int getAD_ColumnSortYesNo_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_ColumnSortYesNo_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_AD_Element getAD_Element() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_Element_ID, org.compiere.model.I_AD_Element.class);
	}

	@Override
	public void setAD_Element(org.compiere.model.I_AD_Element AD_Element)
	{
		set_ValueFromPO(COLUMNNAME_AD_Element_ID, org.compiere.model.I_AD_Element.class, AD_Element);
	}

	/** Set System-Element.
		@param AD_Element_ID 
		Das "System-Element" ermöglicht die zentrale  Verwaltung von Spaltenbeschreibungen und Hilfetexten.
	  */
	@Override
	public void setAD_Element_ID (int AD_Element_ID)
	{
		if (AD_Element_ID < 1) 
			set_Value (COLUMNNAME_AD_Element_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Element_ID, Integer.valueOf(AD_Element_ID));
	}

	/** Get System-Element.
		@return Das "System-Element" ermöglicht die zentrale  Verwaltung von Spaltenbeschreibungen und Hilfetexten.
	  */
	@Override
	public int getAD_Element_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Element_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_AD_Image getAD_Image() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_Image_ID, org.compiere.model.I_AD_Image.class);
	}

	@Override
	public void setAD_Image(org.compiere.model.I_AD_Image AD_Image)
	{
		set_ValueFromPO(COLUMNNAME_AD_Image_ID, org.compiere.model.I_AD_Image.class, AD_Image);
	}

	/** Set Bild.
		@param AD_Image_ID 
		Image or Icon
	  */
	@Override
	public void setAD_Image_ID (int AD_Image_ID)
	{
		if (AD_Image_ID < 1) 
			set_Value (COLUMNNAME_AD_Image_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Image_ID, Integer.valueOf(AD_Image_ID));
	}

	/** Get Bild.
		@return Image or Icon
	  */
	@Override
	public int getAD_Image_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Image_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_AD_Message getAD_Message() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_Message_ID, org.compiere.model.I_AD_Message.class);
	}

	@Override
	public void setAD_Message(org.compiere.model.I_AD_Message AD_Message)
	{
		set_ValueFromPO(COLUMNNAME_AD_Message_ID, org.compiere.model.I_AD_Message.class, AD_Message);
	}

	/** Set Statusleistenmeldung.
		@param AD_Message_ID 
		System Message
	  */
	@Override
	public void setAD_Message_ID (int AD_Message_ID)
	{
		if (AD_Message_ID < 1) 
			set_Value (COLUMNNAME_AD_Message_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Message_ID, Integer.valueOf(AD_Message_ID));
	}

	/** Get Statusleistenmeldung.
		@return System Message
	  */
	@Override
	public int getAD_Message_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Message_ID);
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

	/** Set Register.
		@param AD_Tab_ID 
		Tab within a Window
	  */
	@Override
	public void setAD_Tab_ID (int AD_Tab_ID)
	{
		if (AD_Tab_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Tab_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Tab_ID, Integer.valueOf(AD_Tab_ID));
	}

	/** Get Register.
		@return Tab within a Window
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
			set_Value (COLUMNNAME_AD_Table_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Table_ID, Integer.valueOf(AD_Table_ID));
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
			set_ValueNoCheck (COLUMNNAME_AD_Window_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Window_ID, Integer.valueOf(AD_Window_ID));
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

	/** Set Schnelleingabe einschalten.
		@param AllowQuickInput Schnelleingabe einschalten	  */
	@Override
	public void setAllowQuickInput (boolean AllowQuickInput)
	{
		set_Value (COLUMNNAME_AllowQuickInput, Boolean.valueOf(AllowQuickInput));
	}

	/** Get Schnelleingabe einschalten.
		@return Schnelleingabe einschalten	  */
	@Override
	public boolean isAllowQuickInput () 
	{
		Object oo = get_Value(COLUMNNAME_AllowQuickInput);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Speicherwarnung.
		@param CommitWarning 
		Warning displayed when saving
	  */
	@Override
	public void setCommitWarning (java.lang.String CommitWarning)
	{
		set_Value (COLUMNNAME_CommitWarning, CommitWarning);
	}

	/** Get Speicherwarnung.
		@return Warning displayed when saving
	  */
	@Override
	public java.lang.String getCommitWarning () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_CommitWarning);
	}

	/** Set Default Selected Record.
		@param DefaultWhereClause 
		default SQL WHERE clause for selecting a default line
	  */
	@Override
	public void setDefaultWhereClause (java.lang.String DefaultWhereClause)
	{
		set_Value (COLUMNNAME_DefaultWhereClause, DefaultWhereClause);
	}

	/** Get Default Selected Record.
		@return default SQL WHERE clause for selecting a default line
	  */
	@Override
	public java.lang.String getDefaultWhereClause () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DefaultWhereClause);
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

	/** Set Anzeigelogik.
		@param DisplayLogic 
		If the Field is displayed, the result determines if the field is actually displayed
	  */
	@Override
	public void setDisplayLogic (java.lang.String DisplayLogic)
	{
		set_Value (COLUMNNAME_DisplayLogic, DisplayLogic);
	}

	/** Get Anzeigelogik.
		@return If the Field is displayed, the result determines if the field is actually displayed
	  */
	@Override
	public java.lang.String getDisplayLogic () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DisplayLogic);
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

	/** Set Has Tree.
		@param HasTree 
		Window has Tree Graph
	  */
	@Override
	public void setHasTree (boolean HasTree)
	{
		set_Value (COLUMNNAME_HasTree, Boolean.valueOf(HasTree));
	}

	/** Get Has Tree.
		@return Window has Tree Graph
	  */
	@Override
	public boolean isHasTree () 
	{
		Object oo = get_Value(COLUMNNAME_HasTree);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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

	/** Set Import Fields.
		@param ImportFields 
		Create Fields from Table Columns
	  */
	@Override
	public void setImportFields (java.lang.String ImportFields)
	{
		set_Value (COLUMNNAME_ImportFields, ImportFields);
	}

	/** Get Import Fields.
		@return Create Fields from Table Columns
	  */
	@Override
	public java.lang.String getImportFields () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ImportFields);
	}

	@Override
	public org.compiere.model.I_AD_Tab getIncluded_Tab() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_Included_Tab_ID, org.compiere.model.I_AD_Tab.class);
	}

	@Override
	public void setIncluded_Tab(org.compiere.model.I_AD_Tab Included_Tab)
	{
		set_ValueFromPO(COLUMNNAME_Included_Tab_ID, org.compiere.model.I_AD_Tab.class, Included_Tab);
	}

	/** Set Included Tab.
		@param Included_Tab_ID 
		Included Tab in this Tab (Master Dateail)
	  */
	@Override
	public void setIncluded_Tab_ID (int Included_Tab_ID)
	{
		if (Included_Tab_ID < 1) 
			set_Value (COLUMNNAME_Included_Tab_ID, null);
		else 
			set_Value (COLUMNNAME_Included_Tab_ID, Integer.valueOf(Included_Tab_ID));
	}

	/** Get Included Tab.
		@return Included Tab in this Tab (Master Dateail)
	  */
	@Override
	public int getIncluded_Tab_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Included_Tab_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Advanced Tab.
		@param IsAdvancedTab 
		This Tab contains advanced Functionality
	  */
	@Override
	public void setIsAdvancedTab (boolean IsAdvancedTab)
	{
		set_Value (COLUMNNAME_IsAdvancedTab, Boolean.valueOf(IsAdvancedTab));
	}

	/** Get Advanced Tab.
		@return This Tab contains advanced Functionality
	  */
	@Override
	public boolean isAdvancedTab () 
	{
		Object oo = get_Value(COLUMNNAME_IsAdvancedTab);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Check Parents Changed.
		@param IsCheckParentsChanged 
		Before saving a record in this tab shall we check if the parent tabs were changed?
	  */
	@Override
	public void setIsCheckParentsChanged (boolean IsCheckParentsChanged)
	{
		set_Value (COLUMNNAME_IsCheckParentsChanged, Boolean.valueOf(IsCheckParentsChanged));
	}

	/** Get Check Parents Changed.
		@return Before saving a record in this tab shall we check if the parent tabs were changed?
	  */
	@Override
	public boolean isCheckParentsChanged () 
	{
		Object oo = get_Value(COLUMNNAME_IsCheckParentsChanged);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Grid Mode Only.
		@param IsGridModeOnly 
		Allow grid mode only
	  */
	@Override
	public void setIsGridModeOnly (boolean IsGridModeOnly)
	{
		set_Value (COLUMNNAME_IsGridModeOnly, Boolean.valueOf(IsGridModeOnly));
	}

	/** Get Grid Mode Only.
		@return Allow grid mode only
	  */
	@Override
	public boolean isGridModeOnly () 
	{
		Object oo = get_Value(COLUMNNAME_IsGridModeOnly);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Accounting Tab.
		@param IsInfoTab 
		This Tab contains accounting information
	  */
	@Override
	public void setIsInfoTab (boolean IsInfoTab)
	{
		set_Value (COLUMNNAME_IsInfoTab, Boolean.valueOf(IsInfoTab));
	}

	/** Get Accounting Tab.
		@return This Tab contains accounting information
	  */
	@Override
	public boolean isInfoTab () 
	{
		Object oo = get_Value(COLUMNNAME_IsInfoTab);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Insert Record.
		@param IsInsertRecord 
		The user can insert a new Record
	  */
	@Override
	public void setIsInsertRecord (boolean IsInsertRecord)
	{
		set_Value (COLUMNNAME_IsInsertRecord, Boolean.valueOf(IsInsertRecord));
	}

	/** Get Insert Record.
		@return The user can insert a new Record
	  */
	@Override
	public boolean isInsertRecord () 
	{
		Object oo = get_Value(COLUMNNAME_IsInsertRecord);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Query data on load.
		@param IsQueryOnLoad Query data on load	  */
	@Override
	public void setIsQueryOnLoad (boolean IsQueryOnLoad)
	{
		set_Value (COLUMNNAME_IsQueryOnLoad, Boolean.valueOf(IsQueryOnLoad));
	}

	/** Get Query data on load.
		@return Query data on load	  */
	@Override
	public boolean isQueryOnLoad () 
	{
		Object oo = get_Value(COLUMNNAME_IsQueryOnLoad);
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

	/** Set Refresh All On Activate.
		@param IsRefreshAllOnActivate 
		Refresh all rows when user activates this tab, instead of refreshing only current row
	  */
	@Override
	public void setIsRefreshAllOnActivate (boolean IsRefreshAllOnActivate)
	{
		set_Value (COLUMNNAME_IsRefreshAllOnActivate, Boolean.valueOf(IsRefreshAllOnActivate));
	}

	/** Get Refresh All On Activate.
		@return Refresh all rows when user activates this tab, instead of refreshing only current row
	  */
	@Override
	public boolean isRefreshAllOnActivate () 
	{
		Object oo = get_Value(COLUMNNAME_IsRefreshAllOnActivate);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Refresh view on change events.
		@param IsRefreshViewOnChangeEvents Refresh view on change events	  */
	@Override
	public void setIsRefreshViewOnChangeEvents (boolean IsRefreshViewOnChangeEvents)
	{
		set_Value (COLUMNNAME_IsRefreshViewOnChangeEvents, Boolean.valueOf(IsRefreshViewOnChangeEvents));
	}

	/** Get Refresh view on change events.
		@return Refresh view on change events	  */
	@Override
	public boolean isRefreshViewOnChangeEvents () 
	{
		Object oo = get_Value(COLUMNNAME_IsRefreshViewOnChangeEvents);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Search Active.
		@param IsSearchActive 
		This mark activates the search button from toolbar
	  */
	@Override
	public void setIsSearchActive (boolean IsSearchActive)
	{
		set_Value (COLUMNNAME_IsSearchActive, Boolean.valueOf(IsSearchActive));
	}

	/** Get Search Active.
		@return This mark activates the search button from toolbar
	  */
	@Override
	public boolean isSearchActive () 
	{
		Object oo = get_Value(COLUMNNAME_IsSearchActive);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Collapse Search Panel.
		@param IsSearchCollapsed Collapse Search Panel	  */
	@Override
	public void setIsSearchCollapsed (boolean IsSearchCollapsed)
	{
		set_Value (COLUMNNAME_IsSearchCollapsed, Boolean.valueOf(IsSearchCollapsed));
	}

	/** Get Collapse Search Panel.
		@return Collapse Search Panel	  */
	@Override
	public boolean isSearchCollapsed () 
	{
		Object oo = get_Value(COLUMNNAME_IsSearchCollapsed);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Single Row Layout.
		@param IsSingleRow 
		Default for toggle between Single- and Multi-Row (Grid) Layout
	  */
	@Override
	public void setIsSingleRow (boolean IsSingleRow)
	{
		set_Value (COLUMNNAME_IsSingleRow, Boolean.valueOf(IsSingleRow));
	}

	/** Get Single Row Layout.
		@return Default for toggle between Single- and Multi-Row (Grid) Layout
	  */
	@Override
	public boolean isSingleRow () 
	{
		Object oo = get_Value(COLUMNNAME_IsSingleRow);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Order Tab.
		@param IsSortTab 
		The Tab determines the Order
	  */
	@Override
	public void setIsSortTab (boolean IsSortTab)
	{
		set_Value (COLUMNNAME_IsSortTab, Boolean.valueOf(IsSortTab));
	}

	/** Get Order Tab.
		@return The Tab determines the Order
	  */
	@Override
	public boolean isSortTab () 
	{
		Object oo = get_Value(COLUMNNAME_IsSortTab);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Übersetzungsregister.
		@param IsTranslationTab 
		This Tab contains translation information
	  */
	@Override
	public void setIsTranslationTab (boolean IsTranslationTab)
	{
		set_Value (COLUMNNAME_IsTranslationTab, Boolean.valueOf(IsTranslationTab));
	}

	/** Get Übersetzungsregister.
		@return This Tab contains translation information
	  */
	@Override
	public boolean isTranslationTab () 
	{
		Object oo = get_Value(COLUMNNAME_IsTranslationTab);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Max. Suchergebnisse.
		@param MaxQueryRecords 
		Wenn definiert können Suie nicht mehr Suchergebnisse abfragen - die Suchbedingungen müssen verändert werden, um eine geringere Anzahl zu erhalten
	  */
	@Override
	public void setMaxQueryRecords (int MaxQueryRecords)
	{
		set_Value (COLUMNNAME_MaxQueryRecords, Integer.valueOf(MaxQueryRecords));
	}

	/** Get Max. Suchergebnisse.
		@return Wenn definiert können Suie nicht mehr Suchergebnisse abfragen - die Suchbedingungen müssen verändert werden, um eine geringere Anzahl zu erhalten
	  */
	@Override
	public int getMaxQueryRecords () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_MaxQueryRecords);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Name.
		@param Name Name	  */
	@Override
	public void setName (java.lang.String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	/** Get Name.
		@return Name	  */
	@Override
	public java.lang.String getName () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Name);
	}

	/** Set Sql ORDER BY.
		@param OrderByClause 
		Fully qualified ORDER BY clause
	  */
	@Override
	public void setOrderByClause (java.lang.String OrderByClause)
	{
		set_Value (COLUMNNAME_OrderByClause, OrderByClause);
	}

	/** Get Sql ORDER BY.
		@return Fully qualified ORDER BY clause
	  */
	@Override
	public java.lang.String getOrderByClause () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_OrderByClause);
	}

	@Override
	public org.compiere.model.I_AD_Column getParent_Column() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_Parent_Column_ID, org.compiere.model.I_AD_Column.class);
	}

	@Override
	public void setParent_Column(org.compiere.model.I_AD_Column Parent_Column)
	{
		set_ValueFromPO(COLUMNNAME_Parent_Column_ID, org.compiere.model.I_AD_Column.class, Parent_Column);
	}

	/** Set Parent Column.
		@param Parent_Column_ID 
		The link column on the parent tab.
	  */
	@Override
	public void setParent_Column_ID (int Parent_Column_ID)
	{
		if (Parent_Column_ID < 1) 
			set_Value (COLUMNNAME_Parent_Column_ID, null);
		else 
			set_Value (COLUMNNAME_Parent_Column_ID, Integer.valueOf(Parent_Column_ID));
	}

	/** Get Parent Column.
		@return The link column on the parent tab.
	  */
	@Override
	public int getParent_Column_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Parent_Column_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Read Only Logic.
		@param ReadOnlyLogic 
		Logic to determine if field is read only (applies only when field is read-write)
	  */
	@Override
	public void setReadOnlyLogic (java.lang.String ReadOnlyLogic)
	{
		set_Value (COLUMNNAME_ReadOnlyLogic, ReadOnlyLogic);
	}

	/** Get Read Only Logic.
		@return Logic to determine if field is read only (applies only when field is read-write)
	  */
	@Override
	public java.lang.String getReadOnlyLogic () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ReadOnlyLogic);
	}

	/** Set Reihenfolge.
		@param SeqNo 
		Method of ordering records; lowest number comes first
	  */
	@Override
	public void setSeqNo (int SeqNo)
	{
		set_Value (COLUMNNAME_SeqNo, Integer.valueOf(SeqNo));
	}

	/** Get Reihenfolge.
		@return Method of ordering records; lowest number comes first
	  */
	@Override
	public int getSeqNo () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SeqNo);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Tab Level.
		@param TabLevel 
		Hierarchical Tab Level (0 = top)
	  */
	@Override
	public void setTabLevel (int TabLevel)
	{
		set_Value (COLUMNNAME_TabLevel, Integer.valueOf(TabLevel));
	}

	/** Get Tab Level.
		@return Hierarchical Tab Level (0 = top)
	  */
	@Override
	public int getTabLevel () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_TabLevel);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_AD_Tab getTemplate_Tab() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_Template_Tab_ID, org.compiere.model.I_AD_Tab.class);
	}

	@Override
	public void setTemplate_Tab(org.compiere.model.I_AD_Tab Template_Tab)
	{
		set_ValueFromPO(COLUMNNAME_Template_Tab_ID, org.compiere.model.I_AD_Tab.class, Template_Tab);
	}

	/** Set Template Tab.
		@param Template_Tab_ID Template Tab	  */
	@Override
	public void setTemplate_Tab_ID (int Template_Tab_ID)
	{
		if (Template_Tab_ID < 1) 
			set_Value (COLUMNNAME_Template_Tab_ID, null);
		else 
			set_Value (COLUMNNAME_Template_Tab_ID, Integer.valueOf(Template_Tab_ID));
	}

	/** Get Template Tab.
		@return Template Tab	  */
	@Override
	public int getTemplate_Tab_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Template_Tab_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Sql WHERE.
		@param WhereClause 
		Fully qualified SQL WHERE clause
	  */
	@Override
	public void setWhereClause (java.lang.String WhereClause)
	{
		set_Value (COLUMNNAME_WhereClause, WhereClause);
	}

	/** Get Sql WHERE.
		@return Fully qualified SQL WHERE clause
	  */
	@Override
	public java.lang.String getWhereClause () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_WhereClause);
	}
}