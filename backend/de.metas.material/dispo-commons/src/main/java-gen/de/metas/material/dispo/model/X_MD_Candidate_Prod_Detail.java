/** Generated Model - DO NOT CHANGE */
package de.metas.material.dispo.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for MD_Candidate_Prod_Detail
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("javadoc")
public class X_MD_Candidate_Prod_Detail extends org.compiere.model.PO implements I_MD_Candidate_Prod_Detail, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 1340557322L;

    /** Standard Constructor */
    public X_MD_Candidate_Prod_Detail (Properties ctx, int MD_Candidate_Prod_Detail_ID, String trxName)
    {
      super (ctx, MD_Candidate_Prod_Detail_ID, trxName);
    }

    /** Load Constructor */
    public X_MD_Candidate_Prod_Detail (Properties ctx, ResultSet rs, String trxName)
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
	public void setDescription (java.lang.String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	@Override
	public java.lang.String getDescription() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Description);
	}

	@Override
	public void setIsAdvised (boolean IsAdvised)
	{
		set_Value (COLUMNNAME_IsAdvised, Boolean.valueOf(IsAdvised));
	}

	@Override
	public boolean isAdvised() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsAdvised);
	}

	@Override
	public void setIsPickDirectlyIfFeasible (boolean IsPickDirectlyIfFeasible)
	{
		set_Value (COLUMNNAME_IsPickDirectlyIfFeasible, Boolean.valueOf(IsPickDirectlyIfFeasible));
	}

	@Override
	public boolean isPickDirectlyIfFeasible() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsPickDirectlyIfFeasible);
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
	public void setMD_Candidate_Prod_Detail_ID (int MD_Candidate_Prod_Detail_ID)
	{
		if (MD_Candidate_Prod_Detail_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_MD_Candidate_Prod_Detail_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_MD_Candidate_Prod_Detail_ID, Integer.valueOf(MD_Candidate_Prod_Detail_ID));
	}

	@Override
	public int getMD_Candidate_Prod_Detail_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_MD_Candidate_Prod_Detail_ID);
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

	@Override
	public void setPP_Order_BOMLine_ID (int PP_Order_BOMLine_ID)
	{
		if (PP_Order_BOMLine_ID < 1) 
			set_Value (COLUMNNAME_PP_Order_BOMLine_ID, null);
		else 
			set_Value (COLUMNNAME_PP_Order_BOMLine_ID, Integer.valueOf(PP_Order_BOMLine_ID));
	}

	@Override
	public int getPP_Order_BOMLine_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_PP_Order_BOMLine_ID);
	}

	/** 
	 * PP_Order_DocStatus AD_Reference_ID=131
	 * Reference name: _Document Status
	 */
	public static final int PP_ORDER_DOCSTATUS_AD_Reference_ID=131;
	/** Drafted = DR */
	public static final String PP_ORDER_DOCSTATUS_Drafted = "DR";
	/** Completed = CO */
	public static final String PP_ORDER_DOCSTATUS_Completed = "CO";
	/** Approved = AP */
	public static final String PP_ORDER_DOCSTATUS_Approved = "AP";
	/** NotApproved = NA */
	public static final String PP_ORDER_DOCSTATUS_NotApproved = "NA";
	/** Voided = VO */
	public static final String PP_ORDER_DOCSTATUS_Voided = "VO";
	/** Invalid = IN */
	public static final String PP_ORDER_DOCSTATUS_Invalid = "IN";
	/** Reversed = RE */
	public static final String PP_ORDER_DOCSTATUS_Reversed = "RE";
	/** Closed = CL */
	public static final String PP_ORDER_DOCSTATUS_Closed = "CL";
	/** Unknown = ?? */
	public static final String PP_ORDER_DOCSTATUS_Unknown = "??";
	/** InProgress = IP */
	public static final String PP_ORDER_DOCSTATUS_InProgress = "IP";
	/** WaitingPayment = WP */
	public static final String PP_ORDER_DOCSTATUS_WaitingPayment = "WP";
	/** WaitingConfirmation = WC */
	public static final String PP_ORDER_DOCSTATUS_WaitingConfirmation = "WC";
	@Override
	public void setPP_Order_DocStatus (java.lang.String PP_Order_DocStatus)
	{

		set_Value (COLUMNNAME_PP_Order_DocStatus, PP_Order_DocStatus);
	}

	@Override
	public java.lang.String getPP_Order_DocStatus() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_PP_Order_DocStatus);
	}

	@Override
	public org.eevolution.model.I_PP_Order getPP_Order()
	{
		return get_ValueAsPO(COLUMNNAME_PP_Order_ID, org.eevolution.model.I_PP_Order.class);
	}

	@Override
	public void setPP_Order(org.eevolution.model.I_PP_Order PP_Order)
	{
		set_ValueFromPO(COLUMNNAME_PP_Order_ID, org.eevolution.model.I_PP_Order.class, PP_Order);
	}

	@Override
	public void setPP_Order_ID (int PP_Order_ID)
	{
		if (PP_Order_ID < 1) 
			set_Value (COLUMNNAME_PP_Order_ID, null);
		else 
			set_Value (COLUMNNAME_PP_Order_ID, Integer.valueOf(PP_Order_ID));
	}

	@Override
	public int getPP_Order_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_PP_Order_ID);
	}

	@Override
	public org.compiere.model.I_S_Resource getPP_Plant()
	{
		return get_ValueAsPO(COLUMNNAME_PP_Plant_ID, org.compiere.model.I_S_Resource.class);
	}

	@Override
	public void setPP_Plant(org.compiere.model.I_S_Resource PP_Plant)
	{
		set_ValueFromPO(COLUMNNAME_PP_Plant_ID, org.compiere.model.I_S_Resource.class, PP_Plant);
	}

	@Override
	public void setPP_Plant_ID (int PP_Plant_ID)
	{
		if (PP_Plant_ID < 1) 
			set_Value (COLUMNNAME_PP_Plant_ID, null);
		else 
			set_Value (COLUMNNAME_PP_Plant_ID, Integer.valueOf(PP_Plant_ID));
	}

	@Override
	public int getPP_Plant_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_PP_Plant_ID);
	}

	@Override
	public org.eevolution.model.I_PP_Product_BOMLine getPP_Product_BOMLine()
	{
		return get_ValueAsPO(COLUMNNAME_PP_Product_BOMLine_ID, org.eevolution.model.I_PP_Product_BOMLine.class);
	}

	@Override
	public void setPP_Product_BOMLine(org.eevolution.model.I_PP_Product_BOMLine PP_Product_BOMLine)
	{
		set_ValueFromPO(COLUMNNAME_PP_Product_BOMLine_ID, org.eevolution.model.I_PP_Product_BOMLine.class, PP_Product_BOMLine);
	}

	@Override
	public void setPP_Product_BOMLine_ID (int PP_Product_BOMLine_ID)
	{
		if (PP_Product_BOMLine_ID < 1) 
			set_Value (COLUMNNAME_PP_Product_BOMLine_ID, null);
		else 
			set_Value (COLUMNNAME_PP_Product_BOMLine_ID, Integer.valueOf(PP_Product_BOMLine_ID));
	}

	@Override
	public int getPP_Product_BOMLine_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_PP_Product_BOMLine_ID);
	}

	@Override
	public org.eevolution.model.I_PP_Product_Planning getPP_Product_Planning()
	{
		return get_ValueAsPO(COLUMNNAME_PP_Product_Planning_ID, org.eevolution.model.I_PP_Product_Planning.class);
	}

	@Override
	public void setPP_Product_Planning(org.eevolution.model.I_PP_Product_Planning PP_Product_Planning)
	{
		set_ValueFromPO(COLUMNNAME_PP_Product_Planning_ID, org.eevolution.model.I_PP_Product_Planning.class, PP_Product_Planning);
	}

	@Override
	public void setPP_Product_Planning_ID (int PP_Product_Planning_ID)
	{
		if (PP_Product_Planning_ID < 1) 
			set_Value (COLUMNNAME_PP_Product_Planning_ID, null);
		else 
			set_Value (COLUMNNAME_PP_Product_Planning_ID, Integer.valueOf(PP_Product_Planning_ID));
	}

	@Override
	public int getPP_Product_Planning_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_PP_Product_Planning_ID);
	}
}