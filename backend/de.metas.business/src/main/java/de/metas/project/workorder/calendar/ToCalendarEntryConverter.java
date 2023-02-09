package de.metas.project.workorder.calendar;

import de.metas.calendar.CalendarEntry;
import de.metas.calendar.CalendarResourceId;
import de.metas.calendar.simulation.SimulationPlanRef;
import de.metas.common.util.CoalesceUtil;
import de.metas.i18n.TranslatableStrings;
import de.metas.logging.LogManager;
import de.metas.project.ProjectCategory;
import de.metas.project.budget.BudgetProject;
import de.metas.project.budget.BudgetProjectResource;
import de.metas.project.workorder.project.WOProject;
import de.metas.project.workorder.resource.WOProjectResource;
import de.metas.project.workorder.step.WOProjectStep;
import de.metas.uom.IUOMDAO;
import de.metas.util.Services;
import de.metas.util.time.DurationUtils;
import de.metas.workflow.WFDurationUnit;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.time.temporal.TemporalUnit;
import java.util.Optional;
import java.util.function.Function;

class ToCalendarEntryConverter
{
	private static final Logger logger = LogManager.getLogger(ToCalendarEntryConverter.class);
	@NonNull
	private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
	@NonNull
	private final ProjectFrontendURLsProvider frontendURLs = new ProjectFrontendURLsProvider();

	@NonNull
	public Optional<CalendarEntry> from(
			@NonNull final WOProjectResource resource,
			@NonNull final WOProjectStep step,
			@NonNull final WOProject project,
			@Nullable final SimulationPlanRef simulationHeaderRef)
	{
		final WFDurationUnit durationUnit = resource.getDurationUnit();

		final int durationInt = DurationUtils.toInt(resource.getDuration(), durationUnit.getTemporalUnit());
		final String durationUomSymbol = getTemporalUnitSymbolOrEmpty(durationUnit.getTemporalUnit());

		return Optional.ofNullable(resource.getDateRange())
				.map(dateRange -> CalendarEntry
						.builder()
						.entryId(BudgetAndWOCalendarEntryIdConverters.from(resource.getWoProjectResourceId()))
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
						.dateRange(dateRange)
						.editable(simulationHeaderRef != null && simulationHeaderRef.isEditable())
						.color("#FFCF60") // orange-ish
						.url(frontendURLs.getProjectUrl(ProjectCategory.WorkOrderJob, resource.getProjectId()).orElse(null))
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
				.title(TranslatableStrings.builder()
							   .append(project.getName())
							   .append(" - ")
							   .appendQty(budget.getPlannedDuration().toBigDecimal(),
										  budget.getPlannedDuration().getUOMSymbol())
							   .build())
				.description(TranslatableStrings.anyLanguage(budget.getDescription()))
				.dateRange(budget.getDateRange())
				.editable(simulationHeaderRef != null && simulationHeaderRef.isEditable())
				.color("#89D72D") // metasfresh green
				.url(frontendURLs.getProjectUrl(ProjectCategory.Budget, budget.getProjectId()).orElse(null))
				.build();
	}

}
