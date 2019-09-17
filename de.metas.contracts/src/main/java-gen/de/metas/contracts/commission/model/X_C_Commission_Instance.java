/** Generated Model - DO NOT CHANGE */
package de.metas.contracts.commission.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_Commission_Instance
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_Commission_Instance extends org.compiere.model.PO implements I_C_Commission_Instance, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -1142977897L;

    /** Standard Constructor */
    public X_C_Commission_Instance (Properties ctx, int C_Commission_Instance_ID, String trxName)
    {
      super (ctx, C_Commission_Instance_ID, trxName);
      /** if (C_Commission_Instance_ID == 0)
        {
			setC_Commission_Instance_ID (0);
			setC_Invoice_Candidate_ID (0);
			setMostRecentTriggerTimestamp (new Timestamp( System.currentTimeMillis() ));
			setPointsBase_Forecasted (BigDecimal.ZERO);
			setPointsBase_Invoiceable (BigDecimal.ZERO);
			setPointsBase_Invoiced (BigDecimal.ZERO);
        } */
    }

    /** Load Constructor */
    public X_C_Commission_Instance (Properties ctx, ResultSet rs, String trxName)
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

	/** Set C_Commission_Instance.
		@param C_Commission_Instance_ID C_Commission_Instance	  */
	@Override
	public void setC_Commission_Instance_ID (int C_Commission_Instance_ID)
	{
		if (C_Commission_Instance_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Commission_Instance_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Commission_Instance_ID, Integer.valueOf(C_Commission_Instance_ID));
	}

	/** Get C_Commission_Instance.
		@return C_Commission_Instance	  */
	@Override
	public int getC_Commission_Instance_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Commission_Instance_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Rechnungskandidat.
		@param C_Invoice_Candidate_ID Rechnungskandidat	  */
	@Override
	public void setC_Invoice_Candidate_ID (int C_Invoice_Candidate_ID)
	{
		if (C_Invoice_Candidate_ID < 1) 
			set_Value (COLUMNNAME_C_Invoice_Candidate_ID, null);
		else 
			set_Value (COLUMNNAME_C_Invoice_Candidate_ID, Integer.valueOf(C_Invoice_Candidate_ID));
	}

	/** Get Rechnungskandidat.
		@return Rechnungskandidat	  */
	@Override
	public int getC_Invoice_Candidate_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Invoice_Candidate_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set MostRecentTriggerTimestamp.
		@param MostRecentTriggerTimestamp MostRecentTriggerTimestamp	  */
	@Override
	public void setMostRecentTriggerTimestamp (java.sql.Timestamp MostRecentTriggerTimestamp)
	{
		set_Value (COLUMNNAME_MostRecentTriggerTimestamp, MostRecentTriggerTimestamp);
	}

	/** Get MostRecentTriggerTimestamp.
		@return MostRecentTriggerTimestamp	  */
	@Override
	public java.sql.Timestamp getMostRecentTriggerTimestamp () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_MostRecentTriggerTimestamp);
	}

	/** Set Prognostizierte Basispunktzahl.
		@param PointsBase_Forecasted Prognostizierte Basispunktzahl	  */
	@Override
	public void setPointsBase_Forecasted (java.math.BigDecimal PointsBase_Forecasted)
	{
		set_Value (COLUMNNAME_PointsBase_Forecasted, PointsBase_Forecasted);
	}

	/** Get Prognostizierte Basispunktzahl.
		@return Prognostizierte Basispunktzahl	  */
	@Override
	public java.math.BigDecimal getPointsBase_Forecasted () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PointsBase_Forecasted);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Abzurechn. Basispunktzahl.
		@param PointsBase_Invoiceable Abzurechn. Basispunktzahl	  */
	@Override
	public void setPointsBase_Invoiceable (java.math.BigDecimal PointsBase_Invoiceable)
	{
		set_Value (COLUMNNAME_PointsBase_Invoiceable, PointsBase_Invoiceable);
	}

	/** Get Abzurechn. Basispunktzahl.
		@return Abzurechn. Basispunktzahl	  */
	@Override
	public java.math.BigDecimal getPointsBase_Invoiceable () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PointsBase_Invoiceable);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Abgerechn. Basispunktzahl.
		@param PointsBase_Invoiced Abgerechn. Basispunktzahl	  */
	@Override
	public void setPointsBase_Invoiced (java.math.BigDecimal PointsBase_Invoiced)
	{
		set_Value (COLUMNNAME_PointsBase_Invoiced, PointsBase_Invoiced);
	}

	/** Get Abgerechn. Basispunktzahl.
		@return Abgerechn. Basispunktzahl	  */
	@Override
	public java.math.BigDecimal getPointsBase_Invoiced () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PointsBase_Invoiced);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}
}