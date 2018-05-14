/** Generated Model - DO NOT CHANGE */
package de.metas.contracts.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_Invoice_Candidate_Assignment
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_Invoice_Candidate_Assignment extends org.compiere.model.PO implements I_C_Invoice_Candidate_Assignment, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -800022948L;

    /** Standard Constructor */
    public X_C_Invoice_Candidate_Assignment (Properties ctx, int C_Invoice_Candidate_Assignment_ID, String trxName)
    {
      super (ctx, C_Invoice_Candidate_Assignment_ID, trxName);
      /** if (C_Invoice_Candidate_Assignment_ID == 0)
        {
			setC_Invoice_Candidate_Assigned_ID (0);
			setC_Invoice_Candidate_Assignment_ID (0);
			setC_Invoice_Candidate_ID (0);
        } */
    }

    /** Load Constructor */
    public X_C_Invoice_Candidate_Assignment (Properties ctx, ResultSet rs, String trxName)
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
	public de.metas.contracts.model.I_C_Flatrate_Term getC_Flatrate_Term() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Flatrate_Term_ID, de.metas.contracts.model.I_C_Flatrate_Term.class);
	}

	@Override
	public void setC_Flatrate_Term(de.metas.contracts.model.I_C_Flatrate_Term C_Flatrate_Term)
	{
		set_ValueFromPO(COLUMNNAME_C_Flatrate_Term_ID, de.metas.contracts.model.I_C_Flatrate_Term.class, C_Flatrate_Term);
	}

	/** Set Pauschale - Vertragsperiode.
		@param C_Flatrate_Term_ID Pauschale - Vertragsperiode	  */
	@Override
	public void setC_Flatrate_Term_ID (int C_Flatrate_Term_ID)
	{
		if (C_Flatrate_Term_ID < 1) 
			set_Value (COLUMNNAME_C_Flatrate_Term_ID, null);
		else 
			set_Value (COLUMNNAME_C_Flatrate_Term_ID, Integer.valueOf(C_Flatrate_Term_ID));
	}

	/** Get Pauschale - Vertragsperiode.
		@return Pauschale - Vertragsperiode	  */
	@Override
	public int getC_Flatrate_Term_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Flatrate_Term_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Zugeordneter Rechnungskandidat.
		@param C_Invoice_Candidate_Assigned_ID Zugeordneter Rechnungskandidat	  */
	@Override
	public void setC_Invoice_Candidate_Assigned_ID (int C_Invoice_Candidate_Assigned_ID)
	{
		if (C_Invoice_Candidate_Assigned_ID < 1) 
			set_Value (COLUMNNAME_C_Invoice_Candidate_Assigned_ID, null);
		else 
			set_Value (COLUMNNAME_C_Invoice_Candidate_Assigned_ID, Integer.valueOf(C_Invoice_Candidate_Assigned_ID));
	}

	/** Get Zugeordneter Rechnungskandidat.
		@return Zugeordneter Rechnungskandidat	  */
	@Override
	public int getC_Invoice_Candidate_Assigned_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Invoice_Candidate_Assigned_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set C_Invoice_Candidate_Assignment.
		@param C_Invoice_Candidate_Assignment_ID C_Invoice_Candidate_Assignment	  */
	@Override
	public void setC_Invoice_Candidate_Assignment_ID (int C_Invoice_Candidate_Assignment_ID)
	{
		if (C_Invoice_Candidate_Assignment_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Invoice_Candidate_Assignment_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Invoice_Candidate_Assignment_ID, Integer.valueOf(C_Invoice_Candidate_Assignment_ID));
	}

	/** Get C_Invoice_Candidate_Assignment.
		@return C_Invoice_Candidate_Assignment	  */
	@Override
	public int getC_Invoice_Candidate_Assignment_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Invoice_Candidate_Assignment_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Rechnungskandidat.
		@param C_Invoice_Candidate_ID 
		Eindeutige Identifikationsnummer eines Rechnungskandidaten
	  */
	@Override
	public void setC_Invoice_Candidate_ID (int C_Invoice_Candidate_ID)
	{
		if (C_Invoice_Candidate_ID < 1) 
			set_Value (COLUMNNAME_C_Invoice_Candidate_ID, null);
		else 
			set_Value (COLUMNNAME_C_Invoice_Candidate_ID, Integer.valueOf(C_Invoice_Candidate_ID));
	}

	/** Get Rechnungskandidat.
		@return Eindeutige Identifikationsnummer eines Rechnungskandidaten
	  */
	@Override
	public int getC_Invoice_Candidate_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Invoice_Candidate_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}