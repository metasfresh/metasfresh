// Generated Model - DO NOT CHANGE
package de.metas.invoicecandidate.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for M_ProductGroup
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_M_ProductGroup extends org.compiere.model.PO implements I_M_ProductGroup, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1245339163L;

    /** Standard Constructor */
    public X_M_ProductGroup (final Properties ctx, final int M_ProductGroup_ID, @Nullable final String trxName)
    {
      super (ctx, M_ProductGroup_ID, trxName);
    }

    /** Load Constructor */
    public X_M_ProductGroup (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setM_Product_Proxy_ID (final int M_Product_Proxy_ID)
	{
		if (M_Product_Proxy_ID < 1) 
			set_Value (COLUMNNAME_M_Product_Proxy_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_Proxy_ID, M_Product_Proxy_ID);
	}

	@Override
	public int getM_Product_Proxy_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Product_Proxy_ID);
	}

	@Override
	public void setM_ProductGroup_ID (final int M_ProductGroup_ID)
	{
		if (M_ProductGroup_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_ProductGroup_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_ProductGroup_ID, M_ProductGroup_ID);
	}

	@Override
	public int getM_ProductGroup_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_ProductGroup_ID);
	}

	@Override
	public void setName (final java.lang.String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	@Override
	public java.lang.String getName() 
	{
		return get_ValueAsString(COLUMNNAME_Name);
	}
}