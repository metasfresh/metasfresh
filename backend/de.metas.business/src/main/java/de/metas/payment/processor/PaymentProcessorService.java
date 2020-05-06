package de.metas.payment.processor;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import de.metas.logging.LogManager;
import de.metas.payment.PaymentRule;
import lombok.NonNull;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2019 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@Service
public class PaymentProcessorService
{
	private static final Logger logger = LogManager.getLogger(PaymentProcessorService.class);

	private final ImmutableMap<PaymentRule, PaymentProcessor> processorsByPaymentRule;

	public PaymentProcessorService(
			@NonNull final Optional<List<PaymentProcessor>> processors)
	{
		processorsByPaymentRule = processors.isPresent()
				? Maps.uniqueIndex(processors.get(), PaymentProcessor::getPaymentRule)
				: ImmutableMap.of();

		logger.info("Registered processors: {}", processorsByPaymentRule);
	}

	public Optional<PaymentProcessor> getByPaymentRule(@NonNull final PaymentRule paymentRule)
	{
		final PaymentProcessor processor = processorsByPaymentRule.get(paymentRule);
		return Optional.ofNullable(processor);
	}
}
