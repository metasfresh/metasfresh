/** Generated Model - DO NOT CHANGE */
package de.metas.contracts.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_Invoice_Candidate_Assignment_Aggregate_V
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_Invoice_Candidate_Assignment_Aggregate_V extends org.compiere.model.PO implements I_C_Invoice_Candidate_Assignment_Aggregate_V, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1709043075L;

    /** Standard Constructor */
    public X_C_Invoice_Candidate_Assignment_Aggregate_V (Properties ctx, int C_Invoice_Candidate_Assignment_Aggregate_V_ID, String trxName)
    {
      super (ctx, C_Invoice_Candidate_Assignment_Aggregate_V_ID, trxName);
      /** if (C_Invoice_Candidate_Assignment_Aggregate_V_ID == 0)
        {
        } */
    }

    /** Load Constructor */
    public X_C_Invoice_Candidate_Assignment_Aggregate_V (Properties ctx, ResultSet rs, String trxName)
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
		set_ValueNoCheck (COLUMNNAME_AssignedMoneyAmount, AssignedMoneyAmount);
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
		set_ValueNoCheck (COLUMNNAME_AssignedQuantity, AssignedQuantity);
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
			set_ValueNoCheck (COLUMNNAME_C_Flatrate_RefundConfig_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Flatrate_RefundConfig_ID, Integer.valueOf(C_Flatrate_RefundConfig_ID));
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

	/** Set Vertrag-Rechnungskandidat.
		@param C_Invoice_Candidate_Term_ID Vertrag-Rechnungskandidat	  */
	@Override
	public void setC_Invoice_Candidate_Term_ID (int C_Invoice_Candidate_Term_ID)
	{
		if (C_Invoice_Candidate_Term_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Invoice_Candidate_Term_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Invoice_Candidate_Term_ID, Integer.valueOf(C_Invoice_Candidate_Term_ID));
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
}