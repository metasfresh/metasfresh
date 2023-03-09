package de.metas.project.workorder.calendar;

import de.metas.calendar.CalendarEntry;
import de.metas.calendar.CalendarResourceId;
import de.metas.calendar.simulation.SimulationPlanRef;
import de.metas.common.util.Check;
import de.metas.common.util.CoalesceUtil;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
import de.metas.logging.LogManager;
import de.metas.project.ProjectCategory;
import de.metas.project.budget.BudgetProject;
import de.metas.project.budget.BudgetProjectResource;
import de.metas.project.workorder.project.WOProject;
import de.metas.project.workorder.project.WOProjectService;
import de.metas.project.workorder.resource.WOProjectResource;
import de.metas.project.workorder.step.WOProjectStep;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMDAO;
import de.metas.util.Services;
import de.metas.util.time.DurationUtils;
import de.metas.workflow.WFDurationUnit;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ISysConfigBL;
import org.apache.commons.lang3.StringUtils;
import org.compiere.SpringContextHolder;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.time.temporal.TemporalUnit;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class ToCalendarEntryConverter
{
	private static final String SYSCONFIG_WO_PROJECT_EXTERNAL_ID_PREFIX = "de.metas.project.workorder.calendar.WOProjectExternalIdPrefix";
	private static final Logger logger = LogManager.getLogger(ToCalendarEntryConverter.class);
	@NonNull
	private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
	@NonNull
	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
	@NonNull
	private final WOProjectService woProjectService = SpringContextHolder.instance.getBean(WOProjectService.class);

	@NonNull
	private final ProjectFrontendURLsProvider frontendURLs = new ProjectFrontendURLsProvider();

	@NonNull
	public Optional<CalendarEntry> from(
			@NonNull final WOProjectResource resource,
			@NonNull final WOProjectStep step,
			@NonNull final WOProject project,
			@Nullable final SimulationPlanRef simulationHeaderRef)
	{
		return Optional.ofNullable(resource.getDateRange())
				.map(dateRange -> CalendarEntry
						.builder()
						.entryId(BudgetAndWOCalendarEntryIdConverters.from(resource.getWoProjectResourceId()))
						.simulationId(simulationHeaderRef != null ? simulationHeaderRef.getId() : null)
						.resourceId(CalendarResourceId.ofRepoId(resource.getResourceId()))
						.title(getCalendarWOEntryTitle(project, step, resource))
						.description(TranslatableStrings.anyLanguage(resource.getDescription()))
						.dateRange(dateRange)
						.editable(simulationHeaderRef != null && simulationHeaderRef.isEditable())
						.color(woProjectService.getCalendarColor(project))
						.url(frontendURLs.getProjectUrl(ProjectCategory.WorkOrderJob, resource.getProjectId()).orElse(null))

						.help(computeHelpTextForCalendarEntry(project))
						.build());
	}

	@NonNull
	public Function<WOProjectResource, CalendarEntry> asFunction(
			@NonNull final SimulationPlanRef simulationPlanHeader,
			@NonNull final WOProjectSimulationPlanEditor simulationEditor)
	{
		return woProjectResource -> from(
				woProjectResource,
				simulationEditor.getStepById(woProjectResource.getWoProjectStepId()),
				simulationEditor.getProjectById(woProjectResource.getProjectId()),
				simulationPlanHeader)
				.orElseThrow(() -> new AdempiereException("CalendarEntry could not be obtain for WOProjectResource!")
						.appendParametersToMessage()
						.setParameter("WOProjectResource", woProjectResource));
	}

	@NonNull
	private String getTemporalUnitSymbolOrEmpty(final @NonNull TemporalUnit temporalUnit)
	{
		try
		{
			return StringUtils.trimToEmpty(uomDAO.getByTemporalUnit(temporalUnit).getUOMSymbol());
		}
		catch (final Exception ex)
		{
			logger.warn("Failed to get UOM Symbol for TemporalUnit: {}", temporalUnit, ex);
			return "";
		}
	}

	@NonNull
	public CalendarEntry from(
			@NonNull final BudgetProjectResource budget,
			@NonNull final BudgetProject project,
			@Nullable final SimulationPlanRef simulationHeaderRef)
	{
		return CalendarEntry.builder()
				.entryId(BudgetAndWOCalendarEntryIdConverters.from(budget.getId()))
				.simulationId(simulationHeaderRef != null ? simulationHeaderRef.getId() : null)
				.resourceId(CalendarResourceId.ofRepoId(CoalesceUtil.coalesceNotNull(budget.getResourceId(),
																					 budget.getResourceGroupId())))
				.title(getCalendarBudgetEntryTitle(project, budget.getPlannedDuration()))
				.description(TranslatableStrings.anyLanguage(budget.getDescription()))
				.dateRange(budget.getDateRange())
				.editable(simulationHeaderRef != null && simulationHeaderRef.isEditable())
				.color("#89D72D") // metasfresh green
				.url(frontendURLs.getProjectUrl(ProjectCategory.Budget, budget.getProjectId()).orElse(null))
				.build();
	}

	@NonNull
	private ITranslatableString getCalendarWOEntryTitle(
			@NonNull final WOProject project,
			@NonNull final WOProjectStep step,
			@NonNull final WOProjectResource resource)
	{
		final WFDurationUnit durationUnit = resource.getDurationUnit();

		final int durationInt = DurationUtils.toInt(resource.getDuration(), durationUnit.getTemporalUnit());
		final String durationUomSymbol = getTemporalUnitSymbolOrEmpty(durationUnit.getTemporalUnit());

		final String externalIdPrefix = sysConfigBL.getValue(SYSCONFIG_WO_PROJECT_EXTERNAL_ID_PREFIX);
		final String externalIdWithPrefix = project.getExternalIdAsString()
				.filter(Check::isNotBlank)
				.map(externalId -> externalIdPrefix + externalId + " - ")
				.orElse(null);

		return TranslatableStrings.builder()
				.append(externalIdWithPrefix)
				.append(project.getName())
				.append(" - ")
				.append(step.getSeqNo() + "_" + step.getName())
				.append(" - ")
				.appendQty(durationInt, durationUomSymbol)
				.build();
	}

	@NonNull
	private ITranslatableString getCalendarBudgetEntryTitle(@NonNull final BudgetProject project, @NonNull final Quantity plannedDuration)
	{
		final String externalIdPrefix = project.getExternalIdAsString()
				.filter(Check::isNotBlank)
				.map(externalId -> externalId + " - ")
				.orElse(null);

		return TranslatableStrings.builder()
				.append(externalIdPrefix)
				.append(project.getName())
				.append(" - ")
				.appendQty(plannedDuration.toBigDecimal(), plannedDuration.getUOMSymbol())
				.build();
	}

	@NonNull
	private static String computeHelpTextForCalendarEntry(@NonNull final WOProject project)
	{
		final String projectExternalId = project.getExternalIdAsString()
				.orElse(null);

		return Stream.of(projectExternalId, project.getProjectReferenceExt())
				.filter(de.metas.util.Check::isNotBlank)
				.collect(Collectors.joining(", "));
	}
}
