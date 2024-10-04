package de.metas.pos.payment_gateway;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.logging.LogManager;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class POSPaymentProcessorService
{
	private static final Logger logger = LogManager.getLogger(POSPaymentProcessorService.class);

	private final ImmutableMap<POSPaymentProcessorType, POSPaymentProcessor> paymentProcessors;

	public POSPaymentProcessorService(final Optional<List<POSPaymentProcessor>> paymentProcessors)
	{
		this.paymentProcessors = Maps.uniqueIndex(
				paymentProcessors.orElseGet(ImmutableList::of),
				POSPaymentProcessor::getType
		);
		logger.info("POS Payment Processors: {}", this.paymentProcessors);
	}

	public POSPaymentProcessResponse processPayment(@NonNull final POSPaymentProcessRequest request)
	{
		final POSPaymentProcessor paymentProcessor = getPaymentProcessor(request.getPaymentProcessorConfig().getType());
		return paymentProcessor.process(request);
	}

	private POSPaymentProcessor getPaymentProcessor(@NonNull final POSPaymentProcessorType type)
	{
		final POSPaymentProcessor paymentProcessor = paymentProcessors.get(type);
		if (paymentProcessor == null)
		{
			throw new AdempiereException("No payment processor registered for type " + type);
		}
		return paymentProcessor;
	}
}
