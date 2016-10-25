/** Generated Model - DO NOT CHANGE */
package de.metas.dlm.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Generated Model for DLM_Partition_Config_Line
 *
 * @author Adempiere (generated)
 */
@SuppressWarnings("javadoc")
public class X_DLM_Partition_Config_Line extends org.compiere.model.PO implements I_DLM_Partition_Config_Line, org.compiere.model.I_Persistent
{

	/**
	 *
	 */
	private static final long serialVersionUID = -1101858019L;

	/** Standard Constructor */
	public X_DLM_Partition_Config_Line(final Properties ctx, final int DLM_Partition_Config_Line_ID, final String trxName)
	{
		super(ctx, DLM_Partition_Config_Line_ID, trxName);
		/**
		 * if (DLM_Partition_Config_Line_ID == 0)
		 * {
		 * setDLM_Partition_Config_ID (0);
		 * setDLM_Partition_Config_Line_ID (0);
		 * setDLM_Referencing_Table_ID (0);
		 * }
		 */
	}

	/** Load Constructor */
	public X_DLM_Partition_Config_Line(final Properties ctx, final ResultSet rs, final String trxName)
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

	@Override
	public org.compiere.model.I_AD_Table getDLM_Referencing_Table() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_DLM_Referencing_Table_ID, org.compiere.model.I_AD_Table.class);
	}

	@Override
	public void setDLM_Referencing_Table(final org.compiere.model.I_AD_Table DLM_Referencing_Table)
	{
		set_ValueFromPO(COLUMNNAME_DLM_Referencing_Table_ID, org.compiere.model.I_AD_Table.class, DLM_Referencing_Table);
	}

	/**
	 * Set Referenzierende Tabelle.
	 *
	 * @param DLM_Referencing_Table_ID Referenzierende Tabelle
	 */
	@Override
	public void setDLM_Referencing_Table_ID(final int DLM_Referencing_Table_ID)
	{
		if (DLM_Referencing_Table_ID < 1)
		{
			set_Value(COLUMNNAME_DLM_Referencing_Table_ID, null);
		}
		else
		{
			set_Value(COLUMNNAME_DLM_Referencing_Table_ID, Integer.valueOf(DLM_Referencing_Table_ID));
		}
	}

	/**
	 * Get Referenzierende Tabelle.
	 *
	 * @return Referenzierende Tabelle
	 */
	@Override
	public int getDLM_Referencing_Table_ID()
	{
		final Integer ii = (Integer)get_Value(COLUMNNAME_DLM_Referencing_Table_ID);
		if (ii == null)
		{
			return 0;
		}
		return ii.intValue();
	}
}
