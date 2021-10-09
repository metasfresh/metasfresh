// Generated Model - DO NOT CHANGE
package de.metas.handlingunits.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for M_Source_HU
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_M_Source_HU extends org.compiere.model.PO implements I_M_Source_HU, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 1877649145L;

    /** Standard Constructor */
    public X_M_Source_HU (final Properties ctx, final int M_Source_HU_ID, @Nullable final String trxName)
    {
      super (ctx, M_Source_HU_ID, trxName);
    }

    /** Load Constructor */
    public X_M_Source_HU (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setM_Source_HU_ID (final int M_Source_HU_ID)
	{
		if (M_Source_HU_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Source_HU_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Source_HU_ID, M_Source_HU_ID);
	}

	@Override
	public int getM_Source_HU_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Source_HU_ID);
	}

	@Override
	public void setPreDestroy_Snapshot_UUID (final @Nullable java.lang.String PreDestroy_Snapshot_UUID)
	{
		set_Value (COLUMNNAME_PreDestroy_Snapshot_UUID, PreDestroy_Snapshot_UUID);
	}

	@Override
	public java.lang.String getPreDestroy_Snapshot_UUID() 
	{
		return get_ValueAsString(COLUMNNAME_PreDestroy_Snapshot_UUID);
	}
}