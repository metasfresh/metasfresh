/*
 * #%L
 * de-metas-camel-alberta-camelroutes
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

package de.metas.camel.alberta.common;

import lombok.NonNull;

import static de.metas.camel.alberta.common.CommonAlbertaConstants.ALBERTA_EXTERNAL_REFERENCE_SYSTEM;
import static de.metas.camel.alberta.common.CommonAlbertaConstants.EXTERNAL_IDENTIFIER_PREFIX;
import static de.metas.camel.alberta.ordercandidate.GetOrdersRouteConstants.DELIVERY_ADDRESS_PREFIX;
import static de.metas.camel.alberta.patient.GetPatientsRouteConstants.BILLING_ADDR_PREFIX;
import static de.metas.camel.alberta.patient.GetPatientsRouteConstants.SHIPPING_ADDR_PREFIX;

public class ExternalIdentifierFormat
{
	@NonNull
	public static String formatExternalId(@NonNull final String externalId)
	{
		return EXTERNAL_IDENTIFIER_PREFIX + "-" + ALBERTA_EXTERNAL_REFERENCE_SYSTEM + "-" + externalId;
	}

	@NonNull
	public static String formatMainShippingAddressExternalId(@NonNull final String externalId)
	{
		return EXTERNAL_IDENTIFIER_PREFIX + "-" + ALBERTA_EXTERNAL_REFERENCE_SYSTEM + "-" + SHIPPING_ADDR_PREFIX + externalId;
	}

	@NonNull
	public static String formatBillingAddressExternalId(@NonNull final String externalId)
	{
		return EXTERNAL_IDENTIFIER_PREFIX + "-" + ALBERTA_EXTERNAL_REFERENCE_SYSTEM + "-" + BILLING_ADDR_PREFIX + externalId;
	}

	@NonNull
	public static String formatDeliveryAddressExternalId(@NonNull final String externalId)
	{
		return EXTERNAL_IDENTIFIER_PREFIX + "-" + ALBERTA_EXTERNAL_REFERENCE_SYSTEM + "-" + DELIVERY_ADDRESS_PREFIX + externalId;
	}
}
