package de.metas.frontend_testing.masterdata.sysconfig;

import com.google.common.collect.ImmutableMap;
import de.metas.organization.ClientAndOrgId;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.service.ClientId;
import org.adempiere.service.ISysConfigBL;

import javax.annotation.Nullable;
import java.util.Map;

/**
 * Writes sysconfigs as SYSTEM rows (AD_Client_ID=0, AD_Org_ID=0), like the cucumber {@code AD_SysConfig_StepDef} does.
 * Needed for sysconfigs that are read only at SYSTEM level (e.g. via {@code ISysConfigBL#getBooleanValue(String, boolean)}),
 * which {@link SysconfigCommand} (config-level write) would not affect.
 */
@Builder
public class SystemSysconfigCommand
{
	@NonNull private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

	@Nullable private final Map<String, String> systemSysconfigs;

	/**
	 * @return map from sysconfig name to its previous SYSTEM-level value, captured before any write; names without a SYSTEM row are omitted
	 */
	public ImmutableMap<String, String> execute()
	{
		if (systemSysconfigs == null || systemSysconfigs.isEmpty())
		{
			return ImmutableMap.of();
		}

		final ImmutableMap.Builder<String, String> previousValues = ImmutableMap.builder();
		systemSysconfigs.keySet().forEach(name -> {
			final String prev = sysConfigBL.getValue(name, ClientAndOrgId.SYSTEM);
			if (prev != null)
			{
				previousValues.put(name, prev);
			}
		});

		systemSysconfigs.forEach((name, value) -> sysConfigBL.setValue(name, value, ClientId.SYSTEM, ClientAndOrgId.SYSTEM.getOrgId()));

		return previousValues.build();
	}
}
