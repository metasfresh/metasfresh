package de.metas.pos.rest_api.json;

import com.google.common.collect.ImmutableList;
import de.metas.pos.POSCashJournal;
import de.metas.pos.POSCashJournalLine;
import de.metas.pos.POSPaymentMethod;
import de.metas.util.StringUtils;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Value
@Builder
@Jacksonized
public class JsonCashJournalSummary
{
	boolean closed;
	@Nullable String openingNote;
	@Nullable String closingNote;
	@NonNull String currencySymbol;
	int currencyPrecision;

	@NonNull List<JsonPaymentMethodSummary> paymentMethods;

	public static JsonCashJournalSummary of(@NonNull final POSCashJournal cashJournal, @NonNull JsonContext jsonContext)
	{

		final ArrayList<JsonPaymentMethodSummary> paymentMethods = new ArrayList<>();

		//
		// Cash
		{
			final ArrayList<JsonPaymentDetail> details = new ArrayList<>();
			details.add(JsonPaymentDetail.builder()
					.type(JsonPaymentDetailType.OPENING_BALANCE)
					.amount(cashJournal.getCashBeginningBalance().toBigDecimal())
					.build());

			BigDecimal cashPaymentAmount = BigDecimal.ZERO;
			for (final POSCashJournalLine line : cashJournal.getLines())
			{
				switch (line.getType())
				{
					case CASH_PAYMENT:
						cashPaymentAmount = cashPaymentAmount.add(line.getAmount().toBigDecimal());
						break;
					case CASH_IN_OUT:
						final boolean isCashIn = line.getAmount().signum() >= 0;
						details.add(JsonPaymentDetail.builder()
								.type(isCashIn ? JsonPaymentDetailType.CASH_IN : JsonPaymentDetailType.CASH_OUT)
								.description(StringUtils.trimBlankToNull(line.getDescription()))
								.amount(line.getAmount().toBigDecimal())
								.build());
						break;
					case CASH_CLOSING_DIFFERENCE:
						details.add(JsonPaymentDetail.builder()
								.type(JsonPaymentDetailType.CLOSING_DIFFERENCE)
								.description(StringUtils.trimBlankToNull(line.getDescription()))
								.amount(line.getAmount().toBigDecimal())
								.build());
						break;
				}
			}

			details.add(JsonPaymentDetail.builder()
					.type(JsonPaymentDetailType.CASH_PAYMENTS)
					.amount(cashPaymentAmount)
					.build());

			paymentMethods.add(JsonPaymentMethodSummary.builder()
					.paymentMethod(POSPaymentMethod.CASH)
					.amount(details.stream().map(JsonPaymentDetail::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add))
					.details(details)
					.build());
		}

		//
		// Card
		{
			paymentMethods.add(JsonPaymentMethodSummary.builder()
					.paymentMethod(POSPaymentMethod.CARD)
					.amount(cashJournal.getLines()
							.stream()
							.filter(line -> line.getType().isCard())
							.map(line -> line.getAmount().toBigDecimal())
							.reduce(BigDecimal.ZERO, BigDecimal::add))
					.details(ImmutableList.of())
					.build());
		}

		//
		return JsonCashJournalSummary.builder()
				.closed(cashJournal.isClosed())
				.openingNote(cashJournal.getOpeningNote())
				.closingNote(cashJournal.getClosingNote())
				.currencySymbol(jsonContext.getCurrencySymbol(cashJournal.getCurrencyId()))
				.currencyPrecision(jsonContext.getCurrencyPrecision(cashJournal.getCurrencyId()).toInt())
				.paymentMethods(paymentMethods)
				.build();
	}

	//
	//
	//
	//
	//

	/**
	 * @implSpec keep in sync with misc/services/mobile-webui/mobile-webui-frontend/src/apps/pos/containers/cash_journal/paymentDetailType.js
	 */
	public enum JsonPaymentDetailType
	{
		OPENING_BALANCE,
		CLOSING_DIFFERENCE,
		CASH_PAYMENTS,
		CASH_IN,
		CASH_OUT,
	}

	@Value
	@Builder
	@Jacksonized
	public static class JsonPaymentDetail
	{
		@NonNull JsonPaymentDetailType type;
		@Nullable String description;
		@NonNull BigDecimal amount;
	}

	@Value
	@Builder
	@Jacksonized
	public static class JsonPaymentMethodSummary
	{
		@NonNull POSPaymentMethod paymentMethod;
		@NonNull BigDecimal amount;
		@NonNull List<JsonPaymentDetail> details;
	}
}
