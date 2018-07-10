package de.metas.acct.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import de.metas.Profiles;

/*
 * #%L
 * de.metas.acct.base
 * %%
 * Copyright (C) 2018 metas GmbH
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

@Configuration
@EnableRabbit // needed for @RabbitListener to be considered
public class AMQPConfiguration
{
	public static final String EXCHANGE_NAME = "acct-post-requests-exchange";
	public static final String QUEUE_NAME = "acct-post-requests";
	public static final String ROUTING_KEY = QUEUE_NAME;

	@Bean(EXCHANGE_NAME)
	public DirectExchange exchange()
	{
		return new DirectExchange(EXCHANGE_NAME);
	}

	@Bean(QUEUE_NAME)
	@Profile(Profiles.PROFILE_App)
	public Queue queue()
	{
		final boolean durable = true;
		return new Queue(QUEUE_NAME, durable);
	}

	@Bean
	@Profile(Profiles.PROFILE_App)
	public Binding binding()
	{
		return BindingBuilder.bind(queue())
				.to(exchange())
				.with(ROUTING_KEY);
	}
}
