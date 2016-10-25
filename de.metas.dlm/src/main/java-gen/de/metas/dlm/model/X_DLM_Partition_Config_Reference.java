/** Generated Model - DO NOT CHANGE */
package de.metas.dlm.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Generated Model for DLM_Partition_Config_Reference
 *
 * @author Adempiere (generated)
 */
@SuppressWarnings("javadoc")
public class X_DLM_Partition_Config_Reference extends org.compiere.model.PO implements I_DLM_Partition_Config_Reference, org.compiere.model.I_Persistent
{

	/**
	 *
	 */
	private static final long serialVersionUID = 694000358L;

	/** Standard Constructor */
	public X_DLM_Partition_Config_Reference(final Properties ctx, final int DLM_Partition_Config_Reference_ID, final String trxName)
	{
		super(ctx, DLM_Partition_Config_Reference_ID, trxName);
		/**
		 * if (DLM_Partition_Config_Reference_ID == 0)
		 * {
		 * setDLM_Partition_Config_Line_ID (0);
		 * setDLM_Partition_Config_Reference_ID (0);
		 * setDLM_Referenced_Table_ID (0);
		 * setDLM_Referencing_Column_ID (0);
		 * }
		 */
	}

	/** Load Constructor */
	public X_DLM_Partition_Config_Reference(final Properties ctx, final ResultSet rs, final String trxName)
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
	public de.metas.dlm.model.I_DLM_Partition_Config_Line getDLM_Partition_Config_Line() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_DLM_Partition_Config_Line_ID, de.metas.dlm.model.I_DLM_Partition_Config_Line.class);
	}

	@Override
	public void setDLM_Partition_Config_Line(final de.metas.dlm.model.I_DLM_Partition_Config_Line DLM_Partition_Config_Line)
	{
		set_ValueFromPO(COLUMNNAME_DLM_Partition_Config_Line_ID, de.metas.dlm.model.I_DLM_Partition_Config_Line.class, DLM_Partition_Config_Line);
	}

	/**
	 * Set DLM Partitionierungskonfigzeile.
	 *
	 * @param DLM_Partition_Config_Line_ID DLM Partitionierungskonfigzeile
	 */
	@Override
	public void setDLM_Partition_Config_Line_ID(final int DLM_Partition_Config_Line_ID)
	{
		if (DLM_Partition_Config_Line_ID < 1)
		{
			set_ValueNoCheck(COLUMNNAME_DLM_Partition_Config_Line_ID, null);
		}
		else
		{
			set_ValueNoCheck(COLUMNNAME_DLM_Partition_Config_Line_ID, Integer.valueOf(DLM_Partition_Config_Line_ID));
		}
	}

	/**
	 * Get DLM Partitionierungskonfigzeile.
	 *
	 * @return DLM Partitionierungskonfigzeile
	 */
	@Override
	public int getDLM_Partition_Config_Line_ID()
	{
		final Integer ii = (Integer)get_Value(COLUMNNAME_DLM_Partition_Config_Line_ID);
		if (ii == null)
		{
			return 0;
		}
		return ii.intValue();
	}

	/**
	 * Set DLM Partitionierungkonfigurationsreferenz.
	 *
	 * @param DLM_Partition_Config_Reference_ID DLM Partitionierungkonfigurationsreferenz
	 */
	@Override
	public void setDLM_Partition_Config_Reference_ID(final int DLM_Partition_Config_Reference_ID)
	{
		if (DLM_Partition_Config_Reference_ID < 1)
		{
			set_ValueNoCheck(COLUMNNAME_DLM_Partition_Config_Reference_ID, null);
		}
		else
		{
			set_ValueNoCheck(COLUMNNAME_DLM_Partition_Config_Reference_ID, Integer.valueOf(DLM_Partition_Config_Reference_ID));
		}
	}

	/**
	 * Get DLM Partitionierungkonfigurationsreferenz.
	 *
	 * @return DLM Partitionierungkonfigurationsreferenz
	 */
	@Override
	public int getDLM_Partition_Config_Reference_ID()
	{
		final Integer ii = (Integer)get_Value(COLUMNNAME_DLM_Partition_Config_Reference_ID);
		if (ii == null)
		{
			return 0;
		}
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_AD_Table getDLM_Referenced_Table() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_DLM_Referenced_Table_ID, org.compiere.model.I_AD_Table.class);
	}

	@Override
	public void setDLM_Referenced_Table(final org.compiere.model.I_AD_Table DLM_Referenced_Table)
	{
		set_ValueFromPO(COLUMNNAME_DLM_Referenced_Table_ID, org.compiere.model.I_AD_Table.class, DLM_Referenced_Table);
	}

	/**
	 * Set Referenzierte Tabelle.
	 *
	 * @param DLM_Referenced_Table_ID Referenzierte Tabelle
	 */
	@Override
	public void setDLM_Referenced_Table_ID(final int DLM_Referenced_Table_ID)
	{
		if (DLM_Referenced_Table_ID < 1)
		{
			set_Value(COLUMNNAME_DLM_Referenced_Table_ID, null);
		}
		else
		{
			set_Value(COLUMNNAME_DLM_Referenced_Table_ID, Integer.valueOf(DLM_Referenced_Table_ID));
		}
	}

	/**
	 * Get Referenzierte Tabelle.
	 *
	 * @return Referenzierte Tabelle
	 */
	@Override
	public int getDLM_Referenced_Table_ID()
	{
		final Integer ii = (Integer)get_Value(COLUMNNAME_DLM_Referenced_Table_ID);
		if (ii == null)
		{
			return 0;
		}
		return ii.intValue();
	}

	@Override
	public de.metas.dlm.model.I_DLM_Partition_Config_Line getDLM_Referenced_Table_Partition_Config_Line() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_DLM_Referenced_Table_Partition_Config_Line_ID, de.metas.dlm.model.I_DLM_Partition_Config_Line.class);
	}

	@Override
	public void setDLM_Referenced_Table_Partition_Config_Line(final de.metas.dlm.model.I_DLM_Partition_Config_Line DLM_Referenced_Table_Partition_Config_Line)
	{
		set_ValueFromPO(COLUMNNAME_DLM_Referenced_Table_Partition_Config_Line_ID, de.metas.dlm.model.I_DLM_Partition_Config_Line.class, DLM_Referenced_Table_Partition_Config_Line);
	}

	/**
	 * Set Konfig-Zeile zur referenzierten Tabelle.
	 *
	 * @param DLM_Referenced_Table_Partition_Config_Line_ID Konfig-Zeile zur referenzierten Tabelle
	 */
	@Override
	public void setDLM_Referenced_Table_Partition_Config_Line_ID(final int DLM_Referenced_Table_Partition_Config_Line_ID)
	{
		if (DLM_Referenced_Table_Partition_Config_Line_ID < 1)
		{
			set_Value(COLUMNNAME_DLM_Referenced_Table_Partition_Config_Line_ID, null);
		}
		else
		{
			set_Value(COLUMNNAME_DLM_Referenced_Table_Partition_Config_Line_ID, Integer.valueOf(DLM_Referenced_Table_Partition_Config_Line_ID));
		}
	}

	/**
	 * Get Konfig-Zeile zur referenzierten Tabelle.
	 *
	 * @return Konfig-Zeile zur referenzierten Tabelle
	 */
	@Override
	public int getDLM_Referenced_Table_Partition_Config_Line_ID()
	{
		final Integer ii = (Integer)get_Value(COLUMNNAME_DLM_Referenced_Table_Partition_Config_Line_ID);
		if (ii == null)
		{
			return 0;
		}
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_AD_Column getDLM_Referencing_Column() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_DLM_Referencing_Column_ID, org.compiere.model.I_AD_Column.class);
	}

	@Override
	public void setDLM_Referencing_Column(final org.compiere.model.I_AD_Column DLM_Referencing_Column)
	{
		set_ValueFromPO(COLUMNNAME_DLM_Referencing_Column_ID, org.compiere.model.I_AD_Column.class, DLM_Referencing_Column);
	}

	/**
	 * Set Referenzierende Spalte.
	 *
	 * @param DLM_Referencing_Column_ID Referenzierende Spalte
	 */
	@Override
	public void setDLM_Referencing_Column_ID(final int DLM_Referencing_Column_ID)
	{
		if (DLM_Referencing_Column_ID < 1)
		{
			set_Value(COLUMNNAME_DLM_Referencing_Column_ID, null);
		}
		else
		{
			set_Value(COLUMNNAME_DLM_Referencing_Column_ID, Integer.valueOf(DLM_Referencing_Column_ID));
		}
	}

	/**
	 * Get Referenzierende Spalte.
	 *
	 * @return Referenzierende Spalte
	 */
	@Override
	public int getDLM_Referencing_Column_ID()
	{
		final Integer ii = (Integer)get_Value(COLUMNNAME_DLM_Referencing_Column_ID);
		if (ii == null)
		{
			return 0;
		}
		return ii.intValue();
	}
}
