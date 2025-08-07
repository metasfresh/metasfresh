/*
 * #%L
 * de-metas-camel-externalsystems-core
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.camel.externalsystems.core.authorization;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static de.metas.common.externalsystem.ExternalSystemConstants.QUEUE_NAME_ES_TO_MF_CUSTOM;

@EnableRabbit
@Configuration
public class RabbitMQConfig
{
	@Bean
	public Queue customQueue()
	{
		return QueueBuilder.durable(QUEUE_NAME_ES_TO_MF_CUSTOM).build();
	}

	/**
	 * Makes sure the queue, which the metasfresh {@code app} server listens to 
	 * exists and is bound to the default exchange.
	 * If it didn't exist, any messages would be silently discarded. 
	 */
	@Bean
	public Binding customQueueBinding()
	{
		final DirectExchange exchange = ExchangeBuilder.directExchange("").build();
		
		return BindingBuilder
				.bind(customQueue())
				.to(exchange)
				.with(QUEUE_NAME_ES_TO_MF_CUSTOM);
	}

}
