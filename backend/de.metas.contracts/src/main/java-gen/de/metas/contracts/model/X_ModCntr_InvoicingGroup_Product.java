// Generated Model - DO NOT CHANGE
package de.metas.contracts.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for ModCntr_InvoicingGroup_Product
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_ModCntr_InvoicingGroup_Product extends org.compiere.model.PO implements I_ModCntr_InvoicingGroup_Product, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 199986130L;

    /** Standard Constructor */
    public X_ModCntr_InvoicingGroup_Product (final Properties ctx, final int ModCntr_InvoicingGroup_Product_ID, @Nullable final String trxName)
    {
      super (ctx, ModCntr_InvoicingGroup_Product_ID, trxName);
    }

    /** Load Constructor */
    public X_ModCntr_InvoicingGroup_Product (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public de.metas.contracts.model.I_ModCntr_InvoicingGroup getModCntr_InvoicingGroup()
	{
		return get_ValueAsPO(COLUMNNAME_ModCntr_InvoicingGroup_ID, de.metas.contracts.model.I_ModCntr_InvoicingGroup.class);
	}

	@Override
	public void setModCntr_InvoicingGroup(final de.metas.contracts.model.I_ModCntr_InvoicingGroup ModCntr_InvoicingGroup)
	{
		set_ValueFromPO(COLUMNNAME_ModCntr_InvoicingGroup_ID, de.metas.contracts.model.I_ModCntr_InvoicingGroup.class, ModCntr_InvoicingGroup);
	}

	@Override
	public void setModCntr_InvoicingGroup_ID (final int ModCntr_InvoicingGroup_ID)
	{
		if (ModCntr_InvoicingGroup_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_ModCntr_InvoicingGroup_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_ModCntr_InvoicingGroup_ID, ModCntr_InvoicingGroup_ID);
	}

	@Override
	public int getModCntr_InvoicingGroup_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_ModCntr_InvoicingGroup_ID);
	}

	@Override
	public void setModCntr_InvoicingGroup_Product_ID (final int ModCntr_InvoicingGroup_Product_ID)
	{
		if (ModCntr_InvoicingGroup_Product_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_ModCntr_InvoicingGroup_Product_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_ModCntr_InvoicingGroup_Product_ID, ModCntr_InvoicingGroup_Product_ID);
	}

	@Override
	public int getModCntr_InvoicingGroup_Product_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_ModCntr_InvoicingGroup_Product_ID);
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
}