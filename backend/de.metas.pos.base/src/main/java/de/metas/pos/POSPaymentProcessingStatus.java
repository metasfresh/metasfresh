package de.metas.pos;

import de.metas.pos.repository.model.X_C_POS_Payment;
import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum POSPaymentProcessingStatus implements ReferenceListAwareEnum
{
	SUCCESSFUL(X_C_POS_Payment.POSPAYMENTPROCESSINGSTATUS_SUCCESSFUL),
	CANCELLED(X_C_POS_Payment.POSPAYMENTPROCESSINGSTATUS_CANCELLED),
	FAILED(X_C_POS_Payment.POSPAYMENTPROCESSINGSTATUS_FAILED),
	PENDING(X_C_POS_Payment.POSPAYMENTPROCESSINGSTATUS_PENDING),
	NEW(X_C_POS_Payment.POSPAYMENTPROCESSINGSTATUS_NEW),
	;

	@NonNull private final String code;

	private static final ReferenceListAwareEnums.ValuesIndex<POSPaymentProcessingStatus> index = ReferenceListAwareEnums.index(values());

	public static POSPaymentProcessingStatus ofCode(@NonNull String code) {return index.ofCode(code);}

	public boolean isNew() {return this == POSPaymentProcessingStatus.NEW;}

	public boolean isSuccessful() {return this == POSPaymentProcessingStatus.SUCCESSFUL;}

	public boolean isNewOrCanTryAgain()
	{
		return isNew()
				|| this == CANCELLED
				|| this == FAILED;
	}
}
