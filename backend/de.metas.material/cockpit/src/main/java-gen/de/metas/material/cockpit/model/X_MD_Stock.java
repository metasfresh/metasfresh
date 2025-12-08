// Generated Model - DO NOT CHANGE
package de.metas.material.cockpit.model;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for MD_Stock
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_MD_Stock extends org.compiere.model.PO implements I_MD_Stock, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1875304086L;

    /** Standard Constructor */
    public X_MD_Stock (final Properties ctx, final int MD_Stock_ID, @Nullable final String trxName)
    {
      super (ctx, MD_Stock_ID, trxName);
    }

    /** Load Constructor */
    public X_MD_Stock (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setAttributesKey (final java.lang.String AttributesKey)
	{
		set_Value (COLUMNNAME_AttributesKey, AttributesKey);
	}

	@Override
	public java.lang.String getAttributesKey() 
	{
		return get_ValueAsString(COLUMNNAME_AttributesKey);
	}

	@Override
	public void setMD_Stock_ID (final int MD_Stock_ID)
	{
		if (MD_Stock_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_MD_Stock_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_MD_Stock_ID, MD_Stock_ID);
	}

	@Override
	public int getMD_Stock_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_MD_Stock_ID);
	}

	@Override
	public void setM_Product_ID (final int M_Product_ID)
	{
		if (M_Product_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Product_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Product_ID, M_Product_ID);
	}

	@Override
	public int getM_Product_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Product_ID);
	}

	@Override
	public void setM_Warehouse_ID (final int M_Warehouse_ID)
	{
		if (M_Warehouse_ID < 1) 
			set_Value (COLUMNNAME_M_Warehouse_ID, null);
		else 
			set_Value (COLUMNNAME_M_Warehouse_ID, M_Warehouse_ID);
	}

	@Override
	public int getM_Warehouse_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Warehouse_ID);
	}

	@Override
	public void setQtyOnHand (final @Nullable BigDecimal QtyOnHand)
	{
		set_Value (COLUMNNAME_QtyOnHand, QtyOnHand);
	}

	@Override
	public BigDecimal getQtyOnHand() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyOnHand);
		return bd != null ? bd : BigDecimal.ZERO;
	}
}