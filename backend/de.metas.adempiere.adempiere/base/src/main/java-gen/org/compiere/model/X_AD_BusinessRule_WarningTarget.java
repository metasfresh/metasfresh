// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for AD_BusinessRule_WarningTarget
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_AD_BusinessRule_WarningTarget extends org.compiere.model.PO implements I_AD_BusinessRule_WarningTarget, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 121098287L;

    /** Standard Constructor */
    public X_AD_BusinessRule_WarningTarget (final Properties ctx, final int AD_BusinessRule_WarningTarget_ID, @Nullable final String trxName)
    {
      super (ctx, AD_BusinessRule_WarningTarget_ID, trxName);
    }

    /** Load Constructor */
    public X_AD_BusinessRule_WarningTarget (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public org.compiere.model.I_AD_BusinessRule getAD_BusinessRule()
	{
		return get_ValueAsPO(COLUMNNAME_AD_BusinessRule_ID, org.compiere.model.I_AD_BusinessRule.class);
	}

	@Override
	public void setAD_BusinessRule(final org.compiere.model.I_AD_BusinessRule AD_BusinessRule)
	{
		set_ValueFromPO(COLUMNNAME_AD_BusinessRule_ID, org.compiere.model.I_AD_BusinessRule.class, AD_BusinessRule);
	}

	@Override
	public void setAD_BusinessRule_ID (final int AD_BusinessRule_ID)
	{
		if (AD_BusinessRule_ID < 1) 
			set_Value (COLUMNNAME_AD_BusinessRule_ID, null);
		else 
			set_Value (COLUMNNAME_AD_BusinessRule_ID, AD_BusinessRule_ID);
	}

	@Override
	public int getAD_BusinessRule_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_BusinessRule_ID);
	}

	@Override
	public void setAD_BusinessRule_WarningTarget_ID (final int AD_BusinessRule_WarningTarget_ID)
	{
		if (AD_BusinessRule_WarningTarget_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_BusinessRule_WarningTarget_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_BusinessRule_WarningTarget_ID, AD_BusinessRule_WarningTarget_ID);
	}

	@Override
	public int getAD_BusinessRule_WarningTarget_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_BusinessRule_WarningTarget_ID);
	}

	@Override
	public void setAD_Table_ID (final int AD_Table_ID)
	{
		if (AD_Table_ID < 1) 
			set_Value (COLUMNNAME_AD_Table_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Table_ID, AD_Table_ID);
	}

	@Override
	public int getAD_Table_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Table_ID);
	}

	@Override
	public void setLookupSQL (final java.lang.String LookupSQL)
	{
		set_Value (COLUMNNAME_LookupSQL, LookupSQL);
	}

	@Override
	public java.lang.String getLookupSQL() 
	{
		return get_ValueAsString(COLUMNNAME_LookupSQL);
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