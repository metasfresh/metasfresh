package de.metas.pos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.payment.TenderType;
import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.annotation.Nullable;
import java.util.Objects;

@RequiredArgsConstructor
public enum POSPaymentMethod implements ReferenceListAwareEnum
{
	CASH("CASH", TenderType.Cash),
	CARD("CARD", TenderType.CreditCard);
	private static final ReferenceListAwareEnums.ValuesIndex<POSPaymentMethod> index = ReferenceListAwareEnums.index(values());

	@NonNull private final String code;
	@NonNull @Getter private final TenderType tenderType;

	@JsonCreator
	public static POSPaymentMethod ofCode(@NonNull String code) {return index.ofCode(code);}

	@JsonValue
	@NonNull
	public String getCode() {return code;}

	public static boolean equals(@Nullable final POSPaymentMethod value1, @Nullable final POSPaymentMethod value2) {return Objects.equals(value1, value2);}
}
