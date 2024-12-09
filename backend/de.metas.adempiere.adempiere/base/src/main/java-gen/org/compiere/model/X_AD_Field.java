<<<<<<< HEAD
/** Generated Model - DO NOT CHANGE */
=======
// Generated Model - DO NOT CHANGE
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
<<<<<<< HEAD

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
=======
import javax.annotation.Nullable;

/** Generated Model for AD_Field
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_AD_Field extends org.compiere.model.PO implements I_AD_Field, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -360763912L;

    /** Standard Constructor */
    public X_AD_Field (final Properties ctx, final int AD_Field_ID, @Nullable final String trxName)
    {
      super (ctx, AD_Field_ID, trxName);
    }

    /** Load Constructor */
    public X_AD_Field (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
    {
      super (ctx, rs, trxName);
    }


<<<<<<< HEAD
    /** Load Meta Data */
    @Override
    protected org.compiere.model.POInfo initPO (Properties ctx)
    {
      org.compiere.model.POInfo poi = org.compiere.model.POInfo.getPOInfo (ctx, Table_Name, get_TrxName());
      return poi;
    }

	@Override
	public org.compiere.model.I_AD_Column getAD_Column() throws RuntimeException
=======
	/** Load Meta Data */
	@Override
	protected org.compiere.model.POInfo initPO(final Properties ctx)
	{
		return org.compiere.model.POInfo.getPOInfo(Table_Name);
	}

	@Override
	public org.compiere.model.I_AD_Column getAD_Column()
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		return get_ValueAsPO(COLUMNNAME_AD_Column_ID, org.compiere.model.I_AD_Column.class);
	}

	@Override
<<<<<<< HEAD
	public void setAD_Column(org.compiere.model.I_AD_Column AD_Column)
=======
	public void setAD_Column(final org.compiere.model.I_AD_Column AD_Column)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		set_ValueFromPO(COLUMNNAME_AD_Column_ID, org.compiere.model.I_AD_Column.class, AD_Column);
	}

<<<<<<< HEAD
	/** Set Spalte.
		@param AD_Column_ID 
		Column in the table
	  */
	@Override
	public void setAD_Column_ID (int AD_Column_ID)
=======
	@Override
	public void setAD_Column_ID (final int AD_Column_ID)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		if (AD_Column_ID < 1) 
			set_Value (COLUMNNAME_AD_Column_ID, null);
		else 
<<<<<<< HEAD
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
=======
			set_Value (COLUMNNAME_AD_Column_ID, AD_Column_ID);
	}

	@Override
	public int getAD_Column_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Column_ID);
	}

	@Override
	public org.compiere.model.I_AD_FieldGroup getAD_FieldGroup()
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		return get_ValueAsPO(COLUMNNAME_AD_FieldGroup_ID, org.compiere.model.I_AD_FieldGroup.class);
	}

	@Override
<<<<<<< HEAD
	public void setAD_FieldGroup(org.compiere.model.I_AD_FieldGroup AD_FieldGroup)
=======
	public void setAD_FieldGroup(final org.compiere.model.I_AD_FieldGroup AD_FieldGroup)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		set_ValueFromPO(COLUMNNAME_AD_FieldGroup_ID, org.compiere.model.I_AD_FieldGroup.class, AD_FieldGroup);
	}

<<<<<<< HEAD
	/** Set Feld-Gruppe.
		@param AD_FieldGroup_ID 
		Logical grouping of fields
	  */
	@Override
	public void setAD_FieldGroup_ID (int AD_FieldGroup_ID)
=======
	@Override
	public void setAD_FieldGroup_ID (final int AD_FieldGroup_ID)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		if (AD_FieldGroup_ID < 1) 
			set_Value (COLUMNNAME_AD_FieldGroup_ID, null);
		else 
<<<<<<< HEAD
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
=======
			set_Value (COLUMNNAME_AD_FieldGroup_ID, AD_FieldGroup_ID);
	}

	@Override
	public int getAD_FieldGroup_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_FieldGroup_ID);
	}

	@Override
	public void setAD_Field_ID (final int AD_Field_ID)
	{
		if (AD_Field_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Field_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Field_ID, AD_Field_ID);
	}

	@Override
	public int getAD_Field_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Field_ID);
	}

	@Override
	public org.compiere.model.I_AD_Element getAD_Name()
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		return get_ValueAsPO(COLUMNNAME_AD_Name_ID, org.compiere.model.I_AD_Element.class);
	}

	@Override
