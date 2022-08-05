package de.metas.project.workorder.calendar;

import de.metas.calendar.CalendarEntry;
import de.metas.calendar.CalendarResourceId;
import de.metas.calendar.simulation.SimulationPlanRef;
import de.metas.common.util.CoalesceUtil;
import de.metas.i18n.TranslatableStrings;
import de.metas.logging.LogManager;
import de.metas.project.ProjectCategory;
import de.metas.project.budget.BudgetProject;
import de.metas.project.budget.BudgetProjectData;
import de.metas.project.budget.BudgetProjectResource;
import de.metas.project.budget.BudgetProjectResourceData;
import de.metas.project.workorder.WOProject;
import de.metas.project.workorder.WOProjectResource;
import de.metas.project.workorder.WOProjectStep;
import de.metas.uom.IUOMDAO;
import de.metas.util.Services;
import de.metas.util.time.DurationUtils;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.time.temporal.TemporalUnit;
import java.util.function.Function;

class ToCalendarEntryConverter
{
	private static final Logger logger = LogManager.getLogger(ToCalendarEntryConverter.class);
	@NonNull
	private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
	@NonNull
	private final ProjectFrontendURLsProvider frontendURLs = new ProjectFrontendURLsProvider();

	public CalendarEntry from(
			@NonNull final WOProjectResource resource,
			@NonNull final WOProjectStep step,
			@NonNull final WOProject project,
			@Nullable final SimulationPlanRef simulationHeaderRef)
	{
		final int durationInt = DurationUtils.toInt(resource.getDuration(), resource.getDurationUnit());
		final String durationUomSymbol = getTemporalUnitSymbolOrEmpty(resource.getDurationUnit());

		return CalendarEntry.builder()
				.entryId(BudgetAndWOCalendarEntryIdConverters.from(resource.getId()))
				.simulationId(simulationHeaderRef != null ? simulationHeaderRef.getId() : null)
				.resourceId(CalendarResourceId.ofRepoId(resource.getResourceId()))
				.title(TranslatableStrings.builder()
							   .append(project.getName())
							   .append(" - ")
							   .append(step.getSeqNo() + "_" + step.getName())
							   .append(" - ")
							   .appendQty(durationInt, durationUomSymbol)
							   .build()
				)
				.description(TranslatableStrings.anyLanguage(resource.getDescription()))
				.dateRange(resource.getDateRange())
				.editable(simulationHeaderRef != null && simulationHeaderRef.isEditable())
				.color("#FFCF60") // orange-ish
				.url(frontendURLs.getProjectUrl(ProjectCategory.WorkOrderJob, resource.getProjectId()).orElse(null))
				.build();
	}

	public Function<WOProjectResource, CalendarEntry> asFunction(
			@NonNull final SimulationPlanRef simulationPlanHeader,
			@NonNull final WOProjectSimulationPlanEditor simulationEditor)
	{
		return woProjectResource -> from(
				woProjectResource,
				simulationEditor.getStepById(woProjectResource.getStepId()),
				simulationEditor.getProjectById(woProjectResource.getProjectId()),
				simulationPlanHeader);
	}

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

	public CalendarEntry from(
			@NonNull final BudgetProjectResource budget,
			@NonNull final BudgetProject project,
			@Nullable final SimulationPlanRef simulationHeaderRef)
	{
		final BudgetProjectData budgetProjectData = project.getBudgetProjectData();
		final BudgetProjectResourceData budgetProjectResourceData = budget.getBudgetProjectResourceData();

		return CalendarEntry.builder()
				.entryId(BudgetAndWOCalendarEntryIdConverters.from(budget.getId()))
				.simulationId(simulationHeaderRef != null ? simulationHeaderRef.getId() : null)
				.resourceId(CalendarResourceId.ofRepoId(CoalesceUtil.coalesceNotNull(budgetProjectResourceData.getResourceId(),
																					 budgetProjectResourceData.getResourceGroupId())))
				.title(TranslatableStrings.builder()
							   .append(budgetProjectData.getName())
							   .append(" - ")
							   .appendQty(budgetProjectResourceData.getPlannedDuration().toBigDecimal(),
										  budgetProjectResourceData.getPlannedDuration().getUOMSymbol())
							   .build())
				.description(TranslatableStrings.anyLanguage(budgetProjectResourceData.getDescription()))
				.dateRange(budgetProjectResourceData.getDateRange())
				.editable(simulationHeaderRef != null && simulationHeaderRef.isEditable())
				.color("#89D72D") // metasfresh green
				.url(frontendURLs.getProjectUrl(ProjectCategory.Budget, budget.getProjectId()).orElse(null))
				.build();
	}

}
