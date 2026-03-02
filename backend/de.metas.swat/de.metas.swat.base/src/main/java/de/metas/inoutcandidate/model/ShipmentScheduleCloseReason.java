package de.metas.inoutcandidate.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import de.metas.util.lang.ReferenceListAwareEnums.ValuesIndex;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.annotation.Nullable;

@RequiredArgsConstructor
@Getter
public enum ShipmentScheduleCloseReason implements ReferenceListAwareEnum
{
	Manual(X_M_ShipmentSchedule.CLOSEREASON_Manual),
	OrderReactivated(X_M_ShipmentSchedule.CLOSEREASON_OrderReactivated),
	PartiallyShipped(X_M_ShipmentSchedule.CLOSEREASON_PartiallyShipped),
	InvoiceCandidateClosed(X_M_ShipmentSchedule.CLOSEREASON_InvoiceCandidateClosed),
	FlatrateTerm(X_M_ShipmentSchedule.CLOSEREASON_FlatrateTerm),
	ContractPause(X_M_ShipmentSchedule.CLOSEREASON_ContractPause),
	ShipmentProcessed(X_M_ShipmentSchedule.CLOSEREASON_ShipmentProcessed),
	PickingRejected(X_M_ShipmentSchedule.CLOSEREASON_PickingRejected),
	OutOfStock(X_M_ShipmentSchedule.CLOSEREASON_OutOfStock),
	;

	@NonNull private static final ValuesIndex<ShipmentScheduleCloseReason> index = ReferenceListAwareEnums.index(values());

	@NonNull private final String code;

	@JsonCreator
	@NonNull
	public static ShipmentScheduleCloseReason ofCode(@NonNull final String code)
	{
		return index.ofCode(code);
	}

	@Nullable
	public static ShipmentScheduleCloseReason ofNullableCode(@Nullable final String code)
	{
		return code != null ? ofCode(code) : null;
	}

	@Nullable
	public static String toCodeOrNull(@Nullable final ShipmentScheduleCloseReason reason)
	{
		return reason != null ? reason.getCode() : null;
	}

	@JsonValue
	public String toJson()
	{
		return code;
	}
}
