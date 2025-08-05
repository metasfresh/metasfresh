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

import static de.metas.common.externalsystem.ExternalSystemConstants.QUEUE_NAME_ES_TO_MF_CUSTOM;
import static de.metas.common.externalsystem.ExternalSystemConstants.QUEUE_NAME_MF_TO_ES;
import static de.metas.common.externalsystem.ExternalSystemConstants.QUEUE_NAME_MF_TO_ES_CUSTOM;

public interface CoreConstants
{
	/**
	 * This is the header-key for our API-token when doing http.
	 */
	String AUTHORIZATION = "Authorization";
	String ACCEPT = "accept";

	String AUDIT_SENSITIVE_DATA_PATTERN_PROPERTY = "metasfresh.audit.sensitive-data.pattern";
	String AUDIT_SENSITIVE_DATA_PATTERN_GROUP_PROPERTY = "metasfresh.audit.sensitive-data.pattern.group";

	String AUDIT_SENSITIVE_DATA_PATTERN_DEFAULT = "\".*?(auth|key|pass|token).*?\":(.*?\"(.+?)\")";
	String AUDIT_SENSITIVE_DATA_PATTERN_DEFAULT_GROUP = "3";

	/**
	 * The queue is bound to the default exchange, hence the "default"
	 * (see <a href="https://camel.apache.org/components/4.10.x/spring-rabbitmq-component.html#_default_exchange_name">camel-docu</a>)
	 */
	String FROM_MF_ROUTE = "spring-rabbitmq:default?queues=" + QUEUE_NAME_MF_TO_ES
			+ "&routingKey=" + QUEUE_NAME_MF_TO_ES
			+ "&arg.queue.durable=true";

	String CUSTOM_TO_MF_ROUTE = "spring-rabbitmq:default?queues=" + QUEUE_NAME_ES_TO_MF_CUSTOM
			+ "&routingKey=" + QUEUE_NAME_ES_TO_MF_CUSTOM
			+ "&arg.queue.durable=true";

	String CUSTOM_FROM_MF_ROUTE = "spring-rabbitmq:default?queues=" + QUEUE_NAME_MF_TO_ES_CUSTOM
			+ "&routingKey=" + QUEUE_NAME_MF_TO_ES_CUSTOM
			+ "&arg.queue.durable=true";
}
