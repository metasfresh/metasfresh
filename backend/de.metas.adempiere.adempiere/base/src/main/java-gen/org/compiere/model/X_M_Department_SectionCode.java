// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for M_Department_SectionCode
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_M_Department_SectionCode extends org.compiere.model.PO implements I_M_Department_SectionCode, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1270090653L;

    /** Standard Constructor */
    public X_M_Department_SectionCode (final Properties ctx, final int M_Department_SectionCode_ID, @Nullable final String trxName)
    {
      super (ctx, M_Department_SectionCode_ID, trxName);
    }

    /** Load Constructor */
    public X_M_Department_SectionCode (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public org.compiere.model.I_M_Department getM_Department()
	{
		return get_ValueAsPO(COLUMNNAME_M_Department_ID, org.compiere.model.I_M_Department.class);
	}

	@Override
	public void setM_Department(final org.compiere.model.I_M_Department M_Department)
	{
		set_ValueFromPO(COLUMNNAME_M_Department_ID, org.compiere.model.I_M_Department.class, M_Department);
	}

	@Override
	public void setM_Department_ID (final int M_Department_ID)
	{
		if (M_Department_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Department_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Department_ID, M_Department_ID);
	}

	@Override
	public int getM_Department_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Department_ID);
	}

	@Override
	public void setM_Department_SectionCode_ID (final int M_Department_SectionCode_ID)
	{
		if (M_Department_SectionCode_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Department_SectionCode_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Department_SectionCode_ID, M_Department_SectionCode_ID);
	}

	@Override
	public int getM_Department_SectionCode_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Department_SectionCode_ID);
	}

	@Override
	public org.compiere.model.I_M_SectionCode getM_SectionCode()
	{
		return get_ValueAsPO(COLUMNNAME_M_SectionCode_ID, org.compiere.model.I_M_SectionCode.class);
	}

	@Override
	public void setM_SectionCode(final org.compiere.model.I_M_SectionCode M_SectionCode)
	{
		set_ValueFromPO(COLUMNNAME_M_SectionCode_ID, org.compiere.model.I_M_SectionCode.class, M_SectionCode);
	}

	@Override
	public void setM_SectionCode_ID (final int M_SectionCode_ID)
	{
		if (M_SectionCode_ID < 1) 
			set_Value (COLUMNNAME_M_SectionCode_ID, null);
		else 
			set_Value (COLUMNNAME_M_SectionCode_ID, M_SectionCode_ID);
	}

	@Override
	public int getM_SectionCode_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_SectionCode_ID);
	}

	@Override
	public void setValidFrom (final java.sql.Timestamp ValidFrom)
	{
		set_Value (COLUMNNAME_ValidFrom, ValidFrom);
	}

	@Override
	public java.sql.Timestamp getValidFrom() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_ValidFrom);
	}

	@Override
	public void setValidTo (final @Nullable java.sql.Timestamp ValidTo)
	{
		set_Value (COLUMNNAME_ValidTo, ValidTo);
	}

	@Override
	public java.sql.Timestamp getValidTo() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_ValidTo);
	}
}