/*
 * #%L
 * de-metas-camel-rabbitmq
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

package de.metas.camel.externalsystems.rabbitmq.api;

public interface ApiConstants
{
	String RABBIT_MQ_PAYLOAD_ENCODING = "string";

	int RABBITMQ_PROPS_PERSISTENT_DELIVERY_MODE = 2;
	String RABBITMQ_HEADERS_SUBJECT = "subject";
	String RABBITMQ_HEADERS_METASFRESH_BUSINESS_PARTNER_SYNC = "metasfresh_business_partner_sync";
	String RABBITMQ_HEADERS_METASFRESH_EXTERNAL_REFERENCE_SYNC = "metasfresh_external_reference_sync";
}