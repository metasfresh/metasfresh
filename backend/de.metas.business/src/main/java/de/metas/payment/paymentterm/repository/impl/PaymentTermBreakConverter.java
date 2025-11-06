package de.metas.payment.paymentterm.repository.impl;

import de.metas.payment.paymentterm.PaymentTermBreak;
import de.metas.payment.paymentterm.PaymentTermBreakId;
import de.metas.payment.paymentterm.PaymentTermId;
import de.metas.payment.paymentterm.ReferenceDateType;
import de.metas.util.lang.Percent;
import de.metas.util.lang.SeqNo;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.compiere.model.I_C_PaymentTerm_Break;
import org.jetbrains.annotations.NotNull;

@UtilityClass
class PaymentTermBreakConverter
{
	public static PaymentTermBreak fromRecord(@NonNull final I_C_PaymentTerm_Break record)
	{
		return PaymentTermBreak.builder()
				.id(PaymentTermBreakId.ofRepoId(extractPaymentTermId(record), record.getC_PaymentTerm_Break_ID()))
				.seqNo(SeqNo.ofInt(record.getSeqNo()))
				.description(record.getDescription())
				.percent(Percent.of(record.getPercent()))
				.referenceDateType(ReferenceDateType.ofCode(record.getReferenceDateType()))
				.offsetDays(record.getOffsetDays())
				.build();
	}

	@NonNull
	public static PaymentTermId extractPaymentTermId(final @NotNull I_C_PaymentTerm_Break record)
	{
		return PaymentTermId.ofRepoId(record.getC_PaymentTerm_ID());
	}
}
