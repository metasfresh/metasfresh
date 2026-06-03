package de.metas.frontend_testing.masterdata.sysconfig;

import com.google.common.collect.ImmutableMap;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.service.ISysConfigBL;

import javax.annotation.Nullable;
import java.util.LinkedHashSet;
import java.util.Map;

/**
 * Resets the barcode-scanner sysconfigs to their defaults, then applies the per-test overrides.
 * <p>
 * Resetting first means no test inherits another test's leaked scanner state (e.g. a leaked
 * {@code isInputTextReadonly='N'} that would make every later spec's scanner editable). All writes
 * go through {@link ISysConfigBL#setValueAtConfigLevel(String, String)}, which targets the
 * (client,org) matching each sysconfig's declared {@code ConfigurationLevel} so the
 * {@code AD_SysConfig} interceptor does not reject them.
 */
@Builder
public class SysconfigCommand
{
	private static final ImmutableMap<String, String> SCANNER_SYSCONFIG_DEFAULTS = ImmutableMap.of(
			"mobileui.frontend.barcodeScanner.showInputText", "Y",
			"mobileui.frontend.barcodeScanner.isInputTextReadonly", "Y");

	@NonNull private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

	@Nullable private final Map<String, String> sysconfigs;

	/**
	 * @return map from sysconfig name to its previous (effective) value, captured before any write
	 */
	public ImmutableMap<String, String> execute()
	{
		// capture previous EFFECTIVE values for every name we will touch (defaults + overrides), BEFORE writing
		final LinkedHashSet<String> names = new LinkedHashSet<>(SCANNER_SYSCONFIG_DEFAULTS.keySet());
		if (sysconfigs != null)
		{
			names.addAll(sysconfigs.keySet());
		}
		final ImmutableMap.Builder<String, String> previousValues = ImmutableMap.builder();
		for (final String name : names)
		{
			final String prev = sysConfigBL.getValue(name);
			if (prev != null)
			{
				previousValues.put(name, prev);
			}
		}

		// reset scanner sysconfigs to defaults (so no test inherits another test's leaked scanner state)
		SCANNER_SYSCONFIG_DEFAULTS.forEach(sysConfigBL::setValueAtConfigLevel);

		// apply per-test overrides
		if (sysconfigs != null)
		{
			sysconfigs.forEach(sysConfigBL::setValueAtConfigLevel);
		}

		return previousValues.build();
	}
}
