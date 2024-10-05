package de.metas.pos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.collect.ImmutableSetMultimap;
import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import de.metas.util.lang.ReferenceListAwareEnums.ValuesIndex;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.Objects;

@RequiredArgsConstructor
public enum POSOrderStatus implements ReferenceListAwareEnum
{
	Drafted("DR"),
	WaitingPayment("WP"),
	Completed("CO"),
	Voided("VO");

	private static final ValuesIndex<POSOrderStatus> index = ReferenceListAwareEnums.index(values());

	private static final ImmutableSetMultimap<POSOrderStatus, POSOrderStatus> allowedTransitions = ImmutableSetMultimap.<POSOrderStatus, POSOrderStatus>builder()
			.put(Drafted, WaitingPayment)
			.put(Drafted, Voided)
			.put(WaitingPayment, Drafted)
			.put(WaitingPayment, Completed)
			.put(WaitingPayment, Voided)
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

	public void assertCanTransitionTo(@NonNull final POSOrderStatus nextStatus)
	{
		if (!allowedTransitions.containsEntry(this, nextStatus))
		{
			throw new AdempiereException("Changing status from " + this + " to " + nextStatus + " is not allowed");
		}
	}

}
