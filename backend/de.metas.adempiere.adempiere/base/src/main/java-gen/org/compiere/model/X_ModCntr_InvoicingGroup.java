// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for ModCntr_InvoicingGroup
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_ModCntr_InvoicingGroup extends org.compiere.model.PO implements I_ModCntr_InvoicingGroup, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -2081377453L;

    /** Standard Constructor */
    public X_ModCntr_InvoicingGroup (final Properties ctx, final int ModCntr_InvoicingGroup_ID, @Nullable final String trxName)
    {
      super (ctx, ModCntr_InvoicingGroup_ID, trxName);
    }

    /** Load Constructor */
    public X_ModCntr_InvoicingGroup (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setGroup_Product_ID (final int Group_Product_ID)
	{
		if (Group_Product_ID < 1) 
			set_Value (COLUMNNAME_Group_Product_ID, null);
		else 
			set_Value (COLUMNNAME_Group_Product_ID, Group_Product_ID);
	}

	@Override
	public int getGroup_Product_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Group_Product_ID);
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
	public void setName (final java.lang.String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	@Override
	public java.lang.String getName() 
	{
		return get_ValueAsString(COLUMNNAME_Name);
	}

	@Override
	public void setValidFrom (final java.sql.Timestamp ValidFrom)
	{
		set_Value (COLUMNNAME_ValidFrom, ValidFrom);
	}

	@Override
	public java.sql.Timestamp getValidFrom() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_ValidFrom);
	}

	@Override
	public void setValidTo (final java.sql.Timestamp ValidTo)
	{
		set_Value (COLUMNNAME_ValidTo, ValidTo);
	}

	@Override
	public java.sql.Timestamp getValidTo() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_ValidTo);
	}
}