// Generated Model - DO NOT CHANGE
package de.metas.fresh.model;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for Fresh_QtyOnHand_Line
 *  @author metasfresh (generated)
 */
@SuppressWarnings("unused")
public class X_Fresh_QtyOnHand_Line extends org.compiere.model.PO implements I_Fresh_QtyOnHand_Line, org.compiere.model.I_Persistent
{

	private static final long serialVersionUID = 1216286992L;

	/** Standard Constructor */
	public X_Fresh_QtyOnHand_Line (final Properties ctx, final int Fresh_QtyOnHand_Line_ID, @Nullable final String trxName)
	{
		super (ctx, Fresh_QtyOnHand_Line_ID, trxName);
	}

	/** Load Constructor */
	public X_Fresh_QtyOnHand_Line (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setASIKey (final @Nullable java.lang.String ASIKey)
	{
		set_Value (COLUMNNAME_ASIKey, ASIKey);
	}

	@Override
	public java.lang.String getASIKey()
	{
		return get_ValueAsString(COLUMNNAME_ASIKey);
	}

	@Override
	public void setDateDoc (final java.sql.Timestamp DateDoc)
	{
		set_Value (COLUMNNAME_DateDoc, DateDoc);
	}

	@Override
	public java.sql.Timestamp getDateDoc()
	{
		return get_ValueAsTimestamp(COLUMNNAME_DateDoc);
	}

	@Override
	public de.metas.fresh.model.I_Fresh_QtyOnHand getFresh_QtyOnHand()
	{
		return get_ValueAsPO(COLUMNNAME_Fresh_QtyOnHand_ID, de.metas.fresh.model.I_Fresh_QtyOnHand.class);
	}

	@Override
	public void setFresh_QtyOnHand(final de.metas.fresh.model.I_Fresh_QtyOnHand Fresh_QtyOnHand)
	{
		set_ValueFromPO(COLUMNNAME_Fresh_QtyOnHand_ID, de.metas.fresh.model.I_Fresh_QtyOnHand.class, Fresh_QtyOnHand);
	}

	@Override
	public void setFresh_QtyOnHand_ID (final int Fresh_QtyOnHand_ID)
	{
		if (Fresh_QtyOnHand_ID < 1)
			set_ValueNoCheck (COLUMNNAME_Fresh_QtyOnHand_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_Fresh_QtyOnHand_ID, Fresh_QtyOnHand_ID);
	}

	@Override
	public int getFresh_QtyOnHand_ID()
	{
		return get_ValueAsInt(COLUMNNAME_Fresh_QtyOnHand_ID);
	}

	@Override
	public void setFresh_QtyOnHand_Line_ID (final int Fresh_QtyOnHand_Line_ID)
	{
		if (Fresh_QtyOnHand_Line_ID < 1)
			set_ValueNoCheck (COLUMNNAME_Fresh_QtyOnHand_Line_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_Fresh_QtyOnHand_Line_ID, Fresh_QtyOnHand_Line_ID);
	}

	@Override
	public int getFresh_QtyOnHand_Line_ID()
	{
		return get_ValueAsInt(COLUMNNAME_Fresh_QtyOnHand_Line_ID);
	}

	@Override
	public org.compiere.model.I_M_AttributeSetInstance getM_AttributeSetInstance()
	{
		return get_ValueAsPO(COLUMNNAME_M_AttributeSetInstance_ID, org.compiere.model.I_M_AttributeSetInstance.class);
	}

	@Override
	public void setM_AttributeSetInstance(final org.compiere.model.I_M_AttributeSetInstance M_AttributeSetInstance)
	{
		set_ValueFromPO(COLUMNNAME_M_AttributeSetInstance_ID, org.compiere.model.I_M_AttributeSetInstance.class, M_AttributeSetInstance);
	}

	@Override
	public void setM_AttributeSetInstance_ID (final int M_AttributeSetInstance_ID)
	{
		if (M_AttributeSetInstance_ID < 0)
			set_Value (COLUMNNAME_M_AttributeSetInstance_ID, null);
		else
			set_Value (COLUMNNAME_M_AttributeSetInstance_ID, M_AttributeSetInstance_ID);
	}

	@Override
	public int getM_AttributeSetInstance_ID()
	{
		return get_ValueAsInt(COLUMNNAME_M_AttributeSetInstance_ID);
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
	public org.compiere.model.I_S_Resource getPP_Plant()
	{
		return get_ValueAsPO(COLUMNNAME_PP_Plant_ID, org.compiere.model.I_S_Resource.class);
	}

	@Override
	public void setPP_Plant(final org.compiere.model.I_S_Resource PP_Plant)
	{
		set_ValueFromPO(COLUMNNAME_PP_Plant_ID, org.compiere.model.I_S_Resource.class, PP_Plant);
	}

	@Override
	public void setPP_Plant_ID (final int PP_Plant_ID)
	{
		if (PP_Plant_ID < 1)
			set_Value (COLUMNNAME_PP_Plant_ID, null);
		else
			set_Value (COLUMNNAME_PP_Plant_ID, PP_Plant_ID);
	}

	@Override
	public int getPP_Plant_ID()
	{
		return get_ValueAsInt(COLUMNNAME_PP_Plant_ID);
	}

	@Override
	public void setProductGroup (final @Nullable java.lang.String ProductGroup)
	{
		throw new IllegalArgumentException ("ProductGroup is virtual column");	}

	@Override
	public java.lang.String getProductGroup()
	{
		return get_ValueAsString(COLUMNNAME_ProductGroup);
	}

	@Override
	public void setProductName (final @Nullable java.lang.String ProductName)
	{
		throw new IllegalArgumentException ("ProductName is virtual column");	}

	@Override
	public java.lang.String getProductName()
	{
		return get_ValueAsString(COLUMNNAME_ProductName);
	}

	@Override
	public void setQtyCount (final BigDecimal QtyCount)
	{
		set_Value (COLUMNNAME_QtyCount, QtyCount);
	}

	@Override
	public BigDecimal getQtyCount()
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyCount);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setSeqNo (final int SeqNo)
	{
		set_Value (COLUMNNAME_SeqNo, SeqNo);
	}

	@Override
	public int getSeqNo()
	{
		return get_ValueAsInt(COLUMNNAME_SeqNo);
	}
}