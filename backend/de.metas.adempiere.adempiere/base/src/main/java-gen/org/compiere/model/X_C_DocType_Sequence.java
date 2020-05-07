/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_DocType_Sequence
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_DocType_Sequence extends org.compiere.model.PO implements I_C_DocType_Sequence, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -567091569L;

    /** Standard Constructor */
    public X_C_DocType_Sequence (Properties ctx, int C_DocType_Sequence_ID, String trxName)
    {
      super (ctx, C_DocType_Sequence_ID, trxName);
      /** if (C_DocType_Sequence_ID == 0)
        {
			setC_DocType_ID (0);
			setC_DocType_Sequence_ID (0);
			setDocNoSequence_ID (0);
        } */
    }

    /** Load Constructor */
    public X_C_DocType_Sequence (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }


    /** Load Meta Data */
    @Override
    protected org.compiere.model.POInfo initPO (Properties ctx)
    {
      org.compiere.model.POInfo poi = org.compiere.model.POInfo.getPOInfo (ctx, Table_Name, get_TrxName());
      return poi;
    }

	@Override
	public org.compiere.model.I_C_DocType getC_DocType() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_DocType_ID, org.compiere.model.I_C_DocType.class);
	}

	@Override
	public void setC_DocType(org.compiere.model.I_C_DocType C_DocType)
	{
		set_ValueFromPO(COLUMNNAME_C_DocType_ID, org.compiere.model.I_C_DocType.class, C_DocType);
	}

	/** Set Belegart.
		@param C_DocType_ID 
		Belegart oder Verarbeitungsvorgaben
	  */
	@Override
	public void setC_DocType_ID (int C_DocType_ID)
	{
		if (C_DocType_ID < 0) 
			set_ValueNoCheck (COLUMNNAME_C_DocType_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_DocType_ID, Integer.valueOf(C_DocType_ID));
	}

	/** Get Belegart.
		@return Belegart oder Verarbeitungsvorgaben
	  */
	@Override
	public int getC_DocType_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_DocType_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Document Type Sequence assignment.
		@param C_DocType_Sequence_ID Document Type Sequence assignment	  */
	@Override
	public void setC_DocType_Sequence_ID (int C_DocType_Sequence_ID)
	{
		if (C_DocType_Sequence_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_DocType_Sequence_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_DocType_Sequence_ID, Integer.valueOf(C_DocType_Sequence_ID));
	}

	/** Get Document Type Sequence assignment.
		@return Document Type Sequence assignment	  */
	@Override
	public int getC_DocType_Sequence_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_DocType_Sequence_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_AD_Sequence getDocNoSequence() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_DocNoSequence_ID, org.compiere.model.I_AD_Sequence.class);
	}

	@Override
	public void setDocNoSequence(org.compiere.model.I_AD_Sequence DocNoSequence)
	{
		set_ValueFromPO(COLUMNNAME_DocNoSequence_ID, org.compiere.model.I_AD_Sequence.class, DocNoSequence);
	}

	/** Set Nummernfolgen für Belege.
		@param DocNoSequence_ID 
		Document sequence determines the numbering of documents
	  */
	@Override
	public void setDocNoSequence_ID (int DocNoSequence_ID)
	{
		if (DocNoSequence_ID < 1) 
			set_Value (COLUMNNAME_DocNoSequence_ID, null);
		else 
			set_Value (COLUMNNAME_DocNoSequence_ID, Integer.valueOf(DocNoSequence_ID));
	}

	/** Get Nummernfolgen für Belege.
		@return Document sequence determines the numbering of documents
	  */
	@Override
	public int getDocNoSequence_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DocNoSequence_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}