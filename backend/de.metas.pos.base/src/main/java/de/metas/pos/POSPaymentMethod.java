package de.metas.pos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.payment.PaymentRule;
import de.metas.payment.TenderType;
import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.Objects;

@RequiredArgsConstructor
public enum POSPaymentMethod implements ReferenceListAwareEnum
{
	CASH("CASH", TenderType.Cash, PaymentRule.Cash),

	// NOTE: in case of CARD we use OnCredit payment rule instead of CreditCard because
	// CreditCard payment rule might be inactive and activating it might have other implications
	CARD("CARD", TenderType.CreditCard, PaymentRule.OnCredit),
	;

	private static final ReferenceListAwareEnums.ValuesIndex<POSPaymentMethod> index = ReferenceListAwareEnums.index(values());

	@NonNull private final String code;
	@NonNull @Getter private final TenderType tenderType;
	@NonNull @Getter private final PaymentRule paymentRule;

	@JsonCreator
	public static POSPaymentMethod ofCode(@NonNull String code) {return index.ofCode(code);}

	@JsonValue
	@NonNull
	public String getCode() {return code;}

	public static boolean equals(@Nullable final POSPaymentMethod value1, @Nullable final POSPaymentMethod value2) {return Objects.equals(value1, value2);}

	public boolean isCash() {return this == CASH;}

	public boolean isCard() {return this == CARD;}

	public void assertCash()
	{
		if (!isCash())
		{
			throw new AdempiereException("Expected CASH payment method but it was " + this);
		}

	}

	public void assertCard()
	{
		if (!isCard())
		{
			throw new AdempiereException("Expected CARD payment method but it was " + this);
		}
	}

}
