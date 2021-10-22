// Generated Model - DO NOT CHANGE
package de.metas.handlingunits.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for DD_OrderLine_HU_Candidate
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_DD_OrderLine_HU_Candidate extends org.compiere.model.PO implements I_DD_OrderLine_HU_Candidate, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -644692574L;

    /** Standard Constructor */
    public X_DD_OrderLine_HU_Candidate (final Properties ctx, final int DD_OrderLine_HU_Candidate_ID, @Nullable final String trxName)
    {
      super (ctx, DD_OrderLine_HU_Candidate_ID, trxName);
    }

    /** Load Constructor */
    public X_DD_OrderLine_HU_Candidate (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setC_UOM_ID (final int C_UOM_ID)
	{
		if (C_UOM_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_UOM_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_UOM_ID, C_UOM_ID);
	}

	@Override
	public int getC_UOM_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_UOM_ID);
	}

	@Override
	public org.eevolution.model.I_DD_Order getDD_Order()
	{
		return get_ValueAsPO(COLUMNNAME_DD_Order_ID, org.eevolution.model.I_DD_Order.class);
	}

	@Override
	public void setDD_Order(final org.eevolution.model.I_DD_Order DD_Order)
	{
		set_ValueFromPO(COLUMNNAME_DD_Order_ID, org.eevolution.model.I_DD_Order.class, DD_Order);
	}

	@Override
	public void setDD_Order_ID (final int DD_Order_ID)
	{
		if (DD_Order_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_DD_Order_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_DD_Order_ID, DD_Order_ID);
	}

	@Override
	public int getDD_Order_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_DD_Order_ID);
	}

	@Override
	public void setDD_OrderLine_HU_Candidate_ID (final int DD_OrderLine_HU_Candidate_ID)
	{
		if (DD_OrderLine_HU_Candidate_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_DD_OrderLine_HU_Candidate_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_DD_OrderLine_HU_Candidate_ID, DD_OrderLine_HU_Candidate_ID);
	}

	@Override
	public int getDD_OrderLine_HU_Candidate_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_DD_OrderLine_HU_Candidate_ID);
	}

	@Override
	public org.eevolution.model.I_DD_OrderLine getDD_OrderLine()
	{
		return get_ValueAsPO(COLUMNNAME_DD_OrderLine_ID, org.eevolution.model.I_DD_OrderLine.class);
	}

	@Override
	public void setDD_OrderLine(final org.eevolution.model.I_DD_OrderLine DD_OrderLine)
	{
		set_ValueFromPO(COLUMNNAME_DD_OrderLine_ID, org.eevolution.model.I_DD_OrderLine.class, DD_OrderLine);
	}

	@Override
	public void setDD_OrderLine_ID (final int DD_OrderLine_ID)
	{
		if (DD_OrderLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_DD_OrderLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_DD_OrderLine_ID, DD_OrderLine_ID);
	}

	@Override
	public int getDD_OrderLine_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_DD_OrderLine_ID);
	}

	@Override
	public void setIsPickWholeHU (final boolean IsPickWholeHU)
	{
		set_Value (COLUMNNAME_IsPickWholeHU, IsPickWholeHU);
	}

	@Override
	public boolean isPickWholeHU() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsPickWholeHU);
	}

	@Override
	public de.metas.handlingunits.model.I_M_HU getM_HU()
	{
		return get_ValueAsPO(COLUMNNAME_M_HU_ID, de.metas.handlingunits.model.I_M_HU.class);
	}

	@Override
	public void setM_HU(final de.metas.handlingunits.model.I_M_HU M_HU)
	{
		set_ValueFromPO(COLUMNNAME_M_HU_ID, de.metas.handlingunits.model.I_M_HU.class, M_HU);
	}

	@Override
	public void setM_HU_ID (final int M_HU_ID)
	{
		if (M_HU_ID < 1) 
			set_Value (COLUMNNAME_M_HU_ID, null);
		else 
			set_Value (COLUMNNAME_M_HU_ID, M_HU_ID);
	}

	@Override
	public int getM_HU_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_HU_ID);
	}

	@Override
	public de.metas.handlingunits.model.I_M_HU getPickFrom_HU()
	{
		return get_ValueAsPO(COLUMNNAME_PickFrom_HU_ID, de.metas.handlingunits.model.I_M_HU.class);
	}

	@Override
	public void setPickFrom_HU(final de.metas.handlingunits.model.I_M_HU PickFrom_HU)
	{
		set_ValueFromPO(COLUMNNAME_PickFrom_HU_ID, de.metas.handlingunits.model.I_M_HU.class, PickFrom_HU);
	}

	@Override
	public void setPickFrom_HU_ID (final int PickFrom_HU_ID)
	{
		if (PickFrom_HU_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_PickFrom_HU_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_PickFrom_HU_ID, PickFrom_HU_ID);
	}

	@Override
	public int getPickFrom_HU_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_PickFrom_HU_ID);
	}

	@Override
	public void setQtyPicked (final BigDecimal QtyPicked)
	{
		set_Value (COLUMNNAME_QtyPicked, QtyPicked);
	}

	@Override
	public BigDecimal getQtyPicked() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyPicked);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyToPick (final BigDecimal QtyToPick)
	{
		set_ValueNoCheck (COLUMNNAME_QtyToPick, QtyToPick);
	}

	@Override
	public BigDecimal getQtyToPick() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyToPick);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	/** 
	 * RejectReason AD_Reference_ID=541422
	 * Reference name: QtyNotPicked RejectReason
	 */
	public static final int REJECTREASON_AD_Reference_ID=541422;
	/** NotFound = N */
	public static final String REJECTREASON_NotFound = "N";
	/** Damaged = D */
	public static final String REJECTREASON_Damaged = "D";
	@Override
	public void setRejectReason (final @Nullable java.lang.String RejectReason)
	{
		set_Value (COLUMNNAME_RejectReason, RejectReason);
	}

	@Override
	public java.lang.String getRejectReason() 
	{
		return get_ValueAsString(COLUMNNAME_RejectReason);
	}
}