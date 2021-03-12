// Generated Model - DO NOT CHANGE
package de.metas.handlingunits.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for C_POS_HUEditor_Filter
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_POS_HUEditor_Filter extends org.compiere.model.PO implements I_C_POS_HUEditor_Filter, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 1889840767L;

    /** Standard Constructor */
    public X_C_POS_HUEditor_Filter (final Properties ctx, final int C_POS_HUEditor_Filter_ID, @Nullable final String trxName)
    {
      super (ctx, C_POS_HUEditor_Filter_ID, trxName);
    }

    /** Load Constructor */
    public X_C_POS_HUEditor_Filter (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setAD_JavaClass_ID (final int AD_JavaClass_ID)
	{
		if (AD_JavaClass_ID < 1) 
			set_Value (COLUMNNAME_AD_JavaClass_ID, null);
		else 
			set_Value (COLUMNNAME_AD_JavaClass_ID, AD_JavaClass_ID);
	}

	@Override
	public int getAD_JavaClass_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_JavaClass_ID);
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
		if (AD_Reference_ID < 1) 
			set_Value (COLUMNNAME_AD_Reference_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Reference_ID, AD_Reference_ID);
	}

	@Override
	public int getAD_Reference_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Reference_ID);
	}

	@Override
	public org.compiere.model.I_AD_Reference getAD_Reference_Value()
	{
		return get_ValueAsPO(COLUMNNAME_AD_Reference_Value_ID, org.compiere.model.I_AD_Reference.class);
	}

	@Override
	public void setAD_Reference_Value(final org.compiere.model.I_AD_Reference AD_Reference_Value)
	{
		set_ValueFromPO(COLUMNNAME_AD_Reference_Value_ID, org.compiere.model.I_AD_Reference.class, AD_Reference_Value);
	}

	@Override
	public void setAD_Reference_Value_ID (final int AD_Reference_Value_ID)
	{
		if (AD_Reference_Value_ID < 1) 
			set_Value (COLUMNNAME_AD_Reference_Value_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Reference_Value_ID, AD_Reference_Value_ID);
	}

	@Override
	public int getAD_Reference_Value_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Reference_Value_ID);
	}

	@Override
	public void setC_POS_HUEditor_Filter_ID (final int C_POS_HUEditor_Filter_ID)
	{
		if (C_POS_HUEditor_Filter_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_POS_HUEditor_Filter_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_POS_HUEditor_Filter_ID, C_POS_HUEditor_Filter_ID);
	}

	@Override
	public int getC_POS_HUEditor_Filter_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_POS_HUEditor_Filter_ID);
	}

	@Override
	public void setColumnName (final java.lang.String ColumnName)
	{
		set_Value (COLUMNNAME_ColumnName, ColumnName);
	}

	@Override
	public java.lang.String getColumnName() 
	{
		return get_ValueAsString(COLUMNNAME_ColumnName);
	}
}