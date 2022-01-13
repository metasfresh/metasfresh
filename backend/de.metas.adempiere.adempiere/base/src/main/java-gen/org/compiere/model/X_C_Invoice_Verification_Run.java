// Generated Model - DO NOT CHANGE
package org.compiere.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_Invoice_Verification_Run
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_Invoice_Verification_Run extends org.compiere.model.PO implements I_C_Invoice_Verification_Run, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 2075598591L;

    /** Standard Constructor */
    public X_C_Invoice_Verification_Run (final Properties ctx, final int C_Invoice_Verification_Run_ID, @Nullable final String trxName)
    {
      super (ctx, C_Invoice_Verification_Run_ID, trxName);
    }

    /** Load Constructor */
    public X_C_Invoice_Verification_Run (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public org.compiere.model.I_AD_PInstance getAD_PInstance()
	{
		return get_ValueAsPO(COLUMNNAME_AD_PInstance_ID, org.compiere.model.I_AD_PInstance.class);
	}

	@Override
	public void setAD_PInstance(final org.compiere.model.I_AD_PInstance AD_PInstance)
	{
		set_ValueFromPO(COLUMNNAME_AD_PInstance_ID, org.compiere.model.I_AD_PInstance.class, AD_PInstance);
	}

	@Override
	public void setAD_PInstance_ID (final int AD_PInstance_ID)
	{
		if (AD_PInstance_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_PInstance_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_PInstance_ID, AD_PInstance_ID);
	}

	@Override
	public int getAD_PInstance_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_PInstance_ID);
	}

	@Override
	public void setC_Invoice_Verification_Run_ID (final int C_Invoice_Verification_Run_ID)
	{
		if (C_Invoice_Verification_Run_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Invoice_Verification_Run_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Invoice_Verification_Run_ID, C_Invoice_Verification_Run_ID);
	}

	@Override
	public int getC_Invoice_Verification_Run_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Invoice_Verification_Run_ID);
	}

	@Override
	public org.compiere.model.I_C_Invoice_Verification_Set getC_Invoice_Verification_Set()
	{
		return get_ValueAsPO(COLUMNNAME_C_Invoice_Verification_Set_ID, org.compiere.model.I_C_Invoice_Verification_Set.class);
	}

	@Override
	public void setC_Invoice_Verification_Set(final org.compiere.model.I_C_Invoice_Verification_Set C_Invoice_Verification_Set)
	{
		set_ValueFromPO(COLUMNNAME_C_Invoice_Verification_Set_ID, org.compiere.model.I_C_Invoice_Verification_Set.class, C_Invoice_Verification_Set);
	}

	@Override
	public void setC_Invoice_Verification_Set_ID (final int C_Invoice_Verification_Set_ID)
	{
		if (C_Invoice_Verification_Set_ID < 1) 
			set_Value (COLUMNNAME_C_Invoice_Verification_Set_ID, null);
		else 
			set_Value (COLUMNNAME_C_Invoice_Verification_Set_ID, C_Invoice_Verification_Set_ID);
	}

	@Override
	public int getC_Invoice_Verification_Set_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Invoice_Verification_Set_ID);
	}

	@Override
	public void setDateEnd (final @Nullable java.sql.Timestamp DateEnd)
	{
		set_ValueNoCheck (COLUMNNAME_DateEnd, DateEnd);
	}

	@Override
	public java.sql.Timestamp getDateEnd() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DateEnd);
	}

	@Override
	public void setDateStart (final @Nullable java.sql.Timestamp DateStart)
	{
		set_ValueNoCheck (COLUMNNAME_DateStart, DateStart);
	}

	@Override
	public java.sql.Timestamp getDateStart() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DateStart);
	}

	@Override
	public void setMovementDate_Override (final @Nullable java.sql.Timestamp MovementDate_Override)
	{
		set_Value (COLUMNNAME_MovementDate_Override, MovementDate_Override);
	}

	@Override
	public java.sql.Timestamp getMovementDate_Override() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_MovementDate_Override);
	}

	@Override
	public void setNote (final @Nullable java.lang.String Note)
	{
		set_Value (COLUMNNAME_Note, Note);
	}

	@Override
	public java.lang.String getNote() 
	{
		return get_ValueAsString(COLUMNNAME_Note);
	}

	/** 
	 * Status AD_Reference_ID=541324
	 * Reference name: Invoice Verification Run Status
	 */
	public static final int STATUS_AD_Reference_ID=541324;
	/** Planned = P */
	public static final String STATUS_Planned = "P";
	/** Running = R */
	public static final String STATUS_Running = "R";
	/** Finished = F */
	public static final String STATUS_Finished = "F";
	@Override
	public void setStatus (final java.lang.String Status)
	{
		set_ValueNoCheck (COLUMNNAME_Status, Status);
	}

	@Override
	public java.lang.String getStatus() 
	{
		return get_ValueAsString(COLUMNNAME_Status);
	}
}