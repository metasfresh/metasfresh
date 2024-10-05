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
	@Nullable SumUpPOSRef posRef;

	@NonNull SumUpTransactionStatus statusNew;
	@Nullable SumUpTransactionStatus statusOld;

	boolean refundedNew;
	boolean refundedOld;

	public static SumUpTransactionStatusChangedEvent ofNewTransaction(@NonNull final SumUpTransaction trx)
	{
		return builderFrom(trx).build();
	}

	public static SumUpTransactionStatusChangedEvent ofChangedTransaction(
			@NonNull final SumUpTransaction trx,
			@NonNull final SumUpTransaction trxPrev)
	{
		return builderFrom(trx)
				.statusOld(trxPrev.getStatus())
				.refundedOld(trxPrev.isRefunded())
				.build();
	}

	private static SumUpTransactionStatusChangedEventBuilder builderFrom(final @NonNull SumUpTransaction trx)
	{
		return builder()
				.clientTransactionId(trx.getClientTransactionId())
				.configId(trx.getConfigId())
				.clientAndOrgId(trx.getClientAndOrgId())
				.posRef(trx.getPosRef())
				.statusNew(trx.getStatus())
				.refundedNew(trx.isRefunded());
	}
}
