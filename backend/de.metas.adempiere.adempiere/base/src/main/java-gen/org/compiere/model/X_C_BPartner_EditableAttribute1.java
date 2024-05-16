// Generated Model - DO NOT CHANGE
package org.compiere.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_BPartner_EditableAttribute1
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_BPartner_EditableAttribute1 extends org.compiere.model.PO implements I_C_BPartner_EditableAttribute1, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 1052869657L;

    /** Standard Constructor */
    public X_C_BPartner_EditableAttribute1 (final Properties ctx, final int C_BPartner_EditableAttribute1_ID, @Nullable final String trxName)
    {
      super (ctx, C_BPartner_EditableAttribute1_ID, trxName);
    }

    /** Load Constructor */
    public X_C_BPartner_EditableAttribute1 (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setC_BPartner_EditableAttribute1_ID (final int C_BPartner_EditableAttribute1_ID)
	{
		if (C_BPartner_EditableAttribute1_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_EditableAttribute1_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_EditableAttribute1_ID, C_BPartner_EditableAttribute1_ID);
	}

	@Override
	public int getC_BPartner_EditableAttribute1_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_EditableAttribute1_ID);
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