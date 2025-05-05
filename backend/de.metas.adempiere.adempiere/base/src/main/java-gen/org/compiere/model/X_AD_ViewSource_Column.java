// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for AD_ViewSource_Column
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_AD_ViewSource_Column extends org.compiere.model.PO implements I_AD_ViewSource_Column, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 1284549240L;

    /** Standard Constructor */
    public X_AD_ViewSource_Column (final Properties ctx, final int AD_ViewSource_Column_ID, @Nullable final String trxName)
    {
      super (ctx, AD_ViewSource_Column_ID, trxName);
    }

    /** Load Constructor */
    public X_AD_ViewSource_Column (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setAD_ViewSource_Column_ID (final int AD_ViewSource_Column_ID)
	{
		if (AD_ViewSource_Column_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_ViewSource_Column_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_ViewSource_Column_ID, AD_ViewSource_Column_ID);
	}

	@Override
	public int getAD_ViewSource_Column_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_ViewSource_Column_ID);
	}

	@Override
	public org.compiere.model.I_AD_ViewSource getAD_ViewSource()
	{
		return get_ValueAsPO(COLUMNNAME_AD_ViewSource_ID, org.compiere.model.I_AD_ViewSource.class);
	}

	@Override
	public void setAD_ViewSource(final org.compiere.model.I_AD_ViewSource AD_ViewSource)
	{
		set_ValueFromPO(COLUMNNAME_AD_ViewSource_ID, org.compiere.model.I_AD_ViewSource.class, AD_ViewSource);
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