<<<<<<< HEAD
	public void setAD_Name(org.compiere.model.I_AD_Element AD_Name)
=======
	public void setAD_Name(final org.compiere.model.I_AD_Element AD_Name)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		set_ValueFromPO(COLUMNNAME_AD_Name_ID, org.compiere.model.I_AD_Element.class, AD_Name);
	}

<<<<<<< HEAD
	/** Set AD_Name_ID.
		@param AD_Name_ID AD_Name_ID	  */
	@Override
	public void setAD_Name_ID (int AD_Name_ID)
=======
	@Override
	public void setAD_Name_ID (final int AD_Name_ID)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		if (AD_Name_ID < 1) 
			set_Value (COLUMNNAME_AD_Name_ID, null);
		else 
<<<<<<< HEAD
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
=======
			set_Value (COLUMNNAME_AD_Name_ID, AD_Name_ID);
	}

	@Override
	public int getAD_Name_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Name_ID);
	}

	@Override
	public org.compiere.model.I_AD_Reference getAD_Reference()
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		return get_ValueAsPO(COLUMNNAME_AD_Reference_ID, org.compiere.model.I_AD_Reference.class);
	}

	@Override
<<<<<<< HEAD
	public void setAD_Reference(org.compiere.model.I_AD_Reference AD_Reference)
=======
	public void setAD_Reference(final org.compiere.model.I_AD_Reference AD_Reference)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		set_ValueFromPO(COLUMNNAME_AD_Reference_ID, org.compiere.model.I_AD_Reference.class, AD_Reference);
	}

<<<<<<< HEAD
	/** Set Referenz.
		@param AD_Reference_ID 
		System Reference and Validation
	  */
	@Override
	public void setAD_Reference_ID (int AD_Reference_ID)
=======
	@Override
	public void setAD_Reference_ID (final int AD_Reference_ID)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		if (AD_Reference_ID < 1) 
			set_Value (COLUMNNAME_AD_Reference_ID, null);
		else 
<<<<<<< HEAD
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
=======
			set_Value (COLUMNNAME_AD_Reference_ID, AD_Reference_ID);
	}

	@Override
	public int getAD_Reference_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Reference_ID);
	}

	@Override
	public org.compiere.model.I_AD_Reference getAD_Reference_Value()
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		return get_ValueAsPO(COLUMNNAME_AD_Reference_Value_ID, org.compiere.model.I_AD_Reference.class);
	}

	@Override
<<<<<<< HEAD
	public void setAD_Reference_Value(org.compiere.model.I_AD_Reference AD_Reference_Value)
=======
	public void setAD_Reference_Value(final org.compiere.model.I_AD_Reference AD_Reference_Value)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		set_ValueFromPO(COLUMNNAME_AD_Reference_Value_ID, org.compiere.model.I_AD_Reference.class, AD_Reference_Value);
	}

<<<<<<< HEAD
	/** Set Referenzschlüssel.
		@param AD_Reference_Value_ID 
		Required to specify, if data type is Table or List
	  */
	@Override
	public void setAD_Reference_Value_ID (int AD_Reference_Value_ID)
=======
	@Override
	public void setAD_Reference_Value_ID (final int AD_Reference_Value_ID)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		if (AD_Reference_Value_ID < 1) 
			set_Value (COLUMNNAME_AD_Reference_Value_ID, null);
		else 
<<<<<<< HEAD
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
=======
			set_Value (COLUMNNAME_AD_Reference_Value_ID, AD_Reference_Value_ID);
	}

	@Override
	public int getAD_Reference_Value_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Reference_Value_ID);
	}

	@Override
	public org.compiere.model.I_AD_Tab getAD_Tab()
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		return get_ValueAsPO(COLUMNNAME_AD_Tab_ID, org.compiere.model.I_AD_Tab.class);
	}

	@Override
<<<<<<< HEAD
	public void setAD_Tab(org.compiere.model.I_AD_Tab AD_Tab)
