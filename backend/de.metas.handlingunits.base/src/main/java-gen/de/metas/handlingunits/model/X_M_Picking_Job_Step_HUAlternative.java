// Generated Model - DO NOT CHANGE
package de.metas.handlingunits.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for M_Picking_Job_Step_HUAlternative
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_M_Picking_Job_Step_HUAlternative extends org.compiere.model.PO implements I_M_Picking_Job_Step_HUAlternative, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1843914827L;

    /** Standard Constructor */
    public X_M_Picking_Job_Step_HUAlternative (final Properties ctx, final int M_Picking_Job_Step_HUAlternative_ID, @Nullable final String trxName)
    {
      super (ctx, M_Picking_Job_Step_HUAlternative_ID, trxName);
    }

    /** Load Constructor */
    public X_M_Picking_Job_Step_HUAlternative (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setM_Picking_Job_Step_HUAlternative_ID (final int M_Picking_Job_Step_HUAlternative_ID)
	{
		if (M_Picking_Job_Step_HUAlternative_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Picking_Job_Step_HUAlternative_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Picking_Job_Step_HUAlternative_ID, M_Picking_Job_Step_HUAlternative_ID);
	}

	@Override
	public int getM_Picking_Job_Step_HUAlternative_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Picking_Job_Step_HUAlternative_ID);
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
}