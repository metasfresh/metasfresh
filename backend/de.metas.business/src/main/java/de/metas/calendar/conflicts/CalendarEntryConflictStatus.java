package de.metas.calendar.conflicts;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum CalendarEntryConflictStatus
{
	RESOLVED,
	CONFLICT;

	public boolean isConflict()
	{
		return CONFLICT.equals(this);
	}

	public String toJson() {return name();}
}
