package de.metas.calendar.conflicts;

import com.google.common.collect.ImmutableList;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

import java.util.List;
import java.util.stream.Stream;

@EqualsAndHashCode
@ToString
public class CalendarEntryConflicts
{
	public static final CalendarEntryConflicts EMPTY = new CalendarEntryConflicts(ImmutableList.of());

	private final ImmutableList<CalendarEntryConflict> list;

	public CalendarEntryConflicts(@NonNull final List<CalendarEntryConflict> list)
	{
		this.list = ImmutableList.copyOf(list);
	}

	public static CalendarEntryConflicts ofList(@NonNull final List<CalendarEntryConflict> list)
	{
		return !list.isEmpty() ? new CalendarEntryConflicts(list) : EMPTY;
	}

	public boolean isEmpty() {return list.isEmpty();}

	public CalendarEntryConflicts mergeFrom(@NonNull CalendarEntryConflicts other)
	{
		if (other.isEmpty())
		{
			return this;
		}
		else if (this.isEmpty())
		{
			return other;
		}
		else
		{
			return ofList(ImmutableList.<CalendarEntryConflict>builder()
					.addAll(this.list)
					.addAll(other.list)
					.build());
		}
	}

	public Stream<CalendarEntryConflict> stream()
	{
		return list.stream();
	}
}
