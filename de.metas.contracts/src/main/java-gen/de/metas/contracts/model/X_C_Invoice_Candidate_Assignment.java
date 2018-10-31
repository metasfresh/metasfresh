/** Generated Model - DO NOT CHANGE */
package de.metas.contracts.model;

import java.math.BigDecimal;
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
	private static final long serialVersionUID = -982526037L;

    /** Standard Constructor */
    public X_C_Invoice_Candidate_Assignment (Properties ctx, int C_Invoice_Candidate_Assignment_ID, String trxName)
    {
      super (ctx, C_Invoice_Candidate_Assignment_ID, trxName);
      /** if (C_Invoice_Candidate_Assignment_ID == 0)
        {
			setAssignedQuantity (BigDecimal.ZERO); // 0
			setC_Flatrate_RefundConfig_ID (0);
			setC_Invoice_Candidate_Assigned_ID (0);
			setC_Invoice_Candidate_Assignment_ID (0);
			setC_Invoice_Candidate_Term_ID (0);
			setIsAssignedQuantityIncludedInSum (false); // N
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

	/** Set Zugeordneter Geldbetrag.
		@param AssignedMoneyAmount 
		Zugeordneter Geldbetrag, in der Währung des Vertrags-Rechnungskandidaten.
	  */
	@Override
	public void setAssignedMoneyAmount (java.math.BigDecimal AssignedMoneyAmount)
	{
		set_Value (COLUMNNAME_AssignedMoneyAmount, AssignedMoneyAmount);
	}

	/** Get Zugeordneter Geldbetrag.
		@return Zugeordneter Geldbetrag, in der Währung des Vertrags-Rechnungskandidaten.
	  */
	@Override
	public java.math.BigDecimal getAssignedMoneyAmount () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AssignedMoneyAmount);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Zugeordnete Menge.
		@param AssignedQuantity 
		Zugeordneter Menge in der Maßeinheit des jeweiligen Produktes
	  */
	@Override
	public void setAssignedQuantity (java.math.BigDecimal AssignedQuantity)
	{
		set_Value (COLUMNNAME_AssignedQuantity, AssignedQuantity);
	}

	/** Get Zugeordnete Menge.
		@return Zugeordneter Menge in der Maßeinheit des jeweiligen Produktes
	  */
	@Override
	public java.math.BigDecimal getAssignedQuantity () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AssignedQuantity);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Basisbetrag.
		@param BaseMoneyAmount Basisbetrag	  */
	@Override
	public void setBaseMoneyAmount (java.math.BigDecimal BaseMoneyAmount)
	{
		set_Value (COLUMNNAME_BaseMoneyAmount, BaseMoneyAmount);
	}

	/** Get Basisbetrag.
		@return Basisbetrag	  */
	@Override
	public java.math.BigDecimal getBaseMoneyAmount () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_BaseMoneyAmount);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	@Override
	public de.metas.contracts.model.I_C_Flatrate_RefundConfig getC_Flatrate_RefundConfig() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Flatrate_RefundConfig_ID, de.metas.contracts.model.I_C_Flatrate_RefundConfig.class);
	}

	@Override
	public void setC_Flatrate_RefundConfig(de.metas.contracts.model.I_C_Flatrate_RefundConfig C_Flatrate_RefundConfig)
	{
		set_ValueFromPO(COLUMNNAME_C_Flatrate_RefundConfig_ID, de.metas.contracts.model.I_C_Flatrate_RefundConfig.class, C_Flatrate_RefundConfig);
	}

	/** Set C_Flatrate_RefundConfig.
		@param C_Flatrate_RefundConfig_ID C_Flatrate_RefundConfig	  */
	@Override
	public void setC_Flatrate_RefundConfig_ID (int C_Flatrate_RefundConfig_ID)
	{
		if (C_Flatrate_RefundConfig_ID < 1) 
			set_Value (COLUMNNAME_C_Flatrate_RefundConfig_ID, null);
		else 
			set_Value (COLUMNNAME_C_Flatrate_RefundConfig_ID, Integer.valueOf(C_Flatrate_RefundConfig_ID));
	}

	/** Get C_Flatrate_RefundConfig.
		@return C_Flatrate_RefundConfig	  */
	@Override
	public int getC_Flatrate_RefundConfig_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Flatrate_RefundConfig_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Vertrag-Rechnungskandidat.
		@param C_Invoice_Candidate_Term_ID Vertrag-Rechnungskandidat	  */
	@Override
	public void setC_Invoice_Candidate_Term_ID (int C_Invoice_Candidate_Term_ID)
	{
		if (C_Invoice_Candidate_Term_ID < 1) 
			set_Value (COLUMNNAME_C_Invoice_Candidate_Term_ID, null);
		else 
			set_Value (COLUMNNAME_C_Invoice_Candidate_Term_ID, Integer.valueOf(C_Invoice_Candidate_Term_ID));
	}

	/** Get Vertrag-Rechnungskandidat.
		@return Vertrag-Rechnungskandidat	  */
	@Override
	public int getC_Invoice_Candidate_Term_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Invoice_Candidate_Term_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Zugeordnete Menge wird in Summe einbez..
		@param IsAssignedQuantityIncludedInSum Zugeordnete Menge wird in Summe einbez.	  */
	@Override
	public void setIsAssignedQuantityIncludedInSum (boolean IsAssignedQuantityIncludedInSum)
	{
		set_Value (COLUMNNAME_IsAssignedQuantityIncludedInSum, Boolean.valueOf(IsAssignedQuantityIncludedInSum));
	}

	/** Get Zugeordnete Menge wird in Summe einbez..
		@return Zugeordnete Menge wird in Summe einbez.	  */
	@Override
	public boolean isAssignedQuantityIncludedInSum () 
	{
		Object oo = get_Value(COLUMNNAME_IsAssignedQuantityIncludedInSum);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}
}