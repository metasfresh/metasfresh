// Generated Model - DO NOT CHANGE
package de.metas.contracts.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for ModCntr_BaseModuleConfig
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_ModCntr_BaseModuleConfig extends org.compiere.model.PO implements I_ModCntr_BaseModuleConfig, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1644251845L;

    /** Standard Constructor */
    public X_ModCntr_BaseModuleConfig (final Properties ctx, final int ModCntr_BaseModuleConfig_ID, @Nullable final String trxName)
    {
      super (ctx, ModCntr_BaseModuleConfig_ID, trxName);
    }

    /** Load Constructor */
    public X_ModCntr_BaseModuleConfig (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setModCntr_BaseModuleConfig_ID (final int ModCntr_BaseModuleConfig_ID)
	{
		if (ModCntr_BaseModuleConfig_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_ModCntr_BaseModuleConfig_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_ModCntr_BaseModuleConfig_ID, ModCntr_BaseModuleConfig_ID);
	}

	@Override
	public int getModCntr_BaseModuleConfig_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_ModCntr_BaseModuleConfig_ID);
	}

	@Override
	public de.metas.contracts.model.I_ModCntr_Module getModCntr_BaseModule()
	{
		return get_ValueAsPO(COLUMNNAME_ModCntr_BaseModule_ID, de.metas.contracts.model.I_ModCntr_Module.class);
	}

	@Override
	public void setModCntr_BaseModule(final de.metas.contracts.model.I_ModCntr_Module ModCntr_BaseModule)
	{
		set_ValueFromPO(COLUMNNAME_ModCntr_BaseModule_ID, de.metas.contracts.model.I_ModCntr_Module.class, ModCntr_BaseModule);
	}

	@Override
	public void setModCntr_BaseModule_ID (final int ModCntr_BaseModule_ID)
	{
		if (ModCntr_BaseModule_ID < 1) 
			set_Value (COLUMNNAME_ModCntr_BaseModule_ID, null);
		else 
			set_Value (COLUMNNAME_ModCntr_BaseModule_ID, ModCntr_BaseModule_ID);
	}

	@Override
	public int getModCntr_BaseModule_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_ModCntr_BaseModule_ID);
	}

	@Override
	public de.metas.contracts.model.I_ModCntr_Module getModCntr_Module()
	{
		return get_ValueAsPO(COLUMNNAME_ModCntr_Module_ID, de.metas.contracts.model.I_ModCntr_Module.class);
	}

	@Override
	public void setModCntr_Module(final de.metas.contracts.model.I_ModCntr_Module ModCntr_Module)
	{
		set_ValueFromPO(COLUMNNAME_ModCntr_Module_ID, de.metas.contracts.model.I_ModCntr_Module.class, ModCntr_Module);
	}

	@Override
	public void setModCntr_Module_ID (final int ModCntr_Module_ID)
	{
		if (ModCntr_Module_ID < 1) 
			set_Value (COLUMNNAME_ModCntr_Module_ID, null);
		else 
			set_Value (COLUMNNAME_ModCntr_Module_ID, ModCntr_Module_ID);
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
			set_Value (COLUMNNAME_ModCntr_Settings_ID, null);
		else 
			set_Value (COLUMNNAME_ModCntr_Settings_ID, ModCntr_Settings_ID);
	}

	@Override
	public int getModCntr_Settings_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_ModCntr_Settings_ID);
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
}