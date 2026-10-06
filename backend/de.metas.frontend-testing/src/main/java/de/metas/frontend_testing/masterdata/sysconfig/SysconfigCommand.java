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
 * {@code mode.hardware.input.readOnly='Y'} or {@code defaultMode='manual'} from barcode_scanner_modes.spec.js).
 * All writes go through {@link ISysConfigBL#setValueAtConfigLevel(String, String)}, which targets the
 * (client,org) matching each sysconfig's declared {@code ConfigurationLevel} so the
 * {@code AD_SysConfig} interceptor does not reject them.
 */
@Builder
public class SysconfigCommand
{
	/**
	 * Defaults = the effective values after all migrations on a standard DB (seed + later carry-forward migrations).
	 */
	private static final ImmutableMap<String, String> SCANNER_SYSCONFIG_DEFAULTS = ImmutableMap.<String, String>builder()
			// Timing knobs — a test that lowers them (e.g. the truncated-HU-QR picking test drops idleAbandonMillis to 500
			// so a held partial errors fast) must not leak that value: a small idleAbandonMillis abandons the chunked-scan
			// test's in-flight partial mid-gap → "QR not recognized". Seeds: 5664360 = 300, 5812460 = 15000, 10.
			.put("mobileui.frontend.barcodeScanner.inputText.debounceMillis", "300")
			.put("mobileui.frontend.barcodeScanner.inputText.idleAbandonMillis", "15000")
			.put("mobileui.frontend.barcodeScanner.inputText.triggerOnChangeIfLengthGreaterThan", "10")
			// Scanner-mode knobs — barcode_scanner_modes.spec.js flips them per test (manual-first, camera off,
			// input readOnly=Y …). Seeded by 5807640_sysconfig_barcodeScanner_modes.sql; 5807650 carries the legacy
			// showInputText=Y forward to mode.manual.enabled=Y, so Y is the effective default for manual.
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
