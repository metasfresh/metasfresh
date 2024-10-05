package de.metas.pos;

import de.metas.pos.repository.model.X_C_POS_Payment;
import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;

@Getter
@RequiredArgsConstructor
public enum POSPaymentProcessingStatus implements ReferenceListAwareEnum
{
	SUCCESSFUL(X_C_POS_Payment.POSPAYMENTPROCESSINGSTATUS_SUCCESSFUL),
	CANCELLED(X_C_POS_Payment.POSPAYMENTPROCESSINGSTATUS_CANCELLED),
	FAILED(X_C_POS_Payment.POSPAYMENTPROCESSINGSTATUS_FAILED),
	PENDING(X_C_POS_Payment.POSPAYMENTPROCESSINGSTATUS_PENDING),
	NEW(X_C_POS_Payment.POSPAYMENTPROCESSINGSTATUS_NEW),
	DELETED(X_C_POS_Payment.POSPAYMENTPROCESSINGSTATUS_DELETED),
	;

	@NonNull private final String code;

	private static final ReferenceListAwareEnums.ValuesIndex<POSPaymentProcessingStatus> index = ReferenceListAwareEnums.index(values());

	public static POSPaymentProcessingStatus ofCode(@NonNull String code) {return index.ofCode(code);}

	public boolean isNew() {return this == POSPaymentProcessingStatus.NEW;}

	public boolean isPending() {return this == POSPaymentProcessingStatus.PENDING;}

	public boolean isPendingOrSuccessful() {return isPending() || isSuccessful();}

	public boolean isSuccessful() {return this == POSPaymentProcessingStatus.SUCCESSFUL;}

	public boolean isFailed() {return this == POSPaymentProcessingStatus.FAILED;}

	public boolean isCanceled() {return this == POSPaymentProcessingStatus.CANCELLED;}

	public boolean isDeleted() {return this == POSPaymentProcessingStatus.DELETED;}
	
	public boolean isAllowCheckout()
	{
		return isNew() || isCanceled() || isFailed();
	}

	public void assertAllowCheckout()
	{
		if (!isAllowCheckout())
		{
			throw new AdempiereException("Payments with status " + this + " cannot be checked out");
		}
	}

	public boolean isAllowDeleteFromDB()
	{
		return isNew();
	}

	public void assertAllowDeleteFromDB()
	{
		if (!isAllowDeleteFromDB())
		{
			throw new AdempiereException("Payments with status " + this + " cannot be deleted from DB");
		}
	}

	public boolean isAllowDelete()
	{
		return isAllowDeleteFromDB() || isCanceled() || isFailed();
	}

	public void assertAllowDelete()
	{
		if (!isAllowDelete())
		{
			throw new AdempiereException("Payments with status " + this + " cannot be deleted");
		}
	}
}
