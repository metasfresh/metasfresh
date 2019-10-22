/** Generated Model - DO NOT CHANGE */
package de.metas.contracts.commission.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_Commission_Fact
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_Commission_Fact extends org.compiere.model.PO implements I_C_Commission_Fact, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 270076088L;

    /** Standard Constructor */
    public X_C_Commission_Fact (Properties ctx, int C_Commission_Fact_ID, String trxName)
    {
      super (ctx, C_Commission_Fact_ID, trxName);
      /** if (C_Commission_Fact_ID == 0)
        {
			setC_Commission_Fact_ID (0);
			setC_Commission_Share_ID (0);
			setCommission_Fact_State (null);
			setCommissionFactTimestamp (null);
			setCommissionPoints (BigDecimal.ZERO);
        } */
    }

    /** Load Constructor */
    public X_C_Commission_Fact (Properties ctx, ResultSet rs, String trxName)
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

	/** Set C_Commission_Fact.
		@param C_Commission_Fact_ID C_Commission_Fact	  */
	@Override
	public void setC_Commission_Fact_ID (int C_Commission_Fact_ID)
	{
		if (C_Commission_Fact_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Commission_Fact_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Commission_Fact_ID, Integer.valueOf(C_Commission_Fact_ID));
	}

	/** Get C_Commission_Fact.
		@return C_Commission_Fact	  */
	@Override
	public int getC_Commission_Fact_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Commission_Fact_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.contracts.commission.model.I_C_Commission_Share getC_Commission_Share()
	{
		return get_ValueAsPO(COLUMNNAME_C_Commission_Share_ID, de.metas.contracts.commission.model.I_C_Commission_Share.class);
	}

	@Override
	public void setC_Commission_Share(de.metas.contracts.commission.model.I_C_Commission_Share C_Commission_Share)
	{
		set_ValueFromPO(COLUMNNAME_C_Commission_Share_ID, de.metas.contracts.commission.model.I_C_Commission_Share.class, C_Commission_Share);
	}

	/** Set C_Commission_Share.
		@param C_Commission_Share_ID C_Commission_Share	  */
	@Override
	public void setC_Commission_Share_ID (int C_Commission_Share_ID)
	{
		if (C_Commission_Share_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Commission_Share_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Commission_Share_ID, Integer.valueOf(C_Commission_Share_ID));
	}

	/** Get C_Commission_Share.
		@return C_Commission_Share	  */
	@Override
	public int getC_Commission_Share_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Commission_Share_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Provisionsabrechnungskandidat.
		@param C_Invoice_Candidate_Commission_ID Provisionsabrechnungskandidat	  */
	@Override
	public void setC_Invoice_Candidate_Commission_ID (int C_Invoice_Candidate_Commission_ID)
	{
		if (C_Invoice_Candidate_Commission_ID < 1) 
			set_Value (COLUMNNAME_C_Invoice_Candidate_Commission_ID, null);
		else 
			set_Value (COLUMNNAME_C_Invoice_Candidate_Commission_ID, Integer.valueOf(C_Invoice_Candidate_Commission_ID));
	}

	/** Get Provisionsabrechnungskandidat.
		@return Provisionsabrechnungskandidat	  */
	@Override
	public int getC_Invoice_Candidate_Commission_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Invoice_Candidate_Commission_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** 
	 * Commission_Fact_State AD_Reference_ID=541042
	 * Reference name: C_Commission_Fact_State
	 */
	public static final int COMMISSION_FACT_STATE_AD_Reference_ID=541042;
	/** FORECASTED = FORECASTED */
	public static final String COMMISSION_FACT_STATE_FORECASTED = "FORECASTED";
	/** INVOICEABLE = INVOICEABLE */
	public static final String COMMISSION_FACT_STATE_INVOICEABLE = "INVOICEABLE";
	/** INVOICED = INVOICED */
	public static final String COMMISSION_FACT_STATE_INVOICED = "INVOICED";
	/** SETTLED = SETTLED */
	public static final String COMMISSION_FACT_STATE_SETTLED = "SETTLED";
	/** TO_SETTLE = TO_SETTLE */
	public static final String COMMISSION_FACT_STATE_TO_SETTLE = "TO_SETTLE";
	/** Set Status.
		@param Commission_Fact_State Status	  */
	@Override
	public void setCommission_Fact_State (java.lang.String Commission_Fact_State)
	{

		set_ValueNoCheck (COLUMNNAME_Commission_Fact_State, Commission_Fact_State);
	}

	/** Get Status.
		@return Status	  */
	@Override
	public java.lang.String getCommission_Fact_State () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Commission_Fact_State);
	}

	/** Set Zeitstempel.
		@param CommissionFactTimestamp Zeitstempel	  */
	@Override
	public void setCommissionFactTimestamp (java.lang.String CommissionFactTimestamp)
	{
		set_ValueNoCheck (COLUMNNAME_CommissionFactTimestamp, CommissionFactTimestamp);
	}

	/** Get Zeitstempel.
		@return Zeitstempel	  */
	@Override
	public java.lang.String getCommissionFactTimestamp () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_CommissionFactTimestamp);
	}

	/** Set Provisionspunkte.
		@param CommissionPoints Provisionspunkte	  */
	@Override
	public void setCommissionPoints (java.math.BigDecimal CommissionPoints)
	{
		set_ValueNoCheck (COLUMNNAME_CommissionPoints, CommissionPoints);
	}

	/** Get Provisionspunkte.
		@return Provisionspunkte	  */
	@Override
	public java.math.BigDecimal getCommissionPoints () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_CommissionPoints);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}
}