/** Generated Model - DO NOT CHANGE */
package de.metas.material.dispo.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for MD_Candidate_Demand_Detail
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("javadoc")
public class X_MD_Candidate_Demand_Detail extends org.compiere.model.PO implements I_MD_Candidate_Demand_Detail, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 224168650L;

    /** Standard Constructor */
    public X_MD_Candidate_Demand_Detail (Properties ctx, int MD_Candidate_Demand_Detail_ID, String trxName)
    {
      super (ctx, MD_Candidate_Demand_Detail_ID, trxName);
    }

    /** Load Constructor */
    public X_MD_Candidate_Demand_Detail (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }


	/** Load Meta Data */
	@Override
	protected org.compiere.model.POInfo initPO(Properties ctx)
	{
		return org.compiere.model.POInfo.getPOInfo(Table_Name);
	}

	@Override
	public void setActualQty (java.math.BigDecimal ActualQty)
	{
		set_Value (COLUMNNAME_ActualQty, ActualQty);
	}

	@Override
	public java.math.BigDecimal getActualQty() 
	{
		BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_ActualQty);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public org.compiere.model.I_C_OrderLine getC_OrderLine()
	{
		return get_ValueAsPO(COLUMNNAME_C_OrderLine_ID, org.compiere.model.I_C_OrderLine.class);
	}

	@Override
	public void setC_OrderLine(org.compiere.model.I_C_OrderLine C_OrderLine)
	{
		set_ValueFromPO(COLUMNNAME_C_OrderLine_ID, org.compiere.model.I_C_OrderLine.class, C_OrderLine);
	}

	@Override
	public void setC_OrderLine_ID (int C_OrderLine_ID)
	{
		if (C_OrderLine_ID < 1) 
			set_Value (COLUMNNAME_C_OrderLine_ID, null);
		else 
			set_Value (COLUMNNAME_C_OrderLine_ID, Integer.valueOf(C_OrderLine_ID));
	}

	@Override
	public int getC_OrderLine_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_OrderLine_ID);
	}

	@Override
	public void setC_SubscriptionProgress_ID (int C_SubscriptionProgress_ID)
	{
		if (C_SubscriptionProgress_ID < 1) 
			set_Value (COLUMNNAME_C_SubscriptionProgress_ID, null);
		else 
			set_Value (COLUMNNAME_C_SubscriptionProgress_ID, Integer.valueOf(C_SubscriptionProgress_ID));
	}

	@Override
	public int getC_SubscriptionProgress_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_SubscriptionProgress_ID);
	}

	@Override
	public void setMD_Candidate_Demand_Detail_ID (int MD_Candidate_Demand_Detail_ID)
	{
		if (MD_Candidate_Demand_Detail_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_MD_Candidate_Demand_Detail_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_MD_Candidate_Demand_Detail_ID, Integer.valueOf(MD_Candidate_Demand_Detail_ID));
	}

	@Override
	public int getMD_Candidate_Demand_Detail_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_MD_Candidate_Demand_Detail_ID);
	}

	@Override
	public de.metas.material.dispo.model.I_MD_Candidate getMD_Candidate()
	{
		return get_ValueAsPO(COLUMNNAME_MD_Candidate_ID, de.metas.material.dispo.model.I_MD_Candidate.class);
	}

	@Override
	public void setMD_Candidate(de.metas.material.dispo.model.I_MD_Candidate MD_Candidate)
	{
		set_ValueFromPO(COLUMNNAME_MD_Candidate_ID, de.metas.material.dispo.model.I_MD_Candidate.class, MD_Candidate);
	}

	@Override
	public void setMD_Candidate_ID (int MD_Candidate_ID)
	{
		if (MD_Candidate_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_MD_Candidate_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_MD_Candidate_ID, Integer.valueOf(MD_Candidate_ID));
	}

	@Override
	public int getMD_Candidate_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_MD_Candidate_ID);
	}

	@Override
	public org.compiere.model.I_M_ForecastLine getM_ForecastLine()
	{
		return get_ValueAsPO(COLUMNNAME_M_ForecastLine_ID, org.compiere.model.I_M_ForecastLine.class);
	}

	@Override
	public void setM_ForecastLine(org.compiere.model.I_M_ForecastLine M_ForecastLine)
	{
		set_ValueFromPO(COLUMNNAME_M_ForecastLine_ID, org.compiere.model.I_M_ForecastLine.class, M_ForecastLine);
	}

	@Override
	public void setM_ForecastLine_ID (int M_ForecastLine_ID)
	{
		if (M_ForecastLine_ID < 1) 
			set_Value (COLUMNNAME_M_ForecastLine_ID, null);
		else 
			set_Value (COLUMNNAME_M_ForecastLine_ID, Integer.valueOf(M_ForecastLine_ID));
	}

	@Override
	public int getM_ForecastLine_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_ForecastLine_ID);
	}

	@Override
	public void setM_ShipmentSchedule_ID (int M_ShipmentSchedule_ID)
	{
		if (M_ShipmentSchedule_ID < 1) 
			set_Value (COLUMNNAME_M_ShipmentSchedule_ID, null);
		else 
			set_Value (COLUMNNAME_M_ShipmentSchedule_ID, Integer.valueOf(M_ShipmentSchedule_ID));
	}

	@Override
	public int getM_ShipmentSchedule_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_ShipmentSchedule_ID);
	}

	@Override
	public void setPlannedQty (java.math.BigDecimal PlannedQty)
	{
		set_Value (COLUMNNAME_PlannedQty, PlannedQty);
	}

	@Override
	public java.math.BigDecimal getPlannedQty() 
	{
		BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_PlannedQty);
		return bd != null ? bd : BigDecimal.ZERO;
	}
}