/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_Title
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_Title extends org.compiere.model.PO implements I_C_Title, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -2140284450L;

    /** Standard Constructor */
    public X_C_Title (final Properties ctx, final int C_Title_ID, final String trxName)
    {
      super (ctx, C_Title_ID, trxName);
    }

    /** Load Constructor */
    public X_C_Title (final Properties ctx, final ResultSet rs, final String trxName)
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
	public void setC_Title_ID (final int C_Title_ID)
	{
		if (C_Title_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Title_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Title_ID, C_Title_ID);
	}

	@Override
	public int getC_Title_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Title_ID);
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