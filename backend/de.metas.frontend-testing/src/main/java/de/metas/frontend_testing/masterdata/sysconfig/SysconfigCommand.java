package de.metas.frontend_testing.masterdata.sysconfig;

import com.google.common.collect.ImmutableMap;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.service.ClientId;
import org.adempiere.service.ISysConfigBL;
import org.compiere.model.I_AD_SysConfig;
import org.compiere.model.X_AD_SysConfig;

import javax.annotation.Nullable;
import java.util.Map;

/**
 * Applies per-test sysconfig overrides and reports the previous values so the test can restore them.
 */
@Builder
public class SysconfigCommand
{
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);
	@NonNull private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

	@Nullable private final Map<String, String> sysconfigs;

	/**
	 * @return map from sysconfig name to its previous value, captured before any write (names without a previous value are omitted)
	 */
	public ImmutableMap<String, String> execute()
	{
		if (sysconfigs == null || sysconfigs.isEmpty())
		{
			return ImmutableMap.of();
		}

		final ImmutableMap.Builder<String, String> previousValues = ImmutableMap.builder();
		for (final String name : sysconfigs.keySet())
		{
			final String prev = sysConfigBL.getValue(name);
			if (prev != null)
			{
				previousValues.put(name, prev);
			}
		}

		sysconfigs.forEach(this::setValueAtConfigLevel);

		return previousValues.build();
	}

	/**
	 * Writes the sysconfig at the (client, org) matching its declared ConfigurationLevel,
	 * so the AD_SysConfig model interceptor does not reject the write.
	 */
	private void setValueAtConfigLevel(@NonNull final String name, @NonNull final String value)
	{
		final I_AD_SysConfig record = queryBL.createQueryBuilder(I_AD_SysConfig.class)
				.addEqualsFilter(I_AD_SysConfig.COLUMNNAME_Name, name)
				.addOnlyActiveRecordsFilter()
				.create()
				.first(I_AD_SysConfig.class);
		final String level = record != null ? record.getConfigurationLevel() : null;

		if (X_AD_SysConfig.CONFIGURATIONLEVEL_System.equals(level))
		{
			sysConfigBL.setValue(name, value, ClientId.SYSTEM, OrgId.ANY);
		}
		else if (X_AD_SysConfig.CONFIGURATIONLEVEL_Client.equals(level))
		{
			sysConfigBL.setValue(name, value, ClientId.METASFRESH, OrgId.ANY);
		}
		else
		{
			sysConfigBL.setValue(name, value, ClientId.METASFRESH, OrgId.MAIN);
		}
	}
}
