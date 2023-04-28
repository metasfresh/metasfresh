package de.metas.project.workorder.conflicts;

import de.metas.calendar.conflicts.CalendarEntryConflictStatus;
import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import org.compiere.model.X_C_Project_WO_Resource_Conflict;

@AllArgsConstructor
public enum ResourceAllocationConflictStatus implements ReferenceListAwareEnum
{
	CONFLICT(X_C_Project_WO_Resource_Conflict.STATUS_Conflict, CalendarEntryConflictStatus.CONFLICT),
	RESOLVED(X_C_Project_WO_Resource_Conflict.STATUS_Resolved, CalendarEntryConflictStatus.RESOLVED),
	;

	private static final ReferenceListAwareEnums.ValuesIndex<ResourceAllocationConflictStatus> index = ReferenceListAwareEnums.index(values());

	@Getter
	private final String code;
	private final CalendarEntryConflictStatus calendarEntryConflictStatus;

	public static ResourceAllocationConflictStatus ofCode(@NonNull final String code) {return index.ofCode(code);}

	public boolean isConflict() {return CONFLICT.equals(this);}

	public boolean isResolved() {return RESOLVED.equals(this);}

	public CalendarEntryConflictStatus toCalendarEntryConflictStatus() {return calendarEntryConflictStatus;}
}
