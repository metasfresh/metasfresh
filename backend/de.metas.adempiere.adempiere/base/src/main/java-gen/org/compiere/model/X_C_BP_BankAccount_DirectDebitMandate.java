// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for C_BP_BankAccount_DirectDebitMandate
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_BP_BankAccount_DirectDebitMandate extends org.compiere.model.PO implements I_C_BP_BankAccount_DirectDebitMandate, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1976974232L;

    /** Standard Constructor */
    public X_C_BP_BankAccount_DirectDebitMandate (final Properties ctx, final int C_BP_BankAccount_DirectDebitMandate_ID, @Nullable final String trxName)
    {
      super (ctx, C_BP_BankAccount_DirectDebitMandate_ID, trxName);
    }

    /** Load Constructor */
    public X_C_BP_BankAccount_DirectDebitMandate (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setC_BP_BankAccount_DirectDebitMandate_ID (final int C_BP_BankAccount_DirectDebitMandate_ID)
	{
		if (C_BP_BankAccount_DirectDebitMandate_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BP_BankAccount_DirectDebitMandate_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BP_BankAccount_DirectDebitMandate_ID, C_BP_BankAccount_DirectDebitMandate_ID);
	}

	@Override
	public int getC_BP_BankAccount_DirectDebitMandate_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BP_BankAccount_DirectDebitMandate_ID);
	}

	@Override
	public void setC_BP_BankAccount_ID (final int C_BP_BankAccount_ID)
	{
		if (C_BP_BankAccount_ID < 1) 
			set_Value (COLUMNNAME_C_BP_BankAccount_ID, null);
		else 
			set_Value (COLUMNNAME_C_BP_BankAccount_ID, C_BP_BankAccount_ID);
	}

	@Override
	public int getC_BP_BankAccount_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BP_BankAccount_ID);
	}

	@Override
	public void setDateLastUsed (final @Nullable java.sql.Timestamp DateLastUsed)
	{
		set_Value (COLUMNNAME_DateLastUsed, DateLastUsed);
	}

	@Override
	public java.sql.Timestamp getDateLastUsed() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DateLastUsed);
	}

	@Override
	public void setIsRecurring (final boolean IsRecurring)
	{
		set_Value (COLUMNNAME_IsRecurring, IsRecurring);
	}

	@Override
	public boolean isRecurring() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsRecurring);
	}

	@Override
	public void setMandateDate (final java.sql.Timestamp MandateDate)
	{
		set_Value (COLUMNNAME_MandateDate, MandateDate);
	}

	@Override
	public java.sql.Timestamp getMandateDate() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_MandateDate);
	}

	@Override
	public void setMandateReference (final java.lang.String MandateReference)
	{
		set_Value (COLUMNNAME_MandateReference, MandateReference);
	}

	@Override
	public java.lang.String getMandateReference() 
	{
		return get_ValueAsString(COLUMNNAME_MandateReference);
	}

	/** 
	 * MandateStatus AD_Reference_ID=541976
	 * Reference name: Mandat Status
	 */
	public static final int MANDATESTATUS_AD_Reference_ID=541976;
	/** FirstTime = F */
	public static final String MANDATESTATUS_FirstTime = "F";
	/** Recurring = R */
	public static final String MANDATESTATUS_Recurring = "R";
	/** LastTime = L */
	public static final String MANDATESTATUS_LastTime = "L";
	@Override
	public void setMandateStatus (final java.lang.String MandateStatus)
	{
		set_Value (COLUMNNAME_MandateStatus, MandateStatus);
	}

	@Override
	public java.lang.String getMandateStatus() 
	{
		return get_ValueAsString(COLUMNNAME_MandateStatus);
	}
}