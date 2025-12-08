package de.metas.payment.sumup;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import de.metas.common.util.time.SystemTime;
import de.metas.currency.Amount;
import de.metas.error.AdIssueId;
import de.metas.organization.ClientAndOrgId;
import de.metas.payment.sumup.repository.model.X_SUMUP_Transaction;
import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import de.metas.util.lang.ReferenceListAwareEnums.ValuesIndex;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.time.Instant;

@Value
public class SumUpTransaction
{
	@NonNull SumUpConfigId configId;
	@NonNull SumUpTransactionExternalId externalId;
	@NonNull SumUpClientTransactionId clientTransactionId;
	@NonNull SumUpMerchantCode merchantCode;
	@Nullable CardReader cardReader;
	@NonNull Instant timestamp;

	@NonNull SumUpTransactionStatus status;
	@NonNull Amount amount;
	@NonNull Amount amountRefunded;
	@Nullable Card card;

	@Nullable String json;

	@NonNull ClientAndOrgId clientAndOrgId;
	@Nullable SumUpPOSRef posRef;

	@Nullable LastSync lastSync;

	@Builder(toBuilder = true)
	@Jacksonized
	private SumUpTransaction(
			@NonNull final SumUpConfigId configId,
			@NonNull final SumUpTransactionExternalId externalId,
			@NonNull final SumUpClientTransactionId clientTransactionId,
			@NonNull final SumUpMerchantCode merchantCode,
			@Nullable final CardReader cardReader,
			@NonNull final Instant timestamp,
			@NonNull final SumUpTransactionStatus status,
			@NonNull final Amount amount,
			@Nullable final Amount amountRefunded,
			@Nullable final Card card,
			@Nullable final String json,
			@NonNull final ClientAndOrgId clientAndOrgId,
			@Nullable final SumUpPOSRef posRef,
			@Nullable final LastSync lastSync)
	{
		this.configId = configId;
		this.externalId = externalId;
		this.clientTransactionId = clientTransactionId;
		this.merchantCode = merchantCode;
		this.cardReader = cardReader;
		this.timestamp = timestamp;
		this.status = status;
		this.amount = amount;
		this.card = card;
		this.amountRefunded = amountRefunded != null ? amountRefunded : Amount.zero(amount.getCurrencyCode());
		this.json = json;
		this.clientAndOrgId = clientAndOrgId;
		this.posRef = posRef;
		this.lastSync = lastSync;

		Amount.assertSameCurrency(this.amount, this.amountRefunded);
	}

	@JsonIgnore
	public boolean isRefunded()
	{
		return amount.signum() != 0 && amountRefunded.signum() != 0;
	}

	//
	//
	//
	//
	//
	//
	@Value
	@Builder
	@Jacksonized
	@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
	public static class CardReader
	{
		@NonNull SumUpCardReaderExternalId externalId;
		@NonNull String name;
	}

	//
	//
	//
	//
	//
	//

	@Value
	@Builder
	@Jacksonized
	@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
	public static class Card
	{
		@NonNull String type;
		@NonNull String last4Digits;

		@Override
		@Deprecated
		public String toString() {return getAsString();}

		public String getAsString() {return type + "-" + last4Digits;}
	}

	//
	//
	//
	//
	//
	//

	@RequiredArgsConstructor
	@Getter
	public enum LastSyncStatus implements ReferenceListAwareEnum
	{
		OK(X_SUMUP_Transaction.SUMUP_LASTSYNC_STATUS_OK),
		Error(X_SUMUP_Transaction.SUMUP_LASTSYNC_STATUS_Error),
		;
		private static final ValuesIndex<LastSyncStatus> index = ReferenceListAwareEnums.index(values());

		@NonNull private final String code;

		public static LastSyncStatus ofCode(@NonNull String code) {return index.ofCode(code);}

		public static LastSyncStatus ofNullableCode(@Nullable String code) {return index.ofNullableCode(code);}

	}

	//
	//
	//
	//
	//
	//

	@Value
	@Builder
	@Jacksonized
	@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
	public static class LastSync
	{
		@NonNull LastSyncStatus status;
		@Nullable Instant timestamp;
		@Nullable AdIssueId errorId;

		public static LastSync ok()
		{
			return builder()
					.status(LastSyncStatus.OK)
					.timestamp(SystemTime.asInstant())
					.build();
		}

		public static LastSync error(@NonNull final AdIssueId errorId)
		{
			return builder()
					.status(LastSyncStatus.Error)
					.timestamp(SystemTime.asInstant())
					.errorId(errorId)
					.build();
		}
	}
}