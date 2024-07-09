// Generated Model - DO NOT CHANGE
package org.compiere.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for PostFinance_Org_Config
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_PostFinance_Org_Config extends org.compiere.model.PO implements I_PostFinance_Org_Config, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 61763720L;

    /** Standard Constructor */
    public X_PostFinance_Org_Config (final Properties ctx, final int PostFinance_Org_Config_ID, @Nullable final String trxName)
    {
      super (ctx, PostFinance_Org_Config_ID, trxName);
    }

    /** Load Constructor */
    public X_PostFinance_Org_Config (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setIsArchiveData (final boolean IsArchiveData)
	{
		set_Value (COLUMNNAME_IsArchiveData, IsArchiveData);
	}

	@Override
	public boolean isArchiveData() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsArchiveData);
	}

	@Override
	public void setIsUsePaperBill (final boolean IsUsePaperBill)
	{
		set_Value (COLUMNNAME_IsUsePaperBill, IsUsePaperBill);
	}

	@Override
	public boolean isUsePaperBill() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsUsePaperBill);
	}

	@Override
	public void setPostFinance_Org_Config_ID (final int PostFinance_Org_Config_ID)
	{
		if (PostFinance_Org_Config_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_PostFinance_Org_Config_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_PostFinance_Org_Config_ID, PostFinance_Org_Config_ID);
	}

	@Override
	public int getPostFinance_Org_Config_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_PostFinance_Org_Config_ID);
	}

	@Override
	public void setPostFinance_Sender_BillerId (final java.lang.String PostFinance_Sender_BillerId)
	{
		set_Value (COLUMNNAME_PostFinance_Sender_BillerId, PostFinance_Sender_BillerId);
	}

	@Override
	public java.lang.String getPostFinance_Sender_BillerId() 
	{
		return get_ValueAsString(COLUMNNAME_PostFinance_Sender_BillerId);
	}
}