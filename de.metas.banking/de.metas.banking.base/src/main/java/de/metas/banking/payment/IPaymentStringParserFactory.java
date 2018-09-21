package de.metas.banking.payment;

import de.metas.banking.payment.spi.IPaymentStringParser;
import de.metas.util.ISingletonService;

public interface IPaymentStringParserFactory extends ISingletonService
{
	void registerParser(String type, IPaymentStringParser parser);

	IPaymentStringParser getParser(String type);
}
