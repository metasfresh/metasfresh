// Generated Model - DO NOT CHANGE
package de.metas.contracts.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_Contract_Change
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_Contract_Change extends org.compiere.model.PO implements I_C_Contract_Change, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 2102777715L;

    /** Standard Constructor */
    public X_C_Contract_Change (final Properties ctx, final int C_Contract_Change_ID, @Nullable final String trxName)
    {
      super (ctx, C_Contract_Change_ID, trxName);
    }

    /** Load Constructor */
    public X_C_Contract_Change (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
    {
      super (ctx, rs, trxName);
    }


	/** Load Meta Data */
	@Override
	protected org.compiere.model.POInfo initPO(final Properties ctx)
	{
		return org.compiere.model.POInfo.getPOInfo(Table_Name);
	}

	/** 
	 * Action AD_Reference_ID=540010
	 * Reference name: C_Contract_Change Action
	 */
	public static final int ACTION_AD_Reference_ID=540010;
	/** Statuswechsel = ST */
	public static final String ACTION_Statuswechsel = "ST";
	/** Abowechsel = SU */
	public static final String ACTION_Abowechsel = "SU";
	@Override
	public void setAction (final java.lang.String Action)
	{
		set_Value (COLUMNNAME_Action, Action);
	}

	@Override
	public java.lang.String getAction() 
	{
		return get_ValueAsString(COLUMNNAME_Action);
	}

	@Override
	public void setC_Contract_Change_ID (final int C_Contract_Change_ID)
	{
		if (C_Contract_Change_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Contract_Change_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Contract_Change_ID, C_Contract_Change_ID);
	}

	@Override
	public int getC_Contract_Change_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Contract_Change_ID);
	}

	@Override
	public de.metas.contracts.model.I_C_Flatrate_Conditions getC_Flatrate_Conditions()
	{
		return get_ValueAsPO(COLUMNNAME_C_Flatrate_Conditions_ID, de.metas.contracts.model.I_C_Flatrate_Conditions.class);
	}

	@Override
	public void setC_Flatrate_Conditions(final de.metas.contracts.model.I_C_Flatrate_Conditions C_Flatrate_Conditions)
	{
		set_ValueFromPO(COLUMNNAME_C_Flatrate_Conditions_ID, de.metas.contracts.model.I_C_Flatrate_Conditions.class, C_Flatrate_Conditions);
	}

	@Override
	public void setC_Flatrate_Conditions_ID (final int C_Flatrate_Conditions_ID)
	{
		if (C_Flatrate_Conditions_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Flatrate_Conditions_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Flatrate_Conditions_ID, C_Flatrate_Conditions_ID);
	}

	@Override
	public int getC_Flatrate_Conditions_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Flatrate_Conditions_ID);
	}

	@Override
	public de.metas.contracts.model.I_C_Flatrate_Conditions getC_Flatrate_Conditions_Next()
	{
		return get_ValueAsPO(COLUMNNAME_C_Flatrate_Conditions_Next_ID, de.metas.contracts.model.I_C_Flatrate_Conditions.class);
	}

	@Override
	public void setC_Flatrate_Conditions_Next(final de.metas.contracts.model.I_C_Flatrate_Conditions C_Flatrate_Conditions_Next)
	{
		set_ValueFromPO(COLUMNNAME_C_Flatrate_Conditions_Next_ID, de.metas.contracts.model.I_C_Flatrate_Conditions.class, C_Flatrate_Conditions_Next);
	}

	@Override
	public void setC_Flatrate_Conditions_Next_ID (final int C_Flatrate_Conditions_Next_ID)
	{
		if (C_Flatrate_Conditions_Next_ID < 1) 
			set_Value (COLUMNNAME_C_Flatrate_Conditions_Next_ID, null);
		else 
			set_Value (COLUMNNAME_C_Flatrate_Conditions_Next_ID, C_Flatrate_Conditions_Next_ID);
	}

	@Override
	public int getC_Flatrate_Conditions_Next_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Flatrate_Conditions_Next_ID);
	}

	@Override
	public de.metas.contracts.model.I_C_Flatrate_Transition getC_Flatrate_Transition()
	{
		return get_ValueAsPO(COLUMNNAME_C_Flatrate_Transition_ID, de.metas.contracts.model.I_C_Flatrate_Transition.class);
	}

	@Override
	public void setC_Flatrate_Transition(final de.metas.contracts.model.I_C_Flatrate_Transition C_Flatrate_Transition)
	{
		set_ValueFromPO(COLUMNNAME_C_Flatrate_Transition_ID, de.metas.contracts.model.I_C_Flatrate_Transition.class, C_Flatrate_Transition);
	}

	@Override
	public void setC_Flatrate_Transition_ID (final int C_Flatrate_Transition_ID)
	{
		if (C_Flatrate_Transition_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Flatrate_Transition_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Flatrate_Transition_ID, C_Flatrate_Transition_ID);
	}

	@Override
	public int getC_Flatrate_Transition_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Flatrate_Transition_ID);
	}

	/** 
	 * ContractStatus AD_Reference_ID=540004
	 * Reference name: ContractStatus ChangeStatus
	 */
	public static final int CONTRACTSTATUS_AD_Reference_ID=540004;
	/** Lieferpause = Pa */
	public static final String CONTRACTSTATUS_Lieferpause = "Pa";
	/** Gekündigt = Qu */
	public static final String CONTRACTSTATUS_Gekuendigt = "Qu";
	/** Laufend  = Ru */
	public static final String CONTRACTSTATUS_Laufend = "Ru";
	/** Liefersperre = St */
	public static final String CONTRACTSTATUS_Liefersperre = "St";
	@Override
	public void setContractStatus (final @Nullable java.lang.String ContractStatus)
	{
		set_Value (COLUMNNAME_ContractStatus, ContractStatus);
	}

	@Override
	public java.lang.String getContractStatus() 
	{
		return get_ValueAsString(COLUMNNAME_ContractStatus);
	}

	@Override
	public void setDeadLine (final int DeadLine)
	{
		set_Value (COLUMNNAME_DeadLine, DeadLine);
	}

	@Override
	public int getDeadLine() 
	{
		return get_ValueAsInt(COLUMNNAME_DeadLine);
	}

	/** 
	 * DeadLineUnit AD_Reference_ID=540281
	 * Reference name: TermDurationUnit
	 */
	public static final int DEADLINEUNIT_AD_Reference_ID=540281;
	/** Monat(e) = month */
	public static final String DEADLINEUNIT_MonatE = "month";
	/** Woche(n) = week */
	public static final String DEADLINEUNIT_WocheN = "week";
	/** Tag(e) = day */
	public static final String DEADLINEUNIT_TagE = "day";
	/** Jahr(e) = year */
	public static final String DEADLINEUNIT_JahrE = "year";
	@Override
	public void setDeadLineUnit (final java.lang.String DeadLineUnit)
	{
		set_Value (COLUMNNAME_DeadLineUnit, DeadLineUnit);
	}

	@Override
	public java.lang.String getDeadLineUnit() 
	{
		return get_ValueAsString(COLUMNNAME_DeadLineUnit);
	}

	@Override
	public void setDescription (final @Nullable java.lang.String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	@Override
	public java.lang.String getDescription() 
	{
		return get_ValueAsString(COLUMNNAME_Description);
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
}