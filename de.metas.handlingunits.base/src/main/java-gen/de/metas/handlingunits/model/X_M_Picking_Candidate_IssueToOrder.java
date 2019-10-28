/** Generated Model - DO NOT CHANGE */
package de.metas.handlingunits.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for M_Picking_Candidate_IssueToOrder
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_M_Picking_Candidate_IssueToOrder extends org.compiere.model.PO implements I_M_Picking_Candidate_IssueToOrder, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -589620981L;

    /** Standard Constructor */
    public X_M_Picking_Candidate_IssueToOrder (Properties ctx, int M_Picking_Candidate_IssueToOrder_ID, String trxName)
    {
      super (ctx, M_Picking_Candidate_IssueToOrder_ID, trxName);
      /** if (M_Picking_Candidate_IssueToOrder_ID == 0)
        {
			setC_UOM_ID (0);
			setM_HU_ID (0);
			setM_Picking_Candidate_IssueToOrder_ID (0);
			setPP_Order_BOMLine_ID (0);
			setQtyToIssue (BigDecimal.ZERO);
        } */
    }

    /** Load Constructor */
    public X_M_Picking_Candidate_IssueToOrder (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Maßeinheit.
		@param C_UOM_ID 
		Maßeinheit
	  */
	@Override
	public void setC_UOM_ID (int C_UOM_ID)
	{
		if (C_UOM_ID < 1) 
			set_Value (COLUMNNAME_C_UOM_ID, null);
		else 
			set_Value (COLUMNNAME_C_UOM_ID, Integer.valueOf(C_UOM_ID));
	}

	/** Get Maßeinheit.
		@return Maßeinheit
	  */
	@Override
	public int getC_UOM_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_UOM_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.handlingunits.model.I_M_HU getM_HU()
	{
		return get_ValueAsPO(COLUMNNAME_M_HU_ID, de.metas.handlingunits.model.I_M_HU.class);
	}

	@Override
	public void setM_HU(de.metas.handlingunits.model.I_M_HU M_HU)
	{
		set_ValueFromPO(COLUMNNAME_M_HU_ID, de.metas.handlingunits.model.I_M_HU.class, M_HU);
	}

	/** Set Handling Unit.
		@param M_HU_ID Handling Unit	  */
	@Override
	public void setM_HU_ID (int M_HU_ID)
	{
		if (M_HU_ID < 1) 
			set_Value (COLUMNNAME_M_HU_ID, null);
		else 
			set_Value (COLUMNNAME_M_HU_ID, Integer.valueOf(M_HU_ID));
	}

	/** Get Handling Unit.
		@return Handling Unit	  */
	@Override
	public int getM_HU_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_HU_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Picking Candidate - issue to manufacturing order.
		@param M_Picking_Candidate_IssueToOrder_ID Picking Candidate - issue to manufacturing order	  */
	@Override
	public void setM_Picking_Candidate_IssueToOrder_ID (int M_Picking_Candidate_IssueToOrder_ID)
	{
		if (M_Picking_Candidate_IssueToOrder_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Picking_Candidate_IssueToOrder_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Picking_Candidate_IssueToOrder_ID, Integer.valueOf(M_Picking_Candidate_IssueToOrder_ID));
	}

	/** Get Picking Candidate - issue to manufacturing order.
		@return Picking Candidate - issue to manufacturing order	  */
	@Override
	public int getM_Picking_Candidate_IssueToOrder_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Picking_Candidate_IssueToOrder_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.eevolution.model.I_PP_Cost_Collector getPP_Cost_Collector()
	{
		return get_ValueAsPO(COLUMNNAME_PP_Cost_Collector_ID, org.eevolution.model.I_PP_Cost_Collector.class);
	}

	@Override
	public void setPP_Cost_Collector(org.eevolution.model.I_PP_Cost_Collector PP_Cost_Collector)
	{
		set_ValueFromPO(COLUMNNAME_PP_Cost_Collector_ID, org.eevolution.model.I_PP_Cost_Collector.class, PP_Cost_Collector);
	}

	/** Set Manufacturing Cost Collector.
		@param PP_Cost_Collector_ID Manufacturing Cost Collector	  */
	@Override
	public void setPP_Cost_Collector_ID (int PP_Cost_Collector_ID)
	{
		if (PP_Cost_Collector_ID < 1) 
			set_Value (COLUMNNAME_PP_Cost_Collector_ID, null);
		else 
			set_Value (COLUMNNAME_PP_Cost_Collector_ID, Integer.valueOf(PP_Cost_Collector_ID));
	}

	/** Get Manufacturing Cost Collector.
		@return Manufacturing Cost Collector	  */
	@Override
	public int getPP_Cost_Collector_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PP_Cost_Collector_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.eevolution.model.I_PP_Order_BOMLine getPP_Order_BOMLine()
	{
		return get_ValueAsPO(COLUMNNAME_PP_Order_BOMLine_ID, org.eevolution.model.I_PP_Order_BOMLine.class);
	}

	@Override
	public void setPP_Order_BOMLine(org.eevolution.model.I_PP_Order_BOMLine PP_Order_BOMLine)
	{
		set_ValueFromPO(COLUMNNAME_PP_Order_BOMLine_ID, org.eevolution.model.I_PP_Order_BOMLine.class, PP_Order_BOMLine);
	}

	/** Set Manufacturing Order BOM Line.
		@param PP_Order_BOMLine_ID Manufacturing Order BOM Line	  */
	@Override
	public void setPP_Order_BOMLine_ID (int PP_Order_BOMLine_ID)
	{
		if (PP_Order_BOMLine_ID < 1) 
			set_Value (COLUMNNAME_PP_Order_BOMLine_ID, null);
		else 
			set_Value (COLUMNNAME_PP_Order_BOMLine_ID, Integer.valueOf(PP_Order_BOMLine_ID));
	}

	/** Get Manufacturing Order BOM Line.
		@return Manufacturing Order BOM Line	  */
	@Override
	public int getPP_Order_BOMLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PP_Order_BOMLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Quantity To Issue.
		@param QtyToIssue Quantity To Issue	  */
	@Override
	public void setQtyToIssue (java.math.BigDecimal QtyToIssue)
	{
		set_Value (COLUMNNAME_QtyToIssue, QtyToIssue);
	}

	/** Get Quantity To Issue.
		@return Quantity To Issue	  */
	@Override
	public java.math.BigDecimal getQtyToIssue () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyToIssue);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}
}