package de.metas.distribution.ddorder.movement.schedule;

import de.metas.handlingunits.model.X_DD_OrderLine_HU_Candidate;
import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

@AllArgsConstructor
public enum DDOrderMoveScheduleStatus implements ReferenceListAwareEnum
{
	NOT_STARTED(X_DD_OrderLine_HU_Candidate.STATUS_NotStarted),
	IN_PROGRESS(X_DD_OrderLine_HU_Candidate.STATUS_InProgress),
	COMPLETED(X_DD_OrderLine_HU_Candidate.STATUS_Completed),
	;

	private static final ReferenceListAwareEnums.ValuesIndex<DDOrderMoveScheduleStatus> index = ReferenceListAwareEnums.index(values());

	@Getter
	private final String code;

	public static DDOrderMoveScheduleStatus ofCode(@NonNull final String code)
	{
		return index.ofCode(code);
	}

	public static DDOrderMoveScheduleStatus ofNullableCode(final String code)
	{
		return index.ofNullableCode(code);
	}
}
