package de.metas.invoice;

import de.metas.tax.api.TaxId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.math.BigDecimal;

@Value
@Builder
public class InvoiceTax
{
	@NonNull TaxId taxId;
	@NonNull BigDecimal taxAmt;
	@NonNull BigDecimal taxBaseAmt;
}
