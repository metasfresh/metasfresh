// Generated Model - DO NOT CHANGE
package de.metas.contracts.model;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for ModCntr_Settings
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_ModCntr_Settings extends org.compiere.model.PO implements I_ModCntr_Settings, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -569725144L;

    /** Standard Constructor */
    public X_ModCntr_Settings (final Properties ctx, final int ModCntr_Settings_ID, @Nullable final String trxName)
    {
      super (ctx, ModCntr_Settings_ID, trxName);
    }

    /** Load Constructor */
    public X_ModCntr_Settings (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setAddInterestDays (final int AddInterestDays)
	{
		set_Value (COLUMNNAME_AddInterestDays, AddInterestDays);
	}

	@Override
	public int getAddInterestDays() 
	{
		return get_ValueAsInt(COLUMNNAME_AddInterestDays);
	}

	@Override
	public org.compiere.model.I_C_Calendar getC_Calendar()
	{
		return get_ValueAsPO(COLUMNNAME_C_Calendar_ID, org.compiere.model.I_C_Calendar.class);
	}

	@Override
	public void setC_Calendar(final org.compiere.model.I_C_Calendar C_Calendar)
	{
		set_ValueFromPO(COLUMNNAME_C_Calendar_ID, org.compiere.model.I_C_Calendar.class, C_Calendar);
	}

	@Override
	public void setC_Calendar_ID (final int C_Calendar_ID)
	{
		if (C_Calendar_ID < 1) 
			set_Value (COLUMNNAME_C_Calendar_ID, null);
		else 
			set_Value (COLUMNNAME_C_Calendar_ID, C_Calendar_ID);
	}

	@Override
	public int getC_Calendar_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Calendar_ID);
	}

	@Override
	public org.compiere.model.I_C_Year getC_Year()
	{
		return get_ValueAsPO(COLUMNNAME_C_Year_ID, org.compiere.model.I_C_Year.class);
	}

	@Override
	public void setC_Year(final org.compiere.model.I_C_Year C_Year)
	{
		set_ValueFromPO(COLUMNNAME_C_Year_ID, org.compiere.model.I_C_Year.class, C_Year);
	}

	@Override
	public void setC_Year_ID (final int C_Year_ID)
	{
		if (C_Year_ID < 1) 
			set_Value (COLUMNNAME_C_Year_ID, null);
		else 
			set_Value (COLUMNNAME_C_Year_ID, C_Year_ID);
	}

	@Override
	public int getC_Year_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Year_ID);
	}

	@Override
	public void setInterestRate (final BigDecimal InterestRate)
	{
		set_Value (COLUMNNAME_InterestRate, InterestRate);
	}

	@Override
	public BigDecimal getInterestRate() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_InterestRate);
		return bd != null ? bd : BigDecimal.ZERO;
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
	public void setM_Co_Product_ID (final int M_Co_Product_ID)
	{
		if (M_Co_Product_ID < 1) 
			set_Value (COLUMNNAME_M_Co_Product_ID, null);
		else 
			set_Value (COLUMNNAME_M_Co_Product_ID, M_Co_Product_ID);
	}

	@Override
	public int getM_Co_Product_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Co_Product_ID);
	}

	@Override
	public void setModCntr_Settings_ID (final int ModCntr_Settings_ID)
	{
		if (ModCntr_Settings_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_ModCntr_Settings_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_ModCntr_Settings_ID, ModCntr_Settings_ID);
	}

	@Override
	public int getModCntr_Settings_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_ModCntr_Settings_ID);
	}

	@Override
	public void setM_PricingSystem_ID (final int M_PricingSystem_ID)
	{
		if (M_PricingSystem_ID < 1) 
			set_Value (COLUMNNAME_M_PricingSystem_ID, null);
		else 
			set_Value (COLUMNNAME_M_PricingSystem_ID, M_PricingSystem_ID);
	}

	@Override
	public int getM_PricingSystem_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_PricingSystem_ID);
	}

	@Override
	public void setM_Processed_Product_ID (final int M_Processed_Product_ID)
	{
		if (M_Processed_Product_ID < 1) 
			set_Value (COLUMNNAME_M_Processed_Product_ID, null);
		else 
			set_Value (COLUMNNAME_M_Processed_Product_ID, M_Processed_Product_ID);
	}

	@Override
	public int getM_Processed_Product_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Processed_Product_ID);
	}

	@Override
	public void setM_Raw_Product_ID (final int M_Raw_Product_ID)
	{
		if (M_Raw_Product_ID < 1) 
			set_Value (COLUMNNAME_M_Raw_Product_ID, null);
		else 
			set_Value (COLUMNNAME_M_Raw_Product_ID, M_Raw_Product_ID);
	}

	@Override
	public int getM_Raw_Product_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Raw_Product_ID);
	}

	@Override
	public void setName (final java.lang.String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	@Override
	public java.lang.String getName() 
	{
		return get_ValueAsString(COLUMNNAME_Name);
	}

	@Override
	public void setStorageCostStartDate (final java.sql.Timestamp StorageCostStartDate)
	{
		set_Value (COLUMNNAME_StorageCostStartDate, StorageCostStartDate);
	}

	@Override
	public java.sql.Timestamp getStorageCostStartDate() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_StorageCostStartDate);
	}
}