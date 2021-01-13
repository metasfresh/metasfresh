// Generated Model - DO NOT CHANGE
package de.metas.handlingunits.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for M_HU_Assignment
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_M_HU_Assignment extends org.compiere.model.PO implements I_M_HU_Assignment, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 935131328L;

    /** Standard Constructor */
    public X_M_HU_Assignment (final Properties ctx, final int M_HU_Assignment_ID, @Nullable final String trxName)
    {
      super (ctx, M_HU_Assignment_ID, trxName);
    }

    /** Load Constructor */
    public X_M_HU_Assignment (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setAD_Table_ID (final int AD_Table_ID)
	{
		if (AD_Table_ID < 1) 
			set_Value (COLUMNNAME_AD_Table_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Table_ID, AD_Table_ID);
	}

	@Override
	public int getAD_Table_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Table_ID);
	}

	@Override
	public void setIsTransferPackingMaterials (final boolean IsTransferPackingMaterials)
	{
		set_Value (COLUMNNAME_IsTransferPackingMaterials, IsTransferPackingMaterials);
	}

	@Override
	public boolean isTransferPackingMaterials() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsTransferPackingMaterials);
	}

	@Override
	public void setM_HU_Assignment_ID (final int M_HU_Assignment_ID)
	{
		if (M_HU_Assignment_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_HU_Assignment_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_HU_Assignment_ID, M_HU_Assignment_ID);
	}

	@Override
	public int getM_HU_Assignment_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_HU_Assignment_ID);
	}

	@Override
	public de.metas.handlingunits.model.I_M_HU getM_HU()
	{
		return get_ValueAsPO(COLUMNNAME_M_HU_ID, de.metas.handlingunits.model.I_M_HU.class);
	}

	@Override
	public void setM_HU(final de.metas.handlingunits.model.I_M_HU M_HU)
	{
		set_ValueFromPO(COLUMNNAME_M_HU_ID, de.metas.handlingunits.model.I_M_HU.class, M_HU);
	}

	@Override
	public void setM_HU_ID (final int M_HU_ID)
	{
		if (M_HU_ID < 1) 
			set_Value (COLUMNNAME_M_HU_ID, null);
		else 
			set_Value (COLUMNNAME_M_HU_ID, M_HU_ID);
	}

	@Override
	public int getM_HU_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_HU_ID);
	}

	@Override
	public de.metas.handlingunits.model.I_M_HU getM_LU_HU()
	{
		return get_ValueAsPO(COLUMNNAME_M_LU_HU_ID, de.metas.handlingunits.model.I_M_HU.class);
	}

	@Override
	public void setM_LU_HU(final de.metas.handlingunits.model.I_M_HU M_LU_HU)
	{
		set_ValueFromPO(COLUMNNAME_M_LU_HU_ID, de.metas.handlingunits.model.I_M_HU.class, M_LU_HU);
	}

	@Override
	public void setM_LU_HU_ID (final int M_LU_HU_ID)
	{
		if (M_LU_HU_ID < 1) 
			set_Value (COLUMNNAME_M_LU_HU_ID, null);
		else 
			set_Value (COLUMNNAME_M_LU_HU_ID, M_LU_HU_ID);
	}

	@Override
	public int getM_LU_HU_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_LU_HU_ID);
	}

	@Override
	public de.metas.handlingunits.model.I_M_HU getM_TU_HU()
	{
		return get_ValueAsPO(COLUMNNAME_M_TU_HU_ID, de.metas.handlingunits.model.I_M_HU.class);
	}

	@Override
	public void setM_TU_HU(final de.metas.handlingunits.model.I_M_HU M_TU_HU)
	{
		set_ValueFromPO(COLUMNNAME_M_TU_HU_ID, de.metas.handlingunits.model.I_M_HU.class, M_TU_HU);
	}

	@Override
	public void setM_TU_HU_ID (final int M_TU_HU_ID)
	{
		if (M_TU_HU_ID < 1) 
			set_Value (COLUMNNAME_M_TU_HU_ID, null);
		else 
			set_Value (COLUMNNAME_M_TU_HU_ID, M_TU_HU_ID);
	}

	@Override
	public int getM_TU_HU_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_TU_HU_ID);
	}

	@Override
	public void setProducts (final @Nullable java.lang.String Products)
	{
		throw new IllegalArgumentException ("Products is virtual column");	}

	@Override
	public java.lang.String getProducts() 
	{
		return get_ValueAsString(COLUMNNAME_Products);
	}

	@Override
	public void setQty (final @Nullable BigDecimal Qty)
	{
		set_Value (COLUMNNAME_Qty, Qty);
	}

	@Override
	public BigDecimal getQty() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_Qty);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setRecord_ID (final int Record_ID)
	{
		if (Record_ID < 0) 
			set_Value (COLUMNNAME_Record_ID, null);
		else 
			set_Value (COLUMNNAME_Record_ID, Record_ID);
	}

	@Override
	public int getRecord_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Record_ID);
	}

	@Override
	public de.metas.handlingunits.model.I_M_HU getVHU()
	{
		return get_ValueAsPO(COLUMNNAME_VHU_ID, de.metas.handlingunits.model.I_M_HU.class);
	}

	@Override
	public void setVHU(final de.metas.handlingunits.model.I_M_HU VHU)
	{
		set_ValueFromPO(COLUMNNAME_VHU_ID, de.metas.handlingunits.model.I_M_HU.class, VHU);
	}

	@Override
	public void setVHU_ID (final int VHU_ID)
	{
		if (VHU_ID < 1) 
			set_Value (COLUMNNAME_VHU_ID, null);
		else 
			set_Value (COLUMNNAME_VHU_ID, VHU_ID);
	}

	@Override
	public int getVHU_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_VHU_ID);
	}
}