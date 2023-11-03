// Generated Model - DO NOT CHANGE
package de.metas.contracts.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_BPartner_InterimContract
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_BPartner_InterimContract extends org.compiere.model.PO implements I_C_BPartner_InterimContract, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -423742516L;

    /** Standard Constructor */
    public X_C_BPartner_InterimContract (final Properties ctx, final int C_BPartner_InterimContract_ID, @Nullable final String trxName)
    {
      super (ctx, C_BPartner_InterimContract_ID, trxName);
    }

    /** Load Constructor */
    public X_C_BPartner_InterimContract (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setC_BPartner_ID (final int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_ID, C_BPartner_ID);
	}

	@Override
	public int getC_BPartner_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_ID);
	}

	@Override
	public void setC_BPartner_InterimContract_ID (final int C_BPartner_InterimContract_ID)
	{
		if (C_BPartner_InterimContract_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_InterimContract_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_InterimContract_ID, C_BPartner_InterimContract_ID);
	}

	@Override
	public int getC_BPartner_InterimContract_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_InterimContract_ID);
	}

	@Override
	public org.compiere.model.I_C_Calendar getC_Harvesting_Calendar()
	{
		return get_ValueAsPO(COLUMNNAME_C_Harvesting_Calendar_ID, org.compiere.model.I_C_Calendar.class);
	}

	@Override
	public void setC_Harvesting_Calendar(final org.compiere.model.I_C_Calendar C_Harvesting_Calendar)
	{
		set_ValueFromPO(COLUMNNAME_C_Harvesting_Calendar_ID, org.compiere.model.I_C_Calendar.class, C_Harvesting_Calendar);
	}

	@Override
	public void setC_Harvesting_Calendar_ID (final int C_Harvesting_Calendar_ID)
	{
		if (C_Harvesting_Calendar_ID < 1) 
			set_Value (COLUMNNAME_C_Harvesting_Calendar_ID, null);
		else 
			set_Value (COLUMNNAME_C_Harvesting_Calendar_ID, C_Harvesting_Calendar_ID);
	}

	@Override
	public int getC_Harvesting_Calendar_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Harvesting_Calendar_ID);
	}

	@Override
	public org.compiere.model.I_C_Year getHarvesting_Year()
	{
		return get_ValueAsPO(COLUMNNAME_Harvesting_Year_ID, org.compiere.model.I_C_Year.class);
	}

	@Override
	public void setHarvesting_Year(final org.compiere.model.I_C_Year Harvesting_Year)
	{
		set_ValueFromPO(COLUMNNAME_Harvesting_Year_ID, org.compiere.model.I_C_Year.class, Harvesting_Year);
	}

	@Override
	public void setHarvesting_Year_ID (final int Harvesting_Year_ID)
	{
		if (Harvesting_Year_ID < 1) 
			set_Value (COLUMNNAME_Harvesting_Year_ID, null);
		else 
			set_Value (COLUMNNAME_Harvesting_Year_ID, Harvesting_Year_ID);
	}

	@Override
	public int getHarvesting_Year_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Harvesting_Year_ID);
	}

	@Override
	public void setIsInterimContract (final boolean IsInterimContract)
	{
		set_Value (COLUMNNAME_IsInterimContract, IsInterimContract);
	}

	@Override
	public boolean isInterimContract() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsInterimContract);
	}
}