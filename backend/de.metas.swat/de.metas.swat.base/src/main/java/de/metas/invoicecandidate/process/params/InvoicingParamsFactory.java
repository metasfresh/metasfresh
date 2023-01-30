package de.metas.invoicecandidate.process.params;

import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.adempiere.util.api.IParams;

@UtilityClass
public class InvoicingParamsFactory
{
	public static IInvoicingParams wrap(@NonNull final IParams params)
	{
		return new InvoicingParams(params);
	}

	public static PlainInvoicingParams newPlain()
	{
		return new PlainInvoicingParams();
	}

	public static PlainInvoicingParams overriding(@NonNull final IInvoicingParams defaults)
	{
		return new PlainInvoicingParams(defaults);
	}
}
