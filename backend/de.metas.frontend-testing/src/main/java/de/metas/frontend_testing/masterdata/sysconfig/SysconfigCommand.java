package de.metas.frontend_testing.masterdata.sysconfig;

import com.google.common.collect.ImmutableMap;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ISysConfigBL;

import javax.annotation.Nullable;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;

/**
 * Resets the barcode-scanner sysconfigs to their defaults (so no test inherits another test's scanner state),
 * then applies the per-test overrides.
 */
@Builder
public class SysconfigCommand
{
	// Values on a freshly migrated DB, not the per-script seeds (e.g. mode.manual.enabled is seeded N, migrated to Y).
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

	/**
	 * What {@code SysConfigDAO} stores for "no value" ({@code SysConfigEntryValue.NO_VALUE_MARKER}, not visible from here);
	 * a sysconfig with this value reads as missing.
	 */
	private static final String NO_VALUE = "-";

	@NonNull private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

	@Nullable private final Map<String, String> sysconfigs;

	/**
	 * A {@code null} value in {@link #sysconfigs} unsets the sysconfig; that is how a previously missing one is restored.
	 *
	 * @return map from sysconfig name to its previous value, captured before any write;
	 * the value is {@code null} for names that had no value, so that restoring them unsets it again
	 */
	public Map<String, String> execute()
	{
		final LinkedHashSet<String> names = new LinkedHashSet<>(SCANNER_SYSCONFIG_DEFAULTS.keySet());
		if (sysconfigs != null)
		{
			names.addAll(sysconfigs.keySet());
		}
		final LinkedHashMap<String, String> previousValues = new LinkedHashMap<>();
		for (final String name : names)
		{
			final String prev = sysConfigBL.getValue(name);
			if (prev == null && SCANNER_SYSCONFIG_DEFAULTS.containsKey(name))
			{
				// fail loud instead of creating it: a missing row means the sysconfig was renamed or retired
				throw new AdempiereException("Scanner sysconfig does not exist: " + name + " - update SCANNER_SYSCONFIG_DEFAULTS");
			}
			previousValues.put(name, prev);
		}

		SCANNER_SYSCONFIG_DEFAULTS.forEach(sysConfigBL::setValueAtConfigLevel);

		if (sysconfigs != null)
		{
			sysconfigs.forEach((name, value) -> sysConfigBL.setValueAtConfigLevel(name, value != null ? value : NO_VALUE));
		}

		return previousValues;
	}
}
