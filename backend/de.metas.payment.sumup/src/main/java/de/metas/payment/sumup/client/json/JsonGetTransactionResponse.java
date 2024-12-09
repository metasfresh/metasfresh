package de.metas.payment.sumup.client.json;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.currency.CurrencyCode;
import de.metas.payment.sumup.SumUpClientTransactionId;
import de.metas.payment.sumup.SumUpMerchantCode;
import de.metas.payment.sumup.SumUpTransactionExternalId;
import de.metas.util.StringUtils;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Value
@Builder(toBuilder = true)
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
public class JsonGetTransactionResponse
{
	@NonNull SumUpTransactionExternalId id;
	@NonNull SumUpClientTransactionId client_transaction_id;
	@JsonProperty("merchant_code")
	@NonNull SumUpMerchantCode merchant_code;
	@NonNull String timestamp;

	@NonNull String status;
	@NonNull BigDecimal amount;
	@NonNull CurrencyCode currency;

	@Nullable Card card;
	@Nullable List<Event> events;

	@JsonIgnore
	@Nullable String json;

	@JsonIgnore
	public JsonGetTransactionResponse withJson(final String json)
	{
		return Objects.equals(this.json, json) ? this : toBuilder().json(json).build();
	}

	@JsonIgnore
	public BigDecimal getAmountRefunded()
	{
		if (events == null || events.isEmpty())
		{
			return BigDecimal.ZERO;
		}

		return events.stream()
				.filter(Event::isRefunded)
				.map(Event::getAmountPlusFee)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	//
	//
	//
	//
	//

	@Value
	@Builder
	@Jacksonized
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Card
	{
		String type;

		@JsonProperty("last_4_digits")
		String last_4_digits;
	}

	//
	//
	//
	//
	//

	@EqualsAndHashCode
	@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
	public static class EventType
	{
		public static final EventType PAYOUT = new EventType("PAYOUT");
		public static final EventType REFUND = new EventType("REFUND");

		@NonNull private static final ConcurrentHashMap<String, EventType> intern = new ConcurrentHashMap<>();

		static
		{
			Arrays.asList(PAYOUT, REFUND).forEach(status -> intern.put(status.getCode(), status));
		}

		@NonNull private final String code;

		private EventType(@NonNull final String code)
		{
			final String codeNorm = StringUtils.trimBlankToNull(code);
			if (codeNorm == null)
			{
				throw new AdempiereException("Invalid status: " + code);
			}
			this.code = codeNorm;
		}

		@JsonCreator
		@NonNull
		public static EventType ofString(@NonNull final String code)
		{
			final String codeNorm = StringUtils.trimBlankToNull(code);
			if (codeNorm == null)
			{
				throw new AdempiereException("Invalid status: " + code);
			}

			return intern.computeIfAbsent(codeNorm, EventType::new);
		}

		@Override
		@Deprecated
		public String toString() {return getCode();}

		@JsonValue
		@NonNull
		public String getCode() {return code;}

		public static boolean equals(@Nullable final EventType status1, @Nullable final EventType status2) {return Objects.equals(status1, status2);}
	}

	//
	//
	//
	//
	//

	@EqualsAndHashCode
	@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
	public static class EventStatus
	{
		//SCHEDULED, REFUNDED etc
		public static final EventStatus SCHEDULED = new EventStatus("SCHEDULED");
		public static final EventStatus REFUNDED = new EventStatus("REFUNDED");

		@NonNull private static final ConcurrentHashMap<String, EventStatus> intern = new ConcurrentHashMap<>();

		static
		{
			Arrays.asList(SCHEDULED, REFUNDED).forEach(status -> intern.put(status.getCode(), status));
		}

		@NonNull private final String code;

		private EventStatus(@NonNull final String code)
		{
			final String codeNorm = StringUtils.trimBlankToNull(code);
			if (codeNorm == null)
			{
				throw new AdempiereException("Invalid status: " + code);
			}
			this.code = codeNorm;
		}

		@JsonCreator
		@NonNull
		public static EventStatus ofString(@NonNull final String code)
		{
			final String codeNorm = StringUtils.trimBlankToNull(code);
			if (codeNorm == null)
			{
				throw new AdempiereException("Invalid status: " + code);
			}

			return intern.computeIfAbsent(codeNorm, EventStatus::new);
		}

		@Override
		@Deprecated
		public String toString() {return getCode();}

		@JsonValue
		@NonNull
		public String getCode() {return code;}

		public static boolean equals(@Nullable final EventStatus status1, @Nullable final EventStatus status2) {return Objects.equals(status1, status2);}
	}

	//
	//
	//
	//
	//

	@Value
	@Builder
	@Jacksonized
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Event
	{
		String id;
		EventType type;
		EventStatus status;

		/**
		 * e.g. 2024-10-05T12:48:34.312Z
		 */
		String timestamp;

		BigDecimal amount;
		@JsonProperty("fee_amount") BigDecimal fee_amount;

		// other fields:
		// "deducted_amount": 0.0,
		// "deducted_fee_amount": 0.0,
		// "transaction_id": "1a434bf8-bd5c-44a6-8576-37bc9aedc912",
		// "installment_number": 1,
		// "payout_reference": "SUMUP PID",

		@JsonIgnore
		public boolean isRefunded()
		{
			return EventType.equals(type, EventType.REFUND)
					&& EventStatus.equals(status, EventStatus.REFUNDED);
		}

		@JsonIgnore
		public BigDecimal getAmountPlusFee()
		{
			BigDecimal result = BigDecimal.ZERO;
			if (amount != null)
			{
				result = result.add(amount);
			}
			if (amount != null)
			{
				result = result.add(fee_amount);
			}
			return result;
		}
	}

}