=======
	public void setAD_Tab(final org.compiere.model.I_AD_Tab AD_Tab)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		set_ValueFromPO(COLUMNNAME_AD_Tab_ID, org.compiere.model.I_AD_Tab.class, AD_Tab);
	}

<<<<<<< HEAD
	/** Set Register.
		@param AD_Tab_ID 
		Tab within a Window
	  */
	@Override
	public void setAD_Tab_ID (int AD_Tab_ID)
=======
	@Override
	public void setAD_Tab_ID (final int AD_Tab_ID)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		if (AD_Tab_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Tab_ID, null);
		else 
<<<<<<< HEAD
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
=======
			set_ValueNoCheck (COLUMNNAME_AD_Tab_ID, AD_Tab_ID);
	}

	@Override
	public int getAD_Tab_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Tab_ID);
	}

	@Override
	public org.compiere.model.I_AD_Val_Rule getAD_Val_Rule()
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		return get_ValueAsPO(COLUMNNAME_AD_Val_Rule_ID, org.compiere.model.I_AD_Val_Rule.class);
	}

	@Override
<<<<<<< HEAD
	public void setAD_Val_Rule(org.compiere.model.I_AD_Val_Rule AD_Val_Rule)
=======
	public void setAD_Val_Rule(final org.compiere.model.I_AD_Val_Rule AD_Val_Rule)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		set_ValueFromPO(COLUMNNAME_AD_Val_Rule_ID, org.compiere.model.I_AD_Val_Rule.class, AD_Val_Rule);
	}

<<<<<<< HEAD
	/** Set Dynamische Validierung.
		@param AD_Val_Rule_ID 
		Dynamic Validation Rule
	  */
	@Override
	public void setAD_Val_Rule_ID (int AD_Val_Rule_ID)
=======
	@Override
	public void setAD_Val_Rule_ID (final int AD_Val_Rule_ID)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		if (AD_Val_Rule_ID < 1) 
			set_Value (COLUMNNAME_AD_Val_Rule_ID, null);
		else 
<<<<<<< HEAD
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
=======
			set_Value (COLUMNNAME_AD_Val_Rule_ID, AD_Val_Rule_ID);
	}

	@Override
	public int getAD_Val_Rule_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Val_Rule_ID);
	}

	@Override
	public void setColorLogic (final @Nullable java.lang.String ColorLogic)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		set_Value (COLUMNNAME_ColorLogic, ColorLogic);
	}

<<<<<<< HEAD
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
=======
	@Override
	public java.lang.String getColorLogic() 
	{
		return get_ValueAsString(COLUMNNAME_ColorLogic);
	}

	@Override
	public void setColumnDisplayLength (final int ColumnDisplayLength)
	{
		set_Value (COLUMNNAME_ColumnDisplayLength, ColumnDisplayLength);
	}

	@Override
	public int getColumnDisplayLength() 
	{
		return get_ValueAsInt(COLUMNNAME_ColumnDisplayLength);
	}

	@Override
	public void setDefaultValue (final @Nullable java.lang.String DefaultValue)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		set_Value (COLUMNNAME_DefaultValue, DefaultValue);
	}

<<<<<<< HEAD
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
=======
	@Override
	public java.lang.String getDefaultValue() 
	{
		return get_ValueAsString(COLUMNNAME_DefaultValue);
	}

	@Override
	public void setDescription (final @Nullable java.lang.String Description)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		set_Value (COLUMNNAME_Description, Description);
	}

<<<<<<< HEAD
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
=======
	@Override
	public java.lang.String getDescription() 
	{
		return get_ValueAsString(COLUMNNAME_Description);
	}

	@Override
	public void setDisplayLength (final int DisplayLength)
	{
		set_Value (COLUMNNAME_DisplayLength, DisplayLength);
	}

	@Override
	public int getDisplayLength() 
	{
		return get_ValueAsInt(COLUMNNAME_DisplayLength);
	}

	@Override
	public void setDisplayLogic (final @Nullable java.lang.String DisplayLogic)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		set_Value (COLUMNNAME_DisplayLogic, DisplayLogic);
	}

