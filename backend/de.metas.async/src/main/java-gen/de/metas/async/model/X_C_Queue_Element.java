// Generated Model - DO NOT CHANGE
package de.metas.async.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_Queue_Element
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_Queue_Element extends org.compiere.model.PO implements I_C_Queue_Element, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 99629641L;

    /** Standard Constructor */
    public X_C_Queue_Element (final Properties ctx, final int C_Queue_Element_ID, @Nullable final String trxName)
    {
      super (ctx, C_Queue_Element_ID, trxName);
    }

    /** Load Constructor */
    public X_C_Queue_Element (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setC_Queue_Element_ID (final int C_Queue_Element_ID)
	{
		if (C_Queue_Element_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Queue_Element_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Queue_Element_ID, C_Queue_Element_ID);
	}

	@Override
	public int getC_Queue_Element_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Queue_Element_ID);
	}

	@Override
	public I_C_Queue_WorkPackage getC_Queue_WorkPackage()
	{
		return get_ValueAsPO(COLUMNNAME_C_Queue_WorkPackage_ID, I_C_Queue_WorkPackage.class);
	}

	@Override
	public void setC_Queue_WorkPackage(final I_C_Queue_WorkPackage C_Queue_WorkPackage)
	{
		set_ValueFromPO(COLUMNNAME_C_Queue_WorkPackage_ID, I_C_Queue_WorkPackage.class, C_Queue_WorkPackage);
	}

	@Override
	public void setC_Queue_WorkPackage_ID (final int C_Queue_WorkPackage_ID)
	{
		if (C_Queue_WorkPackage_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Queue_WorkPackage_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Queue_WorkPackage_ID, C_Queue_WorkPackage_ID);
	}

	@Override
	public int getC_Queue_WorkPackage_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Queue_WorkPackage_ID);
	}

	@Override
	public I_C_Queue_WorkPackage getC_Queue_Workpackage_Preceeding()
	{
		return get_ValueAsPO(COLUMNNAME_C_Queue_Workpackage_Preceeding_ID, I_C_Queue_WorkPackage.class);
	}

	@Override
	public void setC_Queue_Workpackage_Preceeding(final I_C_Queue_WorkPackage C_Queue_Workpackage_Preceeding)
	{
		set_ValueFromPO(COLUMNNAME_C_Queue_Workpackage_Preceeding_ID, I_C_Queue_WorkPackage.class, C_Queue_Workpackage_Preceeding);
	}

	@Override
	public void setC_Queue_Workpackage_Preceeding_ID (final int C_Queue_Workpackage_Preceeding_ID)
	{
		throw new IllegalArgumentException ("C_Queue_Workpackage_Preceeding_ID is virtual column");	}

	@Override
	public int getC_Queue_Workpackage_Preceeding_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Queue_Workpackage_Preceeding_ID);
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