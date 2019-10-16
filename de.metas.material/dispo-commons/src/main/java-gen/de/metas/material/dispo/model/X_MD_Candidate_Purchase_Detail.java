/** Generated Model - DO NOT CHANGE */
package de.metas.material.dispo.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for MD_Candidate_Purchase_Detail
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_MD_Candidate_Purchase_Detail extends org.compiere.model.PO implements I_MD_Candidate_Purchase_Detail, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 681925329L;

    /** Standard Constructor */
    public X_MD_Candidate_Purchase_Detail (Properties ctx, int MD_Candidate_Purchase_Detail_ID, String trxName)
    {
      super (ctx, MD_Candidate_Purchase_Detail_ID, trxName);
      /** if (MD_Candidate_Purchase_Detail_ID == 0)
        {
			setIsAdvised (false); // N
			setMD_Candidate_ID (0);
			setMD_Candidate_Purchase_Detail_ID (0);
        } */
    }

    /** Load Constructor */
    public X_MD_Candidate_Purchase_Detail (Properties ctx, ResultSet rs, String trxName)
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

	/** Set C_BPartner_Vendor_ID.
		@param C_BPartner_Vendor_ID C_BPartner_Vendor_ID	  */
	@Override
	public void setC_BPartner_Vendor_ID (int C_BPartner_Vendor_ID)
	{
		if (C_BPartner_Vendor_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_Vendor_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_Vendor_ID, Integer.valueOf(C_BPartner_Vendor_ID));
	}

	/** Get C_BPartner_Vendor_ID.
		@return C_BPartner_Vendor_ID	  */
	@Override
	public int getC_BPartner_Vendor_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_Vendor_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Bestellposition.
		@param C_OrderLinePO_ID Bestellposition	  */
	@Override
	public void setC_OrderLinePO_ID (int C_OrderLinePO_ID)
	{
		if (C_OrderLinePO_ID < 1) 
			set_Value (COLUMNNAME_C_OrderLinePO_ID, null);
		else 
			set_Value (COLUMNNAME_C_OrderLinePO_ID, Integer.valueOf(C_OrderLinePO_ID));
	}

	/** Get Bestellposition.
		@return Bestellposition	  */
	@Override
	public int getC_OrderLinePO_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_OrderLinePO_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Purchase candidate.
		@param C_PurchaseCandidate_ID Purchase candidate	  */
	@Override
	public void setC_PurchaseCandidate_ID (int C_PurchaseCandidate_ID)
	{
		if (C_PurchaseCandidate_ID < 1) 
			set_Value (COLUMNNAME_C_PurchaseCandidate_ID, null);
		else 
			set_Value (COLUMNNAME_C_PurchaseCandidate_ID, Integer.valueOf(C_PurchaseCandidate_ID));
	}

	/** Get Purchase candidate.
		@return Purchase candidate	  */
	@Override
	public int getC_PurchaseCandidate_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_PurchaseCandidate_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Vom System vorgeschlagen.
		@param IsAdvised 
		Ja bedeutet, dass es zumindest ursprünglich kein entsprechendes Dokument (z.B. Produktionsauftrag) gab, sondern dass das System einen Beleg vorgeschlagen hatte.
	  */
	@Override
	public void setIsAdvised (boolean IsAdvised)
	{
		set_Value (COLUMNNAME_IsAdvised, Boolean.valueOf(IsAdvised));
	}

	/** Get Vom System vorgeschlagen.
		@return Ja bedeutet, dass es zumindest ursprünglich kein entsprechendes Dokument (z.B. Produktionsauftrag) gab, sondern dass das System einen Beleg vorgeschlagen hatte.
	  */
	@Override
	public boolean isAdvised () 
	{
		Object oo = get_Value(COLUMNNAME_IsAdvised);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Wareneingangsdisposition.
		@param M_ReceiptSchedule_ID Wareneingangsdisposition	  */
	@Override
	public void setM_ReceiptSchedule_ID (int M_ReceiptSchedule_ID)
	{
		if (M_ReceiptSchedule_ID < 1) 
			set_Value (COLUMNNAME_M_ReceiptSchedule_ID, null);
		else 
			set_Value (COLUMNNAME_M_ReceiptSchedule_ID, Integer.valueOf(M_ReceiptSchedule_ID));
	}

	/** Get Wareneingangsdisposition.
		@return Wareneingangsdisposition	  */
	@Override
	public int getM_ReceiptSchedule_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_ReceiptSchedule_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Dispo-Einkaufsdetail.
		@param MD_Candidate_Purchase_Detail_ID Dispo-Einkaufsdetail	  */
	@Override
	public void setMD_Candidate_Purchase_Detail_ID (int MD_Candidate_Purchase_Detail_ID)
	{
		if (MD_Candidate_Purchase_Detail_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_MD_Candidate_Purchase_Detail_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_MD_Candidate_Purchase_Detail_ID, Integer.valueOf(MD_Candidate_Purchase_Detail_ID));
	}

	/** Get Dispo-Einkaufsdetail.
		@return Dispo-Einkaufsdetail	  */
	@Override
	public int getMD_Candidate_Purchase_Detail_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_MD_Candidate_Purchase_Detail_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Geplante Menge.
		@param PlannedQty Geplante Menge	  */
	@Override
	public void setPlannedQty (java.math.BigDecimal PlannedQty)
	{
		set_Value (COLUMNNAME_PlannedQty, PlannedQty);
	}

	/** Get Geplante Menge.
		@return Geplante Menge	  */
	@Override
	public java.math.BigDecimal getPlannedQty () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PlannedQty);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
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

	/** Set Product Planning.
		@param PP_Product_Planning_ID Product Planning	  */
	@Override
	public void setPP_Product_Planning_ID (int PP_Product_Planning_ID)
	{
		if (PP_Product_Planning_ID < 1) 
			set_Value (COLUMNNAME_PP_Product_Planning_ID, null);
		else 
			set_Value (COLUMNNAME_PP_Product_Planning_ID, Integer.valueOf(PP_Product_Planning_ID));
	}

	/** Get Product Planning.
		@return Product Planning	  */
	@Override
	public int getPP_Product_Planning_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PP_Product_Planning_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Bestellt/ Beauftragt.
		@param QtyOrdered 
		Bestellt/ Beauftragt
	  */
	@Override
	public void setQtyOrdered (java.math.BigDecimal QtyOrdered)
	{
		set_Value (COLUMNNAME_QtyOrdered, QtyOrdered);
	}

	/** Get Bestellt/ Beauftragt.
		@return Bestellt/ Beauftragt
	  */
	@Override
	public java.math.BigDecimal getQtyOrdered () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyOrdered);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}
}