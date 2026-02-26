package de.metas.payment.paymentterm.repository.impl;

import com.google.common.collect.ImmutableList;
import de.metas.organization.OrgId;
import de.metas.payment.paymentterm.PaymentTerm;
import de.metas.payment.paymentterm.PaymentTermId;
import de.metas.util.lang.Percent;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.adempiere.service.ClientId;
import org.compiere.model.I_C_PaySchedule;
import org.compiere.model.I_C_PaymentTerm;
import org.compiere.model.I_C_PaymentTerm_Break;

import java.util.List;

@UtilityClass
class PaymentTermConverter
{
	@NonNull
	public static PaymentTerm fromRecord(
			@NonNull final I_C_PaymentTerm record,
			@NonNull final List<I_C_PaymentTerm_Break> breakRecords,
			@NonNull final List<I_C_PaySchedule> payScheduleRecords)
	{
		return PaymentTerm.builder()
				.id(extractId(record))
				.clientId(ClientId.ofRepoId(record.getAD_Client_ID()))
				.orgId(OrgId.ofRepoId(record.getAD_Org_ID()))
				.value(record.getValue())
				.name(record.getName())
				.description(record.getDescription())
				.discount(Percent.of(record.getDiscount()))
				.discount2(Percent.of(record.getDiscount2()))
				.discountDays(record.getDiscountDays())
				.discountDays2(record.getDiscountDays2())
				.netDay(record.getNetDay())
				.netDays(record.getNetDays())
				.graceDays(record.getGraceDays())
				.isDefault(record.isDefault())
				.isActive(record.isActive())
				.breaks(breakRecords.stream()
						.filter(I_C_PaymentTerm_Break::isActive)
						.map(PaymentTermBreakConverter::fromRecord)
						.collect(ImmutableList.toImmutableList()))
				.paySchedules(payScheduleRecords.stream()
						.filter(I_C_PaySchedule::isActive)
						.map(PayScheduleConverter::fromRecord)
						.collect(ImmutableList.toImmutableList()))
				.build();
	}

	@NonNull
	public static PaymentTermId extractId(@NonNull final I_C_PaymentTerm record)
	{
		return PaymentTermId.ofRepoId(record.getC_PaymentTerm_ID());
	}

	public static void updateRecord(final I_C_PaymentTerm record, final @NonNull PaymentTerm paymentTerm)
	{
		record.setIsComplex(paymentTerm.isComplex());
		record.setIsValid(paymentTerm.isValid());
	}
}
