package de.metas.einvoice;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
@Builder
public class EInvoiceRecipientConfig
{
	@NonNull EInvoiceFormat format;
	@Nullable String buyerReference;
}
