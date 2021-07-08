// Generated Model - DO NOT CHANGE
package de.metas.order.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for C_CompensationGroup_Schema
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_CompensationGroup_Schema extends org.compiere.model.PO implements I_C_CompensationGroup_Schema, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -765492642L;

    /** Standard Constructor */
    public X_C_CompensationGroup_Schema (final Properties ctx, final int C_CompensationGroup_Schema_ID, @Nullable final String trxName)
    {
      super (ctx, C_CompensationGroup_Schema_ID, trxName);
    }

    /** Load Constructor */
    public X_C_CompensationGroup_Schema (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setC_Activity_ID (final int C_Activity_ID)
	{
		if (C_Activity_ID < 1) 
			set_Value (COLUMNNAME_C_Activity_ID, null);
		else 
			set_Value (COLUMNNAME_C_Activity_ID, C_Activity_ID);
	}

	@Override
	public int getC_Activity_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Activity_ID);
	}

	@Override
	public void setC_CompensationGroup_Schema_ID (final int C_CompensationGroup_Schema_ID)
	{
		if (C_CompensationGroup_Schema_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_CompensationGroup_Schema_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_CompensationGroup_Schema_ID, C_CompensationGroup_Schema_ID);
	}

	@Override
	public int getC_CompensationGroup_Schema_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_CompensationGroup_Schema_ID);
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