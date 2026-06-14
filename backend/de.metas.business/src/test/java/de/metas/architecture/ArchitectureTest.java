package de.metas.architecture;

import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.domain.JavaMethodCall;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.core.importer.Location;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noFields;
import static com.tngtech.archunit.library.freeze.FreezingArchRule.freeze;

/**
 * Per-module ArchUnit architecture-enforcement tests for {@code de.metas.business}.
 * <p>
 * The rules run from plain JUnit Jupiter {@code @Test} methods (using the core {@code archunit} library +
 * {@link ClassFileImporter}), NOT via the {@code archunit-junit5} JUnit-Platform engine — that engine
 * requires a newer {@code junit-platform} than the version pinned across the backend, so it silently runs
 * zero tests under Surefire. The Jupiter approach reuses the test engine the backend already runs.
 * <p>
 * Each rule is wrapped in {@link com.tngtech.archunit.library.freeze.FreezingArchRule} so the large existing
 * legacy-violation set is baselined into {@code archunit_store/} and only NEW violations fail the build. As
 * legacy violations are fixed, the store shrinks; once a rule's store empties the {@code freeze(...)} wrapper
 * can be removed to hard-fail. Each rule cites the corpus rule it enforces; see
 * {@code docs/coding-rules/archunit-backlog.md} for the candidate backlog and marker convention.
 */
public class ArchitectureTest
{
	private static JavaClasses thisModuleClasses;

	@BeforeAll
	static void importThisModuleClasses()
	{
		// Import this module's own source roots, restricted to its compiled output so dependency jars
		// (de.metas.adempiere.adempiere.base, …) are NOT pulled into this per-module baseline.
		thisModuleClasses = new ClassFileImporter()
				.withImportOption(new ImportOption.DoNotIncludeTests())
				.withImportOption(ArchitectureTest::isThisModuleClass)
				.importPackages("de.metas", "org.adempiere", "org.compiere", "org.eevolution");
	}

	private static boolean isThisModuleClass(final Location location)
	{
		return location.contains("/de.metas.business/target/classes/");
	}

	/**
	 * Persistence primitives are confined to {@code *Repository} / {@code *DAO} classes — BL / Service /
	 * command / interceptor / process code must not call {@code InterfaceWrapperHelper.save|saveRecord|load}
	 * directly. Enforces the {@code InterfaceWrapperHelper} save/load portion of
	 * docs/coding-rules/service-injection.md §4 + java-general.md §18 (the {@code IQueryBL} portion of those
	 * rules is a separate candidate — see docs/coding-rules/archunit-backlog.md).
	 */
	@Test
	void persistencePrimitivesConfinedToRepositoryOrDao()
	{
		final ArchRule rule = noClasses()
				.that().haveSimpleNameNotEndingWith("Repository")
				.and().haveSimpleNameNotEndingWith("DAO")
				.should().callMethodWhere(interfaceWrapperHelperWriteOrLoad())
				.as("InterfaceWrapperHelper.save/saveRecord/load must only be called from *Repository / *DAO classes")
				.because("docs/coding-rules/service-injection.md §4 + java-general.md §18 — persistence primitives belong in Repository/DAO, not in BL/Service/command/interceptor/process");

		freeze(rule).check(thisModuleClasses);
	}

	/**
	 * No {@link java.sql.Timestamp} as a field type — use the metasfresh time types
	 * ({@code Instant}/{@code ZonedDateTime} via {@code SystemTime}/{@code TimeUtil}).
	 * Enforces the field-type portion of docs/coding-rules/java-time.md §2 (parameters/return types are a
	 * separate candidate — see docs/coding-rules/archunit-backlog.md).
	 */
	@Test
	void noJavaSqlTimestampFields()
	{
		final ArchRule rule = noFields()
				.should().haveRawType(Timestamp.class)
				.as("Fields must not be of type java.sql.Timestamp")
				.because("docs/coding-rules/java-time.md §2 — use Instant/ZonedDateTime via SystemTime/TimeUtil, not java.sql.Timestamp");

		freeze(rule).check(thisModuleClasses);
	}

	/**
	 * No low-level ambient client/org accessors ({@code Env.getAD_Client_ID/getClientId/getAD_Org_ID/getOrgId})
	 * inside services ({@code @Service}/{@code @Component}/{@code @Repository}) or business-logic ({@code *BL})
	 * classes — client/org must be passed explicitly or derived from the domain object, not read from the
	 * thread-local {@code Env} context. Enforces docs/coding-rules/service-injection.md §7.
	 * <p>
	 * Note: §7 also covers {@code Env.getCtx()}-<i>derived</i> client/org reads; detecting those needs
	 * data-flow analysis a static call check cannot do precisely (a bare {@code getCtx()} is legitimate for
	 * e.g. language resolution), so this rule deliberately covers only the explicit client/org accessors.
	 */
	@Test
	void noEnvAmbientContextInServiceOrBL()
	{
		final ArchRule rule = noClasses()
				.that().haveSimpleNameEndingWith("BL")
				.or().areAnnotatedWith(Service.class)
				.or().areAnnotatedWith(Component.class)
				.or().areAnnotatedWith(Repository.class)
				.should().callMethodWhere(envAmbientClientOrgAccessor())
				.as("Env.getAD_Client_ID/getClientId/getAD_Org_ID/getOrgId must not be called from @Service/@Component/@Repository/*BL classes")
				.because("docs/coding-rules/service-injection.md §7 — service/BL code must not read client/org from the ambient Env thread-local; pass it explicitly or derive it from the domain object");

		freeze(rule).check(thisModuleClasses);
	}

	private static DescribedPredicate<JavaMethodCall> interfaceWrapperHelperWriteOrLoad()
	{
		return new DescribedPredicate<JavaMethodCall>("a call to InterfaceWrapperHelper.save/saveRecord/load")
		{
			@Override
			public boolean test(final JavaMethodCall call)
			{
				final String owner = call.getTargetOwner().getFullName();
				final String method = call.getName();
				return "org.adempiere.model.InterfaceWrapperHelper".equals(owner)
						&& ("save".equals(method) || "saveRecord".equals(method) || "load".equals(method));
			}
		};
	}

	private static DescribedPredicate<JavaMethodCall> envAmbientClientOrgAccessor()
	{
		return new DescribedPredicate<JavaMethodCall>("a call to an Env ambient client/org accessor")
		{
			@Override
			public boolean test(final JavaMethodCall call)
			{
				final String owner = call.getTargetOwner().getFullName();
				final String method = call.getName();
				return "org.compiere.util.Env".equals(owner)
						&& ("getAD_Client_ID".equals(method)
								|| "getClientId".equals(method)
								|| "getAD_Org_ID".equals(method)
								|| "getOrgId".equals(method));
			}
		};
	}
}