<<<<<<< HEAD
	/** Get Anzeigelogik.
		@return If the Field is displayed, the result determines if the field is actually displayed
	  */
	@Override
	public java.lang.String getDisplayLogic () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DisplayLogic);
=======
	@Override
	public java.lang.String getDisplayLogic() 
	{
		return get_ValueAsString(COLUMNNAME_DisplayLogic);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}

	/** 
	 * EntityType AD_Reference_ID=389
	 * Reference name: _EntityTypeNew
	 */
	public static final int ENTITYTYPE_AD_Reference_ID=389;
<<<<<<< HEAD
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
=======
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
	public void setFacetFilterSeqNo (final int FacetFilterSeqNo)
	{
		set_Value (COLUMNNAME_FacetFilterSeqNo, FacetFilterSeqNo);
	}

	@Override
	public int getFacetFilterSeqNo() 
	{
		return get_ValueAsInt(COLUMNNAME_FacetFilterSeqNo);
	}

	@Override
	public void setFilterDefaultValue (final @Nullable java.lang.String FilterDefaultValue)
	{
		set_Value (COLUMNNAME_FilterDefaultValue, FilterDefaultValue);
	}

	@Override
	public java.lang.String getFilterDefaultValue() 
	{
		return get_ValueAsString(COLUMNNAME_FilterDefaultValue);
	}

	/** 
	 * FilterOperator AD_Reference_ID=541241
	 * Reference name: FilterOperator
	 */
	public static final int FILTEROPERATOR_AD_Reference_ID=541241;
	/** EqualsOrLike = E */
	public static final String FILTEROPERATOR_EqualsOrLike = "E";
	/** Between = B */
	public static final String FILTEROPERATOR_Between = "B";
	/** NotNull = N */
	public static final String FILTEROPERATOR_NotNull = "N";
	@Override
	public void setFilterOperator (final @Nullable java.lang.String FilterOperator)
	{
		set_Value (COLUMNNAME_FilterOperator, FilterOperator);
	}

	@Override
	public java.lang.String getFilterOperator() 
	{
		return get_ValueAsString(COLUMNNAME_FilterOperator);
	}

	@Override
	public void setHelp (final @Nullable java.lang.String Help)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		set_Value (COLUMNNAME_Help, Help);
	}

<<<<<<< HEAD
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
=======
	@Override
	public java.lang.String getHelp() 
	{
		return get_ValueAsString(COLUMNNAME_Help);
	}

	@Override
	public void setIncludedTabHeight (final int IncludedTabHeight)
	{
		set_Value (COLUMNNAME_IncludedTabHeight, IncludedTabHeight);
	}

	@Override
	public int getIncludedTabHeight() 
	{
		return get_ValueAsInt(COLUMNNAME_IncludedTabHeight);
	}

	@Override
	public org.compiere.model.I_AD_Tab getIncluded_Tab()
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		return get_ValueAsPO(COLUMNNAME_Included_Tab_ID, org.compiere.model.I_AD_Tab.class);
	}

	@Override
<<<<<<< HEAD
	public void setIncluded_Tab(org.compiere.model.I_AD_Tab Included_Tab)
=======
	public void setIncluded_Tab(final org.compiere.model.I_AD_Tab Included_Tab)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		set_ValueFromPO(COLUMNNAME_Included_Tab_ID, org.compiere.model.I_AD_Tab.class, Included_Tab);
	}

<<<<<<< HEAD
	/** Set Included Tab.
		@param Included_Tab_ID 
		Included Tab in this Tab (Master Dateail)
	  */
	@Override
	public void setIncluded_Tab_ID (int Included_Tab_ID)
=======
	@Override
	public void setIncluded_Tab_ID (final int Included_Tab_ID)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		if (Included_Tab_ID < 1) 
			set_Value (COLUMNNAME_Included_Tab_ID, null);
		else 
<<<<<<< HEAD
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
=======
			set_Value (COLUMNNAME_Included_Tab_ID, Included_Tab_ID);
	}

	@Override
	public int getIncluded_Tab_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Included_Tab_ID);
	}

	@Override
	public void setInfoFactoryClass (final @Nullable java.lang.String InfoFactoryClass)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		set_Value (COLUMNNAME_InfoFactoryClass, InfoFactoryClass);
	}

