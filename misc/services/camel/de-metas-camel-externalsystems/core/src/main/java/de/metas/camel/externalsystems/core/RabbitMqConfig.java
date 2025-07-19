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

package de.metas.camel.externalsystems.core;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static de.metas.common.externalsystem.ExternalSystemConstants.QUEUE_NAME_ES_TO_MF_CUSTOM;
import static de.metas.common.externalsystem.ExternalSystemConstants.QUEUE_NAME_MF_TO_ES;
import static de.metas.common.externalsystem.ExternalSystemConstants.QUEUE_NAME_MF_TO_ES_CUSTOM;

@Configuration
public class RabbitMqConfig
{
	@Bean
	public Queue metasfreshToExternalSystemQueue()
	{
		return new Queue(QUEUE_NAME_MF_TO_ES, true, false, false);
	}

	@Bean
	public Queue customMetasfreshToExternalSystemQueue()
	{
		return new Queue(QUEUE_NAME_ES_TO_MF_CUSTOM, true, false, false);
	}

	@Bean
	public Queue customMExternalSystemQueueToetasfreshQueue()
	{
		return new Queue(QUEUE_NAME_MF_TO_ES_CUSTOM, true, false, false);
	}
}
