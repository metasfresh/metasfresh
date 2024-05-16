// Generated Model - DO NOT CHANGE
package org.compiere.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_BPartner_QuickInput_RelatedRecord1
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_BPartner_QuickInput_RelatedRecord1 extends org.compiere.model.PO implements I_C_BPartner_QuickInput_RelatedRecord1, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 1616554338L;

    /** Standard Constructor */
    public X_C_BPartner_QuickInput_RelatedRecord1 (final Properties ctx, final int C_BPartner_QuickInput_RelatedRecord1_ID, @Nullable final String trxName)
    {
      super (ctx, C_BPartner_QuickInput_RelatedRecord1_ID, trxName);
    }

    /** Load Constructor */
    public X_C_BPartner_QuickInput_RelatedRecord1 (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public org.compiere.model.I_C_BPartner_QuickInput getC_BPartner_QuickInput()
	{
		return get_ValueAsPO(COLUMNNAME_C_BPartner_QuickInput_ID, org.compiere.model.I_C_BPartner_QuickInput.class);
	}

	@Override
	public void setC_BPartner_QuickInput(final org.compiere.model.I_C_BPartner_QuickInput C_BPartner_QuickInput)
	{
		set_ValueFromPO(COLUMNNAME_C_BPartner_QuickInput_ID, org.compiere.model.I_C_BPartner_QuickInput.class, C_BPartner_QuickInput);
	}

	@Override
	public void setC_BPartner_QuickInput_ID (final int C_BPartner_QuickInput_ID)
	{
		if (C_BPartner_QuickInput_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_QuickInput_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_QuickInput_ID, C_BPartner_QuickInput_ID);
	}

	@Override
	public int getC_BPartner_QuickInput_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_QuickInput_ID);
	}

	@Override
	public void setC_BPartner_QuickInput_RelatedRecord1_ID (final int C_BPartner_QuickInput_RelatedRecord1_ID)
	{
		if (C_BPartner_QuickInput_RelatedRecord1_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_QuickInput_RelatedRecord1_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_QuickInput_RelatedRecord1_ID, C_BPartner_QuickInput_RelatedRecord1_ID);
	}

	@Override
	public int getC_BPartner_QuickInput_RelatedRecord1_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_QuickInput_RelatedRecord1_ID);
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