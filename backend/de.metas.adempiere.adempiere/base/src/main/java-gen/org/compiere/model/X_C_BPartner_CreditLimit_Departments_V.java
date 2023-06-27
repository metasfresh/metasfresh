// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for C_BPartner_CreditLimit_Departments_V
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_BPartner_CreditLimit_Departments_V extends org.compiere.model.PO implements I_C_BPartner_CreditLimit_Departments_V, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1782766560L;

    /** Standard Constructor */
    public X_C_BPartner_CreditLimit_Departments_V (final Properties ctx, final int C_BPartner_CreditLimit_Departments_V_ID, @Nullable final String trxName)
    {
      super (ctx, C_BPartner_CreditLimit_Departments_V_ID, trxName);
    }

    /** Load Constructor */
    public X_C_BPartner_CreditLimit_Departments_V (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setC_BPartner_CreditLimit_Departments_v_ID (final int C_BPartner_CreditLimit_Departments_v_ID)
	{
		if (C_BPartner_CreditLimit_Departments_v_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_CreditLimit_Departments_v_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_CreditLimit_Departments_v_ID, C_BPartner_CreditLimit_Departments_v_ID);
	}

	@Override
	public int getC_BPartner_CreditLimit_Departments_v_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_CreditLimit_Departments_v_ID);
	}

	@Override
	public void setC_BPartner_ID (final int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, C_BPartner_ID);
	}

	@Override
	public int getC_BPartner_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_ID);
	}

	@Override
	public void setC_Currency_ID (final int C_Currency_ID)
	{
		if (C_Currency_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Currency_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Currency_ID, C_Currency_ID);
	}

	@Override
	public int getC_Currency_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Currency_ID);
	}

	@Override
	public void setCreditLimit (final @Nullable BigDecimal CreditLimit)
	{
		set_ValueNoCheck (COLUMNNAME_CreditLimit, CreditLimit);
	}

	@Override
	public BigDecimal getCreditLimit() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_CreditLimit);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setDelivery_CreditUsed (final @Nullable BigDecimal Delivery_CreditUsed)
	{
		set_ValueNoCheck (COLUMNNAME_Delivery_CreditUsed, Delivery_CreditUsed);
	}

	@Override
	public BigDecimal getDelivery_CreditUsed() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_Delivery_CreditUsed);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public org.compiere.model.I_M_Department getM_Department()
	{
		return get_ValueAsPO(COLUMNNAME_M_Department_ID, org.compiere.model.I_M_Department.class);
	}

	@Override
	public void setM_Department(final org.compiere.model.I_M_Department M_Department)
	{
		set_ValueFromPO(COLUMNNAME_M_Department_ID, org.compiere.model.I_M_Department.class, M_Department);
	}

	@Override
	public void setM_Department_ID (final int M_Department_ID)
	{
		if (M_Department_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Department_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Department_ID, M_Department_ID);
	}

	@Override
	public int getM_Department_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Department_ID);
	}

	@Override
	public void setM_Department_Order_OpenAmt (final @Nullable BigDecimal M_Department_Order_OpenAmt)
	{
		set_ValueNoCheck (COLUMNNAME_M_Department_Order_OpenAmt, M_Department_Order_OpenAmt);
	}

	@Override
	public BigDecimal getM_Department_Order_OpenAmt() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_M_Department_Order_OpenAmt);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public org.compiere.model.I_M_SectionCode getM_SectionCode()
	{
		return get_ValueAsPO(COLUMNNAME_M_SectionCode_ID, org.compiere.model.I_M_SectionCode.class);
	}

	@Override
	public void setM_SectionCode(final org.compiere.model.I_M_SectionCode M_SectionCode)
	{
		set_ValueFromPO(COLUMNNAME_M_SectionCode_ID, org.compiere.model.I_M_SectionCode.class, M_SectionCode);
	}

	@Override
	public void setM_SectionCode_ID (final int M_SectionCode_ID)
	{
		if (M_SectionCode_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_SectionCode_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_SectionCode_ID, M_SectionCode_ID);
	}

	@Override
	public int getM_SectionCode_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_SectionCode_ID);
	}

	@Override
	public void setOpenItems (final @Nullable BigDecimal OpenItems)
	{
		set_ValueNoCheck (COLUMNNAME_OpenItems, OpenItems);
	}

	@Override
	public BigDecimal getOpenItems() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_OpenItems);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setSection_Group_Partner_ID (final int Section_Group_Partner_ID)
	{
		if (Section_Group_Partner_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Section_Group_Partner_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Section_Group_Partner_ID, Section_Group_Partner_ID);
	}

	@Override
	public int getSection_Group_Partner_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Section_Group_Partner_ID);
	}

	@Override
	public void setSO_CreditUsed (final @Nullable BigDecimal SO_CreditUsed)
	{
		set_ValueNoCheck (COLUMNNAME_SO_CreditUsed, SO_CreditUsed);
	}

	@Override
	public BigDecimal getSO_CreditUsed() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_SO_CreditUsed);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setTotalOrderValue (final @Nullable BigDecimal TotalOrderValue)
	{
		set_ValueNoCheck (COLUMNNAME_TotalOrderValue, TotalOrderValue);
	}

	@Override
	public BigDecimal getTotalOrderValue() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_TotalOrderValue);
		return bd != null ? bd : BigDecimal.ZERO;
	}
}