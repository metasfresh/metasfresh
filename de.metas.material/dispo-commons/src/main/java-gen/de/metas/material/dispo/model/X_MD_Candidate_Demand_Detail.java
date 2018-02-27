/** Generated Model - DO NOT CHANGE */
package de.metas.material.dispo.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for MD_Candidate_Demand_Detail
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_MD_Candidate_Demand_Detail extends org.compiere.model.PO implements I_MD_Candidate_Demand_Detail, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1910542388L;

    /** Standard Constructor */
    public X_MD_Candidate_Demand_Detail (Properties ctx, int MD_Candidate_Demand_Detail_ID, String trxName)
    {
      super (ctx, MD_Candidate_Demand_Detail_ID, trxName);
      /** if (MD_Candidate_Demand_Detail_ID == 0)
        {
			setMD_Candidate_Demand_Detail_ID (0);
			setMD_Candidate_ID (0);
        } */
    }

    /** Load Constructor */
    public X_MD_Candidate_Demand_Detail (Properties ctx, ResultSet rs, String trxName)
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
	public org.compiere.model.I_C_OrderLine getC_OrderLine() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_OrderLine_ID, org.compiere.model.I_C_OrderLine.class);
	}

	@Override
	public void setC_OrderLine(org.compiere.model.I_C_OrderLine C_OrderLine)
	{
		set_ValueFromPO(COLUMNNAME_C_OrderLine_ID, org.compiere.model.I_C_OrderLine.class, C_OrderLine);
	}

	/** Set Auftragsposition.
		@param C_OrderLine_ID 
		Auftragsposition
	  */
	@Override
	public void setC_OrderLine_ID (int C_OrderLine_ID)
	{
		if (C_OrderLine_ID < 1) 
			set_Value (COLUMNNAME_C_OrderLine_ID, null);
		else 
			set_Value (COLUMNNAME_C_OrderLine_ID, Integer.valueOf(C_OrderLine_ID));
	}

	/** Get Auftragsposition.
		@return Auftragsposition
	  */
	@Override
	public int getC_OrderLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_OrderLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Abo-Verlauf.
		@param C_SubscriptionProgress_ID Abo-Verlauf	  */
	@Override
	public void setC_SubscriptionProgress_ID (int C_SubscriptionProgress_ID)
	{
		if (C_SubscriptionProgress_ID < 1) 
			set_Value (COLUMNNAME_C_SubscriptionProgress_ID, null);
		else 
			set_Value (COLUMNNAME_C_SubscriptionProgress_ID, Integer.valueOf(C_SubscriptionProgress_ID));
	}

	/** Get Abo-Verlauf.
		@return Abo-Verlauf	  */
	@Override
	public int getC_SubscriptionProgress_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_SubscriptionProgress_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Dispo-Bedarfsdetail.
		@param MD_Candidate_Demand_Detail_ID Dispo-Bedarfsdetail	  */
	@Override
	public void setMD_Candidate_Demand_Detail_ID (int MD_Candidate_Demand_Detail_ID)
	{
		if (MD_Candidate_Demand_Detail_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_MD_Candidate_Demand_Detail_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_MD_Candidate_Demand_Detail_ID, Integer.valueOf(MD_Candidate_Demand_Detail_ID));
	}

	/** Get Dispo-Bedarfsdetail.
		@return Dispo-Bedarfsdetail	  */
	@Override
	public int getMD_Candidate_Demand_Detail_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_MD_Candidate_Demand_Detail_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.material.dispo.model.I_MD_Candidate getMD_Candidate() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_MD_Candidate_ID, de.metas.material.dispo.model.I_MD_Candidate.class);
	}

	@Override
	public void setMD_Candidate(de.metas.material.dispo.model.I_MD_Candidate MD_Candidate)
	{
		set_ValueFromPO(COLUMNNAME_MD_Candidate_ID, de.metas.material.dispo.model.I_MD_Candidate.class, MD_Candidate);
	}

	/** Set Dispositionskandidat.
		@param MD_Candidate_ID Dispositionskandidat	  */
	@Override
	public void setMD_Candidate_ID (int MD_Candidate_ID)
	{
		if (MD_Candidate_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_MD_Candidate_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_MD_Candidate_ID, Integer.valueOf(MD_Candidate_ID));
	}

	/** Get Dispositionskandidat.
		@return Dispositionskandidat	  */
	@Override
	public int getMD_Candidate_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_MD_Candidate_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_M_ForecastLine getM_ForecastLine() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_ForecastLine_ID, org.compiere.model.I_M_ForecastLine.class);
	}

	@Override
	public void setM_ForecastLine(org.compiere.model.I_M_ForecastLine M_ForecastLine)
	{
		set_ValueFromPO(COLUMNNAME_M_ForecastLine_ID, org.compiere.model.I_M_ForecastLine.class, M_ForecastLine);
	}

	/** Set Prognose-Position.
		@param M_ForecastLine_ID 
		Prognose-Position
	  */
	@Override
	public void setM_ForecastLine_ID (int M_ForecastLine_ID)
	{
		if (M_ForecastLine_ID < 1) 
			set_Value (COLUMNNAME_M_ForecastLine_ID, null);
		else 
			set_Value (COLUMNNAME_M_ForecastLine_ID, Integer.valueOf(M_ForecastLine_ID));
	}

	/** Get Prognose-Position.
		@return Prognose-Position
	  */
	@Override
	public int getM_ForecastLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_ForecastLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Lieferdisposition.
		@param M_ShipmentSchedule_ID Lieferdisposition	  */
	@Override
	public void setM_ShipmentSchedule_ID (int M_ShipmentSchedule_ID)
	{
		if (M_ShipmentSchedule_ID < 1) 
			set_Value (COLUMNNAME_M_ShipmentSchedule_ID, null);
		else 
			set_Value (COLUMNNAME_M_ShipmentSchedule_ID, Integer.valueOf(M_ShipmentSchedule_ID));
	}

	/** Get Lieferdisposition.
		@return Lieferdisposition	  */
	@Override
	public int getM_ShipmentSchedule_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_ShipmentSchedule_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}