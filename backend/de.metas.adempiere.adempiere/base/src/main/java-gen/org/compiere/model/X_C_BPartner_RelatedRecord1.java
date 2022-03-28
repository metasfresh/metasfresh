// Generated Model - DO NOT CHANGE
package org.compiere.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_BPartner_RelatedRecord1
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_BPartner_RelatedRecord1 extends org.compiere.model.PO implements I_C_BPartner_RelatedRecord1, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 1735704168L;

    /** Standard Constructor */
    public X_C_BPartner_RelatedRecord1 (final Properties ctx, final int C_BPartner_RelatedRecord1_ID, @Nullable final String trxName)
    {
      super (ctx, C_BPartner_RelatedRecord1_ID, trxName);
    }

    /** Load Constructor */
    public X_C_BPartner_RelatedRecord1 (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setC_BPartner_ID (final int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_ID, C_BPartner_ID);
	}

	@Override
	public int getC_BPartner_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_ID);
	}

	@Override
	public void setC_BPartner_RelatedRecord1_ID (final int C_BPartner_RelatedRecord1_ID)
	{
		if (C_BPartner_RelatedRecord1_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_RelatedRecord1_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_RelatedRecord1_ID, C_BPartner_RelatedRecord1_ID);
	}

	@Override
	public int getC_BPartner_RelatedRecord1_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_RelatedRecord1_ID);
	}

	@Override
	public void setRecord_ID (final int Record_ID)
	{
		if (Record_ID < 0) 
			set_Value (COLUMNNAME_Record_ID, null);
		else 
			set_Value (COLUMNNAME_Record_ID, Record_ID);
	}

	@Override
	public int getRecord_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Record_ID);
	}
}