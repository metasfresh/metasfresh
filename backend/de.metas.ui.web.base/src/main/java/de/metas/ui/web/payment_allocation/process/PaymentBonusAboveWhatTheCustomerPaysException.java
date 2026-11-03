package de.metas.ui.web.payment_allocation.process;

import de.metas.currency.Amount;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.ITranslatableString;
import de.metas.ui.web.payment_allocation.PaymentBonusRowValues;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.UserMessagePresentation;

/**
 * The payment bonus of an invoice is above what the customer pays for it (the open amount minus the discount and the fees).
 * The full message is shown as a dialog; the {@link #getShortReason() short reason} is for places with little room, like the actions list.
 */
public class PaymentBonusAboveWhatTheCustomerPaysException extends AdempiereException
{
	@Getter
	private final ITranslatableString shortReason;

	PaymentBonusAboveWhatTheCustomerPaysException(
			@NonNull final AdMessageKey message,
			@NonNull final Amount paymentBonus,
			@NonNull final Amount maxPaymentBonus,
			@NonNull final String invoiceDocumentNo)
	{
		super(message, paymentBonus, maxPaymentBonus, invoiceDocumentNo);
		this.shortReason = PaymentBonusRowValues.aboveWhatTheCustomerPays(paymentBonus, maxPaymentBonus);
		markAsUserValidationError();
		setUserMessagePresentation(UserMessagePresentation.ACKNOWLEDGE_DIALOG); // a message to the user, not a "Server error" toast
	}
}
