package de.metas.gplr.model;

import de.metas.payment.paymentterm.PaymentTerm;
import de.metas.util.Check;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

import java.util.Objects;

@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GPLRPaymentTermRenderedString
{
	@NonNull String renderedString;

	public static GPLRPaymentTermRenderedString of(@NonNull final PaymentTerm paymentTerm)
	{
		return new GPLRPaymentTermRenderedString(toRenderedString(
				paymentTerm.getValue(),
				paymentTerm.getName(),
				paymentTerm.getDescription()
		));
	}

	public static GPLRPaymentTermRenderedString ofRenderedString(@NonNull final String renderedString)
	{
		return new GPLRPaymentTermRenderedString(renderedString);
	}

	private static String toRenderedString(String code, String name, String description)
	{
		final StringBuilder result = new StringBuilder();
		result.append(code);

		if (!Objects.equals(name, code))
		{
			result.append(" ").append(name.trim());
		}

		if (description != null && !Check.isBlank(description))
		{
			result.append(" ").append(description);
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
