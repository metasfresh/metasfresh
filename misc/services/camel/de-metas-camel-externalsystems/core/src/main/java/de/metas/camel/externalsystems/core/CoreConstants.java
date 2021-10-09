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

import static de.metas.common.externalsystem.ExternalSystemConstants.QUEUE_NAME_MF_TO_ES;

public interface CoreConstants
{
	String AUTHORIZATION = "Authorization";

	String AUTHORIZATION_TOKEN = "{{metasfresh.api.authtoken}}";

	String FROM_MF_ROUTE = "rabbitmq:" + QUEUE_NAME_MF_TO_ES
			+ "?durable=true"
			+ "&autoDelete=false"
			+ "&routingKey=" + QUEUE_NAME_MF_TO_ES
			+ "&queue=" + QUEUE_NAME_MF_TO_ES;
}
