package de.metas.frontend_testing.masterdata.sysconfig;

import com.google.common.collect.ImmutableMap;
import de.metas.organization.ClientAndOrgId;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.service.ISysConfigBL;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

/**
 * Applies per-test sysconfig overrides and reports the previous values so the test can restore them.
 */
@Builder
public class SysconfigCommand
{
	@NonNull private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

	@Nullable private final Map<String, String> sysconfigs;

	/**
	 * @return map from sysconfig name to its previous value, captured before any write as the value effective for the METASFRESH client / MAIN org (falls back to client/system level); a name that had no previous value maps to {@code null}
	 */
	public Map<String, String> execute()
	{
		if (sysconfigs == null || sysconfigs.isEmpty())
		{
			return ImmutableMap.of();
		}

		final HashMap<String, String> previousValues = new HashMap<>();
		sysconfigs.keySet().forEach(name -> previousValues.put(name, sysConfigBL.getValue(name, ClientAndOrgId.MAIN_ORG)));

		sysconfigs.forEach(sysConfigBL::setValueAtConfigLevel);

		return previousValues;
	}
}
