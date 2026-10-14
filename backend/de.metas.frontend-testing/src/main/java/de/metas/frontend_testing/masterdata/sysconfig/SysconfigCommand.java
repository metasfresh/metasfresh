package de.metas.frontend_testing.masterdata.sysconfig;

import com.google.common.collect.ImmutableMap;
import de.metas.organization.ClientAndOrgId;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
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
	@NonNull private final ISysConfigBL sysConfigBL;

	@Nullable private final Map<String, String> sysconfigs;

	/**
	 * @return map from sysconfig name to its previous value, captured before any write as the value effective for the METASFRESH client / MAIN org
	 * (falls back to client/system level); a name that did not exist before maps to {@code null}
	 * @throws AdempiereException (HTTP 422) if any value is {@code null}; checked before anything is written
	 */
	public Map<String, String> execute()
	{
		if (sysconfigs == null || sysconfigs.isEmpty())
		{
			return ImmutableMap.of();
		}

		sysconfigs.forEach((name, value) -> {
			if (value == null)
			{
				throw new AdempiereException("Null value not supported for sysconfig `" + name + "`; set a neutral value instead");
			}
		});

		final HashMap<String, String> previousValues = new HashMap<>();
		sysconfigs.keySet().forEach(name -> previousValues.put(name, sysConfigBL.getValue(name, ClientAndOrgId.MAIN_ORG)));

		sysconfigs.forEach(sysConfigBL::setValueAtConfigLevel);

		return previousValues;
	}
}
