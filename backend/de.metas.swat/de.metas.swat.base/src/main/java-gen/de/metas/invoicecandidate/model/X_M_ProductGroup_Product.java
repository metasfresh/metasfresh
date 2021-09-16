// Generated Model - DO NOT CHANGE
package de.metas.invoicecandidate.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for M_ProductGroup_Product
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_M_ProductGroup_Product extends org.compiere.model.PO implements I_M_ProductGroup_Product, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -2013704372L;

    /** Standard Constructor */
    public X_M_ProductGroup_Product (final Properties ctx, final int M_ProductGroup_Product_ID, @Nullable final String trxName)
    {
      super (ctx, M_ProductGroup_Product_ID, trxName);
    }

    /** Load Constructor */
    public X_M_ProductGroup_Product (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setM_Product_Category_ID (final int M_Product_Category_ID)
	{
		if (M_Product_Category_ID < 1) 
			set_Value (COLUMNNAME_M_Product_Category_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_Category_ID, M_Product_Category_ID);
	}

	@Override
	public int getM_Product_Category_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Product_Category_ID);
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
	public de.metas.invoicecandidate.model.I_M_ProductGroup getM_ProductGroup()
	{
		return get_ValueAsPO(COLUMNNAME_M_ProductGroup_ID, de.metas.invoicecandidate.model.I_M_ProductGroup.class);
	}

	@Override
	public void setM_ProductGroup(final de.metas.invoicecandidate.model.I_M_ProductGroup M_ProductGroup)
	{
		set_ValueFromPO(COLUMNNAME_M_ProductGroup_ID, de.metas.invoicecandidate.model.I_M_ProductGroup.class, M_ProductGroup);
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
	public void setM_ProductGroup_Product_ID (final int M_ProductGroup_Product_ID)
	{
		if (M_ProductGroup_Product_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_ProductGroup_Product_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_ProductGroup_Product_ID, M_ProductGroup_Product_ID);
	}

	@Override
	public int getM_ProductGroup_Product_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_ProductGroup_Product_ID);
	}
}