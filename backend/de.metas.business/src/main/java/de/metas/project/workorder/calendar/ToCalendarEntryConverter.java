package de.metas.project.workorder.calendar;

import de.metas.calendar.CalendarEntry;
import de.metas.calendar.CalendarResourceId;
import de.metas.calendar.simulation.SimulationPlanRef;
import de.metas.common.util.Check;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
import de.metas.logging.LogManager;
import de.metas.organization.ClientAndOrgId;
import de.metas.organization.OrgId;
import de.metas.project.ProjectCategory;
import de.metas.project.budget.BudgetProject;
import de.metas.project.budget.BudgetProjectResource;
import de.metas.project.workorder.project.WOProject;
import de.metas.project.workorder.project.WOProjectService;
import de.metas.project.workorder.resource.ResourceIdAndType;
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
import org.compiere.util.Env;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.time.temporal.TemporalUnit;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static de.metas.project.ProjectConstants.DEFAULT_BUDGET_CALENDAR_ENTRY_COLOR;

class ToCalendarEntryConverter
{
	private static final String SYSCONFIG_WO_PROJECT_EXTERNAL_ID_PREFIX = "de.metas.project.workorder.calendar.WOProjectExternalIdPrefix";
	private static final Logger logger = LogManager.getLogger(ToCalendarEntryConverter.class);
	@NonNull
	private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
	@NonNull
	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
	@NonNull
	private final WOProjectService woProjectService;

	@NonNull
	private final ProjectFrontendURLsProvider frontendURLs = new ProjectFrontendURLsProvider();

	public ToCalendarEntryConverter(
			@NonNull final WOProjectService woProjectService)
	{
		this.woProjectService = woProjectService;
	}

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
						.resourceId(resource.getResourceIdAndType().toCalendarResourceId())
						.title(getCalendarWOEntryTitle(project, step, resource))
						.description(TranslatableStrings.anyLanguage(resource.getDescription()))
						.dateRange(dateRange)
						.editable(isEditable(step, simulationHeaderRef))
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
				.resourceId(extractCalendarResourceId(budget))
				.title(getCalendarBudgetEntryTitle(project, budget.getPlannedDuration()))
				.description(TranslatableStrings.anyLanguage(budget.getDescription()))
				.dateRange(budget.getDateRange())
				.editable(simulationHeaderRef != null && simulationHeaderRef.isEditable())
				.color(DEFAULT_BUDGET_CALENDAR_ENTRY_COLOR) // metasfresh green
				.url(frontendURLs.getProjectUrl(ProjectCategory.Budget, budget.getProjectId()).orElse(null))
				.build();
	}

	@NonNull
	private static CalendarResourceId extractCalendarResourceId(final @NonNull BudgetProjectResource budget)
	{
		if (budget.getResourceId() != null)
		{
			return ResourceIdAndType.machine(budget.getResourceId()).toCalendarResourceId();
		}
		else if (budget.getResourceGroupId() != null)
		{
			return CalendarResourceId.ofResourceGroupId(budget.getResourceGroupId());
		}
		else
		{
			throw new AdempiereException("Cannot extract CalendarResourceId from " + budget);
		}
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

		return TranslatableStrings.builder()
				.append(getWOExternalIdWithPrefix(project)
						.map(externalId -> externalId + " - ")
						.orElse(""))
				.append(project.getName())
				.append(" - ")
				.append(step.getSeqNo() + "_" + step.getName())
				.append(" - ")
				.appendQty(durationInt, durationUomSymbol)
				.build();
	}

	@NonNull
	private Optional<String> getWOExternalIdWithPrefix(@NonNull final WOProject project)
	{
		return project.getExternalIdAsString()
				.filter(Check::isNotBlank)
				.map(externalId -> getExternalIdPrefix(project.getOrgId()) + externalId);
	}

	@NonNull
	private String computeHelpTextForCalendarEntry(@NonNull final WOProject project)
	{
		return Stream.of(getWOExternalIdWithPrefix(project).orElse(""), project.getProjectReferenceExt())
				.filter(Check::isNotBlank)
				.collect(Collectors.joining(", "));
	}

	@NonNull
	private String getExternalIdPrefix(@NonNull final OrgId orgId)
	{
		final ClientAndOrgId clientAndOrgId = ClientAndOrgId.ofClientAndOrg(Env.getClientId(), orgId);
		return Optional.ofNullable(sysConfigBL.getValue(SYSCONFIG_WO_PROJECT_EXTERNAL_ID_PREFIX, clientAndOrgId))
				.orElse("");
	}

	@NonNull
	private static ITranslatableString getCalendarBudgetEntryTitle(
			@NonNull final BudgetProject project,
			@NonNull final Quantity plannedDuration)
	{
		return TranslatableStrings.builder()
				.append(project.getExternalIdAsString()
						.map(externalId -> externalId + " - ")
						.orElse(""))
				.append(project.getName())
				.append(" - ")
				.appendQty(plannedDuration.toBigDecimal(), plannedDuration.getUOMSymbol())
				.build();
	}

	private static boolean isEditable(
			@NonNull final WOProjectStep step,
			@Nullable final SimulationPlanRef simulationHeaderRef)
	{
		if (step.isManuallyLocked())
		{
			return false;
		}

		return simulationHeaderRef != null && simulationHeaderRef.isEditable();
	}
}
