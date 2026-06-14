package de.metas.architecture;

import com.tngtech.archunit.core.domain.JavaClasses;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * Central application of the shared {@link MetasfreshArchRules} to individual modules' classes.
 * <p>
 * The rule <i>definitions</i> and the freezing all live behind a single entry point,
 * {@link MetasfreshArchRules#checkAllModuleRules(String, JavaClasses)}; this class only imports a module's
 * classes and calls it. Adding a new rule changes only {@code MetasfreshArchRules}, never this class. Covering
 * another module = import its classes and add one more {@code @Test} calling {@code checkAllModuleRules} with
 * that module's label — the label keys a separate freeze baseline per module, so modules never share a store.
 * <p>
 * This POC covers {@code de.metas.business}. Trade-offs of this central placement vs. distributed per-module
 * tests (and the path to the latter) are recorded in docs/coding-rules/archunit-backlog.md / skill
 * {@code metasfresh-archunit}.
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
	void de_metas_business_satisfiesArchitectureRules()
	{
		MetasfreshArchRules.checkAllModuleRules("de.metas.business", businessClasses);
	}
}
