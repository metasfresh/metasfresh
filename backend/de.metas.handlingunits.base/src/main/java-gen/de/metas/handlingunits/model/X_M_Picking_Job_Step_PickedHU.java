// Generated Model - DO NOT CHANGE
package de.metas.handlingunits.model;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for M_Picking_Job_Step_PickedHU
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_M_Picking_Job_Step_PickedHU extends org.compiere.model.PO implements I_M_Picking_Job_Step_PickedHU, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -2004018572L;

    /** Standard Constructor */
    public X_M_Picking_Job_Step_PickedHU (final Properties ctx, final int M_Picking_Job_Step_PickedHU_ID, @Nullable final String trxName)
    {
      super (ctx, M_Picking_Job_Step_PickedHU_ID, trxName);
    }

    /** Load Constructor */
    public X_M_Picking_Job_Step_PickedHU (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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

	@Override
	public de.metas.handlingunits.model.I_M_Picking_Candidate getM_Picking_Candidate()
	{
		return get_ValueAsPO(COLUMNNAME_M_Picking_Candidate_ID, de.metas.handlingunits.model.I_M_Picking_Candidate.class);
	}

	@Override
	public void setM_Picking_Candidate(final de.metas.handlingunits.model.I_M_Picking_Candidate M_Picking_Candidate)
	{
		set_ValueFromPO(COLUMNNAME_M_Picking_Candidate_ID, de.metas.handlingunits.model.I_M_Picking_Candidate.class, M_Picking_Candidate);
	}

	@Override
	public void setM_Picking_Candidate_ID (final int M_Picking_Candidate_ID)
	{
		if (M_Picking_Candidate_ID < 1) 
			set_Value (COLUMNNAME_M_Picking_Candidate_ID, null);
		else 
			set_Value (COLUMNNAME_M_Picking_Candidate_ID, M_Picking_Candidate_ID);
	}

	@Override
	public int getM_Picking_Candidate_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Picking_Candidate_ID);
	}

	@Override
	public de.metas.handlingunits.model.I_M_Picking_Job_HUAlternative getM_Picking_Job_HUAlternative()
	{
		return get_ValueAsPO(COLUMNNAME_M_Picking_Job_HUAlternative_ID, de.metas.handlingunits.model.I_M_Picking_Job_HUAlternative.class);
	}

	@Override
	public void setM_Picking_Job_HUAlternative(final de.metas.handlingunits.model.I_M_Picking_Job_HUAlternative M_Picking_Job_HUAlternative)
	{
		set_ValueFromPO(COLUMNNAME_M_Picking_Job_HUAlternative_ID, de.metas.handlingunits.model.I_M_Picking_Job_HUAlternative.class, M_Picking_Job_HUAlternative);
	}

	@Override
	public void setM_Picking_Job_HUAlternative_ID (final int M_Picking_Job_HUAlternative_ID)
	{
		if (M_Picking_Job_HUAlternative_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Picking_Job_HUAlternative_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Picking_Job_HUAlternative_ID, M_Picking_Job_HUAlternative_ID);
	}

	@Override
	public int getM_Picking_Job_HUAlternative_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Picking_Job_HUAlternative_ID);
	}

	@Override
	public de.metas.handlingunits.model.I_M_Picking_Job getM_Picking_Job()
	{
		return get_ValueAsPO(COLUMNNAME_M_Picking_Job_ID, de.metas.handlingunits.model.I_M_Picking_Job.class);
	}

	@Override
	public void setM_Picking_Job(final de.metas.handlingunits.model.I_M_Picking_Job M_Picking_Job)
	{
		set_ValueFromPO(COLUMNNAME_M_Picking_Job_ID, de.metas.handlingunits.model.I_M_Picking_Job.class, M_Picking_Job);
	}

	@Override
	public void setM_Picking_Job_ID (final int M_Picking_Job_ID)
	{
		if (M_Picking_Job_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Picking_Job_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Picking_Job_ID, M_Picking_Job_ID);
	}

	@Override
	public int getM_Picking_Job_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Picking_Job_ID);
	}

	@Override
	public de.metas.handlingunits.model.I_M_Picking_Job_Step getM_Picking_Job_Step()
	{
		return get_ValueAsPO(COLUMNNAME_M_Picking_Job_Step_ID, de.metas.handlingunits.model.I_M_Picking_Job_Step.class);
	}

	@Override
	public void setM_Picking_Job_Step(final de.metas.handlingunits.model.I_M_Picking_Job_Step M_Picking_Job_Step)
	{
		set_ValueFromPO(COLUMNNAME_M_Picking_Job_Step_ID, de.metas.handlingunits.model.I_M_Picking_Job_Step.class, M_Picking_Job_Step);
	}

	@Override
	public void setM_Picking_Job_Step_ID (final int M_Picking_Job_Step_ID)
	{
		if (M_Picking_Job_Step_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Picking_Job_Step_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Picking_Job_Step_ID, M_Picking_Job_Step_ID);
	}

	@Override
	public int getM_Picking_Job_Step_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Picking_Job_Step_ID);
	}

	@Override
	public void setM_Picking_Job_Step_PickedHU_ID (final int M_Picking_Job_Step_PickedHU_ID)
	{
		if (M_Picking_Job_Step_PickedHU_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Picking_Job_Step_PickedHU_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Picking_Job_Step_PickedHU_ID, M_Picking_Job_Step_PickedHU_ID);
	}

	@Override
	public int getM_Picking_Job_Step_PickedHU_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Picking_Job_Step_PickedHU_ID);
	}

	@Override
	public de.metas.handlingunits.model.I_M_HU getPicked_HU()
	{
		return get_ValueAsPO(COLUMNNAME_Picked_HU_ID, de.metas.handlingunits.model.I_M_HU.class);
	}

	@Override
	public void setPicked_HU(final de.metas.handlingunits.model.I_M_HU Picked_HU)
	{
		set_ValueFromPO(COLUMNNAME_Picked_HU_ID, de.metas.handlingunits.model.I_M_HU.class, Picked_HU);
	}

	@Override
	public void setPicked_HU_ID (final int Picked_HU_ID)
	{
		if (Picked_HU_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Picked_HU_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Picked_HU_ID, Picked_HU_ID);
	}

	@Override
	public int getPicked_HU_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Picked_HU_ID);
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
			set_Value (COLUMNNAME_PickFrom_HU_ID, null);
		else 
			set_Value (COLUMNNAME_PickFrom_HU_ID, PickFrom_HU_ID);
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
}