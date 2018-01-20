/** Generated Model - DO NOT CHANGE */
package de.metas.material.dispo.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for MD_Candidate_Dist_Detail
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_MD_Candidate_Dist_Detail extends org.compiere.model.PO implements I_MD_Candidate_Dist_Detail, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1460095005L;

    /** Standard Constructor */
    public X_MD_Candidate_Dist_Detail (Properties ctx, int MD_Candidate_Dist_Detail_ID, String trxName)
    {
      super (ctx, MD_Candidate_Dist_Detail_ID, trxName);
      /** if (MD_Candidate_Dist_Detail_ID == 0)
        {
			setIsAdvised (false); // N
			setIsPickDirectlyIfFeasible (false); // N
			setMD_Candidate_Dist_Detail_ID (0);
			setMD_Candidate_ID (0);
        } */
    }

    /** Load Constructor */
    public X_MD_Candidate_Dist_Detail (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Istmenge.
		@param ActualQty Istmenge	  */
	@Override
	public void setActualQty (java.math.BigDecimal ActualQty)
	{
		set_Value (COLUMNNAME_ActualQty, ActualQty);
	}

	/** Get Istmenge.
		@return Istmenge	  */
	@Override
	public java.math.BigDecimal getActualQty () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ActualQty);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	@Override
	public org.eevolution.model.I_DD_NetworkDistributionLine getDD_NetworkDistributionLine() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_DD_NetworkDistributionLine_ID, org.eevolution.model.I_DD_NetworkDistributionLine.class);
	}

	@Override
	public void setDD_NetworkDistributionLine(org.eevolution.model.I_DD_NetworkDistributionLine DD_NetworkDistributionLine)
	{
		set_ValueFromPO(COLUMNNAME_DD_NetworkDistributionLine_ID, org.eevolution.model.I_DD_NetworkDistributionLine.class, DD_NetworkDistributionLine);
	}

	/** Set Network Distribution Line.
		@param DD_NetworkDistributionLine_ID Network Distribution Line	  */
	@Override
	public void setDD_NetworkDistributionLine_ID (int DD_NetworkDistributionLine_ID)
	{
		if (DD_NetworkDistributionLine_ID < 1) 
			set_Value (COLUMNNAME_DD_NetworkDistributionLine_ID, null);
		else 
			set_Value (COLUMNNAME_DD_NetworkDistributionLine_ID, Integer.valueOf(DD_NetworkDistributionLine_ID));
	}

	/** Get Network Distribution Line.
		@return Network Distribution Line	  */
	@Override
	public int getDD_NetworkDistributionLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DD_NetworkDistributionLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** 
	 * DD_Order_DocStatus AD_Reference_ID=131
	 * Reference name: _Document Status
	 */
	public static final int DD_ORDER_DOCSTATUS_AD_Reference_ID=131;
	/** Drafted = DR */
	public static final String DD_ORDER_DOCSTATUS_Drafted = "DR";
	/** Completed = CO */
	public static final String DD_ORDER_DOCSTATUS_Completed = "CO";
	/** Approved = AP */
	public static final String DD_ORDER_DOCSTATUS_Approved = "AP";
	/** NotApproved = NA */
	public static final String DD_ORDER_DOCSTATUS_NotApproved = "NA";
	/** Voided = VO */
	public static final String DD_ORDER_DOCSTATUS_Voided = "VO";
	/** Invalid = IN */
	public static final String DD_ORDER_DOCSTATUS_Invalid = "IN";
	/** Reversed = RE */
	public static final String DD_ORDER_DOCSTATUS_Reversed = "RE";
	/** Closed = CL */
	public static final String DD_ORDER_DOCSTATUS_Closed = "CL";
	/** Unknown = ?? */
	public static final String DD_ORDER_DOCSTATUS_Unknown = "??";
	/** InProgress = IP */
	public static final String DD_ORDER_DOCSTATUS_InProgress = "IP";
	/** WaitingPayment = WP */
	public static final String DD_ORDER_DOCSTATUS_WaitingPayment = "WP";
	/** WaitingConfirmation = WC */
	public static final String DD_ORDER_DOCSTATUS_WaitingConfirmation = "WC";
	/** Set Belegstatus.
		@param DD_Order_DocStatus Belegstatus	  */
	@Override
	public void setDD_Order_DocStatus (java.lang.String DD_Order_DocStatus)
	{

		set_Value (COLUMNNAME_DD_Order_DocStatus, DD_Order_DocStatus);
	}

	/** Get Belegstatus.
		@return Belegstatus	  */
	@Override
	public java.lang.String getDD_Order_DocStatus () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DD_Order_DocStatus);
	}

	@Override
	public org.eevolution.model.I_DD_Order getDD_Order() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_DD_Order_ID, org.eevolution.model.I_DD_Order.class);
	}

	@Override
	public void setDD_Order(org.eevolution.model.I_DD_Order DD_Order)
	{
		set_ValueFromPO(COLUMNNAME_DD_Order_ID, org.eevolution.model.I_DD_Order.class, DD_Order);
	}

	/** Set Distribution Order.
		@param DD_Order_ID Distribution Order	  */
	@Override
	public void setDD_Order_ID (int DD_Order_ID)
	{
		if (DD_Order_ID < 1) 
			set_Value (COLUMNNAME_DD_Order_ID, null);
		else 
			set_Value (COLUMNNAME_DD_Order_ID, Integer.valueOf(DD_Order_ID));
	}

	/** Get Distribution Order.
		@return Distribution Order	  */
	@Override
	public int getDD_Order_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DD_Order_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.eevolution.model.I_DD_OrderLine getDD_OrderLine() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_DD_OrderLine_ID, org.eevolution.model.I_DD_OrderLine.class);
	}

	@Override
	public void setDD_OrderLine(org.eevolution.model.I_DD_OrderLine DD_OrderLine)
	{
		set_ValueFromPO(COLUMNNAME_DD_OrderLine_ID, org.eevolution.model.I_DD_OrderLine.class, DD_OrderLine);
	}

	/** Set Distribution Order Line.
		@param DD_OrderLine_ID Distribution Order Line	  */
	@Override
	public void setDD_OrderLine_ID (int DD_OrderLine_ID)
	{
		if (DD_OrderLine_ID < 1) 
			set_Value (COLUMNNAME_DD_OrderLine_ID, null);
		else 
			set_Value (COLUMNNAME_DD_OrderLine_ID, Integer.valueOf(DD_OrderLine_ID));
	}

	/** Get Distribution Order Line.
		@return Distribution Order Line	  */
	@Override
	public int getDD_OrderLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DD_OrderLine_ID);
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

	/** Set Sofort Kommissionieren wenn möglich.
		@param IsPickDirectlyIfFeasible 
		Falls "Ja" und ein Bestand wird für einen bestimmten Lieferdispo-Eintrag bereit gestellt oder produziert, dann wird dieser sofort zugeordnet und als kommissioniert markiert.
	  */
	@Override
	public void setIsPickDirectlyIfFeasible (boolean IsPickDirectlyIfFeasible)
	{
		set_Value (COLUMNNAME_IsPickDirectlyIfFeasible, Boolean.valueOf(IsPickDirectlyIfFeasible));
	}

	/** Get Sofort Kommissionieren wenn möglich.
		@return Falls "Ja" und ein Bestand wird für einen bestimmten Lieferdispo-Eintrag bereit gestellt oder produziert, dann wird dieser sofort zugeordnet und als kommissioniert markiert.
	  */
	@Override
	public boolean isPickDirectlyIfFeasible () 
	{
		Object oo = get_Value(COLUMNNAME_IsPickDirectlyIfFeasible);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Dispo-Bereitstellungsdetail.
		@param MD_Candidate_Dist_Detail_ID Dispo-Bereitstellungsdetail	  */
	@Override
	public void setMD_Candidate_Dist_Detail_ID (int MD_Candidate_Dist_Detail_ID)
	{
		if (MD_Candidate_Dist_Detail_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_MD_Candidate_Dist_Detail_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_MD_Candidate_Dist_Detail_ID, Integer.valueOf(MD_Candidate_Dist_Detail_ID));
	}

	/** Get Dispo-Bereitstellungsdetail.
		@return Dispo-Bereitstellungsdetail	  */
	@Override
	public int getMD_Candidate_Dist_Detail_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_MD_Candidate_Dist_Detail_ID);
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
	public org.compiere.model.I_M_Shipper getM_Shipper() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_Shipper_ID, org.compiere.model.I_M_Shipper.class);
	}

	@Override
	public void setM_Shipper(org.compiere.model.I_M_Shipper M_Shipper)
	{
		set_ValueFromPO(COLUMNNAME_M_Shipper_ID, org.compiere.model.I_M_Shipper.class, M_Shipper);
	}

	/** Set Lieferweg.
		@param M_Shipper_ID 
		Methode oder Art der Warenlieferung
	  */
	@Override
	public void setM_Shipper_ID (int M_Shipper_ID)
	{
		if (M_Shipper_ID < 1) 
			set_Value (COLUMNNAME_M_Shipper_ID, null);
		else 
			set_Value (COLUMNNAME_M_Shipper_ID, Integer.valueOf(M_Shipper_ID));
	}

	/** Get Lieferweg.
		@return Methode oder Art der Warenlieferung
	  */
	@Override
	public int getM_Shipper_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Shipper_ID);
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
	public org.compiere.model.I_S_Resource getPP_Plant() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_PP_Plant_ID, org.compiere.model.I_S_Resource.class);
	}

	@Override
	public void setPP_Plant(org.compiere.model.I_S_Resource PP_Plant)
	{
		set_ValueFromPO(COLUMNNAME_PP_Plant_ID, org.compiere.model.I_S_Resource.class, PP_Plant);
	}

	/** Set Produktionsstätte.
		@param PP_Plant_ID Produktionsstätte	  */
	@Override
	public void setPP_Plant_ID (int PP_Plant_ID)
	{
		if (PP_Plant_ID < 1) 
			set_Value (COLUMNNAME_PP_Plant_ID, null);
		else 
			set_Value (COLUMNNAME_PP_Plant_ID, Integer.valueOf(PP_Plant_ID));
	}

	/** Get Produktionsstätte.
		@return Produktionsstätte	  */
	@Override
	public int getPP_Plant_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PP_Plant_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.eevolution.model.I_PP_Product_Planning getPP_Product_Planning() throws RuntimeException
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
}