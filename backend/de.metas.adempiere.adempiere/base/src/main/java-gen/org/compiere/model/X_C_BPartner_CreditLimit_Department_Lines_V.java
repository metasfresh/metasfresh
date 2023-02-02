// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for C_BPartner_CreditLimit_Department_Lines_V
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_BPartner_CreditLimit_Department_Lines_V extends org.compiere.model.PO implements I_C_BPartner_CreditLimit_Department_Lines_V, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1985266560L;

    /** Standard Constructor */
    public X_C_BPartner_CreditLimit_Department_Lines_V (final Properties ctx, final int C_BPartner_CreditLimit_Department_Lines_V_ID, @Nullable final String trxName)
    {
      super (ctx, C_BPartner_CreditLimit_Department_Lines_V_ID, trxName);
    }

    /** Load Constructor */
    public X_C_BPartner_CreditLimit_Department_Lines_V (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setAmount (final @Nullable BigDecimal Amount)
	{
		set_Value (COLUMNNAME_Amount, Amount);
	}

	@Override
	public BigDecimal getAmount() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_Amount);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setApprovedBy_ID (final int ApprovedBy_ID)
	{
		if (ApprovedBy_ID < 1) 
			set_Value (COLUMNNAME_ApprovedBy_ID, null);
		else 
			set_Value (COLUMNNAME_ApprovedBy_ID, ApprovedBy_ID);
	}

	@Override
	public int getApprovedBy_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_ApprovedBy_ID);
	}

	@Override
	public void setC_BPartner_CreditLimit_Department_Lines_V_ID (final int C_BPartner_CreditLimit_Department_Lines_V_ID)
	{
		if (C_BPartner_CreditLimit_Department_Lines_V_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_CreditLimit_Department_Lines_V_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_CreditLimit_Department_Lines_V_ID, C_BPartner_CreditLimit_Department_Lines_V_ID);
	}

	@Override
	public int getC_BPartner_CreditLimit_Department_Lines_V_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_CreditLimit_Department_Lines_V_ID);
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
	public org.compiere.model.I_C_CreditLimit_Type getC_CreditLimit_Type()
	{
		return get_ValueAsPO(COLUMNNAME_C_CreditLimit_Type_ID, org.compiere.model.I_C_CreditLimit_Type.class);
	}

	@Override
	public void setC_CreditLimit_Type(final org.compiere.model.I_C_CreditLimit_Type C_CreditLimit_Type)
	{
		set_ValueFromPO(COLUMNNAME_C_CreditLimit_Type_ID, org.compiere.model.I_C_CreditLimit_Type.class, C_CreditLimit_Type);
	}

	@Override
	public void setC_CreditLimit_Type_ID (final int C_CreditLimit_Type_ID)
	{
		if (C_CreditLimit_Type_ID < 1) 
			set_Value (COLUMNNAME_C_CreditLimit_Type_ID, null);
		else 
			set_Value (COLUMNNAME_C_CreditLimit_Type_ID, C_CreditLimit_Type_ID);
	}

	@Override
	public int getC_CreditLimit_Type_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_CreditLimit_Type_ID);
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
	public void setDateFrom (final @Nullable java.sql.Timestamp DateFrom)
	{
		set_Value (COLUMNNAME_DateFrom, DateFrom);
	}

	@Override
	public java.sql.Timestamp getDateFrom() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DateFrom);
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
			set_Value (COLUMNNAME_M_Department_ID, null);
		else 
			set_Value (COLUMNNAME_M_Department_ID, M_Department_ID);
	}

	@Override
	public int getM_Department_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Department_ID);
	}

	@Override
	public void setProcessed (final boolean Processed)
	{
		set_Value (COLUMNNAME_Processed, Processed);
	}

	@Override
	public boolean isProcessed() 
	{
		return get_ValueAsBoolean(COLUMNNAME_Processed);
	}

	@Override
	public void setSection_Group_Partner_ID (final int Section_Group_Partner_ID)
	{
		if (Section_Group_Partner_ID < 1) 
			set_Value (COLUMNNAME_Section_Group_Partner_ID, null);
		else 
			set_Value (COLUMNNAME_Section_Group_Partner_ID, Section_Group_Partner_ID);
	}

	@Override
	public int getSection_Group_Partner_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Section_Group_Partner_ID);
	}
}