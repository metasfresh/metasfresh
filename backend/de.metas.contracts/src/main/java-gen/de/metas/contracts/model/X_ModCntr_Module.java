// Generated Model - DO NOT CHANGE
package de.metas.contracts.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for ModCntr_Module
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_ModCntr_Module extends org.compiere.model.PO implements I_ModCntr_Module, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -791716617L;

    /** Standard Constructor */
    public X_ModCntr_Module (final Properties ctx, final int ModCntr_Module_ID, @Nullable final String trxName)
    {
      super (ctx, ModCntr_Module_ID, trxName);
    }

    /** Load Constructor */
    public X_ModCntr_Module (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setDescription (final @Nullable java.lang.String Description)
	{
		throw new IllegalArgumentException ("Description is virtual column");	}

	@Override
	public java.lang.String getDescription() 
	{
		return get_ValueAsString(COLUMNNAME_Description);
	}

	/** 
	 * InvoicingGroup AD_Reference_ID=541742
	 * Reference name: InvoicingGroup
	 */
	public static final int INVOICINGGROUP_AD_Reference_ID=541742;
	/** Service = Service */
	public static final String INVOICINGGROUP_Service = "Service";
	/** Costs = Costs */
	public static final String INVOICINGGROUP_Costs = "Costs";
	@Override
	public void setInvoicingGroup (final java.lang.String InvoicingGroup)
	{
		set_Value (COLUMNNAME_InvoicingGroup, InvoicingGroup);
	}

	@Override
	public java.lang.String getInvoicingGroup() 
	{
		return get_ValueAsString(COLUMNNAME_InvoicingGroup);
	}

	@Override
	public void setModCntr_Module_ID (final int ModCntr_Module_ID)
	{
		if (ModCntr_Module_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_ModCntr_Module_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_ModCntr_Module_ID, ModCntr_Module_ID);
	}

	@Override
	public int getModCntr_Module_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_ModCntr_Module_ID);
	}

	@Override
	public de.metas.contracts.model.I_ModCntr_Settings getModCntr_Settings()
	{
		return get_ValueAsPO(COLUMNNAME_ModCntr_Settings_ID, de.metas.contracts.model.I_ModCntr_Settings.class);
	}

	@Override
	public void setModCntr_Settings(final de.metas.contracts.model.I_ModCntr_Settings ModCntr_Settings)
	{
		set_ValueFromPO(COLUMNNAME_ModCntr_Settings_ID, de.metas.contracts.model.I_ModCntr_Settings.class, ModCntr_Settings);
	}

	@Override
	public void setModCntr_Settings_ID (final int ModCntr_Settings_ID)
	{
		if (ModCntr_Settings_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_ModCntr_Settings_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_ModCntr_Settings_ID, ModCntr_Settings_ID);
	}

	@Override
	public int getModCntr_Settings_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_ModCntr_Settings_ID);
	}

	@Override
	public de.metas.contracts.model.I_ModCntr_Type getModCntr_Type()
	{
		return get_ValueAsPO(COLUMNNAME_ModCntr_Type_ID, de.metas.contracts.model.I_ModCntr_Type.class);
	}

	@Override
	public void setModCntr_Type(final de.metas.contracts.model.I_ModCntr_Type ModCntr_Type)
	{
		set_ValueFromPO(COLUMNNAME_ModCntr_Type_ID, de.metas.contracts.model.I_ModCntr_Type.class, ModCntr_Type);
	}

	@Override
	public void setModCntr_Type_ID (final int ModCntr_Type_ID)
	{
		if (ModCntr_Type_ID < 1) 
			set_Value (COLUMNNAME_ModCntr_Type_ID, null);
		else 
			set_Value (COLUMNNAME_ModCntr_Type_ID, ModCntr_Type_ID);
	}

	@Override
	public int getModCntr_Type_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_ModCntr_Type_ID);
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
	public void setProcessed (final boolean Processed)
	{
		set_Value (COLUMNNAME_Processed, Processed);
	}

	@Override
	public boolean isProcessed() 
	{
		return get_ValueAsBoolean(COLUMNNAME_Processed);
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