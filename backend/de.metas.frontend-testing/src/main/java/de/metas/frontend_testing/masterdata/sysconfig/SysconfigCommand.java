package de.metas.frontend_testing.masterdata.sysconfig;

import com.google.common.collect.ImmutableMap;
import de.metas.organization.ClientAndOrgId;
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

@Builder
public class SysconfigCommand
{
	@Nullable private final Map<String, String> sysconfigs;

	/**
	 * Sets the given sysconfigs and returns the previous values.
	 *
	 * @return map from sysconfig name to its previous value (null if it didn't exist before)
	 */
	public ImmutableMap<String, String> execute()
	{
		if (sysconfigs == null || sysconfigs.isEmpty())
		{
			return ImmutableMap.of();
		}

		final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

		final ImmutableMap.Builder<String, String> previousValues = ImmutableMap.builder();

		for (final Map.Entry<String, String> entry : sysconfigs.entrySet())
		{
			final String name = entry.getKey();
			final String newValue = entry.getValue();

			final ClientAndOrgId target = computeTarget(name);

			// Capture the value that exists at exactly this level (not the cascaded/effective value),
			// so a later restore via setSysconfigs round-trips back to the same level.
			// Note: AD_SysConfig has no delete API, so a sysconfig that did not exist at this level
			// beforehand cannot be fully removed on restore - acceptable for test masterdata.
			final String previousValue = retrieveValueAtLevel(name, target);
			if (previousValue != null)
			{
				previousValues.put(name, previousValue);
			}

			sysConfigBL.setValue(name, newValue, target.getClientId(), target.getOrgId());
		}

		return previousValues.build();
	}

	/**
	 * Determines the (client, org) at which the given sysconfig must be written, based on its
	 * {@code ConfigurationLevel} as declared on the system record. System/Client-level parameters
	 * cannot be saved at org level (the {@code AD_SysConfig} interceptor throws "Can't Save Org
	 * Level ..."), so we target the matching level:
	 * <ul>
	 *     <li>System ({@code S}) → client=SYSTEM, org=ANY</li>
	 *     <li>Client ({@code C}) → client=METASFRESH, org=ANY</li>
	 *     <li>Organization ({@code O}) or unknown → client=METASFRESH, org=MAIN</li>
	 * </ul>
	 */
	private static ClientAndOrgId computeTarget(@NonNull final String name)
	{
		final I_AD_SysConfig systemRecord = retrieveRecordAtLevel(name, ClientAndOrgId.SYSTEM);
		final String configurationLevel = systemRecord != null ? systemRecord.getConfigurationLevel() : null;

		if (X_AD_SysConfig.CONFIGURATIONLEVEL_System.equals(configurationLevel))
		{
			return ClientAndOrgId.SYSTEM;
		}
		else if (X_AD_SysConfig.CONFIGURATIONLEVEL_Client.equals(configurationLevel))
		{
			return ClientAndOrgId.ofClientAndOrg(ClientId.METASFRESH, OrgId.ANY);
		}
		else
		{
			return ClientAndOrgId.MAIN;
		}
	}

	@Nullable
	private static String retrieveValueAtLevel(@NonNull final String name, @NonNull final ClientAndOrgId clientAndOrgId)
	{
		final I_AD_SysConfig record = retrieveRecordAtLevel(name, clientAndOrgId);
		return record != null ? record.getValue() : null;
	}

	/**
	 * Reads the exact {@code AD_SysConfig} record for the given name at the given client/org.
	 * Intentionally does NOT filter on {@code IsActive}, to mirror the {@code AD_SysConfig}
	 * interceptor's own {@code retrieveConfigLevel} lookup (which also ignores IsActive) - so that
	 * {@link #computeTarget} predicts exactly whether the interceptor would reject the write.
	 */
	@Nullable
	private static I_AD_SysConfig retrieveRecordAtLevel(@NonNull final String name, @NonNull final ClientAndOrgId clientAndOrgId)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_SysConfig.class)
				.addEqualsFilter(I_AD_SysConfig.COLUMNNAME_Name, name)
				.addEqualsFilter(I_AD_SysConfig.COLUMNNAME_AD_Client_ID, clientAndOrgId.getClientId().getRepoId())
				.addEqualsFilter(I_AD_SysConfig.COLUMNNAME_AD_Org_ID, clientAndOrgId.getOrgId().getRepoId())
				.create()
				.first(I_AD_SysConfig.class);
	}
}
