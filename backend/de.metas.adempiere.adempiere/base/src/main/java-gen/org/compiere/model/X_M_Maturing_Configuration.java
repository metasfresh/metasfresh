// Generated Model - DO NOT CHANGE
package org.compiere.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for M_Maturing_Configuration
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_M_Maturing_Configuration extends org.compiere.model.PO implements I_M_Maturing_Configuration, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1793777178L;

    /** Standard Constructor */
    public X_M_Maturing_Configuration (final Properties ctx, final int M_Maturing_Configuration_ID, @Nullable final String trxName)
    {
      super (ctx, M_Maturing_Configuration_ID, trxName);
    }

    /** Load Constructor */
    public X_M_Maturing_Configuration (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setM_Maturing_Configuration_ID (final int M_Maturing_Configuration_ID)
	{
		if (M_Maturing_Configuration_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Maturing_Configuration_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Maturing_Configuration_ID, M_Maturing_Configuration_ID);
	}

	@Override
	public int getM_Maturing_Configuration_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Maturing_Configuration_ID);
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