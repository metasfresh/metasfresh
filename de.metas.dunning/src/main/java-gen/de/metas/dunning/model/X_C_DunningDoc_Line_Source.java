/** Generated Model - DO NOT CHANGE */
package de.metas.dunning.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_DunningDoc_Line_Source
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_DunningDoc_Line_Source extends org.compiere.model.PO implements I_C_DunningDoc_Line_Source, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 22277120L;

    /** Standard Constructor */
    public X_C_DunningDoc_Line_Source (Properties ctx, int C_DunningDoc_Line_Source_ID, String trxName)
    {
      super (ctx, C_DunningDoc_Line_Source_ID, trxName);
      /** if (C_DunningDoc_Line_Source_ID == 0)
        {
			setC_Dunning_Candidate_ID (0);
			setC_DunningDoc_Line_ID (0);
			setC_DunningDoc_Line_Source_ID (0);
			setIsWriteOff (false); // N
			setIsWriteOffApplied (false); // N
			setProcessed (false); // N
        } */
    }

    /** Load Constructor */
    public X_C_DunningDoc_Line_Source (Properties ctx, ResultSet rs, String trxName)
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
	public de.metas.dunning.model.I_C_Dunning_Candidate getC_Dunning_Candidate() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Dunning_Candidate_ID, de.metas.dunning.model.I_C_Dunning_Candidate.class);
	}

	@Override
	public void setC_Dunning_Candidate(de.metas.dunning.model.I_C_Dunning_Candidate C_Dunning_Candidate)
	{
		set_ValueFromPO(COLUMNNAME_C_Dunning_Candidate_ID, de.metas.dunning.model.I_C_Dunning_Candidate.class, C_Dunning_Candidate);
	}

	/** Set Mahnungsdisposition.
		@param C_Dunning_Candidate_ID Mahnungsdisposition	  */
	@Override
	public void setC_Dunning_Candidate_ID (int C_Dunning_Candidate_ID)
	{
		if (C_Dunning_Candidate_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Dunning_Candidate_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Dunning_Candidate_ID, Integer.valueOf(C_Dunning_Candidate_ID));
	}

	/** Get Mahnungsdisposition.
		@return Mahnungsdisposition	  */
	@Override
	public int getC_Dunning_Candidate_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Dunning_Candidate_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.dunning.model.I_C_DunningDoc_Line getC_DunningDoc_Line() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_DunningDoc_Line_ID, de.metas.dunning.model.I_C_DunningDoc_Line.class);
	}

	@Override
	public void setC_DunningDoc_Line(de.metas.dunning.model.I_C_DunningDoc_Line C_DunningDoc_Line)
	{
		set_ValueFromPO(COLUMNNAME_C_DunningDoc_Line_ID, de.metas.dunning.model.I_C_DunningDoc_Line.class, C_DunningDoc_Line);
	}

	/** Set Dunning Document Line.
		@param C_DunningDoc_Line_ID Dunning Document Line	  */
	@Override
	public void setC_DunningDoc_Line_ID (int C_DunningDoc_Line_ID)
	{
		if (C_DunningDoc_Line_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_DunningDoc_Line_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_DunningDoc_Line_ID, Integer.valueOf(C_DunningDoc_Line_ID));
	}

	/** Get Dunning Document Line.
		@return Dunning Document Line	  */
	@Override
	public int getC_DunningDoc_Line_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_DunningDoc_Line_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Dunning Document Line Source.
		@param C_DunningDoc_Line_Source_ID Dunning Document Line Source	  */
	@Override
	public void setC_DunningDoc_Line_Source_ID (int C_DunningDoc_Line_Source_ID)
	{
		if (C_DunningDoc_Line_Source_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_DunningDoc_Line_Source_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_DunningDoc_Line_Source_ID, Integer.valueOf(C_DunningDoc_Line_Source_ID));
	}

	/** Get Dunning Document Line Source.
		@return Dunning Document Line Source	  */
	@Override
	public int getC_DunningDoc_Line_Source_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_DunningDoc_Line_Source_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Massenaustritt.
		@param IsWriteOff Massenaustritt	  */
	@Override
	public void setIsWriteOff (boolean IsWriteOff)
	{
		set_Value (COLUMNNAME_IsWriteOff, Boolean.valueOf(IsWriteOff));
	}

	/** Get Massenaustritt.
		@return Massenaustritt	  */
	@Override
	public boolean isWriteOff () 
	{
		Object oo = get_Value(COLUMNNAME_IsWriteOff);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Massenaustritt Applied.
		@param IsWriteOffApplied Massenaustritt Applied	  */
	@Override
	public void setIsWriteOffApplied (boolean IsWriteOffApplied)
	{
		set_Value (COLUMNNAME_IsWriteOffApplied, Boolean.valueOf(IsWriteOffApplied));
	}

	/** Get Massenaustritt Applied.
		@return Massenaustritt Applied	  */
	@Override
	public boolean isWriteOffApplied () 
	{
		Object oo = get_Value(COLUMNNAME_IsWriteOffApplied);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Verarbeitet.
		@param Processed 
		Checkbox sagt aus, ob der Beleg verarbeitet wurde. 
	  */
	@Override
	public void setProcessed (boolean Processed)
	{
		set_Value (COLUMNNAME_Processed, Boolean.valueOf(Processed));
	}

	/** Get Verarbeitet.
		@return Checkbox sagt aus, ob der Beleg verarbeitet wurde. 
	  */
	@Override
	public boolean isProcessed () 
	{
		Object oo = get_Value(COLUMNNAME_Processed);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}
}