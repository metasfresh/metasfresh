// Generated Model - DO NOT CHANGE
package de.metas.material.dispo.model;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for MD_Candidate_Prod_Detail
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_MD_Candidate_Prod_Detail extends org.compiere.model.PO implements I_MD_Candidate_Prod_Detail, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -150729457L;

    /** Standard Constructor */
    public X_MD_Candidate_Prod_Detail (final Properties ctx, final int MD_Candidate_Prod_Detail_ID, @Nullable final String trxName)
    {
      super (ctx, MD_Candidate_Prod_Detail_ID, trxName);
    }

    /** Load Constructor */
    public X_MD_Candidate_Prod_Detail (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
    {
      super (ctx, rs, trxName);
    }


	/** Load Meta Data */
	@Override
	protected org.compiere.model.POInfo initPO(final Properties ctx)
	{
		return org.compiere.model.POInfo.getPOInfo(Table_Name);
	}

	@Override
	public void setActualQty (final @Nullable BigDecimal ActualQty)
	{
		set_Value (COLUMNNAME_ActualQty, ActualQty);
	}

	@Override
	public BigDecimal getActualQty() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_ActualQty);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setDescription (final @Nullable String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	@Override
	public String getDescription()
	{
		return get_ValueAsString(COLUMNNAME_Description);
	}

	@Override
	public void setIsAdvised (final boolean IsAdvised)
	{
		set_Value (COLUMNNAME_IsAdvised, IsAdvised);
	}

	@Override
	public boolean isAdvised() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsAdvised);
	}

	@Override
	public void setIsPickDirectlyIfFeasible (final boolean IsPickDirectlyIfFeasible)
	{
		set_Value (COLUMNNAME_IsPickDirectlyIfFeasible, IsPickDirectlyIfFeasible);
	}

	@Override
	public boolean isPickDirectlyIfFeasible() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsPickDirectlyIfFeasible);
	}

	@Override
	public I_MD_Candidate getMD_Candidate()
	{
		return get_ValueAsPO(COLUMNNAME_MD_Candidate_ID, I_MD_Candidate.class);
	}

	@Override
	public void setMD_Candidate(final I_MD_Candidate MD_Candidate)
	{
		set_ValueFromPO(COLUMNNAME_MD_Candidate_ID, I_MD_Candidate.class, MD_Candidate);
	}

	@Override
	public void setMD_Candidate_ID (final int MD_Candidate_ID)
	{
		if (MD_Candidate_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_MD_Candidate_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_MD_Candidate_ID, MD_Candidate_ID);
	}

	@Override
	public int getMD_Candidate_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_MD_Candidate_ID);
	}

	@Override
	public void setMD_Candidate_Prod_Detail_ID (final int MD_Candidate_Prod_Detail_ID)
	{
		if (MD_Candidate_Prod_Detail_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_MD_Candidate_Prod_Detail_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_MD_Candidate_Prod_Detail_ID, MD_Candidate_Prod_Detail_ID);
	}

	@Override
	public int getMD_Candidate_Prod_Detail_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_MD_Candidate_Prod_Detail_ID);
	}

	@Override
	public void setPlannedQty (final @Nullable BigDecimal PlannedQty)
	{
		set_Value (COLUMNNAME_PlannedQty, PlannedQty);
	}

	@Override
	public BigDecimal getPlannedQty() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_PlannedQty);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public org.eevolution.model.I_PP_Order_BOMLine getPP_Order_BOMLine()
	{
		return get_ValueAsPO(COLUMNNAME_PP_Order_BOMLine_ID, org.eevolution.model.I_PP_Order_BOMLine.class);
	}

	@Override
	public void setPP_Order_BOMLine(final org.eevolution.model.I_PP_Order_BOMLine PP_Order_BOMLine)
	{
		set_ValueFromPO(COLUMNNAME_PP_Order_BOMLine_ID, org.eevolution.model.I_PP_Order_BOMLine.class, PP_Order_BOMLine);
	}

	@Override
	public void setPP_Order_BOMLine_ID (final int PP_Order_BOMLine_ID)
	{
		if (PP_Order_BOMLine_ID < 1) 
			set_Value (COLUMNNAME_PP_Order_BOMLine_ID, null);
		else 
			set_Value (COLUMNNAME_PP_Order_BOMLine_ID, PP_Order_BOMLine_ID);
	}

	@Override
	public int getPP_Order_BOMLine_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_PP_Order_BOMLine_ID);
	}

	@Override
	public org.eevolution.model.I_PP_Order_Candidate getPP_Order_Candidate()
	{
		return get_ValueAsPO(COLUMNNAME_PP_Order_Candidate_ID, org.eevolution.model.I_PP_Order_Candidate.class);
	}

	@Override
	public void setPP_Order_Candidate(final org.eevolution.model.I_PP_Order_Candidate PP_Order_Candidate)
	{
		set_ValueFromPO(COLUMNNAME_PP_Order_Candidate_ID, org.eevolution.model.I_PP_Order_Candidate.class, PP_Order_Candidate);
	}

	@Override
	public void setPP_Order_Candidate_ID (final int PP_Order_Candidate_ID)
	{
		if (PP_Order_Candidate_ID < 1) 
			set_Value (COLUMNNAME_PP_Order_Candidate_ID, null);
		else 
			set_Value (COLUMNNAME_PP_Order_Candidate_ID, PP_Order_Candidate_ID);
	}

	@Override
	public int getPP_Order_Candidate_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_PP_Order_Candidate_ID);
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
	public void setPP_Order_DocStatus (final @Nullable String PP_Order_DocStatus)
	{
		set_Value (COLUMNNAME_PP_Order_DocStatus, PP_Order_DocStatus);
	}

	@Override
	public String getPP_Order_DocStatus()
	{
		return get_ValueAsString(COLUMNNAME_PP_Order_DocStatus);
	}

	@Override
	public org.eevolution.model.I_PP_Order getPP_Order()
	{
		return get_ValueAsPO(COLUMNNAME_PP_Order_ID, org.eevolution.model.I_PP_Order.class);
	}

	@Override
	public void setPP_Order(final org.eevolution.model.I_PP_Order PP_Order)
	{
		set_ValueFromPO(COLUMNNAME_PP_Order_ID, org.eevolution.model.I_PP_Order.class, PP_Order);
	}

	@Override
	public void setPP_Order_ID (final int PP_Order_ID)
	{
		if (PP_Order_ID < 1) 
			set_Value (COLUMNNAME_PP_Order_ID, null);
		else 
			set_Value (COLUMNNAME_PP_Order_ID, PP_Order_ID);
	}

	@Override
	public int getPP_Order_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_PP_Order_ID);
	}

	@Override
	public org.eevolution.model.I_PP_OrderLine_Candidate getPP_OrderLine_Candidate()
	{
		return get_ValueAsPO(COLUMNNAME_PP_OrderLine_Candidate_ID, org.eevolution.model.I_PP_OrderLine_Candidate.class);
	}

	@Override
	public void setPP_OrderLine_Candidate(final org.eevolution.model.I_PP_OrderLine_Candidate PP_OrderLine_Candidate)
	{
		set_ValueFromPO(COLUMNNAME_PP_OrderLine_Candidate_ID, org.eevolution.model.I_PP_OrderLine_Candidate.class, PP_OrderLine_Candidate);
	}

	@Override
	public void setPP_OrderLine_Candidate_ID (final int PP_OrderLine_Candidate_ID)
	{
		if (PP_OrderLine_Candidate_ID < 1) 
			set_Value (COLUMNNAME_PP_OrderLine_Candidate_ID, null);
		else 
			set_Value (COLUMNNAME_PP_OrderLine_Candidate_ID, PP_OrderLine_Candidate_ID);
	}

	@Override
	public int getPP_OrderLine_Candidate_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_PP_OrderLine_Candidate_ID);
	}

	@Override
	public org.compiere.model.I_S_Resource getPP_Plant()
	{
		return get_ValueAsPO(COLUMNNAME_PP_Plant_ID, org.compiere.model.I_S_Resource.class);
	}

	@Override
	public void setPP_Plant(final org.compiere.model.I_S_Resource PP_Plant)
	{
		set_ValueFromPO(COLUMNNAME_PP_Plant_ID, org.compiere.model.I_S_Resource.class, PP_Plant);
	}

	@Override
	public void setPP_Plant_ID (final int PP_Plant_ID)
	{
		if (PP_Plant_ID < 1) 
			set_Value (COLUMNNAME_PP_Plant_ID, null);
		else 
			set_Value (COLUMNNAME_PP_Plant_ID, PP_Plant_ID);
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
	public void setPP_Product_BOMLine(final org.eevolution.model.I_PP_Product_BOMLine PP_Product_BOMLine)
	{
		set_ValueFromPO(COLUMNNAME_PP_Product_BOMLine_ID, org.eevolution.model.I_PP_Product_BOMLine.class, PP_Product_BOMLine);
	}

	@Override
	public void setPP_Product_BOMLine_ID (final int PP_Product_BOMLine_ID)
	{
		if (PP_Product_BOMLine_ID < 1) 
			set_Value (COLUMNNAME_PP_Product_BOMLine_ID, null);
		else 
			set_Value (COLUMNNAME_PP_Product_BOMLine_ID, PP_Product_BOMLine_ID);
	}

	@Override
	public int getPP_Product_BOMLine_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_PP_Product_BOMLine_ID);
	}

	@Override
	public void setPP_Product_Planning_ID (final int PP_Product_Planning_ID)
	{
		if (PP_Product_Planning_ID < 1) 
			set_Value (COLUMNNAME_PP_Product_Planning_ID, null);
		else 
			set_Value (COLUMNNAME_PP_Product_Planning_ID, PP_Product_Planning_ID);
	}

	@Override
	public int getPP_Product_Planning_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_PP_Product_Planning_ID);
	}
}