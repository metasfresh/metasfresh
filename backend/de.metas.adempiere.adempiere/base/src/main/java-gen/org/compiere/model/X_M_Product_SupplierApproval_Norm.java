// Generated Model - DO NOT CHANGE
package org.compiere.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for M_Product_SupplierApproval_Norm
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_M_Product_SupplierApproval_Norm extends org.compiere.model.PO implements I_M_Product_SupplierApproval_Norm, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 1977517179L;

    /** Standard Constructor */
    public X_M_Product_SupplierApproval_Norm (final Properties ctx, final int M_Product_SupplierApproval_Norm_ID, @Nullable final String trxName)
    {
      super (ctx, M_Product_SupplierApproval_Norm_ID, trxName);
    }

    /** Load Constructor */
    public X_M_Product_SupplierApproval_Norm (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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

	@Override
	public void setM_Product_SupplierApproval_Norm_ID (final int M_Product_SupplierApproval_Norm_ID)
	{
		if (M_Product_SupplierApproval_Norm_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Product_SupplierApproval_Norm_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Product_SupplierApproval_Norm_ID, M_Product_SupplierApproval_Norm_ID);
	}

	@Override
	public int getM_Product_SupplierApproval_Norm_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Product_SupplierApproval_Norm_ID);
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
}