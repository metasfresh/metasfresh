package de.metas.security.impl;

import org.compiere.model.POInfo;

import de.metas.security.TableAccessLevel;

/**
 * Supporting service for role permissions which basically provide table informations.
 *
 * NOTE: we are keeping it isolated because we intent to make it pluggable in future.
 *
 * @author tsa
 *
 */
public class TablesAccessInfo
{
	public static final transient TablesAccessInfo instance = new TablesAccessInfo();

	// private final transient Logger logger = CLogMgt.getLogger(getClass());

	private TablesAccessInfo()
	{
		super();
	}

	/**
	 * Gets table's access level.
	 *
	 * @param adTableId
	 * @return table's access level or null if no table was found.
	 */
	public final TableAccessLevel getTableAccessLevel(final int adTableId)
	{
		final POInfo poInfo = POInfo.getPOInfo(adTableId);
		if (poInfo == null)
		{
			return null;
		}
		return poInfo.getAccessLevel();
	}

	/**
	 * Check if tableName is a view
	 *
	 * @param tableName
	 * @return boolean
	 */
	public boolean isView(final String tableName)
	{
		final POInfo poInfo = POInfo.getPOInfo(tableName);
		return poInfo == null ? false : poInfo.isView();
	}

	public String getSingleKeyColumnNameOrNull(final String tableName)
	{
		final POInfo poInfo = POInfo.getPOInfo(tableName);
		return poInfo == null ? null : poInfo.getKeyColumnName();
	}

	public int getAdTableId(final String tableName)
	{
		final POInfo poInfo = POInfo.getPOInfo(tableName);
		return poInfo == null ? 0 : poInfo.getAD_Table_ID();
	}

	public boolean isPhysicalColumn(final String tableName, final String columnName)
	{
		final POInfo poInfo = POInfo.getPOInfo(tableName);
		if (poInfo == null)
		{
			return false;
		}
		if (!poInfo.hasColumnName(columnName))
		{
			return false;
		}
		if (poInfo.isVirtualColumn(columnName))
		{
			return false;
		}

		return true;
	}
}