<<<<<<< HEAD
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
=======
	@Override
	public java.lang.String getInfoFactoryClass() 
	{
		return get_ValueAsString(COLUMNNAME_InfoFactoryClass);
	}

	@Override
	public void setIsDisplayed (final boolean IsDisplayed)
	{
		set_Value (COLUMNNAME_IsDisplayed, IsDisplayed);
	}

	@Override
	public boolean isDisplayed() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsDisplayed);
	}

	@Override
	public void setIsDisplayedGrid (final boolean IsDisplayedGrid)
	{
		set_Value (COLUMNNAME_IsDisplayedGrid, IsDisplayedGrid);
	}

	@Override
	public boolean isDisplayedGrid() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsDisplayedGrid);
	}

	@Override
	public void setIsEncrypted (final boolean IsEncrypted)
	{
		set_Value (COLUMNNAME_IsEncrypted, IsEncrypted);
	}

	@Override
	public boolean isEncrypted() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsEncrypted);
	}

	/** 
	 * IsExcludeFromZoomTargets AD_Reference_ID=540528
	 * Reference name: Yes_No
	 */
	public static final int ISEXCLUDEFROMZOOMTARGETS_AD_Reference_ID=540528;
	/** Yes = Y */
	public static final String ISEXCLUDEFROMZOOMTARGETS_Yes = "Y";
	/** No = N */
	public static final String ISEXCLUDEFROMZOOMTARGETS_No = "N";
	@Override
	public void setIsExcludeFromZoomTargets (final @Nullable java.lang.String IsExcludeFromZoomTargets)
	{
		set_Value (COLUMNNAME_IsExcludeFromZoomTargets, IsExcludeFromZoomTargets);
	}

	@Override
	public java.lang.String getIsExcludeFromZoomTargets() 
	{
		return get_ValueAsString(COLUMNNAME_IsExcludeFromZoomTargets);
	}

	/** 
	 * IsFacetFilter AD_Reference_ID=540528
	 * Reference name: Yes_No
	 */
	public static final int ISFACETFILTER_AD_Reference_ID=540528;
	/** Yes = Y */
	public static final String ISFACETFILTER_Yes = "Y";
	/** No = N */
	public static final String ISFACETFILTER_No = "N";
	@Override
	public void setIsFacetFilter (final @Nullable java.lang.String IsFacetFilter)
	{
		set_Value (COLUMNNAME_IsFacetFilter, IsFacetFilter);
	}

	@Override
	public java.lang.String getIsFacetFilter() 
	{
		return get_ValueAsString(COLUMNNAME_IsFacetFilter);
	}

	@Override
	public void setIsFieldOnly (final boolean IsFieldOnly)
	{
		set_Value (COLUMNNAME_IsFieldOnly, IsFieldOnly);
	}

	@Override
	public boolean isFieldOnly() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsFieldOnly);
	}

	/** 
	 * IsFilterField AD_Reference_ID=319
	 * Reference name: _YesNo
	 */
	public static final int ISFILTERFIELD_AD_Reference_ID=319;
	/** Yes = Y */
	public static final String ISFILTERFIELD_Yes = "Y";
	/** No = N */
	public static final String ISFILTERFIELD_No = "N";
	@Override
	public void setIsFilterField (final @Nullable java.lang.String IsFilterField)
	{
		set_Value (COLUMNNAME_IsFilterField, IsFilterField);
	}

	@Override
	public java.lang.String getIsFilterField() 
	{
		return get_ValueAsString(COLUMNNAME_IsFilterField);
	}

	@Override
	public void setIsHeading (final boolean IsHeading)
	{
		set_Value (COLUMNNAME_IsHeading, IsHeading);
	}

	@Override
	public boolean isHeading() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsHeading);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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
