package de.metas.frontend_testing.masterdata.sysconfig;

import de.metas.cache.CacheMgt;
import de.metas.organization.ClientAndOrgId;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.service.ClientId;
import org.adempiere.service.ISysConfigBL;
import org.compiere.model.I_AD_SysConfig;

import javax.annotation.Nullable;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Writes sysconfigs as SYSTEM rows (AD_Client_ID=0, AD_Org_ID=0), like the cucumber {@code AD_SysConfig_StepDef} does.
 * Needed for sysconfigs that are read only at SYSTEM level (e.g. via {@code ISysConfigBL#getBooleanValue(String, boolean)}),
 * which {@link SysconfigCommand} (config-level write) would not affect.
 * <p>
 * A {@code null} value deletes the SYSTEM row; that is how a previously missing row is restored.
 */
@Builder
public class SystemSysconfigCommand
{
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);
	@NonNull private final ISysConfigBL sysConfigBL;

	@Nullable private final Map<String, String> systemSysconfigs;

	/**
	 * @return map from sysconfig name to its previous SYSTEM-level value, captured before any write;
	 * the value is {@code null} for names that had no SYSTEM row, so that restoring them deletes the row again
	 */
	public Map<String, String> execute()
	{
		if (systemSysconfigs == null || systemSysconfigs.isEmpty())
		{
			return new LinkedHashMap<>();
		}

		final LinkedHashMap<String, String> previousValues = new LinkedHashMap<>();
		for (final String name : systemSysconfigs.keySet())
		{
			previousValues.put(name, sysConfigBL.getValue(name, ClientAndOrgId.SYSTEM));
		}

		systemSysconfigs.forEach((name, value) -> {
			if (value == null)
			{
				deleteSystemRow(name);
			}
			else
			{
				sysConfigBL.setValue(name, value, ClientId.SYSTEM, ClientAndOrgId.SYSTEM.getOrgId());
			}
		});

		return previousValues;
	}

	private void deleteSystemRow(@NonNull final String name)
	{
		queryBL.createQueryBuilder(I_AD_SysConfig.class)
				.addEqualsFilter(I_AD_SysConfig.COLUMNNAME_Name, name)
				.addEqualsFilter(I_AD_SysConfig.COLUMNNAME_AD_Client_ID, ClientId.SYSTEM)
				.addEqualsFilter(I_AD_SysConfig.COLUMNNAME_AD_Org_ID, ClientAndOrgId.SYSTEM.getOrgId())
				.create()
				.delete();
		CacheMgt.get().reset(I_AD_SysConfig.Table_Name);
	}
}
