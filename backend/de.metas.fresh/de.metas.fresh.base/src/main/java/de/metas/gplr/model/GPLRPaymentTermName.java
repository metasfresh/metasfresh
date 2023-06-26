package de.metas.gplr.model;

import de.metas.payment.paymentterm.PaymentTerm;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Objects;

@Value
@Builder
public class GPLRPaymentTermName
{
	@NonNull String code;
	@NonNull String name;
	@Nullable String description;

	public static GPLRPaymentTermName of(@NonNull final PaymentTerm paymentTerm)
	{
		return builder()
				.code(paymentTerm.getValue())
				.name(paymentTerm.getName())
				.description(paymentTerm.getDescription())
				.build();
	}

	@Override
	public String toString()
	{
		return toRenderedString();
	}

	public String toRenderedString()
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
}