<<<<<<< HEAD
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
=======
	@Override
	public void setIsMandatory (final @Nullable java.lang.String IsMandatory)
	{
		set_Value (COLUMNNAME_IsMandatory, IsMandatory);
	}

	@Override
	public java.lang.String getIsMandatory() 
	{
		return get_ValueAsString(COLUMNNAME_IsMandatory);
	}

	@Override
	public void setIsOverrideFilterDefaultValue (final boolean IsOverrideFilterDefaultValue)
	{
		set_Value (COLUMNNAME_IsOverrideFilterDefaultValue, IsOverrideFilterDefaultValue);
	}

	@Override
	public boolean isOverrideFilterDefaultValue() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsOverrideFilterDefaultValue);
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
	public void setIsSameLine (final boolean IsSameLine)
	{
		set_Value (COLUMNNAME_IsSameLine, IsSameLine);
	}

	@Override
	public boolean isSameLine() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsSameLine);
	}

	/** 
	 * IsShowFilterInline AD_Reference_ID=540528
	 * Reference name: Yes_No
	 */
	public static final int ISSHOWFILTERINLINE_AD_Reference_ID=540528;
	/** Yes = Y */
	public static final String ISSHOWFILTERINLINE_Yes = "Y";
	/** No = N */
	public static final String ISSHOWFILTERINLINE_No = "N";
	@Override
	public void setIsShowFilterInline (final @Nullable java.lang.String IsShowFilterInline)
	{
		set_Value (COLUMNNAME_IsShowFilterInline, IsShowFilterInline);
	}

	@Override
	public java.lang.String getIsShowFilterInline() 
	{
		return get_ValueAsString(COLUMNNAME_IsShowFilterInline);
	}

	@Override
	public void setMaxFacetsToFetch (final int MaxFacetsToFetch)
	{
		set_Value (COLUMNNAME_MaxFacetsToFetch, MaxFacetsToFetch);
	}

	@Override
	public int getMaxFacetsToFetch() 
	{
		return get_ValueAsInt(COLUMNNAME_MaxFacetsToFetch);
	}

	@Override
	public void setName (final @Nullable java.lang.String Name)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		set_Value (COLUMNNAME_Name, Name);
	}

<<<<<<< HEAD
	/** Get Name.
		@return Alphanumeric identifier of the entity
	  */
	@Override
	public java.lang.String getName () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Name);
=======
	@Override
	public java.lang.String getName() 
	{
		return get_ValueAsString(COLUMNNAME_Name);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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
<<<<<<< HEAD
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
=======
	@Override
	public void setObscureType (final @Nullable java.lang.String ObscureType)
	{
		set_Value (COLUMNNAME_ObscureType, ObscureType);
	}

	@Override
	public java.lang.String getObscureType() 
	{
		return get_ValueAsString(COLUMNNAME_ObscureType);
	}

	@Override
	public void setSelectionColumnSeqNo (final @Nullable BigDecimal SelectionColumnSeqNo)
	{
		set_Value (COLUMNNAME_SelectionColumnSeqNo, SelectionColumnSeqNo);
	}

	@Override
	public BigDecimal getSelectionColumnSeqNo() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_SelectionColumnSeqNo);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setSeqNo (final int SeqNo)
	{
		set_Value (COLUMNNAME_SeqNo, SeqNo);
	}

	@Override
	public int getSeqNo() 
	{
		return get_ValueAsInt(COLUMNNAME_SeqNo);
	}

	@Override
	public void setSeqNoGrid (final int SeqNoGrid)
	{
		set_Value (COLUMNNAME_SeqNoGrid, SeqNoGrid);
	}

	@Override
	public int getSeqNoGrid() 
	{
		return get_ValueAsInt(COLUMNNAME_SeqNoGrid);
	}

	@Override
	public void setSortNo (final @Nullable BigDecimal SortNo)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		set_Value (COLUMNNAME_SortNo, SortNo);
	}

<<<<<<< HEAD
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
=======
	@Override
	public BigDecimal getSortNo() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_SortNo);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setSpanX (final int SpanX)
	{
		set_Value (COLUMNNAME_SpanX, SpanX);
	}

	@Override
	public int getSpanX() 
	{
		return get_ValueAsInt(COLUMNNAME_SpanX);
	}

	@Override
	public void setSpanY (final int SpanY)
	{
		set_Value (COLUMNNAME_SpanY, SpanY);
	}

	@Override
	public int getSpanY() 
	{
		return get_ValueAsInt(COLUMNNAME_SpanY);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}
}