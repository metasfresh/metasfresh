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

package de.metas.camel.externalsystems.rabbitmq.common;

public interface CamelConstants
{
	String ROUTE_PROPERTY_EXPORT_BPARTNER_CONTEXT = "ExportBPartnerRouteContext";
	String ROUTE_PROPERTY_EXPORT_EXTERNAL_REFERENCE_CONTEXT = "ExportExternalReferenceRouteContext";

	String EXPORT_INFO_RETRY_COUNT = "export.bpartner.retry.count";

	String EXPORT_INFO_RETRY_DELAY = "export.bpartner.retry.delay.ms";
}
