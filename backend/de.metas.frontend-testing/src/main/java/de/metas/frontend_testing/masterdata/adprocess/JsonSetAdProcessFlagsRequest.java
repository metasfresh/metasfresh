package de.metas.frontend_testing.masterdata.adprocess;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;

/**
 * Request to set flag columns on {@code AD_Process} records selected by a {@code JasperReport} substring.
 * <p>
 * Mirrors what the cucumber step
 * {@code "set IsPdfA3Output for AD_Process with JasperReport containing:"}
 * does in {@code AD_Process_Create_StepDef}: it queries all {@code AD_Process} rows whose
 * {@code JasperReport} column contains the given substring (case-insensitive LIKE) and sets the
 * requested flags on each match.
 * <p>
 * Example: set {@code IsPdfA3Output=Y} on the sales-invoice report process so that the mock report
 * service returns a valid PDF/A-3 fixture and {@code ZugferdAssembler.embed()} receives parseable PDF
 * bytes during a Playwright E2E run.
 */
@Value
@Builder
@Jacksonized
public class JsonSetAdProcessFlagsRequest
{
	/**
	 * Substring to match against {@code AD_Process.JasperReport} (case-insensitive LIKE).
	 * All matching processes have their flags updated.
	 * Example: {@code "de/metas/docs/sales/invoice"}
	 */
	String jasperReportSubstring;

	/**
	 * If non-null, sets {@code AD_Process.IsPdfA3Output} to this value on every matching process.
	 */
	@Nullable Boolean isPdfA3Output;
}
