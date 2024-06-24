// Generated Model - DO NOT CHANGE
package org.compiere.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for M_Allergen_Trace
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_M_Allergen_Trace extends org.compiere.model.PO implements I_M_Allergen_Trace, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -674050343L;

    /** Standard Constructor */
    public X_M_Allergen_Trace (final Properties ctx, final int M_Allergen_Trace_ID, @Nullable final String trxName)
    {
      super (ctx, M_Allergen_Trace_ID, trxName);
    }

    /** Load Constructor */
    public X_M_Allergen_Trace (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setM_Allergen_Trace_ID (final int M_Allergen_Trace_ID)
	{
		if (M_Allergen_Trace_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Allergen_Trace_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Allergen_Trace_ID, M_Allergen_Trace_ID);
	}

	@Override
	public int getM_Allergen_Trace_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Allergen_Trace_ID);
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
}