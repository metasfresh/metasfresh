// Generated Model - DO NOT CHANGE
package de.metas.contracts.model;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_Flatrate_DataEntry_Detail
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_Flatrate_DataEntry_Detail extends org.compiere.model.PO implements I_C_Flatrate_DataEntry_Detail, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 750374448L;

    /** Standard Constructor */
    public X_C_Flatrate_DataEntry_Detail (final Properties ctx, final int C_Flatrate_DataEntry_Detail_ID, @Nullable final String trxName)
    {
      super (ctx, C_Flatrate_DataEntry_Detail_ID, trxName);
    }

    /** Load Constructor */
    public X_C_Flatrate_DataEntry_Detail (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public org.compiere.model.I_C_BPartner_Department getC_BPartner_Department()
	{
		return get_ValueAsPO(COLUMNNAME_C_BPartner_Department_ID, org.compiere.model.I_C_BPartner_Department.class);
	}

	@Override
	public void setC_BPartner_Department(final org.compiere.model.I_C_BPartner_Department C_BPartner_Department)
	{
		set_ValueFromPO(COLUMNNAME_C_BPartner_Department_ID, org.compiere.model.I_C_BPartner_Department.class, C_BPartner_Department);
	}

	@Override
	public void setC_BPartner_Department_ID (final int C_BPartner_Department_ID)
	{
		if (C_BPartner_Department_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_Department_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_Department_ID, C_BPartner_Department_ID);
	}

	@Override
	public int getC_BPartner_Department_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_Department_ID);
	}

	@Override
	public void setC_Flatrate_DataEntry_Detail_ID (final int C_Flatrate_DataEntry_Detail_ID)
	{
		if (C_Flatrate_DataEntry_Detail_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Flatrate_DataEntry_Detail_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Flatrate_DataEntry_Detail_ID, C_Flatrate_DataEntry_Detail_ID);
	}

	@Override
	public int getC_Flatrate_DataEntry_Detail_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Flatrate_DataEntry_Detail_ID);
	}

	@Override
	public de.metas.contracts.model.I_C_Flatrate_DataEntry getC_Flatrate_DataEntry()
	{
		return get_ValueAsPO(COLUMNNAME_C_Flatrate_DataEntry_ID, de.metas.contracts.model.I_C_Flatrate_DataEntry.class);
	}

	@Override
	public void setC_Flatrate_DataEntry(final de.metas.contracts.model.I_C_Flatrate_DataEntry C_Flatrate_DataEntry)
	{
		set_ValueFromPO(COLUMNNAME_C_Flatrate_DataEntry_ID, de.metas.contracts.model.I_C_Flatrate_DataEntry.class, C_Flatrate_DataEntry);
	}

	@Override
	public void setC_Flatrate_DataEntry_ID (final int C_Flatrate_DataEntry_ID)
	{
		if (C_Flatrate_DataEntry_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Flatrate_DataEntry_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Flatrate_DataEntry_ID, C_Flatrate_DataEntry_ID);
	}

	@Override
	public int getC_Flatrate_DataEntry_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Flatrate_DataEntry_ID);
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
	public void setQty_Reported (final @Nullable BigDecimal Qty_Reported)
	{
		set_Value (COLUMNNAME_Qty_Reported, Qty_Reported);
	}

	@Override
	public BigDecimal getQty_Reported() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_Qty_Reported);
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