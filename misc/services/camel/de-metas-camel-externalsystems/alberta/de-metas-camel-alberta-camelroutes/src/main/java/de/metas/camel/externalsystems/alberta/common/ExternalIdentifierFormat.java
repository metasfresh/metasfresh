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

package de.metas.camel.externalsystems.alberta.common;

import de.metas.camel.externalsystems.alberta.ordercandidate.GetOrdersRouteConstants;
import de.metas.camel.externalsystems.alberta.patient.GetPatientsRouteConstants;
import lombok.NonNull;

public class ExternalIdentifierFormat
{
	@NonNull
	public static String formatExternalId(@NonNull final String externalId)
	{
		return CommonAlbertaConstants.EXTERNAL_IDENTIFIER_PREFIX + "-" + CommonAlbertaConstants.ALBERTA_EXTERNAL_REFERENCE_SYSTEM + "-" + externalId;
	}

	@NonNull
	public static String formatMainShippingAddressExternalId(@NonNull final String externalId)
	{
		return CommonAlbertaConstants.EXTERNAL_IDENTIFIER_PREFIX + "-" + CommonAlbertaConstants.ALBERTA_EXTERNAL_REFERENCE_SYSTEM + "-" + GetPatientsRouteConstants.SHIPPING_ADDR_PREFIX + externalId;
	}

	@NonNull
	public static String formatBillingAddressExternalId(@NonNull final String externalId)
	{
		return CommonAlbertaConstants.EXTERNAL_IDENTIFIER_PREFIX + "-" + CommonAlbertaConstants.ALBERTA_EXTERNAL_REFERENCE_SYSTEM + "-" + GetPatientsRouteConstants.BILLING_ADDR_PREFIX + externalId;
	}

	@NonNull
	public static String formatDeliveryAddressExternalId(@NonNull final String externalId)
	{
		return CommonAlbertaConstants.EXTERNAL_IDENTIFIER_PREFIX + "-" + CommonAlbertaConstants.ALBERTA_EXTERNAL_REFERENCE_SYSTEM + "-" + GetOrdersRouteConstants.DELIVERY_ADDRESS_PREFIX + externalId;
	}

	@NonNull
	public static String formatDeliveryAddressExternalIdHash(@NonNull final String externalId, @NonNull final String locationHash)
	{
		return CommonAlbertaConstants.EXTERNAL_IDENTIFIER_PREFIX + "-" + CommonAlbertaConstants.ALBERTA_EXTERNAL_REFERENCE_SYSTEM + "-" + externalId + "_" + locationHash;
	}
}
