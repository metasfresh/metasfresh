// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for M_SectionCode
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_M_SectionCode extends org.compiere.model.PO implements I_M_SectionCode, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -563380166L;

    /** Standard Constructor */
    public X_M_SectionCode (final Properties ctx, final int M_SectionCode_ID, @Nullable final String trxName)
    {
      super (ctx, M_SectionCode_ID, trxName);
    }

    /** Load Constructor */
    public X_M_SectionCode (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setM_SectionCode_ID (final int M_SectionCode_ID)
	{
		if (M_SectionCode_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_SectionCode_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_SectionCode_ID, M_SectionCode_ID);
	}

	@Override
	public int getM_SectionCode_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_SectionCode_ID);
	}

	@Override
	public void setName (final @Nullable java.lang.String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	@Override
	public java.lang.String getName() 
	{
		return get_ValueAsString(COLUMNNAME_Name);
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