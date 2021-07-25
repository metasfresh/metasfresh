// Generated Model - DO NOT CHANGE
package org.compiere.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_BP_SupplierApproval
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_BP_SupplierApproval extends org.compiere.model.PO implements I_C_BP_SupplierApproval, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -539082694L;

    /** Standard Constructor */
    public X_C_BP_SupplierApproval (final Properties ctx, final int C_BP_SupplierApproval_ID, @Nullable final String trxName)
    {
      super (ctx, C_BP_SupplierApproval_ID, trxName);
    }

    /** Load Constructor */
    public X_C_BP_SupplierApproval (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setC_BP_SupplierApproval_ID (final int C_BP_SupplierApproval_ID)
	{
		if (C_BP_SupplierApproval_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BP_SupplierApproval_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BP_SupplierApproval_ID, C_BP_SupplierApproval_ID);
	}

	@Override
	public int getC_BP_SupplierApproval_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BP_SupplierApproval_ID);
	}

	/** 
	 * SupplierApproval AD_Reference_ID=541362
	 * Reference name: SupplierApproval
	 */
	public static final int SUPPLIERAPPROVAL_AD_Reference_ID=541362;
	/** 3 years = A */
	public static final String SUPPLIERAPPROVAL_3Years = "A";
	/** 2 years = B */
	public static final String SUPPLIERAPPROVAL_2Years = "B";
	/** 1 year = C */
	public static final String SUPPLIERAPPROVAL_1Year = "C";
	@Override
	public void setSupplierApproval (final @Nullable java.lang.String SupplierApproval)
	{
		set_ValueNoCheck (COLUMNNAME_SupplierApproval, SupplierApproval);
	}

	@Override
	public java.lang.String getSupplierApproval() 
	{
		return get_ValueAsString(COLUMNNAME_SupplierApproval);
	}

	@Override
	public void setSupplierApproval_Date (final @Nullable java.sql.Timestamp SupplierApproval_Date)
	{
		set_ValueNoCheck (COLUMNNAME_SupplierApproval_Date, SupplierApproval_Date);
	}

	@Override
	public java.sql.Timestamp getSupplierApproval_Date() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_SupplierApproval_Date);
	}

	/** 
	 * SupplierApproval_Norm AD_Reference_ID=541363
	 * Reference name: SupplierApproval_Norm
	 */
	public static final int SUPPLIERAPPROVAL_NORM_AD_Reference_ID=541363;
	/** ISO 9100 Luftfahrt = ISO9100 */
	public static final String SUPPLIERAPPROVAL_NORM_ISO9100Luftfahrt = "ISO9100";
	/** TS 16949 = TS16949 */
	public static final String SUPPLIERAPPROVAL_NORM_TS16949 = "TS16949";
	@Override
	public void setSupplierApproval_Norm (final java.lang.String SupplierApproval_Norm)
	{
		set_Value (COLUMNNAME_SupplierApproval_Norm, SupplierApproval_Norm);
	}

	@Override
	public java.lang.String getSupplierApproval_Norm() 
	{
		return get_ValueAsString(COLUMNNAME_SupplierApproval_Norm);
	}

	/** 
	 * SupplierApproval_Type AD_Reference_ID=541361
	 * Reference name: Supplier Approval Type
	 */
	public static final int SUPPLIERAPPROVAL_TYPE_AD_Reference_ID=541361;
	/** Customer = C */
	public static final String SUPPLIERAPPROVAL_TYPE_Customer = "C";
	/** Vendor = V */
	public static final String SUPPLIERAPPROVAL_TYPE_Vendor = "V";
	@Override
	public void setSupplierApproval_Type (final @Nullable java.lang.String SupplierApproval_Type)
	{
		set_ValueNoCheck (COLUMNNAME_SupplierApproval_Type, SupplierApproval_Type);
	}

	@Override
	public java.lang.String getSupplierApproval_Type() 
	{
		return get_ValueAsString(COLUMNNAME_SupplierApproval_Type);
	}
}