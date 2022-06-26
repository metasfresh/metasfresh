// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for C_Project_WO_Step
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_Project_WO_Step extends org.compiere.model.PO implements I_C_Project_WO_Step, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -938541754L;

    /** Standard Constructor */
    public X_C_Project_WO_Step (final Properties ctx, final int C_Project_WO_Step_ID, @Nullable final String trxName)
    {
      super (ctx, C_Project_WO_Step_ID, trxName);
    }

    /** Load Constructor */
    public X_C_Project_WO_Step (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setC_Project_ID (final int C_Project_ID)
	{
		if (C_Project_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Project_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Project_ID, C_Project_ID);
	}

	@Override
	public int getC_Project_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Project_ID);
	}

	@Override
	public void setC_Project_WO_Step_ID (final int C_Project_WO_Step_ID)
	{
		if (C_Project_WO_Step_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Project_WO_Step_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Project_WO_Step_ID, C_Project_WO_Step_ID);
	}

	@Override
	public int getC_Project_WO_Step_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Project_WO_Step_ID);
	}

	@Override
	public void setDateEnd (final java.sql.Timestamp DateEnd)
	{
		set_Value (COLUMNNAME_DateEnd, DateEnd);
	}

	@Override
	public java.sql.Timestamp getDateEnd() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DateEnd);
	}

	@Override
	public void setDateStart (final java.sql.Timestamp DateStart)
	{
		set_Value (COLUMNNAME_DateStart, DateStart);
	}

	@Override
	public java.sql.Timestamp getDateStart() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DateStart);
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
	public void setSeqNo (final int SeqNo)
	{
		set_Value (COLUMNNAME_SeqNo, SeqNo);
	}

	@Override
	public int getSeqNo() 
	{
		return get_ValueAsInt(COLUMNNAME_SeqNo);
	}
}