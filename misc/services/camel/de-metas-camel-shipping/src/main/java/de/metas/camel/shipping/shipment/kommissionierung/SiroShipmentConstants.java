/*
 * #%L
 * de-metas-camel-shipping
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.camel.shipping.shipment.kommissionierung;

import com.google.common.collect.ImmutableSet;

import java.util.Set;

public interface SiroShipmentConstants
{
	String SIRO_FTP_PATH = "{{siro.ftp.retrieve.shipments.endpoint}}";
	String CREATE_SHIPMENT_MF_URL = "{{metasfresh.api.baseurl}}/shipment";
	String LOCAL_STORAGE_URL = "{{siro.shipments.local.storage}}";
	String AUTHORIZATION = "Authorization";
	String AUTHORIZATION_TOKEN = "{{metasfresh.api.authtoken}}";
	String SHIPMENT_XML_TO_JSON_PROCESSOR = "shipment-xml-to-json-id";
	String INVENTORY_CORRECTION_XML_TO_JSON_PROCESSOR = "inv-correct-xml-to-json-id";

	String DEFAULT_DELIVERY_RULE_FORCE = "F";

	String DELIVERY_DATE_PATTERN = "dd.MM.yyyy HH:mm:ss";

	String TRACKING_NUMBERS_SEPARATOR = "shipment.tracking.numbers.separator";
	String PACKAGE_WEIGHT_SEPARATOR = "shipment.package.weight.separator";

	String SIRO_LOCALE_PROPERTY = "siro.locale";
	String LOCALE_COUNTRY_LANGUAGE_SEPARATOR = ",";

	Set<String> EXPIRY_DATE_PATTERNS = ImmutableSet.of("yyyy/MM/dd", "yyyy.MM.dd", "dd.MM.yyyy");

	String EMPTY_FIELD = "";
	String SHIPPER_INTERNAL_NAME_SEPARATOR_PROP = "shipper.InternalName.parts.separator";
}
