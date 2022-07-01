/*
 * #%L
 * de-metas-camel-externalsystems-core
 * %%
 * Copyright (C) 2021 metas GmbH
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

import static de.metas.common.externalsystem.ExternalSystemConstants.QUEUE_NAME_ES_TO_MF_CUSTOM;
import static de.metas.common.externalsystem.ExternalSystemConstants.QUEUE_NAME_MF_TO_ES;
import static de.metas.common.externalsystem.ExternalSystemConstants.QUEUE_NAME_MF_TO_ES_CUSTOM;

public interface CoreConstants
{
	String AUTHORIZATION = "Authorization";

	String AUTHORIZATION_TOKEN = "{{metasfresh.api.authtoken}}";

	String AUDIT_SENSITIVE_DATA_PATTERN_PROPERTY = "metasfresh.audit.sensitive-data.pattern";
	String AUDIT_SENSITIVE_DATA_PATTERN_GROUP_PROPERTY = "metasfresh.audit.sensitive-data.pattern.group";

	String AUDIT_SENSITIVE_DATA_PATTERN_DEFAULT = "\".*?(auth|key|pass|token).*?\":(.*?\"(.+?)\")";
	String AUDIT_SENSITIVE_DATA_PATTERN_DEFAULT_GROUP = "3";

	String CONCURRENT_CONSUMERS_PROPERTY = "{{dispatcher.rabbitmq.consumer.concurrentConsumers}}";
	String THREAD_POOL_SIZE_PROPERTY = "{{dispatcher.rabbitmq.consumer.threadPoolSize}}";

	String FROM_MF_ROUTE = "rabbitmq:" + QUEUE_NAME_MF_TO_ES
			+ "?durable=true"
			+ "&autoDelete=false"
			+ "&autoAck=false"
			+ "&threadPoolSize=" + THREAD_POOL_SIZE_PROPERTY
			+ "&concurrentConsumers=" + CONCURRENT_CONSUMERS_PROPERTY
			+ "&routingKey=" + QUEUE_NAME_MF_TO_ES
			+ "&queue=" + QUEUE_NAME_MF_TO_ES;

	String CUSTOM_TO_MF_ROUTE = "rabbitmq:" + QUEUE_NAME_ES_TO_MF_CUSTOM
			+ "?durable=true"
			+ "&autoDelete=false"
			+ "&autoAck=false"
			+ "&routingKey=" + QUEUE_NAME_ES_TO_MF_CUSTOM
			+ "&queue=" + QUEUE_NAME_ES_TO_MF_CUSTOM;

	String CUSTOM_FROM_MF_ROUTE = "rabbitmq:" + QUEUE_NAME_MF_TO_ES_CUSTOM
			+ "?durable=true"
			+ "&autoDelete=false"
			+ "&autoAck=false"
			+ "&routingKey=" + QUEUE_NAME_MF_TO_ES_CUSTOM
			+ "&queue=" + QUEUE_NAME_MF_TO_ES_CUSTOM;
}
