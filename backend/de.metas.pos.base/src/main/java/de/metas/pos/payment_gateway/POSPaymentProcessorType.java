package de.metas.pos.payment_gateway;

import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.compiere.model.X_C_POS;

import javax.annotation.Nullable;

@Getter
@RequiredArgsConstructor
public enum POSPaymentProcessorType implements ReferenceListAwareEnum
{
	SumUp(X_C_POS.POSPAYMENTPROCESSOR_SumUp),
	;

	private static final ReferenceListAwareEnums.ValuesIndex<POSPaymentProcessorType> index = ReferenceListAwareEnums.index(values());

	@NonNull private final String code;

	@NonNull
	public static POSPaymentProcessorType ofCode(@NonNull final String code) {return index.ofCode(code);}

	@Nullable
	public static POSPaymentProcessorType ofNullableCode(@Nullable final String code) {return index.ofNullableCode(code);}
}
