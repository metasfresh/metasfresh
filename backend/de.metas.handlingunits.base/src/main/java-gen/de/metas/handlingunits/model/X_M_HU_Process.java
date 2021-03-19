// Generated Model - DO NOT CHANGE
package de.metas.handlingunits.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for M_HU_Process
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_M_HU_Process extends org.compiere.model.PO implements I_M_HU_Process, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1015251039L;

    /** Standard Constructor */
    public X_M_HU_Process (final Properties ctx, final int M_HU_Process_ID, @Nullable final String trxName)
    {
      super (ctx, M_HU_Process_ID, trxName);
    }

    /** Load Constructor */
    public X_M_HU_Process (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public org.compiere.model.I_AD_Process getAD_Process()
	{
		return get_ValueAsPO(COLUMNNAME_AD_Process_ID, org.compiere.model.I_AD_Process.class);
	}

	@Override
	public void setAD_Process(final org.compiere.model.I_AD_Process AD_Process)
	{
		set_ValueFromPO(COLUMNNAME_AD_Process_ID, org.compiere.model.I_AD_Process.class, AD_Process);
	}

	@Override
	public void setAD_Process_ID (final int AD_Process_ID)
	{
		if (AD_Process_ID < 1) 
			set_Value (COLUMNNAME_AD_Process_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Process_ID, AD_Process_ID);
	}

	@Override
	public int getAD_Process_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Process_ID);
	}

	@Override
	public void setIsApplyToCUs (final boolean IsApplyToCUs)
	{
		set_Value (COLUMNNAME_IsApplyToCUs, IsApplyToCUs);
	}

	@Override
	public boolean isApplyToCUs() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsApplyToCUs);
	}

	@Override
	public void setIsApplyToLUs (final boolean IsApplyToLUs)
	{
		set_Value (COLUMNNAME_IsApplyToLUs, IsApplyToLUs);
	}

	@Override
	public boolean isApplyToLUs() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsApplyToLUs);
	}

	@Override
	public void setIsApplyToTopLevelHUsOnly (final boolean IsApplyToTopLevelHUsOnly)
	{
		set_Value (COLUMNNAME_IsApplyToTopLevelHUsOnly, IsApplyToTopLevelHUsOnly);
	}

	@Override
	public boolean isApplyToTopLevelHUsOnly() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsApplyToTopLevelHUsOnly);
	}

	@Override
	public void setIsApplyToTUs (final boolean IsApplyToTUs)
	{
		set_Value (COLUMNNAME_IsApplyToTUs, IsApplyToTUs);
	}

	@Override
	public boolean isApplyToTUs() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsApplyToTUs);
	}

	@Override
	public void setIsProvideAsUserAction (final boolean IsProvideAsUserAction)
	{
		set_Value (COLUMNNAME_IsProvideAsUserAction, IsProvideAsUserAction);
	}

	@Override
	public boolean isProvideAsUserAction() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsProvideAsUserAction);
	}

	@Override
	public de.metas.handlingunits.model.I_M_HU_PI getM_HU_PI()
	{
		return get_ValueAsPO(COLUMNNAME_M_HU_PI_ID, de.metas.handlingunits.model.I_M_HU_PI.class);
	}

	@Override
	public void setM_HU_PI(final de.metas.handlingunits.model.I_M_HU_PI M_HU_PI)
	{
		set_ValueFromPO(COLUMNNAME_M_HU_PI_ID, de.metas.handlingunits.model.I_M_HU_PI.class, M_HU_PI);
	}

	@Override
	public void setM_HU_PI_ID (final int M_HU_PI_ID)
	{
		if (M_HU_PI_ID < 1) 
			set_Value (COLUMNNAME_M_HU_PI_ID, null);
		else 
			set_Value (COLUMNNAME_M_HU_PI_ID, M_HU_PI_ID);
	}

	@Override
	public int getM_HU_PI_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_HU_PI_ID);
	}

	@Override
	public void setM_HU_Process_ID (final int M_HU_Process_ID)
	{
		if (M_HU_Process_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_HU_Process_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_HU_Process_ID, M_HU_Process_ID);
	}

	@Override
	public int getM_HU_Process_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_HU_Process_ID);
	}
}