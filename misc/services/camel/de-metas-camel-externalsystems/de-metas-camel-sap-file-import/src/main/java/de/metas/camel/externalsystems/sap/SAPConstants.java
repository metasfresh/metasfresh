/*
 * #%L
 * de-metas-camel-sap
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.camel.externalsystems.sap;

public interface SAPConstants
{
	String SAP_SYSTEM_NAME = "SAP";

	String SEEN_FILE_RENAME_PATTERN_PROPERTY_NAME = "sap.sftp.processing.rename-pattern";
	String DEFAULT_RENAME_PATTERN = "${date:now:yyyy-MM-dd_HH-mm-ss}_${file:name}";

	String ROUTE_PROPERTY_CREDIT_LIMIT_ROUTE_CONTEXT = "CreditLimitRouteContext";

	String BPARTNER_DEFAULT_LANGUAGE = "en_US";

	String DEFAULT_PRODUCT_CATEGORY_ID = "sap.material-import.default-product-category";

	String ROUTE_PROPERTY_CONVERSION_RATE_ROUTE_CONTEXT = "ConversionRateRouteContext";
	String DEFAULT_DATE_FORMAT = "yyyyMMdd";
}
