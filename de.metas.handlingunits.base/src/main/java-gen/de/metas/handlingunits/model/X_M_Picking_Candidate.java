/** Generated Model - DO NOT CHANGE */
package de.metas.handlingunits.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for M_Picking_Candidate
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_M_Picking_Candidate extends org.compiere.model.PO implements I_M_Picking_Candidate, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -1299076063L;

    /** Standard Constructor */
    public X_M_Picking_Candidate (Properties ctx, int M_Picking_Candidate_ID, String trxName)
    {
      super (ctx, M_Picking_Candidate_ID, trxName);
      /** if (M_Picking_Candidate_ID == 0)
        {
			setApprovalStatus (null); // ?
			setC_UOM_ID (0);
			setM_Picking_Candidate_ID (0);
			setM_ShipmentSchedule_ID (0);
			setPickStatus (null); // ?
			setQtyPicked (BigDecimal.ZERO); // 0
			setStatus (null); // IP
        } */
    }

    /** Load Constructor */
    public X_M_Picking_Candidate (Properties ctx, ResultSet rs, String trxName)
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

	/** 
	 * ApprovalStatus AD_Reference_ID=540920
	 * Reference name: M_Picking_Candidate_ApprovalStatus
	 */
	public static final int APPROVALSTATUS_AD_Reference_ID=540920;
	/** ToBeApproved = ? */
	public static final String APPROVALSTATUS_ToBeApproved = "?";
	/** Approved = A */
	public static final String APPROVALSTATUS_Approved = "A";
	/** Rejected = R */
	public static final String APPROVALSTATUS_Rejected = "R";
	/** Set Status.
		@param ApprovalStatus Status	  */
	@Override
	public void setApprovalStatus (java.lang.String ApprovalStatus)
	{

		set_Value (COLUMNNAME_ApprovalStatus, ApprovalStatus);
	}

	/** Get Status.
		@return Status	  */
	@Override
	public java.lang.String getApprovalStatus () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ApprovalStatus);
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

	/** Set Picking candidate.
		@param M_Picking_Candidate_ID Picking candidate	  */
	@Override
	public void setM_Picking_Candidate_ID (int M_Picking_Candidate_ID)
	{
		if (M_Picking_Candidate_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Picking_Candidate_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Picking_Candidate_ID, Integer.valueOf(M_Picking_Candidate_ID));
	}

	/** Get Picking candidate.
		@return Picking candidate	  */
	@Override
	public int getM_Picking_Candidate_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Picking_Candidate_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Picking Slot.
		@param M_PickingSlot_ID Picking Slot	  */
	@Override
	public void setM_PickingSlot_ID (int M_PickingSlot_ID)
	{
		if (M_PickingSlot_ID < 1) 
			set_Value (COLUMNNAME_M_PickingSlot_ID, null);
		else 
			set_Value (COLUMNNAME_M_PickingSlot_ID, Integer.valueOf(M_PickingSlot_ID));
	}

	/** Get Picking Slot.
		@return Picking Slot	  */
	@Override
	public int getM_PickingSlot_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_PickingSlot_ID);
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
			set_ValueNoCheck (COLUMNNAME_M_ShipmentSchedule_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_ShipmentSchedule_ID, Integer.valueOf(M_ShipmentSchedule_ID));
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

	@Override
	public de.metas.handlingunits.model.I_M_HU_PI getPackTo_HU_PI()
	{
		return get_ValueAsPO(COLUMNNAME_PackTo_HU_PI_ID, de.metas.handlingunits.model.I_M_HU_PI.class);
	}

	@Override
	public void setPackTo_HU_PI(de.metas.handlingunits.model.I_M_HU_PI PackTo_HU_PI)
	{
		set_ValueFromPO(COLUMNNAME_PackTo_HU_PI_ID, de.metas.handlingunits.model.I_M_HU_PI.class, PackTo_HU_PI);
	}

	/** Set Pack To.
		@param PackTo_HU_PI_ID Pack To	  */
	@Override
	public void setPackTo_HU_PI_ID (int PackTo_HU_PI_ID)
	{
		if (PackTo_HU_PI_ID < 1) 
			set_Value (COLUMNNAME_PackTo_HU_PI_ID, null);
		else 
			set_Value (COLUMNNAME_PackTo_HU_PI_ID, Integer.valueOf(PackTo_HU_PI_ID));
	}

	/** Get Pack To.
		@return Pack To	  */
	@Override
	public int getPackTo_HU_PI_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PackTo_HU_PI_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.handlingunits.model.I_M_HU getPickFrom_HU()
	{
		return get_ValueAsPO(COLUMNNAME_PickFrom_HU_ID, de.metas.handlingunits.model.I_M_HU.class);
	}

	@Override
	public void setPickFrom_HU(de.metas.handlingunits.model.I_M_HU PickFrom_HU)
	{
		set_ValueFromPO(COLUMNNAME_PickFrom_HU_ID, de.metas.handlingunits.model.I_M_HU.class, PickFrom_HU);
	}

	/** Set Pick From HU.
		@param PickFrom_HU_ID Pick From HU	  */
	@Override
	public void setPickFrom_HU_ID (int PickFrom_HU_ID)
	{
		if (PickFrom_HU_ID < 1) 
			set_Value (COLUMNNAME_PickFrom_HU_ID, null);
		else 
			set_Value (COLUMNNAME_PickFrom_HU_ID, Integer.valueOf(PickFrom_HU_ID));
	}

	/** Get Pick From HU.
		@return Pick From HU	  */
	@Override
	public int getPickFrom_HU_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PickFrom_HU_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.eevolution.model.I_PP_Order getPickFrom_Order()
	{
		return get_ValueAsPO(COLUMNNAME_PickFrom_Order_ID, org.eevolution.model.I_PP_Order.class);
	}

	@Override
	public void setPickFrom_Order(org.eevolution.model.I_PP_Order PickFrom_Order)
	{
		set_ValueFromPO(COLUMNNAME_PickFrom_Order_ID, org.eevolution.model.I_PP_Order.class, PickFrom_Order);
	}

	/** Set Pick From Order.
		@param PickFrom_Order_ID Pick From Order	  */
	@Override
	public void setPickFrom_Order_ID (int PickFrom_Order_ID)
	{
		if (PickFrom_Order_ID < 1) 
			set_Value (COLUMNNAME_PickFrom_Order_ID, null);
		else 
			set_Value (COLUMNNAME_PickFrom_Order_ID, Integer.valueOf(PickFrom_Order_ID));
	}

	/** Get Pick From Order.
		@return Pick From Order	  */
	@Override
	public int getPickFrom_Order_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PickFrom_Order_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** 
	 * PickStatus AD_Reference_ID=540919
	 * Reference name: M_Picking_Candidate_PickStatus
	 */
	public static final int PICKSTATUS_AD_Reference_ID=540919;
	/** ToBePicked = ? */
	public static final String PICKSTATUS_ToBePicked = "?";
	/** Picked = P */
	public static final String PICKSTATUS_Picked = "P";
	/** WillNotBePicked = N */
	public static final String PICKSTATUS_WillNotBePicked = "N";
	/** Packed = A */
	public static final String PICKSTATUS_Packed = "A";
	/** Set Komm. Status.
		@param PickStatus Komm. Status	  */
	@Override
	public void setPickStatus (java.lang.String PickStatus)
	{

		set_Value (COLUMNNAME_PickStatus, PickStatus);
	}

	/** Get Komm. Status.
		@return Komm. Status	  */
	@Override
	public java.lang.String getPickStatus () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_PickStatus);
	}

	/** Set Menge (Lagereinheit).
		@param QtyPicked Menge (Lagereinheit)	  */
	@Override
	public void setQtyPicked (java.math.BigDecimal QtyPicked)
	{
		set_Value (COLUMNNAME_QtyPicked, QtyPicked);
	}

	/** Get Menge (Lagereinheit).
		@return Menge (Lagereinheit)	  */
	@Override
	public java.math.BigDecimal getQtyPicked () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyPicked);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Qty Review.
		@param QtyReview Qty Review	  */
	@Override
	public void setQtyReview (java.math.BigDecimal QtyReview)
	{
		set_Value (COLUMNNAME_QtyReview, QtyReview);
	}

	/** Get Qty Review.
		@return Qty Review	  */
	@Override
	public java.math.BigDecimal getQtyReview () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyReview);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** 
	 * Status AD_Reference_ID=540734
	 * Reference name: M_Picking_Candidate_Status
	 */
	public static final int STATUS_AD_Reference_ID=540734;
	/** Closed = CL */
	public static final String STATUS_Closed = "CL";
	/** InProgress = IP */
	public static final String STATUS_InProgress = "IP";
	/** Processed = PR */
	public static final String STATUS_Processed = "PR";
	/** Voided = VO */
	public static final String STATUS_Voided = "VO";
	/** Set Status.
		@param Status Status	  */
	@Override
	public void setStatus (java.lang.String Status)
	{

		set_Value (COLUMNNAME_Status, Status);
	}

	/** Get Status.
		@return Status	  */
	@Override
	public java.lang.String getStatus () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Status);
	}
}