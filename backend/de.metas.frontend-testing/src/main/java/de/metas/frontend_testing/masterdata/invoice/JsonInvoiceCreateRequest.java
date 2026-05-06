package de.metas.frontend_testing.masterdata.invoice;

import de.metas.frontend_testing.masterdata.Identifier;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;

@Value
@Builder
@Jacksonized
public class JsonInvoiceCreateRequest
{
	@Nullable Identifier salesOrder;
	@Nullable Identifier purchaseOrder;
}
