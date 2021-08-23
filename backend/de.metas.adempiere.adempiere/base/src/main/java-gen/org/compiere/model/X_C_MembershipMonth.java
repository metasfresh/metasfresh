// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for C_MembershipMonth
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_MembershipMonth extends org.compiere.model.PO implements I_C_MembershipMonth, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1145810915L;

    /** Standard Constructor */
    public X_C_MembershipMonth (final Properties ctx, final int C_MembershipMonth_ID, @Nullable final String trxName)
    {
      super (ctx, C_MembershipMonth_ID, trxName);
    }

    /** Load Constructor */
    public X_C_MembershipMonth (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setC_MembershipMonth_ID (final int C_MembershipMonth_ID)
	{
		if (C_MembershipMonth_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_MembershipMonth_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_MembershipMonth_ID, C_MembershipMonth_ID);
	}

	@Override
	public int getC_MembershipMonth_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_MembershipMonth_ID);
	}

	@Override
	public org.compiere.model.I_C_Year getC_Year()
	{
		return get_ValueAsPO(COLUMNNAME_C_Year_ID, org.compiere.model.I_C_Year.class);
	}

	@Override
	public void setC_Year(final org.compiere.model.I_C_Year C_Year)
	{
		set_ValueFromPO(COLUMNNAME_C_Year_ID, org.compiere.model.I_C_Year.class, C_Year);
	}

	@Override
	public void setC_Year_ID (final int C_Year_ID)
	{
		if (C_Year_ID < 1) 
			set_Value (COLUMNNAME_C_Year_ID, null);
		else 
			set_Value (COLUMNNAME_C_Year_ID, C_Year_ID);
	}

	@Override
	public int getC_Year_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Year_ID);
	}

	@Override
	public void setValidTo (final @Nullable java.sql.Timestamp ValidTo)
	{
		set_ValueNoCheck (COLUMNNAME_ValidTo, ValidTo);
	}

	@Override
	public java.sql.Timestamp getValidTo() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_ValidTo);
	}
}