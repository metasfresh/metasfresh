// Generated Model - DO NOT CHANGE
package de.metas.handlingunits.model;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for M_Inventory_Candidate
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_M_Inventory_Candidate extends org.compiere.model.PO implements I_M_Inventory_Candidate, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1012932021L;

    /** Standard Constructor */
    public X_M_Inventory_Candidate (final Properties ctx, final int M_Inventory_Candidate_ID, @Nullable final String trxName)
    {
      super (ctx, M_Inventory_Candidate_ID, trxName);
    }

    /** Load Constructor */
    public X_M_Inventory_Candidate (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
			set_Value (COLUMNNAME_C_UOM_ID, null);
		else 
			set_Value (COLUMNNAME_C_UOM_ID, C_UOM_ID);
	}

	@Override
	public int getC_UOM_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_UOM_ID);
	}

	/** 
	 * DisposeReason AD_Reference_ID=541422
	 * Reference name: QtyNotPicked RejectReason
	 */
	public static final int DISPOSEREASON_AD_Reference_ID=541422;
	/** NotFound = N */
	public static final String DISPOSEREASON_NotFound = "N";
	/** Damaged = D */
	public static final String DISPOSEREASON_Damaged = "D";
	@Override
	public void setDisposeReason (final @Nullable java.lang.String DisposeReason)
	{
		set_Value (COLUMNNAME_DisposeReason, DisposeReason);
	}

	@Override
	public java.lang.String getDisposeReason() 
	{
		return get_ValueAsString(COLUMNNAME_DisposeReason);
	}

	@Override
	public void setIsWholeHU (final boolean IsWholeHU)
	{
		set_Value (COLUMNNAME_IsWholeHU, IsWholeHU);
	}

	@Override
	public boolean isWholeHU() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsWholeHU);
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
			set_ValueNoCheck (COLUMNNAME_M_HU_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_HU_ID, M_HU_ID);
	}

	@Override
	public int getM_HU_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_HU_ID);
	}

	@Override
	public void setM_Inventory_Candidate_ID (final int M_Inventory_Candidate_ID)
	{
		if (M_Inventory_Candidate_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Inventory_Candidate_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Inventory_Candidate_ID, M_Inventory_Candidate_ID);
	}

	@Override
	public int getM_Inventory_Candidate_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Inventory_Candidate_ID);
	}

	@Override
	public void setM_Product_ID (final int M_Product_ID)
	{
		if (M_Product_ID < 1) 
			set_Value (COLUMNNAME_M_Product_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_ID, M_Product_ID);
	}

	@Override
	public int getM_Product_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Product_ID);
	}

	@Override
	public void setProcessed (final boolean Processed)
	{
		set_Value (COLUMNNAME_Processed, Processed);
	}

	@Override
	public boolean isProcessed() 
	{
		return get_ValueAsBoolean(COLUMNNAME_Processed);
	}

	@Override
	public void setQtyToDispose (final BigDecimal QtyToDispose)
	{
		set_Value (COLUMNNAME_QtyToDispose, QtyToDispose);
	}

	@Override
	public BigDecimal getQtyToDispose() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyToDispose);
		return bd != null ? bd : BigDecimal.ZERO;
	}
}