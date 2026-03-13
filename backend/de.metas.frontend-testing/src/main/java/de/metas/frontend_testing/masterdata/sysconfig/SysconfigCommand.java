package de.metas.frontend_testing.masterdata.sysconfig;

import com.google.common.collect.ImmutableMap;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import lombok.Builder;
import org.adempiere.service.ClientId;
import org.adempiere.service.ISysConfigBL;

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
		final int adClientId = ClientId.METASFRESH.getRepoId();
		final int adOrgId = OrgId.MAIN.getRepoId();

		final ImmutableMap.Builder<String, String> previousValues = ImmutableMap.builder();

		for (final Map.Entry<String, String> entry : sysconfigs.entrySet())
		{
			final String name = entry.getKey();
			final String newValue = entry.getValue();

			final String previousValue = sysConfigBL.getValue(name, null, adClientId, adOrgId);
			if (previousValue != null)
			{
				previousValues.put(name, previousValue);
			}

			sysConfigBL.setValue(name, newValue, ClientId.METASFRESH, OrgId.MAIN);
		}

		return previousValues.build();
	}
}
