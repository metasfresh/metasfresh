package de.metas.payment.sumup;

import de.metas.organization.ClientAndOrgId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;

@Value
@Builder
@Jacksonized
public class SumUpTransactionStatusChangedEvent
{
	@NonNull SumUpClientTransactionId clientTransactionId;
	@NonNull SumUpConfigId configId;
	@NonNull ClientAndOrgId clientAndOrgId;
	int posOrderId;
	int posPaymentId;

	@NonNull SumUpTransactionStatus statusNew;
	@Nullable SumUpTransactionStatus statusOld;

	public static SumUpTransactionStatusChangedEvent ofNewTransaction(@NonNull final SumUpTransaction trx)
	{
		return builderFrom(trx).build();
	}

	public static SumUpTransactionStatusChangedEvent ofChangedTransaction(@NonNull final SumUpTransaction trx, @NonNull final SumUpTransactionStatus statusPrev)
	{
		return builderFrom(trx)
				.statusOld(statusPrev)
				.build();
	}

	private static SumUpTransactionStatusChangedEventBuilder builderFrom(final @NonNull SumUpTransaction trx)
	{
		return builder()
				.clientTransactionId(trx.getClientTransactionId())
				.configId(trx.getConfigId())
				.clientAndOrgId(trx.getClientAndOrgId())
				.posOrderId(trx.getPosOrderId())
				.posPaymentId(trx.getPosPaymentId())
				.statusNew(trx.getStatus());
	}
}
