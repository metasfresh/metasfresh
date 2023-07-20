// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for AD_ViewSource
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_AD_ViewSource extends org.compiere.model.PO implements I_AD_ViewSource, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -308100805L;

    /** Standard Constructor */
    public X_AD_ViewSource (final Properties ctx, final int AD_ViewSource_ID, @Nullable final String trxName)
    {
      super (ctx, AD_ViewSource_ID, trxName);
    }

    /** Load Constructor */
    public X_AD_ViewSource (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setAD_Table_ID (final int AD_Table_ID)
	{
		if (AD_Table_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Table_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Table_ID, AD_Table_ID);
	}

	@Override
	public int getAD_Table_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Table_ID);
	}

	@Override
	public void setAD_ViewSource_ID (final int AD_ViewSource_ID)
	{
		if (AD_ViewSource_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_ViewSource_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_ViewSource_ID, AD_ViewSource_ID);
	}

	@Override
	public int getAD_ViewSource_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_ViewSource_ID);
	}

	@Override
	public void setIsInvalidateOnAfterChange (final boolean IsInvalidateOnAfterChange)
	{
		set_Value (COLUMNNAME_IsInvalidateOnAfterChange, IsInvalidateOnAfterChange);
	}

	@Override
	public boolean isInvalidateOnAfterChange() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsInvalidateOnAfterChange);
	}

	@Override
	public void setIsInvalidateOnAfterDelete (final boolean IsInvalidateOnAfterDelete)
	{
		set_Value (COLUMNNAME_IsInvalidateOnAfterDelete, IsInvalidateOnAfterDelete);
	}

	@Override
	public boolean isInvalidateOnAfterDelete() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsInvalidateOnAfterDelete);
	}

	@Override
	public void setIsInvalidateOnAfterNew (final boolean IsInvalidateOnAfterNew)
	{
		set_Value (COLUMNNAME_IsInvalidateOnAfterNew, IsInvalidateOnAfterNew);
	}

	@Override
	public boolean isInvalidateOnAfterNew() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsInvalidateOnAfterNew);
	}

	@Override
	public void setIsInvalidateOnBeforeChange (final boolean IsInvalidateOnBeforeChange)
	{
		set_Value (COLUMNNAME_IsInvalidateOnBeforeChange, IsInvalidateOnBeforeChange);
	}

	@Override
	public boolean isInvalidateOnBeforeChange() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsInvalidateOnBeforeChange);
	}

	@Override
	public void setIsInvalidateOnBeforeNew (final boolean IsInvalidateOnBeforeNew)
	{
		set_Value (COLUMNNAME_IsInvalidateOnBeforeNew, IsInvalidateOnBeforeNew);
	}

	@Override
	public boolean isInvalidateOnBeforeNew() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsInvalidateOnBeforeNew);
	}

	@Override
	public org.compiere.model.I_AD_Column getParent_LinkColumn()
	{
		return get_ValueAsPO(COLUMNNAME_Parent_LinkColumn_ID, org.compiere.model.I_AD_Column.class);
	}

	@Override
	public void setParent_LinkColumn(final org.compiere.model.I_AD_Column Parent_LinkColumn)
	{
		set_ValueFromPO(COLUMNNAME_Parent_LinkColumn_ID, org.compiere.model.I_AD_Column.class, Parent_LinkColumn);
	}

	@Override
	public void setParent_LinkColumn_ID (final int Parent_LinkColumn_ID)
	{
		if (Parent_LinkColumn_ID < 1) 
			set_Value (COLUMNNAME_Parent_LinkColumn_ID, null);
		else 
			set_Value (COLUMNNAME_Parent_LinkColumn_ID, Parent_LinkColumn_ID);
	}

	@Override
	public int getParent_LinkColumn_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Parent_LinkColumn_ID);
	}

	@Override
	public org.compiere.model.I_AD_Column getSource_LinkColumn()
	{
		return get_ValueAsPO(COLUMNNAME_Source_LinkColumn_ID, org.compiere.model.I_AD_Column.class);
	}

	@Override
	public void setSource_LinkColumn(final org.compiere.model.I_AD_Column Source_LinkColumn)
	{
		set_ValueFromPO(COLUMNNAME_Source_LinkColumn_ID, org.compiere.model.I_AD_Column.class, Source_LinkColumn);
	}

	@Override
	public void setSource_LinkColumn_ID (final int Source_LinkColumn_ID)
	{
		if (Source_LinkColumn_ID < 1) 
			set_Value (COLUMNNAME_Source_LinkColumn_ID, null);
		else 
			set_Value (COLUMNNAME_Source_LinkColumn_ID, Source_LinkColumn_ID);
	}

	@Override
	public int getSource_LinkColumn_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Source_LinkColumn_ID);
	}

	@Override
	public void setSource_Table_ID (final int Source_Table_ID)
	{
		if (Source_Table_ID < 1) 
			set_Value (COLUMNNAME_Source_Table_ID, null);
		else 
			set_Value (COLUMNNAME_Source_Table_ID, Source_Table_ID);
	}

	@Override
	public int getSource_Table_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Source_Table_ID);
	}

	@Override
	public void setTechnicalNote (final @Nullable java.lang.String TechnicalNote)
	{
		set_Value (COLUMNNAME_TechnicalNote, TechnicalNote);
	}

	@Override
	public java.lang.String getTechnicalNote() 
	{
		return get_ValueAsString(COLUMNNAME_TechnicalNote);
	}
}