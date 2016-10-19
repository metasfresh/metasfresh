/** Generated Model - DO NOT CHANGE */
package de.metas.dlm.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Generated Model for DLM_Partition
 * 
 * @author Adempiere (generated)
 */
@SuppressWarnings("javadoc")
public class X_DLM_Partition extends org.compiere.model.PO implements I_DLM_Partition, org.compiere.model.I_Persistent
{

	/**
	 *
	 */
	private static final long serialVersionUID = 335985815L;

	/** Standard Constructor */
	public X_DLM_Partition(final Properties ctx, final int DLM_Partition_ID, final String trxName)
	{
		super(ctx, DLM_Partition_ID, trxName);
		/**
		 * if (DLM_Partition_ID == 0)
		 * {
		 * setDLM_Partition_Config_ID (0);
		 * setDLM_Partition_ID (0);
		 * }
		 */
	}

	/** Load Constructor */
	public X_DLM_Partition(final Properties ctx, final ResultSet rs, final String trxName)
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

	@Override
	public de.metas.dlm.model.I_DLM_Partition_Config getDLM_Partition_Config() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_DLM_Partition_Config_ID, de.metas.dlm.model.I_DLM_Partition_Config.class);
	}

	@Override
	public void setDLM_Partition_Config(final de.metas.dlm.model.I_DLM_Partition_Config DLM_Partition_Config)
	{
		set_ValueFromPO(COLUMNNAME_DLM_Partition_Config_ID, de.metas.dlm.model.I_DLM_Partition_Config.class, DLM_Partition_Config);
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
	 * Set Partition.
	 * 
	 * @param DLM_Partition_ID Partition
	 */
	@Override
	public void setDLM_Partition_ID(final int DLM_Partition_ID)
	{
		if (DLM_Partition_ID < 1)
		{
			set_ValueNoCheck(COLUMNNAME_DLM_Partition_ID, null);
		}
		else
		{
			set_ValueNoCheck(COLUMNNAME_DLM_Partition_ID, Integer.valueOf(DLM_Partition_ID));
		}
	}

	/**
	 * Get Partition.
	 * 
	 * @return Partition
	 */
	@Override
	public int getDLM_Partition_ID()
	{
		final Integer ii = (Integer)get_Value(COLUMNNAME_DLM_Partition_ID);
		if (ii == null)
		{
			return 0;
		}
		return ii.intValue();
	}
}
