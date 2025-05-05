package de.metas.payment.paymentinstructions;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
@Builder
public class PaymentInstructions
{
	@NonNull PaymentInstructionsId id;
	@Nullable String name;
	boolean active;
}
