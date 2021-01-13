// Generated Model - DO NOT CHANGE
package de.metas.handlingunits.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for M_HU_BestBefore_V
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_M_HU_BestBefore_V extends org.compiere.model.PO implements I_M_HU_BestBefore_V, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 83716577L;

    /** Standard Constructor */
    public X_M_HU_BestBefore_V (final Properties ctx, final int M_HU_BestBefore_V_ID, @Nullable final String trxName)
    {
      super (ctx, M_HU_BestBefore_V_ID, trxName);
    }

    /** Load Constructor */
    public X_M_HU_BestBefore_V (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setGuaranteeDaysMin (final int GuaranteeDaysMin)
	{
		set_ValueNoCheck (COLUMNNAME_GuaranteeDaysMin, GuaranteeDaysMin);
	}

	@Override
	public int getGuaranteeDaysMin() 
	{
		return get_ValueAsInt(COLUMNNAME_GuaranteeDaysMin);
	}

	@Override
	public void setHU_BestBeforeDate (final @Nullable java.sql.Timestamp HU_BestBeforeDate)
	{
		set_ValueNoCheck (COLUMNNAME_HU_BestBeforeDate, HU_BestBeforeDate);
	}

	@Override
	public java.sql.Timestamp getHU_BestBeforeDate() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_HU_BestBeforeDate);
	}

	@Override
	public void setHU_Expired (final @Nullable java.lang.String HU_Expired)
	{
		set_ValueNoCheck (COLUMNNAME_HU_Expired, HU_Expired);
	}

	@Override
	public java.lang.String getHU_Expired() 
	{
		return get_ValueAsString(COLUMNNAME_HU_Expired);
	}

	@Override
	public void setHU_ExpiredWarnDate (final @Nullable java.sql.Timestamp HU_ExpiredWarnDate)
	{
		set_ValueNoCheck (COLUMNNAME_HU_ExpiredWarnDate, HU_ExpiredWarnDate);
	}

	@Override
	public java.sql.Timestamp getHU_ExpiredWarnDate() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_HU_ExpiredWarnDate);
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
}