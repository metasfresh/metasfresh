package de.metas.frontend_testing.masterdata.sysconfig;

import com.google.common.collect.ImmutableMap;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ISysConfigBL;

import javax.annotation.Nullable;
import java.util.LinkedHashSet;
import java.util.Map;

/**
 * Resets the barcode-scanner sysconfigs to their defaults (so no test inherits another test's scanner state),
 * then applies the per-test overrides.
 */
@Builder
public class SysconfigCommand
{
	// Effective values after all migrations on a standard DB.
	private static final ImmutableMap<String, String> SCANNER_SYSCONFIG_DEFAULTS = ImmutableMap.<String, String>builder()
			.put("mobileui.frontend.barcodeScanner.inputText.debounceMillis", "300")
			.put("mobileui.frontend.barcodeScanner.inputText.idleAbandonMillis", "15000")
			.put("mobileui.frontend.barcodeScanner.inputText.triggerOnChangeIfLengthGreaterThan", "10")
			.put("mobileui.frontend.barcodeScanner.mode.hardware.enabled", "Y")
			.put("mobileui.frontend.barcodeScanner.mode.camera.enabled", "Y")
			.put("mobileui.frontend.barcodeScanner.mode.manual.enabled", "Y")
			.put("mobileui.frontend.barcodeScanner.defaultMode", "hardware")
			.put("mobileui.frontend.barcodeScanner.mode.hardware.input.readOnly", "N")
			.put("mobileui.frontend.barcodeScanner.mode.hardware.input.inputMode", "none")
			.build();

	@NonNull private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

	@Nullable private final Map<String, String> sysconfigs;

	/**
	 * @return map from sysconfig name to its previous (effective) value, captured before any write
	 */
	public ImmutableMap<String, String> execute()
	{
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
			else if (SCANNER_SYSCONFIG_DEFAULTS.containsKey(name))
			{
				// fail loud instead of creating it: a missing row means the sysconfig was renamed or retired
				throw new AdempiereException("Scanner sysconfig does not exist: " + name + " - update SCANNER_SYSCONFIG_DEFAULTS");
			}
		}

		SCANNER_SYSCONFIG_DEFAULTS.forEach(sysConfigBL::setValueAtConfigLevel);

		if (sysconfigs != null)
		{
			sysconfigs.forEach(sysConfigBL::setValueAtConfigLevel);
		}

		return previousValues.build();
	}
}
