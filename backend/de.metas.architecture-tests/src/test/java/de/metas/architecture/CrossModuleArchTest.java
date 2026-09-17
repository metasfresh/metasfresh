package de.metas.architecture;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.lang.EvaluationResult;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assumptions.assumeTrue;

/**
 * Cross-module ArchUnit test — the aggregator placement for inter-module invariants (no-cycles).
 * <p>
 * <b>REPORT-ONLY and OPT-IN.</b> At whole-backend scale the no-cycles slice analysis over all ~167
 * {@code de.metas} modules is expensive (several minutes) and only <i>logs</i> the cycle count — it does not
 * gate (frozen {@code beFreeOfCycles()} baselines are non-deterministic, hence ungatable; see
 * docs/coding-rules/archunit-backlog.md row 10 + skill {@code metasfresh-archunit}). So it is skipped unless
 * explicitly enabled with {@code -Darchunit.cycles=true} (run it on demand / nightly, not on every PR). When
 * skipped, the costly whole-backend import is not performed either — the gating rules in
 * {@link ModuleArchitectureTest} carry the per-PR enforcement (~70s).
 */
public class CrossModuleArchTest
{
	private static final Logger logger = LoggerFactory.getLogger(CrossModuleArchTest.class);

	/** Opt-in flag: {@code -Darchunit.cycles=true} runs the slow whole-backend no-cycles report. */
	private static final String CYCLES_REPORT_PROPERTY = "archunit.cycles";

	@Test
	void reportBoundedContextCycles()
	{
		assumeTrue(Boolean.getBoolean(CYCLES_REPORT_PROPERTY),
				"Skipped — set -D" + CYCLES_REPORT_PROPERTY + "=true to run the (slow, report-only) whole-backend no-cycles report");

		// Import + slice only when opted in (both are expensive at whole-backend scale).
		final JavaClasses wholeBackendClasses = MetasfreshArchRules.importWholeBackend();
		final EvaluationResult result = MetasfreshArchRules.boundedContextsFreeOfCycles().evaluate(wholeBackendClasses);
		final int detailLines = result.getFailureReport().getDetails().size();

		// Report-only: surface the legacy cycle count, never fail the build (see class Javadoc).
		logger.warn("ArchUnit cross-module no-cycles is REPORT-ONLY (whole backend): {} cycle-violation detail line(s) "
				+ "across all de.metas modules. Frozen/gating needs deterministic cycle descriptions — see "
				+ "docs/coding-rules/archunit-backlog.md row 10.", detailLines);
	}
}
