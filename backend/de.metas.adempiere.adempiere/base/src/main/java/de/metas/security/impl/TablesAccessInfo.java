package de.metas.security.impl;

import de.metas.security.TableAccessLevel;
import org.adempiere.ad.table.api.AdTableId;
import org.compiere.model.POInfo;

import javax.annotation.Nullable;
import java.util.Optional;

/**
 * Supporting service for role permissions which basically provide table informations.
 * <p>
 * NOTE: we are keeping it isolated because we intent to make it pluggable in future.
 *
 * @author tsa
 */
public class TablesAccessInfo
{
	public static final transient TablesAccessInfo instance = new TablesAccessInfo();

	private TablesAccessInfo()
	{
	}

	/**
	 * @return table's access level or null if no table was found.
	 */
	@Nullable
	public final TableAccessLevel getTableAccessLevel(final int adTableId)
	{
		return POInfo.getPOInfoIfPresent(AdTableId.ofRepoId(adTableId)).map(POInfo::getAccessLevel).orElse(null);
	}

	public boolean isView(final String tableName)
	{
		return POInfo.getPOInfoIfPresent(tableName).map(POInfo::isView).orElse(Boolean.FALSE);
	}

	@Nullable
	public String getSingleKeyColumnNameOrNull(final String tableName)
	{
		return POInfo.getPOInfoIfPresent(tableName).map(POInfo::getKeyColumnName).orElse(null);
	}

	public Optional<AdTableId> getAdTableId(final String tableName)
	{
		return POInfo.getPOInfoIfPresent(tableName).map(POInfo::getAdTableId);
	}
}
