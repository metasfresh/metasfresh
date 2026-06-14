package de.metas.architecture;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.core.importer.Location;
import com.tngtech.archunit.lang.EvaluationResult;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Cross-module ArchUnit test — the aggregator placement that expresses inter-module invariants.
 * <p>
 * Like {@code de.metas.business}'s {@code ArchitectureTest}, the rule runs from a plain JUnit Jupiter
 * {@code @Test} using the core {@code archunit} library (not the {@code archunit-junit5} engine, which needs
 * a newer {@code junit-platform} than the backend pins). This POC scopes the import to a small set of related
 * modules ({@code de.metas.business} + {@code de.metas.adempiere.adempiere.base}); wiring the check across all
 * backend modules is a later growth step.
 * <p>
 * <b>The no-cycles check is REPORT-ONLY here</b> — it logs the cycle count but does not fail the build. This is
 * the deliberate "baseline report-only first" step from the brainstorm: {@code beFreeOfCycles()} violation
 * descriptions are non-deterministic (cycle paths + their edge lists vary run-to-run), so a frozen
 * {@code FreezingArchRule} baseline does not byte-match on re-runs and would make the gate flaky in CI.
 * Turning this into a frozen, gating rule needs deterministic cycle descriptions (or a much smaller, stable
 * slice scope) — tracked in docs/coding-rules/archunit-backlog.md (row 10). The aggregator placement itself
 * (importing several modules' bytecode and running an inter-module rule) is what this POC proves.
 */
public class CrossModuleArchTest
{
	private static final Logger logger = LoggerFactory.getLogger(CrossModuleArchTest.class);

	private static JavaClasses scopedModuleClasses;

	@BeforeAll
	static void importScopedModuleClasses()
	{
		scopedModuleClasses = new ClassFileImporter()
				.withImportOption(new ImportOption.DoNotIncludeTests())
				.withImportOption(CrossModuleArchTest::isScopedModuleClass)
				.importPackages("de.metas", "org.adempiere", "org.compiere", "org.eevolution");

		// Fail loudly rather than let the rule pass vacuously against an empty class set.
		if (scopedModuleClasses.isEmpty())
		{
			throw new IllegalStateException(
					"ArchUnit imported zero scoped-module classes — the classpath form changed or isScopedModuleClass is mis-scoped");
		}
	}

	/** Restrict the import to the scoped POC modules' compiled output (jar or target/classes). */
	private static boolean isScopedModuleClass(final Location location)
	{
		return location.contains("/de.metas.business/")
				|| location.contains("/de.metas.adempiere.adempiere.base/");
	}

	/**
	 * Reports (does not gate) dependency cycles between {@code de.metas} bounded contexts across the scoped
	 * modules. See the class Javadoc for why this is report-only. Relates to
	 * docs/coding-rules/architecture.md §8 (bounded-context dependency discipline).
	 */
	@Test
	void reportBoundedContextCycles()
	{
		// Rule defined once in MetasfreshArchRules; evaluated report-only here (not frozen).
		final EvaluationResult result = MetasfreshArchRules.boundedContextsFreeOfCycles().evaluate(scopedModuleClasses);
		final int detailLines = result.getFailureReport().getDetails().size();

		// Report-only: surface the legacy cycle count, but do not fail the build (see class Javadoc).
		logger.warn("ArchUnit cross-module no-cycles is REPORT-ONLY (POC): {} cycle-violation detail line(s) "
				+ "across de.metas.business + de.metas.adempiere.adempiere.base. Frozen/gating is a next step "
				+ "(needs deterministic cycle descriptions) — see docs/coding-rules/archunit-backlog.md row 10.", detailLines);
	}
}
