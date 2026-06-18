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
import de.metas.common.delivery.v1.json.request.JsonDeliveryAdvisorRequest;
import de.metas.common.delivery.v1.json.request.JsonDeliveryAdvisorRequestItem;
import de.metas.common.util.Check;
import de.metas.shipper.client.nshift.json.JsonAddress;
import de.metas.shipper.client.nshift.json.JsonAddressKind;
import de.metas.shipper.client.nshift.json.JsonLine;
import de.metas.shipper.client.nshift.json.JsonReference;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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

	/**
	 * Builds the nShift {@link JsonLine} for an advise request, mirroring the ship path's
	 * {@code NShiftShipmentService#buildNShiftLine} two-level structure:
	 * <ul>
	 *   <li>PARCEL level — weight (kg→g) and dimensions (cm→mm) come from the request's parcel fields
	 *       ({@link JsonDeliveryAdvisorRequest#getGrossWeightKg()} / {@code getPackageDimensions()}).</li>
	 *   <li>PER-ITEM level — line references are resolved per {@link JsonDeliveryAdvisorRequestItem} via the
	 *       {@code item.getValue → request.getValue} fallback chain (mirror of
	 *       {@code NShiftShipmentService#buildLineLevelDetailGroups}'s {@code content::getValue → parcel → request}
	 *       chain) and merged across items by reference kind.</li>
	 * </ul>
	 * Shared by {@code NShiftShipAdvisorService} and {@code NShiftOrderAdvisorService} — the two advise
	 * endpoints must build the line identically; keep this the single source for advise-line building.
	 * <p>
	 * For a single-element {@code items} list the emitted line is byte-identical to the previous single-item
	 * advise wire format.
	 */
	public static JsonLine buildAdvisorLine(
			@NonNull final JsonDeliveryAdvisorRequest request,
			@NonNull final NShiftMappingConfigs mappingConfigs)
	{
		final int weightGrams = request.getGrossWeightKg().multiply(BigDecimal.valueOf(1000)).intValue();
		final JsonLine.JsonLineBuilder lineBuilder = JsonLine.builder()
				.lineWeight(weightGrams)
				.references(buildAdvisorLineReferences(request, mappingConfigs));
		if (request.getPackageDimensions() != null)
		{
			final int lengthMM = request.getPackageDimensions().getLengthInCM() * 10;
			final int widthMM = request.getPackageDimensions().getWidthInCM() * 10;
			final int heightMM = request.getPackageDimensions().getHeightInCM() * 10;
			lineBuilder.number(1); // always 1: the advise carries a single physical HU / parcel
			lineBuilder.length(lengthMM);
			lineBuilder.width(widthMM);
			lineBuilder.height(heightMM);
		}
		return lineBuilder.build();
	}

	/**
	 * Resolves the line-level references by iterating the request's items (each with the
	 * {@code item.getValue → request.getValue} fallback chain) and merging the resulting references by kind,
	 * concatenating multiple items' values with a single space — matching {@link NShiftMappingConfigs}'
	 * own per-kind value concatenation. For a single item this returns exactly the references that the previous
	 * single-item path produced.
	 */
	private static List<JsonReference> buildAdvisorLineReferences(
			@NonNull final JsonDeliveryAdvisorRequest request,
			@NonNull final NShiftMappingConfigs mappingConfigs)
	{
		// LinkedHashMap to keep the reference kinds in first-seen order (stable wire output).
		final Map<Integer, StringBuilder> valuesByKind = new LinkedHashMap<>();
		for (final JsonDeliveryAdvisorRequestItem item : request.getItems())
		{
			final Function<String, Optional<String>> chain = withFallback(
					item::getValue,
					attributeValue -> Optional.ofNullable(request.getValue(attributeValue)));
			final Function<String, String> lineValueProvider = attributeValue -> chain.apply(attributeValue).orElse(null);

			for (final JsonReference reference : mappingConfigs.getReferences(DeliveryMappingConstants.ATTRIBUTE_TYPE_LINE_REFERENCE, lineValueProvider))
			{
				final StringBuilder sb = valuesByKind.get(reference.getKind());
				if (sb == null)
				{
					valuesByKind.put(reference.getKind(), new StringBuilder(reference.getValue()));
				}
				else
				{
					sb.append(" ").append(reference.getValue());
				}
			}
		}

		final List<JsonReference> references = new ArrayList<>();
		valuesByKind.forEach((kind, sb) -> references.add(JsonReference.builder()
				.kind(kind)
				.value(sb.toString())
				.build()));
		return references;
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
