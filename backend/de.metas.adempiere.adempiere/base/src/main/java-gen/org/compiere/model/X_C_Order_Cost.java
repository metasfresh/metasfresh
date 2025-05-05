// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for C_Order_Cost
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_Order_Cost extends org.compiere.model.PO implements I_C_Order_Cost, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -775061758L;

    /** Standard Constructor */
    public X_C_Order_Cost (final Properties ctx, final int C_Order_Cost_ID, @Nullable final String trxName)
    {
      super (ctx, C_Order_Cost_ID, trxName);
    }

    /** Load Constructor */
    public X_C_Order_Cost (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public org.compiere.model.I_C_Cost_Type getC_Cost_Type()
	{
		return get_ValueAsPO(COLUMNNAME_C_Cost_Type_ID, org.compiere.model.I_C_Cost_Type.class);
	}

	@Override
	public void setC_Cost_Type(final org.compiere.model.I_C_Cost_Type C_Cost_Type)
	{
		set_ValueFromPO(COLUMNNAME_C_Cost_Type_ID, org.compiere.model.I_C_Cost_Type.class, C_Cost_Type);
	}

	@Override
	public void setC_Cost_Type_ID (final int C_Cost_Type_ID)
	{
		if (C_Cost_Type_ID < 1) 
			set_Value (COLUMNNAME_C_Cost_Type_ID, null);
		else 
			set_Value (COLUMNNAME_C_Cost_Type_ID, C_Cost_Type_ID);
	}

	@Override
	public int getC_Cost_Type_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Cost_Type_ID);
	}

	@Override
	public void setC_Currency_ID (final int C_Currency_ID)
	{
		if (C_Currency_ID < 1) 
			set_Value (COLUMNNAME_C_Currency_ID, null);
		else 
			set_Value (COLUMNNAME_C_Currency_ID, C_Currency_ID);
	}

	@Override
	public int getC_Currency_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Currency_ID);
	}

	@Override
	public void setC_Order_Cost_ID (final int C_Order_Cost_ID)
	{
		if (C_Order_Cost_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Order_Cost_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Order_Cost_ID, C_Order_Cost_ID);
	}

	@Override
	public int getC_Order_Cost_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Order_Cost_ID);
	}

	@Override
	public org.compiere.model.I_C_Order getC_Order()
	{
		return get_ValueAsPO(COLUMNNAME_C_Order_ID, org.compiere.model.I_C_Order.class);
	}

	@Override
	public void setC_Order(final org.compiere.model.I_C_Order C_Order)
	{
		set_ValueFromPO(COLUMNNAME_C_Order_ID, org.compiere.model.I_C_Order.class, C_Order);
	}

	@Override
	public void setC_Order_ID (final int C_Order_ID)
	{
		if (C_Order_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Order_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Order_ID, C_Order_ID);
	}

	@Override
	public int getC_Order_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Order_ID);
	}

	@Override
	public void setCostAmount (final BigDecimal CostAmount)
	{
		set_Value (COLUMNNAME_CostAmount, CostAmount);
	}

	@Override
	public BigDecimal getCostAmount() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_CostAmount);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setCostCalculation_FixedAmount (final @Nullable BigDecimal CostCalculation_FixedAmount)
	{
		set_Value (COLUMNNAME_CostCalculation_FixedAmount, CostCalculation_FixedAmount);
	}

	@Override
	public BigDecimal getCostCalculation_FixedAmount() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_CostCalculation_FixedAmount);
		return bd != null ? bd : BigDecimal.ZERO;
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

	@Override
	public void setCostCalculation_Percentage (final @Nullable BigDecimal CostCalculation_Percentage)
	{
		set_Value (COLUMNNAME_CostCalculation_Percentage, CostCalculation_Percentage);
	}

	@Override
	public BigDecimal getCostCalculation_Percentage() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_CostCalculation_Percentage);
		return bd != null ? bd : BigDecimal.ZERO;
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
	public org.compiere.model.I_C_OrderLine getCreated_OrderLine()
	{
		return get_ValueAsPO(COLUMNNAME_Created_OrderLine_ID, org.compiere.model.I_C_OrderLine.class);
	}

	@Override
	public void setCreated_OrderLine(final org.compiere.model.I_C_OrderLine Created_OrderLine)
	{
		set_ValueFromPO(COLUMNNAME_Created_OrderLine_ID, org.compiere.model.I_C_OrderLine.class, Created_OrderLine);
	}

	@Override
	public void setCreated_OrderLine_ID (final int Created_OrderLine_ID)
	{
		if (Created_OrderLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Created_OrderLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Created_OrderLine_ID, Created_OrderLine_ID);
	}

	@Override
	public int getCreated_OrderLine_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Created_OrderLine_ID);
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
	public org.compiere.model.I_M_CostElement getM_CostElement()
	{
		return get_ValueAsPO(COLUMNNAME_M_CostElement_ID, org.compiere.model.I_M_CostElement.class);
	}

	@Override
	public void setM_CostElement(final org.compiere.model.I_M_CostElement M_CostElement)
	{
		set_ValueFromPO(COLUMNNAME_M_CostElement_ID, org.compiere.model.I_M_CostElement.class, M_CostElement);
	}

	@Override
	public void setM_CostElement_ID (final int M_CostElement_ID)
	{
		if (M_CostElement_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_CostElement_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_CostElement_ID, M_CostElement_ID);
	}

	@Override
	public int getM_CostElement_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_CostElement_ID);
	}
}