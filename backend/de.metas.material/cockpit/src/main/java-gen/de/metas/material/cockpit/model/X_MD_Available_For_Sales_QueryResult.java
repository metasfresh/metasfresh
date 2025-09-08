// Generated Model - DO NOT CHANGE
package de.metas.material.cockpit.model;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for MD_Available_For_Sales_QueryResult
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_MD_Available_For_Sales_QueryResult extends org.compiere.model.PO implements I_MD_Available_For_Sales_QueryResult, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 658342584L;

    /** Standard Constructor */
    public X_MD_Available_For_Sales_QueryResult (final Properties ctx, final int MD_Available_For_Sales_QueryResult_ID, @Nullable final String trxName)
    {
      super (ctx, MD_Available_For_Sales_QueryResult_ID, trxName);
    }

    /** Load Constructor */
    public X_MD_Available_For_Sales_QueryResult (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setC_UOM_ID (final int C_UOM_ID)
	{
		if (C_UOM_ID < 1) 
			set_Value (COLUMNNAME_C_UOM_ID, null);
		else 
			set_Value (COLUMNNAME_C_UOM_ID, C_UOM_ID);
	}

	@Override
	public int getC_UOM_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_UOM_ID);
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
	public void setQtyOnHandStock (final @Nullable BigDecimal QtyOnHandStock)
	{
		set_Value (COLUMNNAME_QtyOnHandStock, QtyOnHandStock);
	}

	@Override
	public BigDecimal getQtyOnHandStock() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyOnHandStock);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyToBeShipped (final @Nullable BigDecimal QtyToBeShipped)
	{
		set_Value (COLUMNNAME_QtyToBeShipped, QtyToBeShipped);
	}

	@Override
	public BigDecimal getQtyToBeShipped() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyToBeShipped);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQueryNo (final int QueryNo)
	{
		set_Value (COLUMNNAME_QueryNo, QueryNo);
	}

	@Override
	public int getQueryNo() 
	{
		return get_ValueAsInt(COLUMNNAME_QueryNo);
	}

	@Override
	public void setStorageAttributesKey (final @Nullable java.lang.String StorageAttributesKey)
	{
		set_Value (COLUMNNAME_StorageAttributesKey, StorageAttributesKey);
	}

	@Override
	public java.lang.String getStorageAttributesKey() 
	{
		return get_ValueAsString(COLUMNNAME_StorageAttributesKey);
	}
}