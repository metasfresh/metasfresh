// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for C_Cost_Type
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_Cost_Type extends org.compiere.model.PO implements I_C_Cost_Type, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -243073994L;

    /** Standard Constructor */
    public X_C_Cost_Type (final Properties ctx, final int C_Cost_Type_ID, @Nullable final String trxName)
    {
      super (ctx, C_Cost_Type_ID, trxName);
    }

    /** Load Constructor */
    public X_C_Cost_Type (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setC_Cost_Type_ID (final int C_Cost_Type_ID)
	{
		if (C_Cost_Type_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Cost_Type_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Cost_Type_ID, C_Cost_Type_ID);
	}

	@Override
	public int getC_Cost_Type_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Cost_Type_ID);
	}

	/** 
	 * CostCalculationMethod AD_Reference_ID=541713
	 * Reference name: CostCalculationMethod
	 */
	public static final int COSTCALCULATIONMETHOD_AD_Reference_ID=541713;
	/** FixedAmount = F */
	public static final String COSTCALCULATIONMETHOD_FixedAmount = "F";
	/** PercentageOfAmount = P */
	public static final String COSTCALCULATIONMETHOD_PercentageOfAmount = "P";
	@Override
	public void setCostCalculationMethod (final java.lang.String CostCalculationMethod)
	{
		set_Value (COLUMNNAME_CostCalculationMethod, CostCalculationMethod);
	}

	@Override
	public java.lang.String getCostCalculationMethod() 
	{
		return get_ValueAsString(COLUMNNAME_CostCalculationMethod);
	}

	/** 
	 * CostDistributionMethod AD_Reference_ID=541712
	 * Reference name: CostDistributionMethod
	 */
	public static final int COSTDISTRIBUTIONMETHOD_AD_Reference_ID=541712;
	/** Quantity = Q */
	public static final String COSTDISTRIBUTIONMETHOD_Quantity = "Q";
	/** Amount = A */
	public static final String COSTDISTRIBUTIONMETHOD_Amount = "A";
	@Override
	public void setCostDistributionMethod (final java.lang.String CostDistributionMethod)
	{
		set_Value (COLUMNNAME_CostDistributionMethod, CostDistributionMethod);
	}

	@Override
	public java.lang.String getCostDistributionMethod() 
	{
		return get_ValueAsString(COLUMNNAME_CostDistributionMethod);
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
	public void setValue (final java.lang.String Value)
	{
		set_Value (COLUMNNAME_Value, Value);
	}

	@Override
	public java.lang.String getValue() 
	{
		return get_ValueAsString(COLUMNNAME_Value);
	}
}