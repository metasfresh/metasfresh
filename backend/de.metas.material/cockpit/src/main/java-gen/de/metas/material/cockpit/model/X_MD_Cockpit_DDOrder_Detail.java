// Generated Model - DO NOT CHANGE
package de.metas.material.cockpit.model;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for MD_Cockpit_DDOrder_Detail
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_MD_Cockpit_DDOrder_Detail extends org.compiere.model.PO implements I_MD_Cockpit_DDOrder_Detail, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1646878309L;

    /** Standard Constructor */
    public X_MD_Cockpit_DDOrder_Detail (final Properties ctx, final int MD_Cockpit_DDOrder_Detail_ID, @Nullable final String trxName)
    {
      super (ctx, MD_Cockpit_DDOrder_Detail_ID, trxName);
    }

    /** Load Constructor */
    public X_MD_Cockpit_DDOrder_Detail (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
    {
      super (ctx, rs, trxName);
    }


	/** Load Meta Data */
	@Override
	protected org.compiere.model.POInfo initPO(final Properties ctx)
	{
		return org.compiere.model.POInfo.getPOInfo(Table_Name);
	}

	/** 
	 * DDOrderDetailType AD_Reference_ID=541420
	 * Reference name: DDOrderDetailType
	 */
	public static final int DDORDERDETAILTYPE_AD_Reference_ID=541420;
	/** gepl. Zugang = SUPPLY */
	public static final String DDORDERDETAILTYPE_GeplZugang = "SUPPLY";
	/** gepl. Abgang = DEMAND */
	public static final String DDORDERDETAILTYPE_GeplAbgang = "DEMAND";
	@Override
	public void setDDOrderDetailType (final java.lang.String DDOrderDetailType)
	{
		set_Value (COLUMNNAME_DDOrderDetailType, DDOrderDetailType);
	}

	@Override
	public java.lang.String getDDOrderDetailType() 
	{
		return get_ValueAsString(COLUMNNAME_DDOrderDetailType);
	}

	@Override
	public org.eevolution.model.I_DD_OrderLine getDD_OrderLine()
	{
		return get_ValueAsPO(COLUMNNAME_DD_OrderLine_ID, org.eevolution.model.I_DD_OrderLine.class);
	}

	@Override
	public void setDD_OrderLine(final org.eevolution.model.I_DD_OrderLine DD_OrderLine)
	{
		set_ValueFromPO(COLUMNNAME_DD_OrderLine_ID, org.eevolution.model.I_DD_OrderLine.class, DD_OrderLine);
	}

	@Override
	public void setDD_OrderLine_ID (final int DD_OrderLine_ID)
	{
		if (DD_OrderLine_ID < 1) 
			set_Value (COLUMNNAME_DD_OrderLine_ID, null);
		else 
			set_Value (COLUMNNAME_DD_OrderLine_ID, DD_OrderLine_ID);
	}

	@Override
	public int getDD_OrderLine_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_DD_OrderLine_ID);
	}

	@Override
	public void setMD_Cockpit_DDOrder_Detail_ID (final int MD_Cockpit_DDOrder_Detail_ID)
	{
		if (MD_Cockpit_DDOrder_Detail_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_MD_Cockpit_DDOrder_Detail_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_MD_Cockpit_DDOrder_Detail_ID, MD_Cockpit_DDOrder_Detail_ID);
	}

	@Override
	public int getMD_Cockpit_DDOrder_Detail_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_MD_Cockpit_DDOrder_Detail_ID);
	}

	@Override
	public de.metas.material.cockpit.model.I_MD_Cockpit getMD_Cockpit()
	{
		return get_ValueAsPO(COLUMNNAME_MD_Cockpit_ID, de.metas.material.cockpit.model.I_MD_Cockpit.class);
	}

	@Override
	public void setMD_Cockpit(final de.metas.material.cockpit.model.I_MD_Cockpit MD_Cockpit)
	{
		set_ValueFromPO(COLUMNNAME_MD_Cockpit_ID, de.metas.material.cockpit.model.I_MD_Cockpit.class, MD_Cockpit);
	}

	@Override
	public void setMD_Cockpit_ID (final int MD_Cockpit_ID)
	{
		if (MD_Cockpit_ID < 1) 
			set_Value (COLUMNNAME_MD_Cockpit_ID, null);
		else 
			set_Value (COLUMNNAME_MD_Cockpit_ID, MD_Cockpit_ID);
	}

	@Override
	public int getMD_Cockpit_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_MD_Cockpit_ID);
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
	public void setQtyPending (final BigDecimal QtyPending)
	{
		set_Value (COLUMNNAME_QtyPending, QtyPending);
	}

	@Override
	public BigDecimal getQtyPending() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyPending);
		return bd != null ? bd : BigDecimal.ZERO;
	}
}