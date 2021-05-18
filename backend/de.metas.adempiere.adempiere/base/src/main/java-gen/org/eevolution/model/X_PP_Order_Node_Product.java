// Generated Model - DO NOT CHANGE
package org.eevolution.model;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/**
 * Generated Model for PP_Order_Node_Product
 *
 * @author metasfresh (generated)
 */
@SuppressWarnings("unused")
public class X_PP_Order_Node_Product extends org.compiere.model.PO implements I_PP_Order_Node_Product, org.compiere.model.I_Persistent
{

	private static final long serialVersionUID = -560375951L;

	/**
	 * Standard Constructor
	 */
	public X_PP_Order_Node_Product(final Properties ctx, final int PP_Order_Node_Product_ID, @Nullable final String trxName)
	{
		super(ctx, PP_Order_Node_Product_ID, trxName);
	}

	/**
	 * Load Constructor
	 */
	public X_PP_Order_Node_Product(final Properties ctx, final ResultSet rs, @Nullable final String trxName)
	{
		super(ctx, rs, trxName);
	}

	/**
	 * Load Meta Data
	 */
	@Override
	protected org.compiere.model.POInfo initPO(final Properties ctx)
	{
		return org.compiere.model.POInfo.getPOInfo(Table_Name);
	}

	@Override
	public void setIsSubcontracting(final boolean IsSubcontracting)
	{
		set_Value(COLUMNNAME_IsSubcontracting, IsSubcontracting);
	}

	@Override
	public boolean isSubcontracting()
	{
		return get_ValueAsBoolean(COLUMNNAME_IsSubcontracting);
	}

	@Override
	public int getM_Product_ID()
	{
		return get_ValueAsInt(COLUMNNAME_M_Product_ID);
	}

	@Override
	public void setM_Product_ID(final int M_Product_ID)
	{
		if (M_Product_ID < 1)
			set_Value(COLUMNNAME_M_Product_ID, null);
		else
			set_Value(COLUMNNAME_M_Product_ID, M_Product_ID);
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
	public int getPP_Order_ID()
	{
		return get_ValueAsInt(COLUMNNAME_PP_Order_ID);
	}

	@Override
	public void setPP_Order_ID(final int PP_Order_ID)
	{
		if (PP_Order_ID < 1)
			set_ValueNoCheck(COLUMNNAME_PP_Order_ID, null);
		else
			set_ValueNoCheck(COLUMNNAME_PP_Order_ID, PP_Order_ID);
	}

	@Override
	public org.eevolution.model.I_PP_Order_Node getPP_Order_Node()
	{
		return get_ValueAsPO(COLUMNNAME_PP_Order_Node_ID, org.eevolution.model.I_PP_Order_Node.class);
	}

	@Override
	public void setPP_Order_Node(final org.eevolution.model.I_PP_Order_Node PP_Order_Node)
	{
		set_ValueFromPO(COLUMNNAME_PP_Order_Node_ID, org.eevolution.model.I_PP_Order_Node.class, PP_Order_Node);
	}

	@Override
	public int getPP_Order_Node_ID()
	{
		return get_ValueAsInt(COLUMNNAME_PP_Order_Node_ID);
	}

	@Override
	public void setPP_Order_Node_ID(final int PP_Order_Node_ID)
	{
		if (PP_Order_Node_ID < 1)
			set_ValueNoCheck(COLUMNNAME_PP_Order_Node_ID, null);
		else
			set_ValueNoCheck(COLUMNNAME_PP_Order_Node_ID, PP_Order_Node_ID);
	}

	@Override
	public int getPP_Order_Node_Product_ID()
	{
		return get_ValueAsInt(COLUMNNAME_PP_Order_Node_Product_ID);
	}

	@Override
	public void setPP_Order_Node_Product_ID(final int PP_Order_Node_Product_ID)
	{
		if (PP_Order_Node_Product_ID < 1)
			set_ValueNoCheck(COLUMNNAME_PP_Order_Node_Product_ID, null);
		else
			set_ValueNoCheck(COLUMNNAME_PP_Order_Node_Product_ID, PP_Order_Node_Product_ID);
	}

	@Override
	public org.eevolution.model.I_PP_Order_Workflow getPP_Order_Workflow()
	{
		return get_ValueAsPO(COLUMNNAME_PP_Order_Workflow_ID, org.eevolution.model.I_PP_Order_Workflow.class);
	}

	@Override
	public void setPP_Order_Workflow(final org.eevolution.model.I_PP_Order_Workflow PP_Order_Workflow)
	{
		set_ValueFromPO(COLUMNNAME_PP_Order_Workflow_ID, org.eevolution.model.I_PP_Order_Workflow.class, PP_Order_Workflow);
	}

	@Override
	public int getPP_Order_Workflow_ID()
	{
		return get_ValueAsInt(COLUMNNAME_PP_Order_Workflow_ID);
	}

	@Override
	public void setPP_Order_Workflow_ID(final int PP_Order_Workflow_ID)
	{
		if (PP_Order_Workflow_ID < 1)
			set_ValueNoCheck(COLUMNNAME_PP_Order_Workflow_ID, null);
		else
			set_ValueNoCheck(COLUMNNAME_PP_Order_Workflow_ID, PP_Order_Workflow_ID);
	}

	@Override
	public BigDecimal getQty()
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_Qty);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQty(final @Nullable BigDecimal Qty)
	{
		set_Value(COLUMNNAME_Qty, Qty);
	}

	@Override
	public int getSeqNo()
	{
		return get_ValueAsInt(COLUMNNAME_SeqNo);
	}

	@Override
	public void setSeqNo(final int SeqNo)
	{
		set_Value(COLUMNNAME_SeqNo, SeqNo);
	}

	@Override
	public java.lang.String getSpecification()
	{
		return get_ValueAsString(COLUMNNAME_Specification);
	}

	@Override
	public void setSpecification(final @Nullable java.lang.String Specification)
	{
		set_Value(COLUMNNAME_Specification, Specification);
	}

	@Override
	public BigDecimal getYield()
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_Yield);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setYield(final @Nullable BigDecimal Yield)
	{
		set_Value(COLUMNNAME_Yield, Yield);
	}
}