// Generated Model - DO NOT CHANGE
package org.compiere.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for AD_OrgChange_History
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_AD_OrgChange_History extends org.compiere.model.PO implements I_AD_OrgChange_History, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 1044968022L;

    /** Standard Constructor */
    public X_AD_OrgChange_History (final Properties ctx, final int AD_OrgChange_History_ID, @Nullable final String trxName)
    {
      super (ctx, AD_OrgChange_History_ID, trxName);
    }

    /** Load Constructor */
    public X_AD_OrgChange_History (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setAD_OrgChange_History_ID (final int AD_OrgChange_History_ID)
	{
		if (AD_OrgChange_History_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_OrgChange_History_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_OrgChange_History_ID, AD_OrgChange_History_ID);
	}

	@Override
	public int getAD_OrgChange_History_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_OrgChange_History_ID);
	}

	@Override
	public void setAD_Org_From_ID (final int AD_Org_From_ID)
	{
		if (AD_Org_From_ID < 1) 
			set_Value (COLUMNNAME_AD_Org_From_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Org_From_ID, AD_Org_From_ID);
	}

	@Override
	public int getAD_Org_From_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Org_From_ID);
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
	public void setAD_OrgTo_ID (final int AD_OrgTo_ID)
	{
		if (AD_OrgTo_ID < 1) 
			set_Value (COLUMNNAME_AD_OrgTo_ID, null);
		else 
			set_Value (COLUMNNAME_AD_OrgTo_ID, AD_OrgTo_ID);
	}

	@Override
	public int getAD_OrgTo_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_OrgTo_ID);
	}

	@Override
	public void setC_BPartner_From_ID (final int C_BPartner_From_ID)
	{
		if (C_BPartner_From_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_From_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_From_ID, C_BPartner_From_ID);
	}

	@Override
	public int getC_BPartner_From_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_From_ID);
	}

	@Override
	public void setC_BPartner_To_ID (final int C_BPartner_To_ID)
	{
		if (C_BPartner_To_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_To_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_To_ID, C_BPartner_To_ID);
	}

	@Override
	public int getC_BPartner_To_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_To_ID);
	}

	@Override
	public void setDate_OrgChange (final java.sql.Timestamp Date_OrgChange)
	{
		set_Value (COLUMNNAME_Date_OrgChange, Date_OrgChange);
	}

	@Override
	public java.sql.Timestamp getDate_OrgChange() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_Date_OrgChange);
	}

	@Override
	public void setIsCloseInvoiceCandidate (final boolean IsCloseInvoiceCandidate)
	{
		set_Value (COLUMNNAME_IsCloseInvoiceCandidate, IsCloseInvoiceCandidate);
	}

	@Override
	public boolean isCloseInvoiceCandidate() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsCloseInvoiceCandidate);
	}
}