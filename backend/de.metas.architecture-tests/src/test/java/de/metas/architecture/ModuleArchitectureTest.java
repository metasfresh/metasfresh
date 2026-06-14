package de.metas.architecture;

import com.tngtech.archunit.core.domain.JavaClasses;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * Central application of the shared {@link MetasfreshArchRules} to the WHOLE metasfresh backend.
 * <p>
 * The test classpath includes the full transitive closure of {@code metasfresh-dist-serverRoot}
 * and {@code metasfresh-webui-api} (both assembly modules), so {@link MetasfreshArchRules#importWholeBackend()}
 * imports every backend module's classes in a single pass. The probe inside {@code importWholeBackend()}
 * asserts that the closure is complete by verifying that classes from several distinct modules
 * (de.metas.business, de.metas.invoice, de.metas.handlingunits, de.metas.contracts, de.metas.payment)
 * are present.
 * <p>
 * {@link MetasfreshArchRules#checkAllModuleRules(String, JavaClasses)} is called with the label
 * {@code "metasfresh-backend"}, which keys a single whole-app freeze baseline. Adding a new rule
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
		MetasfreshArchRules.checkAllModuleRules("metasfresh-backend", wholeBackendClasses);
	}
}
