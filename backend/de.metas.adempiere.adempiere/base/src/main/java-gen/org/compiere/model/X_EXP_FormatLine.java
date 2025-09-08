// Generated Model - DO NOT CHANGE
package org.compiere.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for EXP_FormatLine
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_EXP_FormatLine extends org.compiere.model.PO implements I_EXP_FormatLine, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 303743353L;

    /** Standard Constructor */
    public X_EXP_FormatLine (final Properties ctx, final int EXP_FormatLine_ID, @Nullable final String trxName)
    {
      super (ctx, EXP_FormatLine_ID, trxName);
    }

    /** Load Constructor */
    public X_EXP_FormatLine (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public org.compiere.model.I_AD_Column getAD_Column()
	{
		return get_ValueAsPO(COLUMNNAME_AD_Column_ID, org.compiere.model.I_AD_Column.class);
	}

	@Override
	public void setAD_Column(final org.compiere.model.I_AD_Column AD_Column)
	{
		set_ValueFromPO(COLUMNNAME_AD_Column_ID, org.compiere.model.I_AD_Column.class, AD_Column);
	}

	@Override
	public void setAD_Column_ID (final int AD_Column_ID)
	{
		if (AD_Column_ID < 1) 
			set_Value (COLUMNNAME_AD_Column_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Column_ID, AD_Column_ID);
	}

	@Override
	public int getAD_Column_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Column_ID);
	}

	@Override
	public org.compiere.model.I_AD_Reference getAD_Reference()
	{
		return get_ValueAsPO(COLUMNNAME_AD_Reference_ID, org.compiere.model.I_AD_Reference.class);
	}

	@Override
	public void setAD_Reference(final org.compiere.model.I_AD_Reference AD_Reference)
	{
		set_ValueFromPO(COLUMNNAME_AD_Reference_ID, org.compiere.model.I_AD_Reference.class, AD_Reference);
	}

	@Override
	public void setAD_Reference_ID (final int AD_Reference_ID)
	{
		throw new IllegalArgumentException ("AD_Reference_ID is virtual column");	}

	@Override
	public int getAD_Reference_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Reference_ID);
	}

	@Override
	public org.compiere.model.I_AD_Reference getAD_Reference_Override()
	{
		return get_ValueAsPO(COLUMNNAME_AD_Reference_Override_ID, org.compiere.model.I_AD_Reference.class);
	}

	@Override
	public void setAD_Reference_Override(final org.compiere.model.I_AD_Reference AD_Reference_Override)
	{
		set_ValueFromPO(COLUMNNAME_AD_Reference_Override_ID, org.compiere.model.I_AD_Reference.class, AD_Reference_Override);
	}

	@Override
	public void setAD_Reference_Override_ID (final int AD_Reference_Override_ID)
	{
		if (AD_Reference_Override_ID < 1) 
			set_Value (COLUMNNAME_AD_Reference_Override_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Reference_Override_ID, AD_Reference_Override_ID);
	}

	@Override
	public int getAD_Reference_Override_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Reference_Override_ID);
	}

	@Override
	public void setDateFormat (final @Nullable java.lang.String DateFormat)
	{
		set_Value (COLUMNNAME_DateFormat, DateFormat);
	}

	@Override
	public java.lang.String getDateFormat() 
	{
		return get_ValueAsString(COLUMNNAME_DateFormat);
	}

	@Override
	public void setDefaultValue (final @Nullable java.lang.String DefaultValue)
	{
		set_Value (COLUMNNAME_DefaultValue, DefaultValue);
	}

	@Override
	public java.lang.String getDefaultValue() 
	{
		return get_ValueAsString(COLUMNNAME_DefaultValue);
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
	public org.compiere.model.I_EXP_Format getEXP_EmbeddedFormat()
	{
		return get_ValueAsPO(COLUMNNAME_EXP_EmbeddedFormat_ID, org.compiere.model.I_EXP_Format.class);
	}

	@Override
	public void setEXP_EmbeddedFormat(final org.compiere.model.I_EXP_Format EXP_EmbeddedFormat)
	{
		set_ValueFromPO(COLUMNNAME_EXP_EmbeddedFormat_ID, org.compiere.model.I_EXP_Format.class, EXP_EmbeddedFormat);
	}

	@Override
	public void setEXP_EmbeddedFormat_ID (final int EXP_EmbeddedFormat_ID)
	{
		if (EXP_EmbeddedFormat_ID < 1) 
			set_Value (COLUMNNAME_EXP_EmbeddedFormat_ID, null);
		else 
			set_Value (COLUMNNAME_EXP_EmbeddedFormat_ID, EXP_EmbeddedFormat_ID);
	}

	@Override
	public int getEXP_EmbeddedFormat_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_EXP_EmbeddedFormat_ID);
	}

	@Override
	public org.compiere.model.I_EXP_Format getEXP_Format()
	{
		return get_ValueAsPO(COLUMNNAME_EXP_Format_ID, org.compiere.model.I_EXP_Format.class);
	}

	@Override
	public void setEXP_Format(final org.compiere.model.I_EXP_Format EXP_Format)
	{
		set_ValueFromPO(COLUMNNAME_EXP_Format_ID, org.compiere.model.I_EXP_Format.class, EXP_Format);
	}

	@Override
	public void setEXP_Format_ID (final int EXP_Format_ID)
	{
		if (EXP_Format_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_EXP_Format_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_EXP_Format_ID, EXP_Format_ID);
	}

	@Override
	public int getEXP_Format_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_EXP_Format_ID);
	}

	@Override
	public void setEXP_FormatLine_ID (final int EXP_FormatLine_ID)
	{
		if (EXP_FormatLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_EXP_FormatLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_EXP_FormatLine_ID, EXP_FormatLine_ID);
	}

	@Override
	public int getEXP_FormatLine_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_EXP_FormatLine_ID);
	}

	/** 
	 * FilterOperator AD_Reference_ID=541875
	 * Reference name: FilterOperator_for_EXP_FormatLine
	 */
	public static final int FILTEROPERATOR_AD_Reference_ID=541875;
	/** Equals = E */
	public static final String FILTEROPERATOR_Equals = "E";
	/** Like = L */
	public static final String FILTEROPERATOR_Like = "L";
	@Override
	public void setFilterOperator (final java.lang.String FilterOperator)
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
	{
		set_Value (COLUMNNAME_Help, Help);
	}

	@Override
	public java.lang.String getHelp() 
	{
		return get_ValueAsString(COLUMNNAME_Help);
	}

	@Override
	public void setIsMandatory (final boolean IsMandatory)
	{
		set_Value (COLUMNNAME_IsMandatory, IsMandatory);
	}

	@Override
	public boolean isMandatory() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsMandatory);
	}

	@Override
	public void setIsPartUniqueIndex (final boolean IsPartUniqueIndex)
	{
		set_Value (COLUMNNAME_IsPartUniqueIndex, IsPartUniqueIndex);
	}

	@Override
	public boolean isPartUniqueIndex() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsPartUniqueIndex);
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
	public void setPosition (final int Position)
	{
		set_Value (COLUMNNAME_Position, Position);
	}

	@Override
	public int getPosition() 
	{
		return get_ValueAsInt(COLUMNNAME_Position);
	}

	/** 
	 * Type AD_Reference_ID=53241
	 * Reference name: EXP_Line_Type
	 */
	public static final int TYPE_AD_Reference_ID=53241;
	/** XML Element = E */
	public static final String TYPE_XMLElement = "E";
	/** XML Attribute = A */
	public static final String TYPE_XMLAttribute = "A";
	/** Embedded EXP Format = M */
	public static final String TYPE_EmbeddedEXPFormat = "M";
	/** Referenced EXP Format = R */
	public static final String TYPE_ReferencedEXPFormat = "R";
	@Override
	public void setType (final java.lang.String Type)
	{
		set_Value (COLUMNNAME_Type, Type);
	}

	@Override
	public java.lang.String getType() 
	{
		return get_ValueAsString(COLUMNNAME_Type);
	}

	@Override
	public void setValue (final java.lang.String Value)
	{
		set_Value (COLUMNNAME_Value, Value);
	}

	@Override
	public java.lang.String getValue() 
	{
		return get_ValueAsString(COLUMNNAME_Value);
	}
}