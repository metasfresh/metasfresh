package de.metas.architecture;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.core.importer.Location;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.library.dependencies.SlicesRuleDefinition.slices;
import static com.tngtech.archunit.library.freeze.FreezingArchRule.freeze;

/**
 * Cross-module ArchUnit tests — the aggregator placement that expresses inter-module invariants.
 * <p>
 * Like {@code de.metas.business}'s {@code ArchitectureTest}, the rule runs from a plain JUnit Jupiter
 * {@code @Test} using the core {@code archunit} library (not the {@code archunit-junit5} engine, which needs
 * a newer {@code junit-platform} than the backend pins).
 * <p>
 * This POC scopes the import to a small set of related modules ({@code de.metas.business} +
 * {@code de.metas.adempiere.adempiere.base}); wiring the check across all backend modules is a later growth
 * step. The rule is wrapped in {@link com.tngtech.archunit.library.freeze.FreezingArchRule} so existing
 * legacy cycles are baselined into {@code archunit_store/} and only NEW cycles fail the build.
 */
public class CrossModuleArchTest
{
	private static JavaClasses scopedModuleClasses;

	@BeforeAll
	static void importScopedModuleClasses()
	{
		scopedModuleClasses = new ClassFileImporter()
				.withImportOption(new ImportOption.DoNotIncludeTests())
				.withImportOption(CrossModuleArchTest::isScopedModuleClass)
				.importPackages("de.metas", "org.adempiere", "org.compiere", "org.eevolution");
	}

	/** Restrict the import to the scoped POC modules' compiled output (jar or target/classes). */
	private static boolean isScopedModuleClass(final Location location)
	{
		return location.contains("/de.metas.business/")
				|| location.contains("/de.metas.adempiere.adempiere.base/");
	}

	/**
	 * The {@code de.metas} bounded contexts (scoped to the POC modules) must be free of dependency cycles.
	 * <p>
	 * docs/coding-rules/architecture.md §8 defines bounded-context dependency discipline (a {@code *Repository}
	 * must not depend on another bounded context's repo/DAO); freedom from package cycles is the structural
	 * corollary of that discipline at the inter-context level. See docs/coding-rules/archunit-backlog.md.
	 */
	@Test
	void boundedContextsFreeOfCycles()
	{
		final ArchRule rule = slices().matching("de.metas.(*)..").should().beFreeOfCycles()
				.as("de.metas bounded contexts (scoped to de.metas.business + de.metas.adempiere.adempiere.base) must be free of cycles")
				.because("docs/coding-rules/architecture.md §8 — bounded-context dependency discipline; cycle-freedom is its structural corollary");

		freeze(rule).check(scopedModuleClasses);
	}
}
