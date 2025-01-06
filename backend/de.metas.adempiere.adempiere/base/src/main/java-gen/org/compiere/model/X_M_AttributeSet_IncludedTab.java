// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for M_AttributeSet_IncludedTab
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_M_AttributeSet_IncludedTab extends org.compiere.model.PO implements I_M_AttributeSet_IncludedTab, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 777904131L;

    /** Standard Constructor */
    public X_M_AttributeSet_IncludedTab (final Properties ctx, final int M_AttributeSet_IncludedTab_ID, @Nullable final String trxName)
    {
      super (ctx, M_AttributeSet_IncludedTab_ID, trxName);
    }

    /** Load Constructor */
    public X_M_AttributeSet_IncludedTab (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public org.compiere.model.I_M_AttributeSet getM_AttributeSet()
	{
		return get_ValueAsPO(COLUMNNAME_M_AttributeSet_ID, org.compiere.model.I_M_AttributeSet.class);
	}

	@Override
	public void setM_AttributeSet(final org.compiere.model.I_M_AttributeSet M_AttributeSet)
	{
		set_ValueFromPO(COLUMNNAME_M_AttributeSet_ID, org.compiere.model.I_M_AttributeSet.class, M_AttributeSet);
	}

	@Override
	public void setM_AttributeSet_ID (final int M_AttributeSet_ID)
	{
		if (M_AttributeSet_ID < 0) 
			set_ValueNoCheck (COLUMNNAME_M_AttributeSet_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_AttributeSet_ID, M_AttributeSet_ID);
	}

	@Override
	public int getM_AttributeSet_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_AttributeSet_ID);
	}

	@Override
	public void setM_AttributeSet_IncludedTab_ID (final int M_AttributeSet_IncludedTab_ID)
	{
		if (M_AttributeSet_IncludedTab_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_AttributeSet_IncludedTab_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_AttributeSet_IncludedTab_ID, M_AttributeSet_IncludedTab_ID);
	}

	@Override
	public int getM_AttributeSet_IncludedTab_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_AttributeSet_IncludedTab_ID);
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
}