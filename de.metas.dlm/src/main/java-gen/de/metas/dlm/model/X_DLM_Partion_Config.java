/** Generated Model - DO NOT CHANGE */
package de.metas.dlm.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Generated Model for DLM_Partion_Config
 * 
 * @author Adempiere (generated)
 */
@SuppressWarnings("javadoc")
public class X_DLM_Partion_Config extends org.compiere.model.PO implements I_DLM_Partion_Config, org.compiere.model.I_Persistent
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1546930973L;

	/** Standard Constructor */
	public X_DLM_Partion_Config(final Properties ctx, final int DLM_Partion_Config_ID, final String trxName)
	{
		super(ctx, DLM_Partion_Config_ID, trxName);
		/**
		 * if (DLM_Partion_Config_ID == 0)
		 * {
		 * setDLM_Partion_Config_ID (0);
		 * }
		 */
	}

	/** Load Constructor */
	public X_DLM_Partion_Config(final Properties ctx, final ResultSet rs, final String trxName)
	{
		super(ctx, rs, trxName);
	}

	/** Load Meta Data */
	@Override
	protected org.compiere.model.POInfo initPO(final Properties ctx)
	{
		final org.compiere.model.POInfo poi = org.compiere.model.POInfo.getPOInfo(ctx, Table_Name, get_TrxName());
		return poi;
	}

	/**
	 * Set Beschreibung.
	 * 
	 * @param Description Beschreibung
	 */
	@Override
	public void setDescription(final java.lang.String Description)
	{
		set_Value(COLUMNNAME_Description, Description);
	}

	/**
	 * Get Beschreibung.
	 * 
	 * @return Beschreibung
	 */
	@Override
	public java.lang.String getDescription()
	{
		return (java.lang.String)get_Value(COLUMNNAME_Description);
	}

	/**
	 * Set DLM_Partion_Config.
	 * 
	 * @param DLM_Partion_Config_ID DLM_Partion_Config
	 */
	@Override
	public void setDLM_Partion_Config_ID(final int DLM_Partion_Config_ID)
	{
		if (DLM_Partion_Config_ID < 1)
		{
			set_ValueNoCheck(COLUMNNAME_DLM_Partion_Config_ID, null);
		}
		else
		{
			set_ValueNoCheck(COLUMNNAME_DLM_Partion_Config_ID, Integer.valueOf(DLM_Partion_Config_ID));
		}
	}

	/**
	 * Get DLM_Partion_Config.
	 * 
	 * @return DLM_Partion_Config
	 */
	@Override
	public int getDLM_Partion_Config_ID()
	{
		final Integer ii = (Integer)get_Value(COLUMNNAME_DLM_Partion_Config_ID);
		if (ii == null)
		{
			return 0;
		}
		return ii.intValue();
	}
}
