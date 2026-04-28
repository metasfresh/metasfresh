package de.metas.payment.paymentterm.repository.impl;

import de.metas.payment.paymentterm.PaySchedule;
import de.metas.payment.paymentterm.PayScheduleId;
import de.metas.payment.paymentterm.PaymentTermId;
import de.metas.util.StringUtils;
import de.metas.util.lang.Percent;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_PaySchedule;
import org.compiere.model.X_C_PaySchedule;

import java.time.DayOfWeek;

@UtilityClass
class PayScheduleConverter
{
	public static PaySchedule fromRecord(@NonNull final I_C_PaySchedule record)
	{
		return PaySchedule.builder()
				.id(extractId(record))
				.percentage(Percent.of(record.getPercentage()))
				.discount(Percent.of(record.getDiscount()))
				.netDay(extractNetDay(record))
				.netDays(record.getNetDays())
				.graceDays(record.getGraceDays())
				.discountDays(record.getDiscountDays())
				.build();
	}

	@NonNull
	public static PayScheduleId extractId(final @NonNull I_C_PaySchedule record)
	{
		return PayScheduleId.ofRepoId(record.getC_PaySchedule_ID());
	}

	@NonNull
	public static PaymentTermId extractPaymentTermId(final @NonNull I_C_PaySchedule record)
	{
		return PaymentTermId.ofRepoId(record.getC_PaymentTerm_ID());
	}

	private static DayOfWeek extractNetDay(@NonNull final I_C_PaySchedule record)
	{
		final String netDayStr = StringUtils.trimBlankToNull(record.getNetDay());
		if (netDayStr == null)
		{
			return null;
		}
		switch (netDayStr)
		{
			case X_C_PaySchedule.NETDAY_Monday:
				return DayOfWeek.MONDAY;
			case X_C_PaySchedule.NETDAY_Tuesday:
				return DayOfWeek.TUESDAY;
			case X_C_PaySchedule.NETDAY_Wednesday:
				return DayOfWeek.WEDNESDAY;
			case X_C_PaySchedule.NETDAY_Thursday:
				return DayOfWeek.THURSDAY;
			case X_C_PaySchedule.NETDAY_Friday:
				return DayOfWeek.FRIDAY;
			case X_C_PaySchedule.NETDAY_Saturday:
				return DayOfWeek.SATURDAY;
			case X_C_PaySchedule.NETDAY_Sunday:
				return DayOfWeek.SUNDAY;
			default:
				throw new AdempiereException("Unknown net day: " + netDayStr);
		}
	}
}
