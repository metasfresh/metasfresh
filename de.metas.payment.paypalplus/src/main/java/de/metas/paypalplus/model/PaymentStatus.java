package de.metas.paypalplus.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;

@Value
@Getter
public class PaymentStatus
{
	@NonNull
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	String paymentState;
}
