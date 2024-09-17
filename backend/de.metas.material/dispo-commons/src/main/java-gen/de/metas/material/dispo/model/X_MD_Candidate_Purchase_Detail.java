/** Generated Model - DO NOT CHANGE */
package de.metas.material.dispo.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for MD_Candidate_Purchase_Detail
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("javadoc")
public class X_MD_Candidate_Purchase_Detail extends org.compiere.model.PO implements I_MD_Candidate_Purchase_Detail, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 1453603562L;

    /** Standard Constructor */
    public X_MD_Candidate_Purchase_Detail (Properties ctx, int MD_Candidate_Purchase_Detail_ID, String trxName)
    {
      super (ctx, MD_Candidate_Purchase_Detail_ID, trxName);
    }

    /** Load Constructor */
    public X_MD_Candidate_Purchase_Detail (Properties ctx, ResultSet rs, String trxName)
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
	public void setC_BPartner_Vendor_ID (int C_BPartner_Vendor_ID)
	{
		if (C_BPartner_Vendor_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_Vendor_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_Vendor_ID, Integer.valueOf(C_BPartner_Vendor_ID));
	}

	@Override
	public int getC_BPartner_Vendor_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_Vendor_ID);
	}

	@Override
	public org.compiere.model.I_C_OrderLine getC_OrderLinePO()
	{
		return get_ValueAsPO(COLUMNNAME_C_OrderLinePO_ID, org.compiere.model.I_C_OrderLine.class);
	}

	@Override
	public void setC_OrderLinePO(org.compiere.model.I_C_OrderLine C_OrderLinePO)
	{
		set_ValueFromPO(COLUMNNAME_C_OrderLinePO_ID, org.compiere.model.I_C_OrderLine.class, C_OrderLinePO);
	}

	@Override
	public void setC_OrderLinePO_ID (int C_OrderLinePO_ID)
	{
		if (C_OrderLinePO_ID < 1) 
			set_Value (COLUMNNAME_C_OrderLinePO_ID, null);
		else 
			set_Value (COLUMNNAME_C_OrderLinePO_ID, Integer.valueOf(C_OrderLinePO_ID));
	}

	@Override
	public int getC_OrderLinePO_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_OrderLinePO_ID);
	}

	@Override
	public void setC_PurchaseCandidate_ID (int C_PurchaseCandidate_ID)
	{
		if (C_PurchaseCandidate_ID < 1) 
			set_Value (COLUMNNAME_C_PurchaseCandidate_ID, null);
		else 
			set_Value (COLUMNNAME_C_PurchaseCandidate_ID, Integer.valueOf(C_PurchaseCandidate_ID));
	}

	@Override
	public int getC_PurchaseCandidate_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_PurchaseCandidate_ID);
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
	public void setMD_Candidate_Purchase_Detail_ID (int MD_Candidate_Purchase_Detail_ID)
	{
		if (MD_Candidate_Purchase_Detail_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_MD_Candidate_Purchase_Detail_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_MD_Candidate_Purchase_Detail_ID, Integer.valueOf(MD_Candidate_Purchase_Detail_ID));
	}

	@Override
	public int getMD_Candidate_Purchase_Detail_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_MD_Candidate_Purchase_Detail_ID);
	}

	@Override
	public void setM_ReceiptSchedule_ID (int M_ReceiptSchedule_ID)
	{
		if (M_ReceiptSchedule_ID < 1) 
			set_Value (COLUMNNAME_M_ReceiptSchedule_ID, null);
		else 
			set_Value (COLUMNNAME_M_ReceiptSchedule_ID, Integer.valueOf(M_ReceiptSchedule_ID));
	}

	@Override
	public int getM_ReceiptSchedule_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_ReceiptSchedule_ID);
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

	@Override
	public void setQtyOrdered (java.math.BigDecimal QtyOrdered)
	{
		set_Value (COLUMNNAME_QtyOrdered, QtyOrdered);
	}

	@Override
	public java.math.BigDecimal getQtyOrdered() 
	{
		BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyOrdered);
		return bd != null ? bd : BigDecimal.ZERO;
	}
}