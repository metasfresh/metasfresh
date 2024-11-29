package de.metas.tax.api;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.math.BigDecimal;

@Value
@Builder
public class CalculateTaxResult
{
	public static final CalculateTaxResult ZERO = builder().build();

	@NonNull @Builder.Default BigDecimal taxAmount = BigDecimal.ZERO;
	@NonNull @Builder.Default BigDecimal reverseChargeAmt = BigDecimal.ZERO;
}
