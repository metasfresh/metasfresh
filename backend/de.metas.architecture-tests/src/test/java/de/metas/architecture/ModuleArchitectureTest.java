package de.metas.architecture;

import com.tngtech.archunit.core.domain.JavaClasses;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * Central application of the shared {@link MetasfreshArchRules} to the metasfresh backend, <b>keyed per module</b>.
 * <p>
 * The test classpath includes the full transitive closure of {@code metasfresh-dist-serverRoot}
 * and {@code metasfresh-webui-api} (both assembly modules), so {@link MetasfreshArchRules#importWholeBackend()}
 * imports every backend module's classes in a single pass. The probe inside {@code importWholeBackend()}
 * asserts that the closure is complete by verifying that classes from several distinct modules
 * (de.metas.business, de.metas.invoice, de.metas.handlingunits, de.metas.contracts, de.metas.payment)
 * are present.
 * <p>
 * {@link MetasfreshArchRules#checkAllModulesIndividually(JavaClasses)} then splits the imported classes by their
 * owning module (source jar) and freezes each module against its <b>own</b> baseline. Per-module baselines are
 * reproducible by construction (a module's violations depend only on that module's own classes), unlike a single
 * whole-app baseline whose class set drifted between a stale local build and CI's fresh build. Adding a new rule
 * changes only {@code MetasfreshArchRules} — never this class.
 */
public class ModuleArchitectureTest
{
	private static JavaClasses wholeBackendClasses;

	@BeforeAll
	static void importWholeBackendClasses()
	{
		wholeBackendClasses = MetasfreshArchRules.importWholeBackend();
	}

	@Test
	void metasfresh_backend_satisfiesArchitectureRules()
	{
		MetasfreshArchRules.checkAllModulesIndividually(wholeBackendClasses);
	}
}
