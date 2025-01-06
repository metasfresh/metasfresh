package de.metas.pos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSetMultimap;
import de.metas.i18n.BooleanWithReason;
import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import de.metas.util.lang.ReferenceListAwareEnums.ValuesIndex;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.annotation.Nullable;
import java.util.Objects;

@RequiredArgsConstructor
public enum POSOrderStatus implements ReferenceListAwareEnum
{
	Drafted("DR"),
	WaitingPayment("WP"),
	Completed("CO"),
	Voided("VO"),
	Closed("CL"),
	;

	// IMPORTANT: keep in sync with misc/services/mobile-webui/mobile-webui-frontend/src/apps/pos/constants/orderStatus.js - OPEN_ORDER_STATUSES
	public static final ImmutableSet<POSOrderStatus> OPEN_STATUSES = ImmutableSet.of(Drafted, WaitingPayment, Completed);

	private static final ValuesIndex<POSOrderStatus> index = ReferenceListAwareEnums.index(values());

	private static final ImmutableSetMultimap<POSOrderStatus, POSOrderStatus> allowedTransitions = ImmutableSetMultimap.<POSOrderStatus, POSOrderStatus>builder()
			.put(Drafted, WaitingPayment)
			.put(Drafted, Voided)
			.put(WaitingPayment, Drafted)
			.put(WaitingPayment, Completed)
			.put(WaitingPayment, Voided)
			.put(Completed, Closed)
			.build();

	@NonNull private final String code;

	@JsonCreator
	public static POSOrderStatus ofCode(@NonNull String code) {return index.ofCode(code);}

	public boolean isDrafted() {return this == Drafted;}

	public boolean isWaitingPayment() {return this == WaitingPayment;}

	public boolean isCompleted() {return this == Completed;}

	public boolean isVoided() {return this == Voided;}

	public boolean isProcessed() {return isCompleted() || isVoided();}

	public boolean isActive() {return !isVoided();}

	public static boolean equals(@Nullable final POSOrderStatus status1, @Nullable final POSOrderStatus status2) {return Objects.equals(status1, status2);}

	@JsonValue
	@NonNull
	public String getCode() {return code;}

	public BooleanWithReason checkCanTransitionTo(final @NonNull POSOrderStatus nextStatus)
	{
		return allowedTransitions.containsEntry(this, nextStatus)
				? BooleanWithReason.TRUE
				: BooleanWithReason.falseBecause("Changing status from " + this + " to " + nextStatus + " is not allowed");
	}

}
