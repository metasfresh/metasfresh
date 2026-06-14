package de.metas.architecture;

import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.domain.JavaMethodCall;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.core.importer.Location;
import com.tngtech.archunit.lang.ArchRule;

import java.sql.Timestamp;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noFields;

/**
 * The metasfresh architecture rules, defined <b>once</b> and reused everywhere.
 * <p>
 * This is the single source of the rule definitions — tests (per-module or central) reference these factory
 * methods rather than copy-pasting rule bodies. In this POC the central {@link ModuleArchitectureTest}
 * applies them to {@code de.metas.business}; the same methods can be wrapped in a per-module abstract base
 * test if/when the codebase moves to distributed per-module enforcement (see
 * docs/coding-rules/archunit-backlog.md).
 * <p>
 * The rules are returned <i>raw</i> (un-frozen); the caller wraps them in
 * {@link com.tngtech.archunit.library.freeze.FreezingArchRule} so each call site keeps its own baseline.
 * Each rule cites the corpus rule it enforces in its {@code .because()} clause.
 */
public final class MetasfreshArchRules
{
	private MetasfreshArchRules()
	{
	}

	/** Import a single module's own compiled classes (jar or target/classes), failing loudly on an empty set. */
	public static JavaClasses importModule(final String moduleLocationToken)
	{
		final JavaClasses classes = new ClassFileImporter()
				.withImportOption(new ImportOption.DoNotIncludeTests())
				.withImportOption((final Location location) -> location.contains(moduleLocationToken))
				.importPackages("de.metas", "org.adempiere", "org.compiere", "org.eevolution");

		if (classes.isEmpty())
		{
			throw new IllegalStateException("ArchUnit imported zero classes for module token '" + moduleLocationToken
					+ "' — the classpath form changed or the token is mis-scoped");
		}
		return classes;
	}

	/**
	 * Persistence primitives confined to {@code *Repository}/{@code *DAO} — BL/Service/command/interceptor/
	 * process code must not call {@code InterfaceWrapperHelper.save|saveRecord|load} directly. Enforces the
	 * {@code InterfaceWrapperHelper} save/load portion of docs/coding-rules/service-injection.md §4 +
	 * java-general.md §18 (the {@code IQueryBL}/{@code DB.*} portion is a separate candidate — see
	 * docs/coding-rules/archunit-backlog.md).
	 */
	public static ArchRule persistencePrimitivesConfinedToRepositoryOrDao()
	{
		return noClasses()
				.that().haveSimpleNameNotEndingWith("Repository")
				.and().haveSimpleNameNotEndingWith("DAO")
				.should().callMethodWhere(interfaceWrapperHelperWriteOrLoad())
				.as("InterfaceWrapperHelper.save/saveRecord/load must only be called from *Repository / *DAO classes")
				.because("docs/coding-rules/service-injection.md §4 + java-general.md §18 — persistence primitives belong in Repository/DAO, not in BL/Service/command/interceptor/process");
	}

	/**
	 * No {@link java.sql.Timestamp} as a field type — use {@code Instant}/{@code ZonedDateTime} via
	 * {@code SystemTime}/{@code TimeUtil}. Enforces the field-type portion of docs/coding-rules/java-time.md §2
	 * (parameters/return types are a separate candidate — see docs/coding-rules/archunit-backlog.md).
	 */
	public static ArchRule noJavaSqlTimestampFields()
	{
		return noFields()
				.should().haveRawType(Timestamp.class)
				.as("Fields must not be of type java.sql.Timestamp")
				.because("docs/coding-rules/java-time.md §2 — use Instant/ZonedDateTime via SystemTime/TimeUtil, not java.sql.Timestamp");
	}

	/**
	 * No low-level ambient client/org accessors ({@code Env.getAD_Client_ID/getClientId/getAD_Org_ID/getOrgId})
	 * inside {@code @Service}/{@code @Component}/{@code @Repository} or {@code *BL} classes. Enforces
	 * docs/coding-rules/service-injection.md §7.
	 * <p>
	 * Scoped by class simple-name (`*BL`) + Spring stereotype annotations by FQN string (so this rules module
	 * needs no compile dependency on spring). {@code Env.getCtx()}-derived reads and {@code command} classes
	 * are not covered (see backlog).
	 */
	public static ArchRule noEnvAmbientContextInServiceOrBL()
	{
		return noClasses()
				.that().haveSimpleNameEndingWith("BL")
				.or().areAnnotatedWith("org.springframework.stereotype.Service")
				.or().areAnnotatedWith("org.springframework.stereotype.Component")
				.or().areAnnotatedWith("org.springframework.stereotype.Repository")
				.should().callMethodWhere(envAmbientClientOrgAccessor())
				.as("Env.getAD_Client_ID/getClientId/getAD_Org_ID/getOrgId must not be called from @Service/@Component/@Repository/*BL classes")
				.because("docs/coding-rules/service-injection.md §7 — service/BL code must not read client/org from the ambient Env thread-local; pass it explicitly or derive it from the domain object");
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
