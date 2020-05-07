/** Generated Model - DO NOT CHANGE */
package de.metas.dlm.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Generated Model for DLM_Partition_Config
 * 
 * @author Adempiere (generated)
 */
@SuppressWarnings("javadoc")
public class X_DLM_Partition_Config extends org.compiere.model.PO implements I_DLM_Partition_Config, org.compiere.model.I_Persistent
{

	/**
	 *
	 */
	private static final long serialVersionUID = -1114687650L;

	/** Standard Constructor */
	public X_DLM_Partition_Config(final Properties ctx, final int DLM_Partition_Config_ID, final String trxName)
	{
		super(ctx, DLM_Partition_Config_ID, trxName);
		/**
		 * if (DLM_Partition_Config_ID == 0)
		 * {
		 * setDLM_Partition_Config_ID (0);
		 * setIsDefault (false);
		 * // N
		 * setName (null);
		 * }
		 */
	}

	/** Load Constructor */
	public X_DLM_Partition_Config(final Properties ctx, final ResultSet rs, final String trxName)
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
	 * Set DLM Partitionierungskonfiguration.
	 * 
	 * @param DLM_Partition_Config_ID DLM Partitionierungskonfiguration
	 */
	@Override
	public void setDLM_Partition_Config_ID(final int DLM_Partition_Config_ID)
	{
		if (DLM_Partition_Config_ID < 1)
		{
			set_ValueNoCheck(COLUMNNAME_DLM_Partition_Config_ID, null);
		}
		else
		{
			set_ValueNoCheck(COLUMNNAME_DLM_Partition_Config_ID, Integer.valueOf(DLM_Partition_Config_ID));
		}
	}

	/**
	 * Get DLM Partitionierungskonfiguration.
	 * 
	 * @return DLM Partitionierungskonfiguration
	 */
	@Override
	public int getDLM_Partition_Config_ID()
	{
		final Integer ii = (Integer)get_Value(COLUMNNAME_DLM_Partition_Config_ID);
		if (ii == null)
		{
			return 0;
		}
		return ii.intValue();
	}

	/**
	 * Set Standard.
	 * 
	 * @param IsDefault
	 *            Default value
	 */
	@Override
	public void setIsDefault(final boolean IsDefault)
	{
		set_Value(COLUMNNAME_IsDefault, Boolean.valueOf(IsDefault));
	}

	/**
	 * Get Standard.
	 * 
	 * @return Default value
	 */
	@Override
	public boolean isDefault()
	{
		final Object oo = get_Value(COLUMNNAME_IsDefault);
		if (oo != null)
		{
			if (oo instanceof Boolean)
			{
				return ((Boolean)oo).booleanValue();
			}
			return "Y".equals(oo);
		}
		return false;
	}

	/**
	 * Set Name.
	 * 
	 * @param Name
	 *            Alphanumeric identifier of the entity
	 */
	@Override
	public void setName(final java.lang.String Name)
	{
		set_Value(COLUMNNAME_Name, Name);
	}

	/**
	 * Get Name.
	 * 
	 * @return Alphanumeric identifier of the entity
	 */
	@Override
	public java.lang.String getName()
	{
		return (java.lang.String)get_Value(COLUMNNAME_Name);
	}
}
