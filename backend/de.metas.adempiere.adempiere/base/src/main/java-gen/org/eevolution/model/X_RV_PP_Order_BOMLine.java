// Generated Model - DO NOT CHANGE
package org.eevolution.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for RV_PP_Order_BOMLine
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_RV_PP_Order_BOMLine extends org.compiere.model.PO implements I_RV_PP_Order_BOMLine, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 1362538155L;

    /** Standard Constructor */
    public X_RV_PP_Order_BOMLine (final Properties ctx, final int RV_PP_Order_BOMLine_ID, @Nullable final String trxName)
    {
      super (ctx, RV_PP_Order_BOMLine_ID, trxName);
    }

    /** Load Constructor */
    public X_RV_PP_Order_BOMLine (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
			set_ValueNoCheck (COLUMNNAME_C_UOM_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_UOM_ID, C_UOM_ID);
	}

	@Override
	public int getC_UOM_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_UOM_ID);
	}

	@Override
	public void setComponentType (final @Nullable java.lang.String ComponentType)
	{
		set_ValueNoCheck (COLUMNNAME_ComponentType, ComponentType);
	}

	@Override
	public java.lang.String getComponentType() 
	{
		return get_ValueAsString(COLUMNNAME_ComponentType);
	}

	@Override
	public void setIsCritical (final boolean IsCritical)
	{
		set_ValueNoCheck (COLUMNNAME_IsCritical, IsCritical);
	}

	@Override
	public boolean isCritical() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsCritical);
	}

	@Override
	public void setIsQtyPercentage (final @Nullable BigDecimal IsQtyPercentage)
	{
		set_ValueNoCheck (COLUMNNAME_IsQtyPercentage, IsQtyPercentage);
	}

	@Override
	public BigDecimal getIsQtyPercentage() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_IsQtyPercentage);
		return bd != null ? bd : BigDecimal.ZERO;
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
			set_ValueNoCheck (COLUMNNAME_M_Warehouse_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Warehouse_ID, M_Warehouse_ID);
	}

	@Override
	public int getM_Warehouse_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Warehouse_ID);
	}

	@Override
	public org.eevolution.model.I_PP_Order_BOM getPP_Order_BOM()
	{
		return get_ValueAsPO(COLUMNNAME_PP_Order_BOM_ID, org.eevolution.model.I_PP_Order_BOM.class);
	}

	@Override
	public void setPP_Order_BOM(final org.eevolution.model.I_PP_Order_BOM PP_Order_BOM)
	{
		set_ValueFromPO(COLUMNNAME_PP_Order_BOM_ID, org.eevolution.model.I_PP_Order_BOM.class, PP_Order_BOM);
	}

	@Override
	public void setPP_Order_BOM_ID (final int PP_Order_BOM_ID)
	{
		if (PP_Order_BOM_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_PP_Order_BOM_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_PP_Order_BOM_ID, PP_Order_BOM_ID);
	}

	@Override
	public int getPP_Order_BOM_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_PP_Order_BOM_ID);
	}

	@Override
	public org.eevolution.model.I_PP_Order_BOMLine getPP_Order_BOMLine()
	{
		return get_ValueAsPO(COLUMNNAME_PP_Order_BOMLine_ID, org.eevolution.model.I_PP_Order_BOMLine.class);
	}

	@Override
	public void setPP_Order_BOMLine(final org.eevolution.model.I_PP_Order_BOMLine PP_Order_BOMLine)
	{
		set_ValueFromPO(COLUMNNAME_PP_Order_BOMLine_ID, org.eevolution.model.I_PP_Order_BOMLine.class, PP_Order_BOMLine);
	}

	@Override
	public void setPP_Order_BOMLine_ID (final int PP_Order_BOMLine_ID)
	{
		if (PP_Order_BOMLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_PP_Order_BOMLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_PP_Order_BOMLine_ID, PP_Order_BOMLine_ID);
	}

	@Override
	public int getPP_Order_BOMLine_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_PP_Order_BOMLine_ID);
	}

	@Override
	public org.eevolution.model.I_PP_Order getPP_Order()
	{
		return get_ValueAsPO(COLUMNNAME_PP_Order_ID, org.eevolution.model.I_PP_Order.class);
	}

	@Override
	public void setPP_Order(final org.eevolution.model.I_PP_Order PP_Order)
	{
		set_ValueFromPO(COLUMNNAME_PP_Order_ID, org.eevolution.model.I_PP_Order.class, PP_Order);
	}

	@Override
	public void setPP_Order_ID (final int PP_Order_ID)
	{
		if (PP_Order_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_PP_Order_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_PP_Order_ID, PP_Order_ID);
	}

	@Override
	public int getPP_Order_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_PP_Order_ID);
	}

	@Override
	public void setQtyAvailable (final @Nullable BigDecimal QtyAvailable)
	{
		set_ValueNoCheck (COLUMNNAME_QtyAvailable, QtyAvailable);
	}

	@Override
	public BigDecimal getQtyAvailable() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyAvailable);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyBatch (final @Nullable BigDecimal QtyBatch)
	{
		set_ValueNoCheck (COLUMNNAME_QtyBatch, QtyBatch);
	}

	@Override
	public BigDecimal getQtyBatch() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyBatch);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyBatchSize (final @Nullable BigDecimal QtyBatchSize)
	{
		set_ValueNoCheck (COLUMNNAME_QtyBatchSize, QtyBatchSize);
	}

	@Override
	public BigDecimal getQtyBatchSize() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyBatchSize);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyBOM (final @Nullable BigDecimal QtyBOM)
	{
		set_ValueNoCheck (COLUMNNAME_QtyBOM, QtyBOM);
	}

	@Override
	public BigDecimal getQtyBOM() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyBOM);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyOnHand (final @Nullable BigDecimal QtyOnHand)
	{
		set_ValueNoCheck (COLUMNNAME_QtyOnHand, QtyOnHand);
	}

	@Override
	public BigDecimal getQtyOnHand() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyOnHand);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyRequiered (final @Nullable BigDecimal QtyRequiered)
	{
		set_ValueNoCheck (COLUMNNAME_QtyRequiered, QtyRequiered);
	}

	@Override
	public BigDecimal getQtyRequiered() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyRequiered);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyReserved (final @Nullable BigDecimal QtyReserved)
	{
		set_ValueNoCheck (COLUMNNAME_QtyReserved, QtyReserved);
	}

	@Override
	public BigDecimal getQtyReserved() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyReserved);
		return bd != null ? bd : BigDecimal.ZERO;
	}
}