// Generated Model - DO NOT CHANGE
package de.metas.contracts.commission.model;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_Commission_Share
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_Commission_Share extends org.compiere.model.PO implements I_C_Commission_Share, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 2140392510L;

    /** Standard Constructor */
    public X_C_Commission_Share (final Properties ctx, final int C_Commission_Share_ID, @Nullable final String trxName)
    {
      super (ctx, C_Commission_Share_ID, trxName);
    }

    /** Load Constructor */
    public X_C_Commission_Share (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setC_BPartner_Payer_ID (final int C_BPartner_Payer_ID)
	{
		if (C_BPartner_Payer_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_Payer_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_Payer_ID, C_BPartner_Payer_ID);
	}

	@Override
	public int getC_BPartner_Payer_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_Payer_ID);
	}

	@Override
	public void setC_BPartner_SalesRep_ID (final int C_BPartner_SalesRep_ID)
	{
		if (C_BPartner_SalesRep_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_SalesRep_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_SalesRep_ID, C_BPartner_SalesRep_ID);
	}

	@Override
	public int getC_BPartner_SalesRep_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_SalesRep_ID);
	}

	@Override
	public I_C_Commission_Instance getC_Commission_Instance()
	{
		return get_ValueAsPO(COLUMNNAME_C_Commission_Instance_ID, I_C_Commission_Instance.class);
	}

	@Override
	public void setC_Commission_Instance(final I_C_Commission_Instance C_Commission_Instance)
	{
		set_ValueFromPO(COLUMNNAME_C_Commission_Instance_ID, I_C_Commission_Instance.class, C_Commission_Instance);
	}

	@Override
	public void setC_Commission_Instance_ID (final int C_Commission_Instance_ID)
	{
		if (C_Commission_Instance_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Commission_Instance_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Commission_Instance_ID, C_Commission_Instance_ID);
	}

	@Override
	public int getC_Commission_Instance_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Commission_Instance_ID);
	}

	@Override
	public void setC_Commission_Share_ID (final int C_Commission_Share_ID)
	{
		if (C_Commission_Share_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Commission_Share_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Commission_Share_ID, C_Commission_Share_ID);
	}

	@Override
	public int getC_Commission_Share_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Commission_Share_ID);
	}

	@Override
	public I_C_CommissionSettingsLine getC_CommissionSettingsLine()
	{
		return get_ValueAsPO(COLUMNNAME_C_CommissionSettingsLine_ID, I_C_CommissionSettingsLine.class);
	}

	@Override
	public void setC_CommissionSettingsLine(final I_C_CommissionSettingsLine C_CommissionSettingsLine)
	{
		set_ValueFromPO(COLUMNNAME_C_CommissionSettingsLine_ID, I_C_CommissionSettingsLine.class, C_CommissionSettingsLine);
	}

	@Override
	public void setC_CommissionSettingsLine_ID (final int C_CommissionSettingsLine_ID)
	{
		if (C_CommissionSettingsLine_ID < 1) 
			set_Value (COLUMNNAME_C_CommissionSettingsLine_ID, null);
		else 
			set_Value (COLUMNNAME_C_CommissionSettingsLine_ID, C_CommissionSettingsLine_ID);
	}

	@Override
	public int getC_CommissionSettingsLine_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_CommissionSettingsLine_ID);
	}

	@Override
	public void setC_Flatrate_Term_ID (final int C_Flatrate_Term_ID)
	{
		if (C_Flatrate_Term_ID < 1) 
			set_Value (COLUMNNAME_C_Flatrate_Term_ID, null);
		else 
			set_Value (COLUMNNAME_C_Flatrate_Term_ID, C_Flatrate_Term_ID);
	}

	@Override
	public int getC_Flatrate_Term_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Flatrate_Term_ID);
	}

	@Override
	public I_C_MediatedCommissionSettingsLine getC_MediatedCommissionSettingsLine()
	{
		return get_ValueAsPO(COLUMNNAME_C_MediatedCommissionSettingsLine_ID, I_C_MediatedCommissionSettingsLine.class);
	}

	@Override
	public void setC_MediatedCommissionSettingsLine(final I_C_MediatedCommissionSettingsLine C_MediatedCommissionSettingsLine)
	{
		set_ValueFromPO(COLUMNNAME_C_MediatedCommissionSettingsLine_ID, I_C_MediatedCommissionSettingsLine.class, C_MediatedCommissionSettingsLine);
	}

	@Override
	public void setC_MediatedCommissionSettingsLine_ID (final int C_MediatedCommissionSettingsLine_ID)
	{
		if (C_MediatedCommissionSettingsLine_ID < 1) 
			set_Value (COLUMNNAME_C_MediatedCommissionSettingsLine_ID, null);
		else 
			set_Value (COLUMNNAME_C_MediatedCommissionSettingsLine_ID, C_MediatedCommissionSettingsLine_ID);
	}

	@Override
	public int getC_MediatedCommissionSettingsLine_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_MediatedCommissionSettingsLine_ID);
	}

	@Override
	public void setCommission_Product_ID (final int Commission_Product_ID)
	{
		if (Commission_Product_ID < 1) 
			set_Value (COLUMNNAME_Commission_Product_ID, null);
		else 
			set_Value (COLUMNNAME_Commission_Product_ID, Commission_Product_ID);
	}

	@Override
	public int getCommission_Product_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Commission_Product_ID);
	}

	@Override
	public void setIsSimulation (final boolean IsSimulation)
	{
		set_Value (COLUMNNAME_IsSimulation, IsSimulation);
	}

	@Override
	public boolean isSimulation() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsSimulation);
	}

	@Override
	public void setIsSOTrx (final boolean IsSOTrx)
	{
		set_Value (COLUMNNAME_IsSOTrx, IsSOTrx);
	}

	@Override
	public boolean isSOTrx() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsSOTrx);
	}

	@Override
	public void setLevelHierarchy (final int LevelHierarchy)
	{
		set_ValueNoCheck (COLUMNNAME_LevelHierarchy, LevelHierarchy);
	}

	@Override
	public int getLevelHierarchy() 
	{
		return get_ValueAsInt(COLUMNNAME_LevelHierarchy);
	}

	@Override
	public void setPointsSum_Forecasted (final BigDecimal PointsSum_Forecasted)
	{
		set_Value (COLUMNNAME_PointsSum_Forecasted, PointsSum_Forecasted);
	}

	@Override
	public BigDecimal getPointsSum_Forecasted() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_PointsSum_Forecasted);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setPointsSum_Invoiceable (final BigDecimal PointsSum_Invoiceable)
	{
		set_ValueNoCheck (COLUMNNAME_PointsSum_Invoiceable, PointsSum_Invoiceable);
	}

	@Override
	public BigDecimal getPointsSum_Invoiceable() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_PointsSum_Invoiceable);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setPointsSum_Invoiced (final BigDecimal PointsSum_Invoiced)
	{
		set_Value (COLUMNNAME_PointsSum_Invoiced, PointsSum_Invoiced);
	}

	@Override
	public BigDecimal getPointsSum_Invoiced() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_PointsSum_Invoiced);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setPointsSum_Settled (final BigDecimal PointsSum_Settled)
	{
		set_Value (COLUMNNAME_PointsSum_Settled, PointsSum_Settled);
	}

	@Override
	public BigDecimal getPointsSum_Settled() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_PointsSum_Settled);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setPointsSum_ToSettle (final BigDecimal PointsSum_ToSettle)
	{
		set_Value (COLUMNNAME_PointsSum_ToSettle, PointsSum_ToSettle);
	}

	@Override
	public BigDecimal getPointsSum_ToSettle() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_PointsSum_ToSettle);
		return bd != null ? bd : BigDecimal.ZERO;
	}
}