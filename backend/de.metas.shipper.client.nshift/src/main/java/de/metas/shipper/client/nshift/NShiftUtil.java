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
import de.metas.common.util.Check;
import de.metas.shipper.client.nshift.json.JsonAddress;
import de.metas.shipper.client.nshift.json.JsonAddressKind;
import de.metas.shipper.client.nshift.json.JsonLine;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

import javax.annotation.Nullable;
import java.math.BigDecimal;
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
	 *   <li>LINE references — resolved ONCE via {@link JsonDeliveryAdvisorRequest#getValue(String)}, exactly as the
	 *       ship path resolves line references via {@code JsonDeliveryOrderParcel.getValue}. The request's
	 *       {@code getValue} already aggregates the per-product attributes over its items (product name/value,
	 *       customs tariff, total value, …), so parcel-level attributes resolve once (no per-item duplication) and
	 *       per-product attributes are aggregated consistently with the ship path.</li>
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
		final Function<String, String> lineValueProvider = request::getValue;
		final JsonLine.JsonLineBuilder lineBuilder = JsonLine.builder()
				.lineWeight(weightGrams)
				.references(mappingConfigs.getReferences(DeliveryMappingConstants.ATTRIBUTE_TYPE_LINE_REFERENCE, lineValueProvider));
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
