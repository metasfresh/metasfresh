package de.metas.payment.paymentterm;

import de.metas.util.lang.Percent;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
@Builder
public class PaySchedule
{
	@NonNull PayScheduleId id;
	@NonNull PaymentTermId paymentTermId;
	@Nullable Percent discount;
	@Nullable String netDay;

	int discountDays;
	int graceDays;
	int netDays;
}
