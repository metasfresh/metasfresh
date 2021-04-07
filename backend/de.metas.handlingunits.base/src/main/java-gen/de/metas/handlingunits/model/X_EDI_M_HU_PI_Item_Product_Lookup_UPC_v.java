// Generated Model - DO NOT CHANGE
package de.metas.handlingunits.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for EDI_M_HU_PI_Item_Product_Lookup_UPC_v
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_EDI_M_HU_PI_Item_Product_Lookup_UPC_v extends org.compiere.model.PO implements I_EDI_M_HU_PI_Item_Product_Lookup_UPC_v, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 1809833829L;

    /** Standard Constructor */
    public X_EDI_M_HU_PI_Item_Product_Lookup_UPC_v (final Properties ctx, final int EDI_M_HU_PI_Item_Product_Lookup_UPC_v_ID, @Nullable final String trxName)
    {
      super (ctx, EDI_M_HU_PI_Item_Product_Lookup_UPC_v_ID, trxName);
    }

    /** Load Constructor */
    public X_EDI_M_HU_PI_Item_Product_Lookup_UPC_v (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setM_HU_PI_Item_Product_ID (final int M_HU_PI_Item_Product_ID)
	{
		if (M_HU_PI_Item_Product_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_HU_PI_Item_Product_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_HU_PI_Item_Product_ID, M_HU_PI_Item_Product_ID);
	}

	@Override
	public int getM_HU_PI_Item_Product_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_HU_PI_Item_Product_ID);
	}

	@Override
	public void setUPC (final @Nullable java.lang.String UPC)
	{
		set_Value (COLUMNNAME_UPC, UPC);
	}

	@Override
	public java.lang.String getUPC() 
	{
		return get_ValueAsString(COLUMNNAME_UPC);
	}
}