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

import com.google.common.collect.ImmutableList;
import de.metas.common.delivery.v1.json.request.JsonMappingConfig;
import de.metas.common.util.CoalesceUtil;
import de.metas.shipper.client.nshift.json.JsonAddress;
import de.metas.shipper.client.nshift.json.JsonAddressKind;
import de.metas.common.util.Check;
import de.metas.shipper.client.nshift.json.JsonShipmentReference;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

import javax.annotation.Nullable;
import java.util.AbstractMap;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@UtilityClass
public class NShiftUtil
{
	public static JsonAddress.JsonAddressBuilder buildNShiftAddressBuilder(@NonNull final de.metas.common.delivery.v1.json.JsonAddress commonAddress, @NonNull final JsonAddressKind kind)
	{
		String street1 = commonAddress.getStreet();
		if (Check.isNotBlank(commonAddress.getHouseNo()))
		{
			street1 = street1 + " " + commonAddress.getHouseNo();
		}

		return JsonAddress.builder()
				.kind(kind)
				.name1(commonAddress.getCompanyName1())
				.name2(commonAddress.getCompanyName2())
				.street1(street1)
				.street2(commonAddress.getAdditionalAddressInfo())
				.postCode(commonAddress.getZipCode())
				.city(commonAddress.getCity())
				.countryCode(commonAddress.getCountry());
	}

	public static JsonAddress.JsonAddressBuilder buildNShiftReceiverAddress(
			@NonNull final de.metas.common.delivery.v1.json.JsonAddress deliveryAddress,
			@Nullable final de.metas.common.delivery.v1.json.JsonContact deliveryContact) {

		final JsonAddress.JsonAddressBuilder receiverAddressBuilder = buildNShiftAddressBuilder(deliveryAddress, JsonAddressKind.RECEIVER);

		if (deliveryContact != null) {
			if (Check.isNotBlank(deliveryContact.getPhone())) {
				receiverAddressBuilder.phone(deliveryContact.getPhone());
			}
			if (Check.isNotBlank(deliveryContact.getEmailAddress())) {
				receiverAddressBuilder.email(deliveryContact.getEmailAddress());
			}
			receiverAddressBuilder.attention(deliveryContact.getName());
		} else {
			final String attention = CoalesceUtil.coalesceNotNull(
					deliveryAddress.getAdditionalAddressInfo(),
					deliveryAddress.getCompanyName2(),
					deliveryAddress.getCompanyName1());
			receiverAddressBuilder.attention(attention);
		}

		return receiverAddressBuilder;
	}

	public static List<JsonShipmentReference> getShipmentReferences(
			@NonNull final Iterable<JsonMappingConfig> mappingConfigs,
			@NonNull final Function<String, String> valueProvider,
			@NonNull final String attributeType)
	{
		return StreamSupport.stream(mappingConfigs.spliterator(), false)
				.filter(config -> attributeType.equals(config.getAttributeType()))
				.sorted(Comparator.comparingInt(JsonMappingConfig::getSeqNo))
				.map(config -> {
					final String value = valueProvider.apply(config.getAttributeValue());
					if (Check.isNotBlank(value))
					{
						return new AbstractMap.SimpleImmutableEntry<>(config.getAttributeKey(), value);
					}
					return null;
				})
				.filter(Objects::nonNull)
				.collect(Collectors.groupingBy(
						Map.Entry::getKey,
						LinkedHashMap::new,
						Collectors.mapping(Map.Entry::getValue, Collectors.joining())))
				.entrySet().stream()
				.map(entry -> JsonShipmentReference.builder()
						.kind(Integer.parseInt(entry.getKey()))
						.value(entry.getValue())
						.build())
				.collect(ImmutableList.toImmutableList());
	}


}
