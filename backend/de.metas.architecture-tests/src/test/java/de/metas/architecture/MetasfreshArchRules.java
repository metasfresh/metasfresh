package de.metas.architecture;

import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.domain.JavaMethodCall;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.core.importer.Location;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.EvaluationResult;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noFields;
import static com.tngtech.archunit.library.dependencies.SlicesRuleDefinition.slices;
import static com.tngtech.archunit.library.freeze.FreezingArchRule.freeze;

/**
 * The metasfresh architecture rules, defined <b>once</b> and run through a <b>single</b> public entry point.
 * <p>
 * Callers invoke exactly one method — {@link #checkAllModuleRules(JavaClasses)} — which freezes and evaluates
 * every gating rule and joins their results, so a run reports every new violation at once. <b>Adding a new
 * rule ("chapter") means adding a private rule method here plus one line in {@code checkAllModuleRules} — no
 * caller (test class) changes.</b> The individual rule bodies are therefore {@code private}; the only other
 * public member is {@link #boundedContextsFreeOfCycles()} (a distinct, report-only cross-module rule).
 * <p>
 * Each rule cites the corpus rule it enforces in its {@code .because()} clause. Freezing keeps a per-call-site
 * baseline ({@code archunit_store/}). How-to / branch enabler / per-branch baseline: skill
 * {@code metasfresh-archunit}.
 */
public final class MetasfreshArchRules
{
	private MetasfreshArchRules()
	{
	}

	/**
	 * The single gating entry point: freeze + evaluate <b>every</b> per-element rule against the given module's
	 * classes, join the results, and fail once if any rule has NEW (non-baselined) violations. Reports all
	 * failing rules together rather than short-circuiting on the first.
	 * <p>
	 * Add a new rule by adding a private factory method below and one entry to the {@code rules} list here —
	 * callers never change.
	 */
	public static void checkAllModuleRules(final JavaClasses moduleClasses)
	{
		final List<ArchRule> rules = Arrays.asList(
				persistencePrimitivesConfinedToRepositoryOrDao(),
				noJavaSqlTimestampFields(),
				noEnvAmbientContextInServiceOrBL());

		final List<String> violations = new ArrayList<>();
		for (final ArchRule rule : rules)
		{
			// freeze(...).evaluate(...) creates/updates the rule's baseline store and returns only NEW violations.
			final EvaluationResult result = freeze(rule).evaluate(moduleClasses);
			violations.addAll(result.getFailureReport().getDetails());
		}

		if (!violations.isEmpty())
		{
			throw new AssertionError("ArchUnit found " + violations.size() + " new architecture violation(s):\n"
					+ String.join("\n", violations));
		}
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
	private static ArchRule persistencePrimitivesConfinedToRepositoryOrDao()
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
	private static ArchRule noJavaSqlTimestampFields()
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
	private static ArchRule noEnvAmbientContextInServiceOrBL()
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

	/**
	 * Cross-module: {@code de.metas} bounded contexts must be free of dependency cycles. Relates to
	 * docs/coding-rules/architecture.md §8 (bounded-context dependency discipline).
	 * <p>
	 * <b>Returned for report-only use, NOT for freezing.</b> {@code beFreeOfCycles()} violation descriptions
	 * are non-deterministic run-to-run, so a {@code FreezingArchRule} baseline would be flaky — callers should
	 * {@code evaluate()} this and log, not {@code freeze(...).check(...)}. See skill {@code metasfresh-archunit}.
	 */
	public static ArchRule boundedContextsFreeOfCycles()
	{
		return slices().matching("de.metas.(*)..").should().beFreeOfCycles()
				.as("de.metas bounded contexts must be free of cycles")
				.because("docs/coding-rules/architecture.md §8 — bounded-context dependency discipline; cycle-freedom is its structural corollary");
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
