/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for AD_Column
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_AD_Column extends org.compiere.model.PO implements I_AD_Column, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1164764599L;

    /** Standard Constructor */
    public X_AD_Column (Properties ctx, int AD_Column_ID, String trxName)
    {
      super (ctx, AD_Column_ID, trxName);
      /** if (AD_Column_ID == 0)
        {
			setAD_Column_ID (0);
			setAD_Element_ID (0);
			setAD_Reference_ID (0);
			setAD_Table_ID (0);
			setAllowZoomTo (false); // N
			setColumnName (null);
			setDDL_NoForeignKey (false); // N
			setEntityType (null); // U
			setIsAlwaysUpdateable (false); // N
			setIsAutocomplete (false); // N
			setIsCalculated (false); // N
			setIsEncrypted (null); // N
			setIsForceIncludeInGeneratedModel (false); // N
			setIsIdentifier (false);
			setIsKey (false);
			setIsLazyLoading (false); // N
			setIsMandatory (false);
			setIsParent (false);
			setIsRangeFilter (false); // N
			setIsSelectionColumn (false);
			setIsShowFilterIncrementButtons (false); // N
			setIsStaleable (false); // N
			setIsTranslated (false);
			setIsUpdateable (true); // Y
			setIsUseDocSequence (false); // N
			setName (null);
			setVersion (BigDecimal.ZERO);
        } */
    }

    /** Load Constructor */
    public X_AD_Column (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Spalte.
		@param AD_Column_ID 
		Column in the table
	  */
	@Override
	public void setAD_Column_ID (int AD_Column_ID)
	{
		if (AD_Column_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Column_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Column_ID, Integer.valueOf(AD_Column_ID));
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
		System Element enables the central maintenance of column description and help.
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
		@return System Element enables the central maintenance of column description and help.
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
	public org.compiere.model.I_AD_Reference getAD_Reference() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_Reference_ID, org.compiere.model.I_AD_Reference.class);
	}

	@Override
	public void setAD_Reference(org.compiere.model.I_AD_Reference AD_Reference)
	{
		set_ValueFromPO(COLUMNNAME_AD_Reference_ID, org.compiere.model.I_AD_Reference.class, AD_Reference);
	}

	/** Set Referenz.
		@param AD_Reference_ID 
		System Reference and Validation
	  */
	@Override
	public void setAD_Reference_ID (int AD_Reference_ID)
	{
		if (AD_Reference_ID < 1) 
			set_Value (COLUMNNAME_AD_Reference_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Reference_ID, Integer.valueOf(AD_Reference_ID));
	}

	/** Get Referenz.
		@return System Reference and Validation
	  */
	@Override
	public int getAD_Reference_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Reference_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_AD_Reference getAD_Reference_Value() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_Reference_Value_ID, org.compiere.model.I_AD_Reference.class);
	}

	@Override
	public void setAD_Reference_Value(org.compiere.model.I_AD_Reference AD_Reference_Value)
	{
		set_ValueFromPO(COLUMNNAME_AD_Reference_Value_ID, org.compiere.model.I_AD_Reference.class, AD_Reference_Value);
	}

	/** Set Referenzschlüssel.
		@param AD_Reference_Value_ID 
		Required to specify, if data type is Table or List
	  */
	@Override
	public void setAD_Reference_Value_ID (int AD_Reference_Value_ID)
	{
		if (AD_Reference_Value_ID < 1) 
			set_Value (COLUMNNAME_AD_Reference_Value_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Reference_Value_ID, Integer.valueOf(AD_Reference_Value_ID));
	}

	/** Get Referenzschlüssel.
		@return Required to specify, if data type is Table or List
	  */
	@Override
	public int getAD_Reference_Value_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Reference_Value_ID);
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

	@Override
	public org.compiere.model.I_AD_Val_Rule getAD_Val_Rule() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_Val_Rule_ID, org.compiere.model.I_AD_Val_Rule.class);
	}

	@Override
	public void setAD_Val_Rule(org.compiere.model.I_AD_Val_Rule AD_Val_Rule)
	{
		set_ValueFromPO(COLUMNNAME_AD_Val_Rule_ID, org.compiere.model.I_AD_Val_Rule.class, AD_Val_Rule);
	}

	/** Set Dynamische Validierung.
		@param AD_Val_Rule_ID 
		Dynamic Validation Rule
	  */
	@Override
	public void setAD_Val_Rule_ID (int AD_Val_Rule_ID)
	{
		if (AD_Val_Rule_ID < 1) 
			set_Value (COLUMNNAME_AD_Val_Rule_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Val_Rule_ID, Integer.valueOf(AD_Val_Rule_ID));
	}

	/** Get Dynamische Validierung.
		@return Dynamic Validation Rule
	  */
	@Override
	public int getAD_Val_Rule_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Val_Rule_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Allow Zoom To.
		@param AllowZoomTo Allow Zoom To	  */
	@Override
	public void setAllowZoomTo (boolean AllowZoomTo)
	{
		set_Value (COLUMNNAME_AllowZoomTo, Boolean.valueOf(AllowZoomTo));
	}

	/** Get Allow Zoom To.
		@return Allow Zoom To	  */
	@Override
	public boolean isAllowZoomTo () 
	{
		Object oo = get_Value(COLUMNNAME_AllowZoomTo);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Spaltenname.
		@param ColumnName 
		Name of the column in the database
	  */
	@Override
	public void setColumnName (java.lang.String ColumnName)
	{
		set_Value (COLUMNNAME_ColumnName, ColumnName);
	}

	/** Get Spaltenname.
		@return Name of the column in the database
	  */
	@Override
	public java.lang.String getColumnName () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ColumnName);
	}

	/** Set Column SQL.
		@param ColumnSQL 
		Virtual Column (r/o)
	  */
	@Override
	public void setColumnSQL (java.lang.String ColumnSQL)
	{
		set_Value (COLUMNNAME_ColumnSQL, ColumnSQL);
	}

	/** Get Column SQL.
		@return Virtual Column (r/o)
	  */
	@Override
	public java.lang.String getColumnSQL () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ColumnSQL);
	}

	/** Set No Foreign Key.
		@param DDL_NoForeignKey 
		Don't create database foreign key (if applicable) for this column 
	  */
	@Override
	public void setDDL_NoForeignKey (boolean DDL_NoForeignKey)
	{
		set_Value (COLUMNNAME_DDL_NoForeignKey, Boolean.valueOf(DDL_NoForeignKey));
	}

	/** Get No Foreign Key.
		@return Don't create database foreign key (if applicable) for this column 
	  */
	@Override
	public boolean isDDL_NoForeignKey () 
	{
		Object oo = get_Value(COLUMNNAME_DDL_NoForeignKey);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Standardwert-Logik.
		@param DefaultValue 
		Default value hierarchy, separated by ;
	  */
	@Override
	public void setDefaultValue (java.lang.String DefaultValue)
	{
		set_Value (COLUMNNAME_DefaultValue, DefaultValue);
	}

	/** Get Standardwert-Logik.
		@return Default value hierarchy, separated by ;
	  */
	@Override
	public java.lang.String getDefaultValue () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DefaultValue);
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

	/** Set Länge.
		@param FieldLength 
		Length of the column in the database
	  */
	@Override
	public void setFieldLength (int FieldLength)
	{
		set_Value (COLUMNNAME_FieldLength, Integer.valueOf(FieldLength));
	}

	/** Get Länge.
		@return Length of the column in the database
	  */
	@Override
	public int getFieldLength () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_FieldLength);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Filter Default Value.
		@param FilterDefaultValue Filter Default Value	  */
	@Override
	public void setFilterDefaultValue (java.lang.String FilterDefaultValue)
	{
		set_Value (COLUMNNAME_FilterDefaultValue, FilterDefaultValue);
	}

	/** Get Filter Default Value.
		@return Filter Default Value	  */
	@Override
	public java.lang.String getFilterDefaultValue () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_FilterDefaultValue);
	}

	/** Set Format Pattern.
		@param FormatPattern 
		The pattern used to format a number or date.
	  */
	@Override
	public void setFormatPattern (java.lang.String FormatPattern)
	{
		set_Value (COLUMNNAME_FormatPattern, FormatPattern);
	}

	/** Get Format Pattern.
		@return The pattern used to format a number or date.
	  */
	@Override
	public java.lang.String getFormatPattern () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_FormatPattern);
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

	/** Set Info Factory Class.
		@param InfoFactoryClass 
		Fully qualified class name that implements the InfoFactory interface
	  */
	@Override
	public void setInfoFactoryClass (java.lang.String InfoFactoryClass)
	{
		set_Value (COLUMNNAME_InfoFactoryClass, InfoFactoryClass);
	}

	/** Get Info Factory Class.
		@return Fully qualified class name that implements the InfoFactory interface
	  */
	@Override
	public java.lang.String getInfoFactoryClass () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_InfoFactoryClass);
	}

	/** Set Allow Logging.
		@param IsAllowLogging 
		Determine if a column must be recorded into the change log
	  */
	@Override
	public void setIsAllowLogging (boolean IsAllowLogging)
	{
		set_Value (COLUMNNAME_IsAllowLogging, Boolean.valueOf(IsAllowLogging));
	}

	/** Get Allow Logging.
		@return Determine if a column must be recorded into the change log
	  */
	@Override
	public boolean isAllowLogging () 
	{
		Object oo = get_Value(COLUMNNAME_IsAllowLogging);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Always Updateable.
		@param IsAlwaysUpdateable 
		The column is always updateable, even if the record is not active or processed
	  */
	@Override
	public void setIsAlwaysUpdateable (boolean IsAlwaysUpdateable)
	{
		set_Value (COLUMNNAME_IsAlwaysUpdateable, Boolean.valueOf(IsAlwaysUpdateable));
	}

	/** Get Always Updateable.
		@return The column is always updateable, even if the record is not active or processed
	  */
	@Override
	public boolean isAlwaysUpdateable () 
	{
		Object oo = get_Value(COLUMNNAME_IsAlwaysUpdateable);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Autocomplete.
		@param IsAutocomplete 
		Automatic completion for textfields
	  */
	@Override
	public void setIsAutocomplete (boolean IsAutocomplete)
	{
		set_Value (COLUMNNAME_IsAutocomplete, Boolean.valueOf(IsAutocomplete));
	}

	/** Get Autocomplete.
		@return Automatic completion for textfields
	  */
	@Override
	public boolean isAutocomplete () 
	{
		Object oo = get_Value(COLUMNNAME_IsAutocomplete);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Berechnet.
		@param IsCalculated 
		The value is calculated by the system
	  */
	@Override
	public void setIsCalculated (boolean IsCalculated)
	{
		set_Value (COLUMNNAME_IsCalculated, Boolean.valueOf(IsCalculated));
	}

	/** Get Berechnet.
		@return The value is calculated by the system
	  */
	@Override
	public boolean isCalculated () 
	{
		Object oo = get_Value(COLUMNNAME_IsCalculated);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** 
	 * IsEncrypted AD_Reference_ID=354
	 * Reference name: AD_Column Encrypted
	 */
	public static final int ISENCRYPTED_AD_Reference_ID=354;
	/** Encrypted = Y */
	public static final String ISENCRYPTED_Encrypted = "Y";
	/** Nicht verschlüsselt = N */
	public static final String ISENCRYPTED_NichtVerschluesselt = "N";
	/** Set Encrypted.
		@param IsEncrypted 
		Display or Storage is encrypted
	  */
	@Override
	public void setIsEncrypted (java.lang.String IsEncrypted)
	{

		set_Value (COLUMNNAME_IsEncrypted, IsEncrypted);
	}

	/** Get Encrypted.
		@return Display or Storage is encrypted
	  */
	@Override
	public java.lang.String getIsEncrypted () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_IsEncrypted);
	}

	/** Set Force include in generated model.
		@param IsForceIncludeInGeneratedModel 
		Force including this column in java generated interface and class
	  */
	@Override
	public void setIsForceIncludeInGeneratedModel (boolean IsForceIncludeInGeneratedModel)
	{
		set_Value (COLUMNNAME_IsForceIncludeInGeneratedModel, Boolean.valueOf(IsForceIncludeInGeneratedModel));
	}

	/** Get Force include in generated model.
		@return Force including this column in java generated interface and class
	  */
	@Override
	public boolean isForceIncludeInGeneratedModel () 
	{
		Object oo = get_Value(COLUMNNAME_IsForceIncludeInGeneratedModel);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set GenericZoom Quellspalte.
		@param IsGenericZoomOrigin 
		Werden beim GenericZoom Referenzen auf diese Spalte beachtet?
	  */
	@Override
	public void setIsGenericZoomOrigin (boolean IsGenericZoomOrigin)
	{
		set_Value (COLUMNNAME_IsGenericZoomOrigin, Boolean.valueOf(IsGenericZoomOrigin));
	}

	/** Get GenericZoom Quellspalte.
		@return Werden beim GenericZoom Referenzen auf diese Spalte beachtet?
	  */
	@Override
	public boolean isGenericZoomOrigin () 
	{
		Object oo = get_Value(COLUMNNAME_IsGenericZoomOrigin);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Identifier.
		@param IsIdentifier 
		This column is part of the record identifier
	  */
	@Override
	public void setIsIdentifier (boolean IsIdentifier)
	{
		set_Value (COLUMNNAME_IsIdentifier, Boolean.valueOf(IsIdentifier));
	}

	/** Get Identifier.
		@return This column is part of the record identifier
	  */
	@Override
	public boolean isIdentifier () 
	{
		Object oo = get_Value(COLUMNNAME_IsIdentifier);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Schlüssel-Spalte.
		@param IsKey 
		This column is the key in this table
	  */
	@Override
	public void setIsKey (boolean IsKey)
	{
		set_Value (COLUMNNAME_IsKey, Boolean.valueOf(IsKey));
	}

	/** Get Schlüssel-Spalte.
		@return This column is the key in this table
	  */
	@Override
	public boolean isKey () 
	{
		Object oo = get_Value(COLUMNNAME_IsKey);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Lazy loading.
		@param IsLazyLoading Lazy loading	  */
	@Override
	public void setIsLazyLoading (boolean IsLazyLoading)
	{
		set_Value (COLUMNNAME_IsLazyLoading, Boolean.valueOf(IsLazyLoading));
	}

	/** Get Lazy loading.
		@return Lazy loading	  */
	@Override
	public boolean isLazyLoading () 
	{
		Object oo = get_Value(COLUMNNAME_IsLazyLoading);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Pflichtangabe.
		@param IsMandatory 
		Data entry is required in this column
	  */
	@Override
	public void setIsMandatory (boolean IsMandatory)
	{
		set_Value (COLUMNNAME_IsMandatory, Boolean.valueOf(IsMandatory));
	}

	/** Get Pflichtangabe.
		@return Data entry is required in this column
	  */
	@Override
	public boolean isMandatory () 
	{
		Object oo = get_Value(COLUMNNAME_IsMandatory);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Parent link column.
		@param IsParent 
		This column is a link to the parent table (e.g. header from lines) - incl. Association key columns
	  */
	@Override
	public void setIsParent (boolean IsParent)
	{
		set_Value (COLUMNNAME_IsParent, Boolean.valueOf(IsParent));
	}

	/** Get Parent link column.
		@return This column is a link to the parent table (e.g. header from lines) - incl. Association key columns
	  */
	@Override
	public boolean isParent () 
	{
		Object oo = get_Value(COLUMNNAME_IsParent);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Range filter.
		@param IsRangeFilter 
		Check if the filter by this column shall render a range component
	  */
	@Override
	public void setIsRangeFilter (boolean IsRangeFilter)
	{
		set_Value (COLUMNNAME_IsRangeFilter, Boolean.valueOf(IsRangeFilter));
	}

	/** Get Range filter.
		@return Check if the filter by this column shall render a range component
	  */
	@Override
	public boolean isRangeFilter () 
	{
		Object oo = get_Value(COLUMNNAME_IsRangeFilter);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Selection Column.
		@param IsSelectionColumn 
		Is this column used for finding rows in windows
	  */
	@Override
	public void setIsSelectionColumn (boolean IsSelectionColumn)
	{
		set_Value (COLUMNNAME_IsSelectionColumn, Boolean.valueOf(IsSelectionColumn));
	}

	/** Get Selection Column.
		@return Is this column used for finding rows in windows
	  */
	@Override
	public boolean isSelectionColumn () 
	{
		Object oo = get_Value(COLUMNNAME_IsSelectionColumn);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Filter +/- buttons.
		@param IsShowFilterIncrementButtons 
		Show filter increment/decrement buttons
	  */
	@Override
	public void setIsShowFilterIncrementButtons (boolean IsShowFilterIncrementButtons)
	{
		set_Value (COLUMNNAME_IsShowFilterIncrementButtons, Boolean.valueOf(IsShowFilterIncrementButtons));
	}

	/** Get Filter +/- buttons.
		@return Show filter increment/decrement buttons
	  */
	@Override
	public boolean isShowFilterIncrementButtons () 
	{
		Object oo = get_Value(COLUMNNAME_IsShowFilterIncrementButtons);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Staleable.
		@param IsStaleable 
		If checked then this column is staleable and record needs to be loaded after save.
	  */
	@Override
	public void setIsStaleable (boolean IsStaleable)
	{
		set_Value (COLUMNNAME_IsStaleable, Boolean.valueOf(IsStaleable));
	}

	/** Get Staleable.
		@return If checked then this column is staleable and record needs to be loaded after save.
	  */
	@Override
	public boolean isStaleable () 
	{
		Object oo = get_Value(COLUMNNAME_IsStaleable);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Synchronize Database.
		@param IsSyncDatabase 
		Change database table definition when changing dictionary definition
	  */
	@Override
	public void setIsSyncDatabase (java.lang.String IsSyncDatabase)
	{
		set_Value (COLUMNNAME_IsSyncDatabase, IsSyncDatabase);
	}

	/** Get Synchronize Database.
		@return Change database table definition when changing dictionary definition
	  */
	@Override
	public java.lang.String getIsSyncDatabase () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_IsSyncDatabase);
	}

	/** Set Übersetzt.
		@param IsTranslated 
		This column is translated
	  */
	@Override
	public void setIsTranslated (boolean IsTranslated)
	{
		set_Value (COLUMNNAME_IsTranslated, Boolean.valueOf(IsTranslated));
	}

	/** Get Übersetzt.
		@return This column is translated
	  */
	@Override
	public boolean isTranslated () 
	{
		Object oo = get_Value(COLUMNNAME_IsTranslated);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Updateable.
		@param IsUpdateable 
		Determines, if the field can be updated
	  */
	@Override
	public void setIsUpdateable (boolean IsUpdateable)
	{
		set_Value (COLUMNNAME_IsUpdateable, Boolean.valueOf(IsUpdateable));
	}

	/** Get Updateable.
		@return Determines, if the field can be updated
	  */
	@Override
	public boolean isUpdateable () 
	{
		Object oo = get_Value(COLUMNNAME_IsUpdateable);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Autogenerate document sequence.
		@param IsUseDocSequence 
		In case the field is empty, use standard document sequence to set the value
	  */
	@Override
	public void setIsUseDocSequence (boolean IsUseDocSequence)
	{
		set_Value (COLUMNNAME_IsUseDocSequence, Boolean.valueOf(IsUseDocSequence));
	}

	/** Get Autogenerate document sequence.
		@return In case the field is empty, use standard document sequence to set the value
	  */
	@Override
	public boolean isUseDocSequence () 
	{
		Object oo = get_Value(COLUMNNAME_IsUseDocSequence);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Mandatory Logic.
		@param MandatoryLogic Mandatory Logic	  */
	@Override
	public void setMandatoryLogic (java.lang.String MandatoryLogic)
	{
		set_Value (COLUMNNAME_MandatoryLogic, MandatoryLogic);
	}

	/** Get Mandatory Logic.
		@return Mandatory Logic	  */
	@Override
	public java.lang.String getMandatoryLogic () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_MandatoryLogic);
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

	/** Set Selection column ordering.
		@param SelectionColumnSeqNo Selection column ordering	  */
	@Override
	public void setSelectionColumnSeqNo (int SelectionColumnSeqNo)
	{
		set_Value (COLUMNNAME_SelectionColumnSeqNo, Integer.valueOf(SelectionColumnSeqNo));
	}

	/** Get Selection column ordering.
		@return Selection column ordering	  */
	@Override
	public int getSelectionColumnSeqNo () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SelectionColumnSeqNo);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Max. Wert.
		@param ValueMax 
		Maximum Value for a field
	  */
	@Override
	public void setValueMax (java.lang.String ValueMax)
	{
		set_Value (COLUMNNAME_ValueMax, ValueMax);
	}

	/** Get Max. Wert.
		@return Maximum Value for a field
	  */
	@Override
	public java.lang.String getValueMax () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ValueMax);
	}

	/** Set Min. Wert.
		@param ValueMin 
		Minimum Value for a field
	  */
	@Override
	public void setValueMin (java.lang.String ValueMin)
	{
		set_Value (COLUMNNAME_ValueMin, ValueMin);
	}

	/** Get Min. Wert.
		@return Minimum Value for a field
	  */
	@Override
	public java.lang.String getValueMin () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ValueMin);
	}

	/** Set Version.
		@param Version 
		Version of the table definition
	  */
	@Override
	public void setVersion (java.math.BigDecimal Version)
	{
		set_Value (COLUMNNAME_Version, Version);
	}

	/** Get Version.
		@return Version of the table definition
	  */
	@Override
	public java.math.BigDecimal getVersion () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Version);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Value Format.
		@param VFormat 
		Format of the value; Can contain fixed format elements, Variables: "_lLoOaAcCa09"
	  */
	@Override
	public void setVFormat (java.lang.String VFormat)
	{
		set_Value (COLUMNNAME_VFormat, VFormat);
	}

	/** Get Value Format.
		@return Format of the value; Can contain fixed format elements, Variables: "_lLoOaAcCa09"
	  */
	@Override
	public java.lang.String getVFormat () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_VFormat);
	}
}