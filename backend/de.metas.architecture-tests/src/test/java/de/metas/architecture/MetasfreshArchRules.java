package de.metas.architecture;

import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.domain.JavaMethodCall;
import com.tngtech.archunit.core.domain.Source;
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
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noFields;
import static com.tngtech.archunit.library.dependencies.SlicesRuleDefinition.slices;
import static com.tngtech.archunit.library.freeze.FreezingArchRule.freeze;

/**
 * The metasfresh architecture rules, defined <b>once</b> and run through a single per-module entry point.
 * <p>
 * Whole-backend callers invoke exactly one method — {@link #checkAllModulesIndividually(JavaClasses)} — which
 * splits the import by owning module and freezes each module against its own baseline. <b>Adding a new
 * rule ("chapter") means adding a private rule method here plus one line in {@code checkAllModuleRules} — no
 * caller (test class) changes.</b> The individual rule bodies are therefore {@code private}; the other public
 * members are {@link #checkAllModuleRules(String, JavaClasses)} (the per-module building block, also usable
 * directly for a single-module run with {@link #importModule(String)}), {@link #importModule(String)} and
 * {@link #importWholeBackend()} (caller utilities), and {@link #boundedContextsFreeOfCycles()} (a distinct,
 * report-only cross-module rule).
 * <p>
 * Each rule cites the corpus rule it enforces in its {@code .because()} clause. Freezing keeps a per-call-site
 * baseline ({@code archunit_store/}). How-to / branch enabler / per-branch baseline: skill
 * {@code metasfresh-archunit}.
 */
public final class MetasfreshArchRules
{
	private static final Logger logger = LoggerFactory.getLogger(MetasfreshArchRules.class);

