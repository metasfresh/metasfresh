package de.metas.project.workorder.calendar;

import com.google.common.collect.ImmutableList;
import de.metas.calendar.CalendarEntryId;
import de.metas.project.budget.BudgetProjectResourceId;
import de.metas.project.workorder.resource.WOProjectResourceId;
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
			@NonNull final Function<BudgetProjectResourceId, T> budgetResourceIdMapper,
			@NonNull final Function<WOProjectResourceId, T> woProjectResourceIdMapper)
	{
		WOProjectCalendarService.CALENDAR_ID.assertEqualsTo(entryId.getCalendarId());
		try
		{
			final ImmutableList<String> entryLocalIds = entryId.getEntryLocalIds();
			final String type = entryLocalIds.get(0);
			if (TYPE_Budget.equals(type))
			{
				return budgetResourceIdMapper.apply(BudgetProjectResourceId.ofRepoIdObjects(entryLocalIds.get(1), entryLocalIds.get(2)));
			}
			else if (TYPE_WorkOrder.equals(type))
			{
				return woProjectResourceIdMapper.apply(WOProjectResourceId.ofRepoIdObjects(entryLocalIds.get(1),entryLocalIds.get(2)));
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

	public static CalendarEntryId from(@NonNull WOProjectResourceId projectResourceId)
	{
		return CalendarEntryId.ofCalendarAndLocalIds(
				WOProjectCalendarService.CALENDAR_ID,
				TYPE_WorkOrder,
				String.valueOf(projectResourceId.getProjectId().getRepoId()),
				String.valueOf(projectResourceId.getRepoId()));
	}

	public static CalendarEntryId from(@NonNull BudgetProjectResourceId budgetProjectResourceId)
	{
		return CalendarEntryId.ofCalendarAndLocalIds(
				WOProjectCalendarService.CALENDAR_ID,
				TYPE_Budget,
				String.valueOf(budgetProjectResourceId.getProjectId().getRepoId()),
				String.valueOf(budgetProjectResourceId.getRepoId()));
	}
}
