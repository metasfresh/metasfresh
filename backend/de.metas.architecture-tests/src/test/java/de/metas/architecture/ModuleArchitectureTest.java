package de.metas.architecture;

import com.tngtech.archunit.core.domain.JavaClasses;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.library.freeze.FreezingArchRule.freeze;

/**
 * Central application of the shared {@link MetasfreshArchRules} to individual modules' classes.
 * <p>
 * The rule <i>definitions</i> live once in {@link MetasfreshArchRules}; this class only wires them to a
 * module's imported classes and freezes each (baseline in this module's {@code archunit_store/}). Adding
 * coverage for another module = import its classes and add {@code @Test} methods that reference the same
 * {@code MetasfreshArchRules} factory methods — never copy a rule body.
 * <p>
 * This POC covers {@code de.metas.business}. Trade-offs of this central placement vs. distributed per-module
 * tests (and the path to the latter) are recorded in docs/coding-rules/archunit-backlog.md.
 */
public class ModuleArchitectureTest
{
	private static JavaClasses businessClasses;

	@BeforeAll
	static void importModuleClasses()
	{
		businessClasses = MetasfreshArchRules.importModule("/de.metas.business/");
	}

	@Test
	void business_persistencePrimitivesConfinedToRepositoryOrDao()
	{
		freeze(MetasfreshArchRules.persistencePrimitivesConfinedToRepositoryOrDao()).check(businessClasses);
	}

	@Test
	void business_noJavaSqlTimestampFields()
	{
		freeze(MetasfreshArchRules.noJavaSqlTimestampFields()).check(businessClasses);
	}

	@Test
	void business_noEnvAmbientContextInServiceOrBL()
	{
		freeze(MetasfreshArchRules.noEnvAmbientContextInServiceOrBL()).check(businessClasses);
	}
}
