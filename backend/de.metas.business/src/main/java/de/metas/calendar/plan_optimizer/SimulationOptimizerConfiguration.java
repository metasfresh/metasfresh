package de.metas.calendar.plan_optimizer;

import com.google.common.annotations.VisibleForTesting;
import de.metas.calendar.plan_optimizer.domain.Plan;
import de.metas.calendar.plan_optimizer.domain.Step;
import de.metas.calendar.plan_optimizer.solver.PlanConstraintProvider;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.service.ISysConfigBL;
import org.optaplanner.core.config.constructionheuristic.ConstructionHeuristicPhaseConfig;
import org.optaplanner.core.config.constructionheuristic.ConstructionHeuristicType;
import org.optaplanner.core.config.heuristic.selector.move.generic.ChangeMoveSelectorConfig;
import org.optaplanner.core.config.heuristic.selector.value.ValueSelectorConfig;
import org.optaplanner.core.config.localsearch.LocalSearchPhaseConfig;
import org.optaplanner.core.config.localsearch.LocalSearchType;
import org.optaplanner.core.config.solver.SolverConfig;
import org.optaplanner.spring.boot.autoconfigure.OptaPlannerAutoConfiguration;
import org.optaplanner.spring.boot.autoconfigure.config.OptaPlannerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.annotation.Nullable;
import java.time.Duration;

@Configuration
// @ConditionalOnClass({ SolverConfig.class, SolverFactory.class, ScoreManager.class, SolutionManager.class, SolverManager.class })
// @ConditionalOnMissingBean({ SolverConfig.class, SolverFactory.class, ScoreManager.class, SolutionManager.class, SolverManager.class })
@EnableConfigurationProperties({ OptaPlannerProperties.class })
public class SimulationOptimizerConfiguration extends OptaPlannerAutoConfiguration
{
	private final ISysConfigBL sysconfigBL = Services.get(ISysConfigBL.class);

	private static final String SYSCONFIG_TerminationSpentLimit = "de.metas.calendar.plan_optimizer.TerminationSpentLimitMillis";
	private static final Duration DEFAULT_TerminationSpentLimit = Duration.ofMinutes(5);

	private ClassLoader beanClassLoader;

	protected SimulationOptimizerConfiguration(ApplicationContext context,
											   OptaPlannerProperties optaPlannerProperties)
	{
		super(context, optaPlannerProperties);
	}

	@Override
	public void setBeanClassLoader(final ClassLoader beanClassLoader)
	{
		this.beanClassLoader = beanClassLoader;
		super.setBeanClassLoader(beanClassLoader);
	}

	@Bean
	@Primary
	@Override
	public SolverConfig solverConfig()
	{
		return solverConfig(beanClassLoader, getTerminationSpentLimit());
	}

	@VisibleForTesting
	public static SolverConfig solverConfig(
			@Nullable final ClassLoader classLoader,
			@NonNull final Duration terminationSpentLimit)
	{
		final ConstructionHeuristicPhaseConfig constructionHeuristicPhaseConfig = new ConstructionHeuristicPhaseConfig();
		constructionHeuristicPhaseConfig.setConstructionHeuristicType(ConstructionHeuristicType.FIRST_FIT);

		final ChangeMoveSelectorConfig moveSelectorConfig = new ChangeMoveSelectorConfig();
		final ValueSelectorConfig valueSelectorConfig = new ValueSelectorConfig();
		valueSelectorConfig.setVariableName(Step.FIELD_delay);
		moveSelectorConfig.setValueSelectorConfig(valueSelectorConfig);

		final LocalSearchPhaseConfig localSearchPhaseConfig = new LocalSearchPhaseConfig();
		localSearchPhaseConfig.setMoveSelectorConfig(moveSelectorConfig);
		localSearchPhaseConfig.setLocalSearchType(LocalSearchType.TABU_SEARCH);

		return new SolverConfig(classLoader)
				.withSolutionClass(Plan.class)
				.withEntityClasses(Step.class)
				.withConstraintProviderClass(PlanConstraintProvider.class)
				.withTerminationSpentLimit(terminationSpentLimit)
				.withPhases(constructionHeuristicPhaseConfig, localSearchPhaseConfig);
	}

	private Duration getTerminationSpentLimit()
	{
		final int millis = sysconfigBL.getIntValue(SYSCONFIG_TerminationSpentLimit, 0);
		return millis > 0 ? Duration.ofMillis(millis) : DEFAULT_TerminationSpentLimit;
	}
}