	/**
	 * The backend package roots ArchUnit imports and analyses: metasfresh ({@code de.metas}, {@code org.eevolution})
	 * plus the legacy ADempiere bases ({@code org.adempiere}, {@code org.compiere}) that metasfresh classes still
	 * call into. Single source for every importer here, so the analysed surface can't drift between methods.
	 */
	private static final String[] BACKEND_PACKAGE_ROOTS = { "de.metas", "org.adempiere", "org.compiere", "org.eevolution" };

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
	 * For a whole-backend run, call {@link #checkAllModulesIndividually(JavaClasses)} instead — it performs the
	 * per-module split that makes the freeze baselines reproducible. Calling this method directly with the whole
	 * backend under one label recreates the single-baseline that could not be kept in sync with CI.
	 * <p>
	 * {@code moduleLabel} is any unique label prefixed onto each rule's description so the freeze baseline key
	 * is distinct per import scope ({@code "metasfresh-backend"} for the whole-backend run; a single-module label
	 * such as {@code "de.metas.business"} when running against one module). This is mandatory: {@code FreezingArchRule} keys its store
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

	/**
	 * Per-module gating entry point. Splits the whole-backend import into per-owning-module groups (keyed by the
	 * source jar's artifact id) and runs {@link #checkAllModuleRules(String, JavaClasses)} against each group
	 * separately, so every module gets its <b>own</b> freeze baseline.
	 * <p>
	 * This is reproducible by construction: a module's violations derive only from <b>that module's own compiled
	 * classes</b>, independent of which other modules happen to be on the classpath (the whole-backend single
	 * baseline was not — its set drifted between a stale local build and CI's fresh full build). A module that has
	 * no committed baseline auto-records on first run ({@code freeze.store.default.allowStoreCreation=true}) and
	 * passes, so coverage can grow incrementally without ever red-failing CI. See skill {@code metasfresh-archunit}.
	 */
	public static void checkAllModulesIndividually(final JavaClasses allClasses)
	{
		// Cache each class's owning-module label once — URI parsing is not free, and we re-scan per module below.
		final Map<JavaClass, String> labelByClass = new IdentityHashMap<>();
		final Set<String> moduleLabels = new TreeSet<>();
		for (final JavaClass cls : allClasses)
		{
			final String label = moduleLabelOf(cls);
			labelByClass.put(cls, label);
			moduleLabels.add(label);
		}

		logger.info("[ArchUnit per-module] checking {} modules: {}", moduleLabels.size(), moduleLabels);

		final List<String> failingModuleReports = new ArrayList<>();
		for (final String moduleLabel : moduleLabels)
		{
			final JavaClasses moduleClasses = allClasses.that(
					new DescribedPredicate<JavaClass>("owned by module " + moduleLabel)
					{
						@Override
						public boolean test(final JavaClass cls)
						{
							return moduleLabel.equals(labelByClass.get(cls));
						}
					});
			try
			{
				checkAllModuleRules(moduleLabel, moduleClasses);
			}
			catch (final AssertionError e)
			{
				failingModuleReports.add(e.getMessage());
			}
		}

		if (!failingModuleReports.isEmpty())
		{
			throw new AssertionError("ArchUnit found new architecture violations in " + failingModuleReports.size()
					+ " module(s):\n" + String.join("\n", failingModuleReports));
		}
	}

	/**
	 * Derive a stable owning-module label from a class's source jar (e.g. {@code de.metas.business-10.0.0.jar} →
	 * {@code de.metas.business}): the jar's artifact id with the {@code -<version>} suffix stripped. The label is
	 * identical whether the jar is resolved from {@code .m2-local} (CI / maven) or extracted from a Spring Boot fat
	 * jar (baseline generation), so the freeze key is reproducible. Classes not loaded from a jar (e.g. an
	 * assembly's own {@code target/classes}) yield {@code "unknown-module"}; in CI those classes carry their real
	 * jar key instead and simply auto-record.
	 */
	private static String moduleLabelOf(final JavaClass cls)
	{
		final Source source = cls.getSource().orElse(null);
		if (source == null)
		{
			return "unknown-module";
		}
		final String uri = source.getUri().toString();
		final int jarIdx = uri.lastIndexOf(".jar");
		if (jarIdx < 0)
		{
			return "unknown-module";
		}
		final String beforeJar = uri.substring(0, jarIdx);
		final String fileBase = beforeJar.substring(beforeJar.lastIndexOf('/') + 1);
		// Strip the -<version> suffix (a metasfresh artifact version always starts with a digit). Assumes the
		// artifact id itself has no hyphen-then-digit segment — true for the whole backend module corpus.
		return fileBase.replaceFirst("-\\d.*$", "");
	}

	/** Import a single module's own compiled classes (jar or target/classes), failing loudly on an empty set. */
	public static JavaClasses importModule(final String moduleLocationToken)
	{
		final JavaClasses classes = new ClassFileImporter()
				.withImportOption(new ImportOption.DoNotIncludeTests())
				.withImportOption((final Location location) -> location.contains(moduleLocationToken))
				.importPackages(BACKEND_PACKAGE_ROOTS);

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
	 *   <li>Distinct owning modules present (by {@link #moduleLabelOf(JavaClass)} — the source jar / artifact id)</li>
	 * </ul>
	 * and asserts the closure is <b>substantial</b> — a lower bound on the distinct module count — rather than
	 * enumerating a hand-maintained "complete" list (which would rot). Too few modules means the assembly closure
	 * did not fully load.
	 *
	 * @throws IllegalStateException if zero classes were imported, or if the closure is implausibly small
	 */
	public static JavaClasses importWholeBackend()
	{
		final long importStart = System.currentTimeMillis();

		final JavaClasses classes = new ClassFileImporter()
				.withImportOption(new ImportOption.DoNotIncludeTests())
				.importPackages(BACKEND_PACKAGE_ROOTS);

		final long importMs = System.currentTimeMillis() - importStart;

		if (classes.isEmpty())
		{
			throw new IllegalStateException("ArchUnit imported zero classes for the whole backend — "
					+ "the assembly deps are missing from the test classpath");
		}

		// Collect the distinct OWNING MODULES present, using the very same jar-based notion as the per-module
		// freeze grouping (#moduleLabelOf). The completeness probe and the grouping thus speak about "module"
		// identically — there is one module model in this class (the Maven artifact / source jar), not a second
		// package-based one. (Aside: a Maven artifact id is NOT a Java package — e.g. the de.metas.business jar
		// contributes de.metas.invoice, de.metas.invoicecandidate, … packages — which is exactly why we key on
		// the jar, not the package.)
		final Set<String> moduleLabels = new TreeSet<>();
		for (final JavaClass cls : classes)
		{
			moduleLabels.add(moduleLabelOf(cls));
		}

		logger.info("[ArchUnit whole-backend] Total classes imported: {} (import took {} ms)", classes.size(), importMs);
		logger.info("[ArchUnit whole-backend] Distinct owning modules ({}): {}", moduleLabels.size(), moduleLabels);

		// Probe: the closure must be SUBSTANTIAL. We deliberately do NOT enumerate a "complete" module list —
		// there is no authoritative hand-maintained set, and any such list would rot as modules are added/removed.
		// Instead we assert a generous lower bound on the distinct module count: the zero-class guard above catches
		// "nothing loaded", and this catches "loaded only a partial sliver" (assembly deps missing → per-module
		// freeze would silently under-cover). A healthy serverRoot+webui-api closure yields ~140 modules; a floor
		// of minExpectedModules is well below that yet far above any partial-load failure. The full module list
		// is logged above for inspection.
		final int minExpectedModules = 50;
		if (moduleLabels.size() < minExpectedModules)
		{
			throw new IllegalStateException("[ArchUnit whole-backend] INCOMPLETE CLOSURE — only " + moduleLabels.size()
					+ " distinct modules on the classpath (expected >= " + minExpectedModules
					+ "); the serverRoot/webui-api assembly deps did not fully load. Modules present: " + moduleLabels);
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
	 * <p>
	 * Scoped to {@code de.metas} only (not the other {@link #BACKEND_PACKAGE_ROOTS}): {@code de.metas.<x>} is the
	 * bounded-context layer this rule is about, whereas {@code org.adempiere}/{@code org.compiere}/
	 * {@code org.eevolution} are the flat legacy ADempiere base with no bounded-context structure to slice and
	 * known pre-existing cycles — including them would only emit legacy noise on a report-only check.
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
