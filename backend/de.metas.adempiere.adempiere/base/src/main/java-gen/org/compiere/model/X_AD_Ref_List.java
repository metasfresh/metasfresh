// Generated Model - DO NOT CHANGE
package org.compiere.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for AD_Ref_List
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_AD_Ref_List extends org.compiere.model.PO implements I_AD_Ref_List, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1608395826L;

    /** Standard Constructor */
	public X_AD_Ref_List(final Properties ctx, final int AD_Ref_List_ID, @Nullable final String trxName)
    {
      super (ctx, AD_Ref_List_ID, trxName);
    }

    /** Load Constructor */
	public X_AD_Ref_List(final Properties ctx, final ResultSet rs, @Nullable final String trxName)
    {
      super (ctx, rs, trxName);
    }

	/**
	 * Load Meta Data
	 */
	@Override
	protected org.compiere.model.POInfo initPO(final Properties ctx)
	{
		return org.compiere.model.POInfo.getPOInfo(Table_Name);
	}

	@Override
	public org.compiere.model.I_AD_Color getAD_Color()
	{
		return get_ValueAsPO(COLUMNNAME_AD_Color_ID, org.compiere.model.I_AD_Color.class);
	}

	@Override
	public void setAD_Color(final org.compiere.model.I_AD_Color AD_Color)
	{
		set_ValueFromPO(COLUMNNAME_AD_Color_ID, org.compiere.model.I_AD_Color.class, AD_Color);
	}

	@Override
	public void setAD_Color_ID(final int AD_Color_ID)
	{
		if (AD_Color_ID < 1)
			set_Value(COLUMNNAME_AD_Color_ID, null);
		else
			set_Value(COLUMNNAME_AD_Color_ID, AD_Color_ID);
	}

	@Override
	public int getAD_Color_ID()
	{
		return get_ValueAsInt(COLUMNNAME_AD_Color_ID);
	}

	@Override
	public void setAD_Message_ID(final int AD_Message_ID)
	{
		if (AD_Message_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Message_ID, null);
		else
			set_ValueNoCheck(COLUMNNAME_AD_Message_ID, AD_Message_ID);
	}

	@Override
	public int getAD_Message_ID()
	{
		return get_ValueAsInt(COLUMNNAME_AD_Message_ID);
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
	public void setAD_Reference_ID(final int AD_Reference_ID)
	{
		if (AD_Reference_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Reference_ID, null);
		else
			set_ValueNoCheck(COLUMNNAME_AD_Reference_ID, AD_Reference_ID);
	}

	@Override
	public int getAD_Reference_ID()
	{
		return get_ValueAsInt(COLUMNNAME_AD_Reference_ID);
	}

	@Override
	public void setAD_Ref_List_ID(final int AD_Ref_List_ID)
	{
		if (AD_Ref_List_ID < 1)
			set_ValueNoCheck(COLUMNNAME_AD_Ref_List_ID, null);
		else
			set_ValueNoCheck(COLUMNNAME_AD_Ref_List_ID, AD_Ref_List_ID);
	}

	@Override
	public int getAD_Ref_List_ID()
	{
		return get_ValueAsInt(COLUMNNAME_AD_Ref_List_ID);
	}

	@Override
	public void setDescription(final @Nullable java.lang.String Description)
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
	public void setEntityType(final java.lang.String EntityType)
	{
		set_Value (COLUMNNAME_EntityType, EntityType);
	}

	@Override
	public java.lang.String getEntityType()
	{
		return get_ValueAsString(COLUMNNAME_EntityType);
	}

	@Override
	public void setName(final java.lang.String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	@Override
	public java.lang.String getName()
	{
		return get_ValueAsString(COLUMNNAME_Name);
	}

	@Override
	public void setValidFrom(final @Nullable java.sql.Timestamp ValidFrom)
	{
		set_Value (COLUMNNAME_ValidFrom, ValidFrom);
	}

	@Override
	public java.sql.Timestamp getValidFrom()
	{
		return get_ValueAsTimestamp(COLUMNNAME_ValidFrom);
	}

	@Override
	public void setValidTo(final @Nullable java.sql.Timestamp ValidTo)
	{
		set_Value (COLUMNNAME_ValidTo, ValidTo);
	}

	@Override
	public java.sql.Timestamp getValidTo()
	{
		return get_ValueAsTimestamp(COLUMNNAME_ValidTo);
	}

	@Override
	public void setValue(final java.lang.String Value)
	{
		set_Value (COLUMNNAME_Value, Value);
	}

	@Override
	public java.lang.String getValue()
	{
		return get_ValueAsString(COLUMNNAME_Value);
	}

	@Override
	public void setValueName(final @Nullable java.lang.String ValueName)
	{
		set_ValueNoCheck (COLUMNNAME_ValueName, ValueName);
	}

	@Override
	public java.lang.String getValueName()
	{
		return get_ValueAsString(COLUMNNAME_ValueName);
	}
}