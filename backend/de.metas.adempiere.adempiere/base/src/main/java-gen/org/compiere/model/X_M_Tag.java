// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for M_Tag
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_M_Tag extends org.compiere.model.PO implements I_M_Tag, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 1735077641L;

    /** Standard Constructor */
    public X_M_Tag (final Properties ctx, final int M_Tag_ID, @Nullable final String trxName)
    {
      super (ctx, M_Tag_ID, trxName);
    }

    /** Load Constructor */
    public X_M_Tag (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setDescription (final @Nullable java.lang.String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	@Override
	public java.lang.String getDescription() 
	{
		return get_ValueAsString(COLUMNNAME_Description);
	}

	@Override
	public void setM_Tag_ID (final int M_Tag_ID)
	{
		if (M_Tag_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Tag_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Tag_ID, M_Tag_ID);
	}

	@Override
	public int getM_Tag_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Tag_ID);
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