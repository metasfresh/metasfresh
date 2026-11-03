package de.metas.frontend_testing.masterdata.sysconfig;

import de.metas.organization.ClientAndOrgId;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.service.ISysConfigDAO;

import javax.annotation.Nullable;
import java.util.Map;

/**
 * Writes sysconfigs as SYSTEM rows (AD_Client_ID=0, AD_Org_ID=0), like the cucumber {@code AD_SysConfig_StepDef} does.
 * Needed for sysconfigs that are read only at SYSTEM level (e.g. via {@code ISysConfigBL#getBooleanValue(String, boolean)}),
 * which {@link SysconfigCommand} (config-level write) would not affect.
 * <p>
 * A {@code null} value unsets the SYSTEM row (stored as the no-value marker); that is how a previously missing row is restored.
 * Boolean and int reads then fall back to their default like for a missing row, but a plain string read returns "" instead of the default.
 */
@Builder
public class SystemSysconfigCommand
{
	@NonNull private final ISysConfigDAO sysConfigDAO = Services.get(ISysConfigDAO.class);

	@Nullable private final Map<String, String> systemSysconfigs;
	/** Receives the previous SYSTEM row of each sysconfig, all before the first write, so that the caller can restore them even if a write fails. */
	@NonNull private final PreviousSysconfigsCollector previousSysconfigsCollector;

	public void execute()
	{
		if (systemSysconfigs == null || systemSysconfigs.isEmpty())
		{
			return;
		}

		systemSysconfigs.keySet().forEach(name -> previousSysconfigsCollector.add(
				JsonSysconfig.of(name, sysConfigDAO.getValueOfExactRow(name, ClientAndOrgId.SYSTEM).orElse(null), ClientAndOrgId.SYSTEM)));

		systemSysconfigs.forEach((name, value) -> sysConfigDAO.setValue(name, value, ClientAndOrgId.SYSTEM));
	}
}
