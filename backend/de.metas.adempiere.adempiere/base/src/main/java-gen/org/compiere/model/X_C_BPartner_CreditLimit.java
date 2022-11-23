// Generated Model - DO NOT CHANGE
package org.compiere.model;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_BPartner_CreditLimit
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_BPartner_CreditLimit extends org.compiere.model.PO implements I_C_BPartner_CreditLimit, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -818255154L;

    /** Standard Constructor */
    public X_C_BPartner_CreditLimit (final Properties ctx, final int C_BPartner_CreditLimit_ID, @Nullable final String trxName)
    {
      super (ctx, C_BPartner_CreditLimit_ID, trxName);
    }

    /** Load Constructor */
    public X_C_BPartner_CreditLimit (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public org.compiere.model.I_AD_Org_Mapping getAD_Org_Mapping()
	{
		return get_ValueAsPO(COLUMNNAME_AD_Org_Mapping_ID, org.compiere.model.I_AD_Org_Mapping.class);
	}

	@Override
	public void setAD_Org_Mapping(final org.compiere.model.I_AD_Org_Mapping AD_Org_Mapping)
	{
		set_ValueFromPO(COLUMNNAME_AD_Org_Mapping_ID, org.compiere.model.I_AD_Org_Mapping.class, AD_Org_Mapping);
	}

	@Override
	public void setAD_Org_Mapping_ID (final int AD_Org_Mapping_ID)
	{
		if (AD_Org_Mapping_ID < 1) 
			set_Value (COLUMNNAME_AD_Org_Mapping_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Org_Mapping_ID, AD_Org_Mapping_ID);
	}

	@Override
	public int getAD_Org_Mapping_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Org_Mapping_ID);
	}

	@Override
	public void setAmount (final BigDecimal Amount)
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
	public void setC_BPartner_CreditLimit_ID (final int C_BPartner_CreditLimit_ID)
	{
		if (C_BPartner_CreditLimit_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_CreditLimit_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_CreditLimit_ID, C_BPartner_CreditLimit_ID);
	}

	@Override
	public int getC_BPartner_CreditLimit_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_CreditLimit_ID);
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
		throw new IllegalArgumentException ("C_Currency_ID is virtual column");	}

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
	public void setProcessed (final boolean Processed)
	{
		set_Value (COLUMNNAME_Processed, Processed);
	}

	@Override
	public boolean isProcessed() 
	{
		return get_ValueAsBoolean(COLUMNNAME_Processed);
	}
}