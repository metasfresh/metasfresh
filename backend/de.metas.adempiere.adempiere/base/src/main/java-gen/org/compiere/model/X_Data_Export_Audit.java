// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for Data_Export_Audit
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_Data_Export_Audit extends org.compiere.model.PO implements I_Data_Export_Audit, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1753567361L;

    /** Standard Constructor */
    public X_Data_Export_Audit (final Properties ctx, final int Data_Export_Audit_ID, @Nullable final String trxName)
    {
      super (ctx, Data_Export_Audit_ID, trxName);
    }

    /** Load Constructor */
    public X_Data_Export_Audit (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setData_Export_Audit_ID (final int Data_Export_Audit_ID)
	{
		if (Data_Export_Audit_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Data_Export_Audit_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Data_Export_Audit_ID, Data_Export_Audit_ID);
	}

	@Override
	public int getData_Export_Audit_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Data_Export_Audit_ID);
	}

	@Override
	public org.compiere.model.I_Data_Export_Audit getData_Export_Audit_Parent()
	{
		return get_ValueAsPO(COLUMNNAME_Data_Export_Audit_Parent_ID, org.compiere.model.I_Data_Export_Audit.class);
	}

	@Override
	public void setData_Export_Audit_Parent(final org.compiere.model.I_Data_Export_Audit Data_Export_Audit_Parent)
	{
		set_ValueFromPO(COLUMNNAME_Data_Export_Audit_Parent_ID, org.compiere.model.I_Data_Export_Audit.class, Data_Export_Audit_Parent);
	}

	@Override
	public void setData_Export_Audit_Parent_ID (final int Data_Export_Audit_Parent_ID)
	{
		if (Data_Export_Audit_Parent_ID < 1) 
			set_Value (COLUMNNAME_Data_Export_Audit_Parent_ID, null);
		else 
			set_Value (COLUMNNAME_Data_Export_Audit_Parent_ID, Data_Export_Audit_Parent_ID);
	}

	@Override
	public int getData_Export_Audit_Parent_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Data_Export_Audit_Parent_ID);
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
}