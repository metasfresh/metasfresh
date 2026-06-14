package de.metas.architecture;

import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.domain.JavaMethodCall;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.core.importer.Location;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.EvaluationResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noFields;
import static com.tngtech.archunit.library.dependencies.SlicesRuleDefinition.slices;
import static com.tngtech.archunit.library.freeze.FreezingArchRule.freeze;

/**
 * The metasfresh architecture rules, defined <b>once</b> and run through a <b>single</b> public entry point.
 * <p>
 * Callers invoke exactly one method — {@link #checkAllModuleRules(String, JavaClasses)} — which freezes and evaluates
 * every gating rule and joins their results, so a run reports every new violation at once. <b>Adding a new
 * rule ("chapter") means adding a private rule method here plus one line in {@code checkAllModuleRules} — no
 * caller (test class) changes.</b> The individual rule bodies are therefore {@code private}; the other public
 * members are {@link #importModule(String)} (a caller utility) and {@link #boundedContextsFreeOfCycles()}
 * (a distinct, report-only cross-module rule).
 * <p>
 * Each rule cites the corpus rule it enforces in its {@code .because()} clause. Freezing keeps a per-call-site
 * baseline ({@code archunit_store/}). How-to / branch enabler / per-branch baseline: skill
 * {@code metasfresh-archunit}.
 */
public final class MetasfreshArchRules
{
	private static final Logger logger = LoggerFactory.getLogger(MetasfreshArchRules.class);

	private MetasfreshArchRules()
	{
	}

	/**
	 * The single gating entry point: freeze + evaluate <b>every</b> per-element rule against the given module's
	 * classes, join the results, and fail once if any rule has NEW (non-baselined) violations. Reports all
	 * failing rules together (with their rule headings) rather than short-circuiting on the first.
	 * <p>
	 * Add a new rule by adding a private factory method below and one entry to the {@code rules} list here —
	 * callers never change.
	 * <p>
	 * {@code moduleLabel} (e.g. {@code "de.metas.business"}) is prefixed onto each rule's description so the
	 * freeze baseline key is <b>per-module</b>. This is mandatory: {@code FreezingArchRule} keys its store
	 * entry on the rule description, so running the same rule against two modules without distinct labels would
	 * make them share — and corrupt — one baseline file.
	 */
	public static void checkAllModuleRules(final String moduleLabel, final JavaClasses moduleClasses)
	{
		final List<ArchRule> rules = Arrays.asList(
				persistencePrimitivesConfinedToRepositoryOrDao(),
				noJavaSqlTimestampFields(),
				noEnvAmbientContextInServiceOrBL());

		final List<String> failingRuleReports = new ArrayList<>();
		for (final ArchRule rule : rules)
		{
			// Per-module freeze key (see Javadoc): prefix the module label onto the description.
			final ArchRule moduleScopedRule = rule.as("[" + moduleLabel + "] " + rule.getDescription());
			// freeze(...).evaluate(...) creates/updates this rule's baseline store and returns only NEW violations.
			final EvaluationResult result = freeze(moduleScopedRule).evaluate(moduleClasses);
			if (result.hasViolation())
			{
				failingRuleReports.add(result.getFailureReport().toString());
			}
		}

		if (!failingRuleReports.isEmpty())
		{
			throw new AssertionError("ArchUnit found new architecture violations in " + failingRuleReports.size()
					+ " rule(s) for module [" + moduleLabel + "]:\n" + String.join("\n", failingRuleReports));
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
	 * Import the WHOLE backend — every class in {@code de.metas}, {@code org.adempiere}, {@code org.compiere},
	 * and {@code org.eevolution} found on the test classpath, excluding test classes. No location filter is
	 * applied, so all modules reachable via the {@code metasfresh-dist-serverRoot} +
	 * {@code metasfresh-webui-api} transitive dependency closures are included.
	 * <p>
	 * Before returning, this method logs:
	 * <ul>
	 *   <li>Total number of imported classes</li>
	 *   <li>Distinct {@code de.metas.<x>} top-level sub-packages present</li>
	 * </ul>
	 * and asserts that several well-known modules are present (de.metas.business, de.metas.invoice,
	 * de.metas.handlingunits, de.metas.contracts, de.metas.payment). A missing module means the
	 * transitive closure is incomplete.
	 *
	 * @throws IllegalStateException if zero classes were imported, or if a mandatory module is absent
	 */
	public static JavaClasses importWholeBackend()
	{
		final long importStart = System.currentTimeMillis();

		final JavaClasses classes = new ClassFileImporter()
				.withImportOption(new ImportOption.DoNotIncludeTests())
				.importPackages("de.metas", "org.adempiere", "org.compiere", "org.eevolution");

		final long importMs = System.currentTimeMillis() - importStart;

		if (classes.isEmpty())
		{
			throw new IllegalStateException("ArchUnit imported zero classes for the whole backend — "
					+ "the assembly deps are missing from the test classpath");
		}

		// Collect distinct de.metas.<x> top-level sub-packages
		final Set<String> metasSubPackages = new TreeSet<>();
		for (final JavaClass cls : classes)
		{
			final String pkg = cls.getPackageName();
			if (pkg.startsWith("de.metas."))
			{
				final String[] parts = pkg.split("\\.");
				// parts[0]=de, parts[1]=metas, parts[2]=<subpackage>
				if (parts.length >= 3)
				{
					metasSubPackages.add(parts[2]);
				}
			}
		}

		logger.info("[ArchUnit whole-backend] Total classes imported: {} (import took {} ms)", classes.size(), importMs);
		logger.info("[ArchUnit whole-backend] Distinct de.metas.<x> sub-packages ({}): {}", metasSubPackages.size(), metasSubPackages);

		// Probe: assert key module sub-packages are present — a missing one means an incomplete closure.
		// NOTE: Maven artifact names (e.g. "de.metas.business") do not correspond to Java package names.
		// de.metas.business artifact puts classes under de.metas.invoice, de.metas.invoicecandidate, etc.
		// We probe for distinct Java sub-packages that are definitively contributed by different modules:
		//   invoice         → de.metas.invoice          (from de.metas.business jar)
		//   handlingunits   → de.metas.handlingunits     (from de.metas.handlingunits.base)
		//   contracts       → de.metas.contracts         (from de.metas.contracts)
		//   payment         → de.metas.payment           (from de.metas.payment.* jars)
		//   manufacturing   → de.metas.manufacturing     (from de.metas.manufacturing)
		final List<String> missingModules = new ArrayList<>();
		for (final String mandatorySubPkg : Arrays.asList(
				"invoice", "handlingunits", "contracts", "payment", "manufacturing"))
		{
			if (!metasSubPackages.contains(mandatorySubPkg))
			{
				missingModules.add("de.metas." + mandatorySubPkg);
			}
		}
		if (!missingModules.isEmpty())
		{
			throw new IllegalStateException("[ArchUnit whole-backend] INCOMPLETE CLOSURE — missing expected sub-packages: "
					+ missingModules + ". These sub-packages were present: " + metasSubPackages);
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
