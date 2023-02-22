// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for C_DocType_Sequence
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_DocType_Sequence extends org.compiere.model.PO implements I_C_DocType_Sequence, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1980434955L;

    /** Standard Constructor */
    public X_C_DocType_Sequence (final Properties ctx, final int C_DocType_Sequence_ID, @Nullable final String trxName)
    {
      super (ctx, C_DocType_Sequence_ID, trxName);
    }

    /** Load Constructor */
    public X_C_DocType_Sequence (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public org.compiere.model.I_C_Country getC_Country()
	{
		return get_ValueAsPO(COLUMNNAME_C_Country_ID, org.compiere.model.I_C_Country.class);
	}

	@Override
	public void setC_Country(final org.compiere.model.I_C_Country C_Country)
	{
		set_ValueFromPO(COLUMNNAME_C_Country_ID, org.compiere.model.I_C_Country.class, C_Country);
	}

	@Override
	public void setC_Country_ID (final int C_Country_ID)
	{
		if (C_Country_ID < 1) 
			set_Value (COLUMNNAME_C_Country_ID, null);
		else 
			set_Value (COLUMNNAME_C_Country_ID, C_Country_ID);
	}

	@Override
	public int getC_Country_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Country_ID);
	}

	@Override
	public void setC_DocType_ID (final int C_DocType_ID)
	{
		if (C_DocType_ID < 0) 
			set_ValueNoCheck (COLUMNNAME_C_DocType_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_DocType_ID, C_DocType_ID);
	}

	@Override
	public int getC_DocType_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_DocType_ID);
	}

	@Override
	public void setC_DocType_Sequence_ID (final int C_DocType_Sequence_ID)
	{
		if (C_DocType_Sequence_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_DocType_Sequence_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_DocType_Sequence_ID, C_DocType_Sequence_ID);
	}

	@Override
	public int getC_DocType_Sequence_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_DocType_Sequence_ID);
	}

	@Override
	public org.compiere.model.I_AD_Sequence getDocNoSequence()
	{
		return get_ValueAsPO(COLUMNNAME_DocNoSequence_ID, org.compiere.model.I_AD_Sequence.class);
	}

	@Override
	public void setDocNoSequence(final org.compiere.model.I_AD_Sequence DocNoSequence)
	{
		set_ValueFromPO(COLUMNNAME_DocNoSequence_ID, org.compiere.model.I_AD_Sequence.class, DocNoSequence);
	}

	@Override
	public void setDocNoSequence_ID (final int DocNoSequence_ID)
	{
		if (DocNoSequence_ID < 1) 
			set_Value (COLUMNNAME_DocNoSequence_ID, null);
		else 
			set_Value (COLUMNNAME_DocNoSequence_ID, DocNoSequence_ID);
	}

	@Override
	public int getDocNoSequence_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_DocNoSequence_ID);
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