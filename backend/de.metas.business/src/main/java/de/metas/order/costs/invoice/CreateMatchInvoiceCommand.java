package de.metas.order.costs.invoice;

import lombok.Builder;
import lombok.NonNull;

public class CreateMatchInvoiceCommand
{
	@Builder
	private CreateMatchInvoiceCommand(
			@NonNull final CreateMatchInvoiceRequest request)
	{

	}

	public void execute()
	{
		throw new UnsupportedOperationException(); // TODO implement
	}

}
