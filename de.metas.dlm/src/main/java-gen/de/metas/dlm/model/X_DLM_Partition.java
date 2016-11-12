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
	private static final long serialVersionUID = 1564041532L;

	/** Standard Constructor */
	public X_DLM_Partition(final Properties ctx, final int DLM_Partition_ID, final String trxName)
	{
		super(ctx, DLM_Partition_ID, trxName);
		/**
		 * if (DLM_Partition_ID == 0)
		 * {
		 * setDLM_Partition_Config_ID (0);
		 * setDLM_Partition_ID (0);
		 * setIsPartitionComplete (false);
		 * // N
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

	/**
	 * Set aktueller DLM-Level.
	 * 
	 * @param Current_DLM_Level aktueller DLM-Level
	 */
	@Override
	public void setCurrent_DLM_Level(final int Current_DLM_Level)
	{
		set_Value(COLUMNNAME_Current_DLM_Level, Integer.valueOf(Current_DLM_Level));
	}

	/**
	 * Get aktueller DLM-Level.
	 * 
	 * @return aktueller DLM-Level
	 */
	@Override
	public int getCurrent_DLM_Level()
	{
		final Integer ii = (Integer)get_Value(COLUMNNAME_Current_DLM_Level);
		if (ii == null)
		{
			return 0;
		}
		return ii.intValue();
	}

	/**
	 * Set Nächte Prüfung des DLM-Levels.
	 * 
	 * @param DateNextInspection Nächte Prüfung des DLM-Levels
	 */
	@Override
	public void setDateNextInspection(final java.sql.Timestamp DateNextInspection)
	{
		set_Value(COLUMNNAME_DateNextInspection, DateNextInspection);
	}

	/**
	 * Get Nächte Prüfung des DLM-Levels.
	 * 
	 * @return Nächte Prüfung des DLM-Levels
	 */
	@Override
	public java.sql.Timestamp getDateNextInspection()
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_DateNextInspection);
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
			set_Value(COLUMNNAME_DLM_Partition_Config_ID, null);
		}
		else
		{
			set_Value(COLUMNNAME_DLM_Partition_Config_ID, Integer.valueOf(DLM_Partition_Config_ID));
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

	/**
	 * Set Partition is vollständig.
	 * 
	 * @param IsPartitionComplete
	 *            Sagt aus, ob das System dieser Partition noch weitere Datensätze hinzufügen muss bevor sie als Ganzes verschoben werden kann.
	 */
	@Override
	public void setIsPartitionComplete(final boolean IsPartitionComplete)
	{
		set_Value(COLUMNNAME_IsPartitionComplete, Boolean.valueOf(IsPartitionComplete));
	}

	/**
	 * Get Partition is vollständig.
	 * 
	 * @return Sagt aus, ob das System dieser Partition noch weitere Datensätze hinzufügen muss bevor sie als Ganzes verschoben werden kann.
	 */
	@Override
	public boolean isPartitionComplete()
	{
		final Object oo = get_Value(COLUMNNAME_IsPartitionComplete);
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
	 * Set Anz. zugeordneter Datensätze.
	 * 
	 * @param PartitionSize Anz. zugeordneter Datensätze
	 */
	@Override
	public void setPartitionSize(final int PartitionSize)
	{
		set_Value(COLUMNNAME_PartitionSize, Integer.valueOf(PartitionSize));
	}

	/**
	 * Get Anz. zugeordneter Datensätze.
	 * 
	 * @return Anz. zugeordneter Datensätze
	 */
	@Override
	public int getPartitionSize()
	{
		final Integer ii = (Integer)get_Value(COLUMNNAME_PartitionSize);
		if (ii == null)
		{
			return 0;
		}
		return ii.intValue();
	}

	/**
	 * Set Ziel-DLM-Level.
	 * 
	 * @param Target_DLM_Level Ziel-DLM-Level
	 */
	@Override
	public void setTarget_DLM_Level(final int Target_DLM_Level)
	{
		set_Value(COLUMNNAME_Target_DLM_Level, Integer.valueOf(Target_DLM_Level));
	}

	/**
	 * Get Ziel-DLM-Level.
	 * 
	 * @return Ziel-DLM-Level
	 */
	@Override
	public int getTarget_DLM_Level()
	{
		final Integer ii = (Integer)get_Value(COLUMNNAME_Target_DLM_Level);
		if (ii == null)
		{
			return 0;
		}
		return ii.intValue();
	}
}
