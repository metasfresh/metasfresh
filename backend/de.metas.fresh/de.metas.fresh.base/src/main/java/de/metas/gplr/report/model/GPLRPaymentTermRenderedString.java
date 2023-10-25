package de.metas.gplr.report.model;

import de.metas.payment.paymentinstructions.PaymentInstructions;
import de.metas.payment.paymentterm.PaymentTerm;
import de.metas.util.StringUtils;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

import javax.annotation.Nullable;

@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GPLRPaymentTermRenderedString
{
	@NonNull String renderedString;

	public static GPLRPaymentTermRenderedString of(@NonNull final PaymentTerm paymentTerm, @Nullable PaymentInstructions paymentInstructions)
	{
		return new GPLRPaymentTermRenderedString(toRenderedString(
				paymentTerm.getValue(),
				paymentTerm.getDescription(),
				paymentInstructions != null ? paymentInstructions.getName() : null
		));
	}

	public static GPLRPaymentTermRenderedString ofRenderedString(@NonNull final String renderedString)
	{
		return new GPLRPaymentTermRenderedString(renderedString);
	}

	private static String toRenderedString(
			@NonNull String code,
			@Nullable String description,
			@Nullable String paymentInstructions)
	{
		final StringBuilder result = new StringBuilder();
		result.append(code);

		final String descriptionNorm = StringUtils.trimBlankToNull(description);
		if (descriptionNorm != null)
		{
			result.append(" ").append(description);
		}

		final String paymentInstructionsNorm = StringUtils.trimBlankToNull(paymentInstructions);
		if (paymentInstructionsNorm != null)
		{
			result.append(" ").append(paymentInstructionsNorm);
		}

		return result.toString();
	}

	@Override
	public String toString()
	{
		return toRenderedString();
	}

	public String toRenderedString() {return renderedString;}
}
