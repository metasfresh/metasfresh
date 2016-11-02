/** Generated Model - DO NOT CHANGE */
package de.metas.dlm.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Generated Model for DLM_Partition_Record
 * 
 * @author Adempiere (generated)
 */
@SuppressWarnings("javadoc")
public class X_DLM_Partition_Record extends org.compiere.model.PO implements I_DLM_Partition_Record, org.compiere.model.I_Persistent
{

	/**
	 *
	 */
	private static final long serialVersionUID = -884188081L;

	/** Standard Constructor */
	public X_DLM_Partition_Record(final Properties ctx, final int DLM_Partition_Record_ID, final String trxName)
	{
		super(ctx, DLM_Partition_Record_ID, trxName);
		/**
		 * if (DLM_Partition_Record_ID == 0)
		 * {
		 * setDLM_Partition_Records_ID (0);
		 * setIsDLM (false);
		 * }
		 */
	}

	/** Load Constructor */
	public X_DLM_Partition_Record(final Properties ctx, final ResultSet rs, final String trxName)
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
	public org.compiere.model.I_AD_Table getAD_Table() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_Table_ID, org.compiere.model.I_AD_Table.class);
	}

	@Override
	public void setAD_Table(final org.compiere.model.I_AD_Table AD_Table)
	{
		set_ValueFromPO(COLUMNNAME_AD_Table_ID, org.compiere.model.I_AD_Table.class, AD_Table);
	}

	/**
	 * Set DB-Tabelle.
	 * 
	 * @param AD_Table_ID
	 *            Database Table information
	 */
	@Override
	public void setAD_Table_ID(final int AD_Table_ID)
	{
		if (AD_Table_ID < 1)
		{
			set_ValueNoCheck(COLUMNNAME_AD_Table_ID, null);
		}
		else
		{
			set_ValueNoCheck(COLUMNNAME_AD_Table_ID, Integer.valueOf(AD_Table_ID));
		}
	}

	/**
	 * Get DB-Tabelle.
	 * 
	 * @return Database Table information
	 */
	@Override
	public int getAD_Table_ID()
	{
		final Integer ii = (Integer)get_Value(COLUMNNAME_AD_Table_ID);
		if (ii == null)
		{
			return 0;
		}
		return ii.intValue();
	}

	/**
	 * Set DLM_Level.
	 * 
	 * @param DLM_Level DLM_Level
	 */
	@Override
	public void setDLM_Level(final int DLM_Level)
	{
		set_ValueNoCheck(COLUMNNAME_DLM_Level, Integer.valueOf(DLM_Level));
	}

	/**
	 * Get DLM_Level.
	 * 
	 * @return DLM_Level
	 */
	@Override
	public int getDLM_Level()
	{
		final Integer ii = (Integer)get_Value(COLUMNNAME_DLM_Level);
		if (ii == null)
		{
			return 0;
		}
		return ii.intValue();
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

	@Override
	public de.metas.dlm.model.I_DLM_Partition getDLM_Partition() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_DLM_Partition_ID, de.metas.dlm.model.I_DLM_Partition.class);
	}

	@Override
	public void setDLM_Partition(final de.metas.dlm.model.I_DLM_Partition DLM_Partition)
	{
		set_ValueFromPO(COLUMNNAME_DLM_Partition_ID, de.metas.dlm.model.I_DLM_Partition.class, DLM_Partition);
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
	 * Set DLM_Partition_Records.
	 * 
	 * @param DLM_Partition_Records_ID DLM_Partition_Records
	 */
	@Override
	public void setDLM_Partition_Records_ID(final int DLM_Partition_Records_ID)
	{
		if (DLM_Partition_Records_ID < 1)
		{
			set_ValueNoCheck(COLUMNNAME_DLM_Partition_Records_ID, null);
		}
		else
		{
			set_ValueNoCheck(COLUMNNAME_DLM_Partition_Records_ID, Integer.valueOf(DLM_Partition_Records_ID));
		}
	}

	/**
	 * Get DLM_Partition_Records.
	 * 
	 * @return DLM_Partition_Records
	 */
	@Override
	public int getDLM_Partition_Records_ID()
	{
		final Integer ii = (Integer)get_Value(COLUMNNAME_DLM_Partition_Records_ID);
		if (ii == null)
		{
			return 0;
		}
		return ii.intValue();
	}

	/**
	 * Set DLM aktiviert.
	 * 
	 * @param IsDLM
	 *            Die Datensätze einer Tabelle mit aktiviertem DLM können vom System unterschiedlichen DLM-Levels zugeordnet werden
	 */
	@Override
	public void setIsDLM(final boolean IsDLM)
	{
		set_ValueNoCheck(COLUMNNAME_IsDLM, Boolean.valueOf(IsDLM));
	}

	/**
	 * Get DLM aktiviert.
	 * 
	 * @return Die Datensätze einer Tabelle mit aktiviertem DLM können vom System unterschiedlichen DLM-Levels zugeordnet werden
	 */
	@Override
	public boolean isDLM()
	{
		final Object oo = get_Value(COLUMNNAME_IsDLM);
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
	 * Set Datensatz-ID.
	 * 
	 * @param Record_ID
	 *            Direct internal record ID
	 */
	@Override
	public void setRecord_ID(final int Record_ID)
	{
		if (Record_ID < 0)
		{
			set_ValueNoCheck(COLUMNNAME_Record_ID, null);
		}
		else
		{
			set_ValueNoCheck(COLUMNNAME_Record_ID, Integer.valueOf(Record_ID));
		}
	}

	/**
	 * Get Datensatz-ID.
	 * 
	 * @return Direct internal record ID
	 */
	@Override
	public int getRecord_ID()
	{
		final Integer ii = (Integer)get_Value(COLUMNNAME_Record_ID);
		if (ii == null)
		{
			return 0;
		}
		return ii.intValue();
	}

	/**
	 * Set Name der DB-Tabelle.
	 * 
	 * @param TableName Name der DB-Tabelle
	 */
	@Override
	public void setTableName(final java.lang.String TableName)
	{
		set_ValueNoCheck(COLUMNNAME_TableName, TableName);
	}

	/**
	 * Get Name der DB-Tabelle.
	 * 
	 * @return Name der DB-Tabelle
	 */
	@Override
	public java.lang.String getTableName()
	{
		return (java.lang.String)get_Value(COLUMNNAME_TableName);
	}
}
