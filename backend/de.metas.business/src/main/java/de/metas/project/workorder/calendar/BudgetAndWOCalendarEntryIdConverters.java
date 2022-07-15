package de.metas.project.workorder.calendar;

import com.google.common.collect.ImmutableList;
import de.metas.calendar.CalendarEntryId;
import de.metas.project.ProjectId;
import de.metas.project.budget.BudgetProjectAndResourceId;
import de.metas.project.budget.BudgetProjectResourceId;
import de.metas.project.workorder.WOProjectAndResourceId;
import de.metas.project.workorder.WOProjectResourceId;
import de.metas.util.lang.RepoIdAwares;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.adempiere.exceptions.AdempiereException;

import java.util.function.Function;

@UtilityClass
public final class BudgetAndWOCalendarEntryIdConverters
{
	private static final String TYPE_Budget = "budget";
	private static final String TYPE_WorkOrder = "WO";

	public static <T> T withProjectResourceId(
			@NonNull final CalendarEntryId entryId,
			@NonNull final Function<BudgetProjectAndResourceId, T> budgetResourceIdMapper,
			@NonNull final Function<WOProjectAndResourceId, T> woProjectResourceIdMapper)
	{
		WOProjectCalendarService.CALENDAR_ID.assertEqualsTo(entryId.getCalendarId());
		try
		{
			final ImmutableList<String> entryLocalIds = entryId.getEntryLocalIds();
			final String type = entryLocalIds.get(0);
			if (TYPE_Budget.equals(type))
			{
				return budgetResourceIdMapper.apply(
						BudgetProjectAndResourceId.of(
								RepoIdAwares.ofObject(entryLocalIds.get(1), ProjectId.class),
								RepoIdAwares.ofObject(entryLocalIds.get(2), BudgetProjectResourceId.class)));
			}
			else if (TYPE_WorkOrder.equals(type))
			{
				return woProjectResourceIdMapper.apply(
						WOProjectAndResourceId.of(
								RepoIdAwares.ofObject(entryLocalIds.get(1), ProjectId.class),
								RepoIdAwares.ofObject(entryLocalIds.get(2), WOProjectResourceId.class)));
			}
			else
			{
				throw new AdempiereException("Invalid ID: " + entryId);
			}
		}
		catch (AdempiereException ex)
		{
			throw ex;
		}
		catch (Exception ex)
		{
			throw new AdempiereException("Invalid ID: " + entryId, ex);
		}
	}

	public static CalendarEntryId from(@NonNull WOProjectAndResourceId woProjectResourceId)
	{
		return from(woProjectResourceId.getProjectId(), woProjectResourceId.getProjectResourceId());
	}

	public static CalendarEntryId from(@NonNull ProjectId projectId, @NonNull WOProjectResourceId projectResourceId)
	{
		return CalendarEntryId.ofCalendarAndLocalIds(
				WOProjectCalendarService.CALENDAR_ID,
				TYPE_WorkOrder,
				String.valueOf(projectId.getRepoId()),
				String.valueOf(projectResourceId.getRepoId()));
	}

	public static CalendarEntryId from(@NonNull BudgetProjectAndResourceId budgetProjectAndResourceId)
	{
		return from(budgetProjectAndResourceId.getProjectId(), budgetProjectAndResourceId.getProjectResourceId());
	}

	public static CalendarEntryId from(@NonNull ProjectId projectId, @NonNull BudgetProjectResourceId projectResourceId)
	{
		return CalendarEntryId.ofCalendarAndLocalIds(
				WOProjectCalendarService.CALENDAR_ID,
				TYPE_Budget,
				String.valueOf(projectId.getRepoId()),
				String.valueOf(projectResourceId.getRepoId()));
	}
}
