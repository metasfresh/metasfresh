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
	private static final ImmutableMap<String, String> SCANNER_SYSCONFIG_DEFAULTS = ImmutableMap.<String, String>builder()
			.put("mobileui.frontend.barcodeScanner.showInputText", "Y")
			.put("mobileui.frontend.barcodeScanner.isInputTextReadonly", "Y")
			// Reset the per-instance scanner-mode knobs too — a test that flips them (e.g. the
			// Honeywell CT60 keystroke-wedge contract test, or the camera-mode tests) must not leak
			// its setting onto unrelated specs in the same run (the reset runs before every test).
			// Camera scanning is OFF by default in tests: it is irrelevant to non-camera workflows,
			// brings a getUserMedia render-race flake in CI, and renders a stray hardware<->camera
			// toggle. The camera-mode tests in barcode_scanner_modes.spec.js opt back in explicitly
			// (cameraEnabled:'Y'). Both the new mode key and the legacy alias are set off, so the
			// effective value is camera-off regardless of the alias-retirement state.
			.put("mobileui.frontend.barcodeScanner.useCamera", "N")
			.put("mobileui.frontend.barcodeScanner.mode.camera.enabled", "N")
			.put("mobileui.frontend.barcodeScanner.offscreenInput.readOnly", "N")
			.put("mobileui.frontend.barcodeScanner.visibleInput.readOnly", "N")
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
