// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for C_Customer_Retention
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_Customer_Retention extends org.compiere.model.PO implements I_C_Customer_Retention, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -427290870L;

    /** Standard Constructor */
    public X_C_Customer_Retention (final Properties ctx, final int C_Customer_Retention_ID, @Nullable final String trxName)
    {
      super (ctx, C_Customer_Retention_ID, trxName);
    }

    /** Load Constructor */
    public X_C_Customer_Retention (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setC_Customer_Retention_ID (final int C_Customer_Retention_ID)
	{
		if (C_Customer_Retention_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Customer_Retention_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Customer_Retention_ID, C_Customer_Retention_ID);
	}

	@Override
	public int getC_Customer_Retention_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Customer_Retention_ID);
	}

	/** 
	 * CustomerRetention AD_Reference_ID=540937
	 * Reference name: C_BPartner_TimeSpan_List
	 */
	public static final int CUSTOMERRETENTION_AD_Reference_ID=540937;
	/** Neukunde = N */
	public static final String CUSTOMERRETENTION_Neukunde = "N";
	/** Stammkunde = S */
	public static final String CUSTOMERRETENTION_Stammkunde = "S";
	@Override
	public void setCustomerRetention (final @Nullable java.lang.String CustomerRetention)
	{
		set_Value (COLUMNNAME_CustomerRetention, CustomerRetention);
	}

	@Override
	public java.lang.String getCustomerRetention() 
	{
		return get_ValueAsString(COLUMNNAME_CustomerRetention);
	}
}