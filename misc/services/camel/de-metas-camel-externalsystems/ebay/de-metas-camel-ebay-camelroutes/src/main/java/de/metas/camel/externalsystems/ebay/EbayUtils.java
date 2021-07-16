/*
 * #%L
 * de-metas-camel-ebay-camelroutes
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

package de.metas.camel.externalsystems.ebay;

import de.metas.camel.externalsystems.ebay.api.model.Order;
import lombok.NonNull;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;

import static de.metas.camel.externalsystems.ebay.EbayConstants.EXTERNAL_ID_PREFIX;

public class EbayUtils
{

	@NonNull
	public static LocalDate toLocalDate(@NonNull final String in)
	{
		Instant instant = Instant.parse(in);
		LocalDate localDate = LocalDate.ofInstant(instant, ZoneOffset.UTC);
		return localDate;
	}

	@NonNull
	public static String bPartnerIdentifier(@NonNull final Order order)
	{
		return EXTERNAL_ID_PREFIX + order.getBuyer().getUsername();
	}
	
	@NonNull
	public static String formatExternalId(@NonNull final String externalId)
	{
		return EXTERNAL_ID_PREFIX + externalId;
	}
	
}
