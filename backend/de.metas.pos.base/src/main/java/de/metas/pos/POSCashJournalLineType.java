package de.metas.pos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import de.metas.util.lang.ReferenceListAwareEnums.ValuesIndex;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;

@RequiredArgsConstructor
@Getter
public enum POSCashJournalLineType implements ReferenceListAwareEnum
{
	CASH_PAYMENT("CASH_PAY", true),
	CARD_PAYMENT("CARD_PAY", false),
	CASH_IN_OUT("CASH_INOUT", true),
	CASH_CLOSING_DIFFERENCE("CASH_DIFF", true),
	;

	private static final ValuesIndex<POSCashJournalLineType> index = ReferenceListAwareEnums.index(values());

	@NonNull private final String code;
	private final boolean isCash;

	@JsonCreator
	public static POSCashJournalLineType ofCode(@NonNull final String code)
	{
		return index.ofCode(code);
	}

	/**
	 * Explicit override of the Lombok-generated getter, so the wire form (e.g. {@code JsonPOSExpectation}'s
	 * {@code cashJournalLines[].type}) is the DB code ({@code "CASH_INOUT"}), not the Java constant name
	 * ({@code "CASH_IN_OUT"}) — mirrors {@link de.metas.document.engine.DocStatus#getCode()}'s own
	 * {@code @JsonValue}.
	 */
	@Override
	@JsonValue
	public String getCode() {return code;}

	public static POSCashJournalLineType ofPaymentMethod(@NonNull final POSPaymentMethod paymentMethod)
	{
		switch (paymentMethod)
		{
			case CASH:
				return CASH_PAYMENT;
			case CARD:
				return CARD_PAYMENT;
			default:
				throw new AdempiereException("Unknown payment method: " + paymentMethod);
		}
	}

	public boolean isCard() {return this == CARD_PAYMENT;}

	public boolean isPayment() {return this == CASH_PAYMENT || this == CARD_PAYMENT;}
}
