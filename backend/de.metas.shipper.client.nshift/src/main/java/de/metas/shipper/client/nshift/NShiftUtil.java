/*
 * #%L
 * de.metas.shipper.client.nshift
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

package de.metas.shipper.client.nshift;

import de.metas.common.delivery.v1.json.DeliveryMappingConstants;
import de.metas.common.delivery.v1.json.JsonContact;
import de.metas.common.util.Check;
import de.metas.shipper.client.nshift.json.JsonAddress;
import de.metas.shipper.client.nshift.json.JsonAddressKind;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.function.Function;

@UtilityClass
public class NShiftUtil
{
	public static JsonAddress.JsonAddressBuilder buildNShiftAddressBuilder(@NonNull final de.metas.common.delivery.v1.json.JsonAddress commonAddress,
																		   @Nullable final JsonContact contact,
																		   @NonNull final JsonAddressKind kind)
	{
		String street1 = commonAddress.getStreet();
		if (Check.isNotBlank(commonAddress.getHouseNo()))
		{
			street1 = street1 + " " + commonAddress.getHouseNo();
		}

		final JsonAddress.JsonAddressBuilder addressBuilder = JsonAddress.builder()
				.kind(kind)
				.name1(commonAddress.getCompanyName1())
				.name2(commonAddress.getCompanyName2())
				.street1(street1)
				.street2(commonAddress.getAdditionalAddressInfo())
				.postCode(commonAddress.getZipCode())
				.city(commonAddress.getCity())
				.countryCode(commonAddress.getCountry());

		if (contact != null)
		{
			if (Check.isNotBlank(contact.getPhone()))
			{
				addressBuilder.phone(contact.getPhone());
			}
			if (Check.isNotBlank(contact.getEmailAddress()))
			{
				addressBuilder.email(contact.getEmailAddress());
			}
		}

		return addressBuilder;
	}

	public static JsonAddress buildAddressWithAttentionFromMappings(
			@NonNull final de.metas.common.delivery.v1.json.JsonAddress commonAddress,
			@Nullable final JsonContact contact,
			@NonNull final JsonAddressKind kind,
			@NonNull final NShiftMappingConfigs mappingConfigs,
			@NonNull final Function<String, String> valueProvider)
	{
		final String attentionAttributeType = kind == JsonAddressKind.SENDER
				? DeliveryMappingConstants.ATTRIBUTE_TYPE_SENDER_ATTENTION
				: DeliveryMappingConstants.ATTRIBUTE_TYPE_RECEIVER_ATTENTION;
		return buildNShiftAddressBuilder(commonAddress, contact, kind)
				.attention(mappingConfigs.getSingleValue(attentionAttributeType, valueProvider))
				.build();
	}

	public static <T, R> Function<T, Optional<R>> withFallback(
			@NonNull final Function<T, Optional<R>> primary,
			@NonNull final Function<T, Optional<R>> fallback)
	{
		return t -> {
			final Optional<R> value = primary.apply(t);
			return value.isPresent() ? value : fallback.apply(t);
		};
	}
}
