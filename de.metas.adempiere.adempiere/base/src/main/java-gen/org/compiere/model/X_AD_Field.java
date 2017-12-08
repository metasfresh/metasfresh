/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for AD_Field
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_AD_Field extends org.compiere.model.PO implements I_AD_Field, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 244841217L;

    /** Standard Constructor */
    public X_AD_Field (Properties ctx, int AD_Field_ID, String trxName)
    {
      super (ctx, AD_Field_ID, trxName);
      /** if (AD_Field_ID == 0)
        {
			setAD_Column_ID (0);
			setAD_Field_ID (0);
			setAD_Tab_ID (0);
			setEntityType (null); // U
			setIsDisplayed (true); // Y
			setIsDisplayedGrid (true); // Y
			setIsEncrypted (false);
			setIsFieldOnly (false);
			setIsHeading (false);
			setIsReadOnly (false);
			setIsSameLine (false);
			setName (null);
			setSpanX (0); // 1
			setSpanY (0); // 1
        } */
    }

    /** Load Constructor */
    public X_AD_Field (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Feld.
		@param AD_Field_ID 
		Field on a database table
	  */
	@Override
	public void setAD_Field_ID (int AD_Field_ID)
	{
		if (AD_Field_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Field_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Field_ID, Integer.valueOf(AD_Field_ID));
	}

	/** Get Feld.
		@return Field on a database table
	  */
	@Override
	public int getAD_Field_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Field_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_AD_FieldGroup getAD_FieldGroup() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_FieldGroup_ID, org.compiere.model.I_AD_FieldGroup.class);
	}

	@Override
	public void setAD_FieldGroup(org.compiere.model.I_AD_FieldGroup AD_FieldGroup)
	{
		set_ValueFromPO(COLUMNNAME_AD_FieldGroup_ID, org.compiere.model.I_AD_FieldGroup.class, AD_FieldGroup);
	}

	/** Set Feld-Gruppe.
		@param AD_FieldGroup_ID 
		Logical grouping of fields
	  */
	@Override
	public void setAD_FieldGroup_ID (int AD_FieldGroup_ID)
	{
		if (AD_FieldGroup_ID < 1) 
			set_Value (COLUMNNAME_AD_FieldGroup_ID, null);
		else 
			set_Value (COLUMNNAME_AD_FieldGroup_ID, Integer.valueOf(AD_FieldGroup_ID));
	}

	/** Get Feld-Gruppe.
		@return Logical grouping of fields
	  */
	@Override
	public int getAD_FieldGroup_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_FieldGroup_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_AD_Element getAD_Name() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_Name_ID, org.compiere.model.I_AD_Element.class);
	}

	@Override
	public void setAD_Name(org.compiere.model.I_AD_Element AD_Name)
	{
		set_ValueFromPO(COLUMNNAME_AD_Name_ID, org.compiere.model.I_AD_Element.class, AD_Name);
	}

	/** Set AD_Name_ID.
		@param AD_Name_ID AD_Name_ID	  */
	@Override
	public void setAD_Name_ID (int AD_Name_ID)
	{
		if (AD_Name_ID < 1) 
			set_Value (COLUMNNAME_AD_Name_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Name_ID, Integer.valueOf(AD_Name_ID));
	}

	/** Get AD_Name_ID.
		@return AD_Name_ID	  */
	@Override
	public int getAD_Name_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Name_ID);
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

	/** Set Color Logic.
		@param ColorLogic 
		Color used for background
	  */
	@Override
	public void setColorLogic (java.lang.String ColorLogic)
	{
		set_Value (COLUMNNAME_ColorLogic, ColorLogic);
	}

	/** Get Color Logic.
		@return Color used for background
	  */
	@Override
	public java.lang.String getColorLogic () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ColorLogic);
	}

	/** Set Column Display Length.
		@param ColumnDisplayLength 
		Column display length for grid mode
	  */
	@Override
	public void setColumnDisplayLength (int ColumnDisplayLength)
	{
		set_Value (COLUMNNAME_ColumnDisplayLength, Integer.valueOf(ColumnDisplayLength));
	}

	/** Get Column Display Length.
		@return Column display length for grid mode
	  */
	@Override
	public int getColumnDisplayLength () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ColumnDisplayLength);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Anzeigelänge.
		@param DisplayLength 
		Length of the display in characters
	  */
	@Override
	public void setDisplayLength (int DisplayLength)
	{
		set_Value (COLUMNNAME_DisplayLength, Integer.valueOf(DisplayLength));
	}

	/** Get Anzeigelänge.
		@return Length of the display in characters
	  */
	@Override
	public int getDisplayLength () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DisplayLength);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Included Tab Height.
		@param IncludedTabHeight Included Tab Height	  */
	@Override
	public void setIncludedTabHeight (int IncludedTabHeight)
	{
		set_Value (COLUMNNAME_IncludedTabHeight, Integer.valueOf(IncludedTabHeight));
	}

	/** Get Included Tab Height.
		@return Included Tab Height	  */
	@Override
	public int getIncludedTabHeight () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_IncludedTabHeight);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Displayed.
		@param IsDisplayed 
		Determines, if this field is displayed
	  */
	@Override
	public void setIsDisplayed (boolean IsDisplayed)
	{
		set_Value (COLUMNNAME_IsDisplayed, Boolean.valueOf(IsDisplayed));
	}

	/** Get Displayed.
		@return Determines, if this field is displayed
	  */
	@Override
	public boolean isDisplayed () 
	{
		Object oo = get_Value(COLUMNNAME_IsDisplayed);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Displayed in Grid.
		@param IsDisplayedGrid 
		Determines, if this field is displayed in grid mode
	  */
	@Override
	public void setIsDisplayedGrid (boolean IsDisplayedGrid)
	{
		set_Value (COLUMNNAME_IsDisplayedGrid, Boolean.valueOf(IsDisplayedGrid));
	}

	/** Get Displayed in Grid.
		@return Determines, if this field is displayed in grid mode
	  */
	@Override
	public boolean isDisplayedGrid () 
	{
		Object oo = get_Value(COLUMNNAME_IsDisplayedGrid);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Encrypted.
		@param IsEncrypted 
		Display or Storage is encrypted
	  */
	@Override
	public void setIsEncrypted (boolean IsEncrypted)
	{
		set_Value (COLUMNNAME_IsEncrypted, Boolean.valueOf(IsEncrypted));
	}

	/** Get Encrypted.
		@return Display or Storage is encrypted
	  */
	@Override
	public boolean isEncrypted () 
	{
		Object oo = get_Value(COLUMNNAME_IsEncrypted);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Field Only.
		@param IsFieldOnly 
		Label is not displayed
	  */
	@Override
	public void setIsFieldOnly (boolean IsFieldOnly)
	{
		set_Value (COLUMNNAME_IsFieldOnly, Boolean.valueOf(IsFieldOnly));
	}

	/** Get Field Only.
		@return Label is not displayed
	  */
	@Override
	public boolean isFieldOnly () 
	{
		Object oo = get_Value(COLUMNNAME_IsFieldOnly);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Heading only.
		@param IsHeading 
		Field without Column - Only label is displayed
	  */
	@Override
	public void setIsHeading (boolean IsHeading)
	{
		set_Value (COLUMNNAME_IsHeading, Boolean.valueOf(IsHeading));
	}

	/** Get Heading only.
		@return Field without Column - Only label is displayed
	  */
	@Override
	public boolean isHeading () 
	{
		Object oo = get_Value(COLUMNNAME_IsHeading);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** 
	 * IsMandatory AD_Reference_ID=319
	 * Reference name: _YesNo
	 */
	public static final int ISMANDATORY_AD_Reference_ID=319;
	/** Yes = Y */
	public static final String ISMANDATORY_Yes = "Y";
	/** No = N */
	public static final String ISMANDATORY_No = "N";
	/** Set Pflichtangabe.
		@param IsMandatory 
		Data entry is required in this column
	  */
	@Override
	public void setIsMandatory (java.lang.String IsMandatory)
	{

		set_Value (COLUMNNAME_IsMandatory, IsMandatory);
	}

	/** Get Pflichtangabe.
		@return Data entry is required in this column
	  */
	@Override
	public java.lang.String getIsMandatory () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_IsMandatory);
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

	/** Set Same Line.
		@param IsSameLine 
		Displayed on same line as previous field
	  */
	@Override
	public void setIsSameLine (boolean IsSameLine)
	{
		set_Value (COLUMNNAME_IsSameLine, Boolean.valueOf(IsSameLine));
	}

	/** Get Same Line.
		@return Displayed on same line as previous field
	  */
	@Override
	public boolean isSameLine () 
	{
		Object oo = get_Value(COLUMNNAME_IsSameLine);
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

	/** 
	 * ObscureType AD_Reference_ID=291
	 * Reference name: AD_Field ObscureType
	 */
	public static final int OBSCURETYPE_AD_Reference_ID=291;
	/** Obscure Digits but last 4 = 904 */
	public static final String OBSCURETYPE_ObscureDigitsButLast4 = "904";
	/** Obscure Digits but first/last 4 = 944 */
	public static final String OBSCURETYPE_ObscureDigitsButFirstLast4 = "944";
	/** Obscure AlphaNumeric but first/last 4 = A44 */
	public static final String OBSCURETYPE_ObscureAlphaNumericButFirstLast4 = "A44";
	/** Obscure AlphaNumeric but last 4 = A04 */
	public static final String OBSCURETYPE_ObscureAlphaNumericButLast4 = "A04";
	/** Set Obscure.
		@param ObscureType 
		Type of obscuring the data (limiting the display)
	  */
	@Override
	public void setObscureType (java.lang.String ObscureType)
	{

		set_Value (COLUMNNAME_ObscureType, ObscureType);
	}

	/** Get Obscure.
		@return Type of obscuring the data (limiting the display)
	  */
	@Override
	public java.lang.String getObscureType () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ObscureType);
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

	/** Set Reihenfolge (grid).
		@param SeqNoGrid 
		Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst
	  */
	@Override
	public void setSeqNoGrid (int SeqNoGrid)
	{
		set_Value (COLUMNNAME_SeqNoGrid, Integer.valueOf(SeqNoGrid));
	}

	/** Get Reihenfolge (grid).
		@return Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst
	  */
	@Override
	public int getSeqNoGrid () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SeqNoGrid);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Record Sort No.
		@param SortNo 
		Determines in what order the records are displayed
	  */
	@Override
	public void setSortNo (java.math.BigDecimal SortNo)
	{
		set_Value (COLUMNNAME_SortNo, SortNo);
	}

	/** Get Record Sort No.
		@return Determines in what order the records are displayed
	  */
	@Override
	public java.math.BigDecimal getSortNo () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_SortNo);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Column span.
		@param SpanX 
		Number of columns spanned
	  */
	@Override
	public void setSpanX (int SpanX)
	{
		set_Value (COLUMNNAME_SpanX, Integer.valueOf(SpanX));
	}

	/** Get Column span.
		@return Number of columns spanned
	  */
	@Override
	public int getSpanX () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SpanX);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Row Span.
		@param SpanY 
		Number of rows spanned
	  */
	@Override
	public void setSpanY (int SpanY)
	{
		set_Value (COLUMNNAME_SpanY, Integer.valueOf(SpanY));
	}

	/** Get Row Span.
		@return Number of rows spanned
	  */
	@Override
	public int getSpanY () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SpanY);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